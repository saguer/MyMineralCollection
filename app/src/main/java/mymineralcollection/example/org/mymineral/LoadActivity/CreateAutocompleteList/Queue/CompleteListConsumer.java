package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import mymineralcollection.example.org.mymineral.Receiver.MyReceiverMergeDbIntoNewDb;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;

/**
 * Created by Santiago on 9/26/2016.
 */
//http://www.java67.com/2012/12/producer-consumer-problem-with-wait-and-notify-example.html

public class CompleteListConsumer implements Runnable {

    private Vector<QueueObjList> sharedQueue = new Vector<>();
    private AutoCompleteDatabaseHandler databaseH;
    private Thread thread = null;
    private static int completeSteps = 0;
    private Context context = null;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    private boolean completeList = false;

    private static int indexHistory = -1;//Needs to be non-zero at the start
    private static int devideBy = 1;

    public CompleteListConsumer(Context _context, Vector<QueueObjList> _sharedQueue, String _name) {
        this.thread = Thread.currentThread();
        this.thread.setName(_name);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());
        this.context = _context;

        this.sharedQueue = _sharedQueue;
        //this.delegate = (ThreadResponse) _activity;

        MyReceiverMergeDbIntoNewDb _myReceiverMergeDbIntoNewDb = new MyReceiverMergeDbIntoNewDb();
        LocalBroadcastManager.getInstance(context).registerReceiver(_myReceiverMergeDbIntoNewDb,new IntentFilter(Constant.intentMergeName));
        //--- --- ---
    }

    QueueObjList _obj;

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        while (sharedQueue.isEmpty()) {
                            synchronized (sharedQueue) {
                                Log.e("QueueTest", "Queue is empty " + Thread.currentThread().getName()
                                        + " is waiting , size: " + sharedQueue.size());

                                Log.d("QueueTest", "indexHistory      : "+indexHistory);
                                Log.d("QueueTest", "completeSteps     : "+completeSteps);
                                Log.d("QueueTest", "completeSteps/10  : "+(completeSteps/10));
                                Log.d("QueueTest", "devideBy          : "+devideBy);

                                if(((completeSteps/devideBy) == indexHistory) && completeList){
                                    Log.d("QueueTest", "Complete QUEUE is done!");
                                    edit = preferences.edit();
                                    edit.putBoolean(Constant._completee_dictionary_Created, true);
                                    edit.apply();
                                    edit.putString(Constant._dB_Name, Constant._dB_Name_complete);
                                    edit.apply();
                                    //delegate.thread_doneAddingDb();
                                    sendMessage();
                                }
                                sharedQueue.wait();
                            }
                        }

                        _obj = sharedQueue.firstElement();
                        devideBy = _obj.getDevideBy();

                        completeList = _obj.isCompleteList();
                        if(completeList){
                            if(!sharedQueue.isEmpty()) {
                                completeSteps++;
                                consumeList(_obj);
                                indexHistory = _obj.getIndex();
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CompleteListConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    private void sendMessage() {
        Log.e("QueueTest", "Sending message!");
        Bundle _bundle = new Bundle();
        _bundle.putBoolean("start_merge_thread", true);
        Methods.sendMessage(context.getApplicationContext(),Constant.intentMergeName,_bundle);
    }

    private boolean consumeList(QueueObjList _obj) throws InterruptedException {
        //wait if queue is empty
        String _dbName = _obj.getdBName();
        Log.e("QueueTest", "- - - CompleteListConsumer- - - -");
        databaseH = new AutoCompleteDatabaseHandler(context.getApplicationContext(),_dbName);
        Log.e("QueueTest", _dbName+" - dB Opened - "+"Queue: " +_obj.getIndex());
        for (Object n :  _obj.getObjList()) {
            String _text = n.toString();
            MyObject myObject = new MyObject(Methods.toTitleCase(_text),false,false);
            myObject.setTableOrigin("complete");
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

}
