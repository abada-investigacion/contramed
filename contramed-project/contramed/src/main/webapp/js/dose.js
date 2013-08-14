/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.onReady(function(){

    /**
     *Grid donde se muestra la lista de Dosis
     *
     */
    var grid = new Ext.idmedicationweb.DoseGrid({
        id:'griddose',
        title:'Gesti&oacute;n de Dosis',
        urlDose:'griddose.htm',
        listeners:{
            submitInsert:function(){
                handleInsertDose(grid);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){

                    handleUpdateDose(grid.sm,grid);
                }else Ext.Msg.alert('','Seleccione una dosis');
            },
            submitPrint:function(){
                if(grid.sm.hasSelection()){

                    handlePrintDose(grid.sm);
                }else Ext.Msg.alert('','Seleccione una dosis');
            }
        }
    });

    grid.getStore().load({
        params:{
            start:0,
            limit: 13
        }
    });

    setCentralPanel(grid);

    if (Ext.urlDecode(location.search.substring(1))){
        var error=Ext.urlDecode(location.search.substring(1))['error'];
        if (error){
            Ext.Msg.alert('Error',error);
        }
    }

});


/**
 *Funcion que dispara el panel para insertar dosis
 *
 */
function handleInsertDose(grid){


    /**
     *Textfields donde se escribiran los datos de la especialidad seleccionada
     */
    var medicineTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Especialidad',
        width:300,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de la especialidad'
                });
            }
        }
    });

    var codigoTextField = new Ext.idmedicationweb.CodeTextField({
        urlname:'codigo'
    });

    var cbMeasureUnit= new Ext.idmedicationweb.CbIdMeasureUnit({
        store: new Ext.data.JsonStore({
            url:'combounad.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            scope:this,
            fields:['id','value']
        }),
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Unidad de medida de la cantidad a administrar'
                });
            }
        }
    });

    cbMeasureUnit.getStore().load();


    /***
     *Precarga de la fecha actual + 6 meses
     *
     */

    var fecha= new Date();
    fecha.setMonth(fecha.getMonth()+6);
    
        
    /**
     *Grid que muestra las especialidades
     *
     */
    var SimpleGridMed=new Ext.idmedicationweb.SimpleMedicineGrid({
        title:'',
        urlSimpleMed:'gridcatmedicamentos.htm',
        listeners:{
            cellclick:function(){

                codigoTextField.setValue(this.sm.getSelected().get('codigo'));
                medicineTextField.setValue(this.sm.getSelected().get('nombre'));
            }
            
        }

    });

    SimpleGridMed.getStore().load({
        params:{
            start: 0,
            limit: 10
        }
    });

    /**
     *Panel para insertar Dosis
     *
     */
    var insertpanel = new Ext.form.FormPanel( {
        title : 'Insertar Dosis',
        url:'insertdose.htm',
        width:955,
        height: 350,
        monitorValid: true,
        frame: true,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.5,
                layout:'form',
                items:[
                SimpleGridMed

                ]
            },{
                columnWidth:0.5,
                layout:'form',
                items:[{
                    xtype:'fieldset',
                    title:'Dosis',
                    collapsible:false,
                    width:440,
                    items:[
                    codigoTextField,
                    medicineTextField,
                    {
                        layout: 'column',
                        items:[{
                            columnWidth: 0.6,
                            layout:'form',
                            items: [
                            {
                                xtype:'textfield',
                                fieldLabel : 'Lote',
                                name : 'batch',
                                id:'batch',
                                allowBlank : false,
                                width:130,
                                listeners: {
                                    render: function(c) {
                                        Ext.QuickTips.register({
                                            target: c,
                                            text: 'Lote al que pertenece la dosis'
                                        });
                                    }
                                }
                            }
                            ]
                        },{
                            columnWidth: 0.4,
                            labelAlign : 'left',
                            layout:'form',
                            items: [{
                                xtype:'textfield',
                                fieldLabel : 'P. Lote',                                
                                name : 'pbatch',
                                width:50,
                                id:'pbatch',
                                allowBlank : true,
                                value:'L.:',
                                listeners: {
                                    render: function(c) {
                                        Ext.QuickTips.register({
                                            target: c,
                                            text: 'Prefijo del Lote al que pertenece la dosis'
                                        });
                                    }
                                }
                            }]
                        }]
                    },{
                        layout: 'column',
                        items:[{
                            columnWidth: 0.6,
                            layout:'form',
                            items: [
                            new Ext.idmedicationweb.DateDataField({
                                fieldLabel: 'Fecha de Caducidad',
                                urlname: 'expiration_date',
                                width:130,
                                listeners: {
                                    render: function(c) {
                                        Ext.QuickTips.register({
                                            target: c,
                                            text: 'Fecha de caducidad de la dosis'
                                        });
                                    }
                                },
                                value:fecha

                            })
                            ]
                        },{
                            columnWidth: 0.4,
                            labelAlign : 'left',
                            layout:'form',
                            items: [{
                                xtype:'textfield',
                                fieldLabel:'P. Cad',
                                name : 'pexpiration_date',
                                width:50,
                                id:'pexpiration_date',
                                allowBlank : true,
                                value:'Cad.:',
                                listeners: {
                                    render: function(c) {
                                        Ext.QuickTips.register({
                                            target: c,
                                            text: 'Prefijo de la fecha de caducidad de la dosis'
                                        });
                                    }
                                }
                            }]
                        }]
                    },
                    {
                        xtype:'textfield',
                        fieldLabel : 'Cantidad de Administraci&oacute;n',
                        name : 'give_amount',
                        regex:/^[0-9]{0,10}[.]{0,1}[0-9]{0,3}$/,
                        regextext:'El campo introducido no es valido',
                        listeners: {
                            render: function(c) {
                                Ext.QuickTips.register({
                                    target: c,
                                    text: 'Cantidad administrada'
                                });
                            }
                        },
                        allowBlank : false
                    },
                    cbMeasureUnit]
                },
                new Ext.idmedicationweb.Spinner({
                    fieldLabel:'Blisters',
                    maxValue: 100,
                    minValue: 1,
                    name : 'blisters',
                    value:1
                })]
            }]
        }],
        buttons:[{
            text:'Insertar',
            tooltip:'Insertar dosis',
            formBind:true,
            handler:function(){
                var updatepanel=undefined;
                submitDose(insertpanel,updatepanel,grid,wind);
            }
        },{
            text:'Insertar & Imprimir',
            tooltip:'Insertar e imprimir la dosis',
            formBind:true,
            handler:function(){
                Ext.abada.AjaxData.requestJson({
                    url: 'getTemplate.htm',
                    scope:this,
                    method:'GET',
                    //Parametros
                    params: {
                        codigo:codigoTextField.getValue()
                    },
                    success: function(plantilla) {

                        var urlaux='insertprintdose.htm';
                        
                        if (insertpanel.getForm().isValid()){
                            var params=insertpanel.getForm().getValues(true);
                            var banner=window.open(urlaux+'?'+params, null, "width=400,height=50,scrollbars=NO");
                            wind.close();
                            if (Ext.isChrome)
                                grid.getStore().reload();
                            else{
                                banner.onunload=function(event){
                                    var g=Ext.getCmp('griddose');
                                    if (g)
                                        g.getStore().reload();
                                }
                            }                            
                        }
                        
                    },
                    failure:function(){
                        insertpanel.getForm().submit({
                            method:'POST',
                            url:'insertprintdose.htm',
                            failure:function(form,action){
                                Ext.Msg.alert('Error',action.result.errors.reason);
                            },
                            success:function(){
                                Ext.Msg.alert('Impresi&oacute;n de dosis','Inserci&oacute;n correcta - Dosis no imprimida: Compruebe que la especialidad tiene una plantilla asignada');
                                wind.close();
                                grid.getStore().reload();

                            }
                        })
                    }
                });


            }
        }]
    });

    var wind=new Ext.Window({
        resizable:true,
        plain:true,
        border:false,
        modal:true,
        width:955,
        height: 375,
        autoDestroy:true,
        layout:'table',
        items:[insertpanel]
    });

    wind.show();


    return insertpanel;
}

