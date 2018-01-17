// ==ClosureCompiler==
// @compilation_level SIMPLE_OPTIMIZATIONS

/**
 * @license jqGrid  4.5.4 - jQuery Grid
 * Copyright (c) 2008, Tony Tomov, tony@trirand.com
 * Dual licensed under the MIT and GPL licenses
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl-2.0.html
 * Date: 2013-10-06
 */
//jsHint options
/*jshint evil:true, eqeqeq:false, eqnull:true, devel:true */
/*global jQuery */

(function ($) {
"use strict";
$.jgrid = $.jgrid || {};
$.extend($.jgrid,{
	version : "4.5.4",
	htmlDecode : function(value){
		if(value && (value==='&nbsp;' || value==='&#160;' || (value.length===1 && value.charCodeAt(0)===160))) { return "";}
		return !value ? value : String(value).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&amp;/g, "&");		
	},
	htmlEncode : function (value){
		return !value ? value : String(value).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	},
	format : function(format){ //jqgformat
		var args = $.makeArray(arguments).slice(1);
		if(format==null) { format = ""; }
		return format.replace(/\{(\d+)\}/g, function(m, i){
			return args[i];
		});
	},
	msie : navigator.appName === 'Microsoft Internet Explorer',
	msiever : function () {
		var rv = -1;
		var ua = navigator.userAgent;
		var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		if (re.exec(ua) != null) {
			rv = parseFloat( RegExp.$1 );
		}
		return rv;
	},
	getCellIndex : function (cell) {
		var c = $(cell);
		if (c.is('tr')) { return -1; }
		c = (!c.is('td') && !c.is('th') ? c.closest("td,th") : c)[0];
		if ($.jgrid.msie) { return $.inArray(c, c.parentNode.cells); }
		return c.cellIndex;
	},
	stripHtml : function(v) {
		v = String(v);
		var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
		if (v) {
			v = v.replace(regexp,"");
			return (v && v !== '&nbsp;' && v !== '&#160;') ? v.replace(/\"/g,"'") : "";
		} 
			return v;
	},
	stripPref : function (pref, id) {
		var obj = $.type( pref );
		if( obj === "string" || obj === "number") {
			pref =  String(pref);
			id = pref !== "" ? String(id).replace(String(pref), "") : id;
		}
		return id;
	},
	parse : function(jsonString) {
		var js = jsonString;
		if (js.substr(0,9) === "while(1);") { js = js.substr(9); }
		if (js.substr(0,2) === "/*") { js = js.substr(2,js.length-4); }
		if(!js) { js = "{}"; }
		return ($.jgrid.useJSON===true && typeof JSON === 'object' && typeof JSON.parse === 'function') ?
			JSON.parse(js) :
			eval('(' + js + ')');
	},
	parseDate : function(format, date, newformat, opts) {
		var	token = /\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		msDateRegExp = new RegExp("^\/Date\\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\\)\/$"),
		msMatch = ((typeof date === 'string') ? date.match(msDateRegExp): null),
		pad = function (value, length) {
			value = String(value);
			length = parseInt(length,10) || 2;
			while (value.length < length)  { value = '0' + value; }
			return value;
		},
		ts = {m : 1, d : 1, y : 1970, h : 0, i : 0, s : 0, u:0},
		timestamp=0, dM, k,hl,
		h12to24 = function(ampm, h){
			if (ampm === 0){ if (h === 12) { h = 0;} }
			else { if (h !== 12) { h += 12; } }
			return h;
		};
		if(opts === undefined) {
			opts = $.jgrid.formatter.date;
		}
		// old lang files
		if(opts.parseRe === undefined ) {
			opts.parseRe = /[#%\\\/:_;.,\t\s-]/;
		}
		if( opts.masks.hasOwnProperty(format) ) { format = opts.masks[format]; }
		if(date && date != null) {
			if( !isNaN( date - 0 ) && String(format).toLowerCase() === "u") {
				//Unix timestamp
				timestamp = new Date( parseFloat(date)*1000 );
			} else if(date.constructor === Date) {
				timestamp = date;
				// Microsoft date format support
			} else if( msMatch !== null ) {
				timestamp = new Date(parseInt(msMatch[1], 10));
				if (msMatch[3]) {
					var offset = Number(msMatch[5]) * 60 + Number(msMatch[6]);
					offset *= ((msMatch[4] === '-') ? 1 : -1);
					offset -= timestamp.getTimezoneOffset();
					timestamp.setTime(Number(Number(timestamp) + (offset * 60 * 1000)));
				}
			} else {
				var offset = 0;
				//Support ISO8601Long that have Z at the end to indicate UTC timezone
				if(opts.srcformat === 'ISO8601Long' && date.charAt(date.length - 1) === 'Z') {
					offset -= (new Date()).getTimezoneOffset();
				}
				date = String(date).replace(/\T/g,"#").replace(/\t/,"%").split(opts.parseRe);
				format = format.replace(/\T/g,"#").replace(/\t/,"%").split(opts.parseRe);
				// parsing for month names
				for(k=0,hl=format.length;k<hl;k++){
					if(format[k] === 'M') {
						dM = $.inArray(date[k],opts.monthNames);
						if(dM !== -1 && dM < 12){date[k] = dM+1; ts.m = date[k];}
					}
					if(format[k] === 'F') {
						dM = $.inArray(date[k],opts.monthNames,12);
						if(dM !== -1 && dM > 11){date[k] = dM+1-12; ts.m = date[k];}
					}
					if(format[k] === 'a') {
						dM = $.inArray(date[k],opts.AmPm);
						if(dM !== -1 && dM < 2 && date[k] === opts.AmPm[dM]){
							date[k] = dM;
							ts.h = h12to24(date[k], ts.h);
						}
					}
					if(format[k] === 'A') {
						dM = $.inArray(date[k],opts.AmPm);
						if(dM !== -1 && dM > 1 && date[k] === opts.AmPm[dM]){
							date[k] = dM-2;
							ts.h = h12to24(date[k], ts.h);
						}
					}
					if (format[k] === 'g') {
						ts.h = parseInt(date[k], 10);
					}
					if(date[k] !== undefined) {
						ts[format[k].toLowerCase()] = parseInt(date[k],10);
					}
				}
				if(ts.f) {ts.m = ts.f;}
				if( ts.m === 0 && ts.y === 0 && ts.d === 0) {
					return "&#160;" ;
				}
				ts.m = parseInt(ts.m,10)-1;
				var ty = ts.y;
				if (ty >= 70 && ty <= 99) {ts.y = 1900+ts.y;}
				else if (ty >=0 && ty <=69) {ts.y= 2000+ts.y;}
				timestamp = new Date(ts.y, ts.m, ts.d, ts.h, ts.i, ts.s, ts.u);
				//Apply offset to show date as local time.
				if(offset > 0) {
					timestamp.setTime(Number(Number(timestamp) + (offset * 60 * 1000)));
				}
			}
		} else {
			timestamp = new Date(ts.y, ts.m, ts.d, ts.h, ts.i, ts.s, ts.u);
		}
		if( newformat === undefined ) {
			return timestamp;
		}
		if( opts.masks.hasOwnProperty(newformat) )  {
			newformat = opts.masks[newformat];
		} else if ( !newformat ) {
			newformat = 'Y-m-d';
		}
		var 
			G = timestamp.getHours(),
			i = timestamp.getMinutes(),
			j = timestamp.getDate(),
			n = timestamp.getMonth() + 1,
			o = timestamp.getTimezoneOffset(),
			s = timestamp.getSeconds(),
			u = timestamp.getMilliseconds(),
			w = timestamp.getDay(),
			Y = timestamp.getFullYear(),
			N = (w + 6) % 7 + 1,
			z = (new Date(Y, n - 1, j) - new Date(Y, 0, 1)) / 86400000,
			flags = {
				// Day
				d: pad(j),
				D: opts.dayNames[w],
				j: j,
				l: opts.dayNames[w + 7],
				N: N,
				S: opts.S(j),
				//j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th',
				w: w,
				z: z,
				// Week
				W: N < 5 ? Math.floor((z + N - 1) / 7) + 1 : Math.floor((z + N - 1) / 7) || ((new Date(Y - 1, 0, 1).getDay() + 6) % 7 < 4 ? 53 : 52),
				// Month
				F: opts.monthNames[n - 1 + 12],
				m: pad(n),
				M: opts.monthNames[n - 1],
				n: n,
				t: '?',
				// Year
				L: '?',
				o: '?',
				Y: Y,
				y: String(Y).substring(2),
				// Time
				a: G < 12 ? opts.AmPm[0] : opts.AmPm[1],
				A: G < 12 ? opts.AmPm[2] : opts.AmPm[3],
				B: '?',
				g: G % 12 || 12,
				G: G,
				h: pad(G % 12 || 12),
				H: pad(G),
				i: pad(i),
				s: pad(s),
				u: u,
				// Timezone
				e: '?',
				I: '?',
				O: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				P: '?',
				T: (String(timestamp).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				Z: '?',
				// Full Date/Time
				c: '?',
				r: '?',
				U: Math.floor(timestamp / 1000)
			};
		return newformat.replace(token, function ($0) {
			return flags.hasOwnProperty($0) ? flags[$0] : $0.substring(1);
		});
	},
	jqID : function(sid){
		return String(sid).replace(/[!"#$%&'()*+,.\/:; <=>?@\[\\\]\^`{|}~]/g,"\\$&");
	},
	guid : 1,
	uidPref: 'jqg',
	randId : function( prefix )	{
		return (prefix || $.jgrid.uidPref) + ($.jgrid.guid++);
	},
	getAccessor : function(obj, expr) {
		var ret,p,prm = [], i;
		if( typeof expr === 'function') { return expr(obj); }
		ret = obj[expr];
		if(ret===undefined) {
			try {
				if ( typeof expr === 'string' ) {
					prm = expr.split('.');
				}
				i = prm.length;
				if( i ) {
					ret = obj;
					while (ret && i--) {
						p = prm.shift();
						ret = ret[p];
					}
				}
			} catch (e) {}
		}
		return ret;
	},
	getXmlData: function (obj, expr, returnObj) {
		var ret, m = typeof expr === 'string' ? expr.match(/^(.*)\[(\w+)\]$/) : null;
		if (typeof expr === 'function') { return expr(obj); }
		if (m && m[2]) {
			// m[2] is the attribute selector
			// m[1] is an optional element selector
			// examples: "[id]", "rows[page]"
			return m[1] ? $(m[1], obj).attr(m[2]) : $(obj).attr(m[2]);
		}
			ret = $(expr, obj);
			if (returnObj) { return ret; }
			//$(expr, obj).filter(':last'); // we use ':last' to be more compatible with old version of jqGrid
			return ret.length > 0 ? $(ret).text() : undefined;
	},
	cellWidth : function () {
		var $testDiv = $("<div class='ui-jqgrid' style='left:10000px'><table class='ui-jqgrid-btable' style='width:5px;'><tr class='jqgrow'><td style='width:5px;display:block;'></td></tr></table></div>"),
		testCell = $testDiv.appendTo("body")
			.find("td")
			.width();
		$testDiv.remove();
		return Math.abs(testCell-5) > 0.1;
	},
	cell_width : true,
	ajaxOptions: {},
	from : function(source){
		// Original Author Hugo Bonacci
		// License MIT http://jlinq.codeplex.com/license
		var QueryObject=function(d,q){
		if(typeof d==="string"){
			d=$.data(d);
		}
		var self=this,
		_data=d,
		_usecase=true,
		_trim=false,
		_query=q,
		_stripNum = /[\$,%]/g,
		_lastCommand=null,
		_lastField=null,
		_orDepth=0,
		_negate=false,
		_queuedOperator="",
		_sorting=[],
		_useProperties=true;
		if(typeof d==="object"&&d.push) {
			if(d.length>0){
				if(typeof d[0]!=="object"){
					_useProperties=false;
				}else{
					_useProperties=true;
				}
			}
		}else{
			throw "data provides is not an array";
		}
		this._hasData=function(){
			return _data===null?false:_data.length===0?false:true;
		};
		this._getStr=function(s){
			var phrase=[];
			if(_trim){
				phrase.push("jQuery.trim(");
			}
			phrase.push("String("+s+")");
			if(_trim){
				phrase.push(")");
			}
			if(!_usecase){
				phrase.push(".toLowerCase()");
			}
			return phrase.join("");
		};
		this._strComp=function(val){
			if(typeof val==="string"){
				return".toString()";
			}
			return"";
		};
		this._group=function(f,u){
			return({field:f.toString(),unique:u,items:[]});
		};
		this._toStr=function(phrase){
			if(_trim){
				phrase=$.trim(phrase);
			}
			phrase=phrase.toString().replace(/\\/g,'\\\\').replace(/\"/g,'\\"');
			return _usecase ? phrase : phrase.toLowerCase();
		};
		this._funcLoop=function(func){
			var results=[];
			$.each(_data,function(i,v){
				results.push(func(v));
			});
			return results;
		};
		this._append=function(s){
			var i;
			if(_query===null){
				_query="";
			} else {
				_query+=_queuedOperator === "" ? " && " :_queuedOperator;
			}
			for (i=0;i<_orDepth;i++){
				_query+="(";
			}
			if(_negate){
				_query+="!";
			}
			_query+="("+s+")";
			_negate=false;
			_queuedOperator="";
			_orDepth=0;
		};
		this._setCommand=function(f,c){
			_lastCommand=f;
			_lastField=c;
		};
		this._resetNegate=function(){
			_negate=false;
		};
		this._repeatCommand=function(f,v){
			if(_lastCommand===null){
				return self;
			}
			if(f!==null&&v!==null){
				return _lastCommand(f,v);
			}
			if(_lastField===null){
				return _lastCommand(f);
			}
			if(!_useProperties){
				return _lastCommand(f);
			}
			return _lastCommand(_lastField,f);
		};
		this._equals=function(a,b){
			return(self._compare(a,b,1)===0);
		};
		this._compare=function(a,b,d){
			var toString = Object.prototype.toString;
			if( d === undefined) { d = 1; }
			if(a===undefined) { a = null; }
			if(b===undefined) { b = null; }
			if(a===null && b===null){
				return 0;
			}
			if(a===null&&b!==null){
				return 1;
			}
			if(a!==null&&b===null){
				return -1;
			}
			if (toString.call(a) === '[object Date]' && toString.call(b) === '[object Date]') {
				if (a < b) { return -d; }
				if (a > b) { return d; }
				return 0;
			}
			if(!_usecase && typeof a !== "number" && typeof b !== "number" ) {
				a=String(a);
				b=String(b);
			}
			if(a<b){return -d;}
			if(a>b){return d;}
			return 0;
		};
		this._performSort=function(){
			if(_sorting.length===0){return;}
			_data=self._doSort(_data,0);
		};
		this._doSort=function(d,q){
			var by=_sorting[q].by,
			dir=_sorting[q].dir,
			type = _sorting[q].type,
			dfmt = _sorting[q].datefmt,
			sfunc = _sorting[q].sfunc;
			if(q===_sorting.length-1){
				return self._getOrder(d, by, dir, type, dfmt, sfunc);
			}
			q++;
			var values=self._getGroup(d,by,dir,type,dfmt), results=[], i, j, sorted;
			for(i=0;i<values.length;i++){
				sorted=self._doSort(values[i].items,q);
				for(j=0;j<sorted.length;j++){
					results.push(sorted[j]);
				}
			}
			return results;
		};
		this._getOrder=function(data,by,dir,type, dfmt, sfunc){
			var sortData=[],_sortData=[], newDir = dir==="a" ? 1 : -1, i,ab,j,
			findSortKey;

			if(type === undefined ) { type = "text"; }
			if (type === 'float' || type=== 'number' || type=== 'currency' || type=== 'numeric') {
				findSortKey = function($cell) {
					var key = parseFloat( String($cell).replace(_stripNum, ''));
					return isNaN(key) ? 0.00 : key;
				};
			} else if (type==='int' || type==='integer') {
				findSortKey = function($cell) {
					return $cell ? parseFloat(String($cell).replace(_stripNum, '')) : 0;
				};
			} else if(type === 'date' || type === 'datetime') {
				findSortKey = function($cell) {
					return $.jgrid.parseDate(dfmt,$cell).getTime();
				};
			} else if($.isFunction(type)) {
				findSortKey = type;
			} else {
				findSortKey = function($cell) {
					$cell = $cell ? $.trim(String($cell)) : "";
					return _usecase ? $cell : $cell.toLowerCase();
				};
			}
			$.each(data,function(i,v){
				ab = by!=="" ? $.jgrid.getAccessor(v,by) : v;
				if(ab === undefined) { ab = ""; }
				ab = findSortKey(ab, v);
				_sortData.push({ 'vSort': ab,'index':i});
			});
			if($.isFunction(sfunc)) {
				_sortData.sort(function(a,b){
					a = a.vSort;
					b = b.vSort;
					return sfunc.call(this,a,b,newDir);
				});
			} else {
				_sortData.sort(function(a,b){
					a = a.vSort;
					b = b.vSort;
					return self._compare(a,b,newDir);
				});
			}
			j=0;
			var nrec= data.length;
			// overhead, but we do not change the original data.
			while(j<nrec) {
				i = _sortData[j].index;
				sortData.push(data[i]);
				j++;
			}
			return sortData;
		};
		this._getGroup=function(data,by,dir,type, dfmt){
			var results=[],
			group=null,
			last=null, val;
			$.each(self._getOrder(data,by,dir,type, dfmt),function(i,v){
				val = $.jgrid.getAccessor(v, by);
				if(val == null) { val = ""; }
				if(!self._equals(last,val)){
					last=val;
					if(group !== null){
						results.push(group);
					}
					group=self._group(by,val);
				}
				group.items.push(v);
			});
			if(group !== null){
				results.push(group);
			}
			return results;
		};
		this.ignoreCase=function(){
			_usecase=false;
			return self;
		};
		this.useCase=function(){
			_usecase=true;
			return self;
		};
		this.trim=function(){
			_trim=true;
			return self;
		};
		this.noTrim=function(){
			_trim=false;
			return self;
		};
		this.execute=function(){
			var match=_query, results=[];
			if(match === null){
				return self;
			}
			$.each(_data,function(){
				if(eval(match)){results.push(this);}
			});
			_data=results;
			return self;
		};
		this.data=function(){
			return _data;
		};
		this.select=function(f){
			self._performSort();
			if(!self._hasData()){ return[]; }
			self.execute();
			if($.isFunction(f)){
				var results=[];
				$.each(_data,function(i,v){
					results.push(f(v));
				});
				return results;
			}
			return _data;
		};
		this.hasMatch=function(){
			if(!self._hasData()) { return false; }
			self.execute();
			return _data.length>0;
		};
		this.andNot=function(f,v,x){
			_negate=!_negate;
			return self.and(f,v,x);
		};
		this.orNot=function(f,v,x){
			_negate=!_negate;
			return self.or(f,v,x);
		};
		this.not=function(f,v,x){
			return self.andNot(f,v,x);
		};
		this.and=function(f,v,x){
			_queuedOperator=" && ";
			if(f===undefined){
				return self;
			}
			return self._repeatCommand(f,v,x);
		};
		this.or=function(f,v,x){
			_queuedOperator=" || ";
			if(f===undefined) { return self; }
			return self._repeatCommand(f,v,x);
		};
		this.orBegin=function(){
			_orDepth++;
			return self;
		};
		this.orEnd=function(){
			if (_query !== null){
				_query+=")";
			}
			return self;
		};
		this.isNot=function(f){
			_negate=!_negate;
			return self.is(f);
		};
		this.is=function(f){
			self._append('this.'+f);
			self._resetNegate();
			return self;
		};
		this._compareValues=function(func,f,v,how,t){
			var fld;
			if(_useProperties){
				fld='jQuery.jgrid.getAccessor(this,\''+f+'\')';
			}else{
				fld='this';
			}
			if(v===undefined) { v = null; }
			//var val=v===null?f:v,
			var val =v,
			swst = t.stype === undefined ? "text" : t.stype;
			if(v !== null) {
			switch(swst) {
				case 'int':
				case 'integer':
					val = (isNaN(Number(val)) || val==="") ? '0' : val; // To be fixed with more inteligent code
					fld = 'parseInt('+fld+',10)';
					val = 'parseInt('+val+',10)';
					break;
				case 'float':
				case 'number':
				case 'numeric':
					val = String(val).replace(_stripNum, '');
					val = (isNaN(Number(val)) || val==="") ? '0' : val; // To be fixed with more inteligent code
					fld = 'parseFloat('+fld+')';
					val = 'parseFloat('+val+')';
					break;
				case 'date':
				case 'datetime':
					val = String($.jgrid.parseDate(t.newfmt || 'Y-m-d',val).getTime());
					fld = 'jQuery.jgrid.parseDate("'+t.srcfmt+'",'+fld+').getTime()';
					break;
				default :
					fld=self._getStr(fld);
					val=self._getStr('"'+self._toStr(val)+'"');
			}
			}
			self._append(fld+' '+how+' '+val);
			self._setCommand(func,f);
			self._resetNegate();
			return self;
		};
		this.equals=function(f,v,t){
			return self._compareValues(self.equals,f,v,"==",t);
		};
		this.notEquals=function(f,v,t){
			return self._compareValues(self.equals,f,v,"!==",t);
		};
		this.isNull = function(f,v,t){
			return self._compareValues(self.equals,f,null,"===",t);
		};
		this.greater=function(f,v,t){
			return self._compareValues(self.greater,f,v,">",t);
		};
		this.less=function(f,v,t){
			return self._compareValues(self.less,f,v,"<",t);
		};
		this.greaterOrEquals=function(f,v,t){
			return self._compareValues(self.greaterOrEquals,f,v,">=",t);
		};
		this.lessOrEquals=function(f,v,t){
			return self._compareValues(self.lessOrEquals,f,v,"<=",t);
		};
		this.startsWith=function(f,v){
			var val = (v==null) ? f: v,
			length=_trim ? $.trim(val.toString()).length : val.toString().length;
			if(_useProperties){
				self._append(self._getStr('jQuery.jgrid.getAccessor(this,\''+f+'\')')+'.substr(0,'+length+') == '+self._getStr('"'+self._toStr(v)+'"'));
			}else{
				if (v!=null) { length=_trim?$.trim(v.toString()).length:v.toString().length; }
				self._append(self._getStr('this')+'.substr(0,'+length+') == '+self._getStr('"'+self._toStr(f)+'"'));
			}
			self._setCommand(self.startsWith,f);
			self._resetNegate();
			return self;
		};
		this.endsWith=function(f,v){
			var val = (v==null) ? f: v,
			length=_trim ? $.trim(val.toString()).length:val.toString().length;
			if(_useProperties){
				self._append(self._getStr('jQuery.jgrid.getAccessor(this,\''+f+'\')')+'.substr('+self._getStr('jQuery.jgrid.getAccessor(this,\''+f+'\')')+'.length-'+length+','+length+') == "'+self._toStr(v)+'"');
			} else {
				self._append(self._getStr('this')+'.substr('+self._getStr('this')+'.length-"'+self._toStr(f)+'".length,"'+self._toStr(f)+'".length) == "'+self._toStr(f)+'"');
			}
			self._setCommand(self.endsWith,f);self._resetNegate();
			return self;
		};
		this.contains=function(f,v){
			if(_useProperties){
				self._append(self._getStr('jQuery.jgrid.getAccessor(this,\''+f+'\')')+'.indexOf("'+self._toStr(v)+'",0) > -1');
			}else{
				self._append(self._getStr('this')+'.indexOf("'+self._toStr(f)+'",0) > -1');
			}
			self._setCommand(self.contains,f);
			self._resetNegate();
			return self;
		};
		this.groupBy=function(by,dir,type, datefmt){
			if(!self._hasData()){
				return null;
			}
			return self._getGroup(_data,by,dir,type, datefmt);
		};
		this.orderBy=function(by,dir,stype, dfmt, sfunc){
			dir = dir == null ? "a" :$.trim(dir.toString().toLowerCase());
			if(stype == null) { stype = "text"; }
			if(dfmt == null) { dfmt = "Y-m-d"; }
			if(sfunc == null) { sfunc = false; }
			if(dir==="desc"||dir==="descending"){dir="d";}
			if(dir==="asc"||dir==="ascending"){dir="a";}
			_sorting.push({by:by,dir:dir,type:stype, datefmt: dfmt, sfunc: sfunc});
			return self;
		};
		return self;
		};
	return new QueryObject(source,null);
	},
	getMethod: function (name) {
        return this.getAccessor($.fn.jqGrid, name);
	},
	extend : function(methods) {
		$.extend($.fn.jqGrid,methods);
		if (!this.no_legacy_api) {
			$.fn.extend(methods);
		}
	}
});

$.fn.jqGrid = function( pin ) {
	if (typeof pin === 'string') {
		var fn = $.jgrid.getMethod(pin);
		if (!fn) {
			throw ("jqGrid - No such method: " + pin);
		}
		var args = $.makeArray(arguments).slice(1);
		return fn.apply(this,args);
	}
	return this.each( function() {
		if(this.grid) {return;}

		var p = $.extend(true,{
			url: "",
			height: 150,
			page: 1,
			rowNum: 20,
			rowTotal : null,
			records: 0,
			pager: "",
			pgbuttons: true,
			pginput: true,
			colModel: [],
			rowList: [],
			colNames: [],
			sortorder: "asc",
			sortname: "",
			datatype: "xml",
			mtype: "GET",
			altRows: false,
			selarrrow: [],
			savedRow: [],
			shrinkToFit: true,
			xmlReader: {},
			jsonReader: {},
			subGrid: false,
			subGridModel :[],
			reccount: 0,
			lastpage: 0,
			lastsort: 0,
			selrow: null,
			beforeSelectRow: null,
			onSelectRow: null,
			onSortCol: null,
			ondblClickRow: null,
			onRightClickRow: null,
			onPaging: null,
			onSelectAll: null,
			onInitGrid : null,
			loadComplete: null,
			gridComplete: null,
			loadError: null,
			loadBeforeSend: null,
			afterInsertRow: null,
			beforeRequest: null,
			beforeProcessing : null,
			onHeaderClick: null,
			viewrecords: false,
			loadonce: false,
			multiselect: false,
			multikey: false,
			editurl: null,
			search: false,
			caption: "",
			hidegrid: true,
			hiddengrid: false,
			postData: {},
			userData: {},
			treeGrid : false,
			treeGridModel : 'nested',
			treeReader : {},
			treeANode : -1,
			ExpandColumn: null,
			tree_root_level : 0,
			prmNames: {page:"page",rows:"rows", sort: "sidx",order: "sord", search:"_search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del", subgridid:"id", npage: null, totalrows:"totalrows"},
			forceFit : false,
			gridstate : "visible",
			cellEdit: false,
			cellsubmit: "remote",
			nv:0,
			loadui: "enable",
			toolbar: [false,""],
			scroll: false,
			multiboxonly : false,
			deselectAfterSort : true,
			scrollrows : false,
			autowidth: false,
			scrollOffset :18,
			cellLayout: 5,
			subGridWidth: 20,
			multiselectWidth: 20,
			gridview: false,
			rownumWidth: 25,
			rownumbers : false,
			pagerpos: 'center',
			recordpos: 'right',
			footerrow : false,
			userDataOnFooter : false,
			hoverrows : true,
			altclass : 'ui-priority-secondary',
			viewsortcols : [false,'vertical',true],
			resizeclass : '',
			autoencode : false,
			remapColumns : [],
			ajaxGridOptions :{},
			direction : "ltr",
			toppager: false,
			headertitles: false,
			scrollTimeout: 40,
			data : [],
			_index : {},
			grouping : false,
			groupingView : {groupField:[],groupOrder:[], groupText:[],groupColumnShow:[],groupSummary:[], showSummaryOnHide: false, sortitems:[], sortnames:[], summary:[],summaryval:[], plusicon: 'ui-icon-circlesmall-plus', minusicon: 'ui-icon-circlesmall-minus', displayField: [], groupSummaryPos:[], formatDisplayField : [], _locgr : false},
			ignoreCase : false,
			cmTemplate : {},
			idPrefix : "",
			multiSort :  false
		}, $.jgrid.defaults, pin || {});
		var ts= this, grid={
			headers:[],
			cols:[],
			footers: [],
			dragStart: function(i,x,y) {
				var gridLeftPos = $(this.bDiv).offset().left;
				this.resizing = { idx: i, startX: x.clientX, sOL : x.clientX - gridLeftPos };
				this.hDiv.style.cursor = "col-resize";
				this.curGbox = $("#rs_m"+$.jgrid.jqID(p.id),"#gbox_"+$.jgrid.jqID(p.id));
				this.curGbox.css({display:"block",left:x.clientX-gridLeftPos,top:y[1],height:y[2]});
				$(ts).triggerHandler("jqGridResizeStart", [x, i]);
				if($.isFunction(p.resizeStart)) { p.resizeStart.call(ts,x,i); }
				document.onselectstart=function(){return false;};
			},
			dragMove: function(x) {
				if(this.resizing) {
					var diff = x.clientX-this.resizing.startX,
					h = this.headers[this.resizing.idx],
					newWidth = p.direction === "ltr" ? h.width + diff : h.width - diff, hn, nWn;
					if(newWidth > 33) {
						this.curGbox.css({left:this.resizing.sOL+diff});
						if(p.forceFit===true ){
							hn = this.headers[this.resizing.idx+p.nv];
							nWn = p.direction === "ltr" ? hn.width - diff : hn.width + diff;
							if(nWn >33) {
								h.newWidth = newWidth;
								hn.newWidth = nWn;
							}
						} else {
							this.newWidth = p.direction === "ltr" ? p.tblwidth+diff : p.tblwidth-diff;
							h.newWidth = newWidth;
						}
					}
				}
			},
			dragEnd: function() {
				this.hDiv.style.cursor = "default";
				if(this.resizing) {
					var idx = this.resizing.idx,
					nw = this.headers[idx].newWidth || this.headers[idx].width;
					nw = parseInt(nw,10);
					this.resizing = false;
					$("#rs_m"+$.jgrid.jqID(p.id)).css("display","none");
					p.colModel[idx].width = nw;
					this.headers[idx].width = nw;
					this.headers[idx].el.style.width = nw + "px";
					this.cols[idx].style.width = nw+"px";
					if(this.footers.length>0) {this.footers[idx].style.width = nw+"px";}
					if(p.forceFit===true){
						nw = this.headers[idx+p.nv].newWidth || this.headers[idx+p.nv].width;
						this.headers[idx+p.nv].width = nw;
						this.headers[idx+p.nv].el.style.width = nw + "px";
						this.cols[idx+p.nv].style.width = nw+"px";
						if(this.footers.length>0) {this.footers[idx+p.nv].style.width = nw+"px";}
						p.colModel[idx+p.nv].width = nw;
					} else {
						p.tblwidth = this.newWidth || p.tblwidth;
						$('table:first',this.bDiv).css("width",p.tblwidth+"px");
						$('table:first',this.hDiv).css("width",p.tblwidth+"px");
						this.hDiv.scrollLeft = this.bDiv.scrollLeft;
						if(p.footerrow) {
							$('table:first',this.sDiv).css("width",p.tblwidth+"px");
							this.sDiv.scrollLeft = this.bDiv.scrollLeft;
						}
					}
					$(ts).triggerHandler("jqGridResizeStop", [nw, idx]);
					if($.isFunction(p.resizeStop)) { p.resizeStop.call(ts,nw,idx); }
				}
				this.curGbox = null;
				document.onselectstart=function(){return true;};
			},
			populateVisible: function() {
				if (grid.timer) { clearTimeout(grid.timer); }
				grid.timer = null;
				var dh = $(grid.bDiv).height();
				if (!dh) { return; }
				var table = $("table:first", grid.bDiv);
				var rows, rh;
				if(table[0].rows.length) {
					try {
						rows = table[0].rows[1];
						rh = rows ? $(rows).outerHeight() || grid.prevRowHeight : grid.prevRowHeight;
					} catch (pv) {
						rh = grid.prevRowHeight;
					}
				}
				if (!rh) { return; }
				grid.prevRowHeight = rh;
				var rn = p.rowNum;
				var scrollTop = grid.scrollTop = grid.bDiv.scrollTop;
				var ttop = Math.round(table.position().top) - scrollTop;
				var tbot = ttop + table.height();
				var div = rh * rn;
				var page, npage, empty;
				if ( tbot < dh && ttop <= 0 &&
					(p.lastpage===undefined||parseInt((tbot + scrollTop + div - 1) / div,10) <= p.lastpage))
				{
					npage = parseInt((dh - tbot + div - 1) / div,10);
					if (tbot >= 0 || npage < 2 || p.scroll === true) {
						page = Math.round((tbot + scrollTop) / div) + 1;
						ttop = -1;
					} else {
						ttop = 1;
					}
				}
				if (ttop > 0) {
					page = parseInt(scrollTop / div,10) + 1;
					npage = parseInt((scrollTop + dh) / div,10) + 2 - page;
					empty = true;
				}
				if (npage) {
					if (p.lastpage && (page > p.lastpage || p.lastpage===1 || (page === p.page && page===p.lastpage)) ) {
						return;
					}
					if (grid.hDiv.loading) {
						grid.timer = setTimeout(grid.populateVisible, p.scrollTimeout);
					} else {
						p.page = page;
						if (empty) {
							grid.selectionPreserver(table[0]);
							grid.emptyRows.call(table[0], false, false);
						}
						grid.populate(npage);
					}
				}
			},
			scrollGrid: function( e ) {
				if(p.scroll) {
					var scrollTop = grid.bDiv.scrollTop;
					if(grid.scrollTop === undefined) { grid.scrollTop = 0; }
					if (scrollTop !== grid.scrollTop) {
						grid.scrollTop = scrollTop;
						if (grid.timer) { clearTimeout(grid.timer); }
						grid.timer = setTimeout(grid.populateVisible, p.scrollTimeout);
					}
				}
				grid.hDiv.scrollLeft = grid.bDiv.scrollLeft;
				if(p.footerrow) {
					grid.sDiv.scrollLeft = grid.bDiv.scrollLeft;
				}
				if( e ) { e.stopPropagation(); }
			},
			selectionPreserver : function(ts) {
				var p = ts.p,
				sr = p.selrow, sra = p.selarrrow ? $.makeArray(p.selarrrow) : null,
				left = ts.grid.bDiv.scrollLeft,
				restoreSelection = function() {
					var i;
					p.selrow = null;
					p.selarrrow = [];
					if(p.multiselect && sra && sra.length>0) {
						for(i=0;i<sra.length;i++){
							if (sra[i] !== sr) {
								$(ts).jqGrid("setSelection",sra[i],false, null);
							}
						}
					}
					if (sr) {
						$(ts).jqGrid("setSelection",sr,false,null);
					}
					ts.grid.bDiv.scrollLeft = left;
					$(ts).unbind('.selectionPreserver', restoreSelection);
				};
				$(ts).bind('jqGridGridComplete.selectionPreserver', restoreSelection);				
			}
		};
		if(this.tagName.toUpperCase() !== 'TABLE') {
			alert("Element is not a table");
			return;
		}
		if(document.documentMode !== undefined ) { // IE only
			if(document.documentMode <= 5) {
				alert("Grid can not be used in this ('quirks') mode!");
				return;
			}
		}
		$(this).empty().attr("tabindex","0");
		this.p = p ;
		this.p.useProp = !!$.fn.prop;
		var i, dir;
		if(this.p.colNames.length === 0) {
			for (i=0;i<this.p.colModel.length;i++){
				this.p.colNames[i] = this.p.colModel[i].label || this.p.colModel[i].name;
			}
		}
		if( this.p.colNames.length !== this.p.colModel.length ) {
			alert($.jgrid.errors.model);
			return;
		}
		var gv = $("<div class='ui-jqgrid-view'></div>"),
		isMSIE = $.jgrid.msie;
		ts.p.direction = $.trim(ts.p.direction.toLowerCase());
		if($.inArray(ts.p.direction,["ltr","rtl"]) === -1) { ts.p.direction = "ltr"; }
		dir = ts.p.direction;

		$(gv).insertBefore(this);
		$(this).removeClass("scroll").appendTo(gv);
		var eg = $("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");
		$(eg).attr({"id" : "gbox_"+this.id,"dir":dir}).insertBefore(gv);
		$(gv).attr("id","gview_"+this.id).appendTo(eg);
		$("<div class='ui-widget-overlay jqgrid-overlay' id='lui_"+this.id+"'></div>").insertBefore(gv);
		$("<div class='loading ui-state-default ui-state-active' id='load_"+this.id+"'>"+this.p.loadtext+"</div>").insertBefore(gv);
		$(this).attr({cellspacing:"0",cellpadding:"0",border:"0","role":"grid","aria-multiselectable":!!this.p.multiselect,"aria-labelledby":"gbox_"+this.id});
		var sortkeys = ["shiftKey","altKey","ctrlKey"],
		intNum = function(val,defval) {
			val = parseInt(val,10);
			if (isNaN(val)) { return defval || 0;}
			return val;
		},
		formatCol = function (pos, rowInd, tv, rawObject, rowId, rdata){
			var cm = ts.p.colModel[pos],
			ral = cm.align, result="style=\"", clas = cm.classes, nm = cm.name, celp, acp=[];
			if(ral) { result += "text-align:"+ral+";"; }
			if(cm.hidden===true) { result += "display:none;"; }
			if(rowInd===0) {
				result += "width: "+grid.headers[pos].width+"px;";
			} else if (cm.cellattr && $.isFunction(cm.cellattr))
			{
				celp = cm.cellattr.call(ts, rowId, tv, rawObject, cm, rdata);
				if(celp && typeof celp === "string") {
					celp = celp.replace(/style/i,'style').replace(/title/i,'title');
					if(celp.indexOf('title') > -1) { cm.title=false;}
					if(celp.indexOf('class') > -1) { clas = undefined;}
					acp = celp.replace('-style','-sti').split(/style/);
					if(acp.length === 2 ) {
						acp[1] =  $.trim(acp[1].replace('-sti','-style').replace("=",""));
						if(acp[1].indexOf("'") === 0 || acp[1].indexOf('"') === 0) {
							acp[1] = acp[1].substring(1);
						}
						result += acp[1].replace(/'/gi,'"');
					} else {
						result += "\"";
					}
				}
			}
			if(!acp.length) { acp[0] = ""; result += "\"";}
			result += (clas !== undefined ? (" class=\""+clas+"\"") :"") + ((cm.title && tv) ? (" title=\""+$.jgrid.stripHtml(tv)+"\"") :"");
			result += " aria-describedby=\""+ts.p.id+"_"+nm+"\"";
			return result + acp[0];
		},
		cellVal =  function (val) {
			return val == null || val === "" ? "&#160;" : (ts.p.autoencode ? $.jgrid.htmlEncode(val) : String(val));
		},
		formatter = function (rowId, cellval , colpos, rwdat, _act){
			var cm = ts.p.colModel[colpos],v;
			if(cm.formatter !== undefined) {
				rowId = String(ts.p.idPrefix) !== "" ? $.jgrid.stripPref(ts.p.idPrefix, rowId) : rowId;
				var opts= {rowId: rowId, colModel:cm, gid:ts.p.id, pos:colpos };
				if($.isFunction( cm.formatter ) ) {
					v = cm.formatter.call(ts,cellval,opts,rwdat,_act);
				} else if($.fmatter){
					v = $.fn.fmatter.call(ts,cm.formatter,cellval,opts,rwdat,_act);
				} else {
					v = cellVal(cellval);
				}
			} else {
				v = cellVal(cellval);
			}
			return v;
		},
		addCell = function(rowId,cell,pos,irow, srvr, rdata) {
			var v,prp;
			v = formatter(rowId,cell,pos,srvr,'add');
			prp = formatCol( pos,irow, v, srvr, rowId, rdata);
			return "<td role=\"gridcell\" "+prp+">"+v+"</td>";
		},
		addMulti = function(rowid,pos,irow,checked){
			var	v = "<input role=\"checkbox\" type=\"checkbox\""+" id=\"jqg_"+ts.p.id+"_"+rowid+"\" class=\"cbox\" name=\"jqg_"+ts.p.id+"_"+rowid+"\"" + (checked ? "checked=\"checked\"" : "")+"/>",
			prp = formatCol( pos,irow,'',null, rowid, true);
			return "<td role=\"gridcell\" "+prp+">"+v+"</td>";
		},
		addRowNum = function (pos,irow,pG,rN) {
			var v =  (parseInt(pG,10)-1)*parseInt(rN,10)+1+irow,
			prp = formatCol( pos,irow,v, null, irow, true);
			return "<td role=\"gridcell\" class=\"ui-state-default jqgrid-rownum\" "+prp+">"+v+"</td>";
		},
		reader = function (datatype) {
			var field, f=[], j=0, i;
			for(i =0; i<ts.p.colModel.length; i++){
				field = ts.p.colModel[i];
				if (field.name !== 'cb' && field.name !=='subgrid' && field.name !=='rn') {
					f[j]= datatype === "local" ?
					field.name :
					( (datatype==="xml" || datatype === "xmlstring") ? field.xmlmap || field.name : field.jsonmap || field.name );
					if(ts.p.keyIndex !== false && field.key===true ) {
						ts.p.keyName = f[j];
					}
					j++;
				}
			}
			return f;
		},
		orderedCols = function (offset) {
			var order = ts.p.remapColumns;
			if (!order || !order.length) {
				order = $.map(ts.p.colModel, function(v,i) { return i; });
			}
			if (offset) {
				order = $.map(order, function(v) { return v<offset?null:v-offset; });
			}
			return order;
		},
		emptyRows = function (scroll, locdata) {
			var firstrow;
			if (this.p.deepempty) {
				$(this.rows).slice(1).remove();
			} else {
				firstrow = this.rows.length > 0 ? this.rows[0] : null;
				$(this.firstChild).empty().append(firstrow);
			}
			if (scroll && this.p.scroll) {
				$(this.grid.bDiv.firstChild).css({height: "auto"});
				$(this.grid.bDiv.firstChild.firstChild).css({height: 0, display: "none"});
				if (this.grid.bDiv.scrollTop !== 0) {
					this.grid.bDiv.scrollTop = 0;
				}
			}
			if(locdata === true && this.p.treeGrid) {
				this.p.data = []; this.p._index = {};
			}
		},
		refreshIndex = function() {
			var datalen = ts.p.data.length, idname, i, val,
			ni = ts.p.rownumbers===true ? 1 :0,
			gi = ts.p.multiselect ===true ? 1 :0,
			si = ts.p.subGrid===true ? 1 :0;

			if(ts.p.keyIndex === false || ts.p.loadonce === true) {
				idname = ts.p.localReader.id;
			} else {
				idname = ts.p.colModel[ts.p.keyIndex+gi+si+ni].name;
			}
			for(i =0;i < datalen; i++) {
				val = $.jgrid.getAccessor(ts.p.data[i],idname);
				if (val === undefined) { val=String(i+1); }
				ts.p._index[val] = i;
			}
		},
		constructTr = function(id, hide, altClass, rd, cur, selected) {
			var tabindex = '-1', restAttr = '', attrName, style = hide ? 'display:none;' : '',
				classes = 'ui-widget-content jqgrow ui-row-' + ts.p.direction + (altClass ? ' ' + altClass : '') + (selected ? ' ui-state-highlight' : ''),
				rowAttrObj = $(ts).triggerHandler("jqGridRowAttr", [rd, cur, id]);
				if( typeof rowAttrObj !== "object" ) {
					rowAttrObj =   $.isFunction(ts.p.rowattr) ? ts.p.rowattr.call(ts, rd, cur, id) :{};
				}
			if(!$.isEmptyObject( rowAttrObj )) {
				if (rowAttrObj.hasOwnProperty("id")) {
					id = rowAttrObj.id;
					delete rowAttrObj.id;
				}
				if (rowAttrObj.hasOwnProperty("tabindex")) {
					tabindex = rowAttrObj.tabindex;
					delete rowAttrObj.tabindex;
				}
				if (rowAttrObj.hasOwnProperty("style")) {
					style += rowAttrObj.style;
					delete rowAttrObj.style;
				}
				if (rowAttrObj.hasOwnProperty("class")) {
					classes += ' ' + rowAttrObj['class'];
					delete rowAttrObj['class'];
				}
				// dot't allow to change role attribute
				try { delete rowAttrObj.role; } catch(ra){}
				for (attrName in rowAttrObj) {
					if (rowAttrObj.hasOwnProperty(attrName)) {
						restAttr += ' ' + attrName + '=' + rowAttrObj[attrName];
					}
				}
			}
			return '<tr role="row" id="' + id + '" tabindex="' + tabindex + '" class="' + classes + '"' +
				(style === '' ? '' : ' style="' + style + '"') + restAttr + '>';
		},
		addXmlData = function (xml,t, rcnt, more, adjust) {
			var startReq = new Date(),
			locdata = (ts.p.datatype !== "local" && ts.p.loadonce) || ts.p.datatype === "xmlstring",
			xmlid = "_id_", xmlRd = ts.p.xmlReader,
			frd = ts.p.datatype === "local" ? "local" : "xml";
			if(locdata) {
				ts.p.data = [];
				ts.p._index = {};
				ts.p.localReader.id = xmlid;
			}
			ts.p.reccount = 0;
			if($.isXMLDoc(xml)) {
				if(ts.p.treeANode===-1 && !ts.p.scroll) {
					emptyRows.call(ts, false, true);
					rcnt=1;
				} else { rcnt = rcnt > 1 ? rcnt :1; }
			} else { return; }
			var self= $(ts), i,fpos,ir=0,v,gi=ts.p.multiselect===true?1:0,si=0,addSubGridCell,ni=ts.p.rownumbers===true?1:0,idn, getId,f=[],F,rd ={}, xmlr,rid, rowData=[], cn=(ts.p.altRows === true) ? ts.p.altclass:"",cn1;
			if(ts.p.subGrid===true) {
				si = 1;
				addSubGridCell = $.jgrid.getMethod("addSubGridCell");
			}
			if(!xmlRd.repeatitems) {f = reader(frd);}
			if( ts.p.keyIndex===false) {
				idn = $.isFunction( xmlRd.id ) ?  xmlRd.id.call(ts, xml) : xmlRd.id;
			} else {
				idn = ts.p.keyIndex;
			}
			if(f.length>0 && !isNaN(idn)) {
				idn=ts.p.keyName;
			}
			if( String(idn).indexOf("[") === -1 ) {
				if (f.length) {
					getId = function( trow, k) {return $(idn,trow).text() || k;};
				} else {
					getId = function( trow, k) {return $(xmlRd.cell,trow).eq(idn).text() || k;};
				}
			}
			else {
				getId = function( trow, k) {return trow.getAttribute(idn.replace(/[\[\]]/g,"")) || k;};
			}
			ts.p.userData = {};
			ts.p.page = intNum($.jgrid.getXmlData(xml, xmlRd.page), ts.p.page);
			ts.p.lastpage = intNum($.jgrid.getXmlData(xml, xmlRd.total), 1);
			ts.p.records = intNum($.jgrid.getXmlData(xml, xmlRd.records));
			if($.isFunction(xmlRd.userdata)) {
				ts.p.userData = xmlRd.userdata.call(ts, xml) || {};
			} else {
				$.jgrid.getXmlData(xml, xmlRd.userdata, true).each(function() {ts.p.userData[this.getAttribute("name")]= $(this).text();});
			}
			var gxml = $.jgrid.getXmlData( xml, xmlRd.root, true);
			gxml = $.jgrid.getXmlData( gxml, xmlRd.row, true);
			if (!gxml) { gxml = []; }
			var gl = gxml.length, j=0, grpdata=[], rn = parseInt(ts.p.rowNum,10), br=ts.p.scroll?$.jgrid.randId():1, altr;
			if (gl > 0 &&  ts.p.page <= 0) { ts.p.page = 1; }
			if(gxml && gl){
			if (adjust) { rn *= adjust+1; }
			var afterInsRow = $.isFunction(ts.p.afterInsertRow), hiderow=false, groupingPrepare;
			if(ts.p.grouping)  {
				hiderow = ts.p.groupingView.groupCollapse === true;
				groupingPrepare = $.jgrid.getMethod("groupingPrepare");
			}
			while (j<gl) {
				xmlr = gxml[j];
				rid = getId(xmlr,br+j);
				rid  = ts.p.idPrefix + rid;
				altr = rcnt === 0 ? 0 : rcnt+1;
				cn1 = (altr+j)%2 === 1 ? cn : '';
				var iStartTrTag = rowData.length;
				rowData.push("");
				if( ni ) {
					rowData.push( addRowNum(0,j,ts.p.page,ts.p.rowNum) );
				}
				if( gi ) {
					rowData.push( addMulti(rid,ni,j, false) );
				}
				if( si ) {
					rowData.push( addSubGridCell.call(self,gi+ni,j+rcnt) );
				}
				if(xmlRd.repeatitems){
					if (!F) { F=orderedCols(gi+si+ni); }
					var cells = $.jgrid.getXmlData( xmlr, xmlRd.cell, true);
					$.each(F, function (k) {
						var cell = cells[this];
						if (!cell) { return false; }
						v = cell.textContent || cell.text;
						rd[ts.p.colModel[k+gi+si+ni].name] = v;
						rowData.push( addCell(rid,v,k+gi+si+ni,j+rcnt,xmlr, rd) );
					});
				} else {
					for(i = 0; i < f.length;i++) {
						v = $.jgrid.getXmlData( xmlr, f[i]);
						rd[ts.p.colModel[i+gi+si+ni].name] = v;
						rowData.push( addCell(rid, v, i+gi+si+ni, j+rcnt, xmlr, rd) );
					}
				}
				rowData[iStartTrTag] = constructTr(rid, hiderow, cn1, rd, xmlr, false);
				rowData.push("</tr>");
				if(ts.p.grouping) {
					grpdata.push( rowData );
					if(!ts.p.groupingView._locgr) {
						groupingPrepare.call(self, rd, j );
					}
					rowData = [];
				}
				if(locdata || ts.p.treeGrid === true) {
					rd[xmlid] = $.jgrid.stripPref(ts.p.idPrefix, rid);
					ts.p.data.push(rd);
					ts.p._index[rd[xmlid]] = ts.p.data.length-1;
				}
				if(ts.p.gridview === false ) {
					$("tbody:first",t).append(rowData.join(''));
					self.triggerHandler("jqGridAfterInsertRow", [rid, rd, xmlr]);
					if(afterInsRow) {ts.p.afterInsertRow.call(ts,rid,rd,xmlr);}
					rowData=[];
				}
				rd={};
				ir++;
				j++;
				if(ir===rn) {break;}
			}
			}
			if(ts.p.gridview === true) {
				fpos = ts.p.treeANode > -1 ? ts.p.treeANode: 0;
				if(ts.p.grouping) {
					if(!locdata) {
						self.jqGrid('groupingRender',grpdata,ts.p.colModel.length, ts.p.page, rn);
					}
					grpdata = null;
				} else if(ts.p.treeGrid === true && fpos > 0) {
					$(ts.rows[fpos]).after(rowData.join(''));
				} else {
					$("tbody:first",t).append(rowData.join(''));
				}
			}
			if(ts.p.subGrid === true ) {
				try {self.jqGrid("addSubGrid",gi+ni);} catch (_){}
			}
			ts.p.totaltime = new Date() - startReq;
			if(ir>0) { if(ts.p.records===0) { ts.p.records=gl;} }
			rowData =null;
			if( ts.p.treeGrid === true) {
				try {self.jqGrid("setTreeNode", fpos+1, ir+fpos+1);} catch (e) {}
			}
			if(!ts.p.treeGrid && !ts.p.scroll) {ts.grid.bDiv.scrollTop = 0;}
			ts.p.reccount=ir;
			ts.p.treeANode = -1;
			if(ts.p.userDataOnFooter) { self.jqGrid("footerData","set",ts.p.userData,true); }
			if(locdata) {
				ts.p.records = gl;
				ts.p.lastpage = Math.ceil(gl/ rn);
			}
			if (!more) { ts.updatepager(false,true); }
			if(locdata) {
				while (ir<gl) {
					xmlr = gxml[ir];
					rid = getId(xmlr,ir+br);
					rid  = ts.p.idPrefix + rid;
					if(xmlRd.repeatitems){
						if (!F) { F=orderedCols(gi+si+ni); }
						var cells2 = $.jgrid.getXmlData( xmlr, xmlRd.cell, true);
						$.each(F, function (k) {
							var cell = cells2[this];
							if (!cell) { return false; }
							v = cell.textContent || cell.text;
							rd[ts.p.colModel[k+gi+si+ni].name] = v;
						});
					} else {
						for(i = 0; i < f.length;i++) {
							v = $.jgrid.getXmlData( xmlr, f[i]);
							rd[ts.p.colModel[i+gi+si+ni].name] = v;
						}
					}
					rd[xmlid] = $.jgrid.stripPref(ts.p.idPrefix, rid);
					if(ts.p.grouping) {
						groupingPrepare.call(self, rd, ir );
					}
					ts.p.data.push(rd);
					ts.p._index[rd[xmlid]] = ts.p.data.length-1;
					rd = {};
					ir++;
				}
				if(ts.p.grouping) {
					ts.p.groupingView._locgr = true;
					self.jqGrid('groupingRender', grpdata, ts.p.colModel.length, ts.p.page, rn);
					grpdata = null;
				}
			}
		},
		addJSONData = function(data,t, rcnt, more, adjust) {
			var startReq = new Date();
			if(data) {
				if(ts.p.treeANode === -1 && !ts.p.scroll) {
					emptyRows.call(ts, false, true);
					rcnt=1;
				} else { rcnt = rcnt > 1 ? rcnt :1; }
			} else { return; }

			var dReader, locid = "_id_", frd,
			locdata = (ts.p.datatype !== "local" && ts.p.loadonce) || ts.p.datatype === "jsonstring";
			if(locdata) { ts.p.data = []; ts.p._index = {}; ts.p.localReader.id = locid;}
			ts.p.reccount = 0;
			if(ts.p.datatype === "local") {
				dReader =  ts.p.localReader;
				frd= 'local';
			} else {
				dReader =  ts.p.jsonReader;
				frd='json';
			}
			var self = $(ts), ir=0,v,i,j,f=[],cur,gi=ts.p.multiselect?1:0,si=ts.p.subGrid===true?1:0,addSubGridCell,ni=ts.p.rownumbers===true?1:0,arrayReader=orderedCols(gi+si+ni),objectReader=reader(frd),rowReader,len,drows,idn,rd={}, fpos, idr,rowData=[],cn=(ts.p.altRows === true) ? ts.p.altclass:"",cn1;
			ts.p.page = intNum($.jgrid.getAccessor(data,dReader.page), ts.p.page);
			ts.p.lastpage = intNum($.jgrid.getAccessor(data,dReader.total), 1);
			ts.p.records = intNum($.jgrid.getAccessor(data,dReader.records));
			ts.p.userData = $.jgrid.getAccessor(data,dReader.userdata) || {};
			if(si) {
				addSubGridCell = $.jgrid.getMethod("addSubGridCell");
			}
			if( ts.p.keyIndex===false ) {
				idn = $.isFunction(dReader.id) ? dReader.id.call(ts, data) : dReader.id; 
			} else {
				idn = ts.p.keyIndex;
			}
			if(!dReader.repeatitems) {
				f = objectReader;
				if(f.length>0 && !isNaN(idn)) {
					idn=ts.p.keyName;
				}
			}
			drows = $.jgrid.getAccessor(data,dReader.root);
			if (drows == null && $.isArray(data)) { drows = data; }
			if (!drows) { drows = []; }
			len = drows.length; i=0;
			if (len > 0 && ts.p.page <= 0) { ts.p.page = 1; }
			var rn = parseInt(ts.p.rowNum,10),br=ts.p.scroll?$.jgrid.randId():1, altr, selected=false, selr;
			if (adjust) { rn *= adjust+1; }
			if(ts.p.datatype === "local" && !ts.p.deselectAfterSort) {
				selected = true;
			}
			var afterInsRow = $.isFunction(ts.p.afterInsertRow), grpdata=[],hiderow=false, groupingPrepare;
			if(ts.p.grouping)  {
				hiderow = ts.p.groupingView.groupCollapse === true;
				groupingPrepare = $.jgrid.getMethod("groupingPrepare");
			}
			while (i<len) {
				cur = drows[i];
				idr = $.jgrid.getAccessor(cur,idn);
				if(idr === undefined) {
					if (typeof idn === "number" && ts.p.colModel[idn+gi+si+ni] != null) {
						// reread id by name
						idr = $.jgrid.getAccessor(cur,ts.p.colModel[idn+gi+si+ni].name);
					}
					if(idr === undefined) {
						idr = br+i;
						if(f.length===0){
							if(dReader.cell){
								var ccur = $.jgrid.getAccessor(cur,dReader.cell) || cur;
								idr = ccur != null && ccur[idn] !== undefined ? ccur[idn] : idr;
								ccur=null;
							}
						}
					}
				}
				idr  = ts.p.idPrefix + idr;
				altr = rcnt === 1 ? 0 : rcnt;
				cn1 = (altr+i)%2 === 1 ? cn : '';
				if( selected) {
					if( ts.p.multiselect) {
						selr = ($.inArray(idr, ts.p.selarrrow) !== -1);
					} else {
						selr = (idr === ts.p.selrow);
					}
				}
				var iStartTrTag = rowData.length;
				rowData.push("");
				if( ni ) {
					rowData.push( addRowNum(0,i,ts.p.page,ts.p.rowNum) );
				}
				if( gi ){
					rowData.push( addMulti(idr,ni,i,selr) );
				}
				if( si ) {
					rowData.push( addSubGridCell.call(self,gi+ni,i+rcnt) );
				}
				rowReader=objectReader;
				if (dReader.repeatitems) {
					if(dReader.cell) {cur = $.jgrid.getAccessor(cur,dReader.cell) || cur;}
					if ($.isArray(cur)) { rowReader=arrayReader; }
				}
				for (j=0;j<rowReader.length;j++) {
					v = $.jgrid.getAccessor(cur,rowReader[j]);
					rd[ts.p.colModel[j+gi+si+ni].name] = v;
					rowData.push( addCell(idr,v,j+gi+si+ni,i+rcnt,cur, rd) );
				}
				rowData[iStartTrTag] = constructTr(idr, hiderow, cn1, rd, cur, selr);
				rowData.push( "</tr>" );
				if(ts.p.grouping) {
					grpdata.push( rowData );
					if(!ts.p.groupingView._locgr) {
						groupingPrepare.call(self, rd, i);
					}
					rowData = [];
				}
				if(locdata || ts.p.treeGrid===true) {
					rd[locid] = $.jgrid.stripPref(ts.p.idPrefix, idr);
					ts.p.data.push(rd);
					ts.p._index[rd[locid]] = ts.p.data.length-1;
				}
				if(ts.p.gridview === false ) {
					$("#"+$.jgrid.jqID(ts.p.id)+" tbody:first").append(rowData.join(''));
					self.triggerHandler("jqGridAfterInsertRow", [idr, rd, cur]);
					if(afterInsRow) {ts.p.afterInsertRow.call(ts,idr,rd,cur);}
					rowData=[];//ari=0;
				}
				rd={};
				ir++;
				i++;
				if(ir===rn) { break; }
			}
			if(ts.p.gridview === true ) {
				fpos = ts.p.treeANode > -1 ? ts.p.treeANode: 0;
				if(ts.p.grouping) {
					if(!locdata) {
						self.jqGrid('groupingRender', grpdata, ts.p.colModel.length, ts.p.page, rn);
						grpdata = null;
					}
				} else if(ts.p.treeGrid === true && fpos > 0) {
					$(ts.rows[fpos]).after(rowData.join(''));
				} else {
					$("#"+$.jgrid.jqID(ts.p.id)+" tbody:first").append(rowData.join(''));
				}
			}
			if(ts.p.subGrid === true ) {
				try { self.jqGrid("addSubGrid",gi+ni);} catch (_){}
			}
			ts.p.totaltime = new Date() - startReq;
			if(ir>0) {
				if(ts.p.records===0) { ts.p.records=len; }
			}
			rowData = null;
			if( ts.p.treeGrid === true) {
				try {self.jqGrid("setTreeNode", fpos+1, ir+fpos+1);} catch (e) {}
			}
			if(!ts.p.treeGrid && !ts.p.scroll) {ts.grid.bDiv.scrollTop = 0;}
			ts.p.reccount=ir;
			ts.p.treeANode = -1;
			if(ts.p.userDataOnFooter) { self.jqGrid("footerData","set",ts.p.userData,true); }
			if(locdata) {
				ts.p.records = len;
				ts.p.lastpage = Math.ceil(len/ rn);
			}
			if (!more) { ts.updatepager(false,true); }
			if(locdata) {
				while (ir<len && drows[ir]) {
					cur = drows[ir];
					idr = $.jgrid.getAccessor(cur,idn);
					if(idr === undefined) {
						if (typeof idn === "number" && ts.p.colModel[idn+gi+si+ni] != null) {
							// reread id by name
							idr = $.jgrid.getAccessor(cur,ts.p.colModel[idn+gi+si+ni].name);
						}
						if(idr === undefined) {
							idr = br+ir;
							if(f.length===0){
								if(dReader.cell){
									var ccur2 = $.jgrid.getAccessor(cur,dReader.cell) || cur;
									idr = ccur2 != null && ccur2[idn] !== undefined ? ccur2[idn] : idr;
									ccur2=null;
								}
							}
						}
					}
					if(cur) {
						idr  = ts.p.idPrefix + idr;
						rowReader=objectReader;
						if (dReader.repeatitems) {
							if(dReader.cell) {cur = $.jgrid.getAccessor(cur,dReader.cell) || cur;}
							if ($.isArray(cur)) { rowReader=arrayReader; }
						}

						for (j=0;j<rowReader.length;j++) {
							rd[ts.p.colModel[j+gi+si+ni].name] = $.jgrid.getAccessor(cur,rowReader[j]);
						}
						rd[locid] = $.jgrid.stripPref(ts.p.idPrefix, idr);
						if(ts.p.grouping) {
							groupingPrepare.call(self, rd, ir );
						}
						ts.p.data.push(rd);
						ts.p._index[rd[locid]] = ts.p.data.length-1;
						rd = {};
					}
					ir++;
				}
				if(ts.p.grouping) {
					ts.p.groupingView._locgr = true;
					self.jqGrid('groupingRender', grpdata, ts.p.colModel.length, ts.p.page, rn);
					grpdata = null;
				}
			}
		},
		addLocalData = function() {
			var st = ts.p.multiSort ? [] : "", sto=[], fndsort=false, cmtypes={}, grtypes=[], grindexes=[], srcformat, sorttype, newformat;
			if(!$.isArray(ts.p.data)) {
				return;
			}
			var grpview = ts.p.grouping ? ts.p.groupingView : false, lengrp, gin;
			$.each(ts.p.colModel,function(){
				sorttype = this.sorttype || "text";
				if(sorttype === "date" || sorttype === "datetime") {
					if(this.formatter && typeof this.formatter === 'string' && this.formatter === 'date') {
						if(this.formatoptions && this.formatoptions.srcformat) {
							srcformat = this.formatoptions.srcformat;
						} else {
							srcformat = $.jgrid.formatter.date.srcformat;
						}
						if(this.formatoptions && this.formatoptions.newformat) {
							newformat = this.formatoptions.newformat;
						} else {
							newformat = $.jgrid.formatter.date.newformat;
						}
					} else {
						srcformat = newformat = this.datefmt || "Y-m-d";
					}
					cmtypes[this.name] = {"stype": sorttype, "srcfmt": srcformat,"newfmt":newformat, "sfunc": this.sortfunc || null};
				} else {
					cmtypes[this.name] = {"stype": sorttype, "srcfmt":'',"newfmt":'', "sfunc": this.sortfunc || null};
				}
				if(ts.p.grouping ) {
					for(gin =0, lengrp = grpview.groupField.length; gin< lengrp; gin++) {
						if( this.name === grpview.groupField[gin]) {
							var grindex = this.name;
							if (this.index) {
								grindex = this.index;
							}
							grtypes[gin] = cmtypes[grindex];
							grindexes[gin]= grindex;
						}
					}
				}
				if(ts.p.multiSort) {
					if(this.lso) {
						st.push(this.name);
						var tmplso= this.lso.split("-");
						sto.push( tmplso[tmplso.length-1] );
					}
				} else {
					if(!fndsort && (this.index === ts.p.sortname || this.name === ts.p.sortname)){
						st = this.name; // ???
						fndsort = true;
					}
				}
			});
			if(ts.p.treeGrid) {
				$(ts).jqGrid("SortTree", st, ts.p.sortorder, cmtypes[st].stype || 'text', cmtypes[st].srcfmt || '');
				return;
			}
			var compareFnMap = {
				'eq':function(queryObj) {return queryObj.equals;},
				'ne':function(queryObj) {return queryObj.notEquals;},
				'lt':function(queryObj) {return queryObj.less;},
				'le':function(queryObj) {return queryObj.lessOrEquals;},
				'gt':function(queryObj) {return queryObj.greater;},
				'ge':function(queryObj) {return queryObj.greaterOrEquals;},
				'cn':function(queryObj) {return queryObj.contains;},
				'nc':function(queryObj,op) {return op === "OR" ? queryObj.orNot().contains : queryObj.andNot().contains;},
				'bw':function(queryObj) {return queryObj.startsWith;},
				'bn':function(queryObj,op) {return op === "OR" ? queryObj.orNot().startsWith : queryObj.andNot().startsWith;},
				'en':function(queryObj,op) {return op === "OR" ? queryObj.orNot().endsWith : queryObj.andNot().endsWith;},
				'ew':function(queryObj) {return queryObj.endsWith;},
				'ni':function(queryObj,op) {return op === "OR" ? queryObj.orNot().equals : queryObj.andNot().equals;},
				'in':function(queryObj) {return queryObj.equals;},
				'nu':function(queryObj) {return queryObj.isNull;},
				'nn':function(queryObj,op) {return op === "OR" ? queryObj.orNot().isNull : queryObj.andNot().isNull;}

			},
			query = $.jgrid.from(ts.p.data);
			if (ts.p.ignoreCase) { query = query.ignoreCase(); }
			function tojLinq ( group ) {
				var s = 0, index, gor, ror, opr, rule;
				if (group.groups != null) {
					gor = group.groups.length && group.groupOp.toString().toUpperCase() === "OR";
					if (gor) {
						query.orBegin();
					}
					for (index = 0; index < group.groups.length; index++) {
						if (s > 0 && gor) {
							query.or();
						}
						try {
							tojLinq(group.groups[index]);
						} catch (e) {alert(e);}
						s++;
					}
					if (gor) {
						query.orEnd();
					}
				}
				if (group.rules != null) {
					//if(s>0) {
					//	var result = query.select();
					//	query = $.jgrid.from( result);
					//	if (ts.p.ignoreCase) { query = query.ignoreCase(); } 
					//}
					try{
						ror = group.rules.length && group.groupOp.toString().toUpperCase() === "OR";
						if (ror) {
							query.orBegin();
						}
						for (index = 0; index < group.rules.length; index++) {
							rule = group.rules[index];
							opr = group.groupOp.toString().toUpperCase();
							if (compareFnMap[rule.op] && rule.field ) {
								if(s > 0 && opr && opr === "OR") {
									query = query.or();
								}
								query = compareFnMap[rule.op](query, opr)(rule.field, rule.data, cmtypes[rule.field]);
							}
							s++;
						}
						if (ror) {
							query.orEnd();
						}
					} catch (g) {alert(g);}
				}
			}

			if (ts.p.search === true) {
				var srules = ts.p.postData.filters;
				if(srules) {
					if(typeof srules === "string") { srules = $.jgrid.parse(srules);}
					tojLinq( srules );
				} else {
					try {
						query = compareFnMap[ts.p.postData.searchOper](query)(ts.p.postData.searchField, ts.p.postData.searchString,cmtypes[ts.p.postData.searchField]);
					} catch (se){}
				}
			}
			if(ts.p.grouping) {
				for(gin=0; gin<lengrp;gin++) {
					query.orderBy(grindexes[gin],grpview.groupOrder[gin],grtypes[gin].stype, grtypes[gin].srcfmt);
				}
			}
			if(ts.p.multiSort) {
				$.each(st,function(i){
					query.orderBy(this, sto[i], cmtypes[this].stype, cmtypes[this].srcfmt, cmtypes[this].sfunc);
				});
			} else {
				if (st && ts.p.sortorder && fndsort) {
					if(ts.p.sortorder.toUpperCase() === "DESC") {
						query.orderBy(ts.p.sortname, "d", cmtypes[st].stype, cmtypes[st].srcfmt, cmtypes[st].sfunc);
					} else {
						query.orderBy(ts.p.sortname, "a", cmtypes[st].stype, cmtypes[st].srcfmt, cmtypes[st].sfunc);
					}
				}
			}
			var queryResults = query.select(),
			recordsperpage = parseInt(ts.p.rowNum,10),
			total = queryResults.length,
			page = parseInt(ts.p.page,10),
			totalpages = Math.ceil(total / recordsperpage),
			retresult = {};
			if((ts.p.search || ts.p.resetsearch) && ts.p.grouping && ts.p.groupingView._locgr) {
				ts.p.groupingView.groups =[];
				var j, grPrepare = $.jgrid.getMethod("groupingPrepare"), key, udc;
				if(ts.p.footerrow && ts.p.userDataOnFooter) {
					for (key in ts.p.userData) {
						if(ts.p.userData.hasOwnProperty(key)) {
							ts.p.userData[key] = 0;
						}
					}
					udc = true;
				}
				for(j=0; j<total; j++) {
					if(udc) {
						for(key in ts.p.userData){
							ts.p.userData[key] += parseFloat(queryResults[j][key] || 0);
						}
					}
					grPrepare.call($(ts),queryResults[j],j, recordsperpage );
				}
			}
			queryResults = queryResults.slice( (page-1)*recordsperpage , page*recordsperpage );
			query = null;
			cmtypes = null;
			retresult[ts.p.localReader.total] = totalpages;
			retresult[ts.p.localReader.page] = page;
			retresult[ts.p.localReader.records] = total;
			retresult[ts.p.localReader.root] = queryResults;
			retresult[ts.p.localReader.userdata] = ts.p.userData;
			queryResults = null;
			return  retresult;
		},
		updatepager = function(rn, dnd) {
			var cp, last, base, from,to,tot,fmt, pgboxes = "", sppg,
			tspg = ts.p.pager ? "_"+$.jgrid.jqID(ts.p.pager.substr(1)) : "",
			tspg_t = ts.p.toppager ? "_"+ts.p.toppager.substr(1) : "";
			base = parseInt(ts.p.page,10)-1;
			if(base < 0) { base = 0; }
			base = base*parseInt(ts.p.rowNum,10);
			to = base + ts.p.reccount;
			if (ts.p.scroll) {
				var rows = $("tbody:first > tr:gt(0)", ts.grid.bDiv);
				base = to - rows.length;
				ts.p.reccount = rows.length;
				var rh = rows.outerHeight() || ts.grid.prevRowHeight;
				if (rh) {
					var top = base * rh;
					var height = parseInt(ts.p.records,10) * rh;
					$(">div:first",ts.grid.bDiv).css({height : height}).children("div:first").css({height:top,display:top?"":"none"});
					if (ts.grid.bDiv.scrollTop == 0 && ts.p.page > 1) {
						ts.grid.bDiv.scrollTop = ts.p.rowNum * (ts.p.page - 1) * rh;
					}
				}
				ts.grid.bDiv.scrollLeft = ts.grid.hDiv.scrollLeft;
			}
			pgboxes = ts.p.pager || "";
			pgboxes += ts.p.toppager ?  (pgboxes ? "," + ts.p.toppager : ts.p.toppager) : "";
			if(pgboxes) {
				fmt = $.jgrid.formatter.integer || {};
				cp = intNum(ts.p.page);
				last = intNum(ts.p.lastpage);
				$(".selbox",pgboxes)[ this.p.useProp ? 'prop' : 'attr' ]("disabled",false);
				if(ts.p.pginput===true) {
					$('.ui-pg-input',pgboxes).val(ts.p.page);
					sppg = ts.p.toppager ? '#sp_1'+tspg+",#sp_1"+tspg_t : '#sp_1'+tspg;
					$(sppg).html($.fmatter ? $.fmatter.util.NumberFormat(ts.p.lastpage,fmt):ts.p.lastpage);

				}
				if (ts.p.viewrecords){
					if(ts.p.reccount === 0) {
						$(".ui-paging-info",pgboxes).html(ts.p.emptyrecords);
					} else {
						from = base+1;
						tot=ts.p.records;
						if($.fmatter) {
							from = $.fmatter.util.NumberFormat(from,fmt);
							to = $.fmatter.util.NumberFormat(to,fmt);
							tot = $.fmatter.util.NumberFormat(tot,fmt);
						}
						$(".ui-paging-info",pgboxes).html($.jgrid.format(ts.p.recordtext,from,to,tot));
					}
				}
				if(ts.p.pgbuttons===true) {
					if(cp<=0) {cp = last = 0;}
					if(cp===1 || cp === 0) {
						$("#first"+tspg+", #prev"+tspg).addClass('ui-state-disabled').removeClass('ui-state-hover');
						if(ts.p.toppager) { $("#first_t"+tspg_t+", #prev_t"+tspg_t).addClass('ui-state-disabled').removeClass('ui-state-hover'); }
					} else {
						$("#first"+tspg+", #prev"+tspg).removeClass('ui-state-disabled');
						if(ts.p.toppager) { $("#first_t"+tspg_t+", #prev_t"+tspg_t).removeClass('ui-state-disabled'); }
					}
					if(cp===last || cp === 0) {
						$("#next"+tspg+", #last"+tspg).addClass('ui-state-disabled').removeClass('ui-state-hover');
						if(ts.p.toppager) { $("#next_t"+tspg_t+", #last_t"+tspg_t).addClass('ui-state-disabled').removeClass('ui-state-hover'); }
					} else {
						$("#next"+tspg+", #last"+tspg).removeClass('ui-state-disabled');
						if(ts.p.toppager) { $("#next_t"+tspg_t+", #last_t"+tspg_t).removeClass('ui-state-disabled'); }
					}
				}
			}
			if(rn===true && ts.p.rownumbers === true) {
				$(">td.jqgrid-rownum",ts.rows).each(function(i){
					$(this).html(base+1+i);
				});
			}
			if(dnd && ts.p.jqgdnd) { $(ts).jqGrid('gridDnD','updateDnD');}
			$(ts).triggerHandler("jqGridGridComplete");
			if($.isFunction(ts.p.gridComplete)) {ts.p.gridComplete.call(ts);}
			$(ts).triggerHandler("jqGridAfterGridComplete");
		},
		beginReq = function() {
			ts.grid.hDiv.loading = true;
			if(ts.p.hiddengrid) { return;}
			switch(ts.p.loadui) {
				case "disable":
					break;
				case "enable":
					$("#load_"+$.jgrid.jqID(ts.p.id)).show();
					break;
				case "block":
					$("#lui_"+$.jgrid.jqID(ts.p.id)).show();
					$("#load_"+$.jgrid.jqID(ts.p.id)).show();
					break;
			}
		},
		endReq = function() {
			ts.grid.hDiv.loading = false;
			switch(ts.p.loadui) {
				case "disable":
					break;
				case "enable":
					$("#load_"+$.jgrid.jqID(ts.p.id)).hide();
					break;
				case "block":
					$("#lui_"+$.jgrid.jqID(ts.p.id)).hide();
					$("#load_"+$.jgrid.jqID(ts.p.id)).hide();
					break;
			}
		},
		populate = function (npage) {
			if(!ts.grid.hDiv.loading) {
				var pvis = ts.p.scroll && npage === false,
				prm = {}, dt, dstr, pN=ts.p.prmNames;
				if(ts.p.page <=0) { ts.p.page = Math.min(1,ts.p.lastpage); }
				if(pN.search !== null) {prm[pN.search] = ts.p.search;} if(pN.nd !== null) {prm[pN.nd] = new Date().getTime();}
				if(pN.rows !== null) {prm[pN.rows]= ts.p.rowNum;} if(pN.page !== null) {prm[pN.page]= ts.p.page;}
				if(pN.sort !== null) {prm[pN.sort]= ts.p.sortname;} if(pN.order !== null) {prm[pN.order]= ts.p.sortorder;}
				if(ts.p.rowTotal !== null && pN.totalrows !== null) { prm[pN.totalrows]= ts.p.rowTotal; }
				var lcf = $.isFunction(ts.p.loadComplete), lc = lcf ? ts.p.loadComplete : null;
				var adjust = 0;
				npage = npage || 1;
				if (npage > 1) {
					if(pN.npage !== null) {
						prm[pN.npage] = npage;
						adjust = npage - 1;
						npage = 1;
					} else {
						lc = function(req) {
							ts.p.page++;
							ts.grid.hDiv.loading = false;
							if (lcf) {
								ts.p.loadComplete.call(ts,req);
							}
							populate(npage-1);
						};
					}
				} else if (pN.npage !== null) {
					delete ts.p.postData[pN.npage];
				}
				if(ts.p.grouping) {
					$(ts).jqGrid('groupingSetup');
					var grp = ts.p.groupingView, gi, gs="";
					for(gi=0;gi<grp.groupField.length;gi++) {
						var index = grp.groupField[gi];
						$.each(ts.p.colModel, function(cmIndex, cmValue) {
							if (cmValue.name === index && cmValue.index){
								index = cmValue.index;
							}
						} );
						gs += index +" "+grp.groupOrder[gi]+", ";
					}
					prm[pN.sort] = gs + prm[pN.sort];
				}
				$.extend(ts.p.postData,prm);
				var rcnt = !ts.p.scroll ? 1 : ts.rows.length-1;
				var bfr = $(ts).triggerHandler("jqGridBeforeRequest");
				if (bfr === false || bfr === 'stop') { return; }
				if ($.isFunction(ts.p.datatype)) { ts.p.datatype.call(ts,ts.p.postData,"load_"+ts.p.id, rcnt, npage, adjust); return;}
				if ($.isFunction(ts.p.beforeRequest)) {
					bfr = ts.p.beforeRequest.call(ts);
					if(bfr === undefined) { bfr = true; }
					if ( bfr === false ) { return; }
				}
				dt = ts.p.datatype.toLowerCase();
				switch(dt)
				{
				case "json":
				case "jsonp":
				case "xml":
				case "script":
					$.ajax($.extend({
						url:ts.p.url,
						type:ts.p.mtype,
						dataType: dt ,
						data: $.isFunction(ts.p.serializeGridData)? ts.p.serializeGridData.call(ts,ts.p.postData) : ts.p.postData,
						success:function(data,st, xhr) {
							if ($.isFunction(ts.p.beforeProcessing)) {
								if (ts.p.beforeProcessing.call(ts, data, st, xhr) === false) {
									endReq();
									return;
								}
							}
							if(dt === "xml") { addXmlData(data,ts.grid.bDiv,rcnt,npage>1,adjust); }
							else { addJSONData(data,ts.grid.bDiv,rcnt,npage>1,adjust); }
							$(ts).triggerHandler("jqGridLoadComplete", [data]);
							if(lc) { lc.call(ts,data); }
							$(ts).triggerHandler("jqGridAfterLoadComplete", [data]);
							if (pvis) { ts.grid.populateVisible(); }
							if( ts.p.loadonce || ts.p.treeGrid) {ts.p.datatype = "local";}
							data=null;
							if (npage === 1) { endReq(); }
						},
						error:function(xhr,st,err){
							if($.isFunction(ts.p.loadError)) { ts.p.loadError.call(ts,xhr,st,err); }
							if (npage === 1) { endReq(); }
							xhr=null;
						},
						beforeSend: function(xhr, settings ){
							var gotoreq = true;
							if($.isFunction(ts.p.loadBeforeSend)) {
								gotoreq = ts.p.loadBeforeSend.call(ts,xhr, settings); 
							}
							if(gotoreq === undefined) { gotoreq = true; }
							if(gotoreq === false) {
								return false;
							}
								beginReq();
							}
					},$.jgrid.ajaxOptions, ts.p.ajaxGridOptions));
				break;
				case "xmlstring":
					beginReq();
					dstr = typeof ts.p.datastr !== 'string' ? ts.p.datastr : $.parseXML(ts.p.datastr);
					addXmlData(dstr,ts.grid.bDiv);
					$(ts).triggerHandler("jqGridLoadComplete", [dstr]);
					if(lcf) {ts.p.loadComplete.call(ts,dstr);}
					$(ts).triggerHandler("jqGridAfterLoadComplete", [dstr]);
					ts.p.datatype = "local";
					ts.p.datastr = null;
					endReq();
				break;
				case "jsonstring":
					beginReq();
					if(typeof ts.p.datastr === 'string') { dstr = $.jgrid.parse(ts.p.datastr); }
					else { dstr = ts.p.datastr; }
					addJSONData(dstr,ts.grid.bDiv);
					$(ts).triggerHandler("jqGridLoadComplete", [dstr]);
					if(lcf) {ts.p.loadComplete.call(ts,dstr);}
					$(ts).triggerHandler("jqGridAfterLoadComplete", [dstr]);
					ts.p.datatype = "local";
					ts.p.datastr = null;
					endReq();
				break;
				case "local":
				case "clientside":
					beginReq();
					ts.p.datatype = "local";
					var req = addLocalData();
					addJSONData(req,ts.grid.bDiv,rcnt,npage>1,adjust);
					$(ts).triggerHandler("jqGridLoadComplete", [req]);
					if(lc) { lc.call(ts,req); }
					$(ts).triggerHandler("jqGridAfterLoadComplete", [req]);
					if (pvis) { ts.grid.populateVisible(); }
					endReq();
				break;
				}
			}
		},
		setHeadCheckBox = function ( checked ) {
			$('#cb_'+$.jgrid.jqID(ts.p.id),ts.grid.hDiv)[ts.p.useProp ? 'prop': 'attr']("checked", checked);
			var fid = ts.p.frozenColumns ? ts.p.id+"_frozen" : "";
			if(fid) {
				$('#cb_'+$.jgrid.jqID(ts.p.id),ts.grid.fhDiv)[ts.p.useProp ? 'prop': 'attr']("checked", checked);
			}
		},
		setPager = function (pgid, tp){
			// TBD - consider escaping pgid with pgid = $.jgrid.jqID(pgid);
			var sep = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>",
			pginp = "",
			pgl="<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",
			str="", pgcnt, lft, cent, rgt, twd, tdw, i,
			clearVals = function(onpaging){
				var ret;
				if ($.isFunction(ts.p.onPaging) ) { ret = ts.p.onPaging.call(ts,onpaging); }
				if(ret==='stop') {return false;}
				ts.p.selrow = null;
				if(ts.p.multiselect) {ts.p.selarrrow =[]; setHeadCheckBox( false );}
				ts.p.savedRow = [];
				return true;
			};
			pgid = pgid.substr(1);
			tp += "_" + pgid;
			pgcnt = "pg_"+pgid;
			lft = pgid+"_left"; cent = pgid+"_center"; rgt = pgid+"_right";
			$("#"+$.jgrid.jqID(pgid) )
			.append("<div id='"+pgcnt+"' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='"+lft+"' align='left'></td><td id='"+cent+"' align='center' style='white-space:pre;'></td><td id='"+rgt+"' align='right'></td></tr></tbody></table></div>")
			.attr("dir","ltr"); //explicit setting
			if(ts.p.rowList.length >0){
				str = "<td dir='"+dir+"'>";
				str +="<select class='ui-pg-selbox' role='listbox'>";
				for(i=0;i<ts.p.rowList.length;i++){
					str +="<option role=\"option\" value=\""+ts.p.rowList[i]+"\""+((ts.p.rowNum === ts.p.rowList[i])?" selected=\"selected\"":"")+">"+ts.p.rowList[i]+"</option>";
				}
				str +="</select></td>";
			}
			if(dir==="rtl") { pgl += str; }
			if(ts.p.pginput===true) { pginp= "<td dir='"+dir+"'>"+$.jgrid.format(ts.p.pgtext || "","<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>","<span id='sp_1_"+$.jgrid.jqID(pgid)+"'></span>")+"</td>";}
			if(ts.p.pgbuttons===true) {
				var po=["first"+tp,"prev"+tp, "next"+tp,"last"+tp]; if(dir==="rtl") { po.reverse(); }
				pgl += "<td id='"+po[0]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>";
				pgl += "<td id='"+po[1]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>";
				pgl += pginp !== "" ? sep+pginp+sep:"";
				pgl += "<td id='"+po[2]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>";
				pgl += "<td id='"+po[3]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>";
			} else if (pginp !== "") { pgl += pginp; }
			if(dir==="ltr") { pgl += str; }
			pgl += "</tr></tbody></table>";
			if(ts.p.viewrecords===true) {$("td#"+pgid+"_"+ts.p.recordpos,"#"+pgcnt).append("<div dir='"+dir+"' style='text-align:"+ts.p.recordpos+"' class='ui-paging-info'></div>");}
			$("td#"+pgid+"_"+ts.p.pagerpos,"#"+pgcnt).append(pgl);
			tdw = $(".ui-jqgrid").css("font-size") || "11px";
			$(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+tdw+";visibility:hidden;' ></div>");
			twd = $(pgl).clone().appendTo("#testpg").width();
			$("#testpg").remove();
			if(twd > 0) {
				if(pginp !== "") { twd += 50; } //should be param
				$("td#"+pgid+"_"+ts.p.pagerpos,"#"+pgcnt).width(twd);
			}
			ts.p._nvtd = [];
			ts.p._nvtd[0] = twd ? Math.floor((ts.p.width - twd)/2) : Math.floor(ts.p.width/3);
			ts.p._nvtd[1] = 0;
			pgl=null;
			$('.ui-pg-selbox',"#"+pgcnt).bind('change',function() {
				if(!clearVals('records')) { return false; }
				ts.p.page = Math.round(ts.p.rowNum*(ts.p.page-1)/this.value-0.5)+1;
				ts.p.rowNum = this.value;
				if(ts.p.pager) { $('.ui-pg-selbox',ts.p.pager).val(this.value); }
				if(ts.p.toppager) { $('.ui-pg-selbox',ts.p.toppager).val(this.value); }
				populate();
				return false;
			});
			if(ts.p.pgbuttons===true) {
			$(".ui-pg-button","#"+pgcnt).hover(function(){
				if($(this).hasClass('ui-state-disabled')) {
					this.style.cursor='default';
				} else {
					$(this).addClass('ui-state-hover');
					this.style.cursor='pointer';
				}
			},function() {
				if(!$(this).hasClass('ui-state-disabled')) {
					$(this).removeClass('ui-state-hover');
					this.style.cursor= "default";
				}
			});
			$("#first"+$.jgrid.jqID(tp)+", #prev"+$.jgrid.jqID(tp)+", #next"+$.jgrid.jqID(tp)+", #last"+$.jgrid.jqID(tp)).click( function() {
				if ($(this).hasClass("ui-state-disabled")) {
					return false;
				}
				var cp = intNum(ts.p.page,1),
				last = intNum(ts.p.lastpage,1), selclick = false,
				fp=true, pp=true, np=true,lp=true;
				if(last ===0 || last===1) {fp=false;pp=false;np=false;lp=false; }
				else if( last>1 && cp >=1) {
					if( cp === 1) { fp=false; pp=false; }
					//else if( cp>1 && cp <last){ }
					else if( cp===last){ np=false;lp=false; }
				} else if( last>1 && cp===0 ) { np=false;lp=false; cp=last-1;}
				if(!clearVals(this.id)) { return false; }
				if( this.id === 'first'+tp && fp ) { ts.p.page=1; selclick=true;}
				if( this.id === 'prev'+tp && pp) { ts.p.page=(cp-1); selclick=true;}
				if( this.id === 'next'+tp && np) { ts.p.page=(cp+1); selclick=true;}
				if( this.id === 'last'+tp && lp) { ts.p.page=last; selclick=true;}
				if(selclick) {
					populate();
				}
				return false;
			});
			}
			if(ts.p.pginput===true) {
			$('input.ui-pg-input',"#"+pgcnt).keypress( function(e) {
				var key = e.charCode || e.keyCode || 0;
				if(key === 13) {
					if(!clearVals('user')) { return false; }
					$(this).val( intNum( $(this).val(), 1));
					ts.p.page = ($(this).val()>0) ? $(this).val():ts.p.page;
					populate();
					return false;
				}
				return this;
			});
			}
		},
		multiSort = function(iCol, obj ) {
			var splas, sort="", cm = ts.p.colModel, fs=false, ls, 
					selTh = ts.p.frozenColumns ?  obj : ts.grid.headers[iCol].el, so="";
			$("span.ui-grid-ico-sort",selTh).addClass('ui-state-disabled');
			$(selTh).attr("aria-selected","false");

			if(cm[iCol].lso) {
				if(cm[iCol].lso==="asc") {
					cm[iCol].lso += "-desc";
					so = "desc";
				} else if(cm[iCol].lso==="desc") {
					cm[iCol].lso += "-asc";
					so = "asc";
				} else if(cm[iCol].lso==="asc-desc" || cm[iCol].lso==="desc-asc") {
					cm[iCol].lso="";
				}
			} else {
				cm[iCol].lso = so = cm[iCol].firstsortorder || 'asc';
			}
			if( so ) {
				$("span.s-ico",selTh).show();
				$("span.ui-icon-"+so,selTh).removeClass('ui-state-disabled');
				$(selTh).attr("aria-selected","true");
			} else {
				if(!ts.p.viewsortcols[0]) {
					$("span.s-ico",selTh).hide();
				}
			}
			ts.p.sortorder = "";
			$.each(cm, function(i){
				if(this.lso) {
					if(i>0 && fs) {
						sort += ", ";
					}
					splas = this.lso.split("-");
					sort += cm[i].index || cm[i].name;
					sort += " "+splas[splas.length-1];
					fs = true;
					ts.p.sortorder = splas[splas.length-1];
				}
			});
			ls = sort.lastIndexOf(ts.p.sortorder);
			sort = sort.substring(0, ls);
			ts.p.sortname = sort;
		},
		sortData = function (index, idxcol,reload,sor, obj){
			if(!ts.p.colModel[idxcol].sortable) { return; }
			if(ts.p.savedRow.length > 0) {return;}
			if(!reload) {
				if( ts.p.lastsort === idxcol ) {
					if( ts.p.sortorder === 'asc') {
						ts.p.sortorder = 'desc';
					} else if(ts.p.sortorder === 'desc') { ts.p.sortorder = 'asc';}
				} else { ts.p.sortorder = ts.p.colModel[idxcol].firstsortorder || 'asc'; }
				ts.p.page = 1;
			}
			if(ts.p.multiSort) {
				multiSort( idxcol, obj);
			} else {
				if(sor) {
					if(ts.p.lastsort === idxcol && ts.p.sortorder === sor && !reload) { return; }
					ts.p.sortorder = sor;
				}
				var previousSelectedTh = ts.grid.headers[ts.p.lastsort].el, newSelectedTh = ts.p.frozenColumns ?  obj : ts.grid.headers[idxcol].el;

				$("span.ui-grid-ico-sort",previousSelectedTh).addClass('ui-state-disabled');
				$(previousSelectedTh).attr("aria-selected","false");
				if(ts.p.frozenColumns) {
					ts.grid.fhDiv.find("span.ui-grid-ico-sort").addClass('ui-state-disabled');
					ts.grid.fhDiv.find("th").attr("aria-selected","false");
				}
				$("span.ui-icon-"+ts.p.sortorder,newSelectedTh).removeClass('ui-state-disabled');
				$(newSelectedTh).attr("aria-selected","true");
				if(!ts.p.viewsortcols[0]) {
					if(ts.p.lastsort !== idxcol) {
						if(ts.p.frozenColumns){
							ts.grid.fhDiv.find("span.s-ico").hide();
						}
						$("span.s-ico",previousSelectedTh).hide();
						$("span.s-ico",newSelectedTh).show();
					}
				}
				index = index.substring(5 + ts.p.id.length + 1); // bad to be changed!?!
				ts.p.sortname = ts.p.colModel[idxcol].index || index;
			}
			if ($(ts).triggerHandler("jqGridSortCol", [ts.p.sortname, idxcol, ts.p.sortorder]) === 'stop') {
				ts.p.lastsort = idxcol;
				return;
			}
			if($.isFunction(ts.p.onSortCol)) {if (ts.p.onSortCol.call(ts, ts.p.sortname, idxcol, ts.p.sortorder)==='stop') {ts.p.lastsort = idxcol; return;}}
			if(ts.p.datatype === "local") {
				if(ts.p.deselectAfterSort) {$(ts).jqGrid("resetSelection");}
			} else {
				ts.p.selrow = null;
				if(ts.p.multiselect){setHeadCheckBox( false );}
				ts.p.selarrrow =[];
				ts.p.savedRow =[];
			}
			if(ts.p.scroll) {
				var sscroll = ts.grid.bDiv.scrollLeft;
				emptyRows.call(ts, true, false);
				ts.grid.hDiv.scrollLeft = sscroll;
			}
			if(ts.p.subGrid && ts.p.datatype === 'local') {
				$("td.sgexpanded","#"+$.jgrid.jqID(ts.p.id)).each(function(){
					$(this).trigger("click");
				});
			}
			populate();
			ts.p.lastsort = idxcol;
			if(ts.p.sortname !== index && idxcol) {ts.p.lastsort = idxcol;}
		},
		setColWidth = function () {
			var initwidth = 0, brd=$.jgrid.cell_width? 0: intNum(ts.p.cellLayout,0), vc=0, lvc, scw=intNum(ts.p.scrollOffset,0),cw,hs=false,aw,gw=0,cr;
			$.each(ts.p.colModel, function() {
				if(this.hidden === undefined) {this.hidden=false;}
				if(ts.p.grouping && ts.p.autowidth) {
					var ind = $.inArray(this.name, ts.p.groupingView.groupField);
					if(ind >= 0 && ts.p.groupingView.groupColumnShow.length > ind) {
						this.hidden = !ts.p.groupingView.groupColumnShow[ind];
					}
				}
				this.widthOrg = cw = intNum(this.width,0);
				if(this.hidden===false){
					initwidth += cw+brd;
					if(this.fixed) {
						gw += cw+brd;
					} else {
						vc++;
					}
				}
			});
			if(isNaN(ts.p.width)) {
				ts.p.width  = initwidth + ((ts.p.shrinkToFit ===false && !isNaN(ts.p.height)) ? scw : 0);
			}
			grid.width = ts.p.width;
			ts.p.tblwidth = initwidth;
			if(ts.p.shrinkToFit ===false && ts.p.forceFit === true) {ts.p.forceFit=false;}
			if(ts.p.shrinkToFit===true && vc > 0) {
				aw = grid.width-brd*vc-gw;
				if(!isNaN(ts.p.height)) {
					aw -= scw;
					hs = true;
				}
				initwidth =0;
				$.each(ts.p.colModel, function(i) {
					if(this.hidden === false && !this.fixed){
						cw = Math.round(aw*this.width/(ts.p.tblwidth-brd*vc-gw));
						this.width =cw;
						initwidth += cw;
						lvc = i;
					}
				});
				cr =0;
				if (hs) {
					if(grid.width-gw-(initwidth+brd*vc) !== scw){
						cr = grid.width-gw-(initwidth+brd*vc)-scw;
					}
				} else if(!hs && Math.abs(grid.width-gw-(initwidth+brd*vc)) !== 1) {
					cr = grid.width-gw-(initwidth+brd*vc);
				}
				ts.p.colModel[lvc].width += cr;
				ts.p.tblwidth = initwidth+cr+brd*vc+gw;
				if(ts.p.tblwidth > ts.p.width) {
					ts.p.colModel[lvc].width -= (ts.p.tblwidth - parseInt(ts.p.width,10));
					ts.p.tblwidth = ts.p.width;
				}
			}
		},
		nextVisible= function(iCol) {
			var ret = iCol, j=iCol, i;
			for (i = iCol+1;i<ts.p.colModel.length;i++){
				if(ts.p.colModel[i].hidden !== true ) {
					j=i; break;
				}
			}
			return j-ret;
		},
		getOffset = function (iCol) {
			var $th = $(ts.grid.headers[iCol].el), ret = [$th.position().left + $th.outerWidth()];
			if(ts.p.direction==="rtl") { ret[0] = ts.p.width - ret[0]; }
			ret[0] -= ts.grid.bDiv.scrollLeft;
			ret.push($(ts.grid.hDiv).position().top);
			ret.push($(ts.grid.bDiv).offset().top - $(ts.grid.hDiv).offset().top + $(ts.grid.bDiv).height());
			return ret;
		},
		getColumnHeaderIndex = function (th) {
			var i, headers = ts.grid.headers, ci = $.jgrid.getCellIndex(th);
			for (i = 0; i < headers.length; i++) {
				if (th === headers[i].el) {
					ci = i;
					break;
				}
			}
			return ci;
		};
		this.p.id = this.id;
		if ($.inArray(ts.p.multikey,sortkeys) === -1 ) {ts.p.multikey = false;}
		ts.p.keyIndex=false;
		ts.p.keyName=false;
		for (i=0; i<ts.p.colModel.length;i++) {
			ts.p.colModel[i] = $.extend(true, {}, ts.p.cmTemplate, ts.p.colModel[i].template || {}, ts.p.colModel[i]);
			if (ts.p.keyIndex === false && ts.p.colModel[i].key===true) {
				ts.p.keyIndex = i;
			}
		}
		ts.p.sortorder = ts.p.sortorder.toLowerCase();
		$.jgrid.cell_width = $.jgrid.cellWidth();
		if(ts.p.grouping===true) {
			ts.p.scroll = false;
			ts.p.rownumbers = false;
			//ts.p.subGrid = false; expiremental
			ts.p.treeGrid = false;
			ts.p.gridview = true;
		}
		if(this.p.treeGrid === true) {
			try { $(this).jqGrid("setTreeGrid");} catch (_) {}
			if(ts.p.datatype !== "local") { ts.p.localReader = {id: "_id_"};	}
		}
		if(this.p.subGrid) {
			try { $(ts).jqGrid("setSubGrid");} catch (s){}
		}
		if(this.p.multiselect) {
			this.p.colNames.unshift("<input role='checkbox' id='cb_"+this.p.id+"' class='cbox' type='checkbox'/>");
			this.p.colModel.unshift({name:'cb',width:$.jgrid.cell_width ? ts.p.multiselectWidth+ts.p.cellLayout : ts.p.multiselectWidth,sortable:false,resizable:false,hidedlg:true,search:false,align:'center',fixed:true});
		}
		if(this.p.rownumbers) {
			this.p.colNames.unshift("");
			this.p.colModel.unshift({name:'rn',width:ts.p.rownumWidth,sortable:false,resizable:false,hidedlg:true,search:false,align:'center',fixed:true});
		}
		ts.p.xmlReader = $.extend(true,{
			root: "rows",
			row: "row",
			page: "rows>page",
			total: "rows>total",
			records : "rows>records",
			repeatitems: true,
			cell: "cell",
			id: "[id]",
			userdata: "userdata",
			subgrid: {root:"rows", row: "row", repeatitems: true, cell:"cell"}
		}, ts.p.xmlReader);
		ts.p.jsonReader = $.extend(true,{
			root: "rows",
			page: "page",
			total: "total",
			records: "records",
			repeatitems: true,
			cell: "cell",
			id: "id",
			userdata: "userdata",
			subgrid: {root:"rows", repeatitems: true, cell:"cell"}
		},ts.p.jsonReader);
		ts.p.localReader = $.extend(true,{
			root: "rows",
			page: "page",
			total: "total",
			records: "records",
			repeatitems: false,
			cell: "cell",
			id: "id",
			userdata: "userdata",
			subgrid: {root:"rows", repeatitems: true, cell:"cell"}
		},ts.p.localReader);
		if(ts.p.scroll){
			ts.p.pgbuttons = false; ts.p.pginput=false; ts.p.rowList=[];
		}
		if(ts.p.data.length) { refreshIndex(); }
		var thead = "<thead><tr class='ui-jqgrid-labels' role='rowheader'>",
		tdc, idn, w, res, sort,
		td, ptr, tbody, imgs,iac="",idc="",sortarr=[], sortord=[], sotmp=[];
		if(ts.p.shrinkToFit===true && ts.p.forceFit===true) {
			for (i=ts.p.colModel.length-1;i>=0;i--){
				if(!ts.p.colModel[i].hidden) {
					ts.p.colModel[i].resizable=false;
					break;
				}
			}
		}
		if(ts.p.viewsortcols[1] === 'horizontal') {iac=" ui-i-asc";idc=" ui-i-desc";}
		tdc = isMSIE ?  "class='ui-th-div-ie'" :"";
		imgs = "<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc"+iac+" ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-"+dir+"'></span>";
		imgs += "<span sort='desc' class='ui-grid-ico-sort ui-icon-desc"+idc+" ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-"+dir+"'></span></span>";
		if(ts.p.multiSort) {
			sortarr = ts.p.sortname.split(",");
			for (i=0; i<sortarr.length; i++) {
				sotmp = $.trim(sortarr[i]).split(" ");
				sortarr[i] = $.trim(sotmp[0]);
				sortord[i] = sotmp[1] ? $.trim(sotmp[1]) : ts.p.sortorder || "asc";
			}
		}
		for(i=0;i<this.p.colNames.length;i++){
			var tooltip = ts.p.headertitles ? (" title=\""+$.jgrid.stripHtml(ts.p.colNames[i])+"\"") :"";
			thead += "<th id='"+ts.p.id+"_"+ts.p.colModel[i].name+"' role='columnheader' class='ui-state-default ui-th-column ui-th-"+dir+"'"+ tooltip+">";
			idn = ts.p.colModel[i].index || ts.p.colModel[i].name;
			thead += "<div id='jqgh_"+ts.p.id+"_"+ts.p.colModel[i].name+"' "+tdc+">"+ts.p.colNames[i];
			if(!ts.p.colModel[i].width)  { ts.p.colModel[i].width = 150; }
			else { ts.p.colModel[i].width = parseInt(ts.p.colModel[i].width,10); }
			if(typeof ts.p.colModel[i].title !== "boolean") { ts.p.colModel[i].title = true; }
			ts.p.colModel[i].lso = "";
			if (idn === ts.p.sortname) {
				ts.p.lastsort = i;
			}
			if(ts.p.multiSort) {
				sotmp = $.inArray(idn,sortarr);
				if( sotmp !== -1 ) {
					ts.p.colModel[i].lso = sortord[sotmp];
				}
			}
			thead += imgs+"</div></th>";
		}
		thead += "</tr></thead>";
		imgs = null;
		$(this).append(thead);
		$("thead tr:first th",this).hover(function(){$(this).addClass('ui-state-hover');},function(){$(this).removeClass('ui-state-hover');});
		if(this.p.multiselect) {
			var emp=[], chk;
			$('#cb_'+$.jgrid.jqID(ts.p.id),this).bind('click',function(){
				ts.p.selarrrow = [];
				var froz = ts.p.frozenColumns === true ? ts.p.id + "_frozen" : "";
				if (this.checked) {
					$(ts.rows).each(function(i) {
						if (i>0) {
							if(!$(this).hasClass("ui-subgrid") && !$(this).hasClass("jqgroup") && !$(this).hasClass('ui-state-disabled')){
								$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(this.id) )[ts.p.useProp ? 'prop': 'attr']("checked",true);
								$(this).addClass("ui-state-highlight").attr("aria-selected","true");  
								ts.p.selarrrow.push(this.id);
								ts.p.selrow = this.id;
								if(froz) {
									$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(this.id), ts.grid.fbDiv )[ts.p.useProp ? 'prop': 'attr']("checked",true);
									$("#"+$.jgrid.jqID(this.id), ts.grid.fbDiv).addClass("ui-state-highlight");
								}
							}
						}
					});
					chk=true;
					emp=[];
				}
				else {
					$(ts.rows).each(function(i) {
						if(i>0) {
							if(!$(this).hasClass("ui-subgrid") && !$(this).hasClass('ui-state-disabled')){
								$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(this.id) )[ts.p.useProp ? 'prop': 'attr']("checked", false);
								$(this).removeClass("ui-state-highlight").attr("aria-selected","false");
								emp.push(this.id);
								if(froz) {
									$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(this.id), ts.grid.fbDiv )[ts.p.useProp ? 'prop': 'attr']("checked",false);
									$("#"+$.jgrid.jqID(this.id), ts.grid.fbDiv).removeClass("ui-state-highlight");
								}
							}
						}
					});
					ts.p.selrow = null;
					chk=false;
				}
				$(ts).triggerHandler("jqGridSelectAll", [chk ? ts.p.selarrrow : emp, chk]);
				if($.isFunction(ts.p.onSelectAll)) {ts.p.onSelectAll.call(ts, chk ? ts.p.selarrrow : emp,chk);}
			});
		}

		if(ts.p.autowidth===true) {
			var pw = $(eg).innerWidth();
			ts.p.width = pw > 0?  pw: 'nw';
		}
		setColWidth();
		$(eg).css("width",grid.width+"px").append("<div class='ui-jqgrid-resize-mark' id='rs_m"+ts.p.id+"'>&#160;</div>");
		$(gv).css("width",grid.width+"px");
		thead = $("thead:first",ts).get(0);
		var	tfoot = "";
		if(ts.p.footerrow) { tfoot += "<table role='grid' style='width:"+ts.p.tblwidth+"px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-"+dir+"'>"; }
		var thr = $("tr:first",thead),
		firstr = "<tr class='jqgfirstrow' role='row' style='height:auto'>";
		ts.p.disableClick=false;
		$("th",thr).each(function ( j ) {
			w = ts.p.colModel[j].width;
			if(ts.p.colModel[j].resizable === undefined) {ts.p.colModel[j].resizable = true;}
			if(ts.p.colModel[j].resizable){
				res = document.createElement("span");
				$(res).html("&#160;").addClass('ui-jqgrid-resize ui-jqgrid-resize-'+dir)
				.css("cursor","col-resize");
				$(this).addClass(ts.p.resizeclass);
			} else {
				res = "";
			}
			$(this).css("width",w+"px").prepend(res);
			res = null;
			var hdcol = "";
			if( ts.p.colModel[j].hidden ) {
				$(this).css("display","none");
				hdcol = "display:none;";
			}
			firstr += "<td role='gridcell' style='height:0px;width:"+w+"px;"+hdcol+"'></td>";
			grid.headers[j] = { width: w, el: this };
			sort = ts.p.colModel[j].sortable;
			if( typeof sort !== 'boolean') {ts.p.colModel[j].sortable =  true; sort=true;}
			var nm = ts.p.colModel[j].name;
			if( !(nm === 'cb' || nm==='subgrid' || nm==='rn') ) {
				if(ts.p.viewsortcols[2]){
					$(">div",this).addClass('ui-jqgrid-sortable');
				}
			}
			if(sort) {
				if(ts.p.multiSort) {
					if(ts.p.viewsortcols[0]) {
						$("div span.s-ico",this).show(); 
						if(ts.p.colModel[j].lso){ 
							$("div span.ui-icon-"+ts.p.colModel[j].lso,this).removeClass("ui-state-disabled");
						}
					} else if( ts.p.colModel[j].lso) {
						$("div span.s-ico",this).show();
						$("div span.ui-icon-"+ts.p.colModel[j].lso,this).removeClass("ui-state-disabled");
					}
				} else {
					if(ts.p.viewsortcols[0]) {$("div span.s-ico",this).show(); if(j===ts.p.lastsort){ $("div span.ui-icon-"+ts.p.sortorder,this).removeClass("ui-state-disabled");}}
					else if( j === ts.p.lastsort) {$("div span.s-ico",this).show();$("div span.ui-icon-"+ts.p.sortorder,this).removeClass("ui-state-disabled");}
				}
			}
			if(ts.p.footerrow) { tfoot += "<td role='gridcell' "+formatCol(j,0,'', null, '', false)+">&#160;</td>"; }
		}).mousedown(function(e) {
			if ($(e.target).closest("th>span.ui-jqgrid-resize").length !== 1) { return; }
			var ci = getColumnHeaderIndex(this);
			if(ts.p.forceFit===true) {ts.p.nv= nextVisible(ci);}
			grid.dragStart(ci, e, getOffset(ci));
			return false;
		}).click(function(e) {
			if (ts.p.disableClick) {
				ts.p.disableClick = false;
				return false;
			}
			var s = "th>div.ui-jqgrid-sortable",r,d;
			if (!ts.p.viewsortcols[2]) { s = "th>div>span>span.ui-grid-ico-sort"; }
			var t = $(e.target).closest(s);
			if (t.length !== 1) { return; }
			var ci;
			if(ts.p.frozenColumns) {
				var tid =  $(this)[0].id.substring( ts.p.id.length + 1 );
				$(ts.p.colModel).each(function(i){
					if (this.name === tid) {
						ci = i;return false;
					}
				});
			} else {
				ci = getColumnHeaderIndex(this);
			}
			if (!ts.p.viewsortcols[2]) { r=true;d=t.attr("sort"); }
			if(ci != null){
				sortData( $('div',this)[0].id, ci, r, d, this);
			}
			return false;
		});
		if (ts.p.sortable && $.fn.sortable) {
			try {
				$(ts).jqGrid("sortableColumns", thr);
			} catch (e){}
		}
		if(ts.p.footerrow) { tfoot += "</tr></tbody></table>"; }
		firstr += "</tr>";
		tbody = document.createElement("tbody");
		this.appendChild(tbody);
		$(this).addClass('ui-jqgrid-btable').append(firstr);
		firstr = null;
		var hTable = $("<table class='ui-jqgrid-htable' style='width:"+ts.p.tblwidth+"px' role='grid' aria-labelledby='gbox_"+this.id+"' cellspacing='0' cellpadding='0' border='0'></table>").append(thead),
		hg = (ts.p.caption && ts.p.hiddengrid===true) ? true : false,
		hb = $("<div class='ui-jqgrid-hbox" + (dir==="rtl" ? "-rtl" : "" )+"'></div>");
		thead = null;
		grid.hDiv = document.createElement("div");
		$(grid.hDiv)
			.css({ width: grid.width+"px"})
			.addClass("ui-state-default ui-jqgrid-hdiv")
			.append(hb);
		$(hb).append(hTable);
		hTable = null;
		if(hg) { $(grid.hDiv).hide(); }
		if(ts.p.pager){
			// TBD -- escape ts.p.pager here?
			if(typeof ts.p.pager === "string") {if(ts.p.pager.substr(0,1) !== "#") { ts.p.pager = "#"+ts.p.pager;} }
			else { ts.p.pager = "#"+ $(ts.p.pager).attr("id");}
			$(ts.p.pager).css({width: grid.width+"px"}).addClass('ui-state-default ui-jqgrid-pager ui-corner-bottom').appendTo(eg);
			if(hg) {$(ts.p.pager).hide();}
			setPager(ts.p.pager,'');
		}
		if( ts.p.cellEdit === false && ts.p.hoverrows === true) {
		$(ts).bind('mouseover',function(e) {
			ptr = $(e.target).closest("tr.jqgrow");
			if($(ptr).attr("class") !== "ui-subgrid") {
				$(ptr).addClass("ui-state-hover");
			}
		}).bind('mouseout',function(e) {
			ptr = $(e.target).closest("tr.jqgrow");
			$(ptr).removeClass("ui-state-hover");
		});
		}
		var ri,ci, tdHtml;
		$(ts).before(grid.hDiv).click(function(e) {
			td = e.target;
			ptr = $(td,ts.rows).closest("tr.jqgrow");
			if($(ptr).length === 0 || ptr[0].className.indexOf( 'ui-state-disabled' ) > -1 || ($(td,ts).closest("table.ui-jqgrid-btable").attr('id') || '').replace("_frozen","") !== ts.id ) {
				return this;
			}
			var scb = $(td).hasClass("cbox"),
			cSel = $(ts).triggerHandler("jqGridBeforeSelectRow", [ptr[0].id, e]);
			cSel = (cSel === false || cSel === 'stop') ? false : true;
			if(cSel && $.isFunction(ts.p.beforeSelectRow)) { cSel = ts.p.beforeSelectRow.call(ts,ptr[0].id, e); }
			if (td.tagName === 'A' || ((td.tagName === 'INPUT' || td.tagName === 'TEXTAREA' || td.tagName === 'OPTION' || td.tagName === 'SELECT' ) && !scb) ) { return; }
			if(cSel === true) {
				ri = ptr[0].id;
				ci = $.jgrid.getCellIndex(td);
				tdHtml = $(td).closest("td,th").html();
				$(ts).triggerHandler("jqGridCellSelect", [ri,ci,tdHtml,e]);
				if($.isFunction(ts.p.onCellSelect)) {
					ts.p.onCellSelect.call(ts,ri,ci,tdHtml,e);
				}
				if(ts.p.cellEdit === true) {
					if(ts.p.multiselect && scb){
						$(ts).jqGrid("setSelection", ri ,true,e);
					} else {
						ri = ptr[0].rowIndex;
						try {$(ts).jqGrid("editCell",ri,ci,true);} catch (_) {}
					}
				} else if ( !ts.p.multikey ) {
					if(ts.p.multiselect && ts.p.multiboxonly) {
						if(scb){$(ts).jqGrid("setSelection",ri,true,e);}
						else {
							var frz = ts.p.frozenColumns ? ts.p.id+"_frozen" : "";
							$(ts.p.selarrrow).each(function(i,n){
								var trid = $(ts).jqGrid('getGridRowById',n);
								$( trid ).removeClass("ui-state-highlight");
								$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(n))[ts.p.useProp ? 'prop': 'attr']("checked", false);
								if(frz) {
									$("#"+$.jgrid.jqID(n), "#"+$.jgrid.jqID(frz)).removeClass("ui-state-highlight");
									$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+$.jgrid.jqID(n), "#"+$.jgrid.jqID(frz))[ts.p.useProp ? 'prop': 'attr']("checked", false);
								}
							});
							ts.p.selarrrow = [];
							$(ts).jqGrid("setSelection",ri,true,e);
						}
					} else {
						$(ts).jqGrid("setSelection",ri,true,e);
					}
				} else {
					if(e[ts.p.multikey]) {
						$(ts).jqGrid("setSelection",ri,true,e);
					} else if(ts.p.multiselect && scb) {
						scb = $("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+ri).is(":checked");
						$("#jqg_"+$.jgrid.jqID(ts.p.id)+"_"+ri)[ts.p.useProp ? 'prop' : 'attr']("checked", scb);
					}
				}
			}
		}).bind('reloadGrid', function(e,opts) {
			if(ts.p.treeGrid ===true) {	ts.p.datatype = ts.p.treedatatype;}
			if (opts && opts.current) {
				ts.grid.selectionPreserver(ts);
			}
			if(ts.p.datatype==="local"){ $(ts).jqGrid("resetSelection");  if(ts.p.data.length) { refreshIndex();} }
			else if(!ts.p.treeGrid) {
				ts.p.selrow=null;
				if(ts.p.multiselect) {ts.p.selarrrow =[];setHeadCheckBox(false);}
				ts.p.savedRow = [];
			}
			if(ts.p.scroll) {emptyRows.call(ts, true, false);}
			if (opts && opts.page) {
				var page = opts.page;
				if (page > ts.p.lastpage) { page = ts.p.lastpage; }
				if (page < 1) { page = 1; }
				ts.p.page = page;
				if (ts.grid.prevRowHeight) {
					ts.grid.bDiv.scrollTop = (page - 1) * ts.grid.prevRowHeight * ts.p.rowNum;
				} else {
					ts.grid.bDiv.scrollTop = 0;
				}
			}
			if (ts.grid.prevRowHeight && ts.p.scroll) {
				delete ts.p.lastpage;
				ts.grid.populateVisible();
			} else {
				ts.grid.populate();
			}
			if(ts.p._inlinenav===true) {$(ts).jqGrid('showAddEditButtons');}
			return false;
		})
		.dblclick(function(e) {
			td = e.target;
			ptr = $(td,ts.rows).closest("tr.jqgrow");
			if($(ptr).length === 0 ){return;}
			ri = ptr[0].rowIndex;
			ci = $.jgrid.getCellIndex(td);
			$(ts).triggerHandler("jqGridDblClickRow", [$(ptr).attr("id"),ri,ci,e]);
			if ($.isFunction(ts.p.ondblClickRow)) { ts.p.ondblClickRow.call(ts,$(ptr).attr("id"),ri,ci, e); }
		})
		.bind('contextmenu', function(e) {
			td = e.target;
			ptr = $(td,ts.rows).closest("tr.jqgrow");
			if($(ptr).length === 0 ){return;}
			if(!ts.p.multiselect) {	$(ts).jqGrid("setSelection",ptr[0].id,true,e);	}
			ri = ptr[0].rowIndex;
			ci = $.jgrid.getCellIndex(td);
			$(ts).triggerHandler("jqGridRightClickRow", [$(ptr).attr("id"),ri,ci,e]);
			if ($.isFunction(ts.p.onRightClickRow)) { ts.p.onRightClickRow.call(ts,$(ptr).attr("id"),ri,ci, e); }
		});
		grid.bDiv = document.createElement("div");
		if(isMSIE) { if(String(ts.p.height).toLowerCase() === "auto") { ts.p.height = "100%"; } }
		$(grid.bDiv)
			.append($('<div style="position:relative;'+(isMSIE && $.jgrid.msiever() < 8 ? "height:0.01%;" : "")+'"></div>').append('<div></div>').append(this))
			.addClass("ui-jqgrid-bdiv")
			.css({ height: ts.p.height+(isNaN(ts.p.height)?"":"px"), width: (grid.width)+"px"})
			.scroll(grid.scrollGrid);
		$("table:first",grid.bDiv).css({width:ts.p.tblwidth+"px"});
		if( !$.support.tbody ) { //IE
			if( $("tbody",this).length === 2 ) { $("tbody:gt(0)",this).remove();}
		}
		if(ts.p.multikey){
			if( $.jgrid.msie) {
				$(grid.bDiv).bind("selectstart",function(){return false;});
			} else {
				$(grid.bDiv).bind("mousedown",function(){return false;});
			}
		}
		if(hg) {$(grid.bDiv).hide();}
		grid.cDiv = document.createElement("div");
		var arf = ts.p.hidegrid===true ? $("<a role='link' class='ui-jqgrid-titlebar-close ui-corner-all HeaderButton' />").hover(
			function(){ arf.addClass('ui-state-hover');},
			function() {arf.removeClass('ui-state-hover');})
		.append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css((dir==="rtl"?"left":"right"),"0px") : "";
		$(grid.cDiv).append(arf).append("<span class='ui-jqgrid-title'>"+ts.p.caption+"</span>")
		.addClass("ui-jqgrid-titlebar ui-jqgrid-caption"+(dir==="rtl" ? "-rtl" :"" )+" ui-widget-header ui-corner-top ui-helper-clearfix");
		$(grid.cDiv).insertBefore(grid.hDiv);
		if( ts.p.toolbar[0] ) {
			grid.uDiv = document.createElement("div");
			if(ts.p.toolbar[1] === "top") {$(grid.uDiv).insertBefore(grid.hDiv);}
			else if (ts.p.toolbar[1]==="bottom" ) {$(grid.uDiv).insertAfter(grid.hDiv);}
			if(ts.p.toolbar[1]==="both") {
				grid.ubDiv = document.createElement("div");
				$(grid.uDiv).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id).insertBefore(grid.hDiv);
				$(grid.ubDiv).addClass("ui-userdata ui-state-default").attr("id","tb_"+this.id).insertAfter(grid.hDiv);
				if(hg)  {$(grid.ubDiv).hide();}
			} else {
				$(grid.uDiv).width(grid.width).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id);
			}
			if(hg) {$(grid.uDiv).hide();}
		}
		if(ts.p.toppager) {
			ts.p.toppager = $.jgrid.jqID(ts.p.id)+"_toppager";
			grid.topDiv = $("<div id='"+ts.p.toppager+"'></div>")[0];
			ts.p.toppager = "#"+ts.p.toppager;
			$(grid.topDiv).addClass('ui-state-default ui-jqgrid-toppager').width(grid.width).insertBefore(grid.hDiv);
			setPager(ts.p.toppager,'_t');
		}
		if(ts.p.footerrow) {
			grid.sDiv = $("<div class='ui-jqgrid-sdiv'></div>")[0];
			hb = $("<div class='ui-jqgrid-hbox"+(dir==="rtl"?"-rtl":"")+"'></div>");
			$(grid.sDiv).append(hb).width(grid.width).insertAfter(grid.hDiv);
			$(hb).append(tfoot);
			grid.footers = $(".ui-jqgrid-ftable",grid.sDiv)[0].rows[0].cells;
			if(ts.p.rownumbers) { grid.footers[0].className = 'ui-state-default jqgrid-rownum'; }
			if(hg) {$(grid.sDiv).hide();}
		}
		hb = null;
		if(ts.p.caption) {
			var tdt = ts.p.datatype;
			if(ts.p.hidegrid===true) {
				$(".ui-jqgrid-titlebar-close",grid.cDiv).click( function(e){
					var onHdCl = $.isFunction(ts.p.onHeaderClick),
					elems = ".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv",
					counter, self = this;
					if(ts.p.toolbar[0]===true) {
						if( ts.p.toolbar[1]==='both') {
							elems += ', #' + $(grid.ubDiv).attr('id');
						}
						elems += ', #' + $(grid.uDiv).attr('id');
					}
					counter = $(elems,"#gview_"+$.jgrid.jqID(ts.p.id)).length;

					if(ts.p.gridstate === 'visible') {
						$(elems,"#gbox_"+$.jgrid.jqID(ts.p.id)).slideUp("fast", function() {
							counter--;
							if (counter === 0) {
								$("span",self).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
								ts.p.gridstate = 'hidden';
								if($("#gbox_"+$.jgrid.jqID(ts.p.id)).hasClass("ui-resizable")) { $(".ui-resizable-handle","#gbox_"+$.jgrid.jqID(ts.p.id)).hide(); }
								$(ts).triggerHandler("jqGridHeaderClick", [ts.p.gridstate,e]);
								if(onHdCl) {if(!hg) {ts.p.onHeaderClick.call(ts,ts.p.gridstate,e);}}
							}
						});
					} else if(ts.p.gridstate === 'hidden'){
						$(elems,"#gbox_"+$.jgrid.jqID(ts.p.id)).slideDown("fast", function() {
							counter--;
							if (counter === 0) {
								$("span",self).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
								if(hg) {ts.p.datatype = tdt;populate();hg=false;}
								ts.p.gridstate = 'visible';
								if($("#gbox_"+$.jgrid.jqID(ts.p.id)).hasClass("ui-resizable")) { $(".ui-resizable-handle","#gbox_"+$.jgrid.jqID(ts.p.id)).show(); }
								$(ts).triggerHandler("jqGridHeaderClick", [ts.p.gridstate,e]);
								if(onHdCl) {if(!hg) {ts.p.onHeaderClick.call(ts,ts.p.gridstate,e);}}
							}
						});
					}
					return false;
				});
				if(hg) {ts.p.datatype="local"; $(".ui-jqgrid-titlebar-close",grid.cDiv).trigger("click");}
			}
		} else {$(grid.cDiv).hide();}
		$(grid.hDiv).after(grid.bDiv)
		.mousemove(function (e) {
			if(grid.resizing){grid.dragMove(e);return false;}
		});
		$(".ui-jqgrid-labels",grid.hDiv).bind("selectstart", function () { return false; });
		$(document).bind( "mouseup.jqGrid" + ts.p.id, function () {
			if(grid.resizing) {	grid.dragEnd(); return false;}
			return true;
		});
		ts.formatCol = formatCol;
		ts.sortData = sortData;
		ts.updatepager = updatepager;
		ts.refreshIndex = refreshIndex;
		ts.setHeadCheckBox = setHeadCheckBox;
		ts.constructTr = constructTr;
		ts.formatter = function ( rowId, cellval , colpos, rwdat, act){return formatter(rowId, cellval , colpos, rwdat, act);};
		$.extend(grid,{populate : populate, emptyRows: emptyRows, beginReq: beginReq, endReq: endReq});
		this.grid = grid;
		ts.addXmlData = function(d) {addXmlData(d,ts.grid.bDiv);};
		ts.addJSONData = function(d) {addJSONData(d,ts.grid.bDiv);};
		this.grid.cols = this.rows[0].cells;
		$(ts).triggerHandler("jqGridInitGrid");
		if ($.isFunction( ts.p.onInitGrid )) { ts.p.onInitGrid.call(ts); }

		populate();ts.p.hiddengrid=false;
	});
};
$.jgrid.extend({
	getGridParam : function(pName) {
		var $t = this[0];
		if (!$t || !$t.grid) {return;}
		if (!pName) { return $t.p; }
		return $t.p[pName] !== undefined ? $t.p[pName] : null;
	},
	setGridParam : function (newParams){
		return this.each(function(){
			if (this.grid && typeof newParams === 'object') {$.extend(true,this.p,newParams);}
		});
	},
	getGridRowById: function ( rowid ) {
		var row;
		this.each( function(){
			try {
				//row = this.rows.namedItem( rowid );
				var i = this.rows.length;
				while(i--) {
					if( rowid.toString() === this.rows[i].id) {
						row = this.rows[i];
						break;
					}
				}
			} catch ( e ) {
				row = $(this.grid.bDiv).find( "#" + $.jgrid.jqID( rowid ));
			}
		});
		return row;
	},
	getDataIDs : function () {
		var ids=[], i=0, len, j=0;
		this.each(function(){
			len = this.rows.length;
			if(len && len>0){
				while(i<len) {
					if($(this.rows[i]).hasClass('jqgrow')) {
						ids[j] = this.rows[i].id;
						j++;
					}
					i++;
				}
			}
		});
		return ids;
	},
	setSelection : function(selection,onsr, e) {
		return this.each(function(){
			var $t = this, stat,pt, ner, ia, tpsr, fid;
			if(selection === undefined) { return; }
			onsr = onsr === false ? false : true;
			pt=$($t).jqGrid('getGridRowById', selection);
			if(!pt || !pt.className || pt.className.indexOf( 'ui-state-disabled' ) > -1 ) { return; }
			function scrGrid(iR){
				var ch = $($t.grid.bDiv)[0].clientHeight,
				st = $($t.grid.bDiv)[0].scrollTop,
				rpos = $($t.rows[iR]).position().top,
				rh = $t.rows[iR].clientHeight;
				if(rpos+rh >= ch+st) { $($t.grid.bDiv)[0].scrollTop = rpos-(ch+st)+rh+st; }
				else if(rpos < ch+st) {
					if(rpos < st) {
						$($t.grid.bDiv)[0].scrollTop = rpos;
					}
				}
			}
			if($t.p.scrollrows===true) {
				ner = $($t).jqGrid('getGridRowById',selection).rowIndex;
				if(ner >=0 ){
					scrGrid(ner);
				}
			}
			if($t.p.frozenColumns === true ) {
				fid = $t.p.id+"_frozen";
			}
			if(!$t.p.multiselect) {	
				if(pt.className !== "ui-subgrid") {
					if( $t.p.selrow !== pt.id) {
						$( $($t).jqGrid('getGridRowById', $t.p.selrow) ).removeClass("ui-state-highlight").attr({"aria-selected":"false", "tabindex" : "-1"});
						$(pt).addClass("ui-state-highlight").attr({"aria-selected":"true", "tabindex" : "0"});//.focus();
						if(fid) {
							$("#"+$.jgrid.jqID($t.p.selrow), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight");
							$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).addClass("ui-state-highlight");
						}
						stat = true;
					} else {
						stat = false;
					}
					$t.p.selrow = pt.id;
					if( onsr ) { 
						$($t).triggerHandler("jqGridSelectRow", [pt.id, stat, e]);
						if( $t.p.onSelectRow) { $t.p.onSelectRow.call($t, pt.id, stat, e); }
					}
				}
			} else {
				//unselect selectall checkbox when deselecting a specific row
				$t.setHeadCheckBox( false );
				$t.p.selrow = pt.id;
				ia = $.inArray($t.p.selrow,$t.p.selarrrow);
				if (  ia === -1 ){
					if(pt.className !== "ui-subgrid") { $(pt).addClass("ui-state-highlight").attr("aria-selected","true");}
					stat = true;
					$t.p.selarrrow.push($t.p.selrow);
				} else {
					if(pt.className !== "ui-subgrid") { $(pt).removeClass("ui-state-highlight").attr("aria-selected","false");}
					stat = false;
					$t.p.selarrrow.splice(ia,1);
					tpsr = $t.p.selarrrow[0];
					$t.p.selrow = (tpsr === undefined) ? null : tpsr;
				}
				$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID(pt.id))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
				if(fid) {
					if(ia === -1) {
						$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).addClass("ui-state-highlight");
					} else {
						$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight");
					}
					$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
				}
				if( onsr ) {
					$($t).triggerHandler("jqGridSelectRow", [pt.id, stat, e]);
					if( $t.p.onSelectRow) { $t.p.onSelectRow.call($t, pt.id , stat, e); }
				}
			}
		});
	},
	resetSelection : function( rowid ){
		return this.each(function(){
			var t = this, sr, fid;
			if( t.p.frozenColumns === true ) {
				fid = t.p.id+"_frozen";
			}
			if(rowid !== undefined ) {
				sr = rowid === t.p.selrow ? t.p.selrow : rowid;
				$("#"+$.jgrid.jqID(t.p.id)+" tbody:first tr#"+$.jgrid.jqID(sr)).removeClass("ui-state-highlight").attr("aria-selected","false");
				if (fid) { $("#"+$.jgrid.jqID(sr), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight"); }
				if(t.p.multiselect) {
					$("#jqg_"+$.jgrid.jqID(t.p.id)+"_"+$.jgrid.jqID(sr), "#"+$.jgrid.jqID(t.p.id))[t.p.useProp ? 'prop': 'attr']("checked",false);
					if(fid) { $("#jqg_"+$.jgrid.jqID(t.p.id)+"_"+$.jgrid.jqID(sr), "#"+$.jgrid.jqID(fid))[t.p.useProp ? 'prop': 'attr']("checked",false); }
					t.setHeadCheckBox( false);
				}
				sr = null;
			} else if(!t.p.multiselect) {
				if(t.p.selrow) {
					$("#"+$.jgrid.jqID(t.p.id)+" tbody:first tr#"+$.jgrid.jqID(t.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected","false");
					if(fid) { $("#"+$.jgrid.jqID(t.p.selrow), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight"); }
					t.p.selrow = null;
				}
			} else {
				$(t.p.selarrrow).each(function(i,n){
					$( $(t).jqGrid('getGridRowById',n) ).removeClass("ui-state-highlight").attr("aria-selected","false");
					$("#jqg_"+$.jgrid.jqID(t.p.id)+"_"+$.jgrid.jqID(n))[t.p.useProp ? 'prop': 'attr']("checked",false);
					if(fid) { 
						$("#"+$.jgrid.jqID(n), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight"); 
						$("#jqg_"+$.jgrid.jqID(t.p.id)+"_"+$.jgrid.jqID(n), "#"+$.jgrid.jqID(fid))[t.p.useProp ? 'prop': 'attr']("checked",false);
					}
				});
				t.setHeadCheckBox( false );
				t.p.selarrrow = [];
				t.p.selrow = null;
			}
			if(t.p.cellEdit === true) {
				if(parseInt(t.p.iCol,10)>=0  && parseInt(t.p.iRow,10)>=0) {
					$("td:eq("+t.p.iCol+")",t.rows[t.p.iRow]).removeClass("edit-cell ui-state-highlight");
					$(t.rows[t.p.iRow]).removeClass("selected-row ui-state-hover");
				}
			}
			t.p.savedRow = [];
		});
	},
	getRowData : function( rowid ) {
		var res = {}, resall, getall=false, len, j=0;
		this.each(function(){
			var $t = this,nm,ind;
			if(rowid === undefined) {
				getall = true;
				resall = [];
				len = $t.rows.length;
			} else {
				ind = $($t).jqGrid('getGridRowById', rowid);
				if(!ind) { return res; }
				len = 2;
			}
			while(j<len){
				if(getall) { ind = $t.rows[j]; }
				if( $(ind).hasClass('jqgrow') ) {
					$('td[role="gridcell"]',ind).each( function(i) {
						nm = $t.p.colModel[i].name;
						if ( nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn') {
							if($t.p.treeGrid===true && nm === $t.p.ExpandColumn) {
								res[nm] = $.jgrid.htmlDecode($("span:first",this).html());
							} else {
								try {
									res[nm] = $.unformat.call($t,this,{rowId:ind.id, colModel:$t.p.colModel[i]},i);
								} catch (e){
									res[nm] = $.jgrid.htmlDecode($(this).html());
								}
							}
						}
					});
					if(getall) { resall.push(res); res={}; }
				}
				j++;
			}
		});
		return resall || res;
	},
	delRowData : function(rowid) {
		var success = false, rowInd, ia;
		this.each(function() {
			var $t = this;
			rowInd = $($t).jqGrid('getGridRowById', rowid);
			if(!rowInd) {return false;}
				$(rowInd).remove();
				$t.p.records--;
				$t.p.reccount--;
				$t.updatepager(true,false);
				success=true;
				if($t.p.multiselect) {
					ia = $.inArray(rowid,$t.p.selarrrow);
					if(ia !== -1) { $t.p.selarrrow.splice(ia,1);}
				}
				if ($t.p.multiselect && $t.p.selarrrow.length > 0) {
					$t.p.selrow = $t.p.selarrrow[$t.p.selarrrow.length-1];
				} else {
					$t.p.selrow = null;
				}
			if($t.p.datatype === 'local') {
				var id = $.jgrid.stripPref($t.p.idPrefix, rowid),
				pos = $t.p._index[id];
				if(pos !== undefined) {
					$t.p.data.splice(pos,1);
					$t.refreshIndex();
				}
			}
			if( $t.p.altRows === true && success ) {
				var cn = $t.p.altclass;
				$($t.rows).each(function(i){
					if(i % 2 === 1) { $(this).addClass(cn); }
					else { $(this).removeClass(cn); }
				});
			}
		});
		return success;
	},
	setRowData : function(rowid, data, cssp) {
		var nm, success=true, title;
		this.each(function(){
			if(!this.grid) {return false;}
			var t = this, vl, ind, cp = typeof cssp, lcdata={};
			ind = $(this).jqGrid('getGridRowById', rowid);
			if(!ind) { return false; }
			if( data ) {
				try {
					$(this.p.colModel).each(function(i){
						nm = this.name;
						var dval =$.jgrid.getAccessor(data,nm);
						if( dval !== undefined) {
							lcdata[nm] = this.formatter && typeof this.formatter === 'string' && this.formatter === 'date' ? $.unformat.date.call(t,dval,this) : dval;
							vl = t.formatter( rowid, dval, i, data, 'edit');
							title = this.title ? {"title":$.jgrid.stripHtml(vl)} : {};
							if(t.p.treeGrid===true && nm === t.p.ExpandColumn) {
								$("td[role='gridcell']:eq("+i+") > span:first",ind).html(vl).attr(title);
							} else {
								$("td[role='gridcell']:eq("+i+")",ind).html(vl).attr(title);
							}
						}
					});
					if(t.p.datatype === 'local') {
						var id = $.jgrid.stripPref(t.p.idPrefix, rowid),
						pos = t.p._index[id], key;
						if(t.p.treeGrid) {
							for(key in t.p.treeReader){
								if(t.p.treeReader.hasOwnProperty(key)) {
									delete lcdata[t.p.treeReader[key]];
								}
							}
						}
						if(pos !== undefined) {
							t.p.data[pos] = $.extend(true, t.p.data[pos], lcdata);
						}
						lcdata = null;
					}
				} catch (e) {
					success = false;
				}
			}
			if(success) {
				if(cp === 'string') {$(ind).addClass(cssp);} else if(cssp !== null && cp === 'object') {$(ind).css(cssp);}
				$(t).triggerHandler("jqGridAfterGridComplete");
			}
		});
		return success;
	},
	addRowData : function(rowid,rdata,pos,src) {
		if(!pos) {pos = "last";}
		var success = false, nm, row, gi, si, ni,sind, i, v, prp="", aradd, cnm, cn, data, cm, id;
		if(rdata) {
			if($.isArray(rdata)) {
				aradd=true;
				pos = "last";
				cnm = rowid;
			} else {
				rdata = [rdata];
				aradd = false;
			}
			this.each(function() {
				var t = this, datalen = rdata.length;
				ni = t.p.rownumbers===true ? 1 :0;
				gi = t.p.multiselect ===true ? 1 :0;
				si = t.p.subGrid===true ? 1 :0;
				if(!aradd) {
					if(rowid !== undefined) { rowid = String(rowid);}
					else {
						rowid = $.jgrid.randId();
						if(t.p.keyIndex !== false) {
							cnm = t.p.colModel[t.p.keyIndex+gi+si+ni].name;
							if(rdata[0][cnm] !== undefined) { rowid = rdata[0][cnm]; }
						}
					}
				}
				cn = t.p.altclass;
				var k = 0, cna ="", lcdata = {},
				air = $.isFunction(t.p.afterInsertRow) ? true : false;
				while(k < datalen) {
					data = rdata[k];
					row=[];
					if(aradd) {
						try {
							rowid = data[cnm];
							if(rowid===undefined) {
								rowid = $.jgrid.randId();
							}
						}
						catch (e) {rowid = $.jgrid.randId();}
						cna = t.p.altRows === true ?  (t.rows.length-1)%2 === 0 ? cn : "" : "";
					}
					id = rowid;
					rowid  = t.p.idPrefix + rowid;
					if(ni){
						prp = t.formatCol(0,1,'',null,rowid, true);
						row[row.length] = "<td role=\"gridcell\" class=\"ui-state-default jqgrid-rownum\" "+prp+">0</td>";
					}
					if(gi) {
						v = "<input role=\"checkbox\" type=\"checkbox\""+" id=\"jqg_"+t.p.id+"_"+rowid+"\" class=\"cbox\"/>";
						prp = t.formatCol(ni,1,'', null, rowid, true);
						row[row.length] = "<td role=\"gridcell\" "+prp+">"+v+"</td>";
					}
					if(si) {
						row[row.length] = $(t).jqGrid("addSubGridCell",gi+ni,1);
					}
					for(i = gi+si+ni; i < t.p.colModel.length;i++){
						cm = t.p.colModel[i];
						nm = cm.name;
						lcdata[nm] = data[nm];
						v = t.formatter( rowid, $.jgrid.getAccessor(data,nm), i, data );
						prp = t.formatCol(i,1,v, data, rowid, lcdata);
						row[row.length] = "<td role=\"gridcell\" "+prp+">"+v+"</td>";
					}
					row.unshift( t.constructTr(rowid, false, cna, lcdata, data, false ) );
					row[row.length] = "</tr>";
					if(t.rows.length === 0){
						$("table:first",t.grid.bDiv).append(row.join(''));
					} else {
						switch (pos) {
							case 'last':
								$(t.rows[t.rows.length-1]).after(row.join(''));
								sind = t.rows.length-1;
								break;
							case 'first':
								$(t.rows[0]).after(row.join(''));
								sind = 1;
								break;
							case 'after':
								sind = $(t).jqGrid('getGridRowById', src);
								if (sind) {
									if($(t.rows[sind.rowIndex+1]).hasClass("ui-subgrid")) { $(t.rows[sind.rowIndex+1]).after(row); }
									else { $(sind).after(row.join('')); }
									sind=sind.rowIndex + 1;
								}	
								break;
							case 'before':
								sind = $(t).jqGrid('getGridRowById', src);
								if(sind) {
									$(sind).before(row.join(''));
									sind=sind.rowIndex - 1;
								}
								break;
						}
					}
					if(t.p.subGrid===true) {
						$(t).jqGrid("addSubGrid",gi+ni, sind);
					}
					t.p.records++;
					t.p.reccount++;
					$(t).triggerHandler("jqGridAfterInsertRow", [rowid,data,data]);
					if(air) { t.p.afterInsertRow.call(t,rowid,data,data); }
					k++;
					if(t.p.datatype === 'local') {
						lcdata[t.p.localReader.id] = id;
						t.p._index[id] = t.p.data.length;
						t.p.data.push(lcdata);
						lcdata = {};
					}
				}
				if( t.p.altRows === true && !aradd) {
					if (pos === "last") {
						if ((t.rows.length-1)%2 === 1)  {$(t.rows[t.rows.length-1]).addClass(cn);}
					} else {
						$(t.rows).each(function(i){
							if(i % 2 ===1) { $(this).addClass(cn); }
							else { $(this).removeClass(cn); }
						});
					}
				}
				t.updatepager(true,true);
				success = true;
			});
		}
		return success;
	},
	footerData : function(action,data, format) {
		var nm, success=false, res={}, title;
		function isEmpty(obj) {
			var i;
			for(i in obj) {
				if (obj.hasOwnProperty(i)) { return false; }
			}
			return true;
		}
		if(action == undefined) { action = "get"; }
		if(typeof format !== "boolean") { format  = true; }
		action = action.toLowerCase();
		this.each(function(){
			var t = this, vl;
			if(!t.grid || !t.p.footerrow) {return false;}
			if(action === "set") { if(isEmpty(data)) { return false; } }
			success=true;
			$(this.p.colModel).each(function(i){
				nm = this.name;
				if(action === "set") {
					if( data[nm] !== undefined) {
						vl = format ? t.formatter( "", data[nm], i, data, 'edit') : data[nm];
						title = this.title ? {"title":$.jgrid.stripHtml(vl)} : {};
						$("tr.footrow td:eq("+i+")",t.grid.sDiv).html(vl).attr(title);
						success = true;
					}
				} else if(action === "get") {
					res[nm] = $("tr.footrow td:eq("+i+")",t.grid.sDiv).html();
				}
			});
		});
		return action === "get" ? res : success;
	},
	showHideCol : function(colname,show) {
		return this.each(function() {
			var $t = this, fndh=false, brd=$.jgrid.cell_width ? 0: $t.p.cellLayout, cw;
			if (!$t.grid ) {return;}
			if( typeof colname === 'string') {colname=[colname];}
			show = show !== "none" ? "" : "none";
			var sw = show === "" ? true :false,
			gh = $t.p.groupHeader && (typeof $t.p.groupHeader === 'object' || $.isFunction($t.p.groupHeader) );
			if(gh) { $($t).jqGrid('destroyGroupHeader', false); }
			$(this.p.colModel).each(function(i) {
				if ($.inArray(this.name,colname) !== -1 && this.hidden === sw) {
					if($t.p.frozenColumns === true && this.frozen === true) {
						return true;
					}
					$("tr[role=rowheader]",$t.grid.hDiv).each(function(){
						$(this.cells[i]).css("display", show);
					});
					$($t.rows).each(function(){
						if (!$(this).hasClass("jqgroup")) {
							$(this.cells[i]).css("display", show);
						}
					});
					if($t.p.footerrow) { $("tr.footrow td:eq("+i+")", $t.grid.sDiv).css("display", show); }
					cw =  parseInt(this.width,10);
					if(show === "none") {
						$t.p.tblwidth -= cw+brd;
					} else {
						$t.p.tblwidth += cw+brd;
					}
					this.hidden = !sw;
					fndh=true;
					$($t).triggerHandler("jqGridShowHideCol", [sw,this.name,i]);
				}
			});
			if(fndh===true) {
				if($t.p.shrinkToFit === true && !isNaN($t.p.height)) { $t.p.tblwidth += parseInt($t.p.scrollOffset,10);}
				$($t).jqGrid("setGridWidth",$t.p.shrinkToFit === true ? $t.p.tblwidth : $t.p.width );
			}
			if( gh )  {
				$($t).jqGrid('setGroupHeaders',$t.p.groupHeader);
			}
		});
	},
	hideCol : function (colname) {
		return this.each(function(){$(this).jqGrid("showHideCol",colname,"none");});
	},
	showCol : function(colname) {
		return this.each(function(){$(this).jqGrid("showHideCol",colname,"");});
	},
	remapColumns : function(permutation, updateCells, keepHeader)
	{
		function resortArray(a) {
			var ac;
			if (a.length) {
				ac = $.makeArray(a);
			} else {
				ac = $.extend({}, a);
			}
			$.each(permutation, function(i) {
				a[i] = ac[this];
			});
		}
		var ts = this.get(0);
		function resortRows(parent, clobj) {
			$(">tr"+(clobj||""), parent).each(function() {
				var row = this;
				var elems = $.makeArray(row.cells);
				$.each(permutation, function() {
					var e = elems[this];
					if (e) {
						row.appendChild(e);
					}
				});
			});
		}
		resortArray(ts.p.colModel);
		resortArray(ts.p.colNames);
		resortArray(ts.grid.headers);
		resortRows($("thead:first", ts.grid.hDiv), keepHeader && ":not(.ui-jqgrid-labels)");
		if (updateCells) {
			resortRows($("#"+$.jgrid.jqID(ts.p.id)+" tbody:first"), ".jqgfirstrow, tr.jqgrow, tr.jqfoot");
		}
		if (ts.p.footerrow) {
			resortRows($("tbody:first", ts.grid.sDiv));
		}
		if (ts.p.remapColumns) {
			if (!ts.p.remapColumns.length){
				ts.p.remapColumns = $.makeArray(permutation);
			} else {
				resortArray(ts.p.remapColumns);
			}
		}
		ts.p.lastsort = $.inArray(ts.p.lastsort, permutation);
		if(ts.p.treeGrid) { ts.p.expColInd = $.inArray(ts.p.expColInd, permutation); }
		$(ts).triggerHandler("jqGridRemapColumns", [permutation, updateCells, keepHeader]);
	},
	setGridWidth : function(nwidth, shrink) {
		return this.each(function(){
			if (!this.grid ) {return;}
			var $t = this, cw,
			initwidth = 0, brd=$.jgrid.cell_width ? 0: $t.p.cellLayout, lvc, vc=0, hs=false, scw=$t.p.scrollOffset, aw, gw=0, cr;
			if(typeof shrink !== 'boolean') {
				shrink=$t.p.shrinkToFit;
			}
			if(isNaN(nwidth)) {return;}
			nwidth = parseInt(nwidth,10); 
			$t.grid.width = $t.p.width = nwidth;
			$("#gbox_"+$.jgrid.jqID($t.p.id)).css("width",nwidth+"px");
			$("#gview_"+$.jgrid.jqID($t.p.id)).css("width",nwidth+"px");
			$($t.grid.bDiv).css("width",nwidth+"px");
			$($t.grid.hDiv).css("width",nwidth+"px");
			if($t.p.pager ) {$($t.p.pager).css("width",nwidth+"px");}
			if($t.p.toppager ) {$($t.p.toppager).css("width",nwidth+"px");}
			if($t.p.toolbar[0] === true){
				$($t.grid.uDiv).css("width",nwidth+"px");
				if($t.p.toolbar[1]==="both") {$($t.grid.ubDiv).css("width",nwidth+"px");}
			}
			if($t.p.footerrow) { $($t.grid.sDiv).css("width",nwidth+"px"); }
			if(shrink ===false && $t.p.forceFit === true) {$t.p.forceFit=false;}
			if(shrink===true) {
				$.each($t.p.colModel, function() {
					if(this.hidden===false){
						cw = this.widthOrg;
						initwidth += cw+brd;
						if(this.fixed) {
							gw += cw+brd;
						} else {
							vc++;
						}
					}
				});
				if(vc  === 0) { return; }
				$t.p.tblwidth = initwidth;
				aw = nwidth-brd*vc-gw;
				if(!isNaN($t.p.height)) {
					if($($t.grid.bDiv)[0].clientHeight < $($t.grid.bDiv)[0].scrollHeight || $t.rows.length === 1){
						hs = true;
						aw -= scw;
					}
				}
				initwidth =0;
				var cle = $t.grid.cols.length >0;
				$.each($t.p.colModel, function(i) {
					if(this.hidden === false && !this.fixed){
						cw = this.widthOrg;
						cw = Math.round(aw*cw/($t.p.tblwidth-brd*vc-gw));
						if (cw < 0) { return; }
						this.width =cw;
						initwidth += cw;
						$t.grid.headers[i].width=cw;
						$t.grid.headers[i].el.style.width=cw+"px";
						if($t.p.footerrow) { $t.grid.footers[i].style.width = cw+"px"; }
						if(cle) { $t.grid.cols[i].style.width = cw+"px"; }
						lvc = i;
					}
				});

				if (!lvc) { return; }

				cr =0;
				if (hs) {
					if(nwidth-gw-(initwidth+brd*vc) !== scw){
						cr = nwidth-gw-(initwidth+brd*vc)-scw;
					}
				} else if( Math.abs(nwidth-gw-(initwidth+brd*vc)) !== 1) {
					cr = nwidth-gw-(initwidth+brd*vc);
				}
				$t.p.colModel[lvc].width += cr;
				$t.p.tblwidth = initwidth+cr+brd*vc+gw;
				if($t.p.tblwidth > nwidth) {
					var delta = $t.p.tblwidth - parseInt(nwidth,10);
					$t.p.tblwidth = nwidth;
					cw = $t.p.colModel[lvc].width = $t.p.colModel[lvc].width-delta;
				} else {
					cw= $t.p.colModel[lvc].width;
				}
				$t.grid.headers[lvc].width = cw;
				$t.grid.headers[lvc].el.style.width=cw+"px";
				if(cle) { $t.grid.cols[lvc].style.width = cw+"px"; }
				if($t.p.footerrow) {
					$t.grid.footers[lvc].style.width = cw+"px";
				}
			}
			if($t.p.tblwidth) {
				$('table:first',$t.grid.bDiv).css("width",$t.p.tblwidth+"px");
				$('table:first',$t.grid.hDiv).css("width",$t.p.tblwidth+"px");
				$t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
				if($t.p.footerrow) {
					$('table:first',$t.grid.sDiv).css("width",$t.p.tblwidth+"px");
				}
			}
		});
	},
	setGridHeight : function (nh) {
		return this.each(function (){
			var $t = this;
			if(!$t.grid) {return;}
			var bDiv = $($t.grid.bDiv);
			bDiv.css({height: nh+(isNaN(nh)?"":"px")});
			if($t.p.frozenColumns === true){
				//follow the original set height to use 16, better scrollbar width detection
				$('#'+$.jgrid.jqID($t.p.id)+"_frozen").parent().height(bDiv.height() - 17);
			}
			$t.p.height = nh;
			if ($t.p.scroll) { $t.grid.populateVisible(); }
		});
	},
	setCaption : function (newcap){
		return this.each(function(){
			this.p.caption=newcap;
			$("span.ui-jqgrid-title, span.ui-jqgrid-title-rtl",this.grid.cDiv).html(newcap);
			$(this.grid.cDiv).show();
		});
	},
	setLabel : function(colname, nData, prop, attrp ){
		return this.each(function(){
			var $t = this, pos=-1;
			if(!$t.grid) {return;}
			if(colname !== undefined) {
				$($t.p.colModel).each(function(i){
					if (this.name === colname) {
						pos = i;return false;
					}
				});
			} else { return; }
			if(pos>=0) {
				var thecol = $("tr.ui-jqgrid-labels th:eq("+pos+")",$t.grid.hDiv);
				if (nData){
					var ico = $(".s-ico",thecol);
					$("[id^=jqgh_]",thecol).empty().html(nData).append(ico);
					$t.p.colNames[pos] = nData;
				}
				if (prop) {
					if(typeof prop === 'string') {$(thecol).addClass(prop);} else {$(thecol).css(prop);}
				}
				if(typeof attrp === 'object') {$(thecol).attr(attrp);}
			}
		});
	},
	setCell : function(rowid,colname,nData,cssp,attrp, forceupd) {
		return this.each(function(){
			var $t = this, pos =-1,v, title;
			if(!$t.grid) {return;}
			if(isNaN(colname)) {
				$($t.p.colModel).each(function(i){
					if (this.name === colname) {
						pos = i;return false;
					}
				});
			} else {pos = parseInt(colname,10);}
			if(pos>=0) {
				var ind = $($t).jqGrid('getGridRowById', rowid); 
				if (ind){
					var tcell = $("td:eq("+pos+")",ind);
					if(nData !== "" || forceupd === true) {
						v = $t.formatter(rowid, nData, pos,ind,'edit');
						title = $t.p.colModel[pos].title ? {"title":$.jgrid.stripHtml(v)} : {};
						if($t.p.treeGrid && $(".tree-wrap",$(tcell)).length>0) {
							$("span",$(tcell)).html(v).attr(title);
						} else {
							$(tcell).html(v).attr(title);
						}
						if($t.p.datatype === "local") {
//							var cm = $t.p.colModel[pos], index;
//							nData = cm.formatter && typeof cm.formatter === 'string' && cm.formatter === 'date' ? $.unformat.date.call($t,nData,cm) : nData;
//							index = $t.p._index[$.jgrid.stripPref($t.p.idPrefix, rowid)];
//							if(index !== undefined) {
//								$t.p.data[index][cm.name] = nData;
//							}
						}
					}
					if(typeof cssp === 'string'){
						$(tcell).addClass(cssp);
					} else if(cssp) {
						$(tcell).css(cssp);
					}
					if(typeof attrp === 'object') {$(tcell).attr(attrp);}
				}
			}
		});
	},
	getCell : function(rowid,col) {
		var ret = false;
		this.each(function(){
			var $t=this, pos=-1;
			if(!$t.grid) {return;}
			if(isNaN(col)) {
				$($t.p.colModel).each(function(i){
					if (this.name === col) {
						pos = i;return false;
					}
				});
			} else {pos = parseInt(col,10);}
			if(pos>=0) {
				var ind = $($t).jqGrid('getGridRowById', rowid);
				if(ind) {
					try {
						ret = $.unformat.call($t,$("td:eq("+pos+")",ind),{rowId:ind.id, colModel:$t.p.colModel[pos]},pos);
					} catch (e){
						ret = $.jgrid.htmlDecode($("td:eq("+pos+")",ind).html());
					}
				}
			}
		});
		return ret;
	},
	getCol : function (col, obj, mathopr) {
		var ret = [], val, sum=0, min, max, v;
		obj = typeof obj !== 'boolean' ? false : obj;
		if(mathopr === undefined) { mathopr = false; }
		this.each(function(){
			var $t=this, pos=-1;
			if(!$t.grid) {return;}
			if(isNaN(col)) {
				$($t.p.colModel).each(function(i){
					if (this.name === col) {
						pos = i;return false;
					}
				});
			} else {pos = parseInt(col,10);}
			if(pos>=0) {
				var ln = $t.rows.length, i =0, dlen=0;
				if (ln && ln>0){
					while(i<ln){
						if($($t.rows[i]).hasClass('jqgrow')) {
							try {
								val = $.unformat.call($t,$($t.rows[i].cells[pos]),{rowId:$t.rows[i].id, colModel:$t.p.colModel[pos]},pos);
							} catch (e) {
								val = $.jgrid.htmlDecode($t.rows[i].cells[pos].innerHTML);
							}
							if(mathopr) {
								v = parseFloat(val);
								if(!isNaN(v)) {
									sum += v;
									if (max === undefined) {max = min = v;}
									min = Math.min(min, v);
									max = Math.max(max, v);
									dlen++;
								}
							}
							else if(obj) { ret.push( {id:$t.rows[i].id,value:val} ); }
							else { ret.push( val ); }
						}
						i++;
					}
					if(mathopr) {
						switch(mathopr.toLowerCase()){
							case 'sum': ret =sum; break;
							case 'avg': ret = sum/dlen; break;
							case 'count': ret = (ln-1); break;
							case 'min': ret = min; break;
							case 'max': ret = max; break;
						}
					}
				}
			}
		});
		return ret;
	},
	clearGridData : function(clearfooter) {
		return this.each(function(){
			var $t = this;
			if(!$t.grid) {return;}
			if(typeof clearfooter !== 'boolean') { clearfooter = false; }
			if($t.p.deepempty) {$("#"+$.jgrid.jqID($t.p.id)+" tbody:first tr:gt(0)").remove();}
			else {
				var trf = $("#"+$.jgrid.jqID($t.p.id)+" tbody:first tr:first")[0];
				$("#"+$.jgrid.jqID($t.p.id)+" tbody:first").empty().append(trf);
			}
			if($t.p.footerrow && clearfooter) { $(".ui-jqgrid-ftable td",$t.grid.sDiv).html("&#160;"); }
			$t.p.selrow = null; $t.p.selarrrow= []; $t.p.savedRow = [];
			$t.p.records = 0;$t.p.page=1;$t.p.lastpage=0;$t.p.reccount=0;
			$t.p.data = []; $t.p._index = {};
			$t.updatepager(true,false);
		});
	},
	getInd : function(rowid,rc){
		var ret =false,rw;
		this.each(function(){
			rw = $(this).jqGrid('getGridRowById', rowid);
			if(rw) {
				ret = rc===true ? rw: rw.rowIndex;
			}
		});
		return ret;
	},
	bindKeys : function( settings ){
		var o = $.extend({
			onEnter: null,
			onSpace: null,
			onLeftKey: null,
			onRightKey: null,
			scrollingRows : true
		},settings || {});
		return this.each(function(){
			var $t = this;
			if( !$('body').is('[role]') ){$('body').attr('role','application');}
			$t.p.scrollrows = o.scrollingRows;
			$($t).keydown(function(event){
				var target = $($t).find('tr[tabindex=0]')[0], id, r, mind,
				expanded = $t.p.treeReader.expanded_field;
				//check for arrow keys
				if(target) {
					mind = $t.p._index[$.jgrid.stripPref($t.p.idPrefix, target.id)];
					if(event.keyCode === 37 || event.keyCode === 38 || event.keyCode === 39 || event.keyCode === 40){
						// up key
						if(event.keyCode === 38 ){
							r = target.previousSibling;
							id = "";
							if(r) {
								if($(r).is(":hidden")) {
									while(r) {
										r = r.previousSibling;
										if(!$(r).is(":hidden") && $(r).hasClass('jqgrow')) {id = r.id;break;}
									}
								} else {
									id = r.id;
								}
							}
							$($t).jqGrid('setSelection', id, true, event);
							event.preventDefault();
						}
						//if key is down arrow
						if(event.keyCode === 40){
							r = target.nextSibling;
							id ="";
							if(r) {
								if($(r).is(":hidden")) {
									while(r) {
										r = r.nextSibling;
										if(!$(r).is(":hidden") && $(r).hasClass('jqgrow') ) {id = r.id;break;}
									}
								} else {
									id = r.id;
								}
							}
							$($t).jqGrid('setSelection', id, true, event);
							event.preventDefault();
						}
						// left
						if(event.keyCode === 37 ){
							if($t.p.treeGrid && $t.p.data[mind][expanded]) {
								$(target).find("div.treeclick").trigger('click');
							}
							$($t).triggerHandler("jqGridKeyLeft", [$t.p.selrow]);
							if($.isFunction(o.onLeftKey)) {
								o.onLeftKey.call($t, $t.p.selrow);
							}
						}
						// right
						if(event.keyCode === 39 ){
							if($t.p.treeGrid && !$t.p.data[mind][expanded]) {
								$(target).find("div.treeclick").trigger('click');
							}
							$($t).triggerHandler("jqGridKeyRight", [$t.p.selrow]);
							if($.isFunction(o.onRightKey)) {
								o.onRightKey.call($t, $t.p.selrow);
							}
						}
					}
					//check if enter was pressed on a grid or treegrid node
					else if( event.keyCode === 13 ){
						$($t).triggerHandler("jqGridKeyEnter", [$t.p.selrow]);
						if($.isFunction(o.onEnter)) {
							o.onEnter.call($t, $t.p.selrow);
						}
					} else if(event.keyCode === 32) {
						$($t).triggerHandler("jqGridKeySpace", [$t.p.selrow]);
						if($.isFunction(o.onSpace)) {
							o.onSpace.call($t, $t.p.selrow);
						}
					}
				}
			});
		});
	},
	unbindKeys : function(){
		return this.each(function(){
			$(this).unbind('keydown');
		});
	},
	getLocalRow : function (rowid) {
		var ret = false, ind;
		this.each(function(){
			if(rowid !== undefined) {
				ind = this.p._index[$.jgrid.stripPref(this.p.idPrefix, rowid)];
				if(ind >= 0 ) {
					ret = this.p.data[ind];
				}
			}
		});
		return ret;
	}
});
//grid.celledit
$.jgrid.extend({
        editCell : function (iRow,iCol, ed){
            return this.each(function (){
                var $t = this, nm, tmp,cc, cm;
                if (!$t.grid || $t.p.cellEdit !== true) {return;}
                iCol = parseInt(iCol,10);
                // select the row that can be used for other methods
                $t.p.selrow = $t.rows[iRow].id;
                if (!$t.p.knv) {$($t).jqGrid("GridNav");}
                // check to see if we have already edited cell
                if ($t.p.savedRow.length>0) {
                    // prevent second click on that field and enable selects
                    if (ed===true ) {
                        if(iRow == $t.p.iRow && iCol == $t.p.iCol){
                            return;
                        }
                    }
                    // save the cell
                    $($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
                } else {
                    //window.setTimeout(function () { $("#"+$.jgrid.jqID($t.p.knv)).attr("tabindex","-1").focus();},0);
                }
                cm = $t.p.colModel[iCol];
                nm = cm.name;
                if (nm==='subgrid' || nm==='cb' || nm==='rn') {return;}
                cc = $("td:eq("+iCol+")",$t.rows[iRow]);
                if (cm.editable===true && ed===true && !cc.hasClass("not-editable-cell")) {
                    if(parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
                        $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
                        $($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
                    }
                    $(cc).addClass("edit-cell ui-state-highlight");
                    $($t.rows[iRow]).addClass("selected-row ui-state-hover");
                    try {
                        tmp =  $.unformat.call($t,cc,{rowId: $t.rows[iRow].id, colModel:cm},iCol);
                    } catch (_) {
                        tmp = ( cm.edittype && cm.edittype === 'textarea' ) ? $(cc).text() : $(cc).html();
                    }

                    if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
                    if (!cm.edittype) {cm.edittype = "text";}
                    $t.p.savedRow.push({id:iRow,ic:iCol,name:nm,v:tmp});
                    if(tmp === "&nbsp;" || tmp === "&#160;" || (tmp.length===1 && tmp.charCodeAt(0)===160) ) {tmp='';}
                    if($.isFunction($t.p.formatCell)) {
                        var tmp2 = $t.p.formatCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                        if(tmp2 !== undefined ) {tmp = tmp2;}
                    }
                    var opt = $.extend({}, cm.editoptions || {} ,{id:iRow+"_"+nm,name:nm});
                    var elc = $.jgrid.createEl.call($t,cm.edittype,opt,tmp,true,$.extend({},$.jgrid.ajaxOptions,$t.p.ajaxSelectOptions || {}));
                    $($t).triggerHandler("jqGridBeforeEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
                    if ($.isFunction($t.p.beforeEditCell)) {
                        $t.p.beforeEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                    }
                    $(cc).html("").append(elc).attr("tabindex","0");
                    $.jgrid.bindEv.call($t, elc, opt);
                    //edit 修复自定义编辑获取焦点 --arenp
                    window.setTimeout(function () { if($.isFunction(opt.custom_element)){ $(':input', elc).select().focus() } else { $(elc).select().focus() };},20);
                    //end
                    $("input, select, textarea",cc).unbind("keydown.once").bind("keydown.once",function(e) {
                        if (e.keyCode === 27) {
                            if($("input.hasDatepicker",cc).length >0) {
                                if( $(".ui-datepicker").is(":hidden") )  { $($t).jqGrid("restoreCell",iRow,iCol); }
                                else { $("input.hasDatepicker",cc).datepicker('hide'); }
                            } else {
                                $($t).jqGrid("restoreCell",iRow,iCol);
                            }
                        } //ESC
                        if (e.keyCode === 13) {
                            //$($t).jqGrid("saveCell",iRow,iCol);
                            // Prevent default action
                            //edit 支持Enter快捷键 --arenp
                            if(!$t.grid.hDiv.loading ) {
                                $($t).jqGrid("nextCell",iRow,iCol);
                                //setTimeout(function(){$($t).jqGrid("nextCell",iRow,iCol)}, 10);
                            } else {
                                return false;
                            }
                            //end
                            //return false;
                        } //Enter
                        if (e.keyCode === 9)  {
                            if(!$t.grid.hDiv.loading ) {
                                if (e.shiftKey) {$($t).jqGrid("prevCell",iRow,iCol);} //Shift TAb
                                else {$($t).jqGrid("nextCell",iRow,iCol);} //Tab
                            } else {
                                return false;
                            }
                        }
                        e.stopPropagation();
                    }).bind("focus.once",function(e) {
                        //add 新增失去焦点事件 --arenp
                        //curRow = iRow;
                        //curCol = iCol;
                        //$($t).jqGrid("saveCell",iRow,iCol);
                        //end
                    });
                    $($t).triggerHandler("jqGridAfterEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
                    if ($.isFunction($t.p.afterEditCell)) {
                        $t.p.afterEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                    }
                } else {
                    if (parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
                        $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
                        $($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
                    }
                    cc.addClass("edit-cell ui-state-highlight");
                    $($t.rows[iRow]).addClass("selected-row ui-state-hover");
                    tmp = cc.html().replace(/\&#160\;/ig,'');
                    $($t).triggerHandler("jqGridSelectCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
                    if ($.isFunction($t.p.onSelectCell)) {
                        $t.p.onSelectCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                    }
                }
                $t.p.iCol = iCol; $t.p.iRow = iRow;
            });
        },
        saveCell : function (iRow, iCol){
            return this.each(function(){
                var $t= this, fr;
                if (!$t.grid || $t.p.cellEdit !== true) {return;}
                if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;}
                if(fr !== null) {
                    var cc = $("td:eq("+iCol+")",$t.rows[iRow]),v,v2,
                        cm = $t.p.colModel[iCol], nm = cm.name, nmjq = $.jgrid.jqID(nm) ;
                    switch (cm.edittype) {
                        case "select":
                            if(!cm.editoptions.multiple) {
                                v = $("#"+iRow+"_"+nmjq+" option:selected",$t.rows[iRow]).val();
                                v2 = $("#"+iRow+"_"+nmjq+" option:selected",$t.rows[iRow]).text();
                            } else {
                                var sel = $("#"+iRow+"_"+nmjq,$t.rows[iRow]), selectedText = [];
                                v = $(sel).val();
                                if(v) { v.join(",");} else { v=""; }
                                $("option:selected",sel).each(
                                    function(i,selected){
                                        selectedText[i] = $(selected).text();
                                    }
                                );
                                v2 = selectedText.join(",");
                            }
                            if(cm.formatter) { v2 = v; }
                            break;
                        case "checkbox":
                            var cbv  = ["Yes","No"];
                            if(cm.editoptions){
                                cbv = cm.editoptions.value.split(":");
                            }
                            v = $("#"+iRow+"_"+nmjq,$t.rows[iRow]).is(":checked") ? cbv[0] : cbv[1];
                            v2=v;
                            break;
                        case "password":
                        case "text":
                        case "textarea":
                        case "button" :
                            v = $("#"+iRow+"_"+nmjq,$t.rows[iRow]).val();
                            v2=v;
                            break;
                        case 'custom' :
                            try {
                                if(cm.editoptions && $.isFunction(cm.editoptions.custom_value)) {
                                    v = cm.editoptions.custom_value.call($t, $(".customelement",cc),'get');
                                    if (v===undefined) { throw "e2";} else { v2=v; }
                                } else { throw "e1"; }
                            } catch (e) {
                                if (e==="e1") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,$.jgrid.edit.bClose); }
                                if (e==="e2") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,$.jgrid.edit.bClose); }
                                else {$.jgrid.info_dialog($.jgrid.errors.errcap,e.message,$.jgrid.edit.bClose); }
                            }
                            break;
                    }
                    // The common approach is if nothing changed do not do anything
                    if (v2 !== $t.p.savedRow[fr].v){
                        var vvv = $($t).triggerHandler("jqGridBeforeSaveCell", [$t.rows[iRow].id, nm, v, iRow, iCol]);
                        if (vvv) {v = vvv; v2=vvv;}
                        if ($.isFunction($t.p.beforeSaveCell)) {
                            var vv = $t.p.beforeSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
                            if (vv) {v = vv; v2=vv;}
                        }
                        var cv = $.jgrid.checkValues.call($t,v,iCol);
                        if(cv[0] === true) {
                            var addpost = $($t).triggerHandler("jqGridBeforeSubmitCell", [$t.rows[iRow].id, nm, v, iRow, iCol]) || {};
                            if ($.isFunction($t.p.beforeSubmitCell)) {
                                addpost = $t.p.beforeSubmitCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
                                if (!addpost) {addpost={};}
                            }
                            if( $("input.hasDatepicker",cc).length >0) { $("input.hasDatepicker",cc).datepicker('hide'); }
                            if ($t.p.cellsubmit === 'remote') {
                                if ($t.p.cellurl) {
                                    var postdata = {};
                                    if($t.p.autoencode) { v = $.jgrid.htmlEncode(v); }
                                    postdata[nm] = v;
                                    var idname,oper, opers;
                                    opers = $t.p.prmNames;
                                    idname = opers.id;
                                    oper = opers.oper;
                                    postdata[idname] = $.jgrid.stripPref($t.p.idPrefix, $t.rows[iRow].id);
                                    postdata[oper] = opers.editoper;
                                    postdata = $.extend(addpost,postdata);
                                    $("#lui_"+$.jgrid.jqID($t.p.id)).show();
                                    $t.grid.hDiv.loading = true;
                                    $.ajax( $.extend( {
                                        url: $t.p.cellurl,
                                        data :$.isFunction($t.p.serializeCellData) ? $t.p.serializeCellData.call($t, postdata) : postdata,
                                        type: "POST",
                                        complete: function (result, stat) {
                                            $("#lui_"+$t.p.id).hide();
                                            $t.grid.hDiv.loading = false;
                                            if (stat === 'success') {
                                                var ret = $($t).triggerHandler("jqGridAfterSubmitCell", [$t, result, postdata.id, nm, v, iRow, iCol]) || [true, ''];
                                                if (ret[0] === true && $.isFunction($t.p.afterSubmitCell)) {
                                                    ret = $t.p.afterSubmitCell.call($t, result,postdata.id,nm,v,iRow,iCol);
                                                }
                                                if(ret[0] === true){
                                                    $(cc).empty();
                                                    $($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
                                                    $(cc).addClass("dirty-cell");
                                                    $($t.rows[iRow]).addClass("edited");
                                                    $($t).triggerHandler("jqGridAfterSaveCell", [$t.rows[iRow].id, nm, v, iRow, iCol]);
                                                    if ($.isFunction($t.p.afterSaveCell)) {
                                                        $t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
                                                    }
                                                    $t.p.savedRow.splice(0,1);
                                                } else {
                                                    $.jgrid.info_dialog($.jgrid.errors.errcap,ret[1],$.jgrid.edit.bClose);
                                                    $($t).jqGrid("restoreCell",iRow,iCol);
                                                }
                                            }
                                        },
                                        error:function(res,stat,err) {
                                            $("#lui_"+$.jgrid.jqID($t.p.id)).hide();
                                            $t.grid.hDiv.loading = false;
                                            $($t).triggerHandler("jqGridErrorCell", [res, stat, err]);
                                            if ($.isFunction($t.p.errorCell)) {
                                                $t.p.errorCell.call($t, res,stat,err);
                                                $($t).jqGrid("restoreCell",iRow,iCol);
                                            } else {
                                                $.jgrid.info_dialog($.jgrid.errors.errcap,res.status+" : "+res.statusText+"<br/>"+stat,$.jgrid.edit.bClose);
                                                $($t).jqGrid("restoreCell",iRow,iCol);
                                            }
                                        }
                                    }, $.jgrid.ajaxOptions, $t.p.ajaxCellOptions || {}));
                                } else {
                                    try {
                                        $.jgrid.info_dialog($.jgrid.errors.errcap,$.jgrid.errors.nourl,$.jgrid.edit.bClose);
                                        $($t).jqGrid("restoreCell",iRow,iCol);
                                    } catch (e) {}
                                }
                            }
                            if ($t.p.cellsubmit === 'clientArray') {
                                if(cm.edittype ==="custom" && $.isFunction(cm.editoptions.handle)) {
                                    cm.editoptions.handle();
                                };
                                $(cc).empty();
                                $($t).jqGrid("setCell",$t.rows[iRow].id,iCol, v2, false, false, true);
                                $(cc).addClass("dirty-cell");
                                $($t.rows[iRow]).addClass("edited");
                                $($t).triggerHandler("jqGridAfterSaveCell", [$t.rows[iRow].id, nm, v, iRow, iCol]);
                                if ($.isFunction($t.p.afterSaveCell)) {
                                    $t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
                                }
                                $t.p.savedRow.splice(0,1);
                            }
                        } else {
                            try {
                                //gaozf deleted on 2015-4-1, you can check it your self and messagebox it in beforesavecell event.
                                //window.setTimeout(function(){$.jgrid.info_dialog($.jgrid.errors.errcap,v+" "+cv[1],$.jgrid.edit.bClose);},100);
                                $($t).jqGrid("restoreCell",iRow,iCol);
                            } catch (e) {}
                        }
                    } else {
                        $($t).jqGrid("restoreCell",iRow,iCol);
                    }
                }
                window.setTimeout(function () { $("#"+$.jgrid.jqID($t.p.knv)).attr("tabindex","-1"); $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");},0);
            });
        },
        restoreCell : function(iRow, iCol) {
            return this.each(function(){
                var $t= this, fr, cm = $t.p.colModel[iCol];
                if (!$t.grid || $t.p.cellEdit !== true ) {return;}
                if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;}
                if(fr !== null) {
                    var cc = $("td:eq("+iCol+")",$t.rows[iRow]);
                    // datepicker fix
                    if($.isFunction($.fn.datepicker)) {
                        try {
                            $("input.hasDatepicker",cc).datepicker('hide');
                        } catch (e) {}
                    }
                    if(cm.edittype ==="custom" && $.isFunction(cm.editoptions.handle)) {
                        cm.editoptions.handle();
                    };
                    $(cc).empty().attr("tabindex","-1");
                    $($t).jqGrid("setCell",$t.rows[iRow].id, iCol, $t.p.savedRow[fr].v, false, false, true);
                    $($t).triggerHandler("jqGridAfterRestoreCell", [$t.rows[iRow].id, $t.p.savedRow[fr].v, iRow, iCol]);
                    if ($.isFunction($t.p.afterRestoreCell)) {
                        $t.p.afterRestoreCell.call($t, $t.rows[iRow].id, $t.p.savedRow[fr].v, iRow, iCol);
                    }
                    $t.p.savedRow.splice(0,1);
                }
                window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1"); $("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");},0);
            });
        },
        nextCell : function (iRow,iCol) {
            return this.each(function (){
                var $t = this, nCol=false, i;
                if (!$t.grid || $t.p.cellEdit !== true) {return;}
                // try to find next editable cell
                for (i=iCol+1; i<$t.p.colModel.length; i++) {
                    if ( $t.p.colModel[i].editable ===true) {
                        nCol = i; break;
                    }
                };
                //add 新增循环编辑效果 --arenp
                if(i === $t.p.colModel.length) {
                    iRow = iRow + 1;
                    if($($t).find("tbody tr").eq(iRow).length === 0) {
                        if($t.p.triggerAdd === false) {
                            iRow = iRow - 1;
                            $($t).jqGrid("saveCell",iRow,iCol);
                            return;
                        } else {
                            if(THISPAGE.newId) {
                                $($t).jqGrid('addRowData', THISPAGE.newId, {id: THISPAGE.newId}, 'last');
                                THISPAGE.newId++;
                            } else {
                                $($t).jqGrid('addRowData', iRow, {}, 'last');
                            }
                        }
                    };
                    for (i=0; i<$t.p.colModel.length; i++) {
                        if ( $t.p.colModel[i].editable ===true) {
                            nCol = i; break;
                        }
                    };
                };
                //end
                if(nCol !== false) {
                    $($t).jqGrid("editCell",iRow,nCol,true);
                } else {
                    if ($t.p.savedRow.length >0) {
                        $($t).jqGrid("saveCell",iRow,iCol);
                    }
                }
            });
        },
        prevCell : function (iRow,iCol) {
            return this.each(function (){
                var $t = this, nCol=false, i;
                if (!$t.grid || $t.p.cellEdit !== true) {return;}
                // try to find next editable cell
                for (i=iCol-1; i>=0; i--) {
                    if ( $t.p.colModel[i].editable ===true) {
                        nCol = i; break;
                    }
                }
                if(nCol !== false) {
                    $($t).jqGrid("editCell",iRow,nCol,true);
                } else {
                    if ($t.p.savedRow.length >0) {
                        $($t).jqGrid("saveCell",iRow,iCol);
                    }
                }
            });
        },
        GridNav : function() {
            return this.each(function () {
                var  $t = this;
                if (!$t.grid || $t.p.cellEdit !== true ) {return;}
                // trick to process keydown on non input elements
                $t.p.knv = $t.p.id + "_kn";
                var selection = $("<div style='position:fixed;top:0px;width:1px;height:1px;' tabindex='0'><div tabindex='-1' style='width:1px;height:1px;' id='"+$t.p.knv+"'></div></div>"),
                    i, kdir;
                function scrollGrid(iR, iC, tp){
                    if (tp.substr(0,1)==='v') {
                        var ch = $($t.grid.bDiv)[0].clientHeight,
                            st = $($t.grid.bDiv)[0].scrollTop,
                            nROT = $t.rows[iR].offsetTop+$t.rows[iR].clientHeight,
                            pROT = $t.rows[iR].offsetTop;
                        if(tp === 'vd') {
                            if(nROT >= ch) {
                                $($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop + $t.rows[iR].clientHeight;
                            }
                        }
                        if(tp === 'vu'){
                            if (pROT < st ) {
                                $($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop - $t.rows[iR].clientHeight;
                            }
                        }
                    }
                    if(tp==='h') {
                        var cw = $($t.grid.bDiv)[0].clientWidth,
                            sl = $($t.grid.bDiv)[0].scrollLeft,
                            nCOL = $t.rows[iR].cells[iC].offsetLeft+$t.rows[iR].cells[iC].clientWidth,
                            pCOL = $t.rows[iR].cells[iC].offsetLeft;
                        if(nCOL >= cw+parseInt(sl,10)) {
                            $($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft + $t.rows[iR].cells[iC].clientWidth;
                        } else if (pCOL < sl) {
                            $($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft - $t.rows[iR].cells[iC].clientWidth;
                        }
                    }
                }
                function findNextVisible(iC,act){
                    var ind, i;
                    if(act === 'lft') {
                        ind = iC+1;
                        for (i=iC;i>=0;i--){
                            if ($t.p.colModel[i].hidden !== true) {
                                ind = i;
                                break;
                            }
                        }
                    }
                    if(act === 'rgt') {
                        ind = iC-1;
                        for (i=iC; i<$t.p.colModel.length;i++){
                            if ($t.p.colModel[i].hidden !== true) {
                                ind = i;
                                break;
                            }
                        }
                    }
                    return ind;
                }

                $(selection).insertBefore($t.grid.cDiv);
                $("#"+$t.p.knv)
                //.focus()
                    .keydown(function (e){
                        kdir = e.keyCode;
                        if($t.p.direction === "rtl") {
                            if(kdir===37) { kdir = 39;}
                            else if (kdir===39) { kdir = 37; }
                        }
                        switch (kdir) {
                            case 38:
                                if ($t.p.iRow-1 >0 ) {
                                    scrollGrid($t.p.iRow-1,$t.p.iCol,'vu');
                                    $($t).jqGrid("editCell",$t.p.iRow-1,$t.p.iCol,false);
                                }
                                break;
                            case 40 :
                                if ($t.p.iRow+1 <=  $t.rows.length-1) {
                                    scrollGrid($t.p.iRow+1,$t.p.iCol,'vd');
                                    $($t).jqGrid("editCell",$t.p.iRow+1,$t.p.iCol,false);
                                }
                                break;
                            case 37 :
                                if ($t.p.iCol -1 >=  0) {
                                    i = findNextVisible($t.p.iCol-1,'lft');
                                    scrollGrid($t.p.iRow, i,'h');
                                    $($t).jqGrid("editCell",$t.p.iRow, i,false);
                                }
                                break;
                            case 39 :
                                if ($t.p.iCol +1 <=  $t.p.colModel.length-1) {
                                    i = findNextVisible($t.p.iCol+1,'rgt');
                                    scrollGrid($t.p.iRow,i,'h');
                                    $($t).jqGrid("editCell",$t.p.iRow,i,false);
                                }
                                break;
                            case 13:
                                if (parseInt($t.p.iCol,10)>=0 && parseInt($t.p.iRow,10)>=0) {
                                    $($t).jqGrid("editCell",$t.p.iRow,$t.p.iCol,true);
                                }
                                break;
                            default :
                                return true;
                        }
                        return false;
                    });
            });
        },
        getChangedCells : function (mthd) {
            var ret=[];
            if (!mthd) {mthd='all';}
            this.each(function(){
                var $t= this,nm;
                if (!$t.grid || $t.p.cellEdit !== true ) {return;}
                $($t.rows).each(function(j){
                    var res = {};
                    if ($(this).hasClass("edited")) {
                        $('td',this).each( function(i) {
                            nm = $t.p.colModel[i].name;
                            if ( nm !== 'cb' && nm !== 'subgrid') {
                                if (mthd==='dirty') {
                                    if ($(this).hasClass('dirty-cell')) {
                                        try {
                                            res[nm] = $.unformat.call($t,this,{rowId:$t.rows[j].id, colModel:$t.p.colModel[i]},i);
                                        } catch (e){
                                            res[nm] = $.jgrid.htmlDecode($(this).html());
                                        }
                                    }
                                } else {
                                    try {
                                        res[nm] = $.unformat.call($t,this,{rowId:$t.rows[j].id,colModel:$t.p.colModel[i]},i);
                                    } catch (e) {
                                        res[nm] = $.jgrid.htmlDecode($(this).html());
                                    }
                                }
                            }
                        });
                        res.id = this.id;
                        ret.push(res);
                    }
                });
            });
            return ret;
        }
/// end  cell editing
    });
//grid.common
$.extend($.jgrid,{
// Modal functions
        showModal : function(h) {
            h.w.show();
        },
        closeModal : function(h) {
            h.w.hide().attr("aria-hidden","true");
            if(h.o) {h.o.remove();}
        },
        hideModal : function (selector,o) {
            o = $.extend({jqm : true, gb :''}, o || {});
            if(o.onClose) {
                var oncret = o.gb && typeof o.gb === "string" && o.gb.substr(0,6) === "#gbox_" ? o.onClose.call($("#" + o.gb.substr(6))[0], selector) : o.onClose(selector);
                if (typeof oncret === 'boolean'  && !oncret ) { return; }
            }
            if ($.fn.jqm && o.jqm === true) {
                $(selector).attr("aria-hidden","true").jqmHide();
            } else {
                if(o.gb !== '') {
                    try {$(".jqgrid-overlay:first",o.gb).hide();} catch (e){}
                }
                $(selector).hide().attr("aria-hidden","true");
            }
        },
//Helper functions
        findPos : function(obj) {
            var curleft = 0, curtop = 0;
            if (obj.offsetParent) {
                do {
                    curleft += obj.offsetLeft;
                    curtop += obj.offsetTop;
                } while (obj = obj.offsetParent);
                //do not change obj == obj.offsetParent
            }
            return [curleft,curtop];
        },
        createModal : function(aIDs, content, p, insertSelector, posSelector, appendsel, css) {
            p = $.extend(true, {}, $.jgrid.jqModal || {}, p);
            var mw  = document.createElement('div'), rtlsup, self = this;
            css = $.extend({}, css || {});
            rtlsup = $(p.gbox).attr("dir") === "rtl" ? true : false;
            mw.className= "ui-widget ui-widget-content ui-corner-all ui-jqdialog";
            mw.id = aIDs.themodal;
            var mh = document.createElement('div');
            mh.className = "ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";
            mh.id = aIDs.modalhead;
            $(mh).append("<span class='ui-jqdialog-title'>"+p.caption+"</span>");
            var ahr= $("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>")
                .hover(function(){ahr.addClass('ui-state-hover');},
                    function(){ahr.removeClass('ui-state-hover');})
                .append("<span class='ui-icon ui-icon-closethick'></span>");
            $(mh).append(ahr);
            if(rtlsup) {
                mw.dir = "rtl";
                $(".ui-jqdialog-title",mh).css("float","right");
                $(".ui-jqdialog-titlebar-close",mh).css("left",0.3+"em");
            } else {
                mw.dir = "ltr";
                $(".ui-jqdialog-title",mh).css("float","left");
                $(".ui-jqdialog-titlebar-close",mh).css("right",0.3+"em");
            }
            var mc = document.createElement('div');
            $(mc).addClass("ui-jqdialog-content ui-widget-content").attr("id",aIDs.modalcontent);
            $(mc).append(content);
            mw.appendChild(mc);
            $(mw).prepend(mh);
            if(appendsel===true) { $('body').append(mw); } //append as first child in body -for alert dialog
            else if (typeof appendsel === "string") {
                $(appendsel).append(mw);
            } else {$(mw).insertBefore(insertSelector);}
            $(mw).css(css);
            if(p.jqModal === undefined) {p.jqModal = true;} // internal use
            var coord = {};
            if ( $.fn.jqm && p.jqModal === true) {
                if(p.left ===0 && p.top===0 && p.overlay) {
                    var pos = [];
                    pos = $.jgrid.findPos(posSelector);
                    p.left = pos[0] + 4;
                    p.top = pos[1] + 4;
                }
                coord.top = p.top+"px";
                coord.left = p.left;
            } else if(p.left !==0 || p.top!==0) {
                coord.left = p.left;
                coord.top = p.top+"px";
            }
            $("a.ui-jqdialog-titlebar-close",mh).click(function(){
                var oncm = $("#"+$.jgrid.jqID(aIDs.themodal)).data("onClose") || p.onClose;
                var gboxclose = $("#"+$.jgrid.jqID(aIDs.themodal)).data("gbox") || p.gbox;
                self.hideModal("#"+$.jgrid.jqID(aIDs.themodal),{gb:gboxclose,jqm:p.jqModal,onClose:oncm});
                return false;
            });
            if (p.width === 0 || !p.width) {p.width = 300;}
            if(p.height === 0 || !p.height) {p.height =200;}
            if(!p.zIndex) {
                var parentZ = $(insertSelector).parents("*[role=dialog]").filter(':first').css("z-index");
                if(parentZ) {
                    p.zIndex = parseInt(parentZ,10)+2;
                } else {
                    p.zIndex = 950;
                }
            }
            var rtlt = 0;
            if( rtlsup && coord.left && !appendsel) {
                rtlt = $(p.gbox).width()- (!isNaN(p.width) ? parseInt(p.width,10) :0) - 8; // to do
                // just in case
                coord.left = parseInt(coord.left,10) + parseInt(rtlt,10);
            }
            if(coord.left) { coord.left += "px"; }
            $(mw).css($.extend({
                width: isNaN(p.width) ? "auto": p.width+"px",
                height:isNaN(p.height) ? "auto" : p.height + "px",
                zIndex:p.zIndex,
                overflow: 'hidden'
            },coord))
                .attr({tabIndex: "-1","role":"dialog","aria-labelledby":aIDs.modalhead,"aria-hidden":"true"});
            if(p.drag === undefined) { p.drag=true;}
            if(p.resize === undefined) {p.resize=true;}
            if (p.drag) {
                $(mh).css('cursor','move');
                if($.fn.jqDrag) {
                    $(mw).jqDrag(mh);
                } else {
                    try {
                        $(mw).draggable({handle: $("#"+$.jgrid.jqID(mh.id))});
                    } catch (e) {}
                }
            }
            if(p.resize) {
                if($.fn.jqResize) {
                    $(mw).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se'></div>");
                    $("#"+$.jgrid.jqID(aIDs.themodal)).jqResize(".jqResize",aIDs.scrollelm ? "#"+$.jgrid.jqID(aIDs.scrollelm) : false);
                } else {
                    try {
                        $(mw).resizable({handles: 'se, sw',alsoResize: aIDs.scrollelm ? "#"+$.jgrid.jqID(aIDs.scrollelm) : false});
                    } catch (r) {}
                }
            }
            if(p.closeOnEscape === true){
                $(mw).keydown( function( e ) {
                    if( e.which == 27 ) {
                        var cone = $("#"+$.jgrid.jqID(aIDs.themodal)).data("onClose") || p.onClose;
                        self.hideModal("#"+$.jgrid.jqID(aIDs.themodal),{gb:p.gbox,jqm:p.jqModal,onClose: cone});
                    }
                });
            }
        },
        viewModal : function (selector,o){
            o = $.extend({
                toTop: true,
                overlay: 10,
                modal: false,
                overlayClass : 'ui-widget-overlay',
                onShow: $.jgrid.showModal,
                onHide: $.jgrid.closeModal,
                gbox: '',
                jqm : true,
                jqM : true
            }, o || {});
            if ($.fn.jqm && o.jqm === true) {
                if(o.jqM) { $(selector).attr("aria-hidden","false").jqm(o).jqmShow(); }
                else {$(selector).attr("aria-hidden","false").jqmShow();}
            } else {
                if(o.gbox !== '') {
                    $(".jqgrid-overlay:first",o.gbox).show();
                    $(selector).data("gbox",o.gbox);
                }
                $(selector).show().attr("aria-hidden","false");
                try{$(':input:visible',selector)[0].focus();}catch(_){}
            }
        },
        info_dialog : function(caption, content,c_b, modalopt) {
            var mopt = {
                width:290,
                height:'auto',
                dataheight: 'auto',
                drag: true,
                resize: false,
                left:250,
                top:170,
                zIndex : 1000,
                jqModal : true,
                modal : false,
                closeOnEscape : true,
                align: 'center',
                buttonalign : 'center',
                buttons : []
                // {text:'textbutt', id:"buttid", onClick : function(){...}}
                // if the id is not provided we set it like info_button_+ the index in the array - i.e info_button_0,info_button_1...
            };
            $.extend(true, mopt, $.jgrid.jqModal || {}, {caption:"<b>"+caption+"</b>"}, modalopt || {});
            var jm = mopt.jqModal, self = this;
            if($.fn.jqm && !jm) { jm = false; }
            // in case there is no jqModal
            var buttstr ="", i;
            if(mopt.buttons.length > 0) {
                for(i=0;i<mopt.buttons.length;i++) {
                    if(mopt.buttons[i].id === undefined) { mopt.buttons[i].id = "info_button_"+i; }
                    buttstr += "<a href='javascript:void(0)' id='"+mopt.buttons[i].id+"' class='fm-button ui-state-default ui-corner-all'>"+mopt.buttons[i].text+"</a>";
                }
            }
            var dh = isNaN(mopt.dataheight) ? mopt.dataheight : mopt.dataheight+"px",
                cn = "text-align:"+mopt.align+";";
            var cnt = "<div id='info_id'>";
            cnt += "<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"+dh+";"+cn+"'>"+content+"</div>";
            cnt += c_b ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+mopt.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>"+c_b+"</a>"+buttstr+"</div>" :
                buttstr !== ""  ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+mopt.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>"+buttstr+"</div>" : "";
            cnt += "</div>";

            try {
                if($("#info_dialog").attr("aria-hidden") === "false") {
                    $.jgrid.hideModal("#info_dialog",{jqm:jm});
                }
                $("#info_dialog").remove();
            } catch (e){}
            $.jgrid.createModal({
                    themodal:'info_dialog',
                    modalhead:'info_head',
                    modalcontent:'info_content',
                    scrollelm: 'infocnt'},
                cnt,
                mopt,
                '','',true
            );
            // attach onclick after inserting into the dom
            if(buttstr) {
                $.each(mopt.buttons,function(i){
                    $("#"+$.jgrid.jqID(this.id),"#info_id").bind('click',function(){mopt.buttons[i].onClick.call($("#info_dialog")); return false;});
                });
            }
            $("#closedialog", "#info_id").click(function(){
                self.hideModal("#info_dialog",{
                    jqm:jm,
                    onClose: $("#info_dialog").data("onClose") || mopt.onClose,
                    gb: $("#info_dialog").data("gbox") || mopt.gbox
                });
                return false;
            });
            $(".fm-button","#info_dialog").hover(
                function(){$(this).addClass('ui-state-hover');},
                function(){$(this).removeClass('ui-state-hover');}
            );
            if($.isFunction(mopt.beforeOpen) ) { mopt.beforeOpen(); }
            $.jgrid.viewModal("#info_dialog",{
                onHide: function(h) {
                    h.w.hide().remove();
                    if(h.o) { h.o.remove(); }
                },
                modal :mopt.modal,
                jqm:jm
            });
            if($.isFunction(mopt.afterOpen) ) { mopt.afterOpen(); }
            try{ $("#info_dialog").focus();} catch (m){}
        },
        bindEv: function  (el, opt) {
            var $t = this;
            if($.isFunction(opt.dataInit)) {
                opt.dataInit.call($t,el);
            }
            if(opt.dataEvents) {
                $.each(opt.dataEvents, function() {
                    if (this.data !== undefined) {
                        $(el).bind(this.type, this.data, this.fn);
                    } else {
                        $(el).bind(this.type, this.fn);
                    }
                });
            }
        },
// Form Functions
        createEl : function(eltype,options,vl,autowidth, ajaxso) {
            var elem = "", $t = this;
            function setAttributes(elm, atr, exl ) {
                var exclude = ['dataInit','dataEvents','dataUrl', 'buildSelect','sopt', 'searchhidden', 'defaultValue', 'attr', 'custom_element', 'custom_value'];
                if(exl !== undefined && $.isArray(exl)) {
                    $.merge(exclude, exl);
                }
                $.each(atr, function(key, value){
                    if($.inArray(key, exclude) === -1) {
                        $(elm).attr(key,value);
                    }
                });
                if(!atr.hasOwnProperty('id')) {
                    $(elm).attr('id', $.jgrid.randId());
                }
            }
            switch (eltype)
            {
                case "textarea" :
                    elem = document.createElement("textarea");
                    if(autowidth) {
                        if(!options.cols) { $(elem).css({width:"98%"});}
                    } else if (!options.cols) { options.cols = 20; }
                    if(!options.rows) { options.rows = 2; }
                    if(vl==='&nbsp;' || vl==='&#160;' || (vl.length===1 && vl.charCodeAt(0)===160)) {vl="";}
                    elem.value = vl;
                    setAttributes(elem, options);
                    $(elem).attr({"role":"textbox","multiline":"true"});
                    break;
                case "checkbox" : //what code for simple checkbox
                    elem = document.createElement("input");
                    elem.type = "checkbox";
                    if( !options.value ) {
                        var vl1 = vl.toLowerCase();
                        if(vl1.search(/(false|f|0|no|n|off|undefined)/i)<0 && vl1!=="") {
                            elem.checked=true;
                            elem.defaultChecked=true;
                            elem.value = vl;
                        } else {
                            elem.value = "on";
                        }
                        $(elem).attr("offval","off");
                    } else {
                        var cbval = options.value.split(":");
                        if(vl === cbval[0]) {
                            elem.checked=true;
                            elem.defaultChecked=true;
                        }
                        elem.value = cbval[0];
                        $(elem).attr("offval",cbval[1]);
                    }
                    setAttributes(elem, options, ['value']);
                    $(elem).attr("role","checkbox");
                    break;
                case "select" :
                    elem = document.createElement("select");
                    elem.setAttribute("role","select");
                    var msl, ovm = [];
                    if(options.multiple===true) {
                        msl = true;
                        elem.multiple="multiple";
                        $(elem).attr("aria-multiselectable","true");
                    } else { msl = false; }
                    if(options.dataUrl !== undefined) {
                        var rowid = options.name ? String(options.id).substring(0, String(options.id).length - String(options.name).length - 1) : String(options.id),
                            postData = options.postData || ajaxso.postData;

                        if ($t.p && $t.p.idPrefix) {
                            rowid = $.jgrid.stripPref($t.p.idPrefix, rowid);
                        }
                        $.ajax($.extend({
                            url: $.isFunction(options.dataUrl) ? options.dataUrl.call($t, rowid, vl, String(options.name)) : options.dataUrl,
                            type : "GET",
                            dataType: "html",
                            data: $.isFunction(postData) ? postData.call($t, rowid, vl, String(options.name)) : postData,
                            context: {elem:elem, options:options, vl:vl},
                            success: function(data){
                                var ovm = [], elem = this.elem, vl = this.vl,
                                    options = $.extend({},this.options),
                                    msl = options.multiple===true,
                                    a = $.isFunction(options.buildSelect) ? options.buildSelect.call($t,data) : data;
                                if(typeof a === 'string') {
                                    a = $( $.trim( a ) ).html();
                                }
                                if(a) {
                                    $(elem).append(a);
                                    setAttributes(elem, options, postData ? ['postData'] : undefined );
                                    if(options.size === undefined) { options.size =  msl ? 3 : 1;}
                                    if(msl) {
                                        ovm = vl.split(",");
                                        ovm = $.map(ovm,function(n){return $.trim(n);});
                                    } else {
                                        ovm[0] = $.trim(vl);
                                    }
                                    //$(elem).attr(options);
                                    setTimeout(function(){
                                        $("option",elem).each(function(i){
                                            //if(i===0) { this.selected = ""; }
                                            // fix IE8/IE7 problem with selecting of the first item on multiple=true
                                            if (i === 0 && elem.multiple) { this.selected = false; }
                                            $(this).attr("role","option");
                                            if($.inArray($.trim($(this).text()),ovm) > -1 || $.inArray($.trim($(this).val()),ovm) > -1 ) {
                                                this.selected= "selected";
                                            }
                                        });
                                    },0);
                                }
                            }
                        },ajaxso || {}));
                    } else if(options.value) {
                        var i;
                        if(options.size === undefined) {
                            options.size = msl ? 3 : 1;
                        }
                        if(msl) {
                            ovm = vl.split(",");
                            ovm = $.map(ovm,function(n){return $.trim(n);});
                        }
                        if(typeof options.value === 'function') { options.value = options.value(); }
                        var so,sv, ov,
                            sep = options.separator === undefined ? ":" : options.separator,
                            delim = options.delimiter === undefined ? ";" : options.delimiter;
                        if(typeof options.value === 'string') {
                            so = options.value.split(delim);
                            for(i=0; i<so.length;i++){
                                sv = so[i].split(sep);
                                if(sv.length > 2 ) {
                                    sv[1] = $.map(sv,function(n,ii){if(ii>0) { return n;} }).join(sep);
                                }
                                ov = document.createElement("option");
                                ov.setAttribute("role","option");
                                ov.value = sv[0]; ov.innerHTML = sv[1];
                                elem.appendChild(ov);
                                if (!msl &&  ($.trim(sv[0]) === $.trim(vl) || $.trim(sv[1]) === $.trim(vl))) { ov.selected ="selected"; }
                                if (msl && ($.inArray($.trim(sv[1]), ovm)>-1 || $.inArray($.trim(sv[0]), ovm)>-1)) {ov.selected ="selected";}
                            }
                        } else if (typeof options.value === 'object') {
                            var oSv = options.value, key;
                            for (key in oSv) {
                                if (oSv.hasOwnProperty(key ) ){
                                    ov = document.createElement("option");
                                    ov.setAttribute("role","option");
                                    ov.value = key; ov.innerHTML = oSv[key];
                                    elem.appendChild(ov);
                                    if (!msl &&  ( $.trim(key) === $.trim(vl) || $.trim(oSv[key]) === $.trim(vl)) ) { ov.selected ="selected"; }
                                    if (msl && ($.inArray($.trim(oSv[key]),ovm)>-1 || $.inArray($.trim(key),ovm)>-1)) { ov.selected ="selected"; }
                                }
                            }
                        }
                        setAttributes(elem, options, ['value']);
                    }
                    break;
                case "text" :
                case "password" :
                case "button" :
                    var role;
                    if(eltype==="button") { role = "button"; }
                    else { role = "textbox"; }
                    elem = document.createElement("input");
                    elem.type = eltype;
                    elem.value = vl;
                    setAttributes(elem, options);
					/*				if(options.trigger === ) {

					 }*/
                    //edit 修复编辑框样式 --arenp
                    if(eltype !== "button"){
                        if(autowidth) {
                            if(!options.size) { $(elem).css({width:"100%"}); }
                        } else if (!options.size) { options.size = 20; }
                    }
                    $(elem).attr("role",role).addClass(role);
                    //end
                    break;
                case "image" :
                case "file" :
                    elem = document.createElement("input");
                    elem.type = eltype;
                    setAttributes(elem, options);
                    break;
                case "custom" :
                    elem = document.createElement("div");
                    try {
                        if($.isFunction(options.custom_element)) {
                            var celm = options.custom_element.call($t,vl,options);
                            if(celm) {
                                celm = $(celm).addClass("customelement").attr({id:options.id,name:options.name});
                                $(elem).addClass('pr').empty().append(celm);
                                if(options.trigger) {
                                    $(elem).append('<span class="' + options.trigger + '"></span>');
                                };
                            } else {
                                throw "e2";
                            }
                        } else {
                            throw "e1";
                        }
                    } catch (e) {
                        if (e==="e1") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.nodefined, $.jgrid.edit.bClose);}
                        if (e==="e2") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.novalue,$.jgrid.edit.bClose);}
                        else { $.jgrid.info_dialog($.jgrid.errors.errcap,typeof e==="string"?e:e.message,$.jgrid.edit.bClose); }
                    }
                    break;
            }
            return elem;
        },
// Date Validation Javascript
        checkDate : function (format, date) {
            var daysInFebruary = function(year){
                    // February has 29 days in any year evenly divisible by four,
                    // EXCEPT for centurial years which are not also divisible by 400.
                    return (((year % 4 === 0) && ( year % 100 !== 0 || (year % 400 === 0))) ? 29 : 28 );
                },
                tsp = {}, sep;
            format = format.toLowerCase();
            //we search for /,-,. for the date separator
            if(format.indexOf("/") !== -1) {
                sep = "/";
            } else if(format.indexOf("-") !== -1) {
                sep = "-";
            } else if(format.indexOf(".") !== -1) {
                sep = ".";
            } else {
                sep = "/";
            }
            format = format.split(sep);
            date = date.split(sep);
            if (date.length !== 3) { return false; }
            var j=-1,yln, dln=-1, mln=-1, i;
            for(i=0;i<format.length;i++){
                var dv =  isNaN(date[i]) ? 0 : parseInt(date[i],10);
                tsp[format[i]] = dv;
                yln = format[i];
                if(yln.indexOf("y") !== -1) { j=i; }
                if(yln.indexOf("m") !== -1) { mln=i; }
                if(yln.indexOf("d") !== -1) { dln=i; }
            }
            if (format[j] === "y" || format[j] === "yyyy") {
                yln=4;
            } else if(format[j] ==="yy"){
                yln = 2;
            } else {
                yln = -1;
            }
            var daysInMonth = [0,31,29,31,30,31,30,31,31,30,31,30,31],
                strDate;
            if (j === -1) {
                return false;
            }
            strDate = tsp[format[j]].toString();
            if(yln === 2 && strDate.length === 1) {yln = 1;}
            if (strDate.length !== yln || (tsp[format[j]]===0 && date[j]!=="00")){
                return false;
            }
            if(mln === -1) {
                return false;
            }
            strDate = tsp[format[mln]].toString();
            if (strDate.length<1 || tsp[format[mln]]<1 || tsp[format[mln]]>12){
                return false;
            }
            if(dln === -1) {
                return false;
            }
            strDate = tsp[format[dln]].toString();
            if (strDate.length<1 || tsp[format[dln]]<1 || tsp[format[dln]]>31 || (tsp[format[mln]]===2 && tsp[format[dln]]>daysInFebruary(tsp[format[j]])) || tsp[format[dln]] > daysInMonth[tsp[format[mln]]]){
                return false;
            }
            return true;
        },
        isEmpty : function(val)
        {
            if (val.match(/^\s+$/) || val === "")	{
                return true;
            }
            return false;
        },
        checkTime : function(time){
            // checks only hh:ss (and optional am/pm)
            var re = /^(\d{1,2}):(\d{2})([apAP][Mm])?$/,regs;
            if(!$.jgrid.isEmpty(time))
            {
                regs = time.match(re);
                if(regs) {
                    if(regs[3]) {
                        if(regs[1] < 1 || regs[1] > 12) { return false; }
                    } else {
                        if(regs[1] > 23) { return false; }
                    }
                    if(regs[2] > 59) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        },
        checkValues : function(val, valref, customobject, nam) {
            var edtrul,i, nm, dft, len, g = this, cm = g.p.colModel;
            if(customobject === undefined) {
                if(typeof valref==='string'){
                    for( i =0, len=cm.length;i<len; i++){
                        if(cm[i].name===valref) {
                            edtrul = cm[i].editrules;
                            valref = i;
                            if(cm[i].formoptions != null) { nm = cm[i].formoptions.label; }
                            break;
                        }
                    }
                } else if(valref >=0) {
                    edtrul = cm[valref].editrules;
                }
            } else {
                edtrul = customobject;
                nm = nam===undefined ? "_" : nam;
            }
            if(edtrul) {
                if(!nm) { nm = g.p.colNames != null ? g.p.colNames[valref] : cm[valref].label; }
                if(edtrul.required === true) {
                    if( $.jgrid.isEmpty(val) )  { return [false,nm+": "+$.jgrid.edit.msg.required,""]; }
                }
                // force required
                var rqfield = edtrul.required === false ? false : true;
                if(edtrul.number === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        if(isNaN(val)) { return [false,nm+": "+$.jgrid.edit.msg.number,""]; }
                    }
                }
                if(edtrul.minValue !== undefined && !isNaN(edtrul.minValue)) {
                    if (parseFloat(val) < parseFloat(edtrul.minValue) ) { return [false,nm+": "+$.jgrid.edit.msg.minValue+" "+edtrul.minValue,""];}
                }
                if(edtrul.maxValue !== undefined && !isNaN(edtrul.maxValue)) {
                    if (parseFloat(val) > parseFloat(edtrul.maxValue) ) { return [false,nm+": "+$.jgrid.edit.msg.maxValue+" "+edtrul.maxValue,""];}
                }
                var filter;
                if(edtrul.email === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        // taken from $ Validate plugin
                        filter = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
                        if(!filter.test(val)) {return [false,nm+": "+$.jgrid.edit.msg.email,""];}
                    }
                }
                if(edtrul.integer === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        if(isNaN(val)) { return [false,nm+": "+$.jgrid.edit.msg.integer,""]; }
                        if ((val % 1 !== 0) || (val.indexOf('.') !== -1)) { return [false,nm+": "+$.jgrid.edit.msg.integer,""];}
                    }
                }
                if(edtrul.date === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        if(cm[valref].formatoptions && cm[valref].formatoptions.newformat) {
                            dft = cm[valref].formatoptions.newformat;
                            if( $.jgrid.formatter.date.masks.hasOwnProperty(dft) ) {
                                dft = $.jgrid.formatter.date.masks[dft];
                            }
                        } else {
                            dft = cm[valref].datefmt || "Y-m-d";
                        }
                        if(!$.jgrid.checkDate (dft, val)) { return [false,nm+": "+$.jgrid.edit.msg.date+" - "+dft,""]; }
                    }
                }
                if(edtrul.time === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        if(!$.jgrid.checkTime (val)) { return [false,nm+": "+$.jgrid.edit.msg.date+" - hh:mm (am/pm)",""]; }
                    }
                }
                if(edtrul.url === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        filter = /^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
                        if(!filter.test(val)) {return [false,nm+": "+$.jgrid.edit.msg.url,""];}
                    }
                }
                if(edtrul.custom === true) {
                    if( !(rqfield === false && $.jgrid.isEmpty(val)) ) {
                        if($.isFunction(edtrul.custom_func)) {
                            var ret = edtrul.custom_func.call(g,val,nm,valref);
                            return $.isArray(ret) ? ret : [false,$.jgrid.edit.msg.customarray,""];
                        }
                        return [false,$.jgrid.edit.msg.customfcheck,""];
                    }
                }
            }
            return [true,"",""];
        }
    });
//grid.custom
$.jgrid.extend({
        getColProp : function(colname){
            var ret ={}, $t = this[0];
            if ( !$t.grid ) { return false; }
            var cM = $t.p.colModel, i;
            for ( i=0;i<cM.length;i++ ) {
                if ( cM[i].name === colname ) {
                    ret = cM[i];
                    break;
                }
            }
            return ret;
        },
        setColProp : function(colname, obj){
            //do not set width will not work
            return this.each(function(){
                if ( this.grid ) {
                    if ( obj ) {
                        var cM = this.p.colModel, i;
                        for ( i=0;i<cM.length;i++ ) {
                            if ( cM[i].name === colname ) {
                                $.extend(true, this.p.colModel[i],obj);
                                break;
                            }
                        }
                    }
                }
            });
        },
        sortGrid : function(colname,reload, sor){
            return this.each(function(){
                var $t=this,idx=-1,i, sobj=false;
                if ( !$t.grid ) { return;}
                if ( !colname ) { colname = $t.p.sortname; }
                for ( i=0;i<$t.p.colModel.length;i++ ) {
                    if ( $t.p.colModel[i].index === colname || $t.p.colModel[i].name === colname ) {
                        idx = i;
                        if($t.p.frozenColumns === true && $t.p.colModel[i].frozen === true) {
                            sobj = $t.grid.fhDiv.find("#" + $t.p.id + "_" + colname);
                        }
                        break;
                    }
                }
                if ( idx !== -1 ){
                    var sort = $t.p.colModel[idx].sortable;
                    if(!sobj) {
                        sobj = $t.grid.headers[idx].el;
                    }
                    if ( typeof sort !== 'boolean' ) { sort =  true; }
                    if ( typeof reload !=='boolean' ) { reload = false; }
                    if ( sort ) { $t.sortData("jqgh_"+$t.p.id+"_" + colname, idx, reload, sor, sobj); }
                }
            });
        },
        clearBeforeUnload : function () {
            return this.each(function(){
                var grid = this.grid;
                grid.emptyRows.call(this, true, true); // this work quick enough and reduce the size of memory leaks if we have someone

                $(document).unbind("mouseup.jqGrid" + this.p.id );
                $(grid.hDiv).unbind("mousemove"); // TODO add namespace
                $(this).unbind();

                grid.dragEnd = null;
                grid.dragMove = null;
                grid.dragStart = null;
                grid.emptyRows = null;
                grid.populate = null;
                grid.populateVisible = null;
                grid.scrollGrid = null;
                grid.selectionPreserver = null;

                grid.bDiv = null;
                grid.cDiv = null;
                grid.hDiv = null;
                grid.cols = null;
                var i, l = grid.headers.length;
                for (i = 0; i < l; i++) {
                    grid.headers[i].el = null;
                }

                this.formatCol = null;
                this.sortData = null;
                this.updatepager = null;
                this.refreshIndex = null;
                this.setHeadCheckBox = null;
                this.constructTr = null;
                this.formatter = null;
                this.addXmlData = null;
                this.addJSONData = null;
            });
        },
        GridDestroy : function () {
            return this.each(function(){
                if ( this.grid ) {
                    if ( this.p.pager ) { // if not part of grid
                        $(this.p.pager).remove();
                    }
                    try {
                        $(this).jqGrid('clearBeforeUnload');
                        $("#gbox_"+$.jgrid.jqID(this.id)).remove();
                    } catch (_) {}
                }
            });
        },
        GridUnload : function(){
            return this.each(function(){
                if ( !this.grid ) {return;}
                var defgrid = {id: $(this).attr('id'),cl: $(this).attr('class')};
                if (this.p.pager) {
                    $(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager ui-corner-bottom");
                }
                var newtable = document.createElement('table');
                $(newtable).attr({id:defgrid.id});
                newtable.className = defgrid.cl;
                var gid = $.jgrid.jqID(this.id);
                $(newtable).removeClass("ui-jqgrid-btable");
                if( $(this.p.pager).parents("#gbox_"+gid).length === 1 ) {
                    $(newtable).insertBefore("#gbox_"+gid).show();
                    $(this.p.pager).insertBefore("#gbox_"+gid);
                } else {
                    $(newtable).insertBefore("#gbox_"+gid).show();
                }
                $(this).jqGrid('clearBeforeUnload');
                $("#gbox_"+gid).remove();
            });
        },
        setGridState : function(state) {
            return this.each(function(){
                if ( !this.grid ) {return;}
                var $t = this;
                if(state === 'hidden'){
                    $(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv","#gview_"+$.jgrid.jqID($t.p.id)).slideUp("fast");
                    if($t.p.pager) {$($t.p.pager).slideUp("fast");}
                    if($t.p.toppager) {$($t.p.toppager).slideUp("fast");}
                    if($t.p.toolbar[0]===true) {
                        if( $t.p.toolbar[1] === 'both') {
                            $($t.grid.ubDiv).slideUp("fast");
                        }
                        $($t.grid.uDiv).slideUp("fast");
                    }
                    if($t.p.footerrow) { $(".ui-jqgrid-sdiv","#gbox_"+$.jgrid.jqID($t.p.id)).slideUp("fast"); }
                    $(".ui-jqgrid-titlebar-close span",$t.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
                    $t.p.gridstate = 'hidden';
                } else if(state === 'visible') {
                    $(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv","#gview_"+$.jgrid.jqID($t.p.id)).slideDown("fast");
                    if($t.p.pager) {$($t.p.pager).slideDown("fast");}
                    if($t.p.toppager) {$($t.p.toppager).slideDown("fast");}
                    if($t.p.toolbar[0]===true) {
                        if( $t.p.toolbar[1] === 'both') {
                            $($t.grid.ubDiv).slideDown("fast");
                        }
                        $($t.grid.uDiv).slideDown("fast");
                    }
                    if($t.p.footerrow) { $(".ui-jqgrid-sdiv","#gbox_"+$.jgrid.jqID($t.p.id)).slideDown("fast"); }
                    $(".ui-jqgrid-titlebar-close span",$t.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
                    $t.p.gridstate = 'visible';
                }

            });
        },
        filterToolbar : function(p){
            p = $.extend({
                autosearch: true,
                searchOnEnter : true,
                beforeSearch: null,
                afterSearch: null,
                beforeClear: null,
                afterClear: null,
                searchurl : '',
                stringResult: false,
                groupOp: 'AND',
                defaultSearch : "bw",
                searchOperators : false,
                resetIcon : "x",
                operands : { "eq" :"==", "ne":"!","lt":"<","le":"<=","gt":">","ge":">=","bw":"^","bn":"!^","in":"=","ni":"!=","ew":"|","en":"!@","cn":"~","nc":"!~","nu":"#","nn":"!#"}
            }, $.jgrid.search , p  || {});
            return this.each(function(){
                var $t = this;
                if(this.ftoolbar) { return; }
                var triggerToolbar = function() {
                        var sdata={}, j=0, v, nm, sopt={},so;
                        $.each($t.p.colModel,function(){
                            var $elem = $("#gs_"+$.jgrid.jqID(this.name), (this.frozen===true && $t.p.frozenColumns === true) ?  $t.grid.fhDiv : $t.grid.hDiv);
                            nm = this.index || this.name;
                            if(p.searchOperators ) {
                                so = $elem.parent().prev().children("a").attr("soper") || p.defaultSearch;
                            } else {
                                so  = (this.searchoptions && this.searchoptions.sopt) ? this.searchoptions.sopt[0] : this.stype==='select'?  'eq' : p.defaultSearch;
                            }
                            v = this.stype === "custom" && $.isFunction(this.searchoptions.custom_value) && $elem.length > 0 && $elem[0].nodeName.toUpperCase() === "SPAN" ?
                                this.searchoptions.custom_value.call($t, $elem.children(".customelement:first"), "get") :
                                $elem.val();
                            if(v || so==="nu" || so==="nn") {
                                sdata[nm] = v;
                                sopt[nm] = so;
                                j++;
                            } else {
                                try {
                                    delete $t.p.postData[nm];
                                } catch (z) {}
                            }
                        });
                        var sd =  j>0 ? true : false;
                        if(p.stringResult === true || $t.p.datatype === "local") {
                            var ruleGroup = "{\"groupOp\":\"" + p.groupOp + "\",\"rules\":[";
                            var gi=0;
                            $.each(sdata,function(i,n){
                                if (gi > 0) {ruleGroup += ",";}
                                ruleGroup += "{\"field\":\"" + i + "\",";
                                ruleGroup += "\"op\":\"" + sopt[i] + "\",";
                                n+="";
                                ruleGroup += "\"data\":\"" + n.replace(/\\/g,'\\\\').replace(/\"/g,'\\"') + "\"}";
                                gi++;
                            });
                            ruleGroup += "]}";
                            $.extend($t.p.postData,{filters:ruleGroup});
                            $.each(['searchField', 'searchString', 'searchOper'], function(i, n){
                                if($t.p.postData.hasOwnProperty(n)) { delete $t.p.postData[n];}
                            });
                        } else {
                            $.extend($t.p.postData,sdata);
                        }
                        var saveurl;
                        if($t.p.searchurl) {
                            saveurl = $t.p.url;
                            $($t).jqGrid("setGridParam",{url:$t.p.searchurl});
                        }
                        var bsr = $($t).triggerHandler("jqGridToolbarBeforeSearch") === 'stop' ? true : false;
                        if(!bsr && $.isFunction(p.beforeSearch)){bsr = p.beforeSearch.call($t);}
                        if(!bsr) { $($t).jqGrid("setGridParam",{search:sd}).trigger("reloadGrid",[{page:1}]); }
                        if(saveurl) {$($t).jqGrid("setGridParam",{url:saveurl});}
                        $($t).triggerHandler("jqGridToolbarAfterSearch");
                        if($.isFunction(p.afterSearch)){p.afterSearch.call($t);}
                    },
                    clearToolbar = function(trigger){
                        var sdata={}, j=0, nm;
                        trigger = (typeof trigger !== 'boolean') ? true : trigger;
                        $.each($t.p.colModel,function(){
                            var v, $elem = $("#gs_"+$.jgrid.jqID(this.name),(this.frozen===true && $t.p.frozenColumns === true) ?  $t.grid.fhDiv : $t.grid.hDiv);
                            if(this.searchoptions && this.searchoptions.defaultValue !== undefined) { v = this.searchoptions.defaultValue; }
                            nm = this.index || this.name;
                            switch (this.stype) {
                                case 'select' :
                                    $elem.find("option").each(function (i){
                                        if(i===0) { this.selected = true; }
                                        if ($(this).val() === v) {
                                            this.selected = true;
                                            return false;
                                        }
                                    });
                                    if ( v !== undefined ) {
                                        // post the key and not the text
                                        sdata[nm] = v;
                                        j++;
                                    } else {
                                        try {
                                            delete $t.p.postData[nm];
                                        } catch(e) {}
                                    }
                                    break;
                                case 'text':
                                    $elem.val(v || "");
                                    if(v !== undefined) {
                                        sdata[nm] = v;
                                        j++;
                                    } else {
                                        try {
                                            delete $t.p.postData[nm];
                                        } catch (y){}
                                    }
                                    break;
                                case 'custom':
                                    if ($.isFunction(this.searchoptions.custom_value) && $elem.length > 0 && $elem[0].nodeName.toUpperCase() === "SPAN") {
                                        this.searchoptions.custom_value.call($t, $elem.children(".customelement:first"), "set", v || "");
                                    }
                                    break;
                            }
                        });
                        var sd =  j>0 ? true : false;
                        $t.p.resetsearch =  true;
                        if(p.stringResult === true || $t.p.datatype === "local") {
                            var ruleGroup = "{\"groupOp\":\"" + p.groupOp + "\",\"rules\":[";
                            var gi=0;
                            $.each(sdata,function(i,n){
                                if (gi > 0) {ruleGroup += ",";}
                                ruleGroup += "{\"field\":\"" + i + "\",";
                                ruleGroup += "\"op\":\"" + "eq" + "\",";
                                n+="";
                                ruleGroup += "\"data\":\"" + n.replace(/\\/g,'\\\\').replace(/\"/g,'\\"') + "\"}";
                                gi++;
                            });
                            ruleGroup += "]}";
                            $.extend($t.p.postData,{filters:ruleGroup});
                            $.each(['searchField', 'searchString', 'searchOper'], function(i, n){
                                if($t.p.postData.hasOwnProperty(n)) { delete $t.p.postData[n];}
                            });
                        } else {
                            $.extend($t.p.postData,sdata);
                        }
                        var saveurl;
                        if($t.p.searchurl) {
                            saveurl = $t.p.url;
                            $($t).jqGrid("setGridParam",{url:$t.p.searchurl});
                        }
                        var bcv = $($t).triggerHandler("jqGridToolbarBeforeClear") === 'stop' ? true : false;
                        if(!bcv && $.isFunction(p.beforeClear)){bcv = p.beforeClear.call($t);}
                        if(!bcv) {
                            if(trigger) {
                                $($t).jqGrid("setGridParam",{search:sd}).trigger("reloadGrid",[{page:1}]);
                            }
                        }
                        if(saveurl) {$($t).jqGrid("setGridParam",{url:saveurl});}
                        $($t).triggerHandler("jqGridToolbarAfterClear");
                        if($.isFunction(p.afterClear)){p.afterClear();}
                    },
                    toggleToolbar = function(){
                        var trow = $("tr.ui-search-toolbar",$t.grid.hDiv),
                            trow2 = $t.p.frozenColumns === true ?  $("tr.ui-search-toolbar",$t.grid.fhDiv) : false;
                        if(trow.css("display") === 'none') {
                            trow.show();
                            if(trow2) {
                                trow2.show();
                            }
                        } else {
                            trow.hide();
                            if(trow2) {
                                trow2.hide();
                            }
                        }
                    },
                    buildRuleMenu = function( elem, left, top ){
                        $("#sopt_menu").remove();

                        left=parseInt(left,10);
                        top=parseInt(top,10) + 18;

                        var fs =  $('.ui-jqgrid-view').css('font-size') || '11px';
                        var str = '<ul id="sopt_menu" class="ui-search-menu" role="menu" tabindex="0" style="font-size:'+fs+';left:'+left+'px;top:'+top+'px;">',
                            selected = $(elem).attr("soper"), selclass,
                            aoprs = [], ina;
                        var i=0, nm =$(elem).attr("colname"),len = $t.p.colModel.length;
                        while(i<len) {
                            if($t.p.colModel[i].name === nm) {
                                break;
                            }
                            i++;
                        }
                        var cm = $t.p.colModel[i], options = $.extend({}, cm.searchoptions);
                        if(!options.sopt) {
                            options.sopt = [];
                            options.sopt[0]= cm.stype==='select' ?  'eq' : p.defaultSearch;
                        }
                        $.each(p.odata, function() { aoprs.push(this.oper); });
                        for ( i = 0 ; i < options.sopt.length; i++) {
                            ina = $.inArray(options.sopt[i],aoprs);
                            if(ina !== -1) {
                                selclass = selected === p.odata[ina].oper ? "ui-state-highlight" : "";
                                str += '<li class="ui-menu-item '+selclass+'" role="presentation"><a class="ui-corner-all g-menu-item" tabindex="0" role="menuitem" value="'+p.odata[ina].oper+'" oper="'+p.operands[p.odata[ina].oper]+'"><table cellspacing="0" cellpadding="0" border="0"><tr><td width="25px">'+p.operands[p.odata[ina].oper]+'</td><td>'+ p.odata[ina].text+'</td></tr></table></a></li>';
                            }
                        }
                        str += "</ul>";
                        $('body').append(str);
                        $("#sopt_menu").addClass("ui-menu ui-widget ui-widget-content ui-corner-all");
                        $("#sopt_menu > li > a").hover(
                            function(){ $(this).addClass("ui-state-hover"); },
                            function(){ $(this).removeClass("ui-state-hover"); }
                        ).click(function( e ){
                            var v = $(this).attr("value"),
                                oper = $(this).attr("oper");
                            $($t).triggerHandler("jqGridToolbarSelectOper", [v, oper, elem]);
                            $("#sopt_menu").hide();
                            $(elem).text(oper).attr("soper",v);
                            if(p.autosearch===true){
                                var inpelm = $(elem).parent().next().children()[0];
                                if( $(inpelm).val() || v==="nu" || v ==="nn") {
                                    triggerToolbar();
                                }
                            }
                        });
                    };
                // create the row
                var tr = $("<tr class='ui-search-toolbar' role='rowheader'></tr>");
                var timeoutHnd;
                $.each($t.p.colModel,function(ci){
                    var cm=this, soptions, surl, self, select = "", sot="=", so, i,
                        th = $("<th role='columnheader' class='ui-state-default ui-th-column ui-th-"+$t.p.direction+"'></th>"),
                        thd = $("<div style='position:relative;height:100%;padding-right:0.3em;padding-left:0.3em;'></div>"),
                        stbl = $("<table class='ui-search-table' cellspacing='0'><tr><td class='ui-search-oper'></td><td class='ui-search-input'></td><td class='ui-search-clear'></td></tr></table>");
                    if(this.hidden===true) { $(th).css("display","none");}
                    this.search = this.search === false ? false : true;
                    if(this.stype === undefined) {this.stype='text';}
                    soptions = $.extend({},this.searchoptions || {});
                    if(this.search){
                        if(p.searchOperators) {
                            so  = (soptions.sopt) ? soptions.sopt[0] : cm.stype==='select' ?  'eq' : p.defaultSearch;
                            for(i = 0;i<p.odata.length;i++) {
                                if(p.odata[i].oper === so) {
                                    sot = p.operands[so] || "";
                                    break;
                                }
                            }
                            var st = soptions.searchtitle != null ? soptions.searchtitle : p.operandTitle;
                            select = "<a title='"+st+"' style='padding-right: 0.5em;' soper='"+so+"' class='soptclass' colname='"+this.name+"'>"+sot+"</a>";
                        }
                        $("td:eq(0)",stbl).attr("colindex",ci).append(select);
                        if(soptions.clearSearch === undefined) {
                            soptions.clearSearch = true;
                        }
                        if(soptions.clearSearch) {
                            var csv = p.resetTitle || 'Clear Search Value';
                            $("td:eq(2)",stbl).append("<a title='"+csv+"' style='padding-right: 0.3em;padding-left: 0.3em;' class='clearsearchclass'>"+p.resetIcon+"</a>");
                        } else {
                            $("td:eq(2)", stbl).hide();
                        }
                        switch (this.stype)
                        {
                            case "select":
                                surl = this.surl || soptions.dataUrl;
                                if(surl) {
                                    // data returned should have already constructed html select
                                    // primitive jQuery load
                                    self = thd;
                                    $(self).append(stbl);
                                    $.ajax($.extend({
                                        url: surl,
                                        dataType: "html",
                                        success: function(res) {
                                            if(soptions.buildSelect !== undefined) {
                                                var d = soptions.buildSelect(res);
                                                if (d) {
                                                    $("td:eq(1)",stbl).append(d);
                                                }
                                            } else {
                                                $("td:eq(1)",stbl).append(res);
                                            }
                                            if(soptions.defaultValue !== undefined) { $("select",self).val(soptions.defaultValue); }
                                            $("select",self).attr({name:cm.index || cm.name, id: "gs_"+cm.name});
                                            if(soptions.attr) {$("select",self).attr(soptions.attr);}
                                            $("select",self).css({width: "100%"});
                                            // preserve autoserch
                                            $.jgrid.bindEv.call($t, $("select",self)[0], soptions);
                                            if(p.autosearch===true){
                                                $("select",self).change(function(){
                                                    triggerToolbar();
                                                    return false;
                                                });
                                            }
                                            res=null;
                                        }
                                    }, $.jgrid.ajaxOptions, $t.p.ajaxSelectOptions || {} ));
                                } else {
                                    var oSv, sep, delim;
                                    if(cm.searchoptions) {
                                        oSv = cm.searchoptions.value === undefined ? "" : cm.searchoptions.value;
                                        sep = cm.searchoptions.separator === undefined ? ":" : cm.searchoptions.separator;
                                        delim = cm.searchoptions.delimiter === undefined ? ";" : cm.searchoptions.delimiter;
                                    } else if(cm.editoptions) {
                                        oSv = cm.editoptions.value === undefined ? "" : cm.editoptions.value;
                                        sep = cm.editoptions.separator === undefined ? ":" : cm.editoptions.separator;
                                        delim = cm.editoptions.delimiter === undefined ? ";" : cm.editoptions.delimiter;
                                    }
                                    if (oSv) {
                                        var elem = document.createElement("select");
                                        elem.style.width = "100%";
                                        $(elem).attr({name:cm.index || cm.name, id: "gs_"+cm.name});
                                        var sv, ov, key, k;
                                        if(typeof oSv === "string") {
                                            so = oSv.split(delim);
                                            for(k=0; k<so.length;k++){
                                                sv = so[k].split(sep);
                                                ov = document.createElement("option");
                                                ov.value = sv[0]; ov.innerHTML = sv[1];
                                                elem.appendChild(ov);
                                            }
                                        } else if(typeof oSv === "object" ) {
                                            for (key in oSv) {
                                                if(oSv.hasOwnProperty(key)) {
                                                    ov = document.createElement("option");
                                                    ov.value = key; ov.innerHTML = oSv[key];
                                                    elem.appendChild(ov);
                                                }
                                            }
                                        }
                                        if(soptions.defaultValue !== undefined) { $(elem).val(soptions.defaultValue); }
                                        if(soptions.attr) {$(elem).attr(soptions.attr);}
                                        $(thd).append(stbl);
                                        $.jgrid.bindEv.call($t, elem , soptions);
                                        $("td:eq(1)",stbl).append( elem );
                                        if(p.autosearch===true){
                                            $(elem).change(function(){
                                                triggerToolbar();
                                                return false;
                                            });
                                        }
                                    }
                                }
                                break;
                            case "text":
                                var df = soptions.defaultValue !== undefined ? soptions.defaultValue: "";

                                $("td:eq(1)",stbl).append("<input type='text' style='width:100%;padding:0px;' name='"+(cm.index || cm.name)+"' id='gs_"+cm.name+"' value='"+df+"'/>");
                                $(thd).append(stbl);

                                if(soptions.attr) {$("input",thd).attr(soptions.attr);}
                                $.jgrid.bindEv.call($t, $("input",thd)[0], soptions);
                                if(p.autosearch===true){
                                    if(p.searchOnEnter) {
                                        $("input",thd).keypress(function(e){
                                            var key = e.charCode || e.keyCode || 0;
                                            if(key === 13){
                                                triggerToolbar();
                                                return false;
                                            }
                                            return this;
                                        });
                                    } else {
                                        $("input",thd).keydown(function(e){
                                            var key = e.which;
                                            switch (key) {
                                                case 13:
                                                    return false;
                                                case 9 :
                                                case 16:
                                                case 37:
                                                case 38:
                                                case 39:
                                                case 40:
                                                case 27:
                                                    break;
                                                default :
                                                    if(timeoutHnd) { clearTimeout(timeoutHnd); }
                                                    timeoutHnd = setTimeout(function(){triggerToolbar();},500);
                                            }
                                        });
                                    }
                                }
                                break;
                            case "custom":
                                $("td:eq(1)",stbl).append("<span style='width:95%;padding:0px;' name='"+(cm.index || cm.name)+"' id='gs_"+cm.name+"'/>");
                                $(thd).append(stbl);
                                try {
                                    if($.isFunction(soptions.custom_element)) {
                                        var celm = soptions.custom_element.call($t,soptions.defaultValue !== undefined ? soptions.defaultValue: "",soptions);
                                        if(celm) {
                                            celm = $(celm).addClass("customelement");
                                            $(thd).find(">span").append(celm);
                                        } else {
                                            throw "e2";
                                        }
                                    } else {
                                        throw "e1";
                                    }
                                } catch (e) {
                                    if (e === "e1") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.nodefined,$.jgrid.edit.bClose);}
                                    if (e === "e2") { $.jgrid.info_dialog($.jgrid.errors.errcap,"function 'custom_element' "+$.jgrid.edit.msg.novalue,$.jgrid.edit.bClose);}
                                    else { $.jgrid.info_dialog($.jgrid.errors.errcap,typeof e==="string"?e:e.message,$.jgrid.edit.bClose); }
                                }
                                break;
                        }
                    }
                    $(th).append(thd);
                    $(tr).append(th);
                    if(!p.searchOperators) {
                        $("td:eq(0)",stbl).hide();
                    }
                });
                $("table thead",$t.grid.hDiv).append(tr);
                if(p.searchOperators) {
                    $(".soptclass",tr).click(function(e){
                        var offset = $(this).offset(),
                            left = ( offset.left ),
                            top = ( offset.top);
                        buildRuleMenu(this, left, top );
                        e.stopPropagation();
                    });
                    $("body").on('click', function(e){
                        if(e.target.className !== "soptclass") {
                            $("#sopt_menu").hide();
                        }
                    });
                }
                $(".clearsearchclass",tr).click(function(e){
                    var ptr = $(this).parents("tr:first"),
                        coli = parseInt($("td.ui-search-oper", ptr).attr('colindex'),10),
                        sval  = $.extend({},$t.p.colModel[coli].searchoptions || {}),
                        dval = sval.defaultValue ? sval.defaultValue : "";
                    if($t.p.colModel[coli].stype === "select") {
                        if(dval) {
                            $("td.ui-search-input select", ptr).val( dval );
                        } else {
                            $("td.ui-search-input select", ptr)[0].selectedIndex = 0;
                        }
                    } else {
                        $("td.ui-search-input input", ptr).val( dval );
                    }
                    // ToDo custom search type
                    if(p.autosearch===true){
                        triggerToolbar();
                    }

                });
                this.ftoolbar = true;
                this.triggerToolbar = triggerToolbar;
                this.clearToolbar = clearToolbar;
                this.toggleToolbar = toggleToolbar;
            });
        },
        destroyFilterToolbar: function () {
            return this.each(function () {
                if (!this.ftoolbar) {
                    return;
                }
                this.triggerToolbar = null;
                this.clearToolbar = null;
                this.toggleToolbar = null;
                this.ftoolbar = false;
                $(this.grid.hDiv).find("table thead tr.ui-search-toolbar").remove();
            });
        },
        destroyGroupHeader : function(nullHeader)
        {
            if(nullHeader === undefined) {
                nullHeader = true;
            }
            return this.each(function()
            {
                var $t = this, $tr, i, l, headers, $th, $resizing, grid = $t.grid,
                    thead = $("table.ui-jqgrid-htable thead", grid.hDiv), cm = $t.p.colModel, hc;
                if(!grid) { return; }

                $(this).unbind('.setGroupHeaders');
                $tr = $("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels");
                headers = grid.headers;
                for (i = 0, l = headers.length; i < l; i++) {
                    hc = cm[i].hidden ? "none" : "";
                    $th = $(headers[i].el)
                        .width(headers[i].width)
                        .css('display',hc);
                    try {
                        $th.removeAttr("rowSpan");
                    } catch (rs) {
                        //IE 6/7
                        $th.attr("rowSpan",1);
                    }
                    $tr.append($th);
                    $resizing = $th.children("span.ui-jqgrid-resize");
                    if ($resizing.length>0) {// resizable column
                        $resizing[0].style.height = "";
                    }
                    $th.children("div")[0].style.top = "";
                }
                $(thead).children('tr.ui-jqgrid-labels').remove();
                $(thead).prepend($tr);

                if(nullHeader === true) {
                    $($t).jqGrid('setGridParam',{ 'groupHeader': null});
                }
            });
        },
        setGroupHeaders : function ( o ) {
            o = $.extend({
                useColSpanStyle :  false,
                groupHeaders: []
            },o  || {});
            return this.each(function(){
                this.p.groupHeader = o;
                var ts = this,
                    i, cmi, skip = 0, $tr, $colHeader, th, $th, thStyle,
                    iCol,
                    cghi,
                    //startColumnName,
                    numberOfColumns,
                    titleText,
                    cVisibleColumns,
                    colModel = ts.p.colModel,
                    cml = colModel.length,
                    ths = ts.grid.headers,
                    $htable = $("table.ui-jqgrid-htable", ts.grid.hDiv),
                    $trLabels = $htable.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header"),
                    $thead = $htable.children("thead"),
                    $theadInTable,
                    $firstHeaderRow = $htable.find(".jqg-first-row-header");
                if($firstHeaderRow[0] === undefined) {
                    $firstHeaderRow = $('<tr>', {role: "row", "aria-hidden": "true"}).addClass("jqg-first-row-header").css("height", "auto");
                } else {
                    $firstHeaderRow.empty();
                }
                var $firstRow,
                    inColumnHeader = function (text, columnHeaders) {
                        var length = columnHeaders.length, i;
                        for (i = 0; i < length; i++) {
                            if (columnHeaders[i].startColumnName === text) {
                                return i;
                            }
                        }
                        return -1;
                    };

                $(ts).prepend($thead);
                $tr = $('<tr>', {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");
                for (i = 0; i < cml; i++) {
                    th = ths[i].el;
                    $th = $(th);
                    cmi = colModel[i];
                    // build the next cell for the first header row
                    thStyle = { height: '0px', width: ths[i].width + 'px', display: (cmi.hidden ? 'none' : '')};
                    $("<th>", {role: 'gridcell'}).css(thStyle).addClass("ui-first-th-"+ts.p.direction).appendTo($firstHeaderRow);

                    th.style.width = ""; // remove unneeded style
                    iCol = inColumnHeader(cmi.name, o.groupHeaders);
                    if (iCol >= 0) {
                        cghi = o.groupHeaders[iCol];
                        numberOfColumns = cghi.numberOfColumns;
                        titleText = cghi.titleText;

                        // caclulate the number of visible columns from the next numberOfColumns columns
                        for (cVisibleColumns = 0, iCol = 0; iCol < numberOfColumns && (i + iCol < cml); iCol++) {
                            if (!colModel[i + iCol].hidden) {
                                cVisibleColumns++;
                            }
                        }

                        // The next numberOfColumns headers will be moved in the next row
                        // in the current row will be placed the new column header with the titleText.
                        // The text will be over the cVisibleColumns columns
                        $colHeader = $('<th>').attr({role: "columnheader"})
                            .addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
                            .css({'height':'22px', 'border-top': '0 none'})
                            .html(titleText);
                        if(cVisibleColumns > 0) {
                            $colHeader.attr("colspan", String(cVisibleColumns));
                        }
                        if (ts.p.headertitles) {
                            $colHeader.attr("title", $colHeader.text());
                        }
                        // hide if not a visible cols
                        if( cVisibleColumns === 0) {
                            $colHeader.hide();
                        }

                        $th.before($colHeader); // insert new column header before the current
                        $tr.append(th);         // move the current header in the next row

                        // set the coumter of headers which will be moved in the next row
                        skip = numberOfColumns - 1;
                    } else {
                        if (skip === 0) {
                            if (o.useColSpanStyle) {
                                // expand the header height to two rows
                                $th.attr("rowspan", "2");
                            } else {
                                $('<th>', {role: "columnheader"})
                                    .addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
                                    .css({"display": cmi.hidden ? 'none' : '', 'border-top': '0 none'})
                                    .insertBefore($th);
                                $tr.append(th);
                            }
                        } else {
                            // move the header to the next row
                            //$th.css({"padding-top": "2px", height: "19px"});
                            $tr.append(th);
                            skip--;
                        }
                    }
                }
                $theadInTable = $(ts).children("thead");
                $theadInTable.prepend($firstHeaderRow);
                $tr.insertAfter($trLabels);
                $htable.append($theadInTable);

                if (o.useColSpanStyle) {
                    // Increase the height of resizing span of visible headers
                    $htable.find("span.ui-jqgrid-resize").each(function () {
                        var $parent = $(this).parent();
                        if ($parent.is(":visible")) {
                            this.style.cssText = 'height: ' + $parent.height() + 'px !important; cursor: col-resize;';
                        }
                    });

                    // Set position of the sortable div (the main lable)
                    // with the column header text to the middle of the cell.
                    // One should not do this for hidden headers.
                    $htable.find("div.ui-jqgrid-sortable").each(function () {
                        var $ts = $(this), $parent = $ts.parent();
                        if ($parent.is(":visible") && $parent.is(":has(span.ui-jqgrid-resize)")) {
                            $ts.css('top', ($parent.height() - $ts.outerHeight()) / 2 + 'px');
                        }
                    });
                }

                $firstRow = $theadInTable.find("tr.jqg-first-row-header");
                $(ts).bind('jqGridResizeStop.setGroupHeaders', function (e, nw, idx) {
                    $firstRow.find('th').eq(idx).width(nw);
                });
            });
        },
        setFrozenColumns : function () {
            return this.each(function() {
                if ( !this.grid ) {return;}
                var $t = this, cm = $t.p.colModel,i=0, len = cm.length, maxfrozen = -1, frozen= false;
                // TODO treeGrid and grouping  Support
                if($t.p.subGrid === true || $t.p.treeGrid === true || $t.p.cellEdit === true || $t.p.sortable || $t.p.scroll )
                {
                    return;
                }
                if($t.p.rownumbers) { i++; }
                if($t.p.multiselect) { i++; }

                // get the max index of frozen col
                while(i<len)
                {
                    // from left, no breaking frozen
                    if(cm[i].frozen === true)
                    {
                        frozen = true;
                        maxfrozen = i;
                    } else {
                        break;
                    }
                    i++;
                }
                if( maxfrozen>=0 && frozen) {
                    var top = $t.p.caption ? $($t.grid.cDiv).outerHeight() : 0,
                        hth = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).height();
                    //headers
                    if($t.p.toppager) {
                        top = top + $($t.grid.topDiv).outerHeight();
                    }
                    if($t.p.toolbar[0] === true) {
                        if($t.p.toolbar[1] !== "bottom") {
                            top = top + $($t.grid.uDiv).outerHeight();
                        }
                    }
                    $t.grid.fhDiv = $('<div style="position:absolute;left:0px;top:'+top+'px;height:'+hth+'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>');
                    $t.grid.fbDiv = $('<div style="position:absolute;left:0px;top:'+(parseInt(top,10)+parseInt(hth,10) + 1)+'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>');
                    $("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fhDiv);
                    var htbl = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).clone(true);
                    // groupheader support - only if useColSpanstyle is false
                    if($t.p.groupHeader) {
                        $("tr.jqg-first-row-header, tr.jqg-third-row-header", htbl).each(function(){
                            $("th:gt("+maxfrozen+")",this).remove();
                        });
                        var swapfroz = -1, fdel = -1, cs, rs;
                        $("tr.jqg-second-row-header th", htbl).each(function(){
                            cs= parseInt($(this).attr("colspan"),10);
                            rs= parseInt($(this).attr("rowspan"),10);
                            if(rs) {
                                swapfroz++;
                                fdel++;
                            }
                            if(cs) {
                                swapfroz = swapfroz+cs;
                                fdel++;
                            }
                            if(swapfroz === maxfrozen) {
                                return false;
                            }
                        });
                        if(swapfroz !== maxfrozen) {
                            fdel = maxfrozen;
                        }
                        $("tr.jqg-second-row-header", htbl).each(function(){
                            $("th:gt("+fdel+")",this).remove();
                        });
                    } else {
                        $("tr",htbl).each(function(){
                            $("th:gt("+maxfrozen+")",this).remove();
                        });
                    }
                    $(htbl).width(1);
                    // resizing stuff
                    $($t.grid.fhDiv).append(htbl)
                        .mousemove(function (e) {
                            if($t.grid.resizing){ $t.grid.dragMove(e);return false; }
                        });
                    $($t).bind('jqGridResizeStop.setFrozenColumns', function (e, w, index) {
                        var rhth = $(".ui-jqgrid-htable",$t.grid.fhDiv);
                        $("th:eq("+index+")",rhth).width( w );
                        var btd = $(".ui-jqgrid-btable",$t.grid.fbDiv);
                        $("tr:first td:eq("+index+")",btd).width( w );
                    });
                    // sorting stuff
                    $($t).bind('jqGridSortCol.setFrozenColumns', function (e, index, idxcol) {

                        var previousSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+$t.p.lastsort+")",$t.grid.fhDiv), newSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+idxcol+")",$t.grid.fhDiv);

                        $("span.ui-grid-ico-sort",previousSelectedTh).addClass('ui-state-disabled');
                        $(previousSelectedTh).attr("aria-selected","false");
                        $("span.ui-icon-"+$t.p.sortorder,newSelectedTh).removeClass('ui-state-disabled');
                        $(newSelectedTh).attr("aria-selected","true");
                        if(!$t.p.viewsortcols[0]) {
                            if($t.p.lastsort !== idxcol) {
                                $("span.s-ico",previousSelectedTh).hide();
                                $("span.s-ico",newSelectedTh).show();
                            }
                        }
                    });

                    // data stuff
                    //TODO support for setRowData
                    $("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fbDiv);
                    $($t.grid.bDiv).scroll(function () {
                        $($t.grid.fbDiv).scrollTop($(this).scrollTop());
                    });
                    if($t.p.hoverrows === true) {
                        $("#"+$.jgrid.jqID($t.p.id)).unbind('mouseover').unbind('mouseout');
                    }
                    $($t).bind('jqGridAfterGridComplete.setFrozenColumns', function () {
                        $("#"+$.jgrid.jqID($t.p.id)+"_frozen").remove();
                        $($t.grid.fbDiv).height( $($t.grid.bDiv).height()-17 );
                        var btbl = $("#"+$.jgrid.jqID($t.p.id)).clone(true);
                        $("tr[role=row]",btbl).each(function(){
                            $("td[role=gridcell]:gt("+maxfrozen+")",this).remove();
                        });

                        $(btbl).width(1).attr("id",$t.p.id+"_frozen");
                        $($t.grid.fbDiv).append(btbl);
                        if($t.p.hoverrows === true) {
                            $("tr.jqgrow", btbl).hover(
                                function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).addClass("ui-state-hover"); },
                                function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).removeClass("ui-state-hover"); }
                            );
                            $("tr.jqgrow", "#"+$.jgrid.jqID($t.p.id)).hover(
                                function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").addClass("ui-state-hover");},
                                function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").removeClass("ui-state-hover"); }
                            );
                        }
                        btbl=null;
                    });
                    if(!$t.grid.hDiv.loading) {
                        $($t).triggerHandler("jqGridAfterGridComplete");
                    }
                    $t.p.frozenColumns = true;
                }
            });
        },
        destroyFrozenColumns :  function() {
            return this.each(function() {
                if ( !this.grid ) {return;}
                if(this.p.frozenColumns === true) {
                    var $t = this;
                    $($t.grid.fhDiv).remove();
                    $($t.grid.fbDiv).remove();
                    $t.grid.fhDiv = null; $t.grid.fbDiv=null;
                    $(this).unbind('.setFrozenColumns');
                    if($t.p.hoverrows === true) {
                        var ptr;
                        $("#"+$.jgrid.jqID($t.p.id)).bind('mouseover',function(e) {
                            ptr = $(e.target).closest("tr.jqgrow");
                            if($(ptr).attr("class") !== "ui-subgrid") {
                                $(ptr).addClass("ui-state-hover");
                            }
                        }).bind('mouseout',function(e) {
                            ptr = $(e.target).closest("tr.jqgrow");
                            $(ptr).removeClass("ui-state-hover");
                        });
                    }
                    this.p.frozenColumns = false;
                }
            });
        }
    });

})(jQuery);
