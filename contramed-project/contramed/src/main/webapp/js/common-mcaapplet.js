/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.namespace('Ext.contramed');

/***
 * Componente creado para insertar especificamente el applet MCAApplet
 */
Ext.contramed.MCAApplet=Ext.extend(Ext.Component,{
    archiveApplet:'MCAApplet.jar',
    codeApplet:'com.abada.mcapplet.MCAApplet2.class',
    //codeApplet:'com.abada.mcapplet.MCAApplet.class',
    timeout:5000,
    period:1500,
    delay:0,
    constructor:function(cfg){
        Ext.apply(this,cfg);
        Ext.contramed.MCAApplet.superclass.constructor.call(this,cfg);
    },
    onRender:function(ct, position){
        var tplTemplate='<applet ';
        //if (this.nameApplet)
        tplTemplate+='name=\"'+this.getName()+'\" ';
        tplTemplate+='style=\"';
        if (this.width)
            tplTemplate+='width:'+this.width+'px; ';
        else
            tplTemplate+='width:0px; ';
        if (this.height)
            tplTemplate+='height:'+this.height+'px; ';
        else
            tplTemplate+='height:0px; ';
        tplTemplate+='\" ';
        if (this.archiveApplet)
            tplTemplate+='archive=\"'+this.archiveApplet+'\" ';
        if (this.codeApplet)
            tplTemplate+='code=\"'+this.codeApplet+'\" ';
        tplTemplate+='>';

        tplTemplate+='<param name="timeout" value="'+this.timeout+'" />';
        tplTemplate+='<param name="period" value="'+this.period+'" />';
        tplTemplate+='<param name="delay" value="'+this.delay+'" />';

        tplTemplate+='</applet>';

        this.tpl=new Ext.XTemplate(tplTemplate);

        if(position){
            this.tpl.insertBefore(position, undefined, true);
        }else{
            this.tpl.append(ct, undefined, true);
        }

        this.insertCallBackScript();

        Ext.contramed.MCAApplet.superclass.onRender.call(this,ct,position);
    },
    getName:function(){
        return this.id;
    },
    getCallBackFunctionName:function(){
        return this.getName().toString().replace('-','').replace('-','')+"CallBack";
    },
    getCallBackStatusFunctionName:function(){
        return this.getName().toString().replace('-','').replace('-','')+"CallBackStatus";
    },
    insertCallBackScript:function(){        
        var headID=document.getElementsByTagName('head')[0];
        if (headID){
            //script callback de datos
            var newScript=document.createElement('script');
            newScript.type='text/javascript';
            newScript.text='function '+this.getCallBackFunctionName()+'(data,target){Ext.getCmp(target).onCallBackRead(data);}';
            //script callback de estado
            var newScript2=document.createElement('script');
            newScript2.type='text/javascript';
            newScript2.text='function '+this.getCallBackStatusFunctionName()+'(status,target){Ext.getCmp(target).onCallBackStatus(status);}';

            var scriptEl=Ext.get(newScript);
            var script2El=Ext.get(newScript2);
            var headIDExt=Ext.get(headID);

            var append=true;
            if (headIDExt.contains(scriptEl)){
                append=false;
            }
            if (append){
                headIDExt.appendChild(scriptEl);
                headIDExt.appendChild(script2El);
            }
        }
    }
});

/**
 * Botton creado especificamente para llamar al applet MCAApplet y recibir
 * la lectura de rfid,barcode o pcsc mediante el evento 'read'
 */
Ext.contramed.ButtonMCAApplet=Ext.extend(Ext.Button,{
    applet:undefined,
    functionName:undefined,
    //height:Ext.ACCESIBLE_HEIGHT,
    nonStopRead:false,
    scale:'large',
    style:{
        border:'0 none transparent'
    },
    initComponent:function(){
        Ext.contramed.ButtonMCAApplet.superclass.initComponent.call(this);

        this.addListener('click',this.onClickPriv);
        this.addEvents('read');        
    },
    onClickPriv:function(evt){
        var aux=document.applets[this.applet.getName()];
        if (aux){
            try{
                aux.execute(this.functionName,this.applet.getCallBackFunctionName(),this.applet.getCallBackStatusFunctionName(),this.id,this.nonStopRead);
            }catch(e){
                //alert(e);
            }
        }
    },
    onRender:function(ct,position){
        Ext.contramed.ButtonMCAApplet.superclass.onRender.call(this,ct,position);
        Ext.get(this.id).fadeOut({remove:false,useDisplay:true,endOpacity:0.5,duration: 2});
    },
    onCallBackRead:function(data){
        this.fireEvent('read',data);
    },
    forceRead:function(){
        this.fireEvent('click');
    },
    onCallBackStatus:function(status){
        if (status && status=='RUNNING'){
            Ext.get(this.id).fadeIn({remove:false,useDisplay:true,endOpacity:1,duration: 2});
            /*Ext.get(this.id).applyStyles({border: 'medium double Red'});
            Ext.get(this.id).repaint();*/
        }else if (status && status=='STOPED'){
            Ext.get(this.id).fadeOut({remove:false,useDisplay:true,endOpacity:0.5,duration: 2});
            /*Ext.get(this.id).applyStyles({border: '0 none transparent'});
            Ext.get(this.id).repaint();*/
        }
    }
});
