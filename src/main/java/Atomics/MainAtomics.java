package Atomics;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainAtomics {
    private static Random random = new Random();
    private static AtomicInteger length3 = new AtomicInteger(0);
    private static AtomicInteger length4 = new AtomicInteger(0);
    private static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String word : texts) {
                int wordLength = word.length();
                boolean palindrome = word
                        .equalsIgnoreCase(new StringBuilder(word)
                                .reverse().toString());
                if (palindrome) {
                    if (wordLength == 3) {
                        length3.getAndAdd(1);
                    } else if (wordLength == 4) {
                        length4.getAndAdd(1);
                    } else {
                        length5.getAndAdd(1);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String word : texts) {
                int wordLength = word.length();
                boolean identicalWord = true;

                for (int i = 1; i < wordLength; i++) {
                    char at = word.charAt(i - 1);
                    if (at != word.charAt(i)) {
                        identicalWord = false;
                        break;
                    }
                }
                if (identicalWord) {
                    if (wordLength == 3) {
                        length3.getAndAdd(1);
                    } else if (wordLength == 4) {
                        length4.getAndAdd(1);
                    } else {
                        length5.getAndAdd(1);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String word : texts) {
                int wordLength = word.length();
                boolean alphabetWord = true;
                for (int i = 0; i < wordLength - 1; i++) {
                    char at = word.charAt(i);
                    if (at != word.charAt(i + 1)) {
                        if (at != word.charAt(i + 1) - 1) {
                            alphabetWord = false;
                            break;
                        }
                    }
                }
                if (alphabetWord) {
                    if (wordLength == 3) {
                        length3.getAndAdd(1);
                    } else if (wordLength == 4) {
                        length4.getAndAdd(1);
                    } else {
                        length5.getAndAdd(1);
                    }
                }
            }
        }).start();

        System.out.printf("\nКрасивых слов с длиной 3: %s шт", length3);
        System.out.printf("\nКрасивых слов с длиной 4: %s шт", length4);
        System.out.printf("\nКрасивых слов с длиной 5: %s шт", length5);
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}