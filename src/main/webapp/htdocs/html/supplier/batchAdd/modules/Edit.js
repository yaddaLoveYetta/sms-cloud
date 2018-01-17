/**
 * 单据数据管理
 *
 */
define('Edit', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var emitter = MiniQuery.Event.create();
    var SMS = require('SMS');
    var FormEdit = require('FormEdit');

    var samples = FormEdit.getSamples(); // 原始模板

    var f7Selectors;
    var classId;
    var itemId = '';

    // 带表体单据控件
    var GridBuilder = require('GridBuilder');
    var mGrid;
    var cleanGrid = function () {
        mGrid.clear();
        var successData = {
            grid: ""
        };
        showValidInfo(successData, null);
    };

    var gridConfig = {
        gridName: 'bd-grid',
        width: 500,
        height: '100%',
        fnAfterSaveCell_After: function (rowid, data) {
            $('#bd-grid').jqGrid('setCell', rowid, 'roleName', data.name);
        },
        fnAfterEditCell_Before: function (rowId, cellName, cellValue) {
            Combo.getCombo().selectByText(cellValue, false);
        }
    };

    //jqGrid初始化
    var initGrid = function (entryData, template) {

        // 物料证件-一个证件添加多个物料
        if (classId != 3020) {
            return;
        }

        var material = document.getElementById('bd-material');
        // 删除按照模板填充的物料元素
        $('.data-table tbody').children().each(function () {

                if ($(this).children('td').eq(1).children('div').eq(0).attr('id') === 'bd-material') {
                    $(this).remove();
                }
            }
        );

        // 添加dom结构都最后
        var sample = samples['tr.table']
        var text = $.String.format(sample, {
            mustInput: $.String.format(samples["td.mustInput"], {}),
            name: '物料',
        });
        $('.data-table tbody').append(text);

        // 构造模板，模仿物料证件有物料子表
        var materialModel = template.formFields["0"]["material"]; // 主表中的物料模板
        delete template.formFields["1"]["url"];
        delete template.formFields["1"]["parent"];
        template.formFields["1"]["material"] = materialModel; // 子表模板加入物料
        //template.formEntries["1"]["primaryKey"] = "material"; // 修改子表主键-grid判断用

        if (!template.formFields["1"]) {
            return;
        }

        var defaults = {
            gridName: 'bd-grid',
            height: 'auto',
            classId: template.formClass.classId,
        };

        if (mGrid) {
            mGrid.unload();
        }
        SMS.use('Grid', function (Grid) {

            mGrid = new Grid('bd-grid');

            defaults = GridBuilder.getConfig({
                'fields': template.formFields["1"],
                'defaults': defaults,
                'operator': 3, // 新增时有添加删除按钮，编辑时有删除按钮,查看时无按钮
                'showType': 1,
            });

            defaults.getConditions = function (rowNumb, colNumb, colModels) {
                // 选择物料时要考虑供应商过滤条件
                var condition = {};
                var selector = FormEdit.getSelectors("supplier")
                var value = selector && selector.getData() && selector.getData()[0].ID || null;

                if (value) {
                    condition["supplier"] = {
                        'andOr': 'and',
                        'leftParenTheses': '(',
                        'fieldKey': "supplier",
                        'logicOperator': '=',
                        'value': value,
                        'rightParenTheses': ')',
                        needConvert: false
                    }
                }

                return condition;
            }

            mGrid.render(defaults, null, template, 1);

            mGrid.on('f7Selected', function (data) {

            });

            mGrid.on('afterEditCell', function (classId, rowid, cellname, value, iRow, iCol) {

            });

            mGrid.on('afterSaveCell', function (classId, rowid, cellname, value, iRow, iCol) {

                if (classId === 2020) {
                    // 发货单新增编辑时候值更新事件处理
                    // 下一迭代重构(应该由数据库配置字段值更新规则先)
                    switch (cellname) {
                        case 'actualQty':
                            // 实发数量变化后修改金额
                            // 1：获取物料单价
                            var price = mGrid.getCell(rowid, 'price');
                            console.log("price=" + price);
                            var amount = (value * price).toFixed(2);
                            mGrid.setCell(rowid, 'amount', amount)
                            break;
                    }

                }
            });

        });

    };

    function render(formClassId, itemID, defaultValue) {

        itemId = itemID;
        classId = formClassId;
        // FormEdit.render(formClassId, itemId, initGrid, initSelectors);
        FormEdit.render(formClassId, itemId, initGrid, initSelectors, defaultValue);
    }

    function clear() {
        itemId = '';
        FormEdit.clear();
        emitter.fire('addNew', []);
    }

    function save() {

        var errorData = {};
        var entryData = {};

        if (classId == 1001) {
            // 用户信息特殊校验逻辑
            // 供应商用户必须关联供应商
            if ((f7Selectors['type'].getData()[0].ID || '') == 'B3sMo22ZLkWApjO/oEeDOxACEAI=' && (f7Selectors['supplier'].getData()[0].ID || '') == '') {
                errorData["supplier"] = $.String.format("供应商不可为空");
            }
        }

        var entry = [];
        /*
         'flag':'1' 0删除, 1新增，2修改
         */
        var gridData = mGrid.getGridDatas(1); // 获取第一个表体数据

        var errorData = gridData["error"] || []

        if ((gridData["add"] || []).length == 0 && (gridData["update"] || []).length == 0) {
            errorData['1'] = ['无有效分录，请在列表界面选择单据操作!'];
        }


        var msgElement = document.getElementById('bd-grid-msg');
        $(msgElement).html('');

        if (!$.Object.isEmpty(errorData)) {

            var errors = '';
            // 显示错误提示
            for (var item in errorData) {
                errors = errors + '<br/>' + errorData[item].join('-');
            }
            $(msgElement).html(errors);
            if (!$(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            return;
        }


        //新增数据
        $.Array.each(gridData["add"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '1'
            };
            entry.push(addData);
        });

        //修改数据
        $.Array.each(gridData["update"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '2'
            };
            entry.push(addData);
        });

        //删除数据
        $.Array.each(gridData["delete"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '0'
            };
            entry.push(addData);
        });

        var entryData = {
            1: entry
        };

        entryData = entry;

        FormEdit.save(itemId, showValidInfo, saveSuccess, entryData, null);

    }

    function forbid(classID, operateType) {
        if (!itemId) {
            return;
        }
        FormEdit.forbid(classID, itemId, operateType);
    }

    function refresh(classID) {
        if (!itemId) {
            return;
        }
        //    cleanGrid();
        //FormEdit.render(classID, itemId, initGrid);
        FormEdit.render(classID, itemId, null);
    }

    function showValidInfo(successData, errorData) {

        for (var item in successData) {
            // 去掉错误提示
            var msgElement = document.getElementById('bd-' + item + '-msg');
            if ($(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            $(msgElement).html('');
        }
        if (errorData) {
            // 显示错误提示
            for (var item in errorData) {

                var msgElement = document.getElementById('bd-' + item + '-msg');
                if (!$(msgElement).hasClass('show')) {
                    $(msgElement).toggleClass('show');
                }
                $(msgElement).html(errorData[item]);
            }
        }
    }

    function saveSuccess(data) {

        if (itemId) {
            SMS.Tips.success('数据修改成功', 2000);
            emitter.fire('editSuccess', []);
        } else {
            itemId = data['id']; // 新增后设置itemId，单据变成修改状态
            SMS.Tips.success('数据新增成功', 2000);
            emitter.fire('addSuccess', []);
        }
        //refresh(classId, f7Selectors);
    }

    /**
     * F7控件初始化时设置个性化条件
     * @param classId 业务类别
     * @param key 标识字段的key
     */
    function initSelectors(classId, key) {

        var config = {};
        if (classId == 1001 && key == 'role') {
            // 用户角色依赖于用户类别
            config = {
                conditionF7Names: [{ //级联查询条件 多个用逗号分割
                    type: "selector",
                    target: 'type',
                    filterKey: "type",
                    valueRule: {
                        'QpXq24FxxE6c3lvHMPyYCxACEAI=': 'Ro9iCuOsVEmznmE+YZSi7hAEEAQ=',
                        'B3sMo22ZLkWApjO/oEeDOxACEAI=': 'f1sGInqJq0aUNY5MmpKM8RAEEAQ='
                    }
                }],
            };
        }
        if (classId == 3020 && key == 'material') {
            // 物料证件-物料必须是供应商中标库中的物料
            config = {
                conditionF7Names: [{
                    type: "selector",
                    target: 'supplier',
                    filterKey: "supplier",
                }],   //级联查询条件 多个用逗号分割
            };
        }
        if (classId == 3010 && key == 'supplier' && itemId) {
            // 供应商资质新增-供应商设置默认值
            config = {
                defaultValue: ''
            };
        }
        return config;
    }

    FormEdit.on({
        "FType.defaultFill": function (data) { //默认数据填充
            var userTypeId = data[0].ID;
            // UserTypeOpt.render(userTypeId);
        },
        'afterFill': function (classId, metaData, data) {

            if (classId == 1001) {
                // 用户编辑填充页面数据后-对于用户类别是系统用户的用户-锁定关联供应商控件不可用
                if (FormEdit.getSelectors('type').getData()[0]['ID'] === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    FormEdit.getSelectors('supplier').lock();
                }
            }
        }
    });

    FormEdit.on({
        'dialogChange': function (classId, key, data, selectors, metaData) {

            if (classId === 1001 && key === 'type') {
                // 用户编辑时-用户类别变化-将角色及关联供应商清空
                // key 事件触发控件,data 当前编辑的控件数据，selectors所有的F7控件
                selectors['role'] && selectors['role'].clearData();
                selectors['supplier'] && selectors['supplier'].clearData();

                var element = '#bd-supplier';

                if (data[0].ID == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                    // 系统用户类别时锁定关联供应商不可用
                    selectors['supplier'] && selectors['supplier'].lock();

                    if ($(element).parents("td").siblings().find(".must-mark")) {
                        $(element).parents("td").siblings().find(".must-mark").remove();
                    }

                } else {
                    // 供应商用户类别时关联供应商可用且必录
                    selectors['supplier'] && selectors['supplier'].unlock();

                    //如果是必填需要添加 红色 * 号
                    if ($(element).parents("td").siblings().find(".must-mark").length <= 0) { //如果不存在
                        var html = $(element).parents("td").siblings().html();
                        $(element).parents("td").siblings().html("<span class=\"must-mark\">*</span>" + html);
                    }
                }
            }

            if ((classId === 3010 || classId === 3020) && key === 'type') {

                // 供应商证件维护，证件类型变化后带出证件类型‘是否控制’，‘是否必须’属性
                var data = data[0].all; // 完整的控件选择数据
                var fields = metaData['formFields'][0];

                var lookUpClassID = fields[key]['lookUpClassID'];

                for (var item in fields) {

                    var field = fields[item];

                    if (field.lookUpType === 3 && field.lookUpClassID === lookUpClassID) {
                        //当前变化控件的属性携带

                        var value = data[field.disPlayField];

                        var element = getValueElement(field.key);

                        if (!element) {
                            continue;
                        }

                        if (field['ctrlType'] == 3) { // 多选按钮
                            element.checked = value;
                            continue;
                        }
                        element.value = value;

                    }
                }
            }

            if (classId === 3020 && key === 'supplier') {

                // 物料证件维护-供应商变化时-不能保证已选择物料属于该供应商需重新选择物料-清空物料选择框-
                //selectors['material'] && selectors['material'].clearData();
            	cleanGrid();
            }

        },
        'afterInitSelectors': function (selectors) {
            f7Selectors = selectors;
        }
    });

    function getValueElement(keyName) {
        return document.getElementById('bd-' + keyName);
    }

    return {
        render: render,
        clear: clear,
        save: save,
        forbid: forbid,
        refresh: refresh,
        cleanGrid: cleanGrid,
        getf7Selectors: function (name) {
            return f7Selectors[name];
        },
        on: emitter.on.bind(emitter),
    };

});