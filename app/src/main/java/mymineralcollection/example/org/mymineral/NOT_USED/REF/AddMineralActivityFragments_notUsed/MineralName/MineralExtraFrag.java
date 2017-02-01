package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed.MineralName;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed.Views.ToggleButtonGroup;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/22/2016.
 */
public class MineralExtraFrag extends Fragment implements View.OnClickListener {
    public MineralExtraFrag() {
    }

    private Context context;

    private String EXTRAS = null;

    ToggleButtonGroup mineralExtra_ToggleButtonGroupTableLayout;

    private ToolBar_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.mineral_extra_name, container, false);
        context = getActivity();

        //try {
        //    mInterface = (ToolBar_interface) context;
        //} catch (ClassCastException e) {
        //    throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        //}

        mineralExtra_ToggleButtonGroupTableLayout = new ToggleButtonGroup(MineralExtraFrag.this,v.findViewById(R.id.mineralExtra_ToggleButtonGroupTableLayout));
        v.findViewById(R.id.matrix_rad).setOnClickListener(this);
        v.findViewById(R.id.var_rad).setOnClickListener(this);
        v.findViewById(R.id.cluster_rad).setOnClickListener(this);
        v.findViewById(R.id.with_rad).setOnClickListener(this);
        v.findViewById(R.id.on_rad).setOnClickListener(this);
        v.findViewById(R.id.and_rad).setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View view) {

        //mineralExtra_ToggleButtonGroupTableLayout.getButtonGroupSelectedText(view);
        //mineralExtra_ToggleButtonGroupTableLayout.setCheckedRadioButtonID(view.getId(), view);

        switch (view.getId()) {
            case R.id.matrix_rad:{}
            case R.id.var_rad:{}
            case R.id.cluster_rad:{}
            case R.id.with_rad:{}
            case R.id.on_rad:{}
            case R.id.and_rad:{
                EXTRAS = mineralExtra_ToggleButtonGroupTableLayout.getButtonGroupSelectedText(view);
                break;
            }

        }
    }



    public interface ToolBar_interface {
        //void launchCropFragment_interface();
        //void launchOrientationFragment_interface();
        //void save_interface();
        //void reloadOrig_interface();
    }
}
