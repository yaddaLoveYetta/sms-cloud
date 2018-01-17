/**
 * Created by yadda on 2017/5/6.
 */

define('ButtonListOption', function (require, module, exports) {

    var config = {'items': []};

    function get(classId) {

        if (classId == 1001) { // 用户
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }, {
                    text: '导出',
                    name: 'export',
                    icon: '../../../css/main/img/download.png',
                }
                ]
            };
        } else if (classId == 1003) {
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }]
            };
        } else if (classId == 1005) {
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '过滤',
                    name: 'filter',
                    icon: '../../css/main/img/filter.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }, {
                    text: '审核',
                    name: 'check',
                    icon: '../../css/main/img/check.png',
                    items: [
                        {
                            text: '反审核',
                            name: 'unCheck',
                            icon: '../../css/main/img/uncheck.png',
                        }
                    ],
                }, {
                    text: '发送到医院',
                    name: 'send',
                    icon: '../../css/main/img/send.png',
                }]
            };
        } else if (classId == 1007) {
            //供应商证件类别
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }, {
                    text: '审核',
                    name: 'check',
                    icon: '../../css/main/img/check.png',
                    items: [
                        {
                            text: '反审核',
                            name: 'unCheck',
                            icon: '../../css/main/img/uncheck.png',
                        }
                    ],
                }, {
                    text: '发送到医院',
                    name: 'send',
                    icon: '../../css/main/img/send.png',
                }]
            };
        } else if (classId == 1023) {
            //物料证件类型
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }, {
                    text: '审核',
                    name: 'check',
                    icon: '../../css/main/img/check.png',
                    items: [
                        {
                            text: '反审核',
                            name: 'unCheck',
                            icon: '../../css/main/img/uncheck.png',
                        }
                    ],
                }, {
                    text: '发送到医院',
                    name: 'send',
                    icon: '../../css/main/img/send.png',
                }]
            };
        } else if (classId == 3030) {
            // 中标库
            config = {
                'items': [
                    {
                        text: '过滤',
                        name: 'filter',
                        icon: '../../css/main/img/filter.png'
                    }, {
                        text: '刷新',
                        name: 'refresh',
                        icon: '../../css/main/img/refresh.png'
                    }
                ]
            };
        } else if (classId == 3020) {
            // 中标库
            config = {
                'items': [
                    {
                        text: '过滤',
                        name: 'filter',
                        icon: '../../css/main/img/filter.png'
                    }, {
                        text: '刷新',
                        name: 'refresh',
                        icon: '../../css/main/img/refresh.png'
                    }
                ]
            };
        } else if (classId == 1020) {
            // 生产企业
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                    icon: '../../css/main/img/add.png'
                }, {
                    text: '修改',
                    name: 'edit',
                    icon: '../../css/main/img/edit.png'
                }, {
                    text: '删除',
                    name: 'delete',
                    icon: '../../css/main/img/delete.png'
                }, {
                    text: '刷新',
                    name: 'refresh',
                    icon: '../../css/main/img/refresh.png'
                }]
            };
        } else {

            config = {
                'items': []
            };

        }

        return config;
    }

    return {
        get: get,
    }
});
