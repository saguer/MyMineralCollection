package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;

/**
 * Created by Santiago on 11/1/2016.
 */
public class HTML {
    /**
     * HTML doc CSS style
     */
    public static String style = "<style>" +
            "ul.mineralName {" +
            "    list-style: none;" +
            "    font-size: 200%;" +
            "}" +
            "ul.mineralExtra {" +
            "    list-style: none;" +
            "    font-size: 75%;" +
            "}"+
            "div.border {"+
            "    border: 20px solid green;" +
            "}"+
            "body.margin {"+
            "    margin: 0px;" +
            "}"+
            ".right  {" +
            "    text-align: right;" +
            //"    font-size: 100%; "+
            "}"+
            "</style>"
            ;

    public static String setHTMLdoc(String bodyText){

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">"+
                style +
                "</head>" +
                "<body class=\"margin\">" +
                "<div class=\"border\">"
                +bodyText+
                "</div>" +
                "</body>" +
                "</html>";
    }

    //TODO: this is not working, it is adding small bullets that are not possible to remove
    public static String setCombo(String mineralName, String extra1, String MineralVar, String extra2){

        String t = "ul#the-one {list-style-type: none;}";

       return  "<ul id="+t+">" +
               "  <li>"+mineralName +
               "    <ul >" +
               "    <li><i>"+extra1+"</i> "+MineralVar+"</li>" +
               "    <li><b>"+extra2+"</b></li>"+
               "    </ul>" +
               "  </li>" +
               "</ul>"+
               "";



    }


}
