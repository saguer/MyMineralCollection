package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;

/**
 * Created by Santiago on 9/26/2016.
 */
//http://www.java67.com/2012/12/producer-consumer-problem-with-wait-and-notify-example.html

public class SimpleListConsumer implements Runnable {

    private Vector<QueueObjList> sharedQueue = new Vector<>();
    private AutoCompleteDatabaseHandler databaseH;
    private Thread thread = null;
    private static int steps = 0;
    private Context activity = null;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private boolean activityRunning = false;

    private boolean simpleList = false;
    private static int devideBy = 1;

    public SimpleListConsumer(Context _activity, Vector<QueueObjList> _sharedQueue, String _name) {
        this.thread = Thread.currentThread();
        this.thread.setName(_name);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(_activity.getApplicationContext());
        this.activity = _activity;

        this.sharedQueue = _sharedQueue;
        this.delegate = (ThreadResponse) _activity;
    }

    QueueObjList _obj;

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                while (activityRunning) {
                    try {
                        while (sharedQueue.isEmpty()) {
                            synchronized (sharedQueue) {
                                Log.e("QueueTest", "Queue is empty " + Thread.currentThread().getName()
                                        + " is waiting , size: " + sharedQueue.size());

                                if((steps == devideBy) && simpleList){
                                    Log.d("QueueTest", "Simple QUEUE is done!");
                                    edit = preferences.edit();
                                    edit.putBoolean(Constant._simple_dictionary_Created, true);
                                    edit.apply();
                                    delegate.thread_doneAddingDb();
                                }
                                sharedQueue.wait();
                            }
                        }

                        _obj = sharedQueue.firstElement();
                        devideBy = _obj.getDevideBy();

                        simpleList = _obj.isSimpleList();
                        if(simpleList){

                            if(!sharedQueue.isEmpty()) {
                                steps++;
                                consumeList(_obj);
                                delegate.thread_UpdateProgressBar(steps,devideBy);
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SimpleListConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                Log.d("QueueTest", "Out of Loop");
            }
        }.start();
    }

    private boolean consumeList(QueueObjList _obj) throws InterruptedException {
        //wait if queue is empty
        String _dbName = _obj.getdBName();
        Log.e("QueueTest", "- - - SimpleListConsumer - - - - -");
        databaseH = new AutoCompleteDatabaseHandler(activity.getApplicationContext(),_dbName);
        Log.e("QueueTest", _dbName+" - dB Opened - "+"Queue: " +_obj.getIndex());
        for (Object n :  _obj.getObjList()) {
            String _text = n.toString();
            MyObject myObject = new MyObject(Methods.toTitleCase(_text),false,false);
            myObject.setTableOrigin("simple");
            databaseH.create(myObject);
        }
        databaseH.close();
        Log.e("QueueTest", _dbName+" - dB Closed - "+"Queue: " +_obj.getIndex());
        Log.e("QueueTest", "- - - - - - - - - - - - - - - - -");

        //Otherwise Consume element and notify waiting producer
        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            sharedQueue.remove(0);
            return false;
        }
    }

    public ThreadResponse delegate = null;

    public void activityRunning(boolean _state) {
        activityRunning = _state;
    }

    public interface ThreadResponse {
        void thread_doneAddingDb();
        void thread_UpdateProgressBar(int _steps, int _devideBy);
    }

}
