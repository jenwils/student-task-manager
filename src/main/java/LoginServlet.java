import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
 
        HttpSession session = request.getSession(true);

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
        	session.setAttribute("error", "fields");
            response.sendRedirect("login.html");
            return;
        }
 
        StudentManager sm = new StudentManager();
        Student student = sm.findByEmail(email.trim());
 
        if (student == null ||!BCrypt.checkpw(password, student.getPasswordHash())) {
                session.setAttribute("error", "credentials");
                response.sendRedirect("login.html");
                return;
            }
       
        session.setAttribute("studentID", student.getStudentID());
        session.setAttribute("studentName", student.getName());
        session.setAttribute("studentEmail", student.getEmail());
 
        response.sendRedirect("viewTasks");
    }
}
