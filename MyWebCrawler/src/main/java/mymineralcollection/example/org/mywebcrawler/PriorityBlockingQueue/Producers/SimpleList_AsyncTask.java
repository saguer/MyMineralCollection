package mymineralcollection.example.org.mywebcrawler.PriorityBlockingQueue.Producers;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class SimpleList_AsyncTask extends AsyncTask<Void, String, Document> {


    private int progressCounter = 0;

    private final int timeout = 5 * 1000;
    private String URL;
    public LoadURL_AsyncResponse delegate = null;
    private ThreadPoolExecutor executor;

    private Priority priority;
    private String priorityName;

    public interface LoadURL_AsyncResponse {
        void thread_SimpleList(String result, int cellsListSize, int index);
        void thread_SimpleListUpdateProgressBar(int cellsListSize);
        void thread_SimpleListCheckStatus(int cellsListSize);
    }



    public SimpleList_AsyncTask(LoadURL_AsyncResponse _delegate, ThreadPoolExecutor _executor) {
        this.URL = Constant._wikiCompleteListSimple;
        this.delegate = _delegate;
        this.executor = _executor;
        this.priority = Priority.MEDIUM;
        this.priorityName = "MEDIUM";
    }

    public SimpleList_AsyncTask(LoadURL_AsyncResponse _delegate, Priority _priority, String _priorityName, ThreadPoolExecutor _executor) {
        this.URL = Constant._wikiCompleteListSimple;
        this.delegate = _delegate;
        this.executor = _executor;
        this.priority = _priority;
        this.priorityName = _priorityName;
    }


    @Override
    protected Document doInBackground(Void... voids) {

        try {
            //http://jmchung.github.io/blog/2013/10/25/how-to-solve-jsoup-does-not-get-complete-html-document/
            //Document doc  = Jsoup.connect(URL)
            //        .header("Accept-Encoding", "gzip, deflate")
            //        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            //        .maxBodySize(0)
            //        .timeout(600000)
            //        .get();

            Document doc = Jsoup.connect(URL).timeout(timeout).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();

            while (doc == null){
                progressCounter++;
                publishProgress("Loading...   " + progressCounter);
            }

            Elements elements = doc.body().select("ul > li");
            int numOfElements = elements.size();

            Log.e("MyWebCrawler", "numOfElements: " + numOfElements);

            List<int[]> cellsList = Methods.breakIntoSteps(numOfElements, Constant.devideBy);


            for (int i =  0; i < cellsList.size(); i++) {
//
                QueueObjList _obj = new QueueObjList();
                _obj.setSimpleList(true);
                _obj.setIndex(i);
//
                MyTask task = new MyTask(delegate, priority,priorityName,elements, cellsList, _obj);
                executor.execute(new MyFutureTask(task));
            }

            return doc;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
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