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

Ext.idmedicationweb.PrincipioActivoGrid=Ext.extend(Ext.grid.GridPanel,{
    urlPrincipio:'',
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
    filters:new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'string',
            dataIndex: 'codigo'
        },
        {
            type: 'string',
            dataIndex: 'nombre'
        },
        {
            type: 'string',
            dataIndex:'anulado'
        },
        {
            type: 'string',
            dataIndex: 'codigoCa'
        },
        {
            type: 'string',
            dataIndex: 'composicion'
        },
        {
            type: 'string',
            dataIndex:'denominacion'
        },
        {
            type:'string',
            dataIndex:'formula'
        },
        {
            type:'string',
            dataIndex:'peso'
        },
        {
            type:'string',
            dataIndex:'dosis'
        },
        {
            type:'string',
            dataIndex:'dosisSuperficie'
        },
        {
            type:'string',
            dataIndex:'dosisPeso'
        },
        {
            type:'string',
            dataIndex:'uDosis'
        },
        {
            type:'decimal',
            dataIndex:'uDosisSuperficie'
        },
        {
            type:'string',
            dataIndex:'uDosisPeso'
        },
        {
            type:'numeric',
            dataIndex:'via'
        },
        {
            type:'numeric',
            dataIndex:'frecuencia'
        },
        {
            type:'string',
            dataIndex:'aditivo'
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
                name:'codigo'
            },

            {
                name:'nombre'
            },

            {
                name:'anulado'
            },

            {
                name:'codigoCa'
            },
            {
                name:'composicion'
            },
            {
                name:'denominacion'
            },
            {
                name:'formula'
            },
            {
                name:'peso'
            },
            {
                name:'dosis'
            },
            {
                name:'dosisSuperficie'
            },
            {
                name:'dosisPeso'
            },
            {
                name:'uDosis'
            },
            {
                name:'uDosisSuperficie'
            },
            {
                name:'uDosisPeso'
            },
            {
                name:'via'
            },
            {
                name:'frecuencia'
            },
            {
                name:'aditivo'
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
                dataIndex: 'codigo',
                tooltip:'C&oacute;digo del principio activo '
            },
            {
                header:'Nombre',
                dataIndex:'nombre',
                tooltip:'Nombre del principio activo',
                width:300
            },
            {
                header:'Anulado',
                dataIndex:'anulado'
            },
            {
                header:'C&oacute;digo Ca',
                dataIndex:'codigoCa'
            },
            {
                header:'Composici&oacute;n',
                dataIndex:'composicion',
                width:110
            },
            {
                header:'Denominaci&oacute;n',
                dataIndex:'denominacion'
            },
            {
                header:'F&oacute;rmula',
                dataIndex:'formula'
            },
            {
                header:'Peso',
                dataIndex:'peso'
            },
            {
                header:'Dosis',
                dataIndex:'dosis'
            },
            {
                header:'Dosis Superficie',
                dataIndex:'dosisSuperficie'
            },
            {
                header:'Dosis Peso',
                dataIndex:'dosisPeso'
            },
            {
                header:'U Dosis',
                dataIndex:'uDosis',
                width:110
            },
            {
                header:'U Dosis Superficie',
                dataIndex:'uDosisSuperficie',
                width:110
            },
            {
                header:'U Dosis Peso',
                dataIndex:'uDosisPeso'
            },
            {
                header:'V&iacute;a',
                dataIndex:'via'
            },
            {
                header:'Frecuencia',
                dataIndex:'frecuencia'
            },
            {
                header:'Aditivo',
                dataIndex:'aditivo'
            }

            ]
        });

        this.plugins=this.filters;

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        this.tbar= [{
            text: 'Insertar P.Activo',
            tooltip:'Abre el panel para insertar un principio activo',
            icon:'images/custom/add.gif',
            scope:this,
            handler: this.submitInsert
        },{
            text:'Modificar P.Activo',
            tooltip:'Abre el panel para modificar el principio activo seleccionado',
            icon:'images/custom/changestatus.png',
            scope:this,
            handler: this.submitUpdate
        },
        {
            text:'Eliminar P.Activo',
            tooltip:'elimina el principio activo seleccionado',
            icon:'images/custom/delete.gif',
            scope:this,
            handler: this.submitDelete
        }];

        this.addEvents('submitInsert','submitUpdate','submitDelete');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.PrincipioActivoGrid.superclass.constructor.call(this,cfg);

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

Ext.idmedicationweb.SimplePrincipioActivoGrid=Ext.extend(Ext.grid.GridPanel,{
    urlPrincipio:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        hidden:true,
        header:""
    }),
    loadMask: true,
    width: 350,
    autoHeight: true,
    frame: true,
    trackMouseOver:true,
    autoScroll:true,
    filters:new Ext.ux.grid.GridFilters({
        filters:[
        {
            type: 'string',
            dataIndex: 'codigo'
        },
        {
            type: 'string',
            dataIndex: 'nombre'
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
                name:'codigo'
            },

            {
                name:'nombre'
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
                dataIndex: 'codigo',
                tooltip:'C&oacute;digo del principio activo '
            },
            {
                header:'P.Activo',
                dataIndex:'nombre',
                tooltip:'Nombre del principio activo',
                width:200
            }

            ]
        });

        this.plugins=this.filters;

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 9,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        Ext.apply(this, cfg);
        Ext.idmedicationweb.SimplePrincipioActivoGrid.superclass.constructor.call(this,cfg);

    }

});