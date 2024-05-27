import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> tasksHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        tasksHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        if (!tasksHistory.isEmpty() && tasksHistory.size() < 10) {
            result.addAll(tasksHistory);
        } else if (!tasksHistory.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                result.add(tasksHistory.get(i));
            }
        }
        return result;
    }
}
