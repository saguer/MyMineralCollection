package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.SimpleList;

import android.content.Context;
import android.util.Log;

import org.jsoup.select.Elements;

import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.Class.Methods;

public class CreateListSimple_Thread implements Runnable{

    Elements elements;
    int[] csv;

    int index = 0;

    ArrayList<String> _result = new ArrayList<>();

    public CreateListSimple_Thread(Context context, Elements _elements, int[] _cellsList, int _index) {

        this.elements = _elements;

        this.csv = _cellsList;

        this.index = _index;

        this.delegate = (ThreadResponse)context;

        Log.d("QueueTest", "Thread Started: " + index + " -> " + csv[0] + "," + csv[1]);
    }

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {

                generateList();

                Log.d("QueueTest", "Thread Done: " + index + " -> " + csv[0] + "," + csv[1]);
                //databaseH.close();

                delegate.thread_SimpleListComplete(_result,index);
            }
        }.start();
    }

    public void generateList(){

        int startNum =csv[0];
        int endNum = csv[1];


        for (int index = startNum; index < endNum; index++){
            Elements _href = elements.get(index).select("a[href][title]");

            if(!_href.hasClass("mw-redirect")) {
                String _span = elements.get(index).select("span").toString();
                String _text = _href.text();
                if(_span.equalsIgnoreCase("")) {
                    //Log.d("myApp", _href.toString());
                    if(!Methods.removeUndesirableText(_text)){
                        _result.add(_text);
                    }
                }
            }
        }
    }


    public ThreadResponse delegate = null;

    public interface ThreadResponse {
        void thread_SimpleListComplete(ArrayList<String> result, int index);

    }



}
