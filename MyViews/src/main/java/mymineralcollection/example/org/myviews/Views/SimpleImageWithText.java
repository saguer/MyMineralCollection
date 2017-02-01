package mymineralcollection.example.org.myviews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.UnknownHostException;

import mymineralcollection.example.org.myviews.R;
import mymineralcollection.example.org.myviews.RecycleBen;


/**
 * Created by Santiago on 12/6/2016.
 */
public class SimpleImageWithText extends  RecyclerView.ViewHolder {
    TextView third_data_tv;
    ImageView third_iv;
    ProgressBar third_pb;

    public SimpleImageWithText(View v) {
        super(v);
        this.third_data_tv = (TextView) v.findViewById(R.id.third_data_tv);
        this.third_iv = (ImageView) v.findViewById(R.id.third_iv);
        this.third_pb = (ProgressBar)v.findViewById(R.id.third_pb);
    }

    public void setData(Context context, final RecyclerView.ViewHolder holder, RecycleBen bean){
        //((SimpleImageWithText)holder).third_data_tv.setText(bean.getName());

        ((SimpleImageWithText)holder).third_pb.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(bean.getImage_url())
                .fitCenter()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (e instanceof UnknownHostException)
                            ((SimpleImageWithText)holder).third_pb.setVisibility(View.VISIBLE);

                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ((SimpleImageWithText)holder).third_pb.setVisibility(View.GONE);
                        ((SimpleImageWithText)holder).third_iv.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(((SimpleImageWithText)holder).third_iv);
    }
}
