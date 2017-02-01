package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CompleteList;

import android.content.Context;
import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CreateListComplete_Thread implements Runnable{

    private Elements elements;
    private int[] csv;
    private Context context;
    private int outerIndex;
    private ArrayList<String> _result = new ArrayList<>();

    public CreateListComplete_Thread(Context _context, Elements _elements, int[] _cellsList, int _outerIndex) {

        this.elements = _elements;
        this.context = _context;
        this.csv = _cellsList;
        this.outerIndex = _outerIndex;

        this.delegate = (ThreadResponse)_context;

        Log.e("CreateListComplete", "Thread Started, Outside Index: " + outerIndex + " -> " + csv[0] + "," + csv[1]);
    }

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                generateList();
                Log.e("CreateListComplete", "Thread Done, Outside Index: " + outerIndex + " -> " + csv[0] + "," + csv[1]);
                delegate.thread_CompleteListComplete(_result, outerIndex);
            }
        }.start();
    }

    public void generateList(){

        int startNum =csv[0];
        int endNum = csv[1];

        for (int i = startNum; i < endNum; i++){
            Element ele = elements.get(i);
            if(ele != null) {
                String _text = ele.text();
                if (!_text.equalsIgnoreCase("here")) {
                    _result.add(_text);
                    //Log.d("CreateListComplete", "Outside Index: " + outerIndex + " - Inside Index: " + index + " -> " +_text);
                }
            }
        }
    }

    public ThreadResponse delegate = null;

    public interface ThreadResponse {
        void thread_CompleteListComplete(ArrayList<String> result, int index);
    }


}
