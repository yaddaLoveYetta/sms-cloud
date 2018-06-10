/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/25 10:15
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Tree = require('Tree');
    var List = require('List');
    var Pager = require('Pager');

    //默认配置
    var defaults = {
        pageSize: 10,
        sizes: [10, 20, 30],
        pageNo: 1,
        hasBreadcrumbs: true,
        multiSelect: true
    };

    Tree.on('messageTypeChange', function (type) {

        List.render({
                type: type,
                pageNo: defaults.pageNo,
                pageSize: defaults.pageSize
            }, function (total, pageSize) {

                Pager.render({
                    size: pageSize,
                    sizes: defaults.sizes,
                    total: total,
                    change: function (no, pageSize) {
                        defaults.pageSize = pageSize;
                        List.render({
                            type: type,
                            pageNo: no,
                            pageSize: defaults.pageSize
                        });
                    }
                });

            }
        )
        ;
    });

    // 渲染消息类别树
    Tree.render();
})();