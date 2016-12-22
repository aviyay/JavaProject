package com.bnet.tnet.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.R;

public class AgenciesFragment extends Fragment {
    private RecyclerView recyclerView;
    private AgenciesAdapter adapter;

    static {
        ProvidableRepository repository = RepositoriesFactory.getBusinessesRepository();
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness());
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness2());
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness3());
    }

    public AgenciesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_agencies, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AgenciesAdapter();
        recyclerView.setAdapter(adapter);

        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
