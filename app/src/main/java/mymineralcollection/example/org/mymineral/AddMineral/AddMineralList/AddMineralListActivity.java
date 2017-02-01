package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralProp;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralPropResultHtmlText;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralPropResultList;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragmentItem;
import mymineralcollection.example.org.mymineral.AddMineral.Autocomplete.CustomAutoCompleteTextChangedListener;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ImageCropperFullscreenActivity;
import mymineralcollection.example.org.mymineral.AddMineral.ObservableObject;
import mymineralcollection.example.org.mymineral.AddMineral.SharedFragments.FragMinPropResult;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

public class AddMineralListActivity extends FragmentActivity implements FragMineralProp.FragMineralProp_interface,
        FragMinPropResult.FragMineralPropResult_interface,
        CustomAutoCompleteTextChangedListener.CustomAutoCompleteTextChangedListener_Interface,
        FragMineralPropResultList.FragMineralPropResultList_interface,
        FragMineralPropResultHtmlText.FragMineralPropResultHtmlText_interface,
        View.OnClickListener,
        Observer, ViewPager.OnPageChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    //This is the order that they are added to the ArrayList, needed to know for when we get the fragments in the pagerAdapter
    public static final int FRAG_LIST_RESULT_ID = 0;
    public static final int FRAG_TEXT_RESULT_ID = 1;

    PagerAdapter pagerAdapter;
    LinearLayout main_LinearLayout;
    Context context;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_add_mineral);

        context = getApplicationContext();

        Methods.checkLoadActivityState(this);

        ReceiverAddMineralList _myReceiverMergeDbIntoNewDb = new ReceiverAddMineralList();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(_myReceiverMergeDbIntoNewDb,new IntentFilter(Constant.intentAddMineralListActivity));

        main_LinearLayout= (LinearLayout) findViewById(R.id.main_LinearLayout);
        main_LinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override
    public void onGlobalLayout() {
        if (main_LinearLayout.getHeight()>0 && main_LinearLayout.getWidth()>0) {
            Methods.removeOnGlobalLayoutListener(main_LinearLayout,this);
        }

        int mHeight = main_LinearLayout.getHeight();
        int mWidth= main_LinearLayout.getWidth();

        float pxHeight = Methods.dpToPixels(mHeight,context);
        float pxWidth = Methods.dpToPixels(mWidth,context);

        Log.e("AddMineralListActivity", "pxHeight: "+pxHeight+"   height: "+mHeight);
        Log.e("AddMineralListActivity", "pxWidth: "+pxWidth+"   width: "+mWidth);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        init();

        createFragMineralPropFragment();
        addFragMineralPropFragment();

        //createFragMineralPropResultFragment();
        //addFragMineralPropResultFragment();

        Button nextStep_button = (Button) findViewById(R.id.nextStep_button);
        nextStep_button.setOnClickListener(this);

        bottomControl_LinearLayout = (LinearLayout) findViewById(R.id.bottomControl_LinearLayout);

        if (AddMineralData.getLabelItemSize() > 0) {
            pagerAdapter.updateTextArrayList(AddMineralData.getLabelItemsArrayList());
            bottomControl_LinearLayout.setVisibility(View.VISIBLE);
        } else {
            bottomControl_LinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void init(){
        //-----------
        ArrayList<FragmentItem> fragArrayList = new ArrayList<>();
        FragmentItem newItem = new FragmentItem(FRAG_LIST_RESULT_ID,"LIST",new FragMineralPropResultList());
        fragArrayList.add(newItem);

        //// TODO: 1/30/2017 - uncomment if the text tab is wanted
        //We want to see only the list therefore we comment this out
        //newItem = new FragmentItem(FRAG_TEXT_RESULT_ID,"TEXT",new FragMineralPropResultHtmlText());
        //fragArrayList.add(newItem);

        //// TODO: 1/30/2017 - set Visible if the text tab is wanted
        //and hide the element
        tabLayout.setVisibility(View.GONE);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentManager frag = getSupportFragmentManager();

        pagerAdapter = new PagerAdapter(frag, this, fragArrayList);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener (this);

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        //-----------

    }

    @Override
    public void onResume() {
        super.onResume();
        //LABEL_ITEMS = new ArrayList<>();
        Log.e("AddMineralListActivity", "AddMineralListActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //AddMineralData.LABEL_ITEMS = new ArrayList<>();

        Log.e("AddMineralListActivity", "AddMineralListActivity: onPause");
        //if(observableObj != null) {
        //    Log.e("lifeCycle", "observableObj: removed");
        //    observableObj.deleteObserver(this);
        //}
    }

    LinearLayout bottomControl_LinearLayout;

    /************** Fragment Init **************/

    FragMineralProp fragMineralProp;

    void createFragMineralPropFragment(){
        fragMineralProp = new FragMineralProp();
    }
    void removeFragMineralPropFragment(){
        android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
        canvas_transaction.remove(fragMineralProp);
        canvas_transaction.commit();
    }
    void addFragMineralPropFragment(){
        if(!fragMineralProp.isAdded()) {
            android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
            canvas_transaction.add(R.id.fragment_container_top, fragMineralProp);
            canvas_transaction.commit();
        }
    }

    /*******************************************/

    @Override
    public void myAdapterNotifyDataSetChanged_Interface() {
        fragMineralProp.myAdapterNotifyDataSetChanged_Interface();
    }

    @Override
    public void updateAdapter_Interface(MyObject[] myObjs) {
        fragMineralProp.updateAdapter_Interface(myObjs);
    }

    @Override
    public AutoCompleteDatabaseHandler getDatabaseHandler_Interface() {
        return fragMineralProp.getDatabaseHandler_Interface();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextStep_button: {
                //Changing the call to call when the item is clicked
                //fragMineralProp.updateDbFromItemConsumer();


                //launchActivity(this);
                launchActivity();
                //CropImage.startPickImageActivity(this);
                //onSelectImageClick(v);
            }
        }
    }

    @Override
    public void add_LabelObject_FragMineralProp(LabelObject _obj) {

        //todo: NOTE - this is adding a Observer every time this method is called. May cause a error
        ObservableObject.getInstance().addObserver(this);

        AddMineralData.addLabelItem(_obj);

        pagerAdapter.updateTextArrayList(AddMineralData.getLabelItemsArrayList());
    }

    @Override
    public int size_LabelObject_FragMineralProp() {
        return AddMineralData.getLabelItemSize();
    }

    @Override
    public void set_LabelObject_FragMineralProp(int _index, LabelObject _obj) {

        AddMineralData.setLabelItems(_index,_obj);

        pagerAdapter.updateTextArrayList(AddMineralData.getLabelItemsArrayList());
    }

    @Override
    public void remove_LabelObject_FragMineralProp(int _index) {

        if(_index < AddMineralData.getLabelItemSize()) {
            AddMineralData.removeLabelItem(_index);
        }else{
            AddMineralData.clearLabelItemSize();
        }

        pagerAdapter.removeItemAt(AddMineralData.getLabelItemsArrayList(),_index);

    }

    @Override
    public void setBottomControl_FragMineralProp(int state) {
        bottomControl_LinearLayout.setVisibility(state);
    }

    @Override
    public void modifyItem_FragMineralPropResult_interface(int _viewIndex) {
        fragMineralProp.viewTouchIndex(_viewIndex, AddMineralData.getLabelItemsArrayList());
    }

    @Override
    public void update(Observable observable, Object bundle)  {

        Bundle _bundle = (Bundle) bundle;

        //// TODO: 1/23/2017 - we have to check the status of the Queue before deleting the observer
        //observable.deleteObserver(this);

        Log.e("lifeCycle", "AddMineralListActivity: updateCounterAndPercentage");


        int _index = _bundle.getInt( MyKeyConstants.BUNDLE_ITEM_DONE,0);
        String _key = Constant._mineral_Formula.get_key();
        String _formula = _bundle.getString(_key,"");
        long _id = _bundle.getLong( MyKeyConstants.BUNDLE_TABLE_ID,-1);

        if(_index < AddMineralData.getLabelItemSize()) {

            LabelObject _label = new LabelObject(_index);
            _label.setMineralName(AddMineralData.getLabelItems(_index).getMineralName());
            _label.setExtra(AddMineralData.getLabelItems(_index).getExtra());
            _label.setFormula(_formula);
            _label.setDbTableId(_id);
            _label.setQueueDone(true);
            _label.setShowLabel(AddMineralData.getLabelItems(_index).isShowLabel());

            AddMineralData.setLabelItems(_index, _label);

            if (AddMineralData.getLabelItems(_index).isShowLabel()) {
                pagerAdapter.updateTextArrayList(AddMineralData.getLabelItemsArrayList());
            }

            Log.d("CreateDisplayMineralData", "----------------------------");
            Log.d("CreateDisplayMineralData", "_key: "+_key);
            Log.d("CreateDisplayMineralData", "_formula: "+_formula);
            Log.d("CreateDisplayMineralData", "id: "+_id);
            Log.d("CreateDisplayMineralData", "queueEmpty: "+_bundle.getInt("sharedQueueSize",-1));
        }

        if(_bundle.getInt("sharedQueueSize",-1) == 0){
            observable.deleteObserver(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //CALLS MULTIPLE TIMES
    }

    @Override
    public void onPageSelected(int position) {
        pagerAdapter.setPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void launchActivity(){

        Intent myIntent = new Intent(this, ImageCropperFullscreenActivity.class);
        myIntent.putExtra(AddMineralListActivity.class.getName(),true);
        startActivityForResult(myIntent, ImageCropperFullscreenActivity.IMAGE_CROPPER_RESULT_CODE);
        //this.startActivity(myIntent);
        finish();
    }


}