module.exports = function (grunt) {

    var path = {
        src: 'htdocs',
        dest: 'build',
        tmp: '.tmp'
    };

    grunt.initConfig({

        path: path,

        //清空生产文件夹
        clean: {
            beforeBuild: {
                files: [{
                    src: ['<%= path.dest %>/', '<%= path.tmp %>/']
                }
                ]
            },
            afterBuild: {
                files: [{
                    src: ['<%= path.tmp %>/']
                }
                ]
            }
        },

        // 复制文件，后续合并压缩都在目标文件中进行，不操作源代码文件
        copy: {
            src: {
                files: [
                    {
                        expand: true,
                        // 所有src指定的匹配都将相对于此处指定的路径（但不包括此路径）
                        cwd: '<%= path.src %>/',
                        // 匹配所有文件(复制所有文件)
                        src: ['**/*.*'],
                        // 目标文件路径前缀
                        dest: '<%= path.dest %>/',
                        flatten: false,
                        filter: 'isFile'
                    }
                ]
            }
        },

        // 对css和js文件重命名
        filerev: {
            options: {
                algorithm: 'md5',
                length: 8
            },
            build: {
                files: [{
                    src: [
                        '<%= path.dest %>/**/*min.js',
                        '<%= path.dest %>/**/*min.css',
                        '!<%= path.dest %>/lib/**/*',
                        '!<%= path.dest %>/**/*.html'
                    ]
                }
                ]
            }
        },

        uglify: {
            options: {
                // 不混淆代码
                mangle: true,
                // 可理解为保留换行
                beautify: false,
                // 保留注释相关，接受false、'all'、'some'、Function
                output: {comments: false},
                sourceMap: true,
                // 输出压缩率
                report: "min",
                // 压缩文件头注释
                banner: '/*concat-uglify by yadda use grunt==> begin===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                // 压缩文件尾注释
                footer: '\n/*concat-uglify by yadda use grunt==> end===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                // 压缩
                compress: {
                    // 去除console语句
                    drop_console: true
                }
            },
            build: {
                files: [{
                    expand: true,
                    // 所有src指定的匹配都将相对于此处指定的路径（但不包括此路径）
                    cwd: '<%= path.src %>/',
                    // 匹配所有文件(复制所有文件)
                    src: ['**/*.js', '!**/lib/**/*'],
                    // 目标文件路径前缀
                    dest: '<%= path.dest %>/',
                    ext: '.min.js'
                }]
            }
        },
        //替换文件前准备
        useminPrepare: {

            build: {
                files: [{
                    src: '<%= path.src %>/**/*.html'
                }
                ]
            },
            options: {
                // 定义临时文件夹
                staging: '<%= path.tmp %>/',
                // 定义目标文件夹
                dest: '<%= path.dest %>',
                // 默认是源html所在的文件夹，定义处理的资源文件的相对路径,concat:generated将从root开始找文件
                root: '<%= path.dest %>',
                flow: {
                    build: {
                        steps: {
                            //注意不是uglify，是uglifyjs，下面的post中对应的name为uglify
                            js: ['concat', 'uglifyjs'],
                            css: ['concat', 'cssmin']
                        },
                        post: {
                            js: [{
                                name: 'concat',
                                createConfig: function (context, block) {
                                    //context.options即concat task
                                    var generated = context.options.generated;
                                    generated.options = {
                                        // 合并文件之间的分隔符
                                        separator: ';',
                                        //合并时是否删除源文件头部的注释，默认false
                                        stripBanners: false,
                                        // 合并文件头注释
                                        banner: '/*concat by yadda use grunt==> begin===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        // 合并文件尾注释
                                        footer: '/*concat by yadda use grunt==> end===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        sourceMap: true
                                    };
                                }
                            }, {
                                name: 'uglify',
                                createConfig: function (context, block) {
                                    //context.options即concat task
                                    var generated = context.options.generated;
                                    generated.options = {
                                        // 不混淆代码
                                        mangle: false,
                                        // 可理解为保留换行
                                        beautify: true,
                                        // 保留注释相关，接受false、'all'、'some'、Function
                                        output: {comments: true},
                                        sourceMap: true,
                                        // 输出压缩率
                                        report: "min",
                                        // 压缩文件头注释
                                        banner: '/*concat-uglify by yadda use grunt==> begin===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        // 压缩文件尾注释
                                        footer: '\n/*concat-uglify by yadda use grunt==> end===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        // 压缩
                                        compress: {
                                            // 去除console语句
                                            drop_console: true
                                        }
                                    };
                                }
                            }],
                            css: [{
                                name: 'concat',
                                createConfig: function (context, block) {
                                    //context.options即concat task
                                    var generated = context.options.generated;
                                    generated.options = {
                                        // 合并文件之间的分隔符
                                        separator: ';',
                                        //合并时是否删除源文件头部的注释，默认false
                                        stripBanners: false,
                                        // 合并文件头注释
                                        banner: '/*concat by yadda use grunt==> begin===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        // 合并文件尾注释
                                        footer: '/*concat by yadda use grunt==> end===<%= grunt.template.today("yyyy-mm-dd HH:mm:ss") %>*/\n',
                                        sourceMap: true
                                    };
                                }
                            }, {
                                name: 'cssmin',
                                createConfig: function (context, block) {
                                    //context.options即concat task
                                    var generated = context.options.generated;
                                    generated.options = {
                                        // 输出压缩率
                                        report: "min"
                                    };
                                }
                            }]
                        }
                    }
                }
            }
        },

        usemin: {
            html: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= path.dest %>',
                        src: ['**/*.html'],
                        dest: '<%= path.dest %>'
                    }
                ]
            }
        }
    });

    // 加载clean插件
    grunt.loadNpmTasks('grunt-contrib-clean');
    // 加载copy插件
    grunt.loadNpmTasks('grunt-contrib-copy');
    // 加载concat插件
    grunt.loadNpmTasks('grunt-contrib-concat');
    // 加载uglify插件
    grunt.loadNpmTasks('grunt-contrib-uglify');
    // 加载cssmin插件
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    // 加载filerev插件
    grunt.loadNpmTasks('grunt-filerev');
    // 加载usemin插件
    grunt.loadNpmTasks('grunt-usemin');

    //grunt.registerTask('default', ['clean:beforeBuild', 'copy', 'useminPrepare', 'concat:generated', 'cssmin:generated', 'uglify:generated', 'usemin']);

    //grunt.registerTask('default', ['clean:beforeBuild', 'copy', 'useminPrepare', 'uglify', 'usemin']);

    //grunt.registerTask('default', ['copy', 'uglify', 'filerev', 'usemin']);

    grunt.registerTask('default', ['clean:beforeBuild', 'copy', 'useminPrepare', 'cssmin:generated', 'uglify', 'concat:generated', 'usemin']);

};