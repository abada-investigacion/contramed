/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveIncidenceGrid = new Ext.contramed.IncidenceGrid({
        url:'givesIncidencesData.htm'
    });

    giveIncidenceGrid.getStore().load({
        params:{
            start: 0,
            limit: giveIncidenceGrid.getPageSize()
        }
    });
var panelgrid = new Ext.Panel( {
    frame : true,
    autoWidth:true,
    autoHeight:true,
    title : 'Incidencias Administraci&oacute;n de la Medicaci&oacute;n',
    items : [giveIncidenceGrid]
});
setCentralPanel(panelgrid);
    });

