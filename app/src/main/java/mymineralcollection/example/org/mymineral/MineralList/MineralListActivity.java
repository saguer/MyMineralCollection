package mymineralcollection.example.org.mymineral.MineralList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.MineralList.Dialog.DeleteItemFromPersonalDb;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.PersonalDatabaseHandler;
import mymineralcollection.example.org.mymineral.StartActivity.StartActivity;
import mymineralcollection.example.org.myviews.MyCustomLayoutManager;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.RecycleDataAdapter;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.CardOptions;
import mymineralcollection.example.org.myviews.Views.ExpandableViewHolder;

public class MineralListActivity extends Activity implements
        RecycleDataAdapter.mListener, DeleteItemFromPersonalDb.mListener {

    public static int ANIMATION_SPEED = 300;

    private Context context;
    private RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    LinearLayout main_LinearLayout;

    final float MILLISECONDS_PER_INCH = 300;

    ArrayList<RecycleBen> mineralListArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineral_list);

        context = getApplication();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager =  new MyCustomLayoutManager(context, MILLISECONDS_PER_INCH);
        recyclerView.setLayoutManager(linearLayoutManager);

        //main_LinearLayout
        main_LinearLayout = (LinearLayout) findViewById(R.id.main_LinearLayout);

        mineralListArrayList = new ArrayList<>();
        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        //personal.getTable("MineralListActivity");

        SQLiteDatabase db = personal.getWritableDatabase();
        //Log.e(_tag, "--------------------------------------");
        //Log.d("MineralListActivity", "getTable called");
        //String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + PersonalDatabaseHandler.tableName, null);
        if (allRows.moveToFirst() ){
            do {
                String labelList = allRows.getString(allRows.getColumnIndex(PersonalDatabaseHandler.fieldObject_labelList));
                String userSetInfo = allRows.getString(allRows.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));

                int tableId = allRows.getInt(allRows.getColumnIndex(PersonalDatabaseHandler.fieldObjectId));

                String mineralDescription = ExtractJsonNew.getMineralDescription(labelList);

                //ExtractJson.DataObj dataObj = ExtractJson.getMineralUserInfo(userSetInfo);

                ExtractJsonNew.DataObj dataObj = ExtractJsonNew.getUserInfo(tableId,userSetInfo,null);

                assert dataObj != null;
                String mineralInf = dataObj.getResult();

                Log.e("MineralListActivity", tableId + " - userSetInfo: "+userSetInfo);
                Log.e("MineralListActivity", "mineralDescription : "+mineralDescription);
                Log.d("MineralListActivity", "mineralInf         : "+mineralInf);

                //CardOptions cardOptions = new CardOptions();

                //cardOptions.setTittleTextColor(R.color.text_color);
                //cardOptions.setTittleTextAreaColor(R.color.black_overlay);
                //cardOptions.setExpandableTextColor(R.color.text_color);
                //cardOptions.setExpandableTextAreaColor(R.color.dk_black_overlay);
                ////cardOptions.setSeparationLineColor(R.color.dk_black_overlay);
                //cardOptions.setExpandEnable(true);
                //cardOptions.setAnimationSpeed(300);

                CardOptions cardOptions = new CardOptions();
                cardOptions.CardOptionsForMineralListActivity();


                //todo: add dialog type
                int dialogType = -1;
                RecycleBen bean = new RecycleBen(
                        ViewType.EXPANDABLE_VIEW,
                        dialogType,
                        tableId,
                        mineralDescription,
                        mineralInf,
                        cardOptions);

                mineralListArrayList.add(bean);

            } while (allRows.moveToNext());
        }

        //-----------------------

        allRows.close();
        db.close();

        personal.close();

        loadArrayList();

        deleteDialog = new DeleteItemFromPersonalDb(this);
        deleteDialog.setMyDialogOnClickListener(this);

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
    public void onRecycleItemButtonPress(int _position, RecycleBen recycleBen) {

        Intent myIntent = new Intent(this, MyListActivity.class);

        Log.d("MineralListActivity", "recycleBen.getDescription(): "+recycleBen.getDescription());

        myIntent.putExtra("tableId", recycleBen.getTableId());
        myIntent.putExtra("Description", recycleBen.getDescription());
        myIntent.putExtra("dataText", (String)recycleBen.getDataObj());

        myIntent.putExtra(MineralListActivity.class.toString(),true);

        MineralListActivity.this.startActivity(myIntent);
    }

    @Override
    public void setToolBarColor(int position, RecycleBen bean) {
        //empty
    }

    @Override
    public void onScrollToPosition(int _position) {

        //Log.e("MineralListActivity","position: "+_position);
        //linearLayoutManager.notify();
        //linearLayoutManager.scrollToPosition(position);
        recyclerView.smoothScrollToPosition(_position);
    }

    @Override
    public void onCollapseAll(int _position) {

        final int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);

            switch (holder.getItemViewType()) {
                case ViewType.EXPANDABLE_VIEW: {
                    ((ExpandableViewHolder)holder).expandableLayout.collapse();
                }
            }
        }
    }

    DeleteItemFromPersonalDb deleteDialog;

    @Override
    public void onEraseAtPos(final int position) {

        final RecycleBen bean = mineralListArrayList.get(position);
        deleteDialog.dialogEraseFromDbAtId(position, bean);

    }


    @Override
    public void dialogPositivePositionOnClick(int position) {
        mineralListArrayList.remove(position);
        loadArrayList();
    }

    @Override
    public void dialogPositiveOnClick() {
    }


    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(this, StartActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(myIntent);

        //super.onBackPressed();
    }

}
