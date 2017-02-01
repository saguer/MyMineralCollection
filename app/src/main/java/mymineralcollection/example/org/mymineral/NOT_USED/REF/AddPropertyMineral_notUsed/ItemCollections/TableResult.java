package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;

/**
 * Created by Santiago on 10/2/2016.
 */
public class TableResult extends View implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    //------------------------------------------------
    private Context context;

    private TableResult_interface mInterface;

    private TextView mineral_textView;
    private ImageView edit_imageView;
    private ImageView info_imageView;
    private ImageView infoArrow_imageView;

    private RadioButton prop_radioButton;

    private LinearLayout prop_linearLayout;
    private LinearLayout parentLayout;
    private LinearLayout childLayout;

    private HtmlTextView propertyInfo_textView;

    private int index;

    private final int table_ID = 5;
    private final int mineral_textView_ID = 6;
    private final int prop_radioButton_ID = 14;
    private final int edit_imageView_ID = 44;
    private final int infoArrow_imageView_ID = 46;
    private final int info_LinearLayout_ID = 47;
    private final int propertyInfo_textView_ID = 7;

    private String propertyName = "";
    private String propertyInfo = "";
    private String propertyKey = "";
    private int propertyIndex = 0;
    private Bundle itemDataBundle = new Bundle();

    private int tableCols = 0;
    private int tableRows = 0;
    private int buttonGroupIdOffset = 100;
    private Boolean enableOnClick = false;
    private Boolean allowModify = false;

    public int getIndex() {
        return index;
    }
    public void setShowLayout(boolean showLayout) {
        this.showLayout = showLayout;
    }
    public boolean getShowLayout() {
        return showLayout;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public String getPropertyInfo() {
        return propertyInfo;
    }
    public Bundle getItemDataBundle() {
        return itemDataBundle;
    }

    private boolean showLayout = false;
    //------------------------------------------------

    public TableResult(Fragment fragCarSettings, LinearLayout _itemParentLayout, Bundle _bundle) {
        super(fragCarSettings.getActivity());
        this.context = fragCarSettings.getActivity();

        View _view = fragCarSettings.getView();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.index = _bundle.getInt("Index");
        this.propertyName = _bundle.getString("PropertyName");
        this.propertyInfo = _bundle.getString("PropertyInfo");
        this.propertyKey = _bundle.getString("PropertyKey");
        this.propertyIndex = _bundle.getInt("PropertyIndex");
        this.tableCols = _bundle.getInt("TableCols");
        this.enableOnClick = _bundle.getBoolean("EnableOnClick");
        this.allowModify = _bundle.getBoolean("AllowModify");

        this.itemDataBundle.putAll(_bundle);

        this.mInterface = (TableResult_interface) fragCarSettings;

        parentLayout = _itemParentLayout;

        childLayout = (LinearLayout) inflater.inflate(R.layout.table_result_item, null, false);

        parentLayout.addView(childLayout, index);
        childLayout.setId(index);

        ViewTreeObserver observer = childLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(this);

        init(_view);
    }

    private void init(View view){
        assert view != null;

        this.mineral_textView = (TextView) view.findViewById(R.id.mineral_textView);
        //noinspection ResourceType
        this.mineral_textView.setId(mineral_textView_ID);
        this.mineral_textView.setText(propertyName);
        this.mineral_textView.setOnClickListener(this);

        this.prop_radioButton = (RadioButton) view.findViewById(R.id.prop_radioButton);
        //noinspection ResourceType
        this.prop_radioButton.setId(prop_radioButton_ID);
        this.prop_radioButton.setOnClickListener(this);

        this.prop_linearLayout = (LinearLayout) view.findViewById(R.id.prop_linearLayout);
        this.prop_linearLayout.setId(index);
        this.prop_linearLayout.setVisibility(View.GONE);

        this.edit_imageView = (ImageView) view.findViewById(R.id.edit_imageView);
        //noinspection ResourceType
        this.edit_imageView.setId(edit_imageView_ID);
        this.edit_imageView.setOnClickListener(this);
        this.edit_imageView.setVisibility(View.INVISIBLE);


        this.table_layout = (TableLayout) view.findViewById(R.id.tableLayout1);
        //noinspection ResourceType
        this.table_layout.setId(table_ID);

        ArrayList<String> textArray = FormatText.formatData(propertyName, propertyInfo);

        //--In here we can set the button states.
        //--When the db has text and a mineral is already there change the color
        //--to show that the mineral is already owned
        LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);

        for(String text: textArray){

            boolean inLocalDb = _local.checkIfExists(Constant._mineral_Name.get_key(),text);
            tableItems item = new tableItems(text);

            item.setSelected(false);
            item.setLocalBd(inLocalDb);

            this.tableItemsArray.add(item);

        }
        tableRows = this.tableItemsArray.size();
        _local.close();

        BuildTable();

        this.itemDataBundle.putInt("TableRows",tableRows);
        this.itemDataBundle.putStringArrayList("TextArray",textArray);
    }

    private ArrayList<tableItems> tableItemsArray = new ArrayList<>();

    //private String[] textArray;
    TableLayout table_layout;


