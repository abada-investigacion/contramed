/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

if(Ext.ux.grid.filter.DateFilter){
  Ext.apply(Ext.ux.grid.filter.DateFilter.prototype, {
    afterText : "Despues",
    beforeText:"Antes",
    onText:"Igual"

  });
}
if(Ext.ux.grid.GridFilters){
  Ext.apply(Ext.ux.grid.GridFilters.prototype, {
    menuFilterText : 'Filtros'
  });
}

if(Ext.ux.grid.filter.DecimalFilter){
  Ext.apply(Ext.ux.grid.filter.DecimalFilter.prototype, {
    menuItemCfgs : {
        emptyText: 'Introduzca texto...'
       }
  });
}
if(Ext.ux.grid.filter.NumericFilter){
  Ext.apply(Ext.ux.grid.filter.NumericFilter.prototype, {
    menuItemCfgs : {
        emptyText: 'Introduzca texto...'
     }
  });
}
