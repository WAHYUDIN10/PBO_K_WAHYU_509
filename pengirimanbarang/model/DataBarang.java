package pengirimanbarang.model;

public class DataBarang {
    private String namaBarang; // Bisa jadi nama barang atau kategori CC motor
    private double berat; // Hanya berlaku untuk barang umum
    private String asal;
    private String tujuan;
    private String jenisPengiriman; // Reguler / Express
    private String namaEkspedisi;
    private String mainDeliveryType; // Barang Umum / Towing Motor
    private String motorCCCategory; // Properti baru untuk menyimpan kategori CC motor

    // Konstruktor untuk Barang Umum
    public DataBarang(String namaBarang, double berat, String asal, String tujuan,
                      String jenisPengiriman, String namaEkspedisi, String mainDeliveryType) {
        this.namaBarang = namaBarang;
        this.berat = berat;
        this.asal = asal;
        this.tujuan = tujuan;
        this.jenisPengiriman = jenisPengiriman;
        this.namaEkspedisi = namaEkspedisi;
        this.mainDeliveryType = mainDeliveryType;
        this.motorCCCategory = null; // Tidak relevan untuk barang umum
    }

    // Konstruktor untuk Towing Motor
    public DataBarang(String motorCCCategory, String asal, String tujuan,
                      String jenisPengiriman, String namaEkspedisi, String mainDeliveryType) {
        this.namaBarang = "Motor"; // Nama barang default untuk towing
        this.berat = 0; // Berat tidak relevan untuk towing
        this.asal = asal;
        this.tujuan = tujuan;
        this.jenisPengiriman = jenisPengiriman;
        this.namaEkspedisi = namaEkspedisi;
        this.mainDeliveryType = mainDeliveryType;
        this.motorCCCategory = motorCCCategory; // Simpan kategori CC motor di properti baru
    }


    // Getters
    public String getNamaBarang() {
        return namaBarang;
    }

    public double getBerat() {
        return berat;
    }

    public String getAsal() {
        return asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getJenisPengiriman() {
        return jenisPengiriman;
    }

    public String getNamaEkspedisi() {
        return namaEkspedisi;
    }

    public String getMainDeliveryType() {
        return mainDeliveryType;
    }

    // Getter khusus untuk kategori CC motor
    public String getMotorCCCategory() {
        return motorCCCategory;
    }
}