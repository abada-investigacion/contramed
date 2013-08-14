/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.onReady(function() {
    var giveHistoricGrid = new Ext.contramed.GiveHistoricGrid({
        url:'givesHistoricPrecisaEnfer.htm',
        hideprecisa:true
    });
    var panelgrid = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Historial Administracion Medicacion S/P',
        items : [giveHistoricGrid]
    });
    giveHistoricGrid.getStore().load({
        params:{
            start: 0,
            limit: giveHistoricGrid.getPageSize()
        }
    });

setCentralPanel(panelgrid);
    });



