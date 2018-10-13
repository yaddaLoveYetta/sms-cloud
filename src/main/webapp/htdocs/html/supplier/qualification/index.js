/**
 * Created by yadda on 2017/5/8.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var BL = SMS.require('ButtonList');

    var FormAction = require('FormAction');
    var List = require('List');
    var Pager = require('Pager');
    var Tree = require('Tree');

    var classId = 9001;
    var txtSimpleSearch = document.getElementById('txt-simple-search');
    var conditions = {};
    // 当前查看的医院
    var currentNode = 0;
    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    //默认配置
    var defaults = {
        pageSize: 10,
        sizes: [20, 30, 40],
        pageNo: 1,
        hasBreadcrumbs: true,
        multiSelect: true
    };

    $(txtSimpleSearch).bind('keyup', function (event) {

        var keyWorld = $(this).val();

        if ($.trim(keyWorld) === "") {
            return;
        }

        var options = {
            ignoreCase: true,    // case insensitive 忽略大小写
            exactMatch: false,   // like or equals 准确匹配
            revealResults: false // reveal matching nodes 显示结果
        };

        var result = Tree.search(keyWorld, options);
        console.log(result);

        if (result && result.length === 1) {
            Tree.selectNode(result[0], {silent: false});
        }

        if (result && result.length > 1) {
            // 多于一个结果时去掉“所有医院”(如果有)后选择第一个匹配的
            var node = result[0].hospital === 0 ? result[1] : result[0];
            Tree.selectNode(node, {silent: false});
        }


    });

    // 必须指明获取功能按钮的场景 0:查看(列表)1:(新增)2:(编辑)
    FormAction.create({'classId': classId, 'type': 2}, function (config) {

        var ButtonList = new BL(config);

        ButtonList.render();

        //支持二级事件，二级事件对应 item 中的 name
        ButtonList.on('click', {
            // 刷新
            'refresh': function (item, index) {
                refresh();
            },
            // 提交证件给医院
            'transfer': function (item, index) {
                // 发送一个证件给医院
                /*if (currentNode.hospital === 0) {
                    SMS.Tips.error('请选择医院', 1000);
                    return;
                }*/

                SMS.use('Dialog', function (Dialog) {

                    var dialog = new Dialog({
                        title: '发送证件给医院',
                        width: 400,
                        height: 230,
                        url: $.Url.setQueryString('html/supplier/transferQualification/index.html'),
                        data: {
                            node: currentNode
                        },
                        button: [{
                            value: '确定',
                            className: 'sms-submit-btn',
                            callback: function () {

                                dialog.__dispatchEvent('get');
                                var data = dialog.getData();

                                var type = data.type;
                                var hospital = data.hospital;
                                var supplierQualificationId = data.supplierQualificationId;

                                if (type > 0 && hospital > 0 && supplierQualificationId > 0) {

                                    dialog.find('[data-id="确定"]').attr('disabled', true);

                                    transferQualification({
                                        type: type,
                                        hospital: hospital,
                                        supplierQualificationId: supplierQualificationId
                                    }, function (data) {
                                        dialog.find('[data-id="确定"]').attr('disabled', false);
                                        dialog.setData(data);
                                        dialog.__dispatchEvent('serverBack');
                                    });
                                }
                                return false;

                            }
                        }, {
                            value: '关闭',
                            className: 'sms-cancel-btn'
                        }]
                    });

                    //默认关闭行为为不提交
                    dialog.isSubmit = false;

                    dialog.showModal();

                    dialog.on({
                        remove: function () {
                            refresh();
                        }
                    });

                });

                function transferQualification(config, fn) {

                    var api = new API('supplier/transferQualification');

                    api.post({
                        type: config.type,
                        hospital: config.hospital,
                        supplierQualificationId: config.supplierQualificationId
                    });

                    api.on({
                        'success': function (data, json) {
                            fn && fn({
                                result: true,
                                msg: '向医院发送证件成功！'
                            });
                        },

                        'fail': function (code, msg, json) {
                            fn && fn({
                                result: false,
                                msg: msg
                            });
                        },

                        'error': function () {
                            fn && fn({
                                result: false,
                                msg: '网络繁忙，请稍候再试'
                            });
                        }
                    });
                }

            },
            // 供应商合作医院-查看医院详细信息
            'view-hospital': function (item, index) {

                if (currentNode.hospital === 0) {
                    SMS.Tips.error('请选择医院', 1000);
                    return;
                }

                Iframe.open({
                    id: '-view-hospital-' + currentNode.hospital,
                    name: '医院资料',
                    url: './html/bill-ext/hospital/index.html',
                    query: {
                        'id': currentNode.hospital,
                        'classId': 1012,
                        'operate': 0
                    }
                });

            }
        });
    });


    function refresh() {

        if (currentNode.hospital === 0) {
            return;
        }

        List.render({
                hospital: currentNode.hospital,
                pageNo: defaults.pageNo,
                pageSize: defaults.pageSize
            }, function (total, pageSize) {

                Pager.render({
                    size: pageSize,
                    sizes: defaults.sizes,
                    total: total,
                    change: function (no, pageSize) {
                        defaults.pageSize = pageSize;
                        List.render({
                            hospital: currentNode.hospital,
                            pageNo: no,
                            pageSize: defaults.pageSize
                        });
                    }
                });

            }
        );
    }

    /**
     * 获取有可显示附件的记录
     * @param items
     * @return {Array}
     */
    function getShowItems(items) {

        var showItems = [];

        $.Array.each(items.attachments, function (item, index) {
            var url = item.path;
            if (url && ( $.String.endsWith(url, '.pdf', true) || $.String.endsWith(url, '.jpg', true) || $.String.endsWith(url, '.jpeg', true) || $.String.endsWith(url, '.png', true) || $.String.endsWith(url, '.gif', true)  )) {
                showItems.push(items);
                return false;
            }
        });

        return showItems;
    }

    List.on({
        'preview': function (item, index) {

            //  判断列表中有无可预览的附件
            var showItems = getShowItems(item);

            if (showItems.length === 0) {
                SMS.Tips.error('列表没有可预览的附件!', 1000);
                return;
            }

            // 附件预览
            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '附件预览',
                    width: 900,
                    height: 550,
                    url: $.Url.setQueryString('html/supplier/attachmentView/index.html'),
                    data: {
                        'showItems': showItems
                    },
                    button: []
                });

                //默认关闭行为为不提交
                dialog.isSubmit = false;

                dialog.showModal();

                dialog.on({
                    remove: function () {
                        refresh();
                    }
                });

            });
        }
    });

    Tree.on('change', function (node) {

        currentNode = node;

        List.render({
                hospital: node.hospital,
                pageNo: defaults.pageNo,
                pageSize: defaults.pageSize
            }, function (total, pageSize) {

                Pager.render({
                    size: pageSize,
                    sizes: defaults.sizes,
                    total: total,
                    change: function (no, pageSize) {
                        defaults.pageSize = pageSize;
                        List.render({
                            hospital: node.hospital,
                            pageNo: no,
                            pageSize: defaults.pageSize
                        });
                    }
                });

            }
        );
    });

    Tree.render();

})();