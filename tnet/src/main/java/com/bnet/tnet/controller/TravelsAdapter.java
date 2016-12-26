package com.bnet.tnet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.backend.RepositoriesFactory;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;
import com.bnet.shared.model.entities.EntitiesSamples;
import com.bnet.tnet.R;

class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelViewHolder> {

   /* static {
        ProvidableRepository<Activity> repository = RepositoriesFactory.getActivitiesRepository();
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity());
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity2());
        repository.addAndReturnAssignedId(EntitiesSamples.getActivity3());
    }*/

    interface OnItemClickListener {
        void onItemClick(Activity travel);
    }

    class TravelViewHolder extends RecyclerView.ViewHolder{

        private Activity travel;
        private TextView travelCountry;
        private TextView travelDates;
        private TextView travelAgency;
        private TextView travelPrice;

        TravelViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(travel);
                }
            });

            travelCountry = (TextView) itemView.findViewById(R.id.travelCountry);
            travelDates = (TextView) itemView.findViewById(R.id.travelDates);
            travelAgency = (TextView) itemView.findViewById(R.id.travelAgency);
            travelPrice = (TextView) itemView.findViewById(R.id.travelPrice);
        }

        void bind(Activity travel) {
            this.travel = travel;

            travelCountry.setText(travel.getCountry());
            travelDates.setText(travel.getStart().formatDate() + " - " + travel.getEnd().formatDate());
            travelAgency.setText(findAgencyReference(travel.getBusinessId()).getName());
            travelPrice.setText(String.format("%.2f",travel.getPrice()));
        }

        private Business findAgencyReference(int businessId) {

            for (Business business : RepositoriesFactory.getBusinessesRepository().getAll())
                if (business.getId() == businessId) {
                    return business;
                }

            return null;
        }
    }

    private TravelsAdapter.OnItemClickListener listener;
    private ProvidableRepository<Activity> repository;

    TravelsAdapter(ProvidableRepository<Activity> repository) {
        this.repository = repository;
    }

    void setOnItemClickListener(TravelsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_list_row, parent, false);

        return new TravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TravelViewHolder holder, int position) {
        Activity travel = repository.getAll().get(position);
        holder.bind(travel);
    }

    @Override
    public int getItemCount() {
        return repository.getAll().size();
    }
}
