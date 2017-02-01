package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects;

/**
 * Created by Santiago on 10/23/2016.
 */

public class ResultItems {

    private String text;
    private int index;
    private boolean selected = false;

    public ResultItems(int index, String text, boolean selected) {
        this.index = index;
        this.text = text;
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public boolean getPropertyModSelected() {
        return selected;
    }

}