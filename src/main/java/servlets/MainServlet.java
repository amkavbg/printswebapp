package servlets;

import engine.Engine;
import engine.Printer;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tokido on 6/22/16.
 */
public class MainServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MainServlet.class);
    private Engine eng;
    private Template tpl;
    private long lastRefreshTimestamp;
    private Map<String, Object> generatedTemplateInput;
    private long interval = 60000L;

    @Override
    public void init() throws ServletException {
        log.debug("Servlet start. Init in progress");
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        cfg.setClassForTemplateLoading(this.getClass(), "/" + "templates");

        try {
            tpl = cfg.getTemplate("printers.tpl");
        } catch (IOException e) {
            log.error("Can't found template file, aborting");
        }

        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/WEB-INF/classes/");
        this.eng = new Engine(realPath);

        generatedTemplateInput = generateTemplateData();
        lastRefreshTimestamp = new Date().getTime();
        log.debug("Servlet running. Init finish.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("doGet running");

        if (new Date().getTime() - interval > lastRefreshTimestamp) {
            generatedTemplateInput = generateTemplateData();
            generatedTemplateInput.put("error","Work done.");
            lastRefreshTimestamp = new Date().getTime();
        }

        response.setContentType("text/html;charset=utf-8");
        try {
            this.tpl.process(generatedTemplateInput, response.getWriter());
        } catch (TemplateException e) {
            log.error("Failed to create html with freemarker.",e);
        }
        log.debug("Receive page to client.");
    }

    private Map<String, Object> generateTemplateData() {
        Map<String, Object> input = new HashMap<>();
        input.put("title", "PRINTSWEBAPP 0.2b by tokido");
        input.put("tablename", "œ–»Õ“≈–€ ÷œ");
        Map<String, Object> printermap = new HashMap<>();
        try {
            for (Printer printer : eng.getPrintersInfo().values()) {
                printermap.put(printer.getIp() + " - " + printer.getValueByKey("NetName"), printer.getParameters());
            }
        } catch (RuntimeException e) {
            log.error("Critical error populate printer. " + e);
            input.put("Error", "Critical error populate printer.\n" + e);
        } catch (IOException e) {
            log.error("Critical error", e);
        }

        input.put("printers", printermap);
        input.put("error", "working without error");
        return input;
    }

    public void destroy() {
        log.debug("Program receive destroy command.");
        super.destroy();
    }
}
