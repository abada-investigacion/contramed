/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveHistoricGrid = new Ext.contramed.PrepareHistoricGrid({
        url:'prepareHistoricData.htm'
    });
    var panelgrid = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Historial Preparaci&oacute;n de Cajetin',
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

