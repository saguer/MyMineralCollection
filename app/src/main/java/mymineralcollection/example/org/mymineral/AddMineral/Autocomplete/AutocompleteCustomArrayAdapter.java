package mymineralcollection.example.org.mymineral.AddMineral.Autocomplete;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mymineralcollection.example.org.mymineral.Class.MyObject;
import mymineralcollection.example.org.mymineral.R;


public class AutocompleteCustomArrayAdapter extends ArrayAdapter<MyObject> {

    final String TAG = "AutocompleteCustomArrayAdapter.java";

    Context mContext;
    int layoutResourceId;
    MyObject data[] = null;

    public AutocompleteCustomArrayAdapter(Context mContext, int layoutResourceId, MyObject[] data) {
        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{

            /*
             * The convertView argument is essentially a "ScrapView" as described is Lucas post
             * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
             * It will have a non-null value when ListView is asking you recycle the row layout.
             * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
             */
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            MyObject objectItem = data[position];

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
            String _text = objectItem.getMineralName();
            int _index = objectItem.getId();

            textViewItem.setText(_text);

            // in case you want to add some style, you can do something like:
            if(objectItem.getInPersonaldb() && objectItem.getSearchSuccess()){
                //textViewItem.setBackgroundColor(Color.GREEN);
                textViewItem.setTextColor(Color.GREEN);
                return convertView;
            }

            if(objectItem.getPrevSearch() && objectItem.getSearchSuccess()){
                //textViewItem.setBackgroundColor(Color.YELLOW);
                textViewItem.setTextColor(Color.YELLOW);
                return convertView;
            }

            if(objectItem.getPrevSearch() && !objectItem.getSearchSuccess()){
                //textViewItem.setBackgroundColor(Color.RED);
                textViewItem.setTextColor(Color.RED);
                return convertView;
            }

                //textViewItem.setBackgroundColor(Color.LTGRAY);

            return convertView;
            //objectItem.getId();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;

    }
}