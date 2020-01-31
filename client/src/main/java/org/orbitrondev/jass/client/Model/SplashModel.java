package org.orbitrondev.jass.client.Model;

import javafx.concurrent.Task;
import org.orbitrondev.jass.client.Utils.DatabaseUtil;
import org.orbitrondev.jass.lib.MVC.Model;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.util.ArrayList;

public class SplashModel extends Model {
    public final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() {
            // Create the service locator to hold our resources

            // List of all tasks
            ArrayList<Runnable> tasks = new ArrayList<>();

            // Initialize the db connection in the service locator
            // TODO: Should the data file be a variable?
            tasks.add(() -> ServiceLocator.add(new DatabaseUtil("jass.sqlite3")));

            // First, take some time, update progress
            this.updateProgress(1, tasks.size() + 1); // Start the progress bar with 1 instead of 0
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i).run();
                this.updateProgress(i + 2, tasks.size() + 1);
            }
            // For better UX, let the user see the full progress bar
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }
            return null;
        }
    };

    public void initialize() {
        new Thread(initializer).start();
    }
}
