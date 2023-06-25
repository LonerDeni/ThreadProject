package WorkWithThread.CalcFork;

import java.util.concurrent.RecursiveTask;

public class ForkTest extends RecursiveTask<Integer> {
    final int seqThres = 1000;
    int[] data;
    int star, end;
    ForkTest(int[] vals, int s, int e){
        data = vals;
        star = s;
        end = e;
    }

    protected  Integer compute(){
        int sum = 0;
        if((end - star) < seqThres){
            for(int i = star; i < end; i++){
                sum += data[i];
            }
        } else {
            int middle = (star + end) / 2;
            ForkTest forkTestA = new ForkTest(data,star,middle);
            ForkTest forkTestB = new ForkTest(data,middle,end);
            forkTestA.fork();
            forkTestB.fork();
            sum = forkTestA.join() + forkTestB.join();
        }
        return sum;
    }
}
