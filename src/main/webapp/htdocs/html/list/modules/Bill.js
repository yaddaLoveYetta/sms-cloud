/**
 * 表单模块
 * Created by yadda on 2017/5/12.
 */

define('Bill', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");

    var API = require("/API");    // 完整名称为 Bill/API

    var busy = false; // 控制多次点击

    function load(config, fn) {
        SMS.Tips.loading("数据加载中...");
        API.get({
            classId: config.classId,
            items: config.items,
            type: config.type || 0,
        }, function (data) {
            SMS.Tips.success("数据加载成功", 1500);
            fn && fn(data);
        });
    }


    return {
        load: load,
    }
});