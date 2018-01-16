


var $ = require('./lib/MiniQuery');
var File = require('./lib/File');
var FS = require('fs');



function convert(node) {

    if (node instanceof Array) {
        return $.Array.keep(node, function (item, index) {
            return convert(item);
        });
    }

 
    var items = node.children;
    if (!items || items.length == 0) {
        return node;
    }

    node['children'] = undefined;
    node['items'] = convert(items);

    return node;


}


var list = require('./data.js');

FS.writeFileSync('./list.json', JSON.stringify(list, null, 4));

var a = convert(list);
console.dir(a);

var json = JSON.stringify(a);

FS.writeFileSync('./tree.json', a);