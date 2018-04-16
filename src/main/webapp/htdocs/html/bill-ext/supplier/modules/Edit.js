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

    /**
     * 数据渲染，新增时不处理，修改是填充界面数据
     * @param type 操作类别 1：新增 2：修改
     * @param classId 业务类别
     * @param id
     */
    function render(type, classId, id) {

        operate = type;
        itemId = id;
        var tasks = [
            {
                fn: Operator.getMetaData,
                args: [{
                    'classId': classId
                }]
            },
            {
                fn: Operator.getItemData,
                args: [{
                    'classId': classId,
                    id: id
                }]
            }];

        // 并发执行任务
        Multitask.concurrency(tasks, function (list) {

            metaData = list[0];
            itemData = list[1];

            // 填充页面元素
            if (!metaData || !metaData['formFields']) {
                SMS.Tips.error('元数据错误，请联系管理员');
                return;
            }

            if (!itemData) {
                SMS.Tips.error('数据错误，请联系管理员');
                return;
            }
            // 定制页面，不需要构建DOM，此处作为构建玩DOM结构时间节点
            emitter.fire('afterInitPage', [metaData]);

            fill();

            emitter.fire('afterFill', [classId, metaData, itemData]);

        });

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
            var element = Operator.getValueElement(key);
            var value = itemData[key] || '';

            if (!element) {
                // TODO: 按key未找到控件，跳过此字段赋值
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

            var validate = true;

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
            var element = Operator.getValueElement(key);

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
                    var selector = selectors[key];
                    var selectorID = selector.getData()[0]['ID'];

                    if (!selectorID) {
                        if (Operator.isMustInputFiled(metaData, key, operate)) {
                            msg = field['name'] + '为必填项';
                            errorData[key] = msg;
                            validate = false;
                        } else {
                            successData[key] = 0;
                        }
                    } else {
                        successData[key] = selectorID;
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
        on: emitter.on.bind(emitter)
    };

});