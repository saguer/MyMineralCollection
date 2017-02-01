package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Adapters.MyAdapter;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/25/2016.
 */
public class FragMineralPropResultList extends Fragment implements
        MyAdapter.MyAdapterInterface {
    public FragMineralPropResultList(){
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private FragMineralPropResultList_interface mInterface;

    //private AutoLabelUI mAutoLabel;
    public MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.result_recicle_view_list_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (FragMineralPropResultList_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(false);//was true
        ArrayList<LabelObject> mDataset = new ArrayList<>();

        adapter = new MyAdapter(mDataset,this);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        Bundle args = getArguments();

        if(args != null){
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


    public void updateTextArrayList(ArrayList<LabelObject> item) {

        adapter.clearDataSet();
        for (LabelObject _item: item) {
            adapter.addItem(_item);
        }

    }


    @Override
    public void itemOnClickListener(View view) {
        Log.e("AddMineralListActivity", "view: "+view.getId());
        mInterface.modifyItem_FragMineralPropResult_interface(view.getId());
    }


    public interface FragMineralPropResultList_interface {
        void modifyItem_FragMineralPropResult_interface(int _viewIndex);
    }

}
