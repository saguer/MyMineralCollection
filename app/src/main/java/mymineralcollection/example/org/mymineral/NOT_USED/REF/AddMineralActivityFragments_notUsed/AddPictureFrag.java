package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/22/2016.
 */
public class AddPictureFrag extends Fragment implements View.OnClickListener {
    public AddPictureFrag() {
    }

    private Context context;

    LinearLayout mineral_picture_layout;
    LinearLayout mineral_info_layout;
    LinearLayout mineral_name_layout;

    private ToolBar_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.add_picture, container, false);
        context = getActivity();

        try {
            mInterface = (ToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }


        return v;
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
