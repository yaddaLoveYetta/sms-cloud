/**
 * 查找模块
 *
 */
define('Search', function(require, module, exports) {

	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');
	var emitter = MiniQuery.Event.create();

	var btnSearch = document.getElementsByClassName('btnSearch')[0];

	var hasBind = false;

	function init() {
		bindEvents();
	}

	function bindEvents() {

		if (hasBind) {
			return;
		}

		$(btnSearch).on('click', function() {

			var params = {
				name : $('.txtUserName').val()
			};

			emitter.fire('refresh', [params]);

		});

		hasBind = true;
	}

	function render(config) {

		config = $.Object.extend({
			current : 1, //当前激活的页码，默认为 1
			hideIfLessThen: 2,  //总页数小于该值时，分页器会隐藏。 如果不指定，则一直显示。
			error : function(msg) {
				SMS.Tips.error(msg, 1000);
				this.focus();
			}
		}, config, {

			container : '#div-pager', //分页控件的容器

		});

		SMS.Pager.create(config);
	}

	return {
		init : init,
		render : render,
		on : emitter.on.bind(emitter),
	};

});
