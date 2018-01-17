

/**
* Code 模块
* 
*/
define('Response/Code', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var HLJS = window.hljs;

    var code = document.getElementById('code-json');
    var pre = code.parentNode;


    function render(json) {

        try{
            json = JSON.parse(json);
            json = JSON.stringify(json, null, 4);
        }
        catch(ex){
            code.innerHTML = '';
            return;
        }
        

        var html = HLJS.highlight('json', json).value;
        code.innerHTML = html;

    }


    function show() {
        $(pre).show();
    }

    function hide() {
        $(pre).hide();
    }



    return {
        render: render,
        show: show,
        hide: hide,

    };

});






    