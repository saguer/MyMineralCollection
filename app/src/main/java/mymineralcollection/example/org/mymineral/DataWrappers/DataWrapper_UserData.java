package mymineralcollection.example.org.mymineral.DataWrappers;

import java.io.Serializable;
import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.Class.UserData;

/**
 * Created by Santiago on 10/12/2016.
 */
public class DataWrapper_UserData implements Serializable {

    private ArrayList<UserData> bean;

    public DataWrapper_UserData(ArrayList<UserData> data) {
        this.bean = data;
    }

    public ArrayList<UserData> getRecycleBeanObject() {
        return this.bean;
    }

}