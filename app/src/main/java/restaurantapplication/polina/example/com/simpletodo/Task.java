package restaurantapplication.polina.example.com.simpletodo;


/**
 * Created by polina on 8/17/17.
 */

public class Task {
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_HIGH = 1;
    int id;
    String taskName;
    String dueDate;
    int taskPriority;
    int taskCompletion;

    public Task(){}

    public Task(String taskName, String dueDate, int taskPriority, int taskCompletion) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
        this.taskCompletion = taskCompletion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }


    public String getDueDate() {
        return dueDate;
    }


    public int getTaskPriority() {
        return taskPriority;
    }


    public int isTaskCompletion() {
        return taskCompletion;
    }

}
