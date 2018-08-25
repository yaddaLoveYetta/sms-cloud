/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/20 16:32
 */

;(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var List = require('List');
    var MessageBox = SMS.require('MessageBox');

    // 搜索按钮
    $('#btn-search').on('click', function () {

        var keyword = $('#input-search').val();

        if (keyword.trim() === '') {
            SMS.Tips.error('请输入完整的医院机构名称查找!', 1500);
            return;
        }

        List.render({
            keyword: keyword
        });

    });

    // 搜索框enter
    $('#input-search').on('keypress', function (event) {
        if (event.keyCode === 13) {
            $('#btn-search').trigger('click');
        }
    });

    // 查看医院详细资料
    $('#view-hospital').on('click', function () {

        var item = List.getSelectedItem();

        if (!item || item.length === 0) {
            SMS.Tips.error('请选择一个医院查看资料!', 1500);
            return;
        }

        SMS.use('Dialog', function (Dialog) {

            // ./ 表示相对于网站根目录
            var url = $.Url.addQueryString('./html/bill-ext/hospital/index.html', {
                classId: 1012,
                id: item.id,
                operate: 0
            });

            var dialog = new Dialog({
                id: 'view-hospital',
                title: '医院详细信息',
                url: url,
                width: 1024,
                height: 600,
                button: [{
                    value: '关闭',
                    className: 'sms-cancel-btn'
                }]
            });

            dialog.showModal();
        });

    });

    // 申请成为供应商
    $('#cooperation-apply').on('click', function () {

        var item = List.getSelectedItem();

        if (!item || item.length === 0) {
            SMS.Tips.error('请选择一个医院!', 1500);
            return;
        }

        MessageBox.confirm('申请需要院方审核才能生效.发送申请后请主动联系院方审核', function (result) {

            List.addCooperationApply(function () {
                SMS.Tips.success("发送申请成功,请主动联系医院联系人审核!", 1000);
            });

        });

    })

})();