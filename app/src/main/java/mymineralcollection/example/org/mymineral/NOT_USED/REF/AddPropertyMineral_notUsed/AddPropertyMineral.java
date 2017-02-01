package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.Dialog.HelpMenuDialog;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

public class AddPropertyMineral extends Activity implements
        FragMineralNameCards.FragMineralNameCards_interface,
        FragMineralProperties.FragMineralProperties_interface, View.OnClickListener {

    ArrayList<LabelObject> itemList = new ArrayList<>();
    ImageView menu_imageView;

    Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    HelpMenuDialog helpMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property_mineral);

        context = getApplicationContext();

        Methods.checkLoadActivityState(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);



        menu_imageView = (ImageView) findViewById(R.id.menu_imageView);
        menu_imageView.setOnClickListener(this);

       //if(dw != null) {
       //    itemList = dw.getRecycleUriObject();
       //    //Log.d("addProp", "onCreate");
       //    //for (LabelObject _labelObj : itemList) {
       //    //    String mineralName = _labelObj.getMineralName();
       //    //    String extras = _labelObj.getExtra();
       //    //    int index = _labelObj.getIndex();
       //    //    //Log.d("addProp", "- - - - - - - -");
       //    //    //Log.d("addProp", "mineralName: " + mineralName);
       //    //    //Log.d("addProp", "extras: " + extras);
       //    //    //Log.d("addProp", "extras: " + index);
       //    //}
       //}

        itemList = AddMineralData.getLabelItemsArrayList();


        createFragPropertiesFragment();
        createFragImageCardsFragment();
        addFragImageCardsFragment(itemList);
        addFragPropertiesFragment(itemList.get(0));


        //TODO: Remove
        //edit = preferences.edit();
        //edit.remove("AddPropertyMineralHelpDialogOnCreate");
        //edit.apply();

        //startMenuDialogOnCreate();

        String prefName = "AddPropertyMineralHelpDialogOnCreate";
        helpMenu = new HelpMenuDialog(this,prefName,R.layout.mineral_prop_help_dialog_v2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        helpMenu.OnCreateMenuDialog();
    }

    FragMineralNameCards fragMineralCards;

    void createFragImageCardsFragment(){
        fragMineralCards = new FragMineralNameCards();

    }
    void removeFragImageCardsFragment(){
        android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
        canvas_transaction.remove(fragMineralCards);
        canvas_transaction.commit();
    }
    void addFragImageCardsFragment(ArrayList<LabelObject> itemList){
        if(!fragMineralCards.isAdded()) {
            android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
            canvas_transaction.add(R.id.fragment_container_top, fragMineralCards);

            Intent myIntent = new Intent();
            myIntent.putExtra("itemList", new DataWrapper_LabelObject(itemList));
            Bundle b = myIntent.getExtras();
            fragMineralCards.setArguments(b);

            canvas_transaction.commit();

        }
    }

    FragMineralProperties fragProperties;

    void createFragPropertiesFragment(){
        fragProperties = new FragMineralProperties();
    }
    void removeFragPropertiesFragment(){
        android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
        canvas_transaction.remove(fragProperties);
        canvas_transaction.commit();
    }
    void addFragPropertiesFragment(LabelObject itemList){
        if(!fragProperties.isAdded()) {
            android.app.FragmentTransaction canvas_transaction = getFragmentManager().beginTransaction();
            canvas_transaction.add(R.id.fragment_container_bottom, fragProperties);

            Bundle b = new Bundle();
            b.putString("firstItem", itemList.getMineralName());
            fragProperties.setArguments(b);

            canvas_transaction.commit();
        }
    }

    @Override
    public void ItemClicked_FragMineralNameCards_interface(int position) {
        fragProperties = null;
        createFragPropertiesFragment();
        if(itemList.size() > position) {
            addFragPropertiesFragment(itemList.get(position));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_imageView: {
                helpMenu.OnClickMenuDialog();
            }
        }

    }
}
