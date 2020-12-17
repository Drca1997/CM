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
    public void Execute(int turno, Jogador [] jogadores, Carta carta) {
        if (onPlay){
            switch(origem){
                case "maoAdversario":
                    addCartaToHand(jogadores[Utils.getOutraFila(turno)].getMao(), jogadores[turno].getMao(), true, false);
                    Utils.updateCartasNaMaoLabel(jogadores[turno]);
                    Utils.updateCartasNaMaoLabel(jogadores[Utils.getOutraFila(turno)]);
                    break;
                case "baralho":
                    jogadores[turno].tiraCartaDoBaralho();
                    jogadores[turno].tiraCartaDoBaralho();
                    break;
                case "campo":
                    Carta [] array1 = Utils.getCardsArray(jogadores[turno].getCampo()[0]);
                    Carta [] array2 = Utils.getCardsArray(jogadores[turno].getCampo()[1]);
                    addCartaToHand(Utils.mergeArrays(array1, array2), jogadores[turno].getMao(), false, true);
                    Utils.updateCartasNaMaoLabel(jogadores[turno]);
                    break;
                default:
                    System.out.println("ERRO: Habilidade nao reconhecida");
                    break;
            }
        }
    }

    public void addCartaToHand(Carta [] origem, Carta[] mao, boolean removeDaOrigem, boolean doCopy){
        int cartaIndex;
        if (aleatorio){
            Random rand = new Random();
            do{
                cartaIndex = rand.nextInt(origem.length);
                while (origem[cartaIndex] == null){
                    cartaIndex = rand.nextInt(origem.length);
                }
            }while(Utils.isImune(origem[cartaIndex]));
        }
        else{
            cartaIndex = 0;
            assert origem[0] != null;
        }
        int slot = Utils.getNextFreeSlot(mao);
        if (slot >=0){
            if (doCopy){
                mao[slot] = criaCopia(origem[cartaIndex]);
            }
            else{
                mao[slot] = origem[cartaIndex];
            }

        }
        if (removeDaOrigem){
            origem[cartaIndex] = null;
        }
    }

    public Carta criaCopia(Carta original){
        Carta copia = new Carta(original.getId(), original.getNome(), original.getPoderDefault(),
                original.getId_mini(), original.getId_max(),original.getId_som(), original.getFila());
        copia.assignSkill(original.getHabilidade());
        return copia;
    }

    public String getOrigem(){
        return origem;
    }

}
