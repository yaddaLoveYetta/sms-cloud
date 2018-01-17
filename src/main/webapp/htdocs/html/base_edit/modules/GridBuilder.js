/**
 * 编辑处理逻辑模块
 */
define('GridBuilder', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    function getColModel(field) {
        var model = {};
        model.name = field.FKey;
        model.label = field.FName;
        model.width = field.FShowWidth;
        model.title = true;
        model.editable = (field.FCtrlType == 6);// ((field.enableMask & 1) == 1 || (field.enableMask & 2) == 2);

        model.hidden = ((field.FDisplay & 1) != 1);
        model.tabIndex = field.FIndex;

        if (field.FCtrlType == 6) {
            //if (field.FLookUpType == 1) {
            //			model.name = field.FKey + '_DspName';
            model.edittype = 'custom';
            function element(value, options) {
                var el = $('.' + field.FKey + 'Auto')[0];
                return el;
            };
            function value(elem, operation, value) {
                if (operation === 'get') {
                    return "";
                } else if (operation === 'set') {
                    $('input', elem).val(value);
                }
            };
            function handle() {
                $('#initCombo').append($('.' + field.FKey + 'Auto').val(''));
            };

            triggerClass = 'ui-icon-ellipsis';
            if (field.FCtrlType == 5) {
                triggerClass = 'ui-icon-triangle-1-s';
            } else if (field.FCtrlType == 6) {
                triggerClass = 'ui-icon-ellipsis';
            }
            model.editoptions = {
                custom_element: element,
                custom_value: value,
                handle: handle,
                trigger: triggerClass,
            };

            model.formatter = function (val, opt, row) {
                //				if (row[field.FKey + '_DspName']) {
                //					return row[field.FKey + '_DspName'];
                //				} else

                if (val) {
                    return val;
                } else {
                    return '';
                }
            };

            model.data = field;
        }

        if (field.FCtrlType == 15 && !model.hidden) {
            model.formatter = function (cellvalue, options, rowObject) {
                if (cellvalue) {
                    return '<img class="assisPropImg" src="' + cellvalue + '"/>';
                } else if (rowObject[field.FKey]) {
                    return '<img class="assisPropImg" src="' + rowObject[field.FKey] + '"/>';
                } else {
                    return '';
                }
                //return '<img class="assisPropImg" src="' + getImagePath(cellvalue, rowObject) + '"/>';
            };
            model.unformat = function (cellvalue, options, cell) {
                return $('img', cell).attr('src');
            };
            model.align = 'center';

            //todo:gaozf 临时处理
            model.editable = false;
            model.hidden = true;
        }

        return model;
    }

    function getConfig(fields, gridConfig, names, isNeedOpt) {
        var cNames = [];
        var cModel = [];
        if (isNeedOpt) {
            cModel = [{
                name: 'bos_modify',
                label: 'bos_modify',
                hidden: true
            }, {
                name: 'operate',
                label: ' ',
                width: 40,
                fixed: true,
                formatter: function (val, opt, row) {
                    var html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span><span class="ui-icon ui-icon-trash" title="删除行"></span></div>';
                    return html_con;
                },
                align: "center",
            }];
        }
        for (var key in fields) {
            var field = fields[key];

            if (field.FLookUpType == 1) {
                var keyModel = {
                    name: field.FKey,
                    label: field.FName,
                    tabIndex: field.FIndex,
                    hidden: true
                };
                cModel.push(keyModel);

                if (field.FCtrlType == 6) {
                    var keyNmbModel = {
                        name: field.FName,// + '_NmbName',
                        label: field.FKey,// + '_NmbName',
                        tabIndex: field.FIndex,
                        hidden: true
                    };
                    cModel.push(keyNmbModel);
                }
            }

            var model = getColModel(field);
            cModel.push(model);
        }

        cModel = sortModels(cModel);

        for (var m in cModel) {
            cNames.push(cModel[m].label);
        }

        gridConfig.colNames = cNames;
        gridConfig.colModel = cModel;

        gridConfig.fnAfterEditCell = function (rowid, cellname, value, iRow, iCol) {
            var rowdata = $("#" + gridConfig.gridName).getRowData(rowid);
            rowdata[cellname] = value;
            console.log(rowdata);
            $("#" + iRow + "_" + cellname).val(value);
            $('#initCombo').data('selectedRow', rowid);
            $('#initCombo').data('selectedVal' + rowid, rowdata);
            gridConfig.fnAfterEditCell_Before && gridConfig.fnAfterEditCell_Before(rowid, cellname, value);
            $("#" + iRow + "_" + name_dsp, "#" + gridConfig.gridName).val(value);

        };
        gridConfig.fnAfterSaveCell = function (rowid, cellname, val, iRow, iCol) {
            var gridData = $('#initCombo').data('selectedVal' + rowid);
            $("#" + gridConfig.gridName).jqGrid('setRowData', rowid, gridData);
        };

        gridConfig.fnLoadComplete = function (data) {
            //var rows = data['rows'];
            //var len = rows.length;
            //for (var i = 0; i < len; i++) {
            //    var tempId = i + 1, row = rows[i];
            //    if ($.isEmptyObject(rows[i])) {
            //        break;
            //    };
            //    $('#' + tempId).data('rowInfo', row);
            //};
        };

        return gridConfig;
    }

    function sortModels(models) {
        for (var i = 0; i < models.length; i++) {
            for (var j = i + 1; j < models.length; j++) {
                if (models[i].tabIndex > models[j].tabIndex) {
                    var tmp = models[i];
                    models[i] = models[j];
                    models[j] = tmp;
                }
            }
        }

        return models;
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
