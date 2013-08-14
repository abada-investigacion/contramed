Ext.onReady(function(){

	Ext.QuickTips.init();
	
	var mypanel = new Ext.Panel({  

		//html: 'contenido inicial',
		id:'container2',  
		border: false,
		autoWidth       : true,
                autoHeight      : true,
		loadScripts: true,
		loadMask: true,
                autoScroll: true
	});  

	var header = new Ext.Panel({
		id: 'header-panel',
		region:'north',		
		//border: false,
                frame:true,
		//autoHeight: true,
                height:80,
                html:
                '<div class=\"x-panel-header\">'+
                //'<div style=\"float:left;padding-top:23px;height:70px;\"><a style=\"text-decoration:none;color:#77AB44;\" href=\"main.htm\"><h1><@spring.message code="title" /></h1></a></div>'+
                '<div style=\"float:left;\"><a style=\"text-decoration:none;color:#77AB44;\" href=\"main.htm\"><img alt=\"<@spring.message code="title" />\" src=\"images/logos/contramed.jpg\" height=\"65\" width=\"300\"></img></a></div>'+
                '<div style=\"float:left;padding-top:0px;padding-left:15px;font-size:10px;height:65px;text-align:center;\">'+
<#if username??>
                '<div><h1>${username}</h1></div>'+
</#if>
<#if name??>
                '<div><h1>${name}</h1></div>'+
</#if>
<#if role??>
                '<div><h1><@spring.message code="${role}" /></h1></div>'+
</#if>
                '<div><h1><@spring.message code="version" /></h1></div>'+
                '</div>'+
                '<div style=\"float:right;padding:15px;padding-left:20px;padding-top:15px;\"><a style=\"text-decoration:none;color:#77AB44;\" href=\"login.htm\"><img height=\"20\"  alt=\"Salir\" src=\"images/custom/exit.png\" ></img></a></div>'+
                '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/fuhnpaiin.jpg\" height=\"40\" /></div>'+
                '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/hnp.jpg\" height=\"40\" /></div>'+
                '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/sescam.jpg\" height=\"40\" /></div>'+
                '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/abada.png\" height=\"40\" /></div>'+
                '<div style=\"clear:both; \" />'+
                '</div>'
                //title:'<@spring.message code="title" />'
	});

<#if usermenu??>
    <#assign i = 0 />
    <#list usermenu as menuentry >
            var button${i}=new Ext.menu.Item({
                text:'${menuentry.text}',
                style:{
                    /*height:Ext.ACCESIBLE_HEIGHT+'px',left: '24px',*/fontSize:'medium',margin:'4px'
                },
                iconCls: '${menuentry.iconCls}',                
                handler:function(){
                    window.location='${menuentry.action}';
                }
                <#if (menuentry.tooltip != "")>
                    ,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: '${menuentry.tooltip}'
                            });
                        }
                    }
                </#if>
            });
            <#assign i = i +1 />
    </#list>
            /**
             * menu for users functions
             */
            var groupUser = new Ext.Panel({
                    floating:false,                    
                    frame:true,
                    title: 'Funciones Usuario',
                    collapsible:true,
                    titleCollapse: true,
                    frame:false,
                    layout:'menu',
                    autoScroll : true,
                    items:[
    <#if (i > 0) >
        <#if (i > 1) >
            <#list 0..(i-2) as i2 >
                button${i2},
            </#list>
        </#if>
        button${i-1}
    </#if>
                    ]
            });

	var menu = new Ext.Panel({
                floating:false,                
		split:true,
		collapsible: true,
                collapsed:true,
                title:'Men&uacute;',
		frame:true,                
                width:300,
                //autoHeight:true,
		autoScroll : true,
                region:'west',
		items: [                                                            
                    groupUser
                ]
	});
</#if>
	var viewportTemplate= new Ext.Viewport({  
		title: 'Border Layout',  
		layout:'border',                
		items: [
		        header,
<#if usermenu??>
		        menu,
</#if>
		        {  
		        	border:false,
		        	id: 'container',
		        	region:'center',  
		        	autoScroll: true,
		        	items: [mypanel],
		        	loadMask:true
		        }]		        
	});
	
});
