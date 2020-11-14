package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewItemFurniture extends AppCompatActivity {
    private EditText nameedit;
    private EditText initqtyedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Item");
        nameedit = findViewById(R.id.nameedit);
        initqtyedit = findViewById(R.id.initqtyedit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_changes:
                savenote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void savenote(){
        String title = nameedit.getText().toString();
        String initialquantity = initqtyedit.getText().toString();

        if(title.trim().isEmpty()||initialquantity.trim().isEmpty()){
            Toast.makeText(this, "Please add the Name and Initial quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference dbfurRef = FirebaseFirestore.getInstance().collection("furRef");
        dbfurRef.add(new Set_item(R.drawable.ic_baseline_add,title,"Quantity available:",initialquantity));
        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        finish();
    }
}