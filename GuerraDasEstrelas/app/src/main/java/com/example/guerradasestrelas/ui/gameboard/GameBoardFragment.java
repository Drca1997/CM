package com.example.guerradasestrelas.ui.gameboard;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guerradasestrelas.CardSlot;
import com.example.guerradasestrelas.Carta;
import com.example.guerradasestrelas.Jogador;
import com.example.guerradasestrelas.Jogo;
import com.example.guerradasestrelas.R;
import com.example.guerradasestrelas.Utils;

public class GameBoardFragment extends Fragment {

    private View view;

    public static GameBoardFragment newInstance() {
        return new GameBoardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_board_fragment, container, false);

        final Jogo jogo = new Jogo(getActivity(), view);
        CardSlot [] playerhandDisplayed = jogo.getHandSlots();
        for (final CardSlot slot : playerhandDisplayed){
            slot.getSlot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Carta temp = slot.getCarta();
                    if (temp != null){
                        int freeSlot = Utils.getNextFreeSlot(jogo.getJogadorAtual().getCampo()[temp.getFila()]);
                        if (freeSlot >= 0){
                            slot.setCarta(null); //remove carta do handSlot
                            jogo.getJogadorAtual().jogaCarta(temp, freeSlot);
                            jogo.acabaJogada();
                        }
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(GameBoardViewModel.class);


    }

    public View getView(){
        return view;
    }

}