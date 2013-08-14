/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.namespace('Ext.idmedicationweb');

/***
 *Grid que muestra la informacion de las especialidades
 */

Ext.idmedicationweb.MedicineGrid=Ext.extend(Ext.grid.GridPanel,{
    urlMedicamento:'',
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
            dataIndex: 'anulado'
        },
        {
            type: 'string',
            dataIndex:'bioequivalente'
        },
        {
            type: 'date',
            dataIndex: 'caducidad',
            dateFormat:'d/m/Y'
        },
        {
            type: 'string',
            dataIndex: 'caracteristicas'
        },
        {
            type: 'string',
            dataIndex:'codGrupo'
        },
        {
            type:'string',
            dataIndex:'codLab'
        },
        {
            type:'string',
            dataIndex:'conservacion'
        },
        {
            type:'string',
            dataIndex:'dispensacion'
        },
        {
            type:'string',
            dataIndex:'especialControl'
        },
        {
            type:'date',
            dataIndex:'fechaAlta',
            dateFormat:'d/m/Y'
        },
        {
            type:'date',
            dataIndex:'fechaBaja',
            dateFormat:'d/m/Y'
        },
        {
            type:'decimal',
            dataIndex:'ficha'
        },
        {
            type:'string',
            dataIndex:'formaFarma'
        },
        {
            type:'string',
            dataIndex:'generico'
        },
        {
            type:'string',
            dataIndex:'largaDuracion'
        },
        {
            type:'string',
            dataIndex:'nombre'
        },
        {
            type:'string',
            dataIndex:'nombreLab'
        },
        {
            type:'string',
            dataIndex:'precioRef'
        },
        {
            type:'string',
            dataIndex:'pvp'
        },
        {
            type:'string',
            dataIndex:'regimen'
        },
        {
            type:'decimal',
            dataIndex:'dosis'
        },
        {
            type:'decimal',
            dataIndex:'dosisSuperficie'
        },
        {
            type:'decimal',
            dataIndex:'dosisPeso'
        },
        {
            type:'decimal',
            dataIndex:'uDosis'
        },
        {
            type:'decimal',
            dataIndex:'uDosisSuperficie'
        },
        {
            type:'decimal',
            dataIndex:'uDosisPeso'
        },
        {
            type:'string',
            dataIndex:'via_details'
        },
        {
            type:'string',
            dataIndex:'frecuencia_nombre'
        },
        {
            type:'string',
            dataIndex:'diluyente'
        },
        {
            type:'string',
            dataIndex:'aditivo'
        },
        {
            type:'decimal',
            dataIndex:'dosisDiluyente'
        }

        ]
    }),
    constructor:function(cfg){

        this.store= new Ext.data.JsonStore({
            url:cfg.urlMedicamento,
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
                name:'anulado'
            },

            {
                name:'bioequivalente'
            },

            {
                name:'caducidad',
                type:'date',
                format:'c'
            },
            {
                name:'caracteristicas'
            },
            {
                name:'codGrupo'
            },
            {
                name:'codLab'
            },
            {
                name:'conservacion'
            },
            {
                name:'dispensacion'
            },
            {
                name:'especialControl'
            },
            {
                name:'fechaAlta',
                type:'date',
                format:'c'
            },
            {
                name:'fechaBaja',
                type:'date',
                format:'c'
            },
            {
                name:'ficha'
            },
            {
                name:'formaFarma'
            },
            {
                name:'generico'
            },
            {
                name:'largaDuracion'
            },
            {
                name:'nombre'
            },
            {
                name:'nombreLab'
            },
            {
                name:'precioRef'
            },
            {
                name:'pvp'
            },
            {
                name:'regimen'
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
                name:'via_id',
                mapping:'via.id'
            },
            {
                name:'via_details',
                mapping:'via.details'
            },
            {
                name:'frecuencia_idTarea',
                mapping:'frecuencia.idTarea'
            },
            {
                name:'frecuencia_nombre',
                mapping:'frecuencia.nombre'
            },
            {
                name:'diluyente'
            },
            {
                name:'aditivo'
            },
            {
                name:'dosisDiluyente'
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
                tooltip:'C&oacute;digo nacional de la especialidad '
            },
            {
                header:'Nombre',
                dataIndex:'nombre',
                tooltip:'Nombre de la especialidad',
                width:300
            },
            {
                header:'Anulado',
                dataIndex:'anulado'
            },
            {
                header:'Bioequivalente',
                dataIndex:'bioequivalente'
            },
            {
                header:'Caducidad',
                dataIndex:'caducidad',
                xtype: 'datecolumn',
                format: 'd-m-Y',
                width:110
            },
            {
                header:'Caracter&iacute;sticas',
                dataIndex:'caracteristicas'
            },
            {
                header:'C&oacute;digo Grupo',
                dataIndex:'codGrupo'
            },
            {
                header:'C&oacute;digo Laboratorio',
                dataIndex:'codLab'
            },
            {
                header:'Conservaci&oacute;n',
                dataIndex:'conservacion'
            },
            {
                header:'Dispensaci&oacute;n',
                dataIndex:'dispensacion'
            },
            {
                header:'Control especial',
                dataIndex:'especialControl'
            },
            {
                header:'Fecha Alta',
                dataIndex:'fechaAlta',
                xtype: 'datecolumn',
                format: 'd-m-Y',
                width:110
            },
            {
                header:'Fecha Baja',
                dataIndex:'fechaBaja',
                xtype: 'datecolumn',
                format: 'd-m-Y',
                width:110
            },
            {
                header:'Ficha',
                dataIndex:'ficha'
            },
            {
                header:'Forma Farma',
                dataIndex:'formaFarma'
            },
            {
                header:'Gen&eacute;rico',
                dataIndex:'generico'
            },
            {
                header:'Larga Duraci&oacute;n',
                dataIndex:'largaDuracion'
            },
            {
                header:'Nombre Laboratorio',
                dataIndex:'nombreLab'
            },
            {
                header:'Precio Ref',
                dataIndex:'precioRef'
            },
            {
                header:'PVP',
                dataIndex:'pvp'
            },
            {
                header:'R&eacute;gimen',
                dataIndex:'regimen'
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
                dataIndex:'uDosis'
            },
            {
                header:'U Dosis Superficie',
                dataIndex:'uDosisSuperficie'
            },
            {
                header:'U Dosis Peso',
                dataIndex:'uDosisPeso'
            },
            {
                header:'V&iacute;a',
                dataIndex:'via_details',
                width: 150
            },
            {
                header:'ViaId',
                dataIndex:'via_id',
                hidden:true
            },
            {
                header:'Frecuencia',
                dataIndex:'frecuencia_nombre',
                width:200
            },
            {
                header:'Frecuencia Tarea',
                dataIndex:'frecuencia_idTarea',
                hidden:true
            },
            {
                header:'Diluyente',
                dataIndex:'diluyente'
            },
            {
                header:'Aditivo',
                dataIndex:'aditivo'
            },
            {
                header:'Dosis Diluyente',
                dataIndex:'dosisDiluyente'
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
            text: 'Insertar Especialidad',
            tooltip:'Abre el panel para insertar una especialidad',
            icon:'images/custom/add.gif',
            scope:this,
            handler: this.submitInsert
        },{
            text:'Modificar Especialidad',
            tooltip:'Abre el panel para modificar la especialidad seleccionada',
            icon:'images/custom/changestatus.png',
            scope:this,
            handler: this.submitUpdate
        }];

        this.addEvents('submitInsert','submitUpdate');

        Ext.apply(this, cfg);
        Ext.idmedicationweb.MedicineGrid.superclass.constructor.call(this,cfg);

    },
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    }

});

