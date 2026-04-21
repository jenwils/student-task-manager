import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/editTask")
public class EditDeliverableServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }
        
		int id = Integer.parseInt(request.getParameter("id"));
		
		TaskManager tm = new TaskManager();
        Deliverable d = tm.getDeliverableById(id);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");
        
        out.println("<ul class='navbar'>");
        out.println("<li><a href='viewTasks'>Tasks</a></li>");
        out.println("<li><a href='deadlines'>Deadlines</a></li>");   
        out.println("<li><a href='add_task.html'>Add Task</a></li>");
        out.println("<li style='float:right'><a href='logout'>Logout</a></li>");
        out.println("</ul>");
        
        out.println("<h1>Edit Task</h1>");
        
        out.println("<form action='editTask' method='post'>");
        
        out.println("<input type='hidden' name='id' value='" + d.getDeliverableID() + "'/>");
        
        out.println("<label>Title</label><br>");
        out.println("<input type='text' name='title' value='" + d.getTitle() + "'/><br><br>");

        out.println("<label>Due Date</label><br>");
        out.println("<input type='date' name='dueDate' value='" + d.getDueDate() + "'/><br><br>");
        
        out.println("<label>Course</label><br>");
        out.println("<input type='text' name='course' value='" + d.getCourse() + "'/><br><br>");

        out.println("<label>Description</label><br>");
        out.println("<textarea name='description'>" + d.getDescription() + "</textarea><br><br>");
        
        out.println("<label>Category</label><br>");
        out.println("<select name='category'>");
        out.println("<option value='General'" + ("General".equals(d.getCategory()) ? " selected" : "") + ">General</option>");
        out.println("<option value='Assignment'" + ("Assignment".equals(d.getCategory()) ? " selected" : "") + ">Assignment</option>");
        out.println("<option value='Exam'" + ("Exam".equals(d.getCategory()) ? " selected" : "") + ">Exam</option>");
        out.println("</select><br><br>");

        out.println("<label>Status</label><br>");
        out.println("<select name='status'>");
        out.println("<option value='Pending'" + ("Pending".equals(d.getStatus()) ? " selected" : "") + ">Pending</option>");
        out.println("<option value='In Progress'" + ("In Progress".equals(d.getStatus()) ? " selected" : "") + ">In Progress</option>");
        out.println("<option value='Completed'" + ("Completed".equals(d.getStatus()) ? " selected" : "") + ">Completed</option>");
        out.println("</select><br><br>");
        
        out.println("<label>Completed?</label>");
        out.println("<input type='checkbox' name='isHanded' " + (d.isHanded() ? "checked" : "") + " /><br><br>");

        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }

        int deliverableId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        String course = request.getParameter("course");
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        boolean isHanded = request.getParameter("isHanded") != null;

        TaskManager tm = new TaskManager();
        Deliverable d = tm.getDeliverableById(deliverableId);
        d.setTitle(title);
        d.setDueDate(Date.valueOf(dueDate));
        d.setDescription(description);
        d.setCourse(course);
        d.setCategory(category);
        d.setStatus(status);
        d.setHanded(isHanded);

        tm.updateDeliverable(d);

        response.sendRedirect("viewTasks");
    }
}

