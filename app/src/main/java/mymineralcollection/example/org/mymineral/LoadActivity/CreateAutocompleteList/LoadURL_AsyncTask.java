package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList;


import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Santiago on 9/17/2016.
 */
public class LoadURL_AsyncTask extends AsyncTask<Void, String, Document> {

    final int timeout = 5 * 1000;
    String URL;
    public LoadURL_AsyncResponse delegate = null;
    public interface LoadURL_AsyncResponse {
        void loadURL_Result(Document output);
    }

    public LoadURL_AsyncTask(LoadURL_AsyncResponse _delegate, String _url) {
        this.URL = _url;
        this.delegate = _delegate;
    }

    int counter = 0;
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
                counter++;
                publishProgress("Loading...   " + counter);
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
        delegate.loadURL_Result(document);
    }

    @Override
    protected void onPreExecute() {
    }

}