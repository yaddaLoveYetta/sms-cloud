define('Validate', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    /**
     * 整型校验
     * @param value
     * @returns {boolean}
     */
    function integer(value) {
        var reg = /^\d+$/;
        return reg.test(value);
    }

    /**
     * 数字校验
     * @param value
     * @returns {boolean}
     */
    function numeric(value) {
        var reg = /^[0-9]*$/;
        return reg.test(value);
    }

    /**
     * 手机校验
     * @param {} value
     * @returns {}
     */
    function mobilePhone(value) {
        var reg = new RegExp("^((((13|15|18|17)[0-9])|147)\\d{8})$");
        return reg.test(value);
    }

    /**
     * 座机校验
     * @param {} value
     * @returns {}
     */
    function phone(value) {
        var reg = new RegExp("^(0\\d{2,3})?(-)?\\d{7,8}$");
        return reg.test(value);
    }

    /**
     * 验证日期
     * 不仅仅匹配了日期格式，而且对日期的逻辑做了严格要求，判断了大月31天，小月30天，2月28，闰年情况2月29天
     * @param date
     * @returns {boolean}
     */
    function isDate(date) {

        var reg = /((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))/ig;

        return reg.test(date);

    }

    return {
        integer: integer,
        mobilePhone: mobilePhone,
        landlinePhone: landlinePhone
    };
});