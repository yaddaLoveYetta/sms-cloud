

/**
* 菜单组模块
* 
*/
define('Response', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    
    var Lines = require('/Lines');
    var Code = require('/Code');
    var Source = require('/Source');


    var hasBind = false;
    var formatted = true;

    var data = '';


    function render(text) {

        data = text;
      
        Lines.render(data, formatted);
        Code.render(data);
        Source.render(data);

        bindEvents();
    }

    function clear() {
        render('');
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $('#btn-switch').on('click', function (event) {

            var btn = this;

            if (formatted) { //当前已经是格式化的
                Lines.render(data); //重新计算行号
                Code.hide();
                Source.show();
            }
            else {
                Code.show();
                Source.hide();
            }

            formatted = !formatted;

        });

        hasBind = true;

    }

 



    return {
        render: render,
        clear: clear,

    };

});






    