

define('Sorting', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var Sort,SortOptions;

    var sortingEmitter = MiniQuery.Event.create();

    function render(config) {

        var html = 


  
'<div id="sort-body" class="sort-body"><!--\n' +
    '        <span class="sort-title">排序：</span>\n' +
    '        <ul id="sort-items" class="sort-items">\n' +
    '\n' +
    '        #--sortPanel.begin--#\n' +
    '            <li class="sort-bt">\n' +
    '                <a data-field="{fieldName}" class="sorting-btn" href="javascript:">{displayName}</a>\n' +
    '            </li>\n' +
    '        #--sortPanel.end--#\n' +
    '\n' +
    '            <li id="add-sort" class="sort-bt">\n' +
    '                <a id="add-icon" class="add-icon" href="javascript:"></a>\n' +
    '                <ul id="add-sorting" class="add-sorting vertical-ul hidden">\n' +
    '                    <li class="line">\n' +
    '                        <ul id="add-sorting-options" class="g_options add-sorting-options">\n' +
    '                            #--add-sorting-options.begin--#\n' +
    '                            <li class="item">\n' +
    '                                <a data-option-index="{index}" class="option {active-class}" href="javascript:">{name}</a>\n' +
    '                            </li>\n' +
    '                            #--add-sorting-options.end--#\n' +
    '                        </ul>\n' +
    '                    </li>\n' +
    '                    <li class="line right">\n' +
    '                        <input id="{sorting-box-options-confirm}" class="btn-normal" type="button" value="确定" />\n' +
    '                        <input id="{sorting-box-options-cancel}" class="btn-normal btn-cancel" type="button" value="取消" />\n' +
    '                  \n' +
    '                    </li>\n' +
    '                </ul>\n' +
    '            </li>\n' +
    '\n' +
    '        </ul>\n' +
    '    --></div>'

;


        document.getElementById(config.id).innerHTML = html;

        var sorting = config.sorting;
        if(sorting){
            sortingEmitter.on('sorting',sorting);
        }

        var saveOptions = config.saveOptions;
        if(saveOptions){
            sortingEmitter.on('save-sorting-options',saveOptions);
        }

        
Sort = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('sort-body');

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "sortPanel",
            begin: "#--sortPanel.begin--#",
            end: "#--sortPanel.end--#",
            outer: "{sortPanel}"
        }
    ]);

    var list = [];

    var modelList = [];

    var emitter = MiniQuery.Event.create();

    function toggleDire(dataObj, docObj) {
        if (dataObj.dire == 'asc') {
            dataObj.dire = '';
            $(docObj).removeClass('down').removeClass('up').removeClass('selected');
        }
        else if (dataObj.dire == 'desc') {
            dataObj.dire = 'asc';
            $(docObj).removeClass('down').addClass('up').addClass('selected');
        }
        else {
            dataObj.dire = 'desc';
            $(docObj).removeClass('up').addClass('down').addClass('selected');
        }
    }

    $(wrapper).delegate('[data-field]', 'click', function () {

        var btn = this;
        var field = btn.getAttribute('data-field');

        var index = $.Array.findIndex(list, function (item) {
            return item.fieldName == field;
        });

        if (index > -1) {
            toggleDire(list[index], btn);
        }


        emitter.fire('sort', [getListString()]);

    });

    function getListString() {

        return $.map(list, function (item, index) {
            return item.dire ? (item.fieldName + ' ' + item.dire) : null;
        }).join(',') || 'createTime desc';

    }



    //function loadModelList(data) {
    //    modelList = data;
    //}

    //function findDisplayByField(fieldName) {

    //    return $.Array.findItem(modelList, function (model, index) {
    //        return model.fieldName == fieldName;
    //    }).displayName;

    //}

    function render(data) {

        list = $.Array.keep(data, function (item, index) {
            return {
                displayName: item.name,
                fieldName: item.value,
                dire: ''

            }


        });

        wrapper.innerHTML = $.String.format(samples['group'], {
            'sortPanel': $.Array.keep(list, function (item, index) {

                return $.String.format(samples['sortPanel'], {
                    fieldName: item.fieldName,
                    displayName: item.displayName
                });

            }).join('')
        })
    }

    return {
        render: render,
        //loadModelList: loadModelList,
        getListString: getListString,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }


})(jQuery, MiniQuery, SMS);



