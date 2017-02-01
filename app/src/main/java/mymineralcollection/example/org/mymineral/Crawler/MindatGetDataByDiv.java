package mymineralcollection.example.org.mymineral.Crawler;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.Class.PropertyArray;
import mymineralcollection.example.org.mymineral.Class.PropertyObject;


/**
 * Created by Santiago on 9/7/2016.
 */
public class MindatGetDataByDiv {
    /**<div id="introdata"> div#introdata
    static public String introdatTag = "div#introdata"; //# to select ID
     **/

    public static Bundle searchMindat_Array(Element doc){
        Bundle _bundle = new Bundle();
        for (int i = Constant.propertyArrayStartIndex; i < Constant.propertyArray.length; i++){
            //for (PropertyArray ele : Constant.propertyArray) {
            _bundle = searchMindatRow(_bundle,Constant.propertyArray[i], doc);
        }

        if(fail_search_counter == Constant.propertyArray.length){
            Bundle bundle = new Bundle();
            bundle.putString("html",null);
            bundle.putString("text","false");
            _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);
        }else{
            double percentMatch = ((fail_search_counter*1.0)/(Constant.propertyArray.length*1.0))*100;

            percentMatch = (int)(percentMatch*100);
            percentMatch =  percentMatch/100;

            Log.d("MineralSearch", "percentMatch: " + percentMatch);

            Bundle bundle = new Bundle();
            bundle.putString("html",null);
            bundle.putString("text","true");
            bundle.putDouble("percent",percentMatch);
            _bundle.putBundle(Constant._mineral_search_success.get_key(),bundle);

        }
        return _bundle;
    }


    public static Bundle searchMindatRow(Bundle _bundle, PropertyArray _keyValuePair, Element doc){
        Elements _elements = doc.getAllElements().select("div");
        try {
            for (Element ele : _elements) {
                if(ele.className().equalsIgnoreCase("mindataRow")){
                    String _txt = ele.text();
                    String[] _split = _txt.split(":");

                    if(_keyValuePair.getText().equalsIgnoreCase(_split[0])){
                        //Log.d("MineralSearch", "1 toString : " + ele.toString());

                        String split_text = _split[1];
                        split_text = split_text.replaceAll("^\\s+", "");

                        //Log.d("MineralSearch", "1st METHOD : " +_keyValuePair.getKey() + " =|" +split_text);
                        Log.d("MineralSearch", "1st METHOD : " +_keyValuePair.getKey() + " =| " +split_text + " =| "+ele.toString());

                        Bundle bundle = new Bundle();

                        bundle.putString("html",ele.toString());

                        bundle.putString("text",split_text);

                        //_bundle.putString(_keyValuePair.getKey(), _split[1]);
                        _bundle.putBundle(_keyValuePair.getKey(),bundle);

                        //_bundle = doExtraProcessing(_bundle, _keyValuePair.getKey(), _propertyArrayIndex, _split[1]);
                        return _bundle;
                    }
                }
            }

            PropertyObject _propertyObject = getDataFromIntroDataDiv(doc, _keyValuePair);
            if(_propertyObject.get_text() != null) {
                Log.d("MineralSearch", "2nd METHOD : " + _propertyObject.get_key() + " => " + _propertyObject.get_text());
                //_bundle.putString(_propertyObject.getKey(), _propertyObject.getText());

                Bundle bundle = new Bundle();

                bundle.putString("html",_propertyObject.get_html());
                bundle.putString("text",_propertyObject.get_text());

                _bundle.putBundle(_keyValuePair.getKey(),bundle);

                //(Bundle _bundle, String _key, int _propertyArrayIndex, String _txtForProcessing)

                //_bundle = doExtraProcessing(_bundle, _propertyObject.getKey(), _propertyArrayIndex, _propertyObject.getText());
                return _bundle;
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("MineralSearch", e.toString());
        }
        Log.e("MineralSearch","NOTHING:  " + _keyValuePair.getKey() + "=> none");
        fail_search_counter++;
        Log.e("MineralSearch","NOTHING:  fail_search_counter" + fail_search_counter);
        //
        return _bundle;
    }

    private static int fail_search_counter = 0;

    private static PropertyObject getDataFromIntroDataDiv(Element _element, PropertyArray _keyValuePair) {

        PropertyObject _result = new PropertyObject(_keyValuePair.getKey(), null, null);

        try {
            Elements _elements = _element.select("div#introdata").select("div, span");

            for (int i = 1; i < _elements.size(); i++) {
                Element element = _elements.get(i);

                String sentence = element.text();

                String[] _split = sentence.split(":");

                //Log.e("MineralSearch", "--> " + "sentence: " + sentence);

                //http://stackoverflow.com/questions/17134773/to-check-if-string-contains-particular-word
                //if (sentence.toLowerCase().contains(_keyValuePair.getText().toLowerCase())) {
                if(sentence.equalsIgnoreCase(_split[0])){
                    //Log.e("MineralSearch", _keyValuePair.getKey() + " => " + _split[1]);
                    //_bundle.putString(_keyValuePair.getKey(), _split[1]);
                    String split_text = _split[1];
                    split_text = split_text.replaceAll("^\\s+", "");

                    _result = new PropertyObject(_keyValuePair.getKey(),split_text,element.toString());
                    break;
                }
            }
        }catch (Exception e){

        }

        return _result;
    }

    public static String[] convertToArray(String[] _textArray){

        ArrayList<String> _text = new ArrayList<>();
        Collections.addAll(_text, _textArray);

        String[] stockArr = new String[_text.size()];
        _textArray = _text.toArray(stockArr);

        return _textArray;
    }

    /** FIX THIS METHOD, not giving correct results. this method is very important as it formats the data for use later on**/
    @SuppressWarnings("unchecked")
    private static Bundle doExtraProcessing(Bundle _bundle, String _key, int _propertyArrayIndex, String _txtForProcessing){
        PropertyArray property = Constant.propertyArray[_propertyArrayIndex];
        boolean _processing = property.getExtraProcessing();

        String _processedText;

        if(_processing){
            if(Constant._mineral_all_elements_in_Formula.get_key().equalsIgnoreCase(property.getKey()) ||
                    Constant._mineral_Common_Impurities.get_key().equalsIgnoreCase(property.getKey()) ){
                //Log.d("mindataRow", "property.getKey(): " + property.getKey());
                /** Remove dash -> - **/
                _processedText =  _txtForProcessing.split("-")[0];

                String[] _textArray = _processedText.split(",");

                //_bundle.putStringArray(_key,convertToArray(_textArray));
                _bundle.putString(_key,AddSymbol(_textArray));

            }else if(Constant._mineral_Polymorph_Of.get_key().equalsIgnoreCase(property.getKey()) ||
                     Constant._mineral_Isostructural_With.get_key().equalsIgnoreCase(property.getKey()) ){

                String[] _textArray = _txtForProcessing.split(",");

                //_bundle.putStringArray(_key,convertToArray(_textArray));
                _bundle.putString(_key,AddSymbol(_textArray));

            }
            else if(Constant._mineral_Colour.get_key().equalsIgnoreCase(property.getKey()) ||
                     Constant._mineral_Color.get_key().equalsIgnoreCase(property.getKey())){

                _bundle.remove(Constant._mineral_Colour.get_key());
                _bundle.remove(Constant._mineral_Color.get_key());

                /** ------------------------ **/
                /** Change 'or' into a comma**/
                _txtForProcessing = Methods.replaceBy("or", ",", _txtForProcessing);
                //_txtForProcessing = Methods.replaceBy("-", ",", _txtForProcessing);
                //_txtForProcessing = Methods.replaceBy(" - ", ",", _txtForProcessing);
                /** ------------------------ **///YUGAWARALITE

                String[] _textArray = _txtForProcessing.split(",");
                //_bundle.putStringArray(Constant._mineral_Color.getKey(),convertToArray(_textArray));
                _bundle.putString(Constant._mineral_Color.get_key(),AddSymbol(_textArray));

            }
        }else{
            _bundle.putString(_key,_txtForProcessing);
        }

        return _bundle;
    }


    public static String AddSymbol(String[] _textArray){

        String _sysmbol = Constant._separation_symbol;

        String _text = "";
        for (String _tempText : _textArray) {
            _text = _tempText + _sysmbol;
        }
        return _text.replaceAll(_sysmbol, "");
    }


}
