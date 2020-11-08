package com.example.guerradasestrelas.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guerradasestrelas.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {
    private View view;

    private PlayFragment.OnPlayFragmentInteractionListener mListener;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_play, container, false);

        // Ouvir pelo modo vs pc
        Button buttonVsPC = view.findViewById(R.id.vsPC_butt);
        buttonVsPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onVSPCInteraction();
            }
        });

        // Ouvir pelo modo local multiplayer
        Button buttonLocalMP = view.findViewById(R.id.localMP_butt);
        buttonLocalMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLocalMPInteraction();
            }
        });

        // Ouvir para voltar para o menu principal
        Button buttonBack = view.findViewById(R.id.back_butt);
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
        if (context instanceof PlayFragment.OnPlayFragmentInteractionListener) {
            mListener = (PlayFragment.OnPlayFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnPlayFragmentInteractionListener {
        void onLocalMPInteraction();
        void onVSPCInteraction();
        void onBackInteraction();
    }
}