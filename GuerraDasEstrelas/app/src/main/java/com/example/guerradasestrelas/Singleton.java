package com.example.guerradasestrelas;

public class Singleton  {

    public static final int TAMANHO_FILAS = 5;
    public static final int NUM_FILAS = 2;
    public static final int NUM_CARTAS_INICIAIS = 5;
    public static final int NUM_JOGADORES = 2;

    private static Singleton INSTANCE = null;

    private Singleton() {};

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return(INSTANCE);
    }

}