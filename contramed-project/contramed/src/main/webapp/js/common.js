/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.namespace('Ext.contramed');

Ext.contramed.IconColumn = Ext.extend(Ext.grid.TemplateColumn,{
    image:undefined,
    grid:undefined,
    constructor:function(cfg){
        Ext.apply(this, cfg);        
        this.tpl=new Ext.XTemplate('<img src=\"'+this.image+'\" />');

        //TODO añadir al store del grid una columna nueva
        //TODO añadir un evento nuevo que se dará cuando hagan click en el icono

        Ext.contramed.IconColumn.superclass.constructor.call(this,cfg);

        if (this.grid)
            this.grid.on('cellclick',this.onClick,this);
        this.grid.addEvents('iconColumnClick');
    },
    getColumnIndex:function(){
        columns=this.grid.getColumnModel().columns;        
        for (i=0;i<columns.length;i++){            
            if (columns[i]==this)
                return i;
        }
        return -1;
    },
    onClick:function(grid,rowIndex,columnIndex,e){
        ci=this.getColumnIndex();
        if (ci==columnIndex)
            this.grid.fireEvent('iconColumnClick',grid,grid.getStore().getAt(rowIndex),rowIndex,columnIndex);
    }
});

/***
 * Grid para mostrar la informacion de los tratamientos
 *
 * Existe un bug conocido que sucede cuando se colapsa un medicamento con que su nombre contenga '<' o '>', una vez colapsado ya se
 * puede volver a expandir
 */
