package mymineralcollection.example.org.myviews.ColorView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import mymineralcollection.example.org.myviews.MyCustomLayoutManager;
import mymineralcollection.example.org.myviews.R;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 12/8/2016.
 */
public class ColorSliderViewHolder extends RecyclerView.ViewHolder implements ColorPalletViewAdapter.ColorClick {

    public TextView description_textView;
    public HtmlTextView data_textView;

    public FrameLayout separationLineFragmentLayout;

    public ColorPalletViewAdapter adapter;

    private SparseBooleanArray expandState = new SparseBooleanArray();

    RecyclerView recycler;
    private LinearLayoutManager linearLayoutManager;

    public ColorSliderViewHolder(View v) {
        super(v);
        description_textView = (TextView) v.findViewById(R.id.textView);
        data_textView = (HtmlTextView) v.findViewById(R.id.data_textview);
        //expand_imageView = (ImageView) v.findViewById(R.id.expand_imageView);
        //expand_imageView.setVisibility(View.INVISIBLE);
        separationLineFragmentLayout = (FrameLayout) v.findViewById(R.id.separationLineFragmentLayout);

        recycler = (RecyclerView) v.findViewById(R.id.color_recycler_view);

        recycler.setHasFixedSize(false);//was true
    }

    final float MILLISECONDS_PER_INCH = 10;

    private int[] colorNumberArray;
    private String[] colorNameArray;



    public ColorSliderViewHolder(View v, int[] _colorNumberArray, String[] _colorNameArray) {
        super(v);

        this.colorNumberArray = _colorNumberArray;
        this.colorNameArray = _colorNameArray;

        description_textView = (TextView) v.findViewById(R.id.textView);
        data_textView = (HtmlTextView) v.findViewById(R.id.data_textview);
        separationLineFragmentLayout = (FrameLayout) v.findViewById(R.id.separationLineFragmentLayout);

        recycler = (RecyclerView) v.findViewById(R.id.color_recycler_view);

        recycler.setHasFixedSize(false);//was true
    }

    public void setData(Context context, RecyclerView.ViewHolder holder, RecycleBen bean, int viewPosition) {
         holder.setIsRecyclable(true);

        linearLayoutManager = new MyCustomLayoutManager(context,MILLISECONDS_PER_INCH);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setReverseLayout(false);

        recycler.setLayoutManager(linearLayoutManager);

        adapter = new ColorPalletViewAdapter(colorNumberArray, colorNameArray, bean, viewPosition);
        adapter.setColorClickListener(this);

        recycler.setAdapter(adapter);
        //Log.d("MineralListActivity", "getDataObj() : "+bean.getColorName());

        //recycler.smoothScrollToPosition(bean.getColorIndex());
        recycler.scrollToPosition(bean.getColorIndex()-2);

        String description = bean.getDescription();

        int tittleTextColor = bean.getCardOptions().getTittleTextColor();
        int tittleTextAreaColor = bean.getCardOptions().getTittleTextAreaColor();
        //int expandableTextAreaColor = bean.getCardOptions().getExpandableTextAreaColor();
        int separationLineColor = bean.getCardOptions().getSeparationLineColor();

        Log.d("Color", "----------------- bean.getColorName   : "+bean.getColorName());

        ((ColorSliderViewHolder)holder).description_textView.setText(description);

        if(tittleTextColor != -1) ((ColorSliderViewHolder)holder).description_textView.setTextColor(ContextCompat.getColor(context, tittleTextColor));
        if(tittleTextAreaColor != -1) ((ColorSliderViewHolder)holder).itemView.setBackgroundColor(ContextCompat.getColor(context, tittleTextAreaColor));


        if(separationLineColor != -1){
            ((ColorSliderViewHolder)holder).separationLineFragmentLayout.setBackgroundColor(ContextCompat.getColor(context, separationLineColor));
        }

    }


    private mListener mOnEventListener;

    public void setExpandableOnClickListener(mListener listener) {
        mOnEventListener = listener;
    }

    @Override
    public void ColorItemOnClickListener(int position, RecycleBen bean) {
        mOnEventListener.colorItemOnClick(position,bean);
    }


    @Override
    public void unSelectAll() {
        onCollapseAll();
        Log.e("Color","unSelectAll");
    }


    public interface mListener {
        void colorItemOnClick(int position, RecycleBen bean);
    }

    public void onCollapseAll() {

        final int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
            ColorPalletViewHolder holder = (ColorPalletViewHolder) recycler.findViewHolderForAdapterPosition(i);

            if (holder == null) {
                Log.e("MineralListActivity","holder == null -> "+i);
            } else {
                holder.selectorIndicator.setVisibility(View.INVISIBLE);
            }
        }
    }

}