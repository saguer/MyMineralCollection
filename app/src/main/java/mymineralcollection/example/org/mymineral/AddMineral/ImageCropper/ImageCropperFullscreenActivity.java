package mymineralcollection.example.org.mymineral.AddMineral.ImageCropper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.AddMineralListActivity;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ToolBars.CropToolBar;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ToolBars.OrientationToolBar;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ToolBars.ToolBar;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.Class.BitmapMethods;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.MineralList.MyListActivity;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 11/9/2016.
 */
@SuppressWarnings("ResourceType")
public class ImageCropperFullscreenActivity extends Activity implements
        //MenuBar.OnMenuAction,
        View.OnClickListener,
        ToolBar.ToolBar_interface,
        CropToolBar.CropToolBar_interface,
        OrientationToolBar.OrientationToolBar_interface{

    public static final int IMAGE_CROPPER_RESULT_CODE = 192;

    final int THUMB_SIZE = 1024;

    private CropImageView cropImageView;
    private ImageView addImage_imageView;
    private ImageView add_image_place_imageView;

    private Context context;

    private Uri IMG_URI_RESULT = null;

    private String MINERAL_ID = "haveToSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_image_cropper_fullscreen);

        context = getApplicationContext();

        Methods.checkLoadActivityState(this);

        MINERAL_ID = AddMineralData.getLettersForName();

        addImage_imageView =  (ImageView) findViewById(R.id.add_image_imageView);
        addImage_imageView.setOnClickListener(this);

        add_image_place_imageView  =  (ImageView) findViewById(R.id.add_image_place_imageView);
        add_image_place_imageView.setOnClickListener(this);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        cropImageView.setScaleType(CropImageView.ScaleType.FIT_CENTER);

        cropImageView.setShowProgressBar(true);
        cropImageView.setCropRect(new Rect(0, 0, 800, 500));
        cropImageView.setMultiTouchEnabled(true);
        cropImageView.setAutoZoomEnabled(true);
        cropImageView.setShowCropOverlay(false);

        //todo: check if it is null
        IMG_URI_RESULT = AddMineralData.getImgUriResult();

        if(IMG_URI_RESULT != null){
            setImage(IMG_URI_RESULT);
        }

        createFragments();
        showPhoneStatePermission();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_image_imageView: {

                CropImage.startPickImageActivity(this);

                //overlay_FrameLayout.setVisibility(View.VISIBLE);
                Log.d("addImage", ">>> add_image_imageView");

                break;
            }

        }
    }

    private void hideCropFrag(){
        if(fragCrop.isVisible()){
            toggleCropFrag();
            cropImageView.setShowCropOverlay(false);
            cropImageView.setVisibility(View.GONE);

        }
    }
    private void hideOrientationFrag(){
        if(fragOrientation.isVisible()){
            toggleOrientationFrag();
            cropImageView.setShowCropOverlay(false);
        }
    }

    /** Get Image URI from activity result **/
    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean d = true;

        if(d) Log.d("addImage", "requestCode : "+requestCode);
        if(d) Log.d("addImage", "resultCode  : "+resultCode);


        switch (requestCode){
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:{
                if(resultCode == Activity.RESULT_OK){
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);

                    // For API >= 23 we need to check specifically that we have permissions to read external storage.
                    if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                        // request permissions and handle the result in onRequestPermissionsResult()
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    } else {
                        // no permissions required or already granted, can start crop image activity
                        AddMineralData.MOD_COUNTER = 0;
                        startCropImageActivity(imageUri);
                    }
                }else{
                //todo: add static
                IMG_URI_RESULT = AddMineralData.getImgUriResult();
                Log.d("addImage", "Pick Canceled");
                }
                break;
            }
            default:{

            }
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        setImage(imageUri); //startActivity
        Picasso.with(context).load(imageUri).into(target);
    }

    private void setImage(Uri imageUri){

        add_image_place_imageView.setVisibility(View.VISIBLE);

        Picasso.with(context).load(imageUri).into(add_image_place_imageView);

        add_image_place_imageView.setOnClickListener(null);
        addImage_imageView.setOnClickListener(null);
        addImage_imageView.setVisibility(View.GONE);
    }



    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            AddMineralData.IMAGE_LOAD_COUNTER++;
            String mImageName = setFileName(AddMineralData.IMAGE_LOAD_COUNTER +"",null,null);

            boolean d = false;

            if(d) Log.d("addImage", "----------------------");
            if(d) Log.e("addImage", "mImageName: "+mImageName);
            if(d) Log.d("addImage", "----------------------");

            String img_path = Methods.storeImage(context, mImageName, bitmap);
            String thumbnail = Methods.storeThumbnail(context, mImageName+"_th", bitmap, THUMB_SIZE);

            if(img_path != null) {

                //TEMPS.onBitmapLoaded(img_path);

                File f = new File(img_path);
                IMG_URI_RESULT = Uri.fromFile(f); //onBitmapLoaded
                AddMineralData.setImgUriResult( Uri.fromFile(f));

                AddMineralData.addUriPathObj(Uri.fromFile(f).getPath(),thumbnail,true,false,Methods.getTimeStamp());
                //JSON.addOrigPath(img_path);

                if(toolBarFrag != null) {
                    if (!toolBarFrag.isVisible()) {
                        toggleToolBarFrag();
                        if (toolBarFrag.repeat_btn != null) {
                            toolBarFrag.repeat_btn.setVisibility(View.INVISIBLE);
                        }
                    }
                }

            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String permissions[],
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private final int REQUEST_PERMISSION_PHONE_STATE=1;
    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {
            //Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }
    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }
    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    private void addPicture(Bitmap _bitmap, String _action){
        AddMineralData.MOD_COUNTER++;

        addImage_imageView.setVisibility(View.GONE);
        add_image_place_imageView.setVisibility(View.VISIBLE);
        cropImageView.setVisibility(View.GONE);

        File f = null;
        if(_bitmap != null) {

            String mImageName = setFileName(AddMineralData.IMAGE_LOAD_COUNTER +"", _action, AddMineralData.MOD_COUNTER +"");

            String img_path = Methods.storeImage(context, mImageName, _bitmap);
            String thumbnail = Methods.storeThumbnail(context, mImageName+"_th", _bitmap, THUMB_SIZE);

            if (img_path != null) {
                f = new File(img_path);

                IMG_URI_RESULT =Uri.fromFile(f);

                AddMineralData.addUriPathObj(Uri.fromFile(f).getPath(),thumbnail,false,true,Methods.getTimeStamp());

            }

            _bitmap.recycle();
        }else{
            String txt = "Image Not "+_action+"!";
            Toast.makeText(context,txt,
                    Toast.LENGTH_LONG).show();
        }

        Picasso.with(context).load(f).error(R.drawable.no_image).into(add_image_place_imageView);
    }

    public void cropPicture(){
        Bitmap _bitmap = cropImageView.getCroppedImage();
        addPicture(_bitmap,"Cropped");
        _bitmap.recycle();
    }

    public void rotatePicture(){
        if(bitmap != null) {
            addPicture(bitmap, "Rotated");
            bitmap.recycle();
        }
    }

    private String setFileName(String _prefix, String _action, String _suffix){

        String _name = MINERAL_ID;
        if(_suffix != null){
            return _prefix+"_"+_name+"_"+Methods.getTimesStamp()+"_"+_action+"_"+_suffix;
        }else{
            return _prefix+"_"+_name+"_"+Methods.getTimesStamp();
        }
    }

    //--- Fragments --
    //recycle
    Bitmap bitmap;
    ToolBar toolBarFrag;
    CropToolBar fragCrop;
    OrientationToolBar fragOrientation;
    private static final String CROP_FRAGMENT_TAG = "crop_fragment";
    private static final String ORIENTATION_FRAGMENT_TAG = "orientation_fragment";
    private static final String TOOLBAR_FRAGMENT_TAG = "toolbar_fragment";

    void createFragments(){
        fragCrop = new CropToolBar();
        fragOrientation = new OrientationToolBar();
        toolBarFrag = new ToolBar();

        if(IMG_URI_RESULT != null) toggleToolBarFrag();
    }

    private void toggleToolBarFrag() {
        toggleFragSlideUpDown(R.id.controlls_fragment,TOOLBAR_FRAGMENT_TAG,toolBarFrag);
    }

    private void toggleCropFrag() {
        toggleFragSlideUpDown(R.id.controlls_fragment,CROP_FRAGMENT_TAG,fragCrop);
    }

    private void toggleOrientationFrag() {
        toggleFragSlideUpDown(R.id.controlls_fragment,ORIENTATION_FRAGMENT_TAG,fragOrientation);
    }

    private void toggleFragSlideUpDown(int _fragmentLayout, String _tag, Fragment _frag){
        Fragment f = getFragmentManager().findFragmentByTag(_tag);
        if (f != null) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.slide_up,
                            R.animator.slide_down,
                            R.animator.slide_up,
                            R.animator.slide_down)
                    .add(_fragmentLayout, _frag, _tag).addToBackStack(null)
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public void launchCropFragment_interface() {
        if(!fragCrop.isVisible()){
            toggleCropFrag();
            cropImageView.setShowCropOverlay(true);

            addImage_imageView.setVisibility(View.GONE);
            add_image_place_imageView.setVisibility(View.GONE);

            cropImageView.setVisibility(View.VISIBLE);
            cropImageView.setImageUriAsync(IMG_URI_RESULT);
        }
    }

    @Override
    public void launchOrientationFragment_interface() {
        if(!fragOrientation.isVisible()){
            toggleOrientationFrag();
            cropImageView.setShowCropOverlay(false);

            addImage_imageView.setVisibility(View.GONE);
            add_image_place_imageView.setVisibility(View.VISIBLE);

            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver() , IMG_URI_RESULT);
            }
            catch (Exception e)
            {
                //handle exception
            }
        }
    }

    @Override
    public void setCropRatio_interface(boolean _enableRatio, int _xRatio, int _yRatio) {
        cropImageView.setFixedAspectRatio(_enableRatio);
        if(_enableRatio){
            cropImageView.setAspectRatio(_xRatio,_yRatio);
        }
    }

    @Override
    public void acceptCrop_interfaces() {
        cropPicture();
        //todo: add runnable delay
        hideCropFrag();
        setImage(IMG_URI_RESULT);

        if(toolBarFrag.isVisible()){
            toolBarFrag.repeat_btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cancelCrop_interfaces() {
        hideCropFrag();
        setImage(IMG_URI_RESULT);
    }

    @Override
    public void cancelOrientation_interfaces() {
        hideOrientationFrag();
        setImage(IMG_URI_RESULT);
    }

    @Override
    public void acceptOrientation_interfaces() {
        rotatePicture();
        //todo: add runnable delay
        hideOrientationFrag();
        setImage(IMG_URI_RESULT);

        if(toolBarFrag.isVisible()){
            toolBarFrag.repeat_btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void save_interface() {
        saveDialog();
        AddMineralData.printData("JSON_RECORD");
    }

    @Override
    public void reloadOrig_interface() {
        //todo: get from static
        //IMG_URI_RESULT = IMG_URI_TEMP.get(0);
        IMG_URI_RESULT = AddMineralData.getImgUriResult();
        setImage(IMG_URI_RESULT);
    }

    @Override
    public void rotate_interface(int _degree) {
        bitmap = BitmapMethods.rotateBitmap(bitmap, _degree);
        add_image_place_imageView.setImageBitmap(bitmap);
    }

    @Override
    public void flip_interface(int _dir) {
        bitmap = BitmapMethods.flipBitmap(bitmap, _dir);
        add_image_place_imageView.setImageBitmap(bitmap);
    }

    void launchPiker(){
        CropImage.startPickImageActivity(this);
    }

    private void saveDialog(){
        String dialogTitle = "Add Another Image to: "+ MINERAL_ID;
        new MaterialDialog.Builder(this)
                .title(dialogTitle)
                .cancelable(true)
                .positiveText("Add")
                .negativeText("Continue")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {launchPiker();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        /**
                         * In case the user comes back we save the URI
                         */
                        launchActivity();

                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {

        //String secondCol = createJson.createSecondColJson(IMG_FINAL).toString();
        //String curr_img_uri = IMG_URI_RESULT.getPath();


        Intent myIntent = new Intent(this, AddMineralListActivity.class);

        //myIntent.putExtra("itemList", dataWrapper_LabelObject);
        //myIntent.putExtra("secondCol", secondCol);
        //myIntent.putExtra("curr_img_uri", curr_img_uri);
        //myIntent.putExtra(ImageCropperFullscreenActivity.class.getName(),true);


        //int resultCode = Activity.RESULT_OK;
        //setResult(resultCode, myIntent);
        startActivity(myIntent);
        finish();

        super.onBackPressed();
    }

    public void launchActivity(){

        //Intent myIntent = new Intent(this, ViewImageActivity.class);

        //Intent myIntent = new Intent(this, AddMineralSpecificInfoActivity.class);
        Intent myIntent = new Intent(this, MyListActivity.class);

        myIntent.putExtra(ImageCropperFullscreenActivity.class.toString(),true);

        startActivity(myIntent);
        if(bitmap != null) bitmap.recycle();
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Toast.makeText(context, "hasFocus: "+hasFocus, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(context, "onPause()", Toast.LENGTH_SHORT).show();
    }
}


