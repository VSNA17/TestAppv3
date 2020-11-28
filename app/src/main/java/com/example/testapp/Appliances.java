package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Appliances extends AppCompatActivity {

    private FirebaseFirestore dbapl = FirebaseFirestore.getInstance();
    private CollectionReference applRef = dbapl.collection("users");
    private FirebaseAuth fAuth;
    private EAdapter adapter;
    private FloatingActionButton savebutton;
    private String asd;

    private Button appliances_button;
    private String search;
    private EditText searchbar_appliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appliances");

        fAuth = FirebaseAuth.getInstance();
        savebutton = findViewById(R.id.add_item_apl);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Appliances.this, NewItemAppliance.class));
            }
        });

        buildrecyclerview();

        searchbar_appliances=findViewById(R.id.searchbar_appliances);
        appliances_button=findViewById(R.id.appliances_button);

        appliances_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search= searchbar_appliances.getText().toString();
                Query query = applRef.document(asd).collection("aplRef").orderBy("title").startAt(search).endAt(search + "\uf8ff");
                FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
                adapter.updateOptions(options);

            }
        });
    }

    private void buildrecyclerview(){

        asd = fAuth.getCurrentUser().getUid();
        Query query = applRef.document(asd).collection("aplRef");
        FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
        adapter = new EAdapter(options,this);
        RecyclerView recyclerView = findViewById(R.id.recviewapl);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Set_item note = documentSnapshot.toObject(Set_item.class);
                shareitem(note);
            }
        });
    }

    private void shareitem(Set_item note) {
        Intent myIntent= new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody=note.getTitle();
        String shareSub="APPLIANCE ITEM SHARING";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        String sharebody1=note.getInitqty();
        String img= note.getImgres();
        myIntent.putExtra(Intent.EXTRA_TEXT,"Item Name: "+shareBody+"\n"+ "Available Quantity: "+sharebody1+"\n"+ "image url: "+img);
        startActivity(Intent.createChooser(myIntent,"Share using"));
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