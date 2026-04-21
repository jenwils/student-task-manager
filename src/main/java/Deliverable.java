import java.sql.Date;

public class Deliverable {
	 public static final String STATUS_PENDING = "Pending";
	    public static final String STATUS_IN_PROGRESS = "In Progress";
	    public static final String STATUS_COMPLETED = "Completed";

	    public static final String PRIORITY_HIGH = "High";
	    public static final String PRIORITY_MEDIUM = "Medium";
	    public static final String PRIORITY_LOW = "Low";

	    private int deliverableID;
	    private String title;
	    private String description;
	    private Date dueDate;
	    private boolean isHanded;
	    private String course;
	    private String priority;
	    private String category;
	    private String status;
	    private int studentID;

    public Deliverable() {
    }

    public Deliverable(String title, String description, Date dueDate, boolean isHanded,
            String course, String priority, String category, String status, int studentID) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isHanded = isHanded;
        this.course = course;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.studentID = studentID;
    }

    public Deliverable(int deliverableID, String title, String description, Date dueDate, boolean isHanded,
            String course, String priority, String category, String status, int studentID) {
        this.deliverableID = deliverableID;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isHanded = isHanded;
        this.course = course;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.studentID = studentID;
    }
    
    public Deliverable(int deliverableID, String title, String description, Date dueDate,
            boolean isHanded, String category, String status, int studentID) {
        this.deliverableID = deliverableID;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isHanded = isHanded;
        this.category = category;
        this.status = status;
        this.studentID = studentID;
    }

    public int getDeliverableID() {
        return deliverableID;
    }

    public void setDeliverableID(int deliverableID) {
        this.deliverableID = deliverableID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isHanded() {
        return isHanded;
    }

    public void setHanded(boolean handed) {
        isHanded = handed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    
    public String getCourse() {
        return course;
    }
 
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getPriority() {
        return priority;
    }
 
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Deliverable{"
                + "deliverableID=" + deliverableID
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", dueDate=" + dueDate
                + ", isHanded=" + isHanded
                + ", category='" + category + '\''
                + ", status='" + status + '\''
                + ", studentID=" + studentID
                + '}';
    }
}
