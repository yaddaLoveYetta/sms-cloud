define('DataSelector', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Samples = SMS.require('Samples');
    var emitter = MiniQuery.Event.create();
    var mapper = new $.Mapper();
    var guidKey = $.Mapper.getGuidKey();

    var defaults = {
        multiSelect: false,
        width: 1100,
        height: 600,
        pageSize: 10,
        targetList: {
            1: './html/list/index.html',
            2: './html/support-data/index.html',
        }
    };

    var samples = Samples.get('DataSelector', [{
        name: 'selector',
        begin: '#--selector.begin--#',
        end: '#--selector.end--#'
    }]);

    //构造函数
    function DataSelector(config) {

        this[guidKey] = 'DataSelector-' + $.String.random();
        var meta = {
            container: config.container, // 容器
            hasData: false, // 是否有数据
            fieldKey: config.fieldKey, // 控件绑定的字段模板key
            typeId: config.typeId,
            hasBreadcrumbs: config.hasBreadcrumbs, // 导航显示
            targetType: config.targetType, // 列表页面类别
            title: config.title,            // dialog表体
            conditions: config.conditions || {}, // 发送到页面的条件
            classID: config.classID || '', // targetType连接附加classId条件
            destClassId: config.destClassId || '', // 目标单据classId
            checkbox: config.checkbox,
            conditionF7Names: config.conditionF7Names || [], //新增查询条件集合 [{SelectorName:"FCompany",FillterKey: "FCompany", ValueRule: { 50801: 50701, 50802: 50702 } }]
            data: [{
                'ID': '',
                'name': '',
                'number': ''
            }],
            defaults: (function () {
                if (!!config.defaults) {

                    var cDefaults = $.Object.extend({}, defaults, config.defaults);
                    return cDefaults;

                } else {
                    return defaults;
                }
            })(),
        };

        mapper.set(this, meta);

        meta.bindEvents = function (selector) {

            var meta = mapper.get(selector);

            $(meta.container).delegate('[data-role="btn"]', 'click', function (e) {

                var url = $.Url.setQueryString(defaults.targetList[meta.targetType], 'classId', meta.classID);

                //新增关联查询条件逻辑 --------------begin--------------
                var dataSelectors = DataSelector.DataSelectors;

                //获取 对象静态变量
                for (var i = 0; i < meta.conditionF7Names.length; i++) {

                    var conditionData = meta.conditionF7Names[i];

                    var type = conditionData.type || "";  // 目标字段类别

                    var target; // 目标元素
                    var filterKey; //  目标数据过滤字段key
                    var filterValue;// 目标数据过滤字段条件值
                    var valueRule; //    值转换规则


                    target = conditionData.target || "";
                    filterKey = conditionData.filterKey || "";
                    filterValue = conditionData.filterValue || "";
                    valueRule = conditionData.valueRule || "";

                    // 最终过滤条件
                    var value;

                    if (type == "selector" && $.trim(target) !== "" && $.trim(filterKey) !== "") {
                        // 条件取自页面其他F7控件的值

                        value = dataSelectors[target].getData() && dataSelectors[target].getData()[0].ID || '';

                        if ($.trim(valueRule) !== "" && $.trim(target) !== "" && $.trim(filterKey) !== "") {
                            // 值转换
                            value = valueRule[value] || '';
                        }

                        if (value === '') {
                            delete meta.conditions[target];//清除没必要的或者已经清空的查询条件
                            continue;
                            //不是必须关联的 如果所关联的为空则跳过该查询条件
                        }

                        meta.conditions[conditionData.target] = {
                            'andOr': 'and',
                            'leftParenTheses': '(',
                            'fieldKey': filterKey,
                            'logicOperator': '=',
                            'value': value,
                            'rightParenTheses': ')',
                            needConvert: false
                        };
                    } else if (type == "fixedValue" && $.trim(filterKey) !== "") {
                        // 固定值条件

                        value = filterValue || $(target).val();

                        if (value === '') {
                            delete meta.conditions[target];//清除没必要的或者已经清空的查询条件
                            continue;
                            //不是必须关联的 如果所关联的为空则跳过该查询条件
                        }

                        meta.conditions[conditionData.target] = {
                            'andOr': 'and',
                            'leftParenTheses': '(',
                            'fieldKey': filterKey,
                            'logicOperator': '=',
                            'value': value,
                            'rightParenTheses': ')',
                            needConvert: false
                        };

                    }
                }
                //新增关联查询逻辑 --------------end--------------

                SMS.use('Dialog', function (Dialog) {

                    var dialog = new Dialog({
                        title: meta.title,
                        url: url,
                        width: meta.defaults.width,
                        height: meta.defaults.height,
                        data: {
                            typeId: meta.typeId || '',
                            multiSelect: meta.defaults.multiSelect,
                            pageSize: meta.defaults.pageSize,
                            hasBreadcrumbs: meta.hasBreadcrumbs,
                            conditions: meta.conditions,
                            checkbox: meta.checkbox
                        },
                        button: [
                            {
                                value: '取消',
                                className: 'sms-cancel-btn',
                            },
                            {
                                value: '确定',
                                className: 'sms-submit-btn',
                                callback: function () {
                                    this.isSubmit = true;
                                },
                            }
                        ],
                        // ok : function() {
                        // this.isSubmit = true;
                        // },
                        // cancel : function() {
                        // 
                        // }
                    });

                    //默认关闭行为为不提交
                    dialog.isSubmit = false;

                    dialog.showModal();

                    dialog.on({
                        remove: function () {

                            var data = dialog.getData();
                            var label = $(meta.container).find('[data-role="label"]')[0];
                            //console.log(data)
                            //if (dialog.isSubmit && data[0].hasOwnProperty("ID")) {
                            if (dialog.isSubmit && data[0] && typeof data[0].ID != "undefined") {
                                if (meta.data[0].ID != data[0].ID) {
                                    /**
                                     * 抛出个值改变事件
                                     *
                                     * classId :业务对象
                                     * key : 当前改变的控件key
                                     * data:改变后的控件数据
                                     */
                                    emitter.fire('change', [meta.destClassId, meta.fieldKey, data]);
                                }
                                meta.data = dialog.getData();
                                label.title = label.value = meta.data[0].number;
                                //抛出个确认事件
                                emitter.fire('done', [meta.destClassId + '-' + meta.container.getAttribute("id") + '.DialogOk', meta.data]);
                                label.focus();
                                meta.hasData = true;
                            }
                            /*else {
                             meta.hasData = false;
                             }*/
                        }
                    });
                });

            });

            $(meta.container).delegate('[data-role="label"]', {

                'focus': function () {
                    var self = this;
                    if (meta.hasData) {
                        if (meta.data[0].number) {
                            self.value = meta.data[0].number;
                        }
                    }
                },
                'blur': function () {
                    var self = this;
                    if (meta.hasData) {
                        if (meta.data[0].name) {
                            self.value = meta.data[0].name;
                        }
                    }
                }
            });


            var f7DefaultData = [{
                ID: "",
                number: "",
                name: ""
            }];
            //文本清空设置空数据
            $(meta.container).on('change input propertychange', function () {
                if ($(this).find('input[data-role="label"]').val() == "") {
                    meta.data = f7DefaultData;
                    emitter.fire(meta.container.getAttribute("id") + '.DialogEmpty', [f7DefaultData]);
                }
            });

            //$(meta.container).find('input[data-role="label"]').prop("placeholder", "请选择" + meta.title);
        };

    }

    //静态变量 F7 选择框集合
    DataSelector.DataSelectors = {};

    //实例方法
    DataSelector.prototype = {
        constructor: DataSelector,
        getData: function () {

            var meta = mapper.get(this);
            return meta.data;

        },
        render: function () {

            var meta = mapper.get(this);

            var html = $.String.format(samples.selector, {
                name: meta.title,
                key: meta.fieldKey
            });

            //$(meta.container).html(samples.selector);
            $(meta.container).html(html);
            meta.bindEvents(this);

        },
        setData: function (data) {

            var meta = mapper.get(this);
            if (meta.container) {
                var label = $(meta.container).find('[data-role="label"]')[0];
                meta.data = data;
                label.title = label.value = meta.data[0].name;
                meta.hasData = true;
            }

        },
        destroy: function () {

            var meta = mapper.get(this);

            $(meta.container).undelegate();
            meta.data = [];
        },
        lock: function () {

            var meta = mapper.get(this);

            //$(meta.container).undelegate('[data-role="btn"]', 'click');

            $(meta.container).find('input').each(function () {
                $(this).attr("disabled", "disabled");
            });
            $(meta.container).find('[data-role="btn"]').each(function () {
                $(this).attr("data-role", "btnDisalbe");
                $(this).attr("disabled", "disabled");
            });

        },
        unlock: function () {

            var meta = mapper.get(this);

            $(meta.container).find('input').each(function () {
                $(this).removeAttr("disabled");
            });
            $(meta.container).find('[data-role="btnDisalbe"]').each(function () {
                $(this).attr("data-role", "btn");
                $(this).removeAttr("disabled");
            });
        },
        clearData: function () {
            //新增文本清空设置空数据
            var f7DefaultData = [{
                ID: "",
                number: "",
                name: ""
            }];
            this.setData(f7DefaultData);
        }
    };

    //静态方法
    return $.Object.extend(DataSelector, {
        create: function (config) {
            // if (!!config.defaults) {
            // $.Object.extend(defaults, config.defaults);
            // defaults = $.Object.extend(defaults, config.defaults);
            // }
            var selector = new DataSelector(config);
            selector.render();
            return selector;

        },
        on: emitter.on.bind(emitter)
    });
});
