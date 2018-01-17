


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
