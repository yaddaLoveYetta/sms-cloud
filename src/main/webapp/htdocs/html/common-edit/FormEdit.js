﻿/**
 * 单据界面操作
 */
define('FormEdit', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var MD5 = SMS.require('MD5');

    var Validate = require('Validate');
    var DataSelector = require('DataSelector');
    var GridConfig = require('GridConfig');

    var emitter = MiniQuery.Event.create();

    var user = SMS.Login.get();

    var divHead = document.getElementById("div-head-content");
    var divBody = document.getElementById("div-body-content");

    var samples = require("Samples")(divHead);

    // 第一个单据体(应设计成集合支持多表体)
    var billGrid;
    // 控制业务是否有修改-有修改关闭时进行提示
    var billChanged = false;
    // 单据元数据
    var metaData;
    // F7选择控件集合
    var selectors = {};
    // F7选择控件个性处理函数
    var fnSelectors;
    // 业务类别id
    var formClassId;
    //单据内码
    var itemId;
    // 单据体处理函数
    var fnEntry;
    // 默认值
    var initValue = {};

    /**
     * 表格数据呈现
     * @param classId 业务类别
     * @param id 内码，标识了一条数据，编辑时使用，新增时传0即可
     * @param fnE 含有表体字段时，暂时通过回调给到调用者呈现
     * @param fnS 含有特殊控件时，特殊控件初始化前回调给调用者做配置
     */
    function render(classId, id, fnE, fnS, dfValue) {

        formClassId = classId;
        itemId = id;
        fnEntry = fnE;
        fnSelectors = fnS;
        initValue = dfValue;
        getMetaData();

    }

    /**
     *  获取单据模板
     */
    function getMetaData() {

        emitter.fire('beforeGetMetaData', []);

        // 获取单据模板
        var api = new API('template/getFormTemplate');

        api.get({
            'classId': formClassId,
        });

        api.on({
            'success': function (data, json) {
                // 将单据模板数据缓存下来
                metaData = data;
                show();
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });
    }

    //-----------F7处理逻辑Begin-------------//
    var getF7Data = function (itemId, classId, fn) {


        var api = new API('template/getItemById');

        api.get({
            'classId': classId,
            'id': itemId
        });
        api.on({
            'success': function (data, json) {
                fn && fn(data, json);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });

    };

    var setF7Data = function (itemId, classId, key) {

        if (itemId && itemId.trim() != '') { //是否具有默认值

            getF7Data(itemId, classId, function (data) {
                var selectorData = [{
                    ID: data[metaData['formClass'].primaryKey],
                    number: data.number || '',
                    name: data.name || ''
                }];
                selectors[key].setData(selectorData);
                emitter.fire(key + '.defaultFill', [selectorData]);
            });

        }
    };

    //-----------F7处理逻辑End-------------//

    //------是否必填校验逻辑(因多地方使用所以抽出来) BEGIN-----//
    var isMustFiled = function (isUpdate, field) {
        var mustInput = field['mustInput'] || 0;
        var mustInputMask = 0; //是否必填掩码
        if (isUpdate) {
            //FMustInput  字段显示权限-后端mustInput定义 4 编辑时平台用户必填，8编辑时候物业用户必填
            if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                // 平台用户
                mustInputMask = 2;
            } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                //供应商用户
                mustInputMask = 8;
            }
        } else {
            //FMustInput  字段显示权限-后端mustInput定义 4 编辑时平台用户必填，8编辑时候物业用户必填
            if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                // 平台用户
                mustInputMask = 1;
            } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                //供应商用户
                mustInputMask = 4;
            }
        }
        return !!(mustInputMask & mustInput); //是否必填
    }

    //------是否必填校验逻辑 END-----//

    function initController() {

        var isUpdate = !!itemId; //新增 || 修改

        if (!metaData || !metaData['formFields'] || !metaData['formFields'][0]) {
            SMS.Tips.error('元数据错误，请联系管理员');
            return;
        }
        var fields = metaData['formFields'][0];

        for (var item in fields) {

            var field = fields[item];
            var keyName = field['key'];
            var defaultValue = initValue ? initValue[keyName] || field['defaultValue'] : field['defaultValue'];
            var lookUpClassId = field['lookUpClassID'];
            var element = getElements(keyName);
            if (!element) {
                continue;
            }

            var mask = field["display"] || 0;
            var lockMaskDisplay = 0;
            var display = 0;

            // 字段显示性，锁定性处理
            if (isUpdate) {

                lockMaskDisplay = 2;
                //display 字段显示权限-后端FDisPlay定义 4：编辑时对于平台用户显示，8：编辑时对于供应商用户显示：12：平台供应商用户都显示
                //lockMaskDisplay  字段显示权限-后端FLock定义 4 编辑时平台用户锁定，8编辑时候供应商用户锁定
                if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    // 平台用户
                    display = 4;
                    lockMaskDisplay = 2;
                } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                    //供应商用户
                    display = 8;
                    lockMaskDisplay = 8;
                }
            } else {
                // 字段显示权限-后端FDisPlay定义 16：新增时对于平台用户显示，32：新增时对于供应商用户显示：48：平台供应商用户都显示
                //lockMaskDisplay  字段显示权限-后端FLock定义 1 编辑时平台用户锁定，4编辑时候供应商用户锁定
                if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    // 平台用户
                    display = 16;
                    lockMaskDisplay = 1;
                } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                    //物业用户
                    display = 32;
                    lockMaskDisplay = 4;
                }
            }
            var lockMask = field['lock'] || 0;
            //是否锁定
            var isLock = !!(lockMask & lockMaskDisplay);
            //是否 移除
            var isRemove = !(mask & display);

            //锁定处理
            if (isLock) {
                if (field['ctrlType'] == 6) {
                    //F7选择框锁定特殊处理
                    selectors[keyName].lock();
                } else {
                    element.prop("disabled", "disabled");
                }
            } else {
                if (field['ctrlType'] == 6) {
                    //F7选择框锁定特殊处理
                    selectors[keyName].unlock();
                } else {
                    element.prop("disabled", "");
                }
            }

            //F7选择框锁定特殊处理
            /*            if (field['ctrlType'] == 6) {
             /!*                var key1 = keyName;
             var lookUpType = field['lookUpType'];
             var classId = field['lookUpClassId'];
             lockF7(key1, defaultValue, classId, lookUpType, isLock, selectors);*!/

             selectors[keyName].lock();
             }*/

            // 默认值处理
            if (!isUpdate) {

                //新增时处理默认值-默认值可来自模板配置或者业务传递，业务传递的默认值优先于模板配置
                if (defaultValue && defaultValue.trim() != '') {

                    if (field['ctrlType'] == 1) {
                        //                  if (element.autoNumeric) {
                        //                      element.autoNumeric('set', defaultValue);
                        //                  } else {
                        element.val(defaultValue);
                        //                  }
                    }
                    if (field["dataType"] == 2) { //数字 文本值
                        element.val(defaultValue);
                    }
                    if (field["dataType"] == 3) { //日期时间
                        // element.value = defaultValue;
                    }
                    if (field["dataType"] == 4) { //布尔
                        element.prop("checked", defaultValue);
                    }
                    if (field["ctrlType"] == 6) { //选择框
                        setF7Data(defaultValue, field['lookUpClassID'], keyName);
                    }
                }
            }

            //移除处理
            element.parents("tr").hide();
            if (isRemove) {
                element.parents("tr").remove();
            } else {
                element.parents("tr").show();
            }
            //必填处理
            if (isMustFiled(isUpdate, field)) {
                //如果是必填需要添加 红色 * 号
                var addressCascadePicker = field['key'];
                if ($.inArray(addressCascadePicker, addressCascadePickerArray) >= 0) {
                    element = $('#div-address-picker'); //地址级联必填特殊处理
                }
                if (element.parents("td").siblings().find(".must-mark").length <= 0) { //如果不存在
                    var html = element.parents("td").siblings().html();
                    element.parents("td").siblings().html("<span class=\"must-mark\">*</span>" + html);
                }
            } else {
                if (element.parents("td").siblings().find(".must-mark")) {
                    element.parents("td").siblings().find(".must-mark").remove();
                }
            }
        }

        //DOM 元素处理，后台没返回则直接移除掉
        var fildKeys = $.Object.getKeys(fields);
        //选择所有 input元素，和 F7
        $(":input[id^='bd-']:not([type='hidden']),.F7-box[id^='bd-']").each(function (index, item) {
            var domId = $(item).attr("id").replace("bd-", "");
            if ($.inArray(domId, fildKeys) < 0) {
                $(item).parents("tr").remove();
            }
        });
    }

    /**
     * 根据模板构建页面控件
     */
    function initPage() {

        var fields = MiniQuery.Object.toArray(metaData['formFields'][0]);

        var length = fields.length;
        if (length < 10) {
            // 分组：小于10个字段每行显示两个字段
            fields = $.Array.group(fields, 1);
        } else if (length <= 20) {
            // 分组：小于20个字段每行显示两个字段
            fields = $.Array.group(fields, 2);
        } else if (length <= 40) {
            // 分组：小于40个字段每行显示三个字段
            fields = $.Array.group(fields, 3);
        } else {
            // 分组：大于40个字段每行显示四个字段
            fields = $.Array.group(fields, 4);
        }

        divHead.innerHTML = $.Array.keep(fields, function (group, no) {

            return $.String.format(samples["items"], {

                item: $.Array.keep(group, function (field, no) {

                    var sample = "";

                    if (!field.display) {
                        return "";
                    }

                    var ctrlType = field.ctrlType;

                    if (!ctrlType) {
                        // 默认文本
                        ctrlType = 10;
                    }

                    /*
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
                     */
                    switch (ctrlType) {
                        case 1:
                        case 2:
                        case 8:
                        case 9:
                        case 10:
                            sample = samples["text"];
                            break;
                        case 11://多行文本
                            sample = samples["textarea"];
                            break;
                        case 12:
                            sample = samples["datatime"];
                            break;
                        case 3: // checkbox
                            sample = samples["checkbox"];
                            break;
                        case 6:
                            sample = samples["f7"];
                            break;
                        case 13: //男：女
                            sample = samples["man_female"];
                            break;
                        case 14:
                            sample = samples["password"];
                            break;
                        case 15: //是：否
                            sample = samples["yes_no"];
                            break;
                        default:
                            sample = samples["text"];
                    }

                    return $.String.format(sample, {
                        mustInput: field.mustInput ? $.String.format(samples["mustInput"], {}) : "",
                        name: field.name,
                        key: field.key,
                    });

                }).join("")

            });
        }).join('');

        var formClassEntry = MiniQuery.Object.toArray(metaData['formClassEntry']);

        // 构建子表dom结构
        divBody.innerHTML = $.Array.keep(formClassEntry, function (item, no) {
            return $.String.format(samples["detail"], {
                key: item.foreignKey
            });
        }).join('');

        // 使用jqGrid渲染出所有子表表格

        MiniQuery.Object.each(metaData['formClassEntry'], function (entryIndex, formClassEntryItem) {

            SMS.use('Grid', function (Grid) {

                billGrid = new Grid(formClassEntryItem.foreignKey);

                var defaults = GridConfig.getConfig({
                    'fields': metaData['formFields'][entryIndex],
                    'defaults': {
                        gridName: formClassEntryItem.foreignKey,
                        width: 'auto',// $(window).width() - 5,
                        height: 'auto',
                        classId: formClassId,
                    },
                    'showType': 1// 新增时有添加删除按钮，编辑时有删除按钮,查看时无按钮
                });


                billGrid.render(defaults, [], metaData, entryIndex);

                billGrid.on('f7Selected', function (data) {

                });

                billGrid.on('afterEditCell', function (classId, rowid, cellname, value, iRow, iCol) {

                });

                billGrid.on('afterSaveCell', function (classId, rowid, cellname, value, iRow, iCol) {

                    if (classId === 2020) {
                        // 发货单新增编辑时候值更新事件处理
                        // 下一迭代重构(应该由数据库配置字段值更新规则先)
                        switch (cellname) {
                            case 'actualQty':
                                // 实发数量变化后修改金额
                                // 1：获取物料单价
                                var price = billGrid.getCell(rowid, 'price');
                                console.log("price=" + price);
                                var amount = (value * price).toFixed(2);
                                billGrid.setCell(rowid, 'amount', amount)
                                break;
                        }

                    }
                });

            });

        });


        /*SMS.use('Grid', function (Grid) {

            var student = [
                {id:'001',name:'zhangsan1',sex:'m',age:23},
                {id:'002',name:'zhangsan2',sex:'w',age:20},
                {id:'003',name:'zhangsan3',sex:'m',age:22},
                {id:'004',name:'zhangsan4',sex:'w',age:21},
                {id:'005',name:'zhangsan5',sex:'m',age:19},
                {id:'006',name:'zhangsan6',sex:'w',age:18},
                {id:'007',name:'zhangsan7',sex:'m',age:24},
                {id:'008',name:'zhangsan8',sex:'w',age:20},
                {id:'009',name:'zhangsan9',sex:'m',age:25},
                {id:'010',name:'zhangsan10',sex:'w',age:23}
            ];
            $("#user_id").jqGrid({
                data:student,
                datatype:'local',
                caption:'学生信息表',
                styleUI: 'Bootstrap',
                height:'auto',
                rowNum: 30,
                rowList: [10,20,30],
                colNames:['编号','姓名','性别','年龄','aa'],
                colModel:[
                    {name:'id',index:'id', width:300, sorttype:"int"},
                    {name:'name',index:'name', width:300,editable:true,fixed:true},
                    {name:'sex',index:'sex',width:300},
                    {name:'age',index:'age',width:300},
                    {name:'aa',index:'aa',width:300}
                ],
                pager: "#grid-pager",
                viewrecords: true,
                sortname: 'name',


                autowidth: true,
                multiselect: true,
                rownumbers: true, // 显示行号
                autoScroll: true,

                loadComplete:function(){
                    //$(this).jqGrid("setCaption",year+"年毕业学生信息表");
                }
            });
        });*/

    }


    /**
     * 初始化F7选择框控件
     */
    function initSelectors() {

        var fields = metaData['formFields'][0];

        for (var key in fields) {

            var field = fields[key];

            if (field.lookUpType === 1) {
                // 引用基础资料
                var config = {
                    targetType: 1, //跳转方案
                    classID: field.lookUpClassId,
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
                    pConfig = fnSelectors(field.classId, field.key);// 个性化配置
                }

                config = $.Object.extend({}, config, pConfig);

                selectors[field.key] = DataSelector.create(config);

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
            }
        });

        // 初始化selectors后将selectors抛出，Edit模块中可能需要
        emitter.fire("afterInitSelectors", [selectors]);

    }

    function initDateTimerPicker() {

        var fields = metaData['formFields'][0];

        for (var item in fields) {

            var field = fields[item];

            if (field.ctrlType === 12) {
                // 日期控件
                generateDateTimePicker(field.key);

            }
        }
        emitter.fire("afterInitDateTimerPicker", []);

    }

    function generateDateTimePicker(key) {
        // 异步执行，不可放入for循环，否则key存在被覆盖的风险
        SMS.use('DateTimePicker', function (DateTimePicker) {

            new DateTimePicker(getValueElement(key), {
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

    /**
     * 界面渲染
     * @param formClassId
     * @param itemId
     * @param fnEntry
     */
    function show() {


        if (!metaData || !metaData['formFields'] || !metaData['formClass']) {
            SMS.Tips.error('元数据错误，请联系管理员');
            return;
        }

        emitter.fire('beforeShow', [metaData]);

        // 填充页面控件
        initPage();
        // 初始化selectors
        initSelectors();
        // 初始化时间控件
        initDateTimerPicker();
        //控件初始化，控制显示隐藏，只读 ,默认值等..
        initController();

        fixIE();

        if (!itemId) {
            // 初始化子表-调用者处理
            /*            if (!!fnEntry && !MiniQuery.Object.isEmpty(metaData['formEntries'])) {
             fnEntry && fnEntry(null, metaData);
             }*/
            if (!!fnEntry) {
                fnEntry && fnEntry(null, metaData);
            }

            emitter.fire('afterNewBill', [formClassId, metaData]);
            return;
        }

        var api = new API('template/getItemById');
        SMS.Tips.loading('数据加载中..');

        api.get({

            'classId': formClassId,
            'id': itemId

        });

        api.on({
            'success': function (data, json) {
                fill(data, fnEntry);
                SMS.Tips.success('数据加载成功', 1500);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });
    }

    // IE环境下文本框不能获取焦点，不能编辑
    function fixIE() {

        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            $("input[type='text']").each(function () {
                $(this).focus(function () {
                    $(this).select();
                });
            });
            $("textarea").each(function () {
                $(this).focus(function () {
                    $(this).select();
                });
            });
        }
    }

    /**
     * 保存
     * @param {} itemID 项目ID
     * @param {} fnValidate  验证错误回调函数-处理页面显示的错误提示
     * @param {} fnSuccess  成功回调函数
     * @param {} entryData //子表数据
     * @param {} customerErrData  //自定义错误消息
     * @returns {}
     */
    function save(itemID, fnValidate, fnSuccess, entryData, customerErrData) {

        // 编辑OR新增
        var isUpdate = !!itemID;

        verifyFields(isUpdate, function (successData) {

            fnValidate(successData);

            var data = {};
            data['classId'] = metaData['formClass']['classId'];
            data['data'] = successData;

            if (entryData) {
                data['data']['entry'] = entryData;
            }
            if (itemID) {
                data['itemId'] = itemID;
            }
            submitData(data, fnSuccess);

        }, function (successData, errorData) {
            // errorData.push(entryErrorData);
            fnValidate(successData, errorData);

        }, customerErrData);
    }

    /**
     * 数据校验-获取表头数据
     * @param isUpdate 编辑or新增
     * @param fnSuccess 成功回调
     * @param fnFail 失败回调
     * @param customerErrData 自定义错误
     */
    function verifyFields(isUpdate, fnSuccess, fnFail, customerErrData) {

        if (!metaData || !metaData['formFields']) {
            alert('元数据错误，请联系管理员');
            return;
        }
        // var formClass = metaData['formClass'];
        var fields = metaData['formFields'][0];
        var validate = true;
        var successData = {};
        var errorData = {};
        //合并错误提示信息
        if ($.Object.getKeys(customerErrData).length > 0) {
            $.Object.extend(errorData, customerErrData || {});
            validate = false;
        }
        for (var item in fields) {

            var field = fields[item];
            var keyName = field['key'];

            // var prefix = getPrefix(field['ctrlType']);
            var element = getValueElement(keyName);

            if (!element) {
                // todo: 按元数据未找到控件，跳过此字段
                continue;
            }
            //successData[keyName] = "";//清空元素的值，重新赋值
            if (field['ctrlType'] == 1) { // 数字
                var ne = getElements(keyName);
                var minVal = +field['minValue'];
                var maxVal = +field['maxValue'];
                var domValue = ne.val();
                var widgetData = ne.autoNumeric && ne.autoNumeric("get") || domValue;
                if (!domValue) {
                    if (isMustFiled(isUpdate, field)) {
                        var msg = field['name'] + '为必填项';
                        errorData[keyName] = msg;
                        validate = false;
                    } else {
                        //var checkNumber = validateFieldNumber(field['FName'], widgetData, maxVal, minVal);
                        successData[keyName] = widgetData;
                    }
                } else {
                    //var checkNumber = validateFieldNumber(field['FName'], widgetData, maxVal, minVal);
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
                // 用户基础资料密码字段特殊，蛋疼的处理-写死它，用来判断是否修改-加密
                if (!element.value && isMustFiled(isUpdate, field)) {
                    var msg = field['name'] + '为必填项';
                    errorData[keyName] = msg;
                    validate = false;
                } else {
                    if (element.value != 'XXXXXXXX') {
                        successData[keyName] = MD5.encrypt(element.value);
                        // 只有用户有密码，需求说新增用户时要保存明文密码--明文还是密码？？
                        if (!isUpdate) {
                            successData['initPwd'] = element.value;
                        }
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
                        successData[keyName] = element.value || '';
                    }
                }
            } else {
                var result = validateField(element.value, field['dataType'], field['length'], field['scale'], field['ctrlType']);
                if (!result) {
                    var msg = field['name'] + '输入内容不合法';
                    errorData[keyName] = msg;
                    validate = false;
                } else {
                    // 构建待提交的数据
                    successData[keyName] = element.value;
                }
            }
        }

        if (validate) {
            fnSuccess(successData);
        } else {
            fnFail(successData, errorData);
        }
    }

    function fill(data, fnEntry) {

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
            var element = getValueElement(keyName);
            var value = data[keyName] || '';

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
                var ne = getElements(keyName);
                //初始化控件
                try {
                    ne.autoNumeric('set', (value || 0));
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
                element.value = value == null ? '' : Number(value);
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

            element.value = value;
        }

        // 存在表体数据时，交给调用者自己处理
        if (data['entry'] && !MiniQuery.Object.isEmpty(data['entry'])) {
            if (typeof(fnEntry) == 'function') {
                fnEntry(data['entry'], metaData);
            }
        }

        emitter.fire('afterFill', [formClassId, metaData, data]);

        SMS.Tips.success('数据加载成功', 2000);
    }

    // 表体数据的清空，暂未处理
    function clear() {
        if (!metaData || !metaData['formFields']) {
            SMS.Tips.error('元数据错误，请联系管理员');
            return;
        }

        var fields = metaData['formFields'][0];
        var formClass = metaData['formClass'];
        initController();
        //初始化控件
        for (var item in fields) {

            var field = fields[item];
            var keyName = field['key'];

            var element = getValueElement(keyName);

            if (!element) {
                // todo: 按数据未找到控件，跳过此字段赋值
                continue;
            }
            if (fields[keyName]['ctrlType'] == 1) { // 数字
                var ne = getElements(keyName);
                ne.autoNumeric('set', '');
                continue;
            }
            if (fields[keyName]['ctrlType'] == 3) { // 多选按钮
                element.checked = false;
                continue;
            }

            if (fields[keyName]['ctrlType'] == 6) { // F7选择框

                var selectorData = [{
                    ID: '',
                    number: '',
                    name: ''
                }];

                selectors[keyName].setData(selectorData);
                continue;
            }

            element.value = '';
        }
    }

    /**
     * 数字类型校验
     * @param {} value 当前值
     * @param {} maxValue 最大值
     * @param {} minValue 最小值
     * @returns {} 1:小于最小值  2:大于最大值 3:通过
     */
    function validateFieldNumber(fileName, value, maxValue, minValue) {
        if (minValue !== null && minValue !== undefined) {
            if (+value <= +minValue) {
                return {
                    result: false,
                    msg: fileName + "不能小于" + minValue
                };
            }
        }
        if (maxValue !== null && maxValue !== undefined) {
            if (+value >= +maxValue) {
                return {
                    result: false,
                    msg: fileName + "不能大于" + maxValue
                };
            }
        }
        return {
            result: true,
            msg: ""
        };
    }

    // value:待校验值
    // valueType:规定类型
    // length:规定长度
    // scale: 规定小数位
    //ctrlType:控件类型
    function validateField(value, valueType, length, scale, ctrlType) {
        if (valueType == "1") { // 数值
            //return Validate.integer(value);
        }
        if (valueType == "2") { // 文本
            if (ctrlType == "8") {
                //手机号码验证
                return Validate.mobilePhone(value);
            }
            if (ctrlType == "9") {
                //电话验证
                return Validate.landlinePhone(value);
            }
        }
        if (valueType == "3") { // 日期型

        }
        return true;
    }

    function submitData(data, fnSuccess) {

        var action = 'template/addItem';

        if (data['itemId']) {
            action = 'template/editItem';
        }

        if (formClassId == 3020 && metaData.formFields["1"]["material"]) {
            // 物料证件批量新增特殊处理
            action = 'template/batchAddItemLicense';
        }

        var api = new API(action);

        api.post({
            classId: data.classId,
            itemId: data.itemId,
            data: data.data
        });

        api.on({
            'success': function (rData, json) {
                fnSuccess(rData);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');

            }
        });
    }

    function forbid(classId, itemId, operateType) {

        var api = new SMS.API('bos/baseitem');
        api.get({
            action: 'forbid',
            data: {
                'classID': classId,
                'itemID': itemId,
                'operateType': operateType
            }
        });

        api.on({
            'success': function (data, json) {
                SMS.Tips.success(operateType === 1 ? '禁用成功' : '反禁用成功', 2000);
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

    function searchItems(formClassID, keyName, fn) {

        var api = new API('bos/baseitem');

        api.get({
            action: 'itemSearch ',
            data: {
                classID: formClassID,
                fieldKey: keyName,
                keyValue: '',
                searhType: 0,
                pageNo: 1,
                pageSize: 200,
            }
        });

        api.on({
            'success': function (data, json) {
                fn(data);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });

    }

    function getValueElement(keyName) {
        return document.getElementById('bd-' + keyName);
    }

    function getNameElement(keyName) {
        return document.getElementById('bd-' + keyName + '-name');
    }

    function getElements(keyName) {
        return $('#bd-' + keyName);
    }

    function isBillChanged() {
        return billChanged;
    }

    return {
        render: render,
        save: save,
        clear: clear,
        forbid: forbid,
        isBillChanged: isBillChanged,
        getSelectors: function (name) {
            return selectors[name];
        },
        getSamples: function () {
            return samples;
        },
        searchItems: searchItems,
        initController: initController,
        on: emitter.on.bind(emitter)
    };
});