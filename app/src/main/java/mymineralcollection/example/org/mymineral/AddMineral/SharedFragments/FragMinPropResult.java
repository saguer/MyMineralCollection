package mymineralcollection.example.org.mymineral.AddMineral.SharedFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.AutoLabelUISettings;
import com.dpizarro.autolabel.library.Label;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/6/2016.
 */
public class FragMinPropResult extends Fragment implements AutoLabelUI.OnRemoveLabelListener, AutoLabelUI.OnLabelsEmptyListener, AutoLabelUI.OnLabelsCompletedListener, AutoLabelUI.OnLabelClickListener {
    public FragMinPropResult(){

    }
    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private FragMineralPropResult_interface mInterface;

    private AutoLabelUI mAutoLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.result_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (FragMineralPropResult_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        //------------------------
        mAutoLabel = (AutoLabelUI) v.findViewById(R.id.label_view);

        mAutoLabel.setOnRemoveLabelListener(this);
        mAutoLabel.setOnLabelsEmptyListener(this);
        mAutoLabel.setOnLabelsCompletedListener(this);
        mAutoLabel.setOnLabelClickListener(this);
        //------------------------

        Bundle args = getArguments();

        if(args != null){
            try {
                AutoLabelUISettings labelProp = (AutoLabelUISettings) args.get("LabelProp");
                if (labelProp != null) {
                    mAutoLabel.setSettings(labelProp);
                }
            }catch (ClassCastException e){
                //mAutoLabel.setSettings(setDefaultSettings());
            }

            try {
                DataWrapper_LabelObject dw = (DataWrapper_LabelObject) args.get("itemList");
                //TODO: prevent null
                if(dw != null) {
                    ArrayList<LabelObject> list = dw.getLabelObject();
                    updateTextArrayList(list);
                }
            }catch (ClassCastException e){
                //mAutoLabel.setSettings(setDefaultSettings());
            }
        }


        return v;
    }


    private AutoLabelUISettings setDefaultSettings(){
        return new AutoLabelUISettings.Builder()
                .withBackgroundResource(R.drawable.border_dynamic)
                .withIconCross(R.drawable.cross)
                //.withMaxLabels(6)
                //.withShowCross(true)
                //.withLabelsClickables(true)
                .withTextColor(android.R.color.holo_blue_dark)
                .withTextSize(R.dimen.label_title_size)
                //.withLabelPadding(30)
                .build();
    }
//
    //private AutoLabelUISettings setMineralLabelNoCrossSettings(){
    //    return new AutoLabelUISettings.Builder()
    //            .withBackgroundResource(R.color.default_background_label)
    //            //.withIconCross(R.drawable.cross)
    //            //.withMaxLabels(6)
    //            //.withShowCross(false)
    //            //.withLabelsClickables(true)
    //            .withTextColor(android.R.color.holo_red_dark)
    //            .withTextSize(R.dimen.label_title_size)
    //            //.withLabelPadding(30)
    //            .build();
    //}


    public void updateTextArrayList(ArrayList<LabelObject> item) {
        mAutoLabel.clear();
        item_List.clear();

        for (LabelObject _item: item) {

            Log.d("addImage", "getMineralName: "+_item.getMineralName());

            item_List.add(_item);

            if(_item.getExtra()!=null) {
                String _text = _item.getMineralName() + " " + _item.getExtra().toLowerCase();
                mAutoLabel.addLabel(_text);
            }else{
                mAutoLabel.addLabel(_item.getMineralName());
            }
        }

        Log.e("addImage", "---------------------------");
    }


    private ArrayList<LabelObject> item_List = new ArrayList<>();

    @Override
    public void onClickLabel(Label labelClicked) {

        for (int i = 0; i < item_List.size(); i++) {

            LabelObject _labelObj = item_List.get(i);

            String _text = _labelObj.getMineralName();
            if(_labelObj.getExtra()!=null) {
                _text = _labelObj.getMineralName() + " " + _labelObj.getExtra();
            }

            Log.d("addImage", "_text: "+_text);

            assert _text != null;
            if(_text.equalsIgnoreCase(labelClicked.getText())){
                Log.d("addImage", "TRUE");
                break;
            }


        }


    }

    @Override
    public void onRemoveLabel(Label removedLabel, int position) {

        for (int i = 0; i < item_List.size(); i++) {

            LabelObject _labelObj = item_List.get(i);

            String _text = _labelObj.getMineralName();
            if(_labelObj.getExtra()!=null) {
                _text = _labelObj.getMineralName() + " " + _labelObj.getExtra();
            }

            assert _text != null;
            if(_text.equalsIgnoreCase(removedLabel.getText())){
                item_List.remove(i);
                break;
            }
        }

        Log.d("addImage", removedLabel.getText());
        //mInterface.deleteItem_FragMineralPropResult_interface(item_List);
    }

    @Override
    public void onLabelsEmpty() {
        //Calls twice, carefull
        Log.d("addImage", "onLabelsEmpty");
    }

    @Override
    public void onLabelsCompleted() {
        Log.d("myApp", "onLabelsCompleted");
    }

    public ArrayList<LabelObject> getItemList() {
        return item_List;
    }




    public interface FragMineralPropResult_interface {
    }

}
