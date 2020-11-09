package com.example.guerradasestrelas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.guerradasestrelas.fragments.GalleryFragment;
import com.example.guerradasestrelas.fragments.MainMenuFragment;
import com.example.guerradasestrelas.fragments.PlayFragment;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.OnMainMenuFragmentInteractionListener, PlayFragment.OnPlayFragmentInteractionListener, GalleryFragment.OnGalleryFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainMenuFragment fragmentMM = new MainMenuFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout, fragmentMM, "fragMM");
        fragmentTransaction.commit();
    }

    // Interactions para Main Menu fragment
    public void onPlayInteraction() {
        PlayFragment fragmentPlay = (PlayFragment) getSupportFragmentManager().findFragmentByTag("fragPlay");

        if(fragmentPlay == null){ // se ainda nao existe, criar
            fragmentPlay = PlayFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout, fragmentPlay, "fragPlay");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }else{ // se ja existe, simplesmente metelo a aparecer
            getSupportFragmentManager().popBackStack();
        }
    }

    public void onGalleryInteraction() {
        GalleryFragment fragmentGallery = (GalleryFragment) getSupportFragmentManager().findFragmentByTag("fragGal");

        if(fragmentGallery == null){ // se ainda nao existe, criar
            fragmentGallery = GalleryFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout, fragmentGallery, "fragGal");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }else{ // se ja existe, simplesmente metelo a aparecer
            getSupportFragmentManager().popBackStack();
        }
    }

    // Interactions para PlayFragment
    public void onBackInteraction(){ // Tambem serve como interaction para Gallery
        MainMenuFragment fragmentMM = (MainMenuFragment) getSupportFragmentManager().findFragmentByTag("fragMM");

        if(fragmentMM == null){ // se ainda nao existe, criar
            fragmentMM = MainMenuFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout, fragmentMM, "fragMM");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }else{ // se ja existe, simplesmente metelo a aparecer
            getSupportFragmentManager().popBackStack();
        }
    }

    public void onLocalMPInteraction() {
        // chamar atividade de local MP
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onVSPCInteraction() {
        // chamar atividade de vs pc
    }

    // Interactions para GalleryFragment (OnBackInteraction já está anteriormente)
}