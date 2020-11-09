Ext.onReady(function(){
	//将用户编号存放到浏览器本地存储
	setLocalStorage();
	userButtonPrivilegeUrl();
	//用于避免多次重复加载树
    Ext.override(Ext.data.TreeStore, { 
		load: function(options) { 
			options = options || {}; 
			options.params = options.params || {}; 
			var me = this, 
			node = options.node || me.tree.getRootNode(), 
			root; 
			if (!node) { 
				node = me.setRootNode({ 
					expanded: true 
				}); 
			} 
			if (me.clearOnLoad) { 
				node.removeAll(false); 
			} 
			Ext.applyIf(options, { 
				node: node 
			}); 
			options.params[me.nodeParam] = node ? node.getId() : 'root'; 
			if (node) { 
				node.set('loading', true); 
			} 
			return me.callParent([options]); 
		} 
	});
	
	Ext.override(Ext.grid.RowEditor, {
		addFieldsForColumn : function(column, initial) {
			var me = this, i, length, field;
			if (Ext.isArray(column)) {
				for (i = 0, length = column.length; i < length; i++) {
					me.addFieldsForColumn(column[i], initial);
				}
				return;
			}
			if (column.getEditor) {
				field = column.getEditor(null, {
							xtype : 'displayfield',
							getModelData : function() {
								return null;
							}
						});
				if (column.align === 'right') {
					field.fieldStyle = 'text-align:right';
				}
				if (column.xtype === 'actioncolumn') {
					field.fieldCls += ' ' + Ext.baseCSSPrefix
							+ 'form-action-col-field';
				}
				if (me.isVisible() && me.context) {
					if (field.is('displayfield')) {
						me.renderColumnData(field, me.context.record, column);
					} else {
						field.suspendEvents();
						field.setValue(me.context.record.get(column.dataIndex));
						field.resumeEvents();
					}
				}
				if (column.hidden) {
					me.onColumnHide(column);
				} else if (column.rendered && !initial) {
					me.onColumnShow(column);
				}

				// -- start edit
				me.mon(field, 'change', me.onFieldChange, me);
				// -- end edit
			}
		}
	});
})
Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', $commonRoot + '/extjs4.2/ux');
Ext.require([
    'Ext.form.Panel',
    'Ext.ux.form.ItemSelector',
    'Ext.ux.enum.EnumComboBox',
    'Ext.ux.Format',
    'Ext.ux.FormUtils',
    'Ext.ux.TypeUtils',
    'Ext.tip.QuickTipManager',
    'Ext.ux.ajax.JsonSimlet',
    'Ext.ux.ajax.SimManager','*',
    'Ext.ux.DataTip',
    'Ext.Tab.*',
    'Ext.ux.TabCloseMenu'
]);
function setBtnStatus(id){//该函数用于防止快速连续点击按钮 导致数据混乱
    var delayToChangeBtnStatus= new Ext.util.DelayedTask(function(){
    	Ext.getCmp(id).setDisabled(false);
    })
    Ext.getCmp(id).setDisabled(true);//按钮点击后设置极短的时间不可用，则通过延时函数执行 设置其可用
    delayToChangeBtnStatus.delay(300);
}

/**
 * 获取当前路径
 * 
 * @param {}
 *            msg
 */
function getRootPath(){    //http://localhost:8083/uimcardprj/share/meun.jsp    
	var curWwwPath=window.document.location.href;    // uimcardprj /meun.jsp    
	var pathName=window.document.location.pathname;    
	var pos=curWwwPath.indexOf(pathName);    //  http://localhost:8083    
	var localhostPaht=curWwwPath.substring(0,pos);    ///uimcardprj    
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
	return(localhostPaht+projectName);
}

/**
 * 显示请求等待进度条窗口
 * 
 * @param {}
 *            msg
 */
function showWaitMsg(msg) {
	Ext.MessageBox.show({
				title : '系统提示',
				msg : msg == null ? '正在处理数据,请稍候...' : msg,
				progressText : 'processing now,please wait...',
				width : 300,
				wait : true,
				waitConfig : {
					interval : 150
				}
	});
}

/**
 * 隐藏请求等待进度条窗口
 */
