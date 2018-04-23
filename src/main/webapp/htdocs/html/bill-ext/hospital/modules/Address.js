/**
 * 地址
 */
define('Address', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var _default_ = {};
    var picker = null;

    function create(config) {

        _default_ = config;
        picker = SMS.CascadePicker.create({
            container: '#' + config.id,
            selectedValues: [config.province, config.city, config.district],
            defaultTexts: ['--请选择省份--', '--请选择城市--', '--请选择地区--'],
            hideEmpty: true,
            hideNone: false,
            data: '/data/address.array.simple.js',
            varname: '__AddressData__',
            fields: {
                value: 0,
                text: 1,
                child: 2
            },
            change: function (level, index) {
                console.log(level, index);
                var items = this.getSelectedItems();
                console.dir(items);
            }
        });

        return picker;

    }

    function getSelectedItems() {
        return picker ? picker.getSelectedItems() : [];
    }

    function showHeadValidInfo(flag) {

        var msgElement = document.getElementById(_default_.id + '-msg');

        if (!flag) {

            if ($(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            $(msgElement).html('');
        } else {

            if (!$(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            $(msgElement).html('地址信息不完整');
        }
    }

    function lock() {
        picker && picker.lock();
    }

    function unLock() {
        picker && picker.unLock();
    }


    return {
        create: create,
        getSelectedItems: getSelectedItems,
        showHeadValidInfo: showHeadValidInfo,
        lock: lock,
        unLock: unLock
    }
});

