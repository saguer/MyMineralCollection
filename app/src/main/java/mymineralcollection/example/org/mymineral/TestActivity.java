package mymineralcollection.example.org.mymineral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test2);

        Intent myIntent = new Intent(TestActivity.this, mymineralcollection.example.org.mywebcrawler.MainActivity.class);

        TestActivity.this.startActivity(myIntent);
    }
}
