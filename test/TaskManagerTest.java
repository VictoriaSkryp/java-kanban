import org.junit.Assert;
import org.junit.Test;

public class TaskManagerTest {

    @Test
    public void equalsTask() {
        Task task1 = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Assert.assertEquals(task1, task2);
    }

    @Test
    public void equalsSubTask() {
        Subtask task1 = new Subtask("Убраться в кухне", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, 1);
        Subtask task2 = new Subtask("Убраться в кухне", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, 1);
        Assert.assertEquals(task1, task2);
    }

    @Test
    public void existTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        Assert.assertNotNull(taskManager);
        Assert.assertNotNull(taskManager.getAllTasks());
        Assert.assertNotNull(taskManager.getAllSubtasks());
        Assert.assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void addNewTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Epic epic1 = new Epic("Убраться в квартире", "Убраться в кухне, коридоре и других комнатах.", Status.NEW);
        Subtask subtask1 = new Subtask("Убраться в кухне", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, 1);

        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        Assert.assertEquals(1, taskManager.getAllTasks().size());
        Assert.assertEquals(1, taskManager.getAllEpics().size());
        Assert.assertEquals(1, taskManager.getAllSubtasks().size());

        Assert.assertEquals(task1, taskManager.getTaskById(1));
        Assert.assertEquals(epic1, taskManager.getEpicById(1));
        Assert.assertEquals(subtask1, taskManager.getSubtaskById(1));
    }

    @Test
    public void notModifyTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить: Кефир", Status.NEW);
        taskManager.createTask(task1);
        Assert.assertEquals(task1, taskManager.getTaskById(1));
        Assert.assertNotEquals(task2, taskManager.getTaskById(1));
    }

    @Test
    public void saveHistory() {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить: Кефир", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        Assert.assertEquals(task2, taskManager.getHistory().get(1));
    }
}
