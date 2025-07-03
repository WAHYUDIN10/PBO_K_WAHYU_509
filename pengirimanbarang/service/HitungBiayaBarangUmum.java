package pengirimanbarang.service;

public interface HitungBiayaBarangUmum {
    /**
     * Menghitung biaya pengiriman untuk barang umum.
     *
     * @param berat Berat barang dalam kilogram.
     * @param jarak Jarak tempuh dalam kilometer.
     * @return Total biaya pengiriman barang umum.
     */
    double hitungBiaya(double berat, int jarak);

    /**
     * Mendapatkan estimasi waktu pengiriman untuk layanan barang umum.
     *
     * @return String representasi estimasi waktu (misal: "3 - 5 Hari").
     */
    String estimasiWaktu();

    /**
     * Mendapatkan harga per kilogram untuk layanan barang umum.
     *
     * @return Harga per kilogram.
     */
    double getHargaPerKg();

    /**
     * Mendapatkan harga per kilometer untuk layanan barang umum.
     *
     * @return Harga per kilometer.
     */
    double getHargaPerKm();
}