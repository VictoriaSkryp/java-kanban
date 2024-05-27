import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
     ArrayList<Task> getAllTasks();
     ArrayList<Subtask> getAllSubtasks();
     ArrayList<Epic> getAllEpics();
     void deleteAllTasks();
     void deleteAllSubtasks();
     void deleteAllEpics();
     Task getTaskById(int id);
     Subtask getSubtaskById(int id);
     Epic getEpicById(int id);
     int createSubtask(Subtask subtask);
     int createTask(Task task);
     int createEpic(Epic epic);
     void updateTask(Task task);
     void updateSubtask(Subtask subtask);
     void updateEpic(Epic epic);
     void deleteTaskById(int id);
     void deleteSubtaskById(int id);
     void deleteEpicById(int id);
     List<Task> getHistory();
}
