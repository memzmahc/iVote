package com.android.online.voteapp.Candidate.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.online.voteapp.R;

/**
 * Created by Kiduyu klaus
 * on 06/11/2020 13:32 2020
 */
public class UpdateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updateseat, container, false);


        return view;
    }
}
