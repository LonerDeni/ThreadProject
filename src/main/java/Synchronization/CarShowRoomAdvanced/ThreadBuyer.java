package Synchronization.CarShowRoomAdvanced;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadBuyer extends Thread {
    List<String> carStore;
    ReentrantLock locker;
    Condition conditions;


    public ThreadBuyer(String name, List<String> carStore, ReentrantLock lock, Condition condition) {
        super(name);
        this.carStore = carStore;
        locker = lock;
        this.conditions = condition;
    }

//    @Override
//    public void run() {
//            for (int i = 0; i < 10; i++) {
//                try {
//                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
//                if (carStore.isEmpty()) {
//                    System.out.println("Машин нет");
//                    locker.lock();
//                    conditions.await(3, TimeUnit.SECONDS);
//                }
//                    //conditions.signal();
//                    //int index = carStore.size() - 1;
//                    System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.remove(0));
//                } catch (InterruptedException e) {
//                    e.getMessage();
//                } finally {
//                    locker.unlock();
//                }
//            }
//    }
//    @Override
//    public void run() {
//        boolean ans = locker.tryLock();
//        for (int i = 0; i < 10; i++) {
//            try {
//                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
//                if (carStore.isEmpty()) {
//                    System.out.println("Машин нет");
//                    locker.lock();
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.getMessage();
//                    }
//                }
//                System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.remove(0));
//            } finally {
//                locker.unlock();
//            }
//        }
//    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
                locker.lock();
                if (carStore.isEmpty()) {
                    System.out.println("Машин нет");
                    conditions.await();
                }
                System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.remove(0));
            } catch (InterruptedException e) {
                e.getMessage();
            } finally {
                locker.unlock();
            }
        }
    }
}