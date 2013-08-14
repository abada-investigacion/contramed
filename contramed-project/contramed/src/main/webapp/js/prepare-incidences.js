/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveIncidenceGrid = new Ext.contramed.IncidenceGrid({
        url:'prepareIncidencesData.htm'
    });
    var panelgrid = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Incidencias Preparaci&oacute;n de cajetin',
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

