package mymineralcollection.example.org.mymineral.SQLiteDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.MyObject;

public class LocalDatabaseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DB_local_Helper";

    // database version
    private static final int DATABASE_VERSION = 5;

    // database name
    private static String DATABASE_NAME = "";

    // table details
    public String tableName = "local_history";//TODO: change table name
    public String fieldObjectId = "id";

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    private String sql = null;
    // constructor
    public LocalDatabaseHandler(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        DATABASE_NAME = name;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "LocalDatabaseHandler: "+name);

        sql = getSQLCode();
    }

    private String getSQLCode(){
        String _sql = preferences.getString(Constant.dB_local_history,null);
        _sql = null;

        if(null == _sql){

            Log.d(TAG, "Create Sql string and store it in a pref");

            _sql = "";
            _sql += "CREATE TABLE " + tableName;
            _sql += " ( ";
            _sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

            int _size = Constant.propertyArray.length;
            for (int _index = 0; _index < _size; _index++) {

                //String _text = Constant.propertyArray[_index].getText();
                String _key = Constant.propertyArray[_index].getKey();

                _sql += _key + " TEXT, ";

            }
            String _temp = _sql.replaceAll(", $", "");

            _sql = _temp + " ) ";

            edit = preferences.edit();
            edit.putString(Constant.dB_local_history, _sql);
            edit.apply();

        }
        return _sql;
    }

    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e(TAG, "sql: " + sql);
        db.execSQL(sql);
    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        onCreate(db);
    }

    // createOrUpdate new record
    public boolean create(Bundle _bundle) {

        //Methods.printBundle(TAG,_bundle);
        Log.e(TAG, "+++++++++++++++++++++++");
        boolean createSuccessful = false;

        Bundle bundle = _bundle.getBundle(Constant._mineral_Name.get_key());

        if(bundle != null) {

            String mineralName = bundle.getString("text");
            Log.e(TAG, "mineralName: " +mineralName);

            //TODO: check if it is working. EX: returning true
            if (!checkIfExists(Constant._mineral_Name.get_key(), mineralName)) {

                ContentValues values = new ContentValues();

                //---- This makes the mineral name be outside a json object.
                //---- Needed for checkIfExists method

                String _key = Constant.propertyArray[0].getKey();
                bundle = _bundle.getBundle(_key);
                assert bundle != null;
                values.put(_key, bundle.getString("text"));
                //----
                _key = Constant.propertyArray[Constant.propertyArrayCounterArrayPos].getKey();
                bundle = _bundle.getBundle(_key);
                assert bundle != null;
                //sets the counter to zero
                values.put(_key, 0);
                //----
                _key = Constant.propertyArray[Constant.propertyArrayPercenageArrayPos].getKey();
                bundle = _bundle.getBundle(_key);
                assert bundle != null;
                //sets the counter to zero
                double _percent = Methods.getDoubleInsideNestedBundle(Constant._mineral_search_success.get_key(), _bundle, "percent");
                values.put(_key, _percent);
                //----
                int _size = Constant.propertyArray.length;
                for (int _index = Constant.propertyArrayStartIndex; _index < _size; _index++) {
                    _key = Constant.propertyArray[_index].getKey();
                    bundle = _bundle.getBundle(_key);

                    if(bundle != null) {
                        JSONObject json = putJsonInDb(_bundle, _key);
                        assert json != null;

                        Log.d("MineralActivityInfo", "json: " + json);


                        values.put(_key, json.toString());

                        Log.d(TAG, _key);
                        Log.d(TAG, json.toString());
                    }
                }

                SQLiteDatabase db = this.getWritableDatabase();
                createSuccessful = db.insert(tableName, null, values) > 0;
                db.close();

                if (createSuccessful) {
                    Log.i(TAG, DATABASE_NAME + " - " + mineralName + " - created.");
                }
            }
        }

        Log.e(TAG, "+++++++++++++++++++++++");

        return createSuccessful;
    }

    public void updateCounterAndPercentage(String _searchTerm, MyObject myObject) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "--------------------------------------");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                String objectName = allRows.getString(allRows.getColumnIndex(Constant._mineral_Name.get_key()));
                int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));

                if(objectName.equalsIgnoreCase(_searchTerm)) {

                    ContentValues values = new ContentValues();

                    int colIndex = -1; //to account for the id columns
                    for (String name : columnNames) {

                        //to get the counter col
                        if(colIndex == Constant.propertyArrayCounterArrayPos){
                            //Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
                            //Log.d(TAG, colIndex+" -> " + allRows.getColumnName(allRows.getColumnIndex(name)) + "");
                            //Log.d(TAG, "  " +_counter+"");
                            values.put(allRows.getColumnName(allRows.getColumnIndex(name)), myObject.getObjectMineralCounter()+"");
                        }else {
                            //Log.d(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
                            //Log.d(TAG, colIndex+" -> " + allRows.getColumnName(allRows.getColumnIndex(name)) + "");
                            //Log.d(TAG, "  " +allRows.getString(allRows.getColumnIndex(name)) + "");
                            values.put(allRows.getColumnName(allRows.getColumnIndex(name)), allRows.getString(allRows.getColumnIndex(name)));
                        }
                        colIndex++;
                    }

                    db.update(tableName, values, "id = ? ", new String[]{Integer.toString(id)});
                    db.close();

                }

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
        Log.d(TAG, "--------------------------------------");

        Log.e(TAG, "+++++++++++++++++++++++");

    }


    public boolean update_(int id, Bundle _bundle) {

        //Methods.printBundle(TAG,_bundle);
        Log.e(TAG, "+++++++++++++++++++++++");
        boolean createSuccessful = false;

        Bundle bundle = _bundle.getBundle(Constant._mineral_Name.get_key());

        if(bundle != null) {

            String mineralName = bundle.getString("text");
            Log.e(TAG, "mineralName: " +mineralName);

            if (!checkIfExists(Constant._mineral_Name.get_key(), mineralName)) {

                ContentValues values = new ContentValues();

                //---- This makes the mineral name be outside a json object.

                String _key = Constant.propertyArray[0].getKey();
                bundle = _bundle.getBundle(_key);
                assert bundle != null;
                values.put(_key, bundle.getString("text"));
                //----
                int _size = Constant.propertyArray.length;
                for (int _index = Constant.propertyArrayStartIndex; _index < _size; _index++) {
                    _key = Constant.propertyArray[_index].getKey();

                    bundle = _bundle.getBundle(_key);
                    if(bundle != null) {
                        JSONObject json = putJsonInDb(_bundle, _key);
                        assert json != null;
                        values.put(_key, json.toString());
                    }
                }

                SQLiteDatabase db = this.getWritableDatabase();
                //createSuccessful = db.insert(tableName, null, values) > 0;

                createSuccessful = db.update(tableName, values, "id = ? ", new String[]{Integer.toString(id)}) > 0;
                db.close();

                if (createSuccessful) {
                    Log.i(TAG, DATABASE_NAME + " - " + mineralName + " - created.");
                }
            }
        }

        Log.e(TAG, "+++++++++++++++++++++++");

        return createSuccessful;
    }

    private ContentValues getValues(Bundle _bundle){

        Bundle bundle = _bundle.getBundle(Constant._mineral_Name.get_key());

        if(bundle != null) {

            String mineralName = bundle.getString("text");
            Log.e(TAG, "mineralName: " + mineralName);

            //TODO: check if it is working. EX: returning true
            if (!checkIfExists(Constant._mineral_Name.get_key(), mineralName)) {

                ContentValues values = new ContentValues();

                //---- This makes the mineral name be outside a json object.
                //---- Needed for checkIfExists method

                String _key = Constant.propertyArray[0].getKey();
                bundle = _bundle.getBundle(_key);
                assert bundle != null;
                values.put(_key, bundle.getString("text"));
                //----
                int _size = Constant.propertyArray.length;
                for (int _index = Constant.propertyArrayStartIndex; _index < _size; _index++) {
                    _key = Constant.propertyArray[_index].getKey();

                    bundle = _bundle.getBundle(_key);
                    if (bundle != null) {
                        JSONObject json = putJsonInDb(_bundle, _key);

                        assert json != null;
                        values.put(_key, json.toString());
                    }
                }
                return values;
            }
        }
        return null;
    }


    private JSONObject putJsonInDb(Bundle bundle, String _key){

        bundle = bundle.getBundle(_key);
        if(bundle != null) {
            try {
                //Having a Json object makes more sense, since we can add more data without creating more columns.
                JSONObject json = new JSONObject();
                JSONObject propDataJson = new JSONObject();

                propDataJson.put("text", bundle.getString("text"));
                propDataJson.put("html", bundle.getString("html"));

                //Log.d("MineralActivityInfo", "HTML" + bundle.getString("html"));


                json.put(_key,propDataJson);

                return json;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String _column, String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + _column + " = '" + objectName + "'", null);


        if(cursor!=null) {
            if(cursor.getCount()>0) {
                recordExists = true;
                Log.e(TAG, DATABASE_NAME + " - " + objectName + " - NOT created.");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return recordExists;
    }

    public MyObject findInDb(String searchTerm) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            do {
                String objectName = allRows.getString(allRows.getColumnIndex(Constant._mineral_Name.get_key()));
                //String _tableOrigin = allRows.getString(allRows.getColumnIndex(fieldObject_MineralFormula));
                int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));
                int _mineralCounter = allRows.getInt(allRows.getColumnIndex(Constant._mineral_Counter.get_key()));

                if(objectName.equalsIgnoreCase(searchTerm)){
                    return new MyObject(objectName,false,false,false,0.0, _mineralCounter, null,id);
                }

            } while (allRows.moveToNext());
        }
        allRows.close();
        db.close();

        return new MyObject();
    }

    public Bundle getBundle(String searchTerm) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "--------------------------------------");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                String objectName = allRows.getString(allRows.getColumnIndex(Constant._mineral_Name.get_key()));
                int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));

                if(objectName.equalsIgnoreCase(searchTerm)) {

                    Bundle _bundle = new Bundle();
                    for (String name : columnNames) {

                        if (allRows.getString(allRows.getColumnIndex(name)) != null) {
                            Log.e(TAG, "--------------------------------------");
                            Log.d(TAG, allRows.getColumnName(allRows.getColumnIndex(name)) + "");
                            Log.d(TAG, allRows.getString(allRows.getColumnIndex(name)) + "");
                            _bundle.putString(allRows.getColumnName(allRows.getColumnIndex(name)), allRows.getString(allRows.getColumnIndex(name)));
                        }
                    }

                    _bundle.putLong(MyKeyConstants.BUNDLE_TABLE_ID,id);

                    return _bundle;
                }

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
        Log.d(TAG, "--------------------------------------");

        return null;
    }

    public Bundle getBundle(long tableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "--------------------------------------");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows =  db.rawQuery( "SELECT * FROM " + tableName + " where id="+tableId+"", null );

        //Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);

        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                //String objectName = allRows.getString(allRows.getColumnIndex(Constant._mineral_Name.get_key()));
                int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));

                //if(objectName.equalsIgnoreCase(searchTerm)) {

                    Bundle _bundle = new Bundle();
                    for (String name : columnNames) {

                        if (allRows.getString(allRows.getColumnIndex(name)) != null) {
                            Log.e(TAG, "--------------------------------------");
                            Log.d(TAG, allRows.getColumnName(allRows.getColumnIndex(name)) + "");
                            Log.d(TAG, allRows.getString(allRows.getColumnIndex(name)) + "");
                            _bundle.putString(allRows.getColumnName(allRows.getColumnIndex(name)), allRows.getString(allRows.getColumnIndex(name)));
                        }
                    }

                    _bundle.putLong(MyKeyConstants.BUNDLE_TABLE_ID,id);

                    return _bundle;
                //}

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
        Log.d(TAG, "--------------------------------------");

        return null;
    }


    /** ----- Print table helpers  ------------------------------------------------------------- **/
    public void getTable_all(String logTag) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.e(logTag, "--------------------------------------");
        Log.d(logTag, "getTableAsString called");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                String _temp = "> ";
                for (String name: columnNames) {
                    //tableString += String.format("%s: %s\n", name,
                    //        allRows.getString(allRows.getColumnIndex(name)));
                    _temp = _temp + "|" + allRows.getString(allRows.getColumnIndex(name));
                }
                Log.d(logTag, _temp);

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
    }

    public void getTableText(String logTag){
        getTable_by(logTag,"text");
    }

    public void getTable_by(String logTag, String _jsoTag) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.e(logTag, "--------------------------------------");
        Log.d(logTag, "getTableAsString called");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                String _temp = "> ";

                int _size = columnNames.length;

                //---- For the table id -----
                String name = columnNames[0];
                //---- This represents the ID
                String id = allRows.getString(allRows.getColumnIndex(name));
                _temp = _temp + "|" + id;
                //---------------------------
                for (int _index = 1; _index < _size; _index++) {

                    name = columnNames[_index];

                    //String _key = Constant.propertyArray[_index].getKey();;

                    try {
                        String _json = allRows.getString(allRows.getColumnIndex(name));
                        if(_json != null) {
                            JSONObject mainObject = new JSONObject(_json);
                            String _text = mainObject.getJSONObject(name).getString(_jsoTag);
                            _temp = _temp + "|" + _text;
                        }else{
                            _temp = _temp + "|null";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        String _text = allRows.getString(allRows.getColumnIndex(name));
                        _temp = _temp + "|" + _text;
                        //Log.e(logTag, name + " " + _text + " - catch error" );
                    }
                }
                Log.d(logTag, _temp);


            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
    }
    /** ---------------------------------------------------------------------------------------- **/



}