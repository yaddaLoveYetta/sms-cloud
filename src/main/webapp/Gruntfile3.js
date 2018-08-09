module.exports = function (grunt) {

    var path = {
        src: 'htdocs',
        dest: 'build',
        tmp: '.tmp'
    };

    grunt.initConfig({

        path: path,

        clean: {
            beforebuild: {
                files: [{
                    src: ['<%= path.dest %>/', '<%= path.tmp %>/']
                }
                ]
            }
        },

        filerev: {
            build: {
                files: [{
                    src: ['<%= path.dest %>/**', '!<%= path.dest %>/index.html']
                }
                ]
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

        usemin: {
            html: {
                files: [{
                    src: '<%= path.dest %>/**/index.html'
                }
                ]
            },
            css: {
                files: [{
                    src: '<%= path.dest %>/**/*.css'
                }
                ]
            }
        },

        copy: {
            build: {
                files: [{
                    expand: true,
                    cwd: '<%= path.src %>/',
                    src: ['**/*'],
                    dest: '<%= path.dest %>/'
                }
                ]
            }
        },

        imagemin: {
            build: {
                files: [{
                    expand: true,
                    cwd: '<%= path.src %>/',
                    src: ['**/*.{jpg, png, jpeg, gif}'],
                    dest: '<%= path.dest %>/'
                }
                ]
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-filerev');
    grunt.loadNpmTasks('grunt-usemin');

    grunt.registerTask('default', ['clean:beforebuild', 'copy', 'useminPrepare', 'concat', 'cssmin', 'uglify', 'filerev', 'usemin']);

};
