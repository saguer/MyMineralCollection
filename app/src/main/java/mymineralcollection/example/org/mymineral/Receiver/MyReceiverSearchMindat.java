package mymineralcollection.example.org.mymineral.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

public class MyReceiverSearchMindat extends BroadcastReceiver {
    public MyReceiverSearchMindat() {
    }





    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    Context context;

    private SearchMindat_Thread SearchThread;

    @Override
    public void onReceive(Context _context, Intent intent) {
        this.context = _context;
        // Get extra data included in the Intent
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        /** This only maters when the app is first run**/
        //TODO: maybe add it where it was before? - WHERE?
        ////----------------------------------------------
        //Bundle _bundle = new Bundle();
        //_bundle.putBoolean("pause_merge_thread", true);
        //Methods.sendMessage(context, Constant.intentMergeName,_bundle);
        ////----------------------------------------------


        Bundle extras = intent.getExtras();


        String name = extras.getString("item_added_name");

        if(extras.getBoolean("item_added_start_thread",false)) {
            SearchThread = new SearchMindat_Thread(context);
            SearchThread.run();
            Log.d("MySecondReceiver", "item_added_start_thread");
            Log.d("MySecondReceiver", "name: "+name);
        }

    }

    public class SearchMindat_Thread implements Runnable{

        private Context context;
        AutoCompleteDatabaseHandler myDatabaseH;

        public SearchMindat_Thread(Context _context) {
            this.context = _context;
            String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);
            Log.d("MySecondReceiver", "dB Name: " + dbName);
            myDatabaseH = new AutoCompleteDatabaseHandler(context, dbName);
        }


        //todo: this needs to be a Queue...

        @Override
        public void run() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        findInDb(myDatabaseH);
                    } catch (InterruptedException e) {
                        Log.d("MyReceiver","Thread interrupted.");
                    }
                    Log.d("MyReceiver","Thread  exiting.");
                }
            }.start();
        }

        public void findInDb(AutoCompleteDatabaseHandler databaseH) throws InterruptedException {


            myDatabaseH.close();
            databaseH.close();
        }
    }



}
