package com.example.guerradasestrelas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {

    private Carta [] mao;
    private Carta [] baralho;
    private List<Carta> descartes;
    private Carta [][] campo;
    private int rondasGanhas;
    private int [] poder;

    public Jogador(Carta[] baralho){
        this.baralho = baralho;
        this.descartes = new ArrayList<Carta>();
        this.mao = new Carta [10];
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

    public void baralhaBaralho() {
        Random rand = new Random();
        for (int i = 0; i < baralho.length; i++) {
            int randomIndexToSwap = rand.nextInt(baralho.length);
            Carta temp = baralho[randomIndexToSwap];
            baralho[randomIndexToSwap] = baralho[i];
            baralho[i] = temp;
        }
    }

    public void InicializaMao(){
        for (int i = 0; i < Singleton.NUM_CARTAS_INICIAIS; i++){
            mao[i] = baralho[i];
            baralho[i] = null;
        }
    }

    public void JogaCarta(Carta carta){ //nao tira carta da mao aqui. será no onclicklistener da carta
        int freeSlot = getNextFreeSlot(campo[carta.getFila()]);
        if (freeSlot >= 0){
            campo[carta.getFila()][freeSlot] = carta;
            //meter carta visualmente no campo
        }
        else{
            //Nao faz nada a nao ser dar Som de Erro
            //Será aqui o sitio ideal para detetar habilidade de Madonna e Ronaldo?
        }
    }

    private int getNextFreeSlot(Carta [] array){
        int nextFreeSlot = -1;
        int i = 0;
        while (array[i] != null){
            nextFreeSlot = i;
            i++;
        }
        return nextFreeSlot;
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

    public Carta[] getMao() {
        return mao;
    }

    public void setMao(Carta[] mao) {
        this.mao = mao;
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
