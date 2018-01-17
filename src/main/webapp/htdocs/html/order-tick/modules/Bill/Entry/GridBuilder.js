/**
 * 编辑处理逻辑模块
 */
define('Bill/Entry/GridBuilder', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    /**
     * 构建grid model
     * @param field 字段模板
     * @param editAble 是否可编辑
     * @returns {{}}
     */
    function getColModel(field, editAble) {

        var model = {};

        model.name = field.key;
        model.label = field.name;
        model.width = field.showWidth;
        model.title = true;
        model.editable = editAble;// ((field.enableMask & 1) == 1 || (field.enableMask & 2) == 2);
        model.hidden = ((field.display & 1) != 1);
        model.tabIndex = field.index;

        if (field.ctrlType == 6) {
            //if (field.FLookUpType == 1) {
            //
            model.name = field.key + '_DspName'; // 显示的值保存在 field.key + '_DspName' 的key中
            model.edittype = 'custom';

            function element(value, options) {
                var el = $('.' + field.key + 'Auto')[0];
                return el;
            }

            function value(elem, operation, value) {
                if (operation === 'get') {
                    return $(elem).val();
                    //return "";
                } else if (operation === 'set') {
                    $('input', elem).val(value);
                }
            }

            function handle() {
                $('#initCombo').append($('.' + field.key + 'Auto').val(''));
            }

            var triggerClass = 'ui-icon-ellipsis';

            model.editoptions = {
                custom_element: element,
                custom_value: value,
                handle: handle,
                trigger: triggerClass,
            };

            model.formatter = function (val, opt, row) {
                //				if (row[field.key + '_DspName']) {
                //					return row[field.key + '_DspName'];
                //				} else

                if (val) {
                    return val;
                } else {
                    return '';
                }
            };

            model.data = field;
        }

        if (field.ctrlType == 12) {
            // 日期
            /*
             * model.edittype = 'date'; model.formatter = "date";
             * model.formatoptions = { srcformat: 'Y-m-d H:i:s',
             * newformat: 'Y-m-d H:i:s' }
             */
            model.edittype = 'text';
            model.editrules = {required: true};
            model.editoptions = {
                size: 10, maxlengh: 10,
                dataInit: function (element) {

                    SMS.use('DateTimePicker', function (DateTimePicker) {
                        new DateTimePicker($(element), {
                            format: 'yyyy-mm-dd',
                            autoclose: true,
                            todayBtn: true,
                            todayHighlight: true,
                            timepicker: false,
                            startView: 'month',
                            minView: 2,
                        });
                    });
                }
            }
        }
        
        if (field.dataType == 1) {
            // 数字
            //model.edittype = 'text';
            model.align='right'; // 数字靠右显示
            model.editrules = {
                required: false,
                number: true
            };
            model.formatter = 'number';
            model.formatoptions = {
                decimalSeparator: ".",
                thousandsSeparator: "",
                decimalPlaces: 2,
                defaulValue: 0
            };
        }
        
        return model;
    }

    /**
     * 构造grid初始化参数
     * @param fields  单据模板
     * @param config 默认配置
     * @param showKeys 需要展现的字段-null将按照单据模板确定
     * @param editKeys 可以编辑的字段-null将不可编辑
     * @param operator 控制是否有新增，删除行功能-true：可以添加/删除 false：不出现添加/删除行功能
     * @returns {*}
     */
    function getConfig(fields, config, showKeys, editKeys, operator) {

        var cNames = [];
        var cModel = [];

        var model = {};

        if (typeof fields == 'object') {
            //重载方法
            var params = fields;

            fields = params.fields;
            config = params.defaults;
            showKeys = params.showKeys;
            editKeys = params.editKeys;
            operator = params.operator;

        }

        //按照单据模板确定
        if (!showKeys || showKeys.length == 0) {
            showKeys = $.Object.getKeys(fields);
        }

        //按照单据模板确定
        if (!editKeys || showKeys.length == 0) {
            editKeys = $.Object.getKeys(fields);
        }

        // 有需要编辑的列
        if (editKeys) {
            // 增加一列标识-方便数据控制
            model = {
                name: 'bos_modify',
                label: 'bos_modify',
                hidden: true
            };
            cModel.push(model);
        }

        // 需要有添加/删除行功能
        if (operator) {
            // 增加一列添加/删除行功能的按钮
            model = {
                name: 'operate',
                label: ' ',
                width: 40,
                fixed: true,
                formatter: function (val, opt, row) {
                    var html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span><span class="ui-icon ui-icon-trash" title="删除行"></span></div>';
                    return html_con;
                },
                align: "center",
                hidden: !operator,
            };
            cModel.push(model);
        }


        //要展示的字段
        /*        var keys = ['entryId', 'parent', 'seq', 'material', 'unit', 'qty', 'price',
         'confirmDate', 'deliveryDate', 'discountRate', 'taxRate', 'taxPrice',
         'actualTaxPrice', 'discountAmount', 'tax', 'localAmount', 'confirmQty'];*/

        for (var key in showKeys) {

            var field = fields[showKeys[key]];

            if (!field) {
                continue;
            }

            if (field.lookUpType == 1) {
                // 引用类型增加保存列-不显示-用作表格保存时取数
                var keyModel = {
                    name: field.key,
                    label: field.name,
                    tabIndex: field.index,
                    hidden: true
                };

                cModel.push(keyModel);

                if (field.FCtrlType == 6 && field.disPlayNum) {
                    // 有配置显示代码时增加代码显示
                    var keyNmbModel = {
                        name: field.name + '_NmbName',
                        label: field.key + '_NmbName',
                        tabIndex: field.index,
                        hidden: true
                    };
                    cModel.push(keyNmbModel);
                }
            }

            model = getColModel(field, $.Array.contains(editKeys, field.key));

            cModel.push(model);
        }

        cModel = sortModels(cModel);

        for (var m in cModel) {
            cNames.push(cModel[m].label);
        }

        config.colNames = cNames;
        config.colModel = cModel;

        config.fnAfterEditCell = function (rowid, cellname, value, iRow, iCol) {

            /*            var rowdata = $("#" + config.gridName).getRowData(rowid);
             rowdata[cellname] = value;
             console.log(rowdata);*/
            $("#" + iRow + "_" + cellname).val(value);
            /*            $('#initCombo').data('selectedRow', rowid);
             $('#initCombo').data('selectedVal' + rowid, rowdata);
             config.fnAfterEditCell_Before && config.fnAfterEditCell_Before(rowid, cellname, value);
             $("#" + iRow + "_" + name_dsp, "#" + config.gridName).val(value);*/

        };
        config.fnAfterSaveCell = function (rowid, cellname, val, iRow, iCol) {
            /*            var gridData = $('#initCombo').data('selectedVal' + rowid);
             //gridData[cellname] = val;
             $("#" + config.gridName).jqGrid('setRowData', rowid, gridData);*/
        };


        return config;
    }

    function sortModels(models) {

        return $.Array.sort(models, function (models1, models2) {
            return models1.tabIndex > models2.tabIndex;
        });

        /*        for (var i = 0; i < models.length; i++) {
         for (var j = i + 1; j < models.length; j++) {
         if (models[i].tabIndex > models[j].tabIndex) {
         var tmp = models[i];
         models[i] = models[j];
         models[j] = tmp;
         }
         }
         }
         return models;*/
    }

    function getImagePath(path) {
        var images = ['../../../css/bd/goods/img/u199.jpg', '../../../css/bd/goods/img/u353.jpg', '../../../css/bd/goods/img/u376.jpg', '../../../css/bd/goods/img/u468.jpg'];

        if ($.Array.contains(images, path)) {
            return path;
        }

        return $.Array.randomItem(images);
    }

    return {
        getConfig: getConfig,

    };

});
