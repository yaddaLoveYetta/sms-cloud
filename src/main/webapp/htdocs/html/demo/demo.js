




; (function ($, MiniQuery, KERP, Data, Header, Demos, Tabs, Panels, Readme) {




    var name = $.Url.getQueryString(window, 'name');

    Data.load(name, function (json) {

        Header.render(json);

        Demos.render(json.demos, Tabs, Panels);

        Readme.render(json.readme);
        

    });






    



})(jQuery, MiniQuery, KERP, Data, Header, Demos, Tabs, Panels, Readme);