/***
 *
 *Funcion que dispara el panel para modificar dosis
 */
function handleUpdateDose(sm,grid){

    /***
     *Precarga de la Dosis seleccionada en el grid
     *
     */
    if(sm.hasSelection()){
        var iddose=sm.getSelected().get('iddose');
        var codigo=sm.getSelected().get('catalogomedicamentosCODIGO');
        var nombre=sm.getSelected().get('catalogomedicamentosCODIGO_nombre');
        var batch=sm.getSelected().get('batch');
        var give_amount=sm.getSelected().get('giveAmount');
        var idmeasure_unit=sm.getSelected().get('measureUnitIdmeasureUnitId');
        
        var expiration_date_aux=new Date();
        expiration_date_aux=sm.getSelected().get('expirationDate');
        var posicion= expiration_date_aux.indexOf(' ');
        var expiration_date=new Date();
        expiration_date=expiration_date_aux.substring(0, posicion);
                   
    }

    /**
     *Textfields donde se escribiran los datos de la especialidad seleccionado
     */
    var medicineTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Especialidad',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de la especialidad'
                });
            }
        },
        width:300
    });

    var codigoTextField = new Ext.idmedicationweb.CodeTextField({
        urlname:'codigo'

    });

    /**
     *Grid que muestra las especialidades
     *
     */
    var SimpleGridMed=new Ext.idmedicationweb.SimpleMedicineGrid({
        title:'',
        urlSimpleMed:'gridcatmedicamentos.htm',
        listeners:{
            cellclick:function(){

                codigoTextField.setValue(this.sm.getSelected().get('codigo'));
                medicineTextField.setValue(this.sm.getSelected().get('nombre'));
            }
        }

    });

    SimpleGridMed.getStore().load({
        params:{
            start: 0,
            limit: 10
        }
    });

    /***
     *Seteo de valores en los Textfield
     *
     */
    codigoTextField.setValue(codigo);
    medicineTextField.setValue(nombre);


    var cbMeasureUnit= new Ext.idmedicationweb.CbIdMeasureUnit({
        store: new Ext.data.JsonStore({
            url:'combounad.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            scope:this,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbMeasureUnit.setValue(idmeasure_unit);
                }                

            }            

        }),
        listeners:{
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Unidad de medida de la cantidad a administrar'
                });
            }
        }


    });

    cbMeasureUnit.getStore().load({
        params:{
            start: 0,
            limit: 14
        }
    });

    /***
     *Panel para modificar Dosis
     *
     */

    var updatepanel = new Ext.form.FormPanel( {
        title : 'Modificar Dosis',
        url:'updatedose.htm',
        width:955,
        height: 350,
        monitorValid : true,
        frame : true,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.5,
                layout:'form',
                items:[
                SimpleGridMed

                ]
            },{
                columnWidth:0.5,
                layout:'form',
                items:[{
                    xtype:'fieldset',
                    title:'Dosis '+iddose,
                    collapsible:false,
                    width:440,
                    items:[{
                        xtype:'textfield',
                        name : 'iddose',
                        hidden:true,
                        allowBlank : false,
                        value:iddose
                    },
                    codigoTextField,
                    medicineTextField,
                    {
                        xtype:'textfield',
                        fieldLabel : 'Lote',
                        name : 'batch',
                        allowBlank : false,
                        listeners: {
                            render: function(c) {
                                Ext.QuickTips.register({
                                    target: c,
                                    text: 'Lote al que pertenece la dosis'
                                });
                            }
                        },
                        value:batch
                    },
                    new Ext.idmedicationweb.DateDataField({
                        fieldLabel: 'Fecha de Caducidad',
                        urlname: 'expiration_date',
                        listeners: {
                            render: function(c) {
                                Ext.QuickTips.register({
                                    target: c,
                                    text: 'Fecha de caducidad de la dosis'
                                });
                            }
                        },
                        value:expiration_date

                    }),{
                        xtype:'textfield',
                        fieldLabel : 'Cantidad de Administraci&oacute;n',
                        name : 'give_amount',
                        regex:/^[.0-9]{0,10}$/,
                        regextext:'El campo introducido no es valido',
                        allowBlank : false,
                        listeners: {
                            render: function(c) {
                                Ext.QuickTips.register({
                                    target: c,
                                    text: 'Cantidad administrada'
                                });
                            }
                        },
                        value:give_amount
                    },
                    cbMeasureUnit]

                }]
            }]
        }],
        buttons:[{
            text:'Modificar',
            tooltip:'Modificar dosis',
            formBind:true,
            handler:function(){
                var insertpanel=undefined;
                submitDose(insertpanel,updatepanel,grid,wind);
            }
              
        }]
    });

    var wind=new Ext.Window({
        resizable:true,
        plain:true,
        width:955,
        height: 375,
        border:false,
        modal:true,
        layout:'table',
        items:[updatepanel]
    });

    wind.show();

    return updatepanel;
}

