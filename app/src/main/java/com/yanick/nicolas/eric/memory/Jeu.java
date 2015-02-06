package com.yanick.nicolas.eric.memory;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Jeu extends ActionBarActivity {

    boolean isOnePlayer;
    boolean partieTerminee;
    boolean tourJoueur1;
    Integer carteTournee;
    View derniereCarte;
    int ptsP1;
    int ptsP2;

    Random rand = new Random();
    Integer[] ImagesIds = {
            R.drawable.luigi,
            R.drawable.mario,
            R.drawable.dk,
            R.drawable.link,
            R.drawable.samus,
            R.drawable.falcon,
            R.drawable.ness,
            R.drawable.yoshi,
            R.drawable.kirby,
            R.drawable.fox,
            R.drawable.pika,
            R.drawable.jiggs
    };

    Integer[][] cards = new Integer[4][6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        Bundle extras = getIntent().getExtras();
        boolean isOnePlayer;
        if(extras == null) {
            isOnePlayer= false;
        } else {
            isOnePlayer= extras.getBoolean("isOnePlayer");
        }

        for(int i = 0; i < cards.length; ++i){
            for(int j = 0; j < cards[i].length; ++j) {
                cards[i][j] = 255;
            }
        }
        for(int i = 0; i < ImagesIds.length; ++i) {
            placerCarte(i);
            placerCarte(i);
        }

        carteTournee = 255;
        ptsP1 = 0;
        ptsP2 = 0;
        tourJoueur1 = true;
        partieTerminee = false;
        derniereCarte = null;

        initialiserBoutons();
    }

    private void gererPartie(Integer carte, View v){
        if(carteTournee != 255 && derniereCarte != v)
        {
            if(carte == carteTournee)
            {
                if(tourJoueur1) {
                    ++ptsP1;
                    final TextView txtScore1 = (TextView) findViewById(R.id.scoreP1);
                    txtScore1.setText(String.valueOf(ptsP1));
                }
                else {
                    ++ptsP2;
                    final TextView txtScore2 = (TextView) findViewById(R.id.scoreP2);
                    txtScore2.setText(String.valueOf(ptsP2));
                }

                retirerCartes(v);
            }
            else
            {
                tourJoueur1 = !tourJoueur1;

                //TODO:changer image pour savoir le joueur actif
                final ImageView imageP1 = (ImageView) findViewById(R.id.imageP1);
                final ImageView imageP2 = (ImageView) findViewById(R.id.imageP2);

                retournerCartes(v);
            }
        }
        else
            carteTournee = carte;
    }

    private void retirerCartes(View v){
        derniereCarte.setEnabled(false);
        v.setEnabled(false);
        derniereCarte.setVisibility(View.INVISIBLE);
        v.setVisibility(View.INVISIBLE);
        carteTournee = 255;
    }

    private void retournerCartes(View v){
        derniereCarte.setBackgroundResource(R.drawable.facedown);
        v.setBackgroundResource(R.drawable.facedown);
        carteTournee = 255;
    }

    private void click(View v, int x, int y){
        v.setBackground(getResources().getDrawable(cards[x][y]));
        gererPartie(cards[x][y], v);
        derniereCarte = v;
    }

    private void initialiserBoutons(){
        final ImageButton carte1 = (ImageButton) findViewById(R.id.imageButton);
        carte1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 0);
            }
        });

        final ImageButton carte2 = (ImageButton) findViewById(R.id.imageButton2);
        carte2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 1);
            }
        });

        final ImageButton carte3 = (ImageButton) findViewById(R.id.imageButton3);
        carte3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 2);
            }
        });

        final ImageButton carte4 = (ImageButton) findViewById(R.id.imageButton4);
        carte4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 3);
            }
        });

        final ImageButton carte5 = (ImageButton) findViewById(R.id.imageButton5);
        carte5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 4);
            }
        });

        final ImageButton carte6 = (ImageButton) findViewById(R.id.imageButton6);
        carte6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0, 5);
            }
        });

        final ImageButton carte7 = (ImageButton) findViewById(R.id.imageButton7);
        carte7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 0);
            }
        });

        final ImageButton carte8 = (ImageButton) findViewById(R.id.imageButton8);
        carte8.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 1);
            }
        });

        final ImageButton carte9 = (ImageButton) findViewById(R.id.imageButton9);
        carte9.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 2);
            }
        });

        final ImageButton carte10 = (ImageButton) findViewById(R.id.imageButton10);
        carte10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 3);
            }
        });

        final ImageButton carte11 = (ImageButton) findViewById(R.id.imageButton11);
        carte11.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 4);
            }
        });

        final ImageButton carte12 = (ImageButton) findViewById(R.id.imageButton12);
        carte12.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1, 5);
            }
        });

        final ImageButton carte13 = (ImageButton) findViewById(R.id.imageButton13);
        carte13.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 0);
            }
        });

        final ImageButton carte14 = (ImageButton) findViewById(R.id.imageButton14);
        carte14.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 1);
            }
        });

        final ImageButton carte15 = (ImageButton) findViewById(R.id.imageButton15);
        carte15.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 2);
            }
        });

        final ImageButton carte16 = (ImageButton) findViewById(R.id.imageButton16);
        carte16.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 3);
            }
        });

        final ImageButton carte17 = (ImageButton) findViewById(R.id.imageButton17);
        carte17.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 4);
            }
        });

        final ImageButton carte18 = (ImageButton) findViewById(R.id.imageButton18);
        carte18.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2, 5);
            }
        });

        final ImageButton carte19 = (ImageButton) findViewById(R.id.imageButton19);
        carte19.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 0);
            }
        });

        final ImageButton carte20 = (ImageButton) findViewById(R.id.imageButton20);
        carte20.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 1);
            }
        });

        final ImageButton carte21 = (ImageButton) findViewById(R.id.imageButton21);
        carte21.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 2);
            }
        });

        final ImageButton carte22 = (ImageButton) findViewById(R.id.imageButton22);
        carte22.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 3);
            }
        });

        final ImageButton carte23 = (ImageButton) findViewById(R.id.imageButton23);
        carte23.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 4);
            }
        });

        final ImageButton carte24 = (ImageButton) findViewById(R.id.imageButton24);
        carte24.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3, 5);
            }
        });
    }

    private void placerCarte(int i){
        boolean espaceLibre = false;
        while(!espaceLibre){
            int position = rand.nextInt(24);
            int x = position % 4;
            int y = position / 4;
            if(cards[x][y] == 255){
                espaceLibre = true;
                cards[x][y] = ImagesIds[i];
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
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
