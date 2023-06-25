package WorkWithThread.DialogThreadWithCounter;

import java.util.concurrent.Callable;

public class MyThread implements Callable<Integer> {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public Integer call() {
        int count = 0;
        try {
            for(int i = 0; i < 7; i++) {
                Thread.sleep(2500);
                Thread.currentThread().setName(name);
                System.out.println("Всем привет!" + Thread.currentThread().getName());
                count++;
            }
        } catch (InterruptedException err) {

        } finally {
            System.out.printf("%s завершен\n", Thread.currentThread().getName());
        }
        return count;
    }
}