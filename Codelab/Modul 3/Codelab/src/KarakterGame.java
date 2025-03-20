public class KarakterGame {
    protected String nama;
    protected int kesehatan;

    public KarakterGame(String nama, int kesehatan) {
        this.nama = nama;
        this.kesehatan = kesehatan;
    }

    public String getNama() {
        return nama;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public void terimaSerangan(int damage) {
        kesehatan -= damage;
        if (kesehatan < 0) {
            kesehatan = 0; // Pastikan tidak negatif
        }
        System.out.println(nama + " menerima " + damage + " damage. Sisa HP: " + kesehatan);
    }

    public void serang(KarakterGame target) {
    }
}
