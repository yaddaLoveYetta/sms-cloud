

define('OrderPagination', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    //if (top.location.href == location.href) {
    //    alert('请在主控台中打开本页面');
    //    return;
    //}

    var current = 0;
    var size = 20;
    var total = 0;

    var tid = -1;

    var emitter = MiniQuery.Event.create();

    function render(pager) {

        if (pager) {
            current = pager.page || 0;
            size = pager.size || 20;
            total = pager.total || 0;

            SMS.Pagers.create({

                //分页控件的容器
                container: {
                    simple: '#div-pager-simple',    //简易分页
                    normal: '#div-pager-normal'     //标准分页
                },

                current: current, //当前激活的页码，默认为 1
                size: size,    //每页的大小，即每页的记录数
                total: total, //总的记录数，应该从后台取得该值
                hideIfLessThen: 2, //总页数小于该值时，分页器会隐藏。 如果不指定，则一直显示。

                //翻页时会调用该方法，参数 no 是当前页码
                change: function (no) {
                    console.log('pager no is change to: ', no);
                    //todo 在这里写你的逻辑，比如去请求后台拿当前页的数据

                    current = no;

                    clearTimeout(tid);

                    tid = setTimeout(function () {
                        emitter.fire('change', [no, size]);
                    }, 300);

                    console.dir(this);
                },

                //控件发生错误时会调用该方法，比如输入的页码有错误时
                error: function (msg) {
                    SMS.Tips.warn(msg, 2000);
                }

            });
        }
        
    }

    return {
        render: render,
        getCurrent: function () {
            return current;
        },
        getSize: function () {
            return size;
        },
        getTotal: function () {
            return total;
        },
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }


});

