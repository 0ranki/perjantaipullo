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

    ArrayList<Integer> selected_numbers;                // Valitut numerot
    ArrayList<Integer> winners = new ArrayList<Integer>();    // Voittajanumerot
    Button start_button;
    ProgressBar progressBar;    // Pyörivä ilmaisin, joka näytetään "arvonnan" aikana
    Spinner dropdown;           // Valikko, montako numeroa arvotaan
    ArrayAdapter<String> dropdown_adapter;
    int number_of_winners;      // Arvottavien lukujen määrä

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        start_button = findViewById(R.id.button_start_lottery);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);  // Piilotetaan ensin progressbar

        // Täytetään dropdown-valikko
        dropdown = findViewById(R.id.spinner_numers_to_generate);
        String[] dropdown_items = new String[]{"1", "2", "3"};
        dropdown_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdown_items);
        dropdown.setAdapter(dropdown_adapter);

        // onClick-metodi dropdown-valikon itemeille
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Voittajien lukumäärä on valitun itemin indeksi + 1
                number_of_winners = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tämä on vain kaiken varalta, oletuksena valikosta on valittuna 1 numero
                Toast.makeText(getApplicationContext(), R.string.error2, Toast.LENGTH_SHORT).show();
            }
        });

        // Haetaan SelectActivitystä Intent Extrana tuotu valittujen numeroiden ArrayList
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_numbers = extras.getIntegerArrayList("numbers");
        }
    }

    // OnClick-metodi Arvontaan-napille
    public void onClickStartLottery(View v) {

        // Jos valittuja numeroita >= arvottavia numeroita
        if (selected_numbers.size()!=0 && selected_numbers.size() >= number_of_winners) {
            // Nappi piiloon, progressbar tilalle
            start_button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            // Debuggausta
            Log.d("jarno", "arvotaan " + number_of_winners);

            // Arvotaan indeksit selected_numbers-ArrayLististä, ei varsinaisia valittuja numeroita
            // Samojen numeroiden tarkistusta varten alustetaan varmasti sopimattomaksi numeroksi
            int index_of_winners[] = new int[]{0xFF,0xFF,0xFF};
            Random random = new Random();

            // Arvotaan määritetty määrä voittavia indeksejä index_of_winners-taulukkoon
            // Arvotaan ensin indeksi, sitten tarkistetaan että ei tule sama numero kahteen kertaan
            for (int i = 0; i < number_of_winners; i++) {
                boolean hasWon; // jos numero on jo voittanut
                do {
                    hasWon = false;
                    int winner_index = Math.abs(random.nextInt()) % selected_numbers.size(); // Modulo = 0...valittujen numeroiden määrä
                    if (i == 0) {   // jos ensimmäinen arvottava numero, ei tarvitse tarkistella
                        index_of_winners[i] = winner_index;     // Laitetaan voittava indeksi muistiin
                        Log.d("jarno", "winner_index=" + winner_index);
                    }
                    else if (i > 0) {
                        // Käydään index_of_winners läpi ja verrataan juuri arvottuun numeroon
                        for (int j=0; j<index_of_winners.length; j++) {
                            if (index_of_winners[j] == winner_index) {
                                // Jos löytyy jo, tehdään do-while-loop uusiksi
                                hasWon = true;
                                Log.d("jarno", "hasWon=true");
                            }
                        }
                        if (!hasWon) {
                            // Jos ei ollut jo arvottu, lisätään numero
                            index_of_winners[i] = winner_index;
                            Log.d("jarno", "winner_index="+winner_index);
                        }
                    }
                } while (hasWon);


            }


            // Haetaan voittava numero äsken arvottujen indeksien perusteella selected_numbersista
            for (int i = 0; i < index_of_winners.length; i++) {
                // Debuggausta
                Log.d("jarno", "Voittaja: " + String.valueOf(selected_numbers.get(index_of_winners[i])));
                winners.add(i, selected_numbers.get(index_of_winners[i]));  // Haetaan voittava NUMERO winners-ArrayListiin
            }

            // Viive 1s arvottavaa numeroa kohti, näytetään sillä aikaa pyörivää progressbaria.
            // jotta tuntuu hienommalta
            new CountDownTimer(1000 * number_of_winners, 1000) {
                // onFinish kutsutaan, kun ajastin on loppu
                public void onFinish() {
                    progressBar.setVisibility(View.INVISIBLE); // progressbar piiloon
                    // Mennään WinnersActivityyn, mukana winners-ArrayList
                    Intent intent = new Intent(getApplicationContext(), WinnersActivity.class);
                    intent.putExtra("extras_winners", winners);
                    startActivity(intent);
                }

                public void onTick(long millisUntilFinished) {
                    //onTick kutsutaan countDownInterval välein
                }
            }.start(); // Käynnistetään ajastin
        }
        else if (selected_numbers.size() < number_of_winners) {
            // Jos osallistujia < arvottavia numeroita, näytetään Toast
            Toast.makeText(getApplicationContext(), R.string.error1, Toast.LENGTH_LONG).show();
        }

    }

}