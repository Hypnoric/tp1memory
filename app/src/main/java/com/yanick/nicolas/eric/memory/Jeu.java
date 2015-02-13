package com.yanick.nicolas.eric.memory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;

public class Jeu extends Activity {

    private Handler waitHandler = new Handler();
    private ArrayList<ImageView> cartesView = new ArrayList<ImageView>();

    boolean isOnePlayer;
    boolean partieTerminee;
    boolean tourJoueur1;
    boolean combinaisonTrouvee;
    boolean cartesDifferentes;
    Integer carteTournee;
    View carteCourante;
    View derniereCarte;
    View tempCarte;
    int ptsP1;
    int ptsP2;
    int temp;

    String player1Name = null;
    String player2Name = null;

    LimitedQueue<Integer> memoireIds = new LimitedQueue<>(4);
    LimitedQueue<ImageView> memoireCartes = new LimitedQueue<>(4);

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

    Integer[] cards = new Integer[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            isOnePlayer= false;
        } else {
            isOnePlayer= extras.getBoolean("isOnePlayer");
            player1Name = extras.getString("player1Name");
            final TextView txtP1 = (TextView) findViewById(R.id.txtPlayer1);
            txtP1.setText(player1Name);

            if(!isOnePlayer){
                player2Name = extras.getString("player2Name");
                final TextView txtP2 = (TextView) findViewById(R.id.txtPlayer2);
                txtP2.setText(player2Name);
            }
        }

        for(int i = 0; i < 24; ++i){
            cards[i] = 255;
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
        cartesDifferentes = false;
        combinaisonTrouvee = false;

        initialiserBoutons();
    }

    public void onBackPressed() {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Quitter")
                .setMessage("Voulez vous quitter la partie?")

                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    /*
    Fonction qui s'occupe de gerer le tour de jeu de l'AI.
    Il se souvient des 4 derniere cartes qui ont ete retournees.
    S'il y a 2 cartes pareilles en memoir il les retournes.
    Sinon il tourne une carte aleatoirement parmis celles qu'il ne connais pas.
    Il verifie ensuite s'il connais la position de la 2eme carte correspondante.
    S'il ne la connais pas il tourne une deuxieme carte aleatoirement.
    */
    private void jeuAI(){
        disableAllButtons();
        //int indexCarte1 = 255;
        //int indexCarte2 = 255;
        //comparaisonCartes:
        for(int i = 0; i < memoireIds.size(); ++i ) {
            for(int j = i+1; j < memoireIds.size(); ++j ) {
                if(memoireIds.get(i) == memoireIds.get(j)) {
                    memoireIds.remove(i);
                    memoireIds.remove(j - 1);
                    memoireCartes.get(i).callOnClick();
                    memoireCartes.get(j).callOnClick();
                    memoireCartes.remove(i);
                    memoireCartes.remove(j - 1);
                    // if we get here, we found cards and are done
                    return;
                }
            }
        }

        //If we get here, we havent found any pattern matching and needs to search
        int nouvelleCarte = rand.nextInt(24);
        while(memoireCartes.contains(cartesView.get(nouvelleCarte)) || cartesView.get(nouvelleCarte).getVisibility() == View.INVISIBLE){
            nouvelleCarte = rand.nextInt(24);
        }

        if(memoireIds.contains(cards[nouvelleCarte])){
            tempCarte = memoireCartes.get(memoireIds.indexOf(cards[nouvelleCarte]));
            cartesView.get(nouvelleCarte).callOnClick();
            waitHandler.postDelayed(new Runnable() {
                public void run() {
                    tempCarte.callOnClick();
                }
            }, 1000);
        }
        else {
            cartesView.get(nouvelleCarte).callOnClick();
            int nouvelleCarte2 = rand.nextInt(24);
            while (memoireCartes.contains(cartesView.get(nouvelleCarte2)) || cartesView.get(nouvelleCarte2).getVisibility() == View.INVISIBLE || nouvelleCarte == nouvelleCarte2) {
                nouvelleCarte2 = rand.nextInt(24);
            }
            temp = nouvelleCarte2;
            waitHandler.postDelayed(new Runnable() {
                public void run() {
                    cartesView.get(temp).callOnClick();
                }
            }, 1000);
        }
    }

    /*
    Fonction qui gere la logique de la partie lorsqu'une carte est retournee.
    S'occupe d'attribuer les points et retirer les cartes si 2 cartes pareilles sont choisies.
    Retourne les cartes face cachees si les cartes sont differentes.
     */
    private void gererPartie(Integer carte, View v){
        carteCourante = v;
        if(carteTournee != 255 && derniereCarte != carteCourante)
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
                disableAllButtons();
                waitHandler.postDelayed(new Runnable() {
                    public void run() {
                        enableAllButtons();
                        retirerCartes();
                    }
                }, 2000);

                //retirerCartes(v);
                combinaisonTrouvee = true;
            }
            else
            {
                if(!memoireCartes.contains((ImageView) v))
                {
                    memoireIds.add(carte);
                    memoireCartes.add((ImageView) v);
                }
                tourJoueur1 = !tourJoueur1;

                final ImageView imageP1 = (ImageView) findViewById(R.id.imageP1);
                final ImageView imageP2 = (ImageView) findViewById(R.id.imageP2);
                if(tourJoueur1) {
                    imageP1.setBackgroundResource(R.drawable.p1light);
                    imageP2.setBackgroundResource(R.drawable.p2);
                }
                else{
                    imageP1.setBackgroundResource(R.drawable.p1);
                    imageP2.setBackgroundResource(R.drawable.p2light);
                }

                disableAllButtons();
                waitHandler.postDelayed(new Runnable() {
                    public void run() {
                        enableAllButtons();
                        retournerCartes();
                    }
                }, 2000);

                //retournerCartes(v);
                cartesDifferentes = true;
            }
            //tempCarte = derniereCarte;
        }
        else {
            if(!memoireCartes.contains((ImageView) v))
            {
                memoireIds.add(carte);
                memoireCartes.add((ImageView) v);
            }

            carteTournee = carte;
            derniereCarte = carteCourante;
        }
    }

    /*
    Enleve les 2 dernieres cartes retournees du jeu.
     */
    private void retirerCartes(){
        derniereCarte.setEnabled(false);
        carteCourante.setEnabled(false);
        derniereCarte.setVisibility(View.INVISIBLE);
        carteCourante.setVisibility(View.INVISIBLE);
        carteTournee = 255;

        //cartesView.set(cartesView.indexOf(carteCourante), null);
        //cartesView.set(cartesView.indexOf(derniereCarte), null);

        if(!carteRestante()){
            // Fin de la partie. Retour au menu
            Intent returnIntent = new Intent();
            returnIntent.putExtra("nomJoueur1",player1Name);
            returnIntent.putExtra("scoreJoueur1",ptsP1);
            if(!isOnePlayer){
                returnIntent.putExtra("nomJoueur2",player2Name);
                returnIntent.putExtra("scoreJoueur2",ptsP2);
            }
            setResult(RESULT_OK,returnIntent);
            finish();
        }

        derniereCarte = null;
        carteCourante = null;
        if(isOnePlayer && !tourJoueur1)
            jeuAI();
    }

    /*
    Tourne les deux dernieres cartes choisie face cachees
     */
    private void retournerCartes(){
        derniereCarte.setBackgroundResource(R.drawable.facedown);
        carteCourante.setBackgroundResource(R.drawable.facedown);
        carteTournee = 255;
        derniereCarte = null;
        carteCourante = null;
        if(isOnePlayer && !tourJoueur1)
            jeuAI();
    }

    /*
    Tourne la carte cliquee et appel la fonction gererPartie pour la carte cliquee
     */
    private void click(View v, int x){
        /*if(combinaisonTrouvee){
            combinaisonTrouvee = false;
            retirerCartes();
        }
        else if(cartesDifferentes){
            cartesDifferentes = false;
            retournerCartes();
        }*/
        v.setBackground(getResources().getDrawable(cards[x].intValue()));
        gererPartie(cards[x], v);
    }

    /*
    Initialise les boutons des cartes.
     */
    private void initialiserBoutons(){
        final ImageButton carte1 = (ImageButton) findViewById(R.id.imageButton);
        carte1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 0);
            }
        });
        cartesView.add(carte1);

        final ImageButton carte2 = (ImageButton) findViewById(R.id.imageButton2);
        carte2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 1);
            }
        });
        cartesView.add(carte2);

        final ImageButton carte3 = (ImageButton) findViewById(R.id.imageButton3);
        carte3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 2);
            }
        });
        cartesView.add(carte3);

        final ImageButton carte4 = (ImageButton) findViewById(R.id.imageButton4);
        carte4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 3);
            }
        });
        cartesView.add(carte4);

        final ImageButton carte5 = (ImageButton) findViewById(R.id.imageButton5);
        carte5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 4);
            }
        });
        cartesView.add(carte5);

        final ImageButton carte6 = (ImageButton) findViewById(R.id.imageButton6);
        carte6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 5);
            }
        });
        cartesView.add(carte6);

        final ImageButton carte7 = (ImageButton) findViewById(R.id.imageButton7);
        carte7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 6);
            }
        });
        cartesView.add(carte7);

        final ImageButton carte8 = (ImageButton) findViewById(R.id.imageButton8);
        carte8.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 7);
            }
        });
        cartesView.add(carte8);

        final ImageButton carte9 = (ImageButton) findViewById(R.id.imageButton9);
        carte9.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 8);
            }
        });
        cartesView.add(carte9);

        final ImageButton carte10 = (ImageButton) findViewById(R.id.imageButton10);
        carte10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 9);
            }
        });
        cartesView.add(carte10);

        final ImageButton carte11 = (ImageButton) findViewById(R.id.imageButton11);
        carte11.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 10);
            }
        });
        cartesView.add(carte11);

        final ImageButton carte12 = (ImageButton) findViewById(R.id.imageButton12);
        carte12.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 11);
            }
        });
        cartesView.add(carte12);

        final ImageButton carte13 = (ImageButton) findViewById(R.id.imageButton13);
        carte13.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 12);
            }
        });
        cartesView.add(carte13);

        final ImageButton carte14 = (ImageButton) findViewById(R.id.imageButton14);
        carte14.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 13);
            }
        });
        cartesView.add(carte14);

        final ImageButton carte15 = (ImageButton) findViewById(R.id.imageButton15);
        carte15.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 14);
            }
        });
        cartesView.add(carte15);

        final ImageButton carte16 = (ImageButton) findViewById(R.id.imageButton16);
        carte16.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 15);
            }
        });
        cartesView.add(carte16);

        final ImageButton carte17 = (ImageButton) findViewById(R.id.imageButton17);
        carte17.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 16);
            }
        });
        cartesView.add(carte17);

        final ImageButton carte18 = (ImageButton) findViewById(R.id.imageButton18);
        carte18.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 17);
            }
        });
        cartesView.add(carte18);

        final ImageButton carte19 = (ImageButton) findViewById(R.id.imageButton19);
        carte19.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 18);
            }
        });
        cartesView.add(carte19);

        final ImageButton carte20 = (ImageButton) findViewById(R.id.imageButton20);
        carte20.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 19);
            }
        });
        cartesView.add(carte20);

        final ImageButton carte21 = (ImageButton) findViewById(R.id.imageButton21);
        carte21.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 20);
            }
        });
        cartesView.add(carte21);

        final ImageButton carte22 = (ImageButton) findViewById(R.id.imageButton22);
        carte22.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 21);
            }
        });
        cartesView.add(carte22);

        final ImageButton carte23 = (ImageButton) findViewById(R.id.imageButton23);
        carte23.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 22);
            }
        });
        cartesView.add(carte23);

        final ImageButton carte24 = (ImageButton) findViewById(R.id.imageButton24);
        carte24.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v, 23);
            }
        });
        cartesView.add(carte24);
    }

    /*
    Attribue une position dans la grille pour l'id de la carte recue en parametre.
     */
    private void placerCarte(int i){
        boolean espaceLibre = false;
        while(!espaceLibre){
            int position = rand.nextInt(24);
            if(cards[position] == 255){
                espaceLibre = true;
                cards[position] = ImagesIds[i];
            }
        }
    }

    /*
    Mets l'attribut de tous les boutons isEnabled a true
     */
    private void enableAllButtons(){
        for(int i = 0; i < cartesView.size(); i++){
            if(cartesView.get(i) != null)
                cartesView.get(i).setEnabled(true);
        }
    }

    /*
    Mets l'attribut de tous les boutons isEnabled a false
     */
    private void disableAllButtons(){
        for(int i = 0; i < cartesView.size(); i++){
            if(cartesView.get(i) != null)
                cartesView.get(i).setEnabled(false);
        }
    }

    private boolean carteRestante(){
        boolean restantes = false;
        for(int i = 0; i < cartesView.size(); i++){
            if(cartesView.get(i).getVisibility() != View.INVISIBLE)
                restantes = true;
        }
        return restantes;
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