Ext.contramed.ThreatmentGrid=Ext.extend(Ext.grid.GridPanel,{
    urlThreatment:'',
    urlHistoric:'',
    loadMask: true,
    autoHeight:true,
    autoWidth:true,
    autoScroll:true,
    groupCollapsed:true,
    enableColumnMove:false,
    viewConfig: {
        forceFit:true
    },
    constructor:function(cfg){
        Ext.apply(this, cfg);
        //llamamos al constructor
        Ext.contramed.ThreatmentGrid.superclass.constructor.call(this,cfg);
   
        //Add column model
        colModelTemp=new Ext.grid.ColumnModel({
            defaults:{
                sortable:false
            },
            columns:[
            {
                header: 'Fecha de administraci&oacute;n',
                dataIndex: 'giveTime',
                width:10,
                tooltip:'Fecha en la que se ha realizado la administraci&oacute;n del medicamento',
                //sortable:true,
                renderer:function(value){
                    d=Date.parseDate(value,'Y-m-d H:i:s');
                    return '<b>'+Ext.util.Format.date(d, 'H:i d/m/Y')+'</b>';
                }
            },new Ext.abada.BooleanColumn({
                header: 'Si Precisa',
                dataIndex: 'ifNecesary',
                //sortable:true,
                width:5,
                koImage:'images/custom/nothing.png',
                okImage:'images/custom/sp.png'
            }),
            {
                width:8,
                header: 'V&iacute;a de administraci&oacute;n',
                dataIndex: 'administrationType',
                tooltip:'Forma de administrar el medicamento'
            //sortable:true
            },
            new Ext.abada.AppendColumn({
                width:5,
                header: 'Cantidad',
                tooltip:'Cantidad de medicamento que se va a administrar',
                dataIndex: ['giveAmount','measureUnit']
            //sortable:true
            }),
            {
                header: 'Estado',
                dataIndex: 'administrationStatus',
                width:9,
                tooltip:'Indica el estado del tratamiento y la cantidad de tratamiento que falta por administrar'
            //sortable:true
            },            
            new Ext.abada.BooleanColumn({
                header: '&iquest;Al&eacute;rgico?',
                tooltip:'Indica si el paciente es al&eacute;rgico',
                dataIndex: 'alergy',
                //sortable:true,
                width:5,
                koImage:'images/custom/nothing.png',
                okImage:'images/custom/alerta.png'
            }),
            {
                header: 'Especialidad',
                dataIndex: 'nombreMedicamento',
                tooltip:'Medicamento a administrar',
                width:15,
                //sortable:true,
                hidden:true
            },
            {
                width:15,
                header: 'Principio Activo',
                dataIndex: 'principioActivo',
                tooltip:'Principio activo que se ha preescrito en el tratamiento',
                //sortable:true,
                hidden:true
            },new Ext.abada.ListColumn({
                width:10,
                header:'Frecuencia',
                tooltip:'Frecuencia de administraci&oacute;n del medicamento',
                dataIndex:'instructions'
            }),new Ext.abada.ListColumn({
                //hidden:true,
                width:15,
                header:'Observaciones m&eacute;dicas',
                //tooltip:'Observaciones para el enfermero a la hora de la administraci&oacute;n',
                dataIndex:'observations'
            }),
            {
                header: 'Especialidad-Principio Activo',
                dataIndex: 'especialidadPActivo',
                tooltip:'Datos del medicamento a administrar',
                width:15,
                //sortable:true,
                hidden:true
            },
            new Ext.contramed.IconColumn({
                width:3,
                grid:this,
                dataIndex:'chapuza',
                image:'images/custom/observaciones.png'
            })
            ]
        });
        this.colModel=colModelTemp;

                
        //creamos el store
        this.store=new Ext.data.GroupingStore({
            url:this.urlThreatment,
            total:'total',
            scope:this,
            autoDestroy: true,
            idProperty:'id',
            remoteSort: false,
            groupField:'especialidadPActivo',
            sortInfo: {
                field: 'giveTime',
                direction: 'ASC'
            },
            reader: new Ext.data.JsonReader({
                root:'data',
                fields:[
                {
                    name:'idOrderTiming',
                    mapping:'idOrderTiming'
                },
                {
                    name:'giveTime',
                    mapping:'giveTime'
                },
                {
                    name:'codigoNacionalMedicamento',
                    mapping:'codigoNacionalMedicamento'
                }
                ,
                {
                    name:'nombreMedicamento',
                    mapping:'nombreMedicamento'
                },
                {
                    name:'giveAmount',
                    mapping:'giveAmount'
                },
                {
                    name:'idMeasureUnit',
                    mapping:'idMeasureUnit'
                },
                {
                    name:'measureUnit',
                    mapping:'measureUnit'
                },
                {
                    name:'alergy',
                    mapping:'alergy'
                },
                {
                    name:'administrationType',
                    mapping:'administrationType'
                },
                {
                    name:'idPrincipioActivo',
                    mapping:'idPrincipioActivo'
                },
                {
                    name:'principioActivo',
                    mapping:'principioActivo'
                },
                {
                    name:'instructions',
                    mapping:'instructions'
                },
                {
                    name:'observations',
                    mapping:'observations'
                },
                {
                    name:'administrationStatus',
                    mapping:'threatmentInfoStatus.text'
                },
                {
                    name:'ifNecesary',
                    mapping:'ifNecesary'
                },
                {
                    name:'administrationStatusDifference',
                    mapping:'threatmentInfoStatus.difference'
                },
                {
                    name:'especialidadPActivo',
                    mapping:'especialidadPActivo'
                },
                {
                    name:'administrationStatusStatus',
                    mapping:'threatmentInfoStatus.status'
                },
                {
                    name:'chapuza',
                    mapping:'chapuza'
                }
                ]
            }),
            listeners:{
                load:function(store){
                    store.scope.paintRowColors(store.scope);
                    store.scope.onGroupCollapsed();
                    if (store.scope.isAllMedicationComplete())
                        store.scope.fireEvent('allMedicationComplete');
                }
            }
        });

        this.view= new Ext.grid.GroupingView({
            forceFit:true,
            startCollapsed : this.groupCollapsed,
            groupTextTpl: '<b  style=\"font-size:small;\">{group}</b>'//({[values.rs.length]} {[values.rs.length > 1 ? "Tomas" : "Toma"]})'
        });       

        this.addListener('rowclick',this.onRowListener,this);
        this.addListener('sortchange',this.paintRowColors,this);

        this.addEvents('allMedicationComplete');
    },
    onRowListener:function(grid,rowIndex){
        if (!this.App)
            this.App = new Ext.App({});
        var row=this.store.getAt(rowIndex);

        var text='';
        if (row.get('administrationType').toString()!=='')
            text+='V&iacute;a administraci&oacute;n: '+row.get('administrationType').toString()+'<BR/>';
        if (row.get('instructions').toString()!=='')
            text+='Frecuencia: '+row.get('instructions').toString()+'<BR/>';
        if (row.get('observations').toString()!=='')
            text+='Observaciones m&eacute;dicas: '+row.get('observations').toString();

        this.App.setAlert('',text);
    },
    setGroupCollapsed:function(value){
        if (value!=undefined)
            this.groupCollapsed=value;
    },
    onGroupCollapsed:function(value){
        if (this.groupCollapsed)
            this.view.collapseAllGroups();
        else
            this.view.expandAllGroups();
    },
    paintRowColors:function(grid){

        function changeCellTextColor(grid,index,color){
            for (i2=0;i2<grid.colModel.columns.length;i2++){
                grid.getView().getCell(index,i2).style.color = color;
            }
        }

        if (grid.store && grid.store.getCount()>0){
            for (i=0;i<grid.store.getCount();i++){
                if (grid.store.getAt(i).get('alergy').valueOf()){
                    changeCellTextColor(this,i,'Orange');
                }else{
                    if (grid.store.getAt(i).get('administrationStatusStatus').valueOf()=='NOT_COMPLETED'){
                            changeCellTextColor(this,i,'Red');
                    }else if (grid.store.getAt(i).get('administrationStatusStatus').valueOf()=='COMPLETED'){
                            changeCellTextColor(this,i,'Green');
                    }/*else if (grid.store.getAt(i).get('administrationStatusStatus').valueOf()=='NOT_GIVEN_NOT_COMPLETED'){
                        changeCellTextColor(this,i,'Pink');
                    }*/else if (grid.store.getAt(i).get('administrationStatusStatus').valueOf()=='NOT_GIVEN'){
                        changeCellTextColor(this,i,'Blue');
                    }
                }
            }
        }
    },
    isAllMedicationComplete:function(){
        if (this.store && this.store.getCount()>0){
            for (i=0;i<this.store.getCount();i++){
                if (this.store.getAt(i).get('administrationStatusStatus').valueOf()=='NOT_COMPLETED' ||
                    this.store.getAt(i).get('administrationStatusStatus').valueOf()=='NOT_GIVEN_NOT_COMPLETED')
                    return false;
            }
            return true;
        }
        return false;
    }
});

