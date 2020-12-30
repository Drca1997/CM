package com.example.guerradasestrelas;

import android.animation.AnimatorSet;
import android.graphics.Rect;

public class ContainerClass {
    AnimatorSet set;
    float startScale;
    Rect startBounds;

    public ContainerClass(AnimatorSet set, float startScale, Rect startBounds){
        this.set = set;
        this.startScale = startScale;
        this.startBounds = startBounds;
    }

    public AnimatorSet getSet(){
        return this.set;
    }

    public float getStartScale(){
        return this.startScale;
    }

    public Rect getStartBounds(){
        return this.startBounds;
    }
}
