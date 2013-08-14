/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.onReady(function(){

    /**
     *Grid donde se muestra la lista de Principios Activos
     *
     */
    var grid = new Ext.idmedicationweb.PrincipioHasEspecialidadGrid({
        title:'Gesti&oacute;n de P.Activos de Especialidades',
        urlPrincipio:'gridprincipiohasespecialidad.htm',
        listeners:{
            submitInsert:function(){
                handleFormPrincipio('Inserta',grid,' P.Activo de Especialidad','insertPrincipioHasEspecialidad.htm',grid.sm);
            },
            submitUpdate:function(){
                if(grid.sm.hasSelection()){
                    if(grid.sm.getCount()==1){
                        handleFormPrincipio('Modifica',grid,' P.Activo de Especialidad','updatePrincipioHasEspecialidad.htm',grid.sm);
                    }else{
                        Ext.Msg.alert('','Seleccione una unica Especialidad de P.Activo');
                    }
                }else Ext.Msg.alert('','Seleccione una Especialidad de P.Activo');
            },
            submitDelete:function(){
                if(grid.sm.hasSelection()){
                    handledelete(grid,'P.Activo de Especialidad','id','','','deletePrincipioHasEspecialidad.htm');
                }else Ext.Msg.alert('','Seleccione un Principio Activo');
            }
        }
    });

    grid.getStore().load({
        params:{
            start: 0,
            limit: 9
        }
    });

    setCentralPanel(grid);

});

function handleFormPrincipio(opt,grid,title,url,selection){

    var id,codigoespec,nombreespec,codigoprincipio,nombreprincipio,
    composicion,id_unidad,tooltip='A&ntilde;adir Especialidad de P.Activo';

    if(opt!='Inserta'&&selection.hasSelection()){
        id=selection.getSelected().get('id');
        codigoespec=selection.getSelected().get('codigoEspec_codigo');
        nombreespec=selection.getSelected().get('codigoEspec_nombre');
        codigoprincipio=selection.getSelected().get('codigoPrincipio_codigo');
        nombreprincipio=selection.getSelected().get('codigoPrincipio_nombre');
        composicion=selection.getSelected().get('composicion');
        id_unidad=selection.getSelected().get('idUnidad');
        tooltip='Modificar Principio Activo';
    }

    var SimpleGridMed=new Ext.idmedicationweb.SimpleMedicineGrid({
        title:'',
        urlSimpleMed:'gridcatmedicamentos.htm',
        listeners:{
            cellclick:function(){

                codigoEspecialidadTextField.setValue(this.sm.getSelected().get('codigo'));
                especialidadTextField.setValue(this.sm.getSelected().get('nombre'));
            }
        }

    });

    SimpleGridMed.getStore().load({
        params:{
            start: 0,
            limit: 9
        }
    });

    var gridPrincipio = new Ext.idmedicationweb.SimplePrincipioActivoGrid({
        urlPrincipio:'gridprincipioactivo.htm',
        listeners:{
            cellclick:function(){

                codigoPrincipioTextField.setValue(this.sm.getSelected().get('codigo'));
                principioTextField.setValue(this.sm.getSelected().get('nombre'));
            }
        }
    });

    gridPrincipio.getStore().load({
        params:{
            start: 0,
            limit: 9
        }
    });

    /**
     *Textfields donde se escribiran los datos de la especialidad seleccionada
     */
    var especialidadTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'Especialidad',
        width:245,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre de la Especialidad'
                });
            }
        }
    });

    var codigoEspecialidadTextField = new Ext.idmedicationweb.CodeTextField({
        urlname:'codigoespec'
    });

    var principioTextField = new Ext.idmedicationweb.ValueTextField({
        fieldLabel : 'P.Activo',
        width:245,
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Nombre del P.Activo'
                });
            }
        }
    });

    var codigoPrincipioTextField =new Ext.idmedicationweb.CodeTextField({
        urlname:'codigoprincipio'
    });

    if(opt!='Inserta'&&selection.hasSelection()){
        codigoEspecialidadTextField.setValue(codigoespec);
        especialidadTextField.setValue(nombreespec);
        codigoPrincipioTextField.setValue(codigoprincipio);
        principioTextField.setValue(nombreprincipio);
    }

    var formpanel = new Ext.form.FormPanel({
        title : opt+'r ' +title,
        url:url,
        width:855,
        height: 430,
        monitorValid : true,
        frame : true,
        items : [{
            layout:'column',
            items:[{
                columnWidth:0.57,
                layout:'form',
                items:[
                SimpleGridMed,
                codigoEspecialidadTextField,
                codigoPrincipioTextField,
                especialidadTextField,
                principioTextField,
                {
                    xtype:'textfield',
                    name:'oldcodigoespec',
                    hidden:true,
                    value:codigoespec
                }
                ]
            },{
                columnWidth:0.43,
                layout:'form',
                items:[
                gridPrincipio,
                {
                    xtype:'textfield',
                    name:'oldid',
                    hidden:true,
                    value:id
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Composicion',
                    name:'composicion',
                    maxLength:11,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank:true,
                    value:composicion
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Id Unidad',
                    name:'id_unidad',
                    maxLength:9,
                    maxLengthText:'El valor introducido es demasiado largo',
                    allowBlank:true,
                    value:id_unidad
                },
                {
                    xtype:'textfield',
                    fieldLabel:'Id',
                    name:'id',
                    value:id,
                    allowBlank:false,
                    regex:/^[0-9]{0,10}$/,
                    regextext:'El campo introducido no es v&aacute;lido',
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Identificador'
                            });
                        }
                    }
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
        width:855,
        plain:true,
        border:false,
        layout:'table',
        modal:true,
        items:[formpanel]
    });

    wind.show();

    return formpanel;

}