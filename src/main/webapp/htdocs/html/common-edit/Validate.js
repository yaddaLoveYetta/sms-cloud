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

    /**
     * 校验企业统一社会信用代码是否合法
     *
     * 社会信用代码：由18位数字和大写的字母组成，第1位是登记管理部门代码，第2位是机构类别代码，
     * 第3~8位登记管理机关行政区划码，第9~17位主休标识码，第18位校验码。有五个部分组成
     *
     * @param code 统一社会信用代码
     */
    function checkSocialCreditCode(code) {

        var reg = /^[0-9A-Z]+$/;
        //18位校验及大写校验
        if ((code.length !== 18) || (!reg.test(code))) {

            return false;
            // alert("不是有效的统一社会信用编码！");
        }
        else {
            var anCode;//统一社会信用代码的每一个值
            var anCodeValue;//统一社会信用代码每一个值的权重
            var total = 0;
            var weight = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28];//加权因子
            var str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
            //不用I、O、S、V、Z
            for (var i = 0; i < code.length - 1; i++) {
                anCode = code.substring(i, i + 1);
                anCodeValue = str.indexOf(anCode);
                total = total + anCodeValue * weight[i];
                //权重与加权因子相乘之和
            }
            var logicCheckCode = 31 - total % 31;
            if (logicCheckCode === 31) {
                logicCheckCode = 0;
            }
            var Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
            var Array_Str = Str.split(',');
            logicCheckCode = Array_Str[logicCheckCode];


            var checkCode = code.substring(17, 18);

            return logicCheckCode === checkCode;
            /*            if (logicCheckCode !== checkCode) {
                            alert("不是有效的统一社会信用编码！");
                        }*/
        }
    }

    return {
        integer: integer,
        mobilePhone: mobilePhone,
        phone: phone,
        isDate: isDate,
        checkSocialCreditCode: checkSocialCreditCode
    }

});