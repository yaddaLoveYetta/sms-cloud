/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/25 10:25
 */

define('List', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var emitter = MiniQuery.Event.create();
    var API = SMS.require('API');

    // 列表数据
    var list = {};

    var tbody = document.getElementById("message-list");
    var samples = $.String.getTemplates(tbody.innerHTML, [
        {
            name: 'message',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'message.item',
            begin: '#--message.item.begin--#',
            end: '#--message.item.end--#',
            outer: '{item}'
        }
    ]);

    function load(type, fn) {

        SMS.Tips.loading({
            text: '数据加载中，请稍候...',
            delay: 200
        });

        var api = new API('user/getMessage');


        api.get({
            'status': type
        }).on({
            'success': function (data, json) {
                SMS.Tips.success("数据加载成功!", 1000);
                fn && fn(data, json);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    function render(type) {

        load(type, function (data) {

            list = data.list;

            // 填充列表
            tbody.innerHTML = $.String.format(samples["message"], {

                item: $.Array.keep(list, function (item, index) {
                    return $.String.format(samples["message.item"], {
                        index: index,
                        topic: item.topic || '',
                        sender: item.sender,
                        date: item.date,
                        status: item.status
                    });
                }).join("")

            });


            bindEvents();

        });
    }

    function bindEvents() {

    }

    return {
        render: render
    }

});