package mymineralcollection.example.org.myviews;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Santiago on 12/6/2016.
 */
public class DividerViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout separationLineLinearLayout;

    public DividerViewHolder(View v) {
        super(v);

        this.separationLineLinearLayout = (LinearLayout) v.findViewById(R.id.separationLineLinearLayout);
    }

    public void setData(Context context, final RecyclerView.ViewHolder holder, RecycleBen bean){

        int separationLineColor = bean.getCardOptions().getSeparationLineColor();

        if(separationLineColor != -1){
            try {
                ((DividerViewHolder) holder).separationLineLinearLayout.setBackgroundColor(ContextCompat.getColor(context, separationLineColor));
            }catch (Resources.NotFoundException e){
                ((DividerViewHolder) holder).separationLineLinearLayout.setBackgroundColor(separationLineColor);
            }
        }
    }
}
