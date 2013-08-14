<%--
  #%L
  Contramed
  %%
  Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as
  published by the Free Software Foundation, either version 3 of the 
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public 
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/gpl-3.0.html>.
  #L%
  --%>
<%-- 
    Document   : index
    Created on : 09-mar-2010, 10:26:08
    Author     : katsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<meta http-equiv="Refresh" content="0; URL=login.htm" />-->
        <script type="text/javascript">
            function fullView(){
                if (navigator.userAgent.indexOf('Chrom')>=0){
                    window.location='login.htm';
                }else{
                    window.open('login.htm','','fullscreen=yes,scrollbars=auto');
                }
            }
        </script>
    </head>
    <body onload="fullView();">
    </body>
</html>
