package mymineralcollection.example.org.mymineral.Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment.HTML;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.LabelObject;
import mymineralcollection.example.org.mymineral.LoadActivity.LoadActivity;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.AutoCompleteDatabaseHandler;

/**
 * Created by Santiago on 9/2/2016.
 */
public class Methods {

    public static Bundle getChemicalData(Document _doc, int _trIndex){
        Element _tr = _doc.select("tr").get(_trIndex);
        Bundle _bundle = new Bundle();

        _bundle.putAll(getChemicalFormula(_tr));
        _bundle.putAll(getDataIn_tr_href_tag(_tr));
        return _bundle;
    }


    public static Bundle getDataIn_tr_href_tag(Element _tr){
        Element _element = _tr.select("a").first();

        String linkHref = _element.attr("href");
        String title = _element.attr("title");
        String linkText = _element.text();

        //----
        String[] separated = linkHref.split("/");
        String linkHrefText = separated[separated.length-1];
        //----

        Bundle _bundle = new Bundle();

        _bundle.putString(Constant.h_ref_title, title);
        _bundle.putString(Constant.h_ref_text, linkHref);
        _bundle.putString(Constant.h_ref_link, linkHrefText);
        _bundle.putString(Constant.h_ref_link_text , linkText);

        //Log.e("myApp", "-----------------------------");
        //Log.d("myApp", "  tr  ");
        //Log.d("myApp", _element.toString());
        //Log.d("myApp", "-----------------------------");
        //Log.d("myApp",Constant.h_ref_title+" : "+ title);
        //Log.d("myApp",Constant.h_ref_text+" : "+ linkText);
        //Log.d("myApp",Constant.h_ref_link+" : "+ linkHref);
        //Log.d("myApp",Constant.h_ref_link_text+" : "+ linkHrefText);
        //Log.e("myApp", "-----------------------------");

        return  _bundle;
    }
    public static String removeOuterTag(String _tag, String _text){
        final Pattern pattern = Pattern.compile("<"+_tag+">(.+?)</"+_tag+">");
        final Matcher matcher = pattern.matcher(_text);
        matcher.find();
        return matcher.group(1);
    }

    static public Bundle getChemicalFormula(Element _tr){

        Element _element = _tr.select("td").first();
        String _td_tag_formula = _element.toString();
        String _tag_formula = removeOuterTag("td",_td_tag_formula);
        String _formula = _element.select("td").text();

        Bundle _bundle = new Bundle();

        _bundle.putString(Constant._td_tag_formula, _td_tag_formula);
        _bundle.putString(Constant._tag_formula, _tag_formula);
        _bundle.putString(Constant._formula, _formula);

        //Log.e("myApp", "-----------------------------");
        //Log.d("myApp", "  td  ");
        //Log.d("myApp", _element.toString());
        //Log.d("myApp", "-----------------------------");
        //Log.d("myApp",Constant._td_tag_formula+" : "+ _td_tag_formula);
        //Log.d("myApp",Constant._tag_formula+" : "+ _tag_formula);
        //Log.d("myApp",Constant._formula+" : "+ _formula);
        //Log.e("myApp", "-----------------------------");

        return _bundle;
    }

    static public Bundle getElement(Element _tr){

        Element _element = _tr.select("td").first();
        String _elementText = _element.text();
        String[] separated = _elementText.split(", ");

        //Log.e("myApp", _element.text());

        String _elementName = separated[0];
        String _elementSymbol = separated[1];

        Bundle _bundle = new Bundle();

        _bundle.putString(Constant._td_element_name, _elementName);
        _bundle.putString(Constant._td_element_symbol, _elementSymbol);

        //Log.e("myApp", "-----------------------------");
        //Log.d("myApp", "  td  ");
        //Log.d("myApp", _element.text());
        //Log.d("myApp", _elementName);
        //Log.d("myApp", _elementSymbol);
        //Log.d("myApp", "-----------------------------");

        return _bundle;
    }


