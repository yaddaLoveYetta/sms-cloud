define('Tree', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');

    var zTree = null;
    var oldPermit = [];

    var setting = {
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
            onCheck: zTreeOnCheck // checkbox / radio 被勾选 或 取消勾选的事件回调函数
        }
    };

    //节点选中事件
    function zTreeOnCheck(event, treeId, treeNode) {
        if (treeNode.isParent) {
            return;
        }
        var currAcMsk = +treeNode.accessMask;
        var currFAccessUse = +treeNode.accessUse;
        var parentNode = treeNode.getParentNode();
        var currSiblingsNode = parentNode.children;
        if (treeNode.checked) {
            $.Array.each(currSiblingsNode, function (node, index) {
                var nodelAcMsk = +node.accessMask;
                if ((currFAccessUse & nodelAcMsk) == nodelAcMsk) {
                    zTree.checkNode(node, true, true);
                }
            });
        } else {
            $.Array.each(currSiblingsNode, function (node, index) {
                var nodeFAccessUse = +node.accessUse;
                if ((currAcMsk | nodeFAccessUse) == currAcMsk) {
                    zTree.checkNode(node, false, true);
                }
            });
        }
    };

    function render(userId, userType, fn) {

        getUserPermit(userId, userType, function (data) {

            var treeData = [];

            $.Array.each(data, function (grp, index) {
                // 一级菜单
                var parCheck = false;

                if (grp.subSys) {

                    $.Array.each(grp.subSys, function (item, index) {
                        // 二级子系统
                        var twoCheck = false;

                        if (item.accessTypes) {

                            $.Array.each(item.accessTypes, function (acc, index) {
                                // 明细权限项
                                if (acc.enable == 1) { //子节点选择，所属父节点也需要选中
                                    parCheck = true;
                                    twoCheck = true;
                                }
                                var accData = {
                                    id: acc.accessIndex + 1,
                                    name: acc.permissionName,
                                    pId: item.subSysId,
                                    accessMask: acc.accessMask,
                                    accessUse: acc.accessUse,
                                    checked: (acc.enable == 1),
                                    open: true
                                };
                                treeData.push(accData);

                            });
                        }
                        var itemData = {
                            id: item.subSysId,
                            name: item.subSysName,
                            pId: grp.topClassId,
                            checked: twoCheck,
                            open: true
                        };
                        treeData.push(itemData);
                    });
                }
                var group = {
                    id: grp.topClassId,
                    name: grp.topClassName,
                    pId: 0,
                    checked: parCheck,
                    open: true
                };
                treeData.push(group);
            });
            $.fn.zTree.init($("#permitTree"), setting, treeData);
            zTree = $.fn.zTree.getZTreeObj("permitTree");
            fn && fn(treeData);
        });
    };


    function savePermit(user, fn) {
        if (!user) {
            return;
        }

        var roleId = user.roleId;
        var type = user.type;
        var nodes = zTree.getCheckedNodes(true);
        var topNodes = $.Array.grep(nodes, function (item, index) {

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


    function getUserPermit(roleId, type, fn) {
        var api = new API('role/getRolePermissions');
        api.get({
            roleId: roleId,
            type: type
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
                SMS.Tips.success('保存成功！',1500);
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

    return {
        render: render,
        savePermit: savePermit
    }

});