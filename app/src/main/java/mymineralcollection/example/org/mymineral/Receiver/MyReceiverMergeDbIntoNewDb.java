package mymineralcollection.example.org.mymineral.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

//https://www.tutorialspoint.com/java/java_thread_control.htm
public class MyReceiverMergeDbIntoNewDb extends BroadcastReceiver{
    private MergeDb_Thread merge;

    public MyReceiverMergeDbIntoNewDb() {

    }

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    Context context;

    @Override
    public void onReceive(Context _context, Intent intent) {
        this.context = _context;
        // Get extra data included in the Intent
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        Bundle extras = intent.getExtras();

        //String message = intent.getStringExtra("message");
        //Log.d("MyReceiver", "Got message: " + message);
        String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);
        Log.d("MyReceiver", "dB Name: " + dbName);
        //databaseH.close();

        if(extras.getBoolean("start_merge_thread",false)) {
            merge = new MergeDb_Thread(context);
            merge.run();
        }

        if(extras.getBoolean("pause_merge_thread",false)) {
            Log.d("MyReceiver", "pause_merge_thread TRUE");
            //Log.d("MySecondReceiver", "pause_merge_thread TRUE");

            if(merge != null) {
                merge.suspend();
            }else{
                Log.d("MyReceiver", "Thread is null");
            }
        }

        if(extras.getBoolean("resume_merge_thread",false)) {
            Log.d("MyReceiver", "resume_merge_thread TRUE");
            if(merge != null) {
                merge.resume();
            }else{
                Log.d("MyReceiver", "Thread is null");
            }
        }



        //if(extras.getBoolean("do_merge_thread",false)) {
        //    Bundle bundle = extras.getBundle("do_merge_thread_bundle");
//
        //    //msg.putString("getMineralName",myObject.getMineralName());
        //    //msg.putBoolean("getPrevSearch",myObject.getPrevSearch());
//
        //    assert bundle != null;
        //    String name = bundle.getString("getMineralName");
        //    String prevSearch = bundle.getString("getPrevSearch");
//
//
        //    Log.d("MyReceiver", "do_merge_thread TRUE");
        //    Log.d("MyReceiver", "name      : " + name);
        //    Log.d("MyReceiver", "prevSearch: " + prevSearch);
        //    //Log.d("MySecondReceiver", "pause_merge_thread TRUE");
//
        //    if(merge != null) {
        //        merge.suspend();
        //    }else{
        //        Log.d("MyReceiver", "Thread is null");
        //    }
        //}

    }

    static boolean suspended = false;

    class MergeDb_Thread implements Runnable{

        private Context context;
        AutoCompleteDatabaseHandler databaseH_complete;
        AutoCompleteDatabaseHandler databaseH_simple;


        public MergeDb_Thread(Context _context) {
            this.context = _context;
            databaseH_simple = new AutoCompleteDatabaseHandler(context, Constant._dB_Name_simple);
            databaseH_complete = new AutoCompleteDatabaseHandler(context, Constant._dB_Name_complete);
        }

        @Override
        public void run() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        //findInDb_orig(databaseH_simple);
                        findInDb(databaseH_complete);
                    } catch (InterruptedException e) {
                        Log.d("MyReceiver","Thread interrupted.");
                    }
                    Log.d("MyReceiver","Thread  exiting.");
                }
            }.start();
        }

        void suspend() {
            suspended = true;
        }

        synchronized void resume() {
            suspended = false;
            notify();
        }

        /**TODO: before adding if the 2 records exist instead of not adding check first for flags.
        or better add everything from the simple to the complete**/

        public void findInDb(AutoCompleteDatabaseHandler databaseH) throws InterruptedException {

            String tableName = AutoCompleteDatabaseHandler.tableName;
            SQLiteDatabase db = databaseH.getReadableDatabase();
            Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
            if (allRows.moveToFirst() ){

                do {

                    String objectName = allRows.getString(allRows.getColumnIndex(AutoCompleteDatabaseHandler.fieldObject_Name));
                    Log.e("MyReceiver", "*- *- *- *- *- *- *- *- *- *- *- *- *- *- *- ");
                    Log.d("MyReceiver", "objectName   : " + objectName + " - " + databaseH_simple.checkIfExists(objectName));

                    MyObject myObject = databaseH_complete.findInDb(objectName);

                    if (!databaseH_simple.checkIfExists(objectName)) {

                        //Add
                        databaseH_simple.create(myObject);
                    }else{
                        Log.d("MyReceiver", "*- *- *- *- *- *- *- *- *- *- *- *- *- *- *- ");
                        //Log.d("MyReceiver", "myObject: "+myObject.getId());
                        myObject = databaseH_simple.findInDb(objectName);
                        Log.e("MyReceiver", "myObject: "+myObject.getPrevSearch());
                        Log.d("MyReceiver", "*- *- *- *- *- *- *- *- *- *- *- *- *- *- *- ");
                        databaseH_simple.update(myObject);
                    }

                    synchronized(MergeDb_Thread.this) {
                        while(suspended) {
                            Log.e("MyReceiver", "pause_merge_thread WAITING...");
                            wait();
                        }
                    }


                    //TimeUnit.SECONDS.sleep(1);

                } while (allRows.moveToNext());
            }
            allRows.close();
            db.close();

            databaseH.close();
            databaseH_simple.close();
            databaseH_complete.close();
        }
        public void findInDb_orig(AutoCompleteDatabaseHandler databaseH) throws InterruptedException {

            SQLiteDatabase db = databaseH.getReadableDatabase();
            Cursor allRows  = db.rawQuery("SELECT * FROM " + AutoCompleteDatabaseHandler.tableName, null);
            if (allRows.moveToFirst() ){

                do {

                    String objectName = allRows.getString(allRows.getColumnIndex(AutoCompleteDatabaseHandler.fieldObject_Name));
                    Log.e("MyReceiver", "*- *- *- *- *- *- *- *- *- *- *- *- *- *- *- ");
                    Log.d("MyReceiver", "objectName   : " + objectName + " - " + databaseH_complete.checkIfExists(objectName));

                    MyObject myObject = databaseH_simple.findInDb(objectName);
                    Log.d("MyReceiver", "myObject: "+myObject.getId());
                    Log.d("MyReceiver", "myObject: "+myObject.getPrevSearch());
                    Log.d("MyReceiver", "myObject: "+myObject.getInPersonaldb());
                    Log.d("MyReceiver", "myObject: "+myObject.getMineralName());


                    //databaseH_complete.createOrUpdate(myObject);

                    //if(!databaseH_complete.checkIfExists(objectName)){
////
                    //    //TODO: run the app like new, search for Azurite BEFORE the complete list is finished
                    //    //Add delay to the start of that thread
////
                    //    databaseH_complete.createOrUpdate(myObject);
                    //    Log.e("MyReceiver", "Does not Exist Add");
                    //}
                    //else{
                    //    if(myObject.getPrevSearch()){
                    //        databaseH_complete.updateCounterAndPercentage(myObject);
                    //        Log.e("MyReceiver", "Does Update");
                    //    }
                    //}

                    synchronized(MergeDb_Thread.this) {
                        while(suspended) {
                            Log.e("MyReceiver", "pause_merge_thread WAITING...");
                            wait();
                        }
                    }


                    //TimeUnit.SECONDS.sleep(1);

                } while (allRows.moveToNext());
            }
            allRows.close();
            db.close();

            databaseH.close();
            databaseH_simple.close();
            databaseH_complete.close();
        }
    }
}
