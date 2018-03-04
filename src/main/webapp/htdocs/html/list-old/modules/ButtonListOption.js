/**
 * Created by yadda on 2017/5/6.
 */

define('ButtonListOption', function (require, module, exports) {

    var config = {'items': []};

    function get(classId) {

        switch (parseInt(classId)) {

            case 2019:
                // 采购订单
                config = {
                    'items': [
                        {
                            text: '接单',
                            name: 'tick',
                            icon: '../../css/main/img/receive.png'
                        },
                        {
                            text: '刷新',
                            name: 'refresh',
                            icon: '../../css/main/img/refresh.png'
                        },
                        {
                            text: '详情',
                            name: 'detail',
                            icon: '../../css/main/img/detail.png'
                        },
                        {
                            text: '生成发货单',
                            name: 'deliver',
                            icon: '../../css/main/img/deliver.png'
                        }
                    ]
                };
                break;
            case 2020:
                // 发货单
                config = {
                    'items': [
                        {
                            text: '刷新',
                            name: 'refresh',
                            icon: '../../css/main/img/refresh.png'
                        },
                        {
                            text: '详情',
                            name: 'detail',
                            icon: '../../css/main/img/detail.png'
                        }, {
                            text: '修改',
                            name: 'edit',
                            icon: '../../css/main/img/edit.png'
                        }, {
                            text: '删除',
                            name: 'delete',
                            icon: '../../css/main/img/delete.png'
                        },
                        {
                            text: '发送到医院',
                            name: 'send',
                            icon: '../../css/main/img/send.png',
                        },
                        {
                            text: '个体码打印',
                            name: 'print',
                            icon: '../../css/main/img/print.png'
                        },
                    ]
                };
                break;
            default:
                config = {
                    'items': []
                };
                break;
        }

        return config;
    }

    return {
        get: get,
    }
});
