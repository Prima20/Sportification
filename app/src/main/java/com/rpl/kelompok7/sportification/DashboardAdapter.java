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
import com.rpl.kelompok7.sportification.Models.Lapangan;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashBoardHolder> {

    LayoutInflater mInflater;
    ArrayList<Agenda> agenda;
    Context _context;

    public DashboardAdapter(ArrayList<Agenda> agenda, Context _context) {
        this.mInflater = LayoutInflater.from(_context);
        this.agenda = agenda;
        this._context = _context;
    }

    @Override
    public DashBoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_agenda, parent, false);
        return new DashBoardHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(DashBoardHolder holder, int position) {
        Agenda current = agenda.get(position);

        holder.namaLapangan.setText(current.lapangan);
        holder.waktuMulai.setText(current.waktuMulai);
        holder.waktuSelesai.setText(current.waktuSelesai);
        holder.slotPemain.setText("0/10");
    }

    @Override
    public int getItemCount() {

        return agenda.size();
    }

    class DashBoardHolder extends RecyclerView.ViewHolder{

        DashboardAdapter mAdapter;
        TextView namaLapangan;
        TextView waktuMulai;
        TextView waktuSelesai;
        TextView slotPemain;
        Button checkAgenda;

        public DashBoardHolder(View itemView, DashboardAdapter adapter) {
            super(itemView);

            namaLapangan = itemView.findViewById(R.id.tv_lapangan);
            slotPemain = itemView.findViewById(R.id.tv_slot);
            waktuMulai = itemView.findViewById(R.id.tv_waktu_mulai);
            waktuSelesai = itemView.findViewById(R.id.tv_waktu_selesai);
            checkAgenda = itemView.findViewById(R.id.btn_check_agenda);
            this.mAdapter = adapter;

            checkAgenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CheckAgendaActivity.class);
                    _context.startActivity(intent);
                }
            });
        }
//                    Bundle bundle = new Bundle();
//                    Intent intent = new Intent(view.getContext(), CheckAgendaActivity.class);
//                    bundle.putString("lapangan",namaLapangan.getText().toString());
//                    bundle.putString();
//                    _context.startActivity(intent);
    }
}
