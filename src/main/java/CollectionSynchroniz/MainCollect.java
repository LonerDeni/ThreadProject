package CollectionSynchroniz;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class MainCollect {
    private static Random random = new Random();
    static ArrayBlockingQueue<String> wordA = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> wordB = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> wordC = new ArrayBlockingQueue<>(100);
    private static int count = 10_000;

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                String generateText = generateText("abc", 100_000);
                try {
                    wordA.put(generateText);
                    wordB.put(generateText);
                    wordC.put(generateText);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            int j = 0;
            int maxCount = 0;
            String maxWord = null;
            while (j < count) {
                if (wordA.isEmpty()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (String word : wordA) {
                    int count = 0;
                    for (char letter : word.toCharArray()) {
                        if (letter == 'a')
                            count += 1;
                    }
                    if (maxCount < count) {
                        maxCount = count;
                        maxWord = word;
                    }
                    try {
                        wordA.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j += 1;
                }
            }
            System.out.printf("\nКоличесвто букв 'a' = %d , слово - %s", maxCount, maxWord);
        }).start();

        new Thread(() -> {
            int j = 0;
            long maxCount = 0;
            String maxWord = null;
            while (j < count) {
                if (wordB.isEmpty()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (String word : wordB) {
                    long countB = word.chars().filter(x -> x == 'b').count();
                    if (maxCount < countB) {
                        maxCount = countB;
                        maxWord = word;
                    }
                    try {
                        wordB.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j += 1;
                }
            }
            System.out.printf("\nКоличесвто букв 'b' = %d , слово - %s", maxCount, maxWord);
        }).start();

        new Thread(() -> {
            int j = 0;
            long maxCount = 0;
            String maxWord = null;
            while (j < count) {
                if (wordC.isEmpty()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (String word : wordC) {
                    long countB = word.chars().filter(x -> x == 'c').count();
                    if (maxCount < countB) {
                        maxCount = countB;
                        maxWord = word;
                    }
                    try {
                        wordC.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j += 1;
                }
            }
            System.out.printf("\nКоличесвто букв 'c' = %d , слово - %s", maxCount, maxWord);
        }).start();
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}