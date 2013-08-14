<html>
<head>
    <#include "/pages/head_initial.ftl" parse=true />
    <script type="text/javascript">
        <#include "/js/menu.ftl" parse=true />
    </script>
        <#if js??>
            <#list js as j>
                <script src="js/${j}" type="text/javascript">
                </script>
            </#list>
        </#if>
</head>
<body>
</body>
</html>