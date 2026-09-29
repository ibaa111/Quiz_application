package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    private String nom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            nom = bundle.getString("nom", "");
        }

        TextView tvSalut = findViewById(R.id.tvSalut);
        tvSalut.setText("Salut " + nom + " !");

        Button btnMaths = findViewById(R.id.btnMaths);
        Button btnFrancais = findViewById(R.id.btnFrancais);
        Button btnSciences = findViewById(R.id.btnSciences);

        btnMaths.setOnClickListener(v -> ouvrirCours("Maths"));
        btnFrancais.setOnClickListener(v -> ouvrirCours("Français"));
        btnSciences.setOnClickListener(v -> ouvrirCours("Sciences"));
    }

    private void ouvrirCours(String matiere) {
        Bundle b = new Bundle();
        b.putString("nom", nom);
        b.putString("matiere", matiere);

        Intent intent = new Intent(Activity2.this, Activity3.class);
        intent.putExtra("data", b);
        startActivity(intent);
    }
}