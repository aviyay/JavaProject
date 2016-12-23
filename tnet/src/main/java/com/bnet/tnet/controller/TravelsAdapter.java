package com.bnet.tnet.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.R;

class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelViewHolder> {

    static {
        ProvidableRepository repository = RepositoriesFactory.getActivitiesRepository();
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity());
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity2());
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity3());
    }

    class TravelViewHolder extends RecyclerView.ViewHolder{

        TextView temp;

        TravelViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(clickListener);
            temp = (TextView) itemView.findViewById(R.id.temp2);
        }

        private View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent travelDetails = new Intent(context, TravelDetails.class);

                context.startActivity(travelDetails);
            }

        };
    }

    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_list_row, parent, false);

        return new TravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TravelViewHolder holder, int position) {
        Activity travel = RepositoriesFactory.getActivitiesRepository().getAll().get(position);

        holder.temp.setText(travel.getDescription());
    }

    @Override
    public int getItemCount() {
        return RepositoriesFactory.getActivitiesRepository().getAll().size();
    }
}
