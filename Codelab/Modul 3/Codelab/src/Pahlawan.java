public class Pahlawan extends KarakterGame {
    public Pahlawan(String nama, int kesehatan) {
        super(nama, kesehatan);
    }

    @Override
    public void serang(KarakterGame target) {
        int damage = 30; // Damage dasar
        String pasifInfo = "";

        // Pasif: Bonus Damage saat HP turun
        if (kesehatan < 0.8 * 150) { // Jika HP < 80% dari max HP,pasif 1 aktif
            damage += 10;
            pasifInfo = "(Pasif Aktif: +10 Damage)";
        }
        if (kesehatan < 0.5 * 150) { // Jika HP < 50% dari max HP, pasif 2 aktif
            damage += 20;
            pasifInfo = "(Pasif Aktif: +30 Damage)";
        }

        System.out.println(nama + " menyerang " + target.getNama() + " dengan damage " + damage + " " + pasifInfo);
        target.terimaSerangan(damage);
    }
}
