Ext.onReady(function(){

//    var applet=new Ext.contramed.MCAApplet({
//        renderTo:Ext.getBody()
//    });

    /**
     * Grid para mostrar el personal
     */
    var grid = new Ext.contramedadmin.StaffGrid({
        url:'gridstaff.htm'
    });


    var historic=  new Ext.contramedadmin.ComboHistoric({
        fieldLabel: 'Personal',
        urlname: 'personal',
        value: '2',
        listeners:{
            select:function(obj,record,index){
                grid.getStore().setBaseParam('filterStaff',obj.getValue());
                grid.getStore().load({
                    params:{
                        start:0,
                        limit: 15
                    }
                });

            },
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Seleccione el personal que desea ver'
                });
            }
        }
    });
     
    grid.getStore().setBaseParam('filterStaff',historic.getValue());
    grid.getStore().load({
        params:{
            start:0,
            limit: 15
        }
    });

  

    var toolbaraction=new Ext.contramedadmin.insertUpdateDeleteToolbar({
        listeners:{
            submitInsert:function(){
                checkuser('Inserta',grid,'','insertstaff.htm',grid.sm,toolbaraction,applet);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){
                    handleFormulario('Modifica',grid,'Personal','updateStaff.htm',grid.sm,applet);
                }else Ext.Msg.alert('','Seleccione personal');
            },
            submitDelete:function(){
                if(grid.sm.hasSelection()){
                    handledelete(grid,'','idstaff','username','tag','removeStaff.htm');
                }else Ext.Msg.alert('','Seleccione personal');
            }
        }

    });
    Ext.getCmp('insertar').setTooltip('Abre el panel para insertar personal de la Aplicaci&oacute;n');
    Ext.getCmp('modificar').setTooltip('Abre el panel para modificar personal de la Aplicaci&oacute;n');
    Ext.getCmp('borrar').setTooltip('Borrar personal de la Aplicaci&oacute;n');



    var staffPanel = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title:'Gesti&oacute;n de Personal',
        items :[toolbaraction,historic,grid]

    });

    setCentralPanel(staffPanel);

});

/**
 * Panel para comprobar que el usuario existe en el LDAP
 */
function  checkuser(opcion,grid,title,url,seleccion,toolbaraction,applet){

    var usuario=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Usuario administrador',
        name : 'username',
        id:'adminuser',
        allowBlank:false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Introduzca nombre de usuario de administrador'
                });
            }
        }
    });
    var password=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Contrase&ntilde;a administrador',
        name : 'password',
        id:'password',
        inputType:'password',
        allowBlank:false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Introduzca contrase&ntilde;a de administrador'
                });
            }
        }
    });
    var usuarionuevo=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Usuario nuevo',
        name : 'user',
        allowBlank:false,
        id:'user',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Introduzca el nombre del usuario a insertar'
                });
            }
        }
    });

    /**
      *Panel
      */
    var checkpanel= new Ext.form.FormPanel( {
        title:'Comprobaci&oacute;n de role',
        monitorValid: true,
        frame: true,
        items: [
        {
            layout:'column',
            border:false,
            items: [
           
            {
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [usuario]
            },
            {
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [password]
            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [usuarionuevo]
            }]
        }
        ],
        buttons:[
        {
            text:'Comprobar',
            tooltip:'Comprobar personal',
            formBind:true,
            handler:function(){
                if (usuario.isValid() && password.isValid() && usuarionuevo.isValid()){
                    Ext.abada.Ajax.requestJson({
                        url: 'uservalidation.htm',
                        //Parametros
                        params:{
                            adminuser:usuario.getValue(),
                            password:password.getValue(),
                            user:usuarionuevo.getValue()
                        },
                        failure:function(text){
                            usuarionuevo.setValue();
                            if (text && text.reason)
                                Ext.Msg.alert('Error',text.reason);
                        },
                        success:function(text){
                            usuarionuevo.setValue();
                            handleFormulario(opcion,grid,title,url,seleccion,applet);
                        }
                    });
            }
        }
                
    },{
         
        text:'Inicio',
        tooltip:'Volver a Gesti&oacute;n de personal',
        formBind:false,
        handler:function(){
            window.location="staff.htm";
        }

    }
    ]
           
    });
setCentralPanel(checkpanel);

}



