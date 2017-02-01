package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.Fragment;

/**
 * http://stackoverflow.com/questions/2381560/how-to-group-a-3x3-grid-of-radio-buttons/2383978#2383978
 * Created by Santiago on 10/29/2016.
 */
import android.app.Fragment;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.R;

public class ToggleButtonGroupTableLayout extends TableLayout{

    //private RadioButton activeRadioButton;
    private TextView activeRadioButton;

    Context context;

    int _unSelected;
    int _selected;

    ToggleButtonGroupTableLayout mineralExtra_ToggleButtonGroupTableLayout;


    View view;
    public ToggleButtonGroupTableLayout(Fragment _frag, View _view) {
        super(_frag.getActivity());

        this.context = _frag.getActivity();
        this.view = _view;

        _unSelected = ContextCompat.getColor(context, R.color.un_selected);
        _selected = ContextCompat.getColor(context, R.color.selected);

        mineralExtra_ToggleButtonGroupTableLayout = (ToggleButtonGroupTableLayout) view;
        //

    }


    /**
     * @param _context
     */
    public ToggleButtonGroupTableLayout(Context _context) {
        super(_context);
        // TODO Auto-generated constructor stub
        this.context = _context;

        _unSelected = ContextCompat.getColor(context, R.color.un_selected);
        _selected = ContextCompat.getColor(context, R.color.selected);
    }

    /**
     * @param context
     * @param attrs
     */
    public ToggleButtonGroupTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        _unSelected = ContextCompat.getColor(context, R.color.lt_Green);
        _selected = ContextCompat.getColor(context, R.color.Green);
    }

    private int lastClickId = 0;
    private boolean state = false;

    public String geButtonGroupSelectedText(View v){
        final TextView rb = (TextView) v;

        boolean sameIdClicked = (v.getId() == lastClickId);
        if(!sameIdClicked) {
            state = false;
        }

        if ( activeRadioButton != null ) {
            Methods.setBackground(_unSelected,activeRadioButton.getBackground());
        }

        state = !state;

        activeRadioButton = rb;
        lastClickId = v.getId();

        if(state) {
            Methods.setBackground(_selected, rb.getBackground());
            Log.d("AddMineralListActivity", "rb: " + rb.getText());
            return rb.getText().toString();
        }else{
            Methods.setBackground(_unSelected, rb.getBackground());
            Log.d("AddMineralListActivity", "rb: " + rb.getText());
            return null;
        }
    }


    public void setCheckedRadioButtonID(int _textViewId, View view){
        TextView _textView = (TextView) view.findViewById(_textViewId);
        Methods.setBackground(_selected,_textView.getBackground());
    }
    public void setUnCheckedRadioButtonID(int _textViewId, View view){
        TextView _textView = (TextView) view.findViewById(_textViewId);
        Methods.setBackground(_unSelected,_textView.getBackground());
    }

    public void uncheckedAll(View view){

        TextView _textView = (TextView) view.findViewById(R.id.matrix_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());

        _textView = (TextView) view.findViewById(R.id.var_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());

        _textView = (TextView) view.findViewById(R.id.cluster_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());

        _textView = (TextView) view.findViewById(R.id.with_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());

        _textView = (TextView) view.findViewById(R.id.on_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());

        _textView = (TextView) view.findViewById(R.id.and_rad);
        Methods.setBackground(_unSelected,_textView.getBackground());
    }

    public String getText(int _id){
        TextView textView = (TextView) view.findViewById(_id);
        return textView.getText().toString();
    }
    public String getText(View _view){
        TextView textView = (TextView) _view;
        return textView.getText().toString();
    }
    public int getIdFromText(String _text){
        if(_text == null){
            return -2;
        }else if(_text.equalsIgnoreCase(getText(R.id.matrix_rad))){
            return R.id.matrix_rad;
        }else if(_text.equalsIgnoreCase(getText(R.id.var_rad))){
            return R.id.var_rad;
        }else if(_text.equalsIgnoreCase(getText(R.id.cluster_rad))){
            return R.id.cluster_rad;
        }else if(_text.equalsIgnoreCase(getText(R.id.with_rad))){
            return R.id.with_rad;
        }else if(_text.equalsIgnoreCase(getText(R.id.on_rad))){
            return R.id.on_rad;
        }else if(_text.equalsIgnoreCase(getText(R.id.and_rad))){
            return R.id.and_rad;
        }else{
            return -1;
        }
    }

}