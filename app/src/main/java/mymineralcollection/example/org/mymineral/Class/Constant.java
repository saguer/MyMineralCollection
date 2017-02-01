package mymineralcollection.example.org.mymineral.Class;

import mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Items;

/**
 * Created by Santiago on 9/24/2016.
 */
public class Constant {

    public static int degrees = 360;

    //3 seconds (3000 millis)
    public static int timeout = 10 * 1000;

    public static int devidePercentBy = 100;

    //----- HTML special Chars

    //• = \u2022,
    public static String _small_circle_solid = "\u2022";
    //● = \u25CF
    public static String _large_circle_solid = "\u25CF";
    //○ = \u25CB
    public static String _large_circle = "\u25CB";
    //■ = \u25A0
    public static String _large_square_solid = "\u25A0";
    //□ = \u25A1
    public static String _large_square = "\u25A1";
    //► = \u25BA
    public static String _large_triangle = "\u25BA";


    //----- Pref Key
    public static String _dB_Name = "_dB_Name_test_1";
    public static String _dB_Name_simple = "_dB_Name_simple_test_1";
    public static String _dB_Name_complete = "_dB_Name_complete_test_1";


    public static String _completee_dictionary_Created = "_complete_dictionary_Created_test_1";
    public static String _simple_dictionary_Created = "_simple_dictionary_Created_test_1";


    public static String dB_local_history = "dB_local_history_4";


    public static String dB_personal_history = "dB_personal_history_7";



    //----- H ref TAG Bundle Tag
    public static String h_ref_title = "h_ref_title";
    static String h_ref_text = "h_ref_text";
    public static String h_ref_link = "h_ref_link";
    static String h_ref_link_text = "h_ref_link_text";
    //-----
    //----- Get Formula
    static String _td_tag_formula = "td_tag_formula";
    static String _tag_formula = "tag_formula";
    public static String _formula = "formula";
    //-----
    //----- Get Element
    public static String _td_element_symbol = "td_element";
    static String _td_element_name = "td_element_name";
    //-----

    //----- Bundle String separation symbol, to be removed during display
    public static String _separation_symbol = " %# ";


    //----- Bundle Keys ----
    public static PropertyObject _mineral_image_Url = new PropertyObject("_mineral_image_Url");
    public static PropertyObject _mineral_Url = new PropertyObject("_mineral_Url");


    public static PropertyObject _mineral_search_success = new PropertyObject("_mineral_search_success");
    public static PropertyObject _mineral_search_success_percentage = new PropertyObject("_mineral_search_success_percentage");

    public static PropertyObject _mineral_result_text = new PropertyObject("_mineral_result_text");
    public static PropertyObject _mineral_single_result = new PropertyObject("_mineral_single_result");


    public static String _mineral_no_exact_name_found[] = {
            //This is the string to match, has to be the same as website
            "No exact matches or similar sounding names were found, the following entries may be appropriate."
            //This is the message for the app
            ,"No exact matches or similar sounding names were found"};



    private static boolean _mineral_Name_ExtraProcessing = false;
    private static int _mineral_Name_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Name =
            new PropertyObject("_mineral_Name","Mineral Name",_mineral_Name_ExtraProcessing,_mineral_Name_ItemType);

    public static PropertyObject _mineral_Counter =
            new PropertyObject("_mineral_Counter","Mineral Counter",false,0);

    public static PropertyObject _mineral_Percentage =
            new PropertyObject("_mineral_Percentage","Mineral Percentage",false,0);

    private static boolean _mineral_Formula_ExtraProcessing = false;
    private static int _mineral_Formula_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Formula =
            new PropertyObject("_mineral_Formula","Formula",_mineral_Formula_ExtraProcessing,_mineral_Formula_ItemType);

    private static boolean _mineral_System_ExtraProcessing = false;
    private static int _mineral_System_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_System =
            new PropertyObject("_mineral_System","Crystal System",_mineral_System_ExtraProcessing,_mineral_System_ItemType);

    private static boolean _mineral_Color_ExtraProcessing = true;
    private static boolean _mineral_Color_AllowMod = true;
    private static int _mineral_Color_ItemType = Items.HTML_TABLE_TYPE_SINGLE_COL;

    public static PropertyObject _mineral_Color =
            new PropertyObject("_mineral_Color","Color",_mineral_Color_ExtraProcessing,_mineral_Color_ItemType,_mineral_Color_AllowMod);

    private static boolean _mineral_Colour_ExtraProcessing = true;
    private static boolean _mineral_Colour_AllowMod = true;
    private static int _mineral_Colour_ItemType = Items.HTML_TABLE_TYPE_SINGLE_COL;

    public static PropertyObject _mineral_Colour =
            new PropertyObject("_mineral_Colour","Colour",_mineral_Colour_ExtraProcessing,_mineral_Colour_ItemType,_mineral_Colour_AllowMod);

    private static boolean _mineral_Lustre_ExtraProcessing = false;
    private static int _mineral_Lustre_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Lustre =
            new PropertyObject("_mineral_Lustre","Lustre",_mineral_Lustre_ExtraProcessing,_mineral_Lustre_ItemType);

