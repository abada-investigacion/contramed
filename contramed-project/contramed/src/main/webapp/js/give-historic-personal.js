/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.onReady(function() {
    var giveHistoricGrid = new Ext.contramed.GiveHistoricGrid({
        url:'givesHistoricDataByStaff.htm',
        hideStaff:true
    });

    var panelgrid = new Ext.Panel( {
        frame : true,
        autoWidth:true,
        autoHeight:true,
        title : 'Historial Administraci&oacute;n de Medicaci&oacute;n Personal',
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

