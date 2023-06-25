package WorkWithThread.DialogThread;

public class MyThreadDialog extends Thread {

    public MyThreadDialog(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(2500);
                System.out.println("Всем привет!" + Thread.currentThread().getName());
            }
        } catch (InterruptedException err) {

        } finally {
            System.out.printf("%s завершен\n", getName());
        }
    }
}