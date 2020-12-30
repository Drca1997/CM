package com.example.guerradasestrelas.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guerradasestrelas.BaseDados;
import com.example.guerradasestrelas.Carta;
import com.example.guerradasestrelas.ContainerClass;
import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.Singleton;
import com.example.guerradasestrelas.Utils;
import com.example.guerradasestrelas.loadCardsTask;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator currentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int shortAnimationDuration;

    private View view;

    private GalleryFragment.OnGalleryFragmentInteractionListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BaseDados bd = new BaseDados(getActivity());

        Carta cartas[] = new Carta[Singleton.NUM_CARTAS];
        try {
            cartas = new loadCardsTask(bd).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        LinearLayout galLayout = view.findViewById(R.id.galleryLayout);

        for (int i = 0; i<cartas.length; i++){
            View view_card = inflater.inflate(R.layout.card_item,galLayout,false);

            final ImageView card_image = view_card.findViewById(R.id.CardView);
            card_image.setImageResource(cartas[i].getId_mini());
            final int ind = i;

            final Carta[] finalCartas = cartas;
            card_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //click na carta -> ENHANCE
                    zoomImage(card_image, finalCartas[ind].getId_max());

                    // Retrieve and cache the system's default "short" animation time.
                    shortAnimationDuration = getResources().getInteger(
                            android.R.integer.config_shortAnimTime);
                }
            });

            galLayout.addView(view_card);
        }

        // Ouvir para voltar para o menu principal
        Button buttonBack = view.findViewById(R.id.back_gal_butt);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBackInteraction();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GalleryFragment.OnGalleryFragmentInteractionListener) {
            mListener = (GalleryFragment.OnGalleryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnGalleryFragmentInteractionListener {
        void onBackInteraction();
    }

    private void zoomImage(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) view.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        ContainerClass cont = Utils.createAnimator(thumbView, view, R.id.frameLayout3, null, expandedImageView, shortAnimationDuration);
        AnimatorSet set = cont.getSet();
        float startScale = cont.getStartScale();
        final Rect startBounds = cont.getStartBounds();

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
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unZoomCard(expandedImageView,startBounds,startScaleFinal,thumbView);
            }
        });
    }

    private void unZoomCard(final ImageView expandedImageView, Rect startBounds, float startScaleFinal, final View thumbView){
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        AnimatorSet set = Utils.unZoomAnimator(null, expandedImageView, startBounds, startScaleFinal, shortAnimationDuration);

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

}