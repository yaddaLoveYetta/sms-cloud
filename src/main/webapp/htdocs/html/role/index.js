/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/2 15:06
 */

(function () {

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

    var view = $('#tree').treeview({
        data: getTree(),
        multiSelect: true,
        levels: 4,
        showCheckbox: true,
        showBorder: true,
        showIcon: false,
        checkedIcon: 'iconfont icon-chakan',
        uncheckedIcon: 'iconfont icon-jinyong',
        collapseIcon: 'iconfont icon-chakan',
        expandIcon: ' iconfont icon-icon-xinzeng',
        selectedColor: 'red',
        selectedBackColor: '#3071a9'
    });

    $('#tree').treeview('checkAll', {silent: true});

    $('#tree').on('nodeSelected', function (event, data) {
        console.log(data);
        console.log($('.list-group-item').find('span.indent').length);
    });
    $('#tree').on('nodeChecked', function (event, data) {
        console.log(data);
    });
    $('#tree').on('nodeUnchecked', function (event, data) {
        console.log(data);
    });
})();