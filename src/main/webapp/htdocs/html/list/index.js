/**
 * 单据列表页面控制器
 * @author : yadda(silenceisok@163.com)
 * @date : 2018/3/09 11:25
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var API = SMS.require('API');
    var MessageBox = SMS.require('MessageBox');
    var BL = SMS.require('ButtonList');

    var List = require('List');
    var Pager = require('Pager');
    var Search = require('Search');
    var FormAction = require('FormAction');
    // 菜单按钮
    var ButtonList;
    // var CascadeNavigator = require('CascadeNavigator');
    // var ClassMapping = require('ClassMapping');
    // 业务类别
    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');

    // 界面设置的过滤条件
    var conditions = [];

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    //默认配置
    var defaults = {
        pageSize: 10,
        sizes: [10, 20, 30],
        typeId: '',
        pageNo: 1,
        hasBreadcrumbs: true,
        multiSelect: true
    };

    var primaryKey;

    var dialog = Iframe.getDialog();

    if (dialog) {

        var data = dialog.getData();
        defaults = $.Object.extend(defaults, data);

        if (data.conditions) {
            // dialog调用时候传递的查询条件
            $.Object.extend(conditions, data.conditions);
        }

        dialog.on({
            close: function () {
                //window.top && window.top.$("#div-tips").hide();
                getData();
            }
        });
    }

    function getData() {

        var item = (List.getSelectedItems()[0] && List.getSelectedItems()[0].data) ? List.getSelectedItems()[0].data : {};

        if (item) {
            dialog.setData([{
                'ID': item[primaryKey],
                'name': item.name,
                'number': item.number,
                'all': item // 保留一份完整数据
            }]);
        }
    }

    // 必须指明获取功能按钮的场景 0:查看(列表)1:(新增)2:(编辑)
    FormAction.create({'classId': classId, 'type': 0}, function (config) {

        if (dialog) {
            // 对话框中不要工具栏
            config.items = [];
        }

        ButtonList = new BL(config);

        // 总事件，最后触发
        ButtonList.on('click', function (item, index) {
            //console.dir(item);
        });

        ButtonList.render();

        //支持二级事件，二级事件对应 item 中的 name
        ButtonList.on('click', {
            // 查看详情
            'view': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                var metaData = List.getMetaData();
                var url = 'html/bill/index.html';
                var name = metaData.formClass.name || '';

                if (!url) {
                    // 没有配置编辑页面或不需要编辑功能
                    return;
                }

                Iframe.open({
                    id: classId + '-view-' + list[0].primaryValue,
                    name: '查看详情-' + name,
                    url: url,
                    query: {
                        'id': list[0].primaryValue,
                        'classId': classId,
                        'operate': 0
                    }
                });
            },
            // 新增
            'add': function (item, index) {

                var metaData = List.getMetaData();
                var url = 'html/bill/index.html';
                var name = metaData.formClass.name || '';

                if (!url) {
                    // 没有配置编辑页面或不需要编辑功能
                    return;
                }

                Iframe.open({
                    id: classId + '-add',
                    name: '新增-' + name,
                    url: url,
                    query: {
                        'classId': classId,
                        'operate': 1
                    }
                });
            },
            // 修改
            'edit': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                var metaData = List.getMetaData();
                var url = 'html/bill/index.html';
                var name = metaData.formClass.name || '';

                if (!url) {
                    // 没有配置编辑页面或不需要编辑功能
                    return;
                }

                Iframe.open({
                    id: classId + '-edit-' + list[0].primaryValue,
                    name: '修改-' + name,
                    url: url,
                    query: {
                        'id': list[0].primaryValue,
                        'classId': classId,
                        'operate': 2
                    }
                });

            },
            // 删除
            'delete': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要删除的项', 1500);
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
            // 刷新
            'refresh': function (item, index) {
                refresh();
            },
            // 禁用
            'forbid': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要禁用的项', 1500);
                    return;
                }
                MessageBox.confirm('确定禁用选择的项?', function (result) {
                    if (result) {
                        List.forbid(classId, list, 1, function () {
                            refresh();
                        });
                    }
                });
            },
            // 启用
            'enable': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要启用的项', 1500);
                    return;
                }
                MessageBox.confirm('确定启用选择的项?', function (result) {
                    if (result) {
                        List.forbid(classId, list, 2, function () {
                            refresh();
                        });
                    }
                });

            },
            // 更多菜单
            'more': function (item, index) {
                ButtonList.toggle(index);
            },
            // 审核 TODO
            'check': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1500);
                    return;
                }
                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1500);
                    return;
                }
                List.check(classId, list, function () {
                    SMS.Tips.success('审核成功', 2000);
                    refresh();
                });


            },
            // 反审核 TODO
            'unCheck': function (item, index) {

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1500);
                    return;
                }
                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1500);
                    return;
                }

                List.unCheck(classId, list, function () {
                    SMS.Tips.success('反审核成功', 2000);
                    refresh();
                });
            },
            // 发送到医院
            'send': function (item, index) {
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
                        List.send(classId, list, function () {
                            SMS.Tips.success('发送成功', 2000);
                            refresh();
                        });
                    }
                });

            },
            // 导出
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

                //定义一个form表单
                var form = $("<form>");
                form.attr("style", "display:none");
                form.attr("target", "");
                //请求类型
                form.attr("method", "post");
                //请求地址
                form.attr("action", url);
                //将表单放置在DOM中
                $("body").append(form);
                //表单提交
                form.submit();
                $(form).remove();
            },
            // 导入
            'import': function (item, index) {

            }
        });
    });

    List.on({
        'click:number': function (data, event) {
            // 单据列表代码字段打开编辑页面
            if (dialog) {
                // 选择界面不触发
                return;
            }

            var url = ClassMapping.getPage(classId);
            var name = ClassMapping.getTabName(classId) || '';

            if (!url) {
                // 没有配置编辑页面或不需要编辑功能
                return;
            }

            var body = data.body;

            Iframe.open({
                id: classId + '-edit' + body.primaryValue,
                name: '修改-' + name,
                url: url,
                query: {
                    'id': body.primaryValue,
                    'classId': classId
                }
            });

        }
    });

    Search.on('doSearch', function (userConditions) {
        conditions = userConditions;
        refresh();
    });

    function refresh() {

        List.render({
            classId: classId,
            pageNo: 1,
            pageSize: defaults.pageSize,
            conditions: conditions,
            multiSelect: defaults.multiSelect
        }, function (total, pageSize) {
            // 渲染过滤条件控件
            Search.render(List.getFilterItems());
            primaryKey = List.getPrimaryKey();

            Pager.render({
                size: pageSize,
                sizes: defaults.sizes,
                total: total,
                change: function (no, pageSize) {
                    defaults.pageSize = pageSize;
                    List.render({
                        classId: classId,
                        pageNo: no,
                        pageSize: defaults.pageSize,
                        conditions: conditionAll,
                        multiSelect: defaults.multiSelect
                    });
                }
            });

        });
    }

    refresh();

})();