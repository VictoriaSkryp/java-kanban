import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int generatedTaskId = 0;
    private int generatedSubtaskId = 0;
    private int generatedEpicId = 0;

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTaskById(int id) {
        Task result = tasks.get(id);
        if (result != null) {
            historyManager.add(result);
        }
        return result;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask result = subtasks.get(id);
        if (result != null) {
            historyManager.add(result);
        }
        return result;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic result = epics.get(id);
        if (result != null) {
            historyManager.add(result);
        }
        return result;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask value : subtasks.values()) {
            Epic epic = epics.get(value.getEpicId());
            epic.setStatus(Status.NEW);
            epic.setSubtaskIds(new ArrayList<>());
            updateEpic(epic);
        }
        subtasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }


    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
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

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        epics.remove(id);
        for (Integer subTaskId : epic.getSubtaskIds()) {
            deleteSubtaskById(subTaskId);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public int createSubtask(Subtask subtask) {
        subtask.setId(++generatedSubtaskId);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().add(subtask.getId());
            updateEpic(epic);
        }
        return subtask.getId();
    }

    @Override
    public int createTask(Task task) {
        task.setId(++generatedTaskId);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setId(++generatedEpicId);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Epic epic = epics.get(subtask.getEpicId());
            subtasks.put(subtask.getId(), subtask);
            updateEpic(epic);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
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

    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                result.add(subtask);
            }
        }
        return result;
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
