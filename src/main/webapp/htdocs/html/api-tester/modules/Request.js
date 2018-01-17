

/**
* 菜单组模块
* 
*/
define('Request', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    
    var API = require('/API');
    
    
    var method = 'get';
    var disabled = false;
    


    function changeMethod(sw) {
        var type = sw ? 'get' : 'post';
        if (type == method) {
            return;
        }

        method = type;

        $('#p-query').slideToggle(!sw);
        $('#lbl-data').html(sw ? '查询参数：' : '表单数据：');
    }


    function bindEvents() {

        $('#rd-get').on('click', function (event) {
            var rd = this;
            changeMethod(rd.checked);
        });

        $('#rd-post').on('click', function (event) {
            var rd = this;
            changeMethod(!rd.checked);
        });

        API.on('done', function () {
            $('#btn-submit').removeClass('disabled').val('提交');
            disabled = false;
        });


        $('#btn-submit').on('click', function (event) {
            if (disabled) {
                return;
            }

            var btn = this;
            $(btn).addClass('disabled').val('请求中...');
            disabled = true;


            SMS.Tips.loading('请求中，请稍候...');

            var name = $('#txt-name').val();
            var data = $('#txt-data').val();

            if (method == 'get') {
                API.get(name, data);
            }
            else {
                var query = $('#txt-query').val();
                API.post(name, data, query);
            }

        });

    }

    function render() {

        bindEvents();
    }






    return {
        render: render,
        on: API.on,

    };

});






    