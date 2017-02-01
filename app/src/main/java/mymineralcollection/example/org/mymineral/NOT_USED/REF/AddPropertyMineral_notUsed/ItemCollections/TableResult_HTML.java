package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.ResultItems;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.TableItems;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;

/**
 * Created by Santiago on 10/2/2016.
 */
public class TableResult_HTML extends View implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    //------------------------------------------------
    private Context context;

    private TableHTMLResult_interface mInterface;

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

    View view;
    //------------------------------------------------

    public TableResult_HTML(Fragment fragCarSettings, LinearLayout _itemParentLayout, Bundle _bundle) {
        super(fragCarSettings.getActivity());
        this.context = fragCarSettings.getActivity();

        View _view = fragCarSettings.getView();
        this.view = _view;
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

        this.mInterface = (TableHTMLResult_interface) fragCarSettings;

        parentLayout = _itemParentLayout;

        childLayout = (LinearLayout) inflater.inflate(R.layout.table_html_result_item, null, false);

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
            TableItems item = new TableItems(text);

            item.setSelected(false);
            item.setPropertyMod(false);
            item.setLocalBd(inLocalDb);

            this.tableItemsArray.add(item);
        }

        tableRows = this.tableItemsArray.size();

        _local.close();

        BuildTable();

        this.itemDataBundle.putInt("TableRows",tableRows);
        this.itemDataBundle.putStringArrayList("TextArray",textArray);
    }

    private ArrayList<TableItems> tableItemsArray = new ArrayList<>();

    //private String[] textArray;
    TableLayout table_layout;


//http://www.tutorialsbuzz.com/2014/01/android-loading-sqlite-data-tablelayout.html
    private void BuildTable() {

        int counter = 0;
        // outer for loop
        this.table_layout.removeAllViews();
        for (int i = 0; i < this.tableRows; i++) {
            TableRow row;
            row = new TableRow(this.context);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            row.setId(i);

            // inner for loop
            for (int j = 0; j < this.tableCols; j++) {

                if (this.tableItemsArray.size() > counter) {
                    TableItems item = this.tableItemsArray.get(counter);

                    TextView tv;
                    tv = new TextView(this.context);

                    if(enableOnClick) tv.setOnClickListener(this);

                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.LEFT);

                    if (Build.VERSION.SDK_INT < 23) {
                        tv.setTextAppearance(context, android.R.style.TextAppearance_Medium);
                    } else {
                        tv.setTextAppearance(android.R.style.TextAppearance_Medium);
                    }

                    int id = counter + this.buttonGroupIdOffset;
                    tv.setId(id);

                    if (item.isPropertyMod() || item.isLocalBd()) {

                        String temp = Constant._large_circle_solid + item.getText();
                        tv.setText(temp);

                    }else {

                        String temp =Constant._large_circle + " " + item.getText();
                        tv.setText(temp);

                    }


                    row.addView(tv);

                    counter++;
                } else {
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
                mInterface.editResultMineralPropItem_interface(index,this.propertyIndex,this.tableItemsArray);
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

                    TextView _textView =
                            (TextView)v.findViewById(counterId);

                    TableItems item = this.tableItemsArray.get(counter);

                    item.setSelected(!item.isSelected());
                    this.tableItemsArray.set(counter,item);

                    if(item.isSelected()){
                        String temp =Constant._large_triangle + " " + item.getText();
                        _textView.setText(temp);
                        //Methods.setBackground(item.SELECTED_COLOR,_textView.getBackground());
                    }else{
                        if (item.isLocalBd() || item.isPropertyMod()) {
                            String temp =Constant._large_circle_solid + " " + item.getText();
                            _textView.setText(temp);
                            //Methods.setBackground(item.LOCAL_DB_COLOR, tv.getBackground());
                        } else {
                            String temp =Constant._large_circle + " " + item.getText();
                            _textView.setText(temp);
                            //Methods.setBackground(item.UNSELECTED_COLOR, tv.getBackground());
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

    public void updateTable(ArrayList<ResultItems> result) {


        int counter = 0;
        for (ResultItems _item: result) {

            TableItems item = new TableItems(_item.getText());

            item.setPropertyMod(_item.getPropertyModSelected());

            this.tableItemsArray.set(counter,item);
            counter++;
        }

        BuildTable();
    }

    public interface TableHTMLResult_interface {
        void editResultMineralPropItem_interface(int index, int propertyIndex, ArrayList<TableItems> tableItemsArray);
        void hideExpandedLayout_ResultMineralPropItem_interface(int index);

    }



}