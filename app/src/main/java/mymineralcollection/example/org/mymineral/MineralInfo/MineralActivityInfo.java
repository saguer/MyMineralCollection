package mymineralcollection.example.org.mymineral.MineralInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.MyConstant;
import mymineralcollection.example.org.mymineral.MineralList.ExtractJsonNew;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.PersonalDatabaseHandler;
import mymineralcollection.example.org.myviews.MyCustomLayoutManager;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.RecycleDataAdapter;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.CardOptions;


public class MineralActivityInfo extends Activity implements RecycleDataAdapter.mListener {

    private Context context;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private LinearLayout main_LinearLayout;

    final float MILLISECONDS_PER_INCH = 300;

    private ArrayList<RecycleBen> mineralListArrayList;

    private TextView Tittle_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineral_activitynfo);

        context = getApplication();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager =  new MyCustomLayoutManager(context, MILLISECONDS_PER_INCH);
        recyclerView.setLayoutManager(linearLayoutManager);

        //main_LinearLayout
        main_LinearLayout = (LinearLayout) findViewById(R.id.main_LinearLayout);

        mineralListArrayList = new ArrayList<>();


        Tittle_textView = (TextView) findViewById(R.id.Tittle_textView);



        Intent callingIntent = getIntent();

        //if(true) {
        if(callingIntent != null) {

            int tableRowId = callingIntent.getIntExtra("tableId", -1);
            //int tableRowId = 84;

            String title = "Mineral Information";

            Tittle_textView.setText(title);


            Log.e("MineralActivityInfo", "tableRowId: "+tableRowId);

            String userSetInfo = null;
            String labelList = null;
            PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

            Cursor row = personal.getData(tableRowId);

            if(row.moveToFirst()) {
                userSetInfo = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));
                labelList = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList));

                ArrayList<LabelObject> labelListArray = ExtractJsonNew.getMineralLabelArray(labelList);

                for (int index = 0; index < labelListArray.size(); index++) {
                    //for(LabelObject label: labelList){

                    LabelObject label = labelListArray.get(index);

                    long tableId = label.getDbTableId();
                    String mineralName = label.getMineralName();

                    //----

                    CardOptions cardOptions = new CardOptions();
                    cardOptions.CardOptionsForFillEditArray();

                    LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);

                    //--Get the data from the database
                    Bundle bundle = _local.getBundle(tableId);


                    //Methods.printBundle("MineralActivityInfo",bundle);

                    String _key = Constant._mineral_Formula.get_key();
                    String _formula = bundle.getString(_key,"");

                    _formula = Methods.getStringFromJson(_key, _formula, "html");

                    _local.close();

                    RecycleBen bean = new RecycleBen(
                            ViewType.HTML_EXPANDABLE_VIEW,
                            MyConstant.HTML_TEXT_RENDER,
                            tableRowId,
                            null,
                            mineralName,
                            _formula,
                            cardOptions);

                    mineralListArrayList.add(bean);


                    Log.e("MineralActivityInfo", "_formula: "+_formula);
                    Log.e("MineralActivityInfo", "- - - - - - - - - - - - -");
                }

                Log.d("MineralActivityInfo", "labelList: "+labelList);
            }

            row.close();
            personal.close();

            loadArrayList();
        }
        //------------------


        LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);

        _local.getTable_all("MineralActivityInfo");

        _local.close();
    }

    private void loadArrayList(){
        //GetColorArray randomColor = new GetColorArray(context);
        //randomColor.generateColorArray(); //must be second
        //RecycleDataAdapter recycleView = new RecycleDataAdapter(getApplicationContext(), mineralListArrayList, randomColor);

        RecycleDataAdapter recycleView = new RecycleDataAdapter(getApplicationContext(), mineralListArrayList);
        recycleView.setMyOnClickListener(this);
        //recyclerView.addItemDecoration(new DividerItemDecoration(context));
        recyclerView.setAdapter(recycleView);
    }


    @Override
    public void onRecycleItemButtonPress(int position, RecycleBen recycleBen) {

        Toast.makeText(context,position+"\n"+ recycleBen.getDescription() + "\n"+
                "BTN not implemented", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setToolBarColor(int position, RecycleBen bean) {

    }

    @Override
    public void onScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void onCollapseAll(int position) {
        final int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);

            switch (holder.getItemViewType()) {
                case ViewType.HTML_EXPANDABLE_VIEW: {
                    //// TODO: 1/26/2017 - add this the option to disable or enable in a share pref acessible using the menu
                    //((HtmlExpandableViewHolder)holder).expandableLayout.collapse();
                }
            }
        }
    }

    @Override
    public void onEraseAtPos(int position) {

    }
}
