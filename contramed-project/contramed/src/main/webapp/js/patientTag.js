Ext.onReady(function(){

    var applet=new Ext.contramed.MCAApplet({
        renderTo:Ext.getBody()
    });

    /**
 *Pantalla inicial en la que pedimos la identificacion al paciente para buscarlo,
 *sea por DNI,Pasaporte, Historia Clinica,etc..
 */
    function submitDetailsForm(){
        if(detailsForm.getForm().isValid()){
            detailsForm.getForm().submit({
                method:'POST',
                waitTitle:'Conectando',
                waitMsg:'Comprobando la identificaci&oacute;n...',
                failure:function(form,action){
                    if (action){
                        Ext.Msg.alert('Error',action.result.errors.reason);

                    }else{
                        Ext.Msg.alert('Error','Paciente inexistente o identificaci&oacute;n err&oacute;nea.');
                    }
                    detailsForm.getForm().reset();
                },
                success:function(){
                    showPatientInfo(applet);
                }
            });
          
        }
    }
 
    /**
 *Tipo de identificacion de los pacientes
 */
    var cbIdent=new Ext.contramedadmin.ComboCode({
        store: new Ext.data.JsonStore({
            url:'combocode.htm',
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
                    text: 'Tipo de identificaci&oacute;n'
                });
            }
        }
    });
    cbIdent.getStore().load();
    /**
  *Grid con la informacion de los pacientes que tenemos
  */
    var grid=new Ext.contramedadmin.PatientGridInicial({
        url:'gridinicial.htm',
        listeners:{
            submitSearch:function(){
                Ext.abada.Ajax.requestJson({
                    url: 'codevalidationid.htm',
                    //Parametros
                    params: {
                        id:grid.sm.getSelected().get('id')
                    },
                    success: function() {
                        showPatientInfo(applet);
                    },
                    failure:function(text){
                        if (text){
                            Ext.Msg.alert('Error',text.reason);

                        }else{
                            Ext.Msg.alert('Error','Error al buscar el paciente');
                        }
                    }
                });
               
            }
        }
           
    });

    grid.getStore().load({
        params:{
            start:0,
            limit:10
        }
    });
          
    var detailsForm = new Ext.form.FormPanel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'B&uacute;squeda por identificaci&oacute;n',
        url : 'codevalidation.htm',
        defaultType : 'textfield',
        monitorValid : true,
        id:'detailsForm',
        items : [
        new Ext.Toolbar({
            items:[
            cbIdent,
            new Ext.form.TextField({
                fieldlabel:'Identifiaci&oacute;n',
                name : 'valor',
                id:'valor',
                allowBlank : false,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'N&uacute;mero de identificaci&oacute;n'
                        });
                    }
                }
            }),
            new Ext.Button({
                text:'Buscar',
                icon:'images/custom/find.gif',
                formBind:true,
                tooltip:'Buscar',
                handler:submitDetailsForm
            })
            ]
        })
        
        ]
    });

 
    var tab = new Ext.TabPanel({
        renderTo: Ext.getBody(),
        activeTab: 0,
        frame:true,
        defaults:{
            autoHeight: true,
            layout:'fit'
        },
        items:[
        {
            xtype: 'panel',
            title: 'B&uacute;squeda por identificador',
            layout: 'form',
            items:[detailsForm]

        },

        {

            xtype: 'panel',
            title: 'B&uacute;squeda por nombre y apellido',
            layout: 'form',
            items:[grid]
        }
        ]
    });


    var panelInicial = new Ext.form.FormPanel({
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Gesti&oacute;n de Tags de pacientes',
        items : [tab]
    });

    setCentralPanel(panelInicial);





  
});

/**
 * Mostramos la información del paciente al que vamos a asignar,modificar o borrar el tag
 */
function showPatientInfo(applet){
    

    var patientGrid=new Ext.contramedadmin.PatientGrid({
        url:'gridpatientinfo.htm'
       
    });

    patientGrid.getStore().load({
        params:{
            start: 0,
            limit: 13
        }
    });
    
    var toolbar=new Ext.contramedadmin.PatientToolbar({
        id:'pat',
        applet:applet,
        removeText:'Borrar',
        addText:'Leer/A&ntilde;adir/Modificar',
        listeners:{
            submitAdd:function(tag){
                addTagPatient(tag);
            },
            submitRemove:function(){
                removeTagPatient();
            },
            submitBack:function(){
                window.location='patientTag.htm';
            }

        }
    });
    var patientForm = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title:'Paciente',
        items : [toolbar,
                 
        patientGrid
        ]
    });
    
    function addTagPatient(tag){
        Ext.abada.Ajax.requestJson({
            url: 'insertpatienttag.htm',
            //Parametros
            params: {
                tag:tag
            },
            success: function() {
                Ext.Msg.alert('','Tag insertado correctamente');
                patientGrid.getStore().reload();
            },
            failure:function(text){
                if (text){
                    Ext.Msg.alert('Error',text.reason);

                }else{
                    Ext.Msg.alert('Error','Error al realizar la insercci&oacute;n');
                }
            }
        });
    }
   
    function removeTagPatient(){
        Ext.MessageBox.confirm('Borrar','Desea borrar este Tag',
            function (btn) {
                if (btn == "yes") {
                    Ext.abada.Ajax.requestJson({
                        url: 'removepatienttag.htm',
                        success: function() {
                            Ext.Msg.alert('','Eliminado correctamente');
                            patientGrid.getStore().reload();           
                        },
                        failure:function(text){
                            if (text){
                                Ext.Msg.alert('Error',text.reason);

                            }else{
                                Ext.Msg.alert('Error','Error, no puede ser eliminado');
                            }
                        }
                    });
                }
            });
         
    }

    setCentralPanel(patientForm);
}