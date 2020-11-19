package com.example.guerradasestrelas.ui.gameboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.example.guerradasestrelas.BaseDados;
import com.example.guerradasestrelas.Carta;
import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.Singleton;
import com.example.guerradasestrelas.Utils;

import java.util.Arrays;

public class SorteioFragment extends Fragment {
    private static final String PLAYER1_NAME = "PLAYER1_N";
    private static final String PLAYER2_NAME = "PLAYER2_N";

    private String[] player_names;

    View view;

    private SorteioFragment.OnSorteioFragmentInteractionListener mListener;

    private Carta[] allCartas;
    private int[] shakedInds;
    private int[] cardInd;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator currentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int shortAnimationDuration;

    int ind,turn;

    public SorteioFragment() {
        // Required empty public constructor
    }

    public static SorteioFragment newInstance(String jog1, String jog2) {
        SorteioFragment fragment = new SorteioFragment();
        Bundle args = new Bundle();
        args.putString(PLAYER1_NAME,jog1);
        args.putString(PLAYER2_NAME,jog2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player_names = new String[2];
            player_names[0] = getArguments().getString(PLAYER1_NAME);
            player_names[1] = getArguments().getString(PLAYER2_NAME);
        }
    }

    public void initArguments(){
        if (getArguments() != null) {
            player_names = new String[2];
            player_names[0] = getArguments().getString(PLAYER1_NAME);
            player_names[1] = getArguments().getString(PLAYER2_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorteio, container, false);

        initArguments();

        // Obter cartas e baralhar
        BaseDados bd = new BaseDados(getActivity());
        allCartas = bd.GetAllCards();

        // Gerar array com indices baralhados
        int [] preShakedInds = new int[Singleton.NUM_CARTAS];
        cardInd = new int[Singleton.NUM_CARTAS_JOGO];
        for (int i=0;i<Singleton.NUM_CARTAS;i++){
            preShakedInds[i] = i;
        }

        Utils.baralhaIndices(preShakedInds);

        // Ficar apenas com as primeiras 30 do baralho baralhado
        shakedInds = Arrays.copyOfRange(preShakedInds, 0, Singleton.NUM_CARTAS_JOGO);

        // Iniciar sorteio
        turn = 0;
        turnManager();

        return view;
    }

    private void turnManager(){
        // tapar ecra e detetar clique
        final ConstraintLayout tapa = view.findViewById(R.id.tapa_escolha);
        final TextView ronda = view.findViewById(R.id.round_text);
        final TextView turn_t = view.findViewById(R.id.player_num_text);
        turn_t.setText("VEZ DE\n" + player_names[turn%2] + "!!!!");
        if(turn%2+1 == 1){
            tapa.setBackgroundColor(getResources().getColor(R.color.player1sorteio1));
            turn_t.setBackgroundColor(getResources().getColor(R.color.player1sorteio2));
            turn_t.setTextColor(getResources().getColor(R.color.white));
            ronda.setBackgroundColor(getResources().getColor(R.color.player1sorteio3));
            ronda.setTextColor(getResources().getColor(R.color.white));
        }
        else{
            tapa.setBackgroundColor(getResources().getColor(R.color.player2sorteio1));
            turn_t.setBackgroundColor(getResources().getColor(R.color.player2sorteio2));
            turn_t.setTextColor(getResources().getColor(R.color.black));
            ronda.setBackgroundColor(getResources().getColor(R.color.player2sorteio3));
            ronda.setTextColor(getResources().getColor(R.color.black));
        }
        tapa.setVisibility(View.VISIBLE);
        tapa.bringToFront();

        // preparar image buttons
        final ImageButton im_butt_1 = view.findViewById(R.id.im_choice_1);
        final ImageButton im_butt_2 = view.findViewById(R.id.im_choice_2);

        im_butt_1.setOnClickListener(null);
        im_butt_2.setOnClickListener(null);

        Button ready_butt = view.findViewById(R.id.ready_butt);
        ready_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // destapar ecra
                tapa.setVisibility(View.INVISIBLE);

                // preparar info de turno
                TextView turn_sort = view.findViewById(R.id.turn_n_text);
                turn_sort.setText("Sorteio turno (" + (turn + 1) + "/" + Singleton.NUM_CARTAS_JOGO/2 + ")");

                // preparar info de jogador
                TextView turn_player = view.findViewById(R.id.player_turn_text);
                turn_player.setText("Vez de " + player_names[turn%2]);


                if(turn%2+1 == 1){
                    turn_player.setBackgroundColor(getResources().getColor(R.color.player1));
                    turn_player.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    turn_player.setBackgroundColor(getResources().getColor(R.color.player2));
                    turn_player.setTextColor(getResources().getColor(R.color.black));
                }

                updateImages(im_butt_1,allCartas[shakedInds[ind]].getId_max(),true);
                updateImages(im_butt_2,allCartas[shakedInds[ind+1]].getId_max(),false);

                // preparar botao de skip
                toggle_skip(true);
            }
        });
    }

    private void toggle_skip(boolean onoff){
        // preparar botao de skip
        Button skip_sort = view.findViewById(R.id.skit_sort_butt);
        if(onoff){
            skip_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int [] empt = {};
                    mListener.onSorteioDoneInteraction(empt,player_names[0],player_names[1]);
                }
            });
        }else{
            skip_sort.setOnClickListener(null);
        }
    }

    private void updateImages(final ImageButton im_butt, final int im_id, final boolean first){
        im_butt.setImageResource(im_id);
        im_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ZoomCard
                zoomCard(im_butt,im_id,first);

                // Retrieve and cache the system's default "short" animation time.
                shortAnimationDuration = getResources().getInteger(
                        android.R.integer.config_shortAnimTime);

                toggle_skip(false);
            }
        });
    }

    private void chooseCard(boolean first){
        // jogador escolheu esta
        cardInd[turn] = ((turn % 2 == 0) ? shakedInds[(first ? ind:ind+1)]:shakedInds[(first ? ind+1:ind)]);
        cardInd[turn + Singleton.NUM_CARTAS_JOGO/2] = ((turn % 2 == 0) ? shakedInds[(first ? ind+1:ind)]:shakedInds[(first ? ind:ind+1)]);

        // proximo turno de escolha
        turn = turn + 1;
        ind = ind + 2;

        if(ind == Singleton.NUM_CARTAS_JOGO){
            // terminou sorteio
            mListener.onSorteioDoneInteraction(cardInd,player_names[0],player_names[1]);
        }else {
            // tapa ecra - nova ronda
            turnManager();
        }
    }

    private void zoomCard(final View thumbView, int imageResId, final boolean first) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

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
        view.findViewById(R.id.sorteio_layout)
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
                //toggle_click(true, jogo);
            }
        });

        // Botao de Jogar, se for carta ja jogada, nao pode ser jogada, metemos invisivel
        Button play_card = (Button) view.findViewById(R.id.play_card_butt);
        play_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CODIGO PARA ESCOLHER CARTA
                chooseCard(first);
                unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
            }
        });

        // Botao de voltar para o ecra de jogo
        Button back_b = (Button) view.findViewById(R.id.back_hand_butt);
        back_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
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
        toggle_skip(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SorteioFragment.OnSorteioFragmentInteractionListener) {
            mListener = (SorteioFragment.OnSorteioFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnSorteioFragmentInteractionListener {
        void onSorteioDoneInteraction(int[] cards, String jog1, String jog2);
    }
}