package mymineralcollection.example.org.mymineral.MineralList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.myviews.DividerItemDecoration;
import mymineralcollection.example.org.myviews.MyCustomLayoutManager;
import mymineralcollection.example.org.myviews.RecycleBen;
import mymineralcollection.example.org.myviews.RecycleDataAdapter;
import mymineralcollection.example.org.myviews.ViewType;
import mymineralcollection.example.org.myviews.Views.ExpandableViewHolder;

/**
 * Created by Santiago on 12/17/2016.
 */
public class RecyclerFragment extends Fragment implements RecycleDataAdapter.mListener {

    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerFragment_interface mInterface;

    public void setFragId(int fragId) {
        this.fragId = fragId;
        if(mInterface != null) mInterface.updateCurrentFragmentVar(fragId);
    }

    private int fragId;
    final float MILLISECONDS_PER_INCH = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.recycler_layout_fragment, container, false);
        context = getActivity();

        try {
            mInterface = (RecyclerFragment_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        //----------------
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayoutManager =  new MyCustomLayoutManager(context,MILLISECONDS_PER_INCH);
        recyclerView.setLayoutManager(linearLayoutManager);
        //---------------

        /**
         * Load the data when loading and changing between fragments.
         */
        switch (fragId) {
            case MyListActivity.DISPLAY_MINERAL_ID: {
                Log.e("Color", "setRecycler_interface: DISPLAY_MINERAL_ID");
                setRecyclerView(mInterface.setRecycler_interface(fragId));
                break;
            }
            case MyListActivity.ADD_MINERAL_ID: {
                Log.e("Color", "setRecycler_interface: ADD_MINERAL_ID");
                setRecyclerView(mInterface.setRecycler_interface(fragId));
                break;
            }
        }

        mInterface.updateCurrentFragmentVar(fragId);

        Log.e("MineralListActivity", "setRecyclerView: setRecyclerView");

        return v;
    }
    RecycleDataAdapter recycleViewAdapter;

    public void setRecyclerView(ArrayList<RecycleBen> arrayList){

        recycleViewAdapter = new RecycleDataAdapter(context, arrayList);
        recycleViewAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    public void updateRecyclerView(ArrayList<RecycleBen> arrayList){
        recycleViewAdapter.setData(arrayList);
        recycleViewAdapter.notifyDataSetChanged();
    }



    @Override
    public void onRecycleItemButtonPress(int position, RecycleBen recycleBen) {
        mInterface.fragOnButtonPress(position, recycleBen);
    }

    @Override
    public void setToolBarColor(int position, RecycleBen bean) {
        mInterface.fragColorUpdateData(position, bean);
    }

    @Override
    public void onScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void onCollapseAll(int _position) {

        final int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; ++i) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);

            switch (holder.getItemViewType()) {
                case ViewType.EXPANDABLE_VIEW: {
                    ((ExpandableViewHolder)holder).expandableLayout.collapse();
                }
            }
        }
    }

    @Override
    public void onEraseAtPos(int position) {
        Toast.makeText(context, "Long click! \n RecyclerFragment", Toast.LENGTH_SHORT).show();
    }


    public interface RecyclerFragment_interface {
        ArrayList<RecycleBen> setRecycler_interface(int fragId);
        void fragOnButtonPress(int _position, RecycleBen recycleBen);

        void updateCurrentFragmentVar(int currFrag);

        void fragColorUpdateData(int position, RecycleBen bean);

    }
}
