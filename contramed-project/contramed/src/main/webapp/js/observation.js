/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Ext.onReady(function() {
    
    var grid = new Ext.contramed.ObservacionesGrid({
        url:'observationGrid.htm'
    });

      
     grid.getStore().load({
        params:{
            start: 0,
            limit:  grid.getPageSize()
        }
    });

    var observationPanel = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title:'Observaciones',
        items :[grid]
         });

    setCentralPanel(observationPanel);
});
