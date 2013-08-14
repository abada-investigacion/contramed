Ext.onReady(function(){

    var applet=new Ext.contramed.MCAApplet({
        timeout:1000,
        renderTo:Ext.getBody()
    });

    //var giveToolbar=new Ext.contramed.GiveNursingToolbar({
    var giveToolbar=new Ext.contramed.GiveNursingFilterToolbar({
        url:'timingranges.htm',
        hideSubmitButton:true,
        listeners:{
            search:function(timingrange,date){
                //activeThreatmentGrid.getStore().setBaseParam('startTime', startTime);
                //activeThreatmentGrid.getStore().setBaseParam('endTime', endTime);
                grid.getStore().setBaseParam('timingrange', timingrange);
                grid.getStore().setBaseParam('date', date);
                grid.getStore().load();
            }
        }
    });

    var doseToolbar=new Ext.contramed.DoseToolbar({
        applet:applet,
        addText:'Administrar',
        tooltipadd:'Administra la dosis, cogiendola por el lector de codigo de barras',
        removeText:'No Administrar',
        tooltipremove:'',
        listeners:{
            submitAdd:function(value,observation,type){
                giveDose(value,observation,type);
            },
            submitRemove:function(value,observation){
                removeDose(value,observation);
            }
        }
    });

    var bedmap=new Ext.contramed.BedMap({
        urlBeds:'bedMap.htm',
        urlLocalizador:'comboRecursoLocalizador.htm',
        frame : false,
        title : 'Mapa de camas, no interactivo.',
        autoWidth:true,
        autoHeight:true,
        listeners:{
            selectBed:function(bed){
                Ext.abada.Ajax.requestJson({
                    url: 'patientidbedvalidation.htm',
                    scope:this,
                    method:'POST',
                    //Parametros
                    params: {
                        id:bed
                    },
                    success: function() {
                        //TODO modo resumen
                        showThreatment(true);
                    },
                    failure:function(){
                        Ext.Msg.alert('Error','Cama inexistente, no es una etiqueta de cama, o no hay paciente en la misma.');
                    }
                });
            }
        }
    });
    bedmap.load();

    var patientInfo=new Ext.contramed.PatientInfoPanel({
        url:'getPatientInfo.htm'
    });

    var grid=new Ext.contramed.ThreatmentGrid({
        urlThreatment:'getthreatmentNursing.htm',
        urlHistoric:'giveHistoric.htm',
        listeners:{
            iconColumnClick:function(grid,record,rowIndex,columnIndex){
                new Ext.contramed.ObservationWindow({
                    url:'saveObservation.htm',
                    privText:'ordertimingid: '+record.get('idOrderTiming')+' givetime: '+record.get('giveTime')+' giveMedication'
                }).show();
            }
        }
    });   

    function showThreatmentUrl(data,resume,rfidButton){
        Ext.abada.Ajax.requestJson({
            url: 'patienttagvalidation.htm',
            scope:this,
            method:'POST',
            //Parametros
            params: {
                tag:data
            },
            success: function() {
                showThreatment(resume);
            },
            failure:function(){
                Ext.Msg.alert('Error','No se encuentra el paciente.');
                if (rfidButton)
                    rfidButton.forceRead();
            }
        });
    }

    function giveDose(tagDose,observation,type){
        if (!type){
            type='REGULAR';
        }
        Ext.abada.Ajax.requestJson({
            url: 'give.htm',
            waitTitle:'Conectando',
            waitMsg:'Administrando medicaci&oacute;n...',
            //Parametros
            params: {
                tagDose:tagDose,
                type:type,
                observation:observation
            },
            success: function(text) {
                grid.getStore().load();
            },
            failure:function(error){
                if (error.typeError=='NO_THREATMENT' || error.typeError=='NO_CORRECT_THREATMENT'){
                    Ext.Msg.confirm('Medicaci&oacute;n no pautada','&iquest;Desea administrar la medicaci&oacute;n '+error.reason+' al paciente?',function(btn){
                        if(btn === 'yes'){
                            giveDose(tagDose,observation,'OUT_ORDER');
                        }else{
                            //TODO add call to create medication not given exception
                            Ext.abada.Ajax.requestJson({
                                url: 'notgiven.htm',
                                //Parametros
                                params: {
                                    tagDose:tagDose
                                },
                                success: function(text) {
                                },
                                failure:function(error){
                                }
                            });
                    }
                    });
            }else{
                Ext.Msg.alert('Error',error.reason);
            }
        }
        });
}

    function removeDose(tagDose,observation){
        Ext.abada.Ajax.requestJson({
            url: 'removeDose.htm',
            waitTitle:'Conectando',
            waitMsg:'Quitando medicaci&oacute;n...',
            //Parametros
            params: {
                tagDose:tagDose,
                observation:observation
            },
            success: function(text) {
                grid.getStore().load();
            },
            failure:function(error){
                Ext.Msg.alert('Error',error.reason);
            }
        });
    }

function showInit(){
    giveToolbar.setHideSubmitButton(true);

    var rfidButton=new Ext.contramed.ButtonMCAApplet({
        //text:'Leer Tarjeta',
        iconCls:'rfid',
        functionName:'readRfid',
        tooltip:'Lee de la etiqueta del caje&iacute;n o del paciente y muestra su tratamiento',
        applet:applet,
        nonStopRead:false,
        listeners:{
            read:function(data){
                showThreatmentUrl(data, false,rfidButton);
            }
        }
    });
        
    var initPanel=new Ext.Panel({
        items:[new Ext.Toolbar({
            //layout:'hbox',
            items:[giveToolbar,rfidButton
            ]
        }),bedmap
        ]
    });
    rfidButton.forceRead();
    setCentralPanel(initPanel,false);
}

function showThreatment(resume){
    giveToolbar.setHideSubmitButton(false);


    var toolbar=new Ext.Toolbar({
        items:[giveToolbar,'->']
    });
    if (!resume){
        toolbar.add(doseToolbar);
    }
    grid.setGroupCollapsed(resume);

    var toolbarInt=new Ext.Toolbar({
        //layout:'hbox',
        items:[new Ext.contramed.ButtonThreatmentToolbar({
            applet:applet,
            resumeMode:resume,
            listeners:{
                submitNextPatient:function(data,button){
                    showThreatmentUrl(data, false,button);
                },
                submitBack:function(){
                    showInit();
                },
                submiObservation:function(){
                    new Ext.contramed.ObservationWindow({
                        url:'saveObservation.htm'                        
                    }).show();
                },
                submitAcceptAll:function(){
                    Ext.Msg.confirm('Confirmaci&oacute;n','&iquest;Desea marcar como suministrada toda la medicaci&oacute;n de este paciente?',function(btn){
                        if(btn === 'yes'){
                            Ext.abada.Ajax.requestJson({
                                url: 'giveAll.htm',
                                success: function(text) {
                                    grid.getStore().load();
                                },
                                failure:function(error){

                                    Ext.Msg.alert('Error',error.reason);
                                }
                            });
                        }
                    });
                }
            }
        }),'->',patientInfo
        ]
    });

    var threatmentPanel=new Ext.Panel({
        items:[toolbarInt,toolbar,grid]
    });

    if (resume){
        toolbarInt.add('->');
        toolbarInt.add(new Ext.Button({
            scale:'large',
            iconCls:'play',
            tooltip:'Pasar a modo administraci&oacute;n.',
            handler:function(){
                showThreatment(false);
            }
        }));
    }


    patientInfo.load();
    setCentralPanel(threatmentPanel,false);
    giveToolbar.onSubmit();
    if (!resume){
        doseToolbar.submitAdd();
    }
}

showInit();
    });