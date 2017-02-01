package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Adapters.MyAdapter;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/25/2016.
 */
public class FragMineralPropResultHtmlText extends Fragment implements
        MyAdapter.MyAdapterInterface{

    public FragMineralPropResultHtmlText(){
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private FragMineralPropResultHtmlText_interface mInterface;

    private HtmlTextView result_webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.result_html_text_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (FragMineralPropResultHtmlText_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        result_webView = (HtmlTextView) v.findViewById(R.id.result_webView);

        Bundle args = getArguments();

        if(args != null){
            try {
                DataWrapper_LabelObject dw = (DataWrapper_LabelObject) args.get("itemList");

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

    public void updateTextArrayList(ArrayList<LabelObject> item) {
        result_webView.setHtml(Methods.detectVar(item));
    }





    @Override
    public void itemOnClickListener(View view) {
        Log.e("AddMineralListActivity", "view: "+view.getId());
    }


    public interface FragMineralPropResultHtmlText_interface {
    }

}
