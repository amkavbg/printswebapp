package servlets;

import engine.Engine;
import engine.Printer;
import freemarker.template.*;

import javax.servlet.ServletContext;
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

//private Map<String, Printer> pmap = new HashMap<>();
    private Engine eng;
    private Template tpl;

    @Override
    public void init() throws ServletException {
        //  Configure freemarker template
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        cfg.setClassForTemplateLoading(this.getClass(), "/" + "templates");

        try {
            tpl = cfg.getTemplate("printers.tpl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/WEB-INF/classes/");
        this.eng = new Engine(realPath);

//        try {
//           pmap = eng.getPrintersInfo();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  Configure
//        Configuration cfg = new Configuration();
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
//        cfg.setClassForTemplateLoading(this.getClass(),"/"+"templates");
//        //  Load template
//        Template tpl = cfg.getTemplate("printers.tpl");

        //  Data model

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "PRINTSWEBAPP 0.2a by tokido");
        input.put("tablename", "œ–»Õ“≈–€ ÷œ");
        Map<String, Object> printermap = new HashMap<>();

        for (Printer printer : eng.getPrintersInfo().values()) {
                printermap.put(printer.getIp() + " - " + printer.getValueByKey("NetName"), printer.getParameters());
        }
        input.put("printers", printermap);
        response.setContentType("text/html;charset=utf-8");

            try {
                this.tpl.process(input,response.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }

    public void destroy() {
        super.destroy();
    }
}
