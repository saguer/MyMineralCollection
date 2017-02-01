package mymineralcollection.example.org.mymineral.AddMineral;

import android.os.Bundle;

import java.util.Observable;

/**
 * Created by Santiago on 10/20/2016.
 */
public class ObservableObject extends Observable {

    /** Dialog **/
    public static final int LAUNCH_ACTIVITY = 101;
    public static final int SHOW_DIALOG = 201;
    public static final int HIDE_DIALOG = 301;

    /** MineralList Items **/
    public static final int MINERAL_LIST = 401;

    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

    private ObservableObject() {
    }

    public void updateValue(Bundle data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}