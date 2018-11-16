/**
 * 下拉选择器
 */
define('SimpleSelector', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var Samples = SMS.require('Samples');
    var emitter = MiniQuery.Event.create();
    var mapper = new $.Mapper();
    var guidKey = $.Mapper.getGuidKey();

    var defaults = {
        multiSelect: false,
        noneSelectedText: '',
        // 查询为空提示信息
        noneResultsText: '',
        liveSearch: true,
        //设置select高度，同时显示5个值
        size: 5,
        // 后台获取数据分页大小
        pageSize: 10,
        dataFieldKey: {
            // selector option value 取值key
            'id': 'id',
            // selector option text 取值key
            'name': 'name'
        },

    };

    var samples = Samples.get('SimpleSelector', [
        {
            name: 'selector',
            begin: '#--selector.begin--#',
            end: '#--selector.end--#'
        },
        {
            name: 'option',
            begin: '#--selector.option.begin--#',
            end: '#--selector.option.end--#',
            outer: '{options}'
        }
    ]);

    //构造函数
    function SimpleSelector(config) {

        this[guidKey] = 'SimpleSelector-' + $.String.random();

        var meta = {
            // 容器
            container: config.container,
            // 是否有数据
            hasData: false,
            // 控件绑定的字段模板key
            fieldKey: config.fieldKey,
            // 搜索框默认显示文本
            title: config.title,
            // 后台数据查询条件
            conditions: config.conditions || [],
            // 后台查询的业务类别
            classId: config.classId || '',
            //  selector option value，test 取值默认key
            dataFieldKey: config.dataFieldKey || {
                'id': 'id', 'name': 'name'
            },
            data: {
                'id': -1,
                'name': '',
                'info': ''
            },
            optionData: [],
            defaults: (function () {
                if (!!config.defaults) {

                    return $.Object.extend({}, defaults, config.defaults);

                } else {
                    return defaults;
                }
            })()
        };

        mapper.set(this, meta);

        meta.bindEvents = function (selector) {

            var meta = mapper.get(selector);

            $(meta.container).delegate('select', 'click', function (e) {

                loadData({
                    'classId': meta.classId,
                    'pageNo': meta.defaults.pageNo,
                    'pageSize': meta.defaults.pageSize,
                    'condition': config.conditions.length > 0 ? config.conditions : ''
                }, function (data) {

                    meta.optionData = data;
                    var html = $.String.format(samples.selector, {
                        key: meta.fieldKey,
                        options: $.Array.keep(data, function (item, no) {
                            // option
                            return $.String.format(samples["option"], {
                                index: no,
                                value: data[meta.dataFieldKey.id],
                                text: data[meta.dataFieldKey.name]
                            });
                        }).join("")
                    });

                    $(meta.container).html(html);
                    //加载select框选择器
                    $(meta.fieldKey).selectpicker('refresh');
                });

            });

            $(meta.container).delegate('select', 'change', function (e) {

                // 选择项数据
                var index = $(this).find('option:selected').data('index');

                meta.data = {
                    id: $(this).val(),
                    name: $(this).find('option:selected').text(),
                    info: meta.optionData[index]
                }

            });
        };

        function loadData(config, fn) {

            var api = new API('template/getItems');

            var params = {
                'classId': config.classId,
                'pageNo': config.pageNo,
                'pageSize': config.pageSize,
                'condition': config.conditions.length > 0 ? config.conditions : '',
                'sort': config.sort || ''
            };

            api.post(params);

            api.on({
                'success': function (data, json) {
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

    }

    //静态变量 F7 选择框集合
    SimpleSelector.SimpleSelectors = {};

    SimpleSelector.prototype = {
        constructor: SimpleSelector,
        getData: function () {
            var meta = mapper.get(this);
            return meta.data;
        },
        render: function () {

            var meta = mapper.get(this);

            var html = $.String.format(samples.selector, {
                key: meta.fieldKey,
                options: ''
            });

            $(meta.container).html(html);
            meta.bindEvents(this);

        }
    };

    //静态方法
    return $.Object.extend(SimpleSelector, {
        create: function (config) {

            var selector = new SimpleSelector(config);
            selector.render();
            return selector;

        },
        on: emitter.on.bind(emitter)
    });
});