module.exports = function (grunt) {

    /*
     * 安装插件 -并保存依赖到package.json
     * npm install grunt --save-dev
     * npm install grunt-contrib-clean --save-dev
     * npm install grunt-contrib-copy --save-dev
     * npm install grunt-contrib-uglify --save-dev
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

        clean: {
            src: 'build/'
        },

        copy: {
            src: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= path.src %>/',
                        src: ['**/*'],
                        dest: '<%= path.dest %>'
                    }
                ]
            }
        },

        //uglify压缩插件配置信息
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
                //压缩src目录及所有子目录下的js文件
                src: ['<%= path.src %>/**/*.js', '!<%= path.src %>/**/*min.js'],
                //压缩文件存放到build 目录下的同名目录
                dest: 'build',
                //压缩文件的后缀名
                ext: '.min.js'
            }
        }
    });

    // 加载clean插件
    grunt.loadNpmTasks('grunt-contrib-clean');
    // 加载copy插件
    grunt.loadNpmTasks('grunt-contrib-copy');
    // 加载uglify插件
    grunt.loadNpmTasks('grunt-contrib-uglify');

    // 告诉grunt在终端输入grunt时做什么任务(默认任务)任务按顺序执行
    grunt.registerTask('default', ['clean', 'copy', 'uglify']);
};