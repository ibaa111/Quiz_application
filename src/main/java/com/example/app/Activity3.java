package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {

    private String nom = "";
    private String matiere = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // 1) Recevoir le Bundle envoyé par Activity2
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            nom = bundle.getString("nom", "");
            matiere = bundle.getString("matiere", "");
        }

        TextView tvTitreCours = findViewById(R.id.tvTitreCours);
        TextView tvEleve = findViewById(R.id.tvEleve);
        Button btnQuiz = findViewById(R.id.btnQuiz);

        tvTitreCours.setText("Cours de " + matiere);
        tvEleve.setText("Élève : " + nom);

        // 2) Envoyer nom + matière vers Activity4 (le quiz)
        btnQuiz.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("nom", nom);
            b.putString("matiere", matiere);

            Intent intent = new Intent(Activity3.this, Activity4.class);
            intent.putExtra("data", b);
            startActivity(intent);
        });
    }
}