


define('OrderListData', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


   
    //skuTitle: 名字；skuName：显示的值；skuValue：数据库的值；skuType：sku显示类型；type：用于在skuList中找出键值对
    var list = [];

    var sortList = {
        "sq": [
         {
             "sequenceID": 5, "name": "订单时间", "value": "createTime", "shown": true
         },
         {
             "sequenceID": 6, "name": "付款时间", "value": "payTime", "shown": true
         }
        ],
        "allsq": [
            { "sequenceID": 5, "name": "订单时间", "value": "createTime", "shown": true },
            { "sequenceID": 6, "name": "付款时间", "value": "payTime", "shown": true },
            { "sequenceID": 7, "name": "买家昵称", "value": "buyerNick", "shown": false },
            { "sequenceID": 8, "name": "实收金额", "value": "payment", "shown": false },
            { "sequenceID": 9, "name": "物流公司", "value": "logisticsCompanyName", "shown": false },
            { "sequenceID": 10, "name": "收货地址", "value": "concat(deliveryStateID,deliveryCityID,deliveryDistrictID,deliveryAddress)", "shown": false }
        ]
    };

    var pager = {
        "page": 1,
        "size": 20,
        "order": "",
        "total": 230
    }

    var orders = [
    {
        "orderID": 307,
        "webOrderID": 307,
        "tid": "906871850146194",
        "shopID": 3,
        "shopName": "肌漾旗舰店",
        "shopNumber": "ee332d50-53a3-4b32-919d-9082a878b029",
        "orderType": 1,
        "orderTypeName": "网上订单",
        "orderStatus": 1,
        "orderStatusName": "待审核",
        "transStatus": 3,
        "transStatusName": "已发货",
        "uniqueCode": 0,
        "checker": 0,
        "createTime": "2014-12-24 13:21:35",
        "logisticsCompany": 2,
        "logisticsCompanyName": "城市100",
        "logisticsCost": 0,
        "deliveryStateID": 0,
        "deliveryCityID": 0,
        "deliveryDistrictID": 0,
        "deliveryAddress": "钱江开发区顺风路528号  万华科技园内  杭州超探",
        "isMerge": 0,
        "isInvoice": 0,
        "isVoid": 0,
        "totalAmount": 207,
        "discountAmount": 134.1,
        "billDiscountAmount": 0,
        "payAmount": 72.9,
        "freightAmount": 0,
        "payTime": "2014-12-23 19:55:15",
        "consignTime": "2014-12-24 09:32:19",
        "buyerMessage": "",
        "buyerNick": "汤汤5885",
        "receiverZip": "311100",
        "receiverMobile": "15335195658",
        "receiverPhone": "",
        "receiver": "汤晓燕",
        "outerMemo": "{圆通速递}ddddd[张三]ff",
        "tradeFrom": "手机淘宝,手机淘宝",
        "logNumber": "",
        "serialNumber": 0,
        "expressPrint": 1,
        "preparePrint": 0,
        "consignPrint": 0,
        "prepareMan": 0,
        "consignMan": 0,
        "expressPrintCount": 0,
        "expressNotArrive": 0
    },
           {
               "orderID": 982,
               "webOrderID": 982,
               "tid": "899984026589535",
               "shopID": 4,
               "shopName": "韩束上海专卖店",
               "shopNumber": "168210eb-1cf7-4bc1-90e3-6b91a7d480a1",
               "orderType": 1,
               "orderTypeName": "网上订单",
               "orderStatus": 1,
               "orderStatusName": "待审核",
               "transStatus": 3,
               "transStatusName": "已发货",
               "uniqueCode": 0,
               "checker": 0,
               "createTime": "2014-12-24 13:23:04",
               "logisticsCompany": 2,
               "logisticsCompanyName": "城市100",
               "logisticsCost": 0,
               "deliveryStateID": 0,
               "deliveryCityID": 0,
               "deliveryDistrictID": 0,
               "deliveryAddress": "靖安镇陈庄子村",
               "isMerge": 0,
               "isInvoice": 0,
               "isVoid": 0,
               "totalAmount": 475.03,
               "discountAmount": 191.03,
               "billDiscountAmount": 20,
               "payAmount": 264,
               "freightAmount": 0,
               "payTime": "2014-12-21 11:33:47",
               "consignTime": "2014-12-21 16:34:21",
               "buyerMessage": "",
               "buyerNick": "tb124052936",
               "receiverZip": "066600",
               "receiverMobile": "15651086652",
               "receiverPhone": "",
               "receiver": "刘双",
               "outerMemo": "{圆通速递}ddddd[张三]ff",
               "tradeFrom": "手机淘宝,手机淘宝",
               "logNumber": "",
               "serialNumber": 0,
               "expressPrint": 0,
               "preparePrint": 0,
               "consignPrint": 0,
               "prepareMan": 0,
               "consignMan": 0,
               "expressPrintCount": 0,
               "expressNotArrive": 0
           }
    ];

    var entrys = [
           {
               "createTime": "2014-12-23 16:37:42",
               "consignTime": "2014-12-23 16:37:42",
               "orderID": 111,
               "webOrderEntryID": 117,
               "indexNo": 0,
               "tid": "904180359481229",
               "oid": "904180359481229",
               "shopID": 1,
               "shopName": "雪黎化妆品专营店",
               "shopNumber": "0fa22ab3-e5bb-4304-9b3c-4d6d4956ca36",
               "orderFrom": "手机淘宝,手机淘宝",
               "items": [
                   {
                       "orderEntryID": 117,
                       "itemID": 0,
                       "unitID": 0,
                       "spID": 0,
                       "stockID": 0,
                       "packageID": 0,
                       "packageEntryID": 0,
                       "promotionID": 0,
                       "serviceID": 0,
                       "qty": 25,
                       "price": 1,
                       "amount": 25,
                       "discountAmount": 0,
                       "payAmount": 25,
                       "isModified": 0,
                       "isFree": 0,
                       "numIid": "38107518099",
                       "title": "邮费补差价 补拍 拍前请联系客服 擅自拍下不发货",
                       "refundStatus": "NO_REFUND",
                       "picPath": "http://img03.taobaocdn.com/bao/uploaded/i3/T1.gn2FrBaXXXXXXXX_!!0-item_pic.jpg"
                   },
                   {
                       "orderEntryID": 118,
                       "itemID": 0,
                       "unitID": 0,
                       "spID": 0,
                       "stockID": 0,
                       "packageID": 0,
                       "packageEntryID": 0,
                       "promotionID": 0,
                       "serviceID": 0,
                       "qty": 25,
                       "price": 1,
                       "amount": 25,
                       "discountAmount": 0,
                       "payAmount": 25,
                       "isModified": 0,
                       "isFree": 0,
                       "numIid": "38107518099",
                       "title": "邮费补差价 补拍 拍前请联系客服 擅自拍下不发货",
                       "refundStatus": "NO_REFUND",
                       "picPath": "http://img03.taobaocdn.com/bao/uploaded/i3/T1.gn2FrBaXXXXXXXX_!!0-item_pic.jpg"
                   }
               ]

           },
           {
               "createTime": "2014-12-23 16:37:42",
               "consignTime": "2014-12-23 16:37:42",
               "orderID": 111,
               "webOrderEntryID": 117,
               "indexNo": 0,
               "tid": "904180359481229",
               "oid": "904180359481229",
               "shopID": 1,
               "shopName": "雪黎化妆品专营店",
               "shopNumber": "0fa22ab3-e5bb-4304-9b3c-4d6d4956ca36",
               "orderFrom": "手机淘宝,手机淘宝",
               "items": [
                   {
                       "orderEntryID": 119,
                       "itemID": 0,
                       "unitID": 0,
                       "spID": 0,
                       "stockID": 0,
                       "packageID": 0,
                       "packageEntryID": 0,
                       "promotionID": 0,
                       "serviceID": 0,
                       "qty": 25,
                       "price": 1,
                       "amount": 25,
                       "discountAmount": 0,
                       "payAmount": 25,
                       "isModified": 0,
                       "isFree": 0,
                       "numIid": "38107518099",
                       "title": "邮费补差价 补拍 拍前请联系客服 擅自拍下不发货",
                       "refundStatus": "NO_REFUND",
                       "picPath": "http://img03.taobaocdn.com/bao/uploaded/i3/T1.gn2FrBaXXXXXXXX_!!0-item_pic.jpg"
                   }
               ]

           }
    ];

    var pager = {
        "page": 1,
        "size": 20,
        "order": "",
        "total": 230
    }

    function getDefaultFilter() {
        return [{
            'field': 'createTime',
            'value': $.Date.format(new Date(), 'yyyy-MM-dd'),
            'type': '<='
        }];
    }

    function load(data, fn1, fn2) {
      
        //API.list({
        //    pager: {
        //        page: 1,
        //        size: 20,
        //        order: "orderid asc"
        //    },
        //    filter: getDefaultFilter()
        //}, function (data) {

        //    fn1(data.orders);
        //    fn2(sortList);
        //    fn3(data.pager);


        //});

        if (data) {
            fn1(data.orders);
            fn2(sortList);
            fn3(data.pager);
            
        }
        

        fn1(orders);
        fn2(sortList);
        fn3(pager);
       


        
        
    }

    function loadGoodsInfo(fn) {
        fn(entrys);
    }

    return {
        load: load,
        loadGoodsInfo: loadGoodsInfo,
        getDefaultFilter: getDefaultFilter,
        getTestSkuList: function () {
            return list[0].goodsItems[0].skuname;
        }
    }


});