/***
 * Toolbar para entrada de numero de dosis
 */
Ext.contramed.DoseToolbar=Ext.extend(Ext.Toolbar,{
    addText:'Submit',
    applet:undefined,
    heightAux:Ext.ACCESIBLE_HEIGHT,
    initComponent : function(){
        this.items=[
        ];

        this.taObservation=new Ext.abada.TextAreaPanel({
            title:'Observaciones',
            width:300
        });
        this.items.push(this.taObservation);
        this.items.push('->');
        this.addButton=new Ext.contramed.ButtonMCAApplet({
            text:this.addText,
            functionName:'readBarCode',
            scope:this,
            tooltip:this.tooltipadd,
            applet:this.applet,
            //height:this.heightAux,
            scale:'large',
            iconCls:'barcode',
            nonStopRead:true,
            listeners:{
                read:function(data){
                    this.scope.onSubmitAdd(data);
                }
            }
        });
        this.items.push(this.addButton);
        if (this.removeText){
            this.items.push('-');
            this.removeButton=new Ext.contramed.ButtonMCAApplet({
                text:this.removeText,
                functionName:'readBarCode',
                scope:this,
                //height:this.heightAux,
                scale:'large',
                iconCls:'barcode',
                tooltip:this.tooltipremove,
                applet:this.applet,
                nonStopRead:true,
                listeners:{
                    read:function(data){
                        this.scope.onSubmitRemove(data);
                    }
                }
            });
            this.items.push(this.removeButton);
        }

        Ext.contramed.DoseToolbar.superclass.initComponent.call(this);
        this.addEvents('submitAdd','submitRemove');
    },
    onSubmitAdd:function(data){
        if (this.taObservation.isValid()){
            this.fireEvent('submitAdd',data, this.taObservation.getValue());
        }
        this.reset();
    },
    onSubmitRemove:function(data){
        if (this.taObservation.isValid())
            this.fireEvent('submitRemove',data,this.taObservation.getValue());
        this.reset();
    },
    reset:function(){
        this.taObservation.setValue('');
    },
    submitAdd:function(){
        this.addButton.fireEvent('click');
    },
    submitRemove:function(){
        if (this.removeButton)
            this.removeButton.fireEvent('click');
    }
});

