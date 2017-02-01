package mymineralcollection.example.org.myviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mymineralcollection.example.org.myviews.ColorView.ColorObject;
import mymineralcollection.example.org.myviews.ColorView.ColorSliderViewHolder;
import mymineralcollection.example.org.myviews.Views.ExpandableViewHolder;
import mymineralcollection.example.org.myviews.Views.HtmlExpandableViewHolder;
import mymineralcollection.example.org.myviews.Views.SimpleImage;
import mymineralcollection.example.org.myviews.Views.SimpleImageWithText;
import mymineralcollection.example.org.myviews.Views.SimpleText;

/**
 * Created by Santiago on 12/4/2016.
 */
public class RecycleDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ExpandableViewHolder.mListener, ButtonView.mListener, ColorSliderViewHolder.mListener, HtmlExpandableViewHolder.mListener {

    private Context mContext;

    public RecycleDataAdapter(Context mContext, ArrayList<RecycleBen> data, GetColorArray toolbarColor) {
        this.mContext = mContext;
        this.data = data;

        //this.mColorObj = toolbarColor.getmColorObjArray();

        colorNumberarray = mContext.getResources().getIntArray(R.array.colorNumberList);
        colorNamearray = mContext.getResources().getStringArray(R.array.colorNameList);
    }

    //MineralList.class
    public RecycleDataAdapter(Context mContext, ArrayList<RecycleBen> data) {
        this.mContext = mContext;
        this.data = data;

        //this.mColorObj = null;

        colorNumberarray = mContext.getResources().getIntArray(R.array.colorNumberList);
        colorNamearray = mContext.getResources().getStringArray(R.array.colorNameList);
    }

    public void setData(ArrayList<RecycleBen> _data) {
        data = new ArrayList<>();

        for(RecycleBen test: _data){
            Log.e("Color", "_data: "+test.getJsonKey()+ " - " + test.getDataObj());
        }

        for(RecycleBen beanData: _data){
            //Log.e("Color", "data: "+data.getJsonKey()+ " - " + data.getDataObj());
            data.add(beanData);
        }

        for(RecycleBen test: data){
            Log.e("Color", "data: "+test.getJsonKey()+ " - " + test.getDataObj());
        }


        //this.data = (ArrayList<RecycleBen>)_data.clone();

        //this.data  = new ArrayList<>(_data);

        //this.data = _data;
    }

    public void setData(ArrayList<RecycleBen> data, ArrayList<ColorObject> mColorObj) {
        this.data = data;
        //this.mColorObj = mColorObj;
    }

    public void setData(ArrayList<RecycleBen> data, RecycleBen bean) {
        this.data = data;
        //ColorBeanHistory = bean;
    }

    public void setData(ArrayList<RecycleBen> data, RecycleBen bean, ArrayList<ColorObject> mColorObj) {
        this.data = data;
        //this.mColorObj = mColorObj;
       // ColorBeanHistory = bean;
    }

    private int[] colorNumberarray;
    private String[] colorNamearray;

    public ArrayList<RecycleBen> data;
    private mListener mOnEventListener;
    //private ArrayList<ColorObject> mColorObj = new ArrayList<>();
    //private RecycleBen ColorBeanHistory;

    public void setMyOnClickListener(mListener listener) {
        mOnEventListener = listener;
    }


