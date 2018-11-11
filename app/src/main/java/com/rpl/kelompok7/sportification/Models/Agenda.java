package com.rpl.kelompok7.sportification.Models;

import java.util.Date;

public class Agenda {
    public String id;
    public Date tanggalAgenda;
    public String waktuMulai;
    public String waktuSelesai;
    public String usernamePembuat;
    public String[] usernamePemain;
    public String keterangan;
    public Lapangan lapangan;
    public int jumlahSlot;
    public String codeBooking;

    //Empty constructor to data snapshot
    public Agenda() {
    }

    public Agenda(String id, Date tanggalAgenda, String waktuMulai, String waktuSelesai, String usernamePembuat, String keterangan, Lapangan lapangan, int jumlahSlot, String codeBooking) {
        this.id = id;
        this.tanggalAgenda = tanggalAgenda;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.usernamePembuat = usernamePembuat;
        this.keterangan = keterangan;
        this.lapangan = lapangan;
        this.jumlahSlot = jumlahSlot;
        this.codeBooking = codeBooking;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTanggalAgenda(Date tanggalAgenda) {
        this.tanggalAgenda = tanggalAgenda;
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

    public void setUsernamePemain(String[] usernamePemain) {
        this.usernamePemain = usernamePemain;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setLapangan(Lapangan lapangan) {
        this.lapangan = lapangan;
    }

    public void setJumlahSlot(int jumlahSlot) {
        this.jumlahSlot = jumlahSlot;
    }

    public void setCodeBooking(String codeBooking) {
        this.codeBooking = codeBooking;
    }
}
