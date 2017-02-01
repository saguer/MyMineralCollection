package mymineralcollection.example.org.mymineral.MineralList;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralSpecificInfo.Dialog.AddPropDialog;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralSpecificInfo.JSON;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ImageCropperFullscreenActivity;
import mymineralcollection.example.org.mymineral.AddMineral.ObservableObject;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyConstant;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;
import mymineralcollection.example.org.mymineral.Class.UserData;
import mymineralcollection.example.org.mymineral.Crawler.AddUpdateDb;
import mymineralcollection.example.org.mymineral.MineralInfo.MineralActivityInfo;
import mymineralcollection.example.org.mymineral.MineralList.Dialog.DeleteItemFromPersonalDb;
import mymineralcollection.example.org.mymineral.MineralList.EspecialClasses.CreateDisplayMineralData;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.Receiver.ReceiverAddImage;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.PersonalDatabaseHandler;
import mymineralcollection.example.org.mymineral.StartActivity.StartActivity;
import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.CardOptions;

public class MyListActivity extends AppCompatActivity implements RecyclerFragment.RecyclerFragment_interface,
        CollapsingFragment.CollapsingFragment_interface, AddPropDialog.mOnClickListener, Observer, DeleteItemFromPersonalDb.mListener {

    /** Variables that are saved and recovered from the static class **/
    private String mineralID;
    private String labelList;
    private String pathList;

    private Context context;

    private RecyclerFragment fragRecycler;
    private CollapsingFragment fragCollapsingToolbar;

    public static final int DISPLAY_MINERAL_ID = 121;
    public static final int EDIT_MINERAL_ID = 221;
    public static final int ADD_MINERAL_ID = 321;

    private int current_fragment;

    private Intent callingIntent;

    private ArrayList<UserData> userEnteredData = new ArrayList<>();

    private AddPropDialog addPropDialog;

    private int FRAG_ID = 0;

    CreateDisplayMineralData displayMineralData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.e("lifeCycle", "ListActivity: onCreate");
        context = getApplicationContext();

        addPropDialog = new AddPropDialog(MyListActivity.this);
        addPropDialog.setDialogClickListener(this);

        callingIntent = getIntent();

        if(callingIntent != null) {
            createFragment();


            boolean displayMineral = callingIntent.getBooleanExtra(MineralListActivity.class.toString(),false);
            if(displayMineral){
                FRAG_ID = DISPLAY_MINERAL_ID;
            }

            boolean cropperMineral = callingIntent.getBooleanExtra(ImageCropperFullscreenActivity.class.toString(),false);
            if(cropperMineral){
                FRAG_ID = ADD_MINERAL_ID;
                initFromImageCropper();
            }

            addRecyclerFragment(FRAG_ID);
            //toggleCropFrag();
            addCollapsingFragment(callingIntent,FRAG_ID);

        }


        displayMineralData = new CreateDisplayMineralData(context,callingIntent);
    }

    void createFragment(){
        fragRecycler = new RecyclerFragment();
        fragCollapsingToolbar = new CollapsingFragment();
    }

    void addCollapsingFragment(Intent myIntent, int fragId){
        if(!fragCollapsingToolbar.isAdded()) {

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(myIntent != null) {
                Bundle b = myIntent.getExtras();
                b.putInt("fragId", fragId);
                fragCollapsingToolbar.setArguments(b);
            }

            fragmentTransaction.add(R.id.appBarLayout, fragCollapsingToolbar);
            fragmentTransaction.commit();
        }
    }

    private void addRecyclerFragment(int fragId) {
        fragRecycler.setFragId(fragId);
        toggleFragSlideUpDown(R.id.nestedScrollView,"RECYCLER",fragRecycler);
    }

    private void toggleFragSlideUpDown(int _fragmentLayout, String _tag, Fragment _frag){
        Fragment f = getFragmentManager().findFragmentByTag(_tag);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    //.setCustomAnimations(R.animator.slide_up,
                    //        R.animator.slide_down,
                    //        R.animator.slide_up,
                    //        R.animator.slide_down)
                    .add(_fragmentLayout, _frag, _tag).addToBackStack(null)
                    .addToBackStack(null).commit();
        }
    }

    /**
     * Preserves the ColorObject so that if the backspace is pressed and the color was changed
     * without pressing Save it reverts to the last colorObject
     */
    private ColorObject lastColor;
    private void saveTheLastColor(int _position){
        Object colorObj = userEnteredData.get(_position).getPropertyDataObj();
        if(colorObj!=null) {
            String colorJson = colorObj.toString();
            lastColor = ExtractJsonNew.getColorJson(colorJson);
        }
    }

    private void setColor(int _position, RecycleBen recycleBen){
        int colorIndex = recycleBen.getColorIndex();
        int color = recycleBen.getColorNumber();
        String colorName = recycleBen.getColorName();

        saveTheLastColor(_position);

        try {
            JSONObject jSonObj =  new JSONObject();
            jSonObj.put("COLOR_INDEX", colorIndex);
            jSonObj.put("COLOR_INT", color);
            jSonObj.put("COLOR_NAME", colorName);

            JSONObject colorJson =  new JSONObject();

            colorJson.put("COLOR",jSonObj);

            Log.e("MineralListActivity", "colorJson: "+colorJson.toString());
            setData(_position, colorJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setData(int position, Object data){

        String jSonKey = userEnteredData.get(position).getJsonKey();
        String propertyName = userEnteredData.get(position).getPropertyName();
        int dialogType = userEnteredData.get(position).getDialogType();
        int viewType = userEnteredData.get(position).getViewType();

        userEnteredData.set(position,new UserData(jSonKey,propertyName, data,dialogType,viewType));
    }
    /** -------------------------------------- **/
    /**
     * Collapsing fragment data methods
     * **/
    public Intent setMineralUriDataAndColor(Context context, Intent intent){

        int tableRowId = intent.getIntExtra("tableId", -1);
        String mineralImgUri = null;
        String userSetInfo = null;

        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        Cursor row = personal.getData(tableRowId);

        if(row.moveToFirst()) {
            mineralImgUri = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_pathList));
            userSetInfo = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));
        }
        row.close();
        personal.close();

        Intent intent1 = getColorForCollapsingToolbar(tableRowId, userSetInfo);

        if(intent1 == null) intent1 = new Intent();

        intent1.putStringArrayListExtra("mineralImgUri",ExtractJsonNew.fillMineralUriArray(mineralImgUri));
        String description = intent.getStringExtra("Description");
        intent1.putExtra("Description",description);

        intent1.putExtra("tableId",tableRowId);

        return intent1;
    }

    /**
     * Used to pass the color to the Recycler view to init the colorSlider.
     */
    private RecycleBen colorBean;

    private Intent getColorForCollapsingToolbar(int tableId, String _userSetInfo){
        Intent intent = new Intent();

        ExtractJsonNew.DataObj dataObj = ExtractJsonNew.getUserInfo(tableId,_userSetInfo,null);

        if (dataObj != null) {
            for(UserData data: dataObj.getResultArray()){
                String propertyDataObj = Methods.getJsonDataString((String)data.getPropertyDataObj(), MyKeyConstants.PROPERTY_DATA);

                if(propertyDataObj != null) {
                    RecycleBen recycleBen = ExtractJsonNew.getDataBenForDisplay(data.getJsonKey(), propertyDataObj, data.getPropertyName(), data.getDialogType(), tableId, null);
                    if(recycleBen.getViewType() == ViewType.COLOR_SLIDER_VIEW){

                        intent.putExtra("Color_Int",recycleBen.getColorNumber());
                        intent.putExtra("Color_Index",recycleBen.getColorIndex());
                        intent.putExtra("Color_Name",recycleBen.getColorName());

                        colorBean = recycleBen;

                        return intent;
                    }
                }
            }
        }
        return null;
    }

    private void initFromImageCropper(){
        Log.e("lifeCycle", "ListActivity: initFromImageCropper");
        //this has to be commented because we are starting here, uncomment!
        Methods.checkLoadActivityState(this);

        mineralID = AddMineralData.getLettersForName();
        //timeStamp = Methods.getTimesStamp();
        labelList = JSON.labelListJson(AddMineralData.getLabelItemsArrayList());
        pathList = JSON.getImgPathJson(AddMineralData.getUriPathArrayList());


        userEnteredData = AddMineralData.getUserEnteredData();
        //AddMineralData.setRandomColor_old(context);
        AddMineralData.setRandomColor(context);

        if(userEnteredData.size() == 0) {
            Log.d("MyListActivity", "userEnteredData.size() == 0");
            Collections.addAll(userEnteredData, MyConstant.UserDataArray);

            Log.e("MyListActivity", "-------------------------------");
            for(UserData test: userEnteredData){
                Log.d("MyListActivity", "initFromImageCropper" + test.getPropertyDataObj());
            }
        }
        else{
            /**todo:
             * the view that changes the color is a mess. find a way to get the color INIT.
             * The random color gen only on the ADD_MINERAL.
             * Add a way to change the color when responding to the back btn.
             */

            Log.e("MyListActivity", "-------------------------------");
            for(UserData test: userEnteredData){
                Log.d("MyListActivity", "initFromImageCropper: " + test.getPropertyDataObj());
            }

            //---
            //int colorIndex = recycleBen.getColorIndex();
            //int color = recycleBen.getColorNumber();
            //String colorName = recycleBen.getColorName();
//
            //try {
            //    JSONObject jSonObj =  new JSONObject();
            //    jSonObj.put("COLOR_INDEX", colorIndex);
            //    jSonObj.put("COLOR_INT", color);
            //    jSonObj.put("COLOR_NAME", colorName);
//
            //    JSONObject colorJson =  new JSONObject();
//
            //    colorJson.put("COLOR",jSonObj);
//
            //    Log.e("MineralListActivity", "colorJson: "+colorJson.toString());
            //    setData(MyConstant.COLOR_INDEX, colorJson);
//
            //} catch (JSONException e) {
            //    e.printStackTrace();
            //}

            //restore color
            String colorJson = userEnteredData.get(MyConstant.COLOR_INDEX).getPropertyDataObj().toString();
            ColorObject colorObj = ExtractJsonNew.getColorJson(colorJson);

            Log.d("MyListActivity", "userEnteredData.get(5).getPropertyDataObj()");
            Log.e("MyListActivity", colorJson);

            //AddMineralData.setColor_old(context,colorObj);
            AddMineralData.setColorObj(colorObj);

        }

        //Log.e("lifeCycle", "ViewImageActivity: initFromImageCropper: "+ObservableObject.getInstance().countObservers());

        if(ObservableObject.getInstance().countObservers() == 0) {
            Log.d("Observer", "ViewImageActivity: ADD OBSERVER: ");

            LocalBroadcastManager.getInstance(context).registerReceiver(_myReceiverMergeDbIntoNewDb, new IntentFilter(Constant.intentAddImageActivity));
            ObservableObject.getInstance().addObserver(this);
        }else{
            Log.e("Observer", "ViewImageActivity: NOT OBSERVER: ");
        }


    }

    /**
     *  Sets the adapter data, does not modify the array list containing the actual data
     * @param expandItemPos - item position to keep expanded, -1 to keep all collapsed
     */
    private ArrayList<RecycleBen> setAddDataMineralData(int tableId, int expandItemPos, ArrayList<UserData> _userEnteredData) {

        Log.e("BeanFix", "setAddDataMineralData");

        ArrayList<RecycleBen> arrayList = new ArrayList<>();

        CardOptions cardOptions = new CardOptions();
        cardOptions.CardOptionsForAddDataMineralData(expandItemPos);

        for (UserData objectData : _userEnteredData) {

            RecycleBen bean = ExtractJsonNew.getDataBenForEdit(
                    objectData.getJsonKey(),
                    objectData.getPropertyDataObj(),
                    objectData.getPropertyName(),
                    objectData.getDialogType(),
                    tableId,
                    cardOptions);

            if(objectData.getViewType() == ViewType.COLOR_SLIDER_VIEW){

                ColorObject mColorObj = AddMineralData.getColorObj();

                bean.setColorIndex(mColorObj.getColorNumberIndex());
                bean.setColorNumber(mColorObj.getColorNumber());
                bean.setColorName(mColorObj.getColorName());
            }

            arrayList.add(bean);
        }

        return arrayList;
    }
    /** -------------------------------------- **/


    private ArrayList<RecycleBen> fillEditArray(Context context, Intent intent){

        Log.e("BeanFix", "fillEditArray");

        userEnteredData = new ArrayList<>();

        int tableRowId = intent.getIntExtra("tableId", -1);
        String userSetInfo = null;

        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        Cursor row = personal.getData(tableRowId);

        if(row.moveToFirst()) {
            userSetInfo = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));
        }
        row.close();
        personal.close();

        ArrayList<RecycleBen> arrayList = new ArrayList<>();

        //CardOptions cardOptions = new CardOptions();

        //cardOptions.setTittleTextColor(R.color.text_color);
        //cardOptions.setExpandableTextColor(R.color.text_color);
        //cardOptions.setExpandableTextAreaColor(-1);
        //cardOptions.setTittleTextAreaColor(-1);
        //cardOptions.setSeparationLineColor(R.color.dk_black_overlay);
        //cardOptions.setExpandEnable(true);
        //cardOptions.setAnimationSpeed(300);

        CardOptions cardOptions = new CardOptions();
        cardOptions.CardOptionsForFillEditArray();


        ExtractJsonNew.DataObj dataObj = ExtractJsonNew.getUserInfo(tableRowId, userSetInfo,cardOptions);

        if (dataObj != null) {
            for(UserData data: dataObj.getResultArray()){
                //Log.e("MineralListActivity", "-> getJsonKey: "+data.getJsonKey());
                //Log.e("MineralListActivity", "data: "+data.getPropertyName());
                //Log.e("MineralListActivity", "data: "+data.getPropertyDataObj());
                String propertyDataObj = Methods.getJsonDataString((String)data.getPropertyDataObj(), MyKeyConstants.PROPERTY_DATA);
                Log.e("MineralListActivity", "----------------------------");

                if(propertyDataObj != null) {

                    RecycleBen recycleBen = ExtractJsonNew.getDataBenForEdit(data.getJsonKey(),
                            propertyDataObj, data.getPropertyName(), data.getDialogType(), tableRowId, cardOptions);

                    if(recycleBen.getViewType() == ViewType.COLOR_SLIDER_VIEW){
                        arrayList.add(recycleBen);
                        //Log.e("BeanFix", "----------------------------");
                        //Log.e("BeanFix", "recycleBen: "+recycleBen.getColorName());
                        //recycleBen.printBean("BeanFix");
                    } else{
                        recycleBen.setViewType(ViewType.EXPANDABLE_VIEW);
                        arrayList.add(recycleBen);
                    }

                    //recycleBen.printBean("BeanFix");

                }else{
                    RecycleBen recycleBen = ExtractJsonNew.getDataBenForEdit(
                            data.getJsonKey(),
                            null,
                            data.getPropertyName(),
                            data.getDialogType(),
                            tableRowId,
                            cardOptions);

                    arrayList.add(recycleBen);
                }

                userEnteredData.add(new UserData(data.getJsonKey(),
                        data.getPropertyName(),
                        propertyDataObj,
                        data.getDialogType(),
                        data.getViewType()));
            }
        }

        return arrayList;
    }
    /** -------------------------------------- **/

    @Override
    public ArrayList<RecycleBen> setRecycler_interface(int fragId) {

        switch (fragId) {
            case DISPLAY_MINERAL_ID: {
                Log.e("MineralListActivity", "setRecycler_interface: DISPLAY_MINERAL_ID");
                return displayMineralData.setDataToDisplayMineralData();
            }
            case ADD_MINERAL_ID: {
                Log.e("MineralListActivity", "setRecycler_interface: ADD_MINERAL_ID");
                int tableRowId = callingIntent.getIntExtra("tableId", -1);
                return setAddDataMineralData(tableRowId,-1,userEnteredData);
            }
        }
        return null;

    }

    @Override
    public Intent setCollapsingFragmentData_interface(int fragId) {

        switch (fragId) {
            case DISPLAY_MINERAL_ID: {
                Intent intent = setMineralUriDataAndColor(context,callingIntent);

                fragCollapsingToolbar.showEdit(false);
                fragCollapsingToolbar.showMenu(true);
                return intent;
            }
            case ADD_MINERAL_ID:
                Intent intent = new Intent();

                intent.putStringArrayListExtra("mineralImgUri", ExtractJsonNew.fillMineralUriArray(pathList));

                //String labelList = allRows.getString(allRows.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList));
                String mineralDescription = ExtractJsonNew.getMineralDescription(labelList);

                intent.putExtra("Description", mineralDescription);

                fragCollapsingToolbar.showEdit(false);
                fragCollapsingToolbar.showMenu(false);

                return intent;
        }
        return null;
    }

    @Override
    public void replaceFragment_interface(int fragId) {

       switch (fragId) {
           /**
            * Called when the Edit btn is pressed.
            * It fills the recyclerView with the data to be edited
            */
           case DISPLAY_MINERAL_ID: {
               //addRecyclerFragment(EDIT_MINERAL_ID);
               /**
                * The calling fragment has DISPLAY_MINERAL, set the next fragment
                */
               fragRecycler.setFragId(EDIT_MINERAL_ID);

               Log.e("Color", "replaceFragment_interface - DISPLAY_MINERAL_ID");

               fragRecycler.setRecyclerView(fillEditArray(context,callingIntent));//replaceFragment_interface

               fragCollapsingToolbar.showEdit(true);
               fragCollapsingToolbar.showMenu(false);
               break;
           }
           /**
            * This case will never get called
            */
           //case ADD_MINERAL_ID: {
           //    //addRecyclerFragment(EDIT_MINERAL_ID);
           //    /**
           //     * The calling fragment has DISPLAY_MINERAL, set the next fragment
           //     */
           //    fragRecycler.setFragId(ADD_MINERAL_ID);
           //
           //    fragRecycler.setRecyclerView(fillEditArray(context,callingIntent),AddMineralData.getColor());//replaceFragment_interface
           //
           //    fragCollapsingToolbar.showEdit(true);
           //    fragCollapsingToolbar.showMenu(false);
           //    break;
           //}
       }
    }

    @Override
    public void dialogDeleteItemId(String description, final int id) {

        DeleteItemFromPersonalDb deleteDialog = new DeleteItemFromPersonalDb(this);
        deleteDialog.setMyDialogOnClickListener(this);
        deleteDialog.dialogEraseFromDbAtId(description,id);

    }

    @Override
    public void fragOnButtonPress(int _position, RecycleBen recycleBen) {

        Log.e("MyListActivity", "-------------------------------");
        for(UserData test: userEnteredData){
            Log.d("MyListActivity", "fragOnButtonPress: " + test.getPropertyDataObj());
        }

        switch (recycleBen.getDialogType()){
            case MyConstant.MINERAL_INFO:{
                //Toast.makeText(context,"MINERAL_INFO: "+ MyConstant.MINERAL_INFO, Toast.LENGTH_LONG).show();

                recycleBen.printBean("CreateDisplayMineralData");

                Intent myIntent = new Intent(this, MineralActivityInfo.class);

                Log.d("MineralListActivity", "recycleBen.getDescription(): "+recycleBen.getDescription());

                myIntent.putExtra("tableId", recycleBen.getTableId());
                myIntent.putExtra("Description", recycleBen.getDescription());

                myIntent.putExtra(MyListActivity.class.toString(),true);

                MyListActivity.this.startActivity(myIntent);

                break;
            }
            default:{
                fragOnButtonPress_PartOne(_position, recycleBen);
            }
        }

    }

    private void fragOnButtonPress_PartOne(int _position, RecycleBen recycleBen){
        /**
         * In here we need to use the position because we are filling the array
         */
        switch (userEnteredData.get(_position).getDialogType()) {
            case MyConstant.CUSTOM_TEXT_INPUT: {
                //addPropDialog.setBean(recycleBen);
                addPropDialog.startDialogTextInput(userEnteredData, _position, recycleBen);
                break;
            }
            case MyConstant.DATE_PICKER: {
                addPropDialog.startDialogDatePicker(userEnteredData, _position, recycleBen);
                break;
            }
            case MyConstant.PRICE_INPUT: {
                addPropDialog.startDialogPriceInput(userEnteredData, _position, recycleBen);
                break;
            }
            case MyConstant.SAVE: {
                //prepareDataToSave();

                switch (current_fragment) {
                    case EDIT_MINERAL_ID: {
                        prepareDataToUpdate(recycleBen);
                        break;
                    }
                    case ADD_MINERAL_ID: {
                        Bundle _bundle = new Bundle();
                        _bundle.putBoolean("check_queue", true);
                        Methods.sendMessage(getApplicationContext(), Constant.intentItemConsumer, _bundle);
                        break;
                    }
                }

                break;
            }
            case MyConstant.COLOR_INPUT: {
                Log.e("MineralListActivity", "MyConstant.COLOR_INPUT");
                setColor(_position, recycleBen);
                if (fragCollapsingToolbar != null)
                    fragCollapsingToolbar.dynamicToolbarColor(recycleBen.getColorNumber());
                break;
            }
        }
    }
    @Override
    public void updateCurrentFragmentVar(int currFrag) {
        current_fragment = currFrag;
    }

    @Override
    public void fragColorUpdateData(int position, RecycleBen recycleBen) {
        //Log.d("MyListActivity", "fragColorUpdateData");

        for(UserData test: userEnteredData){
            Log.d("MyListActivity", "--> : " + test.getPropertyDataObj());
        }

        setColor(position, recycleBen);//ss

        if(fragCollapsingToolbar != null) fragCollapsingToolbar.dynamicToolbarColor(recycleBen.getColorNumber());
    }

    private void prepareDataToUpdate(RecycleBen recycleBen){

        /** UPDATE TO DATABASE **/

        int tableRowId = recycleBen.getTableId();
        Log.e("MineralListActivity", " recycleBen: "+tableRowId);

        Log.e("MineralListActivity", " UPDATE TO DATABASE ");
        Log.e("MineralListActivity", " UPDATE TO DATABASE ");
        String userSetInfo = JSON.getUserEnteredDataJson(userEnteredData);
        Log.e("MineralListActivity", "userSetInfo : " + userSetInfo);

        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        Cursor row = personal.getData(tableRowId);

        if(row.moveToFirst()) {

            ContentValues values = new ContentValues();
            values.put(PersonalDatabaseHandler.fieldObject_Name, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_Name)));
            values.put(PersonalDatabaseHandler.fieldObject_TimeStamp, row.getInt(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_TimeStamp)));
            values.put(PersonalDatabaseHandler.fieldObject_labelList, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList)));
            values.put(PersonalDatabaseHandler.fieldObject_pathList, row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_pathList)));
            values.put(PersonalDatabaseHandler.fieldObject_userSetInfo, userSetInfo);

            personal.update(tableRowId,values);
        }
        row.close();
        personal.close();

        setEditMineralFrag();
    }

    /**
     *  Sets or Updates the adapter data, does not modify the array list containing the actual data
     * @param expandItemPos - item position to keep expanded, -1 to keep all collapsed
     * @param data
     */
    private ArrayList<RecycleBen> updateAdapterData(int expandItemPos, ArrayList<UserData> _userEnteredData, RecycleBen data) {

        ArrayList<RecycleBen> arrayList = new ArrayList<>();

        CardOptions cardOptions = data.getCardOptions();
        cardOptions.setSkipCollapseAtPosition(expandItemPos);

        for (UserData objectData : _userEnteredData) {


            RecycleBen bean = ExtractJsonNew.getDataBenForEdit(
                    objectData.getJsonKey(),
                    objectData.getPropertyDataObj(),
                    objectData.getPropertyName(),
                    objectData.getDialogType(),
                    data.getTableId(),
                    cardOptions);

            //bean.printBean("BeanFix");//

            arrayList.add(bean);
        }


        return arrayList;
    }

    @Override
    public void onClickSave(int position, String data) {
    }

    @Override
    public void onClickSave(int position, RecycleBen data) {
        Log.e("Color", "data:: "+data.getDataObj());

        setData(position, data.getDataObj());

        if(fragRecycler.isVisible()){
            fragRecycler.updateRecyclerView(updateAdapterData(position,userEnteredData,data));
        }
    }

    @Override
    public void onClickCancel(int position) {

    }

    private void setEditMineralFrag(){
        //addRecyclerFragment(EDIT_MINERAL_ID);
        /**
         * The calling fragment has DISPLAY_MINERAL, set the next fragment
         */
        //addRecyclerFragment(DISPLAY_MINERAL_ID);
        fragRecycler.setFragId(DISPLAY_MINERAL_ID);

        Log.e("Color", "setEditMineralFrag");
        /**
         * Called when the Save btn is pressed.
         * Fills the recycleView with the data taken from the user database.
         */
        fragRecycler.setRecyclerView(displayMineralData.setDataToDisplayMineralData());//setEditMineralFrag

        fragCollapsingToolbar.showEdit(false);
        fragCollapsingToolbar.showMenu(true);
    }

    @Override
    public void onBackPressed() {

        //Toast.makeText(context,"onBackPressed: "+ current_fragment, Toast.LENGTH_LONG).show();

        switch (current_fragment) {
            case EDIT_MINERAL_ID: {


                Log.e("BeanFix", "colorNumber: "+lastColor.getColorName());

                if(fragCollapsingToolbar != null) fragCollapsingToolbar.dynamicToolbarColor(lastColor.getColorNumber());

                setEditMineralFrag();
                //Toast.makeText(context,"onBackPressed: "+ current_fragment, Toast.LENGTH_LONG).show();
                //
                break;
            }
            case DISPLAY_MINERAL_ID:{
                Intent myIntent = new Intent(this, MineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                //finish();
                //super.onBackPressed();
                break;
            }
            case ADD_MINERAL_ID:{

                //Log.e("Observer", "ADD_MINERAL_ID");

                Log.e("MyListActivity", "ADD_MINERAL_ID");

                //if(backPressCounter == 1){
                //    Toast.makeText(context,"BACK NOT IMPLEMENTED \n"+
                //            "Press: "+backPressCounter +"\n"+
                //            "To Go Back to the First Page", Toast.LENGTH_LONG).show();
                //}else if(backPressCounter == 0) {
                //    Toast.makeText(context,"BACK NOT IMPLEMENTED", Toast.LENGTH_LONG).show();
                //}else if(backPressCounter == 2){
                //    Intent myIntent = new Intent(this, StartActivityFullscreen.class);
                //    startActivity(myIntent);
                //    finish();
                //}
                //backPressCounter++;

                AddMineralData.setUserEnteredData(userEnteredData);

                Intent myIntent = new Intent(this, ImageCropperFullscreenActivity.class);
                startActivity(myIntent);
                break;
            }
        }
    }


    /** OBSERVER UPDATE METHOD **/
    private final ReceiverAddImage _myReceiverMergeDbIntoNewDb = new ReceiverAddImage();
    private MaterialDialog dialog;
    private boolean showDialog = true;

    private MaterialDialog.Builder showIndeterminateProgressDialog(boolean horizontal) {
        return new MaterialDialog.Builder(this)
                .title(R.string.please_wait_txt)
                //.content(R.string.loading_data_txt)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MaterialDialog.Builder builder  = showIndeterminateProgressDialog(true);
        dialog = builder.build();
        Log.e("lifeCycle", "ListActivity: onResume");
    }

    @Override
    public void update(Observable observable, Object bundle)  {

        Log.e("Observer", "ViewImageActivity: updateCounterAndPercentage");
        Bundle _bundle = (Bundle) bundle;
        Log.e("Observer", "----------------------------------------------------");
        Log.e("Observer", "LAUNCH_ACTIVITY: "+_bundle.getInt("LAUNCH_ACTIVITY",0));
        Log.e("Observer", "HIDE_DIALOG    : "+_bundle.getInt("HIDE_DIALOG",0));
        Log.e("Observer", "SHOW_DIALOG    : "+_bundle.getInt("SHOW_DIALOG",0));

        if(ObservableObject.LAUNCH_ACTIVITY == _bundle.getInt("LAUNCH_ACTIVITY",0)){

            observable.deleteObserver(this);

            for(LabelObject obj: AddMineralData.getLabelItemsArrayList()){
                AddUpdateDb.setAutoCompletePersonalFlag(obj,true,context);
            }

            //if(fragCollapsingToolbar != null)
            prepareDataToSave();

        }else if((ObservableObject.SHOW_DIALOG == _bundle.getInt("SHOW_DIALOG",0)) && showDialog){

            if(dialog != null) {
                dialog.show();
            }

            showDialog = false;

        }else if(ObservableObject.HIDE_DIALOG == _bundle.getInt("HIDE_DIALOG",0)){
            //Log.e("ItemConsumer", "HIDE_DIALOG: "+data);

            if(dialog != null) {
                dialog.dismiss();
            }
        }

    }

    private void prepareDataToSave(){

        Log.e("MineralListActivity", "mineralID   : " + mineralID);
        //Log.e("MineralListActivity", "timeStamp   : " + timeStamp);
        Log.e("MineralListActivity", "labelList   : " + labelList);
        Log.e("MineralListActivity", "pathList    : " + pathList);
        String userSetInfo = JSON.getUserEnteredDataJson(userEnteredData);
        Log.e("MineralListActivity", "userSetInfo : " + userSetInfo);


        Log.e("CreateDisplayMineralData", "labelList   : " + labelList);
        //// TODO: 1/23/2017 - before saving to the dB make sure that the table ID is not -1 in labelList


        /** SAVE TO DATABASE **/
        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);
        personal.create(mineralID, Methods.getTimeStamp(), labelList, pathList, userSetInfo);
        personal.getTable("PropInfo");
        personal.close();

        launchActivity();

        if(dialog != null) {
            dialog.dismiss();
        }

    }
    public void launchActivity(){
        ///Intent myIntent = new Intent(packageContext, AddPropertyMineral.class);

        Intent myIntent = new Intent(this, StartActivity.class);
        //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //if(LABEL_ITEMS!=null) myIntent.putExtra("itemList", new DataWrapper_LabelObject(LABEL_ITEMS));
        startActivity(myIntent);
        finish();
    }

    @Override
    public void dialogPositivePositionOnClick(int position) {
    }

    @Override
    public void dialogPositiveOnClick() {
        Intent myIntent = new Intent(this, MineralListActivity.class);
        MyListActivity.this.startActivity(myIntent);
    }
}
