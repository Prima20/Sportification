package com.rpl.kelompok7.sportification.Models;

public class Lapangan {

    private String lapangan;
    private String lokasi;

    //Empty constructor for data snapshot
    public Lapangan() {
    }

    public Lapangan(String lapangan, String lokasi) {
        this.lapangan = lapangan;
        this.lokasi = lokasi;
    }

    public String getLapangan() {
        return lapangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    @Override
    public String toString() {
        return lapangan;
    }
}
