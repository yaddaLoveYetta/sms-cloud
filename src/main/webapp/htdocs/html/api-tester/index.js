


; (function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


    var Response = require('Response');

    //Response.render('{"code":200,"msg":"","data":{"userID":2,"number":"manager","name":"管理员"}}');


    var Request = require('Request');
    Request.render();


    Request.on({
        'success': function (data, json, xhr) {
            Response.render(xhr.responseText);
        },

        'fail': function (code, msg, json, xhr) {
            Response.render(xhr.responseText);
        },

        'error': function (xhr) {
            Response.clear();
        },

    });


})();