    public interface mListener {
        void onRecycleItemButtonPress(int position, RecycleBen recycleBen);
        void setToolBarColor(int position, RecycleBen bean);
        void onScrollToPosition(int position);
        void onCollapseAll(int position);
        void onEraseAtPos(int position);
    }


    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType){
            case 1:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_recycle_item, parent, false);
                return new SimpleText(itemView);
            }
            case ViewType.SINGLE_LINE_VIEW:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_item, parent, false);
                return new PriceView(itemView);
            }
            case ViewType.DIVIDER_VIEW:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.divider_item, parent, false);
                return new DividerViewHolder(itemView);
            }
            case ViewType.BUTTON_VIEW:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
                return new ButtonView(itemView);
            }
            case 2:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fsecond_recycle_item, parent, false);
                return new SimpleImage(itemView);
            }
            case 3:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_recycle_item, parent, false);
                return new SimpleImageWithText(itemView);
            }
            case ViewType.EXPANDABLE_VIEW:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                return new ExpandableViewHolder(itemView);
            }
            case ViewType.HTML_EXPANDABLE_VIEW:{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.html_expandable_item, parent, false);
                return new HtmlExpandableViewHolder(itemView);
            }
            case ViewType.COLOR_SLIDER_VIEW:{
                //if(mColorObj == null) return null;
                Log.e("Color", "ViewType.COLOR_SLIDER_VIEW");
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_slider_item_v2, parent, false);
                //return new ColorSliderViewHolder(itemView,mColorObj);
                return new ColorSliderViewHolder(itemView, colorNumberarray, colorNamearray);
            }
            default:{
                Log.e("MineralListActivity", "default");
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                return new SimpleImageWithText(itemView);
            }
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        RecycleBen bean = data.get(position);

        switch (holder.getItemViewType()) {
            case 1: {
                final SimpleText simpleText = (SimpleText) holder;
                simpleText.setData(holder, bean);
                break;
            }
            case 2: {
                final SimpleImage simple_holder = (SimpleImage) holder;
                simple_holder.setData(mContext, holder, bean);
                break;
            }
            case 3: {
                final SimpleImageWithText third_holder = (SimpleImageWithText) holder;
                third_holder.setData(mContext, holder, bean);
                break;
            }
            case ViewType.BUTTON_VIEW: {
                final ButtonView simpleText = (ButtonView) holder;
                simpleText.setData(mContext, holder, bean, position);
                simpleText.setButtonViewOnClickListener(this);
                break;
            }
            case ViewType.SINGLE_LINE_VIEW: {
                final PriceView simpleText = (PriceView) holder;
                simpleText.setData(mContext, holder, bean);
                break;
            }
            case ViewType.DIVIDER_VIEW:{
                final DividerViewHolder dividerView = (DividerViewHolder) holder;
                dividerView.setData(mContext, holder, bean);
                break;
            }
            case ViewType.COLOR_SLIDER_VIEW: {
                Log.e("Color", "bean getDataObj: "+bean.getDataObj());

                mOnEventListener.setToolBarColor(position, bean);

                final ColorSliderViewHolder third_holder = (ColorSliderViewHolder) holder;

                third_holder.setData(mContext, holder, bean, position);
                third_holder.adapter.notifyDataSetChanged();
                third_holder.setExpandableOnClickListener(this);
                break;
            }
            case ViewType.EXPANDABLE_VIEW: {
                final ExpandableViewHolder third_holder = (ExpandableViewHolder) holder;
                int size = data.size();

                SparseBooleanArray expandState = new SparseBooleanArray();
                for (int i = 0; i < size; i++) {
                    // if equal won't collapse item
                    expandState.append(i, (i == bean.cardOptions.getSkipCollapseAtPosition()));
                }

                third_holder.setData(mContext, holder, bean, position, expandState);
                third_holder.setExpandableOnClickListener(this);
                break;
            }
            case ViewType.HTML_EXPANDABLE_VIEW: {
                final HtmlExpandableViewHolder third_holder = (HtmlExpandableViewHolder) holder;
                int size = data.size();

                SparseBooleanArray expandState = new SparseBooleanArray();
                for (int i = 0; i < size; i++) {
                    // if equal won't collapse item
                    expandState.append(i, (i == bean.cardOptions.getSkipCollapseAtPosition()));
                }

                third_holder.setData(mContext, holder, bean, position, expandState);
                third_holder.setExpandableOnClickListener(this);
                break;
            }
        }

    }

    /**
     * The OnClick coming from the ColorPalletViewAdapter
     * @param position
     * @param bean
     */
    @Override
    public void colorItemOnClick(int position, RecycleBen bean) {
        //RecycleBen bean = new RecycleBen(mColorObj.get(position));
        //ColorBeanHistory = bean;
        mOnEventListener.onRecycleItemButtonPress(position, bean);
    }


    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * The OnClick coming from the ButtonView.class
     * @param position
     * @param recycleBen
     */
    @Override
    public void onButtonViewPress(int position, RecycleBen recycleBen) {
        mOnEventListener.onRecycleItemButtonPress(position, recycleBen);
    }

    @Override
    public void expandableOnLaunchDisplayActivity(int position, RecycleBen recycleBen) {
        mOnEventListener.onRecycleItemButtonPress(position, recycleBen);
    }

    @Override
    public void expandableOnScrollToPosition(int position) {
        mOnEventListener.onScrollToPosition(position);
    }

    @Override
    public void expandableOnCollapseAll(int position) {
        mOnEventListener.onCollapseAll(position);
    }

    @Override
    public void expandableOnEraseAtPos(int position) {
        mOnEventListener.onEraseAtPos(position);
    }
}