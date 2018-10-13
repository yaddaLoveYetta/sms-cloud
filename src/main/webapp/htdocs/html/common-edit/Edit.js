/**
 * 表单数据模块
 *
 */
define('Edit', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var emitter = MiniQuery.Event.create();
    var Multitask = SMS.require('Multitask');
    var Operator = require('Operator');
    var Validate = require('Validate');

    var operate = 0;
    var itemId = 0;
    var metaData = {};
    var itemData = {};

    var user = SMS.Login.get();

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    /**
     * 数据渲染，新增时不处理，修改是填充界面数据
     * @param type 操作类别 1：新增 2：修改
     * @param classId 业务类别
     * @param id
     */
    function render(type, classId, id) {

        operate = type;
        itemId = id;

        var tasks = new Array();
        tasks.push({
            fn: Operator.getMetaData,
            args: [{
                'classId': classId
            }]
        });

        if (operate !== 1) {
            tasks.push({
                fn: Operator.getItemData,
                args: [{
                    'classId': classId,
                    id: id
                }]
            });
        }

        // 并发执行任务
        Multitask.concurrency(tasks, function (list) {

            metaData = list[0];
            itemData = list[1] || {};

            // 填充页面元素
            if (!metaData || !metaData['formFields']) {
                SMS.Tips.error('元数据错误，请联系管理员');
                return;
            }

            if (!itemData && operate !== 1) {
                // 查看，修改时有单据数据，新增时没有
                SMS.Tips.error('数据错误，请联系管理员');
                return;
            }

            initFieldLock();
            // 定制页面，不需要构建DOM，此处作为构建玩DOM结构时间节点
            emitter.fire('afterInitPage', [metaData]);

            fill();

            emitter.fire('afterFill', [classId, metaData, itemData]);

        });

    }

    // 处理字段锁定性
    function initFieldLock() {

        if (!metaData || !metaData['formFields'] || !metaData['formFields'][0]) {
            SMS.Tips.error('元数据错误，请联系管理员');
            return;
        }
        var fields = metaData['formFields'][0];

        var lockMask = 0;
        // 用户角色类别
        //var userRoleType = user.role.type;
        var userRoleType = (user.roles && user.roles[0] && user.roles[0]['type']) || -1;

        /*
            1	新增时对于平台用户锁定
            2	编辑时对于平台用户锁定
            4	新增时对于供应商用户锁定
            8	编辑时对于供应商用户锁定
            16	新增时对于医院用户锁定
            32	编辑时对于医院用户锁定
         */
        if (operate === 0) {
            // 查看详情时所有字段锁定
            lockMask = 63;
        } else if (operate === 1) {
            // 新增
            if (userRoleType === 1) {
                // 平台用户
                lockMask = 1;
            } else if (userRoleType === 2) {
                //供应商用户
                lockMask = 4;
            } else if (userRoleType === 3) {
                //医院用户
                lockMask = 16;
            }
        } else if (operate === 2) {
            // 编辑
            if (userRoleType === 1) {
                // 平台用户
                lockMask = 2;
            } else if (userRoleType === 2) {
                //供应商用户
                lockMask = 8;
            } else if (userRoleType === 3) {
                //医院用户
                lockMask = 32;
            }
        }

        for (var key in fields) {

            var field = fields[key];

            var element = Operator.get$Element(key);

            if (element.length === 0) {
                continue;
            }

            var fieldLock = field.lock;

            //新增时处理默认值-默认值可来自模板配置或者业务传递，业务传递的默认值优先于模板配置
            if (operate === 0 || !!(fieldLock & lockMask)) {
                // 需要锁定该字段 --查看时所有字段锁定
                switch (field.ctrlType) {
                    case 6:
                        //F7选择框锁定特殊处理
                        emitter.fire('selectorLock', [key]);
                        break;
                    /*                    case 7:
                                            //级联选择器锁定特殊处理
                                            emitter.fire('cascadePickerLock', [key]);
                                            break;*/
                    default:
                        element.prop("disabled", "disabled");
                }
            } else {
                switch (field.ctrlType) {
                    case 6:
                        //F7选择框锁定特殊处理
                        emitter.fire('selectorUnLock', [key]);
                        break;
                    default:
                        element.prop("disabled", "");
                }
            }
        }

        emitter.fire('afterFieldLock', [metaData, itemData]);
    }

    function fill() {

        if (operate === 1) {
            // 新增不处理
            return;
        }

        var fields = metaData['formFields'][0];

        for (var item in itemData) {

            var key = item;
            var field = fields[key];
            var element = Operator.getElement(key);
            var value = itemData[key] || '';

            if (!element) {
                // 按key未找到控件，跳过此字段赋值
                continue;
            }

            var ctrlType = field.ctrlType;

            if (!ctrlType) {
                // 默认文本
                ctrlType = 10;
            }

            switch (ctrlType) {
                case 1:
                case 2:
                case 16:
                    // 数字
                    emitter.fire('numberFieldSet', [field, key, value]);
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    // 类文本类型
                    element.value = value || '';
                    break;
                case 3:
                    // checkbox
                    element.checked = value;
                    break;
                case 6:
                    // F7选择框
                    var selectorData = [{
                        id: value,
                        number: itemData[key + '_NmbName'] || '',
                        name: itemData[key + '_DspName'] || '',
                        all: itemData
                    }];
                    emitter.fire('selectorSet', [field, key, selectorData]);
                    break;

                case 13:
                    //男：女
                    element.value = value || 0;
                    break;
                case 14:
                    // 用户多一个密码字段特殊，蛋疼的处理-写死它，用来判断是否修改-加密
                    element.value = 'XXXXXXXX';
                    break;
                case 15:
                    //是：否  后台返回true/false
                    element.value = value | 0;
                    break;
                default:
                    element.value = value || '';
            }

        }

    }

    function save(fn) {

        getHeadData(function (headData) {

            // 个性化控件数据处理
            var validate = emitter.fire('beforeSave', [metaData, headData]);
            validate = validate && validate[validate.length - 1];
            validate = validate === undefined ? true : validate;

            if (headData.errorData) {
                // 表头数据校验不通过
                showHeadValidInfo(headData.successData, headData.errorData);
                validate = false;
            } else {
                showHeadValidInfo(headData.successData);
            }

            if (validate) {
                // 校验通过-提交数据
                var data = {};
                data['classId'] = metaData['formClass']['classId'];
                data['data'] = headData.successData;

                if (operate === 2) {
                    // 编辑
                    data['id'] = itemId;
                }
                Operator.submitData(data, operate, function (ret) {
                    if (operate === 1) {
                        // 新增单据提交成功后将新增状态转变为编辑状态
                        // 记录下单据内码
                        itemId = ret.value;
                        // 状态变为编辑
                        operate = 2;
                    }
                    fn && fn(ret);
                });

            }

        })
    }

    // 获取表头数据
    function getHeadData(fn) {

        // 保存校验通过的数据
        var successData = {};
        // 保存校验失败的数据
        var errorData = {};
        // 默认校验通过
        var validate = true;

        var fields = metaData['formFields'][0];

        for (var key in fields) {

            var field = fields[key];
            var element = Operator.getElement(key);

            if (!element) {
                // 按元数据未找到控件，跳过此字段
                continue;
            }

            var domValue;
            var msg;
            var ctrlType = field.ctrlType;

            if (!ctrlType) {
                // 默认文本
                ctrlType = 10;
            }

            switch (ctrlType) {
                case 1:
                case 2:
                case 16:
                    // 数字
                    domValue = emitter.fire('numberFieldGet', [field, key]);

                    var minValue = field.minValue.trim() === '' ? 0 : field.minValue;
                    var maxValue = field.maxValue.trim() === '' ? 99999999.99 : field.maxValue;

                    if (domValue < minValue) {

                        msg = field['name'] + '必须大于' + minValue;
                        errorData[key] = msg;
                        validate = false;

                    } else if (domValue > maxValue) {

                        msg = field['name'] + '必须小于' + maxValue;
                        errorData[key] = msg;
                        validate = false;

                    } else {
                        successData[key] = domValue;
                    }
                    break;
                case 3:
                    // 多选按钮，无需校验
                    successData[key] = element.checked;
                    break;
                case 6:
                    //F7选择框
                    var selector = emitter.fire('selectorGet', [field, key]);

                    var selectorId = selector && selector[0].getData()[0]['id'];

                    if (!selectorId) {
                        if (Operator.isMustInputFiled(metaData, key, operate)) {
                            msg = field['name'] + '为必填项';
                            errorData[key] = msg;
                            validate = false;
                        } else {
                            successData[key] = 0;
                        }
                    } else {
                        successData[key] = selectorId;
                    }
                    break;
                case 14:
                    // 密码控件
                    if (!element.value && Operator.isMustInputFiled(metaData, key, operate)) {
                        msg = field['name'] + '为必填项';
                        errorData[key] = msg;
                        validate = false;
                    } else {
                        if (element.value !== 'XXXXXXXX') {
                            successData[key] = MD5.encrypt(element.value);
                        }
                    }
                    break;
                default:
                    // 默认处理
                    if (!element.value) {
                        if (Operator.isMustInputFiled(metaData, key, operate)) {
                            msg = field['name'] + '为必填项';
                            errorData[key] = msg;
                            validate = false;
                        } else {
                            // 给空字符串
                            successData[key] = element.value || '';
                        }
                    } else {

                        var result = validateField(element.value, key);

                        if (!result) {
                            msg = field['name'] + '输入内容不合法';
                            errorData[key] = msg;
                            validate = false;
                        } else {
                            successData[key] = element.value;
                        }
                    }
            }

        }

        if (validate) {
            fn && fn({
                successData: successData
            });
        } else {
            fn && fn({
                successData: successData,
                errorData: errorData
            });
        }

    }

    /**
     * 校验字段值合法性
     * @param value
     * @param key
     * @returns {boolean}
     */
    function validateField(value, key) {

        var fields = metaData['formFields'][0];

        var field = fields[key];

        var ctrlType = field.ctrlType;

        if (ctrlType === 1 || ctrlType === 2 || ctrlType === 16) {
            // 数值-单价金额
            return Validate.numeric(value);
        }
        if (ctrlType === 8) {
            // 手机号码
            return Validate.mobilePhone(value);
        }
        if (ctrlType === 9) {
            // 座机号码
            return Validate.phone(value);
        }
        if (ctrlType === 12) {
            // 日期时间型
            return Validate.isDateTime(value) || Validate.isDate(value);
        }

        return true;
    }

    function showHeadValidInfo(successData, errorData) {

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
        save: save,
        getHeadData: getHeadData,
        showHeadValidInfo: showHeadValidInfo,
        on: emitter.on.bind(emitter)
    };

});