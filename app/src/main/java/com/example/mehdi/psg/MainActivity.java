package com.example.mehdi.psg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mehdi.psg.R.layout.activity_main);

        Button btn_show_all = findViewById(R.id.show_all_btn);

        btn_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Vous allez affiché tout les joueurs", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, AllPlayerActivity.class);
                startActivity(i);
            }
        });
    }
}
