package com.example.guerradasestrelas;

import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    private int id;
    private Carta [] mao;
    private Carta [] baralho;
    private List<Carta> descartes;
    private CardSlot [][] campo;
    private int rondasGanhas;
    private int [] poder;
    private boolean skipped;

    public Jogador(Carta[] baralho, int id){
        this.id = id;
        this.baralho = baralho;
        this.descartes = new ArrayList<Carta>();
        this.mao = new Carta [Singleton.LIM_MAO];
        this.rondasGanhas = 0;
        this.campo = new CardSlot[Singleton.NUM_FILAS] [Singleton.TAMANHO_FILAS]; //2 filas, cada uma com 5 cartas;
        BuildPlayerCardSlots();
        this.poder = new int[] {0, 0,0};
        this.skipped = false;
    }

    public CardSlot [] getFilaPortugal(){
        return this.campo[0];
    }

    public CardSlot [] getFilaMundo(){
        return this.campo[1];
    }

    public void InicializaMao(){
        for (int i = 0; i < Singleton.NUM_CARTAS_INICIAIS; i++){
            mao[i] = baralho[i];
            baralho[i] = null;
        }
    }

    public void MostraMao(CardSlot [] handSlots){
        for (int i=0; i < mao.length; i++){
            handSlots[i].setCarta(mao[i]);
        }
    }

    public void tiraCartaDoBaralho(){
        for (int i=0; i < baralho.length; i++){
            if (baralho[i] != null){
                int freeHandSlot = Utils.getNextFreeSlot(mao);
                if (freeHandSlot != -1){
                    mao[freeHandSlot] = baralho[i];
                    baralho[i] = null;
                }
                break;
            }
        }
    }

    private void BuildPlayerCardSlots(){
        if (id == 1){
            campo[0][0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_PortugalSlot1));
            campo[0][1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_PortugalSlot2));
            campo[0][2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_PortugalSlot3));
            campo[0][3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_PortugalSlot4));
            campo[0][4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_PortugalSlot5));
            campo[1][0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_MundoSlot1));
            campo[1][1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_MundoSlot2));
            campo[1][2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_MundoSlot3));
            campo[1][3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_MundoSlot4));
            campo[1][4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p1_MundoSlot5));
        }
        else if (id ==2){
            campo[0][0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_PortugalSlot1));
            campo[0][1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_PortugalSlot2));
            campo[0][2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_PortugalSlot3));
            campo[0][3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_PortugalSlot4));
            campo[0][4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_PortugalSlot5));
            campo[1][0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_MundoSlot1));
            campo[1][1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_MundoSlot2));
            campo[1][2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_MundoSlot3));
            campo[1][3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_MundoSlot4));
            campo[1][4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.p2_MundoSlot5));
        }
        else{
            System.out.println("ALGO CORREU MAL NA OBTENÇAO DE CARDSLOTS");
        }
    }

    public void jogaCarta(Carta carta, int freeSlot){
        if (freeSlot >= 0){
            removeCartadaMao(carta);
            campo[carta.getFila()][freeSlot].setCarta(carta); //coloca carta no campo
            carta.ExecutaHabilidade();

        }
        else{
            //Nao faz nada a nao ser dar Som de Erro
            //Será aqui o sitio ideal para detetar habilidade de Madonna e Ronaldo?

        }
    }

    public void removeCartadaMao(Carta carta){
        for (int i=0; i < mao.length; i++){
            if (mao[i] != null){
                if (mao[i].getId() == carta.getId()){
                    mao[i] = null;
                    break;
                }
            }
        }
    }

    public void LimpaCampo(){
        for(int fila=0; fila < Singleton.NUM_FILAS; fila++){
            for (int slot =0; slot < campo[fila].length; slot++){
                Carta cartaAtual = campo[fila][slot].getCarta();
                if (cartaAtual != null){
                    descartes.add(campo[fila][slot].getCarta());
                }
                campo[fila][slot].setCarta(null);
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

    public Carta[] getMao() {
        return mao;
    }

    public CardSlot[][] getCampo() {
        return campo;
    }

    public void setCarta(Carta carta, int fila, int pos) {
        this.campo[fila][pos].setCarta(carta);
    }

    public int[] getPoder() {
        return poder;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }
}
