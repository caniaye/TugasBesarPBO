package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Mobil {
    private int id;
    private String merk;
    private String model;
    private int tahun;
    private String warna;
    private BigDecimal harga;
    private int stok;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Mobil() {
    }

    public Mobil(String merk, String model, int tahun, String warna, BigDecimal harga, int stok) {
        this.merk = merk;
        this.model = model;
        this.tahun = tahun;
        this.warna = warna;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return merk + " " + model + " (" + tahun + ")";
    }
}