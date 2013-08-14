/**
 *is a number
 */
function isNumeric(sText)
{
    var ValidChars = "0123456789.";
    var IsNumber=true;
    var Char;

    for (i = 0; i < sText.length && IsNumber; i++)
    {
        Char = sText.charAt(i);
        if (ValidChars.indexOf(Char) == -1)
        {
            IsNumber = false;
        }
    }
    return IsNumber;
}

/**
 *quitar fecha hora minutos
 */
function quitarhoramin(fecha){
    var fechaa=fecha.toString();
    var fechalimpia='';
    for (var i = 0; i < fechaa.length; i++) {
        if (fechaa.substring(i, i+1) == " "){
            i=fechaa.length;
        }else {
            fechalimpia=fechalimpia+fechaa.substring(i, i+1);
        }
    }
    return fechalimpia;
}



/**
 * Set an Ext Component in the center panel
 * @param panelCentral
 * @return
 */
function setCentralPanel(panelCentral,autoDestroy){
    var aux=Ext.getCmp('container');
    if (aux){
        //aux.suspendEvents();
        if (autoDestroy){
            aux.removeAll(true);
        }
        else{
            aux.get(0).setVisible(false);
            aux.removeAll(false);
        }
        aux.add(panelCentral);
        aux.doLayout();
    //aux.resumeEvents();
    }
}

/**
 *
*Funcion inserta modifica success failere
 */
function submitInsertUpdate(form,grid,wind,waitTitle,waitMsg,ok){
    form.getForm().submit({
        method:'POST',
        waitTitle:waitTitle,
        waitMsg:waitMsg,
        failure:function(form,action){
            Ext.Msg.alert('Error',action.result.errors.reason);
        },
        success:function(){
            Ext.Msg.alert(ok);
            wind.close();
            grid.getStore().reload();
        }
    });
}

/**
 * llamada Ajax borrado de una selecion de un grid
 */
function handledelete(grid,title,opt1,opt2,opt3,url){
    var opcion1='',opcion2='',opcion3='';
    if(opt1!=='')
        opcion1=grid.sm.getSelected().get(opt1);
    if(opt2!=='')
        opcion2=grid.sm.getSelected().get(opt2);
    if(opt3!=='')
        opcion3=grid.sm.getSelected().get(opt3);

    Ext.MessageBox.confirm('Borrar '+title, ' Estas seguro de Borrar '+opcion2+' '+opcion3,
        function (btn) {
            if (btn == "yes") {
                Ext.abada.Ajax.requestJson({
                    url: url,
                    //Parametros
                    params: {
                        id:opcion1
                    },
                    success: function() {
                        Ext.Msg.alert('Borrar',opcion2+' '+opcion3+' borrado correctamente');
                        grid.getStore().reload();
                    },
                    failure:function(error){
                        Ext.Msg.alert('Error',error.reason);
                    }
                });
            }
        });
}
