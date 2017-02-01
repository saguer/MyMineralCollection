package mymineralcollection.example.org.mywebcrawler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.CompeteList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyFutureTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Priority;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.SimpleList_AsyncTask;

public class MainActivity extends AppCompatActivity implements
        SimpleList_AsyncTask.LoadURL_AsyncResponse,
        CompeteList_AsyncTask.LoadURL_AsyncResponse {

    ThreadPoolExecutor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PriorityBlockingQueue queue = new PriorityBlockingQueue();
        executor = new ThreadPoolExecutor(0,1,
                1000, TimeUnit.MILLISECONDS,queue);

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
            /*
             * This does the actual put into the queue. Once the max threads
             * have been reached, the tasks will then queue up.
             */
                    Log.d("MyWebCrawler", "rejectedExecution, adding to queue");

                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });


        MyTask task = new MyTask(Priority.LOWEST,"LOWEST");
        executor.execute(new MyFutureTask(task));
        task = new MyTask(Priority.HIGH,"High");
        executor.execute(new MyFutureTask(task));

        TaskHelper.execute(new SimpleList_AsyncTask(this,executor));
        TaskHelper.execute(new CompeteList_AsyncTask(this,executor));

        task = new MyTask(Priority.MEDIUM,"MEDIUM");
        executor.execute(new MyFutureTask(task));


        //TaskHelper.execute(new CompeteList_AsyncTask(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //simpleListConsumer.activityRunning(false);

        Log.d("MyWebCrawler", "onPause");
    }



    @Override
    public void thread_SimpleList(String result, int cellsListSize, int index) {
        Log.d("MyWebCrawler", "To add to Db: " + result + " - index: " + index);
    }

    @Override
    public void thread_SimpleListUpdateProgressBar(int cellsListSize) {

    }

    int SimpleList_ThreadCounter = 0;
    @Override
    public void thread_SimpleListCheckStatus(int cellsListSize) {
        SimpleList_ThreadCounter++;
        Log.e("MyWebCrawler", "cellsListSize: "+cellsListSize+ " - SimpleList_ThreadCounter: "+ SimpleList_ThreadCounter +
                " --> "+(cellsListSize== SimpleList_ThreadCounter));


    }

    @Override
    public void thread_CompleteList(String result, int index) {
        Log.d("MyWebCrawler", "To add to Db: " + result + " - index: " + index);
    }

    int CompleteList_ThreadCounter = 0;
    @Override
    public void thread_CompleteListCheckStatus(int cellsListSize) {
        CompleteList_ThreadCounter++;
        Log.e("MyWebCrawler", "CompleteList_ThreadCounter: "+ CompleteList_ThreadCounter +
                " --> "+((CompleteList_ThreadCounter/ Constant.devideBy)==Constant._links_complete.length));
    }




}