function getTemplate(codigo,cbTemplate,cbStyles){
    Ext.abada.AjaxData.requestJson({
        url: 'getTemplate.htm',
        scope:this,
        method:'GET',
        //Parametros
        params: {
            codigo:codigo
        },
        success: function(plantilla) {                        
            cbTemplate.setValue(plantilla.data[0].pathTemplate);
            cbStyles.setValue(plantilla.data[0].pathStyle);
        },
        failure:function(){}
    });

}

function submitDose(insertpanel,updatepanel,grid,wind){

    var form,waitTitle,waitMsg,ok;

    if(updatepanel){
        form=updatepanel;
        waitTitle='Modificando';
        waitMsg='Modificando Dosis...';
        ok='Modificaci&oacute;n correcta';
    }else if(insertpanel){
        form=insertpanel;
        waitTitle='Insertando';
        waitMsg='Insertando Dosis...';
        ok='Inserci&oacute;n correcta';
    }

    form.getForm().submit({
        method:'POST',
        waitTitle:waitTitle,
        waitMsg:waitMsg,
        failure:function(form,action){
            Ext.Msg.alert('Error',action.result.errors.reason);
        },
        success:function(){
            Ext.Msg.alert(ok);
            wind.close();
            grid.getStore().reload();
                                          
        }
    });
}

