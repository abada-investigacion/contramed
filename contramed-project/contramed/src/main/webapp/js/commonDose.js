/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.namespace('Ext.idmedicationweb');


/***
 * Grid que muestra la informacion de las dosis
 *
 */
Ext.idmedicationweb.DoseGrid=Ext.extend(Ext.grid.GridPanel,{
    urlDose:'',
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
            dataIndex: 'iddose'
        },

        {
            type: 'string',
            dataIndex: 'catalogomedicamentosCODIGO_nombre'
        },

        {
            type: 'string',
            dataIndex:'batch'
        },

        {
            type: 'date',
            dataIndex: 'expirationDate',
            dateFormat:'d/m/Y'
        },

        {
            type: 'decimal',
            dataIndex: 'giveAmount'
        },

        {
            type: 'string',
            dataIndex:'measureUnitIdmeasureUnit'
        }
        ]
    }),
    constructor:function(cfg){
        this.store=new Ext.data.JsonStore({
            url:cfg.urlDose,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'iddose',
                direction: 'DESC'
            },
            fields:[
            {
                name:'iddose',
                mapping:'iddose'
            },

            {
                name:'catalogomedicamentosCODIGO_nombre',
                mapping:'catalogomedicamentosCODIGO.nombre'
            },
            {
                name:'catalogomedicamentosCODIGO',
                mapping:'catalogomedicamentosCODIGO.codigo'
            },

            {
                name:'batch',
                mapping:'batch'
            },

            {
                name:'expirationDate',
                mapping:'expirationDate'
            },

            {
                name:'giveAmount',
                mapping:'giveAmount'
            },

            {
                name:'measureUnitIdmeasureUnit',
                mapping:'measureUnitIdmeasureUnit.name'
            },
            {
                name:'measureUnitIdmeasureUnitId',
                mapping:'measureUnitIdmeasureUnit.idmeasureUnit'
            }
            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                header: 'Id Dosis',
                dataIndex: 'iddose',
                tooltip:'Identificador de la dosis',
                width:70
            },
            {
                header:'Nombre Especialidad',
                dataIndex:'catalogomedicamentosCODIGO_nombre',
                tooltip:'Especialidad a la que pertenece la dosis',
                width:220
            }
            ,{
                header:'C&oacute;digo Especialidad',
                dataIndex:'catalogomedicamentosCODIGO',
                tootlip:'C&oacute;digo nacional de la especialidad',
                width:220,
                hidden:true
            },
            {
                header:'Lote',
                dataIndex:'batch',
                tooltip:'Lote al que pertenece la dosis'
            },
            {
                header:'Fecha de Caducidad',
                dataIndex:'expirationDate',
                tooltip:'Fecha de caducidad de la dosis',
                width:130
            },
            {
                header:'Cantidad de Administraci&oacute;n',
                dataIndex:'giveAmount',
                tooltip:'Cantidad administrada',
                width:145
            },
            {
                header:'Unidad de Administraci&oacute;n',
                dataIndex:'measureUnitIdmeasureUnit',
                tooltip:'Unidad de medida de la cantidad a administrar',
                width:150
            },
            {
                header:'Id Unidad de Administraci&oacute;n',
                dataIndex:'measureUnitIdmeasureUnitId',
                tooltip:'Identificador de la unidad de medida de la cantidad a administrar',
                width:150,
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
            text: 'Insertar Dosis',
            tooltip:'Abre el panel para insertar dosis',
            icon:'images/custom/add.gif',
            scope:this,
            handler: this.submitInsert
        },{
            text:'Modificar Dosis',
            tooltip:'Abre el panel para modificar la dosis seleccionada',
            icon:'images/custom/changestatus.png',
            scope:this,
            handler: this.submitUpdate
        },{
            text: 'Imprimir Dosis',
            id:'imprimir',
            tooltip:'Abre el panel para imprimir la dosis seleccionada',
            icon:'images/custom/impresora.gif',
            scope:this,
            handler: this.submitPrint
        }];

        this.addEvents('submitInsert','submitUpdate','submitPrint');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.DoseGrid.superclass.constructor.call(this,cfg);
        
    },
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    },
    submitPrint:function(){
        this.fireEvent('submitPrint');
    }
});

/***
 *Combo que carga la lista de MeasureUnit (extiende de AbadaComboBox)
 *
 */

Ext.idmedicationweb.CbIdMeasureUnit=Ext.extend(Ext.abada.ComboBox,{
    hiddenName:'idmeasure_unit',
    name:'idmeasure_unit',
    editable: false,
    forceSelection: false,
    width:157,
    anchor:'',
    fieldLabel:'Unidad de Administraci&oacute;n',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione una Unidad ',
    initComponent:function(){
        
        Ext.idmedicationweb.CbIdMeasureUnit.superclass.initComponent.call(this);

    }
});

/***
 *DateField para seleccionar una fecha
 *
 **/
