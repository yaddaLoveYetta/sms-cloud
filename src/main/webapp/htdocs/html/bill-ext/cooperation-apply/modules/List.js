/**
 * List 模块
 *
 */
define("List", function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var API = SMS.require('API');

    // 容器对象
    var div = document.getElementById("div-search-detail");
    var samples = require("Samples")(div);

    // 列表数据
    var list = {};
    // 事件绑定标识
    var hasBind = false;
    var emitter = MiniQuery.Event.create();
    // 选中的记录
    var selectedItem;

    // 记录选中的索引
    function load(config, fn) {

        SMS.Tips.loading({
            text: '数据加载中，请稍候...',
            delay: 200
        });

        var api = new API('template/getItems');

        var params = {

            'classId': 1012,
            'pageNo': 1,
            'pageSize': 10,
            'condition': [{
                'leftParenTheses': '(',
                'fieldKey': 'name',
                'logicOperator': 'ET',
                'value': config.keyword,
                'rightParenTheses': ')',
                'needConvert': false
            }],

            'sort': config.sort || ''
        };

        api.post(params);

        api.on({
            'success': function (data, json) {
                SMS.Tips.success("数据加载成功!", 1000);
                fn && fn(data, json);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });

    }

    function render(config) {

        if (!config || !config.keyword || config.keyword.trim() === '') {
            SMS.Tips.error('请输入完整的医院机构名称查找!');
        }
        // 清空已选择项
        selectedItem = null;

        load(config, function (data) {

            list = data.list;

            if (Number(data.count) === 0) {
                div.innerHTML = $.String.format(samples["no-item"], {});
            } else {
                // 填充列表
                div.innerHTML = $.String.format(samples["detail"], {

                    'no-item': "",

                    item: $.Array.keep(list, function (item, index) {
                        return $.String.format(samples["item"], {
                            index: index,
                            active: '',
                            logo: item.logo || '',
                            name: item.name,
                            address: item.address
                        });
                    }).join("")

                });
            }

            bindEvents();

        });
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $(div).delegate("div[data-index]", "click", function (event) {

            var active = $(this).hasClass("active");
            var index = $(this).data("index");

            $("div [data-check]").each(function () {
                var item = this;
                $(this).removeClass("active");
            });

            $(this).toggleClass("active");

            selectedItem = !active ? list[index] : null;

            event.stopPropagation();

        });

        hasBind = true;
    }

    function getSelectedItem() {
        return selectedItem;
    }

    function addCooperationApply(fn) {

        var api = new API("supplier/addCooperationApply");
        api.post({
            hospital: selectedItem.id
        });

        api.on({
            'success': function (data, json) {
                SMS.Tips.success("数据加载成功!", 1000);
                fn && fn(data, json);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    return {
        render: render,
        on: emitter.on.bind(emitter),
        getSelectedItem: getSelectedItem,
        addCooperationApply: addCooperationApply
    };
})
;