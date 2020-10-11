package org.oranki.perjantaipullo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> selected_numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_numbers = extras.getIntegerArrayList("numbers");
            if (selected_numbers != null) {
                Toast.makeText(getApplicationContext(), "Valitut numerot: " + selected_numbers.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onClickSelect(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

}