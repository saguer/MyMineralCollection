package mymineralcollection.example.org.mymineral.Crawler;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import mymineralcollection.example.org.mymineral.AddMineral.Queue.QueueObjItem;
import mymineralcollection.example.org.mymineral.Class.Constant;

/**
 * Created by Santiago on 11/3/2016.
 */
public class Crawler {

    public static Bundle background(QueueObjItem obj){

        String _mineral = obj.getmineralName();

        Bundle _bundle = new Bundle();

        if(obj.getInternetAccess()) {
            SearchReturnedObject searchReturnedObject = contactURL(Constant._mindat_url, _mineral);

            Element doc = searchReturnedObject.getReturnedDoc();
            if (searchReturnedObject.getReturnedDoc() != null) {
                _bundle = MindatGetDataByDiv.searchMindat_Array(doc);

                Bundle b = _bundle.getBundle(Constant._mineral_search_success.get_key());
                assert b != null;
                String __text = b.getString("text");
                String __html = b.getString("html");
                String __percent = b.getDouble("percent")+"";
                //Log.e("ItemConsumer", "__text    " + __text);
                //Log.e("ItemConsumer", "__html    " + __html);
                //Log.e("ItemConsumer", "__percent " + __percent);
                //Log.e("ItemConsumer", "--");

                boolean success = false;
                try{
                    success = Boolean.valueOf(__text);
                }catch (Exception ignored){}

                Bundle bundle = searchReturnedObject.get_bundle();
                if(success) bundle.remove(Constant._mineral_search_success.get_key());

                _bundle.putAll(bundle);


            }
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("html",null);
            bundle.putString("text",_mineral);
            _bundle.putBundle(Constant._mineral_Name.get_key(),bundle);

            bundle = new Bundle();
            bundle.putString("html",null);
            bundle.putString("text","false");
            _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);
        }



        return _bundle;
    }

    private static SearchReturnedObject contactURL(String url, String mineral){

        String _url = url+mineral;

        MineralSearch_v2.SearchResult search;

        Bundle _bundle = new Bundle();
        //_bundle.putString(Constant._mineral_Name.getKey(), mineral);

        Bundle bundle = new Bundle();
        bundle.putString("html",null);
        bundle.putString("text",mineral);
        _bundle.putBundle(Constant._mineral_Name.get_key(),bundle);


        SearchReturnedObject searchReturnedObject = new SearchReturnedObject(null, _bundle);
        Element _doc;
        try {
            Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + ");
            Log.e("ItemConsumer", "        FIRST TIME");
            Log.d("ItemConsumer", "Mineral: "+mineral);
            Log.d("ItemConsumer", "URL: "+_url);
            Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + ");
            _doc = Jsoup.connect(_url).timeout(Constant.timeout).get();

            search = MineralSearch_v2.searchStringInBody(_doc.select("a, h1, h2, b"), "Exact match - click name to view");
            if(search._result){
                //Log.d("MineralSearch", mineral + " Multiple Search Results");
                _bundle = MineralSearch_v2.multipleSearchResult(_bundle, _doc, mineral);
                _bundle = MineralSearch_v2.searchMineralVariety(_bundle, _doc, Constant._mineral_variety_Of.get_text());

                //TODO: add a delay between first and second website contact
                bundle = _bundle.getBundle(Constant._mineral_Url.get_key());

                assert bundle != null;
                _url = bundle.getString("text","");

                Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + ");
                Log.e("ItemConsumer", "      SECOND TIME");
                Log.d("ItemConsumer", "Mineral: "+mineral);
                Log.d("ItemConsumer", "URL: "+_url);
                Log.e("ItemConsumer", "- + - + - + - + - + - + - + - + - + ");
                _doc = Jsoup.connect(_url).timeout(Constant.timeout).get().body();

                searchReturnedObject = new SearchReturnedObject(_doc, _bundle);
            }else{
                search = MineralSearch_v2.searchStringInBody(_doc.select("a, h1, h2, b, p"), Constant._mineral_no_exact_name_found[0]);
                if(!search._result) {

                    bundle = new Bundle();
                    bundle.putString("html",null);
                    bundle.putString("text",mineral);
                    _bundle.putBundle(Constant._mineral_Name.get_key(),bundle);

                    bundle = new Bundle();
                    bundle.putString("html",null);
                    bundle.putString("text","true");
                    _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);

                    bundle = new Bundle();
                    bundle.putString("html",null);
                    bundle.putString("text","true");
                    _bundle.putBundle(Constant._mineral_single_result.get_key(),bundle);

                    searchReturnedObject = new SearchReturnedObject(_doc, _bundle);
                }else{
                    Log.e("ItemConsumer", mineral + " No Search Results");

                    String textNoMatch  = Constant._mineral_no_exact_name_found[1];

                    bundle = new Bundle();
                    bundle.putString("html",null);
                    bundle.putString("text",textNoMatch);
                    _bundle.putBundle(Constant._mineral_result_text.get_key(),bundle);

                    bundle = new Bundle();
                    bundle.putString("html",null);
                    bundle.putString("text","false");
                    _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);

                    searchReturnedObject = new SearchReturnedObject(null, _bundle);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.e("ItemConsumer", "ERROR!!!");
        }

        Log.e("ItemConsumer", "DONE!!!");

        return searchReturnedObject;
    }
}
