package mymineralcollection.example.org.mymineral.Class;

/**
 * Created by Santiago on 9/12/2016.
 */
public class MyObject {

    private String objectMineralName = "none";
    private String tableOrigin = "none";//Use to know what table the item came from
    private boolean objectMineralPrevSearch = false;
    private boolean objectMineralInPersonalDataBase = false;
    private boolean objectMineralSearchSuccess = false;
    private int objectId = 0;
    private double objectSearchResultPercent = 0.0;

    private int objectMineralCounter = 0;


    // constructor for adding sample data
    public MyObject(String objectName){
        objectName = objectName.replace("'"," ");
        this.objectMineralName = objectName;
    }

    // constructor for adding sample data
    public MyObject(String _objectName, boolean _objectMineralPrevSearch, boolean _objectMineralInPersonaldb){
        _objectName = _objectName.replace("'"," ");
        this.objectMineralName = _objectName;
        this.objectMineralPrevSearch = _objectMineralPrevSearch;
        this.objectMineralInPersonalDataBase = _objectMineralInPersonaldb;
    }

    // constructor for adding sample data
    public MyObject(String _objectName, boolean _objectMineralPrevSearch, boolean _objectMineralInPersonaldb, int _index){
        _objectName = _objectName.replace("'"," ");
        this.objectMineralName = _objectName;
        this.objectMineralPrevSearch = _objectMineralPrevSearch;
        this.objectMineralInPersonalDataBase = _objectMineralInPersonaldb;
        this.objectId = _index;
    }

    /**
     *
     * @param _objectName
     * @param _objectMineralPrevSearch
     * @param _objectMineralInPersonaldb
     * @param _objectMineralSearchSuccess
     * @param _objectSearchResultPercent
     * @param _mineralCounter
     * @param _tableOrigin
     * @param _index
     */
    public MyObject(String _objectName,
                    boolean _objectMineralPrevSearch,
                    boolean _objectMineralInPersonaldb,

                    boolean _objectMineralSearchSuccess,
                    double _objectSearchResultPercent,
                    int _mineralCounter,
                    //todo: add mineralCounter
                    String _tableOrigin,
                    int _index){
        _objectName = _objectName.replace("'"," ");
        this.objectMineralName = _objectName;
        this.objectMineralPrevSearch = _objectMineralPrevSearch;
        this.objectMineralInPersonalDataBase = _objectMineralInPersonaldb;

        this.objectMineralSearchSuccess = _objectMineralSearchSuccess;
        this.objectSearchResultPercent = _objectSearchResultPercent;
        this.tableOrigin = _tableOrigin;
        this.objectId = _index;
        this.objectMineralCounter = _mineralCounter;
    }

    public MyObject() {
    }

    public String getMineralName() {
        return objectMineralName;
    }


    public void setMineralName(String objectMineralName) {
        this.objectMineralName = objectMineralName;
    }

    public boolean getPrevSearch() {
        return objectMineralPrevSearch;
    }

    public void setPrevSearch(boolean objectMineralPrevSearch) {
        this.objectMineralPrevSearch = objectMineralPrevSearch;
    }

    public boolean getInPersonaldb() {
        return objectMineralInPersonalDataBase;
    }

    public void setPersonaldb(boolean objectMineralInPersonaldb) {
        this.objectMineralInPersonalDataBase = objectMineralInPersonaldb;
    }

    public int getId() {
        return objectId;
    }

    public void setId(int objectId) {
        this.objectId = objectId;
    }

    public String getTableOrigin() {
        return tableOrigin;
    }

    public void setTableOrigin(String tableOrigin) {
        this.tableOrigin = tableOrigin;
    }


    public boolean getSearchSuccess() {
        return objectMineralSearchSuccess;
    }

    public void setObjectMineralSearchSuccess(boolean objectMineralSearchSuccess) {
        this.objectMineralSearchSuccess = objectMineralSearchSuccess;
    }

    public double getMineralPercent() {
        return objectSearchResultPercent;
    }

    public void setMineralPercent(double objectMineralPercent) {
        this.objectSearchResultPercent = objectMineralPercent;
    }


    public int getObjectMineralCounter() {
        return objectMineralCounter;
    }

    public void setObjectMineralCounter(int objectMineralCounter) {
        this.objectMineralCounter = objectMineralCounter;
    }
}
