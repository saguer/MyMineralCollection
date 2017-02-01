package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.ItemCollections;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by Santiago on 10/16/2016.
 */
public class Items {

    public static final int HTML_TYPE = 1;
    public static final int TABLE_TYPE = 2;
    public static final int HTML_LIST_TYPE = 3;
    public static final int HTML_TABLE_TYPE = 4;
    public static final int HTML_TABLE_TYPE_SINGLE_COL = 5;
    public static final int HTML_TABLE_TYPE_MULTIPLE_COL = 6;

    private Fragment frag;
    private LinearLayout layout;
    private int itemType;

    public Items(Fragment _frag, LinearLayout _itemParentLayout) {
        this.frag = _frag;
        this.layout = _itemParentLayout;
    }

    PropResult_HTML propResultHTML;
    TableResult tableResult;
    TableResult_HTML tableResultHTML;
    ListResult_HTML listResultHTML;

    //--------------------------------------------------------

    public PropResult_HTML addPropResultHTML(Bundle _bundle){
        this.propResultHTML = new PropResult_HTML(frag, layout, _bundle);
        return propResultHTML;
    }

    public PropResult_HTML getPropResultHTML() {
        return propResultHTML;
    }

    //--------------------------------------------------------

    public TableResult addTableResult(Bundle _bundle) {
        this.tableResult = new TableResult(frag, layout, _bundle);
        return tableResult;
    }

    public TableResult getTableResult() {
        return tableResult;
    }

    //--------------------------------------------------------

    public ListResult_HTML addListResultHTML(Bundle _bundle) {
        this.listResultHTML = new ListResult_HTML(frag, layout, _bundle);
        return listResultHTML;
    }

    public ListResult_HTML getListResultHTML() {
        return listResultHTML;
    }

    //--------------------------------------------------------

    public TableResult_HTML addTableResultHTML(Bundle _bundle) {
        this.tableResultHTML = new TableResult_HTML(frag, layout, _bundle);
        return tableResultHTML;
    }

    public TableResult_HTML getTableResultHTML() {
        return tableResultHTML;
    }

    //--------------------------------------------------------

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

}
