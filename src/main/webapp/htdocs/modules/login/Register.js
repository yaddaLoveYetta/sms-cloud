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

            switch (stepNumber) {
                case 0:
                    if ($('input[name=user_type]:checked').val() > 0) {
                        //转下一步
                        return true;
                    }
                    else {
                        SMS.Tips.error("请选择注册用户类别!", 1000);
                        return false;
                    }
                    break;

                case 1:

                    var validate = true;

                    var validate_list = ['userName', 'password', 'name', 'mobile'];

                    $.Array.keep(validate_list, function (fieldName, index) {

                        var v = $('#' + fieldName).val();

                        var msgElement = document.getElementById(fieldName + '-msg');

                        if (!v) {

                            validate = false;

                            if (!$(msgElement).hasClass('show')) {
                                $(msgElement).toggleClass('show');
                            }
                            $(msgElement).html('请输入用户名');

                        } else {

                            $(msgElement).html('');
                            if ($(msgElement).hasClass('show')) {
                                $(msgElement).toggleClass('show');
                            }
                        }

                    });

                    if (!validate && stepDirection === 'forward') {
                        return false;
                    }

                    break;
            }

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


        hasBind = true;
    }

    function validate(fn) {

        var validate = true;

        var data = {};

        if ($('input[name=user_type]:checked').val() > 0) {
            data.type = $('input[name=user_type]:checked').val();
        }
        else {
            validate = false;
            SMS.Tips.error("请选择注册用户类别!", 1000);
        }


        var validate_list = ['userName', 'password', 'name', 'mobile', 'businessLicense', 'taxId', 'address'];

        $.Array.keep(validate_list, function (fieldName, index) {

            var v = $('#' + fieldName).val();

            var msgElement = document.getElementById(fieldName + '-msg');

            if (!v) {

                validate = false;

                if (!$(msgElement).hasClass('show')) {
                    $(msgElement).toggleClass('show');
                }
                $(msgElement).html($(msgElement).attr('placeholder'));

            } else {

                data[fieldName] = v;

                $(msgElement).html('');
                if ($(msgElement).hasClass('show')) {
                    $(msgElement).toggleClass('show');
                }
            }

        });

        fn && fn(validate, data);

    }

    function submit() {

        validate(function (falg, registerInfo) {

            if (!validate) {
                return;
            }

            var api = new API("user/register", {

                data: registerInfo,

                'success': function (data, json) {
                    fn && fn(data, json);

                },

                'fail': function (code, msg, json) {
                    SMS.Tips.error(msg, 2000);

                },

                'error': function () {
                    SMS.Tips.error('网络错误，请稍候再试', 2000);
                }

            });

            api.post();

        });

    }

    return {
        render: render
    }
});