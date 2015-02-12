package com.yanick.nicolas.eric.memory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    static final int NB_RANKING_PLAYERS = 5;

    static final int SINGLE_GAME_REQUEST = 0;
    static final int DUAL_GAME_REQUEST = 1;
    static final int HIGH_SCORE_REQUEST = 2;

    public String lastPlayer1Name = "Bobby";
    public String lastPlayer2Name = "Johny";

    public Player[] ranking = new Player[NB_RANKING_PLAYERS];

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getPreferences(Context.MODE_PRIVATE);
        String rank1 = prefs.getString("rank1Name","-");
        int score1 = prefs.getInt("rank1Score", 0);
        ranking[0] = new Player(rank1);
        String rank2 = prefs.getString("rank2Name","-");
        int score2 = prefs.getInt("rank2Score", 0);
        ranking[1] = new Player(rank2);
        String rank3 = prefs.getString("rank3Name","-");
        int score3 = prefs.getInt("rank3Score", 0);
        ranking[2] = new Player(rank3);
        String rank4 = prefs.getString("rank4Name","-");
        int score4 = prefs.getInt("rank4Score", 0);
        ranking[3] = new Player(rank4);
        String rank5 = prefs.getString("rank5Name","-");
        int score5 = prefs.getInt("rank5Score", 0);
        ranking[4] = new Player(rank5);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rank1Name", ranking[0].Name);
        editor.putInt("rank1Score", ranking[0].Score);
        editor.putString("rank2Name", ranking[1].Name);
        editor.putInt("rank2Score", ranking[1].Score);
        editor.putString("rank3Name", ranking[2].Name);
        editor.putInt("rank3Score", ranking[2].Score);
        editor.putString("rank4Name", ranking[3].Name);
        editor.putInt("rank4Score", ranking[3].Score);
        editor.putString("rank5Name", ranking[4].Name);
        editor.putInt("rank5Score", ranking[4].Score);
        editor.commit();
    }

    public void onePlayerBtnOnClick(View v){
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_name_request);
        dialog.setTitle("Player name");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.playername);
        text.setText(lastPlayer1Name);
        String currentPlayer1Name = lastPlayer1Name;

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EditText text = (EditText) dialog.findViewById(R.id.playername);
                lastPlayer1Name = text.getText().toString();
                startSingle(lastPlayer1Name);
            }
        });

        dialog.show();
    }

    public void startSingle(String playerName){
        Intent intent = new Intent(this, Jeu.class);
        intent.putExtra("isOnePlayer", true);
        intent.putExtra("player1Name", playerName);
        startActivityForResult(intent, SINGLE_GAME_REQUEST);
    }

    public void twoPlayerBtnOnClick(View v){
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_name_request);
        dialog.setTitle("Player name");

        // set the custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.playername);
        text.setText(lastPlayer1Name);
        String currentPlayer1Name = lastPlayer1Name;

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EditText text = (EditText) dialog.findViewById(R.id.playername);
                lastPlayer1Name = text.getText().toString();

                dialog.setTitle("Player 2 name");
                text.setText(lastPlayer2Name);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        EditText text = (EditText) dialog.findViewById(R.id.playername);
                        lastPlayer2Name = text.getText().toString();

                        startDual(lastPlayer1Name, lastPlayer2Name);
                    }
                });

                dialog.show();
            }
        });

        dialog.show();
    }

    public void startDual(String playerName1, String playerName2){
        Intent intent = new Intent(this, Jeu.class);
        intent.putExtra("isOnePlayer", false);
        intent.putExtra("player1Name", playerName1);
        intent.putExtra("player2Name", playerName2);
        startActivityForResult(intent, DUAL_GAME_REQUEST);
    }

    public void highScoresBtnOnClick(View v){
        Intent intent = new Intent(this, RankingActivity.class);
        for(int i = 0; i < NB_RANKING_PLAYERS; i++){
            intent.putExtra("rank"+(i+1)+"Name", ranking[i].Name);
            intent.putExtra("rank"+(i+1)+"Score", ranking[i].Score);
        }
        startActivityForResult(intent, HIGH_SCORE_REQUEST);
    }

    public void quitBtnOnClick(View v){
        finish();
        System.exit(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case SINGLE_GAME_REQUEST:
                if(resultCode == RESULT_OK){
                    String nomJoueur = intent.getStringExtra("nomJoueur1");
                    int score = intent.getIntExtra("scoreJoueur1",0);
                    Player joueur = new Player(nomJoueur, score);
                    rankPlayer(joueur);
                }
                break;
            case DUAL_GAME_REQUEST:
                if(resultCode == RESULT_OK){
                    String nomJoueur1 = intent.getStringExtra("nomJoueur1");
                    int score1 = intent.getIntExtra("scoreJoueur1",0);
                    Player joueur1 = new Player(nomJoueur1, score1);
                    rankPlayer(joueur1);
                    String nomJoueur2 = intent.getStringExtra("nomJoueur2");
                    int score2 = intent.getIntExtra("scoreJoueur2",0);
                    Player joueur2 = new Player(nomJoueur2, score2);
                    rankPlayer(joueur2);
                }
                break;
        }
    }

    private void rankPlayer(Player player){
        for(int i = 0; i < NB_RANKING_PLAYERS; i++){
            if(ranking[i].Score <= player.Score){
                // Player should be at position i in ranking and others are shifted
                Player toShift = player;
                for(int j = i; j < NB_RANKING_PLAYERS; j++){
                    Player temp = ranking[j];
                    ranking[j] = toShift;
                    toShift = temp;
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