Ext.contramed.DateRangePanel=Ext.extend(Ext.Panel,{
    layout:'form',
    initComponent : function(){
        this.startDateField=new Ext.abada.DateTimePanel({
            value:this.getTomorrow(),
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'selecione el inicio del rango de fechas para la b&uacute;squeda del tratamiento'
                    });
                }
            }
        });
        //this.startDateField.addListener('select',this.onSelectStarDateField,this);

        this.endDateField=new Ext.abada.DateTimePanel({
            value:this.getTomorrow(2),
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'selecione el final del rango de fechas para la b&uacute;squeda del tratamiento'
                    });
                }
            }
        });
        //this.endDateField.addListener('select',this.onSelectEndDateField,this);

        this.submitButton=new Ext.Button({
            scale:'large',
            hidden:this.hideSubmitButton,
            //text:'Buscar',
            iconCls:'lupa',
            scope:this,
            tooltip:'Aceptar rango de d&iacute;as.',
            handler:this.onSubmit
        });
        this.items=[
        this.startDateField,this.endDateField,this.submitButton
        ];        
       
        this.addEvents('submit');

        Ext.contramed.DateRangePanel.superclass.initComponent.call(this);
    },
    getTomorrow:function(addDays){
        var result=new Date();
        if (addDays)
            result.setDate(result.getDate()+addDays);
        else
            result.setDate(result.getDate()+1);
        result.setHours(15, 0, 0, 0);
        return result;
    },
    onSubmit:function(){
        if (this.startDateField.getValue().getTime()<=this.endDateField.getValue().getTime())
            this.fireEvent('submit',this.startDateField.getValue(),this.endDateField.getValue());
    }/*,
    onSelectStarDateField:function(cmp,value){
        if (this.endDateField.getValue()<value)
            this.endDateField.setValue(value);
    },
    onSelectEndDateField:function(cmp,value){
        if (this.startDateField.getValue()>value)
            this.startDateField.setValue(value);
    }*/
});

Ext.contramed.DateRangeFilterToolbar=Ext.extend(Ext.Toolbar,{
    hideSubmitButton:false,
    initComponent : function(){
        this.initDateLabel=new Ext.form.Label({
            text:Ext.util.Format.date(this.getTomorrow(),'d/m/Y H:i')
        });
        this.endDateLabel=new Ext.form.Label({
            text:Ext.util.Format.date(this.getTomorrow(2),'d/m/Y H:i')
        });

        this.submitButton=new Ext.Button({
            scale:'large',
            hidden:this.hideSubmitButton,
            iconCls:'refresh',
            scope:this,
            tooltip:'Busca el tratamiento que tiene asignado el paciente en esas fechas',
            handler:this.onSubmit
        });

        this.filterButton=new Ext.Button({
            scale:'large',
            //text:'Filtrar',
            iconCls:'calendario',
            tooltip:'Filtra los tratamientos mediante la introducci&oacute;n de un rango de d&iacute;as',
            scope:this,
            handler:function(){
                var win=new Ext.Window({
                    modal:true,
                    width:220,
                    resizable:false,
                    layout:'form',
                    items:[
                    new Ext.contramed.DateRangePanel({
                        scope:this,
                        listeners:{
                            submit:function(start,end){
                                this.scope.initDateLabel.setText(Ext.util.Format.date(start,'d/m/Y H:i'));
                                this.scope.endDateLabel.setText(Ext.util.Format.date(end,'d/m/Y  H:i'));
                                win.close();
                                this.scope.onSubmit();
                            }
                        }
                    })
                    ]
                });
                win.show();
            }
        });

        this.items=[
        this.initDateLabel,'-',this.endDateLabel,'-',this.filterButton,'-',this.submitButton
        ];

        this.addEvents('search');

        Ext.contramed.DateRangeFilterToolbar.superclass.initComponent.call(this);
    },
    getTomorrow:function(addDays){
        var result=new Date();
        if (addDays)
            result.setDate(result.getDate()+addDays);
        else
            result.setDate(result.getDate()+1);
        result.setHours(15, 0, 0, 0);
        return result;
    },
    onSubmit:function(){
        this.fireEvent('search',this.initDateLabel.text,this.endDateLabel.text);
    },
    setHideSubmitButton:function(value){
        this.submitButton.setVisible(!value);
        this.hideSubmitButton=value;
    }
});
/**
 * Toolbar que muestra la informacion de un paciente
 */
