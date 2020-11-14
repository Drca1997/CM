package com.example.guerradasestrelas;

import java.util.Random;

public class Utils {

    public static boolean isImune(Carta carta){
        return carta.getHabilidade() == null || carta.getHabilidade().getNome().equals("Imune");
    }

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

    public static int getOutraFila(int fila){
        if (fila == 1){
            return 0;
        }
        else if(fila == 0){
            return 1;
        }
        return -1;
    }

    public static Carta [] getCardsArray(CardSlot [] array){
        Carta [] cartas = new Carta [contaNotNullSlots(array)];
        for(int i=0; i < cartas.length; i++){
            if (array[i].getCarta() != null){
                cartas[i] = array[i].getCarta();
            }
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

    public static Carta [] mergeArrays(Carta [] array1, Carta[] array2){
        Carta [] resultado = new Carta[array1.length + array2.length];
        System.arraycopy(array1, 0, resultado,0, array1.length);
        System.arraycopy(array2, 0, resultado, array1.length, array2.length);
        return resultado;
    }


}
