package com.example.guerradasestrelas;

import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean gotKamikazed;

    public Jogador(int id){
        this.id = id;
        this.baralho = null;
        this.descartes = new ArrayList<Carta>();
        this.mao = new Carta [Singleton.LIM_MAO];
        this.rondasGanhas = 0;
        this.campo = new CardSlot[Singleton.NUM_FILAS] [Singleton.TAMANHO_FILAS]; //2 filas, cada uma com 5 cartas;
        BuildPlayerCardSlots();
        this.poder = new int[] {0, 0,0};
        this.skipped = false;
        this.gotKamikazed = false;
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
        Utils.updateCartasNaMaoLabel(this);
        Utils.updateCartasNoBaralhoLabel(this);
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
        Utils.updateCartasNoBaralhoLabel(this);
        Utils.updateCartasNaMaoLabel(this);
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

    public void jogaCarta(Carta carta, int freeSlot, int turno, Jogador [] jogadores){
        removeCartadaMao(carta);
        campo[carta.getFila()][freeSlot].setCarta(carta); //coloca carta no campo
        System.out.println("JOGANDO " + carta.getNome().toUpperCase());
        if (carta.getHabilidade() != null){
            carta.getHabilidade().Execute(turno, jogadores);
        }
        if (!Utils.isKamikaze(carta)){
            tiraCartaDoBaralho();
        }
        else{
            jogadores[Utils.getOutraFila(turno)].tiraCartaDoBaralho();
        }
    }

    public void somaPoder(){
        for (int i=0; i < campo.length; i++){
            int soma = 0;
            for(CardSlot slot : campo[i]){
                if (slot.getCarta() != null){
                    soma += slot.getCarta().getPoder();
                }
            }
            poder[i] = soma;
        }
        poder[2] = poder[0] + poder[1];
        Utils.updatePoderLabel(this);
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

    public boolean removeCartaDeCampo(Carta carta){
        for (CardSlot [] fila : campo){
            for(CardSlot slot : fila){
                if (slot.getCarta() != null){
                    if (slot.getCarta().getId() == carta.getId()){
                        slot.setCarta(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void LimpaCampo(){
        AddCardToHand seanbeanHabRef = null;
        for(int fila=0; fila < Singleton.NUM_FILAS; fila++){
            for (int slot =0; slot < campo[fila].length; slot++){
                Carta cartaAtual = campo[fila][slot].getCarta();
                if (cartaAtual != null){
                    if (cartaAtual.getHabilidade() != null){
                        if (cartaAtual.getHabilidade().getNome().equals("AddCardToHand")){
                           AddCardToHand skill = (AddCardToHand)cartaAtual.getHabilidade();
                           if (skill.getOrigem().equals("self")){  //Diogo Morgado
                               //Carta copia = skill.criaCopia(cartaAtual);
                               Carta [] arrayOrigem = {cartaAtual};
                               System.out.println("JESUS RESSUSCITA");
                               skill.addCartaToHand(arrayOrigem, mao, false, true);
                               Utils.updateCartasNaMaoLabel(this);
                           }
                           else if (skill.getOrigem().equals("descartes")){ //Sean Bean
                               seanbeanHabRef = skill;  //nao podia executar habilidade aqui, senao so
                               // ia buscar uma das cartas que ja foram removidas de campo.

                           }
                        }
                    }
                    if (!Utils.isJesus(campo[fila][slot].getCarta())){
                        descartes.add(campo[fila][slot].getCarta());
                    }

                }
                campo[fila][slot].setCarta(null);
            }
        }
        if (seanbeanHabRef != null){
            Carta[] array = descartes.toArray(new Carta[descartes.size()]);
            seanbeanHabRef.addCartaToHand(array, mao, true, true);
            Utils.updateCartasNaMaoLabel(this);
        }

    }

    public int getRondasGanhas() {
        return rondasGanhas;
    }

    public void rondaGanha(){
        this.rondasGanhas++;
        Utils.updateRondasGanhasLabel(this);
    }

    public List<Carta> getDescartes() {
        return descartes;
    }

    public void resetPoder(){
        Arrays.fill(poder, 0);
    }

    public Carta[] getBaralho() {
        return baralho;
    }

    public void setBaralho(Carta [] baralho){
        this.baralho = baralho;
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

    public boolean isGotKamikazed() {
        return gotKamikazed;
    }

    public void setGotKamikazed(boolean gotKamikazed) {
        this.gotKamikazed = gotKamikazed;
    }

    public int getId(){
        return id;
    }
}
