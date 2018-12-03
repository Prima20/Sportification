package com.rpl.kelompok7.sportification;

import com.rpl.kelompok7.sportification.Models.Agenda;

//Interface for call object from dataSnapshot
public interface DataCallback {
    void onCallback(Agenda value);
}
