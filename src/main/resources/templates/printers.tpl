<!DOCTYPE HTML>
<html>
<head>
    <title>${title}</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" type="text/css"  href="${context}/assets/css/main.css"/>
</head>
<body>

<!-- Header -->
<header id="header">
    <div class="inner">
        <div class="content">
            <h1>[Printus]</h1>

            <h2>A fully app to control the resources of<br/>
                the central enterprise printers.</h2>
            <a href="#" class="button big alt"><span>Let's go.</span></a>
        </div>
        <a href="#" class="button hidden"><span>Let's go.</span></a>
    </div>
</header>

<!-- Main -->
<div id="main">
    <div class="inner">
        <div class="columns">

            <#list printers.entrySet() as entry>
                <div class="image fit">
                    <table>
                        <thead>
                        <tr>
                            <th colspan="2">
                                <h2>${entry.key}</h2>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <#list entry.value.entrySet() as param>
                                <tr>
                                    <td>${param.key}
                                    <td>${param.value}
                            </#list>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </#list>

        </div>
    </div>
</div>

<!-- Footer -->
<footer id="footer">
    <a href="#" class="info fa fa-info-circle"><span>About</span></a>

    <div class="inner">
        <div class="content">
            <h3>Application for survey of printers</h3>

            <p>It allows you to track information about printers. Updating data may once in one minute. Version 0.2c. Artemka.</p>
        </div>
        <div class="copyright">
            <h3>System said :</h3>
            <h3>${said}</h3>
            <ul class="icons">

                <#--<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>-->
                <#--<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>-->
                <#--<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>-->
                <#--<li><a href="#" class="icon fa-dribbble"><span class="label">Dribbble</span></a></li>-->
            </ul>
            &copy; by Tokido. Design: <a href="https://templated.co">TEMPLATED</a>.
        </div>
    </div>
</footer>

<!-- Scripts -->
<script src=" ${context}/assets/js/jquery.min.js"></script>
<script src=" ${context}/assets/js/skel.min.js"></script>
<script src=" ${context}/assets/js/util.js"></script>
<script src=" ${context}/assets/js/main.js"></script>

</body>
</html>