Ext.contramed.PatientInfoPanel=Ext.extend(Ext.Toolbar,{
    url:'',
    heightAux:Ext.ACCESIBLE_HEIGHT,
    initComponent : function(){

        this.patientInfo=new Ext.form.Label({
            text:'',
            style:{
                color:'#77AB44',
                fontWeight:'bold',
                fontSize:'large'
            }
        });

        this.items=[
        this.patientInfo
        ];

        Ext.contramed.PatientInfoPanel.superclass.initComponent.call(this);

        this.patientStore=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            fields:[
            {
                name:'id'
            },
            {
                name:'name'
            },
            {
                name:'surname1'
            },
            {
                name:'surname2'
            },
            {
                name:'bed'
            }]

        });
        this.patientStore.addListener('load',this.onLoad,this);
    },
    onLoad:function(store,data,opt){
        if (store.getCount()==1){
            this.patientInfo.setText(store.getAt(0).get('bed')+'-->'+store.getAt(0).get('name')+' '+store.getAt(0).get('surname1')+' '+store.getAt(0).get('surname2'));
        }
    },
    load:function(){
        this.patientStore.load();
    }
});

Ext.contramed.ButtonThreatmentToolbar=Ext.extend(Ext.Toolbar,{
    heightAux:Ext.ACCESIBLE_HEIGHT,
    applet:undefined,
    resumeMode:false,
    initComponent : function(){
        this.items=[];

        this.items.push(new Ext.Button({
            scale:'large',
            tooltip:'Vuelve al men&uacute; anterior',
            scope:this,
            iconCls:'atras',
            handler:this.onSubmitBack
        }));

        if (!this.resumeMode){
            this.items.push(' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ');
            this.items.push(new Ext.Button({
                scale:'large',
                tooltip:'Administra toda la medicaci&oacute;n',
                scope:this,
                iconCls:'nopautada',
                handler:this.onSubmitAcceptAll
            }));

            this.items.push('-');
            this.items.push(new Ext.Button({
                scale:'large',
                scope:this,
                iconCls:'observaciones3',
                tooltip:'Escribir Observaciones Libres',
                handler:this.onSubmiObservation
            }));
            this.items.push(' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ');
            this.rfidButton=new Ext.contramed.ButtonMCAApplet({
                functionName:'readRfid',
                tooltip:'Lee de la etiqueta y muestra el siguiente paciente',
                applet:this.applet,
                scope:this,
                iconCls:'siguiente_paciente',
                nonStopRead:false,
                scale:'large',
                listeners:{
                    read:function(data){
                        this.scope.fireEvent('submitNextPatient',data,this);
                    }
                }
            });
            this.items.push(this.rfidButton);
        }

        this.addEvents(['submitBack','submiObservation','submitNextPatient','submitAcceptAll']);

        Ext.contramed.ButtonThreatmentToolbar.superclass.initComponent.call(this);
    },
    onSubmitBack:function(){
        this.fireEvent('submitBack');
    },
    onSubmitAcceptAll:function(){
        this.fireEvent('submitAcceptAll');
    },
    onSubmiObservation:function(){
        this.fireEvent('submiObservation');
    },
    setResumeMode:function(value){
        this.resumeMode=value;
    },
    forceReadRfid:function(){
        if (this.rfidButton)
            this.rfidButton.fireEvent('click');
    }
});