/**
* Formulario para la inserccion o actualizacion del tag del Personal
*/
function handleFormulario(opt,grid,title,url,seleccion,applet){
    /**
     *Combo con el tipo de role que tiene el personal (SYSTEM_ADMINISTRATOR,
     *ADMINISTRATIVE,NURSE_PLANT,NURSE_PLANT_SUPERVISOR,NURSE_PHARMACY,
     *PHARMACIST)
     */
    var cbRole= new Ext.contramedadmin.ComboRoleStaff({
        store: new Ext.data.JsonStore({
            url:'comborole.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbRole.setValue(role);
                }
            }
        }),
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Role que tiene el personal respecto a la aplicaci&oacute;n'
                });
            }
        }
    });
    cbRole.getStore().load();

    var idstaff,tag,role,surname2,name,surname1,user,historic;

    if(opt !='Inserta' && seleccion.hasSelection()){
        idstaff=grid.sm.getSelected().get('idstaff');
        tag=grid.sm.getSelected().get('tag');
        role=grid.sm.getSelected().get('role');
        surname2=grid.sm.getSelected().get('surname2');
        surname1=grid.sm.getSelected().get('surname1');
        name=grid.sm.getSelected().get('name');
        user=grid.sm.getSelected().get('username');
        historic=grid.sm.getSelected().get('historic');
    }else{
        //Al insertar datos en la base de datos, cargamos los datos
        //Existentes en el LDAP
        Ext.abada.AjaxData.requestJson({
            url:'loadDataLdap.htm',
            scope:this,
            //autoAbort:true,
            //method:'GET',
            failure:function(text){
            //Ext.Msg.alert('Error',text);//action.result.errors.reason);
            },
            success:function(storeData){
                nombre.setValue(storeData.data[0].name);
                papellido.setValue(storeData.data[0].surname1);
                usuario.setValue(storeData.data[0].username);
                sapellido.setValue(storeData.data[0].surname2);
                cbRole.setValue(storeData.data[0].role);
                tags.setValue(storeData.data[0].tag);
            }
        });
    }
   
   
    /**
     *Botón para la lectura del Tag
     */
//    var buttontag=new Ext.contramed.ButtonMCAApplet({
//        text:'Acceso Etiqueta',
//        tooltip:'Acceder con etiqueta',
//        functionName:'readPCSC',
//        applet:applet,
//        listeners:{
//            read:function(data){
//                tags.setValue(data);
//
//            }
//        }
//    });
    /**
     *Definición de los campos del Panel: Nombre,Apellidos,etc..
     */
    var tags=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Tag',
        name : 'tag',
        id:'tag',
        allowBlank : false,
        value:tag,
        readOnly:true,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Identificador del Tag RFID asignado a &eacute;l'
                });
            },
            specialkey:function(field,e){
                if (Ext.isIE)
                    e.stopEvent();
            }
        }
    });

    var nombre=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Nombre',
        name : 'name',
        id:'name',
        value:name,
        allowBlank : false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre'
                });
            }
        }

    });
    var usuario=new Ext.form.TextField({
        width:150,
        fieldLabel : 'UID',
        name : 'username',
        id:'username',
        readOnly:true,
        value:user,
        allowBlank : false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de usuario'
                });
            },
            specialkey:function(field,e){
                if (Ext.isIE)
                    e.stopEvent();
            }
        }
    });
    var papellido=new Ext.form.TextField({
        width:150,
        fieldLabel : 'Primer Apellido',
        name : 'surname1',
        id:'surname1',
        value:surname1,
        allowBlank : false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Primer apellido'
                });
            }
        }
    });
    var sapellido= new Ext.form.TextField({
        width:150,
        fieldLabel : 'Segundo Apellido',
        name : 'surname2',
        id:'surname2',
        value:surname2,
        allowBlank : false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Segundo apellido'
                });
            }
        }
    });

    var formpanel = new Ext.form.FormPanel( {
        url:url,
        title : opt+'r '+title,
        monitorValid: true,
        width:530,
        frame: true,
        items : [
        {
            layout:'column',
            border:false,
            items: [
            {
                xtype: 'textfield',
                layout: 'form',
                name : 'idstaff',
                hidden:true,
                value: idstaff

            },
            {
                xtype: 'textfield',
                layout: 'form',
                name : 'historic',
                hidden:true,
                value: historic

            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [nombre]
            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [papellido]
            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [sapellido]
            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [usuario]
            },
    
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [cbRole]
            }
            ,
            {
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [tags]
            }
//            ,{
//                columnWidth:0.5,
//                border:false,
//                items: [buttontag]
//            }
            ]
        }],
        buttons:[
        {
            text:opt+'r',
            tooltip:opt+'r personal',
            formBind:true,
            handler:function(){

                if (formpanel.getForm().findField('historic').getValue() && formpanel.getForm().findField('historic').getValue()=='true')
                    submitInsertUpdateStaff(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do correctamente. Usuario dado de alta.',opt);
                else
                    submitInsertUpdateStaff(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do correctamente',opt);
            }
        }
        ]
    });

    var wind=new Ext.Window({
        resizable:true,
        width:530,
        plain:true,
        border:false,
        modal:true,
        layout:'table',
        items:[formpanel]
    });

    wind.show();

    return formpanel;

}
function submitInsertUpdateStaff(form,grid,wind,waitTitle,waitMsg,ok,opt){
    form.getForm().submit({
        method:'POST',
        waitTitle:waitTitle,
        waitMsg:waitMsg,
        failure:function(form,action){
            Ext.Msg.alert('Error',action.result.errors.reason);
        },
        success:function(){
            Ext.Msg.alert('',ok);
            /*grid.getStore().load({
                params:{
                    start:0,
                    limit: 15
                }
            });*/
            wind.close();
            if(opt=='Inserta' || opt=='Modifica'){
                window.location="staff.htm";
            }
        }
    });
}
