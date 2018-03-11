package com.kingdee.hrp.sms.common.service.plugin;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.eas.hrp.sms.dao.generate.PluginsMapper;
import com.kingdee.eas.hrp.sms.model.Plugins;
import com.kingdee.eas.hrp.sms.model.PluginsExample;
import com.kingdee.eas.hrp.sms.model.PluginsExample.Criteria;
import com.kingdee.eas.hrp.sms.util.Environ;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于生产服务中所需的所有插件，并统一提供所有插件的方法调用
 * 
 * @ClassName BaseItemPlugInFactory
 * @author yadda
 * @date 2017-04-27 17:31:28 星期四
 */
public class PlugInFactory implements IPlugIn {

	// 业务类型
	private int classId;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
		init();
	}

	// 用于存放所有的插件序列
	private List<IPlugIn> plugIns = new ArrayList<IPlugIn>();

	public PlugInFactory() {

	}

	public PlugInFactory(int classId) {
		super();
		this.classId = classId;
		init();
	}

	/**
	 * 初始化当前业务类型所有插件
	 */
	private void init() {

		Assert.assertNotNull("classId不能为空!", classId);
		
		SqlSession sqlSession = Environ.getBean(SqlSession.class);

		PluginsMapper mapper = sqlSession.getMapper(PluginsMapper.class);

		PluginsExample example = new PluginsExample();
		example.setOrderByClause("[index] ASC");
		Criteria criteria = example.createCriteria();

		criteria.andClassIdEqualTo(classId);

		List<Plugins> plugInNameList = mapper.selectByExample(example);

		this.plugIns = getPlugInsInstance(plugInNameList);

	}

	private List<IPlugIn> getPlugInsInstance(List<Plugins> plugInNameList) {

		List<IPlugIn> plugIns = new ArrayList<IPlugIn>();

		for (Plugins plugin : plugInNameList) {

			String plugInName = plugin.getPlugName();

			try {

				Class clazz = Class.forName(plugInName);

				if (isPlugIn(clazz, "com.kingdee.eas.hrp.sms.service.plugin.IPlugIn")) {

					/**
					 * 从spring中获取插件bean，如果没有则将插件加入到spring中管理- bean注册的名字为插件不包含包名的类名
					 */
					String className = clazz.getName();
					String beanName = className.substring(className.lastIndexOf(".") + 1);
					Object instance = Environ.getBean(beanName);

					if (null == instance) {
						registerBean(beanName, clazz);
						instance = Environ.getBean(beanName);
					}
					plugIns.add((IPlugIn) instance);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
		return plugIns;
	}

	/**
	 * 将插件加入到spring中管理-AOP的必要
	 * 
	 * @param clazz
	 */
	private void registerBean(String name, Class clazz) {

		// 将applicationContext转换为ConfigurableApplicationContext
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) Environ.getApplicationContext();

		// 获取bean工厂并转换为DefaultListableBeanFactory
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

		// 通过BeanDefinitionBuilder创建bean定义
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

		// 设置属性userAcctDAO,此属性引用已经定义的bean:userAcctDAO
		// beanDefinitionBuilder.addPropertyReference("DAO", "DAO");

		// 注册bean
		defaultListableBeanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getRawBeanDefinition());

	}

	/**
	 * 判断插件是否实现系统定义的接口"com.kingdee.eas.hrp.sms.service.plugin.IPlugIn"
	 * 
	 * @param c
	 * @param szInterface
	 * @return
	 */
	public boolean isPlugIn(Class c, String szInterface) {

		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getName().equals(szInterface)) {
						return true;
					} else if (isPlugIn(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return isPlugIn(c.getSuperclass(), szInterface);
		}
		return false;
	}

	@Override
	public PlugInRet beforeSave(int classId, Map<String, Object> formData, JSONObject data) {

		PlugInRet ret = new PlugInRet();
		for (IPlugIn plugin : plugIns) {

			ret = plugin.beforeSave(classId, formData, data);
			data = (JSONObject) ret.getData();
		}
		return ret;
	}

	@Override
	public PlugInRet afterSave(int classId, String id, JSONObject data) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.afterSave(classId, id, data);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet beforeModify(int classId, String id, Map<String, Object> formData, JSONObject data) {

		PlugInRet ret = new PlugInRet();
		for (IPlugIn plugin : plugIns) {

			ret = plugin.beforeModify(classId, id, formData, data);
			// data = (JSONObject) ret.getData();
		}
		return ret;
	}

	@Override
	public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formData, JSONObject data) {
		PlugInRet ret = new PlugInRet();
		for (IPlugIn plugin : plugIns) {
			ret = plugin.beforeEntryModify(classId, primaryId, entryId, formData, data);
		}
		return ret;
	}

	@Override
	public PlugInRet afterModify(int classId, JSONObject data) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.afterModify(classId, data);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet beforeDelete(int classId, Map<String, Object> formData, String data) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.beforeDelete(classId, formData, data);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, Map<String, Object> formData) {
		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.beforeEntryDelete(classId, primaryId, entryId, formData);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet afterDelete(int classId, List<Map<String, Object>> delData, String items) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.afterDelete(classId, delData, items);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet beforeQuery(int classId, Map<String, Object> param) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.beforeQuery(classId, param);
			// if (ret.getCode() != 200) {
			if (ret.getData() != null) {
				// 插件返回了condition查询条件
				return ret;
			}
		}
		return null;
	}

	@Override
	public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {

		for (IPlugIn plugin : plugIns) {

			PlugInRet ret = plugin.afterQuery(classId, list);
			if (ret.getCode() != 200) {
				// 插件返回了阻止继续运行的情况--返回不继续执行
				return ret;
			}
		}
		return null;
	}

	@Override
	public String getConditions(int classId, Map<String, Object> formData, String conditon) {

		String ret = conditon;
		for (IPlugIn plugin : plugIns) {

			ret = plugin.getConditions(classId, formData, ret);

		}

		return ret;
	}

	@Override
	public JSONObject getJson(int classId, Map<String, Object> formData, JSONObject json) {

		JSONObject ret = json;
		for (IPlugIn plugin : plugIns) {

			ret = plugin.getJson(classId, formData, ret);

		}

		return ret;
	}

}
