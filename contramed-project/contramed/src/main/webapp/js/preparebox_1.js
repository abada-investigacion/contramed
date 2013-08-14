Ext.onReady(function(){

    var applet=new Ext.contramed.MCAApplet({
        renderTo:Ext.getBody()
    });

    /**
     * Pantalla inicial pregunta cama
     */
    function submitBedForm(bedForm,iTab){
        if(bedForm.getForm().isValid()){
            bedForm.getForm().submit({
                method:'POST',
                waitTitle:'Conectando',
                waitMsg:'Comprobando paciente encamado...',
                failure:function(form,action){
                    Ext.Msg.alert('Error','Cama inexistente, no es una etiqueta de cama, o no hay paciente en la misma.');
                    bedForm.getForm().reset();
                },
                success:function(){
                    //showActiveTreatment(bedForm.get(fieldName).getValue());
                    //bedForm.destroy();
                    showActiveTreatment('',applet,iTab);
                //window.setTimeout(showActiveTreatment,1000);
                }
            });
        }
    }

    function initMain(applet,iTab){
        var bedFormByTag = new Ext.form.FormPanel( {
            frame : true,
            height:130,
            anchor:'95%',
            title : 'Seleccionar Cama Por Etiqueta',
            url : 'bedtagvalidation.htm',
            defaultType : 'textfield',
            monitorValid : true,
            //id:'bedForm',
            items : [ {
                fieldLabel : 'Etiqueta Cama',
                name : 'tag',
                id:'tag',
                allowBlank : false,
                readOnly:true,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Id de etiqueta'
                        });
                    },
                    specialkey:function(field,e){
                        if (Ext.isIE)
                            e.stopEvent();
                    }
                }
            }],
            buttons:[new Ext.contramed.ButtonMCAApplet({
                text:'Buscar',
                functionName:'readRfid',
                tooltip:'Lee de la etiqueta y muestra la cama que pertenece',
                applet:applet,
                listeners:{
                    read:function(data){
                        bedFormByTag.get('tag').setValue(data);
                        submitBedForm(bedFormByTag,tabs.getIndexActiveTab());
                    }
                }
            })]
        });

        var comboBeds=new Ext.contramed.ComboBoxRecurso({
            url:'comboRecurso.htm',
            hiddenName:'id',
            name:'id',
            id:'bedid',
            width:400,            
            fieldLabel:'Identificador Cama',
            allowBlank: false,
            blankText: "El campo es obligatorio",
            emptyText: 'Seleccione una cama/recurso'
        });

        comboBeds.load();

        var bedFormById = new Ext.form.FormPanel( {
            frame : true,
            height:130,
            anchor:'95%',
            title : 'Seleccionar Cama Por Identificador',
            url : 'bedidvalidation.htm',
            defaultType : 'textfield',
            monitorValid : true,
            //id:'bedForm',
            items : [comboBeds],
            buttons:[{
                text:'Buscar',
                height:Ext.ACCESIBLE_HEIGHT,
                formBind:true,
                listeners:{
                    click:function(){
                        submitBedForm(bedFormById,tabs.getIndexActiveTab());
                    }
                }
            }]
        });

        var bedmap=new Ext.contramed.BedMap({
            urlBeds:'bedMap.htm',
            urlLocalizador:'comboRecursoLocalizador.htm',
            frame : true,
            title : 'Mapa de camas',
            autoWidth:true,
            autoHeight:true,
            listeners:{
                selectBed:function(bed){
                    //submitBedForm(bedFormById,tabs.getIndexActiveTab());
                    Ext.abada.Ajax.requestJson({
                        url: 'bedidvalidation.htm',
                        scope:this,
                        method:'POST',
                        //Parametros
                        params: {
                            id:bed
                        },
                        success: function() {
                            showActiveTreatment(bed,applet,tabs.getIndexActiveTab());
                        },
                        failure:function(){
                            Ext.Msg.alert('Error','Cama inexistente, no es una etiqueta de cama, o no hay paciente en la misma.');
                        }
                    });
                }
            }
        });

        bedmap.load();

        var tabs = new Ext.abada.TabPanel({
            activeTab: iTab,
            //height:130,
            anchor:'95%',
            items: [bedFormByTag,bedFormById,bedmap]
        });

        setCentralPanel(tabs);
    }
    /**
     * Muestra la pantalla con el tratamiento actual
     */
    function showActiveTreatment(bed,applet,iTab){

        var activeThreatmentGrid=new Ext.contramed.ThreatmentGrid({
            urlThreatment:'getthreatment.htm',
            urlHistoric:'prepareHistoric.htm'
        });

        var dateRange=new Ext.contramed.DateRangeToolbar({
            listeners:{
                search:function(startDate,endDate){
                    activeThreatmentGrid.getStore().setBaseParam('startDate', startDate);
                    activeThreatmentGrid.getStore().setBaseParam('endDate', endDate);
                    activeThreatmentGrid.getStore().load();
                }
            }
        });

        var treatmentForm = new Ext.Panel( {
            frame : true,
            autoWidth:true,
            autoHeight:true,
            title : 'Tratamiento Cama: '+bed,            
            items : [
            new Ext.Toolbar({
                items:[
                new Ext.contramed.PatientInfoPanel({
                    url:'getPatientInfo.htm',
                    listeners:{
                        next:function(){
                            initMain(applet,iTab);
                        }
                    }
                }),'->',
                dateRange
                ]
            }),
            new Ext.contramed.DoseToolbar({
                colspan:2,
                applet:applet,
                addText:'A&ntilde;adir',
                removeText:'Quitar',
                tooltipremove:'Eliminar dosis del cajet&iacute;n;',
                tooltipadd:'A&ntilde;adir dosis al cajet&iacute;n',
                listeners:{
                    submitAdd:function(value,observation){
                        addToBoxDose(value,observation);
                    },
                    submitRemove:function(value,observation){
                        removeToBoxDose(value,observation);
                    }
                }
            }),
            activeThreatmentGrid
            ]
        });

        function addToBoxDose(tagDose,observation){
            Ext.abada.Ajax.requestJson({
                url: 'prepare.htm',
                //Parametros
                params: {
                    tagDose:tagDose,
                    observation:observation
                },
                success: function(text) {
                    activeThreatmentGrid.getStore().load();
                },
                failure:function(text){
                    Ext.Msg.alert('Error',text.reason);
                }
            });
        }

        function removeToBoxDose(tagDose,observation){
            Ext.abada.Ajax.requestJson({
                url: 'remove.htm',
                //Parametros
                params: {
                    tagDose:tagDose,
                    observation:observation
                },
                success: function(text) {
                    activeThreatmentGrid.getStore().load();                    
                },
                failure:function(text){
                    Ext.Msg.alert('Error',text.reason);
                }
            });
        }

        setCentralPanel(treatmentForm);

        dateRange.onSubmit();
    }

    initMain(applet,0);
});