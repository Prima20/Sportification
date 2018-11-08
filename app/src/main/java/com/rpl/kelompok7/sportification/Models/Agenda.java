package com.rpl.kelompok7.sportification.Models;

import java.util.Date;

public class Agenda {
    public String waktuMulai;
    public String waktuSelesai;
    public String usernamePembuat;
    public int id;
    public String[] usernamePemain;
    public String keterangan;
    public String lapangan;
    public int jumlahSlot;
    public int codeBooking;

    //Empty constructor to data snapshot
    public Agenda() {
    }

    public Agenda(String waktuMulai, String waktuSelesai, String usernamePembuat, String keterangan, String lapangan, int jumlahSlot, int codeBooking) {
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.usernamePembuat = usernamePembuat;
        this.keterangan = keterangan;
        this.lapangan = lapangan;
        this.jumlahSlot = jumlahSlot;
        this.codeBooking = codeBooking;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public void setUsernamePembuat(String usernamePembuat) {
        this.usernamePembuat = usernamePembuat;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setLapangan(String lapangan) {
        this.lapangan = lapangan;
    }

    public void setJumlahSlot(int jumlahSlot) {
        this.jumlahSlot = jumlahSlot;
    }

    public void setCodeBooking(int codeBooking) {
        this.codeBooking = codeBooking;
    }
}
