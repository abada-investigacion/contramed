/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.namespace('Ext.idmedicationweb');

/***
 *Grid que muestra la informacion de las especialidades
 */

Ext.idmedicationweb.PrincipioHasEspecialidadGrid=Ext.extend(Ext.grid.GridPanel,{
    urlPrincipio:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        header:""
    }),
    loadMask: true,
    viewConfig: {
        forceFit:true
    },
    autoHeight: true,
    frame: true,
    trackMouseOver:true,
    autoScroll:true,
    filters:new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'numeric',
            dataIndex: 'id'
        },
        {
            type:'string',
            dataIndex:'codigoEspec_codigo'
        },
        {
            type: 'string',
            dataIndex: 'codigoEspec_nombre'
        },
         {
            type:'string',
            dataIndex:'codigoPrincipio_codigo'
        },
        {
            type: 'string',
            dataIndex:'codigoPrincipio_nombre'
        },
        {
            type: 'string',
            dataIndex: 'composicion'
        },
        {
            type: 'string',
            dataIndex: 'idUnidad'
        }

        ]
    }),
    constructor:function(cfg){

        this.store= new Ext.data.JsonStore({
            url:cfg.urlPrincipio,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:[
            {
                name:'id'
            },

            {
                name:'codigoEspec_nombre',
                mapping:'codigoEspec.nombre'
            },
            {
                name:'codigoEspec_codigo',
                mapping:'codigoEspec.codigo'
            },

            {
                name:'codigoPrincipio_nombre',
                mapping:'codigoPrincipio.nombre'
            },
            {
                name:'codigoPrincipio_codigo',
                mapping:'codigoPrincipio.codigo'
            },
            {
                name:'composicion'
            },
            {
                name:'idUnidad'
            }
            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                header: 'C&oacute;digo',
                dataIndex: 'id'
            },
            {
                header:'Especialidad',
                dataIndex:'codigoEspec_nombre',
                tooltip:'Nombre de la especialidad',
                width:300
            },
            {
                header:'C&oacute;digo Especialidad',
                dataIndex:'codigoEspec_codigo',
                tooltip:'Codigo de la especialidad',
                hidden:true
            },
            {
                header:'Principio Activo',
                dataIndex:'codigoPrincipio_nombre',
                width:120
            },
            {
                header:' C&oacute;digo Principio Activo',
                dataIndex:'codigoPrincipio_codigo',
                hidden:true
            },
            {
                header:'Composici&oacute;n',
                dataIndex:'composicion'
            },
            {
                header:'ID Unidad',
                dataIndex:'idUnidad'
            }

            ]
        });

        this.plugins=this.filters;

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 9,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        this.tbar= [{
            text: 'Insertar P.Activo de Especialidad',
            tooltip:'Abre el panel para asignar el P.Activo a una Especialidad ',
            icon:'images/custom/add.gif',
            scope:this,
            handler: this.submitInsert
        },{
            text:'Modificar P.Activo de Especialidad',
            tooltip:'Abre el panel para modificar el P.Activo de una Especialidad',
            icon:'images/custom/changestatus.png',
            scope:this,
            handler: this.submitUpdate
        },
        {
            text:'Eliminar P.Activo de Especialidad',
            tooltip:'Elimina el P.Activo de una Especialidad',
            icon:'images/custom/delete.gif',
            scope:this,
            handler: this.submitDelete
        }];

        this.addEvents('submitInsert','submitUpdate','submitDelete');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.PrincipioHasEspecialidadGrid.superclass.constructor.call(this,cfg);

    },
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    },
    submitDelete:function(){
      this.fireEvent('submitDelete');
    }

});