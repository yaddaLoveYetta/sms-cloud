define('Filter', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var index,Filter,Notification,FilterData,Path,DatetimePicker;

    var filterEmitter = MiniQuery.Event.create();

    var config = {};

    function render(_config) {

        config = $.extend(_config);

        var html = 


  
'<div class="bread-crumbs"><div class="search-box"><i class="serach-icon"></i> <input id="search-order" type="text" placeholder="昵称 / 称号"></div><ul id="ul-path"><!--\n' +
    '                <li>\n' +
    '                    <ul>\n' +
    '                        #--breadCrumbs.begin--#\n' +
    '\n' +
    '                        <li class="section">{name}</li>\n' +
    '\n' +
    '                        #--breadCrumbs.end--#\n' +
    '                    </ul>\n' +
    '                </li>\n' +
    '\n' +
    '                #--selectedOption.begin--#\n' +
    '                <li>\n' +
    '                    <span class="option-selected" data-index="{index}">\n' +
    '                        {name}：\n' +
    '                        #--selected.begin--#\n' +
    '                        <span>{name} </span>\n' +
    '                        #--selected.end--#\n' +
    '                </li>\n' +
    '                #--selectedOption.end--#\n' +
    '            --></ul></div><div id="div-filter-box" class="filter-box"><ul id="filter-box-header" class="filter-box-header"><li data-filter-type="1" class="selected">常用条件</li><li data-filter-type="2">方案查询</li></ul><ul id="ul-filter" class="filter-box-body"><!--\n' +
    '                <li id ="criterias_{index}" class="{hiddenClass}">\n' +
    '                    <span class="option-title">{name}：</span>\n' +
    '\n' +
    '                    <div data-btn-index="{index}" class="select-more">\n' +
    '\n' +
    '\n' +
    '                    #--custom-date-range.begin--#\n' +
    '                        <div id="custom-time-zone" class="custom-date-time">\n' +
    '                            <div id="{setting-btn-id}" class="custom-zone" data-group-index="{data-index}">自定义时间段</div>\n' +
    '\n' +
    '\n' +
    '                            <ul id="custome-area" class="custom-area hidden">\n' +
    '                            #--created-area.begin--#\n' +
    '                                <li>\n' +
    '                                #--created-zone.begin--#\n' +
    '                                    <div class="created-zone" data-tag-index={index}>\n' +
    '                                        {name}\n' +
    '                                        <i class="close"></i>\n' +
    '                                    </div>\n' +
    '                                #--created-zone.end--#\n' +
    '                                </li>\n' +
    '                                <li>\n' +
    '                                    <select id="{select-id-1}" class="select-date">\n' +
    '                                        <option>昨天</option>\n' +
    '                                        <option>今天</option>\n' +
    '                                    </select>\n' +
    '                                    <div class="time-setting">\n' +
    '                                        <input id="{input-id-1}" class="input-time" type="text" />\n' +
    '                                    </div>\n' +
    '                                </li>\n' +
    '\n' +
    '                                <li>\n' +
    '                                    <select id="{select-id-2}" class="select-date">\n' +
    '                                        <option>昨天</option>\n' +
    '                                        <option selected>今天</option>\n' +
    '                                    </select>\n' +
    '                                    <div class="time-setting">\n' +
    '                                        <input id="{input-id-2}" class="input-time" type="text" />\n' +
    '\n' +
    '                                    </div>\n' +
    '                                </li>\n' +
    '\n' +
    '                                <li>\n' +
    '                                    <input id="{input-id-name}" type="text" class="input-name" value="方案x" />\n' +
    '                                    <i id="{btn-id}" class="add-icon"></i>\n' +
    '                                </li>\n' +
    '                            #--created-area.end--#\n' +
    '\n' +
    '                            </ul>\n' +
    '\n' +
    '                        </div>\n' +
    '                        #--custom-date-range.end--#\n' +
    '\n' +
    '                        #--more.begin--#\n' +
    '                            <div data-type="more" class="more-bt">\n' +
    '                                <i class="more-icon"></i>\n' +
    '                            </div>\n' +
    '                        #--more.end--#\n' +
    '                    </div>\n' +
    '\n' +
    '                    <ul id="ul-options_{index}" class="options">\n' +
    '                        #--option.begin--#\n' +
    '\n' +
    '                        #--single.begin--#\n' +
    '                        <li data-item-index="{index}" class="{hiddenClass} {selected-class}">\n' +
    '                            <span title="{fullName}">{name}</span>\n' +
    '                        </li>\n' +
    '                        #--single.end--#\n' +
    '\n' +
    '                        #--dateRange.begin--#\n' +
    '\n' +
    '                        #--dateBtn.begin--#\n' +
    '                        <li date-item-index="{date-index}"  class="{hiddenClass}"><span>{name}</span></li>\n' +
    '                        #--dateBtn.end--#\n' +
    '\n' +
    '                        #--datePicker.begin--#\n' +
    '                        <li class="time-picker">\n' +
    '                            <div class="time-box">\n' +
    '                                <div class="datetime-picker time-picker-label">\n' +
    '                                    <input type="text" id="startTime" class="date-label" value="{startTime-value}" />\n' +
    '                                    <i class="icon-calendar"></i>\n' +
    '                                </div>\n' +
    '                                ~\n' +
    '                                <div class="datetime-picker time-picker-label">\n' +
    '                                    <input type="text" id="endTime" class="date-label" value="{endTime-value}" />\n' +
    '                                    <i class="icon-calendar"></i>\n' +
    '                                </div>\n' +
    '                            </div>\n' +
    '                        </li>\n' +
    '\n' +
    '\n' +
    '                        #--datePicker.end--#\n' +
    '                        #--dateRange.end--#\n' +
    '\n' +
    '                        #--input.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <input id="{search-input}" type="text" class="search-input" value="{search-input-value}">\n' +
    '                        </li>\n' +
    '                        #--input.end--#\n' +
    '\n' +
    '                        #--input-range.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <input id="{data-range-index1}" type="text" class="search-range" value="{data-range-start}">-\n' +
    '                            <input id="{data-range-index2}" type="text" class="search-range" value="{data-range-end}">\n' +
    '                        </li>\n' +
    '                        #--input-range.end--#\n' +
    '\n' +
    '                        #--area.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <div data-area-index="{data-area-index}" id="{div-address-picker}"></div>\n' +
    '                        </li>\n' +
    '                        #--area.end--#\n' +
    '\n' +
    '                        #--flags.begin--#\n' +
    '                        <li>\n' +
    '                            <i data-flag-index="{value}" class="icon-flag{value}"></i>\n' +
    '                        </li>\n' +
    '                        #--flags.end--#\n' +
    '\n' +
    '\n' +
    '                        #--option.end--#\n' +
    '\n' +
    '                    </ul>\n' +
    '                    <div id="div-multi-buttons_{index}" class="multi-commit hidden-bt">\n' +
    '                        #--multiButton.begin--#\n' +
    '                        <input type="button" value="确定" data-action="confirm" class="sub-bt" />\n' +
    '                        <input type="button" value="取消" data-action="cancel" class="cancel-bt" />\n' +
    '                        #--multiButton.end--#\n' +
    '                    </div>\n' +
    '                </li>\n' +
    '\n' +
    '            --></ul><div class="filter-box-footer"><!-- 通过给filter-box加hidden-options-show类和给filter-box-header加filter-header-show类来达到更多效果 --><span id="show-more-options" class="filter-box-more"></span><div id="filter-box-setting" class="filter-box-setting"><span id="config-filter" class="filter-box-btn">设置条件</span><ul id="filter-box-options" class="filter-box-options vertical-ul hidden"><!--\n' +
    '                        <li class="line">\n' +
    '                            <div class="filter-box-title">{title}</div>\n' +
    '                            <ul class="g_options add-criteria">\n' +
    '                                #--item.begin--#\n' +
    '                                <li class="item">\n' +
    '                                    <span data-option-index="{index}" class="option {active-class}" title="{title}">{name}</span>\n' +
    '                                </li>\n' +
    '                                #--item.end--#\n' +
    '                            </ul>\n' +
    '                        </li>\n' +
    '                        #--button.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <input id="{filter-box-options-confirm}" class="btn-normal" type="button" value="确定"/>\n' +
    '                            <input id="{filter-box-options-cancel}" class="btn-normal btn-cancel" type="button" value="取消"/>\n' +
    '                        </li>\n' +
    '                        #--button.end--#\n' +
    '                    --></ul></div><!--<span id="options-cancel" class="filter-box-btn">取消</span>--><span id="options-search" class="filter-box-btn search">查询</span></div></div><ul id="notification-list" class="notification"><!--\n' +
    '            <li class="item notification-number-{amount}">\n' +
    '                <div data-item-index={index} class="block {first-class} {selected-class}">\n' +
    '                    <span class="amount">\n' +
    '                        {number}\n' +
    '                    </span>\n' +
    '                    <span class="title">\n' +
    '                        {name}\n' +
    '                    </span>\n' +
    '                </div>\n' +
    '            </li>\n' +
    '        --></ul>'

;


        document.getElementById(config.id).innerHTML = html;

        var search = config.search;
        if(search){
            filterEmitter.on('search',search);
        }

        var changeOptions = config.changeOptions;
        if(changeOptions){
            filterEmitter.on('option-changed',changeOptions);
        }

        
DatetimePicker = (function ($, MiniQuery, SMS) {

    function bindEvents() {

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#startTime', {
                format: 'yyyy-mm-dd hh:ii',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });


        });

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#endTime', {
                format: 'yyyy-mm-dd hh:ii',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });


        });


        $('#date-range-confirm').on('click', function () {

            $('#startTime').val('');
            $('#endTime').val('');

        });
    }

    function render() {
        bindEvents();
    }

    return {
        render: render
    }

})(jQuery, MiniQuery, SMS);




