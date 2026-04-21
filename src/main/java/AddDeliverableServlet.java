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
            response.sendRedirect("Login_Register.html");
            return;
        }
    	
        int studentID = (int) session.getAttribute("studentID");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        String course = request.getParameter("course");

        Deliverable d = new Deliverable();
        d.setTitle(title);
        d.setDescription(description);
        d.setDueDate(Date.valueOf(dueDate));
        d.setHanded(false);
        d.setCategory(category);
        d.setStatus(status);
        d.setStudentID(studentID);
        d.setCourse(course);

        TaskManager tm = new TaskManager();
        tm.createDeliverable(d);

        response.sendRedirect("viewTasks");
    }
}
