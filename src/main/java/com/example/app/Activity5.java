package com.example.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        String nom = "";
        String matiere = "";
        int score = 0;
        int total = 3;

        // 1) Recevoir le Bundle envoyé par Activity4
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            nom = bundle.getString("nom", "");
            matiere = bundle.getString("matiere", "");
            score = bundle.getInt("score", 0);
            total = bundle.getInt("total", 3);
        }

        TextView tvResultat = findViewById(R.id.tvResultat);
        TextView tvMatiere = findViewById(R.id.tvMatiere);
        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvMessage = findViewById(R.id.tvMessage);
        Button btnRecommencer = findViewById(R.id.btnRecommencer);

        // 2) Afficher les données
        tvResultat.setText("Note  " + nom + " !");
        tvMatiere.setText("Matière : " + matiere);
        tvScore.setText(score + "/" + total);

        // 3) Couleur du badge et message selon le score
        int pourcentage = score * 100 / total;
        if (pourcentage >= 60) {
            tvScore.setBackgroundColor(Color.parseColor("#4CAF50"));   // vert
            tvMessage.setText("Excellent travail !");
        } else if (pourcentage >= 30) {
            tvScore.setBackgroundColor(Color.parseColor("#FF9800"));   // orange
            tvMessage.setText("Bien joué, tu peux encore progresser.");
        } else {
            tvScore.setBackgroundColor(Color.parseColor("#F44336"));   // rouge
            tvMessage.setText("Relis le cours et réessaie.");
        }

        // 4) Recommencer : retour à l'accueil
        btnRecommencer.setOnClickListener(v -> {
            Intent intent = new Intent(Activity5.this, accueilactivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}