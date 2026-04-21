import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }
        
        int studentID = (int) session.getAttribute("studentID");
        String keyword = request.getParameter("keyword");

        TaskManager tm = new TaskManager();
        List<Deliverable> results = tm.searchDeliverables(keyword, studentID);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>Search Results:</h2>");

        for (Deliverable d : results) {
            out.println("<p>" + d.getTitle() + " - " + d.getDueDate() + "</p>");
        }
    }
}