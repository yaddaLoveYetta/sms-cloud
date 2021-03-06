/**
 * @Title: 供应商合作医院
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/25 10:25
 */

define('Tree', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var API = SMS.require("API");
    var emitter = MiniQuery.Event.create();

    var treeView;
    var div = document.getElementById("div-hospital");

    function load(params, fn) {

        SMS.Tips.loading({
            text: '数据加载中，请稍候...',
            delay: 200
        });

        var api = new API('template/getItems');

        api.get({
            classId: 1008
        });

        api.on('success', function (data, json) {
            SMS.Tips.success("数据加载成功", 500);
            fn && fn(data);

        });

        api.on('fail', function (code, msg, json) {
            var s = $.String.format('{0} (错误码: {1})', msg, code);
            SMS.Tips.error(s);

        });

        api.on('error', function () {
            SMS.Tips.error('网络繁忙，请稍候再试');

        });

    }

    function buildHospitalTree(data) {

        var hospitalNodes = [];

        $.Array.each(data, function (item, index) {

            hospitalNodes.push({
                text: item.hospital_DspName,
                icon: "iconfont icon-yiyuan1",
                nodeid: ++index,
                hospital: item.hospital,
                id: item.id
            });
        });

        // 所有
        return [{
            text: "所有医院",
            icon: "iconfont icon-yy",
            nodeid: "0",
            hospital: 0,
            id: 0,
            nodes: hospitalNodes
        }];
    }

    function render() {

        load({}, function (data) {

            var treeData = buildHospitalTree(data.list);

            SMS.use('TreeView', function (TreeView) {

                treeView = new TreeView({
                    selector: div,
                    config: {
                        data: treeData,
                        showCheckbox: false,
                        showIcon: true,
                        highlightSearchResults: false // 搜索结果不高亮显示
                    }
                });

                treeView.on('nodeSelected', function (event, node) {
                    emitter.fire("change", [node]);
                });

                // 初始化默认选中第一个医院
                treeView.selectNode(1, {silent: false});
            });
        });

    }

    function search(keyWorld, options) {
        return treeView.search(keyWorld, options);
    }

    function selectNode(node, options) {
        treeView.selectNode(node, options);
    }

    return {
        render: render,
        search: search,
        selectNode: selectNode,
        on: emitter.on.bind(emitter)
    }

});