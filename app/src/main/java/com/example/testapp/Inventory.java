package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Inventory extends AppCompatActivity {
    private ImageButton gro;
    private ImageButton app;
    private ImageButton sta;
    private ImageButton fur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        getSupportActionBar().setTitle("Home maker");
        gro = (ImageButton) findViewById(R.id.Groceries);
        gro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengro();
            }
        });
        app = (ImageButton) findViewById(R.id.Appliances);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openapp();
            }
        });
        sta = (ImageButton) findViewById(R.id.Stationery);
        sta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensta();
            }
        });
        fur = (ImageButton) findViewById(R.id.Furniture);
        fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfur();
            }
        });
    }
    public void opengro(){
        Intent intent = new Intent(this, Groceries.class);
        startActivity(intent);
    }
    public void openapp(){
        Intent intent = new Intent(this, Appliances.class);
        startActivity(intent);
    }
    public void opensta(){
        Intent intent = new Intent(this, Stationery.class);
        startActivity(intent);
    }
    public void openfur(){
        Intent intent = new Intent(this, Furniture.class);
        startActivity(intent);
    }
}