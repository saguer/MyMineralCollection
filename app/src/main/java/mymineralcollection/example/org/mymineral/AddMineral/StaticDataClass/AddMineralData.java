package mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.Class.UserData;
import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.GetColorArray;

/**
 * Created by Santiago on 11/27/2016.
 */
public class AddMineralData {

    private static ArrayList<PathObject> URI_PATH = null;
    private static Uri IMG_URI_RESULT = null;
    public static int IMAGE_LOAD_COUNTER = 0;
    public static int MOD_COUNTER = 0;
    private static ArrayList<LabelObject> LABEL_ITEMS = null;

    //TODO: we need a way to detect if the system erased the static data, ex: the application was in the background too long - DONE
    //Methos.checkLoadActivityState(this);

    public static void initEverything(){
        URI_PATH = new ArrayList<>();
        IMG_URI_RESULT = null;
        IMAGE_LOAD_COUNTER = 0;
        MOD_COUNTER = 0;
        LABEL_ITEMS = new ArrayList<>();
        userEnteredData = new ArrayList<>();
    }

    public static void eraseEverything(){
        URI_PATH = null;
        IMG_URI_RESULT = null;
        IMAGE_LOAD_COUNTER = 0;
        MOD_COUNTER = 0;
        LABEL_ITEMS = null;
    }

    /** ------------------------ URI_PATH ------------------------------- **/

    public static boolean isPathNull(){
        return (URI_PATH == null);
    }

    public static void addUriPathObj(String _path, String _thumpPath, boolean _orig, boolean _mod, long _timeStamp){
        URI_PATH.add(new PathObject(_path, _thumpPath, _orig, _mod, _timeStamp));
    }

    public static ArrayList<PathObject> getUriPathArrayList() {
        return URI_PATH;
    }

    public static void printData(String _tag){
        Log.e(_tag, "----------------------");
        for(PathObject obj: URI_PATH){
            Log.d(_tag, "isOrig: " + obj.isOrig() + " - isMod: " + obj.isMod());
        }
        Log.e(_tag, "----------------------");
    }

    public static String[] getUriOrigStringPath(){
        String[] result = new String[URI_PATH.size()];

        int index = 0;
        for(PathObject obj: URI_PATH){
            result[index] = obj.getPath();
            index++;
        }

        return result;
    }
    public static String[] getUriTthumbStringPath(){
        String[] result = new String[URI_PATH.size()];

        int index = 0;
        for(PathObject obj: URI_PATH){
            result[index] = obj.getThumbPath();
            index++;
        }

        return result;
    }


    public static Uri getImgUriResult() {
        return IMG_URI_RESULT;
    }

    public static void setImgUriResult(Uri imgUriResult) {
        IMG_URI_RESULT = imgUriResult;
    }

    /** ------------------------ LABEL_ITEMS ------------------------------- **/

    public static int getLabelItemSize(){
        return LABEL_ITEMS.size();
    }

    public static boolean isLabelItemNull(){
        return (LABEL_ITEMS == null);
    }

    public static void clearLabelItemSize(){
        LABEL_ITEMS.clear();
    }

    public static void addLabelItem(LabelObject _obj){
        LABEL_ITEMS.add(_obj);
    }

    public static void removeLabelItem(int _index){
        LABEL_ITEMS.remove(_index);
    }

    public static ArrayList<LabelObject> getLabelItemsArrayList() {
        return LABEL_ITEMS;
    }

    public static void setLabelItems(int _index, LabelObject _obj) {
        LABEL_ITEMS.set(_index, _obj);

    }

    public static LabelObject getLabelItems(int _index) {
        return LABEL_ITEMS.get(_index);
    }

    public static String getLettersForName(){
        String name = null;

        for(LabelObject obj: LABEL_ITEMS){
            String objName = obj.getMineralName();
            String tempName = objName.charAt(0)+""+objName.charAt(1)+objName.charAt(2);

            if(name == null){
                name = tempName;
            }else{
                name = name + "_" + tempName;
            }
        }
        return name;
    }

    /** TODO: 1/22/2017 - remove the generation of the colorArray. that is old! generate the random index from the XML array.
     *  In myViews set that the array is taken from GetColorArray.class (less room for confusion)
     *
     *
     */

    /** ------------------------ USER_DATA_ITEMS ------------------------------- **/

    private static ArrayList<UserData> userEnteredData = new ArrayList<>();

    private static ColorObject colorObj;

    public static ArrayList<UserData> getUserEnteredData() {
        return userEnteredData;
    }

    public static void setUserEnteredData(ArrayList<UserData> userEnteredData) {
        AddMineralData.userEnteredData = userEnteredData;
    }

    public static void setRandomColor(Context mContext) {
        if(userEnteredData.size() == 0) {
            Log.e("MyListActivity", "setRandomColor_old");
            GetColorArray randomColor = new GetColorArray(mContext);
            randomColor.generateRandomColor();
            colorObj = randomColor.getColorObj();
        }
    }

    public static ColorObject getColorObj() {
        return colorObj;
    }

    public static void setColorObj(ColorObject _colorObj) {
        colorObj = _colorObj;
    }

    /*
    private static GetColorArray randomColor;

    public static void setRandomColor_old(Context mContext) {
        if(userEnteredData.size() == 0) {
            Log.e("MyListActivity", "setRandomColor_old");
            randomColor = new GetColorArray(mContext);
            randomColor.generateRandomIndex();//must be first
            randomColor.generateColorArray(); //must be second
        }
    }

    public static void setColor_old(Context mContext, ColorObject colorObj) {
        //if(userEnteredData.size() == 0) {
            Log.d("MyListActivity", "setColor");

            randomColor = new GetColorArray(mContext);
            randomColor.setColorIndex(colorObj.getColorNumberIndex());//must be first
            randomColor.generateColorArray(); //must be second
        //}
    }

    public static GetColorArray getColor_old() {
        return randomColor;
    }
    */

    //----

}

