/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.onReady(function(){

    /**
     *Grid donde se muestra la lista de Principios Activos
     *
     */
    var grid = new Ext.idmedicationweb.PrincipioActivoGrid({
        title:'Gesti&oacute;n de Principios Activos',
        urlPrincipio:'gridprincipioactivo.htm',
        listeners:{
            submitInsert:function(){
                handleFormPrincipio('Inserta',grid,' Principio Activo','insertPrincipioActivo.htm',grid.sm);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){
                    if(grid.sm.getCount()==1){
                        handleFormPrincipio('Modifica',grid,'Principio Activo','updatePrincipioActivo.htm',grid.sm);
                    }else{
                        Ext.Msg.alert('','Seleccione un &uacute;nico Principio Activo');
                    }
                }else Ext.Msg.alert('','Seleccione un Principio Activo');
            },
            submitDelete:function(){
                if(grid.sm.hasSelection()){
                    handledelete(grid,'Principio Activo','codigo','','','deletePrincipioActivo.htm');
                }else Ext.Msg.alert('','Seleccione un Principio Activo');
            }
        }
    });

    grid.getStore().load({
        params:{
            start: 0,
            limit: 13
        }
    });

    setCentralPanel(grid);

});

function handleFormPrincipio(opt,grid,title,url,selection){

    var codigo,nombre,anulado,codigo_ca,composicion,denominacion,
    formula,peso,dosis,dosis_superficie,dosis_peso,u_dosis,u_dosis_superficie,
    u_dosis_peso,via,frecuencia,aditivo,tooltip='A&ntilde;adir Principio Activo';

    if(opt!='Inserta'&&selection.hasSelection()){
        codigo=selection.getSelected().get('codigo');
        nombre=selection.getSelected().get('nombre');
        anulado=selection.getSelected().get('anulado');
        dosis=selection.getSelected().get('dosis');
        codigo_ca=selection.getSelected().get('codigoCa');
        composicion=selection.getSelected().get('composicion');
        denominacion=selection.getSelected().get('denominacion');
        formula=selection.getSelected().get('formula');
        peso=selection.getSelected().get('peso');
        dosis_superficie=selection.getSelected().get('dosisSuperficie');
        dosis_peso=selection.getSelected().get('dosisPeso');
        u_dosis=selection.getSelected().get('uDosis');
        u_dosis_superficie=selection.getSelected().get('uDosisSuperficie');
        u_dosis_peso=selection.getSelected().get('uDosisPeso');
        via=selection.getSelected().get('via');
        frecuencia=selection.getSelected().get('frecuencia');
        aditivo=selection.getSelected().get('aditivo');
        tooltip='Modificar Principio Activo';
    }

    var formpanel = new Ext.form.FormPanel( {
        title : opt+'ndo '+title,
        url:url,
        monitorValid : true,
        frame : true,
        width:900,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'C&oacute;digo*',
                    name: 'codigo',
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: false,
                    value:codigo
                    
                },
                {
                    xtype:'textfield',
                    fieldLabel: 'Nombre',
                    name: 'nombre',
                    maxLength:31,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:nombre
                },
                {
                    xtype:'textfield',
                    fieldLabel: 'Anulado',
                    name: 'anulado',
                    maxLength:16,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:anulado
                },
                {
                    xtype:'textfield',
                    fieldLabel:'C&oacute;digo Ca',
                    name:'codigo_ca',
                    maxLength:14,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:codigo_ca
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Composici&oacute;n',
                    name:'composicion',
                    maxLength:11,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:composicion
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Denominaci&oacute;n',
                    name:'denominacion',
                    maxLength:12,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:denominacion
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Formula',
                    name:'formula',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:formula
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Peso',
                    name:'peso',
                    maxLength:4,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:peso
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis',
                    name:'dosis',
                    maxLength:5,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:dosis
                },
                {
                    xtype:'textfield',
                    hidden:true,
                    name : 'oldcodigo',
                    allowBlank : true,
                    value:codigo
                },
                {
                    xtype:'label',
                    text:'* Campo obligatorio'

                }

                ]
            },{
                columnWidth:0.5,
                layout: 'form',
                items: [
                
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Superficie',
                    name:'dosis_superficie',
                    maxLength:16,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:dosis_superficie
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Peso',
                    name:'dosis_peso',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:dosis_peso
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis',
                    name:'u_dosis',
                    maxLength:7,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:u_dosis
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Superficie',
                    name:'u_dosis_superficie',
                    maxLength:18,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:u_dosis_superficie
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Peso',
                    name:'u_dosis_peso',
                    maxLength:12,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:u_dosis_peso
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Via',
                    name:'via',
                    regex:/^[0-9]{0,10}$/,
                    regextext:'El campo introducido no es v&aacute;lido',
                    allowBlank: true,
                    value:via
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Frecuencia',
                    name:'frecuencia',
                    regex:/^[0-9]{0,10}$/,
                    regextext:'El campo introducido no es v&aacute;lido',
                    allowBlank: true,
                    value:frecuencia
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Aditivo',
                    name:'aditivo',
                    maxLength:7,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    value:aditivo
                }

                ]
            }]
        }],
        buttons:[{
            text:opt+'r',
            tooltip:tooltip,
            formBind:true,
            handler:function(){
                submitInsertUpdate(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do');
            }
        }]

    });

    var wind=new Ext.Window({
        resizable:true,
        width:900,
        plain:true,
        border:false,
        layout:'table',
        modal:true,
        items:[formpanel]
    });

    wind.show();

    return formpanel;

}