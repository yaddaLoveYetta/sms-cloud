

var FilterData = (function ($, MiniQuery, SMS) {

    //filterType  0:单选 1:多选 2：时间 3：文本框 4：范围 5：图片 6：省市区
    //shown 0: 隐藏 1：显示
    //selected: 0：不是默认 1：默认
    
    var list = config.list;


    var pathList = config.pathList;

    function load(fn) {

        fn(list.showns);

    }

    function loadPath(fn) {

        fn(pathList);
    }

    function loadOptions(fn) {
        fn(list.categorys);
    }

    function loadNotification(fn) {
        fn(list.notification);
    }

    return {
        load: load,
        loadPath: loadPath,
        loadOptions: loadOptions,
        loadNotification: loadNotification

    }

})(jQuery, MiniQuery, SMS);


