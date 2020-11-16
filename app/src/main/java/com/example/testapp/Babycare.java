package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Babycare extends AppCompatActivity {

    private FirebaseFirestore dbbac = FirebaseFirestore.getInstance();
    private CollectionReference babcRef = dbbac.collection("users");
    private FirebaseAuth fAuth;
    private EAdapter adapter;
    private FloatingActionButton savebutton;
    private String asd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babycare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Baby care");

        fAuth = FirebaseAuth.getInstance();
        savebutton = findViewById(R.id.add_item_bac);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Babycare.this, NewItemBabycare.class));
            }
        });

        buildrecyclerview();

    }

    private void buildrecyclerview(){

        asd = fAuth.getCurrentUser().getUid();
        Query query = babcRef.document(asd).collection("bacRef");
        FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
        adapter = new EAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recviewbac);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}