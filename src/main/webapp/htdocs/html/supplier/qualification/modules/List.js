﻿
/**
 * List 模块
 *
 */
define("List", function (require, module, exports) {
    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var API = SMS.require("API");
    var Iframe = SMS.require('Iframe');
    var dialog = Iframe.getDialog();
    var div = document.getElementById("div-list");
    // 完整名称为 List/Samples
    var samples = require("/Samples")(div);

    var primaryKey = "";
    var list = {};
    var hasBind = false;
    var emitter = MiniQuery.Event.create();
    var index$selected = {};
    // 记录选中的索引
    var classId;

    var tid = null;

    function load(config, fn) {

        SMS.Tips.loading("数据加载中...");

        var api = new API('template/getFormTemplate');

        api.post({
            hospital: config.hospital,
            pageNo: config.pageNo,
            pageSize: config.pageSize
        });

        api.on({
            'success': function (data, json) {
                SMS.Tips.success("数据加载成功", 1000);
                var total = data.total;
                fn && fn(data, total);
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

    function render(config, fn) {
        // 清空已选择项
        index$selected = {};

        load({
            hospital: config.hospital,
            pageNo: config.pageNo,
            pageSize: config.pageSize
        }, function (data, total) {
            list = data;
            primaryKey = list.primaryKey;
            var headItems = data.head.items;
            var bodyItems = data.body.items;
            div.innerHTML = $.String.format();

            bindHover();

            if (!hasBind) {
                bindEvents(config.multiSelect);
                hasBind = true;
            }

            fn && fn(total, config.pageSize);

            emitter.fire("renderDone", []);
        });

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

        $(div).delegate("td[data-index]", "click", function (event) {
            var td = this;

            if (td.getAttribute("child-index")) {
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
        $(div).delegate("tr[data-index]", "click", function (event) {
            var tr = this;

            if (tr.getAttribute("child-index")) {
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

    function bindHover() {
        $('.data-table table tbody tr td a').hover(function () {
            if ($(this).siblings().length > 0) {
                return;
            }
            $(this).after(samples["item.pop.menu"])

        }, function () {
            clearTimeout(tid);
            var a = this;
            tid = setTimeout(function () {
                $(a).siblings().remove();
            }, 3000);

        })
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

    function send(classId, list, fn) {
        Operation.send(classId, list, fn);
    }

    function checkExpired() {

        var headItems = list.head.items;
        var bodyItems = list.body.items;

        var beginDate;
        var endDate;

        for (var i = 0; i < bodyItems.length; i++) {

            beginDate = bodyItems[i]['data']['beginDate'];
            endDate = bodyItems[i]['data']['endDate'];

            if (beginDate && endDate) {

                if (new Date(endDate.replace(/\-/g, '\/')) < new Date()) {
                    //开始时间大于了结束时间
                    $('tr[data-index=' + i + ']').css('color', '#f35151');
                }

                continue;

            }

        }
    }

    return {
        load: load,
        render: render,
        getSelectedItems: getSelectedItems,
        send: send,
        checkExpired: checkExpired,
        on: emitter.on.bind(emitter)

    };
})
;