package mymineralcollection.example.org.mymineral;

import android.os.AsyncTask;

import java.net.URL;

import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
 * Created by Santiago on 10/9/2016.
 */
class DownloadAndExtractTask extends AsyncTask<URL, Void, String> {
    public String doInBackground(URL... urls) {

        try {

            URL url = new URL("http://www.mindat.org/min-1295.html");
            String articleText = ArticleExtractor.INSTANCE.getText(url);
            return articleText;
        }
        catch (Exception e) {
            return "ERROR";
        }
    }
}
