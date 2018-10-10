/**
 * Created by yadda on 2017/9/18.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var Iframe = SMS.require('Iframe');
    var BL = SMS.require('ButtonList');

    var Iframes = require('Iframes');
    var Header = require('Header');
    var Operation = require('Operation');

    var activedIndex = 0; // 当前显示的附件下标

    var dialog = Iframe.getDialog();

    var classId;
    var items = []; // all items
    var showItems = []; // current show items

    if (dialog) {

        var data = dialog.getData() || {};
        items = data.showItems;
        classId = data.classId;
        console.log(items);
    }

    showItems = items = arrangeShowItems(items);

    if (showItems.length === 0) {
        return;
    }

    /**
     * 将每个可显示的附件整理成list
     */
    function arrangeShowItems(items) {

        var list = [];

        $.Array.each(items, function (item, index) {

            $.Array.each(item.attachments, function (attachment, index) {

                var fileName = attachment.path;

                if (fileName && $.String.endsWith(fileName, '.pdf', true)) {
                    // pdf
                    list.push({
                        'id': attachment.id || '',
                        'parent': attachment.parent,
                        'number': item.number || '',
                        'name': item.name || '',
                        'type': item.type || '',
                        'typeName': item.typeName,
                        'beginDate': item.validityPeriodBegin || '',
                        'endDate': item.validityPeriodEnd || '',
                        'check': item.check,
                        'src': "../../../lib/pdfjs/web/viewer.html?file=" + encodeURIComponent(url),
                    });
                } else if (fileName && ( $.String.endsWith(fileName, '.jpg', true) || $.String.endsWith(fileName, '.jpeg', true) || $.String.endsWith(fileName, '.png', true) || $.String.endsWith(fileName, '.gif', true)  )) {
                    // pic
                    list.push({
                        'id': attachment.id || '',
                        'parent': attachment.parent,
                        'number': item.number || '',
                        'name': item.name || '',
                        'type': item.type || '',
                        'typeName': item.typeName,
                        'beginDate': item.validityPeriodBegin || '',
                        'endDate': item.validityPeriodEnd || '',
                        'check': item.check,
                        'src': "picture-view/index.html?file=" + encodeURIComponent(fileName),
                    });
                }

            });

        });
        return list;
    }

    var blConfig = {
        container: '#div-button-list',
        fields: {
            text: 'text',
            child: 'items',
            callback: 'click',
            route: 'name'
        },
        textKey: 'text',
        routeKey: 'name',
        iconKey: 'icon',
        autoClose: true,
        items: [
            {
                text: '通过',
                name: 'check',
                icon: 'icon-Success',
            },
            {
                text: '不通过',
                name: 'uncheck',
                icon: 'icon-jujue'
            },
            {
                text: '上一个',
                name: 'previous',
                icon: 'icon-shangyige'
            },
            {
                text: '下一个',
                name: 'next',
                icon: 'icon-xiayige'
            }, {
                text: '显示未通过',
                name: 'showUnCheck',
                icon: 'icon-jujue',
                items: [
                    {
                        text: '显示已通过',
                        name: 'showCheck',
                        icon: 'icon-Success'
                    }, {
                        text: '显示全部',
                        name: 'showAll',
                        icon: 'icon-icon-1'
                    }
                ]
            }
        ]
    };

    var ButtonList = new BL(blConfig);

    ButtonList.render();

    Iframes.render();

    ButtonList.on('click', {
        'previous': function () {
            var item = getItem(-1);
            //Iframes.add(item);
            Header.render(item);
        },
        'next': function () {
            var item = getItem(1);
            //Iframes.add(item);
            Header.render(item);
        },
        'check': function () {
            var item = getItem();
            Operation.check({
                'classId': classId,
                'id': item.id,
                'entryId': item.entryId,
                'type': 1,
            }, function () {
                item.check = 1;
                Header.render(item);
                SMS.Tips.success('操作成功！', 500);

                setTimeout(function () {
                    var item_next = getItem(1);
                    if (item_next === item) {
                        return;
                    }
                    //Iframes.add(item_next);
                    Header.render(item_next);
                }, 500)
            });

        },
        'uncheck': function () {
            var item = getItem();
            Operation.check({
                'classId': classId,
                'id': item.id,
                'entryId': item.entryId,
                'type': 0,
            }, function () {
                item.check = 0;
                Header.render(item);
                SMS.Tips.success('操作成功！', 500);

                setTimeout(function () {
                    var item_next = getItem(1);
                    if (item_next === item) {
                        return;
                    }
                    //Iframes.add(item_next);
                    Header.render(item_next);
                }, 500)

            });

        },
        'showUnCheck': function (item, index) {
            // 显示未通过审核的证件
            showItems = $.Array.grep(items, function (item, index) {
                return !item.check;
            });
            Iframes.clear();
            Header.render(showItems[0]);
            Iframes.add(showItems[0]);
        },
        'showCheck': function (item, index) {
            // 显示通过审核的证件
            showItems = $.Array.grep(items, function (item, index) {
                return !!item.check;
            });
            Iframes.clear();
            Header.render(showItems[0]);
            Iframes.add(showItems[0]);
        },
        'showAll': function (item, index) {
            // 显示全部
            showItems = items
            Iframes.clear();
            Header.render(showItems[0]);
            Iframes.add(showItems[0]);
        }

    });

    function getItem(step) {

        step = step || 0;

        activedIndex = activedIndex + step;

        if (activedIndex >= showItems.length) {
            SMS.Tips.success('我是有底线的……', 500);
            activedIndex = showItems.length - 1;
        }
        if (activedIndex < 0) {
            SMS.Tips.success('到头了,不要再顶了……', 500);
            activedIndex = 0;
        }

        return showItems[activedIndex];
    }

    Header.render(showItems[0]);

    Header.on('done',function (item) {
        Iframes.add(item); // 默认显示第一个附件
    });



})();