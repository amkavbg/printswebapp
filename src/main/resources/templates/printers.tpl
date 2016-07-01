<html>
<head>
    <title>${title}</title>
</head>
<body>
<center><h2>${tablename}</h2></center>

<center>
<table border="1">
    <#if printers??>
        <#list printers as prt>
            <tr><td>
                ${prt[prop]}
            </td>
        </#list>
    </#if>
</table>
</center>

</body>
</html>