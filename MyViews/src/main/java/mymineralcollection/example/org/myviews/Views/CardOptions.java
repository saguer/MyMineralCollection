package mymineralcollection.example.org.myviews.Views;

import java.io.Serializable;

import mymineralcollection.example.org.myviews.R;

/**
 * Created by Santiago on 12/6/2016.
 */
public class CardOptions implements Serializable {

    private int tittleTextAreaColor = -1;
    private int tittleTextColor = -1;
    private int expandableTextAreaColor = -1;
    private int expandableTextColor = -1;
    private int separationLineColor = -1;
    private int animationSpeed = -1;
    private boolean expandEnable = false;

    private int skipCollapseAtPosition = -1;


    public CardOptions() {
    }
//

    public void CardOptionsForAddDataMineralData(int expandItemPos){

        setTittleTextColor(R.color.text_color);
        setTittleTextAreaColor(R.color.black_overlay);
        setExpandableTextColor(R.color.text_color);
        setExpandableTextAreaColor(R.color.dk_black_overlay);
        setSeparationLineColor(R.color.dk_black_overlay);
        setExpandEnable(true);
        setAnimationSpeed(300);

        setSkipCollapseAtPosition(expandItemPos);

    }

    public void CardOptionsForMineralListActivity(){

        setTittleTextColor(R.color.text_color);
        setTittleTextAreaColor(R.color.black_overlay);
        setExpandableTextColor(R.color.text_color);
        setExpandableTextAreaColor(R.color.dk_black_overlay);
        setSeparationLineColor(-1);
        setExpandEnable(true);
        setAnimationSpeed(300);

    }

    public void CardOptionsForDivider(int color){

        setTittleTextColor(-1);
        setExpandableTextColor(-1);
        setExpandableTextAreaColor(-1);
        setTittleTextAreaColor(-1);
        setSeparationLineColor(color);
        setExpandEnable(false);
        setAnimationSpeed(300);

    }


    public void CardOptionsForUserSetInfoArray() {

        setTittleTextColor(R.color.text_color);
        setExpandableTextColor(R.color.text_color);
        setExpandableTextAreaColor(-1);
        setTittleTextAreaColor(-1);
        setSeparationLineColor(R.color.dk_black_overlay);
        setExpandEnable(false); //hides the icon
        setAnimationSpeed(300);

    }

    public void CardOptionsForFillEditArray() {

        setTittleTextColor(R.color.text_color);
        setExpandableTextColor(R.color.text_color);
        setExpandableTextAreaColor(-1);
        setTittleTextAreaColor(-1);
        setSeparationLineColor(R.color.dk_black_overlay);
        setExpandEnable(true);
        setAnimationSpeed(300);
    }



    public int getTittleTextAreaColor() {
        return tittleTextAreaColor;
    }

    public int getTittleTextColor() {
        return tittleTextColor;
    }

    public int getExpandableTextAreaColor() {
        return expandableTextAreaColor;
    }

    public int getExpandableTextColor() {
        return expandableTextColor;
    }

    public boolean isExpandEnable() {
        return expandEnable;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public int getSkipCollapseAtPosition() {
        return skipCollapseAtPosition;
    }



    public void setTittleTextAreaColor(int tittleTextAreaColor) {
        this.tittleTextAreaColor = tittleTextAreaColor;
    }

    public void setTittleTextColor(int tittleTextColor) {
        this.tittleTextColor = tittleTextColor;
    }

    public void setExpandableTextAreaColor(int expandableTextAreaColor) {
        this.expandableTextAreaColor = expandableTextAreaColor;
    }

    public void setExpandableTextColor(int expandableTextColor) {
        this.expandableTextColor = expandableTextColor;
    }



    public int getSeparationLineColor() {
        return separationLineColor;
    }

    public void setSeparationLineColor(int separationLineColor) {
        this.separationLineColor = separationLineColor;
    }



    public void setExpandEnable(boolean expandEnable) {
        this.expandEnable = expandEnable;
    }


    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public void setSkipCollapseAtPosition(int skipCollapseAtPosition) {
        this.skipCollapseAtPosition = skipCollapseAtPosition;
    }


}
