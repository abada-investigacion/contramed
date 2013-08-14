/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.onReady(function(){

    /**
     *Grid donde se muestra la lista de Especialidades
     *
     */
    var grid = new Ext.idmedicationweb.MedicineGrid({
        title:'Gesti&oacute;n de Especialidades',
        urlMedicamento:'gridcatmedicamentos.htm',
        listeners:{
            submitInsert:function(){
                handleInsertMedicamento(grid);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){

                    handleUpdateMedicamento(grid.sm,grid);
                }else Ext.Msg.alert('','Seleccione una especialidad');
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

function handleInsertMedicamento(grid){


    var cbFrecuencia=  new Ext.idmedicationweb.CbFrecuencia({
        store: new Ext.data.JsonStore({
            url:'combofrecuencia.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value']
        }),
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: ''
                });
            }
        }
    });

    cbFrecuencia.getStore().load();

    var cbVia=  new Ext.idmedicationweb.CbVia({
        store: new Ext.data.JsonStore({
            url:'combovia.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value']
        }),
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: ''
                });
            }
        }
    });

    cbVia.getStore().load();

    var insertpanel = new Ext.form.FormPanel( {
        title : 'Insertar Especialidad',
        url:'insertCatMedicamentos.htm',
        monitorValid : true,
        frame : true,
        width:900,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.33,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'C&oacute;digo*',
                    name: 'codigo',
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: false,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'C&oacute;digo nacional de la especialidad'
                            });
                        }
                    }
                },
                {
                    xtype:'textfield',
                    fieldLabel: 'Nombre',
                    name: 'nombre',
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Nombre de la especialidad'
                            });
                        }
                    }
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Anulado',
                    urlname: 'anulado'
                }),
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Bioequivalente',
                    urlname: 'bioequivalente'

                }),
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Caducidad',
                    urlname: 'caducidad',
                    allowBlank: true                  

                }),
                {
                    xtype:'textfield',
                    fieldLabel: 'Caracter&iacute;sticas',
                    name: 'caracteristicas',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'C&oacute;digo Grupo',
                    name:'codGrupo',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'C&oacute;digo Laboratorio',
                    name:'codLab',
                    maxLength:255,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Conservaci&oacute;n',
                    name:'conservacion',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dispensaci&oacute;n',
                    name:'dispensacion',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Especial Control',
                    urlname: 'especialControl'

                    
                }),
                
                {
                    xtype:'label',
                    text:'* Campo obligatorio'

                }

                ]
            },{
                columnWidth:0.33,
                layout: 'form',
                items: [
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Fecha Alta',
                    urlname: 'fechaAlta',
                    allowBlank: true                    

                }),
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Fecha Baja',
                    urlname: 'fechaBaja',
                    allowBlank: true                 

                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Ficha',
                    name:'ficha',
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Forma Farma',
                    name:'formaFarma',
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Gen&eacute;rico',
                    urlname: 'generico'

                }),
                
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Larga Duraci&oacute;n',
                    urlname: 'largaDuracion'

                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Nombre Laboratorio',
                    name:'nombreLab',
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Precio Ref',
                    name:'precioRef',
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'PVP',
                    name:'pvp',
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'R&eacute;gimen',
                    name:'regimen',
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis',
                    name:'dosis',
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                }                
                
                ]
            },{
                columnWidth:0.33,
                layout: 'form',
                items: [
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Superficie',
                    name:'dosisSuperficie',
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Peso',
                    name:'dosisPeso',
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis',
                    name:'uDosis',
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Superficie',
                    name:'uDosisSuperficie',
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Peso',
                    name:'uDosisPeso',
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                },
                cbVia,
                cbFrecuencia,
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Diluyente*',
                    urlname: 'diluyente'
                //allowBlank: false

                }),
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Aditivo*',
                    urlname: 'aditivo',
                    allowBlank: false

                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Diluyente',
                    name:'dosisDiluyente',
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    allowBlank: true
                }
                ]
            }]
        }],
        buttons:[{
            text:'Insertar',
            tooltip:'Insertar especialidad',
            formBind:true,
            handler:function(){
                var updatepanel=undefined;
                submitMedicamento(insertpanel,updatepanel,grid,wind);
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
        items:[insertpanel]
    });

    wind.show();

    return insertpanel;
}


