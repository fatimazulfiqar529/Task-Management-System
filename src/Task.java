/**
 * Represents a task in the Task Management System.
 * @author Fatima Zulfiqar
 */
public class Task {

    private int id;
    private String title;
    private String description;
    private String status;
    /** Returns the unique ID of the task.
 * @return task ID
 */
public int getId() {
    return id;
}
/** Returns the title of the task.
 * @return task title
 */
public String getTitle() {
    return title;
}
/** Updates the task title.
 * @param title new task title
 */
public void setTitle(String title) {
    this.title = title;
}
/** Returns the task description.
 * @return task description
 */
public String getDescription() {
    return description;
}
/** Updates the task description.
 * @param description new task description
 */
public void setDescription(String description) {
    this.description = description;
}
/** Returns the current task status.
 * @return task status
 */
public String getStatus() {
    return status;
}
/** Updates the task status.
 * @param status new task status
 */
public void setStatus(String status) {
    this.status = status;
}
    /**
     * Creates a new task.
     * @param id task ID
     * @param title task title
     * @param description task description
     */
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "Pending";
    }
}