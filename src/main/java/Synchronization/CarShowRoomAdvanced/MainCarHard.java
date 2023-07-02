package Synchronization.CarShowRoomAdvanced;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MainCarHard {
    public static void main(String[] args) {
        Random random = new Random();
        List<String> carStore = new ArrayList<>();
        List<ManufacturerCar> cars = new ArrayList<>();
        ReentrantLock locker = new ReentrantLock(true);
        Condition condition = locker.newCondition();
        cars.add(new BMW("BMW", "M3"));
        cars.add(new BMW("BMW", "X5"));
        cars.add(new Toyota("Toyota", "Corola"));
        cars.add(new Toyota("Toyota", "RAV4"));
        cars.add(new Ford("Ford", "Focus"));
        cars.add(new Ford("Ford", "Mustang"));

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    int carWaitingInStore = 1000;
                    int modelCar = random.nextInt(cars.size());
                    System.out.println("Производитель: " + cars.get(modelCar).getNameCar() + " выпустил 1 авто " + cars.get(modelCar).getNameModel());
                    carStore.add(cars.get(modelCar).getNameCar() + " " + cars.get(modelCar).getNameModel());
                    Thread.sleep(carWaitingInStore);
                } catch (InterruptedException es) {
                    return;
                }
            }
        }).start();

        new ThreadBuyerHard("Покупатель 1", carStore,locker,condition).start();
        new ThreadBuyerHard("Покупатель 2", carStore,locker,condition).start();
        new ThreadBuyerHard("Покупатель 3", carStore,locker,condition).start();
    }
}