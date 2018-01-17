/**
 * List 模块
 *
 */
define("List", function (require, module, exports) {
    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var API = require("/API");
    // 完整名称为 List/API
    var Operation = require("/Operation");

    var Iframe = SMS.require('Iframe');

    var dialog = Iframe.getDialog();
    // 完整名称为 List/Operation
    var div = document.getElementById("div-list");
    var samples = require("/Samples")(div);
    // 完整名称为 List/Samples
    var primaryKey = "";
    var list = {};
    var hasBind = false;
    var emitter = MiniQuery.Event.create();
    var index$selected = {};
    // 记录选中的索引
    function load(config, fn) {
        //SMS.Tips.loading("数据加载中...");
        SMS.Tips.loading({
            text: '数据加载中...',
            delay: 500
        });
        API.get({
            classId: config.classId,
            pageNo: config.pageNo,
            pageSize: config.pageSize,
            conditions: config.conditions
        }, function (data) {
            SMS.Tips.success("数据加载成功", 1500);
            var total = data.body.total;
            fn && fn(data, total);
        });
    }

    function getTableHtml(field, data) {

        if (!data || data.length == 0 || data[0] == null) {
            return "";
        }
        var html = $.String.format(samples["item.table"], {
            // 行
            'item_table_tr': $.Array.keep(data, function (item, no) {
                return $.String.format(samples["item.table.tr"], {
                    index: no,
                    child: 1,
                    'item_table_tr_td': $.String.format(samples["item.table.tr.td"], {
                        index: no,
                        child: 1,
                        key: field.key,
                        td: item,
                        title: item,
                    })
                });
            }).join(""),
        });


        return html;

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
        if (type == "entry") {
            //如果只有一个就不返回下拉框了
            if (data.length <= 1) {
                return data[0] || "";
            }
            var html = "<select style='border: none;margin-left:-5px;background-color: transparent;'>"
            $.Array.each(data, function (item, index) { //item.FCarNo
                html += $.String.format("<option value='{0}'>{0}</option>", item || "");
            });
            html += " </select>";
            return html;
        }
        return data;
    }

    function render(config, fn) {
        // 清空已选择项
        index$selected = {};
        load({
            classId: config.classId,
            pageNo: config.pageNo,
            pageSize: config.pageSize,
            conditions: config.conditions
        }, function (data, total) {
            list = data;
            primaryKey = list.primaryKey;
            var headItems = data.head.items;
            var bodyItems = data.body.items;
            div.innerHTML = $.String.format(samples["table"], {
                checkbox: data.checkbox ? samples["th.checkbox"] : "",
                ths: $.Array.keep(headItems, function (field, index) {
                    return $.String.format(samples["th"], {
                        index: index,
                        th: field.text,
                        width: field.width
                    });
                }).join(""),
                trs: $.Array.keep(bodyItems, function (item, no) {

                    // 行
                    return $.String.format(samples["tr"], {
                        index: no,
                        //"disabled-class": item.disabled ? "disabled" : "",
                        "disabled-class": "",
                        checkbox: data.checkbox ? $.String.format(samples["td.checkbox"], {
                            index: no
                        }) : "",
                        tds: $.Array.keep(item.items, function (item, index) {
                            // 列
                            var field = headItems[index];
                            // 当前列的表头信息
                            return $.String.format(samples["td"], {
                                index: index,
                                key: field.key,
                                "number-class": field.key == "number" ? "number" : "",
                                td: field.isEntry ? getTableHtml(field, item.value) : getHtml(field.type, item.value),
                                title: field.isEntry ? '' : getHtml(field.type, item.value),
                            });
                        }).join("")
                    });
                }).join(""),
                emptys: data.checkbox ? samples["emptytd"] : "",
                tdtotals: $.Array.keep(headItems, function (field, index) {
                    return $.String.format(samples["tdtotal"], {
                        index: index,
                        key: field.key,
                        needTotal: field.isCount == "1",//(field.type == 1 && field.lookupType == 0),
                        width: field.width
                    });
                }).join("")
            });
            sumTdTotal(data);
            if (!hasBind) {
                bindEvents(config.multiSelect);
                hasBind = true;
            }
            if (!config.multiSelect) {
                // 处理刷新全选按钮出来的问题
                $('[data-check="all"]').hide();
            }
            fn && fn(total, config.pageSize);
        });
    }

    function sumTdTotal(data) {
        // 求和运算
        var needtotalTds = $("#div-list table>tfoot td[needtotal='true']");
        var tbodytrLen = $("#div-list table>tbody>tr").length;
        if (needtotalTds.length <= 0 || tbodytrLen === 0 || dialog) {
            $("#div-list table>tfoot").hide();
        } else {
            needtotalTds.each(function (index, item) {
                var key = $(item).attr("totalkey");
                var totalArray = $("#div-list table>tbody td[key='" + key + "']").map(function () {
                    return $(this).html();
                }).get();
                var totalValue = $.Array.sum(totalArray);
                $(item).html(totalValue.toFixed(2));
            });
        }

        if (data && data.checkbox) {
            $("#div-list table>tfoot>tr>td:eq(1)").html("合计：");
        } else {
            $("#div-list table>tfoot>tr>td:eq(0)").html("合计：");
        }
    }

    function bindEvents(multiSelect) {
        if (multiSelect) {
            $(div).delegate('[data-check="all"]', "click", function (event) {
                var chk = this;
                var checked = chk.checked;
                $('[data-check="item"]').each(function () {
                    var chk = this;
                    check(chk, checked);
                });
            }).delegate('[data-check="item"]', "click", function (event) {
                var chk = this;
                check(chk);
                event.stopPropagation();
            });
        } else {
            $('[data-check="all"]').hide();
            $(div).delegate("[data-check=item]", "click", function (event) {
                // var item = this;
                var item = this;
                var checked = item.checked;
                $('[data-check="item"]').each(function () {
                    var item = this;
                    check(item, false);
                });
                check(item, checked);
                event.stopPropagation();
            });
        }

        // 主表列单击事件
        $(div).delegate("td[data-index]", "click", function (event) {
            var td = this;

            if (td.getAttribute("child")) {
                // 子表列单击
                //td = td.parentNode.parentNode.parentNode.parentNode; // 转换成主表列
                return; // 不触发,猫婆触发上级td事件
            }
            var tr = td.parentNode;
            var index = +td.getAttribute("data-index");
            // 列号
            var no = +tr.getAttribute("data-index");
            // 行号
            var headItems = list.head.items;
            var bodyItems = list.body.items;
            var field = headItems[index];
            var item = bodyItems[no];
            var args = [{
                row: no,
                cell: index,
                head: field,
                body: item,
                item: item.items[index]
            }, event];
            emitter.fire("click:" + no + "-" + index, args);
            emitter.fire("click:" + field.key, args);
            emitter.fire("cell.click", args);
        });
        //主表行单击事件
        $(div).delegate("tr[data-index]", "click", function (event) {
            var tr = this;

            if (tr.getAttribute("child")) {
                // 子表列单击
                // tr = tr.parentNode.parentNode.parentNode.parentNode; // 转换成主表行
                return; // 不触发,猫婆触发上级tr事件
            }

            var no = +tr.getAttribute("data-index");
            // 行号
            var bodyItems = list.body.items;
            var args = [{
                row: no,
                body: bodyItems[no]
            }, event];
            emitter.fire("click:" + no, args);
            emitter.fire("row.click", args);
            var chk = $(tr).find("[data-check=item]")[0];
            var checked = !chk.checked;
            chk.checked = checked;
            if (!multiSelect) {
                $(tr).siblings().removeClass("selected");
                $(tr).siblings().each(function () {
                    var sibCk = $(this).find("[data-check=item]")[0];
                    sibCk.checked = false;
                });
                index$selected = {};
            }
            check(chk, checked);
        });

    }


    function check(chk, checked) {
        checked = chk.checked = typeof checked == "boolean" ? checked : chk.checked;
        var tr = chk.parentNode.parentNode;
        $(tr).toggleClass("selected", checked);
        var index = +chk.getAttribute("data-index");
        // 行号
        index$selected[index] = checked;
    }

    function getSelectedItems() {
        var a = [];
        $.Object.each(index$selected, function (index, selected) {
            if (!selected) {
                return;
            }
            var item = list.body.items[index];
            a.push(item);
        });
        return a;
    }

    function getFilterItems() {
        return list.filterItems;
    }

    function getPrimaryKey() {
        return list.primaryKey;
    }

    function forbid(classId, list, operateType, fn) {
        Operation.forbid(classId, list, operateType, fn);
    }

    function del(classId, list, fn) {
        Operation.del(classId, list, fn);
    }

    function review(classId, list, fn) {
        Operation.review(classId, list, fn);
    }

    function unReview(classId, list, fn) {
        Operation.unReview(classId, list, fn);
    }

    function send(classId, list, fn) {
        Operation.send(classId, list, fn);
    }

    return {
        load: load,
        render: render,
        on: emitter.on.bind(emitter),
        getSelectedItems: getSelectedItems,
        getPrimaryKey: getPrimaryKey,
        del: del,
        getFilterItems: getFilterItems,
        forbid: forbid,
        review: review,
        unReview: unReview,
        send: send,
    };
})
;