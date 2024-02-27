package com.example.appchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.model.Document;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class eightchatadapter extends RecyclerView.Adapter<eightchatviewholder> {

    Context context;
    ArrayList<eightMessages> messages;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Query query = FirebaseFirestore.getInstance().collection("Users");


    public eightchatadapter(Context context,ArrayList<eightMessages> messages){
        this.context=context;
        this.messages=messages;

    }

    @NonNull
    @Override
    public eightchatviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eightchatgroupmodel,parent,false);
        return new eightchatviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull eightchatviewholder holder, int position) {

       // Picasso.get().load(messages.get(position).getImageuri()).into(holder.ppofuser);
        Glide.with(context)
                .load(messages.get(position).getImageuri())
                .into(holder.ppofuser);
        holder.message.setText(messages.get(position).getMessage());
        holder.time.setText(messages.get(position).getCurrenttime());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
