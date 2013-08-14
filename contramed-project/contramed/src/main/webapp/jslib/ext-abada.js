/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.namespace('Ext');
Ext.BLANK_IMAGE_URL ="./jslib/resources/images/s.gif";

Ext.ACCESIBLE_HEIGHT = 30;

Ext.namespace('Ext.abada');

/***
 * Columna que concatena los valores de los nombres de las columnas que aparezca en dataIndex,
 * siendo dataIndex un array
 */
Ext.abada.AppendColumn = Ext.extend(Ext.grid.TemplateColumn,{
    constructor:function(cfg){
        Ext.apply(this, cfg);
        var tplTemplate=this.generateTemplate(this.dataIndex);
        this.tpl=new Ext.XTemplate(tplTemplate);
        Ext.abada.AppendColumn.superclass.constructor.call(this,cfg);
    },
    generateTemplate:function(array){
        var result='';
        for (i=0;i<array.length;i++){
            result+='<tpl if="'+array[i]+'" >{'+array[i]+'} </tpl>';
        }
        return result;
    }
});
/***
 *Columna para representar valores booleanos mediante una imagen
 *true=se muestra imagen que este en okImage
 *false= se muestra imagen que este en koImage
 *
 *salvo que se active el atributo inverse que cambia el orden de las imagenes
 */
Ext.abada.BooleanColumn = Ext.extend(Ext.grid.TemplateColumn,{
    inverse:false,
    okImage:'',
    koImage:'',
    constructor:function(cfg){
        Ext.apply(this, cfg);
        var tplTemplate;
        if (this.inverse){
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.koImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.okImage+'" alt="false" /></tpl>';
        }else{
            tplTemplate = '<tpl if="'+this.dataIndex+' == true" ><img src="'+this.okImage+'" alt="true"/></tpl>'+
            '<tpl if="'+this.dataIndex+' == false" ><img src="'+this.koImage+'" alt="false" /></tpl>';
        }
        this.tpl=new Ext.XTemplate(tplTemplate);
        Ext.abada.BooleanColumn.superclass.constructor.call(this,cfg);
    }
});

/**
 * Columna para representar los valores de una lista
 */
Ext.abada.ListColumn = Ext.extend(Ext.grid.TemplateColumn,{
    constructor:function(cfg){
        Ext.apply(this, cfg);
        tplTemplate = '<tpl if="'+this.dataIndex+'" >'+
        '<tpl for="'+this.dataIndex+'">'+
        '<p>{.}</p>'+
        '</tpl>'+
        '</tpl>';
        this.tpl=new Ext.XTemplate(tplTemplate);
        Ext.abada.ListColumn.superclass.constructor.call(this,cfg);
    }
});

Ext.abada.ToolTipColumn=Ext.extend(Ext.util.Observable,{
    columnsNumber:[],
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.ToolTipColumn.superclass.constructor.call(this,cfg);       
    },
    init:function(grid){
        this.grid=grid;
        this.grid.on('mouseover', this.onMouseOver,this);
    },
    onMouseOver:function(e,t){
        e.stopEvent();
        var row = e.getTarget('.x-grid3-row');
        var col = e.getTarget('.x-grid3-col');
        if(col)
        {
            if(this.contains(col.cellIndex))
            {
                data=this.grid.getView().getCell(row.rowIndex,col.cellIndex);
                if (data){
                    this.showMessage(data.textContent);
                }
            }
        }
    },
    contains:function(value){
        if (this.columnsNumber){
            for (i=0;i<this.columnsNumber.length;i++){
                if (value == this.columnsNumber[i])
                    return true;
            }
            return false;
        }
        return true;
    },
    showMessage:function(htmlData){
        if (this.notificationBox){
            this.notificationBox.close();
        }
        this.notificationBox=new Ext.abada.Notification({
            autoDestroy:true,
            hideDelay:1000
        });
        this.notificationBox.setMessage(htmlData);
        this.notificationBox.show(document);
    }
});

/***
 *Usado para llamadas Ajax y espera la respuesta en Json
 */
