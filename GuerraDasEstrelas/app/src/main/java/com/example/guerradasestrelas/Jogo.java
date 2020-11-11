package com.example.guerradasestrelas;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;

public class Jogo {

    private Jogador [] jogadores;
    private CardSlot[] handSlots;
    private TextView playerLabel;
    private int turno; //0 - vez do jogador 1, 1- vez do jogador 2. Ou seja, posição dos jogadores no array "jogadores"

    public Jogo(Context context, View view){

        Singleton.getInstance();
        Singleton.context = context;
        Singleton.view = view;

        handSlots = new CardSlot[Singleton.LIM_MAO];
        BuildHandSlots();

        playerLabel = view.findViewById(R.id.message);
        BaseDados bd = new BaseDados(context);
        Carta [] allCards= bd.GetAllCards();

        Utils.baralhaBaralho(allCards);
        Utils.PrintBaralho(allCards, "Cartas Recolhidas da Base de Dados");

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
        Utils.PrintBaralho(jogador1.getBaralho(), "Baralho do Jogador 1");
        Utils.PrintBaralho(jogador2.getBaralho(), "Baralho do Jogador 2");
        jogador1.InicializaMao();
        jogador2.InicializaMao();
        updatePlayerLabel();
        jogador1.MostraMao(handSlots);
    }

    private void BuildHandSlots(){
        handSlots[0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.handslot1));
        handSlots[1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.handslot2));
        handSlots[2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.handslot3));
        handSlots[3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.handslot4));
        handSlots[4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.handslot5));
    }

    public void updatePlayerLabel(){
        playerLabel.setText("Mão do Jogador " + (turno + 1));
    }

    public void updatePoder(){
        for (Jogador jogador : jogadores){
            for (int fila = 0; fila < Singleton.NUM_FILAS; fila++){
                int soma = 0;
                int slot = 0;
                CardSlot [] filaSlots = jogador.getCampo()[fila];
                while (slot < filaSlots.length && filaSlots[slot].getCarta() != null){
                    soma += jogador.getCampo()[fila][slot].getCarta().getPoder();
                    slot++;
                }
                jogador.getPoder()[fila] = soma;
            }
            jogador.getPoder()[2] = jogador.getPoder()[0] + jogador.getPoder()[1]; //soma Poder das Filas
        }
    }

    public void updateTurno(){ //evocada quando jogador passar a ronda. (onclick listener do botao de passar jogada)
        if (turno == Singleton.NUM_JOGADORES - 1){
            turno = 0;
        }
        else{
            turno++;
        }
    }

    public void acabaJogada(){
        updatePoder();
        updateTurno();
        updatePlayerLabel();
        if (!jogadores[turno].isSkipped()){
            jogadores[turno].tiraCartaDoBaralho(); //problema: como evitar que jogador 2 nao tire carta logo ao inicio?
            jogadores[turno].MostraMao(handSlots);
        }
        else{
            updateTurno();
            updatePlayerLabel();
        }
    }

    public int getTurno(){
        return turno;
    }

    public Jogador [] getJogadores(){
        return jogadores;
    }

    public Jogador getJogadorAtual(){
        return jogadores[turno];
    }

    public CardSlot[] getHandSlots(){
        return handSlots;
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
