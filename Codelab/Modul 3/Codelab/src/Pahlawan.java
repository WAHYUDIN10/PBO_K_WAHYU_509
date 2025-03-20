public class Pahlawan extends KarakterGame {
    public Pahlawan(String nama, int kesehatan) {
        super(nama, kesehatan);
    }

    @Override
    public void serang(KarakterGame target) {
        int damage = 20;
        System.out.println(getNama() + " menyerang " + target.getNama());
        target.setKesehatan(target.getKesehatan() - damage);
        System.out.println(target.getNama() + " menerima " + damage + " damage.");
    }
}