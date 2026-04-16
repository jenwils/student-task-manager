import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManager {
	
	// Register a student
    public boolean createStudent(String name, String email, String passwordHash) {
        String sql = "INSERT INTO student (name, email, passwordHash) "
        		+ "VALUES (?, ?, ?)";
 
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
 
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, passwordHash);
 
            return statement.executeUpdate() > 0;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Find student by email
    public Student findByEmail(String email) {
        String sql = "SELECT studentID, name, email, passwordHash "
        		+ "FROM student WHERE email = ?";
 
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
 
            statement.setString(1, email);
 
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(
                    		resultSet.getInt("studentID"),
                    		resultSet.getString("name"),
                    		resultSet.getString("email"),
                    		resultSet.getString("passwordHash")
                    );
                }
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Checks if email is unique
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM student WHERE email = ?";
 
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
 
            statement.setString(1, email);
 
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return false;
    }
}

