import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class TaskManager {
    public boolean createDeliverable(Deliverable deliverable) {
    	if (!isValidPriority(deliverable.getPriority()) || !isValidStatus(deliverable.getStatus())) {
            return false;
        }
        String sql = "INSERT INTO deliverable "
                + "(title, description, dueDate, isHanded, course, priority, category, status, studentID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, deliverable.getTitle());
            statement.setString(2, deliverable.getDescription());
            statement.setDate(3, deliverable.getDueDate());
            statement.setBoolean(4, deliverable.isHanded());
            statement.setString(5, deliverable.getCourse());
            statement.setString(6, deliverable.getPriority());
            statement.setString(7, deliverable.getCategory());
            statement.setString(8, deliverable.getStatus());
            statement.setInt(9, deliverable.getStudentID());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Deliverable> getAllDeliverables() {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
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
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
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
    	if (!isValidPriority(deliverable.getPriority()) || !isValidStatus(deliverable.getStatus())) {
            return false;
        }
    	
    	String sql = "UPDATE deliverable SET title = ?, description = ?, dueDate = ?, "
                + "isHanded = ?, course =?, category = ?, status = ?, studentID = ? WHERE deliverableID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, deliverable.getTitle());
            statement.setString(2, deliverable.getDescription());
            statement.setDate(3, deliverable.getDueDate());
            statement.setBoolean(4, deliverable.isHanded());
            statement.setString(5, deliverable.getCourse());
            statement.setString(6, deliverable.getCategory());
            statement.setString(7, deliverable.getStatus());
            statement.setInt(8, deliverable.getStudentID());
            statement.setInt(9, deliverable.getDeliverableID());

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
    
    public boolean updateDeliverableStatus(int deliverableID, String status) {
        if (!isValidStatus(status)) {
            return false;
        }

        String sql = "UPDATE deliverable SET status = ? WHERE deliverableID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, deliverableID);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Deliverable> getDeliverablesByStudent(int studentID) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable WHERE studentID = ? ORDER BY dueDate ASC";
       
        List<Deliverable> deliverables = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentID);
 
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

    public List<Deliverable> getDeliverablesByCourse(String course) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable WHERE course = ? ORDER BY dueDate ASC";
        return getDeliverablesByStringValue(sql, course);
    }

    public List<Deliverable> getDeliverablesByPriority(String priority) {
        if (!isValidPriority(priority)) {
            return new ArrayList<>();
        }

        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable WHERE priority = ? ORDER BY dueDate ASC";
        return getDeliverablesByStringValue(sql, priority);
    }

    public List<Deliverable> getDeliverablesByStatus(String status) {
        if (!isValidStatus(status)) {
            return new ArrayList<>();
        }

        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable WHERE status = ? ORDER BY dueDate ASC";
        return getDeliverablesByStringValue(sql, status);
    }

    public List<Deliverable> getDeliverablesDueOn(Date dueDate) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable WHERE dueDate = ? ORDER BY title ASC";
        List<Deliverable> deliverables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, dueDate);

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

    public List<Deliverable> getDeliverablesOrderedByDueDate() {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable ORDER BY dueDate ASC, priority ASC, title ASC";
        List<Deliverable> deliverables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                deliverables.add(mapRowToDeliverable(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return deliverables;
    }
    
    private List<Deliverable> getDeliverablesByStringValue(String sql, String value) {
        List<Deliverable> deliverables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);

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

    public List<Deliverable> getCategorizedDeliverables(String course, String priority, Date dueDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                        + "FROM deliverable WHERE 1 = 1");
        List<Object> parameters = new ArrayList<>();

        if (course != null && !course.isBlank()) {
            sql.append(" AND course = ?");
            parameters.add(course);
        }

        if (priority != null && !priority.isBlank()) {
            if (!isValidPriority(priority)) {
                return new ArrayList<>();
            }

            sql.append(" AND priority = ?");
            parameters.add(priority);
        }

        if (dueDate != null) {
            sql.append(" AND dueDate = ?");
            parameters.add(dueDate);
        }

        sql.append(" ORDER BY dueDate ASC, title ASC");
        List<Deliverable> deliverables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            bindParameters(statement, parameters);

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

    public List<Deliverable> getUpcomingDeadlines(int studentID, int days) {
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
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
    
    public List<Deliverable> searchDeliverables(String keyword, int studentID) {

        List<Deliverable> deliverables = new ArrayList<>();
        
        String sql = "SELECT deliverableID, title, description, dueDate, isHanded, course, priority, category, status, studentID "
                + "FROM deliverable "
                + "WHERE studentID = ? AND (title LIKE ? OR description LIKE ?)";
 
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentID);
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
 
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
    
    private void bindParameters(PreparedStatement statement, List<Object> parameters) {
    	try {
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    statement.setString(i + 1, (String) param);
                } else if (param instanceof Date) {
                    statement.setDate(i + 1, (Date) param);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }       
    }

    private boolean isValidPriority(String priority) {
    	if (priority == null) return true;
        return Deliverable.PRIORITY_HIGH.equals(priority)
                || Deliverable.PRIORITY_MEDIUM.equals(priority)
                || Deliverable.PRIORITY_LOW.equals(priority);
    }

    private boolean isValidStatus(String status) {
        return Deliverable.STATUS_PENDING.equals(status)
                || Deliverable.STATUS_IN_PROGRESS.equals(status)
                || Deliverable.STATUS_COMPLETED.equals(status);
    }

    private Deliverable mapRowToDeliverable(ResultSet resultSet) throws SQLException {
        return new Deliverable(
            resultSet.getInt("deliverableID"),
            resultSet.getString("title"),
            resultSet.getString("description"),
            resultSet.getDate("dueDate"),
            resultSet.getBoolean("isHanded"),
            resultSet.getString("course"),      
            resultSet.getString("priority"),    
            resultSet.getString("category"),
            resultSet.getString("status"),
            resultSet.getInt("studentID"));
    }
}
