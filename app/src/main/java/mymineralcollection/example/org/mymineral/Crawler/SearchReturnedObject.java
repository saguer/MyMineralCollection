package mymineralcollection.example.org.mymineral.Crawler;

import android.os.Bundle;

import org.jsoup.nodes.Element;

public class SearchReturnedObject{

    private boolean _searchSuccess = false;
    private boolean _contactAgainURL = false;
    private String _returnedURL = null;
    private Element _returnedDoc = null;
    private Bundle _bundle = null;

    public SearchReturnedObject(Element returnedDoc, Bundle bundle) {
        this._returnedDoc = returnedDoc;
        this._bundle = bundle;
    }

    public Bundle get_bundle() {
        return _bundle;
    }

    public boolean get_searchSuccess() {
        return _searchSuccess;
    }

    public void set_searchSuccess(boolean _searchSuccess) {
        this._searchSuccess = _searchSuccess;
    }

    public boolean get_contactAgainURL() {
        return _contactAgainURL;
    }

    public void set_contactAgainURL(boolean _contactAgainURL) {
        this._contactAgainURL = _contactAgainURL;
    }

    public String get_returnedURL() {
        return _returnedURL;
    }

    public void set_returnedURL(String _returnedURL) {
        this._returnedURL = _returnedURL;
    }

    public Element getReturnedDoc() {
        return _returnedDoc;
    }

}
