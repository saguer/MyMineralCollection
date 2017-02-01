package mymineralcollection.example.org.mymineral.DataWrappers;

import java.io.Serializable;
import java.util.ArrayList;

import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 10/12/2016.
 */
public class DataWrapper_RecycleBen implements Serializable {

    private ArrayList<RecycleBen> bean;

    public DataWrapper_RecycleBen(ArrayList<RecycleBen> data) {
        this.bean = data;
    }

    public ArrayList<RecycleBen> getRecycleBeanObject() {
        return this.bean;
    }

}