Ext.idmedicationweb.ComboYesNo=Ext.extend(Ext.form.ComboBox,{
    urlname: '',
    mode: 'local',
    width: 157,
    store: ['S','N'],
    allowBlank: true,
    triggerAction: 'all',
    editable:false,
    initComponent:function(){
        this.name=this.urlname;
        this.allowBlank=this.allowBlank;
        Ext.idmedicationweb.ComboYesNo.superclass.initComponent.call(this);

    }
    
});


/***
 *Combo que carga la lista de Vias (extiende de AbadaComboBox)
 *
 */

Ext.idmedicationweb.CbVia=Ext.extend(Ext.abada.ComboBox,{
    hiddenName:'via',
    name:'via',
    editable: false,
    forceSelection: false,
    width:157,
    anchor:'',
    fieldLabel:'V&iacute;a',
    allowBlank: true,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione una Via',
    initComponent:function(){

        Ext.idmedicationweb.CbVia.superclass.initComponent.call(this);

    }
});

/***
 *Combo que carga la lista de Frecuencias (extiende de AbadaComboBox)
 *
 */

Ext.idmedicationweb.CbFrecuencia=Ext.extend(Ext.abada.ComboBox,{
    hiddenName:'frecuencia',
    name:'frecuencia',
    editable: false,
    forceSelection: false,
    width:157,
    anchor:'',
    fieldLabel:'Frecuencia',
    allowBlank: true,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione una frecuencia',
    initComponent:function(){

        Ext.idmedicationweb.CbFrecuencia.superclass.initComponent.call(this);

    }
});

/***
 *Grid que muestra la columna del nombre de la especialidad
 */

Ext.idmedicationweb.SimpleMedicineGrid=Ext.extend(Ext.grid.GridPanel,{
    urlSimpleMed:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        hidden:true,
        header:""
    }),
    loadMask: true,
    autoHeight: true,
    width:465,
    frame: true,
    trackMouseOver:true,
    autoScroll:true,
    constructor:function(cfg){

        this.store= new Ext.data.JsonStore({
            url:cfg.urlSimpleMed,
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

        this.filters=new Ext.ux.grid.GridFilters({
            filters:[
            {
                type:'string',
                dataIndex:'codigo'
            },
            {
                type:'string',
                dataIndex:'nombre'
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
                tooltip:'C&oacute;digo nacional de la especialidad'
                
            },
            {
                header:'Especialidad',
                dataIndex:'nombre',
                tooltip:'Nombre de la especialidad',
                width:330
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
        Ext.idmedicationweb.SimpleMedicineGrid.superclass.constructor.call(this,cfg);

    }

});

/*
 *Textfield que recibe el identificador de una entidad
 *
 */

Ext.idmedicationweb.CodeTextField=Ext.extend(Ext.form.TextField,{
    urlname:'',
    hidden:true,
    allowBlank : false,
    initComponent:function(){
        this.name=this.urlname;
        Ext.idmedicationweb.CodeTextField.superclass.initComponent.call(this);

    }

});

Ext.idmedicationweb.ValueTextField=Ext.extend(Ext.form.TextField,{
    width:250,
    allowBlank : false,
    readOnly:true,
    initComponent:function(){
        Ext.idmedicationweb.ValueTextField.superclass.initComponent.call(this);

        this.addListener('specialkey',this.onSpecialKey);
    },
    onSpecialKey:function(field,e){
        if (Ext.isIE)
            e.stopEvent();
    }

});