Ext.contramed.RangePanel=Ext.extend(Ext.Panel,{
    layout:'form',
    witdh:150,
    url:undefined,
    initComponent : function(){
        this.startTimeField=new Ext.abada.TimeField({});
        //this.startTimeField.addListener('select',this.onSelectStarTimeField,this);

        this.endTimeField=new Ext.abada.TimeField({});
        //this.endTimeField.addListener('select',this.onSelectEndTimeField,this);

        this.dateField=new Ext.abada.DateField({
            scale:'large',
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'fecha en la cual se va administrar la dosis'
                    });
                }
            }
        });

        cbStore=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            scope:this,
            fields:[
            {
                name:'id'
            },
            {
                name:'value'
            }],
            listeners:{
                load:function(store){
                    store.scope.selectBetterTimingRange(store);
                }
            }
        });

        this.timingRangeCombo=new Ext.abada.ComboBox({
            store:cbStore,
            allowBlank:false,
            scope:this,
            scale:'large',
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Distintas tomas que hay a lo largo del dia'
                    });
                },
                select: function(combo,record,index){
                    aux2=record.get('value').split('(');
                    if (aux2.length==2){
                        aux2=aux2[1].split(')');
                        aux2=aux2[0].split('-');
                        this.scope.startTimeField.setValue(aux2[0].substring(0,5));
                        this.scope.endTimeField.setValue(aux2[1].substring(0,5));
                    }
                }
            }
        });

        cbStore.load();

        this.button=new Ext.Button({
            scope:this,
            scale:'large',
            iconCls:'lupa',
            tooltip:'A&ntilde;ade un nuevo rango de busqueda.',
            handler:this.onSubmit
        });
        this.items=[this.dateField,this.timingRangeCombo,this.startTimeField,this.endTimeField,this.button];
        if (this.textButton)
            this.button.setText(this.textButton);

        this.addEvents('submit');

        Ext.contramed.RangePanel.superclass.initComponent.call(this);
    },
    onSubmit:function(){
        this.fireEvent('submit',this.dateField.getValue(),this.startTimeField.getValue(),this.endTimeField.getValue());
    },
    onSelectStarTimeField:function(cmp,record,index){
        if (this.endTimeField.getValue()<record.get(record.fields.keys[0]))
            this.endTimeField.setValue(record.get(record.fields.keys[0]));
    },
    onSelectEndTimeField:function(cmp,record,index){
        if (this.startTimeField.getValue()>record.get(record.fields.keys[0]))
            this.startTimeField.setValue(record.get(record.fields.keys[0]));
    },
    selectBetterTimingRange:function(store){
        //cutrada para que se seleccione una toma automaticamente
        var now=new Date(0,0,0,0,0,0);
        aux=new Date();
        now.setHours(aux.getHours(), aux.getMinutes(), aux.getSeconds(), 0);

        function getTimeRangePriv(value){
            aux3=value.split(':');
            if (aux3.length==3){
                var  result=new Date(0,0,0,0,0,0);
                result.setHours(aux3[0].valueOf());
                result.setMinutes(aux3[1].valueOf());
                result.setSeconds(aux3[2].valueOf());
                return result;
            }
            return undefined;
        }

        salir=false;
        for (i=0;!salir && i<store.getCount();i++){
            aux=store.getAt(i).get('value');
            result=new Array();

            aux2=aux.split('(');
            if (aux2.length==2){
                aux2=aux2[1].split(')');
                aux2=aux2[0].split('-');
                result.push(getTimeRangePriv(aux2[0]));
                result.push(getTimeRangePriv(aux2[1]));
            }

            if (now.getTime()<=result[1].getTime() && now.getTime()>=result[0].getTime()){
                this.timingRangeCombo.setValue(store.getAt(i).get('id'));
                this.startTimeField.setValue(aux2[0].substring(0,5));
                this.endTimeField.setValue(aux2[1].substring(0,5));
                salir=true;
            }
        }
    }
});

