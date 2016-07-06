<html>

<head>
    <title>${title}</title>
</head>

<body>
    <center><h2>${tablename}</h2></center>
    <center>

    <#list printers.entrySet() as entry>
            <caption>${entry.key}</caption>
        <table border="1">
                <#list entry.value.entrySet() as param>
                    <tr><td>${param.key}<td>${param.value}
                </#list>
        </table>
    </#list>

    </center>

</body>
</html>