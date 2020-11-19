package com.example.guerradasestrelas.ui.gameboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.fragments.MainMenuFragment;

public class NomeFragment extends Fragment {
    private View view;

    private NomeFragment.OnNomeFragmentInteractionListener mListener;

    public NomeFragment() {
        // Required empty public constructor
    }
    public static NomeFragment newInstance() {
        NomeFragment fragment = new NomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_names, container, false);

        final EditText jog1 = view.findViewById(R.id.edit_text_jogador1);
        final EditText jog2 = view.findViewById(R.id.edit_text_jogador2);

        Button beg = view.findViewById(R.id.start_butt);
        beg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChosenInteraction(jog1.getText().toString(),jog2.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NomeFragment.OnNomeFragmentInteractionListener) {
            mListener = (NomeFragment.OnNomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnNomeFragmentInteractionListener {
        void onChosenInteraction(String jog1, String jog2);
    }
}