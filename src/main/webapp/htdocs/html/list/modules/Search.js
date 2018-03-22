/**
 * @Title: 搜索框模块
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/3/22 16:38
 */

define("Search", function (require, module, exports) {


    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");

    // 事件绑定标识
    var hasBind = false;

    var div = document.getElementById('filter-template');

    var samples = $.String.getTemplates(div.innerHTML, [
        {
            name: 'select-option',
            begin: '#--option.begin--#',
            end: '#--option.end--#',
            outer: '{option}'
        }]);

    function render(filterItems) {

        if (hasBind) {
            return;
        }
        // 根据模板填充过滤字段
        $('.field-name').each(function (index, selector) {

            selector.innerHTML = $.Array.keep(filterItems, function (item, index) {

                return $.String.format(samples['select-option'], {
                    'index': index,
                    'name': item.name,
                    'value': item.key,
                    'ctrlType': item.ctrlType
                })
            }).join("");

        });

        bindEvents();
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $('.search-more').on('click', function (e) {

            $(this).closest('table').find('.search-line:not(.search-line-first)').removeClass('hidden');

            $(this).closest('table').addClass('show-more');
        });

        $('.search-lite').on('click', function (e) {

            $(this).closest('table').find('.search-line:not(.search-line-first)').addClass('hidden');

            $(this).closest('table').removeClass('show-more');

        });

        hasBind = true;
    }

    return {
        render: render
    }
});