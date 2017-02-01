package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.AddMineral.ShadowTransformer;
import mymineralcollection.example.org.mymineral.DataWrappers.DataWrapper_LabelObject;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/15/2016.
 */
public class FragMineralNameCards extends Fragment implements ClickableViewPager.OnItemClickListener, ViewTreeObserver.OnGlobalLayoutListener {
    public FragMineralNameCards() {
    }

    private Context context;

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private View view;

    private FragMineralNameCards_interface mInterface;

    private ClickableViewPager mViewPager;
    public AddPropertyMineralCardPagerAdapter mCardAdapter;
    public ShadowTransformer mCardShadowTransformer;

    private ArrayList<LabelObject> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.mineral_name_cards_fragment, container, false);
        this.view = v;
        context = getActivity();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mViewPager = (ClickableViewPager) v.findViewById(R.id.viewPager);

        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(this);
        try {
            mInterface = (FragMineralNameCards_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }


        mCardAdapter = new AddPropertyMineralCardPagerAdapter(context);

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setOnItemClickListener(this);


        Bundle args = getArguments();

        if(args != null){
            try {
                DataWrapper_LabelObject dw = (DataWrapper_LabelObject) args.get("itemList");
                //TODO: prevent null
                if(dw != null) {
                    itemList = dw.getLabelObject();
                }
            }catch (ClassCastException e){
                //mAutoLabel.setSettings(setDefaultSettings());
            }
        }

        Log.d("addProp", "onCreateView");



        return v;
    }



    @Override
    public void onItemClick(int position) {
        Log.d("addProp", "position: "+position);

        mInterface.ItemClicked_FragMineralNameCards_interface(position);
    }

    @Override
    public void onGlobalLayout() {

        if(itemList != null) {
            for (LabelObject _labelObj : itemList) {

                String mineralName = _labelObj.getMineralName();
                //String extras = _labelObj.getExtra();
                //int index = _labelObj.getIndex();
                //Log.d("addProp", "- - - - - - - -");
                //Log.d("addProp", "mineralName: " + mineralName);
                //Log.d("addProp", "extras: " + extras);
                //Log.d("addProp", "extras: " + index);
                mCardAdapter.addCard(mineralName);
                mCardAdapter.notifyDataSetChanged();
                mCardShadowTransformer.enableScaling(true);
            }
        }
        if (Build.VERSION.SDK_INT < 16) {
            mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

    }


    public interface FragMineralNameCards_interface {
        void ItemClicked_FragMineralNameCards_interface(int position);

    }
}
