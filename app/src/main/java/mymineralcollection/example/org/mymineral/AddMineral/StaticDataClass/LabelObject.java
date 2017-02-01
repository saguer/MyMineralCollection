package mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Santiago on 10/12/2016.
 */
public class LabelObject implements Serializable{

    String mineralName;
    String extra;

    int index;
    String formula;
    boolean queueDone = false;
    boolean showLabel = false;

    Bundle bundle;

    private long dbTableId;

    /**
     *
     * @param _mineralName
     * @param _extra
     * @param _index
     * @param _dbTableId
     */
    public LabelObject(String _mineralName, String _extra, int _index, long _dbTableId) {
        this.mineralName = _mineralName;
        this.extra = _extra;
        this.index = _index;
        this.dbTableId = _dbTableId;
    }

    /**
     *
     * @param _mineralName
     * @param _extra
     * @param _formula
     * @param _queueDone
     * @param _showLabel
     * @param _index
     */
    public LabelObject(String _mineralName, String _extra, String _formula, boolean _queueDone, boolean _showLabel, int _index, long _dbTableId) {
        this.mineralName = _mineralName;
        this.extra = _extra;
        this.index = _index;
        this.formula = _formula;
        this.queueDone = _queueDone;
        this.showLabel = _showLabel;
        this.dbTableId = _dbTableId;
    }

    /**
     *
     * @param _mineralName
     * @param _extra
     * @param _queueDone
     * @param _showLabel
     * @param _index
     */
    public LabelObject(String _mineralName, String _extra, boolean _queueDone, boolean _showLabel, int _index, long _dbTableId) {
        this.mineralName = _mineralName;
        this.extra = _extra;
        this.index = _index;
        this.queueDone = _queueDone;
        this.showLabel = _showLabel;
        this.dbTableId = _dbTableId;
    }

    public LabelObject(String _mineralName, String _extra, Bundle _bundle, long _dbTableId) {
        this.mineralName = _mineralName;
        this.extra = _extra;
        this.bundle = _bundle;
        this.dbTableId = _dbTableId;
    }


    public LabelObject() {

    }

    public LabelObject(int _index) {
        this.index = _index;
    }

    public String getMineralName() {
        return mineralName;
    }

    public String getExtra() {
        return extra;
    }

    public void setMineralName(String mineralName) {
        this.mineralName = mineralName;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public boolean isQueueDone() {
        return queueDone;
    }

    public void setQueueDone(boolean queueDone) {
        this.queueDone = queueDone;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public boolean isShowLabel() {
        return showLabel;
    }


    public long getDbTableId() {
        return dbTableId;
    }

    public void setDbTableId(long dbTableId) {
        this.dbTableId = dbTableId;
    }

    public Bundle getBundle() {
        return bundle;
    }


}


