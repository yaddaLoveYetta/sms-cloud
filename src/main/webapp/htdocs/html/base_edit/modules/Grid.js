/**
 * 表体数据模块
 *
 */
define('Grid', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    //FormEdit.getSelectors
    var FormEdit = require('FormEdit');

    var mapper = new $.Mapper();
    var guidKey = $.Mapper.getGuidKey();

    /**
     * 表格构造器。
     */
    function Grid(gridId) {

        var id = $.String.random();
        this[guidKey] = 'Grid-' + id;

        var emitter = new MiniQuery.Event(this);

        var meta = {
            'grid': $('#' + gridId),
            'deleteRows': [],
            'newId': 2,
            'primaryKey': '',
            'inited': false,
            'curCell': {
                row: null,
                col: null
            },
            'oldData': null,

            'initGrid': initGrid,
            'saveGrid': saveGrid,
            'getGridData': getGridData,
            'snapShot': snapShot,

            'getNeedSaveKeys': getNeedSaveKeys,
            'getMustInputFields': getMustInputFields,
            'getPrimaryKey': getPrimaryKey,
            'emitter': emitter
        };

        mapper.set(this, meta);

        var self = this;

    };

    function initGrid(cfg, data) {

        var bdGrid = cfg.grid;
        var config = cfg.config;
        var deleteRows = cfg.deleteRows;
        var newId = cfg.newId;
        var primaryKey = cfg.primaryKey;
        var emitter = cfg.emitter;

        //var curRow, curCol;

        if (!data) {
            data = [];
        }
        if (data.length < 1) {
            data.push({
                id: 'num_1'
            });
        } else {
            newId = data.length + 1;
        }
        ;

        bdGrid.jqGrid({
            data: data,
            datatype: 'clientSide', //'local',
            colNames: config.colNames,
            colModel: config.colModel,
            width: config.width,
            height: config.height,
            cmTemplate: {
                sortable: false
            },
            rownumbers: true,
            cellEdit: true,
            //altRows: true,
            shrinkToFit: true,
            forceFit: true,
            cellsubmit: 'clientArray',
            loadonce: true,
            triggerAdd: false,

            afterEditCell: function (rowid, cellname, value, iRow, iCol) {
                cfg.curCell.row = iRow;
                cfg.curCell.col = iCol;
                console.log("abcd");
                config.fnAfterEditCell(rowid, cellname, value, iRow, iCol);
            },

            afterSaveCell: function (rowid, name, val, iRow, iCol) {

                console.log("abcd宿舍");
                config.fnAfterSaveCell(rowid, name, val, iRow, iCol);
            },

            loadComplete: function (data) {
                config.fnLoadComplete(data);
            },
        });

        //bdGrid.jqGrid('setGridParam', { cellEdit: true });

        //添加行
        bdGrid.on('click', '.ui-icon-plus', function (e) {
            saveGrid(bdGrid, cfg.curCell);

            var rowId = $(this).parent().data('id');
            var datarow = {
                id: 'num_' + newId
            };
            var su = bdGrid.jqGrid('addRowData', 'num_' + newId, datarow, 'after', rowId);
            if (su) {
                $(this).parents('td').removeAttr('class');
                $(this).parents('tr').removeClass('selected-row ui-state-hover');
                bdGrid.jqGrid('resetSelection');
                newId++;
            }
        });
        //删除行
        //$('.grid-wrap').on('click', '.ui-icon-trash', function (e) {
        bdGrid.on('click', '.ui-icon-trash', function (e) {
            saveGrid(bdGrid, cfg.curCell);

            if (bdGrid.children('tbody').children('tr').length === 2) {
                var rowId = $(this).parent().data('id');
                var row = bdGrid.jqGrid('getRowData', rowId);
                if (row[primaryKey]) {
                    deleteRows.push(row);
                }
                ;

                bdGrid.jqGrid("clearGridData");
                bdGrid.jqGrid('addRowData', 'num_1', {
                    id: 'num_1'
                });
                return false;
            }
            var rowId = $(this).parent().data('id');
            var row = bdGrid.jqGrid('getRowData', rowId);
            var su = bdGrid.jqGrid('delRowData', rowId);

            if (su && row[primaryKey]) {
                deleteRows.push(row);
            }
            ;
        });

        //取消分录编辑状态
        $(document).bind('click.cancel', function (e) {
            if (!$(e.target).closest(".ui-jqgrid-bdiv").length > 0) {
                saveGrid(bdGrid, cfg.curCell);
            }
            ;
        });

        //$('.grid-wrap').on('click', '.ui-icon-ellipsis', function (e) {
        bdGrid.on('click', '.ui-icon-ellipsis', function (e) {
            console.log("ellipsis");
            var $_comboAuto = $(this).prev();
            var gridRow = bdGrid.jqGrid('getGridParam');
            var rowNumb = gridRow.selrow;
            var colModels = gridRow.colModel;
            var col = cfg.curCell.col;

            showF7(colModels[col].data, '', emitter, $_comboAuto, rowNumb);
        });

        bdGrid.on('click', '.ui-icon-triangle-1-s', function (e) {
            var $_comboAuto = $(this).prev();
            setTimeout(function () {
                $_comboAuto.trigger('click');
            }, 10);
        });
    }

    function showF7(field, filterID, emitter, container, rowNumb) {
        var formClassID = field.FLookUpClassID;
        var url = $.Url.setQueryString('./html/base/index.html', 'classID', formClassID);
        var condition = {};
        var companySelector = FormEdit.getSelectors("FCompany");
        if (companySelector && companySelector.getData() && companySelector.getData()[0] && companySelector.getData()[0].ID) {
            condition["FCompany"] = {
                'andOr': 'and',
                'leftParenTheses': '(',
                'fieldKey': "FCompany",
                'logicOperator': '=',
                'value': companySelector.getData()[0].ID,
                'rightParenTheses': ')',
                needConvert: false
            }
        } else {
            SMS.Tips.info('请先选择物业公司', 1500);
            return false;
        }
        var title = '';
        switch (formClassID.toString()) {
            case '13002':
                title = '选择车场';
                break;
            default:
                break;
        }

        SMS.use('Dialog', function (Dialog) {
            var dialog = new Dialog({
                title: title,
                url: url,
                width: 900,
                height: 520,
                button: [{
                    value: '取消',
                    className: 'sms-cancel-btn'
                }, {
                    value: '确认',
                    className: 'sms-submit-btn',
                    callback: function () {
                        this.isSubmit = true;
                    }
                }],

                data: {
                    filterID: filterID,
                    multiSelect: false,
                    hasBreadcrumbs: false,
                    pageSize: 10,
                    conditions: condition
                },
            });

            //默认关闭行为为不提交
            dialog.isSubmit = false;

            dialog.showModal();

            dialog.on({
                remove: function () {
                    var data = dialog.getData();
                    if (dialog.isSubmit && data[0].hasOwnProperty('ID')) {
                        data.container = container;
                        data.row = rowNumb;
                        emitter.fire('f7Selected', [data]);
                    }
                }
            });
        });
    }

    function saveGrid(bdGrid, curCell) {
        if (curCell && curCell.row != null && curCell.col != null) {
            bdGrid.jqGrid("saveCell", curCell.row, curCell.col);
            curCell.row = null;
            curCell.col = null;
        }
    }

    function snapShot(bdGrid) {

        var data = {};

        var cols = bdGrid.jqGrid('getGridParam', 'colModel');
        var ids = bdGrid.jqGrid('getDataIDs');

        for (var i = 0, len = ids.length; i < len; i++) {
            var id = ids[i];
            var row = bdGrid.jqGrid('getRowData', id);

            for (var j = 0, l = cols.length; j < l; j++) {
                var key = cols[j]['name'];
                if (key != 'rn' && key != 'bos_modify' && key != 'operate') {
                    data[id + '$' + cols[j]['name']] = row[cols[j]['name']];
                }
            }
        }
        //console.dir(data);
        return data;
    }

    function getNeedSaveKeys(metaData, entryIndex) {
        var keys = [];

        var fields = metaData['formFields'][entryIndex];
        for (var item in fields) {
            var field = fields[item];
            if (field['needSave']) {
                keys[field['fieldKey']] = field;
            }
        }

        return keys;
    }

    function getMustInputFields(metaData, entryIndex) {
        var keys = [];

        var fields = metaData['formFields'][entryIndex];
        for (var item in fields) {
            var field = fields[item];
            if (field['mustInput']) {
                keys.push(field);
            }
        }

        keys = sortFields(keys);

        return keys;
    }

    function sortFields(fields) {
        for (var i = 0; i < fields.length; i++) {
            for (var j = i + 1; j < fields.length; j++) {
                if (fields[i].tabIndex > fields[j].tabIndex) {
                    var tmp = fields[i];
                    fields[i] = fields[j];
                    fields[j] = tmp;
                }
            }
        }

        return fields;
    }

    function getPrimaryKey(metaData, entryIndex) {
        var entry = metaData['formEntries'][entryIndex];
        return entry['FPrimaryKey'];
    }

    function getGridData(bdGrid, metaData, needSaveKeys, mustInputFields) {
        var gridData = [];
        var ids = bdGrid.jqGrid('getDataIDs');

        for (var i = 0, len = ids.length; i < len; i++) {
            var id = ids[i];
            var row = bdGrid.jqGrid('getRowData', id);

            var valid = true;
            var mustInputCaptions = [];
            for (var validIndex in mustInputFields) {
                if (row[mustInputFields[validIndex].fieldKey] == '') {
                    var caption = mustInputFields[validIndex].caption;
                    mustInputCaptions.push(caption);
                    valid = false;
                }
            }

            var itemData = {};

            //该行记录有值时，需要提示必录项
            var hasValue = false;
            for (var key in needSaveKeys) {
                if (row[key] !== '') {
                    itemData[key] = row[key];
                    hasValue = true;
                } else {
                    itemData[key] = needSaveKeys[key].defaultValue;
                }
            }

            //当为空行时，跳过无效分录
            if (!valid && !hasValue) {
                continue;
            }

            //有必录项未录，同时有值时
            if (!valid && hasValue) {
                itemData.bos_mustInput = 'true';
                itemData.bos_mustInputCaptions = mustInputCaptions;
            }

            itemData.bos_modify = row.bos_modify;

            //			gridData.push(itemData); 修改需要全部数据，不只是要主键
            gridData.push(row);
        }
        ;

        return gridData;
    }

    function getInt(str) {
        return str.match(/\d/g).join("");
    }

    //表格实例方法
    Grid.prototype = {

        constructor: Grid,

        /*
         config.editColumnName
         config.colNames
         config.colModel
         config.width
         config.height
         config.fnAfterEditCell(rowid, cellname, value, iRow, iCol)
         */
        render: function (config, entryData, metaData, entryIndex) {
            var meta = mapper.get(this);

            if (!entryIndex) {
                entryIndex = 1;
            }

            meta.config = config;
            meta.metaData = metaData;
            meta.primaryKey = meta.getPrimaryKey(meta.metaData, entryIndex);

            if (meta.inited) {
                meta.grid.jqGrid('setGridParam', {
                    data: entryData[entryIndex]
                }).trigger('reloadGrid', [{
                    page: 1
                }]);
                meta.oldData = meta.snapShot(meta.grid);
                return;
            }

            var entryCount;
            if (entryData) {
                meta.initGrid(meta, entryData[entryIndex]);
            }

            if (!entryData || entryCount == 0) {
                meta.initGrid(meta);
            }
            meta.inited = true;
            meta.oldData = meta.snapShot(meta.grid);
        },

        clear: function () {
            var meta = mapper.get(this);

            var bdGrid = meta.grid;

            bdGrid.jqGrid("clearGridData");
            bdGrid.jqGrid('addRowData', 'num_1', {
                id: 'num_1'
            });
            meta.oldData = meta.snapShot(meta.grid);
        },

        save: function () {
            var meta = mapper.get(this);

            meta.saveGrid(meta.grid, meta.curCell);
        },

        clearDeleteRows: function () {
            var meta = mapper.get(this);

            meta.deleteRows.splice(0, meta.deleteRows.length);
        },

        getGridDatas: function (entryIndex) {
            var meta = mapper.get(this);

            meta.saveGrid(meta.grid, meta.curCell);

            var needSaveKeys = meta.getNeedSaveKeys(meta.metaData, entryIndex);
            var mustInputFields = meta.getMustInputFields(meta.metaData, entryIndex);
            var primaryKey = meta.primaryKey;

            var gridData = meta.getGridData(meta.grid, meta.metaData, needSaveKeys, mustInputFields);
            var deletedData = meta.deleteRows;

            var addDatas = [];
            var deleteDatas = [];
            var updateDatas = [];
            var errorDatas = [];
            /*删除数据获取*/
            for (var index in deletedData) {

                var row = deletedData[index];
                //deleteData[primaryKey] = row, updateDatas.push(deleteData);
                deleteDatas.push(row);
            }
            /*错误数据验证*/
            for (var index in gridData) {
                var row = gridData[index];
                console.log(row);
                if (row.bos_mustInput === 'true') {
                    for (var captionIndex in row.bos_mustInputCaptions) {
                        if (!$.Array.contains(errorDatas, row.bos_mustInputCaptions[captionIndex])) {
                            errorDatas.push(row.bos_mustInputCaptions[captionIndex]);
                        }
                    }

                }
                /*添加数据获取  [FEntryID] 为空表示新增数据*/

                if (!row['FEntryID']) {
                    addDatas.push(row);
                }
                /*修改数据获取  [FEntryID]不为空表示修改数据*/
                if (!!row['FEntryID']) {
                    updateDatas.push(row);
                }
                //				var addData = $.Object.grep(row, function(key, value) {
                //					if( key == 'FEntryID' && value){
                //						return row;
                //					}
                //				});
                //				addDatas.push(addData);
                //				
                //				var updateData = $.Object.grep(row, function(key, value) {
                //					 if(key == 'FEntryID' && (!!value)){
                //						 return row;
                //					 }
                //				});
                //				updateDatas.push(updateData);			
            }

            return {
                'add': addDatas,
                'delete': deleteDatas,
                'update': updateDatas,
                'error': errorDatas,
            };
        },

        isGridChanged: function () {
            var meta = mapper.get(this);
            var curData = meta.snapShot(meta.grid);

            if ($.Object.getKeys(curData).length != $.Object.getKeys(meta.oldData).length) {
                return true;
            }
            for (var key in curData) {
                if (curData[key] != meta.oldData[key]) {
                    console.log('old:   ' + key + ' ' + meta.oldData[key]);
                    console.log('new:   ' + key + ' ' + curData[key]);
                    return true;
                }
            }

            return false;
        },

        tableOperate: function (val, opt, row) {
            var html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span><span class="ui-icon ui-icon-trash" title="删除行"></span></div>';
            return html_con;
        },
        setRowData: function (row, data) {
            var x = $('#initCombo').data('selectedVal' + row);
            var meta = mapper.get(this);
            var gridData = data;
            for (var d in data) {
                if (typeof data[d] == 'undefined') {
                    gridData = x;
                    break;
                }
            }
            meta.grid.jqGrid('setRowData', row, gridData);

        }, on: function (name, fn) {

            var meta = mapper.get(this);
            var emitter = meta.emitter;
            var args = [].slice.call(arguments, 0);

            emitter.on.apply(emitter, args);
        },
    };

    //静态方法
    return Grid;

});
