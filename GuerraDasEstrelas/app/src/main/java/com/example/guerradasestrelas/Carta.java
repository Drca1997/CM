package com.example.guerradasestrelas;

import android.media.Image;

import java.util.function.Function;

public class Carta {

    private String nome;
    private int poder;
    private Image mini;
    private Image max;
    private int fila; //fila 0 - Portugal; fila 1 - Mundo - mais tarde meter estas variaveis como constantes num singleton

    public Carta(String nome, int poder, Image mini, Image max, int fila){
        this.nome = nome;
        this.poder = poder;
        this.mini = mini;
        this.max = max;
        this.fila = fila;
    }

    /*
    public void ExecutaHabilidade(Function  function){

    }
    */

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

    public Image getMini() {
        return mini;
    }

    public Image getMax() {
        return max;
    }

    public int getFila() {
        return fila;
    }
}
