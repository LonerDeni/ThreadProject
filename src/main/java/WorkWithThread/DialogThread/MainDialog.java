package WorkWithThread.DialogThread;


public class MainDialog {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup mainGroup = new ThreadGroup("Главный поток");
        Thread threadOne = new MyThreadDialog(mainGroup, "Поток Один");
        Thread threadTwo = new MyThreadDialog(mainGroup, "Поток Два");
        Thread threadThree = new MyThreadDialog(mainGroup, "Поток Три");
        Thread threadFour = new MyThreadDialog(mainGroup, "Поток Четыре");

        System.out.println("Создаю потоки");
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        Thread.sleep(15000);
        System.out.println("Завершаю все потоки.");
        mainGroup.interrupt();
    }
}