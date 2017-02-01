package mymineralcollection.example.org.myviews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mymineralcollection.example.org.myviews.R;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 12/6/2016.
 */
public class SimpleText extends RecyclerView.ViewHolder {
    private TextView first_data_tv;

    public SimpleText(View v) {
        super(v);
        this.first_data_tv = (TextView) v.findViewById(R.id.first_data_tv);
    }

    public void setData(final RecyclerView.ViewHolder holder, RecycleBen bean){
        //((SimpleText)holder).first_data_tv.setText(bean.getName());
    }
}
