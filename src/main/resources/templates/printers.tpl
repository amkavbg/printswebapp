<html>
<head>
    <style>
        html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;
            padding: 0;
            border: 0;
            font-size: 100%;
            font: inherit;
            vertical-align: baseline;

        }

        .pricing-table {

            margin: 0 auto;
            top: 100px;
            position: relative;



            -webkit-box-shadow: 2px 2px 12px 6px rgba(0,0,0,0.3);
            -moz-box-shadow: 2px 2px 12px 6px rgba(0,0,0,0.3);
            box-shadow: 2px 2px 12px 6px rgba(0,0,0,0.3);
            float: left;
            margin-left: 50px;
            margin-bottom: 50px;
            overflow:hidden;
            z-index:1;
        }

        .pricing-table:hover {
            -webkit-transform: scale(1.2, 1.2);
            -moz-transform: scale(1.2, 1.2);
            -o-transform: scale(1.2, 1.2);
            -ms-transform: scale(1.2, 1.2);
            transform: scale(1.2, 1.2);
            z-index:3;
        }


        .pricing-table {
            max-width: 300px;
        }
        table {
            border-collapse: collapse;
            border-spacing: 0;
        }
        .pricing-table thead .plan .green {
            color: #36611e;
            text-shadow: 1px 1px 0px rgba(255,255,255, .2);
            background: url(http://designmodo.com/demo/css3pricingtables/img/green_pattern.png) repeat-x 0 0;
        }
        .pricing-table thead .plan th {
            width: 210px;
            height: 42px;
            padding: 15px 0;
            text-align: center;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border-radius: 2px;
        }
        .pricing-table thead .plan h2 {
            font-family: 'Arial Black', Arial, Helvetica, sans-serif;
            font-weight: bold;
            font-size: 22px;
            text-transform: uppercase;
            line-height: 24px;
        }
        .pricing-table thead .plan em {
            font-family: Georgia, Arial, Helvetica, sans-serif;
            font-style: italic;
            font-size: 14px;
            line-height: 16px;
        }


        .pricing-table .clock-icon td {
            background-position: 0 0;
        }
        .pricing-table .clock-icon td, .pricing-table .basket-icon td, .pricing-table .star-icon td, .pricing-table .heart-icon td {
            background: #ffffff url(../img/icons.png) no-repeat 0 0;
        }

        .pricing-table tbody td {
            width: 50%;
            padding-top: 3px;
            padding-bottom: 3px;
            border-top: 1px solid #dedede;
            font-family: Helvetica, Arial, sans-serif;
            font-size: 15px;
            color: #828282;
            text-indent: 25px;
        }

        .pricing-table tbody td:nth-child(-n+2) {
            border-top: none;
        }

        .pricing-table td {
            position: relative;
            display: inline-block;
            vertical-align: text-top;
        }
    </style>
    <title>${title}</title>
</head>

<body>
    <center><h2>${tablename}</h2></center>
    <center>

    <#list printers.entrySet() as entry>
        <table class="pricing-table">
            <thead>
                <tr class="plan">
                    <th class="green" colspan="2">
                        <h2>${entry.key}</h2>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr class="clock-icon">
                <#list entry.value.entrySet() as param>
                    <tr><td>${param.key}<td>${param.value}
                </#list>
                </tr>
            </tbody>
        </table>
    </#list>

    </center>

</body>
</html>