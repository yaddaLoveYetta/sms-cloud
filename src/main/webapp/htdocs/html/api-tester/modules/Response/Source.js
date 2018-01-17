

/**
* Source 模块
* 
*/
define('Response/Source', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var HLJS = window.hljs;

    var txt = document.getElementById('txt-json');

    //根据文本内容计算需要的高度。
    function getHeight(code, delta) {
        var lines = code.split(/\r\n|\n|\r/);
        var count = lines.length;

        if (count == 1) {
            return '100%';
        }

        return (count * 20) + 'px';
    }


    function render(text) {
       
        txt.value = text;

        txt.style.height = getHeight(text);
    }


    function show() {
        $(txt).show();
    }

    function hide() {
        $(txt).hide();
    }



    return {
        render: render,
        show: show,
        hide: hide,

    };

});






    