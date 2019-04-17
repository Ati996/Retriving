package com.example.administrator.retriving;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.AdapterView.OnClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

public class Feed extends AppCompatActivity {

    public TimePicker time_picker;
    private Button button_show_time;
    private Button options;
    private Double myNum;
    public Integer check = 0;
    public Double FeedLevel;
    private Button mSaveBtn;
    private EditText mMainText;
    private EditText mMainText2;


    Spinner dropdown;
    public FirebaseFirestore mFirestore2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        time_picker =  findViewById(R.id.time);
        button_show_time = findViewById(R.id.button);
        mFirestore2 = FirebaseFirestore.getInstance();
        mSaveBtn = findViewById(R.id.saveBtn2);
        options = findViewById(R.id.button2);
        mMainText = findViewById(R.id.mainText2);

        String mainText = mMainText.getText().toString();

        final Spinner staticSpinner = findViewById(R.id.spinner1);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);


        staticSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (++check > 1) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    Toast.makeText(Feed.this, (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();


                    Map<String, Integer> dataToAdd = new HashMap<>();
                    dataToAdd.put("Level", position - 1);
                    mFirestore2.collection("users").document("FeedLevel").set(dataToAdd).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Feed.this, "Errrrrror" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        button_show_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   String mainText = time_picker.getCurrentHour().getText().toString();


                Map<String, Integer> dataToAdd = new HashMap<>();
                dataToAdd.put("Hour", time_picker.getCurrentHour());
                mFirestore2.collection("users").document("FeedHour").set(dataToAdd).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Feed.this, "Errrrrror" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                Map<String, Integer> dataToAdd2 = new HashMap<>();
                dataToAdd2.put("Minute", time_picker.getCurrentMinute());
                mFirestore2.collection("users").document("FeedMinute").set(dataToAdd2).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Feed.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        options.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });


    }

    public void openActivity2() {
        Intent intent = new Intent(this, com.example.administrator.retriving.Settings.class);
        startActivity(intent);

    }

}