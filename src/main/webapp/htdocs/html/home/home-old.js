


; (function ($, MiniQuery, SMS, Todo) {


    //检查登录 
    if (!SMS.Login.check(true)) {
        return;
    }

    Todo.render();



    $(document).on('dblclick', function () {



        SMS.Login.show();

 
    });




})(jQuery, MiniQuery, SMS, Todo);
