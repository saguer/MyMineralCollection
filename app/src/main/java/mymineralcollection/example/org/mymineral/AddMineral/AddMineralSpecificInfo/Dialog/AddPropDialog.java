package mymineralcollection.example.org.mymineral.AddMineral.AddMineralSpecificInfo.Dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Date;

import mymineralcollection.example.org.mymineral.Class.UserData;
import mymineralcollection.example.org.mymineral.R;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 11/29/2016.
 */
public class AddPropDialog {
    Context context;

    private int position;
    private EditText data_editText;
    private DatePicker datePicker;

    public AddPropDialog(Context _context) {
        this.context = _context;

    }

    public void startDialogTextInput(ArrayList<UserData> _dataPropName, int _position, final RecycleBen recycleBen) {
        String propertyName = _dataPropName.get(_position).getPropertyName();
        String propertyData = (String) _dataPropName.get(_position).getPropertyDataObj();

        position = _position;
        Log.d("PropInfo", "startDialogTextInput: "+position);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(propertyName)
                .titleColorRes(R.color.ActionBarColor)
                .cancelable(false)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.add_prop_dialog, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog _dialog, @NonNull DialogAction which) {
                        // TODO GET DATE
                        dialog.dismiss();
                        String text = data_editText.getText().toString();

                        if(text.equalsIgnoreCase("")){
                            text = null;
                        }else{
                            text =text.replaceAll("\\n", "<br />");
                        }

                        mOnEventListener.onClickSave(position,text);

                        RecycleBen bean = new RecycleBen(recycleBen.getViewType(), recycleBen.getDialogType(),
                                recycleBen.getTableId(), recycleBen.getDescription(), text, recycleBen.getCardOptions());

                        mOnEventListener.onClickSave(position,bean);


                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog _dialog, @NonNull DialogAction which) {
                        // TODO
                        dialog.dismiss();
                        mOnEventListener.onClickCancel(position);

                    }
                });
        //.positiveText(R.string.positive);

        dialog = builder.build();

        View view = dialog.getCustomView();

        if (view != null) {
            data_editText = (EditText) view.findViewById(R.id.data_editText);
        }
        if(propertyData != null) propertyData = propertyData.replaceAll("<br />","\n");
        data_editText.setText(propertyData);

        dialog.show();
    }

    public void startDialogPriceInput(ArrayList<UserData> _dataPropName, int _position, final RecycleBen recycleBen) {
        String propertyName = _dataPropName.get(_position).getPropertyName();
        String propertyData = (String) _dataPropName.get(_position).getPropertyDataObj();

        position = _position;
        Log.d("PropInfo", "startDialogTextInput: "+position);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(propertyName)
                .titleColorRes(R.color.ActionBarColor)
                .cancelable(false)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.add_prop_price_dialog, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog _dialog, @NonNull DialogAction which) {
                        // TODO GET DATE
                        dialog.dismiss();
                        String text = data_editText.getText().toString();

                        mOnEventListener.onClickSave(position,text);

                        RecycleBen bean = new RecycleBen(recycleBen.getViewType(), recycleBen.getDialogType(),
                                recycleBen.getTableId(), recycleBen.getDescription(), text, recycleBen.getCardOptions());

                        mOnEventListener.onClickSave(position,bean);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog _dialog, @NonNull DialogAction which) {
                        // TODO
                        dialog.dismiss();
                        mOnEventListener.onClickCancel(position);

                    }
                });
        //.positiveText(R.string.positive);

        dialog = builder.build();

        View view = dialog.getCustomView();

        if (view != null) {
            data_editText = (EditText) view.findViewById(R.id.price_editText);
        }


        data_editText.setText(propertyData);

        dialog.show();
    }


    public void startDialogDatePicker(ArrayList<UserData> _dataPropName, int _position, final RecycleBen recycleBen) {
        String propertyName = _dataPropName.get(_position).getPropertyName();
        String propertyData = (String) _dataPropName.get(_position).getPropertyDataObj();

        position = _position;

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(propertyName)
                .titleColorRes(R.color.ActionBarColor)
                .cancelable(false)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.dialog_datepicker, false)
                .cancelable(false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO GET DATE
                        dialog.dismiss();

                        Log.d("PropInfo", "startDialogDatePicker: "+position);

                        int selectedYear = datePicker.getYear();
                        int selectedMonth = datePicker.getMonth()+1;
                        int selectedDay =  datePicker.getDayOfMonth();

                        String date = selectedDay+","+selectedMonth+","+selectedYear;

                        RecycleBen bean = new RecycleBen(recycleBen.getViewType(), recycleBen.getDialogType(),
                                recycleBen.getTableId(), recycleBen.getDescription(), date, recycleBen.getCardOptions());

                        mOnEventListener.onClickSave(position,bean);

                        mOnEventListener.onClickSave(position,date);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        dialog.dismiss();
                    }
                });

        dialog = builder.build();

        //datePicker

        View view = dialog.getCustomView();

        if (view != null) {
            datePicker = (DatePicker) view.findViewById(R.id.datePicker);
            datePicker.setMaxDate(new Date().getTime());

            if(propertyData != null) {
                String[] split = propertyData.split(",");

                int day = Integer.valueOf(split[0]);
                int month = Integer.valueOf(split[1])-1;
                int year = Integer.valueOf(split[2]);

                datePicker.updateDate(year, month, day);
            }
        }

        dialog.show();
    }


    MaterialDialog dialog;


    private mOnClickListener mOnEventListener;

    public void setDialogClickListener(mOnClickListener listener) {
        mOnEventListener = listener;
    }

    public void setBean(RecycleBen recycleBen) {
    }


    public interface mOnClickListener {
        void onClickSave(int position, String data);
        void onClickSave(int position, RecycleBen data);
        void onClickCancel(int position);
    }



}
