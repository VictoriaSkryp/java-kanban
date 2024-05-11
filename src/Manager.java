import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private int generatedTaskId = 0;
    private int generatedSubtaskId = 0;
    private int generatedEpicId = 0;

    public Manager(HashMap<Integer, Task> tasks, HashMap<Integer, Subtask> subtasks, HashMap<Integer, Epic> epics) {
        this.tasks = tasks;
        this.subtasks = subtasks;
        this.epics = epics;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(++generatedSubtaskId);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().add(subtask.getId());
            updateEpic(epic);
        }
        subtasks.put(subtask.getId(), subtask);
    }

    public void createTask(Task task) {
        task.setId(++generatedTaskId);
        tasks.put(task.getId(), task);
    }

    public int createEpic(Epic epic) {
        epic.setId(++generatedEpicId);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }


    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Epic epic = epics.get(subtask.getEpicId());
            if (subtask.getStatus() == Status.DONE) {
                updateEpic(epic);
            }
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                updateEpic(epic);
            }
            subtasks.put(subtask.getId(), subtask);
        }
    }

    public void updateEpic(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            ArrayList<Subtask> subtasksByEpic = getSubtasksByEpicId(epic.getId());
            boolean isCanBeDone = true;
            for (Subtask subtask : subtasksByEpic) {
                if (subtask.getStatus() != Status.DONE) {
                    isCanBeDone = false;
                    break;
                }
            }
            if (isCanBeDone) {
                epic.setStatus(Status.DONE);
            }

            epics.put(epic.getId(), epic);
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                result.add(subtask);
            }
        }
        return result;
    }

}
