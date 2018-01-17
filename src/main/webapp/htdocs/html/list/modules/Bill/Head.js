/**
 * 单据头模块
 * Created by yadda on 2017/5/13.
 */

define('Bill/Head', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');
        var SMS = require('SMS');
        var emitter = MiniQuery.Event.create();

        var API = SMS.require('API');
        var DataSelector = require('DataSelector');

        var div = document.getElementById("dd-head");
        var samples = require("/Samples")(div);
        var user = SMS.Login.get();

        var template = {};
        var visibleTemplate = [];

        var selectors = {};
        var fnSelectors;
        var id; // 单据内码-判断是新增还是修改

        function render(metaData, data, itemId) {

            id = itemId;

            template = metaData.template;
            visibleTemplate = metaData.visibleTemplate;

            initPage(metaData.visibleTemplate); // 初始化页面DOM

            initControls(metaData.visibleTemplate);// 特殊控件初始化

            lockControls(metaData.template);// 控件锁定性(是否可编辑)处理

            fill(metaData.template, data);
        }

        /**
         * 根据模板构建页面控件
         * @param metaData 模板数据
         */
        function initPage(metaData) {

            var fields = metaData['0'];

            fields = $.Array.group(fields, 4); // 分组，一行填充4个字段

            div.innerHTML = $.Array.keep(fields, function (group, no) {

                var text = $.String.format(samples["rows"], {

                    items: $.Array.keep(group, function (field, no) {

                        var sample = "";
                        var key = field.key;

                        if (!field.display) {
                            return '';
                        }

                        var domType = field.ctrlType;

                        if (!!!domType) {
                            // 默认文本
                            domType = 10;
                        }

                        /*
                         1,3,5,6,7,8,9,10,12,98,99
                         */
                        switch (domType) {
                            case 1: // 小数
                            case 8: // 手机号码
                            case 9://座机电话
                            case 10: // 普通文本
                                sample = samples["row.text"];
                                break;
                            case 11://多行文本
                                sample = samples["row.textarea"];
                                break;
                            case 12:
                                sample = samples["row.datatime"];
                                break;
                            case 3: // checkbox
                                sample = samples["row.checkbox"];
                                break;
                            case 6:
                                sample = samples["row.f7"];
                                break;
                            case 99:
                                sample = samples["row.password"];
                                break;
                            default:
                                sample = samples["row.text"];
                        }

                        return $.String.format(sample, {
                            mustInput: field.mustInput ? $.String.format(samples["row.mustInput"], {}) : "",
                            name: field.name,
                            key: field.key,
                            // disabled: item.lookUpType && item.lookUpType == 3 ? "disabled" : "", // 辅助属性不可编辑
                        });

                    }).join(""),

                });

                console.log(text);
                return text;

            }).join("");


        }

        // 初始化特殊控件
        function initControls(metaData) {

            var fields = metaData[0];

            for (var item in fields) {

                var field = fields[item];

                if (field.ctrlType === 6) {
                    // 选择框控件 引用基础资料
                    var config = {
                        targetType: 1, //跳转方案
                        classID: field.lookUpClassID,
                        destClassId: field.classId,
                        hasBreadcrumbs: true,
                        fieldKey: field.key,
                        container: document.getElementById(field.key),
                        title: field.name,
                        defaults: {
                            pageSize: 8
                        }
                    };

                    var pConfig = {};

                    if (!!fnSelectors) {
                        pConfig = fnSelectors && fnSelectors(field.classId, field.key);// 个性化配置
                    }

                    config = $.Object.extend({}, config, pConfig);

                    selectors[field.key] = DataSelector.create(config);

                }

                if (field.ctrlType === 12) {
                    // 日期控件
                    generateDateTimePicker(field.key); // SMS.use 异步作业，field.key容易被覆盖
                }
            }

            //设置 静态变量 用于联动操作
            DataSelector.DataSelectors = selectors;

            //改变事件捕获
            DataSelector.on({
                'change': function (classId, key, data) {
                    emitter.fire('dialogChange', [classId, key, data, selectors, metaData]);
                },
                'done': function (key, data) {
                    emitter.fire(key, [data, selectors, metaData]);
                },
            });

        }

        function generateDateTimePicker(key) {
            // 异步执行，不可放入for循环，否则key存在被覆盖的风险
            SMS.use('DateTimePicker', function (DateTimePicker) {

                new DateTimePicker(document.getElementById(key), {
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    todayHighlight: true,
                    timepicker: false,
                    startView: 'month',
                    minView: 2,
                });

            });
        }

        // 字段锁定性处理
        function lockControls(metaData) {

            var isUpdate = !!id;//是否是修改

            if (!metaData || !metaData['formFields'] || !metaData['formFields'][0]) {
                SMS.Tips.error('元数据错误，请联系管理员');
                return;
            }
            var fields = metaData['formFields'][0];

            for (var item in fields) {

                var field = fields[item];
                var keyName = field['key'];
                var lookUpClassId = field['lookUpClassID'];
                var element = $('#' + keyName);

                if (!element) {
                    continue;
                }

                var lockMaskDisplay = 0;

                if (isUpdate) {
                    //lockMaskDisplay  字段显示权限-后端lock定义 2 编辑时平台用户锁定，8编辑时候供应商用户锁定
                    if (user.type === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                        // 平台用户
                        lockMaskDisplay = 2;
                    } else if (user.type === 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                        //供应商用户
                        lockMaskDisplay = 8;
                    }
                } else {
                    //lockMaskDisplay  字段显示权限-后端lock定义 1 编辑时平台用户锁定，4编辑时候供应商用户锁定
                    if (user.type === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                        // 平台用户
                        lockMaskDisplay = 1;
                    } else if (user.type === 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                        //物业用户
                        lockMaskDisplay = 4;
                    }
                }
                var lockMask = field['lock'] || 0;
                //是否锁定
                var isLock = !!(lockMask & lockMaskDisplay);


                //锁定处理
                if (isLock) {

                    if (field['ctrlType'] == 6) {
                        //F7选择框处理
                        selectors[keyName].lock();
                    } else {
                        element.prop("disabled", "disabled");
                    }
                } else {
                    element.prop("disabled", "");
                }
            }
        }

        // 填充页面数据
        function fill(metaData, data) {

            if (!metaData || !metaData['formFields']) {
                SMS.Tips.error('元数据错误，请联系管理员');
                return;
            }

            if (!data) {
                SMS.Tips.error('数据错误，请联系管理员');
                return;
            }

            var fields = metaData['formFields'][0];
            // var formClass = metaData['formClass'];
            // var data = data.items;
            var data = data;
            for (var item in data) {

                var keyName = item;
                var element = document.getElementById(keyName);
                var value = data[keyName];

                // 保存内码，用于判断保存时，是新增还是修改
                // if (keyName == formClass['primaryKey']) {
                // itemID = value;
                // continue;
                // }

                if (!element) {
                    // todo: 按数据未找到控件，跳过此字段赋值
                    continue;
                }
                if (fields[keyName]['ctrlType'] == 1) { // 数字
                    var ne = $(keyName);
                    //初始化控件
                    try {
                        ne.autoNumeric('set', (value || 0));
                        ne.title = value || 0;
                    } catch (e) {
                        ne.val((value || 0));
                    }

                    continue;
                }
                if (fields[keyName]['ctrlType'] == 3) { // 多选按钮
                    element.checked = value;
                    continue;
                }

                if (fields[keyName]['ctrlType'] == 5) { // 下拉框
                    element.title = element.value = value == null ? '' : Number(value);
                    continue;
                }
                if (fields[keyName]['ctrlType'] == 6) { // F7选择框

                    var selectorData = [{
                        ID: value,
                        number: data[keyName + '_NmbName'],
                        name: data[keyName + '_DspName']
                    }];

                    selectors[keyName].setData(selectorData);
                    continue;
                }

                if (fields[keyName]['ctrlType'] == 99) {
                    // 用户多一个密码字段特殊，蛋疼的处理-写死它，用来判断是否修改-加密
                    element.value = 'XXXXXXXX';
                    continue;
                }

                element.title = element.value = value;
            }

            emitter.fire('afterFill', [metaData, data]);

            SMS.Tips.success('数据加载成功', 2000);
        }

        function getHtml(type, data) {
            /*
             * if ( typeof data == 'boolean') { data = data ? '是' : '否'; }
             */
            if (data == null) {
                data = "";
            }
            if (type == 4) {
                // boolean 类型元数据
                data = data ? "是" : "否";
            }
            if (type == 3) {
                // 日期时间类型
                console.log(data instanceof Date);
            }
            if (type == 98) {
                // 处理男/女显示
                data = data ? "女" : "男";
            }
            return data;
        }

        function getData(isUpdate) {

            if (!template || !template['formFields']) {
                alert('元数据错误，请联系管理员');
                return;
            }

            var fields = template['formFields'][0];
            var validate = true; // 是否检验成功
            var successData = {};
            var errorData = {};

            //合并错误提示信息

            for (var item in fields) {

                var field = fields[item];
                var keyName = field['key'];

                var element = document.getElementById(keyName);

                if (!element) {
                    // todo: 按元数据未找到控件，跳过此字段
                    continue;
                }

                if (field['ctrlType'] == 1) { // 数字
                    var ne = $(keyName);
                    var domValue = ne.val();
                    var widgetData = ne.autoNumeric && ne.autoNumeric("get") || domValue;
                    if (!domValue) {
                        if (isMustFiled(isUpdate, field)) {
                            var msg = field['name'] + '为必填项';
                            errorData[keyName] = msg;
                            validate = false;
                        } else {
                            successData[keyName] = widgetData;
                        }
                    } else {
                        successData[keyName] = widgetData;

                    }
                    continue;
                }
                if (field['ctrlType'] == 3) { // 多选按钮，无需校验
                    successData[keyName] = element.checked;
                    continue;
                }
                if (field['ctrlType'] == 6) { // F7选择框
                    var selector = selectors[keyName];
                    var selectorID = selector.getData()[0]['ID'];

                    if (!selectorID) {
                        if (isMustFiled(isUpdate, field)) {
                            var msg = field['name'] + '为必填项';
                            errorData[keyName] = msg;
                            validate = false;
                        } else {
                            successData[keyName] = '';
                        }
                    } else {
                        successData[keyName] = selectorID;
                    }
                    continue;
                }

                if (fields[keyName]['ctrlType'] == 99) {
                    // 合作商家基础资料多一个密码字段特殊，蛋疼的处理-写死它，用来判断是否修改-加密
                    if (!element.value && isMustFiled(isUpdate, field)) {
                        var msg = field['name'] + '为必填项';
                        errorData[keyName] = msg;
                        validate = false;
                    } else {
                        if (element.value != 'XXXXXXXX') {
                            successData[keyName] = MD5.encrypt(element.value);
                        }
                    }
                    continue;
                }

                if (!element.value) {
                    if (isMustFiled(isUpdate, field)) {
                        var msg = field['name'] + '为必填项';
                        errorData[keyName] = msg;
                        validate = false;
                    } else {
                        if (fields[keyName]['dataType'] == 2 || fields[keyName]['ctrlType'] == 7) { //文本类型、级联选择器 给空字符串
                            successData[keyName] = element.value;
                        }
                    }
                } else {
                    successData[keyName] = element.value;
                }
            }

            return {
                'successData': successData,
                'errorData': errorData,
            }

        }

        //------是否必填校验逻辑(因多地方使用所以抽出来) BEGIN-----//
        function isMustFiled(isUpdate, field) {
            var mustInput = field['mustInput'] || 0;
            var mustInputMask = 0; //是否必填掩码
            if (isUpdate) {
                //FMustInput  字段显示权限-后端mustInput定义 4 编辑时平台用户必填，8编辑时候物业用户必填
                if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    // 平台用户
                    mustInputMask = 2;
                } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                    //物业用户
                    mustInputMask = 8;
                }
            } else {
                //FMustInput  字段显示权限-后端mustInput定义 4 编辑时平台用户必填，8编辑时候物业用户必填
                if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    // 平台用户
                    mustInputMask = 1;
                } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                    //物业用户
                    mustInputMask = 4;
                }
            }
            return !!(mustInputMask & mustInput); //是否必填
        }


        function showValidInfo(successData, errorData) {

            for (var item in successData) {
                // 去掉错误提示
                var msgElement = document.getElementById(item + '-msg');
                if ($(msgElement).hasClass('show')) {
                    $(msgElement).toggleClass('show');
                }
                $(msgElement).html('');
            }
            if (errorData) {
                // 显示错误提示
                for (var item in errorData) {

                    var msgElement = document.getElementById(item + '-msg');
                    if (!$(msgElement).hasClass('show')) {
                        $(msgElement).toggleClass('show');
                    }
                    $(msgElement).html(errorData[item]);
                }
            }
        }

        return {
            render: render,
            getData: getData,
            showValidInfo: showValidInfo,
        };
    }
);
