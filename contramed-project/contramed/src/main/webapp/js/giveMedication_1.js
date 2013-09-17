Ext.onReady(function(){

//    var applet=new Ext.contramed.MCAApplet({
//        renderTo:Ext.getBody()
//    });

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
                    Ext.Msg.alert('Error!','Paciente inexistente o no se encuentra la informaci&oacute;n relacionada con el paciente.');
                    bedForm.getForm().reset();
                },
                success:function(){
                    //bedForm.destroy();
                    //window.setTimeout(showActiveTreatment,1000);
//                    showActiveTreatment('',applet,iTab);
                    showActiveTreatment('',undefined,iTab);
                //showActiveTreatment(bedForm.get(fieldName).getValue());
                //bedForm.destroy();
                }
            });
        }
    }

    function initMain(applet,iTab){
        var patientFormByTag = new Ext.form.FormPanel( {
            frame : true,
            title : 'Seleccionar Medicaci&oacute;n por Etiqueta Paciente',
            url : 'patienttagvalidation.htm',
            defaultType : 'textfield',
            monitorValid : true,
            height:130,
            anchor:'95%',
            //id:'bedForm',
            items : [ {
                fieldLabel : 'Etiqueta del Paciente',
                name : 'tag',
                id:'tag2',
                readOnly:true,
                allowBlank : false,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Id de etiqueta del paciente'
                        });
                    },
                    specialkey:function(field,e){
                        if (Ext.isIE)
                            e.stopEvent();
                    }
                }
            }],
            buttons:[
//                new Ext.contramed.ButtonMCAApplet({
//                text:'Buscar',
//                functionName:'readRfid',
//                tooltip:'Obtener mediante etiqueta etiqueta del paciente',
//                applet:applet,
//                listeners:{
//                    read:function(data){
//                        patientFormByTag.get('tag2').setValue(data);
//                        submitBedForm(patientFormByTag,tabs.getIndexActiveTab());
//                    }
//                }
//            })
        ]
        });

        var bedFormByTag = new Ext.form.FormPanel( {
            frame : true,
            title : 'Seleccionar Medicaci&oacute;n por Etiqueta del Cajet&iacute;n/Cama',
            url : 'patienttagbedvalidation.htm',
            defaultType : 'textfield',
            monitorValid : true,
            height:130,
            anchor:'95%',
            //id:'bedForm',
            items : [ {
                fieldLabel : 'Etiqueta del Cajet&iacute;n/Cama',
                name : 'tag',
                id:'tag1',
                readOnly:true,
                allowBlank : false,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Id de etiqueta de cama/cajet&iacute;n del paciente'
                        });
                    },
                    specialkey:function(field,e){
                        if (Ext.isIE)
                            e.stopEvent();
                    }
                }
            }],
            buttons:[
//                new Ext.contramed.ButtonMCAApplet({
//                text:'Buscar',
//                functionName:'readRfid',
//                tooltip:'Obtener mediante etiqueta la cama/cajetin del paciente',
//                applet:applet,
//                listeners:{
//                    read:function(data){
//                        bedFormByTag.get('tag1').setValue(data);
//                        submitBedForm(bedFormByTag,tabs.getIndexActiveTab());
//                    }
//                }
//            })
        ]
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
            title : 'Seleccionar Medicaci&oacute;n por Cama',
            url : 'patientidbedvalidation.htm',
            defaultType : 'textfield',
            monitorValid : true,
            height:130,
            anchor:'95%',
            //id:'bedForm',
            items : [comboBeds],
            buttons:[{
                text:'Buscar',
                formBind:true,
                height:Ext.ACCESIBLE_HEIGHT,
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
            items: [patientFormByTag,bedFormByTag,bedFormById,bedmap]
        });

        setCentralPanel(tabs);
    }

    /**
     * Muestra la pantalla con el tratamiento actual
     */
    function showActiveTreatment(bed,applet,iTab){

        var activeThreatmentGrid=new Ext.contramed.ThreatmentGrid({
            urlThreatment:'getthreatmentNursing.htm',
            urlHistoric:'giveHistoric.htm'
        });

        var treatmentForm = new Ext.Panel( {
            frame : true,
            autoWidth:true,
            autoHeight:true,
            title : 'Tratamiento Cama: '+bed,
            items : [new Ext.Toolbar({
                items:[new Ext.contramed.PatientInfoPanel({
                    url:'getPatientInfo.htm',
                    listeners:{
                        next:function(){
                            initMain(applet,iTab);
                        }
                    }
                }),'->',
                new Ext.contramed.GiveNursingToolbar({
                    url:'timingranges.htm',
                    listeners:{
                        search:function(timingrange,date){
                            //activeThreatmentGrid.getStore().setBaseParam('startTime', startTime);
                            //activeThreatmentGrid.getStore().setBaseParam('endTime', endTime);
                            activeThreatmentGrid.getStore().setBaseParam('timingrange', timingrange);
                            activeThreatmentGrid.getStore().setBaseParam('date', date);
                            activeThreatmentGrid.getStore().load();
                        }
                    }
                })]
            }),
            new Ext.contramed.DoseToolbar({
//                applet:applet,
                addText:'Administrar',
                tooltipadd:'Administra la dosis, cogiendola por el lector de codigo de barras',
                listeners:{
                    submitAdd:function(value,observation,type){
                        giveDose(value,observation,type);
                    }
                }
            }),
            activeThreatmentGrid
            ]
        });

        function giveDose(tagDose,observation,type){
            Ext.abada.Ajax.requestJson({
                url: 'give.htm',
                //Parametros
                params: {
                    tagDose:tagDose,
                    type:type,
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
    }

    initMain(undefined,0);
});