package CollectionSynchroniz;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class MainCollect {
    private static Random random = new Random();
    static ArrayBlockingQueue<String> wordA = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> wordB = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> wordC = new ArrayBlockingQueue<>(100);
    private static final int count = 10_000;
    private static final int lengthWord = 100_000;
    public static Thread genWord;

    public static void main(String[] args) {

        genWord = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                String generateText = generateText("abc", lengthWord);
                try {
                    wordA.put(generateText);
                    wordB.put(generateText);
                    wordC.put(generateText);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        genWord.start();

        Thread oneThread = createThread('a', wordA);
        Thread twoThread = createThread('b', wordB);
        Thread threeThread = createThread('c', wordC);
        oneThread.start();
        twoThread.start();
        threeThread.start();

    }

    public static Thread createThread(char letter, ArrayBlockingQueue<String> arrayWord) {
        return new Thread(() -> {
            long countChar = theNumberChar(letter, arrayWord);
            System.out.println("Количесвто букв '" + letter + "' = " + countChar);
        });
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static Long theNumberChar(char letter, ArrayBlockingQueue<String> arrayWord) {
        long maxCount = 0;
        while (genWord.isAlive()) {
            for (String word : arrayWord) {
                long count = word.chars().filter(x -> x == letter).count();
                if (maxCount < count) {
                    maxCount = count;
                }
                try {
                    arrayWord.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return maxCount;
    }
}