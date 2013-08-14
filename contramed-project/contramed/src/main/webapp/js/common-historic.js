/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.namespace('Ext.contramed');

/***
 * Grid para mostrar la informacion de la preparación de los cajetines
 */
Ext.contramed.GiveHistoricGrid=Ext.extend(Ext.grid.GridPanel,{
    url:undefined,
    pageSize:15,
    loadMask: true,
    autoHeight:true,
    autoWidth:true,
    autoScroll:true,
    idMapping:'idgivesHistoric',
    hideStaff:false,
    hideprecisa:false,
    viewConfig: {
        forceFit:true
    },
    filters: new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'numeric',
            dataIndex: 'idHistoric'
        },
        {
            type: 'date',
            dataIndex: 'eventDate',
            dateFormat:'d/m/Y'
        },
        {
            type: 'date',
            dataIndex: 'orderTimingDate',
            dateFormat:'d/m/Y'
        },
        {
            type: 'string',
            dataIndex: 'observation'
        },
        {
            type: 'string',
            dataIndex: 'patient_bed_nr'
        },
        {
            type: 'numeric',
            dataIndex: 'doseIddose_iddose'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_batch'
        },
        {
            type: 'date',
            dataIndex: 'doseIddose_expirationDate'
        },
        {
            type: 'decimal',
            dataIndex: 'doseIddose_giveAmount'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_catalogomedicamentosCODIGO_codigo'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_catalogomedicamentosCODIGO_nombre'
        },
        {
            type: 'numeric',
            dataIndex: 'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_measureUnitIdmeasureUnit_name'
        },
        {
            type: 'numeric',
            dataIndex: 'patient_id'
        },
        {
            type: 'string',
            dataIndex: 'patient_name'
        },
        {
            type: 'string',
            dataIndex: 'patient_surname1'
        },
        {
            type: 'string',
            dataIndex: 'patient_surname2'
        },
        {
            type: 'numeric',
            dataIndex: 'staffIdstaff_idstaff'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_username'
        },
        {
            type: 'list',
            dataIndex: 'staffIdstaff_role',
            enumType:'com.abada.contramed.persistence.entity.enums.TypeRole',
            options: [['SYSTEM_ADMINISTRATOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['SYSTEM_ADMINISTRATOR']],
            ['ADMINISTRATIVE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['ADMINISTRATIVE']],
            ['NURSE_PLANT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT']],
            ['NURSE_PLANT_SUPERVISOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT_SUPERVISOR']],
            ['NURSE_PHARMACY',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PHARMACY']],
            ['PHARMACIST',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['PHARMACIST']]]
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_name'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_surname1'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_surname2'
        }]
    }),
    constructor:function(cfg){
        Ext.apply(this, cfg);

        if (this.plugins){
            this.plugins.push(this.filters);
        /*this.plugins.push(new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            }));*/
        }else{
            this.plugins=[this.filters/*,new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            })*/];
        }

        //Add column model
        colModelTemp=new Ext.grid.ColumnModel({
            defaults:{
                sortable:true
            },
            columns:[
            {
                header: 'Id',
                dataIndex: 'idHistoric',
                hidden:true
            },
            {
                header: 'Fecha Adm',
                dataIndex: 'eventDate',
                xtype: 'datecolumn',
                format: 'd-m-Y H:i:s',
                tooltip:'Fecha en la cual se produjo el evento'
            },
            {
                header: 'Fecha de Administraci&oacute;n Prevista',
                dataIndex: 'orderTimingDate',
                tooltip:'Fecha prevista para administrar el medicamento',
                hidden:true
            },
            {
                header: 'Observaciones',
                dataIndex: 'observation',
                tooltip:'Nota que se pone para precisar o aclarar el evento',
                hidden:true
            },
            {
                header: 'Evento',
                dataIndex: 'type',
                tooltip:'Indica el tipo administraci&oacute;n que se ha producido',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer
            },
            {
                header: 'id dosis',
                dataIndex: 'doseIddose_iddose',
                hidden:true
            },
            {
                header: 'Lote de la dosis',
                dataIndex: 'doseIddose_batch',
                tooltip:'Lote de la dosis que se ha administrado al paciente',
                hidden:true
            },
            {
                header: 'Fecha de caducidad',
                dataIndex: 'doseIddose_expirationDate',
                tooltip:'Fecha de caducidad de la dosis',
                hidden:true
            },
            {
                header: 'Cantidad Administrada',
                dataIndex: 'doseIddose_giveAmount',
                tooltip:'Cantidad de dosis administrar',
                hidden:true
            },
           
            {
                header: 'id Unidad de Administraci&oacute;n',
                dataIndex: 'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit',
                hidden:true
            },
            {
                header: 'Unidad de Aministraci&oacute;n',
                dataIndex: 'doseIddose_measureUnitIdmeasureUnit_name',
                tooltip:'Unidad de medida de la cantidad de dosis a administrar',
                hidden:true
            },
            {
                header: 'C&oacute;digo Especialidad',
                dataIndex: 'doseIddose_catalogomedicamentosCODIGO_codigo',
                hidden:true
            },
            {
                header: 'Especialidad',
                dataIndex: 'doseIddose_catalogomedicamentosCODIGO_nombre',
                tooltip:'Medicamento'
            },{
                header: 'Hora',
                renderer: function(v, params, record) {
                    var hora="";
                    if(record.data.eventDate!=null){
                        hora=record.data.eventDate.toString().substring(16, 11);
                    }
                    //
                    return hora;

                },
                hidden:true
            },{
                header: 'Fecha Administraci&oacute;n',
                renderer: function(v, params, record) {
                    var hora="";
                    if(record.data.eventDate!=null)
                        hora=record.data.eventDate.toString().substring(0, 10);
                    return hora;

                },
                hidden:true
            },
            {
                header: 'Dosis',
                tooltip:'Cantidad de dosis administrar',
                renderer: function(v, params, record) {
                    var dosis="";
                    if(record.data.doseIddose_giveAmount!=null)
                        dosis=record.data.doseIddose_giveAmount+' '+record.data.doseIddose_measureUnitIdmeasureUnit_name
                    return dosis;

                }
            },{
                header: 'cama',
                dataIndex: 'patient_bed_nr',
                tooltip:'Cama del paciente',
                hidden:true
            },
            {
                header: 'id del paciente',
                dataIndex: 'patient_id',
                hidden:true
            },
            {
                header: 'Nombre del Paciente',
                dataIndex: 'patient_name',
                tooltip:'Nombre del paciente',
                hidden:true
            },
            {
                header: 'Paciente',
                tooltip:'paciente',
                renderer: function(v, params, record) {
                    var paciente="";
                    if(record.data.patient_name!=null)
                        paciente=record.data.patient_name;
                    if(record.data.patient_surname1!=null)
                        paciente=paciente+' '+record.data.patient_surname1;
                    if(record.data.patient_surname2!=null)
                        paciente=paciente+' '+record.data.patient_surname2;
                    return paciente;
                }
            },
            {
                header: 'Apellido1 del Paciente',
                dataIndex: 'patient_surname1',
                tooltip:'Apellido primero del paciente',
                hidden:true
            },
            {
                header: 'Apellido2 del Paciente',
                dataIndex: 'patient_surname2',
                tooltip:'Apellido segundo del paciente',
                hidden:true

            },
           
         
            {
                header:'Rol del Personal',
                dataIndex: 'staffIdstaff_role',
                tooltip:'Rol del usuario que realiza el evento',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer,
                hidden:true
            },
            {
                header: 'Enfermero',
                tooltip:'persona que realiza el evento',
                renderer: function(v, params, record) {
                    var personal="";
                    if(record.data.staffIdstaff_name!=null)
                        personal=record.data.staffIdstaff_name;
                    if(record.data.staffIdstaff_surname1!=null)
                        personal=personal+' '+record.data.staffIdstaff_surname1;
                    if(record.data.staffIdstaff_surname2!=null)
                        personal=personal+' '+record.data.staffIdstaff_surname2;
                    return personal;

                }
            },
            {
                header: 'Nombre del Personal',
                dataIndex: 'staffIdstaff_name',
                tooltip:'Nombre de la persona que realiza el evento',
                hidden:true
            },
            {
                header: 'Apellido1 del Personal',
                dataIndex: 'staffIdstaff_surname1',
                tooltip:'Apellido primero de la persona que realiza el evento',
                hidden:true
            },
            {
                header: 'Apellido2 del Personal',
                dataIndex: 'staffIdstaff_surname2',
                tooltip:'Apellido segundo de la persona que realiza el evento',
                hidden:true
            }
            ]
        });
        this.colModel=colModelTemp;

        if (this.hideStaff){
            this.colModel.setHidden(23,true);
           
        }
        if (this.hideprecisa){
            this.colModel.setHidden(13,false);
            this.colModel.setHidden(14,false);
            this.colModel.setHidden(16,false);
            this.colModel.setHidden(1,true);
            //this.colModel.setHidden(4,true);
            this.colModel.setHidden(23,true);

        }

        //creamos el store
        this.store=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'eventDate',
                direction: 'DESC'
            },
            fields:[
            {
                mapping:this.idMapping,
                name: 'idHistoric'
            },
            {
                mapping:'eventDate',
                name: 'eventDate',
                type:'date',
                format:'c'
            },
            {
                mapping:'orderTimingDate',
                name: 'orderTimingDate'
            },
            {
                mapping:'observation',
                name: 'observation'
            },
            {
                mapping:'type',
                name: 'type'
            },
            {
                mapping:'doseIddose.iddose',
                name: 'doseIddose_iddose'
            },
            {
                mapping:'doseIddose.batch',
                name: 'doseIddose_batch'
            },
            {
                mapping:'doseIddose.expirationDate',
                name: 'doseIddose_expirationDate'
            },
            {
                mapping:'doseIddose.giveAmount',
                name: 'doseIddose_giveAmount'
            },
            {
                name:'doseIddose_catalogomedicamentosCODIGO_codigo',
                mapping:'doseIddose.catalogomedicamentosCODIGO.codigo'
            },
            {
                name:'doseIddose_catalogomedicamentosCODIGO_nombre',
                mapping:'doseIddose.catalogomedicamentosCODIGO.nombre'
            },
            {
                name:'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit',
                mapping:'doseIddose.measureUnitIdmeasureUnit.idmeasureUnit'
            },
            {
                name:'doseIddose_measureUnitIdmeasureUnit_name',
                mapping:'doseIddose.measureUnitIdmeasureUnit.name'
            },
            {
                name:'patient_id',
                mapping:'patient.id'
            },
            {
                name:'patient_name',
                mapping:'patient.name'
            },
            {
                name:'patient_surname1',
                mapping:'patient.surname1'
            },
            {
                name:'patient_surname2',
                mapping:'patient.surname2'
            },
            {
                name:'staffIdstaff_idstaff',
                mapping:'staffIdstaff.idstaff'
            },
            {
                name:'staffIdstaff_username',
                mapping:'staffIdstaff.username'
            },
            {
                name:'staffIdstaff_role',
                mapping:'staffIdstaff.role'
            },
            {
                name:'staffIdstaff_name',
                mapping:'staffIdstaff.name'
            },
            {
                name:'staffIdstaff_surname1',
                mapping:'staffIdstaff.surname1'
            },
            {
                name:'staffIdstaff_surname2',
                mapping:'staffIdstaff.surname2'
            },
            {
                name:'patient_bed_nr',
                mapping:'patient.bed.nr'
            }
            ]
        });

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: this.pageSize,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        //llamamos al constructor
        Ext.contramed.GiveHistoricGrid.superclass.constructor.call(this,cfg);
    },
    getPageSize:function(){
        return this.pageSize;
    }
});

