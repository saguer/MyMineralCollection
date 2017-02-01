package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Santiago on 10/25/2016.
 */
public class FragmentItem{

    public FragmentItem(String title, Fragment frag) {
        this.frag = frag;
        this.title = title;
    }

    public FragmentItem(int frag_id, String title, Fragment frag) {
        this.fragID = frag_id;
        this.frag = frag;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFrag() {
        return frag;
    }

    public void setFrag(Fragment frag) {
        this.frag = frag;
    }

    private Fragment frag;
    private String title;

    public int getFragID() {
        return fragID;
    }

    private int fragID;


}