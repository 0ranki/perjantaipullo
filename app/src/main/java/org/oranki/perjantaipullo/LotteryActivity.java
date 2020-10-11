package org.oranki.perjantaipullo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class LotteryActivity extends AppCompatActivity {

    ArrayList<Integer> selected_numbers;
    ArrayList<Integer> winners = new ArrayList<Integer>();
    Button start_button;
    ProgressBar progressBar;
    Spinner dropdown;
    ArrayAdapter<String> dropdown_adapter;
    int number_of_winners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        start_button = findViewById(R.id.button_start_lottery);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        dropdown = findViewById(R.id.spinner_numers_to_generate);
        String[] dropdown_items = new String[]{"1", "2", "3"};
        dropdown_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdown_items);
        dropdown.setAdapter(dropdown_adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number_of_winners = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), R.string.error2, Toast.LENGTH_SHORT).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_numbers = extras.getIntegerArrayList("numbers");
        }
    }

    public void onClickStartLottery(View v) {

        if (selected_numbers.size()!=0 && selected_numbers.size() >= number_of_winners) {
            start_button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            Log.d("jarno", "arvotaan " + number_of_winners);

            int index_of_winners[] = new int[number_of_winners];
            Random random = new Random();
            for (int i = 0; i < number_of_winners; i++) {
                int winner_index = Math.abs(random.nextInt()) % selected_numbers.size();
                index_of_winners[i] = winner_index;
                Log.d("jarno", "index_of_winners[" + i + "] :" + String.valueOf(winner_index));
            }
            for (int i = 0; i < index_of_winners.length; i++) {
                Log.d("jarno", "Voittaja: " + String.valueOf(selected_numbers.get(index_of_winners[i])));
                winners.add(i, selected_numbers.get(index_of_winners[i]));
                selected_numbers.remove(index_of_winners[i]);
            }


            new CountDownTimer(1000 * number_of_winners, 1000) {
                public void onFinish() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), WinnersActivity.class);
                    intent.putExtra("extras_winners", winners);
                    startActivity(intent);
                }

                public void onTick(long millisUntilFinished) {
                }
            }.start();
        }
        else if (selected_numbers.size() < number_of_winners) {
            Toast.makeText(getApplicationContext(), R.string.error1, Toast.LENGTH_LONG).show();
        }

    }

}