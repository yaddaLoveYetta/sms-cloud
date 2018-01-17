/**
 * Created by yadda on 2017/5/6.
 */

define('ButtonListOption', function (require, module, exports) {

    var config = {
        'items': []
    };

    function get(classId, type) {

        type = parseInt(type);
        classId = parseInt(classId);

        if (type === 0) {
            // 查看
            switch (classId) {
                case 2019:
                    //采购订单
                    break;
                case 2020:
                    //发货单
                    break;
            }
        } else if (type === 1) {
            // 新增
            switch (classId) {
                case 2019:
                    //采购订单
                    config = {
                        'items': [{
                            text: '保存',
                            name: 'optSave',
                            icon: '../../css/main/img/save.png'
                        }, {
                            text: '刷新',
                            name: 'optRefresh',
                            icon: '../../css/main/img/refresh.png'
                        }]
                    };
                    break;
                case 2020:
                    //发货单
                    config = {
                        'items': [{
                            text: '保存',
                            name: 'optSave',
                            icon: '../../css/main/img/save.png'
                        }]
                    };
                    break;
            }

        } else if (type === 2) {
            // 编辑

            switch (classId) {
                case 2019:
                    //采购订单
                    config = {
                        'items': [{
                            text: '保存',
                            name: 'optSave',
                            icon: '../../css/main/img/save.png'
                        }, {
                            text: '刷新',
                            name: 'optRefresh',
                            icon: '../../css/main/img/refresh.png'
                        }]
                    };
                    break;
                case 2020:
                    //发货单
                    config = {
                        'items': [{
                            text: '保存',
                            name: 'optSave',
                            icon: '../../css/main/img/save.png'
                        }, {
                            text: '刷新',
                            name: 'optRefresh',
                            icon: '../../css/main/img/refresh.png'
                        }]
                    };
                    break;
            }
        }


        return config;

    }

    return {
        get: get,
    }
});
