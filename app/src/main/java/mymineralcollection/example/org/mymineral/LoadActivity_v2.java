package mymineralcollection.example.org.mymineral;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;
import mymineralcollection.example.org.mywebcrawler.Constant;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyFutureTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Priority;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.CompeteList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.SimpleList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.TaskHelper;

public class LoadActivity_v2 extends Activity implements
        SimpleList_AsyncTask.LoadURL_AsyncResponse, CompeteList_AsyncTask.LoadURL_AsyncResponse {

    ThreadPoolExecutor executor;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    private ProgressBar load_progressBar;
    private TextView status_textView, percent_textView;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_activity_v2);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        load_progressBar = (ProgressBar) findViewById(R.id.load_progressBar);
        status_textView = (TextView) findViewById(R.id.status_textView);
        percent_textView = (TextView) findViewById(R.id.percent_textView);

        percent_textView.setVisibility(View.INVISIBLE);
        status_textView.setVisibility(View.INVISIBLE);
        load_progressBar.setVisibility(View.INVISIBLE);

        handler();

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
                    //Log.d("MyWebCrawler", "rejectedExecution, adding to queue");

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

        TaskHelper.execute(new SimpleList_AsyncTask(this,Priority.MEDIUM,"MEDIUM",executor));
        TaskHelper.execute(new CompeteList_AsyncTask(this,Priority.LOW,"LOW",executor));

        task = new MyTask(Priority.MEDIUM,"MEDIUM");
        executor.execute(new MyFutureTask(task));
    }

    private void handler(){
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 2:{

                        Bundle bundle = (Bundle) message.obj;
                        int _steps = bundle.getInt("steps");
                        int _max = bundle.getInt("max");

                        String _text = bundle.getString("text");

                        load_progressBar.setMax(_max);
                        load_progressBar.setProgress(_steps);

                        double percent = ((_steps*1.0)/(_max*1.0))*100;

                        //String txt = _text + ": " +percent;
                        status_textView.setText(_text);
                        percent_textView.setText((int)percent+"%");

                        percent_textView.setVisibility(View.VISIBLE);
                        status_textView.setVisibility(View.VISIBLE);
                        load_progressBar.setVisibility(View.VISIBLE);
                        
                        break;
                    }
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        //simpleListConsumer.activityRunning(false);

        //// TODO: 1/29/2017 - we need to know if the user exists before the queue finishes ,;.
        MyTask task = new MyTask(Priority.HIGHEST,"123-HIGHEST");
        executor.execute(new MyFutureTask(task));

        Log.d("MyWebCrawler", "onPause");
    }

    private AutoCompleteDatabaseHandler databaseH;

    int counterTest = 0;
    @Override
    public void thread_SimpleList(String _text, int cellsListSize, int index) {
        //Log.d("MyWebCrawler", "To add to Db: " + _text + " - index: " + index);



        databaseH = new AutoCompleteDatabaseHandler(this.getApplicationContext(),"test1");
        MyObject myObject = new MyObject(Methods.toTitleCase(_text),false,false);
        myObject.setTableOrigin("simple");
        databaseH.create(myObject);
        databaseH.close();

        progressText = _text;
    }

    @Override
    public void thread_SimpleListUpdateProgressBar(int cellsListSize) {
        counterTest++;
        //thread_UpdateProgressBar(counterTest+"", counterTest, cellsListSize);
        thread_UpdateProgressBar(progressText, counterTest, cellsListSize);
    }


    public void thread_UpdateProgressBar(String _text, int _steps, int _divideBy) {

        Bundle bundle = new Bundle();
        bundle.putInt("steps", _steps);
        bundle.putInt("max",_divideBy);
        bundle.putString("text",_text);

        Message message = mHandler.obtainMessage(2,bundle);
        message.sendToTarget();
    }

    int SimpleList_ThreadCounter = 0;
    String progressText = "";
    @Override
    public void thread_SimpleListCheckStatus(int cellsListSize) {
        SimpleList_ThreadCounter++;
        Log.e("MyWebCrawler", "cellsListSize: "+cellsListSize+ " - SimpleList_ThreadCounter: "+ SimpleList_ThreadCounter +
                " --> "+(cellsListSize== SimpleList_ThreadCounter));

        //thread_UpdateProgressBar(progressText, SimpleList_ThreadCounter, cellsListSize);

        if(cellsListSize== SimpleList_ThreadCounter){
            //boolean _simpleDictionaryCreated = preferences.getBoolean(mymineralcollection.example.org.mymineral.Class.Constant._simple_dictionary_Created,false);
        }

    }

    @Override
    public void thread_CompleteList(String _text, int index) {
        //Log.d("MyWebCrawler", "To add to Db: " + _text + " - index: " + index);

        databaseH = new AutoCompleteDatabaseHandler(this.getApplicationContext(),"test1");
        MyObject myObject = new MyObject(Methods.toTitleCase(_text),false,false);
        myObject.setTableOrigin("complete");
        databaseH.create(myObject);
        databaseH.close();
    }

    int CompleteList_ThreadCounter = 0;
    @Override
    public void thread_CompleteListCheckStatus(int cellsListSize) {
        CompleteList_ThreadCounter++;
        Log.e("MyWebCrawler", "CompleteList_ThreadCounter: "+ CompleteList_ThreadCounter +
                " --> "+((CompleteList_ThreadCounter/ Constant.devideBy)==Constant._links_complete.length));
    }

}
