package com.example.guerradasestrelas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.guerradasestrelas.fragments.GalleryFragment;
import com.example.guerradasestrelas.fragments.MainMenuFragment;
import com.example.guerradasestrelas.fragments.PlayFragment;
import com.example.guerradasestrelas.ui.gameboard.GameBoardFragment;
import com.example.guerradasestrelas.ui.gameboard.SorteioFragment;
import com.example.guerradasestrelas.ui.gameboard.WinnerFragment;

public class GameActivity extends AppCompatActivity implements WinnerFragment.OnWinnerFragmentInteractionListener, GameBoardFragment.OnGameBoardFragmentInteractionListener, SorteioFragment.OnSorteioFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        SorteioFragment sorteioFragment = SorteioFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, sorteioFragment, "fragSort")
                    .commitNow();
        }

        /*
        // Chamar primeiro o sorteio
        // Quando sorteio acaba, chama o gameboardfragment com as cartas
        int [] cards = {}; //enqt n ha sorteio

        GameBoardFragment gameBoardFragment = GameBoardFragment.newInstance(cards);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gameBoardFragment)
                    .commitNow();
        }
        */
    }

    public void onSorteioDoneInteraction(int[] cards){
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("fragSort")).commit();

        GameBoardFragment gameBoardFragment = GameBoardFragment.newInstance(cards);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, gameBoardFragment)
                .commitNow();
    }

    public void onGameWonInteraction(String winner){
        WinnerFragment fragmentWin = WinnerFragment.newInstance(winner);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragmentWin)
                .commitNow();
    }

    public void onBackToMenuInteraction() {
        // acabar esta activity e voltar para a mainactivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}