/***
* Grid para mostrar la informacion de la preparación de los cajetines
*/
Ext.contramed.PrepareHistoricGrid=Ext.extend(Ext.grid.GridPanel,{
    url:undefined,
    pageSize:15,
    loadMask: true,
    autoHeight:true,
    autoWidth:true,
    autoScroll:true,
    idMapping:'idgivesHistoric',
    hideStaff:false,
    viewConfig: {
        forceFit:true
    },
    filters: new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'numeric',
            dataIndex: 'idHistoric'
        },
        {
            type: 'date',
            dataIndex: 'eventDate',
            dateFormat:'d/m/Y'
        },
        {
            type: 'date',
            dataIndex: 'orderTimingDate',
            dateFormat:'d/m/Y'
        },
        {
            type: 'string',
            dataIndex: 'observation'
        }/*,
        {
            type: 'string',
            dataIndex: 'type'
        }*/,
        {
            type: 'numeric',
            dataIndex: 'doseIddose_iddose'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_batch'
        },
        {
            type: 'date',
            dataIndex: 'doseIddose_expirationDate'
        },
        {
            type: 'decimal',
            dataIndex: 'doseIddose_giveAmount'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_catalogomedicamentosCODIGO_codigo'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_catalogomedicamentosCODIGO_nombre'
        },
        {
            type: 'numeric',
            dataIndex: 'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit'
        },
        {
            type: 'string',
            dataIndex: 'doseIddose_measureUnitIdmeasureUnit_name'
        },
        {
            type: 'numeric',
            dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_id'
        },
        {
            type: 'string',
            dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_name'
        },
        {
            type: 'string',
            dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_surname1'
        },
        {
            type: 'string',
            dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_surname2'
        },
        {
            type: 'numeric',
            dataIndex: 'staffIdstaff_idstaff'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_username'
        },
        {
            type: 'list',
            dataIndex: 'staffIdstaff_role',
            enumType:'com.abada.contramed.persistence.entity.enums.TypeRole',
            options: [['SYSTEM_ADMINISTRATOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['SYSTEM_ADMINISTRATOR']],
            ['ADMINISTRATIVE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['ADMINISTRATIVE']],
            ['NURSE_PLANT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT']],
            ['NURSE_PLANT_SUPERVISOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT_SUPERVISOR']],
            ['NURSE_PHARMACY',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PHARMACY']],
            ['PHARMACIST',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['PHARMACIST']]]
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_name'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_surname1'
        },
        {
            type: 'string',
            dataIndex: 'staffIdstaff_surname2'
        }]
    }),
    constructor:function(cfg){
        Ext.apply(this, cfg);

        if (this.plugins){
            this.plugins.push(this.filters);
        /*this.plugins.push(new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            }));*/
        }else{
            this.plugins=[this.filters/*,new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            })*/];
        }

        //Add column model
        colModelTemp=new Ext.grid.ColumnModel({
            defaults:{
                sortable:true
            },
            columns:[
            {
                header: 'Id',
                dataIndex: 'idHistoric',
                hidden:true
            },
            {
                header: 'Fecha Adm',
                dataIndex: 'eventDate',
                xtype: 'datecolumn',
                format: 'd-m-Y',
                tooltip:'Fecha en la cual se produjo el evento'
            },
            {
                header: 'Fecha de Administraci&oacute;n Prevista',
                dataIndex: 'orderTimingDate',
                tooltip:'Fecha prevista para administrar el medicamento',
                hidden:true
            },
            {
                header: 'Observaciones',
                dataIndex: 'observation',
                tooltip:'Nota que se pone para precisar o aclarar el evento',
                hidden:true
            },
            {
                header: 'Evento',
                dataIndex: 'type',
                tooltip:'Indica el tipo administraci&oacute;n que se ha producido',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer
            },
            {
                header: 'id dosis',
                dataIndex: 'doseIddose_iddose',
                hidden:true
            },
            {
                header: 'Lote de la dosis',
                dataIndex: 'doseIddose_batch',
                tooltip:'Lote de la dosis que se ha administrado al paciente',
                hidden:true
            },
            {
                header: 'Fecha de caducidad',
                dataIndex: 'doseIddose_expirationDate',
                tooltip:'Fecha de caducidad de la dosis',
                hidden:true
            },
            {
                header: 'Cantidad Administrada',
                dataIndex: 'doseIddose_giveAmount',
                tooltip:'Cantidad de dosis administrar',
                hidden:true
            },
            {
                header: 'id Unidad de Administraci&oacute;n',
                dataIndex: 'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit',
                hidden:true
            },
            {
                header: 'Unidad de Aministraci&oacute;n',
                dataIndex: 'doseIddose_measureUnitIdmeasureUnit_name',
                tooltip:'Unidad de medida de la cantidad de dosis a administrar',
                hidden:true
            },
            {
                header: 'C&oacute;digo Especialidad',
                dataIndex: 'doseIddose_catalogomedicamentosCODIGO_codigo',
                hidden:true
            },
            {
                header: 'Especialidad',
                dataIndex: 'doseIddose_catalogomedicamentosCODIGO_nombre',
                tooltip:'Medicamento'
            },
            {
                header: 'id del paciente',
                dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_id',
                hidden:true
            },
            {
                header: 'Nombre del Paciente',
                dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_name',
                tooltip:'Nombre del paciente',
                hidden:true
            },
            {
                header: 'Apellido1 del Paciente',
                dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_surname1',
                tooltip:'Apellido primero del paciente',
                hidden:true
            },
            {
                header: 'Apellido2 del Paciente',
                dataIndex: 'orderTimingIdorderTiming_orderIdorder_patientId_surname2',
                tooltip:'Apellido segundo del paciente',
                hidden:true
            },
            {
                header: 'id del personal',
                dataIndex: 'staffIdstaff_idstaff',
                hidden:true
            },
            {
                header: 'Nombre de Usuario del Personal',
                dataIndex: 'staffIdstaff_username',
                tooltip:'Usuario que realiza el evento',
                hidden:true
            },
            {
                header:'Rol del Personal',
                dataIndex: 'staffIdstaff_role',
                tooltip:'Rol del usuario que realiza el evento',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer,
                hidden:true
            },
            {
                header: 'Dosis',
                tooltip:'Cantidad de dosis administrar',
                renderer: function(v, params, record) {
                    var dosis="";
                    if(record.data.doseIddose_giveAmount!=null)
                        dosis=record.data.doseIddose_giveAmount+' '+record.data.doseIddose_measureUnitIdmeasureUnit_name
                    return dosis;

                }
            },{
                header: 'Pacientes',
                tooltip:'paciente',
                renderer: function(v, params, record) {
                    var paciente="";
                    if(record.data.orderTimingIdorderTiming_orderIdorder_patientId_name!=null)
                        paciente=record.data.orderTimingIdorderTiming_orderIdorder_patientId_name;
                    if(record.data.orderTimingIdorderTiming_orderIdorder_patientId_surname1!=null)
                        paciente=paciente+' '+record.data.orderTimingIdorderTiming_orderIdorder_patientId_surname1;
                    if(record.data.orderTimingIdorderTiming_orderIdorder_patientId_surname2!=null)
                        paciente=paciente+' '+record.data.orderTimingIdorderTiming_orderIdorder_patientId_surname2;
                    return paciente;
                }
            }, {
                header: 'Enfermero',
                tooltip:'persona que realiza el evento',
                renderer: function(v, params, record) {
                    var personal="";
                    if(record.data.staffIdstaff_name!=null)
                        personal=record.data.staffIdstaff_name;
                    if(record.data.staffIdstaff_surname1!=null)
                        personal=personal+' '+record.data.staffIdstaff_surname1;
                    if(record.data.staffIdstaff_surname2!=null)
                        personal=personal+' '+record.data.staffIdstaff_surname2;
                    return personal;
                }
            }
            ]
        });
        this.colModel=colModelTemp;

        if (this.hideStaff){
            this.colModel.setHidden(22,true);
        }

        //creamos el store
        this.store=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'eventDate',
                direction: 'DESC'
            },
            fields:[
            {
                mapping:this.idMapping,
                name: 'idHistoric'
            },
            {
                mapping:'eventDate',
                name: 'eventDate',
                type:'date',
                dateFormat:'c'
                
                
            },
            {
                mapping:'orderTimingDate',
                name: 'orderTimingDate'
            },
            {
                mapping:'observation',
                name: 'observation'
            },
            {
                mapping:'type',
                name: 'type'
            },
            {
                mapping:'doseIddose.iddose',
                name: 'doseIddose_iddose'
            },
            {
                mapping:'doseIddose.batch',
                name: 'doseIddose_batch'
            },
            {
                mapping:'doseIddose.expirationDate',
                name: 'doseIddose_expirationDate'
            },
            {
                mapping:'doseIddose.giveAmount',
                name: 'doseIddose_giveAmount'
            },
            {
                name:'doseIddose_catalogomedicamentosCODIGO_codigo',
                mapping:'doseIddose.catalogomedicamentosCODIGO.codigo'
            },
            {
                name:'doseIddose_catalogomedicamentosCODIGO_nombre',
                mapping:'doseIddose.catalogomedicamentosCODIGO.nombre'
            },
            {
                name:'doseIddose_measureUnitIdmeasureUnit_idmeasureUnit',
                mapping:'doseIddose.measureUnitIdmeasureUnit.idmeasureUnit'
            },
            {
                name:'doseIddose_measureUnitIdmeasureUnit_name',
                mapping:'doseIddose.measureUnitIdmeasureUnit.name'
            },
            {
                name:'orderTimingIdorderTiming_orderIdorder_patientId_id',
                mapping:'orderTimingIdorderTiming.orderIdorder.patientId.id'
            },
            {
                name:'orderTimingIdorderTiming_orderIdorder_patientId_name',
                mapping:'orderTimingIdorderTiming.orderIdorder.patientId.name'
            },
            {
                name:'orderTimingIdorderTiming_orderIdorder_patientId_surname1',
                mapping:'orderTimingIdorderTiming.orderIdorder.patientId.surname1'
            },
            {
                name:'orderTimingIdorderTiming_orderIdorder_patientId_surname2',
                mapping:'orderTimingIdorderTiming.orderIdorder.patientId.surname2'
            },
            {
                name:'staffIdstaff_idstaff',
                mapping:'staffIdstaff.idstaff'
            },
            {
                name:'staffIdstaff_username',
                mapping:'staffIdstaff.username'
            },
            {
                name:'staffIdstaff_role',
                mapping:'staffIdstaff.role'
            },
            {
                name:'staffIdstaff_name',
                mapping:'staffIdstaff.name'
            },
            {
                name:'staffIdstaff_surname1',
                mapping:'staffIdstaff.surname1'
            },
            {
                name:'staffIdstaff_surname2',
                mapping:'staffIdstaff.surname2'
            }
            ]
        });

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: this.pageSize,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        //llamamos al constructor
        Ext.contramed.PrepareHistoricGrid.superclass.constructor.call(this,cfg);
    },
    getPageSize:function(){
        return this.pageSize;
    }
});