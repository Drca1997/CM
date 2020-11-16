package com.example.guerradasestrelas.ui.gameboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.fragments.GalleryFragment;

public class WinnerFragment extends Fragment {
    private static final String WINNER_NAME = "";

    private String mParam1;

    private View view;

    private WinnerFragment.OnWinnerFragmentInteractionListener mListener;

    public WinnerFragment() {
        // Required empty public constructor
    }

    public static WinnerFragment newInstance(String param1) {
        WinnerFragment fragment = new WinnerFragment();
        Bundle args = new Bundle();
        args.putString(WINNER_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(WINNER_NAME);
        }
    }

    public void initArguments(){
        if (getArguments() != null) {
            mParam1 = getArguments().getString(WINNER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_winner, container, false);

        initArguments();

        // IR BUSCAR TEXTO E METER VENCEDOR
        TextView winner_t = (TextView) view.findViewById(R.id.winner_text);
        winner_t.setText(mParam1 + " É O VENCEDOR!!!");

        // IR BUSCAR BOTAO E DETETAR CLIQUE PARA VOLTAR PO MENU
        Button back_menu = (Button) view.findViewById(R.id.back_menu_butt);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackToMenuInteraction();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WinnerFragment.OnWinnerFragmentInteractionListener) {
            mListener = (WinnerFragment.OnWinnerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnWinnerFragmentInteractionListener {
        void onBackToMenuInteraction();
    }
}