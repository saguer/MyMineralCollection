package mymineralcollection.example.org.mymineral.NOT_USED.REF.old;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

public class ImageCropperFullscreenActivity_old extends AppCompatActivity implements View.OnClickListener,
        EditPopUpWindow_old.MyOnClick,
        SavePopUpWindow_old.MyOnClick {

    private View mControlsView;

    private CropImageView cropImageView;
    private ImageView addImage_imageView;
    private ImageView add_image_place_imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cropper_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.cropImageView);
        mControlsView.setOnClickListener(this);

        context = getApplicationContext();

        addImage_imageView =  (ImageView) findViewById(R.id.add_image_imageView);
        addImage_imageView.setOnClickListener(this);

        add_image_place_imageView  =  (ImageView) findViewById(R.id.add_image_place_imageView);
        add_image_place_imageView.setOnClickListener(this);
        add_image_place_imageView.setVisibility(View.GONE);

        cropImageView = (CropImageView) mControlsView;
        cropImageView.setVisibility(View.GONE);

        //cropImageView.setAspectRatio(5, 10);
        cropImageView.setFixedAspectRatio(FIXED_ASPECT_RATIO);
        //cropImageView.setCropShape(CropImageView.CropShape.OVAL);
        cropImageView.setScaleType(CropImageView.ScaleType.FIT_CENTER);

        cropImageView.setShowProgressBar(true);
        cropImageView.setCropRect(new Rect(0, 0, 800, 500));

        cropImageView.setMultiTouchEnabled(true);
        cropImageView.setAutoZoomEnabled(true);

        GUIDELINES_TEXT = setGuidelines(GUIDELINES_CLICK_COUNTER);


        initActionBar();

        editPopUpWindowOld = new EditPopUpWindow_old(this, menu_imageView, crop_button_id, crop_undo_button_id, choose_img_button_id, menu_switch);
        editPopUpWindowOld.setOnClickListener(this);

        savePopUpWindowOld = new SavePopUpWindow_old(this, menu_imageView, SAVE_ORIG, SAVE_CROP);
        savePopUpWindowOld.setOnClickListener(this);

        getBundleData();


    }

    Switch menu_switch;

    //Data to be saved to personal dB, contains mineral names combination
    JSONObject mJsonObj;

    private String MINEAL_NAME;

    private void getBundleData(){
        DataWrapper_LabelObject dw = (DataWrapper_LabelObject) getIntent().getSerializableExtra("itemList");

        Bundle getBundle = this.getIntent().getExtras();

        String img_path = getBundle.getString("imageUri");

        Log.d("addImage", "img_path: "+img_path);

        //File f = new File(img_path);
        //todo: save image first
        //IMG_URI_ARRAY.add(Uri.fromFile(f);

        if(dw != null) {
            ArrayList<LabelObject> labelList = dw.getLabelObject();

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            String _mineralID = null; //for App

            Collection<JSONObject> items = new ArrayList<>();

            for (LabelObject _labelObj : labelList) {

                String mineralName = _labelObj.getMineralName();
                String extras = _labelObj.getExtra();
                int index = _labelObj.getIndex();

                /**         NOTE
                 if in the future we require the formula of the mineral without having to open the localDb
                 We can add the formula as a Json field here for each mineral
                 That way we can avoid having to access 2 dB for something we can get from one if we add it here.
                 */

                //Log.d("addImage", "mineralName: " + mineralName);
                //Log.d("addImage", "extras: " + extras);
                //Log.d("addImage", "index: " + index);
                //Log.d("addImage", "- - - - - - - -");

                String temp = mineralName.charAt(0)+""+mineralName.charAt(1)+mineralName.charAt(2);

                if(_mineralID == null){
                    _mineralID = temp;
                }else{
                    _mineralID = _mineralID + "_" + temp;
                }

                if(extras == null) extras = "none";

                JSONObject item1 = recordMineralPersonalDb(mineralName, extras, index);

                items.add(item1);

            }

            mJsonObj = new JSONObject();

            MINEAL_NAME = _mineralID;

            try {
                mJsonObj.put((_mineralID+"_"+ts), new JSONArray(items));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("addImage", "mJsonObj: " + mJsonObj.toString());

        }

        //Cause slow load
        //CropImage.startPickImageActivity(this);

    }

    public JSONObject recordMineralPersonalDb(String _mineral, String _extra, int _index){
        try {

            //JSONObject jo = new JSONObject();
            JSONObject _json = new JSONObject();
            _json.put("mineral", _mineral);
            _json.put("extra", _extra);
            _json.put("index", _index);
            //jo.put("mineralID", _mineralID);
            return _json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    EditPopUpWindow_old editPopUpWindowOld;
    SavePopUpWindow_old savePopUpWindowOld;

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }

    //TextView crop_textView;
    ImageView menu_imageView;

    /** GUI CONTROLLS **/
    //TODO: init popupwindow here, that way we can pass the view to the class
    private void initActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            //TODO: find alternative not deprecated
            actionBar.setIcon(
                    new ColorDrawable(getResources().getColor(android.R.color.transparent)));

            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.image_cropper_action_bar_old);

            menu_imageView = (ImageView) actionBar.getCustomView().findViewById(R.id.menu_imageView);
            menu_imageView.setOnClickListener(this);

            menu_switch = (Switch) actionBar.getCustomView().findViewById(R.id.menu_switch);

            //menu_imageView
        }
    }


    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private boolean mVisible;
    private void toggle() {
        if (mVisible) {
            hide();
            mVisible = false;
        } else {
            show();
            mVisible = true;
        }

        Log.d("addImage","mVisible: "+mVisible);
    }

    private void hide() {
        // Hide UI first
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)actionBar.hide();
    }
    @SuppressLint("InlinedApi")
    private void show() {
        mVisible = true;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)  actionBar.show();
    }

    int fileNameCounterTest = 0;
    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.add_image_imageView: {
                CropImage.startPickImageActivity(this);

                //overlay_FrameLayout.setVisibility(View.VISIBLE);
                Log.d("addImage", ">>> add_image_imageView");

                break;
            }
            case R.id.menu_imageView: {

                if(IMG_URI_ACTVITY_RESULT == null) {
                    editPopUpWindowOld.setChooseImg();
                }

                editPopUpWindowOld.fixedAspectRatioTxt(FIXED_ASPECT_RATIO);
                editPopUpWindowOld.guidelinesTxt(GUIDELINES_TEXT);
                editPopUpWindowOld.delayShow();


                break;
            }
            case R.id.cropper_rotate_ccw_linearLayout: {
                Log.d("addImage", "cropper_rotate_ccw_linearLayout");
                cropImageView.rotateImage(-90);
                //TODO: redraw
                break;
            }
            case R.id.cropper_rotate_cw_linearLayout: {
                Log.d("addImage", "cropper_rotate_cw_linearLayout");
                cropImageView.rotateImage(90);
                //TODO: redraw
                break;
            }
            case R.id.cropper_guidelines_linearLayout: {
                Log.d("addImage", "cropper_guidelines_linearLayout");
                GUIDELINES_TEXT = setGuidelines(GUIDELINES_CLICK_COUNTER);
                editPopUpWindowOld.guidelinesTxt(GUIDELINES_TEXT);
                //Todo: add a delay before hiding
                editPopUpWindowOld.delayHide();
                break;
            }
            case crop_button_id:{

                if(IMG_URI_ACTVITY_RESULT != null) {
                    cropPicture();

                    editPopUpWindowOld.setUndo();

                    //editPopUpWindow.setSaveControlEnable(true);
                    //editPopUpWindow.delayHide();

                    Log.d("addImage", "----------------------");
                    checkArray();
                    Log.d("addImage", "----------------------");
                }else{
                    Toast.makeText(context,"No Image Loaded!",
                            Toast.LENGTH_LONG).show();

                    editPopUpWindowOld.setChooseImg();
                }

                break;
            }
            case crop_undo_button_id:{

                setCropAgain();
                //editPopUpWindow.setCrop();
                editPopUpWindowOld.delaySetCrop();

                //editPopUpWindow.setSaveControlEnable(false);
                //editPopUpWindow.delayHide();

                //TODO: remove last array entry
                IMG_URI_ARRAY.remove(IMG_URI_ARRAY.size() - 1);

                Log.d("addImage", "----------------------");
                checkArray();
                Log.d("addImage", "----------------------");

                break;
            }
            case choose_img_button_id:{
                editPopUpWindowOld.delayHide();
                CropImage.startPickImageActivity(this);

                break;
            }
            //case R.id.crop_again_button:{
