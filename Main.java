import java.util.*;

public class Main {
    private static final List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== Java To-Do List (Automated Version) =====");

        // Create (Add)
        addTask("Setup Jenkins pipeline");
        addTask("Implement Dockerfile for project");
        addTask("Review Java backend logic");

        // Read (View)
        System.out.println("\nInitial Tasks:");
        viewTasks();

        // Update
        updateTask(1, "Implement Dockerfile and deploy to Jenkins");
        System.out.println("\nAfter Updating Task 2:");
        viewTasks();

        // Delete
        deleteTask(0);
        System.out.println("\nAfter Deleting Task 1:");
        viewTasks();

        // Exit message
        System.out.println("\nAll CRUD operations executed successfully!");
    }

    // Create
    private static void addTask(String task) {
        tasks.add(task);
        System.out.println("Added: " + task);
    }

    // Read
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("(No tasks available)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    // Update
    private static void updateTask(int index, String newTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, newTask);
            System.out.println("Updated task " + (index + 1) + " → " + newTask);
        } else {
            System.out.println("Invalid task index!");
        }
    }

    // Delete
    private static void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            System.out.println("Deleted: " + tasks.remove(index));
        } else {
            System.out.println("Invalid task index!");
        }
    }
}
