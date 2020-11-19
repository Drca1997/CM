package com.example.guerradasestrelas;


import android.annotation.SuppressLint;
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
    private int winner;
    private int [] cardsNum;
    private int n_ronda;
    private int turno; //0 - vez do jogador 1, 1- vez do jogador 2. Ou seja, posição dos jogadores no array "jogadores"

    public Jogo(Context context, View view, String jog1, String jog2, int [] cardsInd){
        Singleton.getInstance();
        Singleton.context = context;
        Singleton.view = view;

        handSlots = new CardSlot[Singleton.LIM_MAO];
        BuildHandSlots();
        playerLabel = view.findViewById(R.id.mao_jog_text);

        BaseDados bd = new BaseDados(context);
        Carta [] allCards= bd.GetAllCards();
        // CardsInd vai ter nos 15 primeiros indices os indices no array allCards das cartas do jogador 0 e nos ultimos os do jogador 1
        cardsNum = cardsInd;

        criaJogadores(jog1, jog2);
        TextView player1Label = view.findViewById(R.id.p1_nome);
        player1Label.setText(jog1);
        TextView player2Label = view.findViewById(R.id.p2_nome);
        player2Label.setText(jog2);

        bd.getCardsSkills(allCards);

        setupGame(allCards);

        winner = -1;
    }

    private void BuildHandSlots(){
        handSlots[0] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card0));
        handSlots[1] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card1));
        handSlots[2] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card2));
        handSlots[3] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card3));
        handSlots[4] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card4));
        handSlots[5] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card5));
        handSlots[6] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card6));
        handSlots[7] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card7));
        handSlots[8] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card8));
        handSlots[9] = new CardSlot((ImageButton) Singleton.view.findViewById(R.id.hand_card9));
    }

    private void criaJogadores(String jog1, String jog2){
        jogadores = new Jogador [Singleton.NUM_JOGADORES];
        Jogador jogador1 = new Jogador(Singleton.PLAYER1_ID,jog1);
        Jogador jogador2 = new Jogador(Singleton.PLAYER2_ID,jog2);
        jogadores[0] = jogador1;
        jogadores[1] = jogador2;
    }

    private void setupGame(Carta [] allCards){
        buildBaralhosJogadores(allCards);
        //Inicia indicador de turno: vez do jogador 1
        this.turno = 0;
        this.n_ronda = 1;
        updatePlayerLabel();
        //baralha baralhos
        Utils.baralhaBaralho(jogadores[0].getBaralho());
        Utils.baralhaBaralho(jogadores[1].getBaralho());
        //debug print dos baralhos
        Utils.PrintBaralho(jogadores[0].getBaralho(), "BARALHO DO JOGADOR 1");
        Utils.PrintBaralho(jogadores[1].getBaralho(), "BARALHO DO JOGADOR 2");
        //Atribui mao inicial aos jogadores
        jogadores[0].InicializaMao();
        jogadores[1].InicializaMao();
        //mostra mao do jogador 1, pronto para comecar o jogo
        jogadores[0].MostraMao(handSlots);
    }

    private void buildBaralhosJogadores(Carta [] allCards){
        Carta[] baralho1 = new Carta[Singleton.NUM_CARTAS_JOGO/2];
        Carta[] baralho2 = new Carta[Singleton.NUM_CARTAS_JOGO/2];
        //Atribui baralhos aos jogadores
        if(cardsNum.length > 0){ //se sorteio acontecer
            System.out.println("SORTEIO ACABOU. DISTRIBUINDO CARTAS...");
            // ordenar de acordo com os indices do CardsNum
            for (int i = 0; i<Singleton.NUM_CARTAS_JOGO; i++){
                if(i < Singleton.NUM_CARTAS_JOGO/2){
                    // jogador 1
                    baralho1[i] = allCards[cardsNum[i]];
                }else{
                    // jogador 2
                    baralho2[i-Singleton.NUM_CARTAS_JOGO/2] = allCards[cardsNum[i]];
                }
            }
        }else{ //sem sorteio
            boolean debug = false;
            if (!debug){
                Utils.baralhaBaralho(allCards);
                Utils.PrintBaralho(allCards, "Cartas Recolhidas da Base de Dados");
                baralho1 = Arrays.copyOfRange(allCards, 0, Singleton.NUM_CARTAS_JOGO/ 2);
                baralho2 = Arrays.copyOfRange(allCards, Singleton.NUM_CARTAS_JOGO/2, allCards.length - (Singleton.NUM_CARTAS - Singleton.NUM_CARTAS_JOGO));
            }
            else{ //DEBUG (hardcoded para testar cartas especfíficas)
                //int [] IdsCartasASeremTestadas = new int [] {, 2, 3, 4, 6, 7, 13, 14, 18, 24, 28, 33, 34} ;
                //for (int i=0; i < IdsCartasASeremTestadas.length;  i++){
                //    baralho1[i] = Debug.getCartaFromArray(allCards, IdsCartasASeremTestadas[i]);
                //}
                Utils.baralhaBaralho(allCards);
                baralho1 = Arrays.copyOfRange(allCards, 0, Singleton.NUM_CARTAS_JOGO/ 2);
                baralho2 = Arrays.copyOfRange(allCards, Singleton.NUM_CARTAS_JOGO/2, allCards.length - (Singleton.NUM_CARTAS - Singleton.NUM_CARTAS_JOGO));
            }
        }
        jogadores[0].setBaralho(baralho1);
        jogadores[1].setBaralho(baralho2);
    }

    @SuppressLint("SetTextI18n")
    public void updatePlayerLabel(){
        playerLabel.setText("Mão do " + getJogadorAtual().getNome());
}

    public void updatePoder(){
        int i=1;
        for (Jogador jogador : jogadores){
            jogador.somaPoder();
            System.out.println("Poder Jogador " + i + ": " + jogador.getPoder()[2] +
                    " = PT: " + jogador.getPoder()[0] + " + M: " + jogador.getPoder()[1]);
            i++;
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
            jogadores[turno].MostraMao(handSlots);
        }
        else{
            updateTurno();
            updatePlayerLabel();
            jogadores[turno].MostraMao(handSlots);
        }
    }

    public void skipRound(){
        updateTurno();
        updatePlayerLabel();
        if (!jogadores[turno].isSkipped()){
            jogadores[turno].MostraMao(handSlots);
        }
        else{
            finishRound();
            updatePlayerLabel();
        }
    }

    public void finishRound(){
        if (jogadores[0].getPoder()[2] > jogadores[1].getPoder()[2]){
            increaseRonda();
            jogadores[0].rondaGanha();
            if (checkForWinner(jogadores[0])){
                //ganhou o jogo
                System.out.println("Jogador 1 venceu o jogo");
                winner = 0;
            }
            else{
                System.out.println("Jogador 1 venceu a ronda. Começando proxima ronda...");
                setTurno(0);
                cleanup();
                Utils.updatePoderLabel(jogadores[0]);
                Utils.updatePoderLabel(jogadores[1]);
            }
        }
        else if (jogadores[0].getPoder()[2] < jogadores[1].getPoder()[2]){
            increaseRonda();
            jogadores[1].rondaGanha();
            if(checkForWinner(jogadores[1])){
                //ganhou o jogo
                System.out.println("Jogador 2 venceu o jogo");
                winner = 1;
            }
            else{
                System.out.println("Jogador 2 venceu a ronda. Começando proxima ronda...");
                setTurno(1);
                cleanup();
                Utils.updatePoderLabel(jogadores[0]);
                Utils.updatePoderLabel(jogadores[1]);
            }
        }
        jogadores[turno].MostraMao(handSlots); //para atualizar com novas cartas que vieram para a mão
    }

    public boolean checkForWinner(Jogador jogador){
        return jogador.getRondasGanhas() == 2;
    }

    public void cleanup(){
        for (Jogador jogador : jogadores){
            jogador.resetPoder();
            jogador.LimpaCampo();
            jogador.setSkipped(false);
        }
    }

    public int getTurno(){
        return turno;
    }

    private void setTurno(int turno){
        if (turno >= 0 && turno < jogadores.length){
            this.turno = turno;
        }
        updatePlayerLabel();
        //jogadores[turno].MostraMao(handSlots);
    }

    private void increaseRonda(){
        this.n_ronda = this.n_ronda + 1;
    }

    public int getRonda(){
        return this.n_ronda;
    }

    public int getWinner(){
        return winner;
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
