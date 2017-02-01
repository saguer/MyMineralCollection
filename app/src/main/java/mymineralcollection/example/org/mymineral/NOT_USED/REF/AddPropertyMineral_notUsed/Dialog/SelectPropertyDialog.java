package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.Dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.ResultItems;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.TableItems;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/22/2016.
 */
public class SelectPropertyDialog implements View.OnClickListener {
    Context context;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private int layoutRes;

    private Bundle bundle;
    private int buttonGroupIdOffset = 100;
    TableLayout table_layout;
    TextView dialog_title_textView;
    Button save_button;

    private final int save_button_id = 10;
    private final int table_ID = 5;

    private SelectPropertyDialog_interface mInterface;
    ArrayList<TableItems> tableItemsArray;

    public SelectPropertyDialog(Fragment fragCarSettings, int _layoutRes, Bundle _bundle, ArrayList<TableItems> _tableItemsArray) {
        this.context = fragCarSettings.getActivity();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.layoutRes = _layoutRes;
        this.bundle = _bundle;

        this.tableItemsArray = _tableItemsArray;

        this.mInterface = (SelectPropertyDialog_interface) fragCarSettings;
    }
    public SelectPropertyDialog(Fragment fragCarSettings, int _layoutRes, Bundle _bundle) {
        this.context = fragCarSettings.getActivity();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.layoutRes = _layoutRes;
        this.bundle = _bundle;


        this.mInterface = (SelectPropertyDialog_interface) fragCarSettings;
    }
    public void OnCreateDialog(){
        startMenuDialog();
        //multipleCheckbox();
    }
    boolean wrapInScrollView = false;
    MaterialDialog dialog;

    private void startMenuDialog(){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(layoutRes, wrapInScrollView);

        this.dialog = builder.build();

        View view = this.dialog.getCustomView();
        assert view != null;

        this.table_layout = (TableLayout) view.findViewById(R.id.tableLayout1);
        //noinspection ResourceType
        this.table_layout.setId(table_ID);

        this.dialog_title_textView = (TextView) view.findViewById(R.id.dialog_title_textView);
        this.dialog_title_textView.setText(this.bundle.getString("PropertyName"));

        this.save_button = (Button) view.findViewById(R.id.save_button);
        this.save_button.setOnClickListener(this);
        //noinspection ResourceType
        save_button.setId(save_button_id);

        int tableRows = this.bundle.getInt("TableRows");
        int tableCols = this.bundle.getInt("TableCols");
        //ArrayList<String> textArray = this.bundle.getStringArrayList("TextArray");

        int counter = 0;
        // outer for loop
        for (int i = 0; i < tableRows; i++) {
            TableRow row;
            row = new TableRow(this.context);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setId(i);

            // inner for loop
            for (int j = 0; j < tableCols; j++) {
                if (tableItemsArray != null) {
                    if (tableItemsArray.size() > counter) {
                        //org.sufficientlysecure.htmltextview.HtmlTextView tv;
                        CheckBox tv;
                        tv = new CheckBox(this.context);

                        tv.setOnClickListener(this);

                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.LEFT);

                        if (Build.VERSION.SDK_INT < 23) {
                            tv.setTextAppearance(context, android.R.style.TextAppearance_Medium);
                        } else {
                            tv.setTextAppearance(android.R.style.TextAppearance_Medium);
                        }

                        //tv.setPadding(0, 5, 0, 5);
                        int counterId = counter+buttonGroupIdOffset;
                        tv.setId(counterId);

                        String _text = tableItemsArray.get(counter).getText();
                        boolean _propertyMod = tableItemsArray.get(counter).isPropertyMod();
                        boolean _localDb = tableItemsArray.get(counter).isLocalBd();

                        tv.setText(_text);

                        tv.setChecked(_propertyMod || _localDb);

                        Log.d("addProp", "----------------------------");
                        Log.d("addProp", "_text: " +_text);
                        Log.d("addProp", "_localDb: " +_localDb);
                        Log.d("addProp", "_propertyMod: " +_propertyMod);

                        ResultItems _result = new ResultItems(counter,_text,(_propertyMod || _localDb));
                        resultArray.add(_result);

                        row.addView(tv);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            this.table_layout.addView(row);
        }
        this.dialog.show();

    }

    ArrayList<ResultItems> resultArray = new ArrayList<>();

    private String buttonGroupOnClick(View v){

        int _id = v.getId();

        int tableRows = this.bundle.getInt("TableRows");
        int tableCols = this.bundle.getInt("TableCols");
        ArrayList<String> textArray = this.bundle.getStringArrayList("TextArray");

        //get button group id:
        int counter = 0;
        // outer for loop
        for (int i = 0; i < tableRows; i++) {
            // inner for loop
            for (int j = 0; j < tableCols; j++) {
                int counterId = counter+buttonGroupIdOffset;

                CheckBox _checkBox =
                        (CheckBox)v.findViewById(counterId);

                if(_id == counterId){
                    assert textArray != null;
                    String _text = textArray.get(counter);
                    ResultItems _result = new ResultItems(counter,_text,_checkBox.isChecked());

                    resultArray.set(counter,_result);

                    Log.d("addProp", "mineralName: "+_text+ " - " + _checkBox.isChecked());
                    return _text;
                }
                counter++;
            }
        }
        return null;

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case save_button_id: {
                this.dialog.dismiss();
                Log.d("addProp", "save_button_id: "+save_button_id);
                mInterface.resultSelectPropertyDialog_interface(this.bundle, resultArray);
                break;
            }
            default:{
                buttonGroupOnClick(v);
            }

        }
    }


    public interface SelectPropertyDialog_interface {
        void resultSelectPropertyDialog_interface(Bundle bundle, ArrayList<ResultItems> _result);
    }

}
