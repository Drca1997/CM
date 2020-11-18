package com.example.guerradasestrelas.ui.gameboard;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guerradasestrelas.CardSlot;
import com.example.guerradasestrelas.Carta;
import com.example.guerradasestrelas.Jogador;
import com.example.guerradasestrelas.Jogo;
import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.Utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GameBoardFragment extends Fragment {
    private static final String CARDS_LIST = "CARDS_LIST";

    private int [] cartas;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator currentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int shortAnimationDuration;

    private View view;

    private GameBoardFragment.OnGameBoardFragmentInteractionListener mListener;

    private String old_hand;

    public GameBoardFragment() {
        // Required empty public constructor
    }

    public static GameBoardFragment newInstance(int [] cartas) {
        GameBoardFragment fragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putIntArray(CARDS_LIST,cartas);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cartas = getArguments().getIntArray(CARDS_LIST);
        }
    }

    public void initArguments(){
        if (getArguments() != null) {
            cartas = getArguments().getIntArray(CARDS_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_board_fragment, container, false);

        initArguments();

        final Jogo jogo = new Jogo(getActivity(), view, cartas);

        old_hand = "";

        // Para efeitos de teste
        //final ConstraintLayout next_round = (ConstraintLayout) view.findViewById(R.id.obscure_hand_layout);
        //next_round.setVisibility(View.GONE);

        toggle_click(true,jogo);

        return view;
    }

    private void toggle_click(boolean value, final Jogo jogo){
        CardSlot[] playerhandDisplayed = jogo.getHandSlots();

        if(value) {
            // Cartas no campo
            for (Jogador jog : jogo.getJogadores())
                for (final CardSlot[] slot_list : jog.getCampo())
                    for(final CardSlot slot: slot_list){
                        set_button(jogo,slot,false);
                    }

            // Cartas na mao
            for (final CardSlot slot : playerhandDisplayed) {
                set_button(jogo,slot,true);
            }
            playerTransition();

            Button button = view.findViewById(R.id.skip_butt);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jogo.getJogadorAtual().setSkipped(true);
                    jogo.skipRound();
                    if(jogo.getWinner() != ""){
                        mListener.onGameWonInteraction(jogo.getWinner());
                    }
                    playerTransition();
                }
            });
        }else{
            // Cartas no Campo
            for (Jogador jog : jogo.getJogadores())
                for (final CardSlot[] slot_list : jog.getCampo())
                    for(final CardSlot slot: slot_list){
                        slot.getSlot().setOnClickListener(null);
                    }

            // Cartas na mao
            for (final CardSlot slot : playerhandDisplayed) {
                slot.getSlot().setOnClickListener(null);
            }

            Button button = view.findViewById(R.id.skip_butt);
            button.setOnClickListener(null);
        }
    }

    private void set_button(final Jogo jogo, final CardSlot slot, final boolean playable){
        final ImageButton card_image = slot.getSlot();
        slot.getSlot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carta temp = slot.getCarta();
                if (temp != null) {
                    // Zoom Card
                    zoomCard(card_image, temp.getId_max(), jogo, slot, playable);

                    // Retrieve and cache the system's default "short" animation time.
                    shortAnimationDuration = getResources().getInteger(
                            android.R.integer.config_shortAnimTime);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(GameBoardViewModel.class);
    }

    public View getView(){
        return view;
    }

    private void playCard(final Jogo jogo,final CardSlot slot){
        Carta temp = slot.getCarta();
        if (temp != null){
            if (!Utils.isKamikaze(temp)){
                int freeSlot = Utils.getNextFreeSlot(jogo.getJogadorAtual().getCampo()[temp.getFila()]);
                if (freeSlot >= 0){
                    if ((!(temp.getFila() == 1 && jogo.getJogadorAtual().isGotKamikazed()))
                            || (Utils.isImune(temp))){ //se nao tem kamikaze na fila
                        slot.setCarta(null); //remove carta do handSlot
                        jogo.getJogadorAtual().jogaCarta(temp, freeSlot, jogo.getTurno(), jogo.getJogadores());
                        jogo.acabaJogada();
                    }
                }
                else{
                    //Será aqui o sitio ideal para detetar habilidade de Madonna e Ronaldo?
                    if (temp.getHabilidade() != null){
                        if (temp.getHabilidade().getNome().equals("MultiFila")){
                            int outrafila = Utils.getOutraFila(temp.getFila());
                            int slotOutraFila = Utils.getNextFreeSlot(jogo.getJogadorAtual().getCampo()[outrafila]);
                            if (slotOutraFila >= 0){
                                if (!(outrafila == 1 && jogo.getJogadorAtual().isGotKamikazed())){ //se nao tem kamikaze na fila, ronaldo pode ser posto na fila do mundo
                                    slot.setCarta(null); //remove carta do handSlot
                                    jogo.getJogadorAtual().removeCartadaMao(temp);
                                    System.out.println(temp.getNome());
                                    jogo.getJogadorAtual().getCampo()[outrafila][slotOutraFila].setCarta(temp);
                                    jogo.getJogadorAtual().tiraCartaDoBaralho();
                                    jogo.acabaJogada();
                                }

                            }
                        }
                    }

                }
            }
            else{ // se for Kamikaze
                Jogador adversario = jogo.getJogadores()[Utils.getOutraFila(jogo.getTurno())];
                int freeSlot = Utils.getNextFreeSlot(adversario.getCampo()[temp.getFila()]);
                if (freeSlot >= 0){
                    slot.setCarta(null); //remove carta do handSlot
                    adversario.jogaCarta(temp, freeSlot, jogo.getTurno(), jogo.getJogadores());
                    jogo.acabaJogada();
                }
            }

            if(!jogo.getWinner().equals("")){
                mListener.onGameWonInteraction(jogo.getWinner());
            }
        }
    }

    private void zoomCard(final View thumbView, int imageResId, final Jogo jogo, final CardSlot slot, boolean playable) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        toggle_click(false, jogo);

        final LinearLayout expandedImageView = (LinearLayout) view.findViewById(R.id.expanded_image);
        expandedImageView.bringToFront();
        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImage = (ImageView) view.findViewById(
                R.id.expanded_im);
        expandedImage.setImageResource(imageResId);

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
        view.findViewById(R.id.game_board)
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
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
                toggle_click(true, jogo);
            }
        });

        // Botao de Jogar, se for carta ja jogada, nao pode ser jogada, metemos invisivel
        Button play_card = (Button) view.findViewById(R.id.play_card_butt);
        if(playable) {
            play_card.setVisibility(View.VISIBLE);
            play_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // CODIGO PARA JOGAR CARTA
                    playCard(jogo,slot);
                    unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
                    toggle_click(true, jogo);
                }
            });
        }else{
            play_card.setVisibility(View.GONE);
        }

        // Botao de voltar para o ecra de jogo
        Button back_b = (Button) view.findViewById(R.id.back_hand_butt);
        back_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
                toggle_click(true, jogo);
            }
        });
    }

    private void unZoomCard(final LinearLayout expandedImageView, Rect startBounds, float startScaleFinal, final View thumbView){
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.Y,startBounds.top))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_Y, startScaleFinal));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;
    }

    private void playerTransition(){
        // Detetar aqui se houve ou nao mudança de mao. Se sim, mostrar ecra de transiçao.
        String jog_at = ((TextView) view.findViewById(R.id.mao_jog_text)).getText().toString();
        //System.out.println(jog_at);
        //System.out.println(old_hand);
        if(!jog_at.equals(old_hand)){
            // fazer ecra aparecer
            final ConstraintLayout next_round = (ConstraintLayout) view.findViewById(R.id.obscure_hand_layout);
            Button ready = (Button) view.findViewById(R.id.ready_butt);
            next_round.setVisibility(View.VISIBLE);
            next_round.bringToFront();
            ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next_round.setVisibility(View.INVISIBLE);
                }
            });

            // atualizar jogador
            old_hand = jog_at;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameBoardFragment.OnGameBoardFragmentInteractionListener) {
            mListener = (GameBoardFragment.OnGameBoardFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnGameBoardFragmentInteractionListener {
        void onGameWonInteraction(String winner);
    }
}