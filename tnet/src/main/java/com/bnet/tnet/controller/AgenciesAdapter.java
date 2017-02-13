package com.bnet.tnet.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.view.AgencyListRow;


/**
 * The RecyclerView adapter to provide agencies
 */
class AgenciesAdapter extends RecyclerView.Adapter<AgenciesAdapter.AgencyViewHolder> {

    /**
     * Listener for clicking on an agency
     */
    interface OnItemClickListener {
        void onItemClick(Business agency);
    }

    /**
     * The ViewHolder that holds the custom agency view AgencyListRow
     */
    class AgencyViewHolder extends RecyclerView.ViewHolder{
        private AgencyListRow agencyListRow;

        AgencyViewHolder(View itemView) {
            super(itemView);
            agencyListRow = (AgencyListRow) itemView;

            agencyListRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(agencyListRow.getAgency());
                }
            });
        }

        /**
         * Bind an agency to the view
         * @param agency The agency to bind
         */
        void bind(Business agency) {
            agencyListRow.setAgency(agency);
        }
    }

    /**
     * The subscribed listener
     */
    private OnItemClickListener listener;
    /**
     * The data access repository to retrieve data from
     */
    private ProvidableRepository<Business> repository;

    /**
     * Initialize the adapter
     * @param repository The data access repository to retrieve data from
     */
    AgenciesAdapter(ProvidableRepository<Business> repository) {
        this.repository = repository;
    }

    /**
     * Subscribe a listener to clicking events on the lists' items
     * @param listener The listener to subscribe
     */
    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Create a ViewHolder for the RecyclerView
     * @param parent Used in order to get the context from
     * @param viewType isn't used
     * @return A ViewHolder for agencies
     */
    @Override
    public AgencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new AgencyListRow(parent.getContext());
        return new AgencyViewHolder(view);
    }

    /**
     * Bind a specific agency to a ViewHolder
     * @param holder The viewHolder to bind to
     * @param position The position in the repository where the agency to bind is
     */
    @Override
    public void onBindViewHolder(final AgencyViewHolder holder, int position) {
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
