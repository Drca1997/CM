package com.example.guerradasestrelas;


public class Jogo {

    private Jogador [] jogadores;

    private int turno; //0 - vez do jogador 1, 1- vez do jogador 2. Ou seja, posição dos jogadores no array "jogadores"

    public Jogo(Carta [] baralho1, Carta [] baralho2){
        jogadores = new Jogador [Singleton.NUM_JOGADORES];
        Jogador jogador1 = new Jogador(baralho1);
        Jogador jogador2 = new Jogador(baralho2);
        jogadores[0] = jogador1;
        jogadores[1] = jogador2;
        this.turno = 0;
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

    //loop:
    //jogadores[turno] joga carta:
    // - remover carta da mao
    // - jogadores[turno].JogaCarta(carta)
    // - carta.ExecutaHabilidade();
    // - cartas.addModificador.
    // - UpdatePoder()
    // - update turno



}
