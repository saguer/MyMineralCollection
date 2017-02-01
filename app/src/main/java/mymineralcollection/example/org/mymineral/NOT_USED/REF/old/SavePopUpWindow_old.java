package mymineralcollection.example.org.mymineral.NOT_USED.REF.old;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/7/2016.
 */
public class SavePopUpWindow_old implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Context context;

    private static final int UI_ANIMATION_DELAY = 300;

    private View anchor;

    private boolean SAVE_ORIG;
    private boolean SAVE_CROP;

    public SavePopUpWindow_old(Context _context, View _anchor, boolean _SAVE_ORIG, boolean _SAVE_CROP) {
        this.context = _context;
        this.anchor = _anchor;
        this.SAVE_ORIG = _SAVE_ORIG;
        this.SAVE_CROP = _SAVE_CROP;
        initiatePopupWindow();
    }


    public void setOnClickListener(MyOnClick listener) {
        myOnClick = listener;
    }


    private MyOnClick myOnClick;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        myOnClick.onCheckedChanged(compoundButton, b);
    }

    public interface MyOnClick {
        void onClick(View view);
        void onCheckedChanged(CompoundButton compoundButton, boolean b);
    }

    private final Handler mHideHandler = new Handler();

    private TextView cropper_guidelines_msg_textView;

    private PopupWindow mDropdown = null;

    private LayoutInflater mInflater;


    private PopupWindow initiatePopupWindow() {

        try {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.pop_up_save_image_cropper_old, null);

            Button save_as_button = (Button) layout.findViewById(R.id.save_as_button);
            save_as_button.setOnClickListener(this);
            save_as_button.setVisibility(View.INVISIBLE);

            Button save_button = (Button) layout.findViewById(R.id.save_button);
            save_button.setOnClickListener(this);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);

            Switch save_crop_switch = (Switch) layout.findViewById(R.id.save_crop_switch);
            save_crop_switch.setChecked(SAVE_CROP);
            save_crop_switch.setOnCheckedChangeListener(this);

            Switch save_orig_switch = (Switch) layout.findViewById(R.id.save_orig_switch);
            save_orig_switch.setChecked(SAVE_ORIG);
            save_orig_switch.setOnCheckedChangeListener(this);



            //TODO: find a way not deprecated
            //Drawable background = context.getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
            //mDropdown.setBackgroundDrawable(background);

            //save_linearLayout
            //main_linearLayout

            //LinearLayout main_linearLayout = (LinearLayout) layout.findViewById(R.id.main_linearLayout);
            //main_linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay));

            //LinearLayout save_linearLayout = (LinearLayout) layout.findViewById(R.id.save_linearLayout);
            //save_linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dk_black_overlay));

            //mDropdown.showAsDropDown(edit_button, 5, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }



    public void dismiss(){
        mDropdown.dismiss();
    }

    public void expandPopUpWindows(){
        mDropdown.showAsDropDown(anchor, 5, 5);
        //int _selected = ContextCompat.getColor(context, R.color.dk_Indigo);
        //Methods.setBackground(_selected,edit_button.getBackground());
    }

    public void show(){
        expandPopUpWindows();
    }
    public void delayShow(){
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    public void delayHide(){
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }



    @Override
    public void onClick(View view) {
        myOnClick.onClick(view);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of popUp window
            expandPopUpWindows();

        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed hide of popUp window
            //dismiss();
        }

    };

}
