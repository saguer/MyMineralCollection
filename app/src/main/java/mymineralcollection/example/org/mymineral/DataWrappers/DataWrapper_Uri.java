package mymineralcollection.example.org.mymineral.DataWrappers;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Santiago on 10/12/2016.
 */
public class DataWrapper_Uri implements Serializable {

    private ArrayList<Uri> uri;

    public DataWrapper_Uri(ArrayList<Uri> data) {
        this.uri = data;
    }

    public ArrayList<Uri> getRecycleUriObject() {
        return this.uri;
    }

}