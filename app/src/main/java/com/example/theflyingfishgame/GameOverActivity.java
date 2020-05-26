package com.example.theflyingfishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button playAgainButton;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("score", 0);

        playAgainButton = findViewById(R.id.play_again_button);
        scoreText = findViewById(R.id.score);

        scoreText.setText("Your Score: " + score);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, MainActivity.class));
            }
        });

    }
}
