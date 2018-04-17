/**
 * 表头数据模块
 *
 */
define('Edit', function(require, module, exports) {

	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');

	var FormEdit = require('FormEdit');

	var itemId = '';

	function render(formClassID, itemID, selectors) {

		itemId = itemID;

		FormEdit.render(formClassID, itemId, selectors);
	}

	function clear() {
		itemId = '';
		FormEdit.clear();
	}

	function save() {
		FormEdit.save(itemId, showValidInfo, saveSuccess);
	}

	function forbid(classID, operateType) {
		if (!itemId) {
			return;
		}
		FormEdit.forbid(classID, itemId, operateType);
	}

	function refresh(classID, selectors) {
		if (!itemId) {
			return;
		}
		FormEdit.render(classID, itemId, selectors);
	}

	function showValidInfo(successData, errorData) {
		for (var item in successData) {

			var msgElement = document.getElementById('bd-' + item + '-msg');
			if ($(msgElement).hasClass('show')) {
				$(msgElement).toggleClass('show');
			}
			$(msgElement).html('');
		}
		if (errorData) {
			for (var item in errorData) {

				var msgElement = document.getElementById('bd-' + item + '-msg');
				if (!$(msgElement).hasClass('show')) {
					$(msgElement).toggleClass('show');
				}
				$(msgElement).html(errorData[item]);
			}
		}
	}

	function saveSuccess(data) {
		if (itemId) {
			SMS.Tips.success('数据修改成功', 2000);
		} else {
			itemId = data['itemID'];
			SMS.Tips.success('数据新增成功', 2000);
		}
	}

	return {
		render : render,
		clear : clear,
		save : save,
		forbid : forbid,
		refresh : refresh,
	};

});

