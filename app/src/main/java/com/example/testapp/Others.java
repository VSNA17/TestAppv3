package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Others extends AppCompatActivity {

    private FirebaseFirestore dboth = FirebaseFirestore.getInstance();
    private CollectionReference othrRef = dboth.collection("users");
    private FirebaseAuth fAuth;
    private EAdapter adapter;
    private FloatingActionButton savebutton;
    private String asd;

    private Button others_button;
    private String search;
    private EditText searchbar_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Others");

        fAuth = FirebaseAuth.getInstance();
        savebutton = findViewById(R.id.add_item_oth);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Others.this, NewItemOthers.class));
            }
        });

        buildrecyclerview();

        searchbar_others=findViewById(R.id.searchbar_others);
        others_button=findViewById(R.id.others_button);

        others_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search= searchbar_others.getText().toString().toLowerCase();
                Query query = othrRef.document(asd).collection("othRef").orderBy("title").startAt(search).endAt(search + "\uf8ff");
                FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
                adapter.updateOptions(options);

            }
        });

    }

    private void buildrecyclerview(){

        asd = fAuth.getCurrentUser().getUid();
        Query query = othrRef.document(asd).collection("othRef");
        FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
        adapter = new EAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recviewoth);
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