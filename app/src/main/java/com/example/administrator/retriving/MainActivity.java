package com.example.administrator.retriving;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TimePicker;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.GeoPoint;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import android.support.design.widget.Snackbar;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private static final String FIRE_LOG = "Fire _ log";

    private TextView TempText;
    private TextView textView;
    private TextView WaterLevel;
    public EditText mMainText;
    private TextView WaterLevelText;
    private Button mSaveBtn;
    private Button feed;
    private Button mLoadBtn;
    public String st;
    public Double HeatStatus;
    private FirebaseFirestore mFirestore;
    public Double myNum;
    private Double WLevelPercent;
    private Double tm;
    private String aqHeight;

    @Override
    protected void onStart() {
        super.onStart();


        mFirestore.collection("users").document("MeasTemp").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                Double tm = documentSnapshot.getDouble("temp");
                TempText.setText(String.valueOf(tm) + "° C");


            }
        });
        mFirestore.collection("users").document("AqHeight").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                aqHeight = String.valueOf(documentSnapshot.getDouble("Height"));


            }
        });
        mFirestore.collection("users").document("WaterLevelRaw").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                tm = documentSnapshot.getDouble("WLevelRaw");
                WaterLevel.setText(String.valueOf(tm));


                Log.d("Dbg", aqHeight);

                WLevelPercent = (tm / Double.valueOf(aqHeight)) * 100;

                WaterLevelText.setText(WLevelPercent + "%");
                //  }


            }
        });


        mFirestore.collection("users").document("MeasTemp").addSnapshotListener(new EventListener<DocumentSnapshot>() {


            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {


                //  DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists() && documentSnapshot != null) {
                    HeatStatus = documentSnapshot.getDouble("Heating");

                    if (HeatStatus == 1) {
                        st = "On";
                    } else {
                        st = "Off";
                    }
                    textView.setText(st);
                    //  Double temp = documentSnapshot.getDouble("temp");

                    // TempText.setText("Temp:    " + String.valueOf(temp));
                }

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();
        textView = (TextView) findViewById(R.id.textView);
        WaterLevel = (TextView) findViewById(R.id.textView4);
        TempText = (TextView) findViewById(R.id.welcomeText);
        mMainText = (EditText) findViewById(R.id.mainText2);
        WaterLevelText = (TextView) findViewById(R.id.waterLevelText);

        feed = (Button) findViewById(R.id.feed);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }


        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, com.example.administrator.retriving.Feed.class);
        startActivity(intent);

    }

}
