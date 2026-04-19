import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public boolean createDeliverable(Deliverable deliverable) {
        String sql = "INSERT INTO deliverable "
                + "(title, description, dueDate, isHanded, category, status, studentID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, deliverable.getTitle());
            statement.setString(2, deliverable.getDescription());
            statement.setDate(3, deliverable.getDueDate());
            statement.setBoolean(4, deliverable.isHanded());
            statement.setString(5, deliverable.getCategory());
            statement.setString(6, deliverable.getStatus());
            statement.setInt(7, deliverable.getStudentID());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Deliverable> getAllDeliverables() {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, category, status, studentID "
                + "FROM deliverable";
        List<Deliverable> deliverables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Deliverable deliverable = mapRowToDeliverable(resultSet);
                deliverables.add(deliverable);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return deliverables;
    }

    public Deliverable getDeliverableById(int deliverableID) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, category, status, studentID "
                + "FROM deliverable WHERE deliverableID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deliverableID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToDeliverable(resultSet);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public boolean updateDeliverable(Deliverable deliverable) {
        String sql = "UPDATE deliverable SET title = ?, description = ?, dueDate = ?, "
                + "isHanded = ?, category = ?, status = ?, studentID = ? WHERE deliverableID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, deliverable.getTitle());
            statement.setString(2, deliverable.getDescription());
            statement.setDate(3, deliverable.getDueDate());
            statement.setBoolean(4, deliverable.isHanded());
            statement.setString(5, deliverable.getCategory());
            statement.setString(6, deliverable.getStatus());
            statement.setInt(7, deliverable.getStudentID());
            statement.setInt(8, deliverable.getDeliverableID());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean deleteDeliverable(int deliverableID) {
        String sql = "DELETE FROM deliverable WHERE deliverableID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deliverableID);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Deliverable> getUpcomingDeadlines(int studentID, int days) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, category, status, studentID "
                + "FROM deliverable "
                + "WHERE studentID = ? AND isHanded = FALSE "
                + "AND dueDate BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) "
                + "ORDER BY dueDate ASC";
        List<Deliverable> deliverables = new ArrayList<>();
 
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentID);
            statement.setInt(2, days);
 
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    deliverables.add(mapRowToDeliverable(resultSet));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
 
        return deliverables;
    }
 
    private Deliverable mapRowToDeliverable(ResultSet resultSet) throws SQLException {
        return new Deliverable(
                resultSet.getInt("deliverableID"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getDate("dueDate"),
                resultSet.getBoolean("isHanded"),
                resultSet.getString("category"),
                resultSet.getString("status"),
                resultSet.getInt("studentID"));
    }
}
