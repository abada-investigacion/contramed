/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.namespace('Ext.contramedadmin');
/**
 *Grid inicial que nos permite buscar a los pacientes por apellidos, nombre y cama
 */
Ext.contramedadmin.PatientGridInicial=Ext.extend(Ext.grid.GridPanel,{
    url:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:true,
        header:""
    }),
    title:'B&uacute;squeda por Nombre y Apellidos',
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
            dataIndex: 'surname1'
        },
        {
            type: 'string',
            dataIndex: 'surname2'
        },
        {
            type: 'string',
            dataIndex: 'name'
        },
        {
            type: 'string',
            dataIndex: 'bed'
        },
        {
            type: 'string',
            dataIndex: 'tag'
        }
        ]
    }),

    constructor:function(cfg){
        this.store=new Ext.data.JsonStore({
            url:cfg.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'surname1',
                direction: 'ASC'
            },
            fields:[
            {
                name:'id',
                mapping:'id'
            },
            {
                name:'surname1',
                mapping:'surname1'
            },

            {
                name:'surname2',
                mapping:'surname2'
            },
            {
                name:'name',
                mapping:'name'
            },

            {
                name:'birthday',
                mapping:'birthday'
            },
            {
                name:'bed',
                mapping:'bed.nr'
            },
            {
                name:'tag',
                mapping:'patientTag.tag'
            }
            ]
        });
        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                header: 'Apellido1',
                dataIndex: 'surname1',
                tooltip:'Primer apellido del paciente'

            },
            {
                header: 'Apellido2',
                dataIndex: 'surname2',
                tooltip:'Segundo apellido del paciente'
            },
            {
                header: 'Nombre',
                dataIndex: 'name',
                tooltip:'Nombre del paciente'
            },
            {
                header: 'Fecha Nacimiento',
                dataIndex: 'birthday',
                tooltip:'Fecha de Nacimiento del paciente'
            },
            {
                header: 'Cama',
                dataIndex: 'bed',
                tooltip:'Cama en la que se encuentra el paciente'
            },
            {
                header: 'Tag',
                dataIndex: 'tag',
                tooltip:'Tag del paciente'
            }

            ]
        });
        this.plugins=[this.filters];
        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize:10,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });
        this.tbar= [{
            text: 'Modificar',
            tooltip:'Modifica el paciente selecionado en el grid',
            icon:'images/custom/find.gif',
            scope:this,
            handler: this.submitSearch
        }];
        Ext.apply(this, cfg);

        Ext.contramedadmin.PatientGridInicial.superclass.constructor.call(this,cfg);


    },
    submitSearch:function(){
        this.fireEvent('submitSearch');
    }
});
/**
 *Grid para mostrar la informacion del paciente que hemos buscado para
 *actuar sobre tu tag
 */
Ext.contramedadmin.PatientGrid=Ext.extend(Ext.grid.GridPanel,{
    url:'',
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
            dataIndex: 'patientid'
        },

        {
            type: 'string',
            dataIndex: 'name'
        },
        {
            type: 'string',
            dataIndex: 'surname1'
        },
        {
            type: 'string',
            dataIndex: 'surname2'
        },
        {
            type: 'string',
            dataIndex: 'details'
        },
        {
            type: 'string',
            dataIndex: 'value'
        },
        {
            type: 'string',
            dataIndex: 'tag'
        }]
    }),
    constructor:function(cfg){
        this.colModel=new Ext.grid.ColumnModel({
            defaults:{
                sortable:true
            },
            columns:[
                
            {
                header: 'Nombre',
                dataIndex: 'name',
                tooltip:'Nombre del paciente'
            },
            {
                header: 'Apellidos',
                dataIndex: 'surname1',
                tooltip:'Apellidos del paciente'
            },
            {
                header: 'Identificaci&oacute;n',
                dataIndex: 'details',
                tooltip:'Tipo de identificacion del paciente con su valor'
            },
            {
                header: 'Tag',
                dataIndex: 'tag',
                tooltip:'Identificador de Tarjeta'
            }
            ]
        });
        this.plugins=[this.filters];
        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize:13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });
        
        Ext.apply(this, cfg);

        Ext.contramedadmin.PatientGrid.superclass.constructor.call(this,cfg);
        this.store=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: false,
            fields:[
            {
                name:'id',
                mapping:'patientid'
            },

            {
                name:'name',
                mapping:'name'
            },

            {
                name:'surname1',
                mapping:'surname1'
            },

            {
                name:'surname2',
                mapping:'surname2'
            },

            {
                name:'details',
                mapping:'details'
            },

            {
                name:'value',
                mapping:'value'
            },

            {
                name:'tag',
                mapping:'tag'
            }
            ]
        });
       
    }
});
/**
 *Toolbar con la opcion de insertar, modificar y borrar el tag del paciente
 */
