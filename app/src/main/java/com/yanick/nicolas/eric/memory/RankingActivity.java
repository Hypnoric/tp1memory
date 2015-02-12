package com.yanick.nicolas.eric.memory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class RankingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            TextView rank1 = (TextView) findViewById(R.id.rank1player);
            rank1.setText(extras.getString("rank1Name"));
            TextView rank2 = (TextView) findViewById(R.id.rank2player);
            rank2.setText(extras.getString("rank2Name"));
            TextView rank3 = (TextView) findViewById(R.id.rank3player);
            rank3.setText(extras.getString("rank3Name"));
            TextView rank4 = (TextView) findViewById(R.id.rank4player);
            rank4.setText(extras.getString("rank4Name"));
            TextView rank5 = (TextView) findViewById(R.id.rank5player);
            rank5.setText(extras.getString("rank5Name"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
