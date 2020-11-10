package com.example.guerradasestrelas;

import android.widget.ImageButton;

public class CardSlot {
    private ImageButton slot;
    private Carta carta;

    public CardSlot(ImageButton imageButton){
        this.slot = imageButton;
        this.carta = null;
        slot.setImageResource(R.drawable.blank);
    }

    public ImageButton getSlot(){
        return slot;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
        if (carta != null){
            slot.setImageResource(carta.getId_mini());
        }
        else{
            slot.setImageResource(R.drawable.blank);
        }
    }
}
