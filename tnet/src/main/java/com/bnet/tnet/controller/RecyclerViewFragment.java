package com.bnet.tnet.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.tnet.R;

public class RecyclerViewFragment extends Fragment {
    private RecyclerView recyclerView = null;
    private RecyclerView.Adapter adapter = null;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;

        if (recyclerView != null)
            recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_view);

        recyclerView.setAdapter(adapter);

        return result;
    }
}
