//控制器
;
(function($, MiniQuery, sms) {

	var Tabs = require('Tabs');
	var Login = require('Login');
	var WarnTip = require('WarnTip');

	Tabs.init(0);
	Login.init();

	$(document).on({
		'click' : function() {
			WarnTip.hide();
		},
		// 'B3sMo22ZLkWApjO/oEeDOxACEAI=' 业务用户 'QpXq24FxxE6c3lvHMPyYCxACEAI='
		// 系统用户
		'keydown' : function(event) {

			if (event.keyCode == 13) {

				var filename = location.href;

				filename = filename.substr(filename.lastIndexOf('/') + 1);

				if (filename === 'login.html') {
					// 系统用户登录
					Login.login('QpXq24FxxE6c3lvHMPyYCxACEAI=');
				} else if (filename === 'login_supplier.html') {
					// 供应商登录
					Login.login('B3sMo22ZLkWApjO/oEeDOxACEAI=');
				}

			}
		}
	});

	Login.on({
		'login' : function() {

			var filename = location.href;

			filename = filename.substr(filename.lastIndexOf('/') + 1);

			if (filename === 'login.html') {
				// 系统用户登录
				Login.login('QpXq24FxxE6c3lvHMPyYCxACEAI=');
			} else if (filename === 'login_supplier.html') {
				// 供应商登录
				Login.login('B3sMo22ZLkWApjO/oEeDOxACEAI=');
			}
		}
	});

	Tabs.on({
		'change' : function(index) {
			console.log(index);
		}
	});

})(jQuery, MiniQuery, SMS);