    private static boolean _mineral_Hardness_ExtraProcessing = false;
    private static int _mineral_Hardness_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Hardness =
            new PropertyObject("_mineral_Hardness","Hardness (Mohs)",_mineral_Hardness_ExtraProcessing,_mineral_Hardness_ItemType);

    private static boolean _mineral_Member_Of_ExtraProcessing = false;
    private static int _mineral_Member_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Member_Of =
            new PropertyObject("_mineral_Member_Of","Member Of",_mineral_Member_Of_ExtraProcessing,_mineral_Member_ItemType);

    private static boolean _mineral_Polymorph_Of_ExtraProcessing = true;
    private static int _mineral_Polymorph_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Polymorph_Of =
            new PropertyObject("_mineral_Polymorph_Of","Polymorph Of",_mineral_Polymorph_Of_ExtraProcessing,_mineral_Polymorph_ItemType);

    private static boolean _mineral_Isostructural_With_ExtraProcessing = true;
    private static int _mineral_Isostructural_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Isostructural_With =
            new PropertyObject("_mineral_Isostructural_With","Isostructural With",_mineral_Isostructural_With_ExtraProcessing,_mineral_Isostructural_ItemType);

    private static boolean _mineral_Name_Origin_ExtraProcessing = false;
    private static int _mineral_Name_Origin_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Name_Origin =
            new PropertyObject("_mineral_Name_Origin","Name",_mineral_Name_Origin_ExtraProcessing,_mineral_Name_Origin_ItemType);

    private static boolean _mineral_Geological_Setting_ExtraProcessing = false;
    private static int _mineral_Geological_Setting_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Geological_Setting =
            new PropertyObject("_mineral_Geological_Setting","Geological Setting",_mineral_Geological_Setting_ExtraProcessing,_mineral_Geological_Setting_ItemType);

    private static boolean _mineral_Geological_Setting_of_Type_Material_ExtraProcessing = false;
    private static int _mineral_Geological_Setting_of_Type_Material_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Geological_Setting_of_Type_Material =
            new PropertyObject("_mineral_Geological_Setting_Type_Material","Geological Setting of Type Material",_mineral_Geological_Setting_of_Type_Material_ExtraProcessing,_mineral_Geological_Setting_of_Type_Material_ItemType);

    private static boolean _mineral_Transparency_ExtraProcessing = false;
    private static int _mineral_Transparency_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Transparency =
            new PropertyObject("_mineral_Transparency","Diaphaneity (Transparency)",_mineral_Transparency_ExtraProcessing,_mineral_Transparency_ItemType);

    private static boolean _mineral_Streak_ExtraProcessing = false;
    private static int _mineral_Streak_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Streak =
            new PropertyObject("_mineral_Streak","Streak",_mineral_Streak_ExtraProcessing,_mineral_Streak_ItemType);

    private static boolean _mineral_Tenacity_ExtraProcessing = false;
    private static int _mineral_Tenacity_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Tenacity =
            new PropertyObject("_mineral_Tenacity","Tenacity",_mineral_Tenacity_ExtraProcessing,_mineral_Tenacity_ItemType);

    private static boolean _mineral_Cleavage_ExtraProcessing = false;
    private static int _mineral_Cleavage_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Cleavage =
            new PropertyObject("_mineral_Cleavage","Cleavage",_mineral_Cleavage_ExtraProcessing,_mineral_Cleavage_ItemType);

    private static boolean _mineral_Fracture_ExtraProcessing = false;
    private static int _mineral_Fracture_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Fracture =
            new PropertyObject("_mineral_Fracture","Fracture",_mineral_Fracture_ExtraProcessing,_mineral_Fracture_ItemType);

    private static boolean _mineral_Crystal_System_ExtraProcessing = false;
    private static int _mineral_Crystal_System_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Crystal_System =
            new PropertyObject("_mineral_Crystal_System","Crystal System",_mineral_Crystal_System_ExtraProcessing,_mineral_Crystal_System_ItemType);

    private static boolean _mineral_all_elements_in_Formula_ExtraProcessing = true;
    private static int _mineral_all_elements_in_Formula_ItemType = Items.HTML_TABLE_TYPE_MULTIPLE_COL;

    public static PropertyObject _mineral_all_elements_in_Formula =
            new PropertyObject("_mineral_all_elements_in_Formula","All elements listed in formula",_mineral_all_elements_in_Formula_ExtraProcessing,_mineral_all_elements_in_Formula_ItemType);

    private static boolean _mineral_common_Associates_ExtraProcessing = false;
    //private static int _mineral_common_Associates_ItemType = Items.TABLE_TYPE;
    private static int _mineral_common_Associates_ItemType = Items.HTML_TABLE_TYPE;

    public static PropertyObject _mineral_common_Associates =
            new PropertyObject("_mineral_common_associates","Common Associates",_mineral_common_Associates_ExtraProcessing,_mineral_common_Associates_ItemType);

