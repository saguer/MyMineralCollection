package mymineralcollection.example.org.mymineral.SQLiteDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import mymineralcollection.example.org.mymineral.Class.Constant;

public class PersonalDatabaseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DB_Helper";

    // database version
    private static final int DATABASE_VERSION = 5;

    // database name
    private static String DATABASE_NAME = "";

    // table details
    public static String tableName = "personal_table";
    public static String fieldObjectId = "id";
    public static String fieldObject_Name = "mineralID";
    public static String fieldObject_TimeStamp = "TS";
    public static String fieldObject_labelList = "labelList";
    public static String fieldObject_pathList = "pathList";
    public static String fieldObject_userSetInfo = "userSetInfo";

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    private String sql = null;
    // constructor
    public PersonalDatabaseHandler(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        DATABASE_NAME = name;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "LocalDatabaseHandler");

        sql = getSQLCode();
    }

    private String getSQLCode(){
        String _sql;

            Log.d(TAG, "Create Sql string and store it in a pref");

            _sql = "";
            _sql += "CREATE TABLE " + tableName;
            _sql += " ( ";
            _sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";

            _sql += fieldObject_Name + " TEXT, ";
            _sql += fieldObject_TimeStamp + " INTEGER, ";
            _sql += fieldObject_labelList + " TEXT, ";
            _sql += fieldObject_pathList + " TEXT, ";
            _sql += fieldObject_userSetInfo + " TEXT)";

            edit = preferences.edit();
            edit.putString(Constant.dB_local_history, _sql);
            edit.apply();


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

    /**
     *
     * @param _Name
     * @param _FirstColumns
     * @param _SecondColumns
     * @param _LastUri
     * @return
     */
    public boolean create(String _Name, long _timeStamp, String _FirstColumns, String _SecondColumns, String _LastUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        boolean createSuccessful;

        values.put(fieldObject_Name, _Name);
        values.put(fieldObject_TimeStamp, _timeStamp);
        values.put(fieldObject_labelList, _FirstColumns);
        values.put(fieldObject_pathList, _SecondColumns);
        values.put(fieldObject_userSetInfo, _LastUri);

        createSuccessful = db.insert(tableName, null, values) > 0;
        db.close();

        if (createSuccessful) {
            Log.i(TAG, DATABASE_NAME + " - " + _Name + " - created.");
        }

        return createSuccessful;
    }

    //public boolean update(int _id, String _name, long _timeStamp, String _labelList, String _pathList, String _userSetInfo){
    //    SQLiteDatabase db = this.getWritableDatabase();
//
    //    ContentValues values = new ContentValues();
    //    values.put(fieldObject_Name, _name);
    //    values.put(fieldObject_TimeStamp, _timeStamp);
    //    values.put(fieldObject_labelList, _labelList);
    //    values.put(fieldObject_pathList, _pathList);
    //    values.put(fieldObject_userSetInfo, _userSetInfo);
//
    //    int addedIndex = db.update(tableName, values, "id = ? ", new String[]{Integer.toString(_id)});
//
    //    return true;
    //}

    public boolean update(int _id, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        int addedIndex = db.update(tableName, values, "id = ? ", new String[]{Integer.toString(_id)});

        return true;
    }


    //id is the pos in the order they were created
    public Cursor getData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor row =  db.rawQuery( "select * from " + tableName + " where id="+id+"", null );

        return row;
    }

    //---deletes a particular Id---
    public boolean deleteTitle(int objId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(tableName, fieldObjectId + "=" + objId, null) > 0;
    }


    //public void updateLastRow(String _name, int _timeStamp, String _firstCol, String _secondCol, String _lastUri) {
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    try (Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null)) {
    //        if (allRows.moveToLast()) {
    //            int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));
//
    //            update(id, _name, _timeStamp, _firstCol, _secondCol, _lastUri);
    //        }
    //    }
    //}
//
    //public int getLastRow(){
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    int id = 0;
    //    try (Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null)) {
    //        if (allRows.moveToLast()) {
    //            id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));
//
    //        }
    //    }
    //    return id;
    //}


    //public boolean findInDb(String searchTerm) {
    //    SQLiteDatabase db = this.getWritableDatabase();
//
    //    Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
    //    if (allRows.moveToFirst() ){
    //        do {
//
    //            String objectName = allRows.getString(allRows.getColumnIndex(Constant._mineral_Name.get_key()));
//
    //            if(objectName.equalsIgnoreCase(searchTerm)){
    //                return true;
    //            }
//
    //        } while (allRows.moveToNext());
    //    }
    //    allRows.close();
    //    db.close();
//
    //    return false;
    //}


    //public MyObject printdb(String searchTerm) {
    //    SQLiteDatabase db = this.getWritableDatabase();
//
    //    Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
    //    if (allRows.moveToFirst() ){
    //        do {
//
    //            String mineralId = allRows.getString(allRows.getColumnIndex(fieldObject_Name));
    //            String labelList = allRows.getString(allRows.getColumnIndex(fieldObject_labelList));
    //            String pathList = allRows.getString(allRows.getColumnIndex(fieldObject_pathList));
    //            int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));
    //
    //
    //
    //            boolean _PrevSearch = intToBool(allRows.getInt(allRows.getColumnIndex(fieldObject_labelList)));
    //            boolean _InPersonalDb = intToBool(allRows.getInt(allRows.getColumnIndex(fieldObject_pathList)));
//
//
    //            if(mineralId.equalsIgnoreCase(searchTerm)){
//
    //                return new MyObject(mineralId,_PrevSearch,_InPersonalDb,id);
    //            }
//
    //        } while (allRows.moveToNext());
    //    }
    //    allRows.close();
    //    db.close();
//
    //    return new MyObject();
    //}

    /**
     * @param _tag LogTag
     */
    public void getTable(String _tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Log.e(_tag, "--------------------------------------");
        Log.d(_tag, "getTable called");
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
                Log.d(_tag, _temp);

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
    }



    //public String getLastPathOrig(){
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    try (Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null)) {
    //        if (allRows.moveToLast()) {
    //            int colIndex = allRows.getColumnIndex(fieldObject_pathList);
    //            //Log.d("addImage", "colIndex : "+colIndex);
    //            String _secondCol = allRows.getString(colIndex);
    //            return CreateJson.getOrigPathLastRow(_secondCol);
    //        }
    //    }
    //    return null;
    //}

    //public ArrayList<String> getLastPathArray(){
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    try (Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null)) {
    //        if (allRows.moveToLast()) {
    //            int colIndex = allRows.getColumnIndex(fieldObject_pathList);
    //            //Log.d("addImage", "colIndex : "+colIndex);
    //            String _secondCol = allRows.getString(colIndex);
    //            return CreateJson.getModPathLastRowArray(_secondCol);
    //        }
    //    }
    //    return null;
    //}

    /**
     * Will get the path of the previews images, originals and mods.
     * This will be then added to the new paths created.
     * @return Path string
     */
    //public Collection<JSONObject> getAllPathsExpectLast(){
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    try (Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null)) {
    //        if (allRows.moveToLast()) {
    //            int colIndex = allRows.getColumnIndex(fieldObject_pathList);
    //            //Log.d("addImage", "colIndex : "+colIndex);
    //            String _secondCol = allRows.getString(colIndex);
    //            return CreateJson.getModPaths(_secondCol);
    //        }
    //    }
    //    return null;
    //}



}