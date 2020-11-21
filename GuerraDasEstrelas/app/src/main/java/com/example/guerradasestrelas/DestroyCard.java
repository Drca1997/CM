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
            destruirCarta(turno, jogadores, Utils.mergeArrays(array1, array2), false, jogadores[turno].getDescartes()); //destroi carta mais alta do proprio jogador
            destruirCarta(turno, jogadores, Utils.mergeArrays(array3, array4), true, jogadores[Utils.getOutraFila(turno)].getDescartes()); //destroi carta aleatoria do adversario
            Carta [] array5 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[0]); //processo repete-se pq destruirCarta nao pode receber array com cartas a null
            Carta [] array6 = Utils.getCardsArray(jogadores[Utils.getOutraFila(turno)].getCampo()[1]);
            destruirCarta(turno, jogadores, Utils.mergeArrays(array5, array6), true, jogadores[Utils.getOutraFila(turno)].getDescartes()); //destroi carta aleatoria do adversario
        }
        else if(modoDestruir.equals("gandhi")){
            Carta [] totalCartasJogador = Utils.mergeArrays(array1, array2);
            Carta [] totalCartasOponente = Utils.mergeArrays(array3, array4);
            destruirCarta(turno, jogadores, Utils.mergeArrays(totalCartasJogador, totalCartasOponente), false, null); //isto faz com que Gandhi remova carta permanentemente de jogo, nao indo para a pilha de descartes
        }

    }

    public void destruirCarta(int turno, Jogador[] jogadores, Carta [] origem , boolean aleatorio, List<Carta> descartes){
        if (aleatorio){
            Utils.PrintBaralho(origem, "CARTAS POSSIVEIS DE ELIMINAR: ");
            Random rand = new Random();
            int cartaIndex;
            if (origem.length > 0){
                if (!(origem.length == 1 && Utils.isImune(origem[0]))){ //se NÃO ha só o arnold na fila
                    do{
                        cartaIndex = rand.nextInt(origem.length);
                        while (origem[cartaIndex] == null){
                            cartaIndex = rand.nextInt(origem.length);
                        }
                    }while(Utils.isImune(origem[cartaIndex]));
                    if (origem[cartaIndex].getHabilidade() != null){
                        Utils.updateSkillsInPlay(origem[cartaIndex], jogadores, turno);
                    }
                    descartes.add(origem[cartaIndex]);
                    jogadores[Utils.getOutraFila(turno)].removeCartaDeCampo(origem[cartaIndex]);
                }
            }
        }
        else{
            Carta [] res = Utils.getCartasMaisPoderosas(origem);
            Utils.PrintBaralho(res, "CARTAS MAIS PODEROSAS, PARA SEREM ELIMINADAS");
            for (int i=0; i < origem.length; i++){
                if (!Utils.isImune(origem[i])){
                    for (Carta cartaRes : res){
                        if (origem[i].getId() == cartaRes.getId()){
                            if (descartes != null) {
                                descartes.add(origem[i]);
                            }
                            Utils.updateSkillsInPlay(origem[i], jogadores, turno);
                            boolean removeu = jogadores[Utils.getOutraFila(turno)].removeCartaDeCampo(origem[i]);
                            if (!removeu){
                                jogadores[turno].removeCartaDeCampo(origem[i]);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