    //---- General Methods
    public static String convertToBundleKey(String _searchString){

        if(_searchString.equalsIgnoreCase("Formula")){
            return Constant._mineral_Formula.get_key();
        }else if(_searchString.equalsIgnoreCase("System") || _searchString.equalsIgnoreCase("Systems")){
            return Constant._mineral_System.get_key();
        }else if(_searchString.equalsIgnoreCase("Color") || _searchString.equalsIgnoreCase("Colour")){
            return Constant._mineral_Color.get_key();
        }else if(_searchString.equalsIgnoreCase("Lustre")){
            return Constant._mineral_Lustre.get_key();
        }else if(_searchString.equalsIgnoreCase("Hardness")){
            return Constant._mineral_Hardness.get_key();
        }else if(_searchString.equalsIgnoreCase("Member of")){
            return Constant._mineral_Member_Of.get_key();
        }else if(_searchString.equalsIgnoreCase("Name")){
            return Constant._mineral_Name_Origin.get_key();
        }else if(_searchString.equalsIgnoreCase("Polymorph of")){
            return Constant._mineral_Polymorph_Of.get_key();
        }else if(_searchString.equalsIgnoreCase("Isostructural with")){
            return Constant._mineral_Isostructural_With.get_key();
        }else if(_searchString.equalsIgnoreCase(Constant._mineral_variety_Of.get_text())){
            return Constant._mineral_variety_Of.get_key();
        }else if(_searchString.equalsIgnoreCase("Mineral Name Search")){
            return "Mineral Name Search";
        }else {
            return null;
        }


    }

