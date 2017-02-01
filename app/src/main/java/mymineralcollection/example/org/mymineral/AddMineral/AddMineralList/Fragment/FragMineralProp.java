package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.AddMineral.Queue.ItemConsumer;
import mymineralcollection.example.org.mymineral.AddMineral.Queue.QueueObjItem;
import mymineralcollection.example.org.mymineral.AutoResizeTextView;
import mymineralcollection.example.org.mymineral.AddMineral.Autocomplete.AutocompleteCustomArrayAdapter;
import mymineralcollection.example.org.mymineral.AddMineral.Autocomplete.CustomAutoCompleteTextChangedListener;
import mymineralcollection.example.org.mymineral.AddMineral.Autocomplete.CustomAutoCompleteView;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

/**
 * Created by Santiago on 10/2/2016.
 */
public class FragMineralProp extends Fragment implements View.OnKeyListener, AdapterView.OnItemClickListener, View.OnClickListener {
    public FragMineralProp(){

    }


    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    //View view;
    private ScrollView scrollView;
    private FragMineralProp_interface mInterface;
    private String dbName;
    /**
     Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     since we are extending to customize the view and disable filter
     The same with the XML view, type will be CustomAutoCompleteView
     **/
    private CustomAutoCompleteView myAutoComplete;

    public AutoResizeTextView mineralName_textView;

    // adapter for auto-complete
    private ArrayAdapter<MyObject> myAdapter;

    private mymineralcollection.example.org.mymineral.AddMineral.Queue.ItemConsumer ItemConsumer;

    TextView add_textView;
    TextView update_textView;
    TextView delete_textView;
    TextView cancel_textView;

    LinearLayout control_LinearLayout;
    LinearLayout inputMineral_LinearLayout;

    ToggleButtonGroupTableLayout mineralExtra_ToggleButtonGroupTableLayout;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.prop_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.e("lifeCycle", "FragMineralProp: onCreateView");

        try {
            mInterface = (FragMineralProp_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        //CreateItem.NewItemAddedReceiver _itemReceiver = new CreateItem.NewItemAddedReceiver();
        //LocalBroadcastManager.getInstance(context).registerReceiver(_itemReceiver,new IntentFilter(Constant.intentItemAdded));

        scrollView = (ScrollView)v.findViewById(R.id.id_CarSettings_ScrollView);
        //scrollView.smoothScrollTo(0, pos*100);

        //--- INIT ----

        add_textView = (TextView) v.findViewById(R.id.add_textView);
        add_textView.setOnClickListener(this);

        update_textView = (TextView) v.findViewById(R.id.update_textView);
        update_textView.setOnClickListener(this);
        update_textView.setVisibility(View.GONE);

        delete_textView = (TextView) v.findViewById(R.id.delete_textView);
        delete_textView.setOnClickListener(this);
        delete_textView.setVisibility(View.GONE);

        cancel_textView = (TextView) v.findViewById(R.id.cancel_textView);
        cancel_textView.setOnClickListener(this);

        mineralName_textView = (AutoResizeTextView) v.findViewById(R.id.mineralName_textView);
        mineralName_textView.setText("- - - -");

        mineralName_textView.setTextSize(200);
        mineralName_textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        mineralName_textView.setMaxLines(1);

        //control_LinearLayout
        control_LinearLayout = (LinearLayout) v.findViewById(R.id.control_LinearLayout);
        control_LinearLayout.setVisibility(View.INVISIBLE);

        inputMineral_LinearLayout = (LinearLayout) v.findViewById(R.id.inputMineral_LinearLayout);
        inputMineral_LinearLayout.setVisibility(View.VISIBLE);

        mineralExtra_ToggleButtonGroupTableLayout = new ToggleButtonGroupTableLayout(FragMineralProp.this,v.findViewById(R.id.mineralExtra_ToggleButtonGroupTableLayout));
        v.findViewById(R.id.matrix_rad).setOnClickListener(this);
        v.findViewById(R.id.var_rad).setOnClickListener(this);
        v.findViewById(R.id.cluster_rad).setOnClickListener(this);
        v.findViewById(R.id.with_rad).setOnClickListener(this);
        v.findViewById(R.id.on_rad).setOnClickListener(this);
        v.findViewById(R.id.and_rad).setOnClickListener(this);
        //mineralExtra_ToggleButtonGroupTableLayout.setCheckedRadioButtonID(R.id.matrix_rad);
        //---------------------------------------------------------------------------------------

        myAutoComplete = (CustomAutoCompleteView) v.findViewById(R.id.item_autocomplete);
        myAutoComplete.setOnKeyListener(this);
        myAutoComplete.setOnItemClickListener(this);
        myAutoComplete.setOnClickListener(this);

        // add the listener so it will tries to suggest while the user types
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(context));

        myAutoComplete.requestFocus();

        // ObjectItemData has no value at first
        MyObject[] ObjectItemData = new MyObject[0];

        // set the custom ArrayAdapter
        myAdapter = new AutocompleteCustomArrayAdapter(context, R.layout.list_view_row_item, ObjectItemData);
        myAutoComplete.setAdapter(myAdapter);

        //----------- Queue ----
        Log.d("ItemConsumer", "updateDbFromItemConsumer");
        ItemConsumer = new ItemConsumer(context,simpleQueue, "SimpleListConsumer");
        ItemConsumer.activityRunning(true);
        ItemConsumer.run();

        return v;
    }


