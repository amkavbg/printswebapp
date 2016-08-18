<!DOCTYPE HTML>
<html>
<head>
    <title>${title}</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" type="text/css"  href="/static/templates/assets/css/main.css"/>
</head>
<body>

<!-- Header -->
<header id="header">
    <div class="inner">
        <div class="content">
            <h1>Radius</h1>

            <h2>A fully app to control the resources of<br/>
                the central enterprise printers.</h2>
            <a href="#" class="button big alt"><span>Let's Go</span></a>
        </div>
        <a href="#" class="button hidden"><span>Let's Go</span></a>
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
            <h3>Vestibulum hendrerit tortor id gravida</h3>

            <p>In tempor porttitor nisl non elementum. Nulla ipsum ipsum, feugiat vitae vehicula vitae, imperdiet sed
                risus. Fusce sed dictum neque, id auctor felis. Praesent luctus sagittis viverra. Nulla erat nibh,
                fermentum quis enim ac, ultrices euismod augue. Proin ligula nibh, pretium at enim eget, tempor feugiat
                nulla.</p>
        </div>
        <div class="copyright">
            <h3>Follow me</h3>
            <ul class="icons">
                <li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
                <li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
                <li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
                <li><a href="#" class="icon fa-dribbble"><span class="label">Dribbble</span></a></li>
            </ul>
            &copy; Untitled. Design: <a href="https://templated.co">TEMPLATED</a>. Images: <a
                href="https://unsplash.com/">Unsplash</a>.
        </div>
    </div>
</footer>

<!-- Scripts -->
<script src=" /static/templates/assets/js/jquery.min.js"></script>
<script src=" /static/templates/assets/js/skel.min.js"></script>
<script src=" /static/templates/assets/js/util.js"></script>
<script src=" /static/templates/assets/js/main.js"></script>

</body>
</html>