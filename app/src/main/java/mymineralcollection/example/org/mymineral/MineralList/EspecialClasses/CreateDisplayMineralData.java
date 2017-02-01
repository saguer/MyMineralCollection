package mymineralcollection.example.org.mymineral.MineralList.EspecialClasses;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralSpecificInfo.JSON;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyConstant;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.UserData;
import mymineralcollection.example.org.mymineral.MineralList.ExtractJsonNew;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.PersonalDatabaseHandler;
import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.CardOptions;

/**
 * Created by Santiago on 1/23/2017.
 */
public class CreateDisplayMineralData {

    private String TAG = "CreateDisplayMineralData";
    Context context;
    int tableRowId;

    private ColorObject clorObj;

    public CreateDisplayMineralData(Context _context, Intent intent) {
        this.context = _context;
        tableRowId = intent.getIntExtra("tableId", -1);
    }

    //// TODO: 1/23/2017 - create CardOption setter


    public ArrayList<RecycleBen> setDataToDisplayMineralData(){

        String userSetInfo = null;
        String labelList = null;
        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        Cursor row = personal.getData(tableRowId);

        if(row.moveToFirst()) {
            userSetInfo = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));
            labelList = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList));
            //fieldObject_labelList
        }
        row.close();
        personal.close();

        return fillUserSetInfoArray(tableRowId, userSetInfo, labelList);
    }

    private ArrayList<RecycleBen> fillUserSetInfoArray(int tableId, String _userSetInfo, String _labelList){
        ArrayList<RecycleBen> arrayList = new ArrayList<>();

        CardOptions cardOptions = new CardOptions();
        cardOptions.CardOptionsForUserSetInfoArray();

        ExtractJsonNew.DataObj dataObj = ExtractJsonNew.getUserInfo(tableId,_userSetInfo,cardOptions);

        if (dataObj != null) {
            ArrayList<UserData> resultArray = dataObj.getResultArray();
            for(UserData data: resultArray){
                String propertyDataObj = Methods.getJsonDataString((String)data.getPropertyDataObj(), MyKeyConstants.PROPERTY_DATA);

                if(propertyDataObj != null) {
                    RecycleBen recycleBen = ExtractJsonNew.getDataBenForDisplay(data.getJsonKey(), propertyDataObj, data.getPropertyName(), data.getDialogType(), tableId, cardOptions);
                    if(recycleBen.getViewType() != ViewType.COLOR_SLIDER_VIEW){
                        arrayList.add(recycleBen);
                    }else {
                        clorObj = recycleBen.getColorObj();
                    }
                }

                Log.e(TAG, "data.getJsonKey(): "+data.getJsonKey());
            }

            ArrayList<LabelObject> labelListArray = fixTableIdForNewMinerals(_labelList);

            PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

            Cursor row = personal.getData(tableRowId);

            String test = "none";

            if(row.moveToFirst()) {
                test = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList));
            }

            Log.d(TAG, "newLabelList test: " + test);
            row.close();
            personal.close();

            arrayList = fillDataBaseInfoArray(arrayList, labelListArray);
        }
        return arrayList;
    }


    /**
     * Method to update the tableId of minerals that are new.
     * The tableId is used to get the record faster.
     * @param _labelList
     * @return
     */
    private ArrayList<LabelObject> fixTableIdForNewMinerals(String _labelList){
        ArrayList<LabelObject> labelList = ExtractJsonNew.getMineralLabelArray(_labelList);

        Log.e(TAG, "LabelList: "+_labelList);

        boolean updateDataBase = false;

        for (int index = 0; index < labelList.size(); index++) {
            //for(LabelObject label: labelList){

            LabelObject label = labelList.get(index);

            long tableId = label.getDbTableId();
            String mineralName = label.getMineralName();

            //Log.d(TAG, "-----");
            //Log.e(TAG, "getMineralName: "+mineralName);
            //Log.e(TAG, "LabelList: "+tableId);

            if(tableId == -1){
                //Log.d(TAG, "NO TABLE ID");

                LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);

                //--Get the data from the database
                Bundle bundle = _local.getBundle(mineralName);
                long newTableId = bundle.getLong( MyKeyConstants.BUNDLE_TABLE_ID,-2);

                //--Update the labelList
                LabelObject labelObj = new LabelObject(mineralName,label.getExtra(),bundle,newTableId);
                labelList.set(index,labelObj);

                _local.close();

                updateDataBase = true;
            }else {
                //Log.d(TAG, "TABLE ID - get mineral data");

                LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);

                //--Get the data from the database
                Bundle bundle = _local.getBundle(tableId);
                long newTableId = bundle.getLong( MyKeyConstants.BUNDLE_TABLE_ID,-3);

                //--Update the labelList
                LabelObject labelObj = new LabelObject(mineralName,label.getExtra(),bundle,newTableId);
                labelList.set(index,labelObj);

                _local.close();
            }
        }

        if(updateDataBase) {
            String newLabelList = JSON.labelListJson(labelList);

            Log.d(TAG, "newLabelList: " + newLabelList);

            PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

            Cursor row = personal.getData(tableRowId);

            if (row.moveToFirst()) {

                ContentValues values = new ContentValues();
                values.put(PersonalDatabaseHandler.fieldObject_Name, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_Name)));
                values.put(PersonalDatabaseHandler.fieldObject_TimeStamp, row.getInt(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_TimeStamp)));
                values.put(PersonalDatabaseHandler.fieldObject_labelList, newLabelList);
                values.put(PersonalDatabaseHandler.fieldObject_pathList, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_pathList)));
                values.put(PersonalDatabaseHandler.fieldObject_userSetInfo, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo)));

                personal.update(tableRowId, values);
            }

            row.close();
            personal.close();
        }

        return labelList;
    }

    private ArrayList<RecycleBen> fillDataBaseInfoArray(ArrayList<RecycleBen> arrayList, ArrayList<LabelObject> _labelList){


        //-- Set a Divider

        CardOptions cardOptions = new CardOptions();
        cardOptions.CardOptionsForDivider(clorObj.getColorNumber());

        RecycleBen bean = new RecycleBen(
                ViewType.DIVIDER_VIEW,
                MyConstant.DIVIDER,
                cardOptions,
                clorObj);

        arrayList.add(bean);

        //--


        cardOptions = new CardOptions();
        cardOptions.CardOptionsForFillEditArray();


        bean = new RecycleBen(
                ViewType.BUTTON_VIEW,
                MyConstant.MINERAL_INFO,
                tableRowId,
                null,
                "Mineral Information",
                null,
                cardOptions,
                clorObj);

        arrayList.add(bean);


        for(LabelObject label: _labelList){
            Log.e(TAG, "getMineralName: "+label.getMineralName());
            Log.e(TAG, "LabelList: "+label.getDbTableId());
            Log.e(TAG, "LabelList: "+label.getBundle().getString("_mineral_Formula","none"));


        }



        return arrayList;
    }


}
