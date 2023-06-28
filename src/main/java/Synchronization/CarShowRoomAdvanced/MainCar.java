package Synchronization.CarShowRoomAdvanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MainCar {
    public static void main(String[] args) {
        Random random = new Random();
        List<String> carStore = new ArrayList<>();
        List<ManufacturerCar> cars = new ArrayList<>();
        ReentrantLock locker = new ReentrantLock();
        Condition condition = locker.newCondition();
        cars.add(new BMW("BMW", "M3"));
        cars.add(new BMW("BMW", "X5"));
        cars.add(new Toyota("Toyota", "Corola"));
        cars.add(new Toyota("Toyota", "RAV4"));
        cars.add(new Ford("Ford", "Focus"));
        cars.add(new Ford("Ford", "Mustang"));

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    int modelCar = random.nextInt(cars.size());
                    System.out.println("Производитель: " + cars.get(modelCar).getNameCar() + " выпустил 1 авто " + cars.get(modelCar).getNameModel());
                    carStore.add(cars.get(modelCar).getNameCar() + " " + cars.get(modelCar).getNameModel());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException es) {
                    return;
                }
            }
        }).start();

        new ThreadBuyer("Покупатель 1", carStore,locker,condition).start();
        new ThreadBuyer("Покупатель 2", carStore,locker,condition).start();
        //new ThreadBuyer("Покупатель 3", carStore,locker).start();
    }
}