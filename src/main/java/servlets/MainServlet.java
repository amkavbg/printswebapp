package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tokido on 6/22/16.
 */
@WebServlet(name = "PrintsWebApps")
public class MainServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println("<B>Список принтеров ЦП</B>");
        pw.println("<table border=1>");
        try {
            //TODO: write code to generate a spreadsheet with the data ready
        } catch (Exception e) {
            throw  new ServletException(e);
        }
        pw.println("</table>");
    }

    public void destroy() {
        super.destroy();
    }
}
