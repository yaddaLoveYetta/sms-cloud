

define('Validate', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    /**
     * 整型校验
     * @param {} value 
     * @returns {} 
     */
    function integer(value) {
        var reg = /^\d+$/;
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
    function landlinePhone(value) {
        var reg = new RegExp("^(0\\d{2,3})?(-)?\\d{7,8}$");
        return reg.test(value);
    }

    return {
        integer: integer,
        mobilePhone: mobilePhone,
        landlinePhone: landlinePhone
    };
});