
//可以生成很复杂的动态数据，并根据提交的参数进行处理。
//具有真正模拟后台逻辑的能力。
SMS.Proxy.response(function (data, config) {

    var list = [
        {
            name: '订单',
            icon: 'icon_order_normal.png',
            items: [
                { name: '订单处理', url: 'html/order/index.html' },
                { name: '订单打印', url: 'html/print/print.html' }
            ]
        },
        {
            name: '售后',
            icon: 'icon_service_normal.png',
            hidden: true,
            items: [
                //{ name: '销货订单' },
                //{ name: '销货订单记录' },
                //{ name: '销货单' },
                //{ name: '销货记录' },
            ]
        },
        {
            name: '资料编辑',
            icon: 'icon_res_normal.png',
            items: [
                { name: '辅助资料', url: 'html/support-data/index.html' },
                { name: '商品列表', url: 'html/goods_list/index.html' },
                { name: '商品详情', url: 'html/goods_edit/index.html' },
                { name: '仓库详情', url: 'html/warehouse_edit/index.html' },
                { name: '部门详情', url: 'html/department-detail/index.html' },
                { name: '岗位详情', url: 'html/role_edit/index.html' },
                { name: '基础资料分类', url:'html/category-detail/index.html'}
            ]
        },
        {
            name: '基础资料',
            icon: 'icon_res_normal.png',
            //hidden: true,
            items: [
                { name: '仓库', url: 'html/warehouse/index.html?classID=10107' },
                { name: '部门', url: 'html/warehouse/index.html?classID=10103' },
                { name: '基础资料类别', url: 'html/warehouse/index.html?classID=10110' },
                { name: '岗位', url: 'html/warehouse/index.html?classID=10106' },
                {name: '职员', url: 'html/warehouse/index.html?classID=10105'}
            ]
        },
        {
            name: '营销',
            icon: 'icon_sell_normal.png',
            hidden: true,
            items: [
                //{ name: '调拨单' },
                //{ name: '调拨记录' },
                //{ name: '盘点' },
                //{ name: '其他入库' },
                //{ name: '其他入库记录' },
                //{ name: '其他出库' },
                //{ name: '其他出库记录' },
                //{ name: '成本调整单' },
                //{ name: '成本调整记录' },
            ]
        },
        {
            name: '库存',
            icon: 'icon_stock_normal.png',
            //hidden: true,
            items: [
                { name: '基础资料-仓库', url: 'html/warehouse/index.html?classID=10107' },
                { name: '列表调试', url:'html/warehouse-ding/index.html'}
                //{ name: '采购订单跟踪表' },
                //{ name: '采购明细表' },
                //{ name: '采购汇总表（按商品）' },
                //{ name: '采购汇总表（按供应商）' },
            ]
        },
        {
            name: '报表',
            icon: 'icon_graph_normal.png',
            hidden: true,
            items: [
                //{ name: '销售订单跟踪表' },
                //{ name: '销售明细表' },
                //{ name: '销售汇总表（按商品）' },
                //{ name: '销售汇总表（按客户）' },
                //{ name: '往来单位欠款表' },
                //{ name: '现金银行报表' },
                //{ name: '应付账款明细表' },
                //{ name: '应收账款明细表' },
                //{ name: '客户对账单' },
                //{ name: '供应商对账单' },
            ]
        },
        {
            name: '设置',
            icon: 'icon_setting_normal.png',
            hidden: true,
            items: [

                //{ name: '供应商管理' },
                //{ name: '商品管理' },
                //{ name: '账户管理' },
                //{ name: '发货地址管理' },
            ]
        },
        {
            name: '组件示例',
            icon: 'icon_setting_normal.png',
            items: [
                { name: 'API接口请求', url: 'html/demo/index.html?name=API' },
                { name: '加载提示器', url: 'html/demo/index.html?name=Loading' },
                { name: '分页器', url: 'html/demo/index.html?name=Pager' },
                { name: '级联选择器', url: 'html/demo/index.html?name=CascadePicker' },
                { name: '弹出对话框', url: 'html/demo/index.html?name=Dialog' },
                { name: '登录对话框', url: 'html/demo/index.html?name=Login' },
                { name: '数值型输入框', url: 'html/demo/index.html?name=NumberField' },
                { name: '日期时间选择器', url: 'html/demo/index.html?name=DateTimePicker' },
                { name: '复杂选择器', url: 'html/selector/index.html'}
            ]
        },
        {
            name: '控件预研',
            icon: 'icon_setting_normal.png',
            hidden: true,
            items: [
                { name: '数值型输入框', url: 'html/controls/autoNumeric/index.html' },
                { name: '数值输入框(封装)', url: 'html/controls/autoNumeric/index.kerp.html' },


            ]
        },
        {
            name: '后台接口',
            icon: 'icon_setting_normal.png',
            items: [
                { name: 'API测试器', url: 'html/api-tester/index.html' },

            ]
        },{
            name: '界面计划1',
            icon: 'icon_setting_normal.png',
            items: [
                { name: '仓库列表-2015-01-28', url: 'html/prototype/warehouse.html' },
                { name: '仓库编辑-2015-01-28', url: 'html/prototype/warehouse-edit.html' },
                { name: '部门-2015-01-30', url: 'html/prototype/department.html' },
                { name: '岗位-2015-02-04', url: 'html/prototype/station.html' },
                { name: '辅助属性-2015-02-04', url: 'fq' },
                { name: '网店-2015-02-04', url: 'html/prototype/ESHOP.html' },
                { name: '物流公司-2015-02-04', url: 'html/prototype/logistics-company.html' },
                { name: '职员-2015-02-05', url: 'html/prototype/Employee.html' },
                { name: '货位-2015-02-06', url: 'html/prototype/goods-allocation.html' },
                { name: '买家资料-2015-02-10', url: 'html/prototype/buyers-info.html' },
                { name: '商品列表-2015-02-13', url: 'html/prototype/goods.html' },
                { name: '商品编辑-2015-02-13', url: 'html/prototype/goods-edit.html' },
                { name: '系统设置-2015-02-16', url: 'html/prototype/settings.html' },
                { name: '商品对应-2015-02-16', url: 'html/prototype/goods-correspond.html' },
            ]
        },{
            name: '界面计划2',
            icon: 'icon_setting_normal.png',
            items: [
                { name: '订单处理-2015-02-16', url: 'html/prototype/orders-handle.html' },
                { name: '订单详情-2015-01-12', url: 'html/prototype/order-info.html' },
                { name: '套装商品-2015-02-16', url: 'html/prototype/suit.html' },
                { name: '智能选仓-2015-02-16', url: 'html/prototype/warehouse-setting.html' },
                { name: '订单打印-2015-02-06', url: 'html/prototype/print.html' },
                { name: '订单发货-2015-02-16', url: 'html/prototype/send-goods.html' },
                { name: '智能物流-2015-02-16', url: 'html/prototype/logistics-setting.html' },
                { name: '打印设置-2015-02-12', url: 'html/prototype/print-setting.html' },
                { name: '任务中心--', url: 'html/prototype/task.html' },
                { name: '订单日志--', url: 'html/prototype/order-log.html' },
            ]
        },
    ];

    return {

        code: 200,
        msg: 'ok',
        data: list,

    }

});
