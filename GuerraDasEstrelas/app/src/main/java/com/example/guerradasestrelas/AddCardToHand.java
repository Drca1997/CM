package com.example.guerradasestrelas;

import java.util.Random;

public class AddCardToHand extends Habilidade {
    private String origem;
    private boolean onPlay;
    private boolean aleatorio;

    public AddCardToHand(String nome, String origem, boolean onPlay, boolean aleatorio) {
        super(nome);
        this.origem=origem;
        this.onPlay=onPlay;
        this.aleatorio=aleatorio;
    }

    @Override
    public void Execute(int turno, Jogador [] jogadores) {
        if (onPlay){
            switch(origem){
                case "maoAdversario":
                    addCartaToHand(jogadores[Utils.getOutraFila(turno)].getMao(), jogadores[turno].getMao(), true);
                    break;
                case "baralho":
                    jogadores[turno].tiraCartaDoBaralho();
                    jogadores[turno].tiraCartaDoBaralho();
                    break;
                case "campo":
                    Carta [] array1 = Utils.getCardsArray(jogadores[turno].getCampo()[0]);
                    Carta [] array2 = Utils.getCardsArray(jogadores[turno].getCampo()[1]);
                    addCartaToHand(Utils.mergeArrays(array1, array2), jogadores[turno].getMao(), false);
                    break;
                default:
                    System.out.println("ERRO: Habilidade nao reconhecida");
                    break;
            }
        }
    }

    public void addCartaToHand(Carta [] origem, Carta[] mao, boolean removeDaOrigem){
        int cartaIndex;
        if (aleatorio){
            Random rand = new Random();
            cartaIndex = rand.nextInt(origem.length);
            boolean valido = false;
            while (!valido){
                if (!Utils.isImune(origem[cartaIndex])){ //Arnold Imune
                    valido = true;
                }
                else{
                    cartaIndex = rand.nextInt(origem.length);
                }
            }
        }
        else{
            cartaIndex = 0;
            assert origem[0] != null;
        }
        int slot = Utils.getNextFreeSlot(mao);
        if (slot >=0){
            mao[slot] = origem[cartaIndex];
        }
        if (removeDaOrigem){
            origem[cartaIndex] = null;
        }
    }

    public String getOrigem(){
        return origem;
    }

}