    private static boolean _mineral_Electrical_ExtraProcessing = false;
    private static int _mineral_Electrical_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Electrical =
            new PropertyObject("_mineral_Electrical","Electrical",_mineral_Electrical_ExtraProcessing,_mineral_Electrical_ItemType);

    private static boolean _mineral_Common_Impurities_ExtraProcessing = true;
    private static int _mineral_Common_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Common_Impurities =
            new PropertyObject("_mineral_Common_Impurities","Common Impurities",_mineral_Common_Impurities_ExtraProcessing,_mineral_Common_ItemType);

    private static boolean _mineral_Fluorescence_UV_ExtraProcessing = false;
    private static int _mineral_Fluorescence_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_Fluorescence_UV =
            new PropertyObject("_mineral_Fluorescence_UV","Fluorescence in UV light",_mineral_Fluorescence_UV_ExtraProcessing,_mineral_Fluorescence_ItemType);

    private static boolean _mineral_variety_Of_ExtraProcessing = false;
    private static int _mineral_variety_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_variety_Of =
            new PropertyObject("_mineral_variety_Of","A Variety Of",_mineral_variety_Of_ExtraProcessing,_mineral_variety_ItemType);

    private static boolean _mineral_magnetism_ExtraProcessing = false;
    private static int _mineral_magnetism_ItemType = Items.HTML_TYPE;

    public static PropertyObject _mineral_magnetism =
            new PropertyObject("_mineral_magnetism","Magnetism",_mineral_magnetism_ExtraProcessing,_mineral_magnetism_ItemType);

    public final static int propertyArrayStartIndex = 3;
    public final static int propertyArrayCounterArrayPos = 1;
    public final static int propertyArrayPercenageArrayPos = 2;

    public final static PropertyArray[] propertyArray = {
            new PropertyArray(_mineral_Name),
            new PropertyArray(_mineral_Counter),
            new PropertyArray(_mineral_Percentage),

            new PropertyArray(_mineral_Formula),
            new PropertyArray(_mineral_all_elements_in_Formula),
            new PropertyArray(_mineral_Color),
            new PropertyArray(_mineral_Colour),
            new PropertyArray(_mineral_System),
            new PropertyArray(_mineral_Lustre),
            new PropertyArray(_mineral_Streak),
            new PropertyArray(_mineral_Tenacity),
            new PropertyArray(_mineral_Transparency),
            new PropertyArray(_mineral_Hardness),
            new PropertyArray(_mineral_Cleavage),
            new PropertyArray(_mineral_Fracture),
            new PropertyArray(_mineral_Crystal_System),
            new PropertyArray(_mineral_magnetism),
            new PropertyArray(_mineral_Fluorescence_UV),
            new PropertyArray(_mineral_Electrical),
            new PropertyArray(_mineral_Member_Of),
            new PropertyArray(_mineral_Polymorph_Of),
            new PropertyArray(_mineral_Isostructural_With),
            new PropertyArray(_mineral_Geological_Setting),
            new PropertyArray(_mineral_Geological_Setting_of_Type_Material),
            new PropertyArray(_mineral_common_Associates),
            new PropertyArray(_mineral_Common_Impurities),
            new PropertyArray(_mineral_variety_Of),
            new PropertyArray(_mineral_Name_Origin)};


    //---- Wiki Mineral links compete
    //A • B • C • D • E • F • G • H • I • J • K • L • M • N • O • P–Q • R • S • T • U–V • W–X • Y–Z

    public static final String _mindat_url = "http://www.mindat.org/search.php?name=";

    public static String _wikiCompleteListSimple = "https://en.wikipedia.org/w/index.php?title=List_of_minerals&action=render#V";

    public static String _wiki_link =  "https://en.wikipedia.org/w/index.php?title=";

    public static String _wiki_render_V = "&action=render#V";

    public static String[] _links_complete = {
            "List_of_minerals_A_(complete)","List_of_minerals_B_(complete)",
            "List_of_minerals_C_(complete)","List_of_minerals_D_(complete)",
            "List_of_minerals_E_(complete)","List_of_minerals_F_(complete)",
            "List_of_minerals_G_(complete)","List_of_minerals_H_(complete)",
            "List_of_minerals_I_(complete)","List_of_minerals_J_(complete)",
            "List_of_minerals_K_(complete)","List_of_minerals_L_(complete)",
            "List_of_minerals_M_(complete)","List_of_minerals_N_(complete)",
            "List_of_minerals_O_(complete)","List_of_minerals_P-Q_(complete)",
            "List_of_minerals_R_(complete)","List_of_minerals_S_(complete)",
            "List_of_minerals_T_(complete)","List_of_minerals_U-V_(complete)",
            "List_of_minerals_W-X_(complete)","List_of_minerals_Y-Z_(complete)"};

    //----- Intent Names for Receivers

    public static String intentMergeName = "merge_into_one_db";
    public static String intentItemAdded = "mineral_added";
    public static String intentItemConsumer = "item_consumer";
    public static String intentAddImageActivity = "intentAddImageActivity";
    public static String intentAddMineralListActivity = "intentAddMineralListActivity";

}