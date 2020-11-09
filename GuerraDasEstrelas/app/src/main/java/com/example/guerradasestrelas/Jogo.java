package com.example.guerradasestrelas;


import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import java.util.Arrays;

public class Jogo {

    private Jogador [] jogadores;
    private CardSlot[] cardSlots;

    private int turno; //0 - vez do jogador 1, 1- vez do jogador 2. Ou seja, posição dos jogadores no array "jogadores"

    public Jogo(Context context, View view){
        Singleton.getInstance();
        Singleton.context = context;
        Singleton.view = view;
        //cardSlots = new CardSlot[Singleton.TAMANHO_FILAS];
        //BuildCardSlots(view);

        Carta [] allCards = Singleton.GetAllCards();
        Singleton.baralhaBaralho(allCards);
        Carta[] baralho1 = Arrays.copyOfRange(allCards, 0, Singleton.NUM_CARTAS/ 2);
        Carta[] baralho2 = Arrays.copyOfRange(allCards, Singleton.NUM_CARTAS/2, allCards.length);
        jogadores = new Jogador [Singleton.NUM_JOGADORES];
        Jogador jogador1 = new Jogador(baralho1, 1);
        Jogador jogador2 = new Jogador(baralho2, 2);
        jogadores[0] = jogador1;
        jogadores[1] = jogador2;
        this.turno = 0;
        Singleton.baralhaBaralho(jogador1.getBaralho());
        Singleton.baralhaBaralho(jogador2.getBaralho());
        System.out.println("Baralho");
        Singleton.PrintBaralho(jogador1.getBaralho());
        jogador1.InicializaMao();
        //jogador2.InicializaMao();

    }

    //so usado depois quando houver slots de tabuleiro
    private void BuildCardSlots(View view){
        cardSlots[0] = new CardSlot((ImageButton) view.findViewById(R.id.handslot1));
        cardSlots[1] = new CardSlot((ImageButton) view.findViewById(R.id.handslot2));
        cardSlots[2] = new CardSlot((ImageButton) view.findViewById(R.id.handslot3));
        cardSlots[3] = new CardSlot((ImageButton) view.findViewById(R.id.handslot4));
        cardSlots[4] = new CardSlot((ImageButton) view.findViewById(R.id.handslot5));
    }

    public void UpdatePoder(){
        for (Jogador jogador : jogadores){
            for (int fila = 0; fila < Singleton.NUM_FILAS; fila++){
                int soma = 0;
                int carta = 0;
                while (jogador.getCampo()[fila][carta] != null){
                    soma += jogador.getCampo()[fila][carta].getPoder();
                    carta++;
                }
                jogador.getPoder()[fila] = soma;
            }
            jogador.getPoder()[2] = jogador.getPoder()[0] + jogador.getPoder()[1]; //soma Poder das Filas
        }
    }

    public void UpdateTurno(){
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
    // - cartas.addModificador.
    // - UpdatePoder()
    // - update turno



}
