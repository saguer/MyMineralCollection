package mymineralcollection.example.org.mymineral.Class;

/**
 * Created by Santiago on 12/6/2016.
 */

/**
 * Data object used to store properties that the user must fill
 */
public class UserData {


    private String propertyName = null;
    private Object propertyDataObj = null;
    private String jsonKey = null;
    private int dialogType;
    private int viewType;

    public UserData(String jsonKey, String propertyName, Object propertyDataObj, int dialogType, int viewType) {

        this.jsonKey = jsonKey;
        this.propertyName = propertyName;
        this.propertyDataObj = propertyDataObj;
        this.dialogType = dialogType;
        this.viewType = viewType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    //public String getPropertyData() {
    //    return propertyData;
    //}

    public int getDialogType() {
        return dialogType;
    }

    public int getViewType() {
        return viewType;
    }

    public Object getPropertyDataObj() {
        return propertyDataObj;
    }

    public void setPropertyDataObj(Object propertyDataObj) {
        this.propertyDataObj = propertyDataObj;
    }

    public String getJsonKey() {
        return jsonKey;
    }

    public void setJsonKey(String jsonKey) {
        this.jsonKey = jsonKey;
    }
}