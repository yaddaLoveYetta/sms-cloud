

define('FilterData', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = require('API');


    //filterType  0:单选 1:多选 2：时间 3：文本框 4：范围 5：图片 6：省市区
    //shown 0: 隐藏 1：显示
    //selected: 0：不是默认 1：默认
    //var list = [
    //   {
    //       itemID: 25,
    //       type: 'createTime',
    //       name: '日期',
    //       filterType: 2,
    //       shown: 1,
    //       items: [
    //           {
    //               name: '方案1',
    //               value: '2015-01-01 10:30,',
    //               selected: 0
    //           },

    //           {
    //               name: '方案2',
    //               value: ',2014-11-03 10:30',
    //               selected: 0
    //           },

    //           {
    //               name: '方案3',
    //               value: '2014-12-01 10:30,2014-12-02 10:30',
    //               selected: 0
    //           }
    //       ]
    //   },

    //   {
    //       "itemID": 29,
    //       "type": "expressPrint",
    //       "name": "物流单打印",
    //       "filterType": "1",
    //       "shown": 1,
    //       "items": [
    //           {
    //               "name": "已打印",
    //               "value": "1",
    //               "select": 1
    //           },

    //           {
    //               "name": "未打印",
    //               "value": "0",
    //               "select": 0
    //           }
    //       ]
    //   },

    //   {
    //       "itemID": 30,
    //       "type": "logisticsCompany",
    //       "name": "物流公司",
    //       "filterType": "1",
    //       "shown": 1,
    //       "items": [
    //           {
    //               "name": "华强物流",
    //               "value": "SHQ",
    //               "select": 0
    //           },

    //           {
    //               "name": "城市100",
    //               "value": "BJCS",
    //               "select": 0
    //           },

    //           {
    //               "name": "邮政国内小包",
    //               "value": "POSTB",
    //               "select": 0
    //           },

    //           {
    //               "name": "飞远(爱彼西)配送",
    //               "value": "HZABC",
    //               "select": 0
    //           },

    //           {
    //               "name": "长发",
    //               "value": "YUD",
    //               "select": 0
    //           },

    //           {
    //               "name": "东方汇",
    //               "value": "DFH",
    //               "select": 0
    //           },

    //           {
    //               "name": "E速宝",
    //               "value": "ESB",
    //               "select": 0
    //           }
    //       ]
    //   },

    //   {
    //       "itemID": 31,
    //       "type": "receiver",
    //       "name": "收货人",
    //       "filterType": "3",
    //       "shown": 0
    //   },

    //   {
    //       "itemID": 32,
    //       "type": "buyerMessage",
    //       "name": "买家留言",
    //       "filterType": "3",
    //       "shown": 0
    //   },

    //   {
    //       "itemID": 33,
    //       "type": "outerMemo",
    //       "name": "卖家备注",
    //       "filterType": "3",
    //       "shown": 0
    //   },

    //   {
    //       "itemID": 34,
    //       "type": "payAmount",
    //       "name": "订单实收",
    //       "filterType": "4",
    //       "shown": 0
    //   },

    //   {
    //       "itemID": 35,
    //       "type": "lotNumber",
    //       "name": "打印批号",
    //       "filterType": "3",
    //       "shown": 0
    //   },

    //   {
    //       "itemID": 36,
    //       "type": "serialNumber",
    //       "name": "打印序号",
    //       "filterType": "3",
    //       "shown": 0
    //   }
    //];

    var list = {
        'showns': [
            {
                "planID": 8, "itemID": 1, "name": "订单日期", "filterType": "2", "defaultValue": "2015-01-01 00:00,2015-01-05 23:00",
                "items": [
                    { "name": "今天", "value": "2015-01-06 00:00,2015-01-06 23:59" },

                    { "name": "近三天", "value": "2015-01-04 00:00,2015-01-06 23:59" },

                    { "name": "近七天", "value": "2014-12-31 00:00,2015-01-06 23:59" },

                    { "name": "近一月", "value": "2014-12-06 00:00,2015-01-06 23:59" },

                    { "name": "方案1", "value": "2015-01-05 16:00,2015-01-06 16:00" },

                    { "name": "方案2", "value": "2015-01-05 16:00,2015-01-06 18:00" }
                ]
            },
            {
                "planID": 9, "itemID": 5, "name": "物流单打印", "filterType": "1", "defaultValue": "0",
                "items": [
                    { "name": "已打印", "value": "1", "select": 0 },
                    { "name": "未打印", "value": "0", "select": 1 }
                ]
            },
            {
                "planID": 10, "itemID": 6, "name": "物流公司", "filterType": "1", "defaultValue": "1",
                "items": [
                    { "name": "E速宝", "value": "ESB", "select": 0 },
                    { "name": "东方汇", "value": "DFH", "select": 0 },
                    { "name": "华强物流", "value": "SHQ", "select": 0 },
                    { "name": "城市100", "value": "BJCS", "select": 0 },
                    { "name": "邮政国内小包", "value": "POSTB", "select": 0 },
                    { "name": "长发", "value": "YUD", "select": 0 },
                    { "name": "飞远(爱彼西)配送", "value": "HZABC", "select": 0 }
                ]
            },

            {
                "planID": 11, "itemID": 15, "name": "所属网店", "filterType": "1", "defaultValue": "1",
                "items": [
                    { "name": "1号店一叶子旗舰店", "value": "1d48d780-d642-4005-99c4-4ed6e1f8b789", "select": 0 },
                    { "name": "1号店川慈旗舰店", "value": "0d39d1da-6609-4ca7-ac5d-11144d743174", "select": 0 },
                    { "name": "1号店韩束官方旗舰店", "value": "f56079ff-577f-4f3d-839f-adaf60128ee7", "select": 0 },
                    { "name": "kans韩束", "value": "e32b00dc-f993-4864-9b00-d0c90f4909a7", "select": 0 },
                    { "name": "oneleaf化妆品旗舰店", "value": "44a6e138-0650-4f54-8cec-a684754fed47", "select": 0 },
                    { "name": "wetcode菲莲专卖店", "value": "b4b1a239-8d92-4296-89c4-fe8ed128688d", "select": 0 },
                    { "name": "一叶子化妆品旗舰店", "value": "37410107-c6d4-4d56-b6b8-dba0cda544f4", "select": 0 },
                    { "name": "一叶子旗舰店", "value": "a2c0ec68-a4d4-4a4c-bd4e-f9d39d11bcf5", "select": 0 },
                    { "name": "东方卫视旗舰店", "value": "d7541fa2-048b-4bd3-9b37-cdee55d83af9", "select": 0 },
                    { "name": "京东商城凯岚旗舰店", "value": "e8b3496d-d132-4b3a-8ecb-8e2a7b6d15ed", "select": 0 },
                    { "name": "兰瑟旗舰店", "value": "2bffb2d0-1fce-4de5-9080-ecb5ec7ca16d", "select": 0 },
                    { "name": "凯岚官方旗舰店", "value": "616660a3-e5bf-4477-bef4-1ac34777189d", "select": 0 },
                    { "name": "凯岚旗舰店", "value": "ee3588c7-4874-467c-bc26-1b4823241883", "select": 0 },
                    { "name": "大众点评", "value": "c13ba9aa-fad3-4fa8-a2b2-80857ba8f0bf", "select": 0 },
                    { "name": "我爱小馒头73", "value": "f43457f1-8377-4850-80d3-602465950797", "select": 0 },
                    { "name": "柠檬1009", "value": "df8a1600-4d47-4e07-99bf-053031a721b3", "select": 0 },
                    { "name": "水密码苏雪达化妆品特卖店", "value": "cf82e8ac-4575-436c-b8e3-a327b82111a8", "select": 0 },
                    { "name": "秀曼化妆品专营店", "value": "62e9c87c-6fd0-46c0-8525-c32a9ff7f90c", "select": 0 },
                    { "name": "秀曼名店", "value": "8ad00497-b9e7-4d9c-8acd-82f5678dda3a", "select": 0 },
                    { "name": "肌漾旗舰店", "value": "ee332d50-53a3-4b32-919d-9082a878b029", "select": 0 },
                    { "name": "膜法传奇1853品牌直销店", "value": "bcf40f8c-92d0-43aa-94b4-2f4953f69ea0", "select": 0 },
                    { "name": "苏宁膜法传奇旗舰店", "value": "09fea8af-40e5-4794-9891-560ecba8f35d", "select": 0 },
                    { "name": "苏雪达化妆品专营店", "value": "6c2de571-78de-4147-9d48-f2ed027750e6", "select": 0 },
                    { "name": "菲莲化妆品专营店", "value": "d49c1dc0-de0e-4cae-aadf-9a4cf13a0e44", "select": 0 },
                    { "name": "雪黎化妆品专营店", "value": "0fa22ab3-e5bb-4304-9b3c-4d6d4956ca36", "select": 0 },
                    { "name": "韩束上海专卖店", "value": "168210eb-1cf7-4bc1-90e3-6b91a7d480a1", "select": 0 },
                    { "name": "韩束化妆品旗舰店（拍拍商城）", "value": "c792e2b5-c1be-453d-8faa-941c17fcff40", "select": 0 },
                    { "name": "韩束官方旗舰店", "value": "367dbe22-09d2-4e92-8c75-4edf6282669c", "select": 0 },
                    { "name": "韩束官方旗舰店（蘑菇街）", "value": "55ef6395-5dfb-4e17-9ec4-2fef52bfb530", "select": 0 },
                    { "name": "韩束店（拍拍商城）", "value": "9d44e959-0381-4ead-a598-30321305f0df", "select": 0 },
                    { "name": "韩束旗舰店（美丽说）", "value": "7db71e5d-18a7-4d5c-a270-745ccf548d27", "select": 0 },
                    { "name": "韩束苏雪达化妆品特卖店", "value": "8b4a72ab-fc8a-4c7d-80d1-a22f84637a30", "select": 0 }
                ]
            },

            {
                "planID": 16, "itemID": 34, "name": "地区", "filterType": "6", "defaultValue": "0",
                "items": [
                    { "itemID": 34, "type": 1, "areaID": "0" },
                    { "itemID": 35, "type": 1, "areaID": "0" },
                    { "itemID": 36, "type": 1, "areaID": "0" }
                ]
            },

            {
                "planID": 19, "itemID": 22, "name": "标签", "filterType": "5", "defaultValue": "0",
                "items": [
                    { "name": "分类1", "value": "1", "select": 0 },
                    { "name": "分类2", "value": "2", "select": 0 },
                    { "name": "分类3", "value": "3", "select": 0 },
                    { "name": "分类4", "value": "4", "select": 0 },
                    { "name": "分类5", "value": "5", "select": 0 }
                ]
            },

            {
                "planID": 20, "itemID": 42, "name": "数量", "filterType": "4", "defaultValue": "0"
            }
            ,

            {
                "planID": 21, "itemID": 43, "name": "买家留言", "filterType": "3", "defaultValue": "0"
            }
        ],
        "notification": [
            {
                "name": "超重", "itemID": 20, "defaultValue": "320", "select": 0
            },
            {
                "name": "物流可打印", "itemID": 2, "defaultValue": "72", "select": 0
            },
            {
                "name": "打印后地址变更", "itemID": 3, "defaultValue": "85", "select": 0
            },
            {
                "name": "延迟发货", "itemID": 4, "defaultValue": "32", "select": 1
            },
            {
                "name": "物流不到达", "itemID": 19, "defaultValue": "15", "select": 0
            },
            {
                "name": "冻结", "itemID": 18, "defaultValue": "28", "select": 0
            },
            {
                "name": "退款", "itemID": 17, "defaultValue": "26", "select": 0
            },
            {
                "name": "物流公司为空", "itemID": 16, "defaultValue": "42", "select": 0
            }
        ],
        "categorys": [
            {
                "name": "订单信息",
                "items": [
                    { "name": "订单日期", "itemID": 1, "select": 1 },
                    { "name": "所属网店", "itemID": 15, "select": 0 },
                    { "name": "交易状态", "itemID": 13, "select": 0 },
                    { "name": "标签", "itemID": 22, "select": 0 },
                    { "name": "卖家旗帜", "itemID": 23, "select": 0 },
                    { "name": "买家留言", "itemID": 8, "select": 0 },
                    { "name": "卖家备注", "itemID": 9, "select": 0 },
                    { "name": "唯一码", "itemID": 21, "select": 0 },
                    { "name": "网上订单号", "itemID": 24, "select": 0 },
                    { "name": "订单实收", "itemID": 10, "select": 0 }
                ]
            },
            {
                "name": "打印信息",
                "items": [
                    { "name": "物流单打印", "itemID": 5, "select": 1 },
                    { "name": "物流公司", "itemID": 6, "select": 0 },
                    { "name": "配货单打印", "itemID": 26, "select": 0 },
                    { "name": "发货单打印", "itemID": 27, "select": 0 },
                    { "name": "打印批号", "itemID": 11, "select": 0 },
                    { "name": "打印序号", "itemID": 12, "select": 0 },
                    { "name": "物流单号", "itemID": 28, "select": 0 }
                ]
            },
            {
                "name": "买家信息",
                "items": [
                    { "name": "省", "itemID": 34, "select": 0, "group": 1 },
                    { "name": "市", "itemID": 35, "select": 0, "group": 1 },
                    { "name": "区", "itemID": 36, "select": 0, "group": 1 },
                    { "name": "收货人", "itemID": 7, "select": 0 },
                    { "name": "买家昵称", "itemID": 25, "select": 0 },
                    { "name": "收货地址", "itemID": 37, "select": 0 },
                    { "name": "手机号码", "itemID": 38, "select": 0 },
                    { "name": "联系电话", "itemID": 39, "select": 0 }
                ]
            },
             {
                 "name": "快速过滤",
                 "items": [
                    { "name": "超重", "itemID": 20, "select": 1 },
                    { "name": "物流可打印", "itemID": 2, "select": 1 },
                    { "name": "打印后地址变更", "itemID": 3, "select": 1 },
                    { "name": "延迟发货", "itemID": 4, "select": 1 },
                    { "name": "物流不到达", "itemID": 19, "select": 1 },
                    { "name": "冻结", "itemID": 18, "select": 1 },
                    { "name": "退款", "itemID": 17, "select": 1 },
                    { "name": "物流公司为空", "itemID": 16, "select": 1 }]
             }]
         };


    var pathList = ['首页', '订单打印'];

    function loadFilterData(data, fn) {

        $.extend(list, data);

        fn && fn(list);
       
    }

    return {
        pathList: function () {
            return pathList;
        },
        list: function () {
            return list;
        },
        loadFilterData: loadFilterData

    }
})


