<html>

<head>
    <title>${title}</title>
</head>

<body>
    <center><h2>${tablename}</h2></center>

    <center>
        <#list printers as prt>
            <p>${prt.ip}</p>
        </#list>

    </center>

</body>
</html>