/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/2 15:06
 */

(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var setting = {
        callback: {
            //onCheck: zTreeOnCheck // checkbox / radio 被勾选 或 取消勾选的事件回调函数
        }
    };

    function getTree() {

        var tree = [
            {
                text: "Parent 1",
                id: 0,
                nodes: [
                    {
                        text: "Child 1",
                        nodes: [
                            {
                                text: "Grandchild 1"
                            },
                            {
                                text: "Grandchild 2"
                            },
                            {
                                text: "Grandchild 3"
                            },
                            {
                                text: "Grandchild 4"
                            }
                        ]
                    },
                    {
                        text: "Child 2",
                        nodes: [
                            {
                                text: "Grandchild 5"
                            },
                            {
                                text: "Grandchild 6"
                            },
                            {
                                text: "Grandchild 7"
                            },
                            {
                                text: "Grandchild 8"
                            }
                        ]
                    }
                ]
            },
            {
                text: "Parent 2",
                nodes: [
                    {
                        text: "Child 1",
                        nodes: [
                            {
                                text: "Grandchild 1"
                            },
                            {
                                text: "Grandchild 2"
                            },
                            {
                                text: "Grandchild 3"
                            },
                            {
                                text: "Grandchild 4"
                            }
                        ]
                    },
                    {
                        text: "Child 2"
                    }
                ]
            },
            {
                text: "Parent 3",
                nodes: [
                    {
                        text: "Child 1",
                        nodes: [
                            {
                                text: "Grandchild 1"
                            },
                            {
                                text: "Grandchild 2"
                            }
                        ]
                    },
                    {
                        text: "Child 2"
                    }
                ]
            },
            {
                text: "Parent 4"
            },
            {
                text: "Parent 5"
            }
        ];
        return tree;
    }

    function getZTree() {

        return [
            {
                id: 1,
                name: "1",
                pId: 0,
                checked: false,
                open: true
            },
            {
                id: 2,
                name: "101",
                pId: 1,
                checked: true,
                open: true
            },
            {
                id: 3,
                name: "10101",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 4,
                name: "10102",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 5,
                name: "102",
                pId: 1,
                checked: true,
                open: true
            }
            ,
            {
                id: 6,
                name: "103",
                pId: 1,
                checked: true,
                open: true
            },
            {
                id: 7,
                name: "10103",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 8,
                name: "10104",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 9,
                name: "10105",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 10,
                name: "10106",
                pId: 2,
                checked: true,
                open: true
            },
            {
                id: 11,
                name: "10107",
                pId: 2,
                checked: true,
                open: true
            }

        ];

    }

    /*SMS.use('TreeView', function (TreeView) {

        var treeView = new TreeView({
            selector: '#tree',
            data: getTree()
        });

        treeView.on('nodeSelected', function (event, data) {
            console.log(data);
            console.log($('.list-group-item').find('span.indent').length);
        });
        treeView.on('nodeChecked', function (event, data) {
            console.log(data);
        });
        treeView.on('nodeUnchecked', function (event, data) {
            console.log(data);
        });

    });*/

    //$.fn.zTree.init($("#tree"), setting, getZTree());
    SMS.use('ZTree', function (ZTree) {

        var zTree = new ZTree({
            selector: '#tree',
            //config: setting,
            data: getZTree()
        });

        /*        treeView.on('nodeSelected', function (event, data) {
                    console.log(data);
                    console.log($('.list-group-item').find('span.indent').length);
                });
                treeView.on('nodeChecked', function (event, data) {
                    console.log(data);
                });
                treeView.on('nodeUnchecked', function (event, data) {
                    console.log(data);
                });*/

    });

})();