/**
 * Created by yadda on 2017/5/8.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var List = require('List');
    var Pager = require('Pager');
    var bl = require('ButtonList');
    var Bill = require('Bill');
    var ButtonListOption = require('ButtonListOption');
    var MessageBox = SMS.require('MessageBox');
    var Iframe = SMS.require('Iframe');

    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');
    var txtSimpleSearch = document.getElementById('txt-simple-search');
    var conditions = {};


    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

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
            /*conditions['name'] = $(txtSimpleSearch).val();*/

            conditions['number'] = {
                'andOr': 'AND',
                'leftParenTheses': '(',
                'fieldKey': 'number',
                'logicOperator': 'like',
                'value': $(txtSimpleSearch).val(),
                'rightParenTheses': ')',
            };


            refresh();
        }
    });


    var dialog = Iframe.getDialog();

    var blConfig;
    if (dialog) {
        // 对话框中不要工具栏
        blConfig = {
            'items': []
        };
    } else {
        blConfig = ButtonListOption.get(classId);
    }

    var ButtonList = bl.create(blConfig);

    ButtonList.render();

    //支持二级事件，二级事件对应 item 中的 name
    ButtonList.on('click', {

        'tick': function () {

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要操作的项');
                return;
            }
            if (list.length > 1) {
                SMS.Tips.error('只能对一条记录进行操作');
                return;
            }

            if (list[0].data.tickType) {
                SMS.Tips.error('该订单医院已确认接单，不可修改接单数据');
                return;
            }

            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '接单信息',
                    width: 700,
                    height: 300,
                    url: $.Url.setQueryString('html/order-tick/index.html', {
                        'classId': classId,
                        'orderId': list[0].data.id,
                    }),
                    data: {},
                    button: [
                        {
                            value: '取消',
                            className: 'sms-cancel-btn',
                        },
                        {
                            value: '确定',
                            className: 'sms-submit-btn',
                            callback: function () {
                                dialog.__dispatchEvent('get');
                                var data = dialog.getData();
                                console.log(data);
                                tick(data, function (data) {
                                    SMS.Tips.success("接单成功", 1500);
                                    return true;
                                });

                                return false; // 可能不成功，默认不关闭对话框
                            }
                        }
                    ],
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
        'detail': function (item, index) {
            detailView();
        },
        'refresh': function (item, index) {
            refresh();
        },
        'deliver': function (item, index) {
            deliver();
        },
        'edit': function (item, index) {
            edit();
        },
        'delete': function (item, index) {

            if (parseInt(classId) !== 2020) {
                return;// 只有发货单有删除功能
            }

            var list = List.getSelectedItems();

            if (list.length == 0) {
                SMS.Tips.error('请选择要删除的项');
                return;
            }
            MessageBox.confirm('确定删除选择的项?', function (result) {
                if (result) {
                    List.del(classId, list, function () {
                        refresh();
                    });
                }
            });
        },
        'print': function (item, index) {
            print();
        },
        'send': function (item, index) {
            send();
        }
    });

    function detailView() {

        // 单据详情
        var list = List.getSelectedItems();

        if (list.length == 0) {
            SMS.Tips.error('请选择要操作的项');
            return;
        }
        if (list.length > 1) {
            SMS.Tips.error('只能对一条记录进行操作');
            return;
        }

        var name = '';
        switch (parseInt(classId)) {
            case 2019:
                name = '采购订单';
                break;
            case 2020:
                name = '发货单';
        }

        Iframe.open({
            id: classId + '-detail-' + list[0].data[List.getPrimaryKey()],
            name: '详情-' + name,
            url: 'html/bill/index.html',
            query: {
                'classId': classId,
                'id': list[0].data[List.getPrimaryKey()],
                'type': 0,
            }
        });
    }

    function edit() {

        // 编辑
        var list = List.getSelectedItems();

        if (list.length == 0) {
            SMS.Tips.error('请选择要操作的项', 1500);
            return;
        }

        if (list.length > 1) {
            SMS.Tips.error('只能对一条记录进行操作', 1500);
            return;
        }

        if (classId === 2020) {
            // 发货单已发送到医院-不能修改
            if (list[0].data['type'] === 1) {
                SMS.Tips.error('发货单已经发送到医院，不可修改！', 1500);
                return;
            }
        }

        var name = '';
        switch (parseInt(classId)) {
            case 2019:
                name = '采购订单';
                return; // 采购订单不支持编辑
            case 2020:
                name = '发货单';
        }

        Iframe.open({
            id: classId + '-edit-' + list[0].data[List.getPrimaryKey()],
            name: '编辑-' + name,
            url: 'html/bill/index.html',
            query: {
                'classId': classId,
                'id': list[0].data[List.getPrimaryKey()],
                'type': 2,
            }
        });
    }

    function deliver() {

        var done = true;
        var list = List.getSelectedItems();

        if (list.length === 0) {
            SMS.Tips.error('请选择要操作的项', 1500);
            return;
        }
        // 判断订单类别是否符合发货条件-代销与非代销订单不能合并发货
        var saleProxy;
        $.Array.each(list, function (item, index) {

            if (index === 0) {
                saleProxy = item.data.saleProxy;
                return true;
            }

            if (item.data.saleProxy !== saleProxy) {
                SMS.Tips.error('代销订单不能与非代销订单合并发货', 1500);
                done = false;
                return false;
            }

        });

        if (!done) {
            // 不满足发货条件
            return;
        }
        // 判断订单状态是否符合发货条件
        $.Array.each(list, function (item, index) {
            if (!item.data.confirmTick) {
                // 供应商没有接单
                SMS.Tips.error(item.data.number + ' 供应商未接单，不能发货', 1500);
                done = false;
                return false;
            }
            if (!item.data.tickType) {
                // HRP没有确认接单
                SMS.Tips.error(item.data.number + ' 医院未确认接单信息，不能发货', 1500);
                done = false;
                return false;
            }
        });
        if (!done) {
            // 不满足发货条件
            return;
        }
        // 判断订单数量是否符合发货条件(过滤掉已经发货完毕的订单-每条分录都发货完毕)
        list = $.Array.grep(list, function (item, index) {

            var v = $.Array.grep(item.data.entry[1], function (row, index) {

                return (row.invoiceQty || 0) < (row.confirmQty || 0)

            });

            return v.length > 0;

        });

        if (list.length === 0) {
            // 接单数量已经全部发货
            SMS.Tips.error('您选择的订单已发货完毕，不能再发货', 1500);
            return;
        }

        var items = ''; // 发货订单主键集合，多个逗号分隔

        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue );
            }
        }

        items = items.substr(1);

        // 获取发货单数据包
        Bill.load({
            'classId': 2020,
            'items': items,
            'type': 1, // 新增
        }, function (data) {

            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '生成发货单',
                    width: 1024,
                    height: 550,
                    url: $.Url.setQueryString('html/bill/index.html', {
                        'classId': 2020,
                        'type': 1,
                    }),
                    data: {'billData': data},
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
        });

        /*        SMS.use('Dialog', function (Dialog) {

         var dialog = new Dialog({
         title: '生成发货单',
         width: 1024,
         height: 550,
         url: $.Url.setQueryString('html/order-deliver/index.html', {
         'classId': 2020,
         'items': items,
         }),
         data: {},
         button: [],
         });

         //默认关闭行为为不提交
         dialog.isSubmit = false;

         dialog.showModal();

         dialog.on({
         remove: function () {
         var data = dialog.getData();
         if (data) {
         console.log(data.itemId);
         }
         refresh();
         }
         });

         });*/

    }

    function print() {

        var done = true;
        var list = List.getSelectedItems();

        if (list.length == 0) {
            SMS.Tips.error('请选择要操作的项', 1500);
            return;
        }

        var items = ''; // 发货订单主键集合，多个逗号分隔

        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue );
            }
        }

        items = items.substr(1);

        var api = new API('sendcargo/getCode');
        api.post({
            items: items,
        });

        api.on({
            'success': function (data, json) {
                showCode(data);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg, 1500);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试', 1500);
            }
        });

        function showCode(data) {

            if (data.length === 0) {
                SMS.Tips.error('发货单无个体码数据!', 1500);
                return;
            }
            // 内部函数
            SMS.use('Dialog', function (Dialog) {

                var dialog = new Dialog({
                    title: '个体码打印',
                    width: 700,
                    height: 450,
                    url: $.Url.setQueryString('html/code-print/index.html'),
                    data: {
                        code: data,
                    },
/*                    button: [
                        {
                            value: '取消',
                            className: 'sms-cancel-btn',
                        },
                        {
                            value: '确定',
                            className: 'sms-submit-btn',
                            callback: function () {
                                return true;
                            }
                        }
                    ],*/
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


    }

    //保存接单数据
    function tick(data, fn) {

        var api = new API('order/tick');
        api.post({
            id: data.id,
            entry: data.entry,
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });
    }

    function refresh(data) {

        if (data) {
            conditions['id'] = {
                'andOr': 'AND',
                'leftParenTheses': '(',
                'fieldKey': 'supplier',
                'logicOperator': '=',
                'value': data.id,
                'rightParenTheses': ')',
                'needConvert': false,
            };
        }

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

    function send() {
        // 发送到HRP
        if (classId != 2020) {
            //目前单据只有发货单可同步回HRP
            return;
        }

        var list = List.getSelectedItems();

        if (list.length == 0) {
            SMS.Tips.error('请选择要操作的项');
            return;
        }
        /*            if (list.length > 1) {
         SMS.Tips.error('一次只能对一条记录进行操作');
         return;
         }*/

        MessageBox.confirm('发送后不可撤回！确定要将该记录发送给医院?', function (result) {
            if (result) {
                List.send(classId, list, function () {
                    SMS.Tips.success('发送成功', 2000);
                    refresh();
                });
            }
        });
    }

    refresh();

})();