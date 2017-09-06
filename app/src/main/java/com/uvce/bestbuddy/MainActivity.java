package com.uvce.bestbuddy;

import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    EditText dairyInput;
    String numberOfPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dairyInput=(EditText)findViewById(R.id.dairy_input);

        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference().child("Testuser");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    public void submitDairy(View v)
    {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        numberOfPosts="5";
        ref.child("Post Count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numberOfPosts=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        HashMap hashMap=new HashMap();
        hashMap.put("Date",currentDateTimeString);
        hashMap.put("Record",dairyInput.getText().toString().trim());
        hashMap.put("Score",0);


        ref.child("Post "+numberOfPosts).setValue(hashMap);

        Toast.makeText(getApplicationContext(),"Your day has been saved",Toast.LENGTH_LONG).show();
        dairyInput.setText(null);
    }
}
