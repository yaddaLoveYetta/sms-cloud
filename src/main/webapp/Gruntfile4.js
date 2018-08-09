module.exports = function (grunt) {

    var path = {
        src: 'htdocs',
        dest: 'build',
        tmp: '.tmp'
    };

    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),

        path: path,

        clean: {
            src: 'build/'
        },

        copy: {
            html: {
                files: [{
                    expand: true, //启动动态扩展,保留目录结构
                    cwd: '<%= path.src %>/', //src相对路径(从哪个目录复制)
                    src: ['**/*'],  //复制哪些文件
                    dest: '<%= path.dest %>/' //复制到哪个目录
                }]
            }
        },

        useminPrepare: {

            build: {
                files: [{
                    src: '<%= path.src %>/**/index.html'
                }
                ]
            }
        },

        uglify: {
            buildrelease: {
                options: {
                    report: "min" //输出压缩率
                }
            }
        },

        usemin: {
            html: {
                files: [{
                    src: '<%= path.dest %>/**/index.html'
                }
                ]
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-usemin');

    grunt.registerTask('default', ['clean', 'copy', 'usemin']);

};