










; (function ($, MiniQuery, SMS) {


    SMS.NumberField = (function () {


        var mapper = new $.Mapper();

        var key$key = {

            groupSign: 'aSep',      //分组的分隔符号，默认为逗号 ","
            groupCount: 'dGroup',   //分组的位数
            decimalSign: 'aDec',    //小数点的符号，默认为 "."
            decimalKey: 'altDec',   //输入小数点的代替键，一般不需要用到
            currencySign: 'aSign',  //金钱的符号
            currencyPlace: 'pSign', //金钱的符号所处的位置，前面或后面
            min: 'vMin',            //允许的最小值
            max: 'vMax',            //允许的最大值
            decimalCount: 'mDec',   //小数的位数
            round: 'mRound',        //四舍五入
            padded: 'aPad',         //是否用 "0" 填充小数位
            bracket: 'nBracket',    //输入框失去焦点后，负数的展示括号
            empty: 'wEmpty',        //输入框为空时的显示行为
            leadingZero: 'lZero',   //前缀 "0" 的展示行为
            formatted: 'aForm',     //控制是否在页面就绪时自动格式化输入框的值
        };

        var key$value$value = {

            currencyPlace: {
                left: 'p', //前缀
                right: 's' //后缀
            }
        };




        function normalizeObject(config) {

            var obj = {};

            $.Object.each(config, function (key, value) {

                var oldKey = key$key[key];

                if (oldKey) {

                    var value$value = key$value$value[key];
                    if (value$value) {
                        value = value$value[value];
                    }
                    obj[oldKey] = value;
                }
                else {
                    obj[key] = value;
                }

            });

            return obj;

        }


        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $this = meta.$this;

            var args = [].slice.call($argumetns, 0);
            args = [name].concat(args);

            return $this.autoNumeric.apply($this, args);

        }


        function NumberField(selector, config) {

            if (arguments.length == 1) { // NumberField( config )
                config = selector;
                selector = config.selector;
            }

            config = normalizeObject(config);
            console.log(config);

            var $this = $(selector).autoNumeric(config);

            var meta = {
                $this: $this,
            };

            mapper.set(this, meta);

        }



        NumberField.prototype = { //实例方法
            constructor: NumberField,

            init: function () {
                invoke(this, 'init', arguments);
            },

            destroy: function () {
                invoke(this, 'destroy', arguments);
            },

            update: function () {
                invoke(this, 'update', arguments);
            },

            set: function () {
                invoke(this, 'set', arguments);
            },

            get: function () {
                return invoke(this, 'get', arguments);
            },

            getString: function () {
                return invoke(this, 'getString', arguments);
            },

            getArray: function () {
                return invoke(this, 'getArray', arguments);
            },

            getSettings: function () {
                return invoke(this, 'getSettings', arguments);
            },
        };



        return $.Object.extend(NumberField, { //静态方法

            create: function (selector, config) {

                return new NumberField(selector, config);

            }

        });




    })();




    var nf = SMS.NumberField.create('#txt', {

        currencySign: '$',
        currencyPlace: 'left',
        padded: false,
        empty: 'zero',
        leadingZero: 'deny',
        decimalCount: 3,
        
    });


    $('#txt').focusout(function () {
        //debugger;
        var v = nf.get();
        console.log(v);
    });


    //$('#txt').autoNumeric('init', {
    //    aSign: 'Y',
    //    aPad: false,
    //    wEmpty: 'zero',
    //    lZero: 'allow',
    //});


})(jQuery, MiniQuery, SMS);