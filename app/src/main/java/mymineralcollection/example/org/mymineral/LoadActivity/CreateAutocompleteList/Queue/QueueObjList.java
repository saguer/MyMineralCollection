package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.Queue;

import java.util.ArrayList;

/**
 * Created by Santiago on 9/26/2016.
 */
public class QueueObjList {
    private int index;
    private ArrayList<String> objList = null;

    private int devideBy;

    private boolean simpleList = false;
    private boolean completeList = false;

    private String dBName = null;

    public QueueObjList() {
    }

    public QueueObjList(ArrayList<String> _objList) {
        this.objList = _objList;
    }

    public QueueObjList(ArrayList<String> _objList, int _index) {
        this.objList = _objList;
        this.index = _index;
    }


    public QueueObjList(ArrayList<String> _objList, int _index, int _devideBy) {
        this.objList = _objList;
        this.index = _index;
        this.devideBy = _devideBy;
    }


    public QueueObjList(ArrayList<String> _objList, int _index, int _devideBy, String _dBName) {
        this.objList = _objList;
        this.index = _index;
        this.devideBy = _devideBy;
        this.dBName = _dBName;
    }

    public void setDevideBy(int devideBy) {
        this.devideBy = devideBy;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<String> getObjList() {
        return objList;
    }

    public int getDevideBy() {
        return devideBy;
    }

    public boolean isCompleteList() {
        return completeList;
    }

    public void setCompleteList(boolean completeList) {
        this.completeList = completeList;
    }

    public boolean isSimpleList() {
        return simpleList;
    }

    public void setSimpleList(boolean simpleList) {
        this.simpleList = simpleList;
    }

    public String getdBName() {
        return dBName;
    }

    public void setdBName(String dBName) {
        this.dBName = dBName;
    }

}