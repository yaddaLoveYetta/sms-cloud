/**
 * 用户操作定制模块
 */
define('UserTypeOpt', function (require, module, exports) {
    var $ = require('$');
    var SMS = require('SMS');

    /**
     * 添加必填标识
     * @param {} mastInput 是否必填
     * @param {} element 元素
     * @returns {} 
     */
    function mastInputClass(mastInput, element) {
        //必填处理
        if (mastInput) {
            //如果是必填需要添加 红色 * 号
            if ($(element).parents("td").siblings().find(".must-mark").length <= 0) { //如果不存在
                var html = $(element).parents("td").siblings().html();
                $(element).parents("td").siblings().html("<span class=\"must-mark\">*</span>" + html);
            }
        } else {
            if ($(element).parents("td").siblings().find(".must-mark")) {
                $(element).parents("td").siblings().find(".must-mark").remove();
            }
        }
    }

    /**
     * 禁用元素
     * @param {} elementType 元素类型 F7 默认是input禁用方式
     * @param {} element 需要禁用的元素
     * @returns {} 
     */
    function disableElement(isDisable, elementType, element) {
        //必填处理

        if (isDisable) {//禁用
            if (elementType == "F7") {
                var ftype = $(element);
                var inpt = ftype.find("input");
                var sbtn = ftype.find('[data-role="btn"]');
                $(inpt).attr("disabled", "disabled");
                $(sbtn).attr("data-role", "btnDisalbe");
            } else {
                $(element).attr("disabled", "disabled");
            }
        } else {//启用
            if (elementType == "F7") {
                var ftype1 = $(element);
                var inpt1 = ftype1.find("input");
                var sbtn1 = ftype1.find('[data-role="btnDisalbe"]');
                $(inpt1).removeAttr("disabled");
                $(sbtn1).attr("data-role", "btn");
            } else {
                $(element).removeAttr("disabled");
            }
        }
    }

    function gridVisble(isVisblie, elment) {
        if (isVisblie) {

            $(elment).parents('tr').show();
        } else {
            $(elment).parents('tr').hide();
        }
    }

    function render(userTypeId) {
        //if (userTypeId == 50802) { //物业用户
        //    mastInputClass(true, $("#bd-FCompany"));
        //    disableElement(false, "F7", $("#bd-FCompany"));
        //} else {
        //    mastInputClass(false, $("#bd-FCompany"));
        //    disableElement(true, "F7", $("#bd-FCompany"));
        //}
        mastInputClass(userTypeId == 50802, $("#bd-FCompany"));
        disableElement(!(userTypeId == 50802), "F7", $("#bd-FCompany"));//
        gridVisble(userTypeId == 50802, $("#bd-grid"));
    };

    return { render: render };
});