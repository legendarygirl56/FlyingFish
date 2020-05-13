package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {

    private Button StartAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        StartAgain = (Button) findViewById(R.id.playagain);
        StartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent= new Intent(GameOver.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
