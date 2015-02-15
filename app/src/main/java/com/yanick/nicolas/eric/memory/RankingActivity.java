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
            String tempName;
            int tempScore;
            boolean tempValid = false;
            tempName = extras.getString("rank1Name");
            tempScore = extras.getInt("rank1Score");
            tempValid = (tempName != null && !tempName.isEmpty() && !tempName.equals("-")) && tempScore >= 0;
            TextView rank1 = (TextView) findViewById(R.id.rank1player);
            rank1.setText("1.    "+(tempValid?(tempName+"    "+tempScore):"-"));

            tempName = extras.getString("rank2Name");
            tempScore = extras.getInt("rank2Score");
            tempValid = (tempName != null && !tempName.isEmpty()) && tempScore >= 0;
            TextView rank2 = (TextView) findViewById(R.id.rank2player);
            rank2.setText("2.    "+(tempValid?(tempName+"    "+tempScore):"-"));

            tempName = extras.getString("rank3Name");
            tempScore = extras.getInt("rank3Score");
            tempValid = (tempName != null && !tempName.isEmpty()) && tempScore >= 0;
            TextView rank3 = (TextView) findViewById(R.id.rank3player);
            rank3.setText("3.    "+(tempValid?(tempName+"    "+tempScore):"-"));

            tempName = extras.getString("rank4Name");
            tempScore = extras.getInt("rank4Score");
            tempValid = (tempName != null && !tempName.isEmpty()) && tempScore >= 0;
            TextView rank4 = (TextView) findViewById(R.id.rank4player);
            rank4.setText("4.    "+(tempValid?(tempName+"    "+tempScore):"-"));

            tempName = extras.getString("rank5Name");
            tempScore = extras.getInt("rank5Score");
            tempValid = (tempName != null && !tempName.isEmpty()) && tempScore >= 0;
            TextView rank5 = (TextView) findViewById(R.id.rank5player);
            rank5.setText("5.    "+(tempValid?(tempName+"    "+tempScore):"-"));
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
