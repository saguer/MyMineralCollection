package mymineralcollection.example.org.mymineral.NOT_USED.REF.AddPropertyMineral_notUsed.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 10/22/2016.
 */
public class HelpMenuDialog {
    Context context;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    private int layoutRes;
    private String prefName;


    public HelpMenuDialog(Context _context, String _prefName,int _layoutRes) {
        this.context = _context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.layoutRes = _layoutRes;
        this.prefName = _prefName;
    }

    public void OnCreateMenuDialog(){
        startMenuDialog(true,false);
    }
    public void OnClickMenuDialog(){
        startMenuDialog(false,true);
    }

    private void startMenuDialog(boolean _showCheckBoxLayout, boolean _menuOnClick){

        boolean show = (!preferences.getBoolean(prefName,false)) || _menuOnClick;
        if(show) {
            boolean wrapInScrollView = true;

            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .title(R.string.help_txt)
                    .titleColorRes(R.color.colorAccent)

                    .titleGravity(GravityEnum.CENTER)
                    .customView(layoutRes, wrapInScrollView);
            //.positiveText(R.string.positive);

            MaterialDialog dialog = builder.build();

            View view = dialog.getCustomView();
            assert view != null;

            LinearLayout show_again_LinearLayout = (LinearLayout) view.findViewById(R.id.show_again_LinearLayout);
            if(show_again_LinearLayout != null){
                show_again_LinearLayout.setVisibility(View.GONE);

                if (_showCheckBoxLayout) {
                    show_again_LinearLayout.setVisibility(View.VISIBLE);

                    CheckBox show_again_checkBox = (CheckBox) view.findViewById(R.id.show_again_checkBox);

                    show_again_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            edit = preferences.edit();
                            edit.putBoolean(prefName, isChecked);
                            edit.apply();
                        }
                    });
                }
            }

            dialog.show();
        }
    }

}
