package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.PopUpWindow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/10/2016.
 */
@SuppressWarnings("ResourceType")
public class MenuBar implements View.OnClickListener, EditPopUpWindow.OnEditMenuAction, SavePopUpWindow.OnSaveMenuAction, CompoundButton.OnCheckedChangeListener {

    private SavePopUpWindow savePopUpWindow;
    private EditPopUpWindow editPopUpWindow;

    private Context context;
    private ActionBar actionBar;
    private View anchor;

    private String CROP_RES;
    private String CROP_AGAIN_RES;
    private String LOAD_PICTURE_RES;
    private String UNDO_RES;
    private final int CROP_ID = 101;
    private final int CROP_AGAIN_ID = 102;
    private final int LOAD_PICTURE_ID = 103;
    private final int UNDO_ID = 104;

    private boolean FIXED_ASPECT_RATIO = false;

    private boolean SAVE_ORIG = true;
    private boolean SAVE_CROP = true;

    private ImageView menu_imageView;
    private ImageView undo_imageView;
    private Button save_opt_button;
    private Button crop_button;
    private Button choose_button;
    private Switch crop_enable_switch;
    private TextView crop_textView;

    public MenuBar(Context _context, ActionBar _actionBar) {
        this.context = _context;
        this.actionBar = _actionBar;
        initActionBar();
    }

    /** GUI CONTROLLS **/
    //TODO: init popupwindow here, that way we can pass the view to the class
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initActionBar(){

        if (actionBar != null) {
            actionBar.setTitle("");
            //TODO: find alternative not deprecated
            actionBar.setIcon(
                    new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setCustomView(R.layout.image_cropper_action_bar); //mine
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);

            Toolbar parent =(Toolbar) actionBar.getCustomView().getParent();
            parent.setContentInsetsAbsolute(0,0);

            //--
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            //--

            initViews(actionBar);

            /** where the pop up window will appear**/
            anchor = actionBar.getCustomView().findViewById(R.id.menu_imageView);

            savePopUpWindow = new SavePopUpWindow(context,save_opt_button);
            savePopUpWindow.setOnEventListener(this);
            SAVE_ORIG = savePopUpWindow.save_orig_switch.isChecked();
            SAVE_CROP = savePopUpWindow.save_crop_switch.isChecked();

            editPopUpWindow = new EditPopUpWindow(context,anchor);
            editPopUpWindow.setOnEventListener(this);

        }
    }

    //private boolean SAVE_ORIG = true;
    //private boolean SAVE_CROP = true;

    private void initViews(ActionBar _actionBar){
        menu_imageView = (ImageView) _actionBar.getCustomView().findViewById(R.id.menu_imageView);
        menu_imageView.setOnClickListener(this);

        undo_imageView = (ImageView) _actionBar.getCustomView().findViewById(R.id.undo_imageView);

        save_opt_button = (Button) _actionBar.getCustomView().findViewById(R.id.save_opt_button);
        save_opt_button.setOnClickListener(this);

        crop_button = (Button) _actionBar.getCustomView().findViewById(R.id.crop_button);
        crop_button.setOnClickListener(this);

        CROP_RES = context.getResources().getString(R.string.crop);
        CROP_AGAIN_RES = context.getResources().getString(R.string.crop_again);
        LOAD_PICTURE_RES = context.getResources().getString(R.string.choose_img);
        UNDO_RES = context.getResources().getString(R.string.crop_undo);

        choose_button = (Button) _actionBar.getCustomView().findViewById(R.id.choose_button);
        choose_button.setOnClickListener(this);

        crop_enable_switch = (Switch) _actionBar.getCustomView().findViewById(R.id.crop_enable_switch);
        crop_enable_switch.setOnCheckedChangeListener(this);

        crop_textView = (TextView) _actionBar.getCustomView().findViewById(R.id.crop_textView);

        setSaveVisible(View.INVISIBLE);
        setCrop(View.INVISIBLE);
        setUndo(View.INVISIBLE,false);
        setAnotherPicture(View.INVISIBLE);
        setMenuControl(View.INVISIBLE);
        //undo_button


        if(crop_enable_switch.isChecked()){
            //editPopUpWindow.setEditLayoutVisible(View.VISIBLE);
            crop_button.setVisibility(View.VISIBLE);
        }else {
            //editPopUpWindow.setEditLayoutVisible(View.INVISIBLE);
            crop_button.setVisibility(View.INVISIBLE);
        }
    }

