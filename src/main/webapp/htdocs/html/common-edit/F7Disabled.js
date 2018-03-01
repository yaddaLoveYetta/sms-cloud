define('F7Disabled', function(require, module, exports) {
	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');
	var user = SMS.Login.get();
	var API = SMS.require('API');

	var disabled = function(keyName, itemId, fn) {
		if (user.type != 50801) {// 不是平台用户禁用F7选择框
			var ftype = $("#bd-" + keyName);
			var inpt = ftype.find("input");
			var sbtn = ftype.find('[data-role="btn"]');
			$(inpt).attr("disabled", "disabled");
			$(sbtn).attr("disabled", "disabled");
			$(ftype).undelegate('[data-role="btn"]', 'click');
			getF7Data(itemId, fn);
		}
	};

	var getF7Data = function(itemId, fn) {
		var api = new API('assistitem/getItemByID');
		api.post({
			itemID : itemId
		});
		api.on({
			'success' : function(data, json) {
				if ( typeof (fnEntry) == 'function') {
					fn && fn(data, json);
				}
			},

			'fail' : function(code, msg, json) {
				SMS.Tips.error(msg);
			},

			'error' : function() {
				SMS.Tips.error('网络错误，请稍候再试');
			}
		});
	};

	return {
		disabled : disabled
	};
}); 