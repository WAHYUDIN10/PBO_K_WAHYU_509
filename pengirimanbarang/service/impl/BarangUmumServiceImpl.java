package pengirimanbarang.service.impl;

import pengirimanbarang.model.ServicePrice;
import pengirimanbarang.service.HitungBiayaBarangUmum;
import pengirimanbarang.service.HitungBiayaBarangUmum;

public class BarangUmumServiceImpl implements HitungBiayaBarangUmum {
    private ServicePrice servicePrice; // Ini akan berisi harga per kg, per km, dan estimasi waktu untuk jenis pengiriman tertentu (misal: JNE Reguler)

    public BarangUmumServiceImpl(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
        if (this.servicePrice == null) {
            throw new IllegalArgumentException("ServicePrice untuk Barang Umum tidak boleh null.");
        }
    }

    @Override
    public double hitungBiaya(double berat, int jarak) {
        return servicePrice.getHargaPerKg() * berat + servicePrice.getHargaPerKm() * jarak;
    }

    @Override
    public String estimasiWaktu() {
        return servicePrice.getEstimasiWaktu();
    }

    @Override
    public double getHargaPerKg() {
        return servicePrice.getHargaPerKg();
    }

    @Override
    public double getHargaPerKm() {
        return servicePrice.getHargaPerKm();
    }
}