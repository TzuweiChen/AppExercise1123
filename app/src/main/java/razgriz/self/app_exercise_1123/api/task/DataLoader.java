package razgriz.self.app_exercise_1123.api.task;

public class DataLoader {
    public static void run(final DataTask task) {
        new Thread(task::action).start();
    }
}
