package mymineralcollection.example.org.mymineral.DataWrappers;

import java.io.Serializable;
import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;

/**
 * Created by Santiago on 10/12/2016.
 */
public class DataWrapper_LabelObject implements Serializable {

    private ArrayList<LabelObject> Label;

    public DataWrapper_LabelObject(ArrayList<LabelObject> data) {
        this.Label = data;
    }

    public ArrayList<LabelObject> getLabelObject() {
        return this.Label;
    }

}