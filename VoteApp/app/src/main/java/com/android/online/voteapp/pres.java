package com.android.online.voteapp;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.online.voteapp.Candidate.FormModelClass;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rey.material.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class pres extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase, mbook;
    private StorageReference mStorageRef;
    private Uri MImageURI;
    private ProgressDialog loadingBar;
    private Button locate;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pres);

        loadingBar = new ProgressDialog(this);
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("category");

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Forms_Submitted");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Forms_Submitted");

        mDatabase.keepSynced(true);
        recyclerView = findViewById(R.id.president_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getPresident();
    }

    private void getPresident() {

        Query queries = mDatabase.orderByChild("seat").equalTo("President");

        FirebaseRecyclerOptions<FormModelClass> option = new FirebaseRecyclerOptions.Builder<FormModelClass>()
                .setQuery(queries, FormModelClass.class)
                .build();

        FirebaseRecyclerAdapter<FormModelClass, PresidentViewHolder> adapter = new FirebaseRecyclerAdapter<FormModelClass, PresidentViewHolder>(option) {

            @Override
            public int getItemCount() {
                Log.d("NB", "getItemCount: " + super.getItemCount());
                return super.getItemCount();
            }

            @Override
            protected void onBindViewHolder(@NonNull final PresidentViewHolder holder, int position, @NonNull final FormModelClass model) {
                Glide.with(pres.this).load(model.getImageurl()).into(holder.imageView);

                holder.nameTextView.setText(model.getName());
                holder.checkBox.setChecked(false);

                holder.itemView.setOnClickListener(view -> {
                    TextView textView = findViewById(R.id.textView11);
                    textView.setText("You selected: \n"+model.getName());
                });


            }

            @NonNull
            @Override
            public PresidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_president, parent, false);
                return new PresidentViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void sendvote(View view) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uniqueid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                TextView textView = findViewById(R.id.textView11);
                String voteText = textView.getText().toString();
                String cleanvote = voteText.replace("You selected: \n","");
                VotesCast votesCast = new VotesCast(uniqueid,cleanvote.trim(),"President");
                if (!(dataSnapshot.child("President_Votes").child(uniqueid).exists())){
                    RootRef.child("Votes").child(uniqueid).setValue(votesCast);
                } else {
                    Toast.makeText(pres.this, "Vote already Casted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class PresidentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        CheckBox checkBox;

        public PresidentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prezzo_image);
            nameTextView = itemView.findViewById(R.id.prezzo_name);
            checkBox = itemView.findViewById(R.id.prezzo_checked);
        }
    }
}