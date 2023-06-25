package WorkWithThread.DialogThreadWithCounter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(new MyThread("Поток 1"));
        tasks.add(new MyThread("Поток 2"));
        tasks.add(new MyThread("Поток 3"));
        tasks.add(new MyThread("Поток 4"));
        int result = threadPool.invokeAny(tasks);
        System.out.println(result);
        threadPool.shutdown();
    }
}