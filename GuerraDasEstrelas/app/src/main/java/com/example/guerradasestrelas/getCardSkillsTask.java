package com.example.guerradasestrelas;

import android.os.AsyncTask;

public class getCardSkillsTask extends AsyncTask<Void, Void, Void> {
    BaseDados bd;
    Carta[] cartas;

    public getCardSkillsTask(BaseDados bd, Carta[] cartas){
        this.bd = bd;
        this.cartas = cartas;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        bd.getCardsSkills(cartas);
        return null;
    }
}
