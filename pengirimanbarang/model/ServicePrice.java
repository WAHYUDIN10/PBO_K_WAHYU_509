package pengirimanbarang.model;

public class ServicePrice {
    private double hargaPerKg;
    private double hargaPerKm;
    private String estimasiWaktu;

    // Konstruktor dengan 3 parameter (sesuai yang diharapkan)
    public ServicePrice(double hargaPerKg, double hargaPerKm, String estimasiWaktu) {
        this.hargaPerKg = hargaPerKg;
        this.hargaPerKm = hargaPerKm;
        this.estimasiWaktu = estimasiWaktu;
    }

    // Getters
    public double getHargaPerKg() {
        return hargaPerKg;
    }

    public double getHargaPerKm() {
        return hargaPerKm;
    }

    public String getEstimasiWaktu() {
        return estimasiWaktu;
    }
}