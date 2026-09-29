package com.example.app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class accueilactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueilactivity);

        EditText editNom = findViewById(R.id.editNom);
        Button btnCommencer = findViewById(R.id.btnCommencer);

        btnCommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editNom.getText().toString();

                Bundle b = new Bundle();
                b.putString("nom", nom);
                Intent intent = new Intent(accueilactivity.this, Activity2.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }
}