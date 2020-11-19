package com.example.guerradasestrelas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.guerradasestrelas.fragments.GalleryFragment;
import com.example.guerradasestrelas.fragments.MainMenuFragment;
import com.example.guerradasestrelas.fragments.PlayFragment;
import com.example.guerradasestrelas.ui.gameboard.GameBoardFragment;
import com.example.guerradasestrelas.ui.gameboard.NomeFragment;
import com.example.guerradasestrelas.ui.gameboard.SorteioFragment;
import com.example.guerradasestrelas.ui.gameboard.WinnerFragment;

public class GameActivity extends AppCompatActivity implements WinnerFragment.OnWinnerFragmentInteractionListener, GameBoardFragment.OnGameBoardFragmentInteractionListener, SorteioFragment.OnSorteioFragmentInteractionListener, NomeFragment.OnNomeFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        NomeFragment nomeFragment = NomeFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nomeFragment)
                    .commitNow();
        }

    }

    public void onSorteioDoneInteraction(int[] cards, String nome1, String nome2){
        //getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("fragSort")).commit();

        GameBoardFragment gameBoardFragment = GameBoardFragment.newInstance(cards,nome1,nome2);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, gameBoardFragment)
                .commitNow();
    }

    public void onGameWonInteraction(String winner, int winner_id){
        WinnerFragment fragmentWin = WinnerFragment.newInstance(winner, winner_id);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragmentWin)
                .commitNow();
    }

    public void onBackToMenuInteraction() {
        // acabar esta activity e voltar para a mainactivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChosenInteraction(String jog1, String jog2) {
        SorteioFragment sorteioFragment = SorteioFragment.newInstance(jog1,jog2);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, sorteioFragment, "fragSort")
                .commitNow();
    }
}