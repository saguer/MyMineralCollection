package mymineralcollection.example.org.mymineral.LoadActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ProgressBar;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.TaskHelper;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CompleteList.CreateListCompleteContactWiki_Thread;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CompleteList.CreateListComplete_Thread;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CreateListMethods;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.LoadURL_AsyncTask;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue.CompleteListConsumer;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue.QueueObjList;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue.SimpleListConsumer;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.SimpleList.CreateListSimple_Thread;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.StartActivity.StartActivity;

//http://stackoverflow.com/questions/20862258/android-how-to-get-appcompat-translucent-type-theme-with-support-actionbar

public class LoadActivity extends Activity implements LoadURL_AsyncTask.LoadURL_AsyncResponse,
        CreateListSimple_Thread.ThreadResponse,
        SimpleListConsumer.ThreadResponse,
        CreateListComplete_Thread.ThreadResponse{

    private ProgressBar load_progressBar;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private Handler mHandler;
    private SimpleListConsumer simpleListConsumer;
    private final int devideBy = 10;
    private final Vector<QueueObjList> simpleQueue = new Vector<>();
    private final Vector<QueueObjList> completeQueue = new Vector<>();

    public static boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        initialized = true;

        Log.e("lifeCycle", "LoadActivity: onCreate");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        load_progressBar = (ProgressBar) findViewById(R.id.load_progressBar);

        simpleListConsumer = new SimpleListConsumer(this,simpleQueue, "SimpleListConsumer");
        simpleListConsumer.activityRunning(true);
        simpleListConsumer.run();

        //--- Start Receivers ---
        //MyReceiverSearchMindat _myReceiverSearchMindat = new MyReceiverSearchMindat();
        //LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(_myReceiverSearchMindat,new IntentFilter(Constant.intentItemAdded));

        //MyReceiverMergeDbIntoNewDb _myReceiverMergeDbIntoNewDb = new MyReceiverMergeDbIntoNewDb();
        //LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(_myReceiverMergeDbIntoNewDb,new IntentFilter(Constant.intentMergeName));
        //--- --- ---

        handler();

        boolean _simpleDictionaryCreated = preferences.getBoolean(Constant._simple_dictionary_Created,false);
        //_dictionary_Created = false;
        //TODO:uncomment.
        if(_simpleDictionaryCreated) {

            boolean _completeDictionaryCreated = preferences.getBoolean(Constant._completee_dictionary_Created,false);

            if(!_completeDictionaryCreated) {
                loadCompleteThread();
            }

            launchActivity(LoadActivity.this);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TaskHelper.execute(new LoadURL_AsyncTask(LoadActivity.this, Constant._wikiCompleteListSimple));
                    loadCompleteThread();
                }
            }, 100);
        }

        //TODO: Call when the complete list is done
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
    }


    private void loadCompleteThread(){
        CompleteListConsumer completeListConsumer = new CompleteListConsumer(this, completeQueue, "CompleteListConsumer");
        completeListConsumer.run();
        new CreateListCompleteContactWiki_Thread(LoadActivity.this,devideBy).run();
    }

    private void handler(){
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:{
                        Methods.progressBar(load_progressBar, devideBy, Integer.valueOf(message.obj.toString()));
                        break;
                    }
                    case 2:{

                        Bundle bundle = (Bundle) message.obj;
                        int _steps = bundle.getInt("steps");
                        int _max = bundle.getInt("max");

                        Methods.progressBar(load_progressBar, _max, _steps);
                        break;
                    }
                }
            }
        };
    }

    @Override
    public void loadURL_Result(final Document _doc) {

        new Thread() {
            @Override
            public void run() {
                Elements elements = _doc.body().select("ul > li");
                int numOfElements = elements.size();

                List<int[]> cellsList = CreateListMethods.breakIntoSteps(numOfElements, devideBy);

                for (int i =  0; i < cellsList.size(); i++) {
                    new CreateListSimple_Thread(LoadActivity.this, elements, cellsList.get(i), i).run();
                }
            }
        }.start();

        Log.d("myApp", " DONE ");
    }

    @Override
    public void thread_UpdateProgressBar(int _steps, int _devideBy) {

        Bundle bundle = new Bundle();
        bundle.putInt("steps", _steps);
        bundle.putInt("max",_devideBy);

        Message message = mHandler.obtainMessage(2,bundle);
        message.sendToTarget();
    }

    @Override
    public void thread_doneAddingDb() {
        launchActivity(LoadActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleListConsumer.activityRunning(false);

        //Log.d("myApp", "preferences.contains(Constant._simple_dictionary_Created): "+preferences.contains(Constant._simple_dictionary_Created));
        Log.d("myApp", "onPause()");

        if(!preferences.contains(Constant._simple_dictionary_Created)) {
            edit = preferences.edit();
            edit.putBoolean(Constant._simple_dictionary_Created, false);
            edit.apply();
        }
    }


    public void launchActivity(Context packageContext){

        Intent myIntent = new Intent(packageContext, StartActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        packageContext.startActivity(myIntent);

        finish();
    }

    @Override
    public void thread_CompleteListComplete(ArrayList<String> result, int index) {
        synchronized (completeQueue) {
            QueueObjList _obj = new QueueObjList(result);
            _obj.setDevideBy(devideBy);
            _obj.setIndex(index);
            _obj.setSimpleList(false);
            _obj.setCompleteList(true);
            _obj.setdBName(Constant._dB_Name_complete);
            completeQueue.add(_obj);
            completeQueue.notifyAll();

            Log.e("CreateListComplete", "thread_CompleteListComplete: " + index + " - devideBy: "+devideBy);
        }
    }

    @Override
    public void thread_SimpleListComplete(ArrayList<String> result, int index) {
        synchronized (simpleQueue) {
            QueueObjList _obj = new QueueObjList(result);
            _obj.setDevideBy(devideBy);
            _obj.setIndex(index);
            _obj.setSimpleList(true);
            _obj.setCompleteList(false);
            _obj.setdBName(Constant._dB_Name_simple);
            simpleQueue.add(_obj);
            simpleQueue.notifyAll();
        }
    }
}
