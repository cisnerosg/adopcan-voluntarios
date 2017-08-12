package com.adopcan.adopcan_voluntarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListDogs extends AppCompatActivity {

    private List<Dog> dogs;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dogs);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        dogs = new ArrayList<>();
        dogs.add(new Dog("Emma Wilson", "23 years old", R.drawable.adopcan));
        dogs.add(new Dog("Lavery Maiss", "25 years old", R.drawable.dog));
        dogs.add(new Dog("Lillie Watts", "35 years old", R.drawable.perro));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(dogs);
        rv.setAdapter(adapter);
    }

}