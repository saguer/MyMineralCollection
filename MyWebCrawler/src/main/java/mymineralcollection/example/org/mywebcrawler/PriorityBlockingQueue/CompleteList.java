package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue;

import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.CompeteList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.QueueObjList;

/**
 * Created by Santiago on 1/29/2017.
 */
public class CompleteList {

    CompeteList_AsyncTask.LoadURL_AsyncResponse delegate;

    public CompleteList(CompeteList_AsyncTask.LoadURL_AsyncResponse _delegate, Priority priority, String PriorityName, Elements _elements, List<int[]> _cellsList, int innerIndex, QueueObjList _obj) {
        this.priority = priority;
        this.priorityName = PriorityName;
        this.elements = _elements;

        this.obj = _obj;

        this.csv = _cellsList.get(innerIndex);


        this.delegate = _delegate;

        Log.d("MyWebCrawler", priority + " - Added: " + _obj.getIndex() + " -> " + csv[0] + "," + csv[1]);
    }


    private Elements elements;
    private int[] csv;
    private int cellsListSize = 0;
    private String priorityName;
    private Priority priority;
    private QueueObjList obj;
    private int completeSteps = 0;

    public void generateCompleteList(){


        int startNum =csv[0];
        int endNum = csv[1];

        for (int i = startNum; i < endNum; i++){
            Element ele = elements.get(i);
            if(ele != null) {
                String _text = ele.text();
                if (!_text.equalsIgnoreCase("here")) {
                    //Log.e("MyWebCrawler", priorityName + " - Thread Index: "+obj.getIndex()+ " - Thread _text: " + _text);
                    //_result.add(_text);
                    //Log.d("CreateListComplete", "Outside Index: " + outerIndex + " - Inside Index: " + index + " -> " +_text);
                    Log.d("MyWebCrawler", priorityName);
                    //delegate.thread_CompleteList(_text,obj.getIndex(),((completeSteps/ Constant.devideBy)==Constant._links_complete.length));
                    delegate.thread_CompleteList(_text,obj.getIndex());
                }
            }
        }
        delegate.thread_CompleteListCheckStatus(cellsListSize);

    }
}
