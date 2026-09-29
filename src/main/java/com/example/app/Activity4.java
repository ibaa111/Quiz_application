package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity4 extends AppCompatActivity {

    private static final int DUREE_TOTALE = 30;   // secondes
    private static final int INTERVALLE_MS = 500;

    private String nom = "";
    private String matiere = "";

    private RadioGroup[] groupes;
    private int[] bonnesReponses;

    private TextView tvTemps;
    private ProgressBar progressTemps;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private int tempsRestant = DUREE_TOTALE;
    private boolean termine = false;

    // Ce code s'exécute chaque seconde
    private final Runnable compteARebours = new Runnable() {
        @Override
        public void run() {
            tempsRestant--;
            afficherTemps();

            if (tempsRestant <= 0) {
                Toast.makeText(Activity4.this, "Temps écoulé !", Toast.LENGTH_SHORT).show();
                envoyerResultat();
            } else {
                handler.postDelayed(this, INTERVALLE_MS);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        // 1) Recevoir le Bundle envoyé par Activity3
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            nom = bundle.getString("nom", "");
            matiere = bundle.getString("matiere", "");
        }

        TextView tvTitreQuiz = findViewById(R.id.tvTitreQuiz);
        tvTitreQuiz.setText("Quiz de " + matiere);

        tvTemps = findViewById(R.id.tvTemps);
        progressTemps = findViewById(R.id.progressTemps);
        progressTemps.setMax(DUREE_TOTALE);
        afficherTemps();

        TextView[] tvQuestions = {
                findViewById(R.id.tvQ1),
                findViewById(R.id.tvQ2),
                findViewById(R.id.tvQ3)
        };
        groupes = new RadioGroup[]{
                findViewById(R.id.rg1),
                findViewById(R.id.rg2),
                findViewById(R.id.rg3)
        };

        String[] questions;
        String[][] options;

        if (matiere.equals("Maths")) {
            questions = new String[]{"2 + 3 = ?", "5 x 4 = ?", "Combien de côtés a un triangle ?"};
            options = new String[][]{{"4", "5", "6"}, {"20", "9", "25"}, {"4", "3", "5"}};
            bonnesReponses = new int[]{1, 0, 1};
        } else if (matiere.equals("Français")) {
            questions = new String[]{"Quel mot est un verbe ?", "Pluriel de cheval ?", "Quelle phrase est correcte ?"};
            options = new String[][]{{"chat", "manger", "rouge"}, {"chevals", "chevaus", "chevaux"}, {"Ils mangent", "Ils mange", "Ils manges"}};
            bonnesReponses = new int[]{1, 2, 0};
        } else {
            questions = new String[]{"Quelle planète est la plus proche du Soleil ?", "À quelle température l'eau bout-elle ?", "Quel gaz respirons-nous pour vivre ?"};
            options = new String[][]{{"Mercure", "Terre", "Mars"}, {"50 °C", "100 °C", "200 °C"}, {"Hélium", "Azote", "Oxygène"}};
            bonnesReponses = new int[]{0, 1, 2};
        }

        for (int i = 0; i < 3; i++) {
            tvQuestions[i].setText(questions[i]);
            for (int j = 0; j < 3; j++) {
                RadioButton rb = (RadioButton) groupes[i].getChildAt(j);
                rb.setText(options[i][j]);
            }
        }

        Button btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(v -> valider());
    }

    // ===== Cycle de vie : le minuteur suit l'écran =====

    // L'écran est visible : le temps reprend
    @Override
    protected void onResume() {
        super.onResume();
        if (!termine && tempsRestant > 0) {
            handler.removeCallbacks(compteARebours);
            handler.postDelayed(compteARebours, INTERVALLE_MS);
        }
    }

    // L'utilisateur quitte le quiz : le temps est mis en pause
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(compteARebours);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    // ===== Affichage du temps =====

    private void afficherTemps() {
        tvTemps.setText("Temps restant : " + tempsRestant + " s");
        progressTemps.setProgress(tempsRestant);
    }

    // ===== Validation =====

    private void valider() {
        // Toutes les questions doivent avoir une réponse
        for (int i = 0; i < 3; i++) {
            if (groupes[i].getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Réponds à toutes les questions", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        envoyerResultat();
    }

    private int calculerScore() {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            int idChoisi = groupes[i].getCheckedRadioButtonId();
            if (idChoisi != -1) {   // sans réponse (temps écoulé) : 0 point
                RadioButton choisi = findViewById(idChoisi);
                if (groupes[i].indexOfChild(choisi) == bonnesReponses[i]) {
                    score++;
                }
            }
        }
        return score;
    }

    private void envoyerResultat() {
        if (termine) return;   // évite d'envoyer deux fois
        termine = true;
        handler.removeCallbacks(compteARebours);

        // 4) Envoyer nom, matière, score et total vers Activity5
        Bundle b = new Bundle();
        b.putString("nom", nom);
        b.putString("matiere", matiere);
        b.putInt("score", calculerScore());
        b.putInt("total", 3);

        Intent intent = new Intent(Activity4.this, Activity5.class);
        intent.putExtra("data", b);
        startActivity(intent);
        finish();
    }
}