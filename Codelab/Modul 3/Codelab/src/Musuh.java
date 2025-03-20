public class Musuh extends KarakterGame {
    public Musuh(String nama, int kesehatan) {
        super(nama, kesehatan);
    }

    @Override
    public void serang(KarakterGame target) {
        int damage = 20; // Damage dasar
        System.out.println(nama + " menyerang " + target.getNama() + " dengan damage " + damage);
        target.terimaSerangan(damage);
    }
}
