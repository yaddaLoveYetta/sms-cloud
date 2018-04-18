/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/13 15:29
 */

define('UrlMapping', function (require, module, exports) {

    var defaultUrl = 'html/bill/index.html';

    function get(classId) {

        switch (classId) {
            case 1012:
                return 'html/bill-ext/hospital/index.html';
                break;
            case 1013:
                return 'html/bill-ext/supplier/index.html';
                break;
            case 1005:
                return 'html/bill-ext/hospital-supplier/index.html';
                break;
            default:
                return defaultUrl;
        }
    }


    return get;

});






