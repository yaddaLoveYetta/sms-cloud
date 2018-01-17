define("NumberField", function (require, exports, module) {

    var SMS = require("SMS");

    function render() {

        SMS.use('NumberField', function (NumberField) {

            var intInput = new NumberField(':input[ctltype="int"]', {
                min: 0,
                groupCount: 0,
                empty: 0,
                padded: false
            });

            var floatInput = new NumberField(':input[ctltype="float"]', {
                min: 0,
                empty: 0,
                decimalCount: 2,
                padded: true
            });
        });
    };

    return {
        render: render
    };
});