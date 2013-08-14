/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.namespace('Ext.contramed');

Ext.contramed.BedDataView=Ext.extend(Ext.DataView,{
    urlStore:'',
    autoScroll: true,
    //height: 305,
    autoWidth: true,
    multiSelect: false,
    singleSelect:true,
    kidage:null,
    teenage:null,
    overClass: 'x-view-over',
    itemSelector: 'div.thumb-wrap',
    selectedClass:'x-view-selected',
    emptyText: 'No hay camas para mostrar',
    style: 'border:0px solid #99BBE8; border-top-width: 0',
    listeners: {
        click:function(view,index){
            var selNode=this.store.getAt(index);
            if(selNode){
                this.onClickBedEvent(selNode.get('idBed'));
            }
        }

    },
    constructor:function(cfg){

        this.store=new Ext.data.JsonStore({
            url:cfg.urlStore,
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
                name:'idBed'
            },
            {
                name:'namePatient'
            },
            {
                name:'surname1'
            },
            {
                name:'surname2'
            },
            {
                name:'nameBed'
            },
            {
                name:'genre'
            },
            {
                name:'age'
            },
            {
                name:'inbed'
            }
            ]
        });

       
        this.tpl=new Ext.XTemplate(
            '<tpl for=".">',
            '<div style={[this.getStyle()]} class="thumb-wrap" id="{idbed}">',
            '<div class="thumb" align="center"><img src={[this.getImage(values.genre,values.age,values.inbed)]} title="{[this.getFullNamePatient(values.namePatient,values.surname1,values.surname2)]}"></div>',
            '<p align="center" class="x-editable">{[this.getName(values.namePatient,values.inbed)]}</p><p align="center" class="x-editable">{[this.getNameBed(values.nameBed)]}</p><p align="center" class="x-editable">{[this.getAge(values.age,values.inbed)]}</p></div>',
            '</tpl>',
            '<div class="x-clear"></div>',
            {
                getImage :function(genre, age, inbed){
                    
                    switch(genre){
                        case 1:
                            return this.setImage(age,"woman","kidwoman","teenwoman","oldwoman");
                            break;
                        case 2:
                            return this.setImage(age,"man","kidman","teenman","oldman");
                            break;
                        default:
                            if(inbed){
                                return this.setImage(age,"nogenre","nogenre_kid","nogenre_teen","nogenre_old");
                            }else{
                                return "js/resources/images/bed_icon.jpeg";
                            }
                            break;
                    }
                                       
                }
            },{

                getNameBed:function(nameBed){
                    return nameBed;
                }
            },{

                getFullNamePatient:function(namePatient,surname1,surname2){
                    
                    if(namePatient!==''){
                        return namePatient+' '+surname1+' '+surname2;
                    }else{
                        return "Nombre no disponible";
                    }
                }
            },{

                getName:function(namePatient, inbed){
                    if(inbed){
                        if(namePatient!==''){
                            var aux1;
                            if(namePatient.length>16){
                                aux1 = namePatient.substring(0,15);                                
                                return aux1;
                            }else{
                                return namePatient;
                            }
                        }else{
                            return "";
                        }
                    }else{
                        return "";
                    }
                }
            },{
                getAge:function(age,inbed){
                    if(inbed){
                        if(age>=0){
                            return age+" a&ntilde;os";
                        }else{
                            return "";
                        }
                    }else{
                        return 'Vacia';
                    }
                }
            },
            {
                getStyle:function(){
                    if(Ext.isIE8){
                        return "width:90px;height:90px";
                    }else if(Ext.isIE7){
                        return "width:90px;height:90px";
                    }else{
                        return "width:90px;height:90px";
                    }
                }
            },{
                setImage:function(age,genre,kid,teen,old){
                    if(age==-1){
                        return "js/resources/images/bed_icon_"+genre+".jpeg";
                    }else{
                        if(age<=cfg.kidage){
                            return "js/resources/images/bed_icon_"+kid+".jpeg";
                        }else if(age>cfg.kidage && age<=cfg.teenage){
                            return "js/resources/images/bed_icon_"+teen+".jpeg";
                        }else{
                            return "js/resources/images/bed_icon_"+old+".jpeg";
                        }
                    }

                }

            }
            );        

        Ext.apply(this, cfg);
        Ext.contramed.BedDataView.superclass.constructor.call(this,cfg);

        this.addEvents('clickOnBed');
    },
    onClickBedEvent:function(bed){
        this.fireEvent('clickOnBed',bed);
    }
});


