define('Filter', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var filterEmitter = MiniQuery.Event.create();

    function render(config) {

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
    '            --></ul></div><div id="div-filter-box" class="filter-box"><ul class="filter-box-header"><li class="selected">常用条件</li><li>方案查询</li></ul><ul id="ul-filter" class="filter-box-body"><!--\n' +
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
    '                                    <input type="text" id="startTime" class="date-label" />\n' +
    '                                    <i class="icon-calendar"></i>\n' +
    '                                </div>\n' +
    '                                ~\n' +
    '                                <div class="datetime-picker time-picker-label">\n' +
    '                                    <input type="text" id="endTime" class="date-label" />\n' +
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
    '                            <input type="text" class="search-input">\n' +
    '                        </li>\n' +
    '                        #--input.end--#\n' +
    '\n' +
    '                        #--input-range.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <input id="{data-range-index1}" type="text" class="search-range">-\n' +
    '                            <input id="{data-range-index2}" type="text" class="search-range">\n' +
    '                        </li>\n' +
    '                        #--input-range.end--#\n' +
    '\n' +
    '                        #--area.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <div id="{div-address-picker}"></div>\n' +
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
    '                                    <span data-option-index="{index}" class="option {active-class}">{name}</span>\n' +
    '                                </li>\n' +
    '                                #--item.end--#\n' +
    '                            </ul>\n' +
    '                        </li>\n' +
    '                        #--button.begin--#\n' +
    '                        <li class="line">\n' +
    '                            <input type="button" value="确定"/>\n' +
    '                            <input type="button" value="取消"/>\n' +
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

        
var DatetimePicker = (function ($, MiniQuery, SMS) {

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




var FilterData = (function ($, MiniQuery, SMS) {

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
                    $('#filter-box-options').addClass('hidden');
                }
            });

            required = true;
        }

    });

    $(filterBox).delegate('[data-option-index]', 'click', function () {

        $(this).toggleClass('item-selected');
    });

    function render(data) {

        list = data;

        filterBox.innerHTML = $.map(list, function (item, index) {

            return $.String.format(samples['group'], {
                'title': item.name,
                'item': $.map(item.items || [], function (item, optionIndex) {
                    return $.String.format(samples['item'], {
                        name: item.name,
                        'active-class': item.select ? 'item-selected' : '',
                        'index': index + '_' + optionIndex
                    })
                }).join(''),
                'button': ''
            });

        }).join('') + samples['button'];


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



var Path = (function ($, MiniQuery, SMS) {

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

    function findItems(type) {

        return $.Array.map(list, function (item, index) {
            if (item.type == type)
                return item;
            else
                return null;
        });
    }

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





var Filter = (function ($, MiniQuery, SMS, TimeZone) {
    var ul = document.getElementById('ul-filter');

    var showMoreOption = 5;

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


    //查询
    $('#options-search').on('click', function () {

        selectedItems = [];

        var selectedArray = [];

        $.each(list, function (index, item) {

            switch (+item.filterType) {

                //多选&单选
                case 1:
                    $.each(item.items || [], function (index, item) {

                        if (item.select) {

                            var obj = {
                                name: item.name,
                                value: item.value,
                            };

                            selectedArray.push(obj);
                        }
                    });

                    if (selectedArray.length > 0) {

                        var operator = selectedArray.length > 1 ? 'in' : '';

                        addToSelectedItems(index, selectedArray, operator);

                        selectedArray = [];
                    }
                    break;

                    //时间范围
                case 2:
                    var dateObj = null;
                    var dateObj2 = null;
                    var dateValue = document.getElementById('startTime').value;
                    var dateValue2 = document.getElementById('endTime').value;

                    var datePair = [];

                    if (dateValue) {
                        dateObj = { name: dateValue, value: dateValue };
                        datePair.push(dateObj);
                    }

                    if (dateValue2) {
                        dateObj2 = { name: dateValue2, value: dateValue2 };
                        datePair.push(dateObj2);
                    }

                    if (dateObj) {
                        addToSelectedItems(index, [dateObj], '>=', datePair);
                    }

                    if (dateObj2) {

                        addToSelectedItems(index, [dateObj2], '<=', datePair);
                    }
                    break;

            }

        });

        emitter.fire('optionChanged', [selectedItems]);
        filterEmitter.fire('search', [selectedItems]);

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
    $('#ul-filter').delegate('[date-type]', 'click', function () {

        var btn = this;
        var outer = $(btn).closest('[id^=ul-options_]');

        var id = $(outer).attr('id');
        var index = getIndexFromId(id);


        var dateIndex = btn.getAttribute('date-index');
        var name = $.trim(btn.innerText);

        var dateValue1, dateValue2, dateObj, dateObj2;

        if (dateIndex != 'confirm') {
            addToSelectedItems(index, [{ name: name, value: '' }], dateIndex);
        }
        else {
            dateValue = document.getElementById('startTime').value;
            dateValue2 = document.getElementById('endTime').value;

            var datePair = [];

            if (dateValue) {
                dateObj = { name: dateValue, value: dateValue };
                datePair.push(dateObj);
            }

            if (dateValue2) {
                dateObj2 = { name: dateValue2, value: dateValue2 };
                datePair.push(dateObj2);
            }

            if (dateObj) {
                addToSelectedItems(index, [dateObj], '>=', datePair);
            }

            if (dateObj2) {

                addToSelectedItems(index, [dateObj2], '<=', datePair);
            }
        }

        emitter.fire('optionChanged', [selectedItems]);

        renderAfterSelected(index);

    });


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

        $.each(list, function (ele, i) {
            $.each(ele.items || [], function (item, index) {
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
            type: list[index].type,
            name: list[index].name,
            filterType: list[index].filterType,
            items: selectedItemsArray,
            compareType: compareType,
            displayItems: displayItems ? displayItems : selectedItemsArray

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
    var pickerId = 'div-address-picker-' + $.String.random();
    var pickerValues = [];

    var dataRangeId1 = 'data-range-' + $.String.random();
    var dataRangeId2 = 'data-range-' + $.String.random();

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
                    'hiddenClass': index >= 2 ? 'hidden' : ''
                });

            }).join('') + samples['datePicker']) : '',

            'input': option.filterType == 3 ? samples['input'] : '',

            'input-range': option.filterType == 4 ? $.String.format(samples['input-range'], {
                'data-range-index1': dataRangeId1,
                'data-range-index2': dataRangeId2
            }) : '',

            'flags': option.filterType == 5 ? $.map(option.items, function (item, index) {

                return $.String.format(samples['flags'], {
                    value: item.value
                });

            }).join('') : '',

            'area': option.filterType == 6 ? (function () {

                pickerValues = $.map(option.items, function (ele, i) {
                    return ele.areaID || -1;
                });

                return $.String.format(samples['area'], {
                    'div-address-picker': pickerId
                });
            })() : ''

        });
    }

    function render(data) {

        list = data;

        selectedItems = [];

        ul.innerHTML = $.Array.map(list, function (option, index) {

            return $.String.format(samples['group'], {
                name: option.name,
                index: index,
                hiddenClass: index > 2 ? 'hidden-option' : '',
                more: option.items && (option.items.length > showMoreOption) ? samples['more'] : '',
                'custom-date-range': option.filterType == 2 ? $.String.format(samples['custom-date-range'], {

                    'setting-btn-id': settingBtnId,
                    'data-index': index

                }) : '',
                //multiButton: option. samples['multiButton'],
                option: fillOption(option, index)
            });

        }).join('');


        if (pickerValues.length > 0) {
            picker = createPicker()
        }

        if (document.getElementById(dataRangeId1)) {
            createNumberField(dataRangeId1);
            createNumberField(dataRangeId2);
        }
    }

    function createPicker() {

        return SMS.CascadePicker.create({
            container: '#' + pickerId,
            hideNone: true,
            data: 'data/address/array.simple.js',
            //data: '../../../../htdocs/data/address/array.simple.js',
            varname: '__AddressData__',
            fields: {
                value: 0,
                text: 1,
                child: 2,
            },
            defaultTexts: ['--请选择省份--', '--请选择城市--', '--请选择地区--'],
            selectedValues: pickerValues

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

    return {
        render: render,
        recoverSelectedItems: recoverSelectedItems,
        getSelectedOptions: getSelectedOptions,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS, TimeZone);



var Notification = (function ($, MiniQuery, SMS) {

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



    $(wrapper).delegate('[data-item-index]', 'click', function () {

        var btn = this;

        var index = btn.getAttribute('data-item-index');

        $.each(list, function (i, item) {
            item.select = 0;
        });

        list[index].select = 1;

        render(list);

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
        render: render
    }

})(jQuery, MiniQuery, SMS);



(function ($, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification) {



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


    FilterOptions.on({
        'setting-options': function () {
            FilterData.loadOptions(function (data) {
                FilterOptions.render(data)
            });
        }
    });

    Filter.on({
        'optionChanged': function (selectedItems, type) {

            if (!type) {
                Path.render(selectedItems);

            }

        }
    });

})(jQuery, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification);
    }

    

    return {
        render: render,
        on: function (name, fn) {
            filterEmitter.on(name, fn);
        }
    }

});