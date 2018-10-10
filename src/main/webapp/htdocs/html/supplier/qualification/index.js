/**
 * Created by yadda on 2017/5/8.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var API = SMS.require('API');
    var MessageBox = SMS.require('MessageBox');
    var BL = SMS.require('ButtonList');

    var FormAction = require('FormAction');
    var List = require('List');
    var Pager = require('Pager');
    var Tree = require('Tree');

    var classId = 9001;
    var txtSimpleSearch = document.getElementById('txt-simple-search');
    var txtItemSearch = document.getElementById('txt-item-search');
    var conditions = {};
    // 当前查看的医院
    var currentHospital = 0;
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

    $(txtSimpleSearch).bind('keypress', function (event) {
        if (event.keyCode === 13) {
            refresh();
        }
    });

    $(txtItemSearch).bind('keypress', function (event) {
        if (event.keyCode === 13) {
            Tree.render(treeClassId, $(txtItemSearch).val());
        }
    });

    // 必须指明获取功能按钮的场景 0:查看(列表)1:(新增)2:(编辑)
    FormAction.create({'classId': classId, 'type': 2}, function (config) {

        var ButtonList = new BL(config);

        // 总事件，最后触发
        ButtonList.on('click', function (item, index) {
            //console.dir(item);
        });

        ButtonList.render();

        //支持二级事件，二级事件对应 item 中的 name
        ButtonList.on('click', {
            // 刷新
            'refresh': function (item, index) {
                refresh();
            },
            // 提交证件给医院
            'transfer': function (item, index) {
                // 发送到HRP
                if (!(classId == 1005 || classId == 1007 || classId == 1023)) {
                    //目前基础资料只有供应商-供应商资质类别-物料证件类别可同步回HRP
                    return;
                }
                var list = List.getSelectedItems();

                if (list.length == 0) {
                    SMS.Tips.error('请选择要操作的项', 1500);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作');
                    return;
                }

                if (list[0].data.review === 0) {
                    SMS.Tips.error('该记录未审核，不可发送', 1500);
                    return;
                }

                if (list[0].data.syncStatus === 1) {
                    SMS.Tips.error('该记录已发送到医院', 1500);
                    return;
                }

                MessageBox.confirm('确定要将该记录发送给医院?', function (result) {
                    if (result) {
                        List.send(classId, list, item.info.apiUrl, function () {
                            SMS.Tips.success('发送成功', 2000);
                            refresh();
                        });
                    }
                });

            },
            // 供应商合作医院-查看医院详细信息
            'view-hospital': function (item, index) {

                if (currentHospital === 0) {
                    SMS.Tips.error('请选择医院', 1000);
                    return;
                }

                Iframe.open({
                    id: '-view-hospital-' + currentHospital,
                    name: '医院资料',
                    url: './html/bill-ext/hospital/index.html',
                    query: {
                        'id': currentHospital,
                        'classId': 1012,
                        'operate': 0
                    }
                });

            }
        });
    });

    function getCondition() {

        conditions = {};

        var keyWorld = $(txtSimpleSearch).val()

        if ($.trim(keyWorld) !== "") {
            conditions['name'] = {
                'andOr': 'AND',
                'leftParenTheses': '((',
                'fieldKey': 'name',
                'logicOperator': 'like',
                'value': keyWorld,
                'rightParenTheses': ')'
            };
            conditions['number'] = {
                'andOr': 'OR',
                'leftParenTheses': '(',
                'fieldKey': 'number',
                'logicOperator': 'like',
                'value': keyWorld,
                'rightParenTheses': '))'
            };
        }

        if (treeFilter) {

            if (treeClassId === 1005) {
                // 供应商
                conditions['id'] = {
                    'andOr': 'AND',
                    'leftParenTheses': '(',
                    'fieldKey': 'supplier',
                    'logicOperator': '=',
                    'value': treeFilter.id,
                    'rightParenTheses': ')',
                    'needConvert': false,
                };
            } else if (treeClassId === 3030) {
                // 中标库
                conditions['id'] = {
                    'andOr': 'AND',
                    'leftParenTheses': '(',
                    'fieldKey': 'material',
                    'logicOperator': '=',
                    'value': treeFilter.id,
                    'rightParenTheses': ')',
                    'needConvert': false,
                };
            }
        }

        return conditions;
    }

    function refresh() {

        if (currentHospital === 0) {
            return;
        }

        List.render({
                hospital: currentHospital,
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
                            type: hospital,
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

    Tree.on('change', function (hospital) {

        currentHospital = hospital;

        List.render({
                hospital: hospital,
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
                            type: hospital,
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