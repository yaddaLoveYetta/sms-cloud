/**
 * 列表数据操作 模块
 *
 */
define('Bill/Operation', function(require, module, exports) {

	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');

	var API = SMS.require('API');

	function del(classId, list, fn) {
		var items = '';
		for (var item in list) {
			if (list[item]) {
				items += (',' + list[item].primaryValue);
			}
		}

		items = items.substr(1);

		var api = new API('template/delItem');
		api.get({

			'classId' : classId,
			'items' : items

		});

		api.on({
			'success' : function(data, json) {
				SMS.Tips.success('删除成功', 2000);
				fn();
			},

			'fail' : function(code, msg, json) {
				var s = $.String.format('{0} (错误码: {1})', msg, code);
				SMS.Tips.error(s);
			},

			'error' : function() {
				SMS.Tips.error('网络繁忙，请稍候再试');
			}
		});
	}

	function forbid(classId, list, operateType, fn) {
		var items = '';
		for (var item in list) {
			if (list[item]) {
				items += (',' + list[item].primaryValue);
			}
		}
		items = items.substr(1);

		if (items == '') {
			return;
		}

		var api = new API('template/forbid');
		api.get({

			'classId' : classId,
			'items' : items,
			'operateType' : operateType

		});

		api.on({
			'success' : function(data, json) {
				SMS.Tips.success(operateType === 1 ? '禁用成功' : '反禁用成功', 2000);
				fn();
			},

			'fail' : function(code, msg, json) {
				var s = $.String.format('{0} (错误码: {1})', msg, code);
				SMS.Tips.error(s);
			},

			'error' : function() {
				SMS.Tips.error('网络繁忙，请稍候再试');
			}
		});
	}

	return {
		del : del,
		forbid : forbid,
	};

});

