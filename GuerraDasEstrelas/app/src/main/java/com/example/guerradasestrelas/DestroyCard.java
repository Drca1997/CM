package com.example.guerradasestrelas;

import java.util.List;
import java.util.Random;


public class DestroyCard extends Habilidade {

    private String modoDestruir;

    public DestroyCard(String nome, String modoDestruir) {
        super(nome);
        this.modoDestruir = modoDestruir;
    }


    @Override
    public void Execute(int turno, Jogador[] jogadores) {
        Carta [] array1 = Utils.getCardsArray(jogadores[turno].getCampo()[0]);
        Carta [] array2 = Utils.getCardsArray(jogadores[turno].getCampo()[1]);
        Carta [] array3 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[0]);
        Carta [] array4 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[1]);
        if (modoDestruir.equals("tyrion")){
            destruirCarta(Utils.mergeArrays(array1, array2), false, jogadores[turno].getDescartes()); //destroi carta mais alta do proprio jogador
            destruirCarta(Utils.mergeArrays(array3, array4), true, jogadores[Utils.getOutraFila(turno)].getDescartes()); //destroi carta aleatoria do adversario
            Carta [] array5 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[0]); //processo repete-se pq destruirCarta nao pode receber array com cartas a null
            Carta [] array6 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[1]);
            destruirCarta(Utils.mergeArrays(array5, array6), true, jogadores[Utils.getOutraFila(turno)].getDescartes()); //destroi carta aleatoria do adversario
        }
        else if(modoDestruir.equals("gandhi")){
            Carta [] totalCartasJogador = Utils.mergeArrays(array1, array2);
            Carta [] totalCartasOponente = Utils.mergeArrays(array3, array4);
            destruirCarta(Utils.mergeArrays(totalCartasJogador, totalCartasOponente), false, null); //isto faz com que Gandhi remova carta permanentemente de jogo, nao indo para a pilha de descartes
        }

    }

    public void destruirCarta(Carta [] origem , boolean aleatorio, List<Carta> descartes){
        if (aleatorio){
            Random rand = new Random();
            int cartaIndex;
            do{
                cartaIndex = rand.nextInt(origem.length);
            }while(Utils.isImune(origem[cartaIndex]));
            descartes.add(origem[cartaIndex]);
            origem[cartaIndex] = null;
        }
        else{
            Carta [] res = Utils.getCartasMaisPoderosas(origem);
            for (Carta cartaOriginal : origem){
                if (!Utils.isImune(cartaOriginal)){
                    for (Carta cartaRes : res){
                        if (cartaOriginal.getId() == cartaRes.getId()){
                            if (descartes != null){
                                descartes.add(cartaOriginal);
                                cartaOriginal = null;
                            }
                        }
                    }
                }
            }
        }
    }
}
