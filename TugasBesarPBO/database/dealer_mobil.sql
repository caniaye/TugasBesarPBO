-- Buat database
CREATE DATABASE IF NOT EXISTS dealer_mobil;
USE dealer_mobil;

-- Tabel Mobil
CREATE TABLE mobil (
    id INT PRIMARY KEY AUTO_INCREMENT,
    merk VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    tahun INT NOT NULL,
    warna VARCHAR(50),
    harga DECIMAL(15,2) NOT NULL,
    stok INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabel Penjualan
CREATE TABLE penjualan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    mobil_id INT NOT NULL,
    jumlah INT NOT NULL,
    harga_total DECIMAL(15,2) NOT NULL,
    pembeli_nama VARCHAR(200) NOT NULL,
    pembeli_telepon VARCHAR(20),
    tanggal_jual DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mobil_id) REFERENCES mobil(id) ON DELETE CASCADE
);

-- Tabel Transaksi
CREATE TABLE transaksi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    penjualan_id INT NOT NULL,
    metode_pembayaran ENUM('CASH', 'KREDIT', 'TRANSFER') NOT NULL,
    status ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    bukti_pembayaran TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (penjualan_id) REFERENCES penjualan(id) ON DELETE CASCADE
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);


-- Insert data contoh
INSERT INTO mobil (merk, model, tahun, warna, harga, stok) VALUES
('Toyota', 'Avanza', 2023, 'Hitam', 250000000, 10),
('Honda', 'Brio', 2023, 'Putih', 180000000, 8),
('Suzuki', 'Ertiga', 2023, 'Silver', 220000000, 5),
('Mitsubishi', 'Xpander', 2023, 'Merah', 280000000, 3),
('Daihatsu', 'Terios', 2023, 'Biru', 230000000, 7);

INSERT INTO penjualan (mobil_id, jumlah, harga_total, pembeli_nama, pembeli_telepon, tanggal_jual) VALUES
(1, 1, 250000000, 'Budi Santoso', '081234567890', '2024-01-15'),
(2, 1, 180000000, 'Sari Dewi', '081298765432', '2024-01-16'),
(3, 2, 440000000, 'PT Abadi Jaya', '02155667788', '2024-01-17');

INSERT INTO transaksi (penjualan_id, metode_pembayaran, status) VALUES
(1, 'CASH', 'SUCCESS'),
(2, 'TRANSFER', 'SUCCESS'),
(3, 'KREDIT', 'PENDING');