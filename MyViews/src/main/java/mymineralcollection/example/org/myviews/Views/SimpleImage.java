package mymineralcollection.example.org.myviews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
public class SimpleImage extends  RecyclerView.ViewHolder {
    ImageView second_data_iv;
    ProgressBar second_pb;

    public SimpleImage(View v) {
        super(v);
        this.second_data_iv = (ImageView) v.findViewById(R.id.second_data_iv);
        this.second_pb = (ProgressBar)v.findViewById(R.id.second_pb);
    }

    public void setData(Context context, final RecyclerView.ViewHolder holder, RecycleBen bean){

        ((SimpleImage)holder).second_pb.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(bean.getImage_url())
                .fitCenter()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (e instanceof UnknownHostException)
                            ((SimpleImage)holder).second_pb.setVisibility(View.VISIBLE);

                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ((SimpleImage)holder).second_pb.setVisibility(View.GONE);
                        ((SimpleImage)holder).second_data_iv.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(((SimpleImage)holder).second_data_iv);
    }
}