Ext.contramedadmin.PatientToolbar=Ext.extend(Ext.Toolbar,{
    addText:'Submit',
    applet:undefined,
    initComponent : function(){
     
        this.tfTag=new Ext.form.TextField({
            allowBlank:false,
            preventMark:true,
            scope:this,
            readOnly:true,
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Identificador del tag Rfid'
                    });
                },
                specialkey:function(field,e){
                    if (Ext.isIE)
                        e.stopEvent();
                }
            }
        });
        this.items=[(new Ext.Button({
            scale:'large',
            tooltip:'Vuelve al men&uacute; anterior',
            scope:this,
            iconCls:'atras',
            handler:this.onSubmitBack
        }))];
        this.items.push(' ');
        this.items.push(['Tag',' ',this.tfTag]);
        this.items.push(' ');
        this.items.push(new Ext.contramed.ButtonMCAApplet({
            text:this.addText,
            functionName:'readPCSC',
            id:'add',
            icon:'images/custom/add.gif',
            scope:this,
            scale:'small',
            applet:this.applet,
            listeners:{
                read:function(data){
                    this.scope.tfTag.setValue(data);
                    this.scope.onSubmitAdd();
                },
                render: function() {
                    Ext.getCmp('add').setTooltip('Insertar tag');
                }
            }
        }));
        
        this.items.push(' ');
        this.items.push(new Ext.Button({
            text:'Borrar',
            id:'borrar',
            icon:'images/custom/delete.gif',
            scope:this,
            handler: this.onSubmitRemove
        }));
      
        this.addEvents('submitAdd','submitRemove');
        Ext.contramedadmin.PatientToolbar.superclass.initComponent.call(this);
    },
    onSubmitAdd:function(){
        if (this.tfTag.isValid()){
            this.fireEvent('submitAdd',this.tfTag.getValue());
        }
        this.reset();
    },
    onSubmitRemove:function(){
        this.fireEvent('submitRemove');
        this.reset();
    },
    reset:function(){
        this.tfTag.setValue();
    },
    onSubmitBack:function(){
        this.fireEvent('submitBack');
    }

});
/**
 *Combo con el tipo de documento identificativo de los pacientes
 */
Ext.contramedadmin.ComboCode=Ext.extend(Ext.abada.ComboBox,{
    url:'',
    hiddenName:'identif',
    name:'identif',
    editable: false,
    forceSelection: false,
    width:400,
    fieldLabel:'Identificaci&oacute;n',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione un tipo de identificacion',
    initComponent:function(){

        Ext.contramedadmin.ComboCode.superclass.initComponent.call(this);

    }
});

/***
 * Grid para mostrar la informacion de RecursoTag
 *
 */
Ext.contramedadmin.RecursoTagGrid=Ext.extend(Ext.grid.GridPanel,{
    url:'',
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
            dataIndex: 'recurso.nr'
        },

        {
            type: 'string',
            dataIndex: 'tag'
        }
        ]
    }),
    constructor:function(cfg){
        this.store=new Ext.data.JsonStore({
            url:cfg.url,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'recurso.nr',
                direction: 'ASC'
            },
            fields:[
            {
                name:'id',
                mapping:'id'
            },
            {
                name:'idrecurso',
                mapping:'idrecurso'
            },
            {
                name:'recurso.nr',
                mapping:'recurso.nr'
            },

            {
                name:'tag',
                mapping:'tag'
            }
            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                dataIndex:'id',
                hidden:true
            },
            {

                dataIndex:'idrecurso',
                hidden:true
            },
            {
                header: 'Cama',
                dataIndex: 'recurso.nr',
                tooltip:'Cama donde se encuentra el paciente'


            },
            {
                header:'Tag',
                dataIndex:'tag',
                tooltip:'Tarjeta RFID'
            }

            ]
        });

        this.plugins=[this.filters];

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        Ext.apply(this, cfg);
        Ext.contramedadmin.RecursoTagGrid.superclass.constructor.call(this,cfg);
    }
});

