package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue;

import java.util.concurrent.FutureTask;

/**
 * Created by Santiago on 1/29/2017.
 */
public class MyFutureTask extends FutureTask<MyFutureTask>
        implements Comparable<MyFutureTask> {

    private  MyTask task = null;

    public  MyFutureTask(MyTask task){
        super(task,null);
        this.task = task;
    }

    @Override
    public int compareTo(MyFutureTask another) {
        return task.getPriority() - another.task.getPriority();
    }
}