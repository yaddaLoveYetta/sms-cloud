

(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = require('API');
    var FilterData = require('FilterData');
    var Filter = require('Filter');
    var OrderPagination = require('OrderPagination');

    var OrderListData = require('OrderListData');
    var OrderList = require('OrderList');
    var GoodsInfo = require('GoodsInfo');

    var Sorting = require('Sorting');
    var ButtonList = require('ButtonList');

    function fillOrders() {
        var args = {
            filter: Filter.getSelectedOptions(),
            pager: { "page": 1, "size": OrderPagination.getSize(), "order": Sorting.getListString() }
        };
        //API.list(args, function (data) {

        var data;
            OrderListData.load(data, function (data) {

                OrderList.render(data);

            }, function (data) {
                Sorting.render({
                    id: 'sort-panel',
                    sq: data.sq,
                    allsq: data.allsq,
                    sorting: function (sortString) {
                        console.log(sortString);
                    },
                    saveOptions: function (sortingList) {

                        Sorting.sortingRender(data.sq);//render排序控件

                    }
                });
            }, function (data) {
                OrderPagination.render(data);
            });

        //});
    }

    ButtonList.render();

    API.filterList({ funcID: 30102 }, function (data) {

        FilterData.loadFilterData(data, function (list) {

            Filter.render({
                id: 'filter-panel',
                shown: 4,
                list: list,
                pathList: FilterData.pathList(),
                search: function (selectedItemList) {

                    var args = {
                        filter: selectedItemList,
                        pager: { "page": 1, "size": OrderPagination.getSize(), "order": Sorting.getListString() }
                    };

                    API.list(args, function (data) {

                        SMS.Tips.success('数据加载完成', 2000);
                        OrderList.render(data.orders);
                        OrderPagination.render(data.pager);
                    });


                },
                changeOptions: function (selectedOptions) {

                    //Filter.load(list.showns); //渲染筛选控件
                }
            });

            fillOrders();
        });

    });

    
    

    OrderList.on({
        'spread-detail': function (index) {
            OrderListData.loadGoodsInfo(function (data) {
                GoodsInfo.render(index, data);
            });
        }
    });



})();