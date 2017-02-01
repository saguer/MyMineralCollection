package mymineralcollection.example.org.myviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Santiago on 12/6/2016.
 */
public class PriceView extends RecyclerView.ViewHolder {

    private TextView tittleTextView;

    public PriceView(View v) {
        super(v);
        this.tittleTextView = (TextView) v.findViewById(R.id.tittleTextView);
    }

    public void setData(Context context, final RecyclerView.ViewHolder holder, RecycleBen bean){

        int color = bean.getCardOptions().getTittleTextColor();

        if(color != -1){
            ((PriceView)holder).tittleTextView.setTextColor(ContextCompat.getColor(context, color));
        }

        ((PriceView)holder).tittleTextView.setText((String)bean.getDataObj());
    }
}