/***
 * Toolbar para mostrar los botones de insertar, modificar  y borrar
 */
Ext.contramedadmin.insertUpdateDeleteToolbar=Ext.extend(Ext.Toolbar,{
    initComponent : function(){

        this.items=[new Ext.Button({
            text: 'Insertar',
            id:'insertar',
            icon:'images/custom/add.gif',
            scope:this,
            handler : this.submitInsert
        }),new Ext.Button({
            text:'Modificar',
            id:'modificar',
            icon:'images/custom/changestatus.png',            
            scope:this,
            handler: this.submitUpdate
        }),new Ext.Button({
            text:'Borrar',
            id:'borrar',
            icon:'images/custom/delete.gif',
            scope:this,
            handler: this.submitDelete
        })];

        this.addEvents('submitInsert','submitUpdate','submitDelete');


        Ext.contramedadmin.insertUpdateDeleteToolbar.superclass.initComponent.call(this);

    },/*Evento que dispara submitInsert*/
    submitInsert:function(){
        this.fireEvent('submitInsert');
    },/*Evento que dispara submitUpdate*/
    submitUpdate:function(){
        this.fireEvent('submitUpdate');
    },/*Evento que dispara submitDelete*/
    submitDelete:function(){
        this.fireEvent('submitDelete');
    }

});



/***
 *Combo que carga la lista de recurso Tag (extiende de AbadaComboBox)
 *
 **/

Ext.contramedadmin.ComboxRecursoTag=Ext.extend(Ext.abada.ComboBox,{
    url:'',
    hiddenName:'idrecurso',
    name:'idrecurso',
    editable: false,
    forceSelection: false,
    width:400,
    fieldLabel:'Cama',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione una cama/recurso',
    initComponent:function(){

        Ext.contramedadmin.ComboxRecursoTag.superclass.initComponent.call(this);

    }
});

/***
 * Grid para mostrar la informacion de Timing Range
 *
 */
Ext.contramedadmin.timingRangeGrid=Ext.extend(Ext.grid.GridPanel,{
    url:'',
    sm:new Ext.grid.CheckboxSelectionModel({
        singleSelect:false,
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
            dataIndex: 'idtimingRange'
        },
        {
            type: 'string',
            dataIndex: 'name'
        }]
    }),
    constructor:function(cfg){
        this.store=new Ext.data.JsonStore({
            url:cfg.url,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'startTime',
                direction: 'ASC'
            },
            fields:[
            {
                name:'idtimingRange',
                mapping:'idtimingRange'
            },

            {
                name:'startTime',
                mapping:'startTime',
                type: 'date',
                dateFormat: 'h:i:s A'
            },
            {
                name:'endTime',
                mapping:'endTime',
                type: 'date',
                dateFormat: 'h:i:s A'

            },

            {
                name:'name',
                mapping:'name'
            }]
        });
      
        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,

            {
                header: 'Toma',
                dataIndex: 'name',
                tooltip:'Nombre de la toma'
            },
            {
                header: 'Comienzo',
                dataIndex: 'startTime',
                xtype: 'datecolumn',
                format: 'H:i:s',
                tooltip:'Hora de comienzo'
            },{
                header: 'Fin',
                dataIndex: 'endTime',
                xtype: 'datecolumn',
                format: 'H:i:s',
                tooltip:'Hora de fin'

            }
            ]
        });


        this.plugins=[this.filters];

        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 13,
            plugins: [this.filters,new Ext.ux.SlidingPager()]
        });

        Ext.apply(this, cfg);
        Ext.contramedadmin.timingRangeGrid.superclass.constructor.call(this,cfg);
    }
});

/***
 * Grid para mostrar la informacion del personal (Staff)
 */
