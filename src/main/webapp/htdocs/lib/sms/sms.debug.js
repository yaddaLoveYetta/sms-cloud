/*!
 * HRP-SMS 供应商平台 前端开发框架: SMS.js
 * 版本: 0.1.0
 */
//================================================================================================================>
//开始 SMS.debug.js
;(function (global,
            top,
            parent,
            window,
            document,
            location,
            localStorage,
            sessionStorage,
            console,
            history,
            setTimeout,
            setInterval,
            $,
            jQuery,
            MiniQuery,
            Array,
            Boolean,
            Date,
            Error,
            Function,
            Math,
            Number,
            Object,
            RegExp,
            String,
            undefined) {


    /**
     * 内部的模块管理器
     */
    var Module = (function () {


        var id$module = {};


        /**
         * 定义指定名称的模块。
         *
         * @param {string}
         *            id 模块的名称。
         * @param {Object|function}
         *            exports 模块的导出函数。
         */
        function define(id, exports) {
            id$module[id] = {
                required: false,
                exports: exports,
                exposed: false      // 默认对外不可见
            };
        }

        /**
         * 加载指定的模块。
         *
         * @param {string}
         *            id 模块的名称。
         * @return 返回指定的模块。
         */
        function require(id) {

            var module = id$module[id];
            if (!module) { // 不存在该模块
                return;
            }

            var exports = module.exports;

            if (module.required) { // 已经 require 过了
                return exports;
            }

            // 首次 require
            if (typeof exports == 'function') { // 工厂函数

                var fn = exports;
                exports = {};

                var mod = {
                    id: id,
                    exports: exports,
                };

                var value = fn(require, mod, exports);

                // 没有通过 return 来返回值，则要导出的值在 mod.exports 里
                exports = value === undefined ? mod.exports : value;
                module.exports = exports;
            }

            module.required = true; // 指示已经 require 过一次

            return exports;

        }

        /**
         * 异步加载指定的模块，并在加载完成后执行指定的回调函数。
         *
         * @param {string}
         *            id 模块的名称。
         * @param {function}
         *            fn 模块加载完成后要执行的回调函数。 该函数会接收到模块作为参数。
         */
        function async(id, fn) {

            var module = require(id);

            if (module) {
                var exposed = id$module[id].exposed;
                fn && fn(exposed ? module : null);
                return;
            }

            var $ = require('$');
            var Url = require('Url');

            var url = Url.format('{~}lib/sms/{0}.{@}.js', id);

            $.Script.load(url, function () {
                module = require(id);
                var exposed = id$module[id].exposed;
                fn && fn(exposed ? module : null);
            });

        }


        /**
         * 设置或获取对外暴露的模块。 通过此方法，可以控制指定的模块是否可以通过 SMS.require(id) 来加载到。
         *
         * @param {string|Object}
         *            id 模块的名称。 当指定为一个 {} 时，则表示批量设置。 当指定为一个字符串时，则单个设置。
         * @param {boolean}
         *            [exposed] 模块是否对外暴露。 当参数 id 为字符串时，且不指定该参数时，表示获取操作， 即获取指定 id
         *            的模块是否对外暴露。
         * @return {boolean}
         */
        function expose(id, exposed) {

            if (typeof id == 'object') { // expose({ })，批量 set
                $.Object.each(id, function (id, exposed) {
                    set(id, exposed);
                });
                return;
            }

            if (arguments.length == 2) { // expose('', true|false)，单个 set
                set(id, exposed);
                return;
            }

            // get
            return get(id);


            // 内部方法
            function get(id) {
                var module = id$module[id];
                if (!module) {
                    return false;
                }

                return module.exposed;
            }

            function set(id, exposed) {
                var module = id$module[id];
                if (module) {
                    module.exposed = !!exposed;
                }
            }
        }


        return {
            define: define,
            require: require,
            async: async,
            expose: expose
        };


    })();


    // 提供快捷方式
    var define = Module.define;
    var require = Module.require;


    define('$', function (require, exports, module) {

        return window.$;

    });


    define('MiniQuery', function (require, exports, module) {

        return window.MiniQuery;

    });


    /**
     * 请求后台 API 的接口类
     *
     * @namespace
     * @author micty
     */
    define('API', function (require, exports, module) {

        var $ = require('$');
        var Proxy = require('Proxy');
        var Mapper = $.Mapper;

        var mapper = new Mapper();
        var guidKey = Mapper.getGuidKey();


        // 默认配置
        var defaults = {};


        function parseJSON(data) {

            try {
                return window.JSON.parse(data);
            }
            catch (ex) {

            }

            try {
                data = data.replace(/^(\r\n)+/g, '');
                return (new Function('return ' + data))();
            }
            catch (ex) {

            }

            return null;

        }


        function ajax(config) {

            if (Proxy.request(config)) { // 使用了代理
                return;
            }


            var url = defaults.url + config.name;// + '.do';
            var method = (config.method || 'get').toLowerCase();

            var data = config.data || null; // null 可能会在 xhr.send(data) 里用到
            if (data) {

                data = $.Object.map(data, function (key, value) {

                    if (typeof value == 'object' && value) { // 子对象编码成 JSON
                        // 字符串
                        return $.Object.toJson(value);
                    }

                    // 其他的
                    return value; // 原样返回
                });

                data = $.Object.toQueryString(data);

                if (method == 'get') {
                    url += '?' + data;
                    data = null; // 要发送的数据已附加到 url 参数上
                }
                else { // post
                    var query = config.query;
                    if (query) {
                        query = $.Object.toQueryString(query);
                        url += '?' + query;
                    }
                }
            }

            // // IE8+的跨域问题。
            // // post 时，XDomainRequest 对象不能设置 HTTP 头：
            // // Content-Type: application/x-www-form-urlencoded
            // // 因此需要服务器去处理原始的 post 数据。
            // if (window.XDomainRequest) {
            // var xdr = new XDomainRequest();
            // xdr.open(method, url);
            // xdr.onload = function () {

            // var fnError = config.error;

            // var json = parseJSON(xdr.responseText);
            // if (!json) {
            // fnError && fnError();
            // return;
            // }

            // var code = json['code'];
            // if (code == 200) {
            // var fnSuccess = config.success;
            // fnSuccess && fnSuccess(json['data'] || {}, json);
            // }
            // else {
            // var fnFail = config.fail;
            // fnFail && fnFail(code, json['msg'], json);
            // }
            // };

            // xdr.onerror = function () {
            // var fnError = config.error;
            // fnError && fnError();
            // };

            // xdr.send(data);

            // return;
            // }


            var xhr = new XMLHttpRequest();
            xhr.open(method, url, true);

            xhr.onreadystatechange = function () {

                if (xhr.readyState != 4) {
                    return;
                }

                var successCode = defaults.codes['success'];
                var sessionLostCode = defaults.codes['sessionLost'];
                var fnError = config.error;

                if (xhr.status != successCode) {
                    fnError && fnError(xhr);
                    return;
                }

                var json = parseJSON(xhr.responseText);
                if (!json) {
                    fnError && fnError(xhr);
                    return;
                }

                var code = json['code'];
                if (code == successCode) {
                    var fnSuccess = config.success;
                    fnSuccess && fnSuccess(json['data'] || {}, json, xhr);
                } else if (code == sessionLostCode) {
                    // 只移除会话级的
                    $.SessionStorage.remove('SMS.Login.user.F5F2BA55218E');
                    // 重新登录
                    SMS.Login.show();
                    //SMS.Login.check(true);
                    return;
                }
                else {
                    var fnFail = config.fail;
                    fnFail && fnFail(code, json['msg'], json, xhr);
                }
            };

            if (method == 'post') {
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            }

            xhr.send(data);
        }


        /**
         * 构造器。
         */
        function API(name, config) {

            if (typeof name == 'object') { // 重载 API(config)
                config = name;
                name = config.name;
            }

            config = config || {};


            this[guidKey] = 'API-' + $.String.random();

            var codes = defaults.codes;

            var emitter = MiniQuery.Event.create();

            var meta = {
                'name': name,
                'data': config.data,
                'query': config.query,
                'status': '',
                'args': [],
                'emitter': emitter,
                'url': defaults.url + name
            };

            mapper.set(this, meta);

            $.Object.extend(meta, {

                fireEvent: function (status, args) {

                    status = meta.status = status || meta.status;
                    args = meta.args = args || meta.args;

                    emitter.fire('done', args); // 触发总事件
                    emitter.fire(status, args); // 触发命名的分类事件

                    // 进一步触发具体 code 对应的事件
                    if (status == 'success') {
                        emitter.fire(codes['success'], args);
                    }
                    else if (status == 'fail') {
                        emitter.fire(args.shift(), args);
                    }
                },

                success: function (data, json, xhr) { // 成功
                    meta.fireEvent('success', [data, json, xhr]);
                },

                fail: function (code, msg, json, xhr) { // 失败
                    meta.fireEvent('fail', [code, msg, json, xhr]);

                },

                error: function (xhr) { // 错误
                    meta.fireEvent('error', [xhr]);
                },
            });

            // 预处理特定类型的事件
            var self = this;

            $.Array.each([
                'done',
                'success',
                'fail',
                'error',
                codes['success'],

            ], function (name, index) {

                name = name.toString(); // 在绑定事件时，只识别 string 类型的名称

                var fn = config[name];
                if (fn) {
                    self.on(name, fn);
                }
            });

        }

        // 实例方法
        API.prototype = {
            constructor: API,

            /**
             * 发起 GET 网络请求。 请求完成后会最先触发相应的事件。
             *
             * @param {Object}
             *            [data] 请求的数据对象。 该数据会给序列化成查询字符串以拼接到 url 中。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            get: function (data) {

                var meta = mapper.get(this);

                ajax({
                    method: 'get',
                    name: meta.name,
                    data: data || meta.data,
                    success: meta.success,
                    fail: meta.fail,
                    error: meta.error,
                });

                return this;
            },

            /**
             * 发起 POST 网络请求。 请求完成后会最先触发相应的事件。
             *
             * @param {Object}
             *            [data] POST 请求的数据对象。
             * @param {Object}
             *            [query] 查询字符串的数据对象。 该数据会给序列化成查询字符串，并且通过 form-data
             *            发送出去。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            post: function (data, query) {

                var meta = mapper.get(this);

                ajax({
                    method: 'post',
                    name: meta.name,
                    data: data || meta.data,
                    query: query || meta.query,
                    success: meta.success,
                    fail: meta.fail,
                    error: meta.error,
                });

                return this;

            },

            /**
             * 请求完成时触发。 不管请求完成后是成功、失败、错误，都会触发，会最先触发此类事件。
             *
             * @param {function}
             *            fn 回调函数。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            done: function (fn) {
                this.on('done', fn);
                return this;
            },

            /**
             * 请求成功时触发。 成功是指网络请求成功，且后台业务返回的数据包中的 code == 200 的情形。
             *
             * @param {function}
             *            fn 回调函数。
             */
            success: function (fn) {
                this.on('success', fn);
                return this;
            },

            /**
             * 请求失败时触发。 失败是指网络请求成功，但后台业务返回的数据包中的 code != 200 的情形。
             *
             * @param {function}
             *            fn 回调函数。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            fail: function (fn) {

                this.on('fail', fn);
                return this;
            },

            /**
             * 请求错误时触发。 错误是指网络请求不成功，如网络无法连接、404错误等。
             *
             * @param {function}
             *            fn 回调函数。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            error: function (fn) {
                this.on('error', fn);
                return this;
            },

            /**
             * 绑定事件。 已重载 on({...}，因此支持批量绑定。
             *
             * @param {string}
             *            name 事件名称。
             * @param {function}
             *            fn 回调函数。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            on: function (name, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;

                var args = [].slice.call(arguments, 0);
                emitter.on.apply(emitter, args);

                if (meta.status) {
                    meta.fireEvent();
                }

                return this;

            },

            /**
             * 解除绑定事件。 已重载 off({...}，因此支持批量解除绑定。
             *
             * @param {string}
             *            [name] 事件名称。 当不指定此参数时，则解除全部事件。
             * @param {function}
             *            [fn] 要解除绑定的回调函数。 当不指定此参数时，则解除参数 name 所指定的类型的事件。
             * @return {API} 返回当前 API 的实例 this，因此进一步可用于链式调用。
             */
            off: function (name, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;

                var args = [].slice.call(arguments, 0);
                emitter.off.apply(emitter, args);

                return this;
            },
            /**
             * 最终请求的路径
             * @return {*}
             */
            getUrl: function () {
                var meta = mapper.get(this);
                return meta.url;
            },
            /**
             * get请求方式的完整带参数url
             * @param data
             * @returns {*}
             */
            getUrlWithGetParams: function (data) {

                var meta = mapper.get(this);

                var url = meta.url;

                data = data || meta.data;

                if (data) {

                    data = $.Object.map(data, function (key, value) {

                        if (typeof value == 'object' && value) { // 子对象编码成 JSON
                            // 字符串
                            return $.Object.toJson(value);
                        }

                        // 其他的
                        return value; // 原样返回
                    });

                    data = $.Object.toQueryString(data);

                    url += '?' + data;
                }

                return url;
            }
        };


        // 静态方法
        return $.Object.extend(API, {

            ajax: ajax,

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }
        });

    });


    /**
     * 代理到本地，测试用
     *
     * @namespace
     * @author micty
     */
    define('Proxy', function (require, exports, module) {

        var $ = require('$');
        var Url = require('Url');
        var Debug = require('Debug');
        var Seajs = require('Seajs');
        var File = require('File');


        var name$file = {};     // {接口名称: 本地代理的处理文件名}，通过 config(obj) 指定。
        var url$config = {};    // {请求地址: 请求参数}


        function parseJSON(data) {

            try {
                return window.JSON.parse(data);
            }
            catch (ex) {
                try {
                    data = data.replace(/^(\r\n)+/g, '');
                    return (new Function('return ' + data))();
                }
                catch (ex) {
                    return null;
                }
            }

            return null;

        }

        // 模拟一个网络的随机延迟时间去执行一个回调函数
        function delay(fn) {

            if (!fn) {
                return;
            }

            var args = [].slice.call(arguments, 1); // 提取后面的参数
            var timeout = $.Math.randomInt(500, 3000);

            setTimeout(function () {

                fn.apply(null, args);

            }, timeout);
        }


        function request(config) {

            var name = config.name;
            var file = name$file[name];

            if (file instanceof Array) { // 当指定为一个数组时，则起作用的是最后一个
                file = file.pop();
            }

            if (!file) {
                return false;
            }


            if (File.isJs(file)) { // 映射的响应是 js 文件

                var url = Url.check(file) ? file : Url.root() + file;
                url = $.Url.randomQueryString(url); // 增加随机查询字符串，确保拿到最新的

                url$config[url] = config; // 把本次请求的参数保存下来

                Seajs.use(url, function (json) {

                    if (!json) {
                        delay(config.error);
                        return;
                    }

                    var code = json['code'];
                    if (code == 200) {
                        delay(config.success, json['data'] || {}, json);
                    }
                    else {
                        delay(config.fail, code, json['msg'], json);
                    }
                });

                return true;
            }


            if (file === true) {
                file = name + '.json';
            }

            var url = Url.root() + file + '?r=' + Math.random();

            var xhr = new XMLHttpRequest();
            xhr.open('get', url, true);


            xhr.onreadystatechange = function () {

                if (xhr.readyState != 4) {
                    return;
                }

                if (xhr.status != 200) {
                    delay(config.error);
                    return;
                }

                var json = parseJSON(xhr.responseText);

                if (!json) {
                    delay(config.error);
                    return;
                }

                var code = json['code'];
                if (code == 200) {
                    delay(config.success, json['data'] || {}, json);
                }
                else {
                    delay(config.fail, code, json['msg'], json);
                }

            };

            xhr.send(null);

            return true;


        }


        // 可以生成很复杂的动态数据，并根据提交的参数进行处理。
        // 具有真正模拟后台逻辑的能力。
        function response(action, fns) {

            // seajs
            // 安全起见，这里用 seajs.Module.define 而非 window.define
            window.seajs.Module.define(function (require, exports, module) {

                var config = getConfig(module);
                var data = config.data;

                if ($.Object.isPlain(action)) { // response({})
                    return action;
                }

                var fn = typeof action == 'function' ? action   // response(fn)
                    : fns[data[action]];                        // response('',
                                                                // {})

                if (fn) {
                    return fn(data, config) || {};
                }

                return {};

            });
        }


        function config(obj) {

            if (obj) { // set
                $.Object.extend(name$file, obj);
            }
            else { // get
                return $.Object.extend({}, name$file);
            }
        }


        function getConfig(url) {
            if (typeof url == 'object') { // module
                url = url['uri']; // module.uri
            }

            var obj = url$config[url];
            delete url$config[url]; // 已获取使用了，没必要保留了

            return obj;
        }


        return {
            request: request,
            response: response,
            config: config
        };

    });


    define('MD5', function (require, exports, module) {


        /* md5 生成算法 */
        var hexcase = 0;
        var chrsz = 8;


        function core_md5(x, len) {
            x[len >> 5] |= 0x80 << ((len) % 32);
            x[(((len + 64) >>> 9) << 4) + 14] = len;

            var a = 1732584193;
            var b = -271733879;
            var c = -1732584194;
            var d = 271733878;

            for (var i = 0; i < x.length; i += 16) {
                var olda = a;
                var oldb = b;
                var oldc = c;
                var oldd = d;

                a = md5_ff(a, b, c, d, x[i + 0], 7, -680876936);
                d = md5_ff(d, a, b, c, x[i + 1], 12, -389564586);
                c = md5_ff(c, d, a, b, x[i + 2], 17, 606105819);
                b = md5_ff(b, c, d, a, x[i + 3], 22, -1044525330);
                a = md5_ff(a, b, c, d, x[i + 4], 7, -176418897);
                d = md5_ff(d, a, b, c, x[i + 5], 12, 1200080426);
                c = md5_ff(c, d, a, b, x[i + 6], 17, -1473231341);
                b = md5_ff(b, c, d, a, x[i + 7], 22, -45705983);
                a = md5_ff(a, b, c, d, x[i + 8], 7, 1770035416);
                d = md5_ff(d, a, b, c, x[i + 9], 12, -1958414417);
                c = md5_ff(c, d, a, b, x[i + 10], 17, -42063);
                b = md5_ff(b, c, d, a, x[i + 11], 22, -1990404162);
                a = md5_ff(a, b, c, d, x[i + 12], 7, 1804603682);
                d = md5_ff(d, a, b, c, x[i + 13], 12, -40341101);
                c = md5_ff(c, d, a, b, x[i + 14], 17, -1502002290);
                b = md5_ff(b, c, d, a, x[i + 15], 22, 1236535329);

                a = md5_gg(a, b, c, d, x[i + 1], 5, -165796510);
                d = md5_gg(d, a, b, c, x[i + 6], 9, -1069501632);
                c = md5_gg(c, d, a, b, x[i + 11], 14, 643717713);
                b = md5_gg(b, c, d, a, x[i + 0], 20, -373897302);
                a = md5_gg(a, b, c, d, x[i + 5], 5, -701558691);
                d = md5_gg(d, a, b, c, x[i + 10], 9, 38016083);
                c = md5_gg(c, d, a, b, x[i + 15], 14, -660478335);
                b = md5_gg(b, c, d, a, x[i + 4], 20, -405537848);
                a = md5_gg(a, b, c, d, x[i + 9], 5, 568446438);
                d = md5_gg(d, a, b, c, x[i + 14], 9, -1019803690);
                c = md5_gg(c, d, a, b, x[i + 3], 14, -187363961);
                b = md5_gg(b, c, d, a, x[i + 8], 20, 1163531501);
                a = md5_gg(a, b, c, d, x[i + 13], 5, -1444681467);
                d = md5_gg(d, a, b, c, x[i + 2], 9, -51403784);
                c = md5_gg(c, d, a, b, x[i + 7], 14, 1735328473);
                b = md5_gg(b, c, d, a, x[i + 12], 20, -1926607734);

                a = md5_hh(a, b, c, d, x[i + 5], 4, -378558);
                d = md5_hh(d, a, b, c, x[i + 8], 11, -2022574463);
                c = md5_hh(c, d, a, b, x[i + 11], 16, 1839030562);
                b = md5_hh(b, c, d, a, x[i + 14], 23, -35309556);
                a = md5_hh(a, b, c, d, x[i + 1], 4, -1530992060);
                d = md5_hh(d, a, b, c, x[i + 4], 11, 1272893353);
                c = md5_hh(c, d, a, b, x[i + 7], 16, -155497632);
                b = md5_hh(b, c, d, a, x[i + 10], 23, -1094730640);
                a = md5_hh(a, b, c, d, x[i + 13], 4, 681279174);
                d = md5_hh(d, a, b, c, x[i + 0], 11, -358537222);
                c = md5_hh(c, d, a, b, x[i + 3], 16, -722521979);
                b = md5_hh(b, c, d, a, x[i + 6], 23, 76029189);
                a = md5_hh(a, b, c, d, x[i + 9], 4, -640364487);
                d = md5_hh(d, a, b, c, x[i + 12], 11, -421815835);
                c = md5_hh(c, d, a, b, x[i + 15], 16, 530742520);
                b = md5_hh(b, c, d, a, x[i + 2], 23, -995338651);

                a = md5_ii(a, b, c, d, x[i + 0], 6, -198630844);
                d = md5_ii(d, a, b, c, x[i + 7], 10, 1126891415);
                c = md5_ii(c, d, a, b, x[i + 14], 15, -1416354905);
                b = md5_ii(b, c, d, a, x[i + 5], 21, -57434055);
                a = md5_ii(a, b, c, d, x[i + 12], 6, 1700485571);
                d = md5_ii(d, a, b, c, x[i + 3], 10, -1894986606);
                c = md5_ii(c, d, a, b, x[i + 10], 15, -1051523);
                b = md5_ii(b, c, d, a, x[i + 1], 21, -2054922799);
                a = md5_ii(a, b, c, d, x[i + 8], 6, 1873313359);
                d = md5_ii(d, a, b, c, x[i + 15], 10, -30611744);
                c = md5_ii(c, d, a, b, x[i + 6], 15, -1560198380);
                b = md5_ii(b, c, d, a, x[i + 13], 21, 1309151649);
                a = md5_ii(a, b, c, d, x[i + 4], 6, -145523070);
                d = md5_ii(d, a, b, c, x[i + 11], 10, -1120210379);
                c = md5_ii(c, d, a, b, x[i + 2], 15, 718787259);
                b = md5_ii(b, c, d, a, x[i + 9], 21, -343485551);

                a = safe_add(a, olda);
                b = safe_add(b, oldb);
                c = safe_add(c, oldc);
                d = safe_add(d, oldd);
            }
            return Array(a, b, c, d);
        }

        function md5_cmn(q, a, b, x, s, t) {
            return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s), b);
        }

        function md5_ff(a, b, c, d, x, s, t) {
            return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
        }

        function md5_gg(a, b, c, d, x, s, t) {
            return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
        }

        function md5_hh(a, b, c, d, x, s, t) {
            return md5_cmn(b ^ c ^ d, a, b, x, s, t);
        }

        function md5_ii(a, b, c, d, x, s, t) {
            return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
        }

        function safe_add(x, y) {
            var lsw = (x & 0xFFFF) + (y & 0xFFFF);
            var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
            return (msw << 16) | (lsw & 0xFFFF);
        }

        function bit_rol(num, cnt) {
            return (num << cnt) | (num >>> (32 - cnt));
        }

        function str2binl(str) {
            var bin = Array();
            var mask = (1 << chrsz) - 1;
            for (var i = 0; i < str.length * chrsz; i += chrsz) {
                bin[i >> 5] |= (str.charCodeAt(i / chrsz) & mask) << (i % 32);
            }
            return bin;
        }

        function binl2hex(binarray) {
            var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
            var str = "";
            for (var i = 0; i < binarray.length * 4; i++) {
                str += hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 0xF) + hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 0xF);
            }
            return str;
        }


        return {

            // md5加密主方法
            encrypt: function (s) {

                if (arguments.length > 1) {
                    s = [].slice.call(arguments, 0).join('');
                }

                return binl2hex(core_md5(str2binl(s), s.length * chrsz));
            }

        };
    });


    /**
     * 缓存数据到 top 页面的工具类。
     */
    define('Cache', function (require, exports, module) {


        if (window !== top) {
            return top.SMS.require('Cache');
        }


        var key$value = {};


        function get(key, defaultValue) {
            if (key in key$value) {
                return key$value[key];
            }
            else {
                key$value[key] = defaultValue;
                return defaultValue;
            }
        }

        function set(key, value) {
            key$value[key] = value;
        }

        function remove(key) {
            delete key$value[key];
        }

        function has(key) {
            return key in key$value;
        }


        return {
            get: get,
            set: set,
            remove: remove,
            has: has
        };

    });


    /**
     * 配置工具类。
     */
    define('Config', function (require, exports, module) {


        var $ = require('$');
        var Url = require('Url');


        // 递归扫描并转换 url 成真实的地址
        function convert(config) {

            return $.Object.map(config, function (key, value) {

                if (typeof value == 'string') {
                    return Url.format(value);
                }

                if (value instanceof Array) {
                    return $.Array.keep(value, function (item, index) {

                        if (typeof item == 'string') {
                            return Url.format(item);
                        }

                        if (typeof item == 'object') {
                            return convert(item); // 递归
                        }

                        return item;

                    }, true);
                }

                return value;

            }, true); // 深层次来扫描

        }

        function set(name$config) {

            var obj = name$config['Url'];
            if (obj) { // 先单独设置好站头的根地址，后面的模块要用到
                Url.config(obj);
            }

            $.Object.each(name$config, function (name, config) {

                if (name == 'Url') {
                    return;
                }

                var module = require(name);
                if (!module || !module.config) { // 不存在该模块或该模块不存在 config 方法
                    return;
                }

                config = convert(config);
                module.config(config);

            });
        }


        return {
            set: set,
        };

    });


    /**
     * 当 Debug 工具类
     */
    define('Debug', function (require, exports, module) {

        var $ = require('$');


        var isDebuged = true;

        var type$ext = {
            debug: 'debug',
            min: 'min'
        };


        function set(debuged) {
            isDebuged = !!debuged;
        }

        function get() {
            var key = isDebuged ? 'debug' : 'min';
            return type$ext[key];
        }

        function check() {
            return isDebuged;
        }


        function config(obj) {
            $.Object.extend(type$ext, obj);
        }


        return {
            set: set,
            get: get,
            check: check,
            config: config
        };
    });


    /**
     * 文件工具类
     */
    define('File', function (require, exports, module) {


        /**
         * 检测指定的文件是否为特定的扩展名类型的文件。
         *
         * @param {string}
         *            file 要检测的文件名。
         * @param {string}
         *            ext 要检测的扩展名，以 "." 开始。
         * @return {boolean} 如果该文件名以指定的扩展名结尾，则返回 true；否则返回 false。
         * @example File.is('a/b/c/login.JSON', '.json'); //返回 true
         */
        function is(file, ext) {

            if (typeof file != 'string' || typeof ext != 'string') {
                return false;
            }

            return file.slice(0 - ext.length).toLowerCase() == ext.toLowerCase();
        }

        function isJs(file) {
            return is(file, '.js');
        }

        function isCss(file) {
            return is(file, '.css');
        }

        function isJson(file) {
            return is(file, '.json');
        }


        return {
            is: is,
            isJs: isJs,
            isCss: isCss,
            isJson: isJson
        };
    });


    /**
     * 对外提供模块管理器。 主要提供给页面定义页面级别的私有模块。
     */
    define('Module', function (require, module, exports) {


        var $ = require('$');


        var id$module = {};
        var id$factory = {}; // 辅助用的，针对页面级别的多级目录的以 '/' 开头的模块 id


        // 根据工厂函数反向查找对应的模块 id。
        function getId(factory) {
            return $.Object.findKey(id$factory, function (key, value) {
                return value === factory;
            });
        }


        module.exports = {

            /**
             * 定义指定名称的模块。
             *
             * @param {string}
             *            id 模块的名称。
             * @param {Object|function}
             *            factory 模块的导出函数或对象。
             */
            define: function define(id, factory) {

                id$module[id] = {
                    required: false,
                    exports: factory,   // 这个值在 require 后可能会给改写
                    exposed: false      // 默认对外不可见
                };

                id$factory[id] = factory;
            },


            /**
             * 加载指定的模块。
             *
             * @param {string}
             *            id 模块的名称。
             * @return 返回指定的模块。
             */
            require: function (id) {

                if (id.indexOf('/') == 0) { // 以 '/' 开头，如 '/API'
                    var parentId = getId(arguments.callee.caller); // 如 'List'
                    if (!parentId) {
                        throw new Error('require 时如果指定了以 "/" 开头的短名称，则必须用在 define 的函数体内');
                    }

                    id = parentId + id; // 完整名称，如 'List/API'
                }


                var module = id$module[id];
                if (!module) { // 不存在该模块
                    return;
                }

                var require = arguments.callee; // 引用自身
                var exports = module.exports;

                if (module.required) { // 已经 require 过了
                    return exports;
                }

                // 首次 require
                if (typeof exports == 'function') {

                    var fn = exports;
                    exports = {};

                    var mod = {
                        id: id,
                        exports: exports,
                    };

                    var value = fn(require, mod, exports);

                    // 没有通过 return 来返回值，则要导出的值在 mod.exports 里
                    exports = value === undefined ? mod.exports : value;
                    module.exports = exports;
                }

                module.required = true; // 指示已经 require 过一次

                return exports;

            },
        };

    });


    /**
     * 多任务队列模块
     *
     * @namespace
     * @author micty
     */
    define('Multitask', function (require, exports, module) {

        var $ = require('$');


        function init(list) {

            return $.Array.keep(list, function (item, index) {
                return false;
            });
        }


        function checkReady(list, fn) {

            var isReady = list && list.length > 0;

            if (!isReady) {
                return;
            }

            $.Array.each(list, function (item, index) {

                if (!item) {
                    isReady = false;
                    return false; // 相当于 break
                }
            });

            if (isReady) {
                fn && fn(list);
            }

        }


        /**
         * 启动并发任务。
         */
        function concurrency(list, done) {

            var dones = init(list);

            $.Array.each(list, function (item, index) {

                if (typeof item == 'function') {
                    // 简单情形， item 是 fn，直接调用
                    item(function (data, json) {
                        dones[index] = data;
                        checkReady(dones, done);
                    });
                }
                else {
                    // 复杂情形，用于需要传递一些参数给 fn 的情形
                    var fn = item.fn;
                    var args = item.args || [];
                    var context = item.context || null;

                    // fn 中的最后一个参数必须是成功的回调函数
                    args.push(function (data, json) {
                        dones[index] = data;
                        checkReady(dones, done);
                    });

                    fn.apply(context, args);
                }


            });

        }

        /**
         * 启动串行执行任务队列。
         *
         * @param {Array}
         *            list 任务队列, 即函数队列。 函数队列中的每个函数，第一个参数必须是回调函数。
         */
        function serial(list, fn) {

            var dones = init(list);

            var index = 0;
            var len = list.length;

            (function () {

                var next = arguments.callee;
                var item = list[index];

                item(function (data) {
                    dones[index] = data;

                    index++;

                    if (index < len) {
                        next();
                    }
                    else { // 最后一个
                        fn && fn(dones);
                    }
                });
            })();

        }


        return {
            concurrency: concurrency,
            serial: serial,
        };

    });


    /**
     * 动态加载模块类。 对 seajs 的进一步封装，以适合本项目的使用。
     */

    define('Seajs', function (require, exports, module) {

        var $ = require('$');
        var Url = require('Url');
        var Debug = require('Debug');


        var defaults = {};
        var seajs = window['seajs'];

        function ready(fn) {

            if (seajs) {
                fn && fn(seajs);
                return;
            }


            // 先加载 seajs 库
            var url = Url.format('{~}lib/seajs/seajs.mod.{@}.js');

            $.Script.load({
                url: url,
                id: 'seajsnode', // 提供 id，提高性能。 详见
                // https://github.com/seajs/seajs/issues/260

                onload: function () {
                    seajs = window['seajs'];
                    seajs.config(defaults);

                    fn && fn(seajs);
                }
            });

        }

        function use() {

            var args = [].slice.call(arguments, 0);

            ready(function (seajs) {
                seajs.use.apply(seajs, args);
            });

        }

        function config(obj) {

            $.Object.extend(defaults, obj);

            if (seajs) {
                seajs.config(defaults);
            }
        }


        return {
            ready: ready,
            use: use,
            config: config
        };

    });


    /**
     * 树形结构的数据处理类。
     */
    define('Tree', function (require, exports, module) {

        var $ = require('$');


        var $Object = $.Object;   // require('Object');
        var $Array = $.Array;   // require('Array');
        var $String = $.String; // require('String');
        var Mapper = MiniQuery.Mapper; // require('Mapper');

        var mapper = new Mapper();
        var guidKey = Mapper.getGuidKey();


        /**
         * 用深度优先的遍历方式把树形结构的数据线性化成一个一维的数组。
         *
         * @param {Object|Array}
         *            tree 树形结构的数据对象或其数组。
         * @param {string}
         *            key 子节点数组对应的 key。
         * @param {function}
         *            [fn] 递归迭代处理每个节点时的回调函数。
         *            该回调函数会收到两个参数：当前节点和当前节点的子节点数组（如果没有，则为 null）。
         * @return {Array} 返回一个线性化后的一维数组。
         */
        function linearize(tree, key, fn) {

            // 重载 linearize([], key) 的情况
            if (tree instanceof Array) {
                var list = $Array.keep(tree, function (item, index) {
                    return linearize(item, key, fn); // 递归
                });
                return $Array.reduceDimension(list); // 降维
            }

            // 对单项进行线性化，核心算法
            // linearize({}, key) 的情况

            var items = tree[key];

            if (!items || !items.length) { // 叶子节点
                fn && fn(tree);
                return tree;
            }

            var list = $Array.keep(items, function (item, index) {
                return linearize(item, key, fn);
            });

            list = $Array.reduceDimension(list); // 降维
            fn && fn(tree, items);

            return [tree].concat(list);

        }


        /**
         * 用深度优先的遍历方式对一棵树的每个节点进行迭代。
         *
         * @param {Object|Array}
         *            tree 树形结构的数据对象或其数组。
         * @param {string}
         *            key 子节点数组对应的 key。
         * @param {function}
         *            [fn] 递归迭代处理每个节点时的回调函数。
         *            该回调函数会收到两个参数：当前节点和当前节点的子节点数组（如果没有，则为 null）。
         */
        function each(tree, key, fn) {

            // 重载 each([], key) 的情况
            if (tree instanceof Array) {
                $Array.each(tree, function (item, index) {
                    each(item, key, fn); // 递归
                });
            }

            // 对单项进行线性化，核心算法
            // each({}, key) 的情况

            var items = tree[key];

            if (!items || !items.length) { // 叶子节点
                fn && fn(tree);
                return;
            }

            $Array.each(items, function (item, index) {
                each(item, key, fn);
            });

            fn && fn(tree, items);

        }

        /**
         * 判断两个包含节点的数组或两个节点是否一样。
         *
         * @parma {Array|Object} a 第一个节点的数组或单个节点。
         * @parma {Array|Object} b 第二个节点的数组或单个节点。
         * @return {boolean} 返回一个布尔值，该值指示两个数组或节点是否一样。
         */
        function same(a, b) {

            if (a instanceof Array &&
                b instanceof Array) {

                var len = b.length;

                if (a.length != len) {
                    return false;
                }

                for (var i = 0; i < len; i++) {

                    var itemA = a[i];
                    if (!(guidKey in itemA)) {
                        return false;
                    }

                    var itemB = b[i];
                    if (!(guidKey in itemB)) {
                        return false;
                    }

                    if (itemA[guidKey] != itemB[guidKey]) {
                        return false;
                    }
                }

                return true;

            }

            if (!(guidKey in a) || !(guidKey in b)) {
                return false;
            }

            return a[guidKey] == b[guidKey];
        }


        /**
         * 构造器。
         *
         * @param {Object}
         *            data 要构建的树形结构的数据对象。
         * @param {Object}
         *            config 配置信息对象。
         * @param {string}
         *            config.childKey 下级节点的字段名称。
         * @param {string}
         *            [config.valueKey] 值的字段名称。 如果需要根据值来检索，请提供该值。
         */
        function Tree(data, config) {

            this[guidKey] = $String.random();

            var id$node = {}; // { id: node }，节点的自身数据
            var id$info = {}; // { id: info }，节点的描述信息

            var meta = {
                'data': data,
                'childKey': config.childKey,
                'valueKey': config.valueKey,
                'id$node': id$node,
                'id$info': id$info,
            };

            mapper.set(this, meta);


            this.each(function (node, items) {

                var id = $String.random(); // 分配一个随机 id
                id$node[id] = node;
                node[guidKey] = id;

                var info = id$info[id] = {
                    'isLeaf': !items || !items.length || items.length < 0,
                    'parentId': null,
                    'ids': [], // 子节点的 id 列表
                };

                if (items) {
                    info.ids = $Array.keep(items, function (item, index) {

                        var itemId = item[guidKey];
                        var info = id$info[itemId];
                        info.parentId = id;

                        return itemId;
                    });
                }


            });

        }


        // 实例方法
        Tree.prototype = {
            constructor: Tree,

            /**
             * 用深度优先的遍历方式把树形结构的数据线性化成一个一维的数组。
             *
             * @param {function}
             *            [fn] 递归迭代处理每个节点时的回调函数。
             *            该回调函数会收到两个参数：当前节点和当前节点的子节点数组（如果没有，则为 null）。
             * @return {Array} 返回一个线性化后的一维数组。
             */
            linearize: function (fn) {

                var meta = mapper.get(this);

                var data = meta.data;
                var childKey = meta.childKey;

                var list = linearize(data, childKey, fn);

                return list;

            },

            /**
             * 用深度优先的遍历方式对一棵树的每个节点进行迭代。
             *
             * @param {function}
             *            [fn] 递归迭代处理每个节点时的回调函数。
             *            该回调函数会收到两个参数：当前节点和当前节点的子节点数组（如果没有，则为 null）。
             */
            each: function (fn) {

                var meta = mapper.get(this);

                var data = meta.data;
                var childKey = meta.childKey;

                each(data, childKey, fn);

            },

            /**
             * 判断当前树中是否包含指定的节点。
             *
             * @param {Object}
             *            node 要进行判断的节点对象。
             * @return {boolean} 返回一个布尔值，该值指示当前树中是否包含该节点。
             */
            has: function (node) {

                if (!(guidKey in node)) {
                    return false;
                }

                var meta = mapper.get(this);
                var id$info = meta.id$info;

                var id = node[guidKey];
                var info = id$info[id];
                return !!info;

            },

            /**
             * 判断指定的节点在当前树中是否为叶子节点。
             *
             * @param {Object}
             *            node 要进行判断的节点对象。
             * @return {boolean} 返回一个布尔值，该值指示该节点在当前树中是否为叶子节点。
             */
            isLeaf: function (node) {

                if (!this.has(node)) {
                    throw new Error('该节点不属于本树实例对象。');
                }

                var meta = mapper.get(this);
                var id$info = meta.id$info;
                var id = node[guidKey];

                return id$info[id].isLeaf;

            },

            /**
             * 获取指定的节点在当前树中所有的父节点（包括自身）。
             *
             * @param {Object}
             *            node 要进行获取的节点对象。
             * @return {Array} 返回一个数组，表示该节点在当前树中所有的父节点（包括自身）。
             */
            getParents: function (node) {

                if (!this.has(node)) {
                    return null;
                }

                var meta = mapper.get(this);

                var id$node = meta.id$node;
                var id$info = meta.id$info;

                var list = [];

                do {

                    list.push(node);

                    var id = node[guidKey];
                    var info = id$info[id];
                    var parentId = info.parentId;
                    node = id$node[parentId];

                } while (node);

                list.reverse();

                return list;
            },

            /**
             * 获取指定的节点的所有子节点。
             *
             * @param {Object}
             *            node 要进行获取的节点对象。
             * @return {Array} 返回一个子节点数组。
             */
            getChildren: function (node) {

                if (!this.has(node)) {
                    return null;
                }

                var meta = mapper.get(this);
                var id$node = meta.id$node;

                var id$info = meta.id$info;
                var id = node[guidKey];
                var info = id$info[id];

                return $Array.keep(info.ids, function (id, index) {
                    return id$node[id];
                });

            },

            /**
             * 根据给定的一组值去检索出对应的节点列表。 返回列表的中包括根节点。
             */
            getItemsByValues: function (values) {

                var meta = mapper.get(this);

                var data = meta.data;

                var list = [data];

                if (!values || !values.length) {
                    return list;
                }

                var id$node = meta.id$node;
                var id$info = meta.id$info;

                var valueKey = meta.valueKey;

                var node = data;

                var items = $Array.map(values, function (value, index) {

                    var id = node[guidKey];
                    var info = id$info[id];
                    var ids = info.ids; // 子节点的 id 列表

                    if (!ids || !ids.length) {
                        return; // break
                    }

                    id = $Array.findItem(ids, function (id, index) {
                        var node = id$node[id];
                        return node[valueKey] === value;

                    });

                    if (!id) {
                        return; // break
                    }

                    node = id$node[id];
                    return node;


                });

                return list.concat(items);

            },

            /**
             * 根据给定的一组索引去检索出对应的节点列表。 返回列表的中包括根节点。
             */
            getItemsByIndexes: function (indexes) {

                var meta = mapper.get(this);

                var data = meta.data;

                var list = [data];

                if (!indexes || !indexes.length) {
                    return list;
                }

                var id$node = meta.id$node;
                var id$info = meta.id$info;

                var node = data;

                var items = $Array.map(indexes, function (index, i) {

                    var id = node[guidKey];
                    var info = id$info[id];
                    var ids = info.ids; // 子节点的 id 列表

                    if (!ids || !ids.length) {
                        return; // break
                    }

                    id = ids[index];

                    if (!id) {
                        return; // break
                    }

                    node = id$node[id];
                    return node;


                });

                return list.concat(items);

            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {
                mapper.remove(this);
            },


        };


        // 静态方法
        return $Object.extend(Tree, {
            linearize: linearize,
            each: each,
            same: same,
        });

    });


    /**
     * 当前页面的 Url 工具类
     */
    define('Url', function (require, exports, module) {

        var $ = require('$');


        var host$url = {};
        var host = location.host;


        /**
         * 获取当前 Web 站点的根地址。
         */
        function root() {
            return host$url[host] || host$url['default'];
        }

        /**
         * 获取上传文件存储在站点的根地址。
         */
        function uploadFileUrlRoot() {
            return host$url[host] || host$url['uploadFileUrlRoot'];
        }

        function config(data) {

            if (data) { // set
                $.Object.extend(host$url, data);
            }
            else { // get
                return $.Object.extend({}, host$url);
            }
        }

        /**
         * 检查给定的 url 是否以 'http://' 或 'https://' 开头。
         */
        function check(url) {
            if (typeof url != 'string') {
                return false;
            }

            return url.indexOf('http://') == 0 || url.indexOf('https://') == 0;
        }


        function format(url, data) {


            var Debug = require('Debug');


            if (typeof data != 'object') {
                var args = [].slice.call(arguments, 1);
                data = $.Array.toObject(args);
                delete data['length'];
            }

            data = $.Object.extend({
                '~': root(),
                '@': Debug.get()

            }, data);

            return $.String.format(url, data);
        }


        return $.Object.extend({}, $.Url.Current, {

            root: root,
            uploadFileUrlRoot: uploadFileUrlRoot,
            config: config,
            check: check,
            format: format,
        });

    });


    /**
     * 按钮组。
     */
    define('ButtonList', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        var Samples = require('Samples');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        // 默认配置
        var defaults = {};

        // 模板
        var samples = Samples.get('Bootstrap.ButtonList', [
            {
                name: 'ul',
                begin: '#--ul.begin--#',
                end: '#--ul.end--#',
                trim: true
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}',
                trim: true
            },
            {
                name: 'group',
                begin: '#--group.begin--#',
                end: '#--group.end--#',
                outer: '{groups}',
                trim: true
            },
            {
                name: 'group.item',
                begin: '#--group.item.begin--#',
                end: '#--group.item.end--#',
                outer: '{group.items}',
                trim: true
            },
            {
                name: 'group.item.separator',
                begin: '#--group.item.separator.begin--#',
                end: '#--group.item.separator.end--#',
                outer: '{item.separator}',
                trim: true
            }

        ]);

        //console.dir(samples);

        /**
         * 级联弹出菜单构造器。
         */
        function ButtonList(config) {

            var id = $.String.random();
            this[guidKey] = 'ButtonList-' + id;

            var container = config.container;
            var emitter = MiniQuery.Event.create();
            var fields = $.Object.extend({}, defaults.fields, config.fields);

            var childKey = fields.child;

            var list = config[childKey];
            var olId = 'ol-' + id + '-';
            var spanId = 'span-' + id + '-';

            var meta = {

                'container': container,
                'emitter': emitter,
                'list': list,
                'olId': olId,
                'spanId': spanId,
                'hasBind': false,
                'textKey': fields.text,
                'childKey': childKey,
                'iconKey': fields.icon,
                'callbackKey': fields.callback,
                'routeKey': fields.route,
                'autoClose': 'autoClose' in config ? config.autoClose : defaults.autoClose,
                'groupNo': -1, // 当前展开的菜单组索引
                'hasManualOpened': false, // 用来指示某分组菜单列表是否已补手动打开

                'bindEvents': bindEvents,
                'hideMenus': hideMenus,
                'showMenus': showMenus,

            };

            mapper.set(this, meta);


            var self = this;

            function bindEvents() {

                var $container = $(container);
                var childKey = meta.childKey;
                var callbackKey = meta.callbackKey;
                var routeKey = meta.routeKey;
                var autoClose = meta.autoClose;

                // 点击下拉三角形按钮，弹出菜单
                /*                $container.delegate('li[data-index]>span', 'click', function (event) {

                                    var span = this;
                                    var li = span.parentNode;

                                    var no = +li.getAttribute('data-index');
                                    self.toggle(no);
                                    meta.hasManualOpened = false;

                                    event.stopPropagation();

                                });*/

                // 点击按钮本身
                /*                $container.delegate('ul>li[data-index]', 'click', function (event) {

                                    var li = this;

                                    var index = +li.getAttribute('data-index');
                                    var item = list[index];

                                    var args = [item, index];

                                    var fn = item[callbackKey];
                                    if (fn) {
                                        fn.apply(null, args);
                                    }

                                    if (routeKey && (routeKey in item)) {
                                        emitter.fire('click:' + item[routeKey], args);
                                    }

                                    emitter.fire('click', args);

                                    if (!meta.hasManualOpened) { // 不是手动打开的，则关闭
                                        hideMenus('fade');
                                    }

                                    event.stopPropagation();


                                });*/

                // 点击按钮本身
                $container.delegate('button[data-index]', 'click', function (event) {

                    var btn = this;

                    var index = +btn.getAttribute('data-index');
                    var item = list[index];

                    var args = [item, index];

                    var fn = item[callbackKey];
                    if (fn) {
                        fn.apply(null, args);
                    }

                    if (routeKey && (routeKey in item)) {
                        emitter.fire('click:' + item[routeKey], args);
                    }

                    emitter.fire('click', args);

                    /*                    if (!meta.hasManualOpened) { // 不是手动打开的，则关闭
                                            hideMenus('fade');
                                        }*/

                    event.stopPropagation();


                });

                // 点击弹出的菜单项
                /*                $container.delegate('ol>li[data-index]', 'click', function (event) {

                                    var li = this;

                                    var no = +li.getAttribute('data-no');
                                    var index = +li.getAttribute('data-index');

                                    var group = list[no];
                                    var items = group[childKey];
                                    var item = items[index];

                                    var args = [item, index, no];

                                    var fn = item[callbackKey];
                                    if (fn) {
                                        fn.apply(null, args);
                                    }

                                    if (routeKey && (routeKey in item)) {
                                        emitter.fire('click:' + item[routeKey], args);
                                    }

                                    emitter.fire('click', args);

                                    event.stopPropagation();

                                    if (autoClose) { // 设置了点击后隐藏
                                        hideMenus(no, 'fade');
                                    }

                                });*/

                // 点击弹出的菜单项
                $container.delegate('ul>li[data-index]', 'click', function (event) {

                    var li = this;

                    var no = +li.getAttribute('data-no');
                    var index = +li.getAttribute('data-index');

                    var group = list[no];
                    var items = group[childKey];
                    var item = items[index];

                    var args = [item, index, no];

                    var fn = item[callbackKey];
                    if (fn) {
                        fn.apply(null, args);
                    }

                    if (routeKey && (routeKey in item)) {
                        emitter.fire('click:' + item[routeKey], args);
                    }

                    emitter.fire('click', args);

                    event.stopPropagation();

                    /*                    if (autoClose) { // 设置了点击后隐藏
                                            hideMenus(no, 'fade');
                                        }*/

                });

                // 点击其他地方，隐藏
                /*                $(document).on('click', function (event) {
                                    hideMenus('slide');
                                });

                                $(top.document).on('click', function (event) {
                                    hideMenus('fade');
                                });*/

            }

            function hideMenus(no, type) {
                if (typeof no == 'string') { // 重载 hideMenus(type)
                    type = no;
                    no = meta.groupNo;
                }
                toggleMenus(no, type, false);
                meta.hasManualOpened = false;
            }

            function showMenus(no, type) {
                toggleMenus(no, type, true);
            }

            function toggleMenus(no, type, sw) {

                if (no < 0 || no >= list.length) { // 越界
                    return;
                }

                var activedClass = defaults.activedClass;
                var olId = meta.olId;
                var spanId = meta.spanId;

                var $ol = $('#' + olId + no);
                var $span = $('#' + spanId + no);

                switch (type) {
                    case 'slide':
                        if (sw) {
                            $ol.addClass(activedClass);
                            $span.addClass(activedClass);
                            $ol.slideDown('fast');
                        }
                        else {
                            $ol.slideUp('fast', function () {
                                $ol.removeClass(activedClass);
                                $span.removeClass(activedClass);
                            });
                        }
                        break;

                    case 'fade':
                        $ol[sw ? 'fadeIn' : 'fadeOut']('fast', function () {
                            $ol.toggleClass(activedClass, sw);
                            $span.toggleClass(activedClass, sw);
                        });
                        break;

                    default:
                        $ol.toggle(sw);
                        $ol.toggleClass(activedClass, sw);
                        $span.toggleClass(activedClass, sw);
                        break;
                }

                meta.groupNo = sw ? no : -1;

            }

        }


        // 实例方法
        ButtonList.prototype = {

            constructor: ButtonList,

            /**
             * 渲染 UI，以在页面中呈现出组件。
             *
             * @param Object}
             *            node 渲染所使用的树形数据节点。
             */
            render: function () {
                var meta = mapper.get(this);

                var textKey = meta.textKey;
                var iconKey = meta.iconKey;
                var childKey = meta.childKey;
                var routeKey = meta.routeKey;
                var list = meta.list;

                var html = $.String.format(samples['ul'], {

                    'items': $.Array.keep(list, function (item, no) {

                        var items = item[childKey];

                        // 单个按钮
                        if (!items) {
                            return $.String.format(samples['item'], {
                                'index': no,
                                'text': item[textKey],
                                'name': item[routeKey],
                                'icon': item[iconKey] || 'icon-jibenziliao1'
                            });
                        }

                        // 按钮菜单
                        return $.String.format(samples['group'], {
                            'index': no,
                            'text': item[textKey],
                            'name': item[routeKey],
                            'icon': item[iconKey] || 'icon-jibenziliao1',
                            /*  'ol-id': meta.olId,
                              'span-id': meta.spanId,
                              'css-class': item.cssClass || '',*/

                            'group.items': $.Array.keep(items, function (item, index) {

                                return $.String.format(samples['group.item'], {
                                    'no': no,
                                    'index': index,
                                    'text': item[textKey],
                                    'name': item[routeKey],
                                    'icon': item[iconKey] || 'icon-jibenziliao1',
                                    'item.separator': index === 0 ? '' : $.String.format(samples['group.item.separator'], {})
                                });

                            }).join('')
                        });

                    }).join(''),

                    'groups': '' // 这个清空，弹出菜单已在 items 里填充了
                });

                $(meta.container).html(html);

                if (!meta.hasBind) {
                    meta.bindEvents();
                }

            },

            /**
             * 给本组件实例绑定事件。
             */
            on: function (name, key, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;

                // on({}) 或 on('', fn)
                if (typeof name == 'object' || typeof key == 'function') {
                    var args = [].slice.call(arguments, 0);
                    emitter.on.apply(emitter, args);
                    return;
                }

                // 暂时在这里实现二级事件。 以后会用 MiniQuery 库实现。
                if (typeof key == 'string') { // on('', '', fn)
                    emitter.on(name + ':' + key, fn);
                }
                else if (typeof key == 'object') { // on('', {})
                    $.Object.each(key, function (key, fn) {
                        emitter.on(name + ':' + key, fn);
                    });
                }


            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {
                mapper.remove(this);
            },

            trigger: function (name) {

                var meta = mapper.get(this);
                var list = meta.list;
                var routeKey = meta.routeKey;
                var callbackKey = meta.callbackKey;
                var $container = $(meta.container);
                var emitter = meta.emitter;

                var $targetButtons = $container.find('[data-name=' + name + ']');

                if ($targetButtons.length === 0 || $targetButtons.length > 1) {
                    console.log('uncertainty button selected...');
                    return;
                }

                var targetButton = $targetButtons[0];


                var index = +targetButton.getAttribute('data-index');
                var item = list[index];

                var args = [item, index];

                var fn = item[callbackKey];
                if (fn) {
                    fn.apply(null, args);
                }

                if (routeKey && (routeKey in item)) {
                    emitter.fire('click:' + item[routeKey], args);
                }

            },

            getItem: function (name) {

                var meta = mapper.get(this);
                var list = meta.list;
                var $container = $(meta.container);

                var $targetButtons = $container.find('[data-name=' + name + ']');

                if ($targetButtons.length === 0 || $targetButtons.length > 1) {
                    console.log('uncertainty button selected...');
                    return;
                }

                var targetButton = $targetButtons[0];

                var index = +targetButton.getAttribute('data-index');
                return list[index];

            },

            /**
             * 弹出/关闭指定分组的菜单列表。
             *
             * @param {number}
             *            no 菜单分组的索引值。
             */
            toggle: function (no) {

                var meta = mapper.get(this);
                var list = meta.list;

                var item = list[no];

                var groupNo = meta.groupNo;

                if (groupNo == no) {
                    meta.hideMenus(no, 'slide');
                    return;
                }

                if (groupNo >= 0) {
                    meta.hideMenus('slide');
                }

                meta.showMenus(no, 'slide');
                meta.hasManualOpened = true;
            },

        };


        // 静态方法
        return $.Object.extend(ButtonList, {

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }

        });

    });


    /**
     * 级联弹出菜单模块
     *
     */
    define('CascadeMenus', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        var Samples = require('Samples');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        // 默认配置
        var defaults = {};

        // 模板
        var samples = Samples.get('CascadeMenus', [
            {
                name: 'div',
                begin: '#--div.begin--#',
                end: '#--div.end--#',
                trim: true,
            },
            {
                name: 'group',
                begin: '#--group.begin--#',
                end: '#--group.end--#',
                outer: '{groups}',
                trim: true,
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}',
                trim: true,

            },
            {
                name: 'leaf',
                begin: '#--leaf.begin--#',
                end: '#--leaf.end--#',
                outer: '{leafs}',
                trim: true,

            },
        ]);


        /**
         * 根据给定的索引值集合获取所对应的 CSS 类的 topN 编号。
         *
         * @param {Array}
         *            indexes 索引值列表。
         * @return {Array} 返回对应的 topN 编号数组。
         */
        function getTopNos(indexes) {

            // 完整的路径 index 数组。 第 0 级(顶级)的 index 总是 0
            indexes = [0].concat(indexes);

            return $.Array.keep(indexes, function (index, level) {
                var parents = indexes.slice(0, level + 1);
                return $.Array.sum(parents);

            });
        }

        /**
         * 从一棵树中获取指定了索引值集合所对应的分组。
         *
         * @param {Object}
         *            tree 树形结构的数据对象。
         * @param {Array}
         *            indexes 索引值列表。 如 [0, 2, 1], 表示: 第 0 级的第 0 个节点下的子结点(即分组); 第
         *            1 级的第 2 个节点下的子结点(即分组); 第 2 级的第 1 个节点下的子结点(即分组);
         * @return {Array} 返回对应的节点分组。
         */
        function getGroups(tree, indexes, key) {

            // 完整的路径 index 数组。 第 0 级(顶级)的 index 总是 0
            var fullIndexes = [0].concat(indexes);

            var items = [tree];

            var groups = $.Array.keep(fullIndexes, function (index, level) {

                var node = items[index];
                items = node[key];


                return $.Array.keep(items, function (item, i) {
                    return $.Object.extend({}, item); // 浅拷贝
                });
            });

            $.Array.each(indexes, function (index, level) {

                var group = groups[level];
                var item = group[index];

                item.actived = true;
            });

            return groups;

        }


        /**
         * 级联弹出菜单构造器。
         */
        function CascadeMenus(config) {

            var id = $.String.random();
            this[guidKey] = 'CascadeMenus-' + id;


            var container = config.container;
            var fields = $.Object.extend({}, defaults.fields, config.fields);
            var emitter = MiniQuery.Event.create();
            var divId = 'div-' + id.toLowerCase();

            var meta = {
                'tree': config.tree,
                'container': container,
                'fields': fields,
                'activedClass': defaults.activedClass,
                'leafClass': defaults.leafClass,
                'delay': 'delay' in config ? config.delay : defaults.delay,
                'list': [],
                'indexes': [],
                'node': null,
                'hasShown': false,
                'hasRendered': false,
                'emitter': emitter,
                'divId': divId,
                'div': '#' + divId,
                'style': {
                    top: 0,
                    left: 0,
                },
            };

            mapper.set(this, meta);


            var self = this;

            meta.bindEvents = function () {

                var tree = meta.tree;
                var div = meta.div;
                var activedClass = meta.activedClass;

                var selector = div + ' li[data-index]';

                var $container = $(container);


                // 在菜单项上滑动鼠标时
                $container.delegate(selector, 'mouseover', function (event) {

                    // 隐藏有个动画延迟，为避免在隐藏的动画过程中又 mousever 而导致的重新显示。
                    // 重现方法: 移除下面的代码，点击菜单项后又在上面移动鼠标，可以看到菜单又给显示出来了。
                    if (!meta.hasShown) {
                        return;
                    }

                    var li = this;
                    var ol = li.parentNode;
                    var no = +ol.getAttribute('data-index');    // 组号

                    var index = +li.getAttribute('data-index'); // 项号
                    var group = meta.list[no];
                    var item = group[index];

                    var indexes = meta.indexes;

                    if (tree.isLeaf(item)) { // 叶子结点

                        // 把当前叶子结点所在的组的所有下级组隐藏
                        $(div).find('ol[data-index]:gt(' + no + ')').hide();

                        var activedIndex = indexes[no]; // 当前组的激活项的索引
                        $(ol).find('li[data-index="' + activedIndex + '"]').removeClass(activedClass);

                        return;
                    }

                    indexes = indexes.slice(0, no);
                    indexes[no] = index;

                    self.render(meta.node, indexes);


                });

                // 单击菜单项时
                $container.delegate(selector, 'click', function (event) {

                    self.hide();

                    var li = this;
                    var ol = li.parentNode;

                    var no = +ol.getAttribute('data-index');    // 组号
                    var index = +li.getAttribute('data-index'); // 项号

                    var group = meta.list[no];
                    var item = group[index];

                    event.stopPropagation();
                    emitter.fire('change', [item, no, index]);


                });
            };


        }


        // 实例方法
        CascadeMenus.prototype = {

            constructor: CascadeMenus,

            /**
             * 渲染 UI，以在页面中呈现出组件。
             *
             * @param Object}
             *            node 渲染所使用的树形数据节点。
             */
            render: function (node, indexes, style) {

                var meta = mapper.get(this);

                var currentIndexes = meta.indexes;

                // 避免对同样的数据进行重复渲染。
                if (currentIndexes.length > 0 &&
                    indexes.join('') == currentIndexes.join('')) {
                    this.show();
                    return;
                }


                var isFromInner = !style; // 不指定参数 style 时，认为是内部的调用，如
                // mouseover 导致的

                style = style || meta.style;

                var tree = meta.tree;
                var fields = meta.fields;
                var groups = getGroups(node, indexes, fields['child']);
                var topNos = getTopNos(indexes);

                $.Object.extend(meta, {
                    'list': groups,
                    'node': node,
                    'indexes': indexes,
                    'style': style,
                });

                this.hide();

                var textKey = fields['text'];

                // 分组对应的 html
                var html = $.Array.keep(groups, function (group, no) {

                    return $.String.format(samples.group, {
                        'index': no,
                        'topN': topNos[no],
                        'leafs': '',
                        'items': $.Array.keep(group, function (item, index) {

                            var sample = tree.isLeaf(item) ? samples['leaf'] : samples['item'];

                            return $.String.format(sample, {
                                'index': index,
                                'name': item[textKey],
                                'actived': item.actived ? meta.activedClass : '',
                            });

                        }).join(''),


                    });

                }).join('');

                if (meta.hasRendered) { // 非首次渲染，外层容器 div 已创建，直接填充即可
                    $(meta.div).html(html).css({
                        'left': style.left,
                        'top': style.top,
                    });
                }
                else { // 首次渲染
                    // 外层容器 div 的 html
                    html = $.String.format(samples['div'], {
                        'div-id': meta.divId,
                        'div-class': meta.containerClass,
                        'left': style.left,
                        'top': style.top,
                        'groups': html,
                    });

                    $(meta.container).html(html);
                    meta.bindEvents(); // 首次渲染时需要绑定事件
                    meta.hasRendered = true;
                }

                this.show(isFromInner ? '' : 'fade');
            },

            /**
             * 显示组件。
             */
            show: function (type) {

                var meta = mapper.get(this);
                meta.hasShown = true;

                var div = meta.div;

                switch (type) {

                    case 'fade':
                        $(div).fadeIn();
                        break;

                    case 'slide':
                        $(div).slideDown('fast');
                        break;

                    default:
                        $(div).show();
                        break;
                }


                var lis = $(div).find('ol').find('li:last').toArray();

                var tops = $.Array.keep(lis, function (li, index) {
                    var $li = $(li);
                    return $li.offset().top + $li.outerHeight();
                });

                var max = $.Array.max(tops);

                meta.emitter.fire('show', [max]);
            },

            /**
             * 隐藏组件。
             */
            hide: function (type) {

                var meta = mapper.get(this);

                meta.hasShown = false; // 相当于个信号，先锁定，再动画延迟隐藏

                var div = meta.div;
                var emitter = meta.emitter;

                switch (type) {

                    case 'fade':
                        $(div).fadeOut(function () {
                            emitter.fire('hide');
                        });
                        break;

                    case 'slide':
                        $(div).slideUp(function () {
                            emitter.fire('hide');
                        });
                        break;

                    default:
                        $(div).hide();
                        emitter.fire('hide');

                        break;
                }


            },


            /**
             * 给本组件实例绑定事件。
             */
            on: function () {

                var meta = mapper.get(this);

                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {

                mapper.remove(this);
            },

        };


        // 静态方法
        return $.Object.extend(CascadeMenus, {

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }

        });

    });


    /**
     * 级联导航模块
     *
     */
    define('CascadeNavigator', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        var Tree = require('Tree');
        var Url = require('Url');
        var CascadePath = require('CascadePath');
        var CascadeMenus = require('CascadeMenus');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        // 默认配置
        var defaults = {};


        /**
         * 级联弹出菜单构造器。
         */
        function CascadeNavigator(config) {


            var id = $.String.random();
            this[guidKey] = 'CascadeNavigator-' + id;

            var containers = config.containers;
            var fields = $.Object.extend({}, defaults.fields, config.fields);

            var emitter = MiniQuery.Event.create();

            var meta = {
                'inited': false, // 指示是否已经初始化
                'fields': fields,
                'selectedIndexes': config.selectedIndexes,
                'selectedValues': config.selectedValues,
                'emitter': emitter,
                'init': init,
                'data': config.data,

            };

            mapper.set(this, meta);

            // 初始化
            function init(data) {

                data = meta.data = data || meta.data;

                var tree = new Tree(data, {
                    'childKey': fields['child'],
                    'valueKey': fields['value'],
                });

                var path = new CascadePath({
                    'tree': tree,
                    'container': containers['path'],
                    'fields': fields,
                    'selectedIndexes': config.selectedIndexes,
                    'selectedValues': config.selectedValues,
                });

                var menus = new CascadeMenus({
                    'tree': tree,
                    'container': containers['menus'],
                    'fields': fields,
                });


                path.on({
                    'open': function (item, index, style) {
                        menus.render(item, [], style);
                    },
                    'close': function () {
                        menus.hide();
                    },

                    'change': function (list) {
                        menus.hide();
                        emitter.fire('change', [list]);
                    },
                });


                var bodyHeight; // 记录菜单弹出前的 body 高度

                menus.on({
                    'change': function (item, no, index) {
                        var list = tree.getParents(item);
                        path.render(list);
                    },

                    'hide': function () {
                        if (bodyHeight) {
                            $(document.body).height(bodyHeight);
                            bodyHeight = null;
                        }
                    },

                    'show': function (top) {

                        var height = $(document.body).height();
                        if (!bodyHeight) {
                            bodyHeight = height;
                        }

                        if (top > height) {
                            $(document.body).height(top);
                        }


                    },
                });

                $.Object.extend(meta, {
                    'inited': true, // 指示已经初始化
                    'tree': tree,
                    'path': path,
                    'menus': menus,
                });

                $(document).off('click', hide).on('click', hide); // 先移除之前绑定的，因为
                // init
                // 方法可能多次执行

            }

            function hide() {
                meta.path.reset();
                meta.menus.hide('fade');
            }


        }


        // 实例方法
        CascadeNavigator.prototype = {

            constructor: CascadeNavigator,

            /**
             * 渲染 UI，以在页面中呈现出组件。
             *
             * @param Object}
             *            node 渲染所使用的树形数据节点。
             */
            render: function (data) {

                var meta = mapper.get(this);

                if (!meta.inited || data) { // 未初始化 或 显示指定了 data，都要(重新)初始化
                    meta.init(data);
                }

                var tree = meta.tree;

                var values = meta.selectedValues;

                var list = values ?
                    tree.getItemsByValues(values) : // 优先按值来检索
                    tree.getItemsByIndexes(meta.selectedIndexes);

                meta.path.render(list);
            },


            /**
             * 给本组件实例绑定事件。
             */
            on: function () {

                var meta = mapper.get(this);

                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);


            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {
                var meta = mapper.get(this);

                meta.tree.destroy();
                meta.path.destroy();
                meta.menus.destroy();

                mapper.remove(this);
            },
        };


        // 静态方法
        return $.Object.extend(CascadeNavigator, {

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }

        });

    });


    /**
     * 级联路径模块
     *
     */
    define('CascadePath', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        var Samples = require('Samples');
        var Tree = require('Tree');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        // 默认配置
        var defaults = {};

        // 模板
        var samples = Samples.get('CascadePath', [
            {
                name: 'ol',
                begin: '#--ol.begin--#',
                end: '#--ol.end--#',
                trim: true,
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}',
                trim: true,

            },
            {
                name: 'leaf',
                begin: '#--leaf.begin--#',
                end: '#--leaf.end--#',
                outer: '{leaf}',
                trim: true,
            },
        ]);


        /**
         * 级联路径构造器。
         */
        function CascadePath(config) {

            var id = $.String.random();
            this[guidKey] = id;

            config = $.Object.extendDeeply({}, defaults, config); // 深度合并

            var fields = $.Object.extend({}, defaults.fields, config.fields);
            var olId = 'ol-' + id.toLowerCase();
            var selector = '#' + olId + '>li[data-index]';

            // 实例的私有数据
            var meta = {
                'tree': config.tree,
                'textKey': fields['text'],
                'container': config.container,
                'activedClass': defaults.activedClass,
                'index': -1, // 当前展开的项的索引值。
                'hasBind': false,
                'list': [],
                'self': this,
                'olId': olId,
                'emitter': MiniQuery.Event.create(),
                'span': selector + '>span',
                'a': selector + '>a',
            };

            mapper.set(this, meta);


            meta.bindEvents = function () {

                var tree = meta.tree;
                var container = meta.container;
                var self = meta.self;
                var emitter = meta.emitter;

                var $container = $(container);

                // 单击下拉三角形
                $container.delegate(meta.span, 'click', function (event) {

                    var span = this;
                    var li = span.parentNode;

                    var index = +li.getAttribute('data-index');
                    if (index == meta.index) { // 当前项是展开的状态
                        self.reset(); // 重置，回到关闭状态
                        emitter.fire('close');
                        return;
                    }

                    var item = meta.list[index];
                    if (tree.isLeaf(item)) { // 叶子结点
                        return;
                    }

                    self.reset(); // 先重置，回到初始状态
                    $(span).addClass(meta.activedClass);
                    meta.index = index;

                    var style = {
                        left: $(li).position().left,
                        top: $container.position().top + $container.height(),
                    };

                    event.stopPropagation();
                    emitter.fire('open', [item, index, style]);


                });

                // 单击回退节点
                $container.delegate(meta.a, 'click', function (event) {

                    var a = this;
                    var li = a.parentNode;

                    var index = +li.getAttribute('data-index');
                    var item = meta.list[index];

                    if (tree.isLeaf(item)) {
                        return;
                    }

                    var items = tree.getParents(item);
                    self.render(items);

                });

                meta.hasBind = true;
            };


        }


        // 实例方法
        CascadePath.prototype = {

            constructor: CascadePath,

            /**
             * 渲染 UI，以在页面中呈现出组件。
             *
             * @param {Array|Object}
             *            data 渲染所使用的数据。
             */
            render: function (data) {

                var meta = mapper.get(this);

                var list = (data instanceof Array) ? data : [data];
                if (Tree.same(list, meta.list)) { // 数据一样，不重复渲染
                    return;
                }

                meta.list = list;


                var textKey = meta.textKey;
                var tree = meta.tree;

                var html = $.String.format(samples['ol'], {

                    'ol-id': meta.olId,

                    'items': $.Array.keep(list, function (item, index) {

                        var sample = tree.isLeaf(item) ? samples['leaf'] : samples['item'];

                        return $.String.format(sample, {
                            'index': index,
                            'name': item[textKey],
                        });
                    }).join(''),

                    'leaf': '', // 这里清空即可，因为叶子结点统一在 'items' 里处理了

                });

                $(meta.container).html(html);
                this.reset(); // 回归到关闭状态

                if (!meta.hasBind) { // 只需要绑定一次
                    meta.bindEvents();
                }

                meta.emitter.fire('change', [list]);

            },

            reset: function () {
                var meta = mapper.get(this);
                var span = meta.span;
                var activedClass = meta.activedClass;
                $(span).removeClass(activedClass);

                meta.index = -1;
            },

            /**
             * 给本控件实例绑定事件。
             */
            on: function () {
                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {
                var meta = mapper.get(this);
                mapper.remove(this);
            },

        };


        // 静态方法
        return $.Object.extend(CascadePath, {

            create: function (data, config) {
                var cp = new CascadePath(config);
                cp.render(data);
                return cp;
            },

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }

        });

    });


    /**
     * 级联选择器。
     */
    define('CascadePicker', function (require, exports, module) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');
        var Url = require('Url');
        var Cache = require('Cache');


        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        var samples = $.String.getTemplates(top.document.body.innerHTML, [
            {
                name: 'list',
                begin: '<!--Samples.CascadePicker--!',
                end: '--Samples.CascadePicker-->',
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}'
            }
        ]);


        // 缺省配置
        var defaults = {
            container: '',
            selectedIndexes: [],
            defaultTexts: [],
            defaultText: '--请选择--',
            hideNone: true,
            data: [],
            varname: '',
            fields: {
                text: '',
                child: ''
            }
        };

        // 缓存数据
        var url$data = {};


        // 根据给定的一组值，从级联数据中获取其对应的一组索引。
        function getIndexes(list, values, valueField, childField) {

            return $.Array.keep(values, function (value) {

                var index = $.Array.findIndex(list, function (item) {
                    // 注意，用的是全等。
                    // 这暗示了 value 可以是任何类型的值
                    return item[valueField] === value;
                });

                var item = list[index] || {};
                list = item[childField] || [];

                return index;
            });

        }


        /**
         * 构造器。
         */
        function CascadePicker(config) {

            this[guidKey] = 'CascadePicker-' + $.String.random();


            var emitter = new MiniQuery.Event(this);

            var meta = $.Object.extend({}, defaults, config, {
                'emitter': emitter,
            });

            mapper.set(this, meta);


            var change = meta.change;
            if (change) {
                this.on('change', change);
            }


            var self = this;

            $(meta.container).delegate('select', 'change', function (event) {

                var select = this;
                var level = +select.getAttribute('data-index');
                var selectedIndex = select.selectedIndex - 1;

                var list = meta.selectedIndexes;

                meta.selectedIndexes = $.Array.keep(list, function (item, index) {

                    if (index < level) {
                        return item;
                    }

                    if (index == level) {
                        return selectedIndex;
                    }

                    return -1;
                });

                self.render();

                emitter.fire('change', [level, selectedIndex]);

            });

        }


        // 实例方法
        CascadePicker.prototype = {
            constructor: CascadePicker,

            /**
             * 渲染本实例组件到 UI 层。
             */
            render: function () {

                var self = this;

                this.load(function (list) {

                    var meta = mapper.get(self);
                    var fields = meta.fields;

                    var valueField = fields.value;
                    var textField = fields.text;
                    var childField = fields.child;

                    var defaultTexts = meta.defaultTexts || [];

                    var selectedValues = meta.selectedValues;
                    if (selectedValues) { // 如果指定了选中的值，则把值转成索引。
                        meta.selectedIndexes = getIndexes(list, selectedValues, valueField, childField);
                        meta.selectedValues = null; // 置空，避免再次 render 时
                                                    // selectedIndexes 无法应用。
                    }


                    var html = $.Array.map(meta.selectedIndexes, function (selectedIndex, level) {

                        // 指定了隐藏空数据的级别，并且当前级别的不存在数据，则不渲染。
                        if (meta.hideNone && list.length == 0) {
                            return null;
                        }

                        var defaultItem = {};
                        defaultItem[textField] = defaultTexts[level] || meta.defaultText;

                        list = [defaultItem].concat(list);
                        selectedIndex = selectedIndex + 1;

                        var html = $.String.format(samples['list'], {
                            'index': level,
                            'not-selected': selectedIndex <= 0 ? 'not-selected' : '',

                            'items': $.Array.keep(list, function (item, index) {

                                return $.String.format(samples['item'], {
                                    'text': item[textField],
                                    'selected': index == selectedIndex ? 'selected="selected"' : ''
                                });

                            }).join('')
                        });

                        var item = list[selectedIndex] || {};
                        list = item[childField] || [];

                        return html;


                    }).join('');

                    $(meta.container).addClass('address-picker').html(html);

                });

            },

            /**
             * 加载数据源，并在加载成功后执行一个回调函数。 该方法会使用缓存策略。
             *
             * @param {function}
             *            fn 加载成功后要执行的回调函数。 该函数会接受到一个数组作为其参数，表示加载到的数据源。
             */
            load: function (fn) {

                var meta = mapper.get(this);
                var data = meta.data;

                if (data instanceof Array) { // 现成的数据。 针对实例内的多次调用
                    fn && fn(data);
                    return;
                }


                // 此时 data 为 string，当成 url
                var url = Url.check(data) ? data : Url.root() + data;

                data = url$data[url]; // 从当前页面的缓存中读取。 针对页面内的多次创建实例的调用
                if (data) {
                    meta.data = data;
                    fn && fn(data);
                    return;
                }

                data = Cache.get(url); // 从 top 页面的缓存中读取。 针对跨页面的调用
                if (data) {
                    data = $.Array.parse(data); // 跨页面时在IE下的数组会变成伪数组
                    meta.data = data;
                    url$data[url] = data;
                    fn && fn(data);
                    return;
                }


                // 加上随机查询字符串，确保拿到最新版本。
                var uri = $.Url.setQueryString(url, $.String.random(), '');

                $.Script.load(uri, function () {

                    data = window[meta.varname] || [];

                    // 缓存起来
                    meta.data = data;
                    url$data[url] = data;
                    Cache.set(url, data);

                    fn && fn(data);
                });

            },


            /**
             * 获取所有选中的项所对应的数据。
             */
            getSelectedItems: function () {

                var meta = mapper.get(this);
                var childField = meta.fields.child;
                var list = meta.data;

                return $.Array.map(meta.selectedIndexes, function (selectedIndex, level) {

                    var item = list[selectedIndex];
                    if (item) {
                        list = item[childField] || [];
                    }

                    return item;

                });
            },

            /**
             * 锁定不可编辑
             */
            lock: function () {

                var meta = mapper.get(this);

                $(meta.container).children('select').attr("disabled", "disabled");

            },

            /**
             * 解锁定
             */
            unLock: function () {

                var meta = mapper.get(this);

                $(meta.container).children('select').attr("disabled", "");

            },

            /**
             * 给当前实例绑定一个指定名称的事件回调函数。
             */
            on: function (name, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },


        };


        // 静态方法
        return $.Object.extend(CascadePicker, {

            /**
             * 使用指定的配置对象去设置默认配置。 默认配置用于在创建级联选择器实例时提供缺省的配置字段。
             *
             * @param {Object}
             *            config 配置对象。 具体字段见构造函数中的参数说明。
             */
            config: function (config) {
                $.Object.extendDeeply(defaults, config);
            },


            /**
             * 使用指定的配置对象创建一个级联选择器，并且渲染出来。
             *
             * @param {Object}
             *            config 配置对象。 具体字段见构造函数中的参数说明。
             * @return {CascadePicker} 返回一个已创建好的级联选择器实例。
             */
            create: function (config) {
                var picker = new CascadePicker(config);
                picker.render();
                return picker;
            }

        });


    });


    /**
     * 动态加载弹出对话框类。
     *
     * @author micty
     */
    define('Dialog', function (require, exports, module) {

        if (window !== top) {
            return top.SMS.require('Dialog');
        }


        var $ = require('$');
        var Seajs = require('Seajs');


        var defaults = {};
        var randomPrefix = 'dialog-' + $.String.random() + '-'; // 运行时确定的随机串

        var type$name = {
            'dialog': 'dialog',
            'data': 'data',
        };


        function use(fn) {
            Seajs.use('dialog', fn);
        }

        function config(obj) {

            // get
            if (arguments.length == 0) {
                return defaults;
            }

            // set
            $.Object.extend(defaults, obj);
        }


        /**
         * 根据给定的 sn 获取一个运行时确定的用于存储数据的 key。 仅供 art-dialog 和 Iframe 模块中使用
         */
        function getKey(sn, type) {

            var name = type$name[type];
            if (!name) {
                return;
            }

            return randomPrefix + sn + '-' + name;
        }


        return {
            use: use,
            config: config,
            getKey: getKey,
        };

    });


    /**
     * 获取主控台打开的当前的 Iframe 页面类。
     *
     * @author micty
     */
    define('Iframe', function (require, exports, module) {

        if (window === top) { // 说明加载环境是 top 页面，即主控台页
            return require('IframeManager');
        }


        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        // 这里的模块名称是 Iframe 而非 IframeManager
        var IframeManager = top.SMS.require('Iframe');


        var iframe = null;  // 当前 iframe 页面对象的 iframe DOM 对象。
        var infos = null;   // 当前 iframe 页面的信息对象，运行时确定。
        var emitter = MiniQuery.Event.create();


        function get(key) {

            if (key) { // 重载。 如 get('id')、get('sn')
                var infos = getInfos();
                return infos ? infos[key] : undefined;
            }


            if (iframe) {
                return iframe;
            }

            var iframes = top.$('iframe').toArray();

            iframe = $.Array.findItem(iframes, function (iframe, index) {
                try {
                    return iframe.contentDocument === window.document;
                }
                catch (ex) {
                    return false;
                }

            });

            return iframe;
        }


        /**
         * 获取当前 iframe 页面的信息对象，这些信息在运行时就确定。
         */
        function getInfos() {

            var iframe = get();
            if (!iframe) {
                return null;
            }

            var src = iframe.src;

            if (infos) { // 读缓存

                // 这两个字段在运行后可能会发生变化，需重新获取。
                return $.Object.extend(infos, {
                    'hash': $.Url.getHash(src),
                    'actived': $(iframe).hasClass('actived'),
                });
            }


            var location = iframe.contentDocument.location;
            var url = location.origin + location.pathname;

            var originalSrc = iframe.getAttribute('src');

            infos = {
                'type': iframe.getAttribute('data-type'), // iframe 的类型
                'id': iframe.id,
                'index': iframe.getAttribute('data-index'),
                'src': src,
                'originalSrc': originalSrc, // 原始的 src，即在 DOM 查看器中看到的值
                'path': originalSrc.split('?')[0],
                'url': url,
                'sn': iframe.getAttribute('data-sn'),
                'query': $.Url.getQueryString(src),
                'hash': $.Url.getHash(src),
                'actived': $(iframe).hasClass('actived')

            };

            return infos;

        }

        /**
         * 更改iframe的信息
         * 如单据新增后需要变更单据状态(新增-->修改),此时应改变iframe id 来确保不会阻止新的单据新增操作
         * @param key
         * @param value
         * @returns {*}
         */
        function setInfos(key, value) {

            var iframe = get();
            if (!iframe) {
                return null;
            }

            var src = iframe.src;

            if (infos) { // 读缓存

                infos[key] = value;
                // 这两个字段在运行后可能会发生变化，需重新获取。
                return $.Object.extend(infos, {
                    'hash': $.Url.getHash(src),
                    'actived': $(iframe).hasClass('actived'),
                });
            }


            var location = iframe.contentDocument.location;
            var url = location.origin + location.pathname;

            var originalSrc = iframe.getAttribute('src');

            infos = {
                'type': iframe.getAttribute('data-type'), // iframe 的类型
                'id': iframe.id,
                'index': iframe.getAttribute('data-index'),
                'src': src,
                'originalSrc': originalSrc, // 原始的 src，即在 DOM 查看器中看到的值
                'path': originalSrc.split('?')[0],
                'url': url,
                'sn': iframe.getAttribute('data-sn'),
                'query': $.Url.getQueryString(src),
                'hash': $.Url.getHash(src),
                'actived': $(iframe).hasClass('actived'),

            };

            infos[key] = value;

            return infos;

        }

        function open(no, index, data) {
            IframeManager.open(no, index, data);
        }

        function raise(data) {
            IframeManager.raise(data);
        }


        function getData(key) {
            var sn = get('sn');
            var data = IframeManager.getData(sn);

            if (!data) { // 尚不存在数据
                return;
            }

            return arguments.length == 0 ? data : data[key];
        }

        function setData(key, data) {

            var sn = get('sn');

            if (arguments.length == 1) { // 重载 setData(data)
                data = key;
                IframeManager.setData(sn, data); // 全量覆盖
                return;
            }


            var all = IframeManager.getData(sn) || {};
            all[key] = data;

            IframeManager.setData(sn, all);
        }

        function removeData(key) {

            var sn = get('sn');

            if (arguments.length == 0) { // 重载 removeData()，全部移除
                IframeManager.removeData(sn);
                return;
            }

            var data = IframeManager.getData(sn);
            if (!data) {
                return;
            }

            delete data[key];
            IframeManager.setData(sn, data);
        }


        function getDialog() {

            var Dialog = require('Dialog');
            var sn = get('sn');

            var key = Dialog.getKey(sn, 'dialog');

            return IframeManager.getData(key);
        }


        return {
            get: get,
            getInfos: getInfos,
            setInfos: setInfos,
            getData: getData,
            setData: setData,
            removeData: removeData,
            getDialog: getDialog,
            open: open,
            raise: raise,
            on: emitter.on.bind(emitter),

            // 该接口仅供主控制台调用。
            fire: emitter.fire.bind(emitter),
        };


    });


    /**
     * 管理主控台打开的 iframe 页面类，并在页间传递数据。 该模块仅供主控台页面使用。
     *
     * @author micty
     */
    define('IframeManager', function (require, exports, module) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');

        var emitter = MiniQuery.Event.create();

        var sn$data = {};


        function getData(sn) {
            return sn$data[sn];
        }

        function setData(sn, data) {
            sn$data[sn] = data;
        }

        function removeData(sn) {
            delete sn$data[sn];
        }

        function open(no, index, data) {

            var sn = no + '-' + index;
            sn$data[sn] = data;

            emitter.fire('open', [no, index, data]);
        }

        function raise(data) {

            var eventName = data.eventName;
            var item = data.data;

            var sn = item.sn;

            var iframe = top.$('iframe[data-sn="' + sn + '"]').get(0);

            if (!iframe) {
                throw new Error('不存在 sn 为 ' + sn + ' 的 iframe 页面');
            }

            emitter.fire(eventName, [sn, data]);

        }


        function fire(sn, name, args) {

            if (typeof name == 'object') {
                // 重载 fire(name, item)
                var item = name;
                name = sn;
                sn = item.id;
                args = [item];
            }

            if (!name) {
                // fire(name)-向所有iframe传递事件 TODO
                return;
            }


            var iframe = top.$('iframe[data-sn="' + sn + '"]').get(0);
            if (!iframe) {
                throw new Error('不存在 sn 为 ' + sn + ' 的 iframe 页面');
            }

            var SMS = iframe.contentWindow.SMS;
            if (SMS) { // iframe 已加载完成
                var values = SMS.require('Iframe').fire(name, args);
                return values ? values[values.length - 1] : undefined;
            }


            // 尚未加载完成
            $(iframe).one('load', function () {

                var SMS;
                try {
                    SMS = iframe.contentWindow.SMS;
                } catch (e) {

                }
                if (SMS) { // iframe 已加载完成
                    SMS.require('Iframe').fire(name, args);
                }
            });

        }


        return {
            open: open,
            raise: raise,
            getData: getData,
            setData: setData,
            removeData: removeData,
            on: emitter.on.bind(emitter),

            fire: fire,

            sn$data: sn$data, // for test
        };


    });


    /**
     * 加载中的提示类。
     *
     * @author micty
     */
    define('Loading', function (require, exports, module) {

        var $ = require('$');
        var Samples = require('Samples');


        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();
        var sample = Samples.get('Loading');

        var defaults = {};


        function Loading(config) {

            var self = this;
            this[guidKey] = 'Loading-' + $.String.random();

            var container = config.container;
            var text = config.text || defaults.text;

            var meta = {
                container: container,
                selector: config.selector,
                text: text,
                rendered: false,

            };

            mapper.set(this, meta);


        }


        Loading.prototype = {// 实例方法

            constructor: Loading,

            render: function () {

                var meta = mapper.get(this);

                var html = $.String.format(sample, {
                    'text': meta.text
                });

                $(meta.container).html(html);

                meta.rendered = true;
            },


            show: function () {

                var meta = mapper.get(this);

                if (!meta.rendered) {
                    this.render();
                }

                var selector = meta.selector;
                if (selector) {
                    $(selector).hide();
                }

                $(meta.container).show();

            },

            hide: function () {
                var meta = mapper.get(this);

                var selector = meta.selector;
                if (selector) {
                    $(selector).show();
                }

                $(meta.container).hide();
            }
        };


        return $.Object.extend(Loading, { // 静态方法
            create: function (config) {
                var loading = new Loading(config);
                loading.show();
                return loading;
            },

            config: function (obj) {
                $.Object.extend(defaults, obj);
            }
        });

    });


    /**
     * 登录模块。
     *
     * @namespace
     * @author micty
     */
    define('Login', function (require, exports, module) {

        var $ = require('$');
        var API = require('API');
        var MD5 = require('MD5');
        var Dialog = require('Dialog');
        var Samples = require('Samples');

        var key = 'SMS.Login.user.F5F2BA55218E';
        var sample = Samples.get('Login');

        // 默认配置
        var defaults = {};

        /**
         * 检查是否已经登录。
         */
        function check(jump) {

            var user = get();
            var valid = !!(user && user.id);

            if (!valid && jump) {

                var url = top.location.href;

                var files = defaults.files;

                var master = '/' + files.master;
                var login = '/' + files.login;

                if (top === window && url.indexOf(master) < 0) { // 直接打开的是
                    // iframe
                    var a = url.split('/html/');
                    url = a[0] + login;
                }
                else { // master 页面或嵌套在 master 页面中的 iframe
                    url = url.replace(master, login);

                }

                top.location.href = url;
            }

            return valid;

        }


        /**
         * 显示登录提示对话框。
         */
        function show() {

            Dialog.use(function (Dialog) {

                var txtUser;
                var txtPassword;
                var txtCode;

                var user = getLast();

                function submit(dialog) {

                    var btn = dialog.find('[data-id="ok"]');
                    var html = btn.html();
                    var span = dialog.find('[data-field="msg"]').hide();

                    if (!txtUser.value) {
                        txtUser.focus();
                        span.html('请输入用户名').show();
                        return false;
                    }
                    if (!txtPassword.value) {
                        txtPassword.focus();
                        span.html('请输入密码').show();
                        return false;
                    }
                    if (!txtCode.value) {
                        txtCode.focus();
                        span.html('请输入验证码').show();
                        return false;
                    }

                    btn.html('登录中...').attr('disabled', true);

                    login({
                        'userName': txtUser.value,
                        'password': txtPassword.value,
                        'code': txtCode.value

                    }, function (data, json) {

                        if (window.self === window.top) {
                            top.SMS.Tips.add(window, 'success', '登录成功');
                        } else {
                            SMS.Tips.success('登录成功', 1500);
                        }
                        dialog.close();
                        // 刷新当前创窗口
                        location.href = location.href;
                    }, function (code, msg, json) {
                        span.html(msg).show();
                        btn.html(html).attr('disabled', false);
                        if (code === 40004) {
                            // 验证码错误
                            txtCode.value = '';
                            txtCode.focus();
                        }
                        if (code === 40003) {
                            txtPassword.value = '';
                            txtPassword.focus();
                        }

                    }, function () {
                        span.html('网络繁忙，登录失败，请稍候再试!').show();
                        btn.html(html).attr('disabled', false);
                        txtPassword.value = '';
                        txtPassword.focus();
                    });
                }

                var dialog = new Dialog({

                    /*      width: 240,
                          height: 100,*/
                    // 可能有多个请求被后端判定session超时，不需要弹出多个登录界面,设置固定id
                    id: 'login-dialog',
                    skin: 'login-box',
                    title: '会话结束请重新登录',
                    content: $.String.format(sample, {
                        user: user ? user['userName'] || '' : ''
                    }),

                    okValue: '立即登录',

                    ok: function () {
                        submit(this);
                        return false;
                    },

                    onshow: function () {

                        var self = this;

                        txtUser = this.find('[data-field="user"]').get(0);
                        txtPassword = this.find('[data-field="password"]').get(0);
                        txtCode = this.find('[data-field="code"]').get(0);
                        var img = this.find('[data-field="img"]').get(0);

                        $(txtUser).on('keydown', function (event) {
                            if (event.keyCode === 13) {
                                submit(self);
                            }
                        });

                        $(txtPassword).on('keydown', function (event) {
                            if (event.keyCode === 13) {
                                submit(self);
                            }
                        });

                        $(txtCode).on('keydown', function (event) {
                            if (event.keyCode === 13) {
                                submit(self);
                            }
                        });

                        $(img).on('click', function (event) {
                            var api = new API({
                                name: 'user/getVerificationCode',
                                data: {
                                    r: Math.random()
                                }
                            });

                            $(img).attr("src", api.getUrlWithGetParams());
                        });

                        $(img).click();
                    },

                    onfocus: function () {
                        setTimeout(function () {
                            if (user) {
                                txtPassword.focus();
                            }
                            else {
                                txtUser.focus();
                            }
                        }, 100);
                    }
                });

                dialog.showModal();

            });
        }


        /**
         * 获取当前已登录的用户信息。 如果不存在，则返回 null。
         */
        function get() {
            var user = $.SessionStorage.get(key);
            return user || null;
        }

        /**
         * 获取当前已登录的用户的角色类别。 如果不存在，则返回 null。
         */
        function getUserRoleType() {
            var user = $.SessionStorage.get(key);
            if (!user) {
                return null;
            }
            return user.roles && user.roles[0] && user.roles[0]['type'] || null;
        }

        /**
         * 获取最近曾经登录过的用户信息。 如果不存在，则返回 null。
         */
        function getLast() {
            var user = $.LocalStorage.get(key);
            return user || null;
        }


        /**
         * 调用登录接口进行登录。
         */
        function login(data, fnSuccess, fnFail, fnError) {

            var api = new API(defaults.apiLogin);

            api.get({
                userName: data.userName,
                password: MD5.encrypt(data.password),
                code: data.code
            });

            api.on('success', function (data, json) { // 成功

                var user = $.Object.extend({}, data, {

                    messageCount: data.messageCount || 0,
                    companyList: [
                        // { name: '蓝海机电有限公司' },
                        // { name: '金蝶国际有限公司' },
                    ]
                });
                // 把用户信息存起来，以便跨页使用
                $.SessionStorage.set(key, user);
                $.LocalStorage.set(key, user);

                fnSuccess && fnSuccess(user, data, json);


            });

            api.on({
                'fail': fnFail,
                'error': fnError
            });

        }


        /**
         * 调用注销接口进行注销。
         */
        function logout(fnSuccess, fnFail, fnError) {

            var api = new API(defaults.apiLoginout);

            api.on({
                'fail': fnFail,
                'error': fnError,
            });

            api.get();

            api.on('success', function (data, json) { // 成功

                $.SessionStorage.remove(key); // 只移除会话级的

                var user = get();
                fnSuccess && fnSuccess(user, data, json);

            });
        }


        return {
            check: check,
            get: get,
            getLast: getLast,
            getUserRoleType: getUserRoleType,
            show: show,
            login: login,
            logout: logout,
            config: function (obj) {
                $.Object.extend(defaults, obj);
            }
        };


    });


    /**
     * 标准分页控件
     *
     * @author micty
     */
    define('Pager', function (require, exports, module) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');


        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();

        var samples = $.String.getTemplates(top.document.body.innerHTML, [
            {
                name: 'ul',
                begin: '<!--Samples.Pager--!',
                end: '--Samples.Pager-->',
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}'
            },
            {
                name: 'more',
                begin: '#--more.begin--#',
                end: '#--more.end--#',
                outer: ''
            },
            {
                name: 'pageSize',
                begin: '#--item.page-size.begin--#',
                end: '#--item.page-size.end--#',
                outer: '{pageSize}'
            }
        ]);


        /**
         * 填充指定区间的一个区域页码。
         *
         * @param {number}
         *            current 当前激活的页码。
         * @param {nmuber}
         *            from 要填充的起始页码。
         * @param {nmuber}
         *            to 要填充的结束页码。
         * @param {boolean}
         *            more 指示是否生成"更多"样式。
         * @return {string} 返回填充好的 html 字符串。
         */
        function fillRegion(current, from, to, more) {

            if (typeof from == 'object') { // fillRegion(current, { });
                var obj = from;
                from = obj.from;
                to = obj.to;
                more = obj.more;
            }


            var pageNos = $.Array.pad(from, to + 1);

            var html = $.Array.keep(pageNos, function (no, index) {


                var isCurrent = no == current;

                return $.String.format(samples.item, {

                    no: no,
                    'active': isCurrent ? 'active' : '',
                    'data-no': isCurrent ? '' : 'data-no="' + no + '"',
                });
            }).join('');

            if (more) {
                html += samples.more;
            }

            return html;

        }

        /**
         * 根据总页数和当前页计算出要填充的区间。
         *
         * @param {number}
         *            count 总页数。
         * @param {number}
         *            current 当前激活的页码。
         * @return {Array} 返回一个区间描述的数组。
         */
        function getRegions(count, current) {

            if (count <= 10) {
                return [
                    {
                        from: 1,
                        to: count,
                        more: false
                    }
                ];
            }

            if (current <= 3) {
                return [
                    {
                        from: 1,
                        to: 5,
                        more: true
                    }
                ];
            }

            if (current <= 5) {
                return [
                    {
                        from: 1,
                        to: current + 2,
                        more: true
                    }
                ];
            }

            if (current >= count - 1) {
                return [
                    {
                        from: 1,
                        to: 2,
                        more: true
                    },
                    {
                        from: count - 5,
                        to: count,
                        more: false
                    }
                ];
            }

            return [
                {
                    from: 1,
                    to: 2,
                    more: true
                },
                {
                    from: current - 2,
                    to: current + 2,
                    more: current + 2 != count
                }
            ];
        }

        /**
         * 根据总页数、当前页和上一页预测出要跳转的页码。
         *
         * @param {number}
         *            count 总页数。
         * @param {number}
         *            current 当前激活的页码。
         * @param {number}
         *            last 上一页的页码。
         * @return {number} 返回一个跳转的页码。
         */
        function getJumpNo(count, current, last) {

            if (count <= 1) { // 0 或 1
                return count;
            }

            if (current == count) {
                return count - 1;
            }

            var no;

            if (current > last) {
                no = current + 1;
            }
            else {
                no = current - 1;
                if (no < 1) {
                    no = 2;
                }
            }

            return no;

        }


        /**
         * 根据指定配置信息创建一个分页器实例。
         *
         * @param {Object}
         *            config 传入的配置对象。 其中：
         * @param {string|DOMElement}
         *            container 分页控件的 DOM 元素容器。
         * @param {number}
         *            [current=1] 当前激活的页码，默认从 1 开始。
         * @param {number}
         *            size 分页大小，即每页的记录数。
         * @param {number}
         *            total 总的记录数。
         * @param {function}
         *            change 页码发生变化时的回调函数。 该函数会接受到当前页码的参数；并且内部的 this 指向当前 Pager
         *            实例。
         * @param {function}
         *            error 控件发生错误时的回调函数。 该函数会接受到错误消息的参数；并且内部的 this 指向当前 Pager
         *            实例。
         */
        function Pager(config) {

            var id = $.String.random().toLowerCase();
            this[guidKey] = 'Pager-' + id;


            var container = config.container;

            var current = config.current || 1;  // 当前页码，从 1 开始
            var sizes = config.sizes || [10, 20, 30]; // 分页大小设置项

            //var size = config.size;
            var size = config.size || sizes[0];             // 分页的大小，即每页的记录数

            var total = config.total;           // 总记录数
            var count = Math.ceil(total / size);// 总页数

            var ulId = 'ul-pager-' + id;
            var txtId = 'txt-pager-' + id;

            var emitter = new MiniQuery.Event(this);

            var meta = {
                total: total,
                ulId: ulId,
                txtId: txtId,
                container: container,
                current: current,
                size: size,
                sizes: sizes,
                count: count,
                hideIfLessThen: config.hideIfLessThen || 0,
                emitter: emitter,
                last: 0 // 上一次的页码
            };

            mapper.set(this, meta);


            var self = this;

            $.Array.each(['change', 'error'], function (name, index) {

                var fn = config[name];
                if (fn) {
                    self.on(name, fn);
                }
            });


            function jump() {
                var txt = document.getElementById(txtId);
                var no = txt.value;
                self.to(no, true);
            }

            function changePageSize(pageSize) {
                self.changePageSize(pageSize);
            }

            // 委托控件的 UI 事件
            var delegates = {
                no: '#' + ulId + ' [data-no]',
                button: '#' + ulId + ' [data-button]:not(.disabled)',
                txt: '#' + txtId,
                select: '#' + ulId + ' select.page-size-select'
            };


            $(container).delegate(delegates.no, 'click', function (event) { // 点击分页的页码按钮

                var li = this;
                var no = +li.getAttribute('data-no');
                self.to(no, true);

            }).delegate(delegates.button, 'click', function (event) { // 点击"上一页"|"下一页"|"确定"

                var li = this;
                var name = li.getAttribute('data-button');

                if (name == 'to') { // 点击 "确定"
                    jump();
                }
                else { // previous 或 next
                    self[name](true);
                }

            }).delegate(delegates.txt, 'keydown', function (event) { // 回车确定
                if (event.keyCode == 13) {
                    jump();
                }
            }).delegate(delegates.select, 'change', function () {
                // 切换分页大小
                meta.size = $(this).val();
                // 当前页码，从 1 开始
                meta.current = 1;
                // 重新计算总页数
                meta.count = Math.ceil(meta.total / meta.size);
                // 触发事件
                self.changePageSize(meta.size);
                // 跳转到第一页
                self.to(1, true);
            });


        }


        Pager.prototype = { // 实例方法

            constructor: Pager,

            render: function () {

                var meta = mapper.get(this);
                var total = meta.total; //总条数
                var count = meta.count; // 总页数
                if (count < meta.hideIfLessThen) {
                    $(meta.container).hide();
                    return;
                }

                // 当前页码，不能超过总页数 (考虑到 count==0)
                var current = Math.min(count, meta.current);

                var regions = getRegions(count, current);

                var itemsHtml = $.Array.keep(regions, function (item, index) {

                    return fillRegion(current, item);

                }).join('');

                var sizesHtml = $.Array.keep(meta.sizes, function (item, index) {

                    return $.String.format(samples.pageSize, {
                        value: item,
                        selected: item == meta.size ? 'selected' : ''
                    });

                }).join('');

                var toNo = getJumpNo(count, current, meta.last);

                var html = $.String.format(samples.ul, {
                    'ul-id': meta.ulId,
                    'txt-id': meta.txtId,
                    total: total,
                    current: current,
                    count: count,
                    toNo: toNo,
                    'first-disabled-class': current == Math.min(1, count) ? 'disabled' : '',
                    'final-disabled-class': current == count ? 'disabled' : '',
                    'jump-disabled-class': count == 0 ? 'disabled' : '',
                    'txt-disabled': count == 0 ? 'disabled' : '',
                    items: itemsHtml,
                    pageSize: sizesHtml
                });


                $(meta.container).html(html).show(); // 要重新显示出来，之前可能隐藏了


            },

            /**
             * 跳转到指定页码的分页。
             *
             * @param {number}
             *            no 要跳转的页码。 指定的值必须为从 1-max 的整数，其中 max 为本控件最大的页码值。
             *            如果指定了非法值，则会触发 error 事件。
             * @param {boolean}
             *            [fireEvent=false] 指示是否要触发事件。 该参数仅供内部使用，外部调用时可忽略它。
             */
            to: function (no, fireEvent) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;


                var isValid = (/^\d+$/).test(no)

                if (!isValid) {
                    if (fireEvent) {
                        emitter.fire('error', ['输入的页码必须是大于 0 的数字']);
                    }
                    return;
                }

                no = parseInt(no);
                var count = meta.count;

                if (no < 1 || no > count) {
                    if (fireEvent) {
                        emitter.fire('error', ['输入的页码值只能从 1 到 ' + count]);
                    }
                    return;
                }

                meta.last = meta.current;
                meta.current = no;

                this.render();

                if (fireEvent) {
                    emitter.fire('change', [no, meta.size]);
                }

            },


            previous: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current - 1, fireEvent);
            },

            next: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current + 1, fireEvent);
            },

            first: function (fireEvent) {
                this.to(1, fireEvent);
            },

            final: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.count, fireEvent);
            },

            focus: function () {
                var meta = mapper.get(this);
                $(meta.container).find("input.page-number").get(0).focus();
            },

            refresh: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current, fireEvent);
            },

            changePageSize: function (pageSize) {
                var meta = mapper.get(this);
                var emitter = meta.emitter;
                emitter.fire('changePageSize', [pageSize]);
            },
            /**
             * 给本控件实例绑定事件。
             */
            on: function () {
                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },

            /**
             * 销毁本控件实例。
             */
            destroy: function () {

                var meta = mapper.get(this);

                meta.emitter.off();

                var container = meta.container;
                $(container).html('').undelegate();

                mapper.remove(this);

            },
            /**
             * 获取总记录数
             */
            getTotal: function () {
                var meta = mapper.get(this);
                return meta.total;
            }
        };


        return $.Object.extend(Pager, { // 静态方法

            create: function (config) {

                var pager = new Pager(config);
                pager.render();

                return pager;
            }

        });

    });


    /**
     * 多个分页控件管理器
     *
     * @author micty
     */
    define('Pagers', function (require, exports, module) {

        var $ = require('$');
        var Pager = require('Pager');
        var SimplePager = require('SimplePager');


        var extend = $.Object.extend;


        function create(config) {

            var container = config.container;


            var simple = new SimplePager(extend({}, config, {
                container: container.simple
            }));

            simple.on('change', function (no) {
                pager.to(no);
            });


            var pager = new Pager(extend({}, config, {
                container: container.normal
            }));

            pager.on('change', function (no) {
                simple.to(no);
            });
            pager.on('changePageSize', function (pageSize) {
                simple.changePageSize(pageSize);
            });

            simple.render();
            pager.render();

            //return [simple, pager];
            return {
                simple: simple,
                pager: pager
            }

        }


        return {
            create: create
        };
    });


    /**
     * 模板模块。
     *
     * @namespace
     * @author micty
     */
    define('Samples', function (require, exports, module) {

        var $ = require('$');

        var html = top.document.body.innerHTML;


        function trim(s) {
            return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
        }

        /**
         * 获取指定名称的模板。
         *
         * @param {string}
         *            name 模板的名称。
         * @param {Array}
         *            [tags] 子模板列表。
         * @return {string} 返回指定名称的模板字符串。
         */
        function get(name, tags) {

            var begin = '<!--Samples.' + name + '--!';
            var end = '--Samples.' + name + '-->';

            var sample = $.String.between(html, begin, end);

            if (tags) {

                $.Array.each(tags, function (item, index) {
                    if (!item.trim) {
                        return;
                    }

                    // 指定了要美化模板
                    delete item.trim;

                    var fn = item.fn;
                    if (fn) { // 原来已指定了处理函数，则扩展成钩子函数
                        item.fn = function (s) {
                            s = fn(s);
                            s = trim(s);
                            return s;
                        };
                    }
                    else {
                        item.fn = trim;
                    }
                });

                return $.String.getTemplates(sample, tags);
            }

            return sample;
        }


        return {
            get: get,
        };


    });


    /**
     * 简单分页控件
     *
     * @author micty
     */
    define('SimplePager', function (require, exports, module) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');
        var Samples = require('Samples');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();
        var sample = Samples.get('SimplePager');


        var defaults = {};


        /**
         * 根据指定配置信息创建一个分页器实例。
         *
         * @param {Object}
         *            config 传入的配置对象。 其中：
         * @param {string|DOMElement}
         *            container 分页控件的 DOM 元素容器。
         * @param {number}
         *            [current=1] 当前激活的页码，默认从 1 开始。
         * @param {number}
         *            size 分页大小，即每页的记录数。
         * @param {number}
         *            total 总的记录数。
         * @param {function}
         *            change 页码发生变化时的回调函数。 该函数会接受到当前页码的参数；并且内部的 this 指向当前 Pager
         *            实例。
         * @param {function}
         *            error 控件发生错误时的回调函数。 该函数会接受到错误消息的参数；并且内部的 this 指向当前 Pager
         *            实例。
         */
        function SimplePager(config) {

            var id = $.String.random().toLowerCase();
            var suffixId = '-simple-pager-' + id;

            this[guidKey] = 'SimplePager-' + id;


            var container = config.container || defaults.container;
            var current = config.current || defaults.current;   // 当前页码，从 1 开始
            var sizes = config.sizes || defaults.sizes || [10, 20, 30];
            var size = config.size || sizes[0];            // 分页的大小，即每页的记录数

            var total = config.total;           // 总记录数
            var count = Math.ceil(total / size);// 总页数

            var ulId = 'ul' + suffixId;
            var txtId = 'txt' + suffixId;

            var emitter = new MiniQuery.Event(this);

            var meta = {
                'ulId': ulId,
                'txtId': txtId,
                'container': container,
                'current': current,
                'size': size,
                'sizes': sizes,
                'total': total,
                'count': count,
                'hideIfLessThen': config.hideIfLessThen || defaults.hideIfLessThen,
                'emitter': emitter,
            };

            mapper.set(this, meta);


            var self = this;

            $.Array.each(['change', 'error'], function (name, index) {

                var fn = config[name];
                if (fn) {
                    self.on(name, fn);
                }
            });


            var selector = '#' + ulId + ' [data-button]:not(.disabled)';

            $(container).delegate(selector, 'click', function (event) {

                var li = this;
                var name = li.getAttribute('data-button');
                self[name](true);

            });


            $(container).delegate('#' + txtId, {

                'focusin': function (event) {
                    var txt = this;
                    txt.value = meta.current;

                },

                'focusout': function (event) {
                    jump(this);
                },

                'keydown': function (event) {
                    if (event.keyCode == 13) { // 回车键
                        jump(this);
                    }
                }

            });

            //
            function jump(txt) {

                var no = +txt.value;
                if (no != meta.current) {
                    self.to(no, true);
                }
                else {
                    self.render();
                }
            }


        }


        SimplePager.prototype = { // 实例方法

            constructor: SimplePager,

            render: function () {

                var meta = mapper.get(this);
                var container = meta.container;

                var count = meta.count; // 总页数
                if (count < meta.hideIfLessThen) {
                    $(container).hide();
                    return;
                }

                // 当前页码，不能超过总页数 (考虑到 count==0)
                var current = Math.min(count, meta.current);

                var html = $.String.format(sample, {
                    'ul-id': meta.ulId,
                    'txt-id': meta.txtId,

                    current: current,
                    count: count,

                    'first-disabled-class': current == Math.min(1, count) ? 'disabled' : '',
                    'final-disabled-class': current == count ? 'disabled' : '',
                });


                $(container).html(html).show(); // 要重新显示出来，之前可能隐藏了


            },

            /**
             * 跳转到指定页码的分页。
             */
            to: function (no, fireEvent) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;


                var isValid = (/^\d+$/).test(no)

                if (!isValid) {
                    if (fireEvent) {
                        emitter.fire('error', ['输入的页码必须是大于 0 的数字']);
                    }
                    return false;
                }

                no = parseInt(no);
                var count = meta.count;

                if (no < 1 || no > count) {
                    if (fireEvent) {
                        emitter.fire('error', ['输入的页码值只能从 1 到 ' + count]);
                    }
                    return false;
                }


                meta.current = no;
                this.render();

                if (fireEvent) {
                    emitter.fire('change', [no]);
                }

                return true;

            },

            focus: function () {
                var meta = mapper.get(this);
                $(meta.container).find('input').get(0).focus();
            },


            previous: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current - 1, fireEvent);
            },

            next: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current + 1, fireEvent);
            },

            first: function (fireEvent) {
                this.to(1, fireEvent);
            },

            final: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.count, fireEvent);
            },

            refresh: function (fireEvent) {
                var meta = mapper.get(this);
                this.to(meta.current, fireEvent);
            },
            changePageSize: function (pageSize) {
                var meta = mapper.get(this);
                meta.size = pageSize;
                meta.count = Math.ceil(meta.total / meta.size);// 总页数
                this.render();
            },
            on: function () {
                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },
            /**
             * 销毁本控件实例。
             */
            destroy: function () {

                var meta = mapper.get(this);

                meta.emitter.off();

                var container = meta.container;
                $(container).html('').undelegate();

                mapper.remove(this);

            },
        };


        return $.Object.extend(SimplePager, { // 静态方法

            create: function (config) {

                var pager = new SimplePager(config);
                pager.render();

                return pager;
            },

            config: function (obj) {

                $.Object.extend(defaults, obj);
            }
        });


    });


    /**
     * 标签列表控件
     *
     * @author micty
     */
    define('Tabs', function (require, exports, module) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');


        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();


        /**
         * 根据指定配置信息创建一个标签列表实例。
         *
         * @param {Object}
         *            config 传入的配置对象。 其中：
         * @param {string|DOMElement}
         *            container 标签容器。
         * @param {string|DOMElement}
         *            selector 标签的项的选择器
         * @param {string}
         *            activedClass 激活的标签的 CSS 样式类名。
         * @param {number}
         *            [current] 初始时激活的标签索引，如果不指定，则初始时不激活。
         * @param {string}
         *            event 要绑定到标签的事件名称，如 'click'。
         * @param {function}
         *            change 标签激活发生变化时的回调函数。 该函数会接受到当前标签索引 index 的参数；并且内部的 this
         *            指向当前 Tabs 实例。
         */
        function Tabs(config) {

            var self = this;
            this[guidKey] = 'Tabs-' + $.String.random();

            var container = config.container;
            var selector = config.selector;
            var activedClass = config.activedClass;
            var eventName = config.event;

            var emitter = new MiniQuery.Event(this);

            var meta = {
                container: container,
                selector: selector,
                activedClass: activedClass,
                eventName: eventName,
                activedIndex: -1,
                emitter: emitter,
            };

            mapper.set(this, meta);


            if (eventName) {
                $(container).delegate(selector, eventName, function (event) {
                    // 阻止事件冒泡
                    event.stopPropagation();

                    var item = this;
                    var index;

                    if ('indexKey' in config) { // 推荐的方式
                        index = +item.getAttribute(config.indexKey);
                    }
                    else {
                        var list = $(container).find(selector).toArray();

                        index = $.Array.findIndex(list, function (node, index) {
                            return node === item;
                        });
                    }

                    emitter.fire('event', [index, item]);


                    self.active(index, true);

                });
            }
            // 跑出一个双击事件
            $(container).delegate(selector, 'dblclick', function (event) {
                // 阻止事件冒泡
                event.stopPropagation();

                var item = this;
                var index;

                if ('indexKey' in config) { // 推荐的方式
                    index = +item.getAttribute(config.indexKey);
                }
                else {
                    var list = $(container).find(selector).toArray();

                    index = $.Array.findIndex(list, function (node, index) {
                        return node === item;
                    });
                }

                if (index === meta.activedIndex) {
                    emitter.fire('dblclick', [index, item]);
                }

            });


            var change = config.change;
            if (change) {
                this.on('change', change);
            }


            var current = config.current;
            if (typeof current == 'number' && current >= 0) {
                this.active(current, true);
            }


        }


        Tabs.prototype = { // 实例方法

            constructor: Tabs,

            /**
             * 激活当前实例指定索引值的项。
             *
             * @param {number}
             *            index 要激活的项的索引值，从 0 开始。
             * @param {boolean}
             *            [fireEvent=false] 指示是否要触发 change 事件。 该参数由内部调用时指定为
             *            true。 外部调用时可忽略该参数。
             */
            active: function (index, fireEvent) {

                var meta = mapper.get(this);
                var activedIndex = meta.activedIndex;

                if (index == activedIndex && fireEvent) { // 安静模式时，即使重新激活当前已激活的项，也要允许。
                    return;
                }

                this.reset();


                meta.activedIndex = index;

                var activedClass = meta.activedClass;
                var list = $(meta.container).find(meta.selector).toArray();
                var item = list[index];

                $(item).addClass(activedClass);

                if (fireEvent) { // 触发事件
                    meta.emitter.fire('change', [index, item]);
                }

            },


            /**
             * 重置当前实例到初始状态。
             */
            reset: function () {

                var meta = mapper.get(this);
                $(meta.container).find(meta.selector).removeClass(meta.activedClass);

                meta.activedIndex = -1;
            },


            remove: function (index) {

                var meta = mapper.get(this);
                var activedIndex = meta.activedIndex;

                if (index == activedIndex) { // 移除的是当前的激活项
                    this.reset();
                    return;
                }


                if (index < activedIndex) { // 移除的是当前激活项之前的，则重新设置激活状态即可
                    activedIndex--;
                }

                this.active(activedIndex, false);

            },

            /**
             * 销毁当前实例。
             */
            destroy: function () {

                var meta = mapper.get(this);

                var eventName = meta.eventName;
                if (eventName) {
                    $(meta.container).undelegate(meta.selector, eventName);
                }

                meta.emitter.off();

                mapper.remove(this);
            },

            /**
             * 给当前实例绑定一个指定名称的事件回调函数。
             */
            on: function (name, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },

            /**
             * 获取当前实例激活的索引值。
             */
            getActivedIndex: function () {
                var meta = mapper.get(this);
                return meta.activedIndex;
            }
        };


        return $.Object.extend(Tabs, { // 静态方法

            create: function (config) {
                return new Tabs(config);
            }
        });

    });


    /**
     * 简单的模板填充
     *
     * @author micty
     */
    define('Template', function (require, exports, module) {

        var $ = require('$');


        var format = $.String.format;

        // 缓存已处理过的节点的模板，从而可以再次填充
        var id$sample = {};

        // 用于保存在节点中的自定义属性的键名称，为避免冲突，后缀加个首次运行时就确定的随机串
        var idKey = 'data-template-id-' + $.String.random(4).toLowerCase();


        /**
         * 对指定的 DOM 节点进行简单的模板填充。
         *
         * @param {DOMElement|string|Object}
         *            node DOM 节点或其 id，可以以 # 开始。 如果指定一个 {} 的纯对象，则会迭代每个 key:
         *            value 并递归调用，这相当于批量操作。
         * @param {Object|Array}
         *            data 要填充的数据，数据中的字段名必须跟模板中要用到的一致。 如果是数组，则会迭代数组每项进行填充。
         *            如果是对象，则只填充一次。
         * @param {function}
         *            [fn] 迭代调用时的函数。 当参数 data 为数组时，会进行迭代调用该函数 fn，fn 会接收到 item 和
         *            index 作为参数， 然后以 fn 的返回结果作为当前项的数据来进行填充。
         */
        function fill(node, data, fn) {

            if ($.Object.isPlain(node)) { // 重载，批量填充 fill( { key: value }, fn
                // )

                fn = data; // 修正参数位置。
                $.Object.each(node, function (key, value) {
                    fill(key, value, fn);
                });

                return;
            }


            if (typeof node == 'string') { // node 是 id
                var id = node;
                if (id.indexOf('#') == 0) { // 以 # 开始
                    id = id.slice(1);
                }
                node = document.getElementById(id);
            }

            var sample = get(node);

            if (data instanceof Array) {

                node.innerHTML = $.Array.keep(data, function (item, index) {

                    if (fn) {
                        item = fn(item, index);
                    }

                    return format(sample, item);

                }).join('');
            }
            else {
                node.innerHTML = format(sample, data);
            }

        }


        /**
         * 获取指定的 DOM 节点的模板。 该方法会对模板进行缓存，从而可以多次获取，即使该节点的 innerHTMl 已发生改变。
         *
         * @param {DOMElement|string}
         *            node DOM 节点或基 id，可以以 # 开始。
         * @return {string} 返回该节点的模板字符串。
         */
        function get(node) {

            var id;

            if (typeof node == 'string') { // node 是 id
                id = node;
                if (id.indexOf('#') == 0) { // 以 # 开始
                    id = id.slice(1);
                }
                node = document.getElementById(id);
            }
            else { // node 是 DOM 节点

                id = node.id;

                if (!id) {
                    id = node.getAttribute(idKey);

                    if (!id) {
                        id = node.tagName.toLowerCase() + '-' + $.String.random();
                        node.setAttribute(idKey, id);
                    }
                }
            }


            var sample = id$sample[id];

            if (!sample) { // 首次获取
                sample = $.String.between(node.innerHTML, '<!--', '-->');
                id$sample[id] = sample; // 缓存起来
            }

            return sample;
        }


        return {

            fill: fill,
            get: get
        };


    });


    /**
     * 顶部的提示控件
     *
     * @author micty
     */
    define('Tips', function (require, exports, module) {


        var durationId;
        var delayId;


        function show(type, text, duration, delay) {


            if (typeof text == 'object' && text) { // show(type, config)

                var config = text;

                text = config.text;
                duration = config.duration;
                delay = config.delay;
            }


            // 先清空之前可能留下的
            clearTimeout(durationId);
            clearTimeout(delayId);

            if (delay) { // 指定了延迟开始
                delayId = setTimeout(start, delay);
            }
            else {
                start();
            }

            if (duration) {
                durationId = setTimeout(hide, duration);
            }

            // 一个内部的共用方法
            function start() {

                var Tips = top.SMS.require('Tips');

                if (type) {
                    Tips.add(window, type, text);
                }
                else {
                    Tips.open(window);
                }
            }
        }


        function hide() {

            // 先清空之前可能留下的
            clearTimeout(durationId);
            clearTimeout(delayId);

            top.SMS.require('Tips').close(window);
        }


        function success(text, duration, delay) {
            show('success', text, duration, delay);
        }

        function info(text, duration, delay) {
            show('info', text, duration, delay);
        }

        function warn(text, duration, delay) {
            show('warn', text, duration, delay);
        }

        function error(text, duration, delay) {
            show('error', text, duration, delay);
        }

        function loading(text, duration, delay) {
            show('loading', text, duration, delay);
        }

        function cancel() {
            clearTimeout(delayId);
        }


        return {
            show: show,
            hide: hide,
            success: success,
            info: info,
            warn: warn,
            error: error,
            loading: loading,
            cancel: cancel,

            // 为了兼容直接在 top 页面打开
            add: function (window, type, text) {
                alert(type + ':' + text);
            },

            // 为了兼容直接在 top 页面打开
            open: function () {

            },

            // 为了兼容直接在 top 页面打开
            close: function () {
            }
        };

    });


    /**
     * 数值型输入框类。
     *
     * @author micty
     */
    define('NumberField', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');


        var mapper = new $.Mapper();

        var key$key = {

            groupSign: 'aSep',      // 分组的分隔符号，默认为逗号 ","
            groupCount: 'dGroup',   // 分组的位数，默认为 3
            decimalSign: 'aDec',    // 小数点的符号，默认为点号 "."
            decimalKey: 'altDec',   // 输入小数点的代替键，一般不需要用到
            currencySign: 'aSign',  // 货币的符号
            currencyPlace: 'pSign', // 货币的符号所处的位置，前面或后面，取值: "left"|"right"
            min: 'vMin',            // 允许的最小值
            max: 'vMax',            // 允许的最大值
            decimalCount: 'mDec',   // 小数的位数，默认为 3
            round: 'mRound',        // 四舍五入
            padded: 'aPad',         // 是否用 "0" 填充小数位，取值: true|false
            bracket: 'nBracket',    // 输入框失去焦点后，负数的展示括号
            empty: 'wEmpty',        // 输入框为空时的显示行为
            leadingZero: 'lZero',   // 前缀 "0" 的展示行为
            formatted: 'aForm',     // 控制是否在页面就绪时自动格式化输入框的值
        };

        var key$value$value = {

            currencyPlace: {
                left: 'p', // 前缀
                right: 's' // 后缀
            }
        };

        // 默认配置
        var defaults = {};


        // 把配置对象归一化成原始控件所需要的格式
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

        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $this = meta.$this;

            var args = [].slice.call($argumetns, 0);
            args = [name].concat(args);

            return $this.autoNumeric.apply($this, args);

        }


        /**
         * 构造函数。
         */
        function NumberField(selector, config) {

            if ($.Object.isPlain(selector)) { // 重载 NumberField( config )
                config = selector;
                selector = config.selector;
                delete config.selector; // 删除，避免对原始造成不可知的副作用
            }

            config = config ? normalizeObject(config) : {};
            config = $.Object.extend({}, defaults, config);

            var $this = $(selector).autoNumeric(config);

            var meta = {
                $this: $this,
            };

            mapper.set(this, meta);

        }


        NumberField.prototype = { // 实例方法
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


        return {

            use: function (fn) {
                Seajs.use('autoNumeric', function () {
                    fn && fn(NumberField);
                });
            },

            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };
    });


    /**
     * 普通消息提示框
     */
    define('MessageBox', function (require, module, exports) {

        var $ = require('$');

        var Dialog = require('Dialog');

        /*
         * info: 提示文本 caption: 提示标题 showClose: 是否显示关闭按钮
         */
        function show(info, caption, showClose) {

            info = $.String.format('<div style="padding: 20px 20px;max-width:500px;max-height:300px;overflow: auto;"><code>{0}</code></div>', info);

            Dialog.use(function (Dialog) {
                var config = {
                    title: caption ? caption : '金蝶提示',
                    content: info
                };
                if (showClose) {
                    config.button = [
                        {
                            value: '确定',
                            className: 'sms-submit-btn',
                        }
                    ];
                }
                var dialog = new Dialog(config);

                dialog.showModal();
            });
        }

        /*
         * info: 提示文本 caption: 提示标题 fnClose(result): 回调返回是否点击了确定按钮
         */
        function confirm(info, caption, fnClose) {

            Dialog.use(function (Dialog) {

                if (typeof caption === 'function') {
                    fnClose = caption;
                    caption = '';
                }

                info = $.String.format('<div style="padding: 20px 20px;max-width:500px;">{0}</div>', info);

                var dialog = new Dialog({
                    title: caption ? caption : '金蝶提示!',
                    content: info,
                    button: [
                        {
                            value: '确定',
                            className: 'sms-submit-btn',
                            callback: function () {
                                this.isSubmit = true;
                            },
                        },
                        {
                            value: '取消',
                            className: 'sms-cancel-btn',
                            callback: function () {
                                this.isSubmit = false;
                            },
                        },
                    ],
                });

                dialog.on({
                    remove: function () {
                        fnClose(this.isSubmit);
                    },
                });

                dialog.showModal();
            });
        }

        return {
            show: show,
            confirm: confirm,
        };
    });

    /**
     * 日期时间选择器类。
     *
     * @author micty
     */
    define('DateTimePicker', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');


        var mapper = new $.Mapper();


        // 默认配置
        var defaults = {};


        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $this = meta.$this;

            var args = [].slice.call($argumetns, 0);
            args = [name].concat(args);

            return $this.datetimepicker.apply($this, args);

        }


        /**
         * 构造函数。
         */
        function DateTimePicker(selector, config) {

            if ($.Object.isPlain(selector)) { // 重载 DateTimePicker( config )
                config = selector;
                selector = config.selector;
                delete config.selector; // 删除，避免对原始造成不可知的副作用
            }

            config = $.Object.extend({}, defaults, config);

            var $this = $(selector).datetimepicker(config);

            var meta = {
                $this: $this,
            };

            mapper.set(this, meta);

        }


        DateTimePicker.prototype = { // 实例方法
            constructor: DateTimePicker,

            on: function (name, fn) {
                var meta = mapper.get(this);
                var $this = meta.$this;

                $this.on(name, fn);
            },

            remove: function () {
                invoke(this, 'remove', arguments);
            },

            show: function () {
                invoke(this, 'show', arguments);
            },

            hide: function () {
                invoke(this, 'hide', arguments);
            },

            update: function () {
                invoke(this, 'update', arguments);
            },
            getDate: function () {
                return invoke(this, 'getDate', arguments);
            },
            getFormattedDate: function () {
                return invoke(this, 'getFormattedDate', arguments);
            },
            setStartDate: function () {
                invoke(this, 'setStartDate', arguments);
            },

            setEndDate: function () {
                invoke(this, 'setEndDate', arguments);
            },

            setDaysOfWeekDisabled: function () {
                invoke(this, 'setDaysOfWeekDisabled', arguments);
            },
        };


        return {

            use: function (fn) {

                Seajs.use([
                    'datetimepicker-css',
                    'datetimepicker-js',
                ], function () {

                    fn && fn(DateTimePicker);
                });
            },


            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };


    });

    /**
     * zTree选择器类。
     *
     * @author yadda
     */
    define('ZTree', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');


        var mapper = new $.Mapper();

        // 默认配置
        var defaults = {};

        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $this = meta.$this;

            var args = [].slice.call($argumetns, 0);

            return $this[name].apply($this, args);

        }


        /**
         * 构造函数。
         */
        function ZTree(selector, config, data) {

            if ($.Object.isPlain(selector)) { // 重载 ( config )
                config = selector;
                selector = config.selector;
                data = config.data;
                config = config.config;
            }

            config = $.Object.extend({}, defaults, config);

            var $this = $.fn.zTree.init($(selector), config, data);
            var meta = {
                $this: $this
            };

            mapper.set(this, meta);

        }


        ZTree.prototype = { // 实例方法
            constructor: ZTree,

            on: function (name, fn) {
                var meta = mapper.get(this);
                var $this = meta.$this;

                if ($.Object.isPlain(name)) {
                    for (var item in name) {
                        if (item in $this.setting.callback) {
                            $this.setting.callback[item] = name[item];
                        }
                    }
                    return;
                }

                $this.setting.callback[name] = fn;
            },

            getTrueZTree: function () {
                // 获取原始控件
                var meta = mapper.get(this);
                return meta.$this;
            },
            getNodeByParam: function () {
                return invoke(this, 'getNodeByParam', arguments);
            },
            getSelectedNodes: function () {
                return invoke(this, 'getSelectedNodes', arguments);
            },
            selectNode: function () {
                return invoke(this, 'selectNode', arguments);
            },
            checkNode: function () {
                return invoke(this, 'checkNode', arguments);
            },
            getCheckedNodes: function () {
                return invoke(this, 'getCheckedNodes', arguments);
            },
            setting: function () {
                var meta = mapper.get(this);
                return meta.$this['setting'];
            },
            //setting: mapper.get(this).$this['setting']
        };


        return {

            use: function (fn) {

                Seajs.use([
                    'zTree-js',
                    // 'zTree-css',
                    'zTreeMetro-css'
                ], function () {
                    fn && fn(ZTree);
                });

            },


            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };

    });


    /**
     * BarCode一维码生成器。
     *
     * @author yadda
     */
    define('BarCode', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');

        return {

            use: function (fn) {

                Seajs.use([
                    'jquery-barcode',
                ], function () {
                    fn && fn();
                });

            }

        };

    });

    /**
     * bootstrap-treeview
     *
     * @author yadda
     */
    define('TreeView', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');

        var mapper = new $.Mapper();

        // 默认配置
        var defaults = {};

        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $selector = meta.$this;

            var args = [].slice.call($argumetns, 0);

            return $selector.treeview(name, args);

        }


        /**
         * 构造函数。
         */
        function TreeView(selector, config) {

            if ($.Object.isPlain(selector)) { // 重载 ( config )
                config = selector;
                selector = config.selector;
                config = config.config
            }

            config = $.Object.extend({}, defaults, config);

            var $this = $(selector).treeview(config);

            var meta = {
                $this: $this
            };

            mapper.set(this, meta);

        }


        TreeView.prototype = { // 实例方法

            constructor: TreeView,

            on: function (name, fn) {
                var meta = mapper.get(this);
                var $this = meta.$this;

                if ($.Object.isPlain(name)) {
                    for (var item in name) {
                        $this.on(item.key, item.value);
                    }
                    return;
                }

                $this.on(name, fn);
            },

            getTrueZTree: function () {
                // 获取原始控件
                var meta = mapper.get(this);
                return meta.$this;
            },
            getNodeByParam: function () {
                return invoke(this, 'getNodeByParam', arguments);
            },
            getSelectedNodes: function () {
                return invoke(this, 'getSelectedNodes', arguments);
            },
            selectNode: function () {
                return invoke(this, 'selectNode', arguments);
            },
            toggleNodeSelected: function () {
                return invoke(this, 'toggleNodeSelected', arguments);
            },
            selectNode: function () {
                return invoke(this, 'selectNode', arguments);
            },
            search: function () {
                return invoke(this, 'search', arguments);
            }
        };


        return {

            use: function (fn) {

                Seajs.use([
                    'bootstrap-treeview-js'
                ], function () {
                    fn && fn(TreeView);
                });

            },


            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };

    });

    /**
     * bootstrap-FileInput
     *
     * @author yadda
     */
    define('FileInput', function (require, exports, module) {

        var $ = require('$');
        var Seajs = require('Seajs');

        var mapper = new $.Mapper();

        // 默认配置
        var defaults = {};

        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);
            var $selector = meta.$selector;

            var args = [].slice.call($argumetns, 0);

            return $selector.fileinput(name, args);

        }


        /**
         * 构造函数。
         */
        function FileInput(selector, config) {

            if ($.Object.isPlain(selector)) { // 重载 ( config )
                config = selector;
                selector = config.selector;
                delete config.selector; // 删除，避免对原始造成不可知的副作用
            }

            config = $.Object.extend({}, defaults, config);

            var $this = $(selector).fileinput(config);

            var meta = {
                $this: $this,
                $selector: $(selector),
                $config: config
            };

            mapper.set(this, meta);

        }


        FileInput.prototype = { // 实例方法

            constructor: FileInput,

            on: function (name, fn) {
                var meta = mapper.get(this);
                var $selector = meta.$selector;

                if ($.Object.isPlain(name)) {
                    for (var item in name) {
                        $selector.on(item.key, item.value);
                    }
                    return;
                }

                $selector.on(name, fn);

                return this;
            },

            disable: function () {
                return invoke(this, 'disable', arguments);
            },
            enable: function () {
                return invoke(this, 'enable', arguments);
            },
            lock: function () {
                return invoke(this, 'lock', arguments);
            },
            unlock: function () {
                return invoke(this, 'unlock', arguments);
            },
            upload: function () {
                return invoke(this, 'upload', arguments);
            },
            refresh: function () {
                return invoke(this, 'refresh', arguments);
            },
            reset: function () {
                return invoke(this, 'reset', arguments);
            },
            clear: function () {
                return invoke(this, 'clear', arguments);
            },
            destroy: function () {
                return invoke(this, 'destroy', arguments);
            },
            getFilesCount: function () {
                return invoke(this, 'getFilesCount', arguments);
            },
            render: function () {
                var meta = mapper.get(this);
                var $selector = meta.$selector;
                var $config = meta.$config;
                $selector.fileinput($config);
            }
        };


        return {

            use: function (fn) {

                Seajs.use([
                    'bootstrap-fileinput-js',
                    //'bootstrap-fileinput-local-js',
                    'bootstrap-fileinput-css'
                ], function () {
                    fn && fn(FileInput);
                });

            },


            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };

    });
    /**
     * 表体数据模块
     *
     * @author yadda
     */
    define('Grid', function (require, module, exports) {

        var $ = require('$');
        var MiniQuery = require('MiniQuery');
        var Seajs = require('Seajs');

        var mapper = new $.Mapper();
        var guidKey = $.Mapper.getGuidKey();


        // 默认配置
        var defaults = {};

        // 调用原始控件的方法
        function invoke(self, name, $argumetns) {

            var meta = mapper.get(self);

            var bdGrid = meta.grid;

            var args = [].slice.call($argumetns, 0);

            return bdGrid.jqGrid(name, args).trigger('reloadGrid', [{
                page: 1
            }]);

        }

        /**
         * 表格构造器。
         */
        function Grid(gridId) {

            var id = $.String.random();
            this[guidKey] = 'Grid-' + id;

            var emitter = new MiniQuery.Event(this);

            var meta = {
                'grid': $('#' + gridId),
                'deleteRows': [],
                'newId': 2,
                'primaryKey': '',
                'inited': false,
                'curCell': {
                    row: null,
                    col: null
                },
                'oldData': null,
                'initGrid': initGrid,
                'saveGrid': saveGrid,
                'getGridData': getGridData,
                'snapShot': snapShot,
                'getNeedSaveKeys': getNeedSaveKeys,
                'getMustInputFields': getMustInputFields,
                'getPrimaryKey': getPrimaryKey,
                'getShowKeys': getShowKeys,
                'emitter': emitter
            };

            mapper.set(this, meta);

            var self = this;

        }

        function initGrid(cfg, data) {

            var bdGrid = cfg.grid;
            var config = cfg.config;
            var deleteRows = cfg.deleteRows;
            var newId = cfg.newId;
            var primaryKey = cfg.primaryKey;
            var emitter = cfg.emitter;

            // var curRow, curCol;

            if (!data) {
                data = [];
            }
            if (data.length < 1) {
                data.push({
                    id: 'num_1'
                });
            } else {
                // 新增一条记录时候的id
                newId = data.length + 1;
            }

            bdGrid.jqGrid({
                caption: config.caption,
                data: data,
                datatype: 'clientSide', // 'local',
                styleUI: 'Bootstrap',// 设置jqgrid的全局样式为bootstrap样式
                colNames: config.colNames,
                colModel: config.colModel,
                width: config.width,
                height: config.height,
                autoScroll: true,
                cmTemplate: {
                    sortable: false
                },
                rownumbers: config.showRowNumber && true,  // 是否显示grid行号
                cellEdit: true,
                // altRows: true,
                shrinkToFit: false,
                autoScroll: true,
                forceFit: true,
                cellsubmit: 'clientArray',
                loadonce: true,
                triggerAdd: false,

                afterEditCell: function (rowid, cellname, value, iRow, iCol) {
                    //在单元格对应的输入控件加入DOM中触发事件
                    cfg.curCell.row = iRow;
                    cfg.curCell.col = iCol;
                    console.log("afterEditCell");
                    config.fnAfterEditCell && config.fnAfterEditCell(rowid, cellname, value, iRow, iCol);
                    emitter.fire('afterEditCell', [config.classId, rowid, cellname, value, iRow, iCol]);
                },

                afterSaveCell: function (rowid, cellname, value, iRow, iCol) {
                    //单元格成功保存后触发
                    console.log("afterSaveCell");
                    config.fnAfterSaveCell && config.fnAfterSaveCell(rowid, cellname, value, iRow, iCol);
                    emitter.fire('afterSaveCell', [config.classId, rowid, cellname, value, iRow, iCol]);
                },
                beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
                    //在单元格切换到编辑模式前触发事件
                    emitter.fire('beforeEditCell', [config.classId, rowid, cellname, value, iRow, iCol, config.colModel[cellname]]);
                },

                loadComplete: function (data) {
                    config.fnLoadComplete && config.fnLoadComplete(data);
                    emitter.fire('loadComplete', [config.classId, data]);
                }
            });

            // 添加行
            bdGrid.on('click', '.ui-icon-plus', function (e) {

                saveGrid(bdGrid, cfg.curCell);

                var rowId = $(this).parent().data('id');

                var dataRow = {
                    id: 'num_' + newId
                };

                var su = bdGrid.jqGrid('addRowData', 'num_' + newId, dataRow, 'after', rowId);

                if (su) {
                    $(this).parents('td').removeAttr('class');
                    $(this).parents('tr').removeClass('selected-row ui-state-hover');
                    bdGrid.jqGrid('resetSelection');
                    newId++;
                }
            });

            // 删除行
            bdGrid.on('click', '.ui-icon-trash', function (e) {

                saveGrid(bdGrid, cfg.curCell);

                if (bdGrid.children('tbody').children('tr').length === 2) {
                    // grid 标题行占一行，如果只有两行，删除一条分录后即没有分录了 ，此时新增一条空分录

                    var rowId = $(this).parent().data('id');
                    var row = bdGrid.jqGrid('getRowData', rowId);

                    if (row[primaryKey]) {
                        deleteRows.push(row);
                    }

                    bdGrid.jqGrid("clearGridData");
                    //新增一条空分录
                    bdGrid.jqGrid('addRowData', 'num_1', {
                        id: 'num_1'
                    });

                    return false;
                }

                var rowId = $(this).parent().data('id');
                var row = bdGrid.jqGrid('getRowData', rowId);
                var su = bdGrid.jqGrid('delRowData', rowId);

                if (su && row[primaryKey]) {
                    // 编辑单据时记录删除的记录id
                    deleteRows.push(row);
                }

            });

            // 取消分录编辑状态
            $(document).bind('click.cancel', function (e) {
                if (!$(e.target).closest(".ui-jqgrid-bdiv").length > 0) {
                    saveGrid(bdGrid, cfg.curCell);
                }
            });

            // F7 按钮图标-选择资料 ui-icon-ellipsis
            bdGrid.on('click', '.ui-icon-ellipsis', function (e) {

                var meta = mapper.get(this);

                console.log("ellipsis");
                var $container = $(this).next();
                var gridRow = bdGrid.jqGrid('getGridParam');
                var rowNumb = gridRow.selrow;
                var colModels = gridRow.colModel;
                // var colNumb = cfg.curCell.col;
                var colNumb = gridRow.iCol;

                // showF7(colModels[colNumb].data, emitter, $container,
                // rowNumb, colNumb, colModels);

                showF7({
                    field: colModels[colNumb].fieldInfo,
                    cfg: cfg,
                    container: $container,
                    rowNumb: rowNumb,
                    colNumb: colNumb,
                    colModels: colModels
                });

            });

            // F7快捷键
            bdGrid.on('keyup', '.f7-icon-ellipsis', function (e) {
                if (e.keyCode === 118) {
                    // F7
                    $(bdGrid).parent().find('.ui-icon-ellipsis').trigger("click");
                }
            });
            // F7快捷键
            bdGrid.on('dblclick', '.f7-icon-ellipsis', function (e) {
                //F7 双击
                $(bdGrid).parent().find('.ui-icon-ellipsis').trigger("click");
            });
            //默认的单元格点击行为
            bdGrid.on('click', '.txt_context', function (e) {

                var rowId = $(this).parent().data('id');
                var row = bdGrid.jqGrid('getRowData', rowId);

                var gridRow = bdGrid.jqGrid('getGridParam');
                var colModels = gridRow.colModel;
                var colNumb = gridRow.iCol;

                var field = colModels[colNumb].fieldInfo;

                if (row[field.key + '_NmbName']) {
                    $(this).val(row[field.key + '_NmbName']);
                }

                // 选中内容
                /*this.onfocus();
                this.select();*/
            });
        }

        function showF7(field, cfg, container, rowNumb, colNumb, colModels) {

            if (typeof field === 'object') {
                // 重载方法
                var params = field;
                field = params.field;
                cfg = params.cfg;
                container = params.container;
                rowNumb = params.rowNumb;
                colNumb = params.colNumb;
                colModels = params.colModels;

            }

            var formClassID = field.lookUpClassId;
            var url = $.Url.setQueryString('./html/list/index.html', 'classId', formClassID);

            var condition = cfg.config.getConditions && cfg.config.getConditions(field.classId, field.lookUpClassId, field.key) || [];

            var title = field.name || '';

            SMS.use('Dialog', function (Dialog) {
                var dialog = new Dialog({
                    title: title,
                    url: url,
                    width: 1100,
                    height: 600,
                    button: [{
                        value: '取消',
                        className: 'sms-cancel-btn'
                    }, {
                        value: '确认',
                        className: 'sms-submit-btn',
                        callback: function () {
                            this.isSubmit = true;
                        }
                    }],

                    data: {
                        multiSelect: false,
                        hasBreadcrumbs: false,
                        pageSize: 10,
                        conditions: condition
                    }
                });

                // 默认关闭行为为不提交
                dialog.isSubmit = false;

                dialog.showModal();

                dialog.on({
                    remove: function () {

                        var data = dialog.getData();

                        if (dialog.isSubmit && data[0].hasOwnProperty('id')) {

                            var gridRow = cfg.grid.jqGrid('getGridParam');
                            var row = gridRow.selrow;
                            var col = gridRow.iCol;


                            // 真实的key-保存的内码
                            var idModel = cfg.grid.getColProp(field.key)

                            if (idModel) {
                                //cfg.grid.setCell(row, idModel.name, data[0].id);
                                cfg.grid.setCell(row, idModel.name, data[0].all[field.srcField]);
                            }

                            /*                            // 显示的名称
                                                        idModel = cfg.grid.getColProp(field.key + '_DspName');
                                                        if (idModel.name) {
                                                            cfg.grid.setCell(row, idModel.name, data[0].all[field.displayField]);
                                                        }
                            
                                                        // 显示的扩展属性
                                                        idModel = cfg.grid.getColProp(field.key + '_NmbName');
                            
                                                        if (idModel.name) {
                                                            cfg.grid.setCell(row, idModel.name, data[0].all[field.displayExt]);
                                                        }*/

                            // 显示字段
                            $(container).val(data[0].all[field.displayField]);

                            $(container).on('focus', function (e) {
                                var self = this;
                                if (data) {
                                    if (data[0].all[field.displayExt]) {
                                        self.value = data[0].all[field.displayExt];
                                    }
                                }
                            });

                            $(container).on('blur', function (e) {
                                var self = this;
                                if (data) {
                                    if (data[0].all[field.displayField]) {
                                        self.value = data[0].all[field.displayField];
                                    }
                                }
                            });

                        }
                    }
                });
            });
        }

        function saveGrid(bdGrid, curCell) {
            if (curCell && curCell.row != null && curCell.col != null) {
                bdGrid.jqGrid("saveCell", curCell.row, curCell.col);
                curCell.row = null;
                curCell.col = null;
            }
        }

        function snapShot(bdGrid) {
            // 原始数据
            var data = {};

            var cols = bdGrid.jqGrid('getGridParam', 'colModel');
            var ids = bdGrid.jqGrid('getDataIDs');

            for (var i = 0, len = ids.length; i < len; i++) {
                var id = ids[i];
                var row = bdGrid.jqGrid('getRowData', id);

                for (var j = 0, l = cols.length; j < l; j++) {
                    var key = cols[j]['name'];
                    if (key != 'rn' && key != 'bos_modify' && key != 'operate') {
                        data[id + '$' + cols[j]['name']] = row[cols[j]['name']];
                    }
                }
            }
            console.dir(data);
            return data;
        }

        /**
         * 模板中配置的数据库需要保存的字段key
         *
         * @param metaData  单据模板
         * @param entryIndex page(0:主表1:第一个子表2:第二个子表...)
         * @returns {Array} 模板配置的需要保存的字段key
         */
        function getNeedSaveKeys(metaData, entryIndex) {
            var keys = [];

            var fields = metaData['formFields'][entryIndex];
            for (var item in fields) {
                var field = fields[item];
                if (field['needSave']) {
                    keys[field['key']] = field;
                }
            }

            return keys;
        }

        /**
         * 模板中配置的数据库必录字段key(按照tab顺序排序)
         *
         * @param metaData  单据模板
         * @param entryIndex page(0:主表1:第一个子表2:第二个子表...)
         * @returns {Array} 模板配置的必录字段key
         */
        function getMustInputFields(metaData, entryIndex) {
            var keys = [];

            var fields = metaData['formFields'][entryIndex];
            for (var item in fields) {
                var field = fields[item];
                if (field['mustInput']) {
                    keys.push(field);
                }
            }

            keys = sortFields(keys);

            return keys;

            function sortFields(fields) {
                for (var i = 0; i < fields.length; i++) {
                    for (var j = i + 1; j < fields.length; j++) {
                        if (fields[i].tabIndex > fields[j].tabIndex) {
                            var tmp = fields[i];
                            fields[i] = fields[j];
                            fields[j] = tmp;
                        }
                    }
                }
                return fields;
            }
        }

        /**
         * 模板配置的单据主键key
         * @param metaData 单据模板
         * @param entryIndex  page(0:主表1:第一个子表2:第二个子表...)
         * @returns 主键key
         */
        function getPrimaryKey(metaData, entryIndex) {
            var entry = metaData['formClassEntry'][entryIndex];
            return entry['primaryKey'];
        }

        function getShowKeys(config, metaData, entryIndex) {

            var fields = metaData['formFields'][entryIndex];

            var displayKeys = [];
            var display = 0;

            // 当前单据操作类别 0：查看 1：新增 2：修改
            var showType = config.showType || -1;
            // 用户角色类别
            var userRoleType = config.userRoleType || -1;

            /*        1	查看时对于平台用户显示
                    2	新增时对于平台用户显示
                    4	编辑时对于平台用户显示
                    8	查看时对于供应商用户显示
                    16	新增时对于供应商用户显示
                    32	编辑时对于供应商用户显示
                    64	查看时对于医院用户显示
                    128	新增时对于医院用户显示
                    256	编辑时对于医院用户显示
                    512	是否在列表中显示(子表模板独有,子表数据显示在表头列表中)*/

            if (showType === 0) {
                // 查看
                if (userRoleType === 1) {
                    // 平台用户
                    display = 1;
                } else if (userRoleType === 2) {
                    //医院用户
                    display = 64;
                } else if (userRoleType === 3) {
                    //供应商用户
                    display = 8;
                }
            } else if (showType === 1) {
                // 新增

                if (userRoleType === 1) {
                    // 平台用户
                    display = 2;
                } else if (userRoleType === 2) {
                    //医院用户
                    display = 128;
                } else if (userRoleType === 3) {
                    //供应商用户
                    display = 16;
                }
            } else if (showType === 2) {
                // 编辑
                if (userRoleType === 1) {
                    // 平台用户
                    display = 4;
                } else if (userRoleType === 2) {
                    //医院用户
                    display = 256;
                } else if (userRoleType === 3) {
                    //供应商用户
                    display = 32;
                }
            }

            for (var key in fields) {

                var field = fields[key];

                if (!!(field.display & display)) {
                    // 字段需要显示在页面
                    displayKeys.push(key);
                }
            }

            return displayKeys;
        }

        /**
         * 获取表格数据
         * @param bdGrid
         * @param metaData  单据模板
         * @param needSaveKeys 需要保存的key
         * @param mustInputFields 必录项key
         * @param showKeys 界面需要显示的key
         * @param showType 当前的单据模式 0:1:2-查看/新增/编辑
         * @returns {Array} 单据数据
         */
        function getGridData(bdGrid, metaData, needSaveKeys, mustInputFields, showKeys, showType) {

            var gridData = [];
            var ids = bdGrid.jqGrid('getDataIDs');

            for (var i = 0, len = ids.length; i < len; i++) {

                var id = ids[i];
                var row = bdGrid.jqGrid('getRowData', id);

                var valid = true;
                var mustInputCaptions = [];

                // 必录项校验
                for (var validIndex in mustInputFields) {


                    if (mustInputFields[validIndex].ctrlType === 6) {

                        if (row[mustInputFields[validIndex].key] === '') {
                            // 查找类型，如果删除了界面显示的值，认为是没有值
                            var caption = mustInputFields[validIndex].name;
                            mustInputCaptions.push(caption);
                            valid = false;
                            continue;
                        }


                    }

                    if (row[mustInputFields[validIndex].key] == '') {
                        var caption = mustInputFields[validIndex].name;
                        mustInputCaptions.push(caption);
                        valid = false;
                    }
                }

                var itemData = {};

                // 该行记录有值时，需要提示必录项
                var hasValue = false;
                // 打包表格行数据(只需要打包needSave=true的字段)
                for (var key in needSaveKeys) {

                    var field = needSaveKeys[key];

                    if (row[key] !== '') {
                        itemData[key] = row[key];
                        hasValue = true;
                    } else {
                        // 模板配置的默认值处理
                        var dValue = needSaveKeys[key].defaultValue || '';


                        if (dValue === '') {

                            if (!$.Array.contains(showKeys, field.key) && (showType === 1 || showType === 2)) {
                                // 新增、编辑时界面不需要显示的key-通常不需要提交，由后台设置，
                                // 如日期时间，状态等字段不是在新增时设置的
                                continue;
                            }

                        }

                        itemData[key] = dValue;
                    }
                }

                // 当为空行时，跳过无效分录
                if (!hasValue) {
                    continue;
                }

                // 有效行但有必录项未录
                if (!valid && hasValue) {
                    itemData.bos_mustInput = 'true';
                    itemData.bos_mustInputCaptions = mustInputCaptions;
                }

                // 修改标记
                itemData.bos_modify = row.bos_modify;

                // 修改需要全部数据，不只是要主键
                gridData.push(itemData);
                // gridData.push(row);
            }

            return gridData;
        }

        function getInt(str) {
            return str.match(/\d/g).join("");
        }

        // 表格实例方法
        Grid.prototype = {

            constructor: Grid,

            /*
             * config.editColumnName config.colNames config.colModel
             * config.width config.height config.fnAfterEditCell(rowid,
             * cellname, value, iRow, iCol)
             */
            render: function (config, entryData, metaData, entryIndex) {

                var meta = mapper.get(this);

                if (!entryIndex) {
                    // 默认第一个子表
                    entryIndex = 1;
                }

                meta.config = config;
                meta.metaData = metaData;
                meta.primaryKey = meta.getPrimaryKey(meta.metaData, entryIndex);

                if (meta.inited) {
                    // grid已经渲染，此处重新加载数据
                    meta.grid.jqGrid('setGridParam', {
                        data: entryData[entryIndex]
                    }).trigger('reloadGrid', [{
                        page: 1
                    }]);
                    // 保存一份原始数据
                    meta.oldData = meta.snapShot(meta.grid);
                    return;
                }

                if (entryData) {
                    meta.initGrid(meta, entryData[entryIndex]);
                }

                if (!entryData) {
                    meta.initGrid(meta);
                }
                meta.inited = true;
                // 保存一份原始数据
                meta.oldData = meta.snapShot(meta.grid);
            },

            setData: function (data, page) {
                var meta = mapper.get(this);
                var bdGrid = meta.grid;
                bdGrid.jqGrid('setGridParam', {
                    data: data
                }).trigger('reloadGrid', [{
                    page: page
                }]);
                meta.oldData = meta.snapShot(meta.grid);
            },
            setGridParam: function () {
                return invoke(this, 'setGridParam', arguments).trigger('reloadGrid', [{
                    page: 1
                }]);
            },
            clear: function () {
                var meta = mapper.get(this);

                var bdGrid = meta.grid;

                bdGrid.jqGrid("clearGridData");
                bdGrid.jqGrid('addRowData', 'num_1', {
                    id: 'num_1'
                });
                meta.oldData = meta.snapShot(meta.grid);
            },
            save: function () {
                var meta = mapper.get(this);

                meta.saveGrid(meta.grid, meta.curCell);
            },
            clearDeleteRows: function () {
                var meta = mapper.get(this);

                meta.deleteRows.splice(0, meta.deleteRows.length);
            },
            getGridDatas: function (entryIndex) {
                // 获取grid数据
                var meta = mapper.get(this);

                meta.saveGrid(meta.grid, meta.curCell);
                // 需要保存到数据库的字段
                var needSaveKeys = meta.getNeedSaveKeys(meta.metaData, entryIndex);
                // 必录项字段
                var mustInputFields = meta.getMustInputFields(meta.metaData, entryIndex);
                // 界面需要展示的字段
                var showKeys = meta.getShowKeys(meta.config, meta.metaData, entryIndex);
                var primaryKey = meta.primaryKey;

                var gridData = meta.getGridData(meta.grid, meta.metaData, needSaveKeys, mustInputFields, showKeys, meta.config.showType);
                var deletedData = meta.deleteRows;

                var addDatas = [];
                var deleteDatas = [];
                var updateDatas = [];
                var errorDatas = {};
                /* 删除数据获取 */
                for (var index in deletedData) {
                    var row = deletedData[index];
                    // deleteData[primaryKey] = row,
                    // updateDatas.push(deleteData);
                    deleteDatas.push(row);
                }

                /* 错误数据验证 */
                for (var index in gridData) {
                    var row = gridData[index];
                    console.log(row);
                    if (row.bos_mustInput === 'true') {
                        // 有必录项未赋值
                        var errorData = [];
                        for (var captionIndex in row.bos_mustInputCaptions) {
                            if (!$.Array.contains(errorDatas, row.bos_mustInputCaptions[captionIndex])) {
                                errorData.push(row.bos_mustInputCaptions[captionIndex]);
                            }
                        }
                        errorDatas[++index] = errorData;
                    }

                    // 添加数据获取 [primaryKey]数据主键为空或为0(主键用long)表示新增数据
                    if (!row[primaryKey] || parseInt(row[primaryKey]) === 0) {
                        addDatas.push(row);
                    }

                    /* 修改数据获取 [primaryKey]不为空表示修改数据 */
                    if (!!row[primaryKey] && parseInt(row[primaryKey]) > 0) {
                        updateDatas.push(row);
                    }

                }

                return {
                    'add': addDatas,
                    'delete': deleteDatas,
                    'update': updateDatas,
                    'error': errorDatas,
                };
            },
            isGridChanged: function () {
                var meta = mapper.get(this);
                var curData = meta.snapShot(meta.grid);

                if ($.Object.getKeys(curData).length != $.Object.getKeys(meta.oldData).length) {
                    return true;
                }
                for (var key in curData) {
                    if (curData[key] != meta.oldData[key]) {
                        console.log('old:   ' + key + ' ' + meta.oldData[key]);
                        console.log('new:   ' + key + ' ' + curData[key]);
                        return true;
                    }
                }

                return false;
            },
            tableOperate: function (val, opt, row) {
                var html_con = '<div class="operating" data-id="' + opt.rowId + '"><span class="ui-icon ui-icon-plus" title="新增行"></span><span class="ui-icon ui-icon-trash" title="删除行"></span></div>';
                return html_con;
            },
            setRowData: function (row, data) {
                var x = $('#initCombo').data('selectedVal' + row);
                var meta = mapper.get(this);
                var gridData = data;
                for (var d in data) {
                    if (typeof data[d] == 'undefined') {
                        gridData = x;
                        break;
                    }
                }
                meta.grid.jqGrid('setRowData', row, gridData);
            },
            setCell: function (rowid, colname, data, cssClass, properties) {
                var meta = mapper.get(this);
                // 设置单元格的值-调用原始控件方法
                meta.grid.jqGrid('setCell', rowid, colname, data, cssClass, properties);

            },
            getCell: function (rowid, iCol) {
                var meta = mapper.get(this);
                // 设置单元格的值-调用原始控件方法
                return meta.grid.jqGrid('getCell', rowid, iCol);

            },
            editCell: function (iRow, iCol, edit) {
                // 设置单元格为编辑状态
                var meta = mapper.get(this);
                var pos;
                var colModel = meta.grid.jqGrid('getGridParam', 'colModel');

                if (isNaN(iCol)) {
                    $(colModel).each(function (i) {

                        if (this.name === iCol) {
                            pos = i;
                            return false;
                        }
                    });
                } else {
                    pos = parseInt(iCol, 10);
                }

                meta.grid.jqGrid('editCell', iRow, pos, edit);
            },
            getColProp: function (name) {
                var meta = mapper.get(this);
                // 返回指定列的属性集合。name为colModel中名称-调用原始控件方法
                return meta.grid.jqGrid('getColProp', name);
            },
            on: function (name, fn) {

                var meta = mapper.get(this);
                var emitter = meta.emitter;
                var args = [].slice.call(arguments, 0);

                emitter.on.apply(emitter, args);
            },
            setGridWidth: function (size) {

                var meta = mapper.get(this);
                var bdGrid = meta.grid;
                bdGrid.jqGrid('setGridWidth', size);
            },
            unload: function () {
                var meta = mapper.get(this);
                return meta.grid.jqGrid('GridUnload', meta.grid);
            },
            destroy: function () {
                var meta = mapper.get(this);
                return meta.grid.jqGrid('GridDestroy', meta.grid);
            }
        };

        // 静态方法
        // return Grid;

        return {

            use: function (fn) {
                // 4.5.4
                /*                Seajs.use([
                                    'grid-locale-cn-js',
                                    'grid-base-js',
                                    'jquery-combo-js',
                                    'jqgrid-css',
                                    'ui-css',
                                    'common-css'
                                ], function () {
                                    fn && fn(Grid);
                                });*/

                // 5.1.0-all

                Seajs.use([
                    'grid.locale-cn-51-js',
                    'jqgrid-all-js',
                    'ui.jqgrid-bootstrap-ui-css',
                    'ui.jqgrid-bootstrap-css',
                    'ui.jqgrid-css'
                ], function () {
                    fn && fn(Grid);
                });


            },


            config: function (obj) {
                // get
                if (arguments.length == 0) {
                    return defaults;
                }
                // set
                $.Object.extend(defaults, obj);
            },

        };


    });


    // 设置 MiniQuery 对外挂靠的别名
    var MiniQuery = require('MiniQuery');
    // 将MiniQuery挂靠到$下，这样就可以通过$.xxx引用到MiniQuery功能
    MiniQuery.use('$');

    // 设置对外暴露的模块
    Module.expose({

        'Module': true, // 注意，这个是页面级别的 Module
        'Cache': true,
        'Debug': true,
        'File': true,
        'Url': true,
        'Multitask': true,
        'Tree': true,
        'MD5': true,
        'Seajs': true,

        // api
        'Proxy': true,
        'API': true,

        // ui
        'Iframe': true,
        'IframeManager': false, // 这个不要在这里暴露，因为它通过 Iframe 暴露了
        'Samples': true, // for test for warehouse' SMS.CascadeMenus.js

        'ButtonList': true,
        'CascadeMenus': false,
        'CascadeNavigator': true,
        'CascadePath': false,
        'CascadePicker': true,
        'Dialog': true,
        'Loading': true,
        'Login': true,
        'Pager': true,
        'SimplePager': true,
        'Tabs': true,
        'Template': true,
        'Tips': true,
        'MessageBox': true,
        'Pagers': true,
        'NumberField': true,
        'DateTimePicker': true,
        'ZTree': true,
        'Grid': true,
        'BarCode': true,
        'TreeView': true,
        'FileInput': true
    });


    var SMS = {

        // 快捷方式
        require: function (id) {
            return Module.expose(id) ? Module.require(id) : null;
        },

        use: function (id, fn) {

            var module = SMS.require(id);
            if (!module) {
                fn && fn(null);
                return;
            }

            var use = module.use;
            if (use) {
                use(fn);
            }
            else {
                fn && fn(module);
            }
        },

        config: require('Config').set,

        // crypto
        'MD5': require('MD5'),
        'Cache': require('Cache'),
        'Debug': require('Debug'),
        'File': require('File'),
        'Url': require('Url'),
        // 'Seajs': require('Seajs'),
        // api
        'Proxy': require('Proxy'),
        'API': require('API'),
        // ui
        'Pages': require('Pages'),
        'CascadePicker': require('CascadePicker'),
        'Dialog': require('Dialog'),
        'Loading': require('Loading'),
        'Login': require('Login'),
        'Pager': require('Pager'),
        'SimplePager': require('SimplePager'),
        'Tabs': require('Tabs'),
        'Template': require('Template'),
        'Tips': require('Tips'),
        'MessageBox': require('Tips'),
        'Pagers': require('Pagers'),
        'Grid': require('Grid'),
        'TreeView': require('TreeView'),
        'FileInput': require('FileInput')
    };


    // 暴露
    if (typeof global.define == 'function' && (global.define.cmd || global.define.amd)) {
        // cmd 或 amd
        global.define(function (require) {
            return SMS;
        });
    }
    else { // browser
        global.SMS = SMS;
    }


})(
    this,

    top,
    parent,
    window,
    document,
    location,
    localStorage,
    sessionStorage,
    console,
    history,
    setTimeout,
    setInterval,
    $,
    jQuery,
    MiniQuery,


    Array,
    Boolean,
    Date,
    Error,
    Function,
    Math,
    Number,
    Object,
    RegExp,
    String
    /* , undefined */
);
// 结束文件 SMS.debug.js
// <================================================================================================================
