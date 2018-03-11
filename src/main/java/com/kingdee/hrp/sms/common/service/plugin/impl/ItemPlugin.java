package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.eas.hrp.sms.dao.generate.*;
import com.kingdee.eas.hrp.sms.exception.BusinessLogicRunTimeException;
import com.kingdee.eas.hrp.sms.exception.PlugInRuntimeException;
import com.kingdee.eas.hrp.sms.model.*;
import com.kingdee.eas.hrp.sms.model.UserExample.Criteria;
import com.kingdee.eas.hrp.sms.service.api.ITemplateService;
import com.kingdee.eas.hrp.sms.service.api.IWebService;
import com.kingdee.eas.hrp.sms.service.api.sys.ISyncHRPService;
import com.kingdee.eas.hrp.sms.service.plugin.PlugInAdpter;
import com.kingdee.eas.hrp.sms.service.plugin.PlugInRet;
import com.kingdee.eas.hrp.sms.util.Environ;
import com.kingdee.eas.hrp.sms.util.SessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ItemPlugin extends PlugInAdpter {

	@Resource
	private ITemplateService templateService;
	@Resource
	private ISyncHRPService syncHRPService;

	@Resource
	IWebService IWebService;

	// 当业务用户查询时，相关item需做数据隔离
	List<Integer> isolateClassIdList = new ArrayList<Integer>(Arrays.asList(2019, 2020, 1001, 1005, 3010, 3020, 3030, 1023, 1007));
	// 需要同步和审核classId
	List<Integer> reviewAndSyncClassIdList = new ArrayList<Integer>(Arrays.asList(1005, 3010, 3020, 3030, 1023, 1007));

	@Override
	@Transactional
	public PlugInRet beforeDelete(int classId, Map<String, Object> formData, String data) {

		ITemplateService templateService = Environ.getBean(ITemplateService.class);
		// 判断是否HRP的同步删除
		boolean flag = true;
		if (data.length() > 2) {
			String temp = data.substring(data.length() - 2);
			if (temp.equals(",,")) {
				flag = false;
				data = data.substring(0, data.length() - 2);
			}
		}

		// 装配待删除ID
		String[] idString = data.split(",");
		List<String> idList = new ArrayList<String>(Arrays.asList(idString));

		// 需要审核的数据检查是否为未审核状态
		if (reviewAndSyncClassIdList.contains(classId) && flag) {
			JSONObject deleteJson = JSONObject.parseObject("{'delete':'delete'}");
			for (String id : idList) {
				checkIfReview(classId, id, deleteJson);
			}
		}

		// 查找引用待删除资料的模板
		SqlSession sqlSession = Environ.getBean(SqlSession.class);
		FormFieldsMapper mapper = sqlSession.getMapper(FormFieldsMapper.class);
		FormFieldsExample example = new FormFieldsExample();
		com.kingdee.eas.hrp.sms.model.FormFieldsExample.Criteria criteria = example.createCriteria();

		criteria.andLookUpClassIDEqualTo(classId);
		criteria.andLookUpTypeEqualTo(1);

		List<FormFields> list = mapper.selectByExample(example);

		JSONArray orderByArray = new JSONArray();
		JSONObject orderByItem = new JSONObject(true);

		orderByItem.put("fieldKey", "number");
		orderByItem.put("orderDirection", "ASC");
		orderByArray.add(orderByItem);

		orderByItem = new JSONObject();
		orderByItem.put("fieldKey", "name");
		orderByItem.put("orderDirection", "ASC");
		orderByArray.add(orderByItem);

		String orderBy = JSON.toJSONString(orderByArray);

		for (FormFields ff : list) {
			Integer citedClassId = ff.getClassId();
			String key = ff.getKey();

			for (String id : idList) {
				JSONArray conditionArry = new JSONArray();
				JSONObject condition = new JSONObject(true);
				condition.put("fieldKey", key);
				condition.put("logicOperator", "=");
				condition.put("value", id);
				condition.put("needConvert", false);
				conditionArry.add(condition);

				Map<String, Object> result = templateService.getItems(citedClassId, conditionArry.toString(), orderBy, 1, 10);

				if ((long) result.get("count") > 0) {
					Map<String, Object> errData = templateService.getItemById(classId, id);
					throw new PlugInRuntimeException("该记录(" + errData.get("number") + ")已被引用，无法删除");
				}
			}
		}

		// 同步删除
		if (reviewAndSyncClassIdList.contains(classId) && flag) {

			JSONObject delJson = new JSONObject(true);
			delJson.put("classId", classId);
			delJson.put("items", data);
			// 调用HRP删除接口
			String syncRet = IWebService.webService(delJson.toString(), "delSms2hrpBaseData");
			if (syncRet == null || syncRet.equals("")) {
				throw new RuntimeException("同步删除医院数据时网络异常！");
			}
			JSONObject syncJson = JSONObject.parseObject(syncRet);
			// 获取接口返回值数据
			String syncData = syncJson.getString("data");
			if (!(null == syncData || "".equals(syncData))) {
				throw new PlugInRuntimeException("记录无法在HRP中删除，删除失败");
			}
		}
		if (classId == 1003) {
			String[] split = data.split("\\,");
			SqlSession rolesqlSession = (SqlSession) Environ.getBean("sqlSession");
			RoleMapper roleMapper = rolesqlSession.getMapper(RoleMapper.class);
			AccessControlMapper accessControlMapper = rolesqlSession.getMapper(AccessControlMapper.class);
			for (int i = 0; i < split.length; i++) {
				Role role = roleMapper.selectByPrimaryKey(split[i]);
				AccessControlExample e = new AccessControlExample();
				com.kingdee.eas.hrp.sms.model.AccessControlExample.Criteria c = e.createCriteria();
				c.andRoleIdEqualTo(role.getRoleId());
				accessControlMapper.deleteByExample(e);
			}

		}

		return super.beforeDelete(classId, formData, data);
	}

	@Override
	public PlugInRet afterDelete(int classId, List<Map<String, Object>> data, String items) {
		return super.afterDelete(classId, data, items);
	}

	@Override
	public PlugInRet beforeModify(int classId, String id, Map<String, Object> formData, JSONObject json) {

		// 需要审核的数据检查审核状态
		if (reviewAndSyncClassIdList.contains(classId)){
			checkIfReview(classId, id, json);}

		modifyCheckMustInput(classId, formData, json);

		// 如果json为空说明是同步到HRP修改同步字段，不用验证是否数据重复
		if (classId / 100 == 10 && !json.isEmpty()) {

			checkIfExistRecord(classId, id, formData, json);

		}

		if (classId == 1005) {
			if (json.get("taxRate") != null && !json.get("taxRate").equals("")) {
				if (json.get("taxRate").toString().matches("^[-\\+]?[.\\d]*$")) {

				} else {
					throw new BusinessLogicRunTimeException("税率格式不正确");
				}
			}
		}

		// 如果字段含有同步到HRP的字段syncStatus，设置同步状态
		if (reviewAndSyncClassIdList.contains(classId)) {
			if (json.isEmpty()) { // 构造的json为空即同步到HRP的记录需将同步状态标记为已同步
				json.put("syncStatus", "true");
			} else { // 构造的json不为空即为修改记录，需将同步状态标记为未同步
				if (!json.containsKey("syncStatus")) {
					json.put("syncStatus", "false");
				}
			}
		}

		if (classId == 1001) {
			SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
			UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
			UserExample e = new UserExample();
			Criteria c = e.createCriteria();
			c.andNameEqualTo(json.getString("name"));
			c.andUserIdNotEqualTo(id);
			// UserID和name查询记录
			List<User> o = userMapper.selectByExample(e);
			if (o.size() > 0) {
				throw new PlugInRuntimeException("账号已存在,请重新输入！");
			}
		}

		PlugInRet ret = new PlugInRet();
		ret.setCode(200);
		ret.setData(json);
		return ret;

	}

	private void checkIfReview(int classId, String id, JSONObject json) {

		// 如果json是“{}”说明是同步到HRP数据修改同步状态，不需要验证，如果json有review字段说明是HRP同步过来，也不需要验证
		if (!json.isEmpty() && !json.containsKey("review")) {
			Map<String, Object> result = templateService.getItemById(classId, id);
			short review;
			if (result.containsKey("review")) {
				if (null == result.get("review")) {
					review = 1;
				} else {
					review = (short) result.get("review");
				}
				if (1 == review) {
					throw new PlugInRuntimeException("记录已审核，无法进行操作！");
				}
			}
		}
	}

	@Override
	public PlugInRet beforeSave(int classId, Map<String, Object> formData, JSONObject json) {

		saveCheckMustInput(classId, formData, json);

		if (classId / 100 == 10 || classId == 3010) {
			// 物料证件，一个证件可以多个物料使用
			// if (classId / 100 == 10 || classId == 3010 || classId == 3020) {

			String id = "-1";
			checkIfExistRecord(classId, id, formData, json);
		}

		// 如果字段含有同步到HRP的字段syncStatus，设置同步状态
		if (reviewAndSyncClassIdList.contains(classId)) {
			if (!json.containsKey("syncStatus")) {
				json.put("syncStatus", "false");
			}
			if (!json.containsKey("review")) {
				json.put("review", "false");
			}
		}

		if (classId == 1005) {
			if (json.get("taxRate") != null && !json.get("taxRate").equals("")) {
				if (json.get("taxRate").toString().matches("^[-\\+]?[.\\d]*$")) {

				} else {
					throw new BusinessLogicRunTimeException("税率格式不正确");
				}
			}
		}

		if (classId == 1001) {
			SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
			UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
			UserExample e = new UserExample();
			Criteria c = e.createCriteria();
			c.andNameEqualTo(json.getString("name"));
			// UserID和name查询
			List<User> o = userMapper.selectByExample(e);
			if (o.size() > 0) {
				throw new PlugInRuntimeException("账号已存在,请重新输入！");
			}
		}

		PlugInRet ret = new PlugInRet();
		ret.setCode(200);
		ret.setData(json);
		return ret;
	}

	private void checkIfExistRecord(int classId, String id, Map<String, Object> formData, JSONObject data) {

		// 主表资料描述信息
		FormClass formClass = (FormClass) formData.get("formClass");
		// 主表主键
		String primaryKey = formClass.getPrimaryKey();

		ITemplateService templateService = Environ.getBean(ITemplateService.class);
		JSONArray orderByArray = new JSONArray();
		JSONObject orderByItem = new JSONObject(true);

		orderByItem.put("fieldKey", "number");
		orderByItem.put("orderDirection", "ASC");
		orderByArray.add(orderByItem);

		String orderBy = JSON.toJSONString(orderByArray);

		JSONArray conditionArry = new JSONArray();
		JSONObject condition = new JSONObject(true);
		// condition.put("fieldKey", "name");
		// condition.put("logicOperator", "=");
		// condition.put("value", data.get("name"));
		// conditionArry.add(condition);

		// condition = new JSONObject(true);
		condition.put("andOr", "and");
		condition.put("fieldKey", "number");
		condition.put("logicOperator", "=");
		condition.put("value", data.get("number"));
		conditionArry.add(condition);

		condition = new JSONObject();
		condition.put("fieldKey", primaryKey);
		condition.put("logicOperator", "!=");
		condition.put("value", id);
		conditionArry.add(condition);

		Map<String, Object> result = templateService.getItems(classId, conditionArry.toString(), orderBy, 1, 10);

		if (classId == 1026) {
			// EAS"单据类型"资料 代码可能重复，此处不校验单据类型代码重复
		} else {
			if ((long) result.get("count") > 0) {
				throw new PlugInRuntimeException("该记录已存在,代码重复");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void saveCheckMustInput(int classId, Map<String, Object> formData, JSONObject json) {

		String userType = SessionUtil.getUserType();
		// 用户特殊业务判断，当用户类型是系统用户时，该用户不能选择供应商
		if (classId == 1001) {
			if ("QpXq24FxxE6c3lvHMPyYCxACEAI=".equals(json.getString("type"))) {
				if (json.getString("supplier") != null && !"".equals(json.getString("supplier")) && !"0".equals(json.getString("supplier"))) {
					throw new PlugInRuntimeException("系统用户不能选择供应商");
				}
			}
		}

		// 证件特殊业务判断，起始日期必须小于结束日期
		if (classId == 3010 || classId == 3020) {
			String beginDate = json.getString("beginDate");
			String endDate = json.getString("endDate");

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dt1 = df.parse(beginDate);
				Date dt2 = df.parse(endDate);
				if (dt1.getTime() > dt2.getTime()) {
					throw new BusinessLogicRunTimeException("起始日期必须小于结束日期");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 中标库特殊业务判断，生效日期必须小于失效日期
		if (classId == 3030) {
			String beginDate = json.getString("effectualDate");
			String endDate = json.getString("uneffectualDate");

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dt1 = df.parse(beginDate);
				Date dt2 = df.parse(endDate);
				if (dt1.getTime() >= dt2.getTime()) {
					throw new BusinessLogicRunTimeException("生效日期必须小于失效日期");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 如果flag是true，表明这个字段需要验证是否非空，新增需要验证全部字段
		boolean flag = false;
		// 主表字段模板
		Map<String, FormFields> formFields = (Map<String, FormFields>) ((Map<String, Object>) formData.get("formFields")).get("0"); // 主表的字段模板
		Set<String> keySet = formFields.keySet();
		StringBuilder errMsg = new StringBuilder();
		for (String key : keySet) {
			flag = false;
			FormFields ff = formFields.get(key);
			int mustInput = ff.getMustInput();
			if (("QpXq24FxxE6c3lvHMPyYCxACEAI=").equals(userType)) {
				if ((mustInput & 1) == 1) {
					flag = true;
				}
			} else {
				if ((mustInput & 4) == 4) {
					flag = true;
				}
			}
			if (flag) {
				if (json.getString(key) == null || json.getString(key).equals("")) {
					errMsg.append(ff.getName()).append(" ");
				}
			}
		}

		if (errMsg.length() > 0) {
			throw new PlugInRuntimeException(errMsg.toString() + "为必填值");
		}
	}

	@SuppressWarnings("unchecked")
	private void modifyCheckMustInput(int classId, Map<String, Object> formData, JSONObject json) {

		// json为空说明同步修改同步状态，不用检查
		if (json.isEmpty()){
			return;}

		String userTyepe = SessionUtil.getUserType();
		// 用户特殊业务判断，当用户类型是系统用户时，该用户不能选择供应商
		if (classId == 1001) {
			if ("QpXq24FxxE6c3lvHMPyYCxACEAI=".equals(json.getString("type"))) {
				if (json.getString("supplier") != null && !"".equals(json.getString("supplier")) && !"0".equals(json.getString("supplier"))) {
					throw new PlugInRuntimeException("系统用户不能选择供应商");
				}
			}
		}

		// 证件特殊业务判断，起始日期必须小于结束日期(只是更新entry数据时不用检查)
		if (json.getString("beginDate") != null && json.getString("endDate") != null) {
			if ((classId == 3010 || classId == 3020)) {
				String beginDate = json.getString("beginDate");
				String endDate = json.getString("endDate");

				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date dt1;
					dt1 = df.parse(beginDate);
					Date dt2 = df.parse(endDate);
					if (dt1.getTime() > dt2.getTime()) {
						throw new BusinessLogicRunTimeException("起始日期必须小于结束日期");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 中标库特殊业务判断，生效日期必须小于失效日期
		if (classId == 3030) {
			String beginDate = json.getString("effectualDate");
			String endDate = json.getString("uneffectualDate");

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dt1 = df.parse(beginDate);
				Date dt2 = df.parse(endDate);
				if (dt1.getTime() >= dt2.getTime()) {
					throw new BusinessLogicRunTimeException("生效日期必须小于失效日期");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 如果flag是true，表明这个字段需要验证是否非空,修改只验证修改的字段
		boolean flag = false;
		// 主表字段模板
		Map<String, FormFields> formFields = (Map<String, FormFields>) ((Map<String, Object>) formData.get("formFields")).get("0"); // 主表的字段模板
		Set<String> keySet = json.keySet();
		StringBuilder errMsg = new StringBuilder();
		for (String key : keySet) {
			flag = false;
			if (key.equals("entry")){
				continue;}
			FormFields ff = formFields.get(key);
			if (null == ff) {
				throw new PlugInRuntimeException("字段：" + key + "没有配置到模板上");
			}
			int mustInput = ff.getMustInput();
			if (("QpXq24FxxE6c3lvHMPyYCxACEAI=").equals(userTyepe)) {
				if ((mustInput & 2) == 2) {
					flag = true;
				}
			} else {
				if ((mustInput & 8) == 8) {
					flag = true;
				}
			}
			if (flag) {
				if (json.getString(key) == null || json.getString(key).equals("")) {
					errMsg.append(ff.getName()).append(" ");
				}
			}
		}

		if (errMsg.length() > 0) {
			throw new PlugInRuntimeException(errMsg.toString() + "为必填值");
		}

	}

	@Override
	public PlugInRet beforeQuery(int classId, Map<String, Object> param) {

		return super.beforeQuery(classId, param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.hrp.sms.service.plugin.PlugInAdpter#getConditions(int, java.util.Map, java.lang.String)
	 */
	@Override
	public String getConditions(int classId, Map<String, Object> formData, String conditon) {

		String userType = SessionUtil.getUserType();
		String userId = SessionUtil.getUserId();

		if (userId == "") {
			return conditon;
		}

		String supplier = SessionUtil.getUserLinkSupplier();

		// 当业务用户查询时，相关item需做数据隔离，增加condition条件

		JSONArray conditionArray = JSONArray.parseArray(conditon);
		if (isolateClassIdList.contains(classId)) {

			if ("B3sMo22ZLkWApjO/oEeDOxACEAI=".equals(userType)) {

				JSONObject con = new JSONObject(true);
				if (classId == 1005) {
					con.put("fieldKey", "id");
				} else {
					con.put("fieldKey", "supplier");
				}
				con.put("logicOperator", "=");
				con.put("value", supplier);
				con.put("needConvert", false);
				if (null == conditionArray) {
					conditionArray = new JSONArray();
				}
				conditionArray.add(con);
			}
		}

		// 判断是否传入了供应商筛选条件
		if (null != conditionArray && !conditionArray.isEmpty()) {

			for (int i = 0; i < conditionArray.size(); i++) {
				JSONObject conFromQuery = conditionArray.getJSONObject(i);
				if ("supplier".equals(conFromQuery.get("fieldKey"))) {
					supplier = conFromQuery.getString("value");
					break;
				}
			}
		}

		if (classId == 1013 && null != supplier && !"".equals(supplier)) {

			JSONObject con = new JSONObject(true);
			StringBuilder approveSupplierId = new StringBuilder();

			SqlSession sqlSession = Environ.getBean(SqlSession.class);
			ApprovedSupplierMapper mapper = sqlSession.getMapper(ApprovedSupplierMapper.class);
			ApprovedSupplierExample example = new ApprovedSupplierExample();
			com.kingdee.eas.hrp.sms.model.ApprovedSupplierExample.Criteria criteria = example.createCriteria();
			criteria.andSupplierEqualTo(supplier);
			List<ApprovedSupplier> selectByExample = mapper.selectByExample(example);
			for (ApprovedSupplier approveSupplier : selectByExample) {
				approveSupplierId.append("'").append(approveSupplier.getMaterialItem()).append("'").append(",");
			}

			if (approveSupplierId.length() > 0) {
				approveSupplierId.deleteCharAt(approveSupplierId.length() - 1);
			} else {
				approveSupplierId.append("''");
			}

			con.put("andOr", "and");
			con.put("fieldKey", "id");
			con.put("logicOperator", "in");
			con.put("value", approveSupplierId.toString());
			con.put("needConvert", false);
			if (null == conditionArray) {
				conditionArray = new JSONArray();
			}
			conditionArray.add(con);
		}
		if (null == conditionArray || conditionArray.isEmpty()){
			return conditon;}
		return conditionArray.toString();
	}

	@Override
	public JSONObject getJson(int classId, Map<String, Object> formData, JSONObject json) {

		JSONObject jsonData = json;
		if (reviewAndSyncClassIdList.contains(classId)) {
			jsonData.put("reviwe", "true");
			jsonData.put("syncStatus", "true");
			return jsonData;
		}
		return json;

	}

}
