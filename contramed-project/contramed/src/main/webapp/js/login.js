Ext.onReady(function() {

    Ext.QuickTips.init();    

    var header = new Ext.Panel({
        id: 'header-panel',
        region:'north',
        frame:true,
        height:80,
        html:
        '<div class=\"x-panel-header\">'+
    '<div style=\"float:left;height:70px;\"><img alt=\" \" src=\"images/logos/contramed.jpg\" height=\"65\" width=\"300\"/></div>'+
    '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/fuhnpaiin.jpg\" height=\"40\" /></div>'+
    '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/hnp.jpg\" height=\"40\" /></div>'+
    '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/sescam.jpg\" height=\"40\" /></div>'+
    '<div style=\"float:right;padding:10px;\"><img alt=\" \" src=\"images/logos/abada.png\" height=\"40\" /></div>'+
    '<div style=\"clear:both; \" />'+
    '</div>'
               
    });

    new Ext.Viewport({
        title: 'Border Layout',
        items: [
            header
        ]
    });

    function formSubmit(){
        if(login.getForm().isValid()){
            login.getForm().submit({
                method:'POST',
                waitTitle:'Conectando',
                waitMsg:'Comprobando usuario y contrase&ntilde;a...',
                failure:function(form,action){
                    Ext.Msg.alert('Error','Nombre de usuario o contrase&ntilde;a incorrectos.');
                    login.getForm().reset();
                },
                success:function(){
                    window.location='main.htm';
                }
            });
        }
    }   

//    var applet=new Ext.contramed.MCAApplet({
//        renderTo:Ext.getBody()
//    });
//
//    var MCAButton=new Ext.contramed.ButtonMCAApplet({
//            //text:'Acceder con Etiqueta',
//            functionName:'readRfid',
//            //functionName:'readFingerPrint',
//            tooltip:'Lee etiqueta; a&ntilde;ade el nombre del usuario en el campo de texto',
//            applet:applet,                       
//            iconCls:'tarjeta',
//            listeners:{
//                read:function(data){
//                    Ext.abada.Ajax.requestJson({
//                        url:'getUserName.htm',
//                        //zzurl:'getUserNameByFinger.htm',
//                        scope:this,
//                        waitTitle:'Conectando',
//                        waitMsg:'Comprobando usuario y contrase&ntilde;a...',
//                        params:{
//                            tag:data
//                        },
//                        failure:function(){
//                            //login.get('username_login').setValue('');
//                            Ext.Msg.alert('Error','Nombre de usuario o contrase&ntilde;a incorrectos.');
//                            MCAButton.forceRead();
//                        },
//                        success:function(storeData){
//                            //login.get('username_login').setValue(storeData.data[0]);
//                            window.location='main.htm';
//                        }
//                    });
//                }
//            }
//        });

    var login = new Ext.form.FormPanel( {
        frame : true,
        width:400,
        height:150,
        region:'center',
        title : 'Acceder',
        url : 'loginvalidation.htm',
        defaultType : 'textfield',
        monitorValid : true,
        items : [ 
        {
            fieldLabel : 'Nombre de Usuario',
            name : 'login',
            id:'username_login',
            allowBlank : false,
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Introduzca su nombre de usuario'
                    });
                }
            }
        }, {
            fieldLabel : 'Contrase&ntilde;a',
            name : 'password',
            allowBlank : false,
            inputType : 'password',
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Introduzca tu contrase&ntilde;a'
                    });
                }
            }
        } ],
        buttons:[{
            //text:'Acceso',
            id:'blogin',
            iconCls:'sesion',
            scale:'large',
            formBind:true,
            handler:formSubmit,
            tooltip:'Acceso a la aplicaci&oacute;n'
        }
//        ,MCAButton
    ]
    });
   
    var win=new Ext.Window({
        layout:'form',
        id:'wlogin',
        width:400,
        height:160,
        closable:false,
        resizable:false,
        plain:true,
        border:false,
        items:[login]
    });

    win.show();
    
    new Ext.KeyNav('wlogin', {
        'enter' : formSubmit,
        scope : login
    });

    MCAButton.forceRead();
});