FilterData = (function ($, MiniQuery, SMS) {

    //filterType  0:单选 1:多选 2：时间 3：文本框 4：范围 5：图片 6：省市区
    //shown 0: 隐藏 1：显示
    //selected: 0：不是默认 1：默认

    var list = config.list;
    var pathList = config.pathList;

    function load(fn) {

        fn(list.showns);

    }

    function loadPath(fn) {

        fn(pathList);
    }

    function loadOptions(fn) {
        fn(list.categorys);
    }

    function loadNotification(fn) {
        fn(list.notification);
    }

    return {
        load: load,
        loadPath: loadPath,
        loadOptions: loadOptions,
        loadNotification: loadNotification

    }

})(jQuery, MiniQuery, SMS);






var FilterOptions = (function ($, MiniQuery, SMS) {

    var emitter = MiniQuery.Event.create();

    var filterBox = document.getElementById('filter-box-options');

    var samples = $.String.getTemplates(filterBox.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "button",
            begin: "#--button.begin--#",
            end: "#--button.end--#",
            outer: "{button}"
        },
        {
            name: "item",
            begin: "#--item.begin--#",
            end: "#--item.end--#",
            outer: "{item}"
        }
    ]);

    var list = [];

    var required = false;
    var isFilterOptionsHide = false;

    var confirmBtnId = 'filter-box-options-confirm' + $.String.random();
    var cancelBtnId = 'filter-box-options-cancel' + $.String.random();

    function hideFilterOptions() {
        $('#filter-box-options').addClass('hidden');
        selectedItems = [];
        deletedItems = [];
    }

    $('#config-filter').on('click', function () {

        $('#filter-box-options').toggleClass('hidden');

        if (!$('#filter-box-options').hasClass('hidden')) {
            emitter.fire('setting-options');
        }

        var btn = this;
        var index = btn.getAttribute('data-group-index');

        isFilterOptionsHide = false;

        if (!required) {


            $('#filter-box-setting').on('mouseover', function () {
                isFilterOptionsHide = false;
            });

            $('#filter-box-setting').on('mouseout', function () {
                isFilterOptionsHide = true;
            });

            $(document).on('click', function () {

                if (isFilterOptionsHide) {
                    hideFilterOptions();
                }
            });

            required = true;
        }

    });

    $(filterBox).delegate('#' + confirmBtnId, 'click', function () {

        $.each(selectedItems, function (index, item) {
            var pair = item.split('_');
            
            list[+pair[0]].items[+pair[1]].select = 1;
        });

        $.each(deletedItems, function (index, item) {
            var pair = item.split('_');

            list[+pair[0]].items[+pair[1]].select = 0;
        });

        hideFilterOptions();

        var args = [];
        $.each(list, function (index, item) {

            
            args = $.map(item.items, function (childItem) {
                    if (childItem.select == 1) {
                        return {
                            itemID: childItem.itemID,
                            defaultValue:''
                        }
                    }
                    
                    return null;
                    
                }).concat(args);
        });

        emitter.fire('option-changed', [args]);

    });

    $(filterBox).delegate('#' + cancelBtnId, 'click', function () {
        hideFilterOptions();
    });

    //使用两个数组来记录选中和取消选中
    var selectedItems = [];
    var deletedItems = [];

    function deleteItem(list, index) {
        var i = list.indexOf(index);
        if (i > -1) {
            list.splice(i, 1);
        }
    }

    $(filterBox).delegate('[data-option-index]', 'click', function () {

        var btn = this;
        $(btn).toggleClass('item-selected');

        var index = btn.getAttribute('data-option-index');

        if ($(btn).hasClass('item-selected')) {

            deleteItem(deletedItems, index);

            selectedItems.push(index);
        }
        else {

            deleteItem(selectedItems, index);

            deletedItems.push(index);
        }
        


    });

    function render(data) {


        list = data;

        filterBox.innerHTML = $.map(list, function (item, index) {

            return $.String.format(samples['group'], {
                'title': item.name,
                'item': $.map(item.items || [], function (item, optionIndex) {

                    //将省市区合并为地区
                    if (item.itemID == 34) {
                        return $.String.format(samples['item'], {
                            'name': '地区',
                            'title': '地区',
                            'active-class': item.select ? 'item-selected' : '',
                            'index': index + '_' + optionIndex
                        })
                    }
                    else if (item.itemID == 35 || item.itemID == 36) {
                        return null;
                    }
                    else {
                        return $.String.format(samples['item'], {
                            'name': item.name.substr(0, 6),//最多显示6个字符
                            'title': item.name,
                            'active-class': item.select ? 'item-selected' : '',
                            'index': index + '_' + optionIndex
                        })
                    }
                   
                }).join(''),
                'button': ''
            });

        }).join('') + $.String.format(samples['button'], {
            'filter-box-options-confirm':confirmBtnId,
            'filter-box-options-cancel': cancelBtnId
        });


    }


    return {
        render: render,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);



