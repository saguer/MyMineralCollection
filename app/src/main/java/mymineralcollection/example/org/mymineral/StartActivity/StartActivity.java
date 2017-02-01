package mymineralcollection.example.org.mymineral.StartActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mymineralcollection.example.org.mymineral.AddMineral.AddMineralList.AddMineralListActivity;
import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.AddMineralData;
import mymineralcollection.example.org.mymineral.Class.Methods;
import mymineralcollection.example.org.mymineral.MineralList.MineralListActivity;
import mymineralcollection.example.org.mymineral.R;


public class StartActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Methods.checkLoadActivityState(this);

        ImageView addMineral_button = (ImageView) findViewById(R.id.addMineral_ImageView);
        addMineral_button.setOnClickListener(this);
        ImageView viewMineral_button = (ImageView) findViewById(R.id.viewMineral_ImageView);
        viewMineral_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addMineral_ImageView): {

                AddMineralData.initEverything();

                Intent myIntent = new Intent(this, AddMineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StartActivity.this.startActivity(myIntent);

                break;
            }
            case (R.id.viewMineral_ImageView): {

                Intent myIntent = new Intent(this, MineralListActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StartActivity.this.startActivity(myIntent);

                break;
            }
        }
    }
}
