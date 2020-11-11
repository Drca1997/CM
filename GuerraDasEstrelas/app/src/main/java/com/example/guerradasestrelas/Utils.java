package com.example.guerradasestrelas;

import java.util.Random;

public class Utils {

    public static void baralhaBaralho(Carta [] baralho) {
        Random rand = new Random();
        for (int i = 0; i < baralho.length; i++) {
            int randomIndexToSwap = rand.nextInt(baralho.length);
            Carta temp = baralho[randomIndexToSwap];
            baralho[randomIndexToSwap] = baralho[i];
            baralho[i] = temp;
        }
    }

    public static void PrintBaralho(Carta [] baralho, String mensagem){
        if (mensagem != null){System.out.println(mensagem);}
        for(Carta carta: baralho){
            if (carta != null){
                System.out.println(carta.getNome());
            }

        }
    }

    //retorna -1 se nao ha slot livre
    public static int getNextFreeSlot(CardSlot [] array){
        int nextFreeSlot = 0;
        while (nextFreeSlot < array.length && array[nextFreeSlot].getCarta() != null){
            nextFreeSlot++;
        }
        if (nextFreeSlot >= array.length){
            return -1;
        }
        return nextFreeSlot;
    }

    public static int getNextFreeSlot(Carta [] array){
        int nextFreeSlot = 0;
        while (nextFreeSlot < array.length && array[nextFreeSlot] != null){
            nextFreeSlot++;
        }
        if (nextFreeSlot >= array.length){
            return -1;
        }
        return nextFreeSlot;
    }

    public static Carta [] getCardsArray(CardSlot [] array){
        Carta [] cartas = new Carta [contaNotNullSlots(array)];
        for(int i=0; i < cartas.length; i++){
            cartas[i] = array[i].getCarta();
        }
        return cartas;
    }

    public static int contaNotNullSlots(CardSlot [] array){
        int res = 0;
        for (CardSlot slot: array){
            if (slot.getCarta() != null){
                res++;
            }
        }
        return res;
    }

}
