<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%><%@include file="/include/page.jspf" %><html>
<head>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<style type="text/css">
		.delCls{
			width:70px;
			height:25px;
		}
		#module_panel td {
            padding:2px;
        }
	</style>
<body>
<script type="text/javascript">
	Ext.Loader.setConfig({
	    enabled: true
	});
	Ext.Loader.setPath('Ext.ux', $commonRoot + '/extjs4.2/ux');
	Ext.require(['*','Ext.ux.RowExpander']);
	var logData;
	var logMap = [];
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Log$Level/enums',
		dataType:'json',
		async:false,
		success:function(data){
			logData = data;
			if(data && data.length > 0){
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					logMap[item.name] = item.local;
				}
			}
		},
		failure:function(){
		}
	});
	Ext.onReady(function(){
		var entityId = null;
		var entityName = 'us.Log';
		var entityAction = 'create';
		var app_store = null;
		
		
	/***************************************模块管理   end**************************************************/	
		//创建用户网格
		function CreateLogGrid(){
			
			Ext.define('logModel',{
				extend:'Ext.data.Model',
				fields:['id','createTime','level','source','address','description','event','userName','entityName','description','userId']
			});
			var log_store = Ext.create('Ext.data.Store',{
				model: 'logModel',//数据模型
		        remoteSort: true,
		        autoLoad:false,
		        pageSize:20,
		        proxy: {
		            type: 'ajax',
		            url: $service.portal + '/' + entityName + '/collection/query',
		            reader: {
		                root: 'items',
		                totalProperty: 'total'
		            },
		            simpleSortMode: true
		        }
			});
		    
			log_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var log_grid = Ext.create('Ext.grid.Panel',{
				store: log_store,
				id:'log_grid',
				//title:'应用列表',
				border:false,
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'用户名称',width:100,dataIndex:'userName'},
					{text:'事件',width:100,dataIndex:'event'},
					{text:'日志来源',width:100,dataIndex:'source'},
					{text:'代理类型',width:100,dataIndex:''},
					{text:'IP地址',width:100,dataIndex:'address'},
					{text:'记录时间',width:150,dataIndex:'createTime',renderer:function(v){
						var v = v.replace("T"," ");
						if(v.indexOf("+")!=-1){
							v = v.substring(0,v.indexOf("+"));
						}
						return v;
					}},
					{text:'日志详情',width:300,dataIndex:'description'},
					{text:'日志级别',width:100,dataIndex:'level',renderer:function(v,cls,rec){
						if(v in logMap){
							return logMap[v];
						}else{
							return v;	
						}
					}},
					{text:'用户编码',width:100,dataIndex:'userId'}
				],
				 viewConfig:{  
		                enableTextSelection:true  
		            }, 
				/*selModel: { //选中模型 
		        	selType : 'checkboxmodel'//复选框选择模式Ext.selection.CheckboxModel  
		        },
		        tbar:[{
		        	text:'增加',
		        	iconCls:'',
		        	handler:function(){
		        		addOrUpdateAppInfo('');
		        	}
		        },{
		        	text:'删除',
		        	iconCls:'',
		        	handler:function(){
		        		deleteAppInfo();
		        	}
		        }],
				multiSelect: true,*///是否允许多选
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: log_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return log_grid;
		}
		
		
		Ext.create("Ext.Viewport",{
			layout: {
	            type: 'border',
	            padding: 3
	        },
	        defaults: {
	            split: true
	        },
	        items:[{
	        	region:'north',
	        	height:40,	
        		border:false,	//是否有边框
	        	layout:'fit',	//布局方式
	        	items:[{
	        		xtype:'form',
	        		frame:true,
	        		cls:'x-plain',
	        		id:'log-search-form',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'textfield',
	        			fieldLabel:'用户名称',
	        			name:'userName',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:'17%'
	        		},{
	        			xtype:'combobox',
	        			labelWidth:70,
	        			name:'level',
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:'17%',
	        			displayField: 'local',
					    valueField: 'name',
					    store:Ext.create("Ext.data.Store",{
					    	fields: ['local', 'name'],
					    	data: logData
					    }),
	        			fieldLabel:'日志级别'
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'开始日期',
	        			name:'onlineDate',
	        			format:'Y-m-d',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:'17%'
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'结束日期',
	        			format:'Y-m-d',
	        			name:'offlineDate',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:'17%'
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'IP地址',
	        			name:'address',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:'17%'
	        		},{
	        			xtype:'button',
	        			width:60,
	        			margin:'5px 10px 5px 10px',
	        			text:'查   询',
	        			handler:function(){
	        				doQuery();
	        			}
	        		},{
	        			xtype:'button',
	        			width:60,
	        			margin:'5px 10px 5px 10px',
	        			text:'重置',
	        			handler:function(){
	        				reset();
	        			}
	        		}]
	        	}]
	        },{
	        	region:'center',
	        	layout:'fit',
	        	items:[CreateLogGrid()]
	        }]
		})
	})
	//重置
	function reset(){
		var form = Ext.getCmp("log-search-form").getForm();
		form.reset();
	}
	//查询
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('log-search-form'));
		var store = Ext.getCmp('log_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
</script>
</body>
</html>