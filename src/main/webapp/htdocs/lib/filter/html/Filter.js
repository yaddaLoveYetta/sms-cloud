
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
        //filterEmitter.fire('search', [selectedItems]);

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