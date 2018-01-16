/**
 * Created by yadda on 2017/6/15.
 */

//可以生成很复杂的动态数据，并根据提交的参数进行处理。
//具有真正模拟后台逻辑的能力。
SMS.Proxy.response(function (data, config) {

    var pageSize = data.pageSize;
    var pageNo = data.pageNo;

    var total = 10000;

    if (pageNo * pageSize > total) {
        pageSize = total - pageNo * pageSize;
    }

    var list = $.Array().pad(0, pageSize).keep(function (item, index) {

        return {
            'orderSeq':$.Math.randomInt(1, 5),
            'oustockNo':'ouStockNo-'+$.Math.randomInt(1, 5),
            'date':$.Date.format(new Date(), 'yyyy-MM-dd'),
            'metarial':$.Math.randomInt(1, 500)+'-'+$.Math.randomInt(1, 500),
            'metarialName': 'A' + index,
            'model': 'aa.bb.cc.dd.ee.xx.yy.zz'.slice(0, $.Math.randomInt(3, 23)),
            'unit': '只',
            'qty':$.Math.randomInt(30, 200),
            'orderQty': $.Math.randomInt(30, 200),
            'noneOutStockQty': $.Math.randomInt(10, 100),
            'logistic': 'EMS',
            'logisticNo': $.Math.randomInt(1000, 100000),
        };

    }).valueOf();

    return {

        code: 200,
        msg: 'ok',
        data: {
            total: total,
            list: list
        }

    };

});