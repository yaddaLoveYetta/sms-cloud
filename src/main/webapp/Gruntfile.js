module.exports = function (grunt) {

    /*
     * npm i grunt grunt-cli -g //安装grunt到全局
     * npm i less@latest -g //安装less到全局
     * 安装插件 -并保存依赖到package.json
     * npm install grunt --save-dev
     * npm install grunt-contrib-clean --save-dev   // 清理
     * npm install grunt-contrib-copy --save-dev    // 复制
     * npm install grunt-contrib-concat --save-dev    // 合并
     * npm install grunt-contrib-uglify --save-dev //  js压缩
     * npm install grunt-contrib-cssmin --save-dev // css压缩
     * npm install grunt-filerev --save-dev // 文件重命名
     * npm install grunt-usemin --save-dev // 修改html对js，css的引用
    */

    var path = {
        src: 'htdocs',
        dest: 'build',
        tmp: '.tmp'
    };

    // 所有插件的配置信息
    grunt.initConfig({

        // 读取package.json文件信息
        pkg: grunt.file.readJSON('package.json'),

        path: path,

        //清空生产文件夹
        clean: {
            beforeBuild: {
                files: [{
                    src: ['<%= path.dest %>/']
                }
                ]
            }
        },

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
                        dest: '<%= path.dest %>/'
                    }
                ]
            }
        },

        concat: {
            options: {
                separator: ';'
            },
            dist: {
                src: ['src/zepto.js', 'src/underscore.js', 'src/backbone.js'],
                dest: 'dest/libs.js'
            }
        },

        //uglify压缩js插件配置信息
        uglify: {

            options: {
                // 去掉注释
                stripBanners: true,
                // 在压缩文件头部加上注释
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> start... */\n',
                //在压缩文件底部加上注释
                footer: '\n/*! <%= pkg.name %> 最后修改于： <%= grunt.template.today("yyyy-mm-dd") %> */',
                //不混淆函数名和变量名 默认会混淆
                mangle: false,
                //输出压缩率，可选的值有 false(不输出信息)，gzip
                report: "min",
                //不删除注释，还可以为 false（删除全部注释），some（保留@preserve @license @cc_on等注释）
                preserveComments: 'all'

            },

            // 批量压缩
            batch: {
                //将占位符*展开 即使用占位符匹配文件名
                expand: true,
                cwd: '<%= path.dest %>/',
                //压缩src目录及所有子目录下的js文件
                src: ['**/*.js', '!**/*min.js'],
                //压缩文件存放到build 目录下的同名目录
                dest: '<%= path.dest %>/',
                //压缩文件的后缀名
                ext: '.min.js'
            }
        },

        //css压缩插件配置信息
        cssmin: {
            build: {
                files: [{
                    //为true启用cwd,src,dest选项
                    expand: true,
                    //所有src指定的匹配都将相对于此处指定的路径（但不包括此路径）
                    cwd: '<%= path.dest %>/',
                    //相对于cwd路径的匹配模式。意思就是 src/**/*.css，匹配src下面所有文件
                    src: ['**/*.css', '!**/*min.css'],
                    //目标文件路径前缀。
                    dest: '<%= path.dest %>/',
                    //压缩文件的后缀名
                    ext: '.min.css'
                }
                ]
            }
        },

        //对css和js文件重命名
        filerev: {
            build: {
                files: [{
                    src: ['<%= path.dest %>/**',
                        '!<%= path.dest %>/**/*.html',//html文件不加版本号
                        '!<%= path.dest %>/**/*.{png,jpg,jpeg}']//图片 不需要加版本号
                }
                ]
            }
        },
        //替换文件前准备
        useminPrepare: {
            html: '<%= path.dest %/**/*.html'
            /*            options: {
                            dest: './<%= path.dest %>',
                            root: './<%= path.dest %>'
                        }*/
        },

        //修改html中的css和js引用
        usemin: {
            html: {
                files: [{
                    src: '<%= path.dest %>/**/*.html'
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

    // 告诉grunt在终端输入grunt时做什么任务(默认任务)任务按顺序执行
    //grunt.registerTask('default', ['clean', 'copy', 'useminPrepare', 'usemin']);
    grunt.registerTask('default', ['clean', 'copy', 'uglify', 'cssmin', 'useminPrepare', 'usemin']);
};