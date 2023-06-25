package WorkWithThread.CalcFork;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class MainFork {
    public static void main(String[] args) {
        int size = 100;
        ForkJoinPool fjp = new ForkJoinPool(2);
        ForkTest task = new ForkTest(getInitArray(size),0,getInitArray(size).length);
        long start = System.currentTimeMillis();
        int summa = fjp.invoke(task);
        long finish = System.currentTimeMillis() - start;
        System.out.println("Время выполненя: " + finish);
        System.out.println("Сумма массива: " + summa);
        System.out.println("Среднее значение: " + summa / size);
          oneThread(getInitArray(size));
    }
    public static int[] getInitArray(int size) {
        int numArr[] = new int[size];
        Random random = new Random();
        IntStream.range(0, size)
                .forEach(i -> numArr[i] = random.nextInt(100));
        return numArr;
    }
    public static void oneThread(int[] array) {
        long sum = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println("Время выполнения в одном потоке: " + finish);
        System.out.println("Сумма массива: " + sum);
        System.out.println("Среднее значение: " + sum / array.length);
    }
}