var TimeZone = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('ul-filter');

    //为设置时间，方案名输入框设id
    var panelId = '';
    var inputId1 = 'input-id-' + $.String.random();
    var inputId2 = 'input-id-' + $.String.random();
    var inputIdName = 'input-id-' + $.String.random();
    var selectId1 = 'select-id-' + $.String.random();
    var selectId2 = 'select-id-' + $.String.random();
    var btnId = 'btn-id-' + $.String.random();



    var samples = $.String.getTemplates(wrapper.innerHTML, [

        {
            name: 'created-area',
            begin: '#--created-area.begin--#',
            end: '#--created-area.end--#'
        },
        {
            name: 'created-zone',
            begin: '#--created-zone.begin--#',
            end: '#--created-zone.end--#',
            outer: "{created-zone}"
        }
    ]);


    $(wrapper).delegate('[data-tag-index]', 'click', function () {

        var tab = this;
        $(tab).hide();

    });

    $(wrapper).delegate('#' + btnId, 'click', function () {
        console.log(btnId + 'click');
        $('#' + panelId).addClass('hidden');

    });

    function createTimePidcker(id) {

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#' + id, {
                format: 'hh:ii',
                autoclose: true,
                startView: 'day',
                minView: 'hour'
            });


        });
    }

    function render(config) {

        panelId = config.id;

        $('#' + config.id).toggleClass('hidden');

        if (!$('#' + config.id).hasClass('hidden')) {
            document.getElementById(config.id).innerHTML = $.String.format(samples['created-area'], {

                'created-zone': $.map(config.data, function (item, index) {

                    if (item.value) {
                        return $.String.format(samples['created-zone'], {
                            'index': index,
                            'name': item.name
                        });
                    }
                    else {
                        return null;
                    }

                }).join(''),
                'input-id-1': inputId1,
                'input-id-2': inputId2,
                'input-id-name': inputIdName,
                'select-id-1': selectId1,
                'select-id-2': selectId2,
                'btn-id': btnId

            });

            createTimePidcker(inputId1);
            createTimePidcker(inputId2);
        }


    }

    return {
        render: render
    }

})(jQuery, MiniQuery, SMS);



