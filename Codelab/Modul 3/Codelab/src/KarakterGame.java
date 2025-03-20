public class KarakterGame {
    private String nama;
    private int kesehatan;

    // Constructor
    public KarakterGame(String nama, int kesehatan) {
        this.nama = nama;
        this.kesehatan = kesehatan;
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKesehatan(int kesehatan) {
        this.kesehatan = kesehatan;
    }

    // Method untuk menyerang target
    public void serang(KarakterGame target) {
        int damage = 20; // Damage default
        System.out.println(nama + " menyerang " + target.getNama() + " dengan damage " + damage);
        target.terimaSerangan(damage);
    }

    // Method untuk menerima serangan
    public void terimaSerangan(int damage) {
        kesehatan -= damage;
        if (kesehatan < 0) {
            kesehatan = 0; // Pastikan tidak negatif
        }
        System.out.println(nama + " menerima " + damage + " damage. Sisa HP: " + kesehatan);
    }
}
