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

    public static void main(String[] args) {

        new Thread(() -> {
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
        }).start();

        new Thread(() -> {
            System.out.println(theNumberChar('a', wordA));
        }).start();

        new Thread(() -> {
            System.out.println(theNumberChar('b', wordB));
        }).start();

        new Thread(() -> {
            System.out.println(theNumberChar('c', wordC));
        }).start();
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static String theNumberChar(char letter, ArrayBlockingQueue<String> arrayWord) {
        long maxCount = 0;
        String maxWord = null;
        int j = 0;
        while (j < count){
            for (String word : arrayWord) {
                long count = word.chars().filter(x -> x == letter).count();
                if (maxCount < count) {
                    maxCount = count;
                    maxWord = word;
                }
                try {
                    arrayWord.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                j++;
            }
        }
        return "Количесвто букв " + "'" + letter + "'" + " = " + maxCount + " , слово - " + maxWord;
    }
}