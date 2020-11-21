package com.example.guerradasestrelas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Utils {

    public static void updateSkillsInPlay(Carta carta, Jogador [] jogadores, int turno){
        switch(carta.getHabilidade().getNome()){
            case "Kamikaze":
                for (Jogador jogador : jogadores){
                    if (updateKamikazeStatus(jogador)){
                        break;
                    }
                }
                break;
            case "AddModifier":
                break;
            case "Ligacao":
                break;
        }
    }

    public static boolean updateKamikazeStatus(Jogador jogador){
        for (CardSlot slot : jogador.getCampo()[1]){
            if (slot.getCarta() != null){
                if (isKamikaze(slot.getCarta())){
                    jogador.setGotKamikazed(false);
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    public static void updateRondasGanhasLabel(Jogador jogador){
        TextView text;
        if (jogador.getId() == Singleton.PLAYER1_ID){
           text = Singleton.view.findViewById(R.id.p1_wins);

        }
        else{
            text = Singleton.view.findViewById(R.id.p2_wins);
        }
        text.setText(jogador.getRondasGanhas() +  "");
    }

    @SuppressLint("SetTextI18n")
    public static void updateCartasNaMaoLabel(Jogador jogador){
        TextView text;
        if (jogador.getId() == Singleton.PLAYER1_ID){
            text = Singleton.view.findViewById(R.id.p1_mao);
        }
        else{
            text = Singleton.view.findViewById(R.id.p2_mao);
        }
        text.setText(Utils.contaCartasArray(jogador.getMao()) + "");
    }

    @SuppressLint("SetTextI18n")
    public static void updateCartasNoBaralhoLabel(Jogador jogador){
        TextView text;
        if (jogador.getId() == Singleton.PLAYER1_ID){
            text = Singleton.view.findViewById(R.id.p1_baralho);
        }
        else{
            text = Singleton.view.findViewById(R.id.p2_baralho);
        }
        text.setText(Utils.contaCartasArray(jogador.getBaralho()) +  "");
    }

    @SuppressLint("SetTextI18n")
    public static void updatePoderLabel(Jogador jogador){
        int [] poder = jogador.getPoder();
        TextView poderPT, poderMundo, poderTotal;
        if (jogador.getId() == Singleton.PLAYER1_ID){
            poderPT = Singleton.view.findViewById(R.id.p1_portugal_poder_text);
            poderMundo = Singleton.view.findViewById(R.id.p1_mundo_poder_text);
            poderTotal = Singleton.view.findViewById(R.id.p1_global_poder_text);
        }
        else{
            poderPT = Singleton.view.findViewById(R.id.p2_portugal_poder_text);
            poderMundo = Singleton.view.findViewById(R.id.p2_mundo_poder_text);
            poderTotal = Singleton.view.findViewById(R.id.p2_global_poder_text);
        }
        poderPT.setText(poder[0] + "");
        poderMundo.setText(poder[1] + "");
        poderTotal.setText(poder[2] + "");
    }

    public static void showToast(Context context, CharSequence text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static boolean isImune(Carta carta){
        if (carta.getHabilidade() != null){
            return carta.getHabilidade().getNome().equals("Imune");
        }
        return false;
    }

    public static boolean isJesus(Carta carta){
        if (carta.getHabilidade() != null){
            return carta.getNome().equals("Diogo Morgado");
        }
        return false;
    }

    public static boolean isKamikaze(Carta carta){
        if (carta.getHabilidade() != null){
            return carta.getHabilidade().getNome().equals("Kamikaze");
        }
        return false;
    }

    public static boolean isKamikazeInLine(CardSlot []fila){
        for (CardSlot slot : fila){
            if (slot.getCarta() != null){
                if (Utils.isKamikaze(slot.getCarta())){
                    return true;
                }
            }
        }
        return false;
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

    public static void baralhaIndices(int [] arr) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            int randomIndexToSwap = rand.nextInt(arr.length);
            int temp = arr[randomIndexToSwap];
            arr[randomIndexToSwap] = arr[i];
            arr[i] = temp;
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

    public static int contaCartasArray(Carta [] array){
        int res = 0;
        for (Carta carta : array){
            if (carta != null){
                res++;
            }
        }
        return res;
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
    /*
    public static Carta [] arrayListToArray(List<Carta> arrayList){
        Carta [] array = new Carta[arrayList.size()];
        for (int i=0; i < array.length; i++){
            array[i] = arrayList.get(i);
        }
        return array;
    }
    */
    /*
    public static Comparator<Carta> comparator = new Comparator<Carta>() {
        @Override
        public int compare(Carta carta1, Carta carta2) {
            return Integer.compare(carta1.getPoder(), carta2.getPoder());
        }
    };*/

    public static Carta [] getCartasMaisPoderosas(Carta [] origem){
        int maxPoder = getMaxValueOfObjectAttributeInArray(origem);
        Carta [] array;
        List<Carta> res = new ArrayList<>();
        for (Carta carta : origem){
            if (carta.getPoder() == maxPoder){
                res.add(carta);
            }
        }
        if (res.size() == 1){
            if (res.get(0).getHabilidade() != null){
                if (isImune(res.get(0))){ //se é so Arnold a mais poderosa, vai-se obter as 2ªs mais poderosas
                    array = getCartasMaisPoderosas(copiaArraySemCarta(origem, res.get(0)));
                }
                else{
                   array = res.toArray(new Carta[res.size()]);
                }
            }
            else{
                array = res.toArray(new Carta[res.size()]);
            }
        }
        else{
            array = res.toArray(new Carta[res.size()]);
        }
        assert array[0] != null;
        return array;
    }

    public static int getMaxValueOfObjectAttributeInArray(Carta [] origem){
        int maxValue = 0;
        for (Carta carta : origem){
            if (carta.getPoder() > maxValue){
                maxValue = carta.getPoder();
            }
        }
        return maxValue;
    }

    public static Carta [] copiaArraySemCarta(Carta [] origem, Carta carta){
        Carta [] novoArray = new Carta[origem.length - 1];
        int ind = 0;
        for (int i=0; i < origem.length; i++){
            if(origem[i].getId() != carta.getId()){
                novoArray[ind] = origem[i];
                ind++;
            }
        }
        return novoArray;
    }
}
