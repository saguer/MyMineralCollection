package mymineralcollection.example.org.mymineral.SQLiteDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyObject;

public class AutoCompleteDatabaseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DB_Helper";

    // database version
    private static final int DATABASE_VERSION = 5;

    // database name
    private static String DATABASE_NAME = "";

    // table details
    public static String tableName = "locations";
    public static String fieldObjectId = "id";

    public static String fieldObject_Name = "mineralName";
    public static String fieldObject_PrevSearch = "PrevSearch";
    public static String fieldObject_InPersonaldb = "InPersonaldb";

    public static String fieldObject_tableOrigin = "TableOrigin";
    public static String fieldObject_SearchSuccess = "SearchSuccess";
    public static String fieldObject_SearchResultPercent = "SearchResultPercent";
    public static String fieldObject_MineralCounter = "MineralCounter";

    String sql;

    // constructor
    public AutoCompleteDatabaseHandler(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        DATABASE_NAME = name;

        Log.d(TAG, "AutoCompleteDatabaseHandler");

        sql = getSQLCode();
    }

    private String getSQLCode(){

        String _sql;

        _sql = "";
        _sql += "CREATE TABLE " + tableName;
        _sql += " ( ";
        _sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        _sql += fieldObject_Name + " TEXT, ";
        _sql += fieldObject_PrevSearch + " INTEGER, ";
        _sql += fieldObject_InPersonaldb + " INTEGER, ";
        _sql += fieldObject_tableOrigin + " TEXT, ";
        _sql += fieldObject_SearchSuccess + " TEXT, ";
        _sql += fieldObject_SearchResultPercent + " REAL, ";
        _sql += fieldObject_MineralCounter + " REAL";
        _sql += " ) ";

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

    private ContentValues setValues(MyObject myObj){
        ContentValues values = new ContentValues();

        values.put(fieldObject_Name, myObj.getMineralName());
        values.put(fieldObject_PrevSearch, myObj.getPrevSearch());
        values.put(fieldObject_InPersonaldb, myObj.getInPersonaldb());
        values.put(fieldObject_tableOrigin, myObj.getTableOrigin());
        values.put(fieldObject_SearchSuccess, myObj.getSearchSuccess());
        values.put(fieldObject_SearchResultPercent, myObj.getMineralPercent());
        values.put(fieldObject_MineralCounter, myObj.getObjectMineralCounter());

        return values;
    }

    // createOrUpdate new record
    // @param myObj contains details to be added as single row.
    public boolean create(MyObject myObj) {

        boolean createSuccessful = false;

        if(!checkIfExists(myObj.getMineralName())){
            addedIndex++;
            SQLiteDatabase db = this.getWritableDatabase();

            createSuccessful = db.insert(tableName, null, setValues(myObj)) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, DATABASE_NAME + " - " + myObj.getMineralName() + " - created.");
            }
        }

        return createSuccessful;
    }


    public boolean add(MyObject myObj){
        boolean createSuccessful;

        SQLiteDatabase db = this.getWritableDatabase();

        createSuccessful = db.insert(tableName, null, setValues(myObj)) > 0;

        db.close();

        if(createSuccessful){
            Log.d(TAG, DATABASE_NAME + " - " + myObj.getMineralName() + " - added.");
        }else{
            Log.e(TAG, DATABASE_NAME + " - " + myObj.getMineralName() + " - NOT added.");
        }

        return createSuccessful;
    }


    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + fieldObject_Name + " = '" + objectName + "'", null);


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

    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(SQLiteDatabase _db, String objectName){

        boolean recordExists = false;

        Cursor cursor = _db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + fieldObject_Name + " = '" + objectName + "'", null);

        if(cursor!=null) {
            if(cursor.getCount()>0) {
                recordExists = true;
                Log.e(TAG, DATABASE_NAME + " - " + objectName + " - NOT created.");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        _db.close();

        return recordExists;
    }

    long addedIndex = -1;

    public boolean update(MyObject myObj)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = myObj.getId();

        addedIndex = db.update(tableName, setValues(myObj), "id = ? ", new String[]{Integer.toString(id)});

        db.close();
        return true;
    }
    public int getAddedIndex(){
        return (int) addedIndex;
    }

    //TODO: get ID!
    // Read records related to the search term
    public MyObject[] read(String searchTerm) {

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldObject_Name + " LIKE '%" + searchTerm + "%'";
        //sql += " ORDER BY " + fieldObjectId + " DESC";
        sql += " ORDER BY " + fieldObjectId + " COLLATE NOCASE ASC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();

        MyObject[] ObjectItemData = new MyObject[recCount];
        int x = 0;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String objectName = cursor.getString(cursor.getColumnIndex(fieldObject_Name));
                boolean _PrevSearch = Methods.intToBool(cursor.getInt(cursor.getColumnIndex(fieldObject_PrevSearch)));
                boolean _InPersonalDb = Methods.intToBool(cursor.getInt(cursor.getColumnIndex(fieldObject_InPersonaldb)));

                String _tableOrigin = cursor.getString(cursor.getColumnIndex(fieldObject_tableOrigin));
                boolean _SearchSuccess = Methods.intToBool(cursor.getInt(cursor.getColumnIndex(fieldObject_SearchSuccess)));
                double _SearchResultPercent = cursor.getDouble(cursor.getColumnIndex(fieldObject_SearchResultPercent));

                int _mineralCounter = cursor.getInt(cursor.getColumnIndex(fieldObject_MineralCounter));

                MyObject myObject = new MyObject(objectName,_PrevSearch,_InPersonalDb,_SearchSuccess, _SearchResultPercent, _mineralCounter, _tableOrigin,x);
                ObjectItemData[x] = myObject;
                x++;

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ObjectItemData;
    }

    public MyObject findInDb(String searchTerm) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            do {

                String objectName = allRows.getString(allRows.getColumnIndex(fieldObject_Name));
                boolean _PrevSearch = Methods.intToBool(allRows.getInt(allRows.getColumnIndex(fieldObject_PrevSearch)));
                boolean _InPersonalDb = Methods.intToBool(allRows.getInt(allRows.getColumnIndex(fieldObject_InPersonaldb)));

                String _tableOrigin = allRows.getString(allRows.getColumnIndex(fieldObject_tableOrigin));
                boolean _SearchSuccess = Methods.intToBool(allRows.getInt(allRows.getColumnIndex(fieldObject_SearchSuccess)));
                double _SearchResultPercent = allRows.getDouble(allRows.getColumnIndex(fieldObject_SearchResultPercent));

                int id = allRows.getInt(allRows.getColumnIndex(fieldObjectId));
                int _mineralCounter = allRows.getInt(allRows.getColumnIndex(fieldObject_MineralCounter));

                if(objectName.equalsIgnoreCase(searchTerm)){
                    return new MyObject(objectName,_PrevSearch,_InPersonalDb,_SearchSuccess, _SearchResultPercent, _mineralCounter, _tableOrigin,id);
                }

            } while (allRows.moveToNext());
        }
        allRows.close();
        db.close();

        return new MyObject();
    }


    public String getTableAsString() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));

                    Log.d(TAG, tableString);

                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        allRows.close();
        db.close();
        return tableString;
    }


}