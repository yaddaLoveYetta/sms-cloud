


//按钮操作区
define('operations', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var iframe = SMS.require('Iframe');
    
    var dialog = iframe.getDialog();
    var btnSave = document.getElementById('btn_save');
    var btnCancel = document.getElementById('btn_cancel');
    var name;
    var number;

    var hasBind = false;
    var emitter = MiniQuery.Event.create();

    function render() {
        if (hasBind) {
            return;
        }
        else {
            bindEvents();
        }
    }

    function bindEvents() {
        $(btnCancel).bind('click', function () {
            dialog.remove();
        });

        $(btnSave).bind('click', function () {
            emitter.fire('save.click');
        })

    }

    return {
        render: render,
        on: emitter.on.bind(emitter)
    }

});