function handlePrintDose(sm){


    if(sm.hasSelection()){
        var iddose=sm.getSelected().get('iddose');
        var codigo=sm.getSelected().get('catalogomedicamentosCODIGO');
        var especialidad=sm.getSelected().get('catalogomedicamentosCODIGO_nombre');
        var batch=sm.getSelected().get('batch');

        var expiration_date_aux=new Date();
        expiration_date_aux=sm.getSelected().get('expirationDate');
        var posicion= expiration_date_aux.indexOf(' ');
        var expiration_date=new Date();
        expiration_date=expiration_date_aux.substring(0, posicion);
        
    }

    var cbTemplate= new Ext.idmedicationweb.CbTemplates({
        store: new Ext.data.JsonStore({
            url:'combotemplate.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            scope:this,
            fields:['id','value']
        })
    });

    cbTemplate.getStore().load();

    var cbStyles= new Ext.idmedicationweb.CbStyles({
        store: new Ext.data.JsonStore({
            url:'combostyle.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value']
        })
    });

    cbStyles.getStore().load();

    var cbPageSize= new Ext.idmedicationweb.ComboPageSize({
        store: new Ext.data.JsonStore({
            url:'comboPageSize.htm',
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:['id','value'],
            listeners:{
                load:function(){
                    cbPageSize.setValue('595x842');
                }

            }
        }),
        urlname: 'pagesize'
    });

    cbPageSize.getStore().load();

    var checkbox=new Ext.form.Checkbox({
        checked: false,
        fieldLabel: 'Apaisado',
        name: 'apaisado',
        inputValue: true
    });

    /***
     *Panel para imprimir Dosis
     *
     */

    var printpanel = new Ext.form.FormPanel({
        title : 'Imprimir Dosis',
        url:'printdose.htm',
        monitorValid : true,
        frame : true,
        width:420,
        standardSubmit : true,
        items: [
        {
            xtype:'fieldset',
            title:'Formato de impresi&oacute;n',
            collapsible:false,
            width:400,
            items:[
            cbPageSize,
            new Ext.idmedicationweb.Spinner({
                fieldLabel:'Blisters',
                maxValue: 100,
                name : 'blisters',
                value:1
            }),
            cbTemplate,
            cbStyles,
            checkbox,
            new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Separaci&oacute;n horizontal (mm)',
                name: 'xGap',
                maxValue: 100,
                value:0
            }),
            new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Separaci&oacute;n vertical (mm)',
                name: 'yGap',
                maxValue: 100,
                value:0
            }),
            {
                xtype:'textfield',
                name : 'iddose',
                hidden:true,
                hideLabel:true,
                value:iddose,
                allowBlank : false
            },{
                xtype:'textfield',
                name : 'batch',
                hidden:true,
                hideLabel:true,
                value:batch,
                allowBlank : false
            },{
                xtype:'textfield',
                name:'expiration_date',
                hidden:true,
                hideLabel:true,
                value:expiration_date,
                allowBlank:false
            },{
                xtype:'textfield',
                name : 'codigo',
                hidden:true,
                hideLabel:true,
                value:codigo,
                allowBlank : false
            },{
                xtype:'textfield',
                name : 'especialidad',
                hidden:true,
                hideLabel:true,
                value:especialidad,
                allowBlank : false
            },{
                xtype:'textfield',
                fieldLabel : 'P. Lote',
                name : 'pbatch',
                width:50,
                id:'pbatch',
                allowBlank : true,
                value:'L.:',
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Prefijo del Lote al que pertenece la dosis'
                        });
                    }
                }
            },{
                xtype:'textfield',
                fieldLabel:'P. Cad',
                name : 'pexpiration_date',
                width:50,
                id:'pexpiration_date',
                allowBlank : true,
                value:'Cad.:',
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Prefijo de la fecha de caducidad de la dosis'
                        });
                    }
                }
            }
            ]
        },{
            xtype:'fieldset',
            title:'Margenes (mm)',
            collapsible:false,
            width:400,
            items:[new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Superior',
                name: 'msuperior',
                value:5
            }),
            new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Inferior',
                name: 'minferior',
                value:1
            }),
            new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Derecho',
                name: 'mderecho',
                value:1
            }),
            new Ext.idmedicationweb.Spinner({
                fieldLabel: 'Izquierdo',
                name: 'mizquierdo',
                value:5
            })]
        }],
        buttons:[{
            text:'Imprimir',
            tooltip:'Imprimir dosis',
            handler:function(){
                if (printpanel.getForm().isValid()){
                    var params=printpanel.getForm().getValues(true);
                    window.open(printpanel.getForm().url+'?'+params, null, "width=400,height=50,scrollbars=NO");
                    /* printpanel.getForm().submit({
                        method:'POST'
                    });*/
                    wind.close();
                   
                }
            }
        }],
        listeners:{
            render:function(){
                getTemplate(codigo,cbTemplate,cbStyles);
            }
        }
    });

    

    var wind=new Ext.Window({
        resizable:true,
        plain:true,
        width:432,
        border:false,
        modal:true,
        layout:'table',
        items:[printpanel]
    });

    wind.show();

    return printpanel;
}

