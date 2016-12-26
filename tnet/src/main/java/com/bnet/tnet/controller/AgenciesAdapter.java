package com.bnet.tnet.controller;

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

    interface OnItemClickListener {
        void onItemClick(Business agency);
    }
    private OnItemClickListener listener;

    class AgencyViewHolder extends RecyclerView.ViewHolder{
        private Business agency;

        private TextView agencyName;
        private TextView agencyStreet;

        AgencyViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(agency);
                }
            });

            agencyName = (TextView) itemView.findViewById(R.id.agencyName);
            agencyStreet = (TextView) itemView.findViewById(R.id.agencyStreet);
        }

        void bind(Business agency) {
            this.agency = agency;

            agencyName.setText(agency.getName());
            agencyStreet.setText(agency.getAddress().getCountry());
        }
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