function hideWaitMsg() {
	Ext.MessageBox.hide();
}
/******可编辑表格**********/
function gridRowEditing() {
	Ext.override(Ext.grid.RowEditor, {
		addFieldsForColumn : function(column, initial) {
			var me = this, i, length, field;
			if (Ext.isArray(column)) {
				for (i = 0, length = column.length; i < length; i++) {
					me.addFieldsForColumn(column[i], initial);
				}
				return;
			}
			if (column.getEditor) {
				field = column.getEditor(null, {
							xtype : 'displayfield',
							getModelData : function() {
								return null;
							}
						});
				if (column.align === 'right') {
					field.fieldStyle = 'text-align:right';
				}
				if (column.xtype === 'actioncolumn') {
					field.fieldCls += ' ' + Ext.baseCSSPrefix
							+ 'form-action-col-field';
				}
				if (me.isVisible() && me.context) {
					if (field.is('displayfield')) {
						me.renderColumnData(field, me.context.record, column);
					} else {
						field.suspendEvents();
						field.setValue(me.context.record.get(column.dataIndex));
						field.resumeEvents();
					}
				}
				if (column.hidden) {
					me.onColumnHide(column);
				} else if (column.rendered && !initial) {
					me.onColumnShow(column);
				}

				// -- start edit
				me.mon(field, 'change', me.onFieldChange, me);
				// -- end edit
			}
		}
	});
}

/**
 * 将JS数组转换为JS字符串 表格复选框专用
 */
function jsArray2JsString(arrayChecked, id) {
	var strChecked = "";
	for (var i = 0; i < arrayChecked.length; i++) {
		strChecked = strChecked + arrayChecked[i].get(id) + ',';
	}
	return strChecked.substring(0, strChecked.length - 1)
}

/**
 * 清除Ext.Form表单元素
 */
function clearForm(pForm) {
	var formItems = pForm.items['items'];
	for (i = 0; i < formItems.length; i++) {
		element = formItems[i];
		element.setValue('');
		element.reset(); // 避免出现红色波浪线
	};
}

/**
 * 复制到剪贴板
 */
