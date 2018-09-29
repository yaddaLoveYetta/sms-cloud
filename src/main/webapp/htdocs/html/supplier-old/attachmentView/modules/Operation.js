/**
 * 数据操作 模块
 * Created by yadda on 2017/9/29.
 */

define('Operation', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');


    function post(name, params, fn, msg) {

        //延迟显示。 避免数据很快回来造成的只显示瞬间
        SMS.Tips.loading({
            text: msg || '操作中，请稍等...',
            delay: 500
        });


        var api = new API(name, {

            data: params,

            'success': function (data, json) { //success
                fn && fn(data, json);

            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg, 2000);

            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试', 2000);
            },

        });
        api.post();

    }

    function check(config, fn) {

        post('file/checkAttachment', config, fn);
    }

    function uncheck(config, fn) {

        post('file/checkAttachment', config, fn);
    }


    return {

        check: check,
        uncheck: uncheck,

    };

});

