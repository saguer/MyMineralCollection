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

import mymineralcollection.example.org.mymineral.Class.BitmapMethods;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/14/2016.
 */
public class OrientationToolBar extends Fragment implements View.OnClickListener {
    public OrientationToolBar() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private OrientationToolBar_interface mInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.cropper_orientation_toolbar, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (OrientationToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        LinearLayout right_btn =  (LinearLayout) v.findViewById(R.id.right_btn);
        right_btn.setOnClickListener(this);

        LinearLayout left_btn =  (LinearLayout) v.findViewById(R.id.left_btn);
        left_btn.setOnClickListener(this);

        LinearLayout flip_horizontal_btn =  (LinearLayout) v.findViewById(R.id.flip_horizontal_btn);
        flip_horizontal_btn.setOnClickListener(this);

        LinearLayout flip_vertical_btn =  (LinearLayout) v.findViewById(R.id.flip_vertical_btn);
        flip_vertical_btn.setOnClickListener(this);

        ImageView cancel_imageView =  (ImageView) v.findViewById(R.id.cancel_imageView);
        cancel_imageView.setOnClickListener(this);

        ImageView accept_imageView =  (ImageView) v.findViewById(R.id.accept_imageView);
        accept_imageView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_btn: {
                mInterface.rotate_interface(BitmapMethods.ROTATE_LEFT);
                break;
            }
            case R.id.right_btn: {
                mInterface.rotate_interface(BitmapMethods.ROTATE_RIGHT);
                break;
            }
            case R.id.flip_horizontal_btn: {
                mInterface.flip_interface(BitmapMethods.HORIZONTAL);
                break;
            }
            case R.id.flip_vertical_btn: {
                mInterface.flip_interface(BitmapMethods.VERTICAL);
                break;
            }
            case R.id.cancel_imageView: {
                mInterface.cancelOrientation_interfaces();
                break;
            }
            case R.id.accept_imageView: {
                mInterface.acceptOrientation_interfaces();
                break;
            }
        }
    }

    public interface OrientationToolBar_interface {
        void rotate_interface(int _degree);
        void flip_interface(int _dir);

        void cancelOrientation_interfaces();
        void acceptOrientation_interfaces();
    }
}