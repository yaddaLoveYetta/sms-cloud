﻿//标准分页器
SMS.Pager.create({

    //分页控件的容器
    container: '#div-pager-normal-2',

    current: 1,         //当前激活的页码，默认为 1
    size: 10,           //每页的大小，即每页的记录数
    total: 501,         //总的记录数，应该从后台取得该值
    hideIfLessThen: 2,  //总页数小于该值时，分页器会隐藏。 如果不指定，则一直显示。

    //翻页时会调用该方法，参数 no 是当前页码
    change: function (no) {

        console.log('pager no is change to: ', no);
        //todo 在这里写你的逻辑，比如去请求后台拿当前页的数据

    },

    //控件发生错误时会调用该方法，比如输入的页码有错误时
    error: function (msg) {
        SMS.Tips.warn(msg, 2000);
    }
});