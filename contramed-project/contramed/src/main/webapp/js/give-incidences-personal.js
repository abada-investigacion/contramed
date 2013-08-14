/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveIncidenceGrid = new Ext.contramed.IncidenceGrid({
        url:'givesIncidencesDataByStaff.htm',
        hideStaff:true
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
    title : 'Incidencias Administraci&oacute;n de Medicaci&oacute;n Personal',
    items : [giveIncidenceGrid]
});
setCentralPanel(panelgrid);
    });

