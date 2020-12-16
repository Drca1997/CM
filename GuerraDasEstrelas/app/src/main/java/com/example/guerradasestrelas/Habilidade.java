package com.example.guerradasestrelas;

public abstract class Habilidade {

    protected String nome;

    public Habilidade(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public abstract void Execute(int turno, Jogador [] jogadores, Carta carta);
}
