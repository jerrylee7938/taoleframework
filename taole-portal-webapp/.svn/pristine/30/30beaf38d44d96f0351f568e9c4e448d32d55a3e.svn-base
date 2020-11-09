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

	var tokenData;
	var tokenMap = [];
	$.ajax({
		type:'GET',
		url:$service.portal+'/fw.System/com.taole.usersystem.domain.Token$Status/enums',
		dataType:'json',
		async:false,
		success:function(data){
			tokenData = data;
			if(data && data.length > 0){
				for(var i = 0; i < data.length; i++){
					var item = data[i];
					tokenMap[item.name] = item.local;
				}
			}
		},
		failure:function(){
		}
	});
	
	Ext.onReady(function(){
		var entityId = null;
		var entityName = 'us.Token';
		var entityAction = 'create';
		var token_store = null;
		
		
	/***************************************模块管理   end**************************************************/	
		//创建用户网格
		function CreateTokenGrid(){
			
			Ext.define('tokenModel',{
				extend:'Ext.data.Model',
				fields:['id','startDate','sn','status','expireDate','name','owner','attributes','type','entityName']
			});
			var token_store = Ext.create('Ext.data.Store',{
				model: 'tokenModel',//数据模型
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
		    
			token_store.load({
		    	params:{
		    		start: 0,
	        		limit: 20
	        	}
		    });
		    
			var token_grid = Ext.create('Ext.grid.Panel',{
				store: token_store,
				id:'token_grid',
				//title:'应用列表',
				border:false,
				columnLines:true,
				columns:[
					{xtype: 'rownumberer',text: '序号',align:'center',width:40},
					{text:'令牌名称',width:100,dataIndex:'name'},
					{text:'序列号',width:100,dataIndex:'sn'},
					{text:'生成时间',width:100,dataIndex:'startDate'},
					{text:'激活时间',width:100,dataIndex:'activate'},
					{text:'失效时间',width:100,dataIndex:'expireDate'},
					{text:'状态',width:100,dataIndex:'status',renderer:function(v,cls,rec){
						if(v in tokenMap){
							return tokenMap[v];
						}else{
							return v;	
						}
					}},
					{text:'Owner',width:100,dataIndex:'owner'},
					{text:'类型',width:100,dataIndex:'type'},
					{text:'扩展属性',width:100,dataIndex:'attributes'}
				],
				
		        dockedItems: [{
			        xtype: 'pagingtoolbar',
			        store: token_store,  // same store GridPanel is using
			        dock: 'bottom',
			        pageSize:20,
			        displayInfo: true
			    }]
			})
			return token_grid;
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
	        	minHeight:55,	//最小高度
	        	maxHeight:55,	//最大高度
	        	height:55,	
        		border:false,	//是否有边框
	        	layout:'fit',	//布局方式
	        	items:[{
	        		xtype:'form',
	        		frame:true,
	        		cls:'x-plain',
	        		id:'token-search-form',
	        		layout:'hbox',
	        		items:[{
	        			xtype:'combobox',
	        			labelWidth:70,
	        			name:'status',
	        			labelAlign:'right',
	        			margin:'5px 0px 5px 0px',
	        			width:160,
	        			displayField: 'local',
					    valueField: 'name',
					    store:Ext.create("Ext.data.Store",{
					    	fields: ['local', 'name'],
					    	data: tokenData
					    }),
	        			fieldLabel:'状态'
	        		},{
	        			xtype:'textfield',
	        			fieldLabel:'所有者',
	        			name:'owner',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:280
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'生成时间',
	        			name:'startDate',
	        			format:'Y-m-d',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:180
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'激活时间',
	        			format:'Y-m-d',
	        			name:'activate',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:180
	        		},{
	        			xtype:'datefield',
	        			fieldLabel:'失效时间',
	        			format:'Y-m-d',
	        			name:'expireDate',
	        			margin:'5px 0px 5px 0px',
	        			labelAlign:'right',
	        			labelWidth:70,
	        			width:180
	        		},{
	        			xtype:'button',
	        			width:80,
	        			margin:'5px 10px 5px 10px',
	        			text:'查   询',
	        			handler:function(){
	        				doQuery();
	        			}
	        		},{
	        			xtype:'button',
	        			width:80,
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
	        	items:[CreateTokenGrid()]
	        }]
		})
	})
	//重置
	function reset(){
		var form = Ext.getCmp("token-search-form").getForm();
		form.reset();
	}
	//查询
	function doQuery(){
		var params = Ext.ux.FormUtils.getDataObject(Ext.getCmp('token-search-form'));
		var store = Ext.getCmp('token_grid').getStore();
		store.getProxy().extraParams = params;
		store.loadPage(1);
	}
</script>
</body>
</html>