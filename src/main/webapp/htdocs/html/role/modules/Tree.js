define('Tree', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    var zTree = null;
    var oldPermit = [];

    var setting = {
        treeId: 'permissionTree',
        check: {
            enable: true,
            chkboxType: {"Y": 'ps', "N": 'ps'}
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId"
            }
        }, callback: {
            onCheck: zTreeOnCheck, // checkbox / radio 被勾选 或 取消勾选的事件回调函数
            onClick: function (e, treeId, treeNode, clickFlag) {
                zTree.checkNode(treeNode, !treeNode.checked, true);
                zTree.setting().callback.onCheck(null, zTree.setting().treeId, treeNode);
            }
        }
    };

    //节点选中事件
    function zTreeOnCheck(event, treeId, treeNode) {
        if (treeNode.isParent) {
            return;
        }
        var curAccessMask = treeNode.accessMask;
        var curAccessUse = treeNode.accessUse;
        var parentNode = treeNode.getParentNode();
        var currSiblingsNode = parentNode.children;

        if (treeNode.checked) {
            // 当前权限项选中则依赖权限项也必须选中 eg:新增权限依赖查看权限，则选中新增权限时查看权限也必须选中
            $.Array.each(currSiblingsNode, function (node, index) {
                var accessMask = node.accessMask;
                if ((curAccessUse & accessMask) === accessMask) {
                    zTree.checkNode(node, true, true);
                }
            });
        } else {
            // 当前权限项不选中则依赖此权限的权限项也必须不选中 eg:新增权限依赖查看权限，则不选中查看权限时新增权限也必须不选中
            $.Array.each(currSiblingsNode, function (node, index) {
                var accessUse = +node.accessUse;
                if ((curAccessMask | accessUse) === curAccessMask) {
                    zTree.checkNode(node, false, true);
                }
            });
        }
    }

    function render(roleId, fn) {

        getRolePermissions(roleId, function (data) {

            var treeData = [];

            $.Array.each(data, function (grp, index) {
                // 一级菜单
                var parCheck = false;

                if (grp.items) {

                    $.Array.each(grp.items, function (item, index) {
                        // 二级子系统
                        var twoCheck = false;
                        var twoId = item.id;
                        if (item.accessList) {

                            $.Array.each(item.accessList, function (acc, index) {
                                // 明细权限项
                                if (acc.enable) {
                                    //子节点选择，所属父节点也需要选中
                                    parCheck = true;
                                    twoCheck = true;
                                }
                                var accData = {
                                    //id: acc.accessMask,
                                    pId: twoId,
                                    name: acc.accessName,
                                    accessMask: acc.accessMask,
                                    accessUse: acc.accessUse,
                                    checked: acc.enable,
                                    open: true
                                };
                                treeData.push(accData);

                            });
                        }

                        treeData.push({
                            id: item.id,
                            formActionId: item.formActionId,
                            name: item.name,
                            pId: grp.id,
                            checked: twoCheck,
                            open: true
                        });
                    });
                }

                treeData.push({
                    id: grp.id,
                    name: grp.name,
                    pId: 0,
                    checked: parCheck,
                    open: true
                });
            });

            SMS.use('ZTree', function (ZTree) {
                zTree = new ZTree({
                    selector: '#tree',
                    config: setting,
                    data: treeData
                });
            });
        });
    }


    function savePermit(user, fn) {
        if (!user) {
            return;
        }

        var roleId = user.roleId;
        var type = user.type;
        var nodes = zTree.getCheckedNodes(true);
        var topNodes = $.Array.grep(nodes, function (item, index) {
            //从顶级节点遍历
            return item.level == 0;
        });
        var permitData = [];
        //循环顶级节点
        $.Array.each(topNodes, function (topNode, index) {
            console.log("-顶级节点-" + topNode.name);

            var accessMask = 0;
            if (topNode.children) {
                $.Array.each(topNode.children, function (tNode, index) { //二级节点
                    if (tNode.checked) {
                        accessMask = 0;
                        var pData = {};
                        pData.topClassId = topNode.id; //顶级节点Id
                        pData.subSysId = tNode.id;//二级节点Id
                        console.log("-2级节点-" + tNode.name);
                        if (tNode.children) {
                            $.Array.each(tNode.children, function (cNode, index) { //三级节点
                                if (cNode.checked) {
                                    accessMask = accessMask | cNode.accessMask;
                                    console.log("-3级节点-" + cNode.name);
                                }
                            });
                        }
                        pData.accessMask = accessMask;
                        permitData.push(pData);
                    }
                    //pData.FAccessMask = accessMask;
                    //permitData.push(pData);
                });
                //pData.FAccessMask = accessMask;
                //permitData.push(pData);
            }
        });
        //console.log(permitData);
        //保存权限成功后，重新保存当前权限
        setPermit(permitData, roleId, fn);
    }


    function setPermit(data, roleId, fn) {
        var api = new API('role/saveRolePerMissions');
        var postData = $.Object.toJson(data);

        api.post({
            roleId: roleId,
            data: postData
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
                SMS.Tips.success('保存成功！', 1500);
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


    /**
     * 获取角色权限
     * @param roleId 角色id
     * @param fn 回调
     */
    function getRolePermissions(roleId, fn) {

        var api = new API('user/getRolePermissions');
        api.get({
            roleId: roleId
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
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

    /**
     * 保存角色权限 saveRolePerMissions
     * @param roleId  角色id
     * @param perMissions 权限授权结果集
     * @param fn 回调
     */
    function getPermitData(roleId, fn) {

        var nodes = zTree.getCheckedNodes(true);

        var topNodes = $.Array.grep(nodes, function (item, index) {
            return item.level === 0;
        });

        var permitData = [];
        //循环顶级节点
        $.Array.each(topNodes, function (topNode, index) {

            if (topNode.children) {
                $.Array.each(topNode.children, function (tNode, index) {
                    //二级节点
                    if (tNode.checked) {
                        var accessMask = 0;
                        var pData = {};
                        pData.classId = tNode.formActionId;

                        if (tNode.children) {
                            $.Array.each(tNode.children, function (cNode, index) {
                                //三级节点
                                if (cNode.checked) {
                                    accessMask = accessMask | cNode.accessMask;
                                }
                            });
                        }
                        pData.accessMask = accessMask;
                        pData.roleId = roleId;
                        permitData.push(pData);
                    }
                });
            }
        });

        return permitData;

    }


    return {
        render: render,
        getPermitData: getPermitData
    }

});