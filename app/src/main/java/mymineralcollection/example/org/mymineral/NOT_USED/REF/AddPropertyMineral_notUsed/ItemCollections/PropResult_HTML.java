package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import mymineralcollection.example.org.mymineral.R;


/**
 * Created by Santiago on 10/2/2016.
 */
public class PropResult_HTML extends View implements View.OnClickListener{

    //------------------------------------------------
    private Context context;

    private ResultMineralPropItem_interface mInterface;

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

    public PropResult_HTML(Fragment fragCarSettings, LinearLayout _itemParentLayout, Bundle _bundle) {
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

        this.mInterface = (ResultMineralPropItem_interface) fragCarSettings;

        parentLayout = _itemParentLayout;

        childLayout = (LinearLayout) inflater.inflate(R.layout.prop_result_item, null, false);
        parentLayout.addView(childLayout, index);
        childLayout.setId(index);

        init(_view);


    }

    private void init(View view){
        assert view != null;

        this.mineral_textView = (TextView) view.findViewById(R.id.mineral_textView);
        //noinspection ResourceType
        this.mineral_textView.setId(mineral_textView_ID);
        this.mineral_textView.setText(propertyName);
        this.mineral_textView.setOnClickListener(this);


        this.propertyInfo_textView = (HtmlTextView) view.findViewById(R.id.propertyInfo_textView);
        //noinspection ResourceType
        this.propertyInfo_textView.setId(propertyInfo_textView_ID);
        //this.propertyInfo_textView.setText(propertyInfo);
        this.propertyInfo_textView.setOnClickListener(this);

       this.propertyInfo_textView.setHtml(propertyInfo);


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

        //edit_imageView
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
                //showHideSetParaLayout();
                mInterface.hideExpandedLayout_ResultMineralPropItem_interface(index);
                break;
            }
        }

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


    //private void showHideSetParaLayout(){
    //    showHideLayout(showLayout);
    //}

    public interface ResultMineralPropItem_interface {
        //AutoCompleteDatabaseHandler getDatabaseHandler_interface();
        void editResultMineralPropItem_interface(int index, int propertyIndex);
        void hideExpandedLayout_ResultMineralPropItem_interface(int index);

        //void updateText_RemoveItem_interface(int index);
    }

}