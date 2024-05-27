public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager defaultTaskManager = Managers.getDefault();

        Task buyProducts = new Task("Сходить в магазин", "Купить: Молоко, хлеб, колбасу", Status.NEW);
        Task cutDog = new Task("Постричь собаку", "В пятницу сходить к грумеру", Status.NEW);

        Epic houseCleaning = new Epic("Убраться в квартире", "Убраться в кухне, коридоре и других комнатах.", Status.NEW);
        int houseCleaningId = defaultTaskManager.createEpic(houseCleaning);
        Epic partyPreparing = new Epic("Подготовиться к вечеринке", "Купить пиццу, купить напитки.", Status.NEW);
        int partyPreparingId = defaultTaskManager.createEpic(partyPreparing);

        Subtask cleanKitchen = new Subtask("Убраться в кухне", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, houseCleaningId);
        Subtask cleanHall = new Subtask("Убраться в коридоре", "Пропылесосить, помыть пол, вытереть пыль", Status.NEW, houseCleaningId);
        Subtask buyPizza = new Subtask("Купить пиццу", "Купить 3 маргариты, 2 четыре сыра.", Status.NEW, partyPreparingId);

        defaultTaskManager.createTask(buyProducts);
        defaultTaskManager.createTask(cutDog);

        defaultTaskManager.createSubtask(cleanKitchen);
        defaultTaskManager.createSubtask(cleanHall);
        defaultTaskManager.createSubtask(buyPizza);

        System.out.println("Список всех задач:");
        printAllTasks(defaultTaskManager);

        System.out.println("Список задач:");
        System.out.println(defaultTaskManager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(defaultTaskManager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(defaultTaskManager.getAllEpics());

        buyProducts.setStatus(Status.IN_PROGRESS);
        defaultTaskManager.updateTask(buyProducts);

        cleanHall.setStatus(Status.DONE);
        defaultTaskManager.updateSubtask(cleanHall);

        cleanKitchen.setStatus(Status.IN_PROGRESS);
        defaultTaskManager.updateSubtask(cleanKitchen);

        buyPizza.setStatus(Status.DONE);
        defaultTaskManager.updateSubtask(buyPizza);

        System.out.println("\n\nСписок задач:");
        System.out.println(defaultTaskManager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(defaultTaskManager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(defaultTaskManager.getAllEpics());

        defaultTaskManager.deleteTaskById(cutDog.getId());
        defaultTaskManager.deleteEpicById(partyPreparing.getId());

        System.out.println("\n\nСписок задач:");
        System.out.println(defaultTaskManager.getAllTasks());

        System.out.println("\nСписок подзадач:");
        System.out.println(defaultTaskManager.getAllSubtasks());

        System.out.println("\nСписок эпиков:");
        System.out.println(defaultTaskManager.getAllEpics());
    }

    private static void printAllTasks(TaskManager taskManager) {
        System.out.println("Задачи:");
        for (int i = 1; i <= taskManager.getAllTasks().size(); i++) {
            System.out.println(taskManager.getTaskById(i));
        }
        System.out.println("Эпики:");
        for (int i = 1; i <= taskManager.getAllEpics().size(); i ++) {
            System.out.println(taskManager.getEpicById(i));

            for (int j = 1; j <= taskManager.getEpicById(i).getSubtaskIds().size(); j++) {
                System.out.println("--> " + taskManager.getSubtaskById(j));
            }
        }
        System.out.println("Подзадачи:");
        for (int i = 1; i < taskManager.getAllSubtasks().size(); i++) {
            System.out.println(taskManager.getSubtaskById(i));
        }

        System.out.println("История:");
        for (int i = 0; i < taskManager.getHistory().size(); i ++) {
            System.out.println(taskManager.getHistory().get(i));
        }
    }
}
