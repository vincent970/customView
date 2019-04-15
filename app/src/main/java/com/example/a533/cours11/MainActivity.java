package com.example.a533.cours11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setObjectToDraw();
    }

    private void setObjectToDraw(){
        Bitmap planBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plan);
        PlainView plainView = findViewById(R.id.planView);
        plainView.addElementToDisplay(new Plan(planBitmap));
    }
}
