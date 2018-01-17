


define('operation', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');

    var iframe = KERP.require('Iframe');

    var dialog = iframe.getDialog();

    var selectTypes;
    var addNumber = document.getElementById('add-number');
    var addName = document.getElementById('add-name');
    var pWarn = document.getElementById('p-warn');



    function render() {
        bindEvents();
    }

    function bindEvents() {
        $('#btn-submit').bind('click', function () {

            selectTypes = document.getElementById('select-types');
            var typeID = getKey(selectTypes);
            var number = $(addNumber).val();
            var name = $(addName).val();
            var postData = {
                'action': 'add',
                'number': number,
                'name': name,
                'typeID': typeID
            };

            if (validators()) {
                KERP.API.post('/bd/assistitem', postData).success(function (data, json) {
                    dialog.remove();
                    KERP.Tips.success('新增成功！');
                }).fail(function (code, msg, json) {
                    pWarn.innerText = '*' + msg;
                });
            }
            else {
                return;
            }
        });

        $('#btn-cancel').bind('click', function () {
            //document.location.href = '../supportData/index.html';

            dialog.remove();
        });
    }


    //对提交的表单做不为空校验
    function validators() {
        var isChecked = false;

        if (getKey(selectTypes) == '请选择') {
            pWarn.innerText = '*请选择类别！';
        }
        else {
            if (!addNumber.value) {
                pWarn.innerText = '*代码不能为空！';
            }
            else {
                if (!addName.value) {
                    pWarn.innerText = '*名称不能为空！';
                }
                else {
                    isChecked = true;
                }
            }
        }

        return isChecked;
    }


    function getKey(obj) {
        var val = obj.options[obj.options.selectedIndex].value;
        return val;
    }

    return {
        render: render
    }
});