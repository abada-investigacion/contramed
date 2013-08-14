/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveIncidenceGrid = new Ext.contramed.IncidenceGrid({
        url:'prepareIncidencesDataByStaff.htm',
        hideStaff:true
    });
    var panelgrid = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Incidencia Personal Preparaci&oacute;n de Cajetin',
        items : [giveIncidenceGrid]
    });
    giveIncidenceGrid.getStore().load({
        params:{
            start: 0,
            limit: giveIncidenceGrid.getPageSize()
        }
    });

    setCentralPanel(panelgrid);
});

