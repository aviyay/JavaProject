package com.bnet.tnet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.tnet.view.TravelListRow;


/**
 * The RecyclerView adapter to provide travels
 */
class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelViewHolder> {

    /**
     * Listener for clicking on a travel
     */
    interface OnItemClickListener {
        void onItemClick(Activity travel);
    }

    /**
     * The ViewHolder that holds the custom travel view TravelListRow
     */
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

        /**
         * Bind an travel to the view
         * @param travel The travel to bind
         */
        void bind(Activity travel) {
            travelListRow.setTravel(travel);
        }
    }

    /**
     * The subscribed listener
     */
    private OnItemClickListener listener;
    /**
     * The data access repository to retrieve data from
     */
    private ProvidableRepository<Activity> repository;

    /**
     * Initialize the adapter
     * @param repository The data access repository to retrieve data from
     */
    TravelsAdapter(ProvidableRepository<Activity> repository) {
        this.repository = repository;
    }

    /**
     * Subscribe a listener to clicking events on the lists' items
     * @param listener The listener to subscribe
     */
    void setOnItemClickListener(TravelsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Create a ViewHolder for the RecyclerView
     * @param parent Used in order to get the context from
     * @param viewType isn't used
     * @return A ViewHolder for travels
     */
    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new TravelListRow(parent.getContext());
        return new TravelViewHolder(view);
    }

    /**
     * Bind a specific travel to a ViewHolder
     * @param holder The viewHolder to bind to
     * @param position The position in the repository where the travel to bind is
     */
    @Override
    public void onBindViewHolder(final TravelViewHolder holder, int position) {
        holder.bind(repository.getAll().get(position));
    }

    /**
     * Get the count of all the items in the repository
     * @return The count
     */
    @Override
    public int getItemCount() {
        return repository.getAll().size();
    }
}