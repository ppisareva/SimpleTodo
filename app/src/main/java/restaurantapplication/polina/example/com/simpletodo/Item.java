package restaurantapplication.polina.example.com.simpletodo;

import java.util.Date;

/**
 * Created by polina on 8/17/17.
 */

public class Item {
    public static int PRIORITY_LOW = 1;
    public static int PRIORITY_NORMAL = 2;
    public static int PRIORITY_HIGH = 3;
    int id;
    String taskName;
    String dueDate;
    int taskPriority;
    int taskCompletion;

    public Item(String taskName, String dueDate, int taskPriority, int taskCompletion) {
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

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public int isTaskCompletion() {
        return taskCompletion;
    }

    public void setTaskCompletion(int taskCompletion) {
        this.taskCompletion = taskCompletion;
    }
}
