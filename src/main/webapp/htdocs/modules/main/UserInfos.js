/**
 * 用户信息模块
 */
define('UserInfos', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var emitter = MiniQuery.Event.create();

    var MD5 = SMS.require('MD5');
    var API = SMS.require('API');
    var MessageBox = SMS.require('MessageBox');

    var panel = document.getElementById('li-user-infos');
    var user = SMS.Login.get();

    // 标识事件绑定
    var hasBind = false;

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    function render() {

        //批量填充
        SMS.Template.fill({
            '#li-user-infos': {
                icon: 'icon-unie64d' ,
                name: user.name
            }
        });

        bindEvents();

    }


    function bindEvents() {

        if (hasBind) {
            return;
        }

        $('#btn-edit-profile').on('click', function (e) {

            e.preventDefault();

            if (!user.isAdmin) {
                MessageBox.show('您不是管理员，只有管理员能维护该信息', '金蝶提示', true);
                return;
            }

            editProfile();

        });
        // 修改面
        $('#btn-change-password').on('click', function (e) {
            e.preventDefault();
            changePwd();
            emitter.fire('change-password-over', []);
        });

        $('#btn-logout').on('click', function (e) {

            e.preventDefault();

            var btn = this;

            MessageBox.confirm('确定退出系统?', function (result) {
                if (result) {

                    var values = emitter.fire('before-logout', []);
                    if (values && values[values.length - 1] === false) {
                        emitter.fire('cancel-logout', [item]); //触发事件
                        return; //取消注销
                    }

                    btn.innerHTML = '注销中...';
                    $(btn).addClass('disabled');

                    SMS.Login.logout(function (user, data, json) { //成功

                        location.href = 'login.html';

                    }, function (code, msg, json) { //失败

                        reset();
                        alert(msg);

                    }, function () { //错误
                        reset();
                        alert('网络错误，注销失败，请稍候再试');
                    });

                }
            });

        });

        hasBind = true;
    }

    function editProfile() {
        emitter.fire('edit-profile', []);
    }

    function changePwd() {

        var width = 400;
        var height = 130;
        SMS.use('Dialog', function (Dialog) {
            var dialog = new Dialog({
                id: 'changePassword',
                title: '修改密码',
                url: './change-password.html', // ./ 表示相对于网站根目录
                width: width,
                height: height,
                button: [{
                    value: '修改密码',
                    className: 'sms-submit-btn',
                    callback: function () {
                        dialog.__dispatchEvent('get');
                        var data = dialog.getData();
                        if (data.oldPwd.valid && data.newPwd.valid && data.confirmPwd.valid) {
                            var oldPwd = data.oldPwd.value;
                            var newPwd = data.newPwd.value;

                            dialog.find('[data-id="修改密码"]').attr('disabled', true);
                            doChangePwd({
                                oldPwd: oldPwd,
                                newPwd: newPwd,
                                userId: user.userId
                            }, function (data) {
                                dialog.find('[data-id="修改密码"]').attr('disabled', false);
                                dialog.setData(data);
                                dialog.__dispatchEvent('serverBack');
                            });
                        }
                        return false;
                    }
                }, {
                    value: '关闭',
                    className: 'sms-cancel-btn'
                }]
            });

            dialog.showModal();
        });
    }

    function doChangePwd(config, fn) {

        var api = new API('user/editPwd');

        api.post({
            'userId': config.userId,
            'oldPwd': MD5.encrypt(config.oldPwd),
            'newPwd': MD5.encrypt(config.newPwd)
        });

        api.on({
            'success': function (data, json) {
                fn && fn({
                    result: true,
                    msg: '修改密码成功！'
                });
            },

            'fail': function (code, msg, json) {
                fn && fn({
                    result: false,
                    msg: msg
                });
            },

            'error': function () {
                fn && fn({
                    result: false,
                    msg: '网络繁忙，请稍候再试'
                });
            }
        });
    }

    function reset() {
        $('#btn-logout').removeClass('disabled').html('注销');
    }

    return {
        render: render,
        on: emitter.on.bind(emitter)
    }

});