Path = (function ($, MiniQuery, SMS) {

    var ul = document.getElementById('ul-path');

    var samples = $.String.getTemplates(ul.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "breadCrumbs",
            begin: "#--breadCrumbs.begin--#",
            end: " #--breadCrumbs.end--#",
            outer: "{breadCrumbs}"
        },
        {
            name: "selectedOption",
            begin: "#--selectedOption.begin--#",
            end: " #--selectedOption.end--#",
            outer: "{selectedOption}"
        },
        {
            name: "selected",
            begin: "#--selected.begin--#",
            end: " #--selected.end--#",
            outer: "{selected}"
        }
    ]);

    var list = [];
    var pathHtml = '';
    var searchObj = null;

    var timeOut = 400;

    var emitter = MiniQuery.Event.create();

    function remove(index) {

        var item = list.splice(index, 1)[0];
        fill();

        emitter.fire('closeTab', [index, item]);

    }

    $(ul).delegate('[name="remove"]', 'click', function () {

        $(this).parent().fadeOut(timeOut, function () {
            var index = this.getAttribute('data-index');
            console.log(index);
            remove(index);
        });


    });

    $(document).delegate('#search-order', 'keyup', function () {
        var input = this;

        if (window.event.keyCode == 13 || input.value !== 0 && !input.value) {

            if (input.value === 0 || input.value) {
                searchObj = {
                    'field': 'tid',
                    'value': input.value,
                    'type': 'like'
                };
            }
            else {
                searchObj = null;
            }

            emitter.fire('search-order');
        }

    });

    function getSearchObj() {
        return searchObj;
    }

    //function findItems(type) {

    //    return $.Array.map(list, function (item, index) {
    //        if (item.type == type)
    //            return item;
    //        else
    //            return null;
    //    });
    //}

    function fill() {


        ul.innerHTML = $.String.format(samples['group'], {
            breadCrumbs: pathHtml,
            selectedOption: $.Array.map(list, function (item, index) {


                return $.String.format(samples['selectedOption'], {
                    'name': item.name,
                    'index': index,
                    'selected': $.Array.map(item.displayItems, function (option, valueIndex) {

                        return $.String.format(samples['selected'], {
                            'name': option.name
                        })
                    }).join(',')
                })
            }).join('')
        });

    }

    function fillPath(paths) {

        pathHtml = $.Array.keep(paths, function (path, index) {
            return $.String.format(samples['breadCrumbs'], {
                name: path
            });
        }).join('');

        return pathHtml;

    }

    function init(paths) {

        ul.innerHTML = $.String.format(samples['group'], {
            breadCrumbs: fillPath(paths),
            selectedOption: ''
        });

    }

    function render(items) {

        //用于存放已经存在index对象的数组
        var indexList = [];

        //去掉index重复的对象，时间范围是一种
        list = $.Array.map(items, function (item) {

            var i = $.Array.findIndex(indexList, function (indexItem) {
                return indexItem == item.index;
            });

            if (i == -1) {
                indexList.push(item.index);
                return item;
            }
            else {
                return null;
            }

        });

        fill();

    }

    return {
        init: init,
        render: render,
        getSearchObj: getSearchObj,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);





