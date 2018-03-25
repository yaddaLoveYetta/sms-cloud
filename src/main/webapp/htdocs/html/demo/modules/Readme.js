


define('Readme', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var HLJS = window.hljs;


    var header = document.getElementById('div-readme-header');
    var content = document.getElementById('div-readme-content');


    function render(name, readme) {

        

        SMS.Template.fill(header, {
            name: name
        });

        if (!readme) {
            return;
        }

        var Seajs = SMS.require('Seajs');

        Seajs.use('marked', function (marked) {

            var html = marked(readme);
            content.innerHTML = html;

            $(content).find('code[data-language]').each(function () {
                var code = this;
                var type = code.getAttribute('data-language');

                var html = code.innerHTML;

                html = HLJS.highlight(type, html).value; //高亮代码
                $(code).addClass('hljs').html(html);

            });

        });
    }


    return {

        render: render
    };

});
