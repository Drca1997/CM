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
import android.widget.Button;

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
                            jogo.getJogadorAtual().jogaCarta(temp, freeSlot, jogo.getTurno(), jogo.getJogadores());
                            jogo.acabaJogada();
                        }
                        else{
                            //Será aqui o sitio ideal para detetar habilidade de Madonna e Ronaldo?
                            if (temp.getHabilidade() != null){
                                if (temp.getHabilidade().getNome().equals("MultiFila")){
                                    int outrafila = Utils.getOutraFila(temp.getFila());
                                    int slotOutraFila = Utils.getNextFreeSlot(jogo.getJogadorAtual().getCampo()[outrafila]);
                                    if (slotOutraFila >= 0){
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
            });
        }
        Button button = view.findViewById(R.id.skipButt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogo.getJogadorAtual().setSkipped(true);
                jogo.skipRound();
            }
        });

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