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

    return {
        submitData: submitData,
        getMetaData: getMetaData,
        getItemData: getItemData,
        getValueElement: getValueElement
    }

});