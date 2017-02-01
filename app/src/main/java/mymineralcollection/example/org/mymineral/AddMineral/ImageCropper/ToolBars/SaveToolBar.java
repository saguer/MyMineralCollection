package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ToolBars;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/15/2016.
 */
public class SaveToolBar extends Fragment implements View.OnClickListener {
    public SaveToolBar() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private SaveToolBar_interface mInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.save_toolbar, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            mInterface = (SaveToolBar_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        LinearLayout add_img_btn =  (LinearLayout) v.findViewById(R.id.add_img_btn);
        add_img_btn.setOnClickListener(this);

        LinearLayout save_btn =  (LinearLayout) v.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);
//
        //LinearLayout flip_horizontal_btn =  (LinearLayout) v.findViewById(R.id.flip_horizontal_btn);
        //flip_horizontal_btn.setOnClickListener(this);
//
        //LinearLayout flip_vertical_btn =  (LinearLayout) v.findViewById(R.id.flip_vertical_btn);
        //flip_vertical_btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_img_btn: {
                mInterface.add_img_interface();
                break;
            }
            case R.id.save_btn: {
                mInterface.save_interface();
                break;
            }
        }
    }

    public interface SaveToolBar_interface {
        void save_interface();
        void add_img_interface();
    }
}