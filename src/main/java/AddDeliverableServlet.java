import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/addTask")
public class AddDeliverableServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("login-register.html");
            return;
        }
    	
        int studentID = (int) session.getAttribute("studentID");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");

        Deliverable d = new Deliverable();
        d.setTitle(title);
        d.setDescription(description);
        d.setDueDate(Date.valueOf(dueDate));
        d.setHanded(false);
        d.setCategory("General");
        d.setStatus("Pending");
        d.setStudentID(studentID);

        TaskManager tm = new TaskManager();
        tm.createDeliverable(d);

        response.sendRedirect("viewTasks");
    }
}
