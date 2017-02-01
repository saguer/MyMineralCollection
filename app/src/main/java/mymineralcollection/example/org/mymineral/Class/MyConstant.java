package mymineralcollection.example.org.mymineral.Class;

import mymineralcollection.example.org.myviews.ViewType;

/**
 * Created by Santiago on 12/6/2016.
 */
public class MyConstant {
    public static final int CUSTOM_TEXT_INPUT = 131;
    public static final int DATE_PICKER = 231;
    public static final int PRICE_INPUT = 331;
    public static final int SAVE = 431;
    public static final int COLOR_INPUT = 531;
    public static final int DIVIDER = 631;
    public static final int HTML_TEXT_RENDER = 131; //731
    public static final int MINERAL_INFO = 831;

    public static final String DATA_PURCHASE_KEY = "DATA_PURCHASE_KEY";
    public static final String PURCHASE_PRICE_KEY = "PURCHASE_PRICE_KEY";
    public static final String PURCHASE_LOCATION_KEY = "PURCHASE_LOCATION_KEY";
    public static final String MINERAL_ORIGIN_KEY = "MINERAL_ORIGIN_KEY";
    public static final String MINERAL_NOTES_KEY = "MINERAL_NOTES_KEY";
    public static final String CARD_COLOR_KEY = "CARD_COLOR_KEY";
    public static final String SAVE_KEY = "SAVE_KEY";

    //------------------------
    public final static UserData[] UserDataArray = {
            new UserData(DATA_PURCHASE_KEY,"Date Purchased", null, DATE_PICKER, ViewType.SINGLE_LINE_VIEW),
            new UserData(PURCHASE_PRICE_KEY,"Purchase Price", null, PRICE_INPUT, ViewType.SINGLE_LINE_VIEW),
            new UserData(PURCHASE_LOCATION_KEY,"Purchase Location", null, CUSTOM_TEXT_INPUT, ViewType.EXPANDABLE_VIEW),
            new UserData(MINERAL_ORIGIN_KEY,"Mineral Origin", null, CUSTOM_TEXT_INPUT, ViewType.EXPANDABLE_VIEW),
            new UserData(MINERAL_NOTES_KEY,"Mineral Notes", null, CUSTOM_TEXT_INPUT, ViewType.EXPANDABLE_VIEW),//4
            new UserData(CARD_COLOR_KEY,"Card Color", null, COLOR_INPUT, ViewType.COLOR_SLIDER_VIEW),//5
            new UserData(SAVE_KEY,"Save", null, SAVE, ViewType.BUTTON_VIEW)
    };

    public final static int COLOR_INDEX = 5;


}
