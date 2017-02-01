package mymineralcollection.example.org.mymineral.Class;

/**
 * Created by Santiago on 9/20/2016.
 */
public class PropertyObject {
    //TODO: remove the _ from the vars leave only on the Constructor and Setters
    private String _key = null;
    private String _text = null;
    private String _html = null;

    private boolean _extraProcessing = false;

    private boolean allowModify = false;

    private int _itemType = 0;

    public PropertyObject(String _key, String _text, boolean _extraProcessing, int _itemType) {
        this._key = _key;
        this._text = _text;
        this._extraProcessing = _extraProcessing;
        this._itemType = _itemType;
    }

    public PropertyObject(String _key, String _text, boolean _extraProcessing, int _itemType, boolean _allowModify) {
        this._key = _key;
        this._text = _text;
        this._extraProcessing = _extraProcessing;
        this._itemType = _itemType;
        this.allowModify = _allowModify;
    }

    public PropertyObject(String _key, String _text, String _html) {
        this._key = _key;
        this._text = _text;
        this._html = _html;
    }

    public PropertyObject(String _key) {
        this._key = _key;
    }
    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public boolean get_extraProcessing() {
        return _extraProcessing;
    }

    public void set_extraProcessing(boolean _extraProcessing) {
        this._extraProcessing = _extraProcessing;
    }

    public String get_html() {
        return _html;
    }

    public void set_html(String _html) {
        this._html = _html;
    }

    public int get_itemType() {
        return _itemType;
    }

    public void set_itemType(int _itemType) {
        this._itemType = _itemType;
    }

    public boolean isAllowModify() {
        return allowModify;
    }

    public void setAllowModify(boolean allowModify) {
        this.allowModify = allowModify;
    }
}
