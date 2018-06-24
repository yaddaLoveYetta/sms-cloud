/**
 * @Title: 注册模块
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/3/21 14:17
 */

define('Register', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var MD5 = SMS.require('MD5');
    var MessageBox = SMS.require('MessageBox');
    var Validate = require('Validate');

    var hasBind = false;

    var register = document.getElementById('register');

    var model = document.getElementById('registerModal');

    var registerWizard = document.getElementById('registerWizard');

    function render() {

        // Toolbar extra buttons
        var btnFinish = $('<button></button>').text('提交').css('display', 'none')
            .addClass('btn btn-info final-btn')
            .on('click', submit);

        // Smart Wizard 1
        $(registerWizard).smartWizard({
            selected: 0,  // Initial selected step, 0 = first step
            keyNavigation: true, // Enable/Disable keyboard navigation(left and right keys are used if enabled)
            autoAdjustHeight: true, // Automatically adjust content height
            cycleSteps: false, // Allows to cycle the navigation of steps
            backButtonSupport: true, // Enable the back button support
            useURLhash: true, // Enable selection of the step based on url hash
            lang: {  // Language variables
                next: '下一步',
                previous: '上一步'
            },
            toolbarSettings: {
                toolbarPosition: 'bottom', // none, top, bottom, both
                toolbarButtonPosition: 'right', // left, right
                showNextButton: true, // show/hide a Next button
                showPreviousButton: true, // show/hide a Previous button
                toolbarExtraButtons: [btnFinish]
            },
            anchorSettings: {
                anchorClickable: true, // Enable/Disable anchor navigation
                enableAllAnchors: false, // Activates all anchors clickable all times
                markDoneStep: true, // add done css
                enableAnchorOnDoneStep: true // Enable/Disable the done steps navigation
            },
            contentURL: null, // content url, Enables Ajax content loading. can set as data data-content-url on anchor
            disabledSteps: [],    // Array Steps disabled
            errorSteps: [],    // Highlight step with errors
            theme: 'arrows',
            transitionEffect: 'fade', // Effect on navigation, none/slide/fade
            transitionSpeed: '400',
            showStepURLhash: false
        });


        bindEvents();

    }

    function bindEvents() {

        if (hasBind) {
            return;
        }
        $(register).on('click', function () {
            $(model).modal({backdrop: 'static', keyboard: false});
        });

        // Initialize the showStep event
        $(registerWizard).on("showStep", function (e, anchorObject, stepNumber, stepDirection, stepPosition) {
            //alert("You are on step "+stepNumber+" now");
            if (stepPosition === 'first') {
                $("#prev-btn").addClass('disabled');
                $(".final-btn").css('display', 'none');
            } else if (stepPosition === 'final') {
                $("#next-btn").addClass('disabled');
                $(".final-btn").css('display', 'block');
            } else {
                $("#prev-btn").removeClass('disabled');
                $("#next-btn").removeClass('disabled');
                $(".final-btn").css('display', 'none');
            }
        });

        // Initialize the leaveStep event
        $(registerWizard).on("leaveStep", function (e, anchorObject, stepNumber, stepDirection) {
        });

        // Initialize the beginReset event
        $(registerWizard).on("beginReset", function (e) {
            return confirm("Do you want to reset the wizard?");
        });

        // Initialize the endReset event
        $(registerWizard).on("endReset", function (e) {
            alert("endReset called");
        });

        // Initialize the themeChanged event
        $(registerWizard).on("themeChanged", function (e, theme) {
            alert("Theme changed. New theme name: " + theme);
        });

        //为模态对话框添加拖拽
        $(model).draggable({
            handle: ".modal-header"
        });
        //禁止模态对话框的半透明背景滚动
        $(model).css("overflow", "hidden");

        hasBind = true;
    }

    function validate(fn) {

        var validate = true;

        var data = {};

        var type = $('input[name=user_type]:checked').val();

        if (type > 0) {
            data.userType = type;
        }
        else {
            validate = false;
            MessageBox.show('请选择注册用户类别!', '金蝶提示', true);
            fn && fn(false);
            return;
        }


        // 手机号码校验
        var mobile = $('#mobile').val();
        if (!Validate.mobilePhone(mobile)) {
            validate = false;
            MessageBox.show('手机号码不正确!', '金蝶提示', true);
            fn && fn(false);
            return;
        } else {
            data['mobile'] = mobile;
        }

        // 企业统一社会信用代码校验-医疗机构登记号未校验
        var creditCode = $('#creditCode').val();
        if (type === 3 && !Validate.checkSocialCreditCode(creditCode)) {
            validate = false;
            MessageBox.show('企业统一信用代码不正确!', '金蝶提示', true);
            fn && fn(false);
            return;
        }
        if (type === 2) {
            // 医院用户注册提交的是医疗机构登记号
            data['registrationNo'] = creditCode;
        } else if (type === 3) {
            // 供应商注册提交企业统一信用代码
            data['creditCode'] = creditCode;
        }


        var validate_list = ['userName', 'password', 'name', 'orgName'];

        $.Array.each(validate_list, function (fieldName, index) {

            var $target = $('#' + fieldName);

            var v = $target.val();

            var msgElement = document.getElementById(fieldName + '-msg');

            if (!v) {

                validate = false;
                MessageBox.show($target.attr('placeholder'), '金蝶提示', true);
                return false;

            } else {

                if (fieldName === 'password') {
                    data[fieldName] = MD5.encrypt(v);
                } else {
                    data[fieldName] = v;
                }
            }

        });

        fn && fn(validate, data);

    }

    function submit() {

        $(".final-btn").attr("disabled", true).html('正在注册...');

        validate(function (falg, registerInfo) {

            if (!falg) {
                $(".final-btn").attr("disabled", false).html('立即注册');
                return;
            }

            var api = new API("user/register", {

                data: registerInfo,

                'success': function (data, json) {
                    MessageBox.show('注册成功,请用注册的账号登录系统！', '金蝶提示', true);
                    $(model).modal('hide');
                    $(".final-btn").attr("disabled", false).html('立即注册');
                },

                'fail': function (code, msg, json) {
                    MessageBox.show(msg, '金蝶提示', true);
                    $(".final-btn").attr("disabled", false).html('立即注册');
                },

                'error': function () {
                    MessageBox.show('网络错误，请稍候再试', '金蝶提示', true);
                    $(".final-btn").attr("disabled", false).html('立即注册');
                }

            });

            api.post();

        });

    }

    return {
        render: render
    }
});