/**
 * 填充自定义控件模块
 *
 */
define('SelectorList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var DataSelector = require('DataSelector');

    var selectors = {};

    var idTypeContainer = document.getElementById('idType');
    var config = {
        targetType: 1, //跳转方案
        classId: 1023,
        hasBreadcrumbs: true,
        container: idTypeContainer,
        title: '证件类别',
        defaults: {
            pageSize: 8
        }
    };
    var idTypeSelector = DataSelector.create(config);
    selectors['idType'] = idTypeSelector;

    var supplierContainer = document.getElementById('supplier');
     config = {
        targetType: 1, //跳转方案
        classId: 1005,
        hasBreadcrumbs: true,
        container: supplierContainer,
        title: '供应商',
        defaults: {
            pageSize: 8
        }
    };
    var supplierSelector = DataSelector.create(config);
    selectors['supplier'] = supplierSelector;


    var manufacturerContainer = document.getElementById('manufacturer');
     config = {
        targetType: 1, //跳转方案
        classId: 1020,
        hasBreadcrumbs: true,
        container: manufacturerContainer,
        title: '生产厂家',
        conditionF7Names: [{
            type: "fixedValue",
            filterKey: "type",
            filterValue: '18zNfHxC+0eIL6v1rpN0bFxCPHE=',
        }],
        defaults: {
            pageSize: 8
        }
    };
    var manufacturerSelector = DataSelector.create(config);
    selectors['manufacturer'] = manufacturerSelector;

    var agentContainer = document.getElementById('agent');
     config = {
        targetType: 1, //跳转方案
        classId: 1020,
        hasBreadcrumbs: true,
        container: agentContainer,
        title: '代理商',
        conditionF7Names: [{
            type: "fixedValue",
            filterKey: "type",
            filterValue: 'qdiWDEkcF0e6c5a0zztPfFxCPHE=',
        }],
        defaults: {
            pageSize: 8
        }
    };
    var agentSelector = DataSelector.create(config);
    selectors['agent'] = agentSelector;

    //设置 静态变量 用于联动操作
    DataSelector.DataSelectors = selectors;

    return selectors;
});

