Ext.onReady(function() {

//    var applet=new Ext.contramed.MCAApplet({
//        timeout:1000,
//        renderTo:Ext.getBody()
//    });

    var dateToolbar = new Ext.contramed.DateRangeFilterToolbar({
        listeners: {
            search: function(startDate, endDate) {
                grid.getStore().setBaseParam('startDate', startDate);
                grid.getStore().setBaseParam('endDate', endDate);
                grid.getStore().load();
            }
        }
    });

    var doseToolbar = new Ext.contramed.DoseToolbar({
//        applet:applet,
        addText: 'A&ntilde;adir',
        removeText: 'Quitar',
        tooltipremove: 'Eliminar dosis del cajet&iacute;n;',
        tooltipadd: 'A&ntilde;adir dosis al cajet&iacute;n',
        listeners: {
            submitAdd: function(value, observation) {
                addToBoxDose(value, observation);
            },
            submitRemove: function(value, observation) {
                removeToBoxDose(value, observation);
            }
        }
    });

    var bedmap = new Ext.contramed.BedMap({
        urlBeds: 'bedMap.htm',
        urlLocalizador: 'comboRecursoLocalizador.htm',
        frame: false,
        title: 'Mapa de camas, no interactivo.',
        autoWidth: true,
        autoHeight: true,
        initPath: '/',
        listeners: {
            selectBed: function(bed) {
                Ext.abada.Ajax.requestJson({
                    url: 'patientidbedvalidation.htm',
                    scope: this,
                    method: 'POST',
                    //Parametros
                    params: {
                        id: bed
                    },
                    success: function() {
                        //TODO modo resumen
                        showThreatment(true);
                    },
                    failure: function() {
                        Ext.Msg.alert('Error', 'Cama inexistente, no es una etiqueta de cama, o no hay paciente en la misma.');
                    }
                });
            }
        }
    });
    bedmap.load();

    var patientInfo = new Ext.contramed.PatientInfoPanel({
        url: 'getPatientInfo.htm'
    });

    var grid = new Ext.contramed.ThreatmentGrid({
        urlThreatment: 'getthreatment.htm',
        urlHistoric: 'prepareHistoric.htm',
        listeners: {
            iconColumnClick: function(grid, record, rowIndex, columnIndex) {
                new Ext.contramed.ObservationWindow({
                    url: 'saveObservation.htm',
                    privText: 'ordertimingid: ' + record.get('idOrderTiming') + ' givetime: ' + record.get('giveTime') + ' prepareMedication'
                }).show();
            }
        }
    });

    function showThreatmentUrl(data, resume, rfidButton) {
        Ext.abada.Ajax.requestJson({
            url: 'patienttagvalidation.htm',
            scope: this,
            method: 'POST',
            //Parametros
            params: {
                tag: data
            },
            success: function() {
                showThreatment(resume);
            },
            failure: function() {
                Ext.Msg.alert('Error', 'No se encuentra el paciente.');
                if (rfidButton)
                    rfidButton.forceRead();
            }
        });
    }

    function addToBoxDose(tagDose, observation) {
        Ext.abada.Ajax.requestJson({
            url: 'prepare.htm',
            //Parametros
            waitTitle: 'Conectando',
            waitMsg: 'A&ntilde;adiendo medicaci&oacute;n...',
            params: {
                tagDose: tagDose,
                observation: observation
            },
            success: function(text) {
                grid.getStore().load();
            },
            failure: function(error) {
                Ext.Msg.alert('Error', error.reason);
            }
        });
    }

    function removeToBoxDose(tagDose, observation) {
        Ext.abada.Ajax.requestJson({
            url: 'remove.htm',
            waitTitle: 'Conectando',
            waitMsg: 'Quitando medicaci&oacute;n...',
            //Parametros
            params: {
                tagDose: tagDose,
                observation: observation
            },
            success: function(text) {
                grid.getStore().load();
            },
            failure: function(error) {
                Ext.Msg.alert('Error', error.reason);
            }
        });
    }

    function showInit() {
        dateToolbar.setHideSubmitButton(true);

        var rfidButton = new Ext.contramed.BarcodeButton({
            height: 30,
            tooltip: 'Lee de la etiqueta del caje&iacute;n o del paciente y muestra su tratamiento',
            nonStopRead: false,
            iconCls:'tarjeta',
            listeners: {
                read: function(data) {
                    showThreatmentUrl(data, false, rfidButton);
                }
            }
        });

        var initPanel = new Ext.Panel({
            items: [new Ext.Toolbar({
                    //layout:'hbox',
                    items: [dateToolbar
                    ,rfidButton
                    ]
                }), bedmap
            ]
        });

        rfidButton.forceRead();
        setCentralPanel(initPanel, false);
    }

    function showThreatment(resume) {
        dateToolbar.setHideSubmitButton(false);

        var toolbar = new Ext.Toolbar({
            items: [dateToolbar, '->']
        });
        if (!resume) {
            toolbar.add(doseToolbar);
        }
        grid.setGroupCollapsed(resume);

        var toolbarInt = new Ext.Toolbar({
            //layout:'hbox',
            items: [new Ext.contramed.ButtonThreatmentToolbar({
//                applet:applet,
                    resumeMode: resume,
                    listeners: {
                        submitNextPatient: function(data, button) {
                            showThreatmentUrl(data, false, button);
                        },
                        submitBack: function() {
                            showInit();
                        },
                        submiObservation: function() {
                            new Ext.contramed.ObservationWindow({
                                url: 'saveObservation.htm'
                            }).show();
                        },
                        submitAcceptAll: function() {
                            Ext.Msg.confirm('Confirmaci&oacute;n', '&iquest;Desea marcar como preparada toda la medicaci&oacute;n de este paciente?', function(btn) {
                                if (btn === 'yes') {
                                    Ext.abada.Ajax.requestJson({
                                        url: 'prepareAll.htm',
                                        success: function(text) {
                                            grid.getStore().load();
                                        },
                                        failure: function(error) {

                                            Ext.Msg.alert('Error', error.reason);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }), '->', patientInfo
            ]
        });
        var threatmentPanel = new Ext.Panel({
            items: [toolbarInt,
                toolbar, grid]
        });

        if (resume) {
            toolbarInt.add('->');
            toolbarInt.add(new Ext.Button({
                scale: 'large',
                iconCls: 'play',
                tooltip: 'Pasar a modo administraci&oacute;n.',
                handler: function() {
                    showThreatment(false);
                }
            }));
        }

        patientInfo.load();
        setCentralPanel(threatmentPanel, false);
        dateToolbar.onSubmit();
        if (!resume) {
            doseToolbar.submitAdd();
        }
    }

    showInit();
});