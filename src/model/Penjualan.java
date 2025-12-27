package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Penjualan {
    private int id;
    private int mobilId;
    private String merkMobil;
    private String modelMobil;
    private int jumlah;
    private BigDecimal hargaTotal;
    private String pembeliNama;
    private String pembeliTelepon;
    private LocalDate tanggalJual;
    private LocalDateTime createdAt;

    public Penjualan() {
    }

    public Penjualan(int mobilId, int jumlah, BigDecimal hargaTotal,
            String pembeliNama, String pembeliTelepon, LocalDate tanggalJual) {
        this.mobilId = mobilId;
        this.jumlah = jumlah;
        this.hargaTotal = hargaTotal;
        this.pembeliNama = pembeliNama;
        this.pembeliTelepon = pembeliTelepon;
        this.tanggalJual = tanggalJual;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMobilId() {
        return mobilId;
    }

    public void setMobilId(int mobilId) {
        this.mobilId = mobilId;
    }

    public String getMerkMobil() {
        return merkMobil;
    }

    public void setMerkMobil(String merkMobil) {
        this.merkMobil = merkMobil;
    }

    public String getModelMobil() {
        return modelMobil;
    }

    public void setModelMobil(String modelMobil) {
        this.modelMobil = modelMobil;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(BigDecimal hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public String getPembeliNama() {
        return pembeliNama;
    }

    public void setPembeliNama(String pembeliNama) {
        this.pembeliNama = pembeliNama;
    }

    public String getPembeliTelepon() {
        return pembeliTelepon;
    }

    public void setPembeliTelepon(String pembeliTelepon) {
        this.pembeliTelepon = pembeliTelepon;
    }

    public LocalDate getTanggalJual() {
        return tanggalJual;
    }

    public void setTanggalJual(LocalDate tanggalJual) {
        this.tanggalJual = tanggalJual;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}   