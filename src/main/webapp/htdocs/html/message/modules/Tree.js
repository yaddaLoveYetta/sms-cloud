/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/25 10:25
 */

define('Tree', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var emitter = MiniQuery.Event.create();

    var treeView;
    var div = document.getElementById("div-message-type");
    var treeData = [
        {
            text: "消息类别",
            nodeid: "0",
            nodes: [
                {
                    text: "未处理",
                    type: 0,
                    icon: "iconfont icon-noread",
                    nodeid: "1"
                },
                {
                    text: "已处理",
                    type: 1,
                    icon: "iconfont icon-yiduxiaoxi",
                    nodeid: "2"
                }
            ]
        }
    ];

    function render() {

        SMS.use('TreeView', function (TreeView) {
            treeView = new TreeView({
                selector: div,
                config: {
                    data: treeData,
                    showCheckbox: false,
                    showIcon: true
                }
            });
            treeView.on('nodeSelected', function (event, data) {
                if (data.type !== undefined) {
                    emitter.fire("messageTypeChange", [data.type]);
                }
            });
            // 初始化默认选中未读消息
            treeView.selectNode(1, {silent: false});
        });
    }

    return {
        render: render,
        on: emitter.on.bind(emitter)
    }

});