/**
* Toolbar que permite la introduccion de un rango de horas
*/
Ext.contramed.ComboBoxRecurso=Ext.extend(Ext.abada.ComboBox,{
    rootPath:'/',
    url:undefined,
    selectOnFocus:false,
    editable: false,
    forceSelection: false,
    constructor:function(cfg){
        Ext.apply(this, cfg);
        Ext.contramed.ComboBoxRecurso.superclass.constructor.call(this,cfg);

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
                load:function(store){
                    if (store.scope.getValue())
                        store.scope.fireEvent('selectPath',store.scope.rootPath);
                }
            }
        });

        this.store.addListener('load',this.onLoadStore,this);
        this.addListener('select',this.onSelectRootPath,this);

        this.addEvents('selectPath');
    },
    onSelectRootPath:function(cb,record,index){
        if (this.getValue()=='back'){
            if (this.rootPath.indexOf('/')===0){
                this.rootPath='/';
            }else{
                i=this.rootPath.lastIndexOf('/');
                this.rootPath=this.rootPath.substring(0,i);
            }
        }
        this.load();
    },
    load:function(){
        if (this.getValue() && this.getValue()!=='' && this.getValue()!='back')
            this.rootPath=this.getValue();
        if ((!this.rootPath)||(this.rootPath && this.rootPath.indexOf('/')===0)){
            this.getStore().load({
                params:{
                    path:this.rootPath
                }
            });
        }
    },
    onLoadStore:function(st,records,opt){
        data={
            id: 'back',
            value: 'Atras'
        };
        record=new st.recordType(data,data.id);
        //records.unshift(record);
        st.sort('value','ASC');
        st.insert(0,record);
    },
    setPath:function(path){
        this.rootPath=path;
        this.load();
    }
});

Ext.contramed.BedMap=Ext.extend(Ext.Panel,{
    urlBeds:undefined,
    urlLocalizador:undefined,
    layout:'anchor',
    initPath:undefined,
    initComponent : function(){        
        this.bedmaps=new Ext.contramed.BedDataView({
            urlStore:this.urlBeds,
            kidage:16,
            teenage:75,
            scope:this,
            anchor:'100%',
            listeners: {
                clickOnBed:function(bed){
                    this.scope.onSelectBed(bed);
                }
            }
        });

        this.combo =new Ext.abada.ComboBox({
            url:this.urlLocalizador,
            width:400,
            scope:this,
            fieldLabel:'Localizacion de la Cama',
            allowBlank: false,
            blankText: "El campo es obligatorio",
            emptyText: 'Seleccione Localizacion',
            anchor:'right 100%',
            listeners:{
                select:function(cb,record,index){
                    var path=record.get('id');
                    this.scope.fireEvent('selectPath',path);
                    this.scope.bedmaps.getStore().load({
                        params:{
                            path:path
                        }
                    });
                },
                load:function(cb,records,options){
                    if (cb.scope.initPath){
                        cb.setValue(cb.scope.initPath);
                        cb.scope.fireEvent('selectPath',cb.scope.initPath);
                        cb.scope.bedmaps.getStore().load({
                            params:{
                                path:cb.scope.initPath
                            }
                        });
                    }
                }
            }
        });
        /*this.combo=new Ext.contramed.ComboBoxRecurso({
            url:this.urlLocalizador,
            width:400,
            scope:this,
            fieldLabel:'Localizacion de la Cama',
            allowBlank: false,
            blankText: "El campo es obligatorio",
            emptyText: 'Seleccione Localizacion',
            anchor:'right 100%',
            listeners:{
                selectPath:function(path){
                    this.scope.fireEvent('selectPath',path);
                    this.scope.bedmaps.getStore().load({
                        params:{
                            path:path
                        }
                    });
                }
            }
        });*/
        this.items=[this.combo,this.bedmaps];

        Ext.contramed.BedMap.superclass.initComponent.call(this);

        this.addEvents('selectBed','selectPath');
    },
    onSelectBed:function(bed){
        this.fireEvent('selectBed',bed);
    },
    load:function(){
        this.combo.load();
    }
});