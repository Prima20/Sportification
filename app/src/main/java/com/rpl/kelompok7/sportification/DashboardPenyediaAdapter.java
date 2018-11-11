package com.rpl.kelompok7.sportification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rpl.kelompok7.sportification.Models.Agenda;
import com.rpl.kelompok7.sportification.Models.Booking;
import com.rpl.kelompok7.sportification.Models.Penyedia;

import java.util.ArrayList;

public class DashboardPenyediaAdapter extends RecyclerView.Adapter<DashboardPenyediaAdapter.DashBoardPenyediaHolder> {
    LayoutInflater mInflater;
    ArrayList<Penyedia> penyedia;
    Context _context;

    public DashboardPenyediaAdapter(ArrayList<Penyedia> penyedia, Context _context) {
        this.mInflater = LayoutInflater.from(_context);
        this.penyedia = penyedia;
        this._context = _context;
    }

    @Override
    public DashBoardPenyediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_agenda_penyedia, parent, false);
        return new DashBoardPenyediaHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(DashBoardPenyediaHolder holder, int position) {
        // Retrieve the data for that position
        Penyedia current = penyedia.get(position);
        // Add the data to the view
        holder.noLap.setText(current.noLapangan);
        holder.alamatItemView.setText(current.alamat);
    }

    @Override
    public int getItemCount() {
        return penyedia.size();
    }

    class DashBoardPenyediaHolder extends RecyclerView.ViewHolder{

        DashboardPenyediaAdapter mAdapter;
        TextView noLap, alamatItemView, jamMulai, jamSelesai, genCode;
        Button booking;

        public DashBoardPenyediaHolder(View itemView, DashboardPenyediaAdapter adapter) {
            super(itemView);

            noLap = (TextView) itemView.findViewById(R.id.tv_noLapangan_penyedia);
            alamatItemView = (TextView) itemView.findViewById(R.id.tv_alamat);
            booking = (Button) itemView.findViewById(R.id.btn_booking);
            this.mAdapter = adapter;

            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), BookingActivity.class);
                    _context.startActivity(intent);
                }
            });
        }

    }

}
