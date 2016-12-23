package com.bnet.tnet.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.R;

class AgenciesAdapter extends RecyclerView.Adapter<AgenciesAdapter.AgencyViewHolder> {

    static {
        ProvidableRepository<Business> repository = RepositoriesFactory.getBusinessesRepository();
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness());
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness2());
        repository.addAndReturnAssignedId(EntitiesSamples.getBusiness3());
    }

    class AgencyViewHolder extends RecyclerView.ViewHolder{
        private Business agency;
        private TextView temp;

        AgencyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(clickListener);
            temp = (TextView) itemView.findViewById(R.id.temp);
        }

        private View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent agencyDetails = new Intent(context, AgencyDetails.class);

                ContentValues values = agency.toContentValues(agency);

                for (String key : values.keySet()) {
                    agencyDetails.putExtra(key, values.getAsString(key));
                }

                context.startActivity(agencyDetails);
            }
        };

        void bind(Business agency) {
            this.agency = agency;

            temp.setText(agency.getName());
        }
    }

    @Override
    public AgencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agency_list_row, parent, false);

        return new AgencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgencyViewHolder holder, int position) {
        Business agency = RepositoriesFactory.getBusinessesRepository().getAll().get(position);
        holder.bind(agency);
    }

    @Override
    public int getItemCount() {
        return RepositoriesFactory.getBusinessesRepository().getAll().size();
    }
}
