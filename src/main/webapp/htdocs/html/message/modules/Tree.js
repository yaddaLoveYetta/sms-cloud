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
            nodes: [
                {
                    text: "未处理",
                    type: 0,
                    icon: "iconfont icon-noread"
                },
                {
                    text: "已处理",
                    type: 1,
                    icon: "iconfont icon-yiduxiaoxi"
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
            treeView.toggleNodeSelected([ 0, { silent: true } ]);
        });
    }

    return {
        render: render,
        on: emitter.on.bind(emitter)
    }

});