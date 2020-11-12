package com.example.guerradasestrelas;

import android.widget.ImageButton;

public class Carta {

    private int id;
    private String nome;
    private int poder;
    private int id_mini;
    private int id_max;
    private int fila; //fila 0 - Portugal; fila 1 - Mundo - mais tarde meter estas variaveis como constantes num singleton
    private Habilidade habilidade;

    public Carta(int id, String nome, int poder, int mini, int max, int fila){
        this.id = id;
        this.nome = nome;
        this.poder = poder;
        this.id_mini = mini;
        this.id_max = max;
        this.fila = fila;
        this.habilidade = null;
    }

    public void assignSkill(Habilidade habilidade){
        this.habilidade = habilidade;
        System.out.println("Assigned " + habilidade.getNome() + " to " + nome);
    }


    public void AddModifier(int modificador){
        setPoder(getPoder() + modificador);
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getFila() {
        return fila;
    }

    public int getId_mini() {
        return id_mini;
    }

    public int getId_max() {
        return id_max;
    }

    public int getId() {
        return id;
    }

    public Habilidade getHabilidade(){
        return habilidade;
    }
}
