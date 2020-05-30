package com.bi183.effendi;

public class Obat {
    private int idObat;
    private String namaObat;
    private String deskripsiObat;
    private String aturanPakai;
    private String jenisObat;
    private String gambar;

    public Obat(int idObat, String namaObat, String deskripsiObat, String aturanPakai, String jenisObat, String gambar) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.deskripsiObat = deskripsiObat;
        this.aturanPakai = aturanPakai;
        this.jenisObat = jenisObat;
        this.gambar = gambar;
    }

    public int getIdObat() {
        return idObat;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getDeskripsiObat() {
        return deskripsiObat;
    }

    public void setDeskripsiObat(String deskripsiObat) {
        this.deskripsiObat = deskripsiObat;
    }

    public String getAturanPakai() {
        return aturanPakai;
    }

    public void setAturanPakai(String aturanPakai) {
        this.aturanPakai = aturanPakai;
    }

    public String getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
