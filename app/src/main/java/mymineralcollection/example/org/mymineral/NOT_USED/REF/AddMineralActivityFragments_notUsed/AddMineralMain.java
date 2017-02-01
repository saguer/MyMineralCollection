package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddMineralActivityFragments_notUsed.MineralName.MineralNameFrag;
import mymineralcollection.example.org.mymineral.R;


public class AddMineralMain extends Activity implements
        MainToolBarFrag.ToolBar_interface,
        AddPictureFrag.ToolBar_interface,
        MineralNameFrag.ToolBar_interface,
        MineralInfoFrag.ToolBar_interface{

    MainToolBarFrag toolBarFrag;
    AddPictureFrag addPictureFrag;
    MineralNameFrag addMineralNameFrag;
    MineralInfoFrag addMineralInfoFrag;

    private static final String TOOLBAR_FRAGMENT_MAIN_TAG = "toolbar_main_fragment";
    private static final String TOOLBAR_FRAGMENT_MINERAL_NAME_TAG = "toolbar_mineral_name_fragment";
    private static final String TOOLBAR_FRAGMENT_MINERAL_INFO_TAG = "toolbar_mineral_info_fragment";
    private static final String TOOLBAR_FRAGMENT_MINERAL_PICTURE_TAG = "toolbar_mineral_picture_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mineral_main);

        createFragments();

        toggleToolBarFrag();
        addFragSlideUpDown(R.id.content_fragment, TOOLBAR_FRAGMENT_MINERAL_NAME_TAG, addMineralNameFrag);
    }

    void createFragments(){
        toolBarFrag = new MainToolBarFrag();
        addPictureFrag = new AddPictureFrag();
        addMineralNameFrag = new MineralNameFrag();
        addMineralInfoFrag = new MineralInfoFrag();
    }

    private void toggleToolBarFrag() {
        toggleFragSlideUpDown(R.id.navigation_fragment,TOOLBAR_FRAGMENT_MAIN_TAG,toolBarFrag);
    }
    /*** METHODS THAT ARE USED MORE THAN ONCE **/

    /**
     *
     * @param _fragmentLayout
     * @param _tag
     * @param _frag
     */
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

    private void addFragSlideUpDown(int _fragmentLayout, String _tag, Fragment _frag){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .add(_fragmentLayout, _frag, _tag).addToBackStack(null)
                .addToBackStack(null).commit();
    }

    private void replaceFragSlideUpDown(int _fragmentLayout, String _tag, Fragment _frag){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_up,
                        R.animator.slide_down,
                        R.animator.slide_up,
                        R.animator.slide_down)
                .replace(_fragmentLayout, _frag, _tag)
                .addToBackStack(null)
                .addToBackStack(null).commit();
    }

    @Override
    public void launchMineralPicture_interface() {
        replaceFragSlideUpDown(R.id.content_fragment, TOOLBAR_FRAGMENT_MINERAL_PICTURE_TAG, addPictureFrag);
    }

    @Override
    public void launchMineralInfo_interface() {
        replaceFragSlideUpDown(R.id.content_fragment, TOOLBAR_FRAGMENT_MINERAL_INFO_TAG, addMineralInfoFrag);
    }

    @Override
    public void launchMineralName_interface() {
        replaceFragSlideUpDown(R.id.content_fragment, TOOLBAR_FRAGMENT_MINERAL_NAME_TAG, addMineralNameFrag);
    }
}