Ext.abada.Ajax =new Ext.data.Connection({
    autoAbort : false,
    serializeForm : function(form){
        return Ext.lib.Ajax.serializeForm(form);
    },
    requestJson: function(o){
        //Sacar mensaje de wait
        if (o.waitTitle || o.waitMsg){
            if (!o.waitTitle) o.waitTitle='Esperando...';
            if (!o.waitMsg) o.waitMsg='Esperando...';
            o.waitMessageBox=Ext.MessageBox.wait(o.waitMsg,o.waitTitle);
        }

        this.request({
            url: o.url,
            //Parametros
            params: o.params,
            success: function(result,request) {
                if (o.waitMessageBox)
                    o.waitMessageBox.hide();
                var requestJson=Ext.decode(result.responseText);
                if (requestJson.success){
                    if (requestJson.errors){
                        o.success(requestJson.errors);
                    }else{
                        o.success();
                    }
                }
                else{
                    if (o.failure){
                        if (requestJson.errors){
                            o.failure(requestJson.errors);
                        }
                        else{
                            o.failure('Error');
                        }
                    }
                }
            },
            failure:function(result,request){
                if (o.waitMessageBox)
                    o.waitMessageBox.hide();
                if (o.failure){
                    o.failure(result.responseText);
                }
            }
        }
        );
    }
});

/**
 * ComboBox
 */
Ext.abada.ComboBox = Ext.extend(Ext.form.ComboBox,{
    displayField:'value',
    valueField:'id',
    typeAhead: true,
    editable:false,
    mode: 'local',
    triggerAction: 'all',
    //anchor:'95%',
    selectOnFocus:true,
    constructor:function(cfg){
        Ext.apply(this, cfg);

        if (this.url){
            this.store=new Ext.data.JsonStore({
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
                    load:function(store,records, options){
                        store.scope.fireEvent('load',store.scope,records, options);
                    }
                }
            });
        }
        this.addEvents('load');
        Ext.abada.ComboBox.superclass.constructor.call(this,cfg);
    },
    load:function(){
        if (this.store)
            this.store.load();
    }
});
/***
 * ComboBox que se puede deseleccionar un valor
 */
Ext.abada.ComboBoxDeSelect = Ext.extend(Ext.form.ComboBox, {
    displayField:'value',
    valueField:'id',
    typeAhead: true,
    editable:false,
    mode: 'local',
    triggerAction: 'all',
    //anchor:'95%',
    selectOnFocus:true,
    noSelection:null,
    selectedValue:null,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.ComboBoxDeSelect.superclass.constructor.call(this,cfg);
    },
    initComponent : function(){
        this.store.on('load',function(){
            if(this.noSelection && this.store){
                var Record = Ext.data.Record.create([ // creates a subclass of Ext.data.Record
                {
                    name: 'id'
                },{
                    name: 'value'
                }]);
                var NewRecord = new Record({
                    id: '',
                    value: this.noSelection
                });
                if(!this.getStore().getById('')){
                    this.getStore().addSorted(NewRecord);
                }
            }
            if (this.selectedValue)
                this.setValue(this.selectedValue);
        },this);
    }
});

/**
 * DateField que se instancia el dia de hoy
 */
Ext.abada.DateField=Ext.extend(Ext.form.DateField,{
    format:'d/m/Y',
    hideLabel:false,
    editable:false,
    allowBlank:false,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.DateField.superclass.constructor.call(this,cfg);
        if (!this.value)
            this.setValue(new Date());
    }
});

/**
 * TimeField que se instancia la hora actual
 */
Ext.abada.TimeField=Ext.extend(Ext.form.TimeField,{
    format:'H:i',
    hideLabel:false,
    editable:false,
    allowBlank:false,
    increment: 60,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.TimeField.superclass.constructor.call(this,cfg);
        //pongo hora actual
        this.onLoadedStore(this.getStore());
    },
    onLoadedStore:function(store){
        data={            
            field1: '23:59'
        };
        record=new store.recordType(data);
        store.add(record);

        this.setValue(new Date());
    }
});

/**
 * Panel para seleccionar una fecha y hora 
 */
