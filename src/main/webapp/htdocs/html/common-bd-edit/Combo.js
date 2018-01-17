
/**
* ComboBox模块
* 
*/
define('Combo', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    var combo;
    var cfg;

    function render(config, classID, comboxKey) {
        searchItems(config, classID, comboxKey, loadComboData);
    }

    function getCombo() {
        return combo;
    }

    function loadComboData(config, data) {
        if (!data || !data['items']) {
            return;
        }

        initCombo(config, data['items']);
    }

    /*
    config.comboxClassName
    config.fnFormat(data)
    config.valueField
    config.fnOnChange(data)
    config.fnOnListClick()
    */
    function initCombo(config, data) {
        cfg = config;
        combo = $('.' + config.comboClassName).combo({
            data: data,
            formatText: config.fnFormat,
            value: config.valueField,
            defaultSelected: -1,
            editable: true,
            maxListWidth: 500,
            cache: false,
            forceSelection: true,
            defaultFlag: false,
            maxFilter: 50,
            trigger: false,
            noDataText: config.noDataText ? config.noDataText : '无记录',
            listHeight: 182,
            listWrapCls: 'ui-droplist-wrap',
            callback: {
                onChange: config.fnOnChange,
                onListClick: function () {

                }
            },
        }).getCombo();
    }

    function searchItems(formClassID, keyName, fn) {

//        var api = new API('bos/baseitem');
//
//        api.get({
//            action: 'itemSearch ',
//            data: {
//                classID: formClassID,
//                fieldKey: keyName,
//                keyValue: '',
//                searhType: 0,
//                pageNo: 1,
//                pageSize: 200,
//            }
//        });
//
//        api.on({
//            'success': function (data, json) {
//                fn(data);
//            },
//
//            'fail': function (code, msg, json) {
//                YWTC.Tips.error(msg);
//            },
//
//            'error': function () {
//                YWTC.Tips.error('网络错误，请稍候再试');
//            }
//        });

    }

    //function searchItems(config, formClassID, keyName, fn) {

    //    var api = new API('bos/baseitem');

    //    api.get({
    //        action: 'itemSearch ',
    //        data: {
    //            classID: formClassID,
    //            fieldKey: keyName,
    //            keyValue: '',
    //            searhType: 0,
    //            pageNo: 1,
    //            pageSize: 200
    //        }
    //    });

    //    api.on({
    //        'success': function (data, json) {
    //            fn(config, data);
    //        },

    //        'fail': function (code, msg, json) {
    //            YWTC.Tips.error(msg);
    //        },

    //        'error': function () {
    //            YWTC.Tips.error('网络错误，请稍候再试');
    //        }
    //    });


    //}

    return {
        render: render,
        getCombo: getCombo
    }

});

