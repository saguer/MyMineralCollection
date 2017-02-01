package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.PopUpWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Switch;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/9/2016.
 */
@SuppressWarnings("ResourceType")
public class SavePopUpWindow implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    Context context;
    private View anchor;

    private PopupWindow mDropdown = null;
    private LayoutInflater mInflater;

    public Switch save_orig_switch;
    public Switch save_crop_switch;

    public SavePopUpWindow(Context _context, View _anchor) {
        this.context = _context;
        this.anchor = _anchor;
        initiatePopupWindow();
    }

    private PopupWindow initiatePopupWindow() {

        try {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.pop_up_save_cropper, null);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);

            save_crop_switch = (Switch) layout.findViewById(R.id.save_crop_switch);
            save_crop_switch.setOnCheckedChangeListener(this);

            save_orig_switch = (Switch) layout.findViewById(R.id.save_orig_switch);
            save_orig_switch.setOnCheckedChangeListener(this);

            Button save_button = (Button) layout.findViewById(R.id.save_button);
            save_button.setOnClickListener(this);

            Button save_as_button = (Button) layout.findViewById(R.id.save_as_button);
            save_as_button.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }


    public void expandPopUpWindows(){
        mDropdown.showAsDropDown(anchor, 5, 15);
    }

    public void dismiss(){
        mDropdown.dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean _state) {
        mOnEditMenuAction.onSaveAction(compoundButton,_state);
    }


    @Override
    public void onClick(View view) {
        mOnEditMenuAction.onSaveAction(view,false);
    }


    /** Selection Listener
     * Pass selection to MenuBar*/
    private OnSaveMenuAction mOnEditMenuAction;

    public void setOnEventListener(OnSaveMenuAction listener) {
        mOnEditMenuAction = listener;
    }

    public interface OnSaveMenuAction {
        /**
         * @param view
         * @param _state - passed if the action came from a switch or checkbox. if not is false
         */
        void onSaveAction(View view, boolean _state);
    }

}
