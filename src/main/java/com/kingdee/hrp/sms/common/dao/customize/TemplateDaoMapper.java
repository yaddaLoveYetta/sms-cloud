package com.kingdee.hrp.sms.common.dao.customize;

import java.util.List;
import java.util.Map;

/**
 * 模板操作接口
* @ClassName TemplateDaoMapper 
* @author yadda 
* @date 2017-04-20 16:46:36 星期四
 */
public interface TemplateDaoMapper {

	/**
	 * 通过模板查询数据
	 * 
	 * @param statementParam
	 *            查询语句结构
	 * @return
	 */
	List<Map<String, Object>> getItems(Map<String, Object> statementParam);

	/**
	 * 通过模板插入数据
	 * 
	 * @Title addItem
	 * @param statement
	 * @return int
	 * @date 2017-04-27 14:36:32 星期四
	 */
	int add(Map<String, Object> statement);

	/**
	 * 通过模板更新数据
	 * 
	 * @Title addItem
	 * @param statement
	 * @return int
	 * @date 2017-04-27 14:36:32 星期四
	 */
	void edit(Map<String, Object> statement);

	/**
	 * 通过模板删除数据
	 * 
	 * @Title del
	 * @param statement
	 *            void
	 * @date 2017-04-27 15:18:12 星期四
	 */
	void del(Map<String, Object> statement);

	/**
	 * 审核
	 * 
	 * @Title check
	 * @param statement
	 *            void
	 * @date 2017-05-23 16:41:48 星期二
	 */
	void check(Map<String, Object> statement);

	/**
	 * 反审核
	 * 
	 * @Title unCheck
	 * @param statement
	 * @return void
	 * @date 2017-05-23 16:46:10 星期二
	 */
	void unCheck(Map<String, Object> statement);

}
