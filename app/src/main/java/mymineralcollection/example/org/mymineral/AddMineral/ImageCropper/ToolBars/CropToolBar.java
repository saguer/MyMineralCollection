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
public class CropToolBar extends Fragment implements View.OnClickListener {
    public CropToolBar() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private CropToolBar_interface mInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.cropper_crop_toolbar, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (CropToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }



        LinearLayout crop_custom_btn =  (LinearLayout) v.findViewById(R.id.crop_custom_btn);
        crop_custom_btn.setOnClickListener(this);

        LinearLayout crop_1_1_btn =  (LinearLayout) v.findViewById(R.id.crop_1_1_btn);
        crop_1_1_btn.setOnClickListener(this);

        LinearLayout crop_16_9_btn =  (LinearLayout) v.findViewById(R.id.crop_16_9_btn);
        crop_16_9_btn.setOnClickListener(this);

        LinearLayout crop_4_3_btn =  (LinearLayout) v.findViewById(R.id.crop_4_3_btn);
        crop_4_3_btn.setOnClickListener(this);

        LinearLayout crop_3_2_btn =  (LinearLayout) v.findViewById(R.id.crop_3_2_btn);
        crop_3_2_btn.setOnClickListener(this);

        LinearLayout crop_2_3_btn =  (LinearLayout) v.findViewById(R.id.crop_2_3_btn);
        crop_2_3_btn.setOnClickListener(this);

        ImageView cancel_imageView =  (ImageView) v.findViewById(R.id.cancel_imageView);
        cancel_imageView.setOnClickListener(this);

        ImageView accept_imageView =  (ImageView) v.findViewById(R.id.accept_imageView);
        accept_imageView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crop_custom_btn: {
                mInterface.setCropRatio_interface(false,0,0);
                break;
            }
            case R.id.crop_1_1_btn: {
                mInterface.setCropRatio_interface(true,1,1);
                break;
            }
            case R.id.crop_16_9_btn: {
                mInterface.setCropRatio_interface(true,19,9);
                break;
            }
            case R.id.crop_4_3_btn: {
                mInterface.setCropRatio_interface(true,4,3);
                break;
            }
            case R.id.crop_3_2_btn: {
                mInterface.setCropRatio_interface(true,3,2);
                break;
            }
            case R.id.crop_2_3_btn: {
                mInterface.setCropRatio_interface(true,2,3);
                break;
            }
            case R.id.cancel_imageView: {
                mInterface.cancelCrop_interfaces();
                break;
            }
            case R.id.accept_imageView: {
                mInterface.acceptCrop_interfaces();
                break;
            }

        }
    }


    public interface CropToolBar_interface {
        void setCropRatio_interface(boolean _enableRatio, int _xRatio, int _yRatio);
        void acceptCrop_interfaces();
        void cancelCrop_interfaces();

    }
}