Ext.abada.DateTimePanel=Ext.extend(Ext.Panel,{
    allowBlank:true,
    preventMark:false,
    plain:true,
    layout:'hbox',
    initComponent:function(){
        this.tfDate=new Ext.abada.DateField({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.tfTime=new Ext.abada.TimeField({
            width:100,
            allowBlank:this.allowBlank,
            preventMark:this.preventMark
        });
        this.items=[this.tfDate,this.tfTime];

        if (this.value){
            this.tfDate.setValue(this.value);
            
            this.tfTime.setValue(Ext.util.Format.date(this.value, 'H:i'));
        }

        Ext.abada.DateTimePanel.superclass.initComponent.call(this);
    },
    getValue:function(){
        function parseValue(date,stringtime){
            aux=stringtime.split(':');
            if (aux.length==2){
                date.setHours(parseInt(aux[0]), parseInt(aux[1]), 0, 0);
                return date;
            }else{
                return undefined;
            }
        }

        if (this.isValid()){
            return parseValue(this.tfDate.getValue(),this.tfTime.getValue());
        }
    },
    isValid:function(preventMark){
        return this.tfDate.isValid(preventMark) && this.tfTime.isValid(preventMark);
    }
});

/**
 * TextAreaPanel, panel con un textarea en un interior
 */
Ext.abada.TextAreaPanel=Ext.extend(Ext.Panel,{
    collapsible:true,
    titleCollapse: true,
    plain:true,
    border:false,
    collapsed:true,
    allowBlank:true,
    preventMark:false,
    maxLength:1204,
    initComponent:function(){                
        this.taText=new Ext.form.TextArea({            
            allowBlank:this.allowBlank,
            preventMark:this.preventMark,
            width:this.width,
            maxLength:this.maxLength
        });
        this.items=[this.taText];

        Ext.abada.TextAreaPanel.superclass.initComponent.call(this);
    },
    getValue:function(){
        return this.taText.getValue();
    },
    setValue:function(text){
        this.taText.setValue(text);
    },
    isValid:function(preventMark){
        return this.taText.isValid(preventMark);
    }
});

/***
 *Usado para llamadas Ajax y espera la respuesta en Json
 *igual que la que esperan los JsonStore
 */
Ext.abada.AjaxData =new Ext.data.Connection({
    autoAbort : false,
    serializeForm : function(form){
        return Ext.lib.Ajax.serializeForm(form);
    },
    requestJson: function(o){
        this.request({
            url: o.url,
            //Parametros
            params: o.params,
            success: function(result,request) {
                var requestJson=Ext.decode(result.responseText);
                if (requestJson.total && requestJson.total>=0 && requestJson.data){
                    o.success(requestJson);
                }else{
                    o.failure('Error');
                }
            },
            failure:function(result,request){
                if (o.failure){
                    if (result.responseText){
                        o.failure(result.responseText);
                    }else if (result.statusText){
                        o.failure(result.statusText);
                    }else{
                        o.failure('');
                    }

                }
            }
        });
    }
});

/****
 * Expander que hace una llamada Ajax a una url, espera una respuesta
 * igual que la que espera un jsonstore
 */
Ext.abada.RowExpanderURL = Ext.extend(Ext.ux.grid.RowExpander, {
    url:'',
    searchFields:[],    
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.RowExpanderURL.superclass.constructor.call(this,cfg);       
    },
    expandRow : function(row){
        if(typeof row == 'number'){
            row = this.grid.view.getRow(row);
        }
        var record = this.grid.store.getAt(row.rowIndex);
        var body = Ext.DomQuery.selectNode('tr:nth(2) div.x-grid3-row-body', row);
        this.loadStoreData(record, body, row);
    },
    generateSearchParam:function(record){
        var result=[];
        for (i=0;i<this.searchFields.length;i++){
            result.push({
                value:record.get(this.searchFields[i]),
                key:this.searchFields[i]
            });
        }
        return result;
    },
    loadStoreData:function(record,body,row){
        if (!this.enableCaching){
            this.loadStoreDataInt(record,body,row);
        }
        else{
            if (!this.bodyContent[record.id])
                this.loadStoreDataInt(record,body,row);
            else
                this.onLoad(record,body,row);
        }
    },
    loadStoreDataInt:function(record,body,row){
        Ext.abada.AjaxData.requestJson({
            url:this.url,
            scope:this,
            params:{
                search:Ext.util.JSON.encode(this.generateSearchParam(record))
            },
            failure:function(){                
            },
            success:function(storeData){
                /*var aux={};
                aux.id=record.id;
                aux.data=storeData;
                this.scope.onLoad(aux, body, row);*/
                storeData.id=record.id;
                this.scope.onLoad(storeData, body, row);
            }
        });
    },
    onLoad:function(record,body,row){
        if(this.beforeExpand(record, body, row.rowIndex)){
            this.state[record.id] = true;
            Ext.fly(row).replaceClass('x-grid3-row-collapsed', 'x-grid3-row-expanded');
            this.fireEvent('expand', this, record, body, row.rowIndex);
        }
    }
});

Ext.abada.NotificationMgr = {
    positions: []
};

/**
 * Notification Window
 */
Ext.abada.Notification=Ext.extend(Ext.Window,{
    initComponent: function(){
        Ext.apply(this, {
            iconCls: this.iconCls || 'x-icon-information',
            cls: 'x-notification',
            //autoWidth: true,
            width:200,
            autoHeight: true,
            plain: false,
            draggable: false,
            shadow:false,
            bodyStyle: 'text-align:center'
        });
        this.label=new Ext.form.Label({});

        this.items=[this.label];

        if(this.autoDestroy) {
            this.task = new Ext.util.DelayedTask(this.hide, this);
        } else {
            this.closable = true;
        }
        Ext.abada.Notification.superclass.initComponent.apply(this);
    },
    setMessage: function(msg){
        //this.get('body').update(msg);
        this.label.setText(msg);
    },
    setTitle: function(title, iconCls){
        Ext.abada.Notification.superclass.setTitle.call(this, title, iconCls||this.iconCls);
    },
    onDestroy: function(){
        Ext.abada.NotificationMgr.positions.remove(this.pos);
        Ext.abada.Notification.superclass.onDestroy.call(this);
    },
    cancelHiding: function(){
        this.addClass('fixed');
        if(this.autoDestroy) {
            this.task.cancel();
        }
    },
    afterShow: function(){
        Ext.abada.Notification.superclass.afterShow.call(this);
        Ext.fly(this.body.dom).on('click', this.cancelHiding, this);
        if(this.autoDestroy) {
            this.task.delay(this.hideDelay || 5000);
        }
    },
    animShow: function(){
        this.pos = 0;
        while(Ext.abada.NotificationMgr.positions.indexOf(this.pos)>-1)
            this.pos++;
        Ext.abada.NotificationMgr.positions.push(this.pos);
        this.setSize(200,100);
        this.el.alignTo(document, "br-br", [ -20, -20-((this.getSize().height+10)*this.pos) ]);
        this.el.slideIn('b', {
            duration: 1,
            callback: this.afterShow,
            scope: this
        });
    },
    animHide: function(){
        this.el.ghost("b", {
            duration: 1,
            remove: false,
            callback : function () {
                Ext.abada.NotificationMgr.positions.remove(this.pos);
                this.destroy();
            }.createDelegate(this)

        });
    },
    focus: Ext.emptyFn
/*setHtml:function(html){
        this.html=html;
    }*/
});

Ext.abada.TabPanel=Ext.extend(Ext.TabPanel,{
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.abada.TabPanel.superclass.constructor.call(this,cfg);
    },
    getIndexActiveTab:function(){
        actTab=this.getActiveTab();
        for (i=0;i<this.items.length;i++){
            aux=this.items.get(i);
            if (actTab==aux){
                return i;
            }
        }
        return -1;
    }
});

Ext.namespace('Ext.abada.grid.ColumnModel.LocaleRenderer');
/**
 * Para simular locale en una columna
 */
Ext.abada.grid.ColumnModel.LocaleRenderer.defaultRenderer=function(value){
    if(typeof value == "string"){
        if (value.length < 1){
            return " ";
        }else{
            //intento localizar el valor para ello busco un array que debe existir
            //con los valores de localización
            try{
                result=Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale[value];
                if (result)
                    return result;
            }catch (e){
            }
        }
    }
    return value;
};
/**
 * Se debe sobreescribir en un javascript con las traducciones
 */
Ext.abada.grid.ColumnModel.LocaleRenderer.defaultLocale={};