//
            //    setCropAgain();
            //    editPopUpWindowOld.delaySetCrop();
//
            //    break;
            //}

            //
            case R.id.save_button:{
                Log.d("addImage", "save_button: SAVE IMG");
                Log.d("addImage", "SAVE_ORIG: "+SAVE_ORIG);
                Log.d("addImage", "SAVE_CROP: "+SAVE_CROP);

                //if(CROP_URI_RESULT != null) {
                //    IMG_URI_ARRAY.add(CROP_URI_RESULT);
                //}

                Log.d("addImage", "Save to DB Launch other activity");
                //setDialog();

                Log.d("addImage", "mJsonObj: " + mJsonObj.toString());

                //---- To test
                String txt = null;
                for(Uri img_: IMG_URI_ARRAY){
                    Log.d("addImage", "img: "+img_.toString());

                    if(txt == null) txt = img_.toString() + "\n";

                    txt = txt + img_.toString() + "\n";

                }

                Toast.makeText(context,"PATH: \n"+txt,
                        Toast.LENGTH_LONG).show();

                break;
            }
            case R.id.save_opt_button:{

                editPopUpWindowOld.dismiss();
                savePopUpWindowOld.show();

                //editPopUpWindow.setCropControlEnable(false);
                //editPopUpWindow.setSaveControlEnable(true);
                break;
            }
            case R.id.save_as_button:{
                Log.d("addImage", "NOT IMPLEMENTED");
                Log.d("addImage", "save_as_button: SAVE AS");
                Log.d("addImage", "SAVE_ORIG: "+SAVE_ORIG);
                Log.d("addImage", "SAVE_CROP: "+SAVE_CROP);

                break;
            }
            default:{
                //toggle();
            }
        }
    }


    private void setDialog(){
        new MaterialDialog.Builder(this)
                .content("Crop Again?")
                .positiveText("Yes")
                .negativeText("No")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        //setCropAgain();
                        //checkArray();
//
                        //editPopUpWindow.setCrop();
                        //editPopUpWindow.setCropControlEnable(true);
//
                        //editPopUpWindow.dismiss();
                        //savePopUpWindow.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d("addImage", "Launch other activity");

                    }
                })
                .show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean _state) {
        switch (compoundButton.getId()) {
            //case R.id.cropper_fixedAspectRatio_switch: {
            //    FIXED_ASPECT_RATIO = _state;
            //    Log.d("addImage", "cropper_fixedAspectRatio_linearLayout: "+FIXED_ASPECT_RATIO);
            //    cropImageView.setFixedAspectRatio(FIXED_ASPECT_RATIO);
            //    if(FIXED_ASPECT_RATIO) cropImageView.setAspectRatio(5, 10);
            //    //Todo: add a delay before hiding
            //    //editPopUpWindow.delayHide();
//
            //    break;
            //}
            case R.id.save_crop_switch:{
                SAVE_CROP = _state;
                break;
            }
            case R.id.save_orig_switch:{
                SAVE_ORIG = _state;
                break;
            }
            case R.id.menu_switch: {

                if (_state) {
                    menu_switch.setText("Collapse ");

                    editPopUpWindowOld.fixedAspectRatioTxt(FIXED_ASPECT_RATIO);
                    editPopUpWindowOld.guidelinesTxt(GUIDELINES_TEXT);
                    editPopUpWindowOld.delayShow();

                    if(IMG_URI_ACTVITY_RESULT == null) {
                        editPopUpWindowOld.setChooseImg();
                    }
                } else {
                    menu_switch.setText("Expand   ");
                }

                break;
            }


        }
    }

    private String setFileName(String _suffix){
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String _name = MINEAL_NAME;
        if(_suffix != null){
            return _name+"_"+timeStamp+"_"+_suffix;
        }else{
            return _name+"_"+timeStamp;
        }
    }


    private void cropPicture(){
        fileNameCounterTest++;

        addImage_imageView.setVisibility(View.GONE);
        add_image_place_imageView.setVisibility(View.VISIBLE);
        cropImageView.setVisibility(View.GONE);

        Bitmap _bitmap = cropImageView.getCroppedImage();
        File f = null;
        if(_bitmap != null) {

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
            //String mImageName = "MI_" + timeStamp+"_"+fileNameCounterTest;
            //String mImageName = MINEAL_NAME+"_"+fileNameCounterTest;

            String mImageName = setFileName(fileNameCounterTest+"");

            String img_path = Methods.storeImage(context, mImageName, _bitmap);

            if (img_path != null) {
                f = new File(img_path);
                IMG_URI_ARRAY.add(Uri.fromFile(f));
            }

            _bitmap.recycle();

            Toast.makeText(context,"Image Cropped!",
                    Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(context,"Image Not Cropped!",
                    Toast.LENGTH_LONG).show();
        }

        Picasso.with(context).load(f).error(R.drawable.no_image).into(add_image_place_imageView);
        //showPopup(R.menu.save_popup_menu,save_textView);


    }

    public void setCropAgain(){
        addImage_imageView.setVisibility(View.GONE);
        add_image_place_imageView.setVisibility(View.GONE);
        cropImageView.setVisibility(View.VISIBLE);

        cropImageView.setImageUriAsync(IMG_URI_ACTVITY_RESULT);
    }



    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("addImage", "onActivityResult: "+requestCode);
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
            editPopUpWindowOld.setCrop();
        }
    }

    private boolean SAVE_ORIG = true;
    private boolean SAVE_CROP = true;

    private ArrayList<Uri> IMG_URI_ARRAY= new ArrayList<>();
    private Uri IMG_URI_ACTVITY_RESULT = null;




    private void checkArray(){

        for(Uri img_: IMG_URI_ARRAY){
            Log.d("addImage", "img: "+img_.toString());
        }
    }
    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {

        Picasso.with(context).load(imageUri).into(target);

        addImage_imageView.setVisibility(View.GONE);
        add_image_place_imageView.setVisibility(View.GONE);
        cropImageView.setVisibility(View.VISIBLE);

        cropImageView.setImageUriAsync(imageUri);

        Log.d("addImage", "imageUri: " + imageUri);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
            //String mImageName = "MI_" + timeStamp;
            //String mImageName = "MI_" + MINEAL_NAME;

            String mImageName = setFileName(null);

            String img_path = Methods.storeImage(context, mImageName, bitmap);

            if(img_path != null) {
                File f = new File(img_path);
                IMG_URI_ACTVITY_RESULT = Uri.fromFile(f);
                IMG_URI_ARRAY.add(IMG_URI_ACTVITY_RESULT);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private final Handler mHideHandler = new Handler();


    /** MENU CONTROLS **/
    private boolean FIXED_ASPECT_RATIO = false;
    private int GUIDELINES_CLICK_COUNTER = 0;
    private String GUIDELINES_TEXT = null;

    private final int crop_button_id = 101;
    private final int crop_undo_button_id = 102;
    private final int choose_img_button_id = 103;

    private String setGuidelines(int _clickCounter){

        Log.d("addImage","clickCounter: " + _clickCounter);

        switch (_clickCounter){
            case 0:{
                cropImageView.setGuidelines(CropImageView.Guidelines.OFF);
                Log.d("addImage",">>> " + CropImageView.Guidelines.OFF);
                GUIDELINES_CLICK_COUNTER = 1;
                return getResources().getString(R.string.set_guidelines_OFF);
            }
            case 1:{
                cropImageView.setGuidelines(CropImageView.Guidelines.ON);
                Log.d("addImage",">>> " + CropImageView.Guidelines.ON);
                GUIDELINES_CLICK_COUNTER = 2;
                return getResources().getString(R.string.set_guidelines_ON);
            }
            case 2:{
                cropImageView.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
                Log.d("addImage",">>> " + CropImageView.Guidelines.ON_TOUCH);
                GUIDELINES_CLICK_COUNTER = 0;
                return getResources().getString(R.string.set_guidelines_ON_TOUCH);
            }
        }
        return "";
    }



    /** PopUp Menu **/
    //public IconizedMenu showPopup(int _menuLayout, View v) {
    //    IconizedMenu popup = new IconizedMenu(this, v);
//
    //    MenuInflater inflater = popup.getMenuInflater();
    //    inflater.inflate(_menuLayout, popup.getMenu());
//
    //    popup.setOnMenuItemClickListener(this);
    //    popup.show();
//
    //    return popup;
    //}
    //private void editPopUpMenu(){
    //    IconizedMenu popup = showPopup(R.menu.edit_popup_menu, edit_button);
    //    popup.show();
//
    //    Menu menuItem = popup.getMenu();
//
    //    String text = getResources().getString(R.string.set_guidelines) + ": " + GUIDELINES_TEXT;
    //    menuItem.findItem(R.id.cropper_guidelines).setTitle(text);
//
    //    text = getResources().getString(R.string.fixed_aspect_ratio) + ": " + FIXED_ASPECT_RATIO;
    //    menuItem.findItem(R.id.cropper_fixedAspectRatio).setTitle(text);
    //}
    //@Override
    //public boolean onMenuItemClick(MenuItem item) {
//
    //    return false;
    //}

}

