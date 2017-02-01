package mymineralcollection.example.org.myviews.ColorView;

import java.io.Serializable;

/**
 * Created by Santiago on 12/10/2016.
 */
public class ColorObject implements Serializable {

    private int colorNumberIndex;
    private int colorNumberArray;
    private String colorNameArray;


    public ColorObject() {
        this.colorNumberArray = 0;
        this.colorNameArray = null;
    }

    public ColorObject(int colorNumberIndex, int colorNumberArray, String colorNameArray) {
        this.colorNumberIndex = colorNumberIndex;
        this.colorNumberArray = colorNumberArray;
        this.colorNameArray = colorNameArray;
    }


    public void setColorNumber(int colorNumberArray) {
        this.colorNumberArray = colorNumberArray;
    }

    public void setColorName(String colorNameArray) {
        this.colorNameArray = colorNameArray;
    }

    public int getColorNumberIndex() {
        return colorNumberIndex;
    }

    public void setColorNumberIndex(int colorNumberIndex) {
        this.colorNumberIndex = colorNumberIndex;
    }


    public int getColorNumber() {
        return colorNumberArray;
    }

    public String getColorName() {
        return colorNameArray;
    }


}