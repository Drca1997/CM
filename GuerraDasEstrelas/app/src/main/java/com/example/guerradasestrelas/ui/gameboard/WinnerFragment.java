package com.example.guerradasestrelas.ui.gameboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.fragments.GalleryFragment;

public class WinnerFragment extends Fragment {
    private static final String J1_NAME = "param1";
    private static final String J2_NAME = "param2";

    private static final String WINNER = "param3";

    private String mParam1;
    private String mParam2;

    private int mParam3;

    private View view;

    private WinnerFragment.OnWinnerFragmentInteractionListener mListener;

    public WinnerFragment() {
        // Required empty public constructor
    }

    public static WinnerFragment newInstance(String param1, String param2, int param3) {
        WinnerFragment fragment = new WinnerFragment();
        Bundle args = new Bundle();
        args.putString(J1_NAME, param1);
        args.putString(J2_NAME, param2);
        args.putInt(WINNER, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(J1_NAME);
            mParam2 = getArguments().getString(J2_NAME);
            mParam3 = getArguments().getInt(WINNER);
        }
    }

    public void initArguments(){
        if (getArguments() != null) {
            mParam1 = getArguments().getString(J1_NAME);
            mParam2 = getArguments().getString(J2_NAME);
            mParam3 = getArguments().getInt(WINNER);
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
        String win;
        if ((mParam3 == 1)) {
            win = mParam1;
        } else {
            win = mParam2;
        }
        winner_t.setText(win);

        // MUDAR A IMAGEM DE FUNDO DE ACORDO COM O VENCEDOR
        ImageView image_winner = (ImageView) view.findViewById(R.id.winner_image);
        if(mParam3==1){
            image_winner.setImageResource(R.drawable.player1_win_bolt);
            winner_t.setTextColor(view.getContext().getResources().getColor(R.color.white));
        }
        else{
            image_winner.setImageResource(R.drawable.player2_win_bolt);
            winner_t.setTextColor(view.getContext().getResources().getColor(R.color.black));
        }
        // IR BUSCAR BOTAO E DETETAR CLIQUE PARA VOLTAR PO MENU
        Button back_menu = (Button) view.findViewById(R.id.back_menu_butt);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackToMenuInteraction();
            }
        });

        Button desforra = (Button) view.findViewById(R.id.desforra_butt);
        desforra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChosenInteraction(mParam1, mParam2);
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
        void onChosenInteraction(String jog1, String jog2);
    }
}