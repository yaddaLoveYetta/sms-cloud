/**
 * 导航栏模板
 */
define('NavBar', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var emitter = MiniQuery.Event.create();

    // 标识事件绑定
    var hasBind = false;

    /**
     * 绑定事件
     */
    function bindEvents() {

        if (hasBind) {
            return;
        }

        // class
        $(document).on('click', '[data-toggle^="class"]', function (e) {
            e && e.preventDefault();
            var $this = $(e.target), $class, $target, $tmp, $classes, $targets;
            !$this.data('toggle') && ($this = $this.closest('[data-toggle^="class"]'));
            $class = $this.data()['toggle'];
            $target = $this.data('target') || $this.attr('href');
            $class && ($tmp = $class.split(':')[1]) && ($classes = $tmp.split(','));
            $target && ($targets = $target.split(','));
            $classes && $classes.length && $.each($targets, function (index, value) {
                if ($classes[index].indexOf('*') !== -1) {
                    var patt = new RegExp('\\s' +
                        $classes[index].replace(/\*/g, '[A-Za-z0-9-_]+').split(' ').join('\\s|\\s') +
                        '\\s', 'g');
                    $($this).each(function (i, it) {
                        var cn = ' ' + it.className + ' ';
                        while (patt.test(cn)) {
                            cn = cn.replace(patt, ' ');
                        }
                        it.className = $.trim(cn);
                    });
                }
                ($targets[index] != '#') && $($targets[index]).toggleClass($classes[index]) || $this.toggleClass($classes[index]);

                if ($targets[index] === '.app') {

                    // 菜单栏缩样式变化，tabs（fixed）位置调整
                    $('.tab-panel').toggleClass('fixed-left');

                    if ($($targets[index]).hasClass("app-aside-folded")) {

                    } else {

                    }
                }
            });
            $this.toggleClass('active');
        });

        // collapse nav
        $(document).on('click', 'nav a', function (e) {
            var $this = $(e.target), $active;
            $this.is('a') || ($this = $this.closest('a'));

            $active = $this.parent().siblings(".active");
            $active && $active.toggleClass('active').find('> ul:visible').slideUp(200);

            ($this.parent().hasClass('active') && $this.next().slideUp(200)) || $this.next().slideDown(200);
            $this.parent().toggleClass('active');

            e.preventDefault();

            /*            $this.next().is('ul') && e.preventDefault();

                        setTimeout(function () {
                            $(document).trigger('updateNav');
                        }, 300);*/
        });

        hasBind = true;
    }

    function render() {
        bindEvents();
    }

    return {
        render: render,
        on: emitter.on.bind(emitter),
    }

});