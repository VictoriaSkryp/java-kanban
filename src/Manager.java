import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int generatedTaskId = 0;
    private int generatedSubtaskId = 0;
    private int generatedEpicId = 0;

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
        for (Subtask value : subtasks.values()) {
            Epic epic = epics.get(value.getEpicId());
            epic.setStatus(Status.NEW);
            epic.setSubtaskIds(new ArrayList<>());
            updateEpic(epic);
        }
        subtasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
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
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().add(subtask.getId());
            updateEpic(epic);
        }
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
            subtasks.put(subtask.getId(), subtask);
            updateEpic(epic);
        }
    }


    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            subtasks.remove(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                List<Integer> subtaskIds = epic.getSubtaskIds();
                int removeId = 0;
                for (int i = 0; i < subtaskIds.size(); i++) {
                    if (subtaskIds.get(i) == id) {
                        removeId = i;
                    }
                }
                subtaskIds.remove(removeId);
                epic.setSubtaskIds(subtaskIds);
                updateEpic(epic);
            }
        }
    }

    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        epics.remove(id);
        for (Integer key : epic.getSubtaskIds()) {
            deleteSubtask(key);
        }
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

    private void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            ArrayList<Subtask> subtasksByEpic = getSubtasksByEpicId(epic.getId());
            if (isAllSubtaskInNew(subtasksByEpic) || epic.getSubtaskIds().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else if (isAllSubtaskInDone(subtasksByEpic)) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            epics.put(epic.getId(), epic);
        }
    }

    private boolean isAllSubtaskInDone(List<Subtask> subtasks) {
        if (subtasks == null || subtasks.isEmpty()) {
            return false;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Status.DONE) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllSubtaskInNew(List<Subtask> subtasks) {
        if (subtasks == null || subtasks.isEmpty()) {
            return false;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Status.NEW) {
                return false;
            }
        }
        return true;
    }
}
