/**
 * @Title: Sjqgrid表格配置逻辑模块
 * @author：yadda(silenceisok@163.com)
 * @date： 2018/2/28 19:05
 */
define('GridConfig', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var user = SMS.Login.get();

    var showType = 0; // 0:1:2-查看/新增/编辑

    /**
     * 构建grid model
     * @param field 字段模板
     * @param isShow 是否显示
     * @param isEditAble 是否可编辑
     * @returns {{}}
     */
    function getColModel(field, isShow, isEditAble) {

        var model = {};

        model.name = field.key;
        model.label = field.name;
        model.width = field.showWidth;
        model.title = true;
        model.editable = isEditAble;
        model.hidden = !isShow;
        model.tabIndex = field.index;

        if (field.ctrlType == 6) {

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
            model.editrules = {required: false};
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
            model.align = 'right'; // 数字靠右显示
            model.editrules = {
                required: false,
                number: true
            };
            model.formatter = 'number';
            model.formatoptions = {
                decimalSeparator: ".",
                thousandsSeparator: " ",
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

     * @param type 0:1:2-查看/新增/编辑
     * @returns {*}
     */
    function getConfig(fields, config, showKeys, editKeys, type) {

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
            showType = type = params.showType;

        }
        //控制是否有新增，删除行功能-true：可以添加/删除 false：不出现添加/删除行功能
        var operator = showType === 1 ? 2 : showType === 2 ? 2 : 0;

        //按照单据模板确定显示字段
        if (!showKeys) {
            showKeys = getShowKeys(fields);
        }

        //按照单据模板确定可编辑字段
        if (!editKeys) {
            editKeys = getEditKeys(fields);
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

                    var html_con;

                    if (operator === 1) {
                        // add
                        html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span></div>';
                    } else if (operator === 2) {
                        // del
                        html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon-trash iconfont icon-delete" title="删除行"></span></div>';
                    } else if (operator === 3) {
                        // add & del
                        html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span><span class="ui-icon ui-icon-trash" title="删除行"></span></div>';
                    }

                    html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus iconfont icon-icon-xinzeng" title="新增行"></span><span class="ui-icon ui-icon-trash iconfont icon-delete" title="删除行"></span></div>';
                    return html_con;
                },
                align: "center",
                hidden: !operator,
            };
            cModel.push(model);
        }

        for (var key in fields) {

            var field = fields[key];

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

                /*                if (field.ctrlType == 6 && field.disPlayNum) {
                                    // 有配置显示代码时增加代码显示
                                    var keyNmbModel = {
                                        name: field.name + '_NmbName',
                                        label: field.name+'代码',
                                        tabIndex: field.index +1,
                                        hidden: false
                                    };
                                    cModel.push(keyNmbModel);
                                }*/
            }

            model = getColModel(field, $.Array.contains(showKeys, field.key), $.Array.contains(editKeys, field.key));

            cModel.push(model);
        }

        cModel = sortModels(cModel);

        for (var m in cModel) {
            cNames.push(cModel[m].label);
        }

        config.colNames = cNames;
        config.colModel = cModel;

        config.fnAfterEditCell = function (rowid, cellname, value, iRow, iCol) {
            $("#" + iRow + "_" + cellname).val(value);
        };
        config.fnAfterSaveCell = function (rowid, cellname, val, iRow, iCol) {
        };


        return config;
    }

    function sortModels(models) {

        return $.Array.sort(models, function (m1, m2) {
            return m1.tabIndex > m2.tabIndex;
        });

    }

    // 根据用户的角色类别确定模板字段的显示列
    function getShowKeys(fields) {

        var displayKeys = [];
        var display = 0;
        // 用户角色类别
        var userRoleType = user.role.type;

        /*        1	查看时对于平台用户显示
                2	新增时对于平台用户显示
                4	编辑时对于平台用户显示
                8	查看时对于供应商用户显示
                16	新增时对于供应商用户显示
                32	编辑时对于供应商用户显示
                64	查看时对于医院用户显示
                128	新增时对于医院用户显示
                256	编辑时对于医院用户显示
                512	是否在列表中显示(子表模板独有,子表数据显示在表头列表中)*/

        if (showType === 0) {
            // 查看
            if (userRoleType === 1) {
                // 平台用户
                display = 1;
            } else if (userRoleType === 2) {
                //供应商用户
                display = 8;
            } else if (userRoleType === 3) {
                //医院用户
                display = 64;
            }
        } else if (showType === 1) {
            // 新增

            if (userRoleType === 1) {
                // 平台用户
                display = 2;
            } else if (userRoleType === 2) {
                //供应商用户
                display = 16;
            } else if (userRoleType === 3) {
                //医院用户
                display = 128;
            }
        } else if (showType === 2) {
            // 编辑

            if (userRoleType === 1) {
                // 平台用户
                display = 4;
            } else if (userRoleType === 2) {
                //供应商用户
                display = 16;
            } else if (userRoleType === 3) {
                //医院用户
                display = 256;
            }
        }

        for (var key in fields) {

            var field = fields[key];

            if (!!(field.display & display)) {
                // 字段需要显示在页面
                displayKeys.push(key);
            }
        }

        return displayKeys;

    }

    //根据用户的角色类别确定模板字段的编辑列
    function getEditKeys(fields) {

        var editKeys = [];
        var lock = 0;

        // 用户角色类别
        var userRoleType = user.role.type;

        if (showType == 0) {
            // 查看时都锁定
            lock = 15;
        } else if (showType == 1) {
            // 新增
            //lockMaskDisplay  字段显示权限-后端lock定义 1 编辑时平台用户锁定，4编辑时候供应商用户锁定
            if (userRoleType === 1) {
                // 平台用户
                lock = 1;
            } else if (userRoleType === 2) {
                //供应商用户
                lock = 4;
            } else if (userRoleType === 3) {
                //医院用户
                lock = 4;
            }
        } else if (showType == 2) {
            // 编辑
            //lockMaskDisplay  字段显示权限-后端lock定义 2 编辑时平台用户锁定，8编辑时候供应商用户锁定
            if (userRoleType === 1) {
                // 平台用户
                lock = 2;
            } else if (userRoleType === 2) {
                //供应商用户
                lock = 8;
            } else if (userRoleType === 3) {
                //医院用户
                lock = 8;
            }
        }

        for (var key in fields) {

            var field = fields[key];

            var lockMask = field.lock || 0;

            if (showType == 0) {
                // 查看锁定所有字段
                lockMask = 15;
            }

            if (!(lockMask & lock)) {
                // 字段可编辑
                editKeys.push(key);
            }
        }

        return editKeys;

    }

    return {
        getConfig: getConfig,
    };

});
