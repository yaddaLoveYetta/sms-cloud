/**
 * 按钮模块
 * 
 */
define('ButtonList', function(require, module, exports) {

	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');

	var ButtonList = SMS.require('ButtonList');

	var emitter = MiniQuery.Event.create();

	var config = {
		container : '#div-button-list',
		fields : {
			text : 'text',
			child : 'items',
			callback : 'click',
			route : 'name',
		},

		autoClose : true,

		items : [ {
			text : '新增',
			name : 'optNew',
			icon:'../../css/main/img/add.png',
			click : function(item, index) {
				emitter.fire('optNew');
			}
		}, {
			text : '保存',
			name : 'optSave',
			icon:'../../css/main/img/save.png',

		}, {
			text : '刷新',
			name : 'optRefresh',
            icon:'../../css/main/img/refresh.png',

		} ]
	};

	function create(itemId) {

		if (itemId) {
			// 编辑
			config = $.Object.extend({}, config, {

				items : [ {
					text : '保存',
					name : 'optSave',
                    icon:'../../css/main/img/save.png',
				}, {

					text : '刷新',
					name : 'optRefresh',
                    icon:'../../css/main/img/refresh.png',
				} ],
			});
		} else {
			// 新增
			config = $.Object.extend({}, config, {

				items : [ {
					text : '新增',
					name : 'optNew',
                    icon:'../../css/main/img/add.png',
				}, {
					text : '保存',
					name : 'optSave',
                    icon:'../../css/main/img/save.png',
				} ],
			});
		}

		var bl = new ButtonList(config);

		// 总事件，最后触发
		bl.on('click', function(item, index) {
			console.dir(item);
		});

		return bl;

	}
	return {
		create : create,
		on : emitter.on.bind(emitter)
	};

});
