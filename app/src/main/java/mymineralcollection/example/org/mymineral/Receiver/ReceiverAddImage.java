package mymineralcollection.example.org.mymineral.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import mymineralcollection.example.org.mymineral.AddMineral.ObservableObject;

public class ReceiverAddImage extends BroadcastReceiver {
    public ReceiverAddImage() {
    }

        @Override
        public void onReceive(final Context _context, final Intent intent) {
            final Bundle extras = intent.getExtras();

            Log.e("ItemConsumer", "queue_empty -> queueEmpty? "+extras.getBoolean("queue_empty",false));

            if(extras.getBoolean("queue_empty",false)) {
                //Bundle bundle = new Bundle();
                //bundle.putBoolean("value", true);
                //intent.putExtras(bundle);

                Bundle _bundle = new Bundle();

                _bundle.putInt("HIDE_DIALOG", ObservableObject.HIDE_DIALOG);
                _bundle.putInt("LAUNCH_ACTIVITY",ObservableObject.LAUNCH_ACTIVITY);

                ObservableObject.getInstance().updateValue(_bundle);

            }else{

                Bundle _bundle = new Bundle();
                _bundle.putInt("SHOW_DIALOG",ObservableObject.SHOW_DIALOG);

                ObservableObject.getInstance().updateValue(_bundle);

            }


        }

    }

