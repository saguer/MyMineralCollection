package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/25/2016.
 */
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public CardView mCardView;
    public TextView mineral_name_TextView;
    public TextView mineral_extra_textView;
    public HtmlTextView mineral_formula_textView;
    public ProgressBar item_progressBar;


    public LinearLayout mItemRelativeLayout;
    public MyViewHolderClicks mListener;

    public MyViewHolder(View v, MyViewHolderClicks listener) {
        super(v);
        mListener = listener;
        mCardView = (CardView) v.findViewById(R.id.card_view);

        mineral_name_TextView = (TextView) v.findViewById(R.id.mineral_name_TextView);
        mineral_extra_textView = (TextView) v.findViewById(R.id.mineral_extra_textView);
        mineral_formula_textView = (HtmlTextView) v.findViewById(R.id.mineral_formula_textView);

        item_progressBar = (ProgressBar) v.findViewById(R.id.item_progressBar);

        mItemRelativeLayout = (LinearLayout) v.findViewById(R.id.item_RelativeLayout);
        mItemRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.itemOnClickListener(v);
    }

    public interface MyViewHolderClicks {
        void itemOnClickListener(View caller);
    }
}