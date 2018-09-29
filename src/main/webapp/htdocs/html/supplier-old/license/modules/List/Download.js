define('List/Download', function(require, module, exports) {
	var $ = require('$');
	var MiniQuery = require('MiniQuery');
	var SMS = require('YWTC');
	var API = SMS.require("API");

	function downloadFile() {

		var api = new API("car/downloadTemplate");
		return api.getUrl();

	}

	return {
		downloadFile: downloadFile
	}
});