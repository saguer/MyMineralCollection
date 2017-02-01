package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/22/2016.
 */
public class MainToolBarFrag extends Fragment implements View.OnClickListener {
    public MainToolBarFrag() {
    }

    private Context context;

    LinearLayout mineral_picture_layout;
    LinearLayout mineral_info_layout;
    LinearLayout mineral_name_layout;

    private ToolBar_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.main_toolbar, container, false);
        context = getActivity();

        try {
            mInterface = (ToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        mineral_picture_layout =  (LinearLayout) v.findViewById(R.id.mineral_picture_layout);
        mineral_picture_layout.setOnClickListener(this);
        mineral_info_layout =  (LinearLayout) v.findViewById(R.id.mineral_info_layout);
        mineral_info_layout.setOnClickListener(this);
        mineral_name_layout =  (LinearLayout) v.findViewById(R.id.mineral_name_layout);
        mineral_name_layout.setOnClickListener(this);

        setTabColor(R.id.mineral_name_layout);

        return v;
    }


    public void setTabColor(int _id){
        switch (_id){
            case R.id.mineral_picture_layout:{
                mineral_picture_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.dk_black_overlay));
                mineral_info_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                mineral_name_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                break;
            }
            case R.id.mineral_info_layout:{
                mineral_picture_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                mineral_info_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.dk_black_overlay));
                mineral_name_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                break;
            }
            case R.id.mineral_name_layout:{
                mineral_picture_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                mineral_info_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));
                mineral_name_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.dk_black_overlay));
                break;
            }
        }
    }



    @Override
    public void onClick(View view) {
        setTabColor(view.getId());
        switch (view.getId()) {
            case R.id.mineral_picture_layout: {
                mInterface.launchMineralPicture_interface();
                break;
            }
            case R.id.mineral_info_layout:{
                mInterface.launchMineralInfo_interface();
                break;
            }
            case R.id.mineral_name_layout:{
                mInterface.launchMineralName_interface();
                break;
            }
        }
    }



    public interface ToolBar_interface {
        void launchMineralPicture_interface();
        void launchMineralInfo_interface();
        void launchMineralName_interface();

    }
}
