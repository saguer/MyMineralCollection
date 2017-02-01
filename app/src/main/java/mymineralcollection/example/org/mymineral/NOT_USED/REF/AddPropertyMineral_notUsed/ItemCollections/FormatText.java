package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections;

import java.util.ArrayList;
import java.util.Collections;

import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.Class.Methods;

/**
 * Created by Santiago on 10/22/2016.
 */
public class FormatText {

    public static ArrayList<String> formatData(String propertyName, String temp){
        //String temp = propertyInfo;
        ArrayList<String> textArray = new ArrayList<>();

        if(propertyName.equalsIgnoreCase(Constant._mineral_Colour.get_text()) ||
                propertyName.equalsIgnoreCase(Constant._mineral_Color.get_text() )){

            temp = Methods.replaceBy("or",",",temp);
            temp = temp.replace(";", ",");

            String[] tempArray = temp.split(",");

            for(String _text: tempArray){
                _text = _text.replaceAll("^\\s+", "");
                _text = Methods.toTitleCase(_text);

                if(!_text.equalsIgnoreCase("")) {
                    textArray.add(_text);
                }
            }
        }else if(propertyName.equalsIgnoreCase(Constant._mineral_common_Associates.get_text())){
            Collections.addAll(textArray, temp.split(" "));
        }else if(propertyName.equalsIgnoreCase(Constant._mineral_all_elements_in_Formula.get_text())){
            Collections.addAll(textArray, temp.split("-")[0].split(","));
        }

        return textArray;
    }
}
