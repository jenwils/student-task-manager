import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deleteTask")
public class DeleteDeliverableServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentID") == null) {
            response.sendRedirect("Login_Register.html");
            return;
        }
		
		int deliverableId = Integer.parseInt(request.getParameter("id"));
		
		TaskManager tm = new TaskManager();
		tm.deleteDeliverable(deliverableId);
		
		response.sendRedirect("viewTasks");
		
	}
}
