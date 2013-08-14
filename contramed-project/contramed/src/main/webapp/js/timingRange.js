

Ext.onReady(function(){



    /**
     *Grid donde se muestra la lista de RecursoTag
     **/
    var grid = new Ext.contramedadmin.timingRangeGrid({
        url:'gridTimingRange.htm'
    });

    grid.getStore().load({
        params:{
            start:0,
            limit: 13
        }
    });
    /*Toolbar insert modificacion borrado*/
    var timingRangePanel = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title:'Gesti&oacute;n de Tomas',
        items :[new Ext.contramedadmin.insertUpdateDeleteToolbar({
            listeners:{
                submitInsert:function(){
                    handleInsertFormularios('Inserta',grid,' Toma ','insertTimingRange.htm');
                },
                submitUpdate:function(){
                    if(grid.sm.hasSelection()){
                        if(grid.sm.getCount()==1){
                            handleUpdateFormulario('Modifica',grid,' Toma ','updateTimingRange.htm');
                        }else{
                            Ext.Msg.alert('','Seleccione una &uacute;nica toma');
                        }
                    }else Ext.Msg.alert('','Seleccione una toma ');
                },
                submitDelete:function(){
                    if(grid.sm.hasSelection()){
                        handleDeletes(getSelectedIds(grid,'idtimingRange'),grid);
                    }else Ext.Msg.alert('','Seleccione una toma');
                }
            }

        }),grid ]

    });

    Ext.getCmp('insertar').setTooltip('Abre el panel para insertar una nueva toma');
    Ext.getCmp('modificar').setTooltip('Abre el panel para modificar tomas');
    Ext.getCmp('borrar').setTooltip('Borrar una toma');

    setCentralPanel(timingRangePanel);

});

/*De una fecha obtenemos su hora y minuto*/
function hourDate(value){
    var dt = new Date(value);
    return dt.format('H:i:s');
}


/**
 *Funcion modifica una toma
 *
 */

function handleUpdateFormulario(opt,grid,title,url){
    var cbstartTime=    new Ext.form.TimeField({
        emptyText: 'Seleccione una hora',
        width:150,
        fieldLabel: 'Inicio',
        name: 'startTime',
        allowBlank:false,
        format:'H:i',
        increment: 60,
        id:'start',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Hora de comienzo'
                });
            }
        }
    });
    var cbendTime=new Ext.form.TimeField({
        emptyText: 'Seleccione una hora',
        width:150,
        fieldLabel: 'Fin',
        name: 'endTime',
        format:'H:i',
        allowBlank:false,
        increment: 60,
        id:'end',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Hora de fin'
                });
            }
        }
    });
    var id,start,end,name;
    if(grid.sm.hasSelection()){
        id=grid.sm.getSelected().get('idtimingRange');
        start=hourDate(grid.sm.getSelected().get('startTime'));
        end=hourDate(grid.sm.getSelected().get('endTime'));
        name=grid.sm.getSelected().get('name');
    }
 
    var formpanel = new Ext.form.FormPanel( {
        title : opt+'r '+title,
        url:url,
        monitorValid: true,
        frame: true,
        width:530,
        items: [
        {
            layout:'column',
            border:false,
            items:[{
                xtype: 'textfield',
                layout: 'form',
                name : 'idtimingRange',
                hidden:true,
                value: id
            },
            {
                columnWidth:1,
                layout: 'form',
                border:false,
                items: [{
                    fieldLabel : 'Toma',
                    xtype: 'textfield',
                    blankText: "El campo es obligatorio",
                    width:100,
                    allowBlank: false,
                    name : 'name',
                    value: name,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Nombre de la toma'
                            });
                        }
                    }
                }]
            },
            {
                columnWidth:0.35,
                layout: 'form',
                border:false,
                items: [{
                    fieldLabel:'Toma anterior',
                    xtype: 'textfield',
                    width:50,
                    name:'inicio anterior',
                    allowBlank:false,
                    readOnly:true,
                    value:start,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Hora de comienzo anterior'
                            });
                        },
                        specialkey:function(field,e){
                            if (Ext.isIE)
                                e.stopEvent();
                        }
                    }
                }]
            },
            {
                columnWidth:0.65,
                layout: 'form',
                border:false,
                items: [cbstartTime]
            },
            {
                columnWidth:0.35,
                layout: 'form',
                border:false,
                items: [{
                    fieldLabel:'Fin Toma anterior',
                    xtype: 'textfield',
                    width:50,
                    name:'fin anterior',
                    readOnly:true,
                    allowBlank:false,
                    editable:false,
                    value:end,
                    listeners: {
                        render: function(c) {
                            Ext.QuickTips.register({
                                target: c,
                                text: 'Hora de fin anterior'
                            });
                        },
                        specialkey:function(field,e){
                            if (Ext.isIE)
                                e.stopEvent();
                        }
                    }
                }]
            }, {
                columnWidth:0.65,
                layout: 'form',
                border:false,
                items: [cbendTime]
            }
            ]

        }],
        buttons:[{
            text:opt+'r',
            tooltip:opt+'r toma',
            formBind:true,
            handler:function(){
                submitInsertUpdate(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do');
            }
        }]
    });

    var wind=new Ext.Window({
        width:530,
        autoScroll:false,
        closable:true,
        modal:true,
        layout: 'table',
        items:[formpanel]
    });

    wind.show();

}

