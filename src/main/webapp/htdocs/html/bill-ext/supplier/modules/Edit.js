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

    /**
     * 数据渲染，新增时不处理，修改是填充界面数据
     * @param type 操作类别 1：新增 2：修改
     * @param id
     */
    function render(type, id) {

        if (type === 1) {
            return;
        }

        var tasks = [
            {
                fn: Operator.getMetaData,
                args: [{
                    'classId': 1012
                }]
            },
            {
                fn: Operator.getItemData,
                args: [{
                    'classId': 1012,
                    id: id
                }]
            }];

        // 并发执行任务
        Multitask.concurrency(tasks, function (list) {

            var metaData = list[0];
            var itemData = list[1];

            // 填充页面元素
            if (!metaData || !metaData['formFields']) {
                SMS.Tips.error('元数据错误，请联系管理员');
                return;
            }

            if (!itemData) {
                SMS.Tips.error('数据错误，请联系管理员');
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
                            16	单价/金额(两位小数)
                         */

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
                        /*                        var ne = numberFields[key];
                                                ne.set(value || 0);*/
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
                        /*                        var selectorData = [{
                                                    ID: value,
                                                    number: itemData[key + '_NmbName'],
                                                    name: itemData[key + '_DspName']
                                                }];
                                                selectors[key].setData(selectorData);*/

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

        });

    }

    return {
        render: render
    };

});