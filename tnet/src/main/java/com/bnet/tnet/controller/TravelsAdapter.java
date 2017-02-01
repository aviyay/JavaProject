package com.bnet.tnet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.tnet.view.TravelListRow;

class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelViewHolder> {

    interface OnItemClickListener {
        void onItemClick(Activity travel);
    }

    class TravelViewHolder extends RecyclerView.ViewHolder {
        private TravelListRow travelListRow;

        TravelViewHolder(View itemView) {
            super(itemView);
            travelListRow = (TravelListRow) itemView;

            travelListRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(travelListRow.getTravel());
                }
            });
        }

        void bind(Activity travel) {
            travelListRow.setTravel(travel);
        }
    }

    private OnItemClickListener listener;
    private ProvidableRepository<Activity> repository;

    TravelsAdapter(ProvidableRepository<Activity> repository) {
        this.repository = repository;
    }

    void setOnItemClickListener(TravelsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new TravelListRow(parent.getContext());
        return new TravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelViewHolder holder, int position) {
        holder.bind(repository.getAll().get(position));
    }

    @Override
    public int getItemCount() {
        return repository.getAll().size();
    }
}