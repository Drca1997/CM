package com.example.guerradasestrelas;

public abstract class Habilidade {

    private String nome;

    public Habilidade(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public abstract void Execute(int turno, CardSlot[][]campo1, CardSlot[][] campo2);
}
