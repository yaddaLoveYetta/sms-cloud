//控制器
;(function ($, MiniQuery, sms) {

    var SMS = require('SMS');
    var Login = require('Login');
    var WarnTip = require('WarnTip');
    var Register = require('Register');
    var API = SMS.require("API");

    var $vCode = $('#img-code');

    Login.init();

    $(document).on({
        'click': function () {
            WarnTip.hide();
        },
        'keydown': function (event) {

            if (event.keyCode === 13) {
                Login.login();
            }
        }
    });

    var contentWayPoint = function () {
        var i = 0;
        $('.animate-box').waypoint(function (direction) {

            if (direction === 'down' && !$(this.element).hasClass('animated-fast')) {

                i++;

                $(this.element).addClass('item-animate');
                setTimeout(function () {

                    $('body .animate-box.item-animate').each(function (k) {
                        var el = $(this);
                        setTimeout(function () {
                            var effect = el.data('animate-effect');
                            if (effect === 'fadeIn') {
                                el.addClass('fadeIn animated-fast');
                            } else if (effect === 'fadeInLeft') {
                                el.addClass('fadeInLeft animated-fast');
                            } else if (effect === 'fadeInRight') {
                                el.addClass('fadeInRight animated-fast');
                            } else {
                                el.addClass('fadeInUp animated-fast');
                            }

                            el.removeClass('item-animate');
                        }, k * 200, 'easeInOutExpo');
                    });

                }, 100);

            }

        }, {offset: '85%'});
    };

    $vCode.click(function () {

        var api = new API({
            name: 'user/getVerificationCode',
            data: {
                r: Math.random()
            }
        });

        $vCode.attr("src", api.getUrlWithGetParams());
    });

    contentWayPoint();

    $vCode.click();

    Register.render();

})(jQuery, MiniQuery, SMS);