Ext.contramedadmin.StaffGrid=Ext.extend(Ext.grid.GridPanel,{
    url:'',
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
            dataIndex: 'idstaff'
        },

        {
            type: 'string',
            dataIndex: 'username'
        },
        {
            type: 'string',
            dataIndex: 'tag'
        },
               
        {
            dataIndex:'role',
            type: 'list',
            enumType:'com.abada.contramed.persistence.entity.enums.TypeRole',
            options: [['SYSTEM_ADMINISTRATOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['SYSTEM_ADMINISTRATOR']],
            ['ADMINISTRATIVE',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['ADMINISTRATIVE']],
            ['NURSE_PLANT',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT']],
            ['NURSE_PLANT_SUPERVISOR',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PLANT_SUPERVISOR']],
            ['NURSE_PHARMACY',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['NURSE_PHARMACY']],
            ['PHARMACIST',Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale['PHARMACIST']]]
        }
        ]
    }),
    constructor:function(cfg){
        this.store=new Ext.data.JsonStore({
            url:cfg.url,
            total:'total',
            root:'data',
            autoDestroy: true,
            idProperty:'id',
            remoteSort: true,
            sortInfo: {
                field: 'username',
                direction: 'ASC'
            },
            fields:[
            {
                name:'idstaff',
                mapping:'idstaff'
            },
            {
                name:'name',
                mapping:'name'
            },
            {
                name:'surname1',
                mapping:'surname1'
            },
            {
                name:'surname2',
                mapping:'surname2'
            },
            {
                name:'username',
                mapping:'username'
            },
            
            {
                name:'role',
                mapping:'role'
            },
            {
                name:'tag',
                mapping:'tag'
            },
            {
                name:'historic',
                mapping:'historic'
            }
            ]
        });

        this.colModel=new Ext.grid.ColumnModel({
            defaults: {
                sortable: true
            },
            columns:[this.sm,
            {
                header: 'Nombre',
                dataIndex: 'name',
                sortable:true,
                tooltip:'Nombre'
            },
            {
                header: 'Primer Apellido',
                dataIndex: 'surname1',
                tooltip:'Primer apellido'
            },
            {
                header: 'Segundo Apellido',
                dataIndex: 'surname2',
                tooltip:'Segundo apellido'
            },
            {
                header: 'Nombre Usuario',
                dataIndex: 'username',
                tooltip:'Nombre de usuario'
            },
            {
                header: 'Role',
                dataIndex: 'role',
                tooltip:'Role que tiene respecto de la aplicaci&oacute;n',
                renderer:Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer

            },
            {
                header: 'Tag',
                dataIndex: 'tag',
                tooltip:'Identificador del Tag RFID asignado a &eacute;l'
            }
           

            ]
        });
        this.plugins=[this.filters];
        this.bbar=new Ext.PagingToolbar({
            store:this.store,
            pageSize: 15,
            plugins:[this.filters,new Ext.ux.SlidingPager()]

        });
        Ext.apply(this, cfg);
        Ext.contramedadmin.StaffGrid.superclass.constructor.call(this,cfg);
    }
});

/**
 * Combo para el tipo de role del personal
 */
Ext.contramedadmin.ComboRoleStaff=Ext.extend(Ext.abada.ComboBox,{
    url:'',
    hiddenName:'role',
    name:'role',
    editable: false,
    forceSelection: false,
    width:200,
    fieldLabel:'Role',
    allowBlank: false,
    blankText: "El campo es obligatorio",
    emptyText: 'Seleccione un tipo de role',
    initComponent:function(){

        Ext.contramedadmin.ComboRoleStaff.superclass.initComponent.call(this);

    }
});


Ext.contramedadmin.ComboHistoric=Ext.extend(Ext.abada.ComboBox,{
    urlname: '',
    width: 230,
    grid:undefined,
    store: new Ext.data.SimpleStore({
        fields:['id','value'],
        data:[[1,'Mostrar todo el personal'],
        [2,'Personal dado de alta']  ,
        [3,'Personal dado de baja']]
    }),
    allowBlank: false,
    initComponent:function(){
        this.name=this.urlname;
        this.hiddenName=this.urlname;
        Ext.contramedadmin.ComboHistoric.superclass.initComponent.call(this);
    }
});


