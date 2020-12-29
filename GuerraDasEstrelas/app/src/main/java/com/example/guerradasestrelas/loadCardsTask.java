package com.example.guerradasestrelas;

import android.os.AsyncTask;

public class loadCardsTask extends AsyncTask<Void, Void, Carta[]> {
    BaseDados bd;

    public loadCardsTask(BaseDados bd){
        this.bd = bd;
    }

    @Override
    protected Carta[] doInBackground(Void... voids) {
        return bd.GetAllCards();
    }

    @Override
    protected void onPostExecute(Carta[] cartas) {
        super.onPostExecute(cartas);
    }
}
