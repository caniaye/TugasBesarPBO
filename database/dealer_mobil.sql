-- ================================
-- DATABASE DEALER MOBIL
-- ================================

CREATE DATABASE IF NOT EXISTS dealer_mobil;
USE dealer_mobil;

-- ================================
-- TABEL USER (LOGIN)
-- ================================
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- ================================
-- TABEL MOBIL (MASTER DATA)
-- ================================
CREATE TABLE mobil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    merk VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    tahun INT NOT NULL,
    warna VARCHAR(50),
    harga DECIMAL(15,2) NOT NULL,
    stok INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ================================
-- TABEL PENJUALAN (TRANSAKSI UTAMA)
-- ================================
CREATE TABLE penjualan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mobil_id INT NOT NULL,
    jumlah INT NOT NULL,
    harga_total DECIMAL(15,2) NOT NULL,
    pembeli_nama VARCHAR(200) NOT NULL,
    pembeli_telepon VARCHAR(20),
    tanggal_jual DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_penjualan_mobil
        FOREIGN KEY (mobil_id)
        REFERENCES mobil(id)
        ON DELETE CASCADE
);

-- ================================
-- DATA AWAL (SEED DATA)
-- ================================

<<<<<<< HEAD
-- User login
INSERT INTO user (username, password) VALUES
('admin', '123');

-- Data mobil
=======
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);


-- Insert data contoh
>>>>>>> bc49ba2424442755a586e605b5809865650fc042
INSERT INTO mobil (merk, model, tahun, warna, harga, stok) VALUES
('Toyota', 'Avanza', 2023, 'Hitam', 250000000, 10),
('Honda', 'Brio', 2023, 'Putih', 180000000, 8),
('Suzuki', 'Ertiga', 2023, 'Silver', 220000000, 5),
('Mitsubishi', 'Xpander', 2023, 'Merah', 280000000, 2),
('Daihatsu', 'Terios', 2023, 'Hijau', 230000000, 7);

-- Data penjualan (transaksi)
INSERT INTO penjualan (
    mobil_id, jumlah, harga_total,
    pembeli_nama, pembeli_telepon, tanggal_jual
) VALUES
(1, 1, 250000000, 'Budi Santoso', '081234567890', '2024-01-15'),
(2, 1, 180000000, 'Sari Dewi', '081298765432', '2024-01-16'),
(3, 2, 440000000, 'PT Abadi Jaya', '02155667788', '2024-01-17'),
(4, 1, 280000000, 'Ade Nugraha', '085876543219', '2025-12-27');