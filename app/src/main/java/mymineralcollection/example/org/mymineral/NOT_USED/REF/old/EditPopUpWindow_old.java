package mymineralcollection.example.org.mymineral.NOT_USED.REF.old;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/7/2016.
 */
public class EditPopUpWindow_old implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Context context;

    private static final int UI_ANIMATION_DELAY = 300;

    private int crop_button_id;
    private int crop_undo_button_id;
    private int choose_img_button_id;
    private View anchor;
    private Switch menu_switch;

    public EditPopUpWindow_old(Context _context, View _anchor, int _crop_button_id, int _crop_undo_button_id, int _choose_img_button_id, Switch _menu_switch) {
        this.context = _context;
        this.crop_undo_button_id = _crop_undo_button_id;
        this.crop_button_id = _crop_button_id;
        this.choose_img_button_id = _choose_img_button_id;
        this.anchor = _anchor;

        //todo: get view from actionbar init
        this.menu_switch = _menu_switch;
        this.menu_switch.setOnCheckedChangeListener(this);

        initiatePopupWindow();
    }


    public void setOnClickListener(MyOnClick listener) {
        myOnClick = listener;
    }


    private MyOnClick myOnClick;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean _state) {

        myOnClick.onCheckedChanged(compoundButton, _state);

        switch (compoundButton.getId()) {
            case R.id.menu_switch: {



                break;
            }
        }


    }

    public interface MyOnClick {
        void onClick(View view);
        void onCheckedChanged(CompoundButton compoundButton, boolean b);
    }

    private final Handler mHideHandler = new Handler();

    private LinearLayout crop_control_linearLayout;

    private Button crop_button;
    private Button save_opt_button;
    private Button crop_again_button;
    //Button save_button;

    private PopupWindow mDropdown = null;

    private Switch cropper_fixedAspectRatio_switch;

    private LayoutInflater mInflater;
    private TextView cropper_guidelines_title_textView;

    private boolean visible = false;
    private PopupWindow initiatePopupWindow() {

        try {

            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.pop_up_edit_image_cropper, null);

            crop_control_linearLayout =  (LinearLayout) layout.findViewById(R.id.crop_control_linearLayout);
            crop_control_linearLayout.setVisibility(View.GONE);

            //menu_switch

            LinearLayout cropper_rotate_ccw_linearLayout = (LinearLayout) layout.findViewById(R.id.cropper_rotate_ccw_linearLayout);
            LinearLayout cropper_rotate_cw_linearLayout = (LinearLayout) layout.findViewById(R.id.cropper_rotate_cw_linearLayout);
            LinearLayout cropper_fixedAspectRatio_linearLayout = (LinearLayout) layout.findViewById(R.id.cropper_fixedAspectRatio_linearLayout);
            LinearLayout cropper_guidelines_linearLayout = (LinearLayout) layout.findViewById(R.id.cropper_guidelines_linearLayout);

            cropper_rotate_ccw_linearLayout.setOnClickListener(this);
            cropper_rotate_cw_linearLayout.setOnClickListener(this);
            cropper_fixedAspectRatio_linearLayout.setOnClickListener(this);
            cropper_guidelines_linearLayout.setOnClickListener(this);

            crop_button = (Button) layout.findViewById(R.id.crop_button);
            crop_button.setOnClickListener(this);
            //noinspection ResourceType
            crop_button.setId(crop_button_id);

            save_opt_button = (Button) layout.findViewById(R.id.save_opt_button);
            save_opt_button.setOnClickListener(this);
            save_opt_button.setVisibility(View.INVISIBLE);

            //crop_again_button = (Button) layout.findViewById(R.id.crop_again_button);
            //crop_again_button.setOnClickListener(this);
            //crop_again_button.setVisibility(View.GONE);

            //cropper_guidelines_title_textView = (TextView) layout.findViewById(R.id.cropper_guidelines_title_textView);

            //cropper_fixedAspectRatio_switch = (Switch) layout.findViewById(R.id.cropper_fixedAspectRatio_switch);
            //cropper_fixedAspectRatio_switch.setOnClickListener(this);
            cropper_fixedAspectRatio_switch.setOnCheckedChangeListener(this);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);

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

            setCropControlEnable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }

    public void setUndo(){
        crop_button.setId(crop_undo_button_id);
        crop_button.setText(context.getResources().getString(R.string.crop_undo));

        save_opt_button.setVisibility(View.VISIBLE);
        crop_again_button.setVisibility(View.VISIBLE);

        setCropControlEnable(false);
    }
    public void setCrop(){
        crop_button.setId(crop_button_id);
        crop_button.setText(context.getResources().getString(R.string.crop));

        save_opt_button.setVisibility(View.INVISIBLE);
        crop_again_button.setVisibility(View.GONE);

        setCropControlEnable(true);
    }

    public void setChooseImg(){
        crop_button.setId(choose_img_button_id);
        crop_button.setText(context.getResources().getString(R.string.choose_img));
        //

        save_opt_button.setVisibility(View.GONE);
        crop_again_button.setVisibility(View.GONE);

        setCropControlEnable(false);
    }
    private void setCropControlEnable(boolean _state){
        if(_state){
            crop_control_linearLayout.setVisibility(View.VISIBLE);
        }else{
            crop_control_linearLayout.setVisibility(View.GONE);
        }
    }


    public void fixedAspectRatioTxt(boolean _state){
        cropper_fixedAspectRatio_switch.setChecked(_state);
    }

    public void guidelinesTxt(String _txt){

        String txt = context.getResources().getString(R.string.set_guidelines)+" "+_txt;

        //cropper_guidelines_title_textView
        cropper_guidelines_title_textView.setText(txt);
    }


    public void dismiss(){
        mDropdown.dismiss();

        Switch menu_switch = (Switch) anchor.findViewById(R.id.menu_switch);
        menu_switch.setChecked(visible);

        ImageView menu_imageView = (ImageView) anchor.findViewById(R.id.menu_imageView);
        menu_imageView.setImageResource(R.mipmap.down_arrow_blue);
    }

    public void expandPopUpWindows(){
        mDropdown.showAsDropDown(anchor, 5, 5);

        ImageView menu_imageView = (ImageView) anchor.findViewById(R.id.menu_imageView);
        menu_imageView.setImageResource(R.mipmap.up_arrow_blue);
    }

    public void delayShow(){
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    public void delayHide(){
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    public void delaySetCrop(){

        dismiss();

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mSetCropRunnable, UI_ANIMATION_DELAY);

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

    private final Runnable mSetCropRunnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of popUp window
            setCrop();
        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed hide of popUp window
            dismiss();
        }

    };

}
