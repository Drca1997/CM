package com.example.guerradasestrelas;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {

    private int id;
    private CardSlot [] mao;
    private Carta [] baralho;
    private List<Carta> descartes;
    private Carta [][] campo; //mudar tb para card slot
    private int rondasGanhas;
    private int [] poder;
    private View view;

    public Jogador(Carta[] baralho, int id){
        this.id = id;
        this.baralho = baralho;
        this.descartes = new ArrayList<Carta>();
        this.mao = new CardSlot [Singleton.LIM_MAO];
        BuildPlayerCardSlots(Singleton.view);
        this.rondasGanhas = 0;
        this.campo = new Carta [Singleton.NUM_FILAS] [Singleton.TAMANHO_FILAS]; //2 filas, cada uma com 5 cartas;
        this.poder = new int[] {0, 0,0};
    }

    public Carta [] getFilaPortugal(){
        return this.campo[0];
    }

    public Carta [] getFilaMundo(){
        return this.campo[1];
    }

    public void InicializaMao(){
        for (int i = 0; i < Singleton.NUM_CARTAS_INICIAIS; i++){
            mao[i].setCarta(baralho[i]);
            baralho[i] = null;
        }

    }

    private void BuildPlayerCardSlots(View view){
        mao[0] = new CardSlot((ImageButton) view.findViewById(R.id.handslot1));
        mao[1] = new CardSlot((ImageButton) view.findViewById(R.id.handslot2));
        mao[2] = new CardSlot((ImageButton) view.findViewById(R.id.handslot3));
        mao[3] = new CardSlot((ImageButton) view.findViewById(R.id.handslot4));
        mao[4] = new CardSlot((ImageButton) view.findViewById(R.id.handslot5));
    }

    public void JogaCarta(Carta carta){ //nao tira carta da mao aqui. será no onclicklistener da carta
        int freeSlot = Singleton.getNextFreeSlot(campo[carta.getFila()]);
        if (freeSlot >= 0){
            campo[carta.getFila()][freeSlot] = carta;
            //meter carta visualmente no campo
            //playSlot = Singleton.getNextFreeSlot(cardSlots)
            //carta.Play(playSlot);
        }
        else{
            //Nao faz nada a nao ser dar Som de Erro
            //Será aqui o sitio ideal para detetar habilidade de Madonna e Ronaldo?
        }
    }



    public void LimpaCampo(){
        for(int fila=0; fila < Singleton.NUM_FILAS; fila++){
            for (int slot =0; slot < campo[fila].length; slot++){
                descartes.add(campo[fila][slot]);
                campo[fila][slot] = null;
            }
        }
    }

    public int getRondasGanhas() {
        return rondasGanhas;
    }

    public void rondaGanha(){
        this.rondasGanhas++;
    }

    public List<Carta> getDescartes() {
        return descartes;
    }

    public Carta[] getBaralho() {
        return baralho;
    }

    public CardSlot[] getMao() {
        return mao;
    }

    public Carta[][] getCampo() {
        return campo;
    }

    public void setCampo(Carta[][] campo) {
        this.campo = campo;
    }

    public int[] getPoder() {
        return poder;
    }


}
