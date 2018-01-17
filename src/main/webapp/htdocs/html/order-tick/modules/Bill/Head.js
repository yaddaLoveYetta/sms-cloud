/**
 * 单据头模块
 * Created by yadda on 2017/5/13.
 */

define('Bill/Head', function (require, module, exports) {

        var data;

        var div = document.getElementById("dd-head");
        var samples = require("/Samples")(div);


        function render(template, data) {

            var fields = template['0'];

            fields = $.Array.group(fields, 4); // 分组，一行填充4个字段

            div.innerHTML = $.Array.keep(fields, function (group, no) {

                return $.String.format(samples["rows"], {

                    item: $.Array.keep(group, function (field, no) {

                        var key = field.key;

                        if (!field.visible) {
                            return '';
                        }

                        if (field.lookupType > 0 && field.lookupType < 3) { // lookupType
                            // 不为 0时，说明是引用类型
                            key = key + '_DspName';
                            // 此时要显示的字段为 key + '_DspName'
                        }

                        return $.String.format(samples["row"], {
                            name: field.text,
                            value: getHtml(field.type, data[key]),
                        })

                    }).join(""),

                });

            }).join("");
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

        return {
            render: render,
        };
    }
);
