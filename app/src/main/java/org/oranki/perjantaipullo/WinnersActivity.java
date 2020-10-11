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
    TextView textView_winners1;
    TextView textView_winners2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        textView_winners1 = findViewById(R.id.textView_winners1);
        textView_winners2 = findViewById(R.id.textView_winners2);

        // Haetaan edelliseltä activityltä tuotu winners-ArrayList
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            winners = extras.getIntegerArrayList("extras_winners");
            Log.d("jarno", "ArrayList winners="+String.valueOf(winners));
            Log.d("jarno", "winners.size()=" + winners.size());
        }

        // Animoitu taustakuva:
        ImageView confetti = (ImageView)findViewById(R.id.imageView_confetti);  // Haetaan kyseinen ImageView
        confetti.setBackgroundResource(R.drawable.animation_confetti);          // Asetetaan TAUSTAKUVAKSI animaatio
        // animation confetti määritelty app/res/drawable/animation_confetti.xml:ssä kuva kuvalta
        // koska Android ei tue GIFejä

        // Luodaan AnimationDrawable ImageView:stä
        AnimationDrawable frameAnimation = (AnimationDrawable) confetti.getBackground();
        frameAnimation.start();

        // Voittavat numerot TextView:hin
        if (winners.size() == 1) textView_winners1.setText(R.string.winner);
        textView_winners2.setText(String.valueOf(winners));
    }

    // OnClick Alkuun-napille
    public void onClickReset(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        // Asetetaan liput, joilla Intentin yhteydessä aloitetaan uusi Task, tai tyhjennetään entinen
        // Jotta vanhat arvotut numerot eivät jää kummittelemaan
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}