function copyToClipboard(txt) {
	if (window.clipboardData) {
		window.clipboardData.clearData();
		window.clipboardData.setData("Text", txt);
	} else if (navigator.userAgent.indexOf("Opera") != -1) {
		window.location = txt;
	} else if (window.netscape) {
		try {
			netscape.security.PrivilegeManager
					.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			Ext.Msg
					.alert(
							'提示',
							"复制单元格操作被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'")
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1']
				.createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1']
				.createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"]
				.createInstance(Components.interfaces.nsISupportsString);
		var copytext = txt;
		str.data = copytext;
		trans.setTransferData("text/unicode", str, copytext.length * 2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
			return false;
		clip.setData(trans, null, clipid.kGlobalClipboard);
		// Ext.Msg.alert('提示','单元格内容已成功复制到剪贴板!')
	}
}

// 这个可以验证15位和18位的身份证，并且包含生日和校验位的验证。
function isIdCardNo(num) {
	if (Ext.isEmpty(num))
		return false;
	num = num.toUpperCase();
	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
		Ext.MessageBox.alert('提示',
				'输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
		return false;
	}
	// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	// 下面分别分析出生日期和校验位
	var len, re;
	len = num.length;
	if (len == 15) {
		re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
		var arrSplit = num.match(re);
		// 检查生日日期是否正确
		var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/'
				+ arrSplit[4]);
		var bGoodDay;
		bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
				&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
				&& (dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bGoodDay) {
			Ext.MessageBox.alert('提示', '输入的身份证号里出生日期不对！');
			return false;
		} else {
			// 将15位身份证转成18位
			// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var nTemp = 0, i;
			num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
			for (i = 0; i < 17; i++) {
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			num += arrCh[nTemp % 11];
			return num;
		}
	}
	if (len == 18) {
		re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
		var arrSplit = num.match(re);
		// 检查生日日期是否正确
		var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
				+ arrSplit[4]);
		var bGoodDay;
		bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
				&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
				&& (dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bGoodDay) {
			// alert(dtmBirth.getYear());
			// alert(arrSplit[2]);
			Ext.MessageBox.alert('提示', '输入的身份证号里出生日期不对！');
			return false;
		} else {
			// 检验18位身份证的校验码是否正确。
			// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
			var valnum;
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var nTemp = 0, i;
			for (i = 0; i < 17; i++) {
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[nTemp % 11];
			if (valnum != num.substr(17, 1)) {
				Ext.MessageBox.alert('提示', '18位身份证的校验码不正确！应该为:' + valnum);
				return false;
			}
			return num;
		}
	}
	return false;
}


function showUppder(sum){//将数字金额转换为大写
	var s ="￥";//存放最终返回结果
	var m=0; //用于标记万元以下的是否有值
	if(sum==0){
		s+="零";	
	}else if(Math.floor((sum*100)/Math.pow(10,10))!=0){//判断是否过亿
		var num = Math.floor((sum*100)/Math.pow(10,10));//获取亿元单位的值
		s+=contactString(num,'亿');
		var wan_num = Math.floor((sum*100)%Math.pow(10,10)/Math.pow(10,6));//除去亿  获取万单位的值
		s+=contactString(wan_num,'万');
	}else if(Math.floor((sum*100)/Math.pow(10,6))!=0){//判断是否过万
		var wan_num = Math.floor((sum*100)/Math.pow(10,6));//获取万单位的值
		s+=contactString(wan_num,'万');
	}
	if(Math.floor((sum*100)%Math.pow(10,6)/Math.pow(10,2))!=0){
		var yuan_num = Math.floor((sum*100)%Math.pow(10,6)/Math.pow(10,2));//获取万单位的值
		s+=contactString(yuan_num,'');
	}
	if(Math.floor((sum*100)%Math.pow(10,2))!=0){//至少含有角|分中的一个单位有值
		if(Math.floor((sum*100)%Math.pow(10,6)/Math.pow(10,2))!=0){
			s+='元';
		}
		var jiao_num = Math.floor((sum*100)%Math.pow(10,2));//含角分
		if(Math.floor(jiao_num/10)!=0){//角单位含有值
			s+=checkUpper(Math.floor(jiao_num/10))+checkUnit(2);
			if(Math.floor(jiao_num%10)!=0){//分单位含有值
				s+=checkUpper(Math.floor(jiao_num%10))+checkUnit(1);
			}
		}else if(Math.floor(jiao_num%10)!=0){//角单位没有值 ，则在分之前加零
			s+='零'+checkUpper(Math.floor(jiao_num%10))+checkUnit(1);
		}
	}else{
		s+='元整';
	}
	return s;
}

function contactString(sum,unit){//组合字符串
	var isHasZero=0;
	var str='';
	var i =0;
	var length = sum.toString().length;//获取数值长度
	if(length==4){//当length为4，表示仟位上有值
		for(var n=length-1;n>=0;n--){//循环遍历
			if(n==0){
				if(Math.floor(sum%Math.pow(10,1))!=0){
					str+=checkUpper(Math.floor(sum%Math.pow(10,1)));
					i=1;
				}
			}else{
				if(Math.floor(sum%Math.pow(10,n+1)/Math.pow(10,n))!=0){
					if(isHasZero==1){
						str+='零';
						isHasZero=0;
					}
					str+=checkUpper(Math.floor(sum%Math.pow(10,n+1)/Math.pow(10,n)))+checkUnit(n+3);
					i=1;
				}else if(isHasZero==0){
					isHasZero=1;
				}
			}
		}
	}else if(length==1){//只有个位含有值
		if(Math.floor(sum%Math.pow(10,1))!=0){
			str+='零'+checkUpper(Math.floor(sum%Math.pow(10,1)));
			i=1;
		}
	}else{//其他情况
		for(var n=length-1;n>=0;n--){
			if(n==0){
				if(Math.floor(sum%Math.pow(10,1))!=0){
					str+=checkUpper(Math.floor(sum%Math.pow(10,1)));
					i=1;
				}
			}else{
				if(Math.floor(sum%Math.pow(10,n+1)/Math.pow(10,n))!=0){
					if(isHasZero==1){
						str+='零';
						isHasZero=0;
					}
					str+=checkUpper(Math.floor(sum%Math.pow(10,n+1)/Math.pow(10,n)))+checkUnit(n+3);
					i=1;
				}else if(isHasZero==0){
					isHasZero =1;
				}
			}
		}
	}
	if(i==1){
		str+=unit;
	}
	return str;		
}

function checkUnit(value){//替换单位值
   switch(value){
   	 case 1:
       return "分" ;
     case 2:
       return "角" ;
     case 3:
       return "元" ;
     case 4:
       return "拾" ;
     case 5:
       return "佰" ; 
     case 6:
       return "仟" ;
     case 7:
       return "万" ;
     case 8:
       return "亿" ; 
     default:
       return "" ;
   }
}

function checkUpper(value){//转换大写
		switch (value){
		    case 0:
		    	return "零" ;
			case 1 : 
				return "壹";
			case 2 : 
				return "贰";
			case 3 : 
				return "叁";
			case 4 : 
				return "肆";
			case 5 : 
				return "伍";
			case 6 : 
				return "陆";
			case 7 : 
				return "柒";
			case 8 : 
				return "捌";
			case 9 : 
				return "玖";
			default:
				return "零" ;
		}
}


/**
* Kunoy
* 合并单元格
* @param {} grid  要合并单元格的grid对象
* @param {} cols  要合并哪几列 [1,2,4]
*/
var mergeCells = function(grid,cols){
  var arrayTr=document.getElementById(grid.getId()+"-body").getElementsByTagName('tr');
  var trCount = arrayTr.length;//获取grid中的行记录数
  var arrayTd;
  var td;
  var merge = function(rowspanObj,removeObjs){ //定义合并函数
    if(rowspanObj.rowspan != 1){
      arrayTd =arrayTr[rowspanObj.tr].getElementsByTagName("td"); //合并行
      td=arrayTd[rowspanObj.td-1];
      td.rowSpan=rowspanObj.rowspan;
      td.vAlign="top";        
      Ext.each(removeObjs,function(obj){ //隐身被合并的单元格
       arrayTd =arrayTr[obj.tr].getElementsByTagName("td");
        //arrayTr[obj.tr].deleteCell(obj.td-1);
       arrayTd[obj.td-1].style.display='none';              
      });
    }  
  };  
  var rowspanObj = {}; //要进行跨列操作的td对象{tr:1,td:2,rowspan:5}  
  var removeObjs = []; //要进行删除的td对象[{tr:2,td:2},{tr:3,td:2}]
  var col;
  Ext.each(cols,function(colIndex){ //逐列去操作tr
    var rowspan = 1;
    var divHtml = null;//单元格内的数值    
    for(var i=1;i<trCount;i++){  //i=0表示表头等没用的行
      arrayTd = arrayTr[i].getElementsByTagName("td");//获取对应行中的列
      var cold=0;
      Ext.each(arrayTd,function(Td){ //获取RowNumber列和check列
        if(Td.getAttribute("class").indexOf("x-grid-cell-special") != -1)
          cold++;                
      });
      col=colIndex+cold;//跳过RowNumber列和check列
      if(!divHtml){
        divHtml = arrayTd[col-1].innerHTML;
        rowspanObj = {tr:i,td:col,rowspan:rowspan+2}
      }else{
        var cellText = arrayTd[col-1].innerHTML;
        var addf=function(){ 
          rowspanObj["rowspan"] = rowspanObj["rowspan"]+1;
          removeObjs.push({tr:i,td:col});
          if(i==trCount-1)
            merge(rowspanObj,removeObjs);//执行合并函数
        };
        var mergef=function(){
		  removeObjs =[{tr:2,td:1},{tr:3,td:1}];
          merge(rowspanObj,removeObjs);//执行合并函数
          divHtml = cellText;
          rowspanObj = {tr:i,td:col,rowspan:rowspan}
          removeObjs = [];
        };
        if(cellText == divHtml){
          if(colIndex!=1){ 
            var leftDisplay=arrayTd[col-2].style.display;//判断左边单元格值是否已display
            if(leftDisplay=='none')
              addf();  
            else
              mergef();              
          }else
            addf();                      
        }else
          mergef();      
      }
    }
  });  
};

function getResp(data){
	var flag;
	if(data){
		var result_code = data.code;
		
		if (result_code == undefined){
			var text = data.responseText;
			var data = eval("("+text+")");
			if (data!=null){
			  result_code = data.code;
			}
		}

		if(result_code != '' && result_code != null && result_code != undefined){
			if(data.code!='1'){
				flag = false;
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
	}
	return flag;
}
Ext.apply(Ext.form.field.VTypes, {
	daterange: function (val, field) {
		var date = field.parseDate(val); 
		if (!date) { 
			return false; 
		} 
		if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) { 
			var start = Ext.ComponentQuery.query(field.startDateField);
			if(start.length>0){
				start[0].setMaxValue(date); 
			}
			this.dateRangeMax = date; 
		} else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) { 
			var end = Ext.ComponentQuery.query(field.endDateField);
			if(end.length>0){
				end[0].setMinValue(date);
			}
			this.dateRangeMin = date; 
		} 
		return true; 
	}, 
	daterangeText: '开始日期必须小于结束日期' 
}); 
/**
 * 获取字典store
 * @param {} code
 */
function getDicStore(code,url){
	var dictionaryUrl =url
	if(url=='' || url==null || url==undefined){
		dictionaryUrl =URL_DICTIONARY
	}
	return Ext.create("Ext.data.Store", {
		autoLoad: true,
		fields: ['value', 'name'],
		proxy: {
	        type: 'ajax',
	        url: Ext.util.Format.format(dictionaryUrl, code),
	        reader: {
	            type: 'json'
	        }
	    }		
	}); 
}
function getDicMiaiStore(code){
	return Ext.create("Ext.data.Store", {
		autoLoad: true,
		fields: ['value', 'name'],
		proxy: {
	        type: 'ajax',
	        url: Ext.util.Format.format(URL_DICTIONARY_MIAI, code),
	        reader: {
	            type: 'json'
	        }
	    }		
	}); 
}
/**
 * 共用的从字典store获取字典名称的renderer
 * @param {} store
 * @param {} dicValue
 * @return {}
 */
function commonRenderer(store){
	return function(val){
		for(var i=0;i<store.getCount();i++){
			var record = store.getAt(i);
			if(record.data.value == val){
				return record.data.name;
			}
		}
		return "";
	}
}

/**
 * 给store添加查询条件
 * @param {} f
 * @param {} store
 */
function appendParam(f, store) {
	if (f.isDisabled())return;
	if (f.items) {
    	f.items.each(function(field){
    		appendParam(field, store);
    	});
    }else if(f instanceof Ext.form.DateField){//&&f.getValue()
    	store.proxy.extraParams[f.getName()] = Ext.Date.format(f.getValue(),f.format);
    }else if(f instanceof Ext.form.Radio){
		store.proxy.extraParams[f.getName()] = f.getGroupValue();
	}else if(f instanceof Ext.form.Field){
    	store.proxy.extraParams[f.getName()] = f.getValue();
    }
};
/**
 * 隐藏/显示列
 * @param {} grid
 * @param {} columnIndexes 列索引号
 * @param {} visible
 */
function commonShowColumn(grid, columnIndexes, visible){
	Ext.each(columnIndexes, function(index){
		grid.columns[index].setVisible(visible);
	});	
}

function columnWidth(grid, columnIndexes, width){
	Ext.each(columnIndexes, function(index){
		grid.columns[index].setWidth(width);
	});	
}
/**
 * 打开菜单
 * @param {} menuId
 */
function commonOpenMenu(menuId){
	commonGetMenu(menuId, function(menu){
		exec_main(menu.id, menu.name, $syspath +"/"+ menu.url);
	});
}

/**
 * 打开菜单（带参数）
 * @param {} menuId
 * @param {} parameter  要传的参数from=needPlan&projectId=projectId
 */
function commonOpenMenuParameter(menuId,parameter){
	commonGetMenu(menuId, function(menu){
		exec_main(menu.id, parameter, $syspath);
	});
}

/**
 * 刷新菜单页面
 * @param {} menuId
 */
function commonRefreshMenu(menuId){
	if(parent&&parent.refreshTab){
		commonGetMenu(menuId, function(menu){
			parent.refreshTab(menu.id, false);
		});
	}    	   	
}
/**
 * 获取菜单
 * @param {} successFn
 */
function commonGetMenu(menuId, successFn){
	Ext.Ajax.request({
		url: Ext.util.Format.format(URL_MENU_GET, menuId),
		success: function(response){
			var data = Ext.decode(response.responseText);
			if(getResp(data)){
				successFn.call(this,data);
			}else{
				Ext.Msg.alert('提示','获取菜单信息失败!<br/>'+data.result_code+":"+data.result_desc);
			}
		},
		failure: function(){
			Ext.Msg.alert("提示", "获取菜单信息失败");
		}
	});	
}

function numChange(number, places, symbol, thousand, decimal) {
    number = number || 0;
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "￥";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
}

function moneyFormat(number, places, symbol, thousand, decimal) {
    number = number || 0;
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "元";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "")+symbol;
}

function numFormat(num){
	var ceilNum = Math.ceil(num);
	var floorNum = Math.floor(num);
	if(num%1 == 0){
		return num;
	}
	if(num+0.5-floorNum>1){
		return ceilNum;
	}else{
		return floorNum + 0.5;
	}
}

function getMaterialSpec(specId, successFn){
	Ext.Ajax.request({
		url: $service.rest+'/mms.MaterialSpec/'+specId+'/retrieve',
		success: function(response){
			var data = Ext.decode(response.responseText);
			if(getResp(data)){
				successFn.call(this,data);
			}else{
				Ext.Msg.alert('提示','获取物资信息失败!<br/>'+data.result_code+":"+data.result_desc);
			}
		},
		failure: function(){
			Ext.Msg.alert("提示", "获取物资信息失败");
		}
	});	
}

//获取用户按钮权限接口
function userButtonPrivilegeUrl(){

	var userButtonIsHave = sessionStorage.getItem('userButtonIsHave');
	var isUserId = sessionStorage.getItem('isUserId');
	if(userButtonIsHave=='true' && isUserId=='true'){return;}
	$.ajax({
	    "method": "get",
	    "url": $service.portal + "/us.User/collection/userButtonPrivilege?userId="+getQueryValue('loginUserId'),
	    "success": function (r) {
	        if (r.code == 1) {
	        	var userButtonPrivilegeUrl = JSON.stringify(r.result);
	        		sessionStorage.setItem("userButtonIsHave", true);
	        	localStorage.setItem("userButtonPrivilegeUrl", userButtonPrivilegeUrl);
	        } else {
	      	  console.log(r.description);
	        }
	    },
	    "Error": function (text) {
	    	console.log(text);
	    }
	});
}

function validateButtonPrivilege(buttons){
	var userButtonPrivilegeUrlAry = JSON.parse(localStorage.getItem("userButtonPrivilegeUrl"));
	console.log(userButtonPrivilegeUrlAry);
	
	Ext.each(buttons, function(btn){
		//console.log(btn)
		var btnHref = btn.privilegeUrl;
		if(btnHref){
			//如果按钮配置url参数则校验此按钮权限
			if(userButtonPrivilegeUrlAry && userButtonPrivilegeUrlAry.length>0){
				Ext.each(userButtonPrivilegeUrlAry, function(url){
					if(btnHref == url){
						btn.show();
						return false;
					}else {
						btn.hide();
					}
				});
			}else {
				btn.hide();
			}
			
		}
	});
}
function setLocalStorage(){
	var userId = getQueryValue('loginUserId');
	var lastUserId =localStorage.getItem("loginUserId");
	if(userId == lastUserId){
		sessionStorage.setItem("isUserId", true);
	}else{
		localStorage.setItem("loginUserId", userId);
		sessionStorage.setItem("isUserId", false);
	}
}

function getQueryValue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r != null) return decodeURI(r[2]);
    return null;
}

hookAjax({
    //拦截回调
    onreadystatechange:function(xhr){
        //console.log("onreadystatechange called: %O",xhr)
    },
    onload:function(xhr){
        //console.log("onload called: %O",xhr)
    },
    //拦截函数
    open:function(arg){
    	var userId = localStorage.getItem("loginUserId");
    	if(userId != null && userId != undefined){
    		var url = arg[1];
    		if(url.indexOf("?") > -1)
    			arg[1]+="&dataPrivilegeUserId=" + userId;
    		else 
    			arg[1]+="?dataPrivilegeUserId=" + userId;
		}
     	
    }
})
