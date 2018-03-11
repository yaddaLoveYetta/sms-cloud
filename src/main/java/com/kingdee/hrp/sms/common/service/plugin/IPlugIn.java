package com.kingdee.hrp.sms.common.service.plugin;

import java.util.List;
import java.util.Map;

/**
 * 后台系统-基础资料业务插件接口<br>
 * 
 * 用于处理个性化业务需求<br>
 * 
 * 例如：基础资料操作前的数据校验，操作前的关联操作<br>
 * 
 * 操作后的数据校验，关联操作等等跟业务相关但又缺乏共性的逻辑<br>
 * 
 * @ClassName IPlugIn
 * @author yadda
 * @date 2017-04-27 17:40:40 星期四
 */
public interface IPlugIn {
	/**
	 * 基础资料新增前操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param formData
	 *            单据模板
	 * @param data
	 *            业务数据
	 * @return
	 */
	public PlugInRet beforeSave(int classId, Map<String, Object> formData, Json data);

	/**
	 * 基础资料新增后操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param id
	 *            新增的资料内码
	 * @param data
	 *            业务数据
	 * @return
	 */
	public PlugInRet afterSave(int classId, String id, JSONObject data);

	/**
	 * 基础资料修改前操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param data
	 *            业务数据
	 * @return
	 */
	public PlugInRet beforeModify(int classId, String id, Map<String, Object> formData, JSONObject data);

	/**
	 * 单据/基础资料修改分录数据前事件
	 * 
	 * @Title beforeEntryModify
	 * @param classId
	 *            事务类型
	 * @param primaryId
	 *            主表内码
	 * @param entryId
	 *            子表内码
	 * @param formData
	 *            模板数据
	 * @param data
	 *            修改数据内容
	 * @return
	 * @return PlugInRet
	 * @date 2017-07-12 09:05:42 星期三
	 */
	public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formData, JSONObject data);

	/**
	 * 基础资料修改后操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param data
	 *            业务数据
	 * @return
	 */
	public PlugInRet afterModify(int classId, JSONObject data);

	/**
	 * 基础资料删除前操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param data
	 *            删除的内码集合
	 * @return
	 */
	public PlugInRet beforeDelete(int classId, Map<String, Object> formData, String data);

	/**
	 * 单据/基础资料分录删除前事件
	 * 
	 * @Title beforeentryDelete
	 * @param classId
	 *            事务类型
	 * @param primaryId
	 *            主表内码
	 * @param entryId
	 *            子表内码
	 * @param formData
	 *            模板数据
	 * @return
	 * @return PlugInRet
	 * @date 2017-07-12 09:10:46 星期三
	 */
	public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, Map<String, Object> formData);

	/**
	 * 基础资料删除后操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param delData
	 *            待删除的数据明细集合
	 * @param items
	 *            删除的内码集合
	 * @return
	 */
	public PlugInRet afterDelete(int classId, List<Map<String, Object>> delData, String items);

	/**
	 * 基础资料查询前操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param param
	 *            查询参数
	 * @return
	 */
	public PlugInRet beforeQuery(int classId, Map<String, Object> param);

	/**
	 * 基础资料查询后操作
	 * 
	 * @param classId
	 *            业务类型
	 * @param list
	 *            查询结果集
	 * @return
	 */
	public PlugInRet afterQuery(int classId, List<Map<String, Object>> list);

	/**
	 * 
	 * @Title getConditions
	 * @param classId
	 * @param formData
	 * @param userType
	 * @param userId
	 * @return
	 * @return String
	 * @date 2017-05-23 17:52:35 星期二
	 */
	public String getConditions(int classId, Map<String, Object> formData, String conditon);

	/**
	 * 
	 * @Title getJson
	 * @param classId
	 * @param formData
	 * @param json
	 * @param userType
	 * 
	 * @return JSONObject
	 *
	 *         2017年5月27日下午2:41:32
	 */
	public JSONObject getJson(int classId, Map<String, Object> formData, JSONObject json);

}
