package mymineralcollection.example.org.mymineral.AddMineral.AddMineralSpecificInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.PathObject;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.UserData;

/**
 * Created by Santiago on 11/29/2016.
 */
public class JSON {


    public static String labelListJson(ArrayList<LabelObject> labelList) {
        JSONObject firstColumnJSON = new JSONObject();

        //String _mineralID = null;

        Collection<JSONObject> items = new ArrayList<>();

        for (LabelObject _labelObj : labelList) {

            String mineralName = _labelObj.getMineralName();
            String extras = _labelObj.getExtra();
            int index = _labelObj.getIndex();
            long tableId = _labelObj.getDbTableId();

            ///**         NOTE
            // if in the future we require the formula of the mineral without having to open the localDb
            // We can add the formula as a Json field here for each mineral
            // That way we can avoid having to access 2 dB for something we can get from one if we add it here.
            // */

            //String temp = mineralName.charAt(0)+""+mineralName.charAt(1)+mineralName.charAt(2);
//
            //if(_mineralID == null){
            //    _mineralID = temp;
            //}else{
            //    _mineralID = _mineralID + "_" + temp;
            //}

            if(extras == null) extras = "none"; //or leave as null?

            JSONObject item1 = recordMineralPersonalDb(mineralName, extras, index, tableId);

            items.add(item1);

        }
        try {
            firstColumnJSON.put(MyKeyConstants.MINERAL_LABEL, new JSONArray(items));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("addImage", "firstColumnJSON: " + firstColumnJSON.toString());

        return firstColumnJSON.toString();
    }


    public static JSONObject recordMineralPersonalDb(String _mineral, String _extra, int _index, long _id){
        try {
            JSONObject _json = new JSONObject();
            _json.put(MyKeyConstants.MINERAL_KEY, _mineral);
            _json.put(MyKeyConstants.EXTRA_KEY, _extra);
            _json.put(MyKeyConstants.INDEX_KEY, _index);
            _json.put(MyKeyConstants.TABLE_ID, _id);
            return _json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUserEnteredDataJson(ArrayList<UserData> dataPropName){

        Collection<JSONObject> data = new ArrayList<>();

        JSONObject jSonObjFinal =  new JSONObject();

        for(UserData objectData: dataPropName) {
            try {

                boolean isString = false;
                boolean isJson = false;

                if(objectData.getPropertyDataObj() !=  null){
                    isString = objectData.getPropertyDataObj().getClass().equals(String.class); //True
                }

                if(objectData.getPropertyDataObj() != null){
                    isJson = objectData.getPropertyDataObj().getClass().equals(JSONObject.class); //True
                }

                JSONObject jSonObj =  new JSONObject();

                if(isString){
                    //jSonObj.put("PROPERTY_DATA", objectData.getPropertyDataObj());
                    jSonObj.put(MyKeyConstants.PROPERTY_DATA, objectData.getPropertyDataObj());
                }

                if(isJson){
                    //jSonObj.put("PROPERTY_DATA", objectData.getPropertyDataObj());
                    jSonObj.put(MyKeyConstants.PROPERTY_DATA, objectData.getPropertyDataObj());
                }

                //jSonObj.put("PROPERTY_NAME", objectData.getPropertyName());
                //jSonObj.put("DIALOG_TYPE", objectData.getDialogType());

                jSonObj.put(MyKeyConstants.PROPERTY_NAME, objectData.getPropertyName());
                jSonObj.put(MyKeyConstants.DIALOG_TYPE, objectData.getDialogType());

                JSONObject jSonObj2 =  new JSONObject();

                jSonObj2.put(objectData.getJsonKey(), jSonObj);

                jSonObjFinal.put(objectData.getJsonKey(), jSonObj);

                data.add(jSonObj2);

            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }

        //Log.d("MineralListActivity","jSonObjFinal: "+jSonObjFinal.toString() );

        JSONObject jsonObj =  new JSONObject();
        try {
            //jsonObj.put("MINERAL_INFO_USER_SET", jSonObjFinal);
            jsonObj.put(MyKeyConstants.MINERAL_INFO_USER_SET, jSonObjFinal);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

    public static String getImgPathJson(ArrayList<PathObject> dataPropName){

        Collection<JSONObject> data = new ArrayList<>();

        for(PathObject objectData: dataPropName) {
            try {
                JSONObject jSonObj =  new JSONObject();
                jSonObj.put(MyKeyConstants.TIMESTAMP, objectData.getTimeStamp());

                if(objectData.isOrig()){
                    jSonObj.put(MyKeyConstants.MINERAL_IMG_PATH_ORIG_KEY, objectData.getPath());
                    //jSonObj.put("THUMBNAIL_IMG_ORIG_PATH", objectData.getThumbPath());
                    jSonObj.put(MyKeyConstants.THUMBNAIL_IMG_ORIG_PATH, objectData.getThumbPath());
                }
                if(objectData.isMod()){
                    jSonObj.put(MyKeyConstants.MINERAL_IMG_PATH_MOD_KEY, objectData.getPath());
                    //jSonObj.put("THUMBNAIL_IMG_MOD_PATH", objectData.getThumbPath());
                    jSonObj.put(MyKeyConstants.THUMBNAIL_IMG_MOD_PATH, objectData.getThumbPath());
                }

                data.add(jSonObj);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObj =  new JSONObject();
        try {
            jsonObj.put(MyKeyConstants.MINERAL_IMG_PATH_KEY, new JSONArray(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

}