/**
* funcion que inserta un timing range
 */

function  handleInsertFormularios(opt,grid,title,url){
    var cbstartTime=    new Ext.form.TimeField({
        emptyText: 'Seleccione una hora',
        width:150,
        fieldLabel: 'Inicio',
        name: 'startTime',
        allowBlank:false,
        format:'H:i',
        increment: 60,
        id:'start',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Hora de comienzo'
                });
            }
        }
    });
    var cbendTime=new Ext.form.TimeField({
        emptyText: 'Seleccione una hora',
        width:150,
        fieldLabel: 'Fin',
        name: 'endTime',
        format:'H:i',
        allowBlank:false,
        increment: 60,
        id:'end',
        listeners: {
            render: function(c) {
                Ext.QuickTips.register({
                    target: c,
                    text: 'Hora de fin'
                });
            }
        }
    });
    var formpanel = new Ext.form.FormPanel( {
        title : opt+'r '+title,
        url:url,
        monitorValid: true,
        frame: true,
        width:280,
        items: [
        {
            fieldLabel : 'Toma',
            xtype: 'textfield',
            width:100,
            allowBlank: false,
            blankText: "El campo es obligatorio",
            name : 'name',
            listeners: {
                render: function(c) {
                    Ext.QuickTips.register({
                        target: c,
                        text: 'Nombre de la toma'
                    });
                }
            }
        }
        ,cbstartTime,cbendTime
        ],
        buttons:[{
            text:opt+'r',
            tooltip:opt+'r toma',
            formBind:true,
            handler:function(){
                submitInsertUpdate(formpanel,grid,wind,opt+'ndo',opt+'ndo '+title+'...',opt+'do');
            }
        }]
    });

    var wind=new Ext.Window({
        width:280,
        autoScroll:false,
        closable:true,
        modal:true,
        layout: 'table',
        items:[formpanel]
    });

    wind.show();

}

//Returns a string in the form id,id,id...id from selected rows in grid
function getSelectedIds(grid,columnName){
    var selectedRows = grid.sm.getSelections();
    var rows = [];
    var i;
    for(i=0;i<selectedRows.length;i++){
        rows.push(selectedRows[i].get(columnName));
    }
    //return rows.join(',');
    return rows;
}
//Delete TimingRange
function handleDeletes(selection,grid) {
    Ext.MessageBox.confirm('Borrar toma', ' Estas seguro de Borrar ',
        function (btn) {
            if (btn == "yes") {
                Ext.abada.Ajax.requestJson({
                    url: 'removeTimingRange.htm',
                    //Parametros
                    params: {
                        timingRange : Ext.util.JSON.encode(selection)
                    },
                    success: function() {
                        Ext.Msg.alert('','Borrado con &eacute;xito');
                        grid.getStore().reload();
                    },
                    failure:function(response){
                        Ext.Msg.alert('Error',response.reason);

                    }
                });
            }
        });
}




 
 