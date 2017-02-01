package mymineralcollection.example.org.mymineral.AddMineral.Queue;

/**
 * Created by Santiago on 9/26/2016.
 */
public class QueueObjItem {
    private int index = 0;
    private String mineralName = null;


    private boolean internetAccess = false;



    public QueueObjItem() {
    }

    public boolean getInternetAccess() {
        return internetAccess;
    }

    public void setInternetAccess(boolean internetAccess) {
        this.internetAccess = internetAccess;
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getmineralName() {
        return mineralName;
    }

    public void setmineralName(String mineralName) {
        this.mineralName = mineralName;
    }

}