    public static void printBundle(String logKey, Bundle _bundle){
        try {
            for (String key : _bundle.keySet())
            {
                try {
                    String classFromBundle = getClassNameFromBundle(key, _bundle);

                    if(classFromBundle.equalsIgnoreCase("String")) {
                        Log.d(logKey, key + " => " + _bundle.get(key) + " => " + classFromBundle);
                    }else if(classFromBundle.equalsIgnoreCase("String[]")) {
                        String[] _textArray = _bundle.getStringArray(key);
                        String _text = "";
                        assert _textArray != null;
                        for (String _tempText : _textArray) {
                            _text = _tempText + ",";
                        }
                        Log.d(logKey, key + " => " + _text + " => " + classFromBundle);
                    }else if(classFromBundle.equalsIgnoreCase("ArrayList")){
                        Log.d(logKey, key + " => " + _bundle.get(key) + " => " + classFromBundle);
                    }else{
                        Log.e(logKey, key + " => " + _bundle.get(key) + " => " + classFromBundle);
                    }
                }
                catch ( Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static String getJsonFromDB(String _jsonString, String _jsonParentKey, String _jsonChildKey){
        try {
            JSONObject parentJson = new JSONObject(_jsonString);
            JSONObject childJson = new JSONObject(parentJson.getString(_jsonParentKey));

            return childJson.getString(_jsonChildKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getClassNameFromBundle(String _key, Bundle _bundle){
        try {
            return _bundle.get(_key).getClass().getSimpleName();
        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }
    }

    public static String printBundleToString(String logKey, Bundle _bundle){
        String _result = "";
        for (String key : _bundle.keySet())
        {
            try {
                Log.d(logKey, key + " => " + _bundle.get(key));
                String _txt = "("+key+","+ _bundle.get(key)+")";
                _result = _txt + _result + "\n";
            }catch (Exception ex){
                Log.d(logKey, key);
            }
        }

        return _result;
    }

    static public boolean removeUndesirableText(String _text) {

        return  _text.equalsIgnoreCase("Book: Mineralogy") ||
                _text.equalsIgnoreCase("Gemstone") ||
                _text.equalsIgnoreCase("List of decorative stones") ||
                _text.equalsIgnoreCase("List of gemstones") ||
                _text.equalsIgnoreCase("List of meteorite minerals") ||
                _text.equalsIgnoreCase("List of minerals (complete)") ||
                _text.equalsIgnoreCase("List of rocks on Mars") ||
                _text.equalsIgnoreCase("List of rock types") ||
                _text.equalsIgnoreCase("Mineral collecting") ||
                _text.equalsIgnoreCase("Mineraloid") ||
                _text.equalsIgnoreCase("Mineralogy") ||
                _text.equalsIgnoreCase("");
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    //http://stackoverflow.com/questions/6652687/strip-leading-and-trailing-spaces-from-java-string

    /**
     *
     * @param _toSearch - text to search
     * @param _toReplace - text that will be inserted
     * @param _originalText - the original text to search
     * @return
     */
    public static String replaceBy(String _toSearch, String _toReplace, String _originalText){
        String regex = "\\s*\\b"+_toSearch+"\\b\\s*";
        _originalText = _originalText.replaceAll(regex, _toReplace);
        return  _originalText;
    }

    public static String removeWordRegex(String _searchTerm, String _textToModify){
        _textToModify = _textToModify.toLowerCase();
        String[] _words = _searchTerm.split(" ");

        for (int i = 0; i < _words.length; i++){
            String _TempWord = _words[i].toLowerCase();
            String regex = "\\s*\\b"+_TempWord+"\\b\\s*";
            if(i == _words.length-1) {
                _textToModify = _textToModify.replaceAll(regex, "");
            }else{
                _textToModify = _textToModify.replaceAll(regex, " ");
            }
        }
        return _textToModify;
    }

    public static void progressBar(ProgressBar _progressBar, int _max, int _steps){
        _progressBar.setMax(_max);
        _progressBar.setProgress(_steps);
    }


    public static void progressBar(ProgressBar _progressBar, TextView _textView, int _max, int _steps, String _text) {
        _progressBar.setMax(_max);
        _progressBar.setProgress(_steps);

        double percent = ((_steps*1.0)/(_max*1.0))*100;

        String txt = _text + ": " +percent;
        _textView.setText(txt);
    }


    public static void checkMyObject(String _tag, MyObject _myObject){
        Log.d(_tag, "- - - - - - - - - - - - - - - -");
        Log.d(_tag, "- - -  CheckMyObject  - - - - -");
        Log.d(_tag, "myObject getId             : " + _myObject.getId());
        Log.d(_tag, "myObject getPrevSearch     : " + _myObject.getPrevSearch());
        Log.d(_tag, "myObject getInPersonaldb   : " + _myObject.getInPersonaldb());
        Log.d(_tag, "myObject getMineralName    : " + _myObject.getMineralName());
        Log.d(_tag, "myObject getSearchSuccess  : " + _myObject.getSearchSuccess());
        Log.d(_tag, "myObject getMineralPercent : " + _myObject.getMineralPercent());
        Log.d(_tag, "myObject getTableOrigin    : " + _myObject.getTableOrigin());
        Log.d(_tag, "myObject getMineralCounter : " + _myObject.getObjectMineralCounter());
        Log.d(_tag, "- - - - - - - - - - - - - - - -");
    }

    public static void checkRow_dB(String _tag, AutoCompleteDatabaseHandler databaseH, MyObject _myObject) {

        _myObject = databaseH.findInDb(_myObject.getMineralName());
        databaseH.close();

        checkMyObject(_tag,_myObject);
    }

    public static boolean intToBool(int _input){
        if(_input == 1){
            return true;
        }else if(_input == 0){
            return false;
        }else{
            return false;
        }
    }

    public static void sendMessage(Context context, String _intentName, Bundle _bundle) {
        Log.e("MyReceiver", _intentName + " - Broadcasting message");
        Log.e("MySecondReceiver", _intentName + " - Broadcasting message");
        Intent intent = new Intent(_intentName);
        // You can also include some extra data.
        intent.putExtras(_bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    //http://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically
    public static void setBackground(int _color, Drawable _background){
        GradientDrawable gradientDrawable = (GradientDrawable)_background;
        gradientDrawable.setColor(_color);
    }

    public static void changeDrawable(Context _context, int _drawable, ImageView _imageView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            _imageView.setImageDrawable(_context.getResources().getDrawable(_drawable, _context.getTheme()));
        } else {
            _imageView.setImageDrawable(_context.getResources().getDrawable(_drawable));
        }
    }

    public static String getTimesStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    public static long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    /**
     * @param _key Bundle Key
     * @param _bundle Bundle to extract the data from
     * @param _whatToGet Bundle contains 2 texts, HTTP and Plain Text
     * @return String of the data
     */
    public static String getStringInsideNestedBundle(String _key, Bundle _bundle, String _whatToGet){

        Bundle bundle = _bundle.getBundle(_key);
        if (bundle != null) {
            return bundle.getString(_whatToGet);
        }else{
            return null;
        }
    }

    /**
     * @param _key Bundle Key
     * @param _bundle Bundle to extract the data from
     * @param _whatToGet Bundle contains 2 texts, HTTP and Plain Text
     * @return String of the data
     */
    public static double getDoubleInsideNestedBundle(String _key, Bundle _bundle, String _whatToGet){

        Bundle bundle = _bundle.getBundle(_key);
        if (bundle != null) {
            return bundle.getDouble(_whatToGet);
        }else{
            return 0.0;
        }
    }

    /**
     *
     * Extracts a String from a nested Json Obj
     *
     * @param data Json String with a Jso string inside
     * @param _key Main Json _key
     * @param _whatToGet Json key for nested Json String
     *
     * @return String of the data
     */
    public static String getStringFromJson(String _key, String data, String _whatToGet){

        try {
            if(data == null) return null;

            JSONObject obj = new JSONObject(data);
            //Log.e("ItemConsumer", "Json: "+obj.getJSONObject(_key).toString());
            //Log.e("ItemConsumer", "Json: "+obj.getJSONObject(_key).getString("text"));
            //Log.e("ItemConsumer", "Json: "+obj.getJSONObject(_key).getString("html"));
            //Log.e("ItemConsumer", "_whatToGet: "+_whatToGet);
            return obj.getJSONObject(_key).getString(_whatToGet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Removes the Observer, once the layout inflates we do not need this.
     * But might be useful to know th size if the soft screen keyboard appears.
     *
     * @param v - the view
     * @param listener - this
     */
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    /**
     * Checks the network state, returns true if there is internet
     *
     * @param _context
     * @return
     */
    public static boolean checkInternetConnection(Context _context) {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v("ItemConsumer", "Internet Connection Not Present");

            Toast.makeText(_context,"Cant't connect to the internet",
                    Toast.LENGTH_LONG).show();

            return false;
        }
    }

    /**
     *
     * @param _view
     * @param _context
     *
     * http://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
     */
    public static void hideKeyboard(View _view, Context _context) {
        InputMethodManager inputMethodManager =(InputMethodManager)_context.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(_view.getWindowToken(), 0);
    }

    /**
     *
     * @param context
     * @param image
     * @return Path of file createOrUpdate, or null if fail
     */
    public static String storeImage(Context context, String _name, Bitmap image) {
        File pictureFile = getOutputMediaFile(context,_name);

        String _path;

        if (pictureFile == null) {
            Log.d("addImage",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }else{
            _path = pictureFile.getAbsoluteFile().toString();
            //Log.d("addImage", "File path: " + _path);
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return _path;
        } catch (FileNotFoundException e) {
            Log.d("addImage", "File not found: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.d("addImage", "Error accessing file: " + e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param context
     * @param image
     * @return Path of file createOrUpdate, or null if fail
     */
    public static String storeThumbnail(Context context, String _name, Bitmap image, int thumpSize) {
        File pictureFile = getOutputMediaFile(context,_name);

        String _path;

        if (pictureFile == null) {
            Log.d("addImage",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }else{
            _path = pictureFile.getAbsoluteFile().toString();
            //Log.d("addImage", "File path: " + _path);
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);

            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(image,thumpSize,thumpSize);

            ThumbImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);


            fos.close();
            return _path;
        } catch (FileNotFoundException e) {
            Log.d("addImage", "File not found: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.d("addImage", "Error accessing file: " + e.getMessage());
            return null;
        }
    }

    public static File getBaseDir(Context context){
        return new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");
    }

    /**
     *  Create a File for saving an image or video
     * @param context
     * @return The file to be saved
     */
    private static File getOutputMediaFile(Context context, String _name){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = getBaseDir(context);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        //String mImageName="MI_"+ timeStamp +".jpg";

        File mediaFile;
        String mImageName=_name+".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    /**
     * Check if the activity is initialized or if it was killed by the system
     * @param _activity
     */
    public static void checkLoadActivityState(Activity _activity){

        if(!LoadActivity.initialized){
            Intent myIntent = new Intent(_activity, LoadActivity.class);
            _activity.startActivity(myIntent);
            _activity.finish();
        }
    }



    public static void listFile(Context context){
        File mediaStorageDir = getBaseDir(context);

        Log.d("Files", "Path: " + mediaStorageDir);
        //File f = new File(path);
        File file[] = mediaStorageDir.listFiles();
        Log.d("Files", "Size: "+ file.length);
        for (File aFile : file) {
            Log.d("Files", "FileName:" + aFile.getName());
        }
    }

    public static void listAndDeleteAllFiles(Context context){
        File mediaStorageDir = getBaseDir(context);

        //String path = Environment.getExternalStorageDirectory().toString()+"/Files/";

        Log.d("Files", "Path: " + mediaStorageDir);
        //File f = new File(path);
        File file[] = mediaStorageDir.listFiles();
        Log.d("Files", "Size: "+ file.length);
        for (File aFile : file) {
            //here populate your listview

            String pathToDelete = mediaStorageDir +"/"+aFile.getName();

            File fileToDelete = new File(pathToDelete);
            boolean deleteImgFile = fileToDelete.delete();

            Log.d("Files", "FileName:" + aFile.getName() + " - " +deleteImgFile);
        }
    }


    public static String formatDate(String propertyData){

        String[] split = propertyData.split(",");
        int day;
        int month;
        int year;

        try{
            day = Integer.valueOf(split[0]);
            month = Integer.valueOf(split[1]);
            year = Integer.valueOf(split[2]);

            String monthText = getMonthName(month);

            return monthText + " " + day + ", " + year;

        }catch (NumberFormatException e){

            return "Error Formatting Date";
        }
    }

    /**
     *
     * @param month - remember that android gives the month from 0. add one when getting the month.
     * @return
     */
    public static String getMonthName(int month){
        switch(month){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
        }

        return "";
    }

    public static ArrayList<LabelObject> cloneArrayList(ArrayList<LabelObject> list) {
        ArrayList<LabelObject> clone = new ArrayList<>(list.size());
        for (LabelObject item : list){
            clone.add(item);
        }
        return clone;
    }

    public static String detectVar(ArrayList<LabelObject> item){

        //TODO: is it possible to do without cloning the Array?
        ArrayList<LabelObject> _item = cloneArrayList(item);

        ListIterator<LabelObject> listIterator = item.listIterator();

        String html = "";

        int varCounter = 0;

        while(listIterator.hasNext()) {
            listIterator.next();
            int next = listIterator.nextIndex()-1;
            int prev = listIterator.previousIndex()-1;

            String _name = item.get(next).getMineralName();
            String _extra = item.get(next).getExtra();

            String _namePrev = null;
            String _extraPrev = null;

            if(prev >= 0) {
                _namePrev = item.get(prev).getMineralName();
                _extraPrev = item.get(prev).getExtra();
            }

            String tst;

            if(_extra == null) _extra ="";

            boolean _isPrevExtraVar = (_extraPrev != null) && _extraPrev.toLowerCase().contains("var");
            boolean _isExtraVar = _extra.toLowerCase().contains("var");
            boolean add = !(_isPrevExtraVar || _isExtraVar);

            if(_isExtraVar) varCounter++;

            Log.e("ResultHtmlText", add + " -nextIndex: " + listIterator.nextIndex() + " " +_name+"|"+_extra +
                    " - _isPrevExtraVar: " + _isPrevExtraVar + " _isExtraVar: "+_isExtraVar);

            //if(_isExtraVar){
            //    _extra = "";
            //}

            if(add) {
                tst = HTML.setCombo(_name, "", "", _extra);
                html = html + tst;
            }

            if (_isPrevExtraVar) {
                tst = HTML.setCombo(_namePrev, _extraPrev, _name, _extra);
                html = html + tst;
            }

            //override if for the last item the user selected Var
            if((listIterator.nextIndex()==item.size()) && _isExtraVar){
                tst = HTML.setCombo(_name, _extra, "", "");
                html = html + tst;
            }

            Log.d("ResultHtmlText", "varCounter: "+varCounter);

            if(varCounter == item.size()){
                Log.d("ResultHtmlText", "Erase html and do for loop");
                html = "";
                for(LabelObject _label: item){
                    tst = HTML.setCombo(_label.getMineralName(), "", "", _label.getExtra());
                    html = html + tst;
                }
            }
        }

        //String HTML_string = HTML.setHTMLdoc(html);
        //Log.e("ResultHtmlText", " --------------------- ");
        //Log.e("ResultHtmlText", HTML_string);
        //result_webView.loadData(HTML_string, "text/html", null);

        return html;
    }


    /**
     * Todo: find where is the duplicate method created earlier and move to a common place
     * Gets the json data, if no key returns null
     * @param _jsonData
     * @param _key
     * @return
     */
    public static String getJsonDataString(String _jsonData, String _key){
        String propertyData = null;
        try {
            propertyData = new JSONObject(_jsonData).getString(_key);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        return propertyData;
    }
    /**
     * Todo: find where is the duplicate method created earlier and move to a common place
     * Gets the json data, if no key returns null
     * @param _jsonData
     * @param _key
     * @return
     */
    public static int getJsonDataInt(String _jsonData, String _key){
        int propertyData = 0;
        try {
            propertyData = new JSONObject(_jsonData).getInt(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyData;
    }

    /**
     * List the content of the intent
     * @param i - the intent
     */
    public static void dumpIntent(String LOG_TAG, Intent i){


        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e(LOG_TAG,"Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e(LOG_TAG,"[ " + key + " = " + bundle.get(key)+" ]");
            }
            Log.e(LOG_TAG,"Dumping Intent end");
        }
    }



}
