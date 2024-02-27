package com.example.appchat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class eightchatviewholder extends RecyclerView.ViewHolder {

    public TextView message;
    public TextView time;
    public ImageView ppofuser;
    public String senderid;


    public eightchatviewholder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.sendermessage);
        time = itemView.findViewById(R.id.timeofmessage);
        ppofuser=itemView.findViewById(R.id.ppofsender);
    }
}
