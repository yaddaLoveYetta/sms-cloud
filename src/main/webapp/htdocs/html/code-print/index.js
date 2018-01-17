/**
 * Created by yadda on 2017/6/1.
 */
;(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var ButtonList = require('ButtonList');
    var Iframe = SMS.require('Iframe');

    var BarCode = require('BarCode');

    var code = [];// 条形码字符内容

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    var dialog = Iframe.getDialog();

    if (dialog) {

        var data = dialog.getData();

        code = data.code;

    } else {
        return;
    }

    // 支持二级事件，二级事件对应 item 中的 name
    ButtonList.on('click', {
        'optPrint': function () {
            $("#code").jqprint({
                debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）默认是true。。
                operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
            });
        },
        'optRefresh': function () {
            alert('optRefresh');
        }
    });

    ButtonList.render();
    BarCode.render(code);

})();