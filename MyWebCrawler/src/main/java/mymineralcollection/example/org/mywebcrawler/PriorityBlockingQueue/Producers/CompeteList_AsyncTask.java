package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import mymineralcollection.example.org.mywebcrawler.Constant;
import mymineralcollection.example.org.mywebcrawler.Methods;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyFutureTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.MyTask;
import mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Priority;
import mymineralcollection.example.org.mywebcrawler.QueueObjList;


/**
 * Created by Santiago on 1/28/2017.
 */
public class CompeteList_AsyncTask extends AsyncTask<Void, String, Document> {

    final int timeout = 5 * 1000;
    public LoadURL_AsyncResponse delegate = null;

    ThreadPoolExecutor executor;

    private Priority priority;
    private String priorityName;

    public CompeteList_AsyncTask(LoadURL_AsyncResponse _delegate, ThreadPoolExecutor _executor) {
        this.delegate = _delegate;
        this.executor = _executor;
    }

    public CompeteList_AsyncTask(LoadURL_AsyncResponse _delegate, Priority _priority, String _priorityName, ThreadPoolExecutor _executor) {

        this.delegate = _delegate;
        this.executor = _executor;
        this.priority = _priority;
        this.priorityName = _priorityName;
    }


    public interface LoadURL_AsyncResponse {
        void thread_CompleteList(String result, int index);

        void thread_CompleteListCheckStatus(int cellsListSize);
    }

    @Override
    protected Document doInBackground(Void... voids) {

        for (int index = 0; index < Constant._links_complete.length; index++){
            String _url = Constant._wiki_link + Constant._links_complete[index]+Constant._wiki_render_V;

            Log.d("CreateListComplete", "URL: "+_url);

            try {
                Document doc = Jsoup.connect(_url).timeout(timeout).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();
                Element body = doc.body();

                //E F	an F element descended from an E element
                Elements elements = body.select("ol a[href][title]");

                int numOfElements = elements.size();

                List<int[]> cellsList = Methods.breakIntoSteps(numOfElements, Constant.devideBy);

                int _cellsListSize = cellsList.size();
                //completeSteps++;
                //Log.d("MyWebCrawler", "completeSteps:  "+completeSteps);
                //Log.d("MyWebCrawler", "cellsListSize: "+_cellsListSize);
                //Log.d("MyWebCrawler", "numOfElements ---> "+numOfElements);

                QueueObjList _obj = new QueueObjList();
                _obj.setCompleteList(true);

                for (int innerIndex =  0; innerIndex < _cellsListSize; innerIndex++) {

                    _obj.setIndex(index);

                    MyTask task = new MyTask(delegate,priority,priorityName,elements, cellsList, innerIndex, _obj);
                    executor.execute(new MyFutureTask(task));
                }




            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }



    @Override
    protected void onProgressUpdate(String... values) {
        //dialog.setMessage(values[0]);
        //dialog.show();
    }


    @Override
    protected void onPostExecute(Document document) {
        //super.onPostExecute(document);
        //delegate.loadURL_Result(document);
    }


    @Override
    protected void onPreExecute() {
    }
}