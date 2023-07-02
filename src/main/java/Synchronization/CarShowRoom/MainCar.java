package Synchronization.CarShowRoom;

import WorkWithThread.DialogThread.MyThreadDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class MainCar {
    public static void main(String[] args) {
        Random random = new Random();
        List<String> carStore = new ArrayList<>();
        List<ManufacturerCar> cars = new ArrayList<>();
        cars.add(new BMW("BMW", "M3"));
        cars.add(new BMW("BMW", "X5"));
        cars.add(new Toyota("Toyota", "Corola"));
        cars.add(new Toyota("Toyota", "RAV4"));
        cars.add(new Ford("Ford", "Focus"));
        cars.add(new Ford("Ford", "Mustang"));

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (carStore) {
                    int modelCar = random.nextInt(cars.size());
                    System.out.println("Производитель: " + cars.get(modelCar).getNameCar() + " выпустил 1 авто " + cars.get(modelCar).getNameModel());
                    carStore.add(cars.get(modelCar).getNameCar() + " " + cars.get(modelCar).getNameModel());
                    carStore.notify();
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                synchronized (carStore) {
//                    System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
//                    if (carStore.isEmpty()) {
//                        try {
//                            System.out.println("Машин нет");
//                            carStore.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто: " + carStore.get(0));
//                    carStore.remove(0);
//                }
//            }
//        },"Покупатель 1").start();

        new ThreadBuyer("Покупатель 1", carStore).start();
        new ThreadBuyer("Покупатель 2", carStore).start();
        new ThreadBuyer("Покупатель 3", carStore).start();
    }
}