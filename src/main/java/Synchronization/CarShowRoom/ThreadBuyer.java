package Synchronization.CarShowRoom;

import java.util.ArrayList;
import java.util.List;

public class ThreadBuyer extends Thread {
     List<String> carStore;

    public ThreadBuyer(String name, List<String> carStore) {
        super(name);
        this.carStore = carStore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (carStore) {
                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
                if (carStore.isEmpty()) {
                    try {
                        System.out.println("Машин нет");
                        carStore.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.get(0));
                carStore.remove(0);
            }
        }
    }
}