package com.rpl.kelompok7.sportification.Models;

public class Booking {

    String waktuMulai, waktuSelesai;
    String generateCode;

    public Booking(String waktuMulai, String waktuSelesai, String generateCode) {
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.generateCode = generateCode;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getGenerateCode() {
        return generateCode;
    }

    public void setGenerateCode(String generateCode) {
        this.generateCode = generateCode;
    }
}
