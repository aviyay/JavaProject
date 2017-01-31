package com.bnet.tnet.controller;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Business;
import com.bnet.tnet.view.AgencyListRow;

class AgenciesAdapter extends RecyclerView.Adapter<AgenciesAdapter.AgencyViewHolder> {

    interface OnItemClickListener {
        void onItemClick(Business agency);
    }

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

        void bind(Business agency) {
            agencyListRow.setAgency(agency);
        }
    }

    private OnItemClickListener listener;
    private ProvidableRepository<Business> repository;

    AgenciesAdapter(ProvidableRepository<Business> repository) {
        this.repository = repository;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public AgencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new AgencyListRow(parent.getContext());
        return new AgencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AgencyViewHolder holder, int position) {
        new AsyncTask<Integer,Void,Business>()
        {
            @Override
            protected Business doInBackground(Integer... params) {
                return repository.getAll().get(params[0]);
            }

            @Override
            protected void onPostExecute(Business aAgency) {
                super.onPostExecute(aAgency);
                holder.bind(aAgency);
            }
        }.execute(position);
    }

    @Override
    public int getItemCount() {//TODO
        return repository.getAll().size();
    }
}
