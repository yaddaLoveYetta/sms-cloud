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
                }, {
                    text: '删除',
                    name: 'delete',
                }, {
                    text: '刷新',
                    name: 'refresh',
                },
                    {
                        text: '接单',
                        name: 'enable',
                        items: [{
                            text: '反接单',
                            name: 'disable'
                        }],
                    }
                ]
            };
        } else if (classId == 1003) {
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                }, {
                    text: '删除',
                    name: 'delete',
                }, {
                    text: '刷新',
                    name: 'refresh',
                }]
            };
        } else if (classId == 1005) {
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                }, {
                    text: '删除',
                    name: 'delete',
                }, {
                    text: '刷新',
                    name: 'refresh',
                }]
            };
        } else if (classId == 1007) {
            config = {
                'items': [{
                    text: '新增',
                    name: 'add',
                }, {
                    text: '删除',
                    name: 'delete',
                }, {
                    text: '刷新',
                    name: 'refresh',
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