Filter = (function ($, MiniQuery, SMS, TimeZone) {
    var ul = document.getElementById('ul-filter');

    var showMoreOption = 5;

    //默认展开行数
    var shown = config.shown;

    var list = [];

    var samples = $.String.getTemplates(ul.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "more",
            begin: "#--more.begin--#",
            end: "#--more.end--#",
            outer: "{more}"
        },
        {
            name: "option",
            begin: "#--option.begin--#",
            end: "#--option.end--#",
            outer: "{option}"
        },
        {
            name: "single",
            begin: "#--single.begin--#",
            end: "#--single.end--#",
            outer: "{single}"
        },
        {
            name: "multiButton",
            begin: "#--multiButton.begin--#",
            end: "#--multiButton.end--#",
            outer: "{multiButton}"
        },
        {
            name: "custom-date-range",
            begin: "#--custom-date-range.begin--#",
            end: "#--custom-date-range.end--#",
            outer: "{custom-date-range}"
        },
        {
            name: "dateRange",
            begin: "#--dateRange.begin--#",
            end: "#--dateRange.end--#",
            outer: "{dateRange}"
        },
        {
            name: "dateBtn",
            begin: "#--dateBtn.begin--#",
            end: "#--dateBtn.end--#",
            outer: "{}"
        },
        {
            name: "datePicker",
            begin: "#--datePicker.begin--#",
            end: "#--datePicker.end--#",
            outer: "{}"
        },
        {
            name: "input",
            begin: "#--input.begin--#",
            end: "#--input.end--#",
            outer: "{input}"
        },
        {
            name: "input-range",
            begin: "#--input-range.begin--#",
            end: "#--input-range.end--#",
            outer: "{input-range}"
        },
        {
            name: "area",
            begin: "#--area.begin--#",
            end: "#--area.end--#",
            outer: "{area}"
        },
        {
            name: "flags",
            begin: "#--flags.begin--#",
            end: "#--flags.end--#",
            outer: "{flags}"
        }
    ]);

    var emitter = MiniQuery.Event.create();

    var timeOut = 400;

    var settingBtnId = 'setting-btn-' + $.String.random();

    function getIndexFromId(id) {
        return id.substr(id.indexOf('_') + 1);
    }

    function recoverOption(obj) {

        obj.siblings().removeClass('multi-options').
            removeClass('more-options').
            find('[data-item-index],.icon-select').
            removeClass('selected');
    }

    //常用条件/方案查询 选择
    $('[data-filter-type]').on('click', function () {
        var btn = this;
        var index = btn.getAttribute('data-filter-type');

        $(btn).addClass('selected').siblings().removeClass('selected');

        if (index == 2) {
            $(ul).addClass('solution');
        }
        else {
            $(ul).removeClass('solution');
        }
    });

    //多选
    $('#ul-filter').delegate('[data-type=multi]', 'click', function () {

        var btn = this;

        var outer = $(btn).closest('[id^=criterias_]');
        outer.addClass('multi-options');

        recoverOption(outer);

    });

    //选中选项
    $('#ul-filter').delegate('[data-item-index]', 'click', function () {

        var btn = this;

        var itemIndex = btn.getAttribute('data-item-index');

        var outer = $(btn).closest('[id^=ul-options_]');

        var id = $(outer).attr('id');
        var index = getIndexFromId(id);

        var item = list[index].items[itemIndex];

        $(btn).toggleClass('selected');

        if ($(btn).hasClass('selected')) {

            item.select = 1;
        }
        else {
            item.select = 0;
        }

        //else {


        //    var index = getIndexFromId(outer.attr('id'));

        //    var selectedIndex = btn.getAttribute('data-item-index');



        //    item[]

        //    var obj = {
        //        name: item.name,
        //        value: item.value,
        //    };

        //    addToSelectedItems(index, [obj], '=');

        //    //emitter.fire('optionChanged', [selectedItems]);

        //    //renderAfterSelected(index);

        //};


    });

    function initSelectedList() {


        selectedItems = [];

        var selectedArray = [];

        $.each(list, function (index, item) {

            switch (+item.filterType) {

                
                case 1://多选&单选
                case 5://小旗子

                    //是否选中从数组list中读取select的值，而不是通过dom节点去读取

                    $.each(item.items || [], function (index, item) {

                        if (item.select) {

                            var obj = {
                                name: item.name,
                                value: item.value,
                            };//name:页面显示的值，value:传到后台的值

                            selectedArray.push(obj);
                        }
                    });

                    if (selectedArray.length > 0) {

                        var operator = selectedArray.length > 1 ? 'in' : '=';

                        addToSelectedItems(index, selectedArray, operator);

                        selectedArray = [];
                    }
                    break;

                    
                case 2://时间范围
                case 4://数量范围


                    var obj1 = null;
                    var obj2 = null;
                    var value1;
                    var value2;

                    // 时间范围读取时间范围控件的值
                    if (item.filterType == 2) {
                        value1 = $.trim(document.getElementById('startTime').value);
                        value2 = $.trim(document.getElementById('endTime').value);
                    }
                    else {
                        //数量范围读取数量范围的值
                        value1 = $.trim(document.getElementById(dataRangeId1 + index).value);
                        value2 = $.trim(document.getElementById(dataRangeId2 + index).value);
                    }


                    var pair = [];

                    if (value1) {
                        obj1 = { name: value1, value: value1 };
                        pair.push(obj1);
                    }

                    if (value2) {
                        obj2 = { name: value2, value: value2 };
                        pair.push(obj2);
                    }

                    if (pair.length > 0) {

                        var param = pair.length == 2 ? pair.join(',') : pair[0];

                        var operator = pair.length == 2 ? 'in' : '=';

                        addToSelectedItems(index, [param], operator, pair);
                    }
                    

                    //if (obj1) {
                    //    addToSelectedItems(index, [obj1], '>=', pair);
                    //}

                    //if (obj2) {

                    //    addToSelectedItems(index, [obj2], '<=', pair);
                    //}
                    break;

                case 3://模糊查询输入框

                    var inputValue = document.getElementById(searchInput + index).value;

                    if (inputValue) {
                        var obj = {
                            name: inputValue,
                            value: inputValue

                        };

                        addToSelectedItems(index, [obj], 'like');
                    }
                    
                    
                    break;

                case 6://省市区

                    var names = [];
                    $.each(item.items, function (i, obj) {

                        if (obj.areaID > 0) {
                            names.push({
                                name: obj.name,
                                value: obj.name
                            });
                        }
                        
                    });

                    $.each(item.items, function (i, obj) {

                        if (obj.areaID > 0) {

                            selectedItems.push({
                                index: index,
                                itemID: obj.itemID,
                                name: list[index].name,
                                filterType: list[index].filterType,
                                items: [{
                                    name:obj.name,
                                    value:obj.name
                                }],
                                compareType: '=',
                                displayItems: names

                            })
                        }
                    });

                    break;

            }

        });
    }


    //查询
    $('#options-search').on('click', function () {

        initSelectedList();

        emitter.fire('optionChanged', [selectedItems]);
        

    });

    //时间方案
    $('#ul-filter').delegate('[date-item-index]', 'click', function () {

        var btn = this;

        var index = btn.getAttribute('date-item-index');

        var outer = $(btn).closest('[id^=ul-options_]');

        var id = $(outer).attr('id');
        var filterIndex = getIndexFromId(id);

        var temp = list[filterIndex].items[index].value.split(',');

        $('#startTime').val(temp[0]);
        $('#endTime').val(temp[1]);

    });


    //时间范围
    //$('#ul-filter').delegate('[date-type]', 'click', function () {

    //    var btn = this;
    //    var outer = $(btn).closest('[id^=ul-options_]');

    //    var id = $(outer).attr('id');
    //    var index = getIndexFromId(id);


    //    var dateIndex = btn.getAttribute('date-index');
    //    var name = $.trim(btn.innerText);

    //    var dateValue1, dateValue2, dateObj, dateObj2;

    //    if (dateIndex != 'confirm') {
    //        addToSelectedItems(index, [{ name: name, value: '' }], dateIndex);
    //    }
    //    else {
    //        dateValue = document.getElementById('startTime').value;
    //        dateValue2 = document.getElementById('endTime').value;

    //        var datePair = [];

    //        if (dateValue) {
    //            dateObj = { name: dateValue, value: dateValue };
    //            datePair.push(dateObj);
    //        }

    //        if (dateValue2) {
    //            dateObj2 = { name: dateValue2, value: dateValue2 };
    //            datePair.push(dateObj2);
    //        }

    //        if (dateObj) {
    //            addToSelectedItems(index, [dateObj], '>=', datePair);
    //        }

    //        if (dateObj2) {

    //            addToSelectedItems(index, [dateObj2], '<=', datePair);
    //        }
    //    }

    //    emitter.fire('optionChanged', [selectedItems]);

    //    renderAfterSelected(index);

    //});


    //更多
    var openMoreGroups = [];

    $('#ul-filter').delegate('[data-type=more]', 'click', function () {

        var btn = this;

        var outer = $(btn).closest('[id^=criterias_]');
        outer.toggleClass('more-options');

        //recoverOption(outer);

    });

    //选中小旗帜
    $('#ul-filter').delegate('[data-flag-index]', 'click', function () {

        var btn = this;

        $(btn).toggleClass('flag-selected');

        var outer = $(btn).closest('[id^=ul-options_]');

        var id = $(outer).attr('id');
        var index = getIndexFromId(id);

        var itemIndex = btn.getAttribute('data-flag-index');

        if ($(btn).hasClass('flag-selected')) {
            list[index].items[itemIndex].select = 1;
        }
        else {
            list[index].items[itemIndex].select = 0;
        }


    });


    //取消所有选中
    $('#options-cancel').on('click', function () {

        $.each(list, function (i, ele) {
            $.each(ele.items || [], function (index, item) {
                if (item.select) {
                    item.select = 0;
                }
            })
        });

        render(list);

    });

    //更多条件
    $('#show-more-options').on('click', function () {

        $('#div-filter-box').toggleClass('hidden-options-show');

    });

    var selectedItems = [];

    function addToSelectedItems(index, selectedItemsArray, compareType, displayItems) {

        selectedItems.push({
            index: index,
            itemID: list[index].itemID,
            name: list[index].name,
            filterType: list[index].filterType,
            items: selectedItemsArray,//发送给后台时用到，它是也个对象，有两个字段name和value，name:页面显示的值，value:传到后台的值
            compareType: compareType,//以compareType的in和not in来判断该项是否多选
            displayItems: displayItems ? displayItems : selectedItemsArray//时间范围下使用displayItems，其它情况使用selectedItemsArray，在Path模块中用到

        });

    }

    function recoverSelectedItems(index, item) {

        //selectedItems.splice(index, 1);
        selectedItems = $.Array.map(selectedItems, function (selectedItem) {
            if (selectedItem.index != item.index) {
                return selectedItem;
            }
            else {
                return null;
            }
        });

        var outer = $('#criterias_' + item.index);


        outer.removeClass('options-selected');
        outer.fadeIn(timeOut, function () {
            emitter.fire('optionChanged', [selectedItems, 'del']);
        });
    }

    function renderAfterSelected(index, isHide) {

        var outer = $('#criterias_' + index);

        if (!isHide) {
            outer.fadeOut(timeOut, function () {

                outer.addClass('options-selected')

            });
        }
        outer.removeClass('multi-options').removeClass('more-options');
        outer.find('[data-item-index],.icon-select').removeClass('selected');

        $('#ul-filter [data-btn-index = ' + index + ']').html(
            (list[index].items.length > showMoreOption ? samples['more'] : ''));

    }


    //多选(确定/取消)
    $('#ul-filter').delegate('[data-action]', 'click', function () {

        var btn = this;

        var action = btn.getAttribute('data-action');
        var id = btn.parentNode.getAttribute('id');
        var index = getIndexFromId(id);

        var outer = $('#ul-options_' + index);
        var checkedEle = outer.find('.selected[data-item-index]');

        if (action == 'confirm') {

            if (checkedEle.length <= 0)
                return false;

            var selectedOptions = $.Array.keep(checkedEle, function (item) {

                var itemIndex = item.getAttribute('data-item-index');

                return {
                    name: list[index].items[itemIndex].name,
                    value: list[index].items[itemIndex].value,
                }
            });

            addToSelectedItems(index, selectedOptions, 'in');

            //发送参数到后端
            emitter.fire('optionChanged', [selectedItems]);
        }

        renderAfterSelected(index, action == 'cancel');
    });

    var isHide = false;
    var required = false;//第一次加载时绑定事件

    $(ul).delegate('#' + settingBtnId, 'click', function () {

        var btn = this;
        var index = btn.getAttribute('data-group-index');

        var config = {
            id: 'custome-area',
            data: list[index].items
        }

        TimeZone.render(config);

        isHide = false;

        if (!required) {


            $('#custom-time-zone').on('mouseover', function () {
                isHide = false;
            });

            $('#custom-time-zone').on('mouseout', function () {
                isHide = true;
            });

            $('#filter-box-setting').on('mouseover', function () {
                isFilterOptionsHide = false;
            });

            $('#filter-box-setting').on('mouseout', function () {
                isHide = true;
            });

            $(document).on('click', function () {

                if (isHide) {
                    $('#custome-area').addClass('hidden');
                }
            });

            required = true;
        }


    });

    function dispalyOption(name) {
        if (name.length > 7)
            return name.slice(0, 7) + '...';
        return name;
    }

    var picker = {};
    var pickerId = 'div-address-picker-' + $.String.random() + '_';
    var pickerValues = [];

    var dataRangeId1 = 'data-range-' + $.String.random() + '_';
    var dataRangeId2 = 'data-range-' + $.String.random() + '_';
    var dateRangeList = [];

    var searchInput = 'search-input_';
    var searchRange = 'search-range_';
    function fillOption(option, dataIndex) {

        return $.String.format(samples['option'], {
            single: (option.filterType == 1) ?
                ($.Array.keep(option.items || [], function (item, index) {

                    return $.String.format(samples['single'], {
                        index: index,
                        name: dispalyOption(item.name),
                        fullName: item.name,
                        hiddenClass: index >= showMoreOption ? 'hidden' : '',
                        'selected-class': item.select ? 'selected' : ''

                    })

                }).join('')) : '',

            dateRange: option.filterType == 2 ? ($.Array.keep(option.items || [], function (item, index) {

                return $.String.format(samples['dateBtn'], {
                    'date-index': index,
                    name: item.name,
                    'hiddenClass': index >= 2 ? 'hidden' : ''//时间范围默认显示两个
                });

            }).join('') + $.String.format(samples['datePicker'], (function () {

                var val1 = '';
                var val2 = '';

                if (option.defaultValue && option.defaultValue != 0) {

                    var range = option.defaultValue.split(',');

                    if (range.length > 0) {

                        if (range.length == 2) {
                            val1 = range[0];
                            val2 = range[1];
                        }
                        else if (option.defaultValue.indexOf(',') == 0) {
                            val1 = range[0];
                        }
                        else {
                            val2 = range[0];
                        }

                    }
                }
                

                return {
                    'startTime-value': val1,
                    'endTime-value': val2
                }
            })())) : '',

            'input': option.filterType == 3 ? $.String.format(samples['input'],{
                'search-input': searchInput + dataIndex,
                'search-input-value': option.defaultValue && option.defaultValue != 0 ? option.defaultValue : ''
                }) : '',

            'input-range': option.filterType == 4 ? (function () {

                dateRangeList.push(dataRangeId1 + dataIndex);
                dateRangeList.push(dataRangeId2 + dataIndex);

                var val1 = '';
                var val2 = '';

                if (option.defaultValue && option.defaultValue != 0) {
                    var range = option.defaultValue.split(',');

                    if (range.length > 0) {

                        if (range.length == 2) {
                            val1 = range[0];
                            val2 = range[1];
                        }
                        else if (option.defaultValue.indexOf(',') == 0) {
                            val1 = range[0];
                        }
                        else {
                            val2 = range[0];
                        }

                    }
                }
                return $.String.format(samples['input-range'], {
                    'data-range-index1': dataRangeId1 + dataIndex,
                    'data-range-index2': dataRangeId2 + dataIndex,
                    'data-range-start': val1,
                    'data-range-end': val2

                });
            })() : '',

            'flags': option.filterType == 5 ? $.map(option.items, function (item, index) {

                return $.String.format(samples['flags'], {
                    value: item.value
                });

            }).join('') : '',

            'area': option.filterType == 6 ? (function () {

                pickerValues.push(
                    {
                        'id': pickerId + dataIndex,
                        'index': dataIndex,
                        'selectedItems': $.map(option.items, function (ele, i) {
                            return ele.areaID || -1;
                        })
                    });

                return $.String.format(samples['area'], {
                    'div-address-picker': pickerId + dataIndex,
                    'data-area-index': dataIndex
                });
            })() : ''

        });
    }

    function render(data) {

        dateRangeList = [];
        pickerValues = [];
        list = data;

        ul.innerHTML = $.Array.map(list, function (option, index) {

            return $.String.format(samples['group'], {
                name: option.name,
                index: index,
                hiddenClass: index > shown ? 'hidden-option' : '',
                more: option.items && (option.items.length > showMoreOption) ? samples['more'] : '',
                'custom-date-range': option.filterType == 2 ? $.String.format(samples['custom-date-range'], {

                    'setting-btn-id': settingBtnId,
                    'data-index': index

                }) : '',
                //multiButton: option. samples['multiButton'],
                option: fillOption(option, index)
            });

        }).join('');

        //将已选项记录下来并告诉Path模块
        initSelectedList();
        emitter.fire('filter-init', [selectedItems]);



        //初始化所有的级联控件
        $.each(pickerValues, function (index, item) {
            createPicker(item);
        });
        
        //把所有的数字范围输入框初始化为控件
        $.each(dateRangeList, function (index, id) {
            createNumberField(id);
        });

    }

    function createPicker(item) {

        return SMS.CascadePicker.create({
            container: '#' + item.id,
            hideNone: true,
            data: 'data/address/array.simple.js',
            //data: '../../../../../htdocs/data/address/array.simple.js',
            varname: '__AddressData__',
            fields: {
                value: 0,
                text: 1,
                child: 2,
            },
            defaultTexts: ['--请选择省份--', '--请选择城市--', '--请选择地区--'],
            selectedValues: item.selectedItems,
            change: function () {

                var items = this.getSelectedItems();

                //list[item.index].items 里面默认是省市区的顺序

                $.each(list[item.index].items, function (index, obj) {
                    
                    obj.areaID = items[index]? items[index][0] : '0';
                    obj.name = items[index]?  items[index][1] : '';

                })
            }

        });
    }

    function createNumberField(id) {
        SMS.use('NumberField', function (NumberField) {

            var nf = new NumberField('#' + id);

            //获取文本框的值
            $('#' + id).focusout(function () {
                var value = nf.get();
            });

        });
    }

    function getSelectedOptions() {

        return selectedItems;
    }

    function getDefaultFilter() {
        return [{
            'itemID': 'createTime',
            'value': $.Date.format(new Date(), 'yyyy-MM-dd'),
            'type': '<='
        }];
    }

    return {
        render: render,
        recoverSelectedItems: recoverSelectedItems,
        getSelectedOptions: getSelectedOptions,
        getDefaultFilter: getDefaultFilter,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS, TimeZone);