Ext.idmedicationweb.DateDataField=Ext.extend(Ext.form.DateField,{
    urlname:'',
    width: 157,
    allowBlank: false,
    editable:false,
    format: 'd-M-Y',
    showWeekNumber: true,
    initComponent:function(){

        this.name=this.urlname;
       
        Ext.idmedicationweb.DateDataField.superclass.initComponent.call(this);
    }

});

Ext.idmedicationweb.ComboPageSize=Ext.extend(Ext.abada.ComboBox,{
    urlname: '',
    width: 230,
    allowBlank: false,
    fieldLabel: 'Formato Pagina',
    initComponent:function(){
        this.name=this.urlname;
        this.hiddenName=this.urlname;
        Ext.idmedicationweb.ComboPageSize.superclass.initComponent.call(this);

    }

});
/*
Ext.idmedicationweb.ComboPageSize=Ext.extend(Ext.abada.ComboBox,{
    urlname: '',
    width: 230,
    store: new Ext.data.SimpleStore({
        fields:['id','value'],
        data:[['A0','A0 (84,1 x 118,9)'],['A1','A1 (59,4 x 84,1)'],['A2','A2 (42 x 59,4)'],['A3','A3 (29,7 x 42)'],['A4','A4 (21 x 29,7)'],['A5','A5 (14,8 x 21)'],['A6','A6 (10,5 x 14,8)'],['A7','A7 (7,4 x 10,5)'],['A8','A8 (5,2 x 7,4)'],
        ['A9','A9 (3,7 x 5,2)'],['A10','A10 (2,6 x 3,7)'],['ARCH_A','ARCH_A (22,9 x 30,5)'],['ARCH_B','ARCH_B (30,5 x 45,7)'],['ARCH_C','ARCH_C (45,7 x 61)'],['ARCH_D','ARCH_D (61 x 91,4)'],['ARCH_E','ARCH_E (91,4 x 121,9)'],
        ['B0','B0 (100 x 141,4)'],['B1','B1 (70,7 x 100)'],['B2','B2 (50 x 70,7)'],['B3','B3 (35,3 x 50)'],['B4','B4 (25 x 35,3)'],['B5','B5 (17,6 x 25)'],['B6','B6 (12,5 x 17,6)'],['B7','B7 (8,8 x 12,5)'],['B8','B8 (6,2 x 8,8)'],['B9','B9 (4,4 x 6,2)'],['B10','B10 (3,1 x 4,4)'],
        ['CROWN_OCTAVO','CROWN_OCTAVO (19 x 12,7)'],['CROWN_QUARTO','CROWN_QUARTO (25,4 x 19)'],['DEMY_OCTAVO','DEMY_OCTAVO (22,2 x 14,3)'],['DEMY_QUARTO','DEMY_QUARTO (28,6 x 22,2)'],['EXECUTIVE','EXECUTIVE (19 x 25,4)'],['FLSA','FLSA (33 x 21.5)'],
        ['FLSE','FLSE (33 x 22.8)'],['HALFLETTER','HALFLETTER (14 x 21,6)'],['ID_1','ID_1 (8,5 x 5,3)'],['ID_2','ID_2 (10,5 x 7,4)'],['ID_3','ID_3 (12,5 x 8,8)'],['LARGE_CROWN_OCTAVO','LARGE_CROWN_OCTAVO (19,8 x 12,8)'],['LARGE_CROWN_QUARTO','LARGE_CROWN_QUARTO (25.7 x 20)'],
        ['LEDGER','LEDGER (43,2 x 27,9)'],['LEGAL','LEGAL (21,6 x 35,6)'],['LETTER','LETTER (21,6 x 27,9)'],['NOTE','NOTE (25,4 X 19)'],['PENGUIN_LARGE_PAPERBACK','PENGUIN_LARGE_PAPERBACK (19,8 x 12,8)'],
        ['PENGUIN_SMALL_PAPERBACK','PENGUIN_SMALL_PAPERBACK (18.09 x 11.07)'],['POSTCARD','POSTCARD (14,6 x 9,9)'],['ROYAL_OCTAVO','ROYAL_OCTAVO (25,4 x 15,8)'],['ROYAL_QUARTO','ROYAL_QUARTO (31,7 x 25,4)'],
        ['SMALL_PAPERBACK','SMALL_PAPERBACK (17,7 x 11)'],['TABLOID','TABLOID (27,9 x 43,2)'],['_11X17','_11X17 (43,1 x 27,9)'],['11x17','11x17 (11,3 x 16,8)'],['10x16','10x16 (9,9 x 15,9)']]
    }),
    allowBlank: false,
    initComponent:function(){
        this.name=this.urlname;
        this.hiddenName=this.urlname;
        Ext.idmedicationweb.ComboPageSize.superclass.initComponent.call(this);

    }

});*/

Ext.idmedicationweb.Spinner=Ext.extend(Ext.ux.form.SpinnerField,{
    width:157,
    minValue: 0,
    value:0,
    allowBlank:false,
    initComponent:function(){

        Ext.idmedicationweb.Spinner.superclass.initComponent.call(this);

    }
});