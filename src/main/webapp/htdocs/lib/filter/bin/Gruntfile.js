
module.exports = function(grunt) {

    grunt.initConfig({
        concat: {
            dist: {
                src: [ '../html/js/DatetimePicker.js', '../html/js/FilterData.js', '../html/js/FilterOptions.js',
                    '../html/js/TimeZone.js', '../html/js/Path.js', '../html/js/Filter.js', '../html/js/Notification.js', '../html/login.js'],
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

    //1.��htmlת���ַ��� 2.ȥ���ַ�����ͷ��β 3.�ϲ�����jsģ�� 4.�ѿؼ�ģ���html�ַ������ϲ���ģ��js�滻�� 5.�ſ��׳��¼���ע��
    grunt.registerTask('default', ['html2js', 'replace:html', 'concat', 'replace:htmlandmodule', 'replace:filterEvents']);

}