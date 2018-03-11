package com.kingdee.hrp.sms.common.service.plugin;


import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.List;
import java.util.Map;

/**
 * 插件适配器
 * 
 * @ClassName PlugInAdpter
 * @author yadda
 * @date 2017-04-27 17:32:12 星期四
 */
public abstract class PlugInAdpter implements IPlugIn {

	PlugInRet result = new PlugInRet();

	@Override
	public PlugInRet beforeSave(int classId, Map<String, Object> formData, JSONPObject data) {
		return result;
	}

	@Override
	public PlugInRet afterSave(int classId, String id, JSONObject data) {
		return result;
	}

	@Override
	public PlugInRet beforeModify(int classId, String id, Map<String, Object> formData, JSONObject data) {
		return result;
	}
	@Override
	public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formData, JSONObject data) {
		return result;
	}
	@Override
	public PlugInRet afterModify(int classId, JSONObject data) {
		return result;
	}

	@Override
	public PlugInRet beforeDelete(int classId, Map<String, Object> formData, String data) {
		return result;
	}

	@Override
	public PlugInRet afterDelete(int classId,List<Map<String, Object>> data, String items) {
		return result;
	}
	@Override
	public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, Map<String, Object> formData) {
		return result;
	}
	@Override
	public PlugInRet beforeQuery(int classId, Map<String, Object> param) {
		return result;
	}

	@Override
	public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {
		return result;
	}

	@Override
	public String getConditions(int classId, Map<String, Object> formData, String conditon) {
		return conditon;
	}

	@Override
	public JSONObject getJson(int classId, Map<String, Object> formData, JSONObject json) {
		return json;
	}
}
