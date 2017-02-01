package mymineralcollection.example.org.myviews;

import android.content.Context;

import java.util.Random;

import mymineralcollection.example.org.myviews.ColorView.ColorObject;

/**
 * Created by Santiago on 12/19/2016.
 */
public class GetColorArray {


    //array of color values
    private int[] colorNumberarray;
    // array of color names
    private String[] colorNameArray;


    public GetColorArray(Context context) {
        //this.context = context;
        //mColorObjArray = new ArrayList<>();

        // defines the width of each color square
        //colorGridColumnWidth = context.getResources().getInteger(R.integer.colorGridColumnWidth);

        //fill  colorNumberarray with values from the colorNumberList Array in strings.xml
        colorNumberarray = context.getResources().getIntArray(R.array.colorNumberList);
        //fill  colorNameArray with values from the colorNameArray Array in strings.xml
        colorNameArray = context.getResources().getStringArray(R.array.colorNameList);
    }


    private ColorObject colorObj;

    public void generateRandomColor(){
        int colorIndex = new Random().nextInt(colorNumberarray.length);
        colorObj = new ColorObject(colorIndex, colorNumberarray[colorIndex], colorNameArray[colorIndex]);
    }

    public ColorObject getColorObj() {
        return colorObj;
    }

    //---------------------

/*
    private Context context;

    private int colorIndex = 0;
    //private int colorGridColumnWidth;

    private ArrayList<ColorObject> mColorObjArray;

    public int getColorIndex() {
        return colorIndex;
    }


    public void generateRandomIndex(){
        colorIndex = new Random().nextInt(colorNumberarray.length);
    }


    public void generateColorArray(){

        for (int i = 0; i < colorNumberarray.length; i++) {
            boolean underline = (colorIndex == i);
            mColorObjArray.add(new ColorObject(i, colorNumberarray[i], colorNameArray[i]));

            if(underline) Log.e("MineralListActivity", i + " - underline: true");
        }
    }


    public ArrayList<ColorObject> getmColorObjArray() {
        return mColorObjArray;
    }


    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }
*/
}
