package com.example.administrator.retriving;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {


    private Button setHeight;
    private EditText Height;
    private Double aqHeight;
    public FirebaseFirestore mFirestore3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        setHeight = (Button) findViewById(R.id.buttonSetHeight);
        Height = (EditText) findViewById(R.id.height);
        mFirestore3 = FirebaseFirestore.getInstance();
        setHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mainText = Height.getText().toString();
                try {
                    aqHeight = Double.parseDouble(mainText);
                } catch (NumberFormatException nfe) {
                }

                Toast.makeText(Settings.this, "Aquarium's height is set to: " + Height.getText().toString() + " cm", Toast.LENGTH_LONG).show();
                Log.d("aqht", aqHeight.toString() + " Worked!");

                Map<String, Double> dataToAdd = new HashMap<>();
                dataToAdd.put("Height", aqHeight);
                mFirestore3.collection("users").document("AqHeight").set(dataToAdd).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Settings.this, "Errrrrror" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}
