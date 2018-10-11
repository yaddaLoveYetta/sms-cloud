/**
 * 填充自定义控件模块
 *
 */
define('Selector', function (require, module, exports) {

    var $ = require('$');
    var DataSelector = require('DataSelector');
    var selectors = {};


    function render() {

        // 医院需要的类别
        var typeConfig = {
            targetType: 1,
            classId: 1016,
            destClassId: 1019,
            fieldKey: 'type',
            hasBreadcrumbs: true,
            container: document.getElementById('type'),
            title: '医院需要的类别',
            defaults: {
                pageSize: 8
            }
        };

        selectors['type'] = DataSelector.create(typeConfig);

        // 合作医院
        var hospitalConfig = {
            targetType: 1, //跳转方案
            classId: 1008,
            destClassId: 1019,
            fieldKey: 'hospital',
            hasBreadcrumbs: true,
            container: document.getElementById('hospital'),
            title: '合作医院',
            dataFieldKey:  {
                'id': 'hospital', 'name': 'hospital_DspName', 'number': 'hospital_DspName'
            },
            defaults: {
                pageSize: 8
            }
        };

        selectors['hospital'] = DataSelector.create(hospitalConfig);

        // 供应商证件
        var qualificationConfig = {
            targetType: 1, //跳转方案
            classId: 1019,
            destClassId: 1019,
            fieldKey: 'qualification',
            hasBreadcrumbs: true,
            container: document.getElementById('qualification'),
            title: '证件',
            defaults: {
                pageSize: 8
            }
        };

        selectors['qualification'] = DataSelector.create(qualificationConfig);

        //设置 静态变量 用于联动操作
        DataSelector.DataSelectors = selectors;
        //改变事件捕获
        DataSelector.on({});
    }

    function set(key, selectorData) {

        var selector = selectors[key];
        selector.setData(selectorData);
    }

    function get(key) {
        var selector = selectors[key];
        return selector.getData();
    }

    function clearData(key) {
        var selector = selectors[key];
        return selector.clearData();
    }

    return {
        render: render,
        set: set,
        get: get,
        clearData: clearData
    }

});

