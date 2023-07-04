package Synchronization.RobotDeliverywithStar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainRobotStar {
    private static final int numberOfRoutes = 1000;
    private static final Map<Integer, Integer> sizeToFreqMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable runTwo = () -> {
            while (!Thread.interrupted()) {
                synchronized (sizeToFreqMap) {
                    try {
                        sizeToFreqMap.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Поток был прерван");
                        ;
                    }
                    int countNameR = Collections.max(sizeToFreqMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                    int maxCount = Collections.max(sizeToFreqMap.entrySet(), Map.Entry.comparingByValue()).getValue();
                    System.out.printf("Лидер по количеству повторений %d встретился %d раз\n", countNameR, maxCount);
                }
            }
        };
        Thread threadTwo = new Thread(runTwo);
        threadTwo.start();

        for (int i = 0; i < numberOfRoutes; i++) {
            Runnable runOne = () -> {
                int countRinWord = 0;
                String routeRobot = generateRoute("RLRFR", 100);
                for (char element : routeRobot.toCharArray()) {
                    if (element == 'R')
                        countRinWord++;
                }
                sizeToFreq(countRinWord);
            };
            Thread threadOne = new Thread(runOne);
            threadOne.start();
        }

        threadTwo.interrupt();
        System.out.println(maxCountRinString());
        //countRinString();
    }

    public static void countRinString() {
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreqMap.entrySet()) {
            int keyR = entry.getKey();
            int countR = entry.getValue();
            System.out.printf(" - %d (%d раз)\n", keyR, countR);
        }
    }

    public static String maxCountRinString() {
        int countNameR = Collections.max(sizeToFreqMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        int maxCount = Collections.max(sizeToFreqMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        return "Самое частое количество повторений " + countNameR + " (встретилось " + maxCount + " раз)";
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void sizeToFreq(int countR) {
        synchronized (sizeToFreqMap) {
            int value;
            if (sizeToFreqMap.containsKey(countR)) {
                value = sizeToFreqMap.get(countR) + 1;
            } else {
                value = 1;
            }
            sizeToFreqMap.put(countR, value);
            sizeToFreqMap.notify();
        }
    }
}