package pengirimanbarang.service;

public interface HitungBiayaTowingMotor {
    /**
     * Menghitung biaya layanan towing motor.
     *
     * @param ccCategory Kategori CC motor (misal: "<250cc", "250-600cc", ">600cc").
     * Di implementasi, ini akan digunakan untuk menentukan base fee yang relevan.
     * @param jarak Jarak tempuh dalam kilometer.
     * @return Total biaya layanan towing motor.
     */
    double hitungBiaya(String ccCategory, int jarak);

    /**
     * Mendapatkan estimasi waktu layanan towing motor.
     *
     * @return String representasi estimasi waktu (misal: "1 - 2 Hari").
     */
    String estimasiWaktu();

    /**
     * Mendapatkan base fee untuk layanan towing motor.
     * Catatan: Nilai ini kemungkinan akan bergantung pada ccCategory.
     * Implementasi konkret perlu menangani bagaimana base fee ditentukan.
     *
     * @return Base fee untuk towing.
     */
    double getBaseFee();

    /**
     * Mendapatkan harga per kilometer untuk layanan towing motor.
     *
     * @return Harga per kilometer.
     */
    double getHargaPerKm();
}