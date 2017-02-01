package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ToolBars;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/14/2016.
 */
public class ToolBar extends Fragment implements View.OnClickListener {
    public ToolBar() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    public LinearLayout repeat_btn;

    private ToolBar_interface mInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.cropper_main_toolbar, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (ToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        //

        LinearLayout orientation_btn =  (LinearLayout) v.findViewById(R.id.orientation_btn);
        orientation_btn.setOnClickListener(this);

        LinearLayout crop_btn =  (LinearLayout) v.findViewById(R.id.crop_btn);
        crop_btn.setOnClickListener(this);

        LinearLayout save_btn =  (LinearLayout) v.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        repeat_btn =  (LinearLayout) v.findViewById(R.id.repeat_btn);
        repeat_btn.setOnClickListener(this);
        repeat_btn.setVisibility(View.INVISIBLE);

        //repeat_btn


        ImageView cancel_imageView =  (ImageView) v.findViewById(R.id.cancel_imageView);
        cancel_imageView.setOnClickListener(this);
        cancel_imageView.setVisibility(View.INVISIBLE);

        ImageView accept_imageView =  (ImageView) v.findViewById(R.id.accept_imageView);
        accept_imageView.setOnClickListener(this);
        accept_imageView.setVisibility(View.INVISIBLE);

        //

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crop_btn: {
                mInterface.launchCropFragment_interface();
                break;
            }
            case R.id.orientation_btn:{
                mInterface.launchOrientationFragment_interface();
                break;
            }
            case R.id.save_btn:{
                mInterface.save_interface();
                break;
            }
            case R.id.repeat_btn:{
                mInterface.reloadOrig_interface();
                break;
            }
        }
    }



    public interface ToolBar_interface {
        void launchCropFragment_interface();
        void launchOrientationFragment_interface();
        void save_interface();
        void reloadOrig_interface();
    }
}
