package com.bnet.tnet.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.Constants;
import com.bnet.shared.model.services.utils.ProvidableUtils;
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

        private TextView agencyName;
        private TextView agencyStreet;

        AgencyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(clickListener);

            agencyName = (TextView) itemView.findViewById(R.id.agencyName);
            agencyStreet = (TextView) itemView.findViewById(R.id.agencyStreet);
        }

        private View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent agencyDetails = new Intent(context, AgencyDetails.class);
                Bundle bundle = ProvidableUtils.bundleConvert(agency);

                agencyDetails.putExtra(Constants.BUSINESSES_URI_PATH, bundle);

                context.startActivity(agencyDetails);
            }
        };

        void bind(Business agency) {
            this.agency = agency;

            agencyName.setText(agency.getName());
            agencyStreet.setText(agency.getAddress().getCountry());
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
