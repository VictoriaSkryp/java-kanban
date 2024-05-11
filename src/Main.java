import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, Subtask> subtasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();

        Manager manager = new Manager(tasks, subtasks, epics);

        Task buyProducts = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Task cutDog = new Task("Постричь собаку", "В пятницу сходить к грумеру", Status.NEW);

        Epic houseCleaning = new Epic("Убраться в квартире", "Убраться в кухне, коридоре и других комнатах.", Status.NEW);
        int houseCleaningId = manager.createEpic(houseCleaning);
        Epic partyPreparing = new Epic("Подготовиться к вечеринке", "Купить пиццу, купить напитки.", Status.NEW);
        int partyPreparingId = manager.createEpic(partyPreparing);

        Subtask cleanKitchen = new Subtask("Убраться в кухне", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, houseCleaningId);
        Subtask cleanHall = new Subtask("Убраться в коридоре", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, houseCleaningId);
        Subtask buyPizza = new Subtask("Купить пиццу", "Купить 3 маргариты, 2 четыре сыра.", Status.NEW, partyPreparingId);

        manager.createTask(buyProducts);
        manager.createTask(cutDog);

        manager.createSubtask(cleanKitchen);
        manager.createSubtask(cleanHall);
        manager.createSubtask(buyPizza);

        System.out.println("Список задач:");
        System.out.println(manager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(manager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(manager.getAllEpics());

        buyProducts.setStatus(Status.IN_PROGRESS);
        manager.updateTask(buyProducts);

        cleanHall.setStatus(Status.DONE);
        manager.updateSubtask(cleanHall);

        cleanKitchen.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(cleanKitchen);

        buyPizza.setStatus(Status.DONE);
        manager.updateSubtask(buyPizza);

        System.out.println("\n\nСписок задач:");
        System.out.println(manager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(manager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(manager.getAllEpics());

        manager.deleteTask(cutDog.getId());
        manager.deleteEpic(partyPreparing.getId());

        System.out.println("\n\nСписок задач:");
        System.out.println(manager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(manager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(manager.getAllEpics());
    }
}
