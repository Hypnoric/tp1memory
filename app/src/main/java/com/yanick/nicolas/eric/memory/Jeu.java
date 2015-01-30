package com.yanick.nicolas.eric.memory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;


public class Jeu extends ActionBarActivity {

    boolean isOnePlayer;

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

        initialiserBoutons();

    }

    private void initialiserBoutons(){
        final ImageButton carte1 = (ImageButton) findViewById(R.id.imageButton);
        carte1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][0]));
            }
        });

        final ImageButton carte2 = (ImageButton) findViewById(R.id.imageButton2);
        carte2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][1]));
            }
        });

        final ImageButton carte3 = (ImageButton) findViewById(R.id.imageButton3);
        carte3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][2]));
            }
        });

        final ImageButton carte4 = (ImageButton) findViewById(R.id.imageButton4);
        carte4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][3]));
            }
        });

        final ImageButton carte5 = (ImageButton) findViewById(R.id.imageButton5);
        carte5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][4]));
            }
        });

        final ImageButton carte6 = (ImageButton) findViewById(R.id.imageButton6);
        carte6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[0][5]));
            }
        });

        final ImageButton carte7 = (ImageButton) findViewById(R.id.imageButton7);
        carte7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][0]));
            }
        });

        final ImageButton carte8 = (ImageButton) findViewById(R.id.imageButton8);
        carte8.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][1]));
            }
        });

        final ImageButton carte9 = (ImageButton) findViewById(R.id.imageButton9);
        carte9.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][2]));
            }
        });

        final ImageButton carte10 = (ImageButton) findViewById(R.id.imageButton10);
        carte10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][3]));
            }
        });

        final ImageButton carte11 = (ImageButton) findViewById(R.id.imageButton11);
        carte11.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][4]));
            }
        });

        final ImageButton carte12 = (ImageButton) findViewById(R.id.imageButton12);
        carte12.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[1][5]));
            }
        });

        final ImageButton carte13 = (ImageButton) findViewById(R.id.imageButton13);
        carte13.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][0]));
            }
        });

        final ImageButton carte14 = (ImageButton) findViewById(R.id.imageButton14);
        carte14.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][1]));
            }
        });

        final ImageButton carte15 = (ImageButton) findViewById(R.id.imageButton15);
        carte15.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][2]));
            }
        });

        final ImageButton carte16 = (ImageButton) findViewById(R.id.imageButton16);
        carte16.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][3]));
            }
        });

        final ImageButton carte17 = (ImageButton) findViewById(R.id.imageButton17);
        carte17.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][4]));
            }
        });

        final ImageButton carte18 = (ImageButton) findViewById(R.id.imageButton18);
        carte18.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[2][5]));
            }
        });

        final ImageButton carte19 = (ImageButton) findViewById(R.id.imageButton19);
        carte19.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][0]));
            }
        });

        final ImageButton carte20 = (ImageButton) findViewById(R.id.imageButton20);
        carte20.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][1]));
            }
        });

        final ImageButton carte21 = (ImageButton) findViewById(R.id.imageButton21);
        carte21.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][2]));
            }
        });

        final ImageButton carte22 = (ImageButton) findViewById(R.id.imageButton22);
        carte22.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][3]));
            }
        });

        final ImageButton carte23 = (ImageButton) findViewById(R.id.imageButton23);
        carte23.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][4]));
            }
        });

        final ImageButton carte24 = (ImageButton) findViewById(R.id.imageButton24);
        carte24.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(cards[3][5]));
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
