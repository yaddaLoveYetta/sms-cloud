/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/4 10:34
 */

define('Edit', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    /**
     * 获取角色权限
     * @param roleId 角色id
     * @param fn 回调
     */
    function getRolePermissions(roleId, fn) {

        var api = new API('user/getRolePermissions');
        api.get({
            roleId: roleId
        });

        api.on({
            'success': function (data, json) {
                fn && fn(data);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    /**
     * 保存角色权限
     * @param roleId  角色id
     * @param perMissions 权限授权结果集
     * @param fn 回调
     */
    function saveRolePerMissions(roleId, perMissions, fn) {

    }

    module.exports = {
        getRolePermissions: getRolePermissions,
        saveRolePerMissions: saveRolePerMissions
    }
});