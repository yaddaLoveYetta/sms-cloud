

/**
* Lines 模块
* 
*/
define('Response/Lines', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var ul = document.getElementById('ul-lines');
    var list = [];

    

    function getData(code) {

        if (!code) {
            return [];
        }

        var lines = code.split(/\r\n|\n|\r/);
        var count = lines.length;

        return $.Array.keep(1, count + 1, function (item, index) {
            return {
                text: item
            };
        });

    }


    function render(code, formated) {
      
        if (formated) {
            try{
                var json = JSON.parse(code);
                code = JSON.stringify(json, null, 4);
            }
            catch (ex) {
                code = '';
            }
            
        }

        list = getData(code);
        SMS.Template.fill(ul, list);
    }



    return {
        render: render,

    };

});






    