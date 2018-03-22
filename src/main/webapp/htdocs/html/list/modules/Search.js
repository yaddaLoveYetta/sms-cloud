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

    /* ctrlType
    1	数字
    2	数字带小数
    3	选择框
    5	下拉列表
    6	F7选择框
    7	级联选择器
    8	手机号码
    9	座机电话
    10	普通文本
    11	多行文本
    12	日期时间
    13	男：女
    14	密码控件
    15	是：否
    16	单价/金额(两位小数)

         EQUAL("ET", "=", "等于"), NOT_EQUAL("NET", "!=", "不等于"), LESS_THAN("LT", "<", "小于"), LESS_OR_EQUAL("LET", "<=", "小于或等于"),
        GREATER("GT", ">", "大于"), GREATER_OR_EQUAL("GET", ">=", "大于或等于"),
        LIKE("BLT", "LIKE", "LIKE模糊匹配"), IN("IN", "IN", "包含于"),
        NOT_SUPPORT("NOT_SUPPORT", "NOT_SUPPORT", "不支持的比较符号");

 */

    var _ctrlTypeOperator = {
        //-1数字-2	数字带小数-16	单价/金额(两位小数)
        1: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        2: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        16: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        //3	选择框
        3: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}],
        //下拉框
        5: [ {name: "等于", value: "ET"}, {name: "不等于", value: "NET"},{name: "包含", value: "like"}],
        //F7选择框
        6: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}],
        //级联选择器
        7: [ {name: "等于", value: "ET"},{name: "包含", value: "BLT"}],
        //手机号码
        8: [{name: "等于", value: "="}, {name: "不等于", value: "NET"},{name: "包含", value: "BLT"}],
        //座机电话
        9: [ {name: "等于", value: "="}, {name: "不等于", value: "NET"},{name: "包含", value: "BLT"}],
        //普通文本
        10: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"}],
        //多行文本
        11: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"}],
        // 日期
        12: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}, {name: "小于", value: "<"},
            {name: "小于等于", value: "<="}, {name: "大于", value: ">"}, {name: "大于等于", value: ">="}],
        //男：女
        13: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}],
        //密码控件
        14: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}],
        //是：否
        15: [{name: "等于", value: "="}, {name: "不等于", value: "NET"}]
    };

    function render(filterItems) {

        if (hasBind) {
            return;
        }

        bindEvents();

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

            $(selector).trigger('change');

        });
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
        // 比较字段变化事件
        $('.field-name').on("change", function (ev) {

            var $selected = $(this).find("option:selected");
            var value = $selected.val();
            var ctrlType = $selected.data('ctrltype');

            if (!ctrlType && !value) {
                return;
            }

            var compareKeys = _ctrlTypeOperator[ctrlType];

            var html = $.Array.keep(compareKeys, function (item, index) {

                return $.String.format(samples['select-option'], {
                    'index': index,
                    'name': item.name,
                    'value': item.value,
                    'ctrlType': ctrlType
                })
            }).join("");
            // 比较操作符呈现
            $(this).parent('td').parent("tr").find("select[name='operator']").html(html)
            ev.stopPropagation();
        });


        hasBind = true;
    }

    return {
        render: render
    }
});