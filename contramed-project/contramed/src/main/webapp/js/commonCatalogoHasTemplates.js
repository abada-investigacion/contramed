/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.namespace('Ext.idmedicationweb');

/***
 * Grid que muestra la informacion de los Templates de cada Especialidad
 *
 */

Ext.idmedicationweb.CatHasTemplateGrid=Ext.extend(Ext.grid.GridPanel,{
    urlMedTemplate:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        header:""
    }),
    loadMask: true,
    autoHeight: true,
    frame: true,
    trackMouseOver:true,
    autoScroll:true,
    viewConfig: {
        forceFit:true
    },
    filters: new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'string',
            dataIndex: 'catalogoMedicamentos_nombre'
        },
        {
            type:'string',
            dataIndex:'templatesId_template'

        }

        ]
    }),
    constructor:function(cfg){

        this.store=new Ext.data.JsonStore({
            url:cfg.urlMedTemplate,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:[
            {
                name:'catalogoCodigo'
            },
            {
                name:'catalogoMedicamentos_nombre',
                mapping:'catalogoMedicamentos.nombre'
            },
            {
                name:'catalogoMedicamentos_codigo',
                mapping:'catalogoMedicamentos.codigo'
            },
            {
                name:'templatesId_idtemplatesMedication',
                mapping:'templatesId.idtemplatesMedication'
            },
            {
                name:'templatesId_template',
                mapping:'templatesId.template'
            }

            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,

            {
                header: 'Especialidad',
                dataIndex: 'catalogoMedicamentos_nombre',
                tooltip:'Nombre de la especialidad',
                width:250
            },
            {
                header:'C&oacute;digo Especialidad',
                dataIndex:'catalogoMedicamentos_codigo',
                tooltip:'C&oacute;digo nacional de la especialidad',
                hidden:true
            },
            {
                header:'Plantilla',
                dataIndex:'templatesId_template',
                tooltip:'Nombre de la plantilla asociada a la especialidad',
                width:250
            },
            {
                header:'C&oacute;digo Plantilla',
                dataIndex:'templatesId_idtemplatesMedication',
                tooltip:'C&oacute;digo de la plantilla asociada a la especialidad',
                hidden:true
            }

            ]
        });

        this.plugins=[this.filters];

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        this.tbar= [{
            text: 'Insertar Plantilla de Especialidad',
            icon:'images/custom/add.gif',
            scope:this,
            tooltip:'Abre el panel que permite insertar una plantilla de especialidad',
            handler: this.submitInsert
        },{
            text:'Modificar Plantilla de Especialidad',
            icon:'images/custom/changestatus.png',
            scope:this,
            tooltip:'Abre el panel que permite modficiar la plantilla de especialidad seleccionada',
            handler: this.submitUpdate
        }];

        this.addEvents('submitInsert','submitUpdate');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.CatHasTemplateGrid.superclass.constructor.call(this,cfg);

    },
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    }


});