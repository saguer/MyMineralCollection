package mymineralcollection.example.org.mymineral.Crawler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;

/**
 * Created by Santiago on 11/4/2016.
 */
public class AddUpdateDb {

    public static void addToLocalDb(ArrayList<Bundle> _bundleArrayList, Context _context) {

        Log.e("CreateDisplayMineralData", "data:------------------------------ ");

        Log.e("ItemConsumer", "Calling: addToLocalDb");

        LocalDatabaseHandler _local = new LocalDatabaseHandler(_context, Constant.dB_local_history);

        for (Bundle _bundle : _bundleArrayList) {

            String _fail = Methods.getStringInsideNestedBundle(Constant._mineral_search_success.get_key(), _bundle, "text");
            boolean searchSuccess = Boolean.valueOf(_fail);

            if(searchSuccess) _local.create(_bundle);
        }

        _bundleArrayList.clear();
        Log.d("ItemConsumer", "- + - + - + - + - + - + - + - + - + - + - + - +");
        //

        Log.d("ItemConsumer", "- + - + - + - + - + - + - + - + - + - + - + - +");

        //_local.getTableText("ItemConsumer");
        //Log.d("ItemConsumer", "- + - + - + - + - + - + - + - + - + - + - + - +");

        //_local.updateCounterAndPercentage("Azurite",12);

        _local.getTable_all("ItemConsumer");

        _local.close();
    }


    /**
     * when this method gets called, we take the elements from the Arraylist and
     * add them to the Local Db
     * Since the user is adding the pictures, maybe add to personal Db?
     *
     * @param _bundleArrayList data as a bundle arrayList
     * @param _context the context
     */
    public static void updateAutoCompleteDb(ArrayList<Bundle> _bundleArrayList, Context _context) {

        Log.e("ItemConsumer", "Calling: updateAutoCompleteDb");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());
        String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);

        AutoCompleteDatabaseHandler databaseH = new AutoCompleteDatabaseHandler(_context,dbName);

        for (Bundle _bundle : _bundleArrayList) {

            String _minName = Methods.getStringInsideNestedBundle(Constant._mineral_Name.get_key(), _bundle, "text");

            String _fail = Methods.getStringInsideNestedBundle(Constant._mineral_search_success.get_key(), _bundle, "text");
            boolean searchSuccess = Boolean.valueOf(_fail);

            double _percent = Methods.getDoubleInsideNestedBundle(Constant._mineral_search_success.get_key(), _bundle, "percent");

            MyObject myObject = databaseH.findInDb(_minName);

            Methods.checkMyObject("ItemConsumer",myObject);

            myObject.setObjectMineralSearchSuccess(searchSuccess);
            myObject.setMineralPercent(_percent);
            myObject.setPrevSearch(true);

            databaseH.update(myObject);
            //}

        }
        databaseH.close();
    }

    /**
     * @param obj - the object containing the mineral name and formula
     * @param _inPersonalDb - it is true because we are adding it to the personal db. in the future maybe if we delete that mineral reset this flag
     * @param _context - the context
     * @return
     */
    public static void setAutoCompletePersonalFlag(LabelObject obj, boolean _inPersonalDb, Context _context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());
        String dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);

        AutoCompleteDatabaseHandler databaseH = new AutoCompleteDatabaseHandler(_context,dbName);

        MyObject myObject = databaseH.findInDb(obj.getMineralName());
        //Methods.checkMyObject("ItemConsumer",myObject);
        int mineralCounter = myObject.getObjectMineralCounter()+1;

        myObject.setObjectMineralCounter(mineralCounter);
        myObject.setPersonaldb(_inPersonalDb);

        databaseH.update(myObject);

        databaseH.close();

        Methods.checkMyObject("ItemConsumer",myObject);

        updateMineralCounter(_context,obj.getMineralName(),myObject);
    }

    public static void updateMineralCounter(Context _context, String _name, MyObject myObject){

        LocalDatabaseHandler _local = new LocalDatabaseHandler(_context, Constant.dB_local_history);

        _local.updateCounterAndPercentage(_name,myObject);

        Log.e("ItemConsumer", "==============================");

        _local.getTable_all("ItemConsumer");

        _local.close();

    }

    //public static void addToMineralHistory(LabelObject obj, Context _context){

        //MineralAddHistoryDatabaseHandler databaseH = new MineralAddHistoryDatabaseHandler(_context,Constant.dB_mineral_add_history);
//
        //String mineralName = obj.getMineralName();
        //String mineralFormula = obj.getFormula();
        //int mineralCounter = 0;
//
        //databaseH.createOrUpdate(mineralName,mineralFormula,mineralCounter);
//
        //databaseH.close();
    //}



}
