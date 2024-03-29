package com.example.appchat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class chatFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth firebaseAuth;

    ImageView mimageviewofuser;

    FirestoreRecyclerAdapter<firebaselodel,NoteViewHolder> chatAdapter;

    RecyclerView mrecyclerview;

    RelativeLayout eightchat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.chatfragment,container,false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        mrecyclerview=v.findViewById(R.id.recyclerview);
        eightchat = v.findViewById(R.id.eightchatgroup);



        //Query query=firebaseFirestore.collection("Users");
        Query query = firebaseFirestore.collection("Users").whereNotEqualTo("uid",firebaseAuth.getCurrentUser().getUid());
        FirestoreRecyclerOptions<firebaselodel> allusername=new FirestoreRecyclerOptions.Builder<firebaselodel>().setQuery(query,firebaselodel.class).build(); //why build

        chatAdapter=new FirestoreRecyclerAdapter<firebaselodel, NoteViewHolder>(allusername) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull firebaselodel firebaselodel) {

                noteViewHolder.particularusername.setText(firebaselodel.getName());
                String uri=firebaselodel.getImage();

                Picasso.get().load(uri).into(mimageviewofuser);
                if(firebaselodel.getStatus().equals("Online"))
                {
                    noteViewHolder.statusofuser.setText(firebaselodel.getStatus());
                    noteViewHolder.statusofuser.setTextColor(Color.GREEN);
                }
                else
                {
                    noteViewHolder.statusofuser.setText(firebaselodel.getStatus());
                }

                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),specificchat.class);
                        intent.putExtra("name",firebaselodel.getName());
                        intent.putExtra("receiveruid",firebaselodel.getUid());
                        intent.putExtra("imageuri",firebaselodel.getImage());
                        startActivity(intent);
                    }
                });
            }



            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatviewlayout,parent,false);
                return new NoteViewHolder(view);
            }
        };


        mrecyclerview.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setAdapter(chatAdapter);


        eightchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),eightchat.class);
                startActivity(intent);
            }
        });

        return v;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder
    {

        private TextView particularusername;
        private TextView statusofuser;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            particularusername=itemView.findViewById(R.id.nameofuser);
            statusofuser=itemView.findViewById(R.id.statusofuser);
            mimageviewofuser=itemView.findViewById(R.id.imageviewofuser);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            chatAdapter.startListening();
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
            documentReference.update("status", "Online");
            }
        }


    @Override
    public void onStop() {
        super.onStop();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            chatAdapter.stopListening();
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
            documentReference.update("status", "Offline");
            }

        }
}