    public void setSaveVisible(int _visibility){
        save_opt_button.setVisibility(_visibility);
    }


    public void setMenuControl(int _visibility) {
        menu_imageView.setVisibility(_visibility);
        save_opt_button.setVisibility(_visibility);
        if(crop_enable_switch.isChecked()) setCrop(_visibility);
        crop_enable_switch.setVisibility(_visibility);
        crop_textView.setVisibility(_visibility);
    }

    public void setAnotherPicture(int _visibility){
        choose_button.setVisibility(_visibility);
        choose_button.setId(LOAD_PICTURE_ID);
        choose_button.setText(LOAD_PICTURE_RES);
    }

    public void setCrop(int _visibility){
        crop_button.setVisibility(_visibility);
        crop_button.setId(CROP_ID);
        crop_button.setText(CROP_RES);
    }

    public void setCropAgain(){
        crop_button.setId(CROP_AGAIN_ID);
        crop_button.setText(CROP_AGAIN_RES);
    }

    public void setUndo(int _visibility, boolean state){
        undo_imageView.setId(UNDO_ID);
        undo_imageView.setVisibility(_visibility);
        if(state){
            undo_imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.undo_blue));
            undo_imageView.setOnClickListener(this);
        }else{
            undo_imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.undo_grey));
            undo_imageView.setOnClickListener(null);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_imageView: {

                if(crop_enable_switch.isChecked()){
                    editPopUpWindow.setEditLayoutVisible(View.VISIBLE);
                }else {
                    editPopUpWindow.setEditLayoutVisible(View.GONE);
                }

                if(crop_enable_switch.isChecked()){
                    //editPopUpWindow.setEditLayoutVisible(View.VISIBLE);
                    crop_button.setVisibility(View.VISIBLE);
                }else {
                    //editPopUpWindow.setEditLayoutVisible(View.INVISIBLE);
                    crop_button.setVisibility(View.INVISIBLE);
                }

                editPopUpWindow.expandPopUpWindows();
                break;
            }
            case R.id.save_opt_button: {
                //Todo: not needed, too complicated.
                //savePopUpWindow.expandPopUpWindows();
                mOnMenuAction.onSaveSimple();
                break;
            }
            case CROP_ID: {
                setCropAgain();
                //Log.d("addImage", "CROP_ID");
                mOnMenuAction.onCropPicture();
                break;
            }
            case CROP_AGAIN_ID: {
                if(crop_enable_switch.isChecked()) setCrop(View.VISIBLE);
                //Log.d("addImage", "CROP_AGAIN_ID");
                mOnMenuAction.onCropAgainPicture();
                break;
            }
            case LOAD_PICTURE_ID:{
                mOnMenuAction.onReplaceSelectedPicture();
                break;
            }
            case UNDO_ID:{
                crop_button.setId(CROP_ID);
                crop_button.setText(CROP_RES);

                setUndo(View.VISIBLE,false);

                mOnMenuAction.onCropUndo();
                break;
            }
        }
    }

    @Override
    public void onEditAction(View view, boolean _state) {
        switch (view.getId()) {
            case R.id.choose_button:{

                boolean state = editPopUpWindow.keep_prev_switch.isChecked();
                mOnMenuAction.onSetAnotherPicture(state);
                editPopUpWindow.dismiss();

                break;
            }
            case R.id.cw_button:{
                //Log.d("addImage","cw_button");
                mOnMenuAction.onRotateCropView(90);
                break;
            }
            case R.id.ccw_button:{
                //Log.d("addImage","ccw_button");
                mOnMenuAction.onRotateCropView(-90);
                break;
            }
            case R.id.guidelines_off_textView:{
                mOnMenuAction.onSetGuidelinesCropView(CropImageView.Guidelines.OFF);
                editPopUpWindow.setGuidelines(CropImageView.Guidelines.OFF);
                break;
            }
            case R.id.guidelines_on_textView:{
                mOnMenuAction.onSetGuidelinesCropView(CropImageView.Guidelines.ON);
                editPopUpWindow.setGuidelines(CropImageView.Guidelines.ON);
                break;
            }
            case R.id.guidelines_on_touch_textView:{
                mOnMenuAction.onSetGuidelinesCropView(CropImageView.Guidelines.ON_TOUCH);
                editPopUpWindow.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
                break;
            }
            case R.id.fixedAspectRatio_switch: {
                FIXED_ASPECT_RATIO = _state;
                mOnMenuAction.onSetFixedAspectRatioCropView(FIXED_ASPECT_RATIO);
                //Log.d("addImage", "fixedAspectRatio_switch: "+FIXED_ASPECT_RATIO);
                break;
            }
        }
    }

    @Override
    public void onSaveAction(View view, boolean _state) {
        switch (view.getId()) {
            case R.id.save_button:{
                //Log.d("addImage","save_button");
                //Log.d("addImage", "SAVE_ORIG: "+SAVE_ORIG);
                //Log.d("addImage", "SAVE_CROP: "+SAVE_CROP);
                mOnMenuAction.onSaveAction(view,SAVE_ORIG,SAVE_CROP);
                break;
            }
            case R.id.save_as_button:{
                //Log.d("addImage","save_as_button");
                mOnMenuAction.onSaveAsAction(view,SAVE_ORIG,SAVE_CROP);
                break;
            }
            case R.id.save_orig_switch: {
                SAVE_ORIG = _state;
                //Log.d("addImage", "save_orig_switch: "+SAVE_ORIG);
                break;
            }
            case R.id.save_crop_switch: {
                SAVE_CROP = _state;
                //Log.d("addImage", "save_crop_switch: "+SAVE_CROP);
                break;
            }
        }
    }

    /** Selection Listener
     * Pass selection to MenuBar*/
    private OnMenuAction mOnMenuAction;

    public void setOnEventListener(OnMenuAction listener) {
        mOnMenuAction = listener;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean _state) {
        //crop_enable_switch
        switch (compoundButton.getId()) {
            case R.id.crop_enable_switch: {
                mOnMenuAction.onCropOverlay(_state);
                if(_state) {
                    crop_button.setVisibility(View.VISIBLE);
                    editPopUpWindow.setEditLayoutVisible(View.VISIBLE);
                }else {
                    crop_button.setVisibility(View.INVISIBLE);
                    editPopUpWindow.setEditLayoutVisible(View.INVISIBLE);
                }
            }
        }

    }

    public interface OnMenuAction {
        /**
         * Save the image based on the state of the switches
         *
         * @param view
         * @param save_orig
         * @param save_crop
         */
        void onSaveAction(View view, boolean save_orig, boolean save_crop);

        void onSaveSimple();

        /**
         * Save the image based on the state of the switches, the user can change the name
         *
         * @param view
         * @param save_orig
         * @param save_crop
         */
        void onSaveAsAction(View view, boolean save_orig, boolean save_crop);

        /**
         * @param guidelines
         */
        void onSetGuidelinesCropView(CropImageView.Guidelines guidelines);

        /**
         * @param state
         */
        void onSetFixedAspectRatioCropView(boolean state);

        /**
         * Rotates image by the specified number of degrees clockwise.<br>
         * Cycles from 0 to 360 degrees.
         *
         * @param degrees Integer specifying the number of degrees to rotate.
         *        negative CCW
         *        positive CW
         */
        void onRotateCropView(int degrees);

        /**
         * Crops the picture
         */
        void onCropPicture();

        /**
         * Crops the picture again
         */
        void onCropAgainPicture();

        /**
         * Undo the prev cropping
         */
        void onCropUndo();

        void onSetAnotherPicture(boolean state);

        void onReplaceSelectedPicture();

        void onCropOverlay(boolean state);
    }
}
