package mymineralcollection.example.org.myviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

/**
 * Created by Santiago on 12/8/2016.
 */
public class ButtonView extends RecyclerView.ViewHolder {

    private Button tittleTextView;


    public ButtonView(View v) {
        super(v);
        this.tittleTextView = (Button) v.findViewById(R.id.tittleButton);
    }

    public void setData(Context context, final RecyclerView.ViewHolder holder, final RecycleBen bean, final int position) {
        holder.setIsRecyclable(true);

        int color = bean.getCardOptions().getTittleTextColor();

        if(color != -1){
            ((ButtonView)holder).tittleTextView.setTextColor(ContextCompat.getColor(context, color));
        }

        ((ButtonView)holder).tittleTextView.setText(bean.getDescription());

        ((ButtonView) holder).tittleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnEventListener.onButtonViewPress(position,bean);
            }
        });
    }

    private mListener mOnEventListener;

    public void setButtonViewOnClickListener(mListener listener) {
        mOnEventListener = listener;
    }

    public interface mListener {
        void onButtonViewPress(int position, RecycleBen recycleBen);
    }

}
