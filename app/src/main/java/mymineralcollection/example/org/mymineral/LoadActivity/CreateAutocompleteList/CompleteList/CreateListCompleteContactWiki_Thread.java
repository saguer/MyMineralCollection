package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CompleteList;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList.CreateListMethods;

public class CreateListCompleteContactWiki_Thread implements Runnable{

    private final int timeout = 5 * 1000;

    private int index = 0;
    private Context context;
    private int devideBy = 0;
    private static int completeSteps = 0;


    public CreateListCompleteContactWiki_Thread(Context _context, int _devideBy) {
        this.context = _context;
        Log.d("QueueTest", "Thread Started: " + index);
        this.devideBy = _devideBy;
    }
    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {

                for (int index = 0; index < Constant._links_complete.length; index++){
                    String _url = Constant._wiki_link + Constant._links_complete[index]+Constant._wiki_render_V;

                    Log.d("CreateListComplete", "URL: "+_url);

                    try {
                        Document doc = Jsoup.connect(_url).timeout(timeout).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();
                        Element body = doc.body();

                        //E F	an F element descended from an E element
                        Elements elements = body.select("ol a[href][title]");

                        int numOfElements = elements.size();

                        List<int[]> cellsList = CreateListMethods.breakIntoSteps(numOfElements, devideBy);

                        int _cellsListSize = cellsList.size();
                        Log.d("CreateListComplete", "---> "+cellsList.size());
                        Log.d("CreateListComplete", "numOfElements ---> "+numOfElements);
                        completeSteps++;
                        for (int i =  0; i < _cellsListSize; i++) {
                            new CreateListComplete_Thread(context, elements, cellsList.get(i), completeSteps).run();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                Log.e("QueueTest", "completeSteps -> "+completeSteps);

            }
        }.start();
    }






}
