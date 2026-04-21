import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deadlines")
public class DeadlineServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }
        
        int studentID = (int) session.getAttribute("studentID");

        TaskManager tm = new TaskManager();
        List<Deliverable> upcoming = tm.getUpcomingDeadlines(studentID, 7);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");
        
        out.println("<ul class='navbar'>");
        out.println("<li><a href='viewTasks'>Tasks</a></li>");
        out.println("<li><a href='deadlines'>Deadlines</a></li>");   
        out.println("<li><a href='add_task.html'>Add Task</a></li>");
        out.println("<li style='float:right'><a href='logout'>Logout</a></li>");
        out.println("</ul>");

        out.println("<h1>Upcoming Deadlines</h1>");

        if (upcoming.isEmpty()) {
            out.println("<p>No upcoming deadlines in the next 7 days.</p>");
        } else {
            LocalDate today = LocalDate.now();

            out.println("<table style='border-spacing: 15px'>");
            out.println("<tr>");
            out.println("<th>Title</th>");
            out.println("<th>Due Date</th>");
            out.println("<th>Days Left</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            for (Deliverable d : upcoming) {
                LocalDate dueDate = d.getDueDate().toLocalDate();
                long daysLeft = ChronoUnit.DAYS.between(today, dueDate);

                String urgencyLabel;
                String rowColor;

                if (daysLeft <= 3) {
                    urgencyLabel = daysLeft + " day(s) &#128308; Urgent";
                    rowColor = "#ffcccc";
                } else {
                    urgencyLabel = daysLeft + " day(s) &#128993; Soon";
                    rowColor = "#fff3cc";
                }

                out.println("<tr style='background-color:" + rowColor + "'>");
                out.println("<td>" + d.getTitle() + "</td>");
                out.println("<td>" + d.getDueDate() + "</td>");
                out.println("<td>" + urgencyLabel + "</td>");
                out.println("<td>" + d.getStatus() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }

    }
}
