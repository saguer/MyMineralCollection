package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue;

import android.util.Log;

import org.jsoup.select.Elements;

import java.util.List;

import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.SimpleList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.QueueObjList;

/**
 * Created by Santiago on 1/29/2017.
 */
public class SimpleList {

    public SimpleList(SimpleList_AsyncTask.LoadURL_AsyncResponse _delegate, Priority priority, String PriorityName, Elements _elements, List<int[]> _cellsList, QueueObjList _obj) {
        this.delegate = _delegate;
        this.priority = priority;
        this.priorityName = PriorityName;
        this.elements = _elements;

        this.obj = _obj;

        this.csv = _cellsList.get(_obj.getIndex());

        cellsListSize = _cellsList.size();

        int[] csv =_cellsList.get(_obj.getIndex());
        Log.d("MyWebCrawler", priority + " - Added: " + _obj.getIndex() + " -> " + csv[0] + "," + csv[1] + " - Max: "+csv[2]);
    }

    private Elements elements;
    private int[] csv;
    private int cellsListSize = 0;
    private String priorityName;
    private Priority priority;
    QueueObjList obj;

    public SimpleList_AsyncTask.LoadURL_AsyncResponse delegate = null;

    public void generateSimpleList(){

        int startNum =csv[0];
        int endNum = csv[1];

        int max = csv[2];

        for (int index = startNum; index < endNum; index++){
            Elements _href = elements.get(index).select("a[href][title]");

            if(!_href.hasClass("mw-redirect")) {
                String _span = elements.get(index).select("span").toString();
                String _text = _href.text();
                if(_span.equalsIgnoreCase("")) {
                    //Log.d("myApp", _href.toString());
                    if(!removeUndesirableText(_text)){
                        //Log.d("MyWebCrawler", getPriorityName() + " - Thread Index: "+obj.getIndex()+" - Index: "+index + " - Thread _text: " + _text);
                        Log.d("MyWebCrawler", priorityName);
                        //Log.d("MyWebCrawler", priorityName + " - cellsListSize: " + cellsListSize + " - threadCounter: "+_threadCounter);

                        //delegate.thread_SimpleList(_text, obj.getIndex(), (cellsListSize==_threadCounter));

                        delegate.thread_SimpleList(_text, cellsListSize, obj.getIndex());
                    }
                }
            }

            delegate.thread_SimpleListUpdateProgressBar(max);
        }
        delegate.thread_SimpleListCheckStatus(cellsListSize);
    }


    public boolean removeUndesirableText(String _text) {

        return  _text.equalsIgnoreCase("Book: Mineralogy") ||
                _text.equalsIgnoreCase("Gemstone") ||
                _text.equalsIgnoreCase("List of decorative stones") ||
                _text.equalsIgnoreCase("List of gemstones") ||
                _text.equalsIgnoreCase("List of meteorite minerals") ||
                _text.equalsIgnoreCase("List of minerals (complete)") ||
                _text.equalsIgnoreCase("List of rocks on Mars") ||
                _text.equalsIgnoreCase("List of rock types") ||
                _text.equalsIgnoreCase("Mineral collecting") ||
                _text.equalsIgnoreCase("Mineraloid") ||
                _text.equalsIgnoreCase("Mineralogy") ||
                _text.equalsIgnoreCase("");
    }


}
