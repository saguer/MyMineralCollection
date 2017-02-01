package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Items;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.ListResult_HTML;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.ResultItems;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects.TableItems;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.PropResult_HTML;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.TableResult;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.TableResult_HTML;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Crawler.CrawlerMethods;
import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.Dialog.SelectPropertyDialog;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.LocalDatabaseHandler;

/**
 * Created by Santiago on 10/16/2016.
 */
public class FragMineralProperties extends Fragment implements
        PropResult_HTML.ResultMineralPropItem_interface,
        ListResult_HTML.ListHTMLResultMineralPropItem_interface,
        TableResult_HTML.TableHTMLResult_interface,
        TableResult.TableResult_interface,
        SelectPropertyDialog.SelectPropertyDialog_interface,
        ViewTreeObserver.OnGlobalLayoutListener {

    public FragMineralProperties() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    ArrayList<Items> item_List = new ArrayList<>();
    LinearLayout id_parent;

    Bundle bundle = new Bundle();

    private FragMineralProperties_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.mineral_properties_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (FragMineralProperties_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        bundle.putBoolean("error",true);

        Bundle args = getArguments();

        if(args != null){
            String mineralName = args.getString("firstItem", null);
            //Todo: search Local DB. if not available yet wait. Maybe check a pref to see if it was done?

            LocalDatabaseHandler _local = new LocalDatabaseHandler(context, Constant.dB_local_history);
            //Log.d("addProp", "- + - + - + - + - + - + - + - + - + - + - + - +");
            //_local.getTable_by("addProp");
            //Log.d("addProp", "- + - + - + - + - + - + - + - + - + - + - + - +");
            //Log.d("addProp", "mineralName: "+mineralName);
            bundle.clear();

            bundle = _local.getBundle(mineralName);

            _local.close();

        }

        Log.d("addProp", "fragProp - onCreateView");

        //id_parent
        id_parent = (LinearLayout) v.findViewById(R.id.id_parent);
        itemParentLayout = (LinearLayout) v.findViewById(R.id.id_CarSettingsMainLayout);

        ViewTreeObserver observer = id_parent.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(this);

        return v;
    }

    LinearLayout itemParentLayout;

    @Override
    public void onGlobalLayout() {

        int _size = Constant.propertyArray.length;
        for (int _index = Constant.propertyArrayStartIndex; _index < _size; _index++) {

            String _text = Constant.propertyArray[_index].getText();
            String _key = Constant.propertyArray[_index].getKey();

            int _itemType = Constant.propertyArray[_index].getItemType();

            boolean _allowMod = Constant.propertyArray[_index].isAllowModify();

            String bundleData = bundle.getString(_key,null);
            if(bundleData != null) {
                /** If in the future we want to filter certain word so that they do not appear
                 * on the list here is a good place to do it
                 * what word? - SA**/

                String _jsonString = bundle.getString(_key);

                Bundle _bundle = new Bundle();

                String temp;

                _bundle.putInt("Index", item_List.size());
                _bundle.putString("PropertyName", _text);
                _bundle.putString("PropertyKey", _key);
                _bundle.putInt("PropertyIndex", _index);
                _bundle.putBoolean("AllowModify", _allowMod);

                Items item = new Items(this, itemParentLayout);

                switch(_itemType){
                    case Items.TABLE_TYPE:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "text");

                        _bundle.putString("PropertyInfo", temp);
                        _bundle.putInt("TableCols", 2);
                        _bundle.putBoolean("EnableOnClick",false);//true to enable it

                        item.setItemType(Items.TABLE_TYPE);
                        item.addTableResult(_bundle);
                        item_List.add(item);
                        break;
                    }
                    case Items.HTML_LIST_TYPE:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "text");

                        _bundle.putString("PropertyInfo", temp);

                        item.setItemType(Items.HTML_LIST_TYPE);
                        item.addListResultHTML(_bundle);
                        item_List.add(item);
                        break;
                    }
                    case Items.HTML_TABLE_TYPE:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "text");

                        _bundle.putString("PropertyInfo", temp);
                        _bundle.putInt("TableCols", 2);
                        _bundle.putBoolean("EnableOnClick",false);//true to enable it

                        item.setItemType(Items.HTML_TABLE_TYPE);
                        item.addTableResultHTML(_bundle);
                        item_List.add(item);
                        break;
                    }
                    case Items.HTML_TABLE_TYPE_SINGLE_COL:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "text");

                        _bundle.putString("PropertyInfo", temp);
                        _bundle.putInt("TableCols", 1);
                        _bundle.putBoolean("EnableOnClick",false);//true to enable it

                        item.setItemType(Items.HTML_TABLE_TYPE_SINGLE_COL);
                        item.addTableResultHTML(_bundle);
                        item_List.add(item);
                        break;
                    }
                    case Items.HTML_TABLE_TYPE_MULTIPLE_COL:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "text");

                        _bundle.putString("PropertyInfo", temp);
                        _bundle.putInt("TableCols", 4);
                        _bundle.putBoolean("EnableOnClick",false);//true to enable it

                        item.setItemType(Items.HTML_TABLE_TYPE_MULTIPLE_COL);
                        item.addTableResultHTML(_bundle);
                        item_List.add(item);
                        break;
                    }
                    case Items.HTML_TYPE:{
                        temp = Methods.getJsonFromDB(_jsonString, _key, "html");
                        temp = CrawlerMethods.getMindatam2Div(temp);

                        if(temp == null){
                            temp = Methods.getJsonFromDB(_jsonString, _key, "text");
                        }

                        _bundle.putString("PropertyInfo", temp);

                        item.setItemType(Items.HTML_TYPE);
                        item.addPropResultHTML(_bundle);
                        item_List.add(item);
                        break;
                    }
                }

            }
        }

        if (Build.VERSION.SDK_INT < 16) {
            id_parent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            id_parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }




    @Override
    public void editResultMineralPropItem_interface(int index, int propertyIndex) {
        //Toast.makeText(context, "Debug - editImageView: " + index, Toast.LENGTH_SHORT).show();
        dialogPromptAddImage(index,propertyIndex);
    }

    @Override
    public void editResultMineralPropItem_interface(int index, int propertyIndex, ArrayList<TableItems> tableItemsArray) {
        //Toast.makeText(context, "Debug - editImageView: " + index, Toast.LENGTH_SHORT).show();

        Items items_test = item_List.get(index);
        String text = "";

        switch(items_test.getItemType()){
            case Items.TABLE_TYPE:{
                TableResult items = items_test.getTableResult();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }
            case Items.HTML_LIST_TYPE:{
                ListResult_HTML items = items_test.getListResultHTML();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }

            /** MUST BE KEPT IN TE SAME ORDER **/
            case Items.HTML_TABLE_TYPE_SINGLE_COL:{
                //HTML_TABLE_TYPE
            }
            case Items.HTML_TABLE_TYPE_MULTIPLE_COL:{
                //HTML_TABLE_TYPE
            }
            case Items.HTML_TABLE_TYPE:{
                TableResult_HTML items = items_test.getTableResultHTML();
                //text = items.getPropertyName()+": \n"+items.getPropertyInfo();

                Bundle _bundle = items.getItemDataBundle();
                _bundle.putInt("PropertyIndex",propertyIndex);
                _bundle.putInt("ItemIndex",index);

                SelectPropertyDialog propDialog = new SelectPropertyDialog(this,R.layout.mineral_select_prop,_bundle,tableItemsArray);
                propDialog.OnCreateDialog();

                break;
            }
            /** ** ** ** ** ** ** ** ** ** ** **/

            case Items.HTML_TYPE:{
                PropResult_HTML items = items_test.getPropResultHTML();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }
        }

    }

    private void dialogPromptAddImage(int index, int propertyIndex){
        Items items_test = item_List.get(index);
        String text = "";

        switch(items_test.getItemType()){
            case Items.TABLE_TYPE:{
                TableResult items = items_test.getTableResult();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }
            case Items.HTML_LIST_TYPE:{
                ListResult_HTML items = items_test.getListResultHTML();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }

            /** MUST BE KEPT IN TE SAME ORDER **/
            case Items.HTML_TABLE_TYPE_SINGLE_COL:{
                //HTML_TABLE_TYPE
            }
            case Items.HTML_TABLE_TYPE_MULTIPLE_COL:{
                //HTML_TABLE_TYPE
            }
            case Items.HTML_TABLE_TYPE:{
                TableResult_HTML items = items_test.getTableResultHTML();
                //text = items.getPropertyName()+": \n"+items.getPropertyInfo();

                Bundle _bundle = items.getItemDataBundle();
                _bundle.putInt("PropertyIndex",propertyIndex);
                _bundle.putInt("ItemIndex",index);

                SelectPropertyDialog propDialog = new SelectPropertyDialog(this,R.layout.mineral_select_prop,_bundle);
                propDialog.OnCreateDialog();

                break;
            }
            /** ** ** ** ** ** ** ** ** ** ** **/

            case Items.HTML_TYPE:{
                PropResult_HTML items = items_test.getPropResultHTML();
                text = items.getPropertyName()+": \n"+items.getPropertyInfo();
                break;
            }
        }

    }


    @Override
    public void hideExpandedLayout_ResultMineralPropItem_interface(int itemIndex) {
        int _size = item_List.size();
        for (int _index = 0; _index < _size; _index++) {

            int type = item_List.get(_index).getItemType();

            switch(type){
                case Items.TABLE_TYPE:{
                    if (itemIndex != _index) {
                        //Makes sure that the OnClick of the radio button shows it.
                        item_List.get(_index).getTableResult().setShowLayout(false);
                        item_List.get(_index).getTableResult().showHideLayout(false);
                    } else {
                        //Makes sure that the OnClick of the radio button hides it.
                        item_List.get(_index).getTableResult().setShowLayout(true);
                        item_List.get(_index).getTableResult().showHideLayout(true);
                    }
                    break;
                }
                case Items.HTML_LIST_TYPE:{
                    if (itemIndex != _index) {
                        //Makes sure that the OnClick of the radio button shows it.
                        item_List.get(_index).getListResultHTML().setShowLayout(false);
                        item_List.get(_index).getListResultHTML().showHideLayout(false);
                    } else {
                        //Makes sure that the OnClick of the radio button hides it.
                        item_List.get(_index).getListResultHTML().setShowLayout(true);
                        item_List.get(_index).getListResultHTML().showHideLayout(true);
                    }
                    break;
                }

                /** MUST BE KEPT IN TE SAME ORDER **/
                case Items.HTML_TABLE_TYPE_SINGLE_COL:{
                    //HTML_TABLE_TYPE
                }
                case Items.HTML_TABLE_TYPE_MULTIPLE_COL:{
                    //HTML_TABLE_TYPE
                }
                case Items.HTML_TABLE_TYPE:{
                    if (itemIndex != _index) {
                        //Makes sure that the OnClick of the radio button shows it.
                        item_List.get(_index).getTableResultHTML().setShowLayout(false);
                        item_List.get(_index).getTableResultHTML().showHideLayout(false);
                    } else {
                        //Makes sure that the OnClick of the radio button hides it.
                        item_List.get(_index).getTableResultHTML().setShowLayout(true);
                        item_List.get(_index).getTableResultHTML().showHideLayout(true);
                    }
                    break;
                }
                /** ** ** ** ** ** ** ** ** ** ** **/

                case Items.HTML_TYPE:{
                    if (itemIndex != _index) {
                        //Makes sure that the OnClick of the radio button shows it.
                        item_List.get(_index).getPropResultHTML().setShowLayout(false);
                        item_List.get(_index).getPropResultHTML().showHideLayout(false);
                    } else {
                        //Makes sure that the OnClick of the radio button hides it.
                        item_List.get(_index).getPropResultHTML().setShowLayout(true);
                        item_List.get(_index).getPropResultHTML().showHideLayout(true);
                    }
                    break;
                }
            }
        }

    }

    @Override
    public void resultSelectPropertyDialog_interface(Bundle bundle, ArrayList<ResultItems> result) {
        Items items_test = item_List.get(bundle.getInt("ItemIndex"));


        //Log.d("addProp", "getItemType: " +items_test.getItemType());

        ArrayList<ResultItems> resultArray = new ArrayList<>();

        int counter = 0;
        for (ResultItems _item: result) {

            ResultItems _result = new ResultItems(counter,_item.getText(),_item.getPropertyModSelected());
            resultArray.add(_result);
            counter++;
        }
        items_test.getTableResultHTML().updateTable(resultArray);

    }

    public interface FragMineralProperties_interface {

    }
}
