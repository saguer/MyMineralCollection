package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed.MineralName;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/22/2016.
 */
public class MineralNameFrag extends Fragment implements View.OnClickListener {
    public MineralNameFrag() {
    }

    private Context context;

    MineralExtraFrag addMineralInfoFrag;

    private static final String TOOLBAR_FRAGMENT_MINERAL_EXTRA_TAG = "TOOLBAR_FRAGMENT_MINERAL_EXTRA_TAG";

    private ToolBar_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.mineral_name, container, false);
        context = getActivity();

        try {
            mInterface = (ToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

//item_frag
        addMineralInfoFrag = new MineralExtraFrag();

        addFragSlideUpDown(R.id.item_frag, TOOLBAR_FRAGMENT_MINERAL_EXTRA_TAG, addMineralInfoFrag);

        return v;
    }

    private void addFragSlideUpDown(int _fragmentLayout, String _tag, Fragment _frag){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .add(_fragmentLayout, _frag, _tag).addToBackStack(null)
                .addToBackStack(null).commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crop_btn: {
                //mInterface.launchCropFragment_interface();
                break;
            }
            case R.id.orientation_btn:{
                //mInterface.launchOrientationFragment_interface();
                break;
            }
            case R.id.save_btn:{
                //mInterface.save_interface();
                break;
            }
            case R.id.repeat_btn:{
                //mInterface.reloadOrig_interface();
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
