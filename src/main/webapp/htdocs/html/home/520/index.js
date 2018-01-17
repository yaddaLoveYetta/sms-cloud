/**
 * Created by yadda on 2017/5/21.
 */

;(function () {

    var flake = $("<div></div>").css("position", "absolute").html("❄");

    $('.ig').eq(0).show().siblings('.ig').hide();

    // 轮播图
    var i = 0;
    setInterval(function () {

        $('.ig').eq(i).fadeIn(3000).siblings('.ig').fadeOut(3000);


        i++;
        //当图片是最后一张的后面时，设置图片为第一张
        if (i == 10) {
            i = 0;
        }

    }, 3000);

    setInterval(function () {

        var startLeft = Math.random() * $(document).width() + 'px';
        var endLeft = Math.random() * $(document).width() + 'px';

        var startTop = '-60px';
        var endTop = Math.random() * $(document).height() + 'px';

        endTop = endTop < $(document).height() / 2 ? $(document).height() - endTop : endTop; // 超过屏幕一半

        var stratOpacity = 0.6 + 0.4 * Math.random();
        var endOpacity = 0.1- Math.random();

        var startSize = 20 + 30 * Math.random();
        //var startSize=14 + 6 * Math.random();

        flake.clone().appendTo($('body')).css({
            'left': startLeft,
            'top': startTop,
            'opacity': stratOpacity,
            'font-size': startSize,
            'color': 'white'
        }).animate({
            'left': endLeft,
            'top': endTop,
            'opacity': endOpacity,
            'font-size': startSize,
            'color': 'white'
        }, 5000, function () {
            $(this).remove();
        });
    }, 80)
})();