function handleUpdateMedicamento(sm,grid){

    /***
 *Precarga de la especialidad seleccionada en el grid
 *
 */
    if(sm.hasSelection()){
        var codigo=sm.getSelected().get('codigo');
        var nombre=sm.getSelected().get('nombre');
        var anulado=sm.getSelected().get('anulado');
        var bioequivalente=sm.getSelected().get('bioequivalente');

        var caducidad_aux=new Date();
        caducidad_aux=sm.getSelected().get('caducidad');
        var posicion=caducidad_aux.indexOf(' ');
        var caducidad=new Date();
        caducidad=caducidad_aux.substring(0, posicion);

        var caracteristicas=sm.getSelected().get('caracteristicas');
        var codGrupo=sm.getSelected().get('codGrupo');
        var codLab=sm.getSelected().get('codLab');
        var conservacion=sm.getSelected().get('conservacion');
        var dispensacion=sm.getSelected().get('dispensacion');
        var especialControl=sm.getSelected().get('especialControl');

        var fechaAlta_aux=new Date();
        fechaAlta_aux=sm.getSelected().get('fechaAlta');
        var fechaAlta=new Date();
        fechaAlta=fechaAlta_aux.substring(0, posicion);

        var fechaBaja_aux=new Date();
        fechaBaja_aux=sm.getSelected().get('fechaBaja');
        var fechaBaja=new Date();
        fechaBaja=fechaBaja_aux.substring(0, posicion);

        var ficha=sm.getSelected().get('ficha');
        var formaFarma=sm.getSelected().get('formaFarma');
        var generico=sm.getSelected().get('generico');
        var largaDuracion=sm.getSelected().get('largaDuracion');
        var nombreLab=sm.getSelected().get('nombreLab');
        var precioRef=sm.getSelected().get('precioRef');
        var pvp=sm.getSelected().get('pvp');
        var regimen=sm.getSelected().get('regimen');
        var dosis=sm.getSelected().get('dosis');
        var dosisSuperficie=sm.getSelected().get('dosisSuperficie');
        var dosisPeso=sm.getSelected().get('dosisPeso');
        var uDosis=sm.getSelected().get('uDosis');
        var uDosisSuperficie=sm.getSelected().get('uDosisSuperficie');
        var uDosisPeso=sm.getSelected().get('uDosisPeso');
        var via=sm.getSelected().get('via_id');
        var frecuencia=sm.getSelected().get('frecuencia_idTarea');
        var diluyente=sm.getSelected().get('diluyente');
        var aditivo=sm.getSelected().get('aditivo');
        var dosisDiluyente=sm.getSelected().get('dosisDiluyente');


    }


    var cbFrecuencia=  new Ext.idmedicationweb.CbFrecuencia({
        store: new Ext.data.JsonStore({
            url:'combofrecuencia.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbFrecuencia.setValue(frecuencia);
                }
            }
        }),
        listeners:{
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: ''
                });
            }
        }
    });

    cbFrecuencia.getStore().load();

    var cbVia=  new Ext.idmedicationweb.CbVia({
        store: new Ext.data.JsonStore({
            url:'combovia.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbVia.setValue(via);
                }
            }
        }),
        listeners:{
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: ''
                });
            }
        }
    });

    cbVia.getStore().load();

    var updatepanel = new Ext.form.FormPanel( {
        title : 'Modificar Especialidad',
        url:'updateCatMedicamentos.htm',
        monitorValid : true,
        frame : true,
        width:900,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.33,
                layout: 'form',
                items: [
                {
                    xtype:'textfield',
                    fieldLabel: 'C&oacute;digo*',
                    name: 'newcodigo',
                    allowBlank: false,
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:codigo,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'C&oacute;digo nacional de la especialidad'
                            });
                        }
                    }
                },
                {
                    xtype:'textfield',
                    fieldLabel: 'Nombre',
                    name: 'nombre',
                    allowBlank: true,
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:nombre,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Nombre de la especialidad'
                            });
                        }
                    }
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Anulado',
                    urlname: 'anulado',
                    value:anulado

                }),
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Bioequivalente',
                    urlname: 'bioequivalente',
                    value:bioequivalente

                }),
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Caducidad',
                    urlname: 'caducidad',
                    allowBlank: true,
                    value:caducidad
                }),
                {
                    xtype:'textfield',
                    fieldLabel: 'Caracter&iacute;sticas',
                    name: 'caracteristicas',
                    allowBlank: true,
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:caracteristicas
                },
                {
                    xtype:'textfield',
                    fieldLabel:'C&oacute;digo Grupo',
                    name:'codGrupo',
                    allowBlank: true,
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:codGrupo
                },
                {
                    xtype:'textfield',
                    fieldLabel:'C&oacute;digo Laboratorio',
                    name:'codLab',
                    allowBlank: true,
                    maxLength:255,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:codLab
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Conservaci&oacute;n',
                    name:'conservacion',
                    allowBlank: true,
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:conservacion
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dispensaci&oacute;n',
                    name:'dispensacion',
                    allowBlank: true,
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:dispensacion
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Especial Control',
                    urlname: 'especialControl',
                    value:especialControl

                }),
                {
                    xtype:'textfield',
                    hidden:true,
                    name : 'codigo',
                    allowBlank : false,
                    value:codigo
                }

                ]
            },{
                columnWidth:0.33,
                layout: 'form',
                items: [
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Fecha Alta',
                    urlname: 'fechaAlta',
                    allowBlank: true,
                    value:fechaAlta

                }),
                new Ext.idmedicationweb.DateDataField({
                    fieldLabel: 'Fecha Baja',
                    urlname: 'fechaBaja',
                    allowBlank: true,
                    value:fechaBaja
                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Ficha',
                    name:'ficha',
                    allowBlank: true,
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    value:ficha
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Forma Farma',
                    name:'formaFarma',
                    allowBlank: true,
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:formaFarma
                },
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Gen&eacute;rico',
                    urlname: 'generico',
                    value:generico

                }),

                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Larga Duraci&oacute;n',
                    urlname: 'largaDuracion',
                    value:largaDuracion

                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Nombre Laboratorio',
                    name:'nombreLab',
                    allowBlank: true,
                    maxLength:1000,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:nombreLab
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Precio Ref',
                    name:'precioRef',
                    allowBlank: true,
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:precioRef
                },
                {
                    xtype:'textfield',
                    fieldLabel:'PVP',
                    name:'pvp',
                    allowBlank: true,
                    maxLength:50,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:pvp
                },
                {
                    xtype:'textfield',
                    fieldLabel:'R&eacute;gimen',
                    name:'regimen',
                    allowBlank: true,
                    maxLength:10,
                    maxLengthText:'El valor introducido es demasiado largo',
                    value:regimen
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis',
                    name:'dosis',
                    allowBlank: true,
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    value:dosis
                }

                ]
            },{
                columnWidth:0.33,
                layout: 'form',
                items: [
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Superficie',
                    name:'dosisSuperficie',
                    allowBlank: true,
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    value:dosisSuperficie
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Peso',
                    name:'dosisPeso',
                    allowBlank: true,
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    value:dosisPeso
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis',
                    name:'uDosis',
                    allowBlank: true,
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    value:uDosis
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Superficie',
                    name:'uDosisSuperficie',
                    allowBlank: true,
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    value:uDosisSuperficie
                },
                {
                    xtype:'textfield',
                    fieldLabel:'U Dosis Peso',
                    name:'uDosisPeso',
                    allowBlank: true,
                    regex:/^[0-9]{0,19}$/,
                    regextext:'El campo introducido no es valido',
                    value:uDosisPeso
                },
                cbVia,
                cbFrecuencia,
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Diluyente*',
                    urlname: 'diluyente',
                    value:diluyente

                }),
                new Ext.idmedicationweb.ComboYesNo({
                    fieldLabel: 'Aditivo*',
                    urlname: 'aditivo',
                    value:aditivo

                }),
                {
                    xtype:'textfield',
                    fieldLabel:'Dosis Diluyente',
                    name:'dosisDiluyente',
                    allowBlank: true,
                    regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                    regextext:'El campo introducido no es valido',
                    value:dosisDiluyente
                }
                ]
            }]
        }],
        buttons:[{
            text:'Modificar',
            tooltip:'Modificar especialidad',
            formBind:true,
            handler:function(){
                var insertpanel=undefined;
                submitMedicamento(insertpanel,updatepanel,grid,wind);
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
        items:[updatepanel]
    });

    wind.show();

    return updatepanel;
}

function submitMedicamento(insertpanel,updatepanel,grid,wind){

    var form,waitTitle,waitMsg,ok;
    if(updatepanel){
        form=updatepanel;
        waitTitle='Modificando';
        waitMsg='Modificando Especialidad...';
        ok='Modificaci&oacute;n correcta';
    }else{
        form=insertpanel;
        waitTitle='Insertando';
        waitMsg='Insertando Especialidad...';
        ok='Inserci&oacute;n correcta';
    }
    form.getForm().submit({
        method:'POST',
        waitTitle:waitTitle,
        waitMsg:waitMsg,
        failure:function(form,action){
            Ext.Msg.alert('Error', action.result.errors.reason);
        },
        success:function(){
            Ext.Msg.alert(ok);
            wind.close();
            grid.getStore().reload();
        }
    });
}

 
