/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.onReady(function(){


    var grid = new Ext.idmedicationweb.CatHasTemplateGrid({
        title:'Asignaci&oacute;n de Plantillas a Especialidades',
        urlMedTemplate:'gridcatalogohastemplates.htm',
        listeners:{
            submitInsert:function(){
                handleInsertMedTemplate(grid);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){

                    handleUpdateMedTemplate(grid.sm,grid);
                }else Ext.Msg.alert('','Seleccione una plantilla de especialidad');
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

}

);

function handleInsertMedTemplate(grid){

    /***
 *Textfields donde se escribiran los datos de la especialidad seleccionada
 */
    var medicineTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Especialidad',
        width:245,
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

    var templateTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Plantilla',
        width:245,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de la plantilla'
                });
            }
        }
    });

    var idTemplateTextField =new Ext.idmedicationweb.CodeTextField({
        urlname:'idtemplate'
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

    /**
     *Grid que muestra los templates
     *
     */
    var SimpleGridTem=new Ext.idmedicationweb.SimpleTemplateGrid({
        title:'',
        urlSimpleTemplate:'gridtempmedication.htm',
        listeners:{
            cellclick:function(){
                idTemplateTextField.setValue(this.sm.getSelected().get('idtemplatesMedication'));
                templateTextField.setValue(this.sm.getSelected().get('template'));
            }
        }

    });

    SimpleGridTem.getStore().load({
        params:{
            start: 0,
            limit: 10
        }
    });

    var insertpanel = new Ext.form.FormPanel( {
        title : 'Insertar Plantilla de Especialidad',
        url:'insertcatalogohastemplates.htm',
        width:855,
        height: 425,
        monitorValid: true,
        frame: true,
        items: [{
            layout:'column',
            items:[{
                columnWidth:0.57,
                layout:'form',
                items:[
                SimpleGridMed,
                codigoTextField

                ]
            },{
                columnWidth:0.43,
                layout:'form',
                items:[
                SimpleGridTem,
                {
                    xtype:'textfield',
                    textfield:'',
                    hidden:true

                },
                medicineTextField,
                templateTextField,
                idTemplateTextField
                ]
            }]
        }],
        buttons:[{
            text:'Insertar',
            tooltip:'Insertar plantilla de especialidad',
            formBind:true,
            handler:function(){
                var updatepanel=undefined;
                submitMedTemplate(insertpanel,updatepanel,grid,wind);
            }
        }]
    });

    var wind=new Ext.Window({
        resizable:true,
        width:855,
        height: 455,
        plain:true,
        border:false,
        layout:'table',
        modal:true,
        items:[insertpanel]
    });

    wind.show();

    return insertpanel;
}

function handleUpdateMedTemplate(sm,grid){

    if(sm.hasSelection()){

        var codigo=sm.getSelected().get('catalogoMedicamentos_codigo');
        var nombre=sm.getSelected().get('catalogoMedicamentos_nombre');
        var idtemplate=sm.getSelected().get('templatesId_idtemplatesMedication');
        var template=sm.getSelected().get('templatesId_template');
        var oldcodigo=sm.getSelected().get('catalogoMedicamentos_codigo');
    }

    /***
 *Textfields donde se escribiran los datos de la especialidad seleccionada
 */
    var medicineTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Especialidad',
        width:245,
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

    var templateTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Plantilla',
        width:245,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de la plantilla'
                });
            }
        }
    });

    var idTemplateTextField =new Ext.idmedicationweb.CodeTextField({
        urlname:'idtemplate'
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

    /**
     *Grid que muestra los templates
     *
     */
    var SimpleGridTem=new Ext.idmedicationweb.SimpleTemplateGrid({
        title:'',
        urlSimpleTemplate:'gridtempmedication.htm',
        listeners:{
            cellclick:function(){

                idTemplateTextField.setValue(this.sm.getSelected().get('idtemplatesMedication'));
                templateTextField.setValue(this.sm.getSelected().get('template'));
            }
        }

    });

    SimpleGridTem.getStore().load({
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
    templateTextField.setValue(template);
    idTemplateTextField.setValue(idtemplate);
      
    var updatepanel = new Ext.form.FormPanel({
        title : 'Modificar Plantilla de Especialidad',
        url:'updatecatalogohastemplates.htm',
        width:855,
        height: 425,
        monitorValid : true,
        frame : true,
        items : [{
            layout:'column',
            items:[{
                columnWidth:0.57,
                layout:'form',
                items:[
                SimpleGridMed,
                codigoTextField,
                {
                    xtype:'textfield',
                    name:'oldcodigo',
                    hidden:true,
                    value:oldcodigo
                }

                ]
            },{
                columnWidth:0.43,
                layout:'form',
                items:[
                SimpleGridTem,
                {
                    xtype:'textfield',
                    textfield:'',
                    hidden:true

                },
                medicineTextField,
                templateTextField,
                idTemplateTextField
                ]
            }]
        }],
        buttons:[{
            text:'Modificar',
            tooltip:'Modificar plantilla de especialidad',
            formBind:true,
            handler:function(){
                var insertpanel=undefined;
                submitMedTemplate(insertpanel,updatepanel,grid,wind);
            }
        }]
    });

    var wind=new Ext.Window({
        resizable:true,
        plain:true,
        border:false,
        width:855,
        height: 455,
        modal:true,
        layout:'table',
        items:[updatepanel]
    });

    wind.show();

    return updatepanel;
}

function submitMedTemplate(insertpanel,updatepanel,grid,wind){

    var form,waitTitle,waitMsg,ok;

    if(updatepanel){
        form=updatepanel;
        waitTitle='Modificando';
        waitMsg='Modificando Plantilla de Especialidad...';
        ok='Modificaci&oacute;n correcta';
    }else{
        form=insertpanel;
        waitTitle='Insertando';
        waitMsg='Insertando Plantilla de Especialidad...';
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
