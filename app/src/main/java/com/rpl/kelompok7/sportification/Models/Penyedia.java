package com.rpl.kelompok7.sportification.Models;

public class Penyedia {

    public String noLapangan, alamat, jamMulai, jamSelesai, genCode;


    public Penyedia(){

    }

//    public Penyedia(String noLapangan, String alamat, String jamMulai, String jamSelesai, String genCode) {
//        this.noLapangan = noLapangan;
//        this.alamat = alamat;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//        this.genCode = genCode;
//    }

    public Penyedia(String noLapangan, String alamat){
        this.noLapangan = noLapangan;
        this.alamat = alamat;
    }

}
