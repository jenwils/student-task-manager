import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/viewTasks")
public class ViewDeliverableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }
        
        int studentID = (int) session.getAttribute("studentID");
    	
        TaskManager tm = new TaskManager();
        List<Deliverable> task = tm.getDeliverablesByStudent(studentID);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>All Tasks</h1>");

        if (task.isEmpty()) {
            out.println("<p>No tasks found.</p>");
            out.println("<a href='index.html'>Home Page</a>");
        } else {
            out.println("<table style='border-spacing: 15px'>");
            out.println("<tr>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Due Date</th>");
            out.println("<th>Days Left</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            LocalDate today = LocalDate.now();

            for (Deliverable d : task) {
                LocalDate dueDate = d.getDueDate().toLocalDate();
                long daysLeft = ChronoUnit.DAYS.between(today, dueDate);

                String urgencyLabel;
                String rowColor;

                if (daysLeft < 0) {
                    urgencyLabel = "Overdue";
                    rowColor = "#ffcccc";
                } else if (daysLeft <= 3) {
                    urgencyLabel = daysLeft + " day(s) left &#128308;";
                    rowColor = "#ffcccc";
                } else if (daysLeft <= 7) {
                    urgencyLabel = daysLeft + " day(s) left &#128993;";
                    rowColor = "#fff3cc";
                } else {
                    urgencyLabel = daysLeft + " day(s) left &#128994;";
                    rowColor = "#ffffff";
                }

                out.println("<tr style='background-color:" + rowColor + "'>");
                out.println("<td>" + d.getTitle() + "</td>");
                out.println("<td>" + d.getDescription() + "</td>");
                out.println("<td>" + d.getDueDate() + "</td>");
                out.println("<td>" + urgencyLabel + "</td>");
                out.println("<td>" + d.getStatus() + "</td>");
                out.println("<td><a href='editTask?id=" + d.getDeliverableID() + "'>Edit</a></td>");
                out.println("<td><a href='deleteTask?id=" + d.getDeliverableID() + "'>Delete</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='deadlines'>View Upcoming Deadlines</a>");
            out.println("<br><a href='index.html'>Home Page</a>");
        }
    }
}
