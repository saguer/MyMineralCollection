package mymineralcollection.example.org.mymineral.MineralList;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.PathObject;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyConstant;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.UserData;
import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.CardOptions;

/**
 * Created by Santiago on 12/13/2016.
 */
public class ExtractJsonNew {

    public static DataObj getUserInfo(int tableID, String userSetInfo, CardOptions cardOptions){
        JSONObject jsonObject;

        if(userSetInfo != null) {
            try {
                jsonObject = new JSONObject(userSetInfo).getJSONObject(MyKeyConstants.MINERAL_INFO_USER_SET);

                return UserDataLoop(tableID, jsonObject, cardOptions);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DataObj UserDataLoop(int tableID, JSONObject jsonObject, CardOptions cardOptions){
        ArrayList<UserData> resultArray = new ArrayList<>();

        for(UserData data: MyConstant.UserDataArray){
            String jsonKey = data.getJsonKey();

            String resultArr = Methods.getJsonDataString(jsonObject.toString(),jsonKey);
            UserData userData = new UserData(data.getJsonKey(),data.getPropertyName(), resultArr, data.getDialogType(), data.getViewType());
            resultArray.add(userData);
        }

        int size = resultArray.size();
        String result = null;

        for (int index = 0; index < size; index++) {
            UserData userData = resultArray.get(index);

            String jsonKey = userData.getJsonKey();

            String userDataObj = (String) userData.getPropertyDataObj();

            Log.e("MineralListActivity", "UserData: userDataObj: "+userDataObj);

            boolean isSaveKey = jsonKey.equalsIgnoreCase(MyConstant.SAVE_KEY);
            boolean isColorCardKey = jsonKey.equalsIgnoreCase(MyConstant.CARD_COLOR_KEY);

            String propertyData = Methods.getJsonDataString(userDataObj, MyKeyConstants.PROPERTY_DATA);

            String propertyName = Methods.getJsonDataString(userDataObj, MyKeyConstants.PROPERTY_NAME);
            int dialogType = Methods.getJsonDataInt(userDataObj, MyKeyConstants.DIALOG_TYPE);

            String propertyDataNotNull = propertyData;

            if(propertyDataNotNull == null) propertyDataNotNull = "";

            DataObj dataObj = getDataBenForDisplay(index, size, jsonKey, propertyDataNotNull, propertyName, dialogType, tableID, result, cardOptions);

            if(!isColorCardKey && !isSaveKey) {
                if (propertyData != null) {
                    result = dataObj.getResult();
                }
            }
        }

        return new DataObj(result,resultArray);
    }

    private static DataObj getDataBenForDisplay(int index, int size, String jSonKey, String propertyData, String propertyName, int dialogType, int tableID, String result, CardOptions cardOptions){

        RecycleBen bean;
        propertyName = "<u>" + propertyName + "</u>";
        String temp = "";

        bean = getDataBenForDisplay(jSonKey, propertyData, propertyName, dialogType, tableID, cardOptions);

        switch (dialogType){
            case MyConstant.PRICE_INPUT:{
                temp = propertyName + ": $" + propertyData;
                break;
            }
            case MyConstant.DATE_PICKER:{
                propertyData = Methods.formatDate(propertyData);
                temp = propertyName+": "+propertyData;
                break;
            }
            case MyConstant.COLOR_INPUT:{
                temp = temp + "";
                break;
            }
            default:{
                if (propertyData.toLowerCase().equalsIgnoreCase("ebay")) {
                    temp = propertyName + ": " + propertyData;
                } else {
                    temp = propertyName + "<br />" + propertyData;
                }
            }
        }


        if (result == null) {
            result = temp + "<br />" + "<br />";
        } else {
            if (index == (size - 1)) {
                result = result + " " + temp;
            } else {
                result = result + " " + temp + "<br />" + "<br />";
            }
        }

        return new DataObj(result, bean);
    }
    //todo: maybe organize the order similar to the RecycleBen.class constructor
    public static RecycleBen getDataBenForDisplay(String jSonKey, String propertyData, String propertyName, int dialogType, int tableID, CardOptions cardOptions){

        RecycleBen bean;
        //propertyName = "<u>" + propertyName + "</u>";
        String temp;
        int viewType;

        switch (dialogType){
            case MyConstant.PRICE_INPUT:{
                temp = propertyName + ": $" + propertyData;
                viewType = ViewType.SINGLE_LINE_VIEW;
                break;
            }
            case MyConstant.DATE_PICKER:{
                propertyData = Methods.formatDate(propertyData);
                temp = propertyName+": "+propertyData;
                viewType = ViewType.SINGLE_LINE_VIEW;
                break;
            }
            case MyConstant.CUSTOM_TEXT_INPUT:{
                temp = propertyData;
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.COLOR_INPUT:{
                ///temp = propertyData;
                viewType = ViewType.COLOR_SLIDER_VIEW;

                bean = new RecycleBen(
                        viewType,
                        dialogType,
                        tableID,
                        jSonKey,
                        propertyName,
                        null,
                        cardOptions,
                        getColorJson(propertyData));

                return bean;
            }
            default:{
                if (propertyData.toLowerCase().equalsIgnoreCase("ebay")) {
                    temp = propertyData;
                    viewType = ViewType.SINGLE_LINE_VIEW;
                } else {
                    temp = propertyData;
                    viewType = ViewType.EXPANDABLE_VIEW;
                }

            }
        }

        bean = new RecycleBen(
                viewType,
                dialogType,
                tableID,
                jSonKey,
                propertyName,
                temp,
                cardOptions);

        return bean;
    }

    public static RecycleBen getDataBenForEdit(String jSonKey, Object propertyData, String propertyName,
                                               int dialogType, int tableID, CardOptions cardOptions){

        RecycleBen bean;
        //String temp = null;
        int viewType = 0;

        switch (dialogType) {
            case MyConstant.CUSTOM_TEXT_INPUT: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.PRICE_INPUT: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                } else {
                    propertyData = propertyData + " $";
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.DATE_PICKER: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                } else {
                    propertyData = Methods.formatDate((String)propertyData);
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.SAVE: {
                propertyData = null;
                viewType = ViewType.BUTTON_VIEW;
                break;
            }
            case MyConstant.COLOR_INPUT: {
                viewType = ViewType.COLOR_SLIDER_VIEW;

                if(propertyData != null) {
                    try {
                        bean = new RecycleBen(
                                viewType,
                                dialogType,
                                tableID,
                                jSonKey,
                                propertyName,
                                propertyData,
                                cardOptions,
                                getColorJson(propertyData.toString()));


                        //ColorObject colorObj = ExtractJsonNew.getColorJson(propertyData.toString());

                        //bean.printBean("BeanFix");

                        //Log.e("BeanFix", ">>>>");

                        //RecycleBen newBean = new RecycleBen(bean.getViewType(), bean.getDialogType(), bean.getTableId(),
                        //        bean.getJsonKey(), bean.getDescription(), bean.getDataObj(), bean.getCardOptions(), colorObj);

                        //newBean.printBean("BeanFix");

                        return bean;
                    }catch (ClassCastException e){
                        // TODO: 1/14/2017 - Handle the exception
                        Log.e("BeanFix", "ClassCastException: " +e);
                        break;
                    }
                }

                break;
            }
        }

        bean = new RecycleBen(
                viewType,
                dialogType,
                tableID,
                jSonKey,
                propertyName,
                propertyData,
                cardOptions);



        return bean;
    }
    public static RecycleBen getDataBenForEdit_v2(String jSonKey, Object propertyData, String propertyName,
                                               int dialogType, int tableID, CardOptions cardOptions){

        RecycleBen bean;
        //String temp = null;
        int viewType = 0;

        switch (dialogType) {
            case MyConstant.CUSTOM_TEXT_INPUT: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.PRICE_INPUT: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                } else {
                    propertyData = propertyData + " $";
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.DATE_PICKER: {
                if (propertyData == null) {
                    propertyData = "Click Edit to Add Data";
                } else {
                    propertyData = Methods.formatDate((String)propertyData);
                }
                viewType = ViewType.EXPANDABLE_VIEW;
                break;
            }
            case MyConstant.SAVE: {
                propertyData = null;
                viewType = ViewType.BUTTON_VIEW;
                break;
            }
            case MyConstant.COLOR_INPUT: {
                viewType = ViewType.COLOR_SLIDER_VIEW;

                Log.e("BeanFix", "propertyData != null: "+(propertyData != null));
                Log.e("BeanFix", "propertyData: "+propertyData);

                if(propertyData != null) {
                    try {

                        //ColorObject colorObj = ExtractJsonNew.getColorJson((String)propertyData);

                        //bean = new RecycleBen(
                        //        viewType,
                        //        dialogType,
                        //        tableID,
                        //        jSonKey,
                        //        propertyName,
                        //        propertyData,
                        //        cardOptions,
                        //        colorObj);

                        bean = new RecycleBen(
                                viewType,
                                dialogType,
                                tableID,
                                jSonKey,
                                propertyName,
                                propertyData,
                                cardOptions,
                                getColorJson((String) propertyData));


                        return bean;
                    }catch (ClassCastException e){
                        Log.e("BeanFix", "ClassCastException"+e);
                        // TODO: 1/14/2017 - Handle the exception
                        break;
                    }
                }

                break;
            }
        }

        bean = new RecycleBen(
                viewType,
                dialogType,
                tableID,
                jSonKey,
                propertyName,
                propertyData,
                cardOptions);

        return bean;
    }
    /******************************************************************************************/
    public static ArrayList<String> fillMineralUriArray(String _mineralImgUri){
        ArrayList<String> arrayList = new ArrayList<>();

        for(PathObject pathObject: getMineralImgUri(_mineralImgUri)){
            //File f = new File(pathObject.getThumbPath());
            //Uri uri = Uri.fromFile(f);


            arrayList.add(pathObject.getThumbPath());
            //mineralListArrayList.add(pathObject.getPath());
        }

        return arrayList;
    }

    public static ArrayList<PathObject> getMineralImgUri(String _jsonString) {

        ArrayList<PathObject> resultsArray = new ArrayList<>();

        /*
            public static String MINERAL_IMG_PATH_KEY = "MINERAL_IMG_PATH";
    public static String MINERAL_IMG_PATH_ORIG_KEY = "MINERAL_IMG_PATH_ORIG";
    public static String MINERAL_IMG_PATH_MOD_KEY = "MINERAL_IMG_PATH_MOD";
         */
        try {
            JSONArray jsonObject = new JSONObject(_jsonString).getJSONArray(MyKeyConstants.MINERAL_IMG_PATH_KEY);
            //JSONArray jsonObject = new JSONObject(_jsonString).getJSONArray("MINERAL_IMG_PATH");

            int size = jsonObject.length();
            for (int index = 0; index < size; index++) {
                String jsonArray = jsonObject.get(index).toString();

                //boolean contain_path_orig = new JSONObject(jsonArray).has("MINERAL_IMG_PATH_ORIG");
                //boolean contain_path_mod = new JSONObject(jsonArray).has("MINERAL_IMG_PATH_MOD");
                boolean contain_path_orig = new JSONObject(jsonArray).has("MINERAL_IMG_PATH_ORIG");
                boolean contain_path_mod = new JSONObject(jsonArray).has("MINERAL_IMG_PATH_MOD");
                long ts = new JSONObject(jsonArray).getLong("TS");

                String path_orig = null;
                String path_orig_th = null;

                if(contain_path_orig) {
                    path_orig = new JSONObject(jsonArray).getString("MINERAL_IMG_PATH_ORIG");
                    path_orig_th = new JSONObject(jsonArray).getString("THUMBNAIL_IMG_ORIG_PATH");
                }else if(contain_path_mod){
                    path_orig = new JSONObject(jsonArray).getString("MINERAL_IMG_PATH_MOD");
                    path_orig_th = new JSONObject(jsonArray).getString("THUMBNAIL_IMG_MOD_PATH");
                }

                //PathObject(String _path, String _thumbPath, boolean _orig, boolean _mod, long _timeStamp) {
                PathObject pathObject = new PathObject(path_orig, path_orig_th, contain_path_orig, contain_path_mod, ts);

                resultsArray.add(pathObject);
            }
        }catch (Exception e){
            //break;
        }
        return resultsArray;
    }
    /******************************************************************************************/


    public static String getMineralDescription(String _jsonString){

        String result = null;
        try {
            JSONArray jsonObject = new JSONObject(_jsonString).getJSONArray(MyKeyConstants.MINERAL_LABEL);

            int size = jsonObject.length();
            for (int index = 0; index < size; index++) {

                String _jsonArray = jsonObject.get(index).toString();

                String mineralName = new JSONObject(_jsonArray).getString(MyKeyConstants.MINERAL_KEY);
                String mineralExtra = new JSONObject(_jsonArray).getString(MyKeyConstants.EXTRA_KEY);

                String temp;

                if(mineralExtra.equalsIgnoreCase("none")){
                    if(index == (size-1)){
                        temp = mineralName + "";
                    }else {
                        temp = mineralName + ",";
                    }
                }else {
                    temp = mineralName + " " + mineralExtra;
                }

                if(result == null){
                    result = temp;
                }else {
                    result = result + " " + temp;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("DisplayMineralActivity","ERROR!");
        }
        return result;
    }

    public static ArrayList<LabelObject> getMineralLabelArray(String _jsonString){

        ArrayList<LabelObject> labelList = new ArrayList<>();

        try {
            JSONArray jsonObject = new JSONObject(_jsonString).getJSONArray(MyKeyConstants.MINERAL_LABEL);

            int size = jsonObject.length();
            for (int index = 0; index < size; index++) {

                String _jsonArray = jsonObject.get(index).toString();

                String mineralName = new JSONObject(_jsonArray).getString(MyKeyConstants.MINERAL_KEY);
                String mineralExtra = new JSONObject(_jsonArray).getString(MyKeyConstants.EXTRA_KEY);
                int indexJson = new JSONObject(_jsonArray).getInt(MyKeyConstants.INDEX_KEY);
                long tableId = new JSONObject(_jsonArray).getLong(MyKeyConstants.TABLE_ID);

                LabelObject labelObj = new LabelObject(mineralName,mineralExtra,indexJson,tableId);
                labelList.add(labelObj);

                Log.d("CreateDisplayMineralData", "-----");
                Log.e("CreateDisplayMineralData", "LabelList: "+mineralName);
                Log.e("CreateDisplayMineralData", "LabelList: "+tableId);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("DisplayMineralActivity","ERROR!");
        }
        return labelList;
    }

    public static ColorObject getColorJson(String jasonString){

        ColorObject colorObj = new ColorObject();

        try {
            JSONObject colorJson = new JSONObject(jasonString).getJSONObject("COLOR");

            int colorIndex = colorJson.getInt("COLOR_INDEX");
            int colorInt = colorJson.getInt("COLOR_INT");
            String colorName = colorJson.getString("COLOR_NAME");


            colorObj.setColorName(colorName);
            colorObj.setColorNumberIndex(colorIndex);
            colorObj.setColorNumber(colorInt);
            //colorObj.setUnderline(true);

        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e("DisplayMineralActivity","No value for COLOR_NAME");
        }

        return colorObj;
    }

    public static class DataObj implements Serializable {
        String result;
        RecycleBen bean;

        ArrayList<UserData> resultArray;

        public ArrayList<UserData> getResultArray() {
            return resultArray;
        }

        public DataObj(String result, ArrayList<UserData> resultArray) {
            this.result = result;
            this.resultArray = resultArray;
        }

        public DataObj(String result, RecycleBen bean) {
            this.result = result;
            this.bean = bean;
        }

        public String getResult() {
            return result;
        }

        public RecycleBen getBean() {
            return bean;
        }
    }
}