Ext.contramed.GiveNursingFilterToolbar=Ext.extend(Ext.Toolbar,{
    url:undefined,
    hideSubmitButton:false,
    initComponent : function(){
        this.dateLabel=new Ext.form.Label({
            text:Ext.util.Format.date(new Date(),'d/m/Y')
        });
        this.rangeLabel=new Ext.form.Label({});

        cbStore=new Ext.data.JsonStore({
            url:this.url,
            root:'data',
            total:'total',
            autoDestroy: true,
            idProperty:'id',
            scope:this,
            fields:[
            {
                name:'id'
            },
            {
                name:'value'
            }],
            listeners:{
                load:function(store){
                    store.scope.selectBetterTimingRange(store);
                }
            }
        });

        this.filterButton=new Ext.Button({
            scale:'large',
            //text:'Filtrar',
            iconCls:'calendario',
            tooltip:'Filtra los tratamientos mediante la introducci&oacute;n de un rango de horas manualmente',
            scope:this,
            handler:function(){
                var win=new Ext.Window({
                    autoScroll:false,
                    closable:true,
                    modal:true,
                    width: 295,
                    layout:'form',
                    items:[
                    new Ext.contramed.RangePanel({
                        url:this.url,
                        scope:this,
                        listeners:{
                            submit:function(date,start,end){
                                this.scope.dateLabel.setText(Ext.util.Format.date(date,'d/m/Y'));
                                this.scope.rangeLabel.setText(start+'-'+end);
                                win.close();
                                this.scope.onSubmit();
                            }
                        }
                    })
                    ]
                });
                win.show();
            }
        });

        this.submitButton=new Ext.Button({
            scale:'large',
            iconCls:'refresh',
            hidden:this.hideSubmitButton,
            tooltip:'Busca el tratamiento que tiene asignado el paciente en esa fecha con una toma',
            scope:this,
            handler:this.onSubmit
        });

        this.items=[this.dateLabel,'-',this.rangeLabel,'-',this.filterButton,'-',this.submitButton];

        cbStore.load();

        this.addEvents('search');

        Ext.contramed.GiveNursingFilterToolbar.superclass.initComponent.call(this);
    },
    setHideSubmitButton:function(value){
        this.submitButton.setVisible(!value);
        this.hideSubmitButton=value;
    },
    selectBetterTimingRange:function(store){
        //cutrada para que se seleccione una toma automaticamente
        var now=new Date(0,0,0,0,0,0);
        aux=new Date();
        now.setHours(aux.getHours(), aux.getMinutes(), aux.getSeconds(), 0);

        function getTimeRangePriv(value){
            aux3=value.split(':');
            if (aux3.length==3){
                var  result=new Date(0,0,0,0,0,0);
                result.setHours(aux3[0].valueOf());
                result.setMinutes(aux3[1].valueOf());
                result.setSeconds(aux3[2].valueOf());
                return result;
            }
            return undefined;
        }

        salir=false;
        for (i=0;!salir && i<store.getCount();i++){
            aux=store.getAt(i).get('value');
            result=new Array();

            aux2=aux.split('(');
            if (aux2.length==2){
                aux2=aux2[1].split(')');
                aux2=aux2[0].split('-');
                result.push(getTimeRangePriv(aux2[0]));
                result.push(getTimeRangePriv(aux2[1]));
            }

            //alert(result[0]+"-->"+result[1]+"-->"+now);
            if (now.getTime()<=result[1].getTime() && now.getTime()>=result[0].getTime()){
                this.rangeLabel.setText(aux2[0].substring(0,5)+'-'+aux2[1].substring(0,5)/*store.getAt(i).get('id')*/);
                salir=true;
            }
        }

        this.onSubmit();
    },
    onSubmit:function(){
        if (!this.hideSubmitButton)
            this.fireEvent('search',this.rangeLabel.text,this.dateLabel.text);
    }
});

Ext.contramed.ObservationWindow=Ext.extend(Ext.Window,{
    layout:'form',
    width:400,
    autoHeight:true,
    closable:true,
    resizable:false,
    plain:true,
    border:false,
    modal:true,
    url:undefined,
    initComponent : function(){
        var win=this;
        var formPanel=new Ext.form.FormPanel({
            url : this.url,
            defaultType : 'textarea',
            monitorValid : true,
            width:387,
            autoHeight:true,
            items : [
            {
                fieldLabel : 'Observaci&oacute;n',
                name : 'observation',
                width:250,
                allowBlank : false,
                listeners: {
                    render: function(c) {
                        Ext.QuickTips.register({
                            target: c,
                            text: 'Introduzca el texto.'
                        });
                    }
                }
            },
            {
                name:'privtext',
                value:this.privText,
                hidden:true
            }
            ],
            buttons:[{
                iconCls:'observaciones3',
                formBind:true,
                scale:'large',
                handler:function(btn){
                    formPanel.getForm().submit({
                        method:'POST',
                        waitTitle:'Insertando',
                        waitMsg:'Insertando Observaci&oacute;n...',
                        failure:function(form,action){
                            Ext.Msg.alert('Error', action.result.errors.reason);
                        },
                        success:function(){
                            win.close();
                        }
                    });
                }
            }]
        });

        if (this.items)
            this.items.push(formPanel);
        else{
            this.items=[formPanel];
        }

        Ext.contramed.ObservationWindow.superclass.initComponent.call(this);
    }
});
