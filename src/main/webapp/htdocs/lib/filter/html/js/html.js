
  
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

