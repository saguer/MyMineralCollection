package mymineralcollection.example.org.myviews.ColorView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mymineralcollection.example.org.myviews.R;

/**
 * Created by Santiago on 12/8/2016.
 */
public class ColorPalletViewHolder extends RecyclerView.ViewHolder{
    public LinearLayout colorPalletLinearLayout;
    public ImageView selectorIndicator;



    public ColorPalletViewHolder(View v) {
        super(v);

        colorPalletLinearLayout = (LinearLayout) v.findViewById(R.id.colorPalletLinearLayout);
        selectorIndicator = (ImageView) v.findViewById(R.id.selectorIndicator);
    }

}