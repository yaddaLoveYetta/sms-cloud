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
    var classId = Number(MiniQuery.Url.getQueryString(window.location.href, 'classId'));

    // 界面设置的过滤条件
    var conditions = [];
    // 被其他页面调用时传递的过滤条件，此过滤条件必须一致有效
    var dialogConditions = [];

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

    // 单据头主键key
    var primaryKey;

    var dialog = Iframe.getDialog();

    if (dialog) {

        var data = dialog.getData();
        defaults = $.Object.extend(defaults, data);

        if (data.conditions) {
            // dialog调用时候传递的查询条件
            dialogConditions = data.conditions || [];
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
                'id': item[primaryKey],
                /*                'name': item.name,
                                'number': item.number || '',*/
                'all': item // 保留一份完整数据
            }]);
        }
    }

    Iframe.on('dblclick', function (infos) {
        refresh();
    });

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
                var url = item.info.url;
                var name = metaData.formClass.name || '';

                if (!url) {
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
                var url = item.info.url;
                var name = metaData.formClass.name || '';

                if (!url) {
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
                //var url = require("UrlMapping")(classId);
                var url = item.info.url;
                var name = metaData.formClass.name || '';

                if (!url) {
                    // 没有配置
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
                        List.del(classId, list, item.info.apiUrl, function () {
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
                MessageBox.confirm('禁用后资料不能参与业务。\r\n确定禁用?', function (result) {
                    if (result) {
                        List.forbid(classId, list, 1, item.info.apiUrl, function () {
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
                        List.forbid(classId, list, 2, item.info.apiUrl, function () {
                            refresh();
                        });
                    }
                });

            },
            // 角色授权
            'authorize': function (item, index) {

                if (classId !== 1002) {
                    return;
                }
                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                SMS.use('Dialog', function (Dialog) {

                    var dialog = new Dialog({
                        //var url = $.Url.setQueryString('./html/list/index.html', 'classId', formClassID);
                        title: '角色-' + list[0].data.name + '-权限管理',
                        url: './html/role/index.html',
                        width: $(window).width() * 0.8,
                        height: $(window).height(),
                        button: [{
                            value: '取消授权',
                            className: 'sms-cancel-btn'
                        }, {
                            value: '确认授权',
                            className: 'sms-submit-btn',
                            callback: function () {
                                this.isSubmit = true;
                                /*        dialog.__dispatchEvent('saveRolePerMissions');
                                        return true;*/
                            }

                        }],
                        data: {
                            roleId: list[0].primaryValue
                        }
                    });

                    // 默认关闭行为为不提交
                    dialog.isSubmit = false;
                    dialog.showModal();

                    dialog.on({
                        remove: function () {
                            var data = dialog.getData();
                            if (dialog.isSubmit) {

                                // 保存
                                var api = new API('user/saveRolePerMissions');
                                var permitData = $.Object.toJson(data);

                                api.post({
                                    roleId: list[0].primaryValue,
                                    perMissions: permitData
                                });

                                api.on({
                                    'success': function (data, json) {
                                        SMS.Tips.success('保存成功！', 1500);
                                    },
                                    'fail': function (code, msg, json) {
                                        var s = $.String.format('{0} (错误码: {1})', msg, code);
                                        SMS.Tips.error(s, 1500);
                                    },
                                    'error': function () {
                                        SMS.Tips.error('网络繁忙，请稍候再试', 1500);
                                    }
                                });


                            }
                        }
                    });

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
                List.check(classId, list, item.info.apiUrl, function () {
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

                List.unCheck(classId, list, item.info.apiUrl, function () {
                    SMS.Tips.success('反审核成功', 2000);
                    refresh();
                });
            },
            // 发送到医院 TODO
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
                        List.send(classId, list, item.info.apiUrl, function () {
                            SMS.Tips.success('发送成功', 2000);
                            refresh();
                        });
                    }
                });

            },
            // 导出 TODO
            'export': function (item, index) {

                var api = new API(item.info.apiUrl);

                var url = api.getUrl();
                url = $.Url.addQueryString(url, {
                    classId: classId,
                    condition: conditions.concat(dialogConditions)
                });

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
            // 导入 TODO
            'import': function (item, index) {

            },
            //中标库-合作申请-合作供应商 查看关联供应商详细资料
            'view-supplier': function (item, index) {

                if (classId !== 1007 && classId !== 3001 && classId !== 1009) {
                    return;
                }

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                var url;
                var name;

                if (classId === 1007) {
                    // 中标库查看的供应商是HRP供应商
                    url = item.info.url;

                    name = list[0].data.supplier_DspName || '';

                    if (!url) {
                        // 没有配置编辑页面或不需要编辑功能
                        return;
                    }

                    Iframe.open({
                        id: classId + '-view-supplier-' + list[0].primaryValue,
                        name: '供应商资料-' + name,
                        url: url,
                        query: {
                            'id': list[0].data.supplier,
                            'classId': 1005,
                            'operate': 0
                        }
                    });
                } else if (classId === 3001 || classId === 1009) {
                    // 合作申请-合作供应商查看的供应商是供应商注册用户资料
                    url = item.info.url;

                    name = list[0].data.supplier_DspName || '';

                    if (!url) {
                        // 没有配置编辑页面或不需要编辑功能
                        return;
                    }

                    Iframe.open({
                        id: classId + '-view-supplier-' + list[0].primaryValue,
                        name: '供应商注册资料-' + name,
                        url: url,
                        query: {
                            'id': list[0].data.supplier,
                            'classId': 1013,
                            'operate': 0
                        }
                    });
                }


            },
            // 中标库查看关联合同资料
            'view-contract': function (item, index) {
                MessageBox.show('此功能正在开发中，敬请期待……', '金蝶提示！', true);
            },
            // 供应商合作医院-查看医院详细信息
            'view-hospital': function (item, index) {

                if (classId !== 3001 && classId !== 1008) {
                    return;
                }

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                var url = item.info.url;
                var name = list[0].data.hospital_DspName || '';

                if (!url) {
                    // 没有配置编辑页面或不需要编辑功能
                    return;
                }

                Iframe.open({
                    id: classId + '-view-hospital-' + list[0].primaryValue,
                    name: '医院资料-' + name,
                    url: url,
                    query: {
                        'id': list[0].data.hospital,
                        'classId': 1012,
                        'operate': 0
                    }
                });

            },
            // 供应商合作医院-添加合作医院
            'cooperation-apply': function (item, index) {

                if (classId !== 3001) {
                    return;
                }

                SMS.use('Dialog', function (Dialog) {

                    var dialog = new Dialog({
                        id: 'cooperation-apply',
                        title: '添加合作医院',
                        url: './html/bill-ext/cooperation-apply/index.html', // ./ 表示相对于网站根目录
                        width: 500,
                        height: 400,
                        button: [{
                            value: '关闭',
                            className: 'sms-cancel-btn'
                        }]
                    });

                    dialog.showModal();
                });
            },
            // 医院同意供应商提交的合作申请
            'agree': function (item, index) {

                if (classId !== 3001) {
                    return;
                }

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                if (list[0].data.status !== 1) {
                    SMS.Tips.error('该记录已操作，不可重复操作', 1000);
                    return;
                }

                SMS.use('Dialog', function (Dialog) {

                    var dialog = new Dialog({
                        id: 'cooperation-apply-agree',
                        title: '请选择关联本地供应商',
                        url: item.info.url,
                        width: 400,
                        height: 200,
                        button: [
                            {
                                value: '取消',
                                className: 'sms-cancel-btn'
                            },
                            {
                                value: '确定',
                                className: 'sms-submit-btn',
                                callback: function () {
                                    dialog.__dispatchEvent('get');
                                    var data = dialog.getData();
                                    if (data && data[0].all) {
                                        return true;
                                    } else {
                                        var err = {
                                            result: false,
                                            msg: '请指定关联HRP供应商!'
                                        };
                                        dialog.setData(err);
                                        dialog.__dispatchEvent('back');
                                        return false;
                                    }
                                }
                            }]
                    });

                    dialog.on({
                        remove: function () {

                            var data = dialog.getData();

                            if (data) {
                                agree(data[0].id, function () {
                                    SMS.Tips.success("操作成功!", 1000);
                                    refresh();
                                });
                            }
                        }
                    });

                    dialog.showModal();
                });

                function agree(hrpSupplier, fn) {

                    var url = item.info.apiUrl;

                    var api = new API('hospital/agreeApply');

                    api.post({
                        id: list[0].primaryValue,
                        hrpSupplier: hrpSupplier
                    });

                    api.on({
                        'success': function (data, json) {
                            fn && fn(data, json);
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
            },
            // 医院拒绝供应商提交的合作申请
            'disagree': function (item, index) {

                if (classId !== 3001) {
                    return;
                }

                var list = List.getSelectedItems();

                if (list.length === 0) {
                    SMS.Tips.error('请选择要操作的项', 1000);
                    return;
                }

                if (list.length > 1) {
                    SMS.Tips.error('一次只能对一条记录进行操作', 1000);
                    return;
                }

                if (list[0].data.status !== 1) {
                    SMS.Tips.error('该记录已操作，不可重复操作', 1000);
                    return;
                }

                MessageBox.confirm('确定不接受该供应商申请?', function (result) {
                    if (result) {
                        disagree();
                    }
                });

                function disagree() {

                    var url = item.info.apiUrl;

                    var api = new API('hospital/disagreeApply');

                    api.post({
                        id: list[0].primaryValue
                    });

                    api.on({
                        'success': function (data, json) {
                            SMS.Tips.success("操作成功!", 1000);
                            refresh();
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

            }

        });
    });

    Search.on({
        'doSearch': function (userConditions) {
            conditions = userConditions;
            refresh();
        },
        'initSelector': function (destClassId, lookUpClassId, key, field) {

            var config = {};

            if (classId === 1008 && key === 'hospital') {

                // 供应商-我的合作医院查询中医院过滤条件只能在跟自身有关系的医院中过滤
                config = {
                    conditionF7Names: [{
                        type: "selector",
                        target: 'supplier',
                        filterKey: "supplier"
                    }]
                };

            }

            if (destClassId === 3001 && key === 'status') {

                // 申请记录状态
                return {
                    conditionF7Names: [{
                        type: "fixedValue",
                        filterValue: 43,
                        filterKey: "type"
                    }]
                };

            }

            return config;
        }
    });

    List.on({
        'row.dblclick': function (data, event) {

            if (dialog) {
                // 双击选择资料返回

                // 设置选中该记录
                List.setItemSelected(data.tr, true);
                // 触发返回填充
                dialog.isSubmit = true;
                dialog.close().remove();
                return;
            }

            // 双击查看详情
            if (dialog) {
                // 选择界面不触发
                return;
            }

            var metaData = List.getMetaData();
            var name = metaData.formClass.name || '';

            // 触发查看事件
            var item = ButtonList.getItem('view');
            var url = item.info.url;

            if (!url) {
                // 没有配置编辑页面或不需要编辑功能
                return;
            }

            Iframe.open({
                id: classId + '-view-' + data.body.primaryValue,
                name: '查看详情-' + name,
                url: url,
                query: {
                    'id': data.body.primaryValue,
                    'classId': classId,
                    'operate': 0
                }
            });

        }
    });

    function refresh() {

        List.render({
            classId: classId,
            pageNo: 1,
            pageSize: defaults.pageSize,
            conditions: conditions.concat(dialogConditions),
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
                        conditions: conditions.concat(dialogConditions),
                        multiSelect: defaults.multiSelect
                    });
                }
            });

        });
    }

    refresh();

})();