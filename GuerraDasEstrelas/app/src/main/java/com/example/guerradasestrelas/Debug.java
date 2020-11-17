package com.example.guerradasestrelas;

public class Debug {

    public static void addCartaToArray(Carta[] array, Carta carta){
        int i =0;
        while ((i <  array.length) && (array[i] != null)){
            i++;
        }
        if (i < array.length){
            array[i] = carta;
        }
        else{
            System.out.println("DEBUG: Array cheio. Nao foi possivel adicionar carta");
        }
    }

    public static Carta getCartaFromArray(Carta [] allCards, int id){
        for (int i=0; i < allCards.length; i++){
            if (allCards[i].getId() == id){
                return allCards[i];
            }
        }
        return null;
    }
}
