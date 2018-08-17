/**
 * Created by yadda on 2017/5/8.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var API = SMS.require('API');
    var List = require('List');
    var Pager = require('Pager');
    var bl = require('ButtonList');
    var ButtonListOption = require('ButtonListOption');
    var MessageBox = SMS.require('MessageBox');
    var Tree = require('Tree');
    var ClassMapping = require('ClassMapping');

    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');

    var sysName = classId == 3010 ? '供应商资质' : '物料证件'

    var txtSimpleSearch = document.getElementById('txt-simple-search');
    var txtItemSearch = document.getElementById('txt-item-search');

    var conditions = {};
    var treeFilter;

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    // 供应商资质维护tree中显示供应商，物料证件维护tree中显示供应商中标库物料
    var treeClassId = classId == 3010 ? 1005 : classId == 3020 ? 3030 : 0;

    //默认配置
    var defaults = {
        pageSize: 10,
        typeId: '',
        pageNo: 1,
        hasBreadcrumbs: true,
        multiSelect: true
    };

    $(txtSimpleSearch).bind('keypress', function (event) {
        if (event.keyCode == 13) {
            refresh();
        }
    });

    $(txtItemSearch).bind('keypress', function (event) {
        if (event.keyCode == 13) {
            Tree.render(treeClassId, $(txtItemSearch).val());
        }
    });

    var blConfig;

    if (classId == 3020) {
        blConfig = {
            'items': [
                {
                    text: '新增',
                    name: 'add',
                    icon: '../../../css/main/img/add.png',
                    items: [{
                        text: '批量新增',
                        name: 'batchAdd',
                        icon: '../../../css/main/img/add.png',
                    }],
                },
                {
                    text: '删除',
                    name: 'del',
                    icon: '../../../css/main/img/delete.png',
                },
                {
                    text: '修改',
                    name: 'edit',
                    icon: '../../../css/main/img/edit.png',
                },
                {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../../css/main/img/refresh.png',
                },
                {
                    text: '审核',
                    name: 'check',
                    icon: '../../../css/main/img/check.png',
                    items: [{
                        text: '反审核',
                        name: 'unCheck',
                        icon: '../../../css/main/img/uncheck.png',
                    }],
                },
                {
                    text: '上传附件',
                    name: 'upload',
                    icon: '../../../css/main/img/upload.png',
                    items: [{
                        text: '附件预览',
                        name: 'preview',
                        icon: '../../../css/main/img/preview.png',
                    }],
                },
                {
                    text: '发送到医院',
                    name: 'send',
                    icon: '../../../css/main/img/send.png',
                },
                {
                    text: '导出',
                    name: 'export',
                    icon: '../../../css/main/img/download.png',
                }]
        };
    } else {
        blConfig = {
            'items': [
                {
                    text: '新增',
                    name: 'add',
                    icon: '../../../css/main/img/add.png',
                },
                {
                    text: '删除',
                    name: 'del',
                    icon: '../../../css/main/img/delete.png',
                },
                {
                    text: '修改',
                    name: 'edit',
                    icon: '../../../css/main/img/edit.png',
                },
                {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../../css/main/img/refresh.png',
                },
                {
                    text: '审核',
                    name: 'check',
                    icon: '../../../css/main/img/check.png',
                    items: [{
                        text: '反审核',
                        name: 'unCheck',
                        icon: '../../../css/main/img/uncheck.png',
                    }],
                },
                {
                    text: '上传附件',
                    name: 'upload',
                    icon: '../../../css/main/img/upload.png',
                    items: [{
                        text: '附件预览',
                        name: 'preview',
                        icon: '../../../css/main/img/preview.png',
                    }],
                },
                {
                    text: '发送到医院',
                    name: 'send',
                    icon: '../../../css/main/img/send.png',
                },
                {
                    text: '导出',
                    name: 'export',
                    icon: '../../../css/main/img/download.png',
                }]
        };

    }


    var ButtonList = bl.create(blConfig);

    ButtonList.render();

    //支持二级事件，二级事件对应 item 中的 name
    ButtonList.on('click', {

        'add': function (item, index) {
            // 增加
            SMS.use('Dialog', function (Dialog) {

                var dfValue = {};

                if (treeClassId == 1005) {
                    // 供应商资质
                    dfValue['supplier'] = treeFilter ? (treeFilter.id == 0 ? null : treeFilter.id) : null  // 新增供应商资质时，供应商默认值为当前选中的供应商
                } else if (treeClassId == 3030) {
                    // 物料证件
                    dfValue['material'] = treeFilter ? (treeFilter.id == 0 ? null : treeFilter.id) : null  // 新增物料证件，物料默认值为当前选中中标库的物料
                    dfValue['supplier'] = treeFilter ? (treeFilter.value.supplier ? treeFilter.value.supplier : null ) : null  // 新增物料证件，供应商默认值为当前选中中标库物料关联的供应商
                }

                var dialog = new Dialog({
                    title: '新增-' + sysName,
                    width: 700,
                    height: 550,
                    url: $.Url.setQueryString('html/base_edit/index.html', 'classId', classId),
                    data: {
                        defaultValue: dfValue
                    },
                    button: [],
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

        },
        'batchAdd': function (item, index) {
            // 增加
            SMS.use('Dialog', function (Dialog) {

                var dfValue = {};

                if (treeClassId == 1005) {
                    // 供应商资质
                    dfValue['supplier'] = treeFilter ? (treeFilter.id == 0 ? null : treeFilter.id) : null  // 新增供应商资质时，供应商默认值为当前选中的供应商
                } else if (treeClassId == 3030) {
                    // 物料证件
                    dfValue['material'] = treeFilter ? (treeFilter.id == 0 ? null : treeFilter.id) : null  // 新增物料证件，物料默认值为当前选中中标库的物料
                    dfValue['supplier'] = treeFilter ? (treeFilter.value.supplier ? treeFilter.value.supplier : null ) : null  // 新增物料证件，供应商默认值为当前选中中标库物料关联的供应商
                }

                var dialog = new Dialog({
                    title: '批量新增-' + sysName,
                    width: 700,
                    height: 550,
                    url: $.Url.setQueryString('html/supplier/batchAdd/index.html', 'classId', classId),
                    data: {
                        defaultValue: dfValue
                    },
                    button: [],
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
        },
        'del': function (item, index) {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            if (list.length > 1) {
                SMS.Tips.error('只能对一条记录进行操作', 1500);
                return;
            }
            if (list[0].data.review === 1) {
                SMS.Tips.error('该记录已审核，请反审核后再操作', 1500);
                return;
            }
            MessageBox.confirm('确定删除选择的项?', function (result) {
                if (result) {
                    List.del(classId, list, function () {
                        SMS.Tips.success("删除成功", 1500);
                        refresh();
                    });
                }
            });
        },
        'edit': function (item, index) {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            if (list.length > 1) {
                SMS.Tips.error('只能对一条记录进行操作', 1500);
                return;
            }
            if (list[0].data.review === 1) {
                SMS.Tips.error('该记录已审核，请反审核后再操作', 1500);
                return;
            }
            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '修改-' + sysName,
                    width: 700,
                    height: 550,
                    url: $.Url.setQueryString('html/base_edit/index.html', {
                        'classId': classId,
                        id: list[0].data.id,
                    }),
                    data: {},
                    button: [],
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


        },
        'check': function (item, index) {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            /*            if (list.length > 1) {
             SMS.Tips.error('一次只能对一条记录进行操作', 1500);
             return;
             }*/
            List.review(classId, list, function () {
                SMS.Tips.success("审核成功", 1500);
                refresh();
            });


        },
        'unCheck': function (item, index) {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            /*            if (list.length > 1) {
             SMS.Tips.error('一次只能对一条记录进行操作', 1500);
             return;
             }*/

            List.unReview(classId, list, function () {
                SMS.Tips.success("反审核成功", 1500);
                refresh();
            });
        },
        'upload': function (item, index) {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            if (list.length > 1) {
                SMS.Tips.error('一次只能对一条记录进行操作', 1500);
                return;
            }
            if (list[0].data.review === 1) {
                SMS.Tips.error('该记录已审核，请反审核后再操作', 1500);
                return;
            }

            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '上传附件-' + sysName,
                    width: 500,
                    height: 300,
                    url: $.Url.setQueryString('html/supplier/upload/index.html'),
                    data: {
                        'classId': classId,
                        id: list[0].data.id,
                    },
                    button: [],
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

        },
        'send': function (item, index) {
            // 发送到HRP

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项', 1500);
                return;
            }
            if (list.length > 1) {
                SMS.Tips.error('一次只能对一条记录进行操作', 1500);
                return;
            }
            if (list[0].data.review === 0) {
                SMS.Tips.error('该记录未审核，不可发送', 1500);
                return;
            }
            MessageBox.confirm('确定要将该记录发送给医院?', function (result) {
                if (result) {
                    List.send(classId, list, function () {
                        SMS.Tips.success('发送成功', 2000);
                        refresh();
                    });
                }
            });
        },
        'refresh': function (item, index) {
            refresh();
        },
        'export': function (item, index) {
            var conditions = getCondition();

            var conditionArray = new Array();
            for (var item in conditions) {
                if (conditions[item] === '') {
                    continue;
                }
                conditionArray.push(conditions[item]);
            }

            var api = new API("file/export");

            var url = api.getUrl();
            url = $.Url.addQueryString(url, {
                classId: classId,
                condition: $.Object.toJson(conditionArray),
            })


            var form = $("<form>");//定义一个form表单
            form.attr("style", "display:none");
            form.attr("target", "");
            form.attr("method", "post");//请求类型
            form.attr("action", url);//请求地址
            $("body").append(form);//将表单放置在web中

            form.submit();//表单提交
            $(form).remove();
        },
        'preview': function (item, index) {

            //  判断列表中有无可预览的附件

            // var showItems = getShowItems(List.getData().body.items);
            var showItems = getShowItems(List.getSelectedItems());

            if (showItems.length === 0) {
                SMS.Tips.error('列表没有可预览的附件!', 1000);
                return;
            }

            // 附件预览
            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: sysName + '-附件预览-',
                    width: 900,
                    height: 550,
                    url: $.Url.setQueryString('html/supplier/attachmentView/index.html'),
                    data: {
                        'showItems': showItems,
                        'classId': classId,
                    },
                    button: [],
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
        },

    });

    /**
     * 获取有可显示附件的记录
     * @param items
     * @return {Array}
     */
    function getShowItems(items) {

        var showItems = [];
        var url

        for (var i = 0; i < items.length; i++) {
            for (var j = 0; j < items[i].data.entry["1"].length; j++) {
                url = items[i].data.entry["1"][j].url;
                if (url && ( $.String.endsWith(url, '.pdf', true) || $.String.endsWith(url, '.jpg', true) || $.String.endsWith(url, '.jpeg', true) || $.String.endsWith(url, '.png', true) || $.String.endsWith(url, '.gif', true)  )) {
                    showItems.push(items[i].data);
                    break;
                }
            }
        }

        return showItems;
    }

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

        var conditions = getCondition();

        List.render({
            classId: classId,
            pageNo: 1,
            pageSize: defaults.pageSize,
            conditions: conditions,
            multiSelect: defaults.multiSelect
        }, function (total, pageSize) {

            Pager.render({
                size: pageSize,
                total: total,
                change: function (no) {
                    List.render({
                        classId: classId,
                        pageNo: no,
                        pageSize: defaults.pageSize,
                        conditions: conditions,
                        multiSelect: defaults.multiSelect
                    });
                }
            });
        });
    }

    List.on({
        'row.item.click': function (data, event) {
            // 子表行操作
            console.log(data);

            var type = data.operate;

            if (type == 0) {
                // 预览
                var api = new API("file/preview");
                var url = api.getUrl();
                url = $.Url.addQueryString(url, {
                    classId: classId,
                    itemId: data.itemId,
                    fileName: data.fileName,
                })

                data.control.href = url;
                data.control.click();
                data.control.href = "#";
            }
            else if (type == 1) {
                // 下载子表行附件

                var api = new API("file/download");
                var url = api.getUrl();
                url = $.Url.addQueryString(url, {
                    classId: classId,
                    itemId: data.itemId,
                    fileName: data.fileName,
                })

                data.control.href = url;
                data.control.click();
                data.control.href = "#";

            } else if (type == 2) {

                //删除子表行
                var primaryKey = data.body.items[data.col].value[data.entryRow].primaryKey;

                var obj = {};
                obj[primaryKey] = data.body.items[data.col].value[data.entryRow].primaryValue;

                var para = {
                    classId: classId,
                    itemId: data.body.primaryValue,
                    data: {
                        entry: {
                            "1": [
                                {
                                    flag: 0,// 删除改行
                                    data: obj
                                }
                            ],
                        }
                    }
                };

                List.edit(para, function () {
                    refresh();
                });
            }

        },
        'renderDone': function () {
            if (classId == 3010 || classId == 3020) {
                List.checkExpired(classId);
            }
        }
    });
    Tree.on({

        onClick: function (data) {

            if (data.id === 0) {
                // 根节点data.id==0,不过滤
                data = null;
            }
            treeFilter = data;
            refresh();
        }
    });

    Tree.render(treeClassId);

})();