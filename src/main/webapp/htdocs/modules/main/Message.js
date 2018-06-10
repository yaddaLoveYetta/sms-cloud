/**
 * @Title: 信息通知模块
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/23 17:57
 */

define('Message', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var emitter = MiniQuery.Event.create();
    var Iframe = SMS.require('Iframe');
    var API = SMS.require('API');
    var MessageBox = SMS.require('MessageBox');

    var li = document.getElementById('li-message');

    var samples = $.String.getTemplates(li.innerHTML, [
        {
            name: 'message',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'message.count',
            begin: '#--message.count.begin--#',
            end: '#--message.count.end--#',
            outer: '{count}'
        },
        {
            name: 'message.count.tip',
            begin: '#--message.count.tip.begin--#',
            end: '#--message.count.tip.end--#',
            outer: '{tip}'
        },
        {
            name: 'message.item',
            begin: '#--message.item.begin--#',
            end: '#--message.item.end--#',
            outer: '{item}'
        }
    ]);

    var user = SMS.Login.get();

    // 标识事件绑定
    var hasBind = false;

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    function load(fn) {

        var api = new API('user/getMessage');

        api.get({
            type: 0
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                MessageBox.show(s, '金蝶提示', true);
            },
            'error': function () {
                MessageBox.show('网络繁忙，请重试!', '金蝶提示', true);
            }
        });

    }

    function render() {

        load(function (data) {

            if (data.list.length > 10) {
                // 最多显示10条消息
                data.list = data.list.slice(0, 10);
            }

            li.innerHTML = $.String.format(samples["message"], {

                count: $.String.format(samples["message.count"], {
                    count: data.count
                }),
                tip: $.String.format(samples["message.count.tip"], {
                    count: data.count
                }),
                item: $.Array.keep(data.list, function (messageItem, index) {
                    return $.String.format(samples["message.item"], {
                        index: index,
                        topic: messageItem.topic,
                        date: messageItem.date
                    });
                }).join("")

            });

            bindEvents();

        });

    }


    function bindEvents() {

        if (hasBind) {
            return;
        }

        $('#message-center').on('click', function () {
            Iframe.open({
                id: 'view-message-center',
                name: '消息中心',
                url: './html/message/index.html'
            });

        });

        hasBind = true;
    }

    function changeMsgCount() {
        // 简单粗暴，直接重新load
        render()
    }


    return {
        render: render,
        changeMsgCount: changeMsgCount,
        on: emitter.on.bind(emitter)
    }

});




