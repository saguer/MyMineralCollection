package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mymineralcollection.example.org.mymineral.AddMineral.CardAdapter;
import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/10/2016.
 */
public class AddPropertyMineralCardPagerAdapter extends PagerAdapter implements CardAdapter, ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    private List<CardView> mViews;
    private List<String> mData;
    private float mBaseElevation;



    public AddPropertyMineralCardPagerAdapter(Context _context) {

        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        //for (int i = 0; i < 5; i++) {
        mData.add("");
        mViews.add(null);
        //}

        //addCard(_context);
    }

    public int getPosition(){
        return position;
    }

    ArrayList<Uri> uriArrayList = new ArrayList<>();

    public void addCard(Uri uri){

        //debug_textView.setText(position);

        uriArrayList.add(uri);

        cardView.setVisibility(View.VISIBLE);

        int targetWidth = MeasuredWidth;
        int targetHeight = MeasuredHeight;


        //Picasso.with(context).load(uri).error(R.drawable.ic_bookmark_24dp)
        //        .resize(targetWidth, targetHeight)// resizes the image to these dimensions (in pixel)
        //        .centerInside()
        //        .into(mineral_imageView);

        mData.add("");
        mViews.add(null);

        //mineral_imageView.setOnClickListener(null);

    }
    public void addCard(String text){

        //debug_textView.setText(position);

        cardView.setVisibility(View.VISIBLE);

        mineralNameTextView.setText(text);


        //Picasso.with(context).load(uri).error(R.drawable.ic_bookmark_24dp)
        //        .resize(targetWidth, targetHeight)// resizes the image to these dimensions (in pixel)
        //        .centerInside()
        //        .into(mineral_imageView);

        mData.add("");
        mViews.add(null);

        //mineral_imageView.setOnClickListener(null);

    }

    public void addCard(){

        //debug_textView.setText(position);

        cardView.setVisibility(View.GONE);

        int targetWidth = MeasuredWidth;
        int targetHeight = MeasuredHeight;

        //Picasso.with(context).load(R.drawable.add_image_grey)
        //        .resize(targetWidth, targetHeight)// resizes the image to these dimensions (in pixel)
        //        .centerInside()
        //        .into(mineral_imageView);
    }


    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    Context context;
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //ImageView mineral_imageView;
    CardView cardView;
    //TextView debug_textView;
    private int position;
    TextView mineralNameTextView;
    @Override
    public Object instantiateItem(ViewGroup container, final int _position) {
        View v = LayoutInflater.from(container.getContext())
                .inflate(R.layout.add_property_page_adapter, container, false);

        context = container.getContext();
        position = _position;
        container.addView(v);
        cardView = (CardView) v.findViewById(R.id.cardView_mineralName);
        cardView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        mineralNameTextView =  (TextView)v.findViewById(R.id.mineralNameTextView);

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);

        mViews.set(position, cardView);

        //----------------
        return v;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    int MeasuredWidth;
    int MeasuredHeight;

    @Override
    public void onGlobalLayout() {

        MeasuredHeight = cardView.getMeasuredHeight();
        MeasuredWidth = cardView.getMeasuredWidth();


        Log.d("Adapter", "- - - - - - - - - - - - - - - - - -");
        Log.d("Adapter", "MeasuredHeight: "+MeasuredHeight);
        Log.d("Adapter", "MeasuredWidth : "+MeasuredWidth);
        Log.d("Adapter", "- - - - - - - - - - - - - - - - - -");

        if(MeasuredHeight>0) {
            cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            cardView.setVisibility(View.GONE);
        }

        //addCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mineral_imageView: {

                //mInterface.addImage_CardPagerAdapter_interface();
            }
        }
    }

    public ArrayList<Uri> getUriArrayList() {
        return uriArrayList;
    }


}
