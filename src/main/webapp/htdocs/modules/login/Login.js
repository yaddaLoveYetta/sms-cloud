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
    var txtCode = document.getElementById('txt-code');


    function login() {

        if ($(btn).hasClass('disabled')) {
            return;
        }


        var user = txtUser.value;
        if (!user) {
            txtUser.focus();
            WarnTip.show('请输入用户名');
            return false;
        }

        var password = txtPassword.value;

        if (!password) {
            txtPassword.focus();
            WarnTip.show('请输入密码');
            return false;
        }

        var code = txtCode.value;

        $(btn).addClass('disabled').html('正在登录...');


        SMS.Login.login({
            userName: user,
            password: SMS.MD5.encrypt(password),
            code: code
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
            .html('登录');
    }

    function init() {

        var user = SMS.Login.getLast() || {};
        user = user.userName || '';

        if (user) {
            txtUser.value = user;
            txtPassword.focus();
        }
        else {
            txtUser.focus();
        }

        $(btn).on('click', function (event) {
            event.stopPropagation();
            login();
        });
    }

    return {
        init: init,
        login: login,
        on: emitter.on.bind(emitter),
    };
});


