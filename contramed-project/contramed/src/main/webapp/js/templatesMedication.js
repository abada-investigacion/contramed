/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.onReady(function(){

    /***
     *Grid donde se muestra la lista de Templates
     *
     */
    var grid = new Ext.idmedicationweb.TemplateGrid({
        title:'Gesti&oacute;n de Plantillas',
        urlTemplate:'gridtempmedication.htm',
        listeners:{
            submitInsert:function(){
                handleInsertTemplate(grid);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){

                    handleUpdateTemplate(grid.sm,grid);
                }else Ext.Msg.alert('','Seleccione una plantilla');
            }
        }

    });

    grid.getStore().load({
        params:{
            start:0,
            limit: 15
        }
    });


    setCentralPanel(grid);


});

function handleInsertTemplate(grid){

  
    var insertpanel = new Ext.form.FormPanel( {
        title : 'Insertar plantilla',
        url:'insertTemplate.htm',
        width:600,
        defaultType: 'textfield',
        monitorValid: true,
        fileUpload: true,
        frame: true,
        items: [
        {
            fieldLabel : 'Plantilla',
            name : 'template',
            width:250,
            allowBlank : false,
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Nombre de la plantilla'
                    });
                }
            }
        },
        {
            xtype: 'fileuploadfield',
            id: 'form-file-template',
            width:250,
            emptyText: 'Selecione una plantilla',
            allowBlank: false,
            blankText: "El campo es obligatorio",
            fieldLabel: 'Ruta Plantillas (jrxml)',
            name: 'file',
            regex:/^.*\jrxml$/,
            regextext:'El archivo debe tener formato .jrxml',
            listeners:{

                beforerender : function(v) {
                    v.buttonCfg= {
                        text: '',
                        iconCls: 'upload-icon',
                        width:20
                    };
                },
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Carga el fichero de plantilla'
                    });
                }
            }

        },
        {
            xtype: 'fileuploadfield',
            id: 'form-file-style',
            width:250,
            emptyText: 'Selecione un estilo',
            allowBlank: false,
            blankText: "El campo es obligatorio",
            fieldLabel: 'Ruta Estilos (jrtx)',
            name: 'style',
            regex:/^.*\jrtx$/,
            regextext:'El archivo debe tener formato .jrtx',
            listeners:{
                beforerender : function(v) {
                    v.buttonCfg= {
                        text: '',
                        iconCls: 'upload-icon',
                        width:20
                    };
                },
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Cargar el fichero de estilo'
                    });
                }
            }

        }
       
        ],
        buttons:[{
            text:'Insertar',
            formBind:true,
            tooltip:'Insertar plantilla',
            handler:function(){
                var updatepanel=undefined;
                submitTemplate(insertpanel,updatepanel,grid,wind);

            }
        }]
    });

    var wind=new Ext.Window({
        width:600,
        resizable:true,
        plain:true,
        border:false,
        modal:true,
        layout:'table',
        items:[insertpanel]
    });

    wind.show();

    return insertpanel;
}

function handleUpdateTemplate(sm,grid){

    if(sm.hasSelection()){
        var idtemplatesmedication=sm.getSelected().get('idtemplatesMedication');
        var template=sm.getSelected().get('template');
        var pathTemplate=sm.getSelected().get('pathTemplate');
        var pathStyle=sm.getSelected().get('pathStyle');
    }
  

    var updatepanel = new Ext.form.FormPanel( {
        title : 'Modificar Plantilla '+idtemplatesmedication,
        url:'updateTemplate.htm',
        width:600,
        defaultType : 'textfield',
        monitorValid : true,
        fileUpload: true,
        frame : true,
        items : [{
            hidden:true,
            name : 'idtemplatesmedication',
            value:idtemplatesmedication
        },
        {
            fieldLabel : 'Plantilla',
            name : 'template',
            allowBlank : false,
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Nombre de la plantilla'
                    });
                }
            },
            width:250,
            value:template
        },
        {
            name : 'olduuid',
            hidden:true,
            value:pathTemplate
        },
        {
            name : 'olduuidStyle',
            hidden:true,
            value:pathStyle
        },
        {
            xtype: 'fileuploadfield',
            id: 'form-file-template',
            width:250,
            emptyText: 'Selecione una plantilla',
            allowBlank: true,
            blankText: "El campo es obligatorio",
            fieldLabel: 'Ruta Plantillas (jrxml)',
            name: 'file',
            regex:/^.*\jrxml$/,
            regextext:'El archivo debe tener formato .jrxml',
            listeners:{
                beforerender : function(v) {
                    v.buttonCfg= {
                        text: '',
                        iconCls: 'upload-icon',
                        width:20
                    };
                } ,
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Cargar el fichero de plantilla'
                    });
                }

            }

        },
        {
            xtype: 'fileuploadfield',
            id: 'form-file-style',
            width:250,
            emptyText: 'Selecione un estilo',
            allowBlank: true,
            blankText: "El campo es obligatorio",
            fieldLabel: 'Ruta Estilos (jrtx)',
            name: 'style',
            regex:/^.*\jrtx$/,
            regextext:'El archivo debe tener formato .jrtx',
            listeners:{
                beforerender : function(v) {
                    v.buttonCfg= {
                        text: '',
                        iconCls: 'upload-icon',
                        width:20
                    };
                },
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Cargar el fichero de estilo'
                    });
                }
            }

        }
        ],
        buttons:[{
            text:'Modificar',
            tooltip:'Modificar plantilla',
            formBind:true,
            handler:function(){
                var insertpanel=undefined;
                submitTemplate(insertpanel,updatepanel,grid,wind);
            }
        }]
    });

    var wind=new Ext.Window({
        width:600,
        resizable:true,
        plain:true,
        border:false,
        modal:true,
        layout:'table',
        items:[updatepanel]
    });

    wind.show();

    return updatepanel;
}

function submitTemplate(insertpanel,updatepanel,grid,wind){

    var form, waitTitle, waitMsg, ok;
    if(updatepanel){
        form=updatepanel;
        waitTitle='Modificando';
        waitMsg='Modificando Plantilla...';
        ok='Modificaci&oacute;n correcta';
    }else{
        form=insertpanel;
        waitTitle='Insertando';
        waitMsg='Insertando Plantilla...';
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