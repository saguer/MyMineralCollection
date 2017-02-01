package mymineralcollection.example.org.myviews.ColorView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mymineralcollection.example.org.myviews.R;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 12/8/2016.
 */
public class ColorPalletViewAdapter extends RecyclerView.Adapter<ColorPalletViewHolder> {

    private ColorPalletViewHolder vh;

    private RecycleBen bean;
    private int viewPosition;

    private int[] colorNumberArray;
    private String[] colorNameArray;

    // Create new views (invoked by the layout manager)
    @Override
    public ColorPalletViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // createOrUpdate a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_pallet_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ColorPalletViewHolder(v);

        return vh;
    }

    public void setColorClickListener(ColorClick listener) {
        mListener = listener;
    }

    public ColorClick mListener;

    public interface ColorClick {
        void ColorItemOnClickListener(int position, RecycleBen bean);
        void unSelectAll();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ColorPalletViewHolder holder, int position) {
        // Get element from your data set at this position
        // Replace the contents of the view with that element
        // Clear the ones that won't be used

        final int _pos = position;

        holder.colorPalletLinearLayout.setBackgroundColor(colorNumberArray[_pos]);

        //// TODO: 1/22/2017 - 2 ways to do it - first way
        //if(bean.getColorNumber() == colorNumberArray[_pos]){
        //    holder.selectorIndicator.setVisibility(View.VISIBLE);
        //}else{
        //    holder.selectorIndicator.setVisibility(View.INVISIBLE);
        //}

        //// TODO: 1/22/2017 - 2 ways to do it - second way
        if(bean.getColorIndex() == _pos){
            holder.selectorIndicator.setVisibility(View.VISIBLE);
        }else{
            holder.selectorIndicator.setVisibility(View.INVISIBLE);
        }

        holder.colorPalletLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                mListener.unSelectAll();

                Log.e("Color","_pos: "+_pos);

                bean.setColorIndex(_pos);
                bean.setColorNumber(colorNumberArray[_pos]);
                bean.setColorName(colorNameArray[_pos]);

                mListener.ColorItemOnClickListener(viewPosition,bean);

                holder.selectorIndicator.setVisibility(View.VISIBLE);
            }
        });
    }

    public ColorPalletViewAdapter(int[] _colorNumberArray,String[] _colorNameArray, RecycleBen _bean, int _viewPosition) {
        this.bean = _bean;
        this.viewPosition = _viewPosition;

        colorNumberArray = _colorNumberArray;
        colorNameArray = _colorNameArray;
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return colorNumberArray.length;
    }

}