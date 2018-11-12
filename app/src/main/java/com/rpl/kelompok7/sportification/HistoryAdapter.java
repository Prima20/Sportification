package com.rpl.kelompok7.sportification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rpl.kelompok7.sportification.Models.Agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    LayoutInflater mInflater;
    ArrayList<Agenda> history;
    Context _context;
    SimpleDateFormat dateFormatter;

    public HistoryAdapter(ArrayList<Agenda> history, Context _context) {
        this.mInflater = LayoutInflater.from(_context);
        this.history = history;
        this._context = _context;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_histroy_agenda, parent, false);
        return new HistoryHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        Agenda current = history.get(position);

        holder.namaLapangan.setText(current.lapangan.getLapangan());
        holder.tanggalAgenda.setText(dateFormatter.format(current.tanggalAgenda));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }


    class HistoryHolder extends RecyclerView.ViewHolder{

        TextView namaLapangan;
        TextView tanggalAgenda;

        HistoryAdapter mAdapter;


        public HistoryHolder(View itemView, HistoryAdapter adapter) {
            super(itemView);
            namaLapangan = itemView.findViewById(R.id.tv_lapangan_history);
            tanggalAgenda = itemView.findViewById(R.id.tv_tanggal_history);

            this.mAdapter = adapter;
        }
    }
}
