import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Login_Register.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        HttpSession session = request.getSession();

        // Data validation
        if (name == null || name.isBlank() ||
                email == null || email.isBlank() ||
                password == null || password.isBlank()) {

                session.setAttribute("error", "fields");
                response.sendRedirect("Login_Register.html");
                return;
            }
        
        StudentManager sm = new StudentManager();
       
        // Hashes password
        String salt = BCrypt.gensalt(12);
        String passwordHash = BCrypt.hashpw(password, salt);

        // Saves user input
        if (sm.createStudent(name.trim(), email.trim(), passwordHash)) {
            session.setAttribute("success", "registered");
            response.sendRedirect("Login_Register.html");
        } else {
            session.setAttribute("error", "failed");
            response.sendRedirect("Login_Register.html");
        }
    }
}
