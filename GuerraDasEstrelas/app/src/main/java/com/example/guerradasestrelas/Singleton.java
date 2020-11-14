package com.example.guerradasestrelas;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.Random;

public class Singleton  {

    public static final int TAMANHO_FILAS = 5;
    public static final int NUM_FILAS = 2;
    public static final int NUM_CARTAS_INICIAIS = 5;
    public static final int NUM_JOGADORES = 2;
    public static final int NUM_CARTAS = 40;
    public static final int LIM_MAO = 5;


    private static Singleton INSTANCE = null;
    public static Context context;
    public static View view;

    private Singleton() {};

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();

        }
        return(INSTANCE);
    }

    /*
    //driver code
    public static Carta [] GetAllCards(){
        Carta [] allCards = new Carta[NUM_CARTAS];
        allCards[0] = new Carta("Diogo Morgado", 3, R.drawable.diogo_morgado, R.drawable.diogo_morgado, 0);
        allCards[1] = new Carta("Fernando Mendes", 10, R.drawable.fernando_mendes, R.drawable.fernando_mendes, 0);
        allCards[2] = new Carta("Cristiano Ronaldo", 7, R.drawable.cristiano_ronaldo, R.drawable.cristiano_ronaldo, 0);
        allCards[3] = new Carta("José Castelo Branco", 7, R.drawable.jose_castelo_branco, R.drawable.jose_castelo_branco, 0);
        allCards[4] = new Carta("Maya", 1, R.drawable.maya, R.drawable.maya, 0);
        allCards[5] = new Carta("Daniel Oliveira", 7, R.drawable.daniel_oliveira, R.drawable.daniel_oliveira, 0);
        allCards[6] = new Carta("António de Oliveira Salazar", 7, R.drawable.salazar, R.drawable.salazar, 0);
        allCards[7] = new Carta("Marcelo Rebelo de Sousa", 6, R.drawable.marcelo_rebelo_sousa, R.drawable.marcelo_rebelo_sousa, 0);
        allCards[8] = new Carta("Luciana Abreu", 2, R.drawable.luciana_abreu, R.drawable.luciana_abreu, 0);
        allCards[9] = new Carta("D.Afonso Henriques", 9, R.drawable.afonso_henriques, R.drawable.afonso_henriques, 0);
        allCards[10] = new Carta("Éder", 9, R.drawable.eder, R.drawable.eder, 0);
        allCards[11] = new Carta("Estudantes Académicos", 9, R.drawable.estudantes, R.drawable.estudantes, 0);
        allCards[12] = new Carta("O Emplastro", 0, R.drawable.o_emplastro, R.drawable.o_emplastro, 0);
        allCards[13] = new Carta("Camões",3,  R.drawable.camoes, R.drawable.camoes, 0);

        return allCards;
    }
    */


}