    public boolean updateDbFromItemConsumer(){
        Log.d("ItemConsumer", "updateDbFromItemConsumer");
        if(ItemConsumer!=null){
            ItemConsumer.setUpdateDb();
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public AutoCompleteDatabaseHandler getDatabaseHandler(){
        dbName = preferences.getString(Constant._dB_Name,Constant._dB_Name_simple);
        Log.d("AutoCompleteActivity", "dB Name: " + dbName);
        return new AutoCompleteDatabaseHandler(context,dbName);
    }


    public void myAdapterNotifyDataSetChanged_Interface() {

        myAdapter.notifyDataSetChanged();
    }

    public void updateAdapter_Interface(MyObject[] myObjs) {


        myAdapter = new AutocompleteCustomArrayAdapter(context, R.layout.list_view_row_item, myObjs);
        myAutoComplete.setAdapter(myAdapter);
    }

    public AutoCompleteDatabaseHandler getDatabaseHandler_Interface() {
        return getDatabaseHandler();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        //----------------------------------------------
        Bundle _bundle = new Bundle();
        _bundle.putBoolean("pause_merge_thread", true);
        Methods.sendMessage(context,Constant.intentMergeName,_bundle);
        //----------------------------------------------

        CustomAutoCompleteView rl = (CustomAutoCompleteView) view;
        String mineralName;
        mineralName = rl.getText().toString();

        //Log.e("AddMineralListActivity", "mineralName : " + mineralName);

        if(mineralName.contains( "\n" )){
            String text = mineralName.replace("\n", "");
            //Log.e("AddMineralListActivity", "New line : ");

            text = Methods.toTitleCase(text);

            LABEL = new LabelObject();
            LABEL.setMineralName(text);
            LABEL.setExtra(null);
            LABEL.setIndex(mInterface.size_LabelObject_FragMineralProp());

            // -- GUI
            control_LinearLayout.setVisibility(View.VISIBLE);
            mineralName_textView.setText(text);
            myAutoComplete.setText("");
        }

        return false;
    }

    //private static int addIndex = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
        RelativeLayout rl = (RelativeLayout) arg1;
        TextView tv = (TextView) rl.getChildAt(0);
        myAutoComplete.setText(tv.getText().toString());
        String mineralName;
        mineralName = tv.getText().toString();
        Methods.hideKeyboard(view,context);

        LABEL = new LabelObject();
        LABEL.setMineralName(mineralName);
        LABEL.setExtra(null);
        LABEL.setIndex(mInterface.size_LabelObject_FragMineralProp());

        // -- GUI
        control_LinearLayout.setVisibility(View.VISIBLE);
        mineralName_textView.setText(mineralName);
        myAutoComplete.setText("");
    }

    private LabelObject LABEL = new LabelObject();
    private int VIEW_TOUCH_INDEX = 0;

    private final Vector<QueueObjItem> simpleQueue = new Vector<>();



    private void addToQueue(int _index, String _mineralName){
        boolean isOnline = Methods.checkInternetConnection(context);

        if(!isOnline) Toast.makeText(context,"Cant't connect to the internet",
                Toast.LENGTH_LONG).show();

        synchronized (simpleQueue) {
            QueueObjItem _obj = new QueueObjItem();
            _obj.setIndex(_index);
            _obj.setmineralName(_mineralName);
            _obj.setInternetAccess(isOnline);

            simpleQueue.add(_obj);
            simpleQueue.notifyAll();
            Log.e("AddMineralListActivity", "Added to Queue");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.item_autocomplete: {

                //----------------------------------------------
                Bundle _bundle = new Bundle();
                _bundle.putBoolean("pause_merge_thread", true);
                Methods.sendMessage(context, Constant.intentMergeName, _bundle);
                //----------------------------------------------
                myAutoComplete.setText("");
                control_LinearLayout.setVisibility(View.INVISIBLE);

                break;
            }
            case R.id.add_textView: {

                control_LinearLayout.setVisibility(View.INVISIBLE);
                mInterface.setBottomControl_FragMineralProp(View.VISIBLE);
                mineralExtra_ToggleButtonGroupTableLayout.uncheckedAll(view);

                //----------------------------------------------
                LABEL.setExtra(EXTRAS);
                LABEL.setShowLabel(true);
                //TODO: add to ITEM list of activity
                //----------------------------------------------
                mInterface.add_LabelObject_FragMineralProp(LABEL);
                //----------------------------------------------
                addToQueue(LABEL.getIndex(),LABEL.getMineralName());
                updateDbFromItemConsumer();


                EXTRAS = null;

                break;
            }
            case R.id.update_textView: {

                add_textView.setVisibility(View.VISIBLE);
                update_textView.setVisibility(View.GONE);
                delete_textView.setVisibility(View.GONE);
                control_LinearLayout.setVisibility(View.INVISIBLE);
                inputMineral_LinearLayout.setVisibility(View.VISIBLE);
                mInterface.setBottomControl_FragMineralProp(View.VISIBLE);
                mineralExtra_ToggleButtonGroupTableLayout.uncheckedAll(view);

                LABEL.setExtra(EXTRAS);
                LABEL.setShowLabel(true);
                //TODO: updateCounterAndPercentage with the TOUCH_INDEX the ITEM list of activity
                mInterface.set_LabelObject_FragMineralProp(LABEL.getIndex(),LABEL);


                Log.d("CreateDisplayMineralData", "_key: ");
                Log.d("CreateDisplayMineralData", "_formula: "+LABEL.getFormula());

                Log.e("AddMineralListActivity", "UPDATE : " +
                        LABEL.getMineralName() + " " +
                        LABEL.getExtra() + " " +
                        //LABEL.getFormula() + " " +
                        LABEL.isQueueDone() + " " +
                        LABEL.getIndex());

                EXTRAS = null;

                break;
            }
            case R.id.delete_textView:{

                Log.e("AddMineralListActivity", "DELETE : " +
                        LABEL.getMineralName() + " " +
                        LABEL.getExtra() + " " +
                        //LABEL.getFormula() + " " +
                        LABEL.isQueueDone() + " " +
                        LABEL.getIndex());

                mInterface.remove_LabelObject_FragMineralProp(LABEL.getIndex());

                add_textView.setVisibility(View.VISIBLE);
                update_textView.setVisibility(View.GONE);
                delete_textView.setVisibility(View.GONE);
                control_LinearLayout.setVisibility(View.INVISIBLE);
                inputMineral_LinearLayout.setVisibility(View.VISIBLE);
                mInterface.setBottomControl_FragMineralProp(View.VISIBLE);
                mineralExtra_ToggleButtonGroupTableLayout.uncheckedAll(view);

                LABEL = new LabelObject();
                EXTRAS = null;

                break;
            }
            case R.id.cancel_textView: {

                add_textView.setVisibility(View.VISIBLE);
                update_textView.setVisibility(View.GONE);
                delete_textView.setVisibility(View.GONE);
                control_LinearLayout.setVisibility(View.INVISIBLE);
                inputMineral_LinearLayout.setVisibility(View.VISIBLE);
                mineralExtra_ToggleButtonGroupTableLayout.uncheckedAll(view);

                Log.e("AddMineralListActivity", "CANCEL : " +
                        LABEL.getMineralName() + " " +
                        LABEL.getExtra() + " " +
                        //LABEL.getFormula() + " " +
                        LABEL.isQueueDone() + " " +
                        LABEL.getIndex());

                EXTRAS = null;
                break;
            }
            case R.id.matrix_rad:{}
            case R.id.var_rad:{}
            case R.id.cluster_rad:{}
            case R.id.with_rad:{}
            case R.id.on_rad:{}
            case R.id.and_rad:{
                EXTRAS = mineralExtra_ToggleButtonGroupTableLayout.geButtonGroupSelectedText(v);

                if(EXTRAS != null) {
                    mineralName_textView.setText(LABEL.getMineralName() + " " + EXTRAS);
                }else {
                    mineralName_textView.setText(LABEL.getMineralName());
                }
                break;
            }
            default:{
            }
        }
    }

    private String EXTRAS = null;


    public void viewTouchIndex(int index, ArrayList<LabelObject> ITEMS){
        VIEW_TOUCH_INDEX = index;

        LABEL = new LabelObject();

        add_textView.setVisibility(View.GONE);
        update_textView.setVisibility(View.VISIBLE);
        delete_textView.setVisibility(View.VISIBLE);
        inputMineral_LinearLayout.setVisibility(View.INVISIBLE);
        control_LinearLayout.setVisibility(View.VISIBLE);
        mInterface.setBottomControl_FragMineralProp(View.INVISIBLE);

        LABEL = ITEMS.get(index);
        LABEL.setIndex(VIEW_TOUCH_INDEX);

        /*
        Log.d("AddMineralListActivity", "VIEW LABEL: " +
                LABEL.getMineralName() + " " +
                LABEL.getExtra() + " " +
                LABEL.getFormula() + " " +
                LABEL.isQueueDone() + " " +
                LABEL.getIndex());

        Log.d("AddMineralListActivity", "VIEW ITEMS: " +
                ITEMS.get(index).getMineralName() + " " +
                ITEMS.get(index).getExtra() + " " +
                ITEMS.get(index).getFormula() + " " +
                ITEMS.get(index).isQueueDone() + " " +
                ITEMS.get(index).getIndex());
        */

        String _mineralName = ITEMS.get(index).getMineralName();
        String _extras = ITEMS.get(index).getExtra();

        if(_extras!=null) {
            mineralName_textView.setText(_mineralName+" "+_extras);
        }else{
            mineralName_textView.setText(_mineralName);
        }

        Log.d("AddMineralListActivity", "_extras: " + _extras);
        if(_extras != null) {
            int _id = mineralExtra_ToggleButtonGroupTableLayout.getIdFromText(_extras);
            Log.d("AddMineralListActivity", "_id: " + _id);
                mineralExtra_ToggleButtonGroupTableLayout.setCheckedRadioButtonID(_id, view);
        }
    }


    public interface FragMineralProp_interface {

        void setBottomControl_FragMineralProp(int state);

        void add_LabelObject_FragMineralProp(LabelObject _obj);
        int  size_LabelObject_FragMineralProp();
        void set_LabelObject_FragMineralProp(int _index, LabelObject _obj);
        void remove_LabelObject_FragMineralProp(int _index);
    }
}
