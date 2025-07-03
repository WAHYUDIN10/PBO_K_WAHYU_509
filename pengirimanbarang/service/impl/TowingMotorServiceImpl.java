package pengirimanbarang.service.impl;

import pengirimanbarang.model.ServicePrice;
import pengirimanbarang.service.HitungBiayaTowingMotor;

import java.util.Map;
import java.util.NoSuchElementException;

public class TowingMotorServiceImpl implements HitungBiayaTowingMotor {
    private Map<String, ServicePrice> towingPricesByCC; // Map dari towingPricing, misal "Towing Pro" -> Map<CC_Category, ServicePrice>

    // Constructor ini akan menerima seluruh map harga untuk ekspedisi towing tertentu
    public TowingMotorServiceImpl(Map<String, ServicePrice> towingPricesByCC) {
        this.towingPricesByCC = towingPricesByCC;
        if (this.towingPricesByCC == null || this.towingPricesByCC.isEmpty()) {
            throw new IllegalArgumentException("Data harga towing motor (towingPricesByCC) tidak boleh kosong.");
        }
    }

    @Override
    public double hitungBiaya(String ccCategory, int jarak) {
        ServicePrice sp = towingPricesByCC.get(ccCategory);
        if (sp == null) {
            throw new IllegalArgumentException("Kategori CC motor tidak valid atau tidak memiliki harga: " + ccCategory);
        }
        return sp.getHargaPerKg() + sp.getHargaPerKm() * jarak; // hargaPerKg di sini adalah base fee
    }

    // Untuk estimasi waktu, getBaseFee, dan getHargaPerKm, kita akan mengembalikan
    // nilai dari kategori default (<250cc) atau yang pertama ditemukan.
    // Paling akurat adalah jika nilai-nilai ini juga diberikan konteks ccCategory
    // atau ServicePrice spesifik diinisialisasi untuk objek service ini.
    // Untuk kemudahan implementasi, kita ambil dari kategori <250cc sebagai referensi.
    private ServicePrice getDefaultOrFirstServicePrice() {
        ServicePrice sp = towingPricesByCC.get("<250cc");
        if (sp == null) {
            try {
                // If <250cc is not present, just get the first available entry
                sp = towingPricesByCC.values().iterator().next();
            } catch (NoSuchElementException e) {
                return null; // Should not happen if map is not empty, handled by constructor
            }
        }
        return sp;
    }


    @Override
    public String estimasiWaktu() {
        ServicePrice sp = getDefaultOrFirstServicePrice();
        return sp != null ? sp.getEstimasiWaktu() : "N/A";
    }

    @Override
    public double getBaseFee() {
        ServicePrice sp = getDefaultOrFirstServicePrice();
        return sp != null ? sp.getHargaPerKg() : 0.0;
    }

    @Override
    public double getHargaPerKm() {
        ServicePrice sp = getDefaultOrFirstServicePrice();
        return sp != null ? sp.getHargaPerKm() : 0.0;
    }
}