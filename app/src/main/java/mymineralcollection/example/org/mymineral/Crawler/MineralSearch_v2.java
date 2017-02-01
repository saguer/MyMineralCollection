package mymineralcollection.example.org.mymineral.Crawler;

/**
 * Created by Santiago on 9/7/2016.
 */
//TODO: watch out for: Fatal error: Maximum execution time of 30 seconds exceeded in /home/mindat/www/include.php on line 3913

import android.os.Bundle;
import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;


/** Returns the link pointing to the mineral main page**/

public class MineralSearch_v2 {

    //INFO: Finds the URL for the main page
    public static Bundle multipleSearchResult(Bundle _bundle, Element body, String _mineral){
        //Log.e("MineralSearch", "+ + - - - - - - - - - - - - - - - -");
        try {
            Elements elements = body.select("a");
            SearchResult search = searchStringInBody(elements, _mineral);

            if(search._result) {
                Element _div = getParentDiv(search._element);
                String mineralName = search._element.text();
                String imageUrl = _div.select("img").first().absUrl("src");
                String mineralUrl = _div.select("a").first().absUrl("href");

                Bundle bundle = new Bundle();

                bundle.putString("html",null);
                bundle.putString("text",imageUrl);

                _bundle.putBundle(Constant._mineral_image_Url.get_key(),bundle);

                bundle = new Bundle();
                bundle.putString("html",null);
                bundle.putString("text",mineralUrl);

                _bundle.putBundle(Constant._mineral_Url.get_key(),bundle);

                bundle = new Bundle();
                bundle.putString("html",null);
                bundle.putString("text",mineralName);

                _bundle.putBundle(Constant._mineral_Name.get_key(),bundle);

                bundle = new Bundle();
                bundle.putString("html",null);
                bundle.putString("text","true");

                _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);

                bundle = new Bundle();
                bundle.putString("html",null);
                bundle.putString("text","false");

                _bundle.putBundle(Constant._mineral_single_result.get_key(),bundle);

            }

            return _bundle;

        } catch (Exception e) {
            e.printStackTrace();
            _bundle.putBoolean(Constant._mineral_search_success.get_key(), false);
            return _bundle;
        }
    }

    public static Bundle searchMineralVariety(Bundle _bundle, Element body, String _searchTerm){
        Elements elements = body.select("a, h1, h2");
        SearchResult search = searchStringInBody(elements, _searchTerm);

            if(search._result) {
                String elementText = search._element.text();
                String[] _split = elementText.split(" ");

                try{
                    String _txt = _split[0] + " " + _split[1] + " " + _split[2];
                    String _key = Methods.convertToBundleKey(_txt);

                    if(_key != null) {
                        elementText = Methods.removeWordRegex(_searchTerm, elementText);
                        elementText = Methods.toTitleCase(elementText);

                        //_bundle.putString(_key, elementText);

                        Bundle bundle = new Bundle();
                        //Todo: maybe add html responce?
                        bundle.putString("html",null);
                        bundle.putString("text",elementText);
                        _bundle.putBundle(_key,bundle);

                        Log.e("MineralSearch", "------------------------");
                        Log.e("MineralSearch", _key + " has value: " + elementText);
                        Log.e("MineralSearch", "------------------------");
                    }
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                //break;
            //}
        }
        return _bundle;
    }


    public static SearchResult searchStringInBody(Elements elements, String _searchTerm){
        SearchResult _searchResult = new SearchResult();
        _searchResult._element = null;
        _searchResult._result = false;

        for (Element element : elements) {

            String sentence = element.text();

            //Log.e("MineralSearch", "--> " + "sentence: " + sentence);
            //http://stackoverflow.com/questions/17134773/to-check-if-string-contains-particular-word
            if (sentence.toLowerCase().contains(_searchTerm.toLowerCase())) {
                Log.e("MineralSearch", "--> " + "sentence: " + sentence);
                //break;
                _searchResult._element = element;
                _searchResult._result = true;
                break;
            }
        }
        return _searchResult;
    }

    public static SearchResult searchStringInBody(Elements elements, String _searchTerm, String pattern1, String pattern2){
        SearchResult _searchResult = new SearchResult();
        _searchResult._element = null;
        _searchResult._result = false;

        for (Element element : elements) {
            String sentence = elements.text();

            //http://stackoverflow.com/questions/17134773/to-check-if-string-contains-particular-word
            if (sentence.toLowerCase().contains(_searchTerm.toLowerCase())) {
                Log.d("MineralSearch", "--> " + "sentence: " + sentence);
                //break;
                _searchResult._element = element;
                _searchResult._result = true;

                //http://stackoverflow.com/questions/11255353/java-best-way-to-grab-all-strings-between-two-strings-regex
                Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                Matcher m = p.matcher(sentence);
                if(m.find()) {
                    //System.out.println(m.group(1));
                    _searchResult._text =  m.group(1);
                    Log.d("MineralSearch", "--> " + "Match: " + _searchResult._text);
                    break;
                }
                break;
            }
        }
        return _searchResult;
    }

    private static Element getParentDiv(Element element){
        String _tagName = "";
        Element _div = null;
        int countNum = 0;
        do{
            if(countNum == 0){
                _tagName = element.tagName();
                _div = element;
            }else if(countNum == 1){
                _tagName = element.parent().tagName();
                _div = element.parent();
            }else if(countNum == 2){
                _tagName = element.parent().parent().tagName();
                _div = element.parent().parent();
            }
            countNum++;
        }while (!_tagName.equalsIgnoreCase("div"));

        return _div;

    }

    public static class SearchResult {
        //ArrayList propertyArray = new ArrayList<>();
        public boolean _result = false;
        public Element _element = null;
        public String _text = null;
    }

}
