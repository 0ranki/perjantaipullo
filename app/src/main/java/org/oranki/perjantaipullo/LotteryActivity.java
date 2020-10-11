package org.oranki.perjantaipullo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class LotteryActivity extends AppCompatActivity {

    ArrayList<Integer> selected_numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_numbers = extras.getIntegerArrayList("numbers");
            if (selected_numbers != null) {
                Toast.makeText(getApplicationContext(), "Valitut numerot: " + selected_numbers.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}