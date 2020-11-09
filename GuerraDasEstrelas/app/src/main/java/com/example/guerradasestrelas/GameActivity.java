package com.example.guerradasestrelas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.guerradasestrelas.fragments.MainMenuFragment;
import com.example.guerradasestrelas.ui.gameboard.GameBoardFragment;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        GameBoardFragment gameBoardFragment = GameBoardFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gameBoardFragment)
                    .commitNow();


        }


    }

}