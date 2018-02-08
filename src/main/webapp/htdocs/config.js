;(function ($, MiniQuery, SMS) {


    /**
     * 字符串中的 {~} 表示站头的根地址；{@} 表示使用的文件版本 debug 或 min
     *
     */

    SMS.config({

        // Web 站点的根地址
        Url: {
            'default': $('script[src*="config.js"]').get(0).src.split('config.js')[0]
        },

        //后台接口
        API: {
            //后台接口的基础地址
            url: 'http://127.0.0.1:8081/sms-cloud/manager/',  //内网
            //url: 'http://172.20.131.250:8080/sms/',    //外网
            codes: {
                // 成功返回码-跟后端定义一致
                success: 200,
                // session失效返回码
                sessionLost: 10000,
            },
        },

        //代理到本地网站根目录下的文件。 
        //不指定时则请求后台的真实数据。
        //格式为 接口名称: 本地代理的处理文件名
        Proxy: {

            //控件示例
            'demo/1': 'api/demo/1.js',
            'demo/2': 'api/demo/2.js',
            'demo/3': 'api/demo/3.js',
            'demo/4': 'api/demo/4.js',


            'user/login': [ //当指定为一个数组时，则起作用的是最后一个
                //'api/portal/login.js',
                // 'api/portal/login-action.js',

            ],

        },

        //简易分页器
        SimplePager: {
            container: '#div-pager-simple',
            current: 1,
            size: 10,
            hideIfLessThen: 0,
        },

        //级联路径默认配置
        CascadePath: {
            activedClass: 'on',
            fields: {
                text: 'name',
                child: 'items',
            },
        },

        //级联弹出菜单默认配置
        CascadeMenus: {
            activedClass: 'actived',
            leafClass: 'leaf-item',
            delay: 100,
            fields: {
                text: 'name',
                child: 'items',
            },
        },

        //级联导航器默认配置
        CascadeNavigator: {
            containers: {
                path: '#div-cascade-path',
                menus: '#div-cascade-menus'
            },
            fields: {
                text: 'name',
                child: 'items',
            },
        },

        //级联选择器默认配置
        CascadePicker: {
            selectedIndexes: [-1, -1, -1],
            defaultTexts: [],
            defaultText: '--请选择--',
            hideNone: true,
            data: 'data/address/array.simple.js',
            varname: '__AddressData__',
            fields: {
                value: 0,
                text: 1,
                child: 2
            }
        },

        //动态加载模块的默认配置 (for seajs)
        Seajs: {
            base: '{~}lib/',
            debug: true,
            alias: {
                juery: 'jquery/jquery.js',
                marked: 'marked/marked.mod.{@}.js',
                dialog: 'art-dialog/dialog.all.{@}.js?r=' + Math.random(),
                autoNumeric: 'autoNumeric/autoNumeric.{@}.js',
                'datetimepicker-css': 'datetimepicker/css/datetimepicker.mod.{@}.css#',
                'datetimepicker-js': 'datetimepicker/js/datetimepicker.mod.{@}.js',
                'zTree-js': 'ztree/js/jquery.ztree.all-3.5.js?r=' + Math.random(),
                'zTree-css': 'ztree/css/zTreeStyle/zTreeStyle.css#',
                'grid-base-js': 'jqgrid/grid.base.js?r=' + Math.random(),
                'grid-celledit-js': 'jqgrid/grid.celledit.js',
                'grid-custom-js': 'jqgrid/grid.custom.js',
                'grid-common-js': 'jqgrid/grid.common.js',
                'grid-locale-cn-js': 'jqgrid/i18n/grid.locale-cn.js',
                'jquery-combo-js': 'jqgrid/jquery.combo.js',
                'jqgrid-css': 'jqgrid/ui.jqgrid.css#',
                'ui-css': 'jqgrid/ui.css#',
                'common-css': 'jqgrid/common.css#',
                'jqgrid-all-js': 'jqGrid-5.1.0/jqGrid-master/js/jquery.jqGrid.js',
                'jqgrid-all-css': 'jqGrid-5.1.0/jqGrid-master/css/ui.jqgrid.css#',
                'jquery-barcode': 'jquery-barcode-2.0.3/jquery/jquery-barcode.{@}.js',
                'jquery.jqprint': 'jquery.jqprint/jquery.jqprint-0.3.js',
                'jquery-migrate': 'jquery.jqprint/jquery-migrate-1.2.1.min.js',
                'easy-tabs': 'jspkg-archive/lib/jquery.easytabs.{@}.js',
            }
        },


        Loading: {
            text: '数据加载中...',
        },


        //弹出对话框默认配置 (artDialog)
        Dialog: {
            cssUri: '{~}lib/art-dialog/dialog.all.{@}.css#',
            backdropOpacity: 0.2,       //遮罩层不透明度(越小越透明)
            backdropBackground: '#000', //遮罩层背景色
            quickClose: false,          //是否支持快捷关闭(点击遮罩层自动关闭)
            fixed: true,                //是否固定定位
            draggable: true,            //是否可拖动 (by micty)
            zIndex: 1024,               // 对话框叠加高度值(重要：此值不能超过浏览器最大限制)
        },

        //数值型输入框
        NumberField: {
            groupSign: ',',         //分组的分隔符号
            groupCount: 3,          //分组的位数
            decimalSign: '.',       //小数点的符号
            decimalKey: null,       //输入小数点的代替键，一般不需要用到
            currencySign: '',       //货币的符号，如 '$'|'¥'|'€' 之类的
            currencyPlace: 'left',  //货币的符号所处的位置，前面或后面，取值: 'left'|'right'
            min: '0.00',            //允许的最小值，必须用字符串
            max: '9999999999999.99',//允许的最大值，必须用字符串，且比 min 要大
            decimalCount: 2,        //小数的位数
            round: 'S',             //四舍五入
            padded: true,           //是否用 "0" 填充小数位，取值: true|false
            bracket: null,          //输入框失去焦点后，负数的代替展示括号，不指定则原样展示

            /** Displayed on empty string
             * 'empty', - input can be blank
             * 'zero', - displays zero
             * 'sign', - displays the currency sign
             */
            empty: 'empty',         //输入框为空时的显示行为

            /** controls leading zero behavior
             * 'allow', - allows leading zeros to be entered. Zeros will be truncated when entering additional digits. On focusout zeros will be deleted.
             * 'deny', - allows only one leading zero on values less than one
             * 'keep', - allows leading zeros to be entered. on fousout zeros will be retained.
             */
            leadingZero: 'allow',   //前缀 "0" 的展示行为
            formatted: true,        //控制是否在页面就绪时自动格式化输入框的值，取值: true|false
        },

        ZTree: {
            treeId: '',
            treeObj: null,
            callback: {},
            view: {
                showIcon: true,
                showLine: true,
                dblClickExpand: true,//双击展开
                selectedMulti: false,//是否允许多选
                addDiyDom: function (treeId, treeNode) {

                    var text = $("#" + treeNode.tId + "_span").html();
                    if (text.length > 15) {
                        text = text.substring(0, 15) + "...";
                        $("#" + treeNode.tId + "_span").html(text);
                    }
                }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0,
                }
            },
            check: {
                enable: false,
                chkStyle: "checkbox",
                chkboxType: {"Y": "p", "N": "s"},
            },

        },

        DateTimePicker: {
            language: 'zh-CN',//显示中文
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            timepicker: false,
            startView: 'month',
            minView: 'hour',
        },

        Module: {},

        Login: {
            apiLogin: 'user/login',
            apiLoginout: 'user/logout',
            actions: {
                login: 'login',
                logout: 'logout',
            },

            files: {
                master: 'master.html',
                login: 'login.html',
            },
        },

        //按钮组
        ButtonList: {
            activedClass: 'on',
            fields: {
                text: 'text',
                child: 'items',
                callback: 'fn',
                route: 'name',
                icon: 'icon',
            },
            autoClose: true,
        },


        Menu: {
            alias: {
                'abc': [0, 0],
            },
        }

    });


    //调试模式下使用。
    //使用 grunt 工具构建页面后，本区代码可以去掉
    if (SMS.require('Debug').check()) {

        var Module = SMS.require('Module');
        var define = Module.define;

        define('$', function () {
            return window.$;
        });

        define('MiniQuery', function () {
            return window.MiniQuery;
        });

        define('SMS', function () {
            return window.SMS;
        });

        window.define = define;
        window.require = Module.require;
    }


})(jQuery, MiniQuery, SMS);


