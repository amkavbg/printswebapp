package servlets;

import engine.Engine;
import engine.Printer;
import freemarker.template.*;

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

    private Map<String, Printer> pmap = new HashMap<>();
    //private Engine eng;

    @Override
    public void init() throws ServletException {
        Engine eng = new Engine();
        try {
           pmap = eng.getPrintersInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  Configure  //TODO: make downloading templates from directory [use setDirectoryForTemplateLoading]
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //  Load template
        Template tpl = cfg.getTemplate("src/main/resources/templates/pmap.tpl");

        //  Data model
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "PRINTSWEBAPP 0.2a by tokido");
        input.put("tablename", "ПРИНТЕРЫ ЦП");


        input.put("printers", pmap);
//      System.out.println("In method DOGET printer map: " + pmap.size() + "\n " + pmap);

        response.setContentType("text/html;charset=utf-8");
            try {
                tpl.process(input,response.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
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

    public void destroy() {
        super.destroy();
    }
}
