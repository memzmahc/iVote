package com.android.online.voteapp.Candidate.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.online.voteapp.R;

/**
 * Created by Kiduyu klaus
 * on 06/11/2020 13:32 2020
 */
public class HomeFragment extends Fragment {
    private CardView submittv,myformstv,seattv,profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        submittv=view.findViewById(R.id.submit);
        myformstv=view.findViewById(R.id.myforms);
        seattv=view.findViewById(R.id.seat);
        profile=view.findViewById(R.id.profile);

        submittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                        new SubmitFragment()).commit();
            }
        });

        myformstv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                        new Myforms()).commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                        new ProfileFragment()).commit();
            }
        });

        seattv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                        new UpdateFragment()).commit();
            }
        });
        return view;
    }


}
