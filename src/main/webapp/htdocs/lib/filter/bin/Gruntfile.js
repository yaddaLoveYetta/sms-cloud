
module.exports = function(grunt) {

    grunt.initConfig({
        concat: {
            dist: {
                src: [ '../html/js/DatetimePicker.js', '../html/js/FilterData.js', '../html/js/FilterOptions.js',
                    '../html/js/TimeZone.js', '../html/js/Path.js', '../html/js/Filter.js', '../html/js/Notification.js', '../html/index.js'],
                dest: '../html/Filter.js'
            }
        },
        html2js: {
            options: {
                // custom options, see below
                htmlmin: {
                    collapseWhitespace: true
                },
                quoteChar: '\'',
                singleModule: true,
                rename: function (moduleName) {
                  return '/' + moduleName.replace('.html', '');
                }
            },
            main: {
                src: ['../html/index.html'],
                dest: '../html/build/html.js'
            },
        },
        replace: {
            html: {
                options: {
                    patterns: [
                      {
                          match: /^.*(<body>)/mg,
                          replacement: '\''
                      },
                      {
                          match: /(<script).*/mg,
                          replacement: '\''
                      },
                      {
                          match: /(angular).*/mg,
                          replacement: ''
                      },
                      {
                          match: /(\$templateCache).*/mg,
                          replacement: ''
                      },
                      {
                          match: /(}]\);).*/mg,
                          replacement: ''
                      }
                    ]
                },
                files: [
                  {
                      expand: true,
                      flatten: true,
                      src: ['../html/build/html.js'],
                      dest: '../html/js/'
                  }
                ]
            },
            htmlandmodule: {
                options: {
                    patterns: [
                      {
                          match: 'html-area',
                          replacement: function () {
                              return grunt.file.read('../html/js/html.js');
                          }
                      },
                      {
                          match: 'modules-area',
                          replacement: function () {
                              return grunt.file.read('../html/Filter.js');
                          }
                      }
                    ]
                },
                files: [
                  {
                      
                      src: ['../html/template.js'],
                      dest: '../dest/Filter.js'
                  }
                ]
            },
            filterEvents: {
                options: {
                    patterns: [
                      {
                          match: '//filterEmitter.fire(\'search\', [selectedItems]);',
                          replacement: 'filterEmitter.fire(\'search\', [selectedItems]);'
                      }
                    ],
                    usePrefix: false
                },
                files: [
                  {

                      src: ['../dest/Filter.js'],
                      dest: '../dest/Filter.js'
                  }
                ]
            }

        }
    });


    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-html2js');
    grunt.loadNpmTasks('grunt-replace');

    //1.将html转成字符串 2.去掉字符串的头和尾 3.合并所有js模块 4.把控件模板的html字符串、合并的模块js替换上 5.放开抛出事件的注释
    grunt.registerTask('default', ['html2js', 'replace:html', 'concat', 'replace:htmlandmodule', 'replace:filterEvents']);

}