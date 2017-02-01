package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralPropResultHtmlText;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralPropResultList;
import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragmentItem;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/25/2016.
 */
class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<FragmentItem> fragArrayList;
    private Context context;
    private int position = 0;

    public PagerAdapter(FragmentManager fm, Activity context, ArrayList<FragmentItem> _fragArrayList) {
        super(fm);
        this.context = context;
        this.fragArrayList = _fragArrayList;
    }

    @Override
    public int getCount() {
        return fragArrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragArrayList.get(position).getFrag();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return fragArrayList.get(position).getTitle();
    }


    public View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(fragArrayList.get(position).getTitle());
        return tab;
    }

    public void updateTextArrayList(ArrayList<LabelObject> items) {

        FragMineralPropResultList fragMineralPropResultList =
        (FragMineralPropResultList) fragArrayList.get(AddMineralListActivity.FRAG_LIST_RESULT_ID).getFrag();

        fragMineralPropResultList.updateTextArrayList(items);


        //// TODO: 1/30/2017 - uncomment if the text tab is wanted
        //FragMineralPropResultHtmlText fragMineralPropResultHtmlText =
        //(FragMineralPropResultHtmlText) fragArrayList.get(AddMineralListActivity.FRAG_TEXT_RESULT_ID).getFrag();

        //fragMineralPropResultHtmlText.updateTextArrayList(items);

    }

    public void removeItemAt(ArrayList<LabelObject> ITEMS, int _index) {
        //FragmentItem _frag = fragArrayList.get(this.position);
//
        //if(_frag.getFragID() == AddMineralListActivity.FRAG_LIST_RESULT_ID) {
        //    FragMineralPropResultList fragMineralPropResultList = (FragMineralPropResultList) _frag.getFrag();
//
        //    if(fragMineralPropResultList != null) {
        //        if (fragMineralPropResultList.isVisible()) {
        //            fragMineralPropResultList.adapter.removeItemAt(_index);
        //        }
        //    }
        //}

        FragMineralPropResultList fragMineralPropResultList =
                (FragMineralPropResultList) fragArrayList.get(AddMineralListActivity.FRAG_LIST_RESULT_ID).getFrag();

        //In here we actually remove the item from the adapter.
        fragMineralPropResultList.adapter.removeItemAt(_index);

        FragMineralPropResultHtmlText fragMineralPropResultHtmlText =
                (FragMineralPropResultHtmlText) fragArrayList.get(AddMineralListActivity.FRAG_TEXT_RESULT_ID).getFrag();

        //In here we set the new text.
        fragMineralPropResultHtmlText.updateTextArrayList(ITEMS);

    }

    public void setPosition(int _position) {
        this.position = _position;

        Log.e("AddMineralListActivity", "INDEX: " + this.position);
    }
}