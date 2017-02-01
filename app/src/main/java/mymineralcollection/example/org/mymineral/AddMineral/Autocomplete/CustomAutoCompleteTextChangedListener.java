package mymineralcollection.example.org.mymineral.AddMineral.Autocomplete;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

/**
 * Created by Santiago on 9/14/2016.
 */
public class CustomAutoCompleteTextChangedListener implements TextWatcher, TextView.OnEditorActionListener {

    public static final String TAG = "myApp";
    Context context;
    Activity activity;

    private CustomAutoCompleteTextChangedListener_Interface mListener;


    public CustomAutoCompleteTextChangedListener(Context _context) {
        this.context = _context;
        try {
            this.mListener = (CustomAutoCompleteTextChangedListener_Interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        //MyReceiverMergeDbIntoNewDb _myReceiverMergeDbIntoNewDb = new MyReceiverMergeDbIntoNewDb();
        //LocalBroadcastManager.getInstance(context).registerReceiver(_myReceiverMergeDbIntoNewDb, new IntentFilter("custom-event-name"));

    }

    public CustomAutoCompleteTextChangedListener(Fragment fragMinralProp) {
        this.context = fragMinralProp.getActivity();
        try {
            this.mListener = (CustomAutoCompleteTextChangedListener_Interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        try {
            // if you want to see in the logcat what the user types
            Log.e(TAG, "User input: " + userInput);

            //AutoCompleteActivity mainActivity = ((AutoCompleteActivity) context);

            // updateCounterAndPercentage the adapater
            //mainActivity.myAdapter.notifyDataSetChanged();
            this.mListener.myAdapterNotifyDataSetChanged_Interface();

            // get suggestions from the database
            //AutoCompleteDatabaseHandler _databaseH = mainActivity.getDatabaseHandler();
            AutoCompleteDatabaseHandler _databaseH = this.mListener.getDatabaseHandler_Interface();
            MyObject[] myObjs = _databaseH.read(userInput.toString());
            _databaseH.close();


            // updateCounterAndPercentage the adapter
            //mainActivity.myAdapter = new AutocompleteCustomArrayAdapter(mainActivity, R.layout.list_view_row_item, myObjs);
            //mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);
            this.mListener.updateAdapter_Interface(myObjs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        Log.e(TAG, "KEY DOWN ");
        return false;
    }


    public interface CustomAutoCompleteTextChangedListener_Interface {
        void myAdapterNotifyDataSetChanged_Interface();
        void updateAdapter_Interface(MyObject[] myObjs);
        AutoCompleteDatabaseHandler getDatabaseHandler_Interface();

    }

}