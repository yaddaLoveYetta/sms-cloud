/**
 * 表单模块
 * Created by yadda on 2017/5/12.
 */

define('Bill', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var API = require("/API");
    // 完整名称为 Bill/API

    // var Head = require('/Head');// 完整名称为 Bill/Head
    var Entry = require('/Entry'); // 完整名称为 Bill/Entry

    var template = {};
    var visibleTemplate = {};
    var billData = {};
    var headData = {};

    function load(config, fn) {
        SMS.Tips.loading("数据加载中...");
        API.get({
            classId: config.classId,
            id: config.id,
        }, function (data) {
            SMS.Tips.success("数据加载成功", 1500);
            console.log(data.template);
            console.log(data.visibleTemplate);
            console.log(data.data);
            fn && fn(data);
        });
    }

    function render(config) {
        load(config, function (data) {
            // 填充数据
            //Head.render(data.visibleTemplate, data.data.headData);
            Entry.render(data.template, data.data.entryData);
        });
    }

    function getHeadData() {
        return Head.getData();
    }

    function getEntryData() {
        return Entry.getData();
    }

    function getBillData() {
        return {
            head: getHeadData(),
            entry: getEntryData(),
        }
    }

    return {
        render: render,
        getBillData: getBillData,
        getHeadData: getHeadData,
        getEntryData: getEntryData
    }
});