/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.namespace('Ext.contramed');


Ext.contramed.ObservacionesGrid=Ext.extend(Ext.grid.GridPanel,{
    url:undefined,
    loadMask: true,
    autoHeight: true,
    frame: true,
    pageSize:15,
    idMapping:'idobservation',
    hideStaff:false,
    trackMouseOver:true,
    autoScroll:true,
    viewConfig: {
        forceFit:true
    },
    filters:new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'date',
            dataIndex: 'eventTime',
            dateFormat:'d/m/Y'
        },
        {
            type: 'string',
            dataIndex: 'patientId_name'
        },
        {
            type: 'string',
            dataIndex: 'patientId_surname1'
        },
        {
            type: 'string',
            dataIndex: 'staffId_name'
        },
        {
            type: 'string',
            dataIndex: 'staffId_surname1'
        },
        {
            type: 'string',
            dataIndex: 'staffId_surname2'
        },
        {
            type: 'string',
            dataIndex: 'observation'
        }

        ]
    }),

    constructor:function(cfg){
        Ext.apply(this, cfg);

        colModelTmp=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[
            {
                header: 'Id',
                dataIndex: 'idobservation',
                hidden:true
            },
            {
                header: 'Fecha de la Observaci&oacute;n',
                dataIndex: 'eventTime',
                tooltip:'Fecha en la cual se produjo el evento'
            },
            {
                header: 'id del paciente',
                dataIndex: 'patient_id',
                hidden:true
            },
            {
                header: 'Nombre del Paciente',
                dataIndex: 'patientId_name',
                tooltip:'Nombre del paciente'
            },
            {
                header: 'Apellido1 del Paciente',
                dataIndex: 'patientId_surname1',
                tooltip:'Apellido primero del paciente'
            },
            {
                header: 'Apellido2 del Paciente',
                dataIndex: 'patientId_surname2',
                tooltip:'Apellido segundo del paciente'
            },{
                header: 'Observaciones',
                dataIndex: 'observation',
                tooltip:'Nota que se pone para precisar o aclarar el evento'
            },
            {
                header: 'id del personal',
                dataIndex: 'staffId_idstaff',
                hidden:true
            },{
                header: 'Nombre del Personal',
                dataIndex: 'staffId_name',
                tooltip:'Nombre de la persona que realiza el evento'
            },
            {
                header: 'Apellido1 del Personal',
                dataIndex: 'staffId_surname1',
                tooltip:'Apellido primero de la persona que realiza el evento'
            },
            {
                header: 'Apellido2 del Personal',
                dataIndex: 'staffId_surname2',
                tooltip:'Apellido segundo de la persona que realiza el evento'
            }]
        });
        this.colModel=colModelTmp;

        if (this.hideStaff){
            this.colModel.setHidden(8,true);
            this.colModel.setHidden(9,true);
            this.colModel.setHidden(10,true);
            
        }
        this.store=new Ext.data.JsonStore({
            url:cfg.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'eventTime',
                direction: 'DESC'
            },
            fields:[
            {
                mapping:this.idMapping,
                name: 'idobservation'
            },
            {
                mapping:'eventTime',
                name: 'eventTime'
            },
            {
                name:'patientId_id',
                mapping:'patientId.id'
            },
            {
                name:'patientId_name',
                mapping:'patientId.name'
            },
            {
                name:'patientId_surname1',
                mapping:'patientId.surname1'
            },
            {
                name:'patientId_surname2',
                mapping:'patientId.surname2'
            },
            {
                name:'observation',
                mapping:'observation'
            },
            {
                name:'staffId_idstaff',
                mapping:'staffId.idstaff'
            },
            {
                name:'staffId_name',
                mapping:'staffId.name'
            },
            {
                name:'staffId_surname1',
                mapping:'staffId.surname1'
            },
            {
                name:'staffId_surname2',
                mapping:'staffId.surname2'
            }
            ]
        });
        this.plugins=[this.filters];
        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize:this.pageSize,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });
     
        //llamamos al constructor
        Ext.contramed.ObservacionesGrid.superclass.constructor.call(this,cfg);
    },
    getPageSize:function(){
        return this.pageSize;
    }
});