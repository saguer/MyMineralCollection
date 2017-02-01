package mymineralcollection.example.org.mymineral.Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Santiago on 11/5/2016.
 */
public class CrawlerMethods {

    public static String getMindatam2Div(String htmlCode){

        if(htmlCode != null) {
            Element doc = Jsoup.parse(htmlCode).body();

            Elements _element = doc.select("div.mindatarow").select("div.mindatam2");
            String temp = _element.toString();

            if (temp.equalsIgnoreCase("")) {
                return null;
            } else {
                return temp;
            }
        }
        return null;
    }
}
