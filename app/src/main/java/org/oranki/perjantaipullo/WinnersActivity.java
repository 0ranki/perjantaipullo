package org.oranki.perjantaipullo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WinnersActivity extends AppCompatActivity {

    ArrayList<Integer> winners = new ArrayList<Integer>();
    TextView textView_winners2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        textView_winners2 = findViewById(R.id.textView_winners2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            winners = extras.getIntegerArrayList("extras_winners");
            Log.d("jarno", "ArrayList winners="+String.valueOf(winners));
            Log.d("jarno", "winners.size()=" + winners.size());
        }

        ImageView confetti = (ImageView)findViewById(R.id.imageView_confetti);
        confetti.setBackgroundResource(R.drawable.animation_confetti);

        AnimationDrawable frameAnimation = (AnimationDrawable) confetti.getBackground();

        frameAnimation.start();

        textView_winners2.setText(String.valueOf(winners));
    }

    public void onClickReset(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}