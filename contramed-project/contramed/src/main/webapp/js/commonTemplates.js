/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.namespace('Ext.idmedicationweb');


/***
 * Grid que muestra la informacion de los Templates
 *
 */

Ext.idmedicationweb.TemplateGrid=Ext.extend(Ext.grid.GridPanel,{
    urlTemplate:'',
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
            type: 'numeric',
            dataIndex: 'idtemplatesMedication'
        },

        {
            type: 'string',
            dataIndex: 'template'
        },

        {
            type: 'string',
            dataIndex:'pathTemplate'
        },
        {
            type:'string',
            dataIndex:'pathStyle'
        }

        ]
    }),
    constructor:function(cfg){

        this.store = new Ext.data.JsonStore({
            url:cfg.urlTemplate,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:[
            {
                name:'idtemplatesMedication',
                mapping:'idtemplatesMedication'
            },

            {
                name:'template',
                mapping:'template'
            },

            {
                name:'pathTemplate',
                mapping:'pathTemplate'
            },
            {
                name:'pathStyle',
                mapping:'pathStyle'
            }
            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                header: 'Id Plantilla',
                dataIndex: 'idtemplatesMedication',
                tooltip:'Identificador de la plantilla',
                width:90
            },
            {
                header:'Plantilla',
                dataIndex:'template',
                tooltip:'Nombre de la plantilla',
                width:300
            },
            {
                header:'Ruta Plantillas',
                dataIndex:'pathTemplate',
                tooltip:'UUID bajo el que esta almacenado el fichero de plantilla',
                width:300
            },
            {
                header:'Ruta Estilos',
                dataIndex:'pathStyle',
                tooltip:'UUID bajo el que esta almacenado el fichero de estilos',
                width:300
            }
            ]
        });

        this.plugins=this.filters;

        this.bbar= new Ext.PagingToolbar({
            store: this.store,
            pageSize: 13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]

        });

        this.tbar= [{
            text: 'Insertar Plantilla',
            tooltip:'Abre el panel que permite insertar una plantilla',
            icon:'images/custom/add.gif',
            scope:this,
            handler: this.submitInsert
        },{
            text:'Modificar Plantilla',
            icon:'images/custom/changestatus.png',
            tooltip:'Abre el panel para modificar la plantilla seleccionada',
            scope:this,
            handler: this.submitUpdate
        }];

        this.addEvents('submitInsert','submitUpdate');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.TemplateGrid.superclass.constructor.call(this,cfg);

    },
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    }

});

/***
 * Grid que muestra la columna del nombre del Template
 *
 */

Ext.idmedicationweb.SimpleTemplateGrid=Ext.extend(Ext.grid.GridPanel,{
    urlSimpleTemplate:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        hidden:true,
        header:""
    }),
    loadMask: true,
    autoHeight: true,
    width:350,
    frame: true,
    trackMouseOver:true,
    autoScroll:true,
    constructor:function(cfg){

        this.store = new Ext.data.JsonStore({
            url:cfg.urlSimpleTemplate,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            fields:[
            {
                name:'idtemplatesMedication',
                mapping:'idtemplatesMedication'
            },

            {
                name:'template',
                mapping:'template'
            }
            ]
        });

        this.filters=new Ext.ux.grid.GridFilters({
            filters:[
            {
                type: 'numeric',
                dataIndex: 'idtemplatesMedication'
            },

            {
                type: 'string',
                dataIndex: 'template'
            }

            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,


            {
                header: 'Id Plantilla',
                dataIndex: 'idtemplatesMedication',
                tooltip:'Identificador de la plantilla',
                hidden:true,
                width:90
            },
            {
                header:'Plantilla',
                dataIndex:'template',
                tooltip:'Nombre de la plantilla',
                width:315
            }
            ]
        });

        this.plugins=this.filters;

        this.bbar= new Ext.PagingToolbar({
            store: this.store,
            pageSize: 10,
            plugins: [this.filters,new Ext.ux.SlidingPager()]

        });

        Ext.apply(this, cfg);
        Ext.idmedicationweb.SimpleTemplateGrid.superclass.constructor.call(this,cfg);

    }

});

/***
 *Combo que carga la lista de Vias (extiende de AbadaComboBox)
 *
 */

Ext.idmedicationweb.CbTemplates=Ext.extend(Ext.abada.ComboBox,{
    hiddenName:'temp',
    name:'temp',
    editable: false,
    forceSelection: false,
    width:157,
    anchor:'',
    fieldLabel:'Plantilla',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione una Plantilla',
    initComponent:function(){

        Ext.idmedicationweb.CbTemplates.superclass.initComponent.call(this);

    }
});

Ext.idmedicationweb.CbStyles=Ext.extend(Ext.abada.ComboBox,{
    hiddenName:'style',
    name:'style',
    editable: false,
    forceSelection: false,
    width:157,
    anchor:'',
    fieldLabel:'Estilo',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione un Estilo',
    initComponent:function(){

        Ext.idmedicationweb.CbStyles.superclass.initComponent.call(this);

    }
});