//http://www.tutorialsbuzz.com/2014/01/android-loading-sqlite-data-tablelayout.html
    private void BuildTable() {

        int counter = 0;
        // outer for loop
        outer:
        for (int i = 0; i < this.tableRows; i++) {
            TableRow row;
            row = new TableRow(this.context);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            row.setId(i);
            // inner for loop
            inner:
            for (int j = 0; j < this.tableCols; j++) {

                if(this.tableItemsArray.size() > counter)
                {
                    tableItems item = this.tableItemsArray.get(counter);

                    Button tv;
                    tv = new Button(this.context);

                    if(enableOnClick) tv.setOnClickListener(this);

                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                    tv.setBackgroundResource(R.drawable.border_not_rounded_dynamic);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(0, 5, 0, 5);
                    int id = counter+this.buttonGroupIdOffset;
                    tv.setId(id);

                    tv.setText(item.getText());

                    if(item.isLocalBd()) {
                        Methods.setBackground(item.LOCAL_DB_COLOR, tv.getBackground());
                    }else{
                        Methods.setBackground(item.UNSELECTED_COLOR, tv.getBackground());
                    }

                    row.addView(tv);

                    counter++;
                }else{
                    break;
                }
            }
            this.table_layout.addView(row);

        }
    }




    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case prop_radioButton_ID: {
                this.showLayout = !this.showLayout;
                showHideLayout(showLayout);

                break;
            }
            case edit_imageView_ID: {
                mInterface.editResultMineralPropItem_interface(index,this.propertyIndex);
                break;
            }
            case mineral_textView_ID:{
                mInterface.hideExpandedLayout_ResultMineralPropItem_interface(index);


                break;
            }
            default:{

                if(enableOnClick) buttonGroupOnClick(v);

            }
        }

    }




    private String buttonGroupOnClick(View v){

        int _id = v.getId();

        //get button group id:
        int counter = 0;
        // outer for loop

        for (int i = 0; i < this.tableRows; i++) {
            // inner for loop

            for (int j = 0; j < this.tableCols; j++) {
                int counterId = counter+buttonGroupIdOffset;

                if(_id == counterId){

                    Button _button = (Button)v.findViewById(counterId);

                    tableItems item = this.tableItemsArray.get(counter);

                    item.setSelected(!item.isSelected());
                    this.tableItemsArray.set(counter,item);

                    if(item.isSelected()){
                        Methods.setBackground(item.SELECTED_COLOR,_button.getBackground());
                    }else{
                        if(item.isLocalBd()){
                            Methods.setBackground(item.LOCAL_DB_COLOR, _button.getBackground());
                        }else {
                            Methods.setBackground(item.UNSELECTED_COLOR, _button.getBackground());
                        }
                    }


                    return item.getText();
                }
                counter++;
            }
        }
        return null;

    }

    public void showHideLayout(boolean _state){
        if(_state) {
            this.prop_linearLayout.setVisibility(View.VISIBLE);
            if(this.allowModify) this.edit_imageView.setVisibility(View.VISIBLE);
        }else{
            this.prop_linearLayout.setVisibility(View.GONE);
            if(this.allowModify) this.edit_imageView.setVisibility(View.INVISIBLE);
        }
        this.prop_radioButton.setChecked(_state);
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT < 16) {
            childLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            childLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }


    //private void showHideSetParaLayout(){
    //    showHideLayout(showLayout);
    //}

    public interface TableResult_interface {
        //AutoCompleteDatabaseHandler getDatabaseHandler_interface();
        void editResultMineralPropItem_interface(int index, int propertyIndex);
        void hideExpandedLayout_ResultMineralPropItem_interface(int index);

        //void updateText_RemoveItem_interface(int index);
    }

    public class tableItems {
        String text;
        boolean selected;
        boolean localBd;

        int UNSELECTED_COLOR = ContextCompat.getColor(context, R.color.Indigo);
        int SELECTED_COLOR = ContextCompat.getColor(context, R.color.lt_Indigo);

        int LOCAL_DB_COLOR = ContextCompat.getColor(context, R.color.mineralInCollection);


        public tableItems(String _data) {
            this.text = _data;
        }

        public boolean isLocalBd() {
            return localBd;
        }

        public void setLocalBd(boolean localBd) {
            this.localBd = localBd;
        }


        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

}