package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue;

import android.util.Log;

import org.jsoup.select.Elements;

import java.util.List;

import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.CompeteList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers.SimpleList_AsyncTask;
import mymineralcollection.example.org.mywebcrawler.QueueObjList;

/**
 * Created by Santiago on 1/29/2017.
 */
public class MyTask implements Runnable {

    private String priorityName;

    public MyTask(Priority priority, String PriorityName){
        this.priority = priority;
        this.priorityName = PriorityName;

        obj = new QueueObjList();
    }

    private QueueObjList obj;

    private SimpleList simpleList;
    private CompleteList completeList;

    public MyTask(SimpleList_AsyncTask.LoadURL_AsyncResponse _delegate, Priority _priority, String _priorityName, Elements _elements, List<int[]> _cellsList, QueueObjList _obj) {

        this.priority = _priority;
        this.priorityName = _priorityName;
        this.obj = _obj;


        //int[] csv =_cellsList.get(_obj.getIndex());
        //Log.d("MyWebCrawler", priority + " - Added: " + _obj.getIndex() + " -> " + csv[0] + "," + csv[1]);

        simpleList = new SimpleList(_delegate, _priority, _priorityName, _elements, _cellsList, _obj);
    }



    public MyTask(CompeteList_AsyncTask.LoadURL_AsyncResponse _delegate, Priority _priority, String _priorityName, Elements _elements, List<int[]> _cellsList, int innerIndex, QueueObjList _obj) {
        this.priority = _priority;
        this.priorityName = _priorityName;
        this.obj = _obj;
        completeList = new CompleteList(_delegate,_priority, _priorityName, _elements, _cellsList, innerIndex, _obj);
    }



    @Override
    public void run() {

        //Log.e("MyWebCrawler", "The following Runnable is getting executed "+getPriorityName());

        if(obj.isSimpleList()) {
            simpleList.generateSimpleList();
        }else if(obj.isCompleteList()) {
            completeList.generateCompleteList();
        }
        else{
            Log.e("MyWebCrawler", "The following Runnable is getting executed "+getPriorityName());
        }


        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getPriority() {
        return priority.getValue();
    }

    private Priority priority;

    public String getPriorityName() {
        return priorityName;
    }




}