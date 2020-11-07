package com.android.online.voteapp.Candidate.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.online.voteapp.Candidate.FormModelClass;
import com.android.online.voteapp.R;
import com.android.online.voteapp.Session.Prevalent;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kiduyu klaus
 * on 06/11/2020 15:49 2020
 */
public class Myforms extends Fragment {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private RecyclerView recyclerView;

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myforms, container, false);

        progressDialog = new ProgressDialog(getActivity());
        //mStorageRef= FirebaseStorage.getInstance().getReference().child("Forms_Submitted");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Forms_Submitted");
        //mDatabase.keepSynced(true);

        recyclerView = view.findViewById(R.id.myforms_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getForms();

        return view;
    }

    private void getForms() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("retriving Data.");
        progressDialog.show();

        Query queries = mDatabase.orderByChild("name").equalTo(Prevalent.currentOnlineUser.getName());
        FirebaseRecyclerOptions<FormModelClass> option = new FirebaseRecyclerOptions.Builder<FormModelClass>()
                .setQuery(queries, FormModelClass.class)
                .build();

        FirebaseRecyclerAdapter<FormModelClass, FormViewHolder> adapter = new FirebaseRecyclerAdapter<FormModelClass, FormViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull final FormViewHolder holder, int position, @NonNull final FormModelClass model) {

                Glide.with(getActivity()).load(model.getImageurl()).into(holder.postImage);
                Glide.with(getActivity()).load(Prevalent.currentOnlineUser.getImage()).into(holder.userImage);
                holder.userName.setText(Prevalent.currentOnlineUser.getName());
                holder.postDate.setText("Less than a day");

                holder.blogLikeCount.setText("Form Status: " + model.getStatus());


                holder.descView.setText("Form Details\nName: " + model.getName()
                        + "\nOwner Id Number: " + model.getIdnumber()
                        + "\nOwner Registration Number: " + model.getRenumber()
                        + "\nOwner School Department: " + model.getDepartment()
                        + "\nOwner Home Address: " + model.getLocation()
                        + "\nOwner Preffered Seat: " + model.getSeat()
                        + "\nTranscript Uploaded: Yes"
                        + "\n\nA Description About The Owner\n\n" + model.getDescription());

                progressDialog.dismiss();
            }

            @NonNull
            @Override
            public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_item, parent, false);
                return new FormViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    public class FormViewHolder extends RecyclerView.ViewHolder {

        private TextView descView;
        private TextView userName;
        private TextView postDate;
        private ImageView postImage;
        private CircleImageView userImage;
        private View mview;
        private TextView blogLikeCount;
        private TextView blogCommentCount;
        private ImageView BlogLikeBtn;
        private ImageView BlogCommentBtn;


        public FormViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            BlogLikeBtn = mview.findViewById(R.id.blog_like);
            blogCommentCount = mview.findViewById(R.id.blog_comment_count);
            BlogCommentBtn = mview.findViewById(R.id.blog_comment);
            blogLikeCount = mview.findViewById(R.id.blog_like_count);
            userName = mview.findViewById(R.id.commentUsername);
            postDate = mview.findViewById(R.id.blogDate);
            postImage = mview.findViewById(R.id.blogImage);
            descView = mview.findViewById(R.id.blogDescription);
            userImage = mview.findViewById(R.id.commentProfilePic);

        }
    }
}
