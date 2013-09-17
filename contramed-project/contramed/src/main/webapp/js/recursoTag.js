/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *@david
 */

Ext.onReady(function(){
//    var applet=new Ext.contramed.MCAApplet({
//        renderTo:Ext.getBody()
//    });

    /**
     *Grid donde se muestra la lista de RecursoTag
     **/
    var grid = new Ext.contramedadmin.RecursoTagGrid({
        url:'gridRecursoTag.htm'
    });

    grid.getStore().load({
        params:{
            start:0,
            limit: 13
        }
    });
    /*Toolbar insert modificacion borrado*/
    var recursoTagPanel = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title:'Gesti&oacute;n de Tags de camas/recursos',
        items :[new Ext.contramedadmin.insertUpdateDeleteToolbar({

            listeners:{
                submitInsert:function(){
                    handleFormulario('Inserta',grid,'cama/recurso','insertRecursoTag.htm',grid.sm,applet);
                },
                submitUpdate:function(){
                    if(grid.sm.hasSelection()){
                        if(grid.sm.getCount()==1){
                            handleFormulario('Modifica',grid,'cama/recurso','updateRecursoTag.htm',grid.sm,applet);
                        }else{
                            Ext.Msg.alert('','Seleccione una &uacute;nica Cama');
                        }
                    }else Ext.Msg.alert('','Seleccione un Tag de cama/recurso');
                },
                submitDelete:function(){
                    if(grid.sm.hasSelection()){
                        handledelete(grid,'cama/recurso','id','','','deleteRecursoTag.htm');
                    }else Ext.Msg.alert('','Seleccione Tag de cama/recurso');
                }
            }

        }),grid ]

    });
    Ext.getCmp('insertar').setTooltip('Abre el panel para asociar un Tag a una cama');
    Ext.getCmp('modificar').setTooltip('Abre el panel para modificar una asociaci&oacute;n entre Tag y cama');
    Ext.getCmp('borrar').setTooltip('Borrar la asociaci&oacute;n entre Tag y cama');
    setCentralPanel(recursoTagPanel);

});


/**
 *Funcion para los frompanel
 *
 */

function handleFormulario(opt,grid,title,url,selecion,applet){
    var tag,idrecurso,id,tooltip='A&ntilde;adir Asociaci&oacute;n Cama-Tag';
    if(opt!='Inserta'&&selecion.hasSelection()){
        idrecurso=selecion.getSelected().get('idrecurso');
        tag=selecion.getSelected().get('tag');
        id=selecion.getSelected().get('id');
        tooltip='Modificar Asociaci&oacute;n Cama-Tag';
    }
    var cbRecursoTag= new Ext.contramedadmin.ComboxRecursoTag({
        store: new Ext.data.JsonStore({
            url:'comboRecursoTag.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbRecursoTag.setValue(idrecurso);
                }
            }
        }),
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Combo con todas las camas'
                });
            }
        }

    });

    cbRecursoTag.getStore().load();
//    var buttontag=new Ext.contramed.ButtonMCAApplet({
//        text:'Leer Etiqueta',
//        functionName:'readPCSC',
//        tooltip:'Coge id de la tarjeta Rfid',
//        applet:applet,
//        listeners:{
//            read:function(data){ 
//                tagg.setValue(data);
//
//            }
//        }
//    });


    var tagg =new Ext.form.TextField({
        width:150,
        fieldLabel : 'Tag',
        name : 'tag',
        id:'tag',
        readOnly:true,
        value:tag,
        allowBlank : false,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Id de la tarjeta Rfid'
                });
            },
            specialkey:function(field,e){
                if (Ext.isIE)
                    e.stopEvent();
            }
        }
    });


    //form panel de recurso tag
    var formpanel = new Ext.form.FormPanel( {
        title : opt+'r '+title,
        url:url,
        monitorValid: true,
        frame: true,
        width: 550,
        items : [
        {
            layout:'column',
            
            border:false,
            items:[{
                xtype: 'textfield',
                layout: 'form',
                name : 'id',
                hidden:true,
                value: id
            },{
                xtype: 'textfield',
                layout: 'form',
                name : 'idrecursoorigen',
                hidden:true,
                value: idrecurso
            },{
                xtype: 'textfield',
                layout: 'form',
                name : 'tagorigen',
                hidden:true,
                value: tag
            },{
                columnWidth:0.5,
                layout: 'form',
                border:false,
                items: [tagg]
            },
//            {
//                columnWidth:0.5,
//                border:false,
//                items: [buttontag]
//            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [cbRecursoTag]
            }]
        }],
        buttons:[{
            text:opt+'r',
            formBind:true,
            tooltip:tooltip,
            handler:function(){
                submitInsertUpdate(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do');
            }
        }]
    });

    var wind=new Ext.Window({
        autoScroll:false,
        closable:true,
        modal:true,
        width: 550,
        layout: 'table',
        items:[formpanel]
    });

    wind.show();

    return formpanel;
}
