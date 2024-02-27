package com.example.appchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class eightchat extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    EditText mgetmessage;
    ImageButton msendmessagebutton;

    CardView msendmessage;

    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    ImageView mimageviewofspecificuser;
    TextView mnameofspecificuser;

    private String enteredmessage;
    Intent intent;
    String mrecievername, msendername, mrecieveruid, msenderuid;
    String imageuri;


    ImageButton mbackbutton;

    RecyclerView recyclerView;

    String currentime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    eightchatadapter messagesAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<eightMessages> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eightchat);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("8ChatGroup");
        firebaseStorage = FirebaseStorage.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        msendmessagebutton = findViewById(R.id.imageviewsendmessage);
        mgetmessage = findViewById(R.id.getmessage);
        mbackbutton = findViewById(R.id.backbuttonofspecificchat);
        recyclerView = findViewById(R.id.recyclerviewofspecific);

        setSupportActionBar(mtoolbarofspecificchat);


        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messagesAdapter);

        messages = new ArrayList<eightMessages>();


        mbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mgetmessage.getText().toString().isEmpty()) {
                   // Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    String msg = mgetmessage.getText().toString();
                    mgetmessage.setText(null);
                    currentime = simpleDateFormat.format(calendar.getTime());
                    storageReference = firebaseStorage.getReference();
                    storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(@NonNull Uri uri) {
                            imageuri = uri.toString();
                            eightMessages message = new eightMessages(msg, firebaseAuth.getUid(), date.getTime(), currentime, imageuri);
                            databaseReference.push().setValue(message);

                        }
                    });
                }
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    eightMessages message1=snapshot1.getValue(eightMessages.class);
                    messages.add(message1);

                }

                messagesAdapter = new eightchatadapter(eightchat.this, messages);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(messagesAdapter);  //A LOT IMPORTANT
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}




