public class Admin {
    private final String[] matkul = {
            "Pemrograman Java", "Basis Data", "Struktur Data",
            "Jaringan Komputer", "Kecerdasan Buatan"
    };

    public void tampilkanMenu() {
        System.out.println("\n=== Menu Admin ===");
        System.out.println("Daftar Mata Kuliah:");
        for (int i = 0; i < matkul.length; i++) {
            System.out.println((i + 1) + ". " + matkul[i]);
        }
    }
}
