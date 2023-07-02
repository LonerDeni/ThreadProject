package Synchronization.CarShowRoomAdvanced;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadBuyerHard extends Thread {
    List<String> carStore;
    ReentrantLock locker;
    Condition conditions;


    public ThreadBuyerHard(String name, List<String> carStore, ReentrantLock lock, Condition condition) {
        super(name);
        this.carStore = carStore;
        locker = lock;
        this.conditions = condition;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int carWaiting = 1000;
            try {
                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
                if (carStore.isEmpty()) {
                    System.out.println("Машин нет");
                }
                locker.lock();
                Thread.sleep(carWaiting);
                if (!carStore.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.remove(0));
                } else {
                    System.out.println(Thread.currentThread().getName() + " ушел без машины");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }
    //Тестовый вариант через tryLock
//    @Override
//    public void run() {
//        for (int i = 0; i < 10; i++) {
//            try {
//                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
//                if (carStore.isEmpty()) {
//                    System.out.println("Машин нет");
//                    Thread.sleep(2000);
//                }
//                    if (locker.tryLock(0, TimeUnit.SECONDS)) {
//                        try {
//                            if(!carStore.isEmpty()) {
//                                System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.remove(0));
//                            }
//                        } finally {
//                            locker.unlock();
//                        }
//                    }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}