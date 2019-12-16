package com.renj.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_high_light).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_cover).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThreeActivity.class);
            startActivity(intent);
        });
    }
}
