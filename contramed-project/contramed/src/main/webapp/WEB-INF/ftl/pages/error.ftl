<html>
    <head>
        <#include "/pages/head_initial.ftl" parse=true />

	<script type="text/javascript">
            Ext.onReady(function(){
                Ext.MessageBox.show({
                    title: 'Error',
                    msg: '${error}',
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR,
                    fn:function(){
                        window.location='login.htm';
                    }
                });
            });
        </script>		
    </head>
    <body>
    </body>
</html>