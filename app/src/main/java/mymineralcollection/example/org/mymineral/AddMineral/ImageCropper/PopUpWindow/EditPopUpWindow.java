package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.PopUpWindow;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/9/2016.
 */
@SuppressWarnings("ResourceType")
public class EditPopUpWindow implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    Context context;
    private View anchor;

    private PopupWindow mDropdown = null;
    private LayoutInflater mInflater;


    public EditPopUpWindow(Context _context, View _anchor) {
        this.context = _context;
        this.anchor = _anchor;
        initiatePopupWindow();
    }

    TextView guidelines_off_textView;
    TextView guidelines_on_textView;
    TextView guidelines_on_touch_textView;
    Switch keep_prev_switch;
    LinearLayout edit_linearLayout;

    private PopupWindow initiatePopupWindow() {

        try {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.pop_up_edit_image_cropper, null);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);

            Button ccw_button = (Button) layout.findViewById(R.id.ccw_button);
            ccw_button.setOnClickListener(this);
            Button cw_button = (Button) layout.findViewById(R.id.cw_button);
            cw_button.setOnClickListener(this);
            Button choose_button = (Button) layout.findViewById(R.id.choose_button);
            choose_button.setOnClickListener(this);

            Switch fixedAspectRatio_switch = (Switch) layout.findViewById(R.id.fixedAspectRatio_switch);
            fixedAspectRatio_switch.setOnCheckedChangeListener(this);

            keep_prev_switch = (Switch) layout.findViewById(R.id.keep_prev_switch);
            keep_prev_switch.setOnCheckedChangeListener(this);


            guidelines_off_textView = (TextView) layout.findViewById(R.id.guidelines_off_textView);
            guidelines_off_textView.setOnClickListener(this);
            guidelines_on_textView = (TextView) layout.findViewById(R.id.guidelines_on_textView);
            guidelines_on_textView.setOnClickListener(this);
            guidelines_on_touch_textView = (TextView) layout.findViewById(R.id.guidelines_on_touch_textView);
            guidelines_on_touch_textView.setOnClickListener(this);

            setGuidelines(CropImageView.Guidelines.OFF);

            edit_linearLayout = (LinearLayout) layout.findViewById(R.id.edit_linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }

    public void setEditLayoutVisible(int _visibility){
        edit_linearLayout.setVisibility(_visibility);
    }

    public void expandPopUpWindows(){
        mDropdown.showAsDropDown(anchor, 5, 25);
    }

    public void dismiss(){
        mDropdown.dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean _state) {
        mOnEditMenuAction.onEditAction(compoundButton,_state);
    }


    @Override
    public void onClick(View view) {
        mOnEditMenuAction.onEditAction(view,false);
    }


    public void setGuidelines(CropImageView.Guidelines guidelines){
        //cropImageView.setGuidelines(guidelines);
        if(CropImageView.Guidelines.OFF.equals(guidelines)){
            //Log.d("addImage",">>> " + CropImageView.Guidelines.OFF);
            guidelines_off_textView.setTextColor(ContextCompat.getColor(context, R.color.royal_blue));
            guidelines_on_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
            guidelines_on_touch_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        }else if(CropImageView.Guidelines.ON.equals(guidelines)){
            //Log.d("addImage",">>> " + CropImageView.Guidelines.ON);
            guidelines_off_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
            guidelines_on_textView.setTextColor(ContextCompat.getColor(context, R.color.royal_blue));
            guidelines_on_touch_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        }else if(CropImageView.Guidelines.ON_TOUCH.equals(guidelines)){
            //Log.d("addImage",">>> " + CropImageView.Guidelines.ON_TOUCH);
            guidelines_off_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
            guidelines_on_textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
            guidelines_on_touch_textView.setTextColor(ContextCompat.getColor(context, R.color.royal_blue));
        }
    }


    /** Selection Listener
     * Pass selection to MenuBar*/
    private OnEditMenuAction mOnEditMenuAction;

    public void setOnEventListener(OnEditMenuAction listener) {
        mOnEditMenuAction = listener;
    }

    public interface OnEditMenuAction {
        /**
         * @param view
         * @param _state passed if the action came from a switch or checkbox. if not is false
         */
        void onEditAction(View view, boolean _state);
    }


}
