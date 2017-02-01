package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.FragMineralPropResultList;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.R;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements MyViewHolder.MyViewHolderClicks {
    private ArrayList<LabelObject> new_mDataset;

    MyViewHolder vh;
    public MyAdapterInterface mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset, MyAdapterInterface listener) {
        mListener = listener;
    }

    public MyAdapter(ArrayList<LabelObject> mDataset, FragMineralPropResultList listener) {
        new_mDataset = mDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // createOrUpdate a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new MyViewHolder(v,this);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Get element from your data set at this position
        // Replace the contents of the view with that element
        // Clear the ones that won't be used

        holder.mineral_name_TextView.setText(new_mDataset.get(position).getMineralName());
        holder.mineral_extra_textView.setText(new_mDataset.get(position).getExtra());

        String html = new_mDataset.get(position).getFormula();
        //Log.e("AddMineralListActivity", "TEST: " + html);

        if(html != null)
        holder.mineral_formula_textView.setHtml(new_mDataset.get(position).getFormula());

        if(new_mDataset.get(position).isQueueDone()){
            holder.item_progressBar.setVisibility(View.GONE);
        }else{
            holder.item_progressBar.setVisibility(View.VISIBLE);
        }

        holder.mItemRelativeLayout.setId(position);
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return new_mDataset.size();
    }

    @Override
    public void itemOnClickListener(View v) {
        mListener.itemOnClickListener(v);
    }

    //works
    public void removeItemAt(int position) {

        if(position < new_mDataset.size()) {
            new_mDataset.remove(position);
        }else{
            new_mDataset.clear();
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, new_mDataset.size());

    }

    public void addItem(LabelObject _obj){
        new_mDataset.add(_obj);
        notifyItemInserted(new_mDataset.size() - 1);
    }

    public void clearDataSet(){
        new_mDataset.clear();
//
        notifyDataSetChanged();
    }

    //public void setDataset(int i, String text) {
    //    if(mDataset.size() > i) {
    //        mDataset.set(i, text);
    //    }else{
    //        mDataset.add(text);
    //    }
    //    notifyDataSetChanged();
    //}

    public void setDataset(int i, LabelObject _obj) {

        if(new_mDataset.size() > i) {
            new_mDataset.set(i, _obj);
        }else{
            new_mDataset.add(_obj);
        }

        notifyDataSetChanged();
    }


    public int sizeDataset() {
        return new_mDataset.size();
    }



    public interface MyAdapterInterface {
        void itemOnClickListener(View view);
    }

}