/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.namespace('Ext.contramed');

/***
 * Grid para mostrar la informacion de los tratamientos
 */
Ext.contramed.IncidenceGrid=Ext.extend(Ext.grid.GridPanel,{
    url:undefined,
    pageSize:15,
    loadMask: true,
    autoHeight:true,
    autoWidth:true,
    autoScroll:true,
    hideStaff:false,
    viewConfig: {
        forceFit:true
    },
    filters: new Ext.ux.grid.GridFilters({
        filters:[{
            dataIndex:'idgivesIncidence',
            type:'numeric'
        },

        {
            dataIndex:'incidence',
            type:'string'
        },

        {
            dataIndex:'eventDate',
            type:'date',
            dateFormat:'d/m/Y'
        },

        {
            dataIndex:'staffIdstaff_idstaff',
            type:'numeric'
        },

        {
            dataIndex:'staffIdstaff_username',
            type:'string'
        },

        {
            dataIndex:'bed_idRecurso',
            type:'numeric'
        },

        {
            dataIndex:'bed_nr',
            type:'string'
        },

        {
            dataIndex:'patient_id',
            type:'numeric'
        },

        {
            dataIndex:'patient_name',
            type:'string'
        },

        {
            dataIndex:'patient_surname1',
            type:'string'
        },

        {
            dataIndex:'patient_surname2',
            type:'string'
        },

        {
            dataIndex:'dose_iddose',
            type:'numeric'
        },

        {
            dataIndex:'dose_batch',
            type:'string'
        },

        {
            dataIndex:'dose_giveAmount',
            type:'decimal'
        },

        {
            dataIndex:'dose_catalogomedicamentosCODIGO_codigo',
            type:'string'
        },

        {
            dataIndex:'dose_catalogomedicamentosCODIGO_nombre',
            type:'string'
        },

        {
            dataIndex:'dose_measureUnitIdmeasureUnit_idmeasureUnit',
            type:'numeric'
        },

        {
            dataIndex:'dose_measureUnitIdmeasureUnit_name',
            type:'string'
        },

        {
            dataIndex:'typeIncidence',
            type:'list',
            enumType:'com.abada.contramed.persistence.entity.enums.TypeIncidence',
            options: [['NO_EXIST_DOSE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NO_EXIST_DOSE']],
            ['EXPIRED_DOSE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['EXPIRED_DOSE']],
            ['NO_THREATMENT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NO_THREATMENT']],
            ['NO_CORRECT_THREATMENT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NO_CORRECT_THREATMENT']],
            ['IMPOSIBLE_REMOVE_DOSE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['IMPOSIBLE_REMOVE_DOSE']],
            ['ALLERGY',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['ALLERGY']],
            ['UNDEFINED',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['UNDEFINED']],
            ['RANGE_TIME_OUT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['RANGE_TIME_OUT']],
            ['MEDICATION_NOT_GIVEN',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['MEDICATION_NOT_GIVEN']]]
        }]
    }),
    constructor:function(cfg){
        Ext.apply(this, cfg);

        if (this.plugins){
            this.plugins.push(this.filters);
            this.plugins.push(new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            }));
        }else{
            this.plugins=[this.filters,new Ext.abada.ToolTipColumn({
                columnsNumber:[1],
                width:400
            })];
        }

        //Add column model
        colModelTemp=new Ext.grid.ColumnModel({
            defaults:{
                sortable:true
            },
            columns:[
            {
                dataIndex:'idgivesIncidence',
                header:'Id. Incidencia',
                hidden:true
            },
            {
                dataIndex:'incidence',
                header:'Texto',
                tooltip:'Descripci&oacute;n de la Incidencia',
                hidden:true

            },
            {
                dataIndex:'eventDate',
                header:'Fecha Incidencia',
                tooltip:'Fecha en la cual se produjo la incidencia',
                hidden:true
            },

            {
                dataIndex:'staffIdstaff_username',
                header:'Usuario del Personal',
                tooltip:'Usuario que produce la incidencia',
                hidden:true
            },
            {
                dataIndex:'bed_idRecurso',
                header:'Id. Cama',
                hidden:true
            },
            {
                dataIndex:'bed_nr',
                header:'Cama',
                tooltip:'Cama del paciente',
                hidden:true
            },
            {
                dataIndex:'patient_id',
                header:'Id. Paciente',
                hidden:true
            },
            {
                dataIndex:'patient_name',
                header:'Nombre',
                tooltip:'Nombre del paciente',
                hidden:true
            },
            {
                dataIndex:'patient_surname1',
                header:'Apellido1',
                tooltip:'Apellido primero del paciente',
                hidden:true
            },
            {
                dataIndex:'patient_surname2',
                header:'Apellido2',
                tooltip:'Apellido Segundo del paciente',
                hidden:true
            },
            {
                dataIndex:'dose_iddose',
                header:'Id. Dosis',
                hidden:true
            },
            {
                dataIndex:'dose_batch',
                header:'Lote',
                tooltip:'Lote del medicamento',
                hidden:true
            },
            {
                dataIndex:'dose_giveAmount',
                header:'Cantidad',
                tooltip:'Cantidad del medicamento',
                hidden:true
            },
            {
                dataIndex:'dose_measureUnitIdmeasureUnit_name',
                header:'Unidad',
                tooltip:'Unidad de medida',
                hidden:true
            },
            {
                dataIndex:'dose_catalogomedicamentosCODIGO_codigo',
                header:'C&oacute;digo Especialidad',
                tooltip:'C&oacute;digo del medicamento',
                hidden:true
            },
            {
                dataIndex:'dose_catalogomedicamentosCODIGO_nombre',
                header:'Especialidad',
                tooltip:'medicamento'
            },
           
            {
                header: 'Dosis',
                tooltip:'Cantidad de dosis administrar',
                renderer: function(v, params, record) {
                    var dosis="";
                    if(record.data.dose_giveAmount!=null)
                        dosis=record.data.dose_giveAmount+' '+record.data.dose_measureUnitIdmeasureUnit_name
                    return dosis;

                }
            },

            {
                dataIndex:'typeIncidence',
                header:'Incidencia',
                tooltip:'Los distintos tipos de incidencias que hay',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer

            },{
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
            },{
                header: 'Enfermero',
                tooltip:'persona que realiza el evento',
                renderer: function(v, params, record) {
                    var personal="";
                    if(record.data.staffIdstaff_username!=null)
                        personal=record.data.staffIdstaff_username;
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

        if (this.hideStaff)
            this.colModel.setHidden(19,true);

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
                name:'idgivesIncidence',
                mapping:'idgivesIncidence'
            },

            {
                name:'incidence',
                mapping:'incidence'
            },

            {
                name:'eventDate',
                mapping:'eventDate'
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
                name:'staffIdstaff_surname1',
                mapping:'staffIdstaff.surname1'
            },
            {
                name:'staffIdstaff_surname2',
                mapping:'staffIdstaff.surname2'
            },

            {
                name:'bed_idRecurso',
                mapping:'bed.idRecurso'
            },

            {
                name:'bed_nr',
                mapping:'bed.nr'
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
                name:'dose_iddose',
                mapping:'dose.iddose'
            },

            {
                name:'dose_batch',
                mapping:'dose.batch'
            },

            {
                name:'dose_giveAmount',
                mapping:'dose.giveAmount'
            },

            {
                name:'dose_catalogomedicamentosCODIGO_codigo',
                mapping:'dose.catalogomedicamentosCODIGO.codigo'
            },

            {
                name:'dose_catalogomedicamentosCODIGO_nombre',
                mapping:'dose.catalogomedicamentosCODIGO.nombre'
            },

            {
                name:'dose_measureUnitIdmeasureUnit_idmeasureUnit',
                mapping:'dose.measureUnitIdmeasureUnit.idmeasureUnit'
            },

            {
                name:'dose_measureUnitIdmeasureUnit_name',
                mapping:'dose.measureUnitIdmeasureUnit.name'
            },

            {
                name:'typeIncidence',
                mapping:'typeIncidence'
            }
            ]
        });

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: this.pageSize,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        //llamamos al constructor
        Ext.contramed.IncidenceGrid.superclass.constructor.call(this,cfg);
    },
    getPageSize:function(){
        return this.pageSize;
    }
});