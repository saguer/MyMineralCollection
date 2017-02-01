package mymineralcollection.example.org.mymineral.AddMineral.AddMineralList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import mymineralcollection.example.org.mymineral.AddMineral.ObservableObject;
import mymineralcollection.example.org.mymineral.Class.MyKeyConstants;

/**
 * Created by Santiago on 10/25/2016.
 */
public class ReceiverAddMineralList extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle _bundle = intent.getExtras();

        Log.e("AddMineralListActivity", "item_done --> "+_bundle.getInt( MyKeyConstants.BUNDLE_ITEM_DONE,0));
        Log.e("AddMineralListActivity", "item_name --> "+_bundle.getString( MyKeyConstants.BUNDLE_ITEM_NAME));

        _bundle.putInt( MyKeyConstants.BUNDLE_MINERAL_LIST, ObservableObject.MINERAL_LIST);

        ObservableObject.getInstance().updateValue(_bundle);

    }
}
