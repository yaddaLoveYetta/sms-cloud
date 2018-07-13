

/**
* 树形结构的数据模块
* 
*/
define('CascadeMenus/Tree', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var YWTC = require('YWTC');


    //用深度优先的遍历方式把树形结构的数据线性化成一个一维的数组。
    function linearize(tree, key, fn) {

        //重载 linearize([], key) 的情况
        if (tree instanceof Array) { 
            var list = $.Array.keep(tree, function (item, index) {
                return linearize(item, key, fn); //递归
            });
            return $.Array.reduceDimension(list); //降维
        }

        //对单项进行线性化，核心算法
        //linearize({}, key) 的情况

        var items = tree[key];

        if (!items) { //叶子结点
            fn && fn(tree);
            return tree;
        }

        var list = $.Array.keep(items, function (item, index) {
            return linearize(item, key, fn);
        });

        list = $.Array.reduceDimension(list); //降维
        fn && fn(tree, items);

        return [tree].concat(list);

    }


    function toArray(tree) {

        var nodes = linearize(tree, 'items', function (node, items) {

            if (!items) { //叶子结点
                node.isLeaf = true;
                return;
            }

            //普通结点
            node.isLeaf = false;

            $.Array.each(items, function (item, index) {
                item.parent = node;
            });
            
        });

        //分配 id，用 index 作为其 id，方便以后检索。
        $.Array.each(nodes, function (item, index) {
            item.id = index;
        });

        return nodes;

    }


    function getGroups(tree, indexes) {

        //完整的路径 index 数组。 第 0 级(顶级)的 index 总是 0
        var fullIndexes = [0].concat(indexes);

        var items = [tree];

        var groups = $.Array.keep(fullIndexes, function (index, level) {

            var parent = items[index];

            items = parent.items;


            return $.Array.keep(items, function (item, i) {
                return $.Object.extend({}, item);
            });
        });

        $.Array.each(indexes, function (index, level) {

            var group = groups[level];
            var item = group[index];

            item.actived = true;
        });

        return groups;

    }


    function getTopNos(indexes) {

        //完整的路径 index 数组。 第 0 级(顶级)的 index 总是 0
        indexes = [0].concat(indexes);

        return $.Array.keep(indexes, function (index, level) {

            var parents = indexes.slice(0, level + 1);

            return $.Array.sum(parents);

        });
    }


    function getParents(item) {
        var items = [];

        do {
            items.push(item);
            item = item.parent;
        } while (item);

        items.reverse();

        return items;
    }



    return {
        toArray: toArray,
        getGroups: getGroups,
        getTopNos: getTopNos,
        getParents: getParents,
    };

});






    