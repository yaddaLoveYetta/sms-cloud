/**
 * 侧边弹出的菜单列表模块
 */
define('Menus', function(require, module, exports) {

	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('SMS');

	var ul = document.getElementById('ul-sidebar-menus');
	var div = ul.parentNode;

	var emitter = MiniQuery.Event.create();

	var currentIndex = -1;
	var currentItem = null; // 当前激活的菜单项
	var list = [];
	var isVisible = false;
	var hasBind = false;
	var hasFixed = false;

	var tid = null;

	var tabs = null; // 互斥高亮控件的实例

	function show() {
		if (isVisible) {
			return;
		}

		isVisible = true;

		$(div).fadeIn('fast');
		// $(div).fadeIn(200);

		var index = currentItem ? $.Array.findIndex(list,
				function(item, index) {
					return currentItem.id == item.id;
				}) : -1;

		tabs.active(index);
	}

	// 隐藏。
	function hide() {
		isVisible = false;
		$(div).hide();
	}

	// 延迟隐藏，从而有机会取消隐藏
	function hideAfter(delay) {
		tid = setTimeout(hide, delay);
	}

	// 取消隐藏
	function cancelHide() {
		clearTimeout(tid);
	}

	// 激活指定的项。 由外部调用
	function active(item) {
		currentItem = item;
	}

	function reset() {

		hide();

		list = [];
		currentIndex = -1;

		if ($(div).hasClass('toggle-menus-more')) {
			div.className = 'menus-more toggle-menus-more';
		} else {
			div.className = 'menus-more';
		}

		tabs = SMS.Tabs.create({
			container : ul,
			selector : '>li',
			current : null,
			event : 'click',
			indexKey : 'data-index',
			activedClass : 'actived'
		});

	}

	function bindEvents() {

		if (hasBind) {
			return;
		}

		$(div).hover(function() {

			emitter.fire('mouseover');

		}, function() {

			emitter.fire('mouseout');

		}).delegate('li[data-index]', 'click', function() {

			var li = this;
			var index = +li.getAttribute('data-index');
			var item = list[index]; // 取得数据
			currentItem = item;
			emitter.fire('item.click', [ item ]);

		});

		hasBind = true;

	}

	/**
	 * 固定位置显示。
	 */
	function fixed(isFixed) {

		hasFixed = isFixed;

		if (currentIndex < 0) { // 尚未填充
			return;
		}

		var index = currentIndex + 1;
		$(div).toggleClass('menus-' + index, !isFixed);

		// 重算菜单高度
		$(div).css('height', '');
		if (($(document.body).height() - 85 - $(div).height()) < 0) {
			$(div).css('height', $(document.body).height() - 85);
		}
		// 重算菜单位置
		$(div).css('top', '');
		var t = $(document.body).height() - 85 - $(div).offset().top
				- $(div).outerHeight(true);// 顶部fixed预留85px
		if (t < 0) {
			$(div).css('top',
					$(document.body).height() - $(div).outerHeight(true));
		}

	}

	/**
	 * 填充呈现
	 * 
	 * @param {Object}
	 *            config，配置参数，其中：
	 * @param {number}
	 *            config.index 对齐的位置索引值。
	 * @param {boolean}
	 *            config.reversed 是否反向。
	 * @param {Array}
	 *            config.data 填充的数据。
	 */
	function render(config) {

		var index = config.index;
		if (index == currentIndex) {
			show();
			return;
		}

		reset();

		currentIndex = index;
		list = config.data;

		SMS.Template.fill(ul, list, function(item, index) {
			return {
				index : index,
				name : item.name,
				icon : item.icon,
			};
		});

		show();
		fixed(hasFixed);
		// $(div).addClass('items-' + list.length);
		bindEvents();
	}

	// return {
	// active: active,
	// render: render,
	// hide: hide,
	// hideAfter: hideAfter,
	// cancelHide: cancelHide,
	// fixed: fixed,
	// on: emitter.on.bind(emitter)
	// };
	module.exports = {
		active : active,
		render : render,
		hide : hide,
		hideAfter : hideAfter,
		cancelHide : cancelHide,
		fixed : fixed,
		on : emitter.on.bind(emitter)
	};

});