Notification = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('notification-list');

    var list = [];

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        }
    ]);

    var emitter = MiniQuery.Event.create();

    function getSelectedItem() {

        var obj = null;
        $.each(list,function(index,item){
            if (item.select == 1) {
                obj = {
                    itemID: item.itemID,
                    items: [{
                        value: item.defaultValue
                    }],
                    compareType: '=',//以compareType的in和not in来判断该项是否多选
            
                }
            }
        });

        return obj;
        
    }

    $(wrapper).delegate('[data-item-index]', 'click', function () {

        var btn = this;

        var index = btn.getAttribute('data-item-index');

        $.each(list, function (i, item) {
            item.select = 0;
        });

        list[index].select = 1;

        render(list);

        emitter.fire('item-selected', [getSelectedItem()]);

    });

    function render(data) {

        list = data;

        wrapper.innerHTML = $.map(list, function (item, index) {
            return $.String.format(samples['group'], {
                'index': index,
                'amount': list.length,
                'first-class': index == 0 ? 'first' : '',
                'selected-class': item.select ? 'selected' : '',
                'number': item.defaultValue,
                'name': item.name
            });
        }).join('');

    }

    return {
        render: render,
        getSelectedItem: getSelectedItem,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);



index = (function ($, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification) {

    function getFilterList(selectedItems) {

        var list = $.Array.keep(selectedItems, function (item, index) {

            var argsValue;

            //以compareType的in和not in来判断该项是否多选
            if (item.compareType == 'in' || item.compareType == 'not in') {
                argsValue = $.Array.keep(item.items, function (i) {
                    return i.value;
                }).join(',');
            }
            else if (item.items.length == 1) {
                argsValue = item.items[0].value;
            }
            else {
                argsValue = '';
            }

            return {
                'itemID': item.itemID,
                'value': argsValue,
                'type': 0
            }
        });

        var obj = Path.getSearchObj();
        if (obj) {
            list.push(obj);
        }

        return list.length > 0 ? list : Filter.getDefaultFilter();
    }

    FilterOptions.on({
        'setting-options': function () {
            FilterData.loadOptions(function (data) {
                FilterOptions.render(data)
            });
        },
        'option-changed': function (selectedOptions) {
            filterEmitter.fire('option-changed', [selectedOptions]);
        }
    });


    Filter.on({
        'optionChanged': function (selectedItems, type) {

            filterEmitter.fire('search', [getFilterList(selectedItems)]);

            if (!type) {
                Path.render(selectedItems);

            }

        },
        'filter-init': function (selectedItems) {

            Path.render(selectedItems);

        }
    });

    FilterData.loadPath(function (data) {
        Path.init(data);
    });

    FilterData.load(function (data) {
        Filter.render(data);
    });

    FilterData.loadNotification(function (data) {
        Notification.render(data);
    });

    DatetimePicker.render();

    Notification.on({
        'item-selected': function (item) {

            var list = getFilterList(Filter.getSelectedOptions().concat([item]));
            console.log(list);
            //filterEmitter.fire('search', [list])]);
        }
    });


    Path.on({
        //"closeTab": function (index, item) {
        //    Filter.recoverSelectedItems(index, item);
        //},
        'search-order': function () {

            //var options = Filter.getSelectedOptions();
            //var list = getFilterList(options);

            //var args = {
            //    filter: list,
            //    pager: { "page": 1, "size": OrderPagination.getSize(), "order": Sort.getListString() }
            //};

            //API.list(args, function (data) {

            //    SMS.Tips.success('数据加载完成', 2000);
            //    OrderList.render(data.orders);
            //    OrderPagination.render(data.pager);
            //});
        }
    });

    return {
        getFilterList: getFilterList
    }

})(jQuery, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification);
    }

    function getSelectedOptions(){
        return index.getFilterList(Filter.getSelectedOptions().concat([Notification.getSelectedItem()]))
    }

    function load(data){
        
        config.list.showns = data;
        FilterData.load(function (data) {

            Filter.render(data);
        });

        DatetimePicker.render();
    }

    return {
        render: render,
        getSelectedOptions: getSelectedOptions,
        load:load

    }

});