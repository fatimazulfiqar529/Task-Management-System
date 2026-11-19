import java.util.ArrayList;
import java.util.List;

/**
 * Manages all tasks in the system.
 * @author Fatima Zulfiqar
 */
public class TaskManagerService {

    private List<Task> tasks;
    private int nextId;
/** Initializes the task management service.
 */
    public TaskManagerService() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    /**
     * Adds a new task.
     * @param title task title
     * @param description task description
     */
    public void addTask(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }

        Task task = new Task(nextId++, title, description);
        tasks.add(task);
    }

    /**
     * Returns all tasks.
     * @return list of tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Updates an existing task.
     * @param id task ID
     * @param title new title
     * @param description new description
     * @return true if updated, otherwise false
     */
    public boolean updateTask(int id, String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(title);
                task.setDescription(description);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a task.
     * @param id task ID
     * @return true if deleted, otherwise false
     */
    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    /**
     * Changes the status of a task.
     * @param id task ID
     * @param status new task status
     * @return true if updated, otherwise false
     */
    public boolean updateStatus(int id, String status) {
        if (!status.equals("Pending") &&
            !status.equals("In Progress") &&
            !status.equals("Completed")) {
            return false;
        }

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(status);
                return true;
            }
        }
        return false;
    }
}