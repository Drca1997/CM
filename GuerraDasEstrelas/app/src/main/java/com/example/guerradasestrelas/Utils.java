package com.example.guerradasestrelas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static void updateSkillsInPlay(Carta carta, Jogador [] jogadores, int turno){
       if (carta.getHabilidade() != null){
           switch(carta.getHabilidade().getNome()){
               case "Kamikaze":
                   for (Jogador jogador : jogadores){
                       updateKamikazeStatus(jogador);
                   }
                   break;
               case "Ligacao":
                   for (Jogador jogador: jogadores){
                        updatePowerRangersPower(jogador.getFilaPortugal(), carta);
                   }
                   break;
           }
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

    private static void updatePowerRangersPower(CardSlot [] filaPT, Carta carta){
        ArrayList<Carta> powerRangers = getPowerRangersInLine(filaPT);
        int mod = (int) Math.pow(carta.getPoderDefault(), (powerRangers.size() - 1));
        for (Carta pr : powerRangers){
            if (pr != carta){
                pr.AddModifier(-mod);
                System.out.println("Aplicado -" + mod + " a " + pr.getNome());
            }
        }
    }

    private static ArrayList<Carta> getPowerRangersInLine(CardSlot [] fila){
        ArrayList<Carta> res = new ArrayList<>();
        for (CardSlot slot : fila){
            if (slot.getCarta() != null){
                if (slot.getCarta().getHabilidade() != null){
                    if (slot.getCarta().getHabilidade().getNome().equals("Ligacao")) {
                        res.add(slot.getCarta());
                    }
                }
            }
        }
        return res;
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

    public static ContainerClass createAnimator(View thumbView, View view, int resID, LinearLayout expandedLL, ImageView expandedIV, int shortAnimationDuration){
        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        view.findViewById(resID)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);

        AnimatorSet set;
        if(expandedLL == null){
            expandedIV.setVisibility(View.VISIBLE);
            expandedIV.setPivotX(0f);
            expandedIV.setPivotY(0f);
        }else{
            expandedLL.setVisibility(View.VISIBLE);
            expandedLL.setPivotX(0f);
            expandedLL.setPivotY(0f);
        }

        set = getAnimatorSet(expandedLL, expandedIV, startBounds, finalBounds, startScale, shortAnimationDuration );

        return new ContainerClass(set,startScale,startBounds);
    }

    public static AnimatorSet getAnimatorSet(LinearLayout expandedLL, ImageView expandedIV, Rect startBounds, Rect finalBounds, float startScale, int shortAnimationDuration){
        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        if(expandedLL == null){
            set
                    .play(ObjectAnimator.ofFloat(expandedIV, View.X,
                            startBounds.left, finalBounds.left))
                    .with(ObjectAnimator.ofFloat(expandedIV, View.Y,
                            startBounds.top, finalBounds.top))
                    .with(ObjectAnimator.ofFloat(expandedIV, View.SCALE_X,
                            startScale, 1f))
                    .with(ObjectAnimator.ofFloat(expandedIV,
                            View.SCALE_Y, startScale, 1f));
        }else{
            set
                    .play(ObjectAnimator.ofFloat(expandedLL, View.X,
                            startBounds.left, finalBounds.left))
                    .with(ObjectAnimator.ofFloat(expandedLL, View.Y,
                            startBounds.top, finalBounds.top))
                    .with(ObjectAnimator.ofFloat(expandedLL, View.SCALE_X,
                            startScale, 1f))
                    .with(ObjectAnimator.ofFloat(expandedLL,
                            View.SCALE_Y, startScale, 1f));
        }
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());

        return set;
    }

    public static AnimatorSet unZoomAnimator(LinearLayout expandedLL, ImageView expandedIV, Rect startBounds, float startScaleFinal, int shortAnimationDuration){
        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
        AnimatorSet set = new AnimatorSet();
        if(expandedLL == null){
            set.play(ObjectAnimator
                    .ofFloat(expandedIV, View.X, startBounds.left))
                    .with(ObjectAnimator
                            .ofFloat(expandedIV,
                                    View.Y,startBounds.top))
                    .with(ObjectAnimator
                            .ofFloat(expandedIV,
                                    View.SCALE_X, startScaleFinal))
                    .with(ObjectAnimator
                            .ofFloat(expandedIV,
                                    View.SCALE_Y, startScaleFinal));
            set.setDuration(shortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
        }else{
            set.play(ObjectAnimator
                    .ofFloat(expandedLL, View.X, startBounds.left))
                    .with(ObjectAnimator
                            .ofFloat(expandedLL,
                                    View.Y,startBounds.top))
                    .with(ObjectAnimator
                            .ofFloat(expandedLL,
                                    View.SCALE_X, startScaleFinal))
                    .with(ObjectAnimator
                            .ofFloat(expandedLL,
                                    View.SCALE_Y, startScaleFinal));
            set.setDuration(shortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
        }

        return set;
    }
}
