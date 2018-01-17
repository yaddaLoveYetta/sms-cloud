


define('selector', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var label;
    var templates = document.getElementById('div-samples');
    var samples = $.String.getTemplates(templates.innerHTML, [
        {
            'name': 'selector',
            'begin': '<!--',
            'end': '-->'
        }
    ]);

    var isName = true;


    //默认配置信息
    var defaults = {
        title: '资料选取',
        height: 450,
        width: 700,
        targetList: {   //跳转对应关系
            1: '',
            2: './html/support-data/index.html',
        },
        typeID: '',
        multiSelect: false,
        hasbreadcrumbs: true,
        pageSize: 10,
    };


    //选中元素的ID
    var outData = [];


    function create(div, config) {

        render(div);
        bindEvents(div, config);

    }


    function render(div) {

        div.innerHTML = samples.selector;

    }


    function bindEvents(div, config) {

        var meta = $.Object.extendDeeply(defaults, config);
        var url = meta.targetList[config.targetType];

        $(div).delegate('[data-role="btn"]', 'click', function () {
            SMS.use('Dialog', function (Dialog) {
                var dialog = new Dialog({
                    id: meta.id,
                    title: meta.title,
                    url: url,
                    width: meta.width,
                    height: meta.height,

                    data: {
                        typeID: meta.typeID,
                        multiSelect: meta.multiSelect,
                        pageSize: meta.pageSize,
                        needNav: meta.needNav
                    },

                    ok: function () {

                    },
                    cancel: function () {

                    }
                });

                dialog.showModal();

                dialog.on({
                    remove: function () {
                        setData(dialog.getData());
                        $('[data-role="label"]').focus();
                    }
                });
            });
        });

        $(div).delegate('[data-role="label"]', {
            'focus': function () {
                var self = this;
                if (outData[0].number) {
                    self.value = outData[0].number;
                }
            },
            'blur': function () {
                var self = this;
                if (outData[0].name) {
                    self.value = outData[0].name;
                }
            }
        });

    }


    //传入数据，仅支持传一条数据
    function setData(data) {

        if (defaults.multiSelect) {

        }
        else {
            var dataStr = '';

            //$.Object.extend(outData, data);
            outData = data;

            label = $('[data-role="label"]');
            if (outData[0].name) {
                label[0].value = outData[0].name;
            }
        }
    }


    //传出数据
    function getData(data) {
        return outData;
    }


    return {
        create: create,
        setData: setData,
        getData: getData
    }
});