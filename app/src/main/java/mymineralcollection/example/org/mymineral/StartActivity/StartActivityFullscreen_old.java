package mymineralcollection.example.org.mymineral.StartActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.AddMineralListActivity;
import mymineralcollection.example.org.mymineral.AddMineral.ImageCropper.ImageCropperFullscreenActivity;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.OnSwipeListener;
import mymineralcollection.example.org.mymineral.MineralList.MineralListActivity;
import mymineralcollection.example.org.mymineral.MineralList.MyListActivity;
import mymineralcollection.example.org.mymineral.R;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivityFullscreen_old extends Activity implements View.OnTouchListener, OnSwipeListener.OnEventListener, View.OnClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("");
                getActionBar().setIcon(
                        new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_activity_fullscreen);

        Methods.checkLoadActivityState(this);

        //Log.d("startActivity","onCreate");

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        Button addMineral_button = (Button) findViewById(R.id.addMineral_button);
        addMineral_button.setOnClickListener(this);
        Button viewMineral_button = (Button) findViewById(R.id.viewMineral_button);
        viewMineral_button.setOnClickListener(this);
        Button editMineral_button = (Button) findViewById(R.id.editMineral_button);
        editMineral_button.setOnClickListener(this);


        //LinearLayout editMineral_button = (LinearLayout) findViewById(R.id.fullscreen_content);

        //main_linearLayout

        OnSwipeListener mSwipeDir = new OnSwipeListener();
        mSwipeDir.setOnEventListener(this);
        mDetector = new GestureDetectorCompat(this, mSwipeDir);

        mContentView.setOnTouchListener(this);
        mContentView.setOnClickListener(this);

//
        //// Set up the user interaction to manually show or hide the system UI.
        //mContentView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        //toggle();
        //    }
        //});

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        //Log.e("AddMineralSpecific", "SAVE_INDEX       : "+ MyConstant.UserDataArray[MyConstant.SAVE_INDEX].getJsonKey());
        //Log.e("AddMineralSpecific", "CARD_COLOR_INDEX : "+ MyConstant.UserDataArray[MyConstant.CARD_COLOR_INDEX].getJsonKey());

        //public static int CARD_COLOR_INDEX = UserDataArray.length-1;
        //public static int SAVE_INDEX = UserDataArray.length;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);


        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(0, 1, 1, "New");
        //menu.add(0, 2, 2, "Create");
        //menu.add(0, 3, 3, "Open");
        //menu.add(0, 4, 4, "Delete");
        //menu.add(0, 5, 5, "Exit");

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.start_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (1):
                //----//
                return true;
            case (2):
                //---//
                return true;
            case (3):
                //---//
                return true;
            case (4):
                //---//
                return true;
            case (5):
                //finish();
                return true;

        }
        return false;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mDetector.onTouchEvent(motionEvent);
        return true;
    }


    @Override
    public void onSwipe(OnSwipeListener.Direction direction) {
        Log.d("startActivity",">>> " + direction);

        OnSwipeListener.Direction down = OnSwipeListener.Direction.down;
        OnSwipeListener.Direction up = OnSwipeListener.Direction.up;

        if(direction == down){
            show();
        }
        //else if(direction == up){
        //    hide();
        //}

    }

    @Override
    public void onSwipeOnDown(MotionEvent e) {
        Log.d("startActivity", ">>> " + "fullscreen_content: "+mVisible);
        if(mVisible) hide();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addMineral_button): {

                Log.d("startActivity", ">>> " + "addMineral_button");

                //Intent myIntent = new Intent(StartActivityFullscreen.this, AddMineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //StartActivityFullscreen.this.startActivity(myIntent);

                AddMineralData.initEverything();

                Intent myIntent = new Intent(this, AddMineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StartActivityFullscreen_old.this.startActivityForResult(myIntent, 1);

                break;
            }
            case (R.id.viewMineral_button): {

                Log.d("startActivity", ">>> " + "viewMineral_button");

                Intent myIntent = new Intent(this, MineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StartActivityFullscreen_old.this.startActivity(myIntent);


                break;
            }
            case (R.id.editMineral_button): {

                Log.d("startActivity", ">>> " + "editMineral_button");

                //AddMineralData.initEverything();
//
                //Intent myIntent = new Intent(this, AddMineralSpecificInfoActivity.class);
                ////myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ////StartActivityFullscreen.this.startActivity(myIntent);
//
                ////Intent myIntent = new Intent(this, DisplayMineralActivity.class);
//
                //myIntent.putExtra(StartActivityFullscreen.class.toString(),true);
//
                //startActivity(myIntent);

                Intent myIntent = new Intent(this, MyListActivity.class);


                myIntent.putExtra(ImageCropperFullscreenActivity.class.toString(),true);

                //myIntent.putExtra("RowType", recycleBen.getViewType());
                //myIntent.putExtra("DialogType", recycleBen.getDialogType());

                //myIntent.putExtra("CardOptions", recycleBen.getCardOptions());
                //myIntent.putExtra("ColorObj", recycleBen.getColorObj());

                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(myIntent);


                break;
            }
            case R.id.fullscreen_content:{




                break;
            }
        }
    }
}
