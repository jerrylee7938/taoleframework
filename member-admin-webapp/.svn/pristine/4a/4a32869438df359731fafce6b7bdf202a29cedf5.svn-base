/**
 * 新增、修改、查看会员管理
 */
var required ='<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Taole.user.tradeeExamine.view.AddOrEditPositionWindow", {
	extend: 'Ext.Window',
	alias : 'widget.addOrEditPositionWindow',
	layout: 'border',
	width: 400,
	height: 180,
	modal: true,
	/**
	 * @type Function 
	 * 
	 */
	afterChooseFn: Ext.emptyFn,
	/**
	 * 调用afterChooseFn时的作用域，默认window
	 * @type 
	 */
	scope: window,
	appAdPositionId: null,
	isView: null,
	isRetrieve: null,
	initComponent: function() {
		this.items = [
		      		{//-----------------------------------------------表单
		    			xtype: 'form',
		    			region: 'center',
		    			frame: true,
		    			defaults:{
		    				xtype: 'panel',
		    				baseCls:'x-plain',
		    				frame: true
		    			},
		    			items:[{
		    				//--------------------------------第1行
		    				layout: 'column',
		    				frame: true,
		    				baseCls: 'x-plain',
		    				style: 'margin-top:5px;',
		    				defaults:{
		    					labelAlign: 'right',
		    					labelWidth: 100,
		    					width: 300
		    				},
		    				items:[
		    				       {
		    				    	   xtype:'hidden',
		    				    	   name:'userCardId'
		    				       },{
		    							fieldLabel: '会员卡号',
		    							xtype: 'textfield',
		    							name: 'cardNo',
		    							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
		    							readOnly:true
		    				       }
		    				]
		    			},{
		    				//--------------------------------第1行
		    				layout: 'column',
		    				frame: true,
		    				baseCls: 'x-plain',
		    				style: 'margin-top:5px;',
		    				defaults:{
		    					labelAlign: 'right',
		    					labelWidth: 100,
		    					width: 300
		    				},
		    				items:[{
		    							fieldLabel: '会员姓名',
		    							xtype: 'textfield',
		    							name: 'userName',
		    							fieldStyle:'text-align:left;background-color:#EEEEE0;background-image: none;font-size:12px;color:black;font-family:宋体;',
		    							readOnly:true
		    				       }
		    				]
		    			},{
		    				//--------------------------------第1行
		    				layout: 'column',
		    				frame: true,
		    				baseCls: 'x-plain',
		    				style: 'margin-top:5px;',
		    				defaults:{
		    					labelAlign: 'right',
		    					labelWidth: 100,
		    					width: 300
		    				},
		    				items:[{
		    							fieldLabel: '到期时间',
		    							xtype:'datetimefield',
										format:'Y-m-d',	
										name:'deadline',
		    				       }
		    				]
		    			}
		
		    			]
		    		}];
        this.buttons = [
            {
				text: '确定',
				action: 'confirm'
			}
            ,
            {
				text: '取消',
				action: 'cancel'
			}
        ];
        this.callParent(arguments);
    },
	buttonAlign: 'center'
});