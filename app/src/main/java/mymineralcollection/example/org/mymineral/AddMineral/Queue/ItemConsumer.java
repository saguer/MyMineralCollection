package mymineralcollection.example.org.mymineral.AddMineral.Queue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.Crawler.AddUpdateDb;
import mymineralcollection.example.org.mymineral.Crawler.Crawler;
import mymineralcollection.example.org.mymineral.Crawler.CrawlerMethods;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;

/**
 * Created by Santiago on 10/7/2016.
 */
public class ItemConsumer implements Runnable {

    private Vector<QueueObjItem> sharedQueue = new Vector<>();
    private Thread thread = null;
    private SharedPreferences preferences;
    private boolean activityRunning = false;

    private boolean queueEmpty = true;



    private Context context;

    public ItemConsumer(Context _context, Vector<QueueObjItem> _sharedQueue, String _name) {
        this.thread = Thread.currentThread();
        this.thread.setName(_name);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());

        this.sharedQueue = _sharedQueue;
        //this.delegate = _activity;

        //String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);
        Log.e("QueueTest", "ItemConsumer");
        //databaseH =  new AutoCompleteDatabaseHandler(_context,dbName);

        this.context = _context;

        MyReceiverItemConsumer _myReceiverMergeDbIntoNewDb = new MyReceiverItemConsumer();
        LocalBroadcastManager.getInstance(context).registerReceiver(_myReceiverMergeDbIntoNewDb,new IntentFilter(Constant.intentItemConsumer));
    }

    QueueObjItem _obj;

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                while (activityRunning) {
                    try {
                        while (sharedQueue.isEmpty()) {

                            synchronized (sharedQueue) {
                                //Log.e("ItemConsumer", "Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + sharedQueue.size());

                                //We enter this loop only when the Queue is empty
                                //We need to have !queueEmpty before we set it to true
                                //This ensures that we send only one message

                                if(!queueEmpty){
                                    Bundle _bundle;
                                    Log.d("ItemConsumer", "queue_empty: " + !queueEmpty);
                                    _bundle = new Bundle();
                                    Log.d("ItemConsumer", "MSG Sent!");
                                    _bundle.putBoolean("queue_empty", !queueEmpty);
                                    Methods.sendMessage(context, Constant.intentAddImageActivity,_bundle);
                                }

                                queueEmpty = true;


                                if(updateDb){
                                    //----------------------------------------------
                                    Bundle _bundle = new Bundle();
                                    _bundle.putBoolean("pause_merge_thread", true);
                                    Methods.sendMessage(context, Constant.intentMergeName,_bundle);
                                    //----------------------------------------------
                                    AddUpdateDb.updateAutoCompleteDb(bundleArrayList,context);
                                    AddUpdateDb.addToLocalDb(bundleArrayList,context);
                                    //TODO: send message to merge_thread that if it has not finished we need to add a item
                                    //TODO: merge receiver is not working properly too many bugs...
                                    //if a search is made before the merge is done the flags are not passed.
                                    //Ex Azurite will appear as it was not searched, when in fact it was.
                                    //Also some minerals are not appearing. Ex Amethyst
                                    //----------------------------------------------
                                    _bundle = new Bundle();
                                    _bundle.putBoolean("resume_merge_thread", true);
                                    Methods.sendMessage(context,Constant.intentMergeName,_bundle);
                                    //----------------------------------------------
                                    updateDb = false;
                                }

                                sharedQueue.wait(500);// every 500ms
                                //sharedQueue.wait(); //default
                            }
                        }

                        _obj = sharedQueue.firstElement();

                        if(!sharedQueue.isEmpty()) {

                            queueEmpty = false;

                            Log.d("ItemConsumer", "getIndex: "+_obj.getIndex());
                            Log.d("ItemConsumer", "get mineralName: "+_obj.getmineralName());
                            Log.d("ItemConsumer", "get internetAccess: "+_obj.getInternetAccess());

                            consumeList(_obj);
                        }



                    } catch (InterruptedException ex) {
                        Logger.getLogger(ItemConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                Log.d("ItemConsumer", "Out of Loop");
            }
        }.start();
    }

    ArrayList<Bundle> bundleArrayList = new ArrayList<>();


    private boolean consumeList(QueueObjItem _obj) throws InterruptedException {

        Log.d("ItemConsumer", "Added to Queue: "+_obj.getmineralName());

        String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);
        AutoCompleteDatabaseHandler _databaseH = new AutoCompleteDatabaseHandler(context,dbName);

        MyObject myObject = _databaseH.findInDb(_obj.getmineralName());


        Methods.checkMyObject("ItemConsumer", myObject);

        //Bundle _bundle = background(_obj.getmineralName());
        //bundleArrayList.add(_bundle);

        Bundle MSG_bundle = new Bundle();

        String textFormat = "html";
        //TODO:Uncomment
        if(!myObject.getSearchSuccess()){
        //if(true){


        //if(!_local.checkIfExists(Constant._mineral_Name.get_key(),_obj.getmineralName())) {
            /** ---------------------- Search Mindat ---------------------------- **/
            Log.d("ItemConsumer", "Searching...");

            Bundle _bundle = Crawler.background(_obj);

            //Log.d("ItemConsumer", "--------------------------------------");
            //Methods.printBundle("ItemConsumer", _bundle);
            //Log.d("ItemConsumer", "--------------------------------------");

            //Log.d("MineralActivityInfo", "--------------------------------------");
            ///Methods.printBundle("MineralActivityInfo", _bundle);
            //Log.d("MineralActivityInfo", "--------------------------------------");

            bundleArrayList.add(_bundle);
            /** ----------------------------------------------------------------- **/

            String _key = Constant._mineral_Formula.get_key();

            String data = Methods.getStringInsideNestedBundle(_key, _bundle, textFormat);
            data = CrawlerMethods.getMindatam2Div(data);

            MSG_bundle.putString(_key, data);

            MSG_bundle.putLong( MyKeyConstants.BUNDLE_TABLE_ID,-1);
            //Log.e("ItemConsumer", "_key: "+_key);

            //Log.e("CreateDisplayMineralData", "_key: "+_key);
            //Log.e("CreateDisplayMineralData", "data: "+data);
        }else{
            /** ---------------------- Search LocalDb ---------------------------
             *  -- In here we do nothing, this is for dev only.**/
            Log.d("ItemConsumer", "Already on LocalDb...");

            //Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + - + - + - +");
            //_local.getTableText("ItemConsumer");
            //Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + - + - + - +");

            /** ----------------------------------------------------------------- **/

            LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);
            //checkIfExists(Constant._mineral_Name.getKey(),_bundle.getString(Constant._mineral_Name.getKey()))
            Log.d("ItemConsumer", "Exists? "+_local.checkIfExists(Constant._mineral_Name.get_key(),_obj.getmineralName()));

            String _key = Constant._mineral_Formula.get_key();
            //TODO: Can we get the data using the table id?

            Bundle bundle = _local.getBundle(_obj.getmineralName());

            String data = bundle.getString(_key);

            Log.e("MineralActivityInfo", "data:  "+data);

            data = Methods.getStringFromJson(_key, data, textFormat);

            Log.d("MineralActivityInfo", "data:  "+data);


            data = CrawlerMethods.getMindatam2Div(data);

            MSG_bundle.putString(_key, data);

                //MSG_bundle.putString(_key, "No data found!");


            MSG_bundle.putLong( MyKeyConstants.BUNDLE_TABLE_ID, bundle.getLong( MyKeyConstants.BUNDLE_TABLE_ID));
            //Log.e("ItemConsumer", "_key: "+_key);
            //Log.e("ItemConsumer", "data: "+data);

            _local.close();
        }

        //Log.e("ItemConsumer", "WAITING!!!");
        //TimeUnit.SECONDS.sleep(4);
        //Log.e("ItemConsumer", "DONE WAITING...");

        //String _key = Constant._mineral_Formula.get_key();
        //String _formula = MSG_bundle.getString(_key,"fail");

        //Log.e("CreateDisplayMineralData", "_key: "+_key);
        //Log.e("CreateDisplayMineralData", "_formula: "+_formula);

        //Log.e("ItemConsumer", "_key: "+_key);
        //Log.e("ItemConsumer", "_formula: "+_formula);

        MSG_bundle.putInt( MyKeyConstants.BUNDLE_ITEM_DONE, _obj.getIndex());
        MSG_bundle.putString( MyKeyConstants.BUNDLE_ITEM_NAME, _obj.getmineralName());


        //// TODO: 1/24/2017 - move to synchronized
        //MSG_bundle.putInt("sharedQueueSize",sharedQueue.size());
        //Methods.sendMessage(context, Constant.intentAddMineralListActivity,MSG_bundle);


        //Otherwise Consume element and notify waiting producer
        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            sharedQueue.remove(0);

            MSG_bundle.putInt("sharedQueueSize",sharedQueue.size());
            Methods.sendMessage(context, Constant.intentAddMineralListActivity,MSG_bundle);

            return false;
        }

    }


    public void activityRunning(boolean _state) {
        activityRunning = _state;
    }

    private boolean updateDb = false;

    public void setUpdateDb(){
        Log.d("ItemConsumer", "setUpdateDb");
        updateDb = true;
    }

    public class MyReceiverItemConsumer extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            Bundle extras = intent.getExtras();

            Log.e("ItemConsumer", "check_queue: "+extras.getBoolean("check_queue",false));

            if(extras.getBoolean("check_queue",false)) {

                Bundle _bundle = new Bundle();
                _bundle.putBoolean("queue_empty", queueEmpty);
                Methods.sendMessage(context, Constant.intentAddImageActivity,_bundle);

            }
        }

    }
























    private boolean boilerPlateTry(QueueObjItem _obj) throws InterruptedException {
        //wait if queue is empty
        String _mineralName = _obj.getmineralName();
        int _index = _obj.getIndex();

        //MyObject myObj=databaseH.findInDb(_mineralName);

        Log.e("QueueTest", "- - - ItemConsumer - - - - -");

        /**
        try {
            URL url = new URL("http://www.mindat.org/min-1295.html");
            String articleText;// = ArticleExtractor.INSTANCE.getText(url);
            articleText = KeepEverythingExtractor.INSTANCE.getText(url);
            Log.e("QueueTest", "--> "+ articleText);
            Log.e("QueueTest", "- - - - - - - - - - - - - - - - -");
        }
        catch (Exception e) {
            Log.e("QueueTest",  "ERROR");
        }
        **/
        try {
            String _url = "http://www.mindat.org/min-1295.html";
            int timeout = 10 * 1000;
            Document _doc;
            _doc = Jsoup.connect(_url).timeout(timeout).get();

            String articleText = KeepEverythingExtractor.INSTANCE.getText(_doc.toString());
            Log.e("QueueTest", "--> "+ articleText);

            Log.d("QueueTest", "- - - - - - - - - - - - - - - - -");

            Log.e("QueueTest", "--> "+ _doc.toString());

            /**
             * //-> http://stackoverflow.com/questions/35550710/how-to-get-result-of-boilerpipe-extraction-in-html-instead-of-plain-text

            URL url = new URL(_url);

            final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);

            final BoilerpipeExtractor extractor = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR;

            final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();
            hh.setOutputHighlightOnly(false);

            hh.setBodyOnly(true);

            TextDocument doc;

            String text;

            doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
            extractor.process(doc);
            final InputSource is = htmlDoc.toInputSource();
            text = hh.process(doc, is);

            System.out.println(text);

            Log.e("QueueTest", "--> "+text);
             **/
        }
        catch (Exception e) {
            Log.e("QueueTest",  "ERROR");
        }

        //Log.e("QueueTest", "_mineralName: " + _mineralName + "_index: " + _index );

        //String url = "http://www.mindat.org/min-1295.html";

       //try {

       //    url = "http://blog.openshift.com/day-18-boilerpipe-article-extraction-for-java-developers/";

       //    //String content = ArticleExtractor.INSTANCE.getText(url);


       //    //Log.e("QueueTest", "-->"+content);
       //} catch (BoilerpipeProcessingException e) {
       //    e.printStackTrace();

       //    Log.e("QueueTest", "ERROR!");
       //}

        //Log.e("QueueTest", "- - - SimpleListConsumer - - - - -");
        //databaseH = new AutoCompleteDatabaseHandler(activity.getApplicationContext(),_mineralName);
        //Log.e("QueueTest", _mineralName+" - dB Opened - "+"Queue: " +_obj.getIndex());
        //for (Object n :  _obj.getObjList()) {
        //    String _text = n.toString();
        //    databaseH.createOrUpdate( new MyObject(Methods.toTitleCase(_text),false,false) );
        //}
        //databaseH.close();
        //Log.e("QueueTest", _mineralName+" - dB Closed - "+"Queue: " +_obj.getIndex());
        Log.e("QueueTest", "- - - - - - - - - - - - - - - - -");

        //Otherwise Consume element and notify waiting producer
        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            sharedQueue.remove(0);
            return false;
        }
    }






}
