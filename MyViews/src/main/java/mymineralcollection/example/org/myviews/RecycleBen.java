package mymineralcollection.example.org.myviews;

import android.util.Log;

import java.io.Serializable;

import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.Views.CardOptions;

/**
 * Created by Santiago on 12/4/2016.
 */
public class RecycleBen implements Serializable {

    private int viewType;

    private String image_url;

    private String description;

    private Object dataObj;
    private int tableId;

    public CardOptions cardOptions;

    public ColorObject colorObj;

    private int dialogType;

    private String jSonKey;

    /**
     *
     * @param viewType - The View type, can be the same in the arrayList
     * @param dialogType - The dialog type based on the  UserDataArray constant and MyConstants. Should be unique.
     *                   This field is used afterwards to know where the click event is comming from.
     * @param description -
     * @param dataObj -
     * @param cardOptions -
     */
    public RecycleBen(int viewType, int dialogType, int tableId, String description, Object dataObj, CardOptions cardOptions) {
        this.description = description;
        this.dataObj = dataObj;
        this.cardOptions = cardOptions;
        this.viewType = viewType;
        this.tableId = tableId;
        this.dialogType = dialogType;

        this.colorObj = new ColorObject();
    }


    public RecycleBen(int viewType, int dialogType, int tableId, String jSonKey, String description, Object dataObj, CardOptions cardOptions) {
        this.description = description;
        this.dataObj = dataObj;
        this.cardOptions = cardOptions;
        this.viewType = viewType;
        this.tableId = tableId;
        this.dialogType = dialogType;

        this.jSonKey = jSonKey;

        this.colorObj = new ColorObject();
    }


    public RecycleBen(int viewType, int dialogType, CardOptions cardOptions, ColorObject colorJson) {
        this.cardOptions = cardOptions;
        this.viewType = viewType;
        this.dialogType = dialogType;
        this.colorObj = new ColorObject();

        this.colorObj = colorJson;
    }


    public RecycleBen(int viewType, int dialogType, int tableId, String jSonKey, String description, Object dataObj, CardOptions cardOptions, ColorObject colorJson) {
        this.description = description;
        this.dataObj = dataObj;
        this.cardOptions = cardOptions;
        this.viewType = viewType;
        this.tableId = tableId;
        this.dialogType = dialogType;

        this.jSonKey = jSonKey;

        this.colorObj = colorJson;
    }


    public RecycleBen(int viewType, int dialogType, int tableId, String description, Object dataObj, CardOptions cardOptions, ColorObject colorObj) {
        this.description = description;
        this.dataObj = dataObj;
        this.cardOptions = cardOptions;
        this.viewType = viewType;
        this.tableId = tableId;
        this.dialogType = dialogType;

        this.colorObj = colorObj;
    }

    /**
     * GETTERS
     */

    public String getDescription() {
        return description;
    }
    public Object getDataObj() {
        return dataObj;
    }
    public CardOptions getCardOptions() {
        return cardOptions;
    }
    public int getTableId() {
        return tableId;
    }
    public int getDialogType() {
        return dialogType;
    }
    public ColorObject getColorObj() {
        return colorObj;
    }
    public String getJsonKey() {
        return jSonKey;
    }

    /** -colorObj- */

    /*************************************************************/
    public int getColorNumber() {
        return colorObj.getColorNumber();
    }
    public int getColorIndex() {
        return colorObj.getColorNumberIndex();
    }
    public String getColorName() {
        return colorObj.getColorName();
    }


    public void printBean(String TAG){
        Log.e(TAG, "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        Log.d(TAG, "---- printBean ----");
        Log.d(TAG, "JsonKey: "+getJsonKey());

        Log.d(TAG, "Description: "+getDescription());
        Log.d(TAG, "DataObj    : "+getDataObj());

        Log.d(TAG, "TableId: "+getTableId());
        Log.d(TAG, "DialogType: "+getDialogType());

        Log.d(TAG, "---- CardOptions ----"+
                "\nTittleTextAreaColor     : " + getCardOptions().getTittleTextAreaColor() +
                " - TittleTextColor        : " + getCardOptions().getTittleTextColor() +
                "\nExpandableTextAreaColor : " + getCardOptions().getExpandableTextAreaColor() +
                " - ExpandableTextColor    : " + getCardOptions().getExpandableTextColor() +
                "\nIsExpandEnable          : " + getCardOptions().isExpandEnable() +
                "\nAnimationSpeed          : " + getCardOptions().getAnimationSpeed() +
                "\nSkipCollapseAtPosition  : " + getCardOptions().getSkipCollapseAtPosition()
        );

        if(getColorName() != null) {
            Log.d(TAG, "---- ColorObj ----" +
                    "\nColorNumber      : " + getColorNumber() +
                    "\nColorIndex       : " + getColorIndex() +
                    "\nColorName        : " + getColorName());
        }else {
            Log.e(TAG, "ColorObj has no Data");
        }


        Log.e(TAG, "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }

    /**
     * SETTERS
     */

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
    public int getViewType() {
        return viewType;
    }
    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }

    /** -colorObj- */

    public void setColorNumber(int _color) {
        colorObj.setColorNumber(_color);
    }
    public void setColorIndex(int _color) {
        colorObj.setColorNumberIndex(_color);
    }
    public void setColorName(String _name) {
        colorObj.setColorName(_name);
    }

    /*************************************************************/



    //---  todo: errase
    /**
     * SimpleImage.class
     * SimpleImageWithText.class
     * @return
     */
    public String getImage_url() {
        return image_url;
    }


}
