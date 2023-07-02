package Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MultithreadingWithStart {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        List<Future<Integer>> threadsGet = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time

        for (String text : texts) {
            Callable<Integer> callOne = () -> {
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
                return maxSize;
            };
            FutureTask<Integer> futureTaskOne = new FutureTask<>(callOne);
            threadsGet.add(futureTaskOne);
            new Thread(futureTaskOne).start();
        }
        int maxNumber = 0;
        for (Future<Integer> futere : threadsGet) {
            if (futere.get() > maxNumber) {
                maxNumber = futere.get();
            }
        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
        System.out.println("Максимальный интервал значений: " + maxNumber);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}