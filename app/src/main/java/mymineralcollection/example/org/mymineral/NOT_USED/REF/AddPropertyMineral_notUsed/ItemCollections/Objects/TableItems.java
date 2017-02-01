package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections.Objects;

/**
 * Created by Santiago on 10/23/2016.
 */
public class TableItems {
    String text;
    boolean selected;
    boolean localBd;
    boolean propertyMod;

    public TableItems(String _data) {
        this.text = _data;
    }

    public boolean isLocalBd() {
        return localBd;
    }

    public void setLocalBd(boolean localBd) {
        this.localBd = localBd;
    }

    public boolean isPropertyMod() {
        return propertyMod;
    }

    public void setPropertyMod(boolean propertyMod) {
        this.propertyMod = propertyMod;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}