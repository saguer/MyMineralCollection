package mymineralcollection.example.org.myviews.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import mymineralcollection.example.org.myviews.R;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 12/6/2016.
 */
public class ExpandableViewHolder  extends RecyclerView.ViewHolder {

    public TextView description_textView;
    public HtmlTextView data_textView;
    public ImageView expand_imageView;

    public ImageView expand_arrow_imageView;

    public FrameLayout separationLineFragmentLayout;
    public LinearLayout item_linearLayout;

    private SparseBooleanArray expandState = new SparseBooleanArray();
    /**
     * You must use the ExpandableLinearLayout in the recycler view.
     * The ExpandableRelativeLayout doesn't work.
     */
    public ExpandableLinearLayout expandableLayout;

    public ExpandableViewHolder(View v) {
        super(v);
        description_textView = (TextView) v.findViewById(R.id.textView);
        data_textView = (HtmlTextView) v.findViewById(R.id.data_textview);
        expand_imageView = (ImageView) v.findViewById(R.id.expand_imageView);
        separationLineFragmentLayout = (FrameLayout) v.findViewById(R.id.separationLineFragmentLayout);
        separationLineFragmentLayout.setVisibility(View.INVISIBLE);
        expand_arrow_imageView = (ImageView) v.findViewById(R.id.expand_arrow_imageView);
        expand_imageView.setVisibility(View.INVISIBLE);

        expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);

        item_linearLayout = (LinearLayout) v.findViewById(R.id.item_linearLayout);

        //
    }


    private boolean showSeparationLine = false;
    private boolean showExpand = false;

    public void setData(final Context context, final RecyclerView.ViewHolder holder, final RecycleBen bean, final int position, SparseBooleanArray expandState) {
        holder.setIsRecyclable(true);

        this.expandState = expandState;

        final int animationSpeed = bean.getCardOptions().getAnimationSpeed();

        String description = bean.getDescription();
        String dataText = (String) bean.getDataObj();

        int tittleTextColor = bean.getCardOptions().getTittleTextColor();
        int tittleTextAreaColor = bean.getCardOptions().getTittleTextAreaColor();
        int expandableTextAreaColor = bean.getCardOptions().getExpandableTextAreaColor();
        int separationLineColor = bean.getCardOptions().getSeparationLineColor();

        showExpand  = bean.getCardOptions().isExpandEnable();

        if (description == null) description = "empty";
        if (dataText == null) dataText = "empty";

        //Log.d("MineralListActivity", "description : "+description);
        //Log.d("MineralListActivity", "dataText::::: "+dataText);

        ((ExpandableViewHolder)holder).description_textView.setText(description);
        ((ExpandableViewHolder)holder).data_textView.setHtml(dataText);

        if(tittleTextColor != -1) ((ExpandableViewHolder)holder).description_textView.setTextColor(ContextCompat.getColor(context, tittleTextColor));
        if(tittleTextAreaColor != -1) ((ExpandableViewHolder)holder).itemView.setBackgroundColor(ContextCompat.getColor(context, tittleTextAreaColor));
        if(expandableTextAreaColor != -1) ((ExpandableViewHolder)holder).expandableLayout.setBackgroundColor(ContextCompat.getColor(context, expandableTextAreaColor));

        if(separationLineColor != -1){
            showSeparationLine = true;
            ((ExpandableViewHolder)holder).separationLineFragmentLayout.setBackgroundColor(ContextCompat.getColor(context, separationLineColor));
        }

        if(this.expandState.get(position)){
            expand_imageView.setVisibility(View.VISIBLE);

            showCollapse(holder, 0);

            ((ExpandableViewHolder) holder).expand_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mOnEventListener.expandableOnLaunchDisplayActivity(position,bean);
                }
            });
        }else {
            expand_imageView.setVisibility(View.INVISIBLE);
        }

        //((ExpandableViewHolder)holder).expandableLayout.setDuration(MineralListActivity.ANIMATION_SPEED);
        ((ExpandableViewHolder)holder).expandableLayout.setDuration(animationSpeed);
        ((ExpandableViewHolder)holder).expandableLayout.setInRecyclerView(true);
        //((ExpandableViewHolder)holder).expandableLayout.setInterpolator(bean.interpolator);
        ((ExpandableViewHolder)holder).expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));

        ((ExpandableViewHolder)holder).expandableLayout.setExpanded(this.expandState.get(position));
        ((ExpandableViewHolder)holder).expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {
                ((ExpandableViewHolder)holder).expandableLayout.initLayout();//<< looks like it's preventing a bug that
                ExpandableViewHolder.this.expandState.put(position, true);
                //Todo: fix this. or add a simple arrow fade

                mOnEventListener.expandableOnCollapseAll(position);

                showCollapse(holder, animationSpeed);

                if(showExpand) {
                    ((ExpandableViewHolder) holder).expand_imageView.animate()
                            //.translationY(holder.expand_imageView.getHeight())
                            .alpha(1.0f)
                            .setDuration(animationSpeed)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ((ExpandableViewHolder) holder).expand_imageView.setVisibility(View.VISIBLE);

                                    ((ExpandableViewHolder) holder).expand_imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                            mOnEventListener.expandableOnLaunchDisplayActivity(position,bean);
                                        }
                                    });
                                }
                                //public void onAnimationStart(Animator animation) {
                                //    super.onAnimationEnd(animation);
                                //    holder.expand_imageView.setVisibility(View.INVISIBLE);
                                //}

                            });
                }

                if(showSeparationLine) {
                    ((ExpandableViewHolder) holder).separationLineFragmentLayout.animate()
                            //.translationY(holder.expand_imageView.getHeight())
                            .alpha(1.0f)
                            .setDuration(animationSpeed)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ((ExpandableViewHolder) holder).separationLineFragmentLayout.setVisibility(View.VISIBLE);

                                }
                            });
                }
            }

            @Override
            public void onPreClose() {
                ExpandableViewHolder.this.expandState.put(position, false);
                //Todo: fix this. or add a simple arrow fade
                //createRotateAnimator(holder.buttonLayout, 180f, 0f).start();

                //((ExpandableViewHolder)holder).expand_arrow_imageView.animate()
                //        .rotation(0)
                //        .setDuration(animationSpeed)
                //        .start();

                showExpand( holder, animationSpeed);

                if(showExpand) {
                    ((ExpandableViewHolder) holder).expand_imageView.animate()
                            //.translationY(holder.expand_imageView.getHeight())
                            .alpha(0.0f)
                            .setDuration(animationSpeed)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ((ExpandableViewHolder) holder).expand_imageView.setVisibility(View.INVISIBLE);

                                    ((ExpandableViewHolder) holder).expand_imageView.setOnClickListener(null);

                                }
                            });
                }

                if(showSeparationLine) {
                    ((ExpandableViewHolder) holder).separationLineFragmentLayout.animate()
                            //.translationY(holder.expand_imageView.getHeight())
                            .alpha(0.0f)
                            .setDuration(animationSpeed)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ((ExpandableViewHolder) holder).separationLineFragmentLayout.setVisibility(View.INVISIBLE);

                                }
                            });
                }

            }

            @Override
            public void onOpened() {
                mOnEventListener.expandableOnScrollToPosition(position);
                //holder.expand_imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onClosed() {
            }
        });

        //holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        ((ExpandableViewHolder)holder).expand_arrow_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(((ExpandableViewHolder)holder).expandableLayout);
            }
        });

        ((ExpandableViewHolder)holder).item_linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnEventListener.expandableOnEraseAtPos(position);
                return true;
            }
        });

    }

    /**
     * @param holder
     * @param animationSpeed
     */
    private void showExpand(RecyclerView.ViewHolder holder, int animationSpeed){
        ((ExpandableViewHolder)holder).expand_arrow_imageView.animate()
                .rotation(0)
                .setDuration(animationSpeed)
                .start();
    }

    /**
     * @param holder
     * @param animationSpeed
     */
    private void showCollapse(RecyclerView.ViewHolder holder, int animationSpeed){
        ((ExpandableViewHolder)holder).expand_arrow_imageView.animate()
                .rotation(180)
                .setDuration(animationSpeed)
                .start();
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    private mListener mOnEventListener;

    public void setExpandableOnClickListener(mListener listener) {
        mOnEventListener = listener;
    }

    public interface mListener {
        void expandableOnLaunchDisplayActivity(int position, RecycleBen recycleBen);
        void expandableOnScrollToPosition(int position);
        void expandableOnCollapseAll(int position);
        void expandableOnEraseAtPos(int position);
    }
}