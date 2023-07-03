package Synchronization.RobotDelivery;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainRobot {
    private static final int numberOfRoutes = 1000;
    private static final Map<Integer, Integer> sizeToFreqMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < numberOfRoutes; i++) {
            Runnable runOne = () -> {
                int countRinWord = 0;
                String routeRobot = generateRoute("RLRFR", 100);
                for (char element : routeRobot.toCharArray()) {
                    if (element == 'R')
                        countRinWord++;
                }
                sizeToFreq(countRinWord);
                //System.out.println("Количество 'R' в строке: " + routeRobot + " равняется: " + countRinWord);
            };
            Thread threadOne = new Thread(runOne);
            threadOne.start();
            threadOne.join();
        }
        System.out.println(maxCountRinString());
        countRinString();
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
        int maxCount = 0;
        int countNameR = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreqMap.entrySet()) {
            int keyR = entry.getKey();
            int countR = entry.getValue();
            if (countR > maxCount) {
                maxCount = countR;
                countNameR = keyR;
            }
        }
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

    public synchronized static void sizeToFreq(int countR) {
        int value;
        if (sizeToFreqMap.containsKey(countR)) {
            value = sizeToFreqMap.get(countR) + 1;
        } else {
            value = 1;
        }
        sizeToFreqMap.put(countR, value);
    }
}