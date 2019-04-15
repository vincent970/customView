package com.example.a533.cours11;

import android.graphics.Bitmap;

public class Marqueur implements PlainViewDisplayable {
    private float positionX = 0;
    private float positionY = 0;
    private Bitmap marqueur;

    public Marqueur(Bitmap marqueur, int x, int y) {
        positionX = x - marqueur.getHeight()/2;
        positionY = y - marqueur.getWidth()/2;
        this.marqueur = marqueur;
    }

    @Override
    public float getPositionX() {
        return positionX;
    }

    @Override
    public float getPositionY() {
        return positionY;
    }

    @Override
    public float getWidth() {
        return marqueur.getWidth();
    }

    @Override
    public float getHeight() {
        return marqueur.getHeight();
    }

    @Override
    public Bitmap getBitmap() {
        return marqueur;
    }
}
