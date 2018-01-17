//登录模块
define('Login', function (require, module, exports) {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var WarnTip = require('WarnTip');

    var emitter = MiniQuery.Event.create();


    var btn = document.getElementById('btn-login');
    var txtUser = document.getElementById('txt-user');
    var txtPassword = document.getElementById('txt-password');


    function login(loginType) {

        if ($(btn).hasClass('disabled')) {
            return;
        }


        var user = txtUser.value;
        if (!user) {
            WarnTip.show('请输入用户名');
            return false;
        }

        var password = txtPassword.value;


        $(btn).addClass('disabled').html('登录中...');


        SMS.Login.login({
            user: user,
            password: password,
            type: loginType,
        }, function (user, data, json) { //成功

            location.href = 'master.html';

        }, function (code, msg, json) { //失败
            reset();
            WarnTip.show(msg);

        }, function () { //错误

            reset();
            WarnTip.show('网络错误，请稍候再试');
        });


    }


    function reset() {
        $(btn).removeClass('disabled')
            .html('立即登录');
    }

    function init() {

        var user = SMS.Login.getLast() || {};
        user = user.name || '';

        if (user) {
            txtUser.value = user;
            txtPassword.focus();
        }
        else {
            txtUser.focus();
        }

        $(btn).on('click', function (event) {

            event.stopPropagation();
            emitter.fire('login');

            // var isOK = login();
            // if (isOK === false) {
            //     event.stopPropagation();
            // }
        });
    }

    return {
        init: init,
        login: login,
        on: emitter.on.bind(emitter),
    };
});