SortOptions = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('sort-body');

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: "add-sorting-options",
            begin: "#--add-sorting-options.begin--#",
            end: "#--add-sorting-options.end--#"
        }
    ]);

    var sortingOptionsList = [];

    var confirmBtnId = 'sorting-box-options-confirm' + $.String.random();
    var cancelBtnId = 'sorting-box-options-cancel' + $.String.random();

    var emitter = MiniQuery.Event.create();


    var required = false;
    var isSortingOptionsHide = false;

    $(wrapper).delegate('#add-icon', 'click', function () {

        $('#add-sorting').toggleClass('hidden');

        if (!$('#add-sorting').hasClass('hidden')) {

            if (wrapper.innerHTML.indexOf(confirmBtnId) > -1) {

                wrapper.innerHTML = $.String.format(wrapper.innerHTML, {
                    'sorting-box-options-confirm': confirmBtnId,
                    'sorting-box-options-cancel': cancelBtnId
                });
            }

            document.getElementById('add-sorting-options').innerHTML = $.map(sortingOptionsList || [], function (item, index) {

                return $.String.format(samples['add-sorting-options'], {
                    'name': item.name,
                    'index': index,
                    'active-class': item.shown ? 'item-selected' : ''

                });

            }).join('');
        }

        if (!required) {


            $('#add-sort').on('mouseover', function () {
                isSortingOptionsHide = false;
            });

            $('#add-sort').on('mouseout', function () {
                isSortingOptionsHide = true;
            });

            $(document).on('click', function () {

                if (isSortingOptionsHide) {
                    hideSortingOptions();
                }
            });

            required = true;
        }
    });

    $(wrapper).delegate('#' + confirmBtnId, 'click', function () {

        $.each(selectedItems, function (index, item) {

            sortingOptionsList[+item].shown = true;
        });

        $.each(deletedItems, function (index, item) {

            sortingOptionsList[+item].shown = false;
        });

        hideSortingOptions();

        var args = $.map(sortingOptionsList, function (item) {

            return item.shown ?
                { sequenceID: item.sequenceID } : null

        });

        emitter.fire('save-sorting-options', [args]);
    });

    $(wrapper).delegate('#' + cancelBtnId, 'click', function () {
        hideSortingOptions();
    });

    var selectedItems = [];
    var deletedItems = [];

    function deleteItem(list, index) {
        var i = list.indexOf(index);
        if (i > -1) {
            list.splice(i, 1);
        }
    }

    function hideSortingOptions() {
        $('#add-sorting').addClass('hidden');
        selectedItems = [];
        deletedItems = [];
    }


    $(wrapper).delegate('[data-option-index]', 'click', function () {

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

    function loadSortingOptions(data) {
        sortingOptionsList = data;
    }

    return {
        loadSortingOptions: loadSortingOptions,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);


(function ($, MiniQuery, SMS, Sort, SortOptions) {

    Sort.render(config.sq);

    SortOptions.loadSortingOptions(config.allsq);

    Sort.on({
        'sort': function (sortString) {
         
            sortingEmitter.fire('sorting', [sortString]);
        }
    });

    SortOptions.on({
        'save-sorting-options': function (sortingList) {
            
            sortingEmitter.fire('save-sorting-options', [sortingList]);
        }
    });

})(jQuery, MiniQuery, SMS, Sort, SortOptions);
    }


    

    return {
        render: render,
        sortingRender:function(sq){
            Sort.render(sq)
        },
        loadSortingOptions: function(allsq){
            SortOptions.loadSortingOptions(allsq)
        },
        getListString:function(){
            return Sort && Sort.getListString()  || 'createTime desc';
        }
    }

});