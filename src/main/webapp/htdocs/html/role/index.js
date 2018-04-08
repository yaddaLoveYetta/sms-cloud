/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/2 15:06
 */

(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var Tree = require('Tree');
    var Edit = require('Edit');

    var dialog = Iframe.getDialog();
    var roleId = MiniQuery.Url.getQueryString(window.location.href, 'roleId');

    if (dialog) {

        var data = dialog.getData();
        roleId = data.roleId;

        dialog.on({
            'saveRolePerMissions': saveRolePerMissions
        });
    }
    if (!roleId) {
        return;
    }

    function saveRolePerMissions(fn) {
        Tree.saveRolePerMissions(roleId, fn);
    }

    Tree.render(roleId);

})();