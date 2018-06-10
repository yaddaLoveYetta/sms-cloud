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
    // 事件绑定标识
    var hasBind = false;

    var tbody = document.getElementById("message-list");
    var samples = $.String.getTemplates(tbody.innerHTML, [
        {
            name: 'message',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'message.none',
            begin: '#--message.none.begin--#',
            end: '#--message.none.end--#',
            outer: '{none}'
        },
        {
            name: 'message.item',
            begin: '#--message.item.begin--#',
            end: '#--message.item.end--#',
            outer: '{item}'
        },
        {
            name: 'message.item.operate',
            begin: '#--message.item.operate.begin--#',
            end: '#--message.item.operate.end--#',
            outer: '{operate}'
        }
    ]);

    function load(config, fn) {

        SMS.Tips.loading({
            text: '数据加载中，请稍候...',
            delay: 100
        });

        var api = new API('user/getMessage');


        api.get({
            'type': config.type,
            'pageSize': config.pageSize || 10,
            'pageNo': config.pageNo || 1
        }).on({
            'success': function (data, json) {
                SMS.Tips.success("数据加载成功!", 500);
                var total = data.count;
                fn && fn(data, total);
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

    function render(config, fn) {

        load(config, function (data, total) {


            list = data.list;

            // 填充列表
            tbody.innerHTML = $.String.format(samples["message"], {

                none: (!list || list.length === 0) ? $.String.format(samples["message.none"], {}) : '',
                item: $.Array.keep(list, function (item, index) {
                    return $.String.format(samples["message.item"], {
                        index: index,
                        topic: item.topic || '',
                        sender: item.senderOrg.name,
                        date: item.date,
                        status: item.status,
                        operate: config.type === 0 ? $.String.format(samples["message.item.operate"], {
                            index: index
                        }) : ''
                    });
                }).join("")

            });


            bindEvents();

            fn && fn(total, config.pageSize);

        });
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $(tbody).delegate('>tr[data-index]', 'dblclick', function (event) {
            var index = $(this).data('index');
            emitter.fire('dblclick', [list[index]]);
        });
        $(tbody).delegate('button[type="button"]', 'click', function (event) {
            var index = $(this).data('index');

            setProcessed(list[index], function () {
                render(0);
            });

            emitter.fire('set', [list[index]]);
        });

    }

    function setProcessed(message, fn) {

        var api = new API('user/setMessageProcessed');

        api.post({
            id: message.id
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
            }
        });


    }

    return {
        render: render
    }

});