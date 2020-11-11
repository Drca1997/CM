package com.example.guerradasestrelas;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageButton;

import java.util.Arrays;

public class Jogo {

    private Jogador [] jogadores;
    //private CardSlot[] cardSlots;
    private int turno; //0 - vez do jogador 1, 1- vez do jogador 2. Ou seja, posição dos jogadores no array "jogadores"

    public Jogo(Context context, View view){

        Singleton.getInstance();
        Singleton.context = context;
        Singleton.view = view;

        //cardSlots = new CardSlot[Singleton.TAMANHO_FILAS]; se calhar nao vai ser necessario, pois cada jgoaddor tem cardslots do seu campo respetivo
        //BuildCardSlots(view);

        BaseDados bd = new BaseDados(context);
        Carta [] allCards= bd.GetAllCards();

        Utils.baralhaBaralho(allCards);
        Utils.PrintBaralho(allCards);

        Carta[] baralho1 = Arrays.copyOfRange(allCards, 0, Singleton.NUM_CARTAS/ 2);
        Carta[] baralho2 = Arrays.copyOfRange(allCards, Singleton.NUM_CARTAS/2, allCards.length);
        jogadores = new Jogador [Singleton.NUM_JOGADORES];
        Jogador jogador1 = new Jogador(baralho1, 1);
        Jogador jogador2 = new Jogador(baralho2, 2);
        jogadores[0] = jogador1;
        jogadores[1] = jogador2;
        this.turno = 0;
        Utils.baralhaBaralho(jogador1.getBaralho());
        Utils.baralhaBaralho(jogador2.getBaralho());
        System.out.println("Baralho");
        Utils.PrintBaralho(jogador1.getBaralho());
        jogador1.InicializaMao();
        //jogador2.InicializaMao();

    }

    public void UpdatePoder(){
        for (Jogador jogador : jogadores){
            for (int fila = 0; fila < Singleton.NUM_FILAS; fila++){
                int soma = 0;
                int slot = 0;
                while (jogador.getCampo()[fila][slot].getCarta() != null){
                    soma += jogador.getCampo()[fila][slot].getCarta().getPoder();
                    slot++;
                }
                jogador.getPoder()[fila] = soma;
            }
            jogador.getPoder()[2] = jogador.getPoder()[0] + jogador.getPoder()[1]; //soma Poder das Filas
        }
    }

    public void UpdateTurno(){ //evocada quando jogador passar a ronda. (onclick listener do botao de passar jogada)
        if (turno == Singleton.NUM_JOGADORES - 1){
            turno = 0;
        }
        else{
            turno++;
        }
    }

    public int getTurno(){
        return turno;
    }

    public Jogador [] getJogadores(){
        return jogadores;
    }

    //loop:
    //jogadores[turno] joga carta:
    // - remover carta da mao
    // - jogadores[turno].JogaCarta(carta)
    // - carta.ExecutaHabilidade();
    // -- cartas.addModificador.
    // - UpdatePoder()
    // - update turno



}
