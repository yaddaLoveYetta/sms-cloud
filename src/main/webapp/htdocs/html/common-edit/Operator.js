/**
 * @Title: 公共方法
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/16 10:18
 */

define('Operator', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    /**
     *  新增/修改单据时的提交功能(走模板方式)
     * @param data  单据数据
     * @param type 类别 1：新增 2：修改
     * @param fnSuccess 成功的回调
     */
    function submitData(data, type, fnSuccess) {

        var action = 'template/addItem';

        if (type === 2) {
            action = 'template/editItem';
        }

        var api = new API(action);

        api.post({
            classId: data.classId,
            id: data.id,
            data: data.data
        });

        api.on({
            'success': function (data, json) {
                fnSuccess && fnSuccess(data);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });
    }

    /**
     * 获取单据模板
     * @param classId 模板id
     * @param fnSuccess 成功的回调
     */
    function getMetaData(classId, fnSuccess) {

        if ($.Object.isPlain(classId)) {
            // 重载
            config = classId;
            classId = config.classId;
        }

        // 获取单据模板
        var api = new API('template/getFormTemplate');

        api.get({
            'classId': classId
        });

        api.on({
            'success': function (data, json) {
                fnSuccess && fnSuccess(data);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });
    }

    /**
     * 获取单据数据-只在查看/编辑时调用
     * @param classId 事物类型
     * @param id 单据内码
     * @param fnSuccess 成功回调
     */
    function getItemData(classId, id, fnSuccess) {

        if ($.Object.isPlain(classId)) {
            // 重载
            config = classId;
            fnSuccess = id;
            classId = config.classId;
            id = config.id;
        }

        var api = new API('template/getItemById');
        SMS.Tips.loading('数据加载中..');

        api.get({
            'classId': classId,
            'id': id
        });

        api.on({
            'success': function (data, json) {
                fnSuccess && fnSuccess(data);
                SMS.Tips.success('数据加载成功', 1500);
            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg);
            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试');
            }
        });

    }

    /**
     * 获取界面DOM元素（document.getElementById方式）
     * @param key 模板key
     * @returns {Element}
     */
    function getValueElement(key) {
        return document.getElementById(key);
    }

    /**
     * 判断表头字段是否是必填字段
     * @param metaData 字段模板
     * @param key 字段模板key
     * @param operate 当前业务场景 0查看 1新增 2修改
     * @returns {boolean}
     */
    function isMustInputFiled(metaData, key, operate) {

        var user = SMS.Login.get();

        var fields = metaData['formFields'][0];

        var field = fields[key];
        // 模板中配置的mustInput
        var mustInput = field['mustInput'] || 0;
        //是否必填掩码
        var mustInputMask = 0;
        // 角色类别 1:系统管理员 2:医院 3:供应商
        var userRoleType = (user.roles && user.roles[0] && user.roles[0]['type']) || -1;

        /*
                    1	新增时对于平台用户必填
                    2	编辑时对于平台用户必填
                    4	新增时对于供应商用户必填
                    8	编辑时对于供应商用户必填
                    16	新增时对于医院用户必填
                    32	编辑时对于医院用户必填
                    */

        if (operate === 1) {
            // 新增
            if (userRoleType === 1) {
                // 平台用户
                mustInputMask = 1;
            } else if (userRoleType === 2) {
                //医院用户
                mustInputMask = 16;
            } else if (userRoleType === 3) {
                //供应商用户
                mustInputMask = 4;
            }
        } else if (operate === 2) {
            // 修改
            if (userRoleType === 1) {
                // 平台用户
                mustInputMask = 2;
            } else if (userRoleType === 2) {
                //医院用户
                mustInputMask = 32;
            } else if (userRoleType === 3) {
                //供应商用户
                mustInputMask = 8;
            }
        }

        //是否必填
        return !!(mustInputMask & mustInput);
    }

    return {
        submitData: submitData,
        getMetaData: getMetaData,
        getItemData: getItemData,
        getValueElement: getValueElement,
        isMustInputFiled: isMustInputFiled
    }

});