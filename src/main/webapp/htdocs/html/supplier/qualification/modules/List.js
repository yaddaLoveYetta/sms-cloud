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
    var div = document.getElementById("div-qualification-info");
    // 完整名称为 List/Samples
    var samples = require("/Samples")(div);

    var list = {};
    var hasBind = false;
    var emitter = MiniQuery.Event.create();
    // 记录选中的索引
    var index$selected = {};

    var tid = null;

    // 附件展示时候的图片
    var src = {
        'file': '../../../css/img/file.png',
        'png': '../../../css/img/picture.png',
        'jpg': '../../../css/img/picture.png',
        'jpeg': '../../../css/img/picture.png',
        'excel': '../../../css/img/excel.png'
    };

    function load(config, fn) {

        SMS.Tips.loading("数据加载中...");

        var api = new API('supplier/getQualificationByHospital');

        if (config.hospital === 0) {
            // 没指定医院获取所有医院证件信息
            api = new API('supplier/getQualifications');
        }

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

            list = data.detail;

            div.innerHTML = $.String.format(samples["all"], {

                'typeCaption': config.hospital === 0 ? '' : samples["typeCaption"],
                'typeList': config.hospital === 0 ? '' : $.String.format(samples["typeList"], {
                    'typeListItem': $.Array.keep(data.types, function (type, index) {
                        return $.String.format(samples["typeListItem"], {
                            'checked': type.isExist ? 'checked' : '',
                            'name': type.name
                        });
                    }).join("")
                }),
                'qualificationList': $.String.format(samples["qualificationList"], {
                    'qualificationListItem': $.Array.keep(data.detail, function (item, index) {

                        return $.String.format(samples["qualificationListItem"], {
                            'index': index,
                            'src': getSrc(item.attachments),
                            'title': item.attachments.length + '个附件，点击查看',
                            'typeName': item.typeName,
                            'number': item.number,
                            'issue': item.issue,
                            'validityPeriod': item.validityPeriodBegin + ' -- ' + item.validityPeriodEnd
                        });
                    }).join("")
                })
            });

            if (!hasBind) {
                bindEvents();
                hasBind = true;
            }

            fn && fn(total, config.pageSize);

            emitter.fire("renderDone", []);
        });

    }

    function getSrc(attachments) {

        //return attachments[0].path;

        if (!attachments || attachments.length === 0) {
            return src.file;
        }

        return src[attachments[0].path.toLowerCase().split('.').splice(-1)] || src.file;

    }

    function bindEvents() {

        $(div).delegate("a.thumbnail", 'click', function (event) {

            var a = this;
            var index = a.parentNode.getAttribute("data-index");

            console.log(list[index]);

            emitter.fire("preview", [list[index], index]);

            event.stopPropagation();
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
        checkExpired: checkExpired,
        on: emitter.on.bind(emitter)

    };
})
;