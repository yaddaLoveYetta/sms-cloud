/**
 * 修改密码控制器
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');

    var dialog = Iframe.getDialog();

    dialog.on({

        get: function () {

            data.oldPwd.value = $('#txt-pwd-old')[0].value;
            data.newPwd.value = $('#txt-pwd-new')[0].value;
            data.confirmPwd.value = $('#txt-pwd-confirm')[0].value;

            data.oldPwd.valid = data.oldPwd.value !== '';
            data.newPwd.valid = data.newPwd.value !== '' && data.newPwd.value !== data.oldPwd.value;
            data.confirmPwd.valid = data.confirmPwd.value !== '' && data.confirmPwd.value === data.newPwd.value;

            $('#lbl-hint-old')[0].innerText = data.oldPwd.valid ? '' : '原密码不能为空';

            if (data.newPwd.value === '') {
                $('#lbl-hint-new')[0].innerText = '新密码不能为空';
            } else if (data.newPwd.value === data.oldPwd.value) {
                $('#lbl-hint-new')[0].innerText = '不能与旧密码相同';
            } else {
                $('#lbl-hint-new')[0].innerText = '';
            }

            if (data.confirmPwd.value === '') {
                $('#lbl-hint-confirm')[0].innerText = '确认密码不能为空';
            } else if (data.confirmPwd.value !== data.newPwd.value) {
                $('#lbl-hint-confirm')[0].innerText = '与新密码不一致';
            } else {
                $('#lbl-hint-confirm')[0].innerText = '';
            }

            $('#lbl-msg')[0].innerText = '';

            dialog.setData(data);
        },
        serverBack: function () {
            var data = dialog.getData();
            console.dir(data);
            if (data) {
                $('#lbl-msg').toggleClass('error-msg', !data.result);
                $('#lbl-msg')[0].innerText = data.msg;
            }
        }
    });

    var data = {
        oldPwd: {
            valid: false,
            value: $('#txt-pwd-old')[0].value
        },
        newPwd: {
            valid: false,
            value: $('#txt-pwd-new')[0].value
        },
        confirmPwd: {
            valid: false,
            value: $('#txt-pwd-confirm')[0].value
        }
    };

    function bindEvents() {
        $('#txt-pwd-old').on('blur', function () {
            if (this.value === '') {
                $('#lbl-hint-old')[0].innerText = '原密码不能为空';
            } else {
                $('#lbl-hint-old')[0].innerText = '';
            }

        });
        $('#txt-pwd-new').on('blur', function () {
            if (this.value === '') {
                $('#lbl-hint-new')[0].innerText = '新密码不能为空';
            } else {
                $('#lbl-hint-new')[0].innerText = '';
            }
            var confirmValue = $('#txt-pwd-confirm')[0].value;
            if (confirmValue !== '') {
                if (confirmValue !== this.value) {
                    $('#lbl-hint-confirm')[0].innerText = '与新密码不一致';
                } else {
                    $('#lbl-hint-confirm')[0].innerText = '';
                }
            }
        });
        $('#txt-pwd-confirm').on('blur', function () {
            if (this.value === '') {
                $('#lbl-hint-confirm')[0].innerText = '确认密码不能为空';
            } else {
                if ($('#txt-pwd-new')[0].value !== this.value) {
                    $('#lbl-hint-confirm')[0].innerText = '与新密码不一致';
                } else {
                    $('#lbl-hint-confirm')[0].innerText = '';
                }
            }
        });
    }

    dialog.setData(data);

    bindEvents();

})();
