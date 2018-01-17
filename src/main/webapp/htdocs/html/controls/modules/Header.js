



var Header = (function ($, MiniQuery, SMS) {
    
    var div = document.getElementById('div-header');


    function render(data) {


        SMS.Template.fill(div, {
            'name-display': 'name' in data ? '' : 'display: none;',
            'tips-display': 'tips' in data ? '' : 'display: none;',
            'name': data.name,
            'tips': data.tips,
        });
    }


    return {
        render: render,
    };



})($, MiniQuery, SMS);