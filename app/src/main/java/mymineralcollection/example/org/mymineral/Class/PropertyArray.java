package mymineralcollection.example.org.mymineral.Class;

/**
 * Created by Santiago on 9/15/2016.
 */
public class PropertyArray {
    //TODO: remove the _ from the vars leave only on the Constructor and Setters


    private String _key = "";
    private String _text = "";
    private boolean _extraProcessing = false;
    private boolean allowModify = false;
    private int _itemType = 0;

    public PropertyArray(PropertyObject _newElement) {
        this._key = _newElement.get_key();
        this._text = _newElement.get_text();
        this._extraProcessing = _newElement.get_extraProcessing();
        this._itemType = _newElement.get_itemType();
        this.allowModify = _newElement.isAllowModify();
    }


    public boolean getExtraProcessing() {
        return _extraProcessing;
    }

    public void setExtraProcessing(boolean _key) {
        this._extraProcessing = _key;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String _key) {
        this._key = _key;
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }


    public int getItemType() {
        return _itemType;
    }

    public void setItemType(int _itemType) {
        this._itemType = _itemType;
    }

    public boolean isAllowModify() {
        return allowModify;
    }

    public void setAllowModify(boolean allowModify) {
        this.allowModify = allowModify;
    }

}

