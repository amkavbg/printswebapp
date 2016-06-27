package servlets;

import engine.Engine;
import engine.Printer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tokido on 6/22/16.
 */
public class MainServlet extends HttpServlet {
    private Map<String, Printer> printers= new HashMap<>();
    private Engine eng;


    @Override
    public void init() throws ServletException {
         this.eng=new Engine();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Configuration cfg=new Configuration();
        Template tpl = cfg.getTemplate("src/main/java/printers.tpl");
        Map<String, Object> data = new HashMap<>();
        data.put("hellomsg","ALLO YOBA ETO TI?");
        Map<String, Printer> printers=eng.getPrintersInfo();

        response.setContentType("text/html;charset=utf-8");
        try {
            tpl.process(printers.values(),response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

//        PrintWriter pw = response.getWriter();
//        pw.println("<B>Список принтеров ЦП</B>");
//        pw.println("<table border=1>");
//        try {
//            //TODO: write code to generate a spreadsheet with the data ready
//        } catch (Exception e) {
//            throw  new ServletException(e);
//        }
//        pw.println("</table>");
    }

    public void destroy() {
        super.destroy();
    }
}
