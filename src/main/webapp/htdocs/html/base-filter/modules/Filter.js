define('Filter', function (require, module, exports) {

    var $ = require("$");
    var MiniQuery = require('MiniQuery');
    var emitter = MiniQuery.Event.create();
    var div = document.getElementById('myFilterBody');
    var CompareKey = require('CompareKey');

    var isFirst = true;
    var index = 0;
    var samples = $.String.getTemplates(div.innerHTML, [{
        name: 'data',
        begin: '<!--',
        end: '-->'
    }, {
        name: 't.filterKeys',
        begin: '#--filterKeys.begin--#',
        end: '#--filterKeys.end--#',
        outer: '{filterKeys}'
    }]);

    function getFilterObject() {
        var conditions = {};
        $("#myFilterBody tr").each(function (index, item) {
            var fieldKey = $(item).find("select[name='filterKeys']").val();
            var logicOperator = $(item).find("select[name='compareKeys']").val();
            var value = $(item).find("input[name='compareValue']").val();
            var joinType = $(item).find("select[name='joinType']").val();

            if (!fieldKey) {
                return true;
            }
            // 查询条件
            conditions[fieldKey] = {
                'andOr': joinType,
                'leftParenTheses': '(',
                'fieldKey': fieldKey,
                'logicOperator': logicOperator,
                'value': value,
                'rightParenTheses': ')'
            };
        })
        return conditions;

    }

    //界面呈现
    function render(fields, fn) {

        if (!fields && fields.length <= 0) {
            return;
        }

        var html = '';

        for (var i = 0; i < 2; i++) {
            // 提供两个过滤条件-and关系
            html += $.String.format(samples['data'], {
                'index': i,
                "filterKeys": $.Array.keep(fields, function (item, index) {
                    return $.String.format(samples['t.filterKeys'], {
                        'name': item.name,
                        'value': item.key,
                        'ctrlType': item.ctrlType,
                        'dataType': item.dataType,
                        'keyIndex': index
                    })
                }).join("")
            });
        }

        div.innerHTML = html;
        /*        if (isFirst) {
         //div.innerHTML
         $("#myFilterBody").html(html)
         isFirst = false;
         } else {
         $("#myFilterBody").append(html)
         index = index + 1;
         }*/
        fn && fn();
        emitter.fire('render.done');
    };

    $("#myFilterBody").on("change", "select[name='filterKeys']", function (ev) {
        var _this = $(this).children('option:selected')[0]
        var ctrlType = _this.getAttribute("ctrlType");
        var dataType = _this.getAttribute("dataType");
        var keyIndex = _this.getAttribute("keyIndex");
        if (!ctrlType && !dataType && !keyIndex) {
            return;
        }

        CompareKey.setCompareKey(this, dataType, ctrlType); //操作符呈现
        //emitter.fire('filterKey.change', [this, ctrlType, dataType, keyIndex]);
        ev.stopPropagation();
    })

    function setDefaultFilter(conditions) {
        var index = 0;
        for (var key in conditions) {
            console.log(conditions[key]);
            var condition = conditions[key]
            var fieldKey = condition.fieldKey;
            var logicOperator = condition.logicOperator;
            var value = condition.value;
            $("tr[index='" + index + "'] select[name='filterKeys']").val(fieldKey);
            $("tr[index='" + index + "'] select[name='filterKeys']").trigger("change");
            $("tr[index='" + index + "'] select[name='compareKeys']").val(logicOperator);
            $("tr[index='" + index + "'] input[name='compareValue']").val(value);
            index++;
        }
    }

    return {
        on: emitter.on.bind(emitter),
        getFilterObject: getFilterObject,
        setDefaultFilter: setDefaultFilter,
        render: render
    }

})