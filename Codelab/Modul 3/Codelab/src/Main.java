public class Main {
    public static void main(String[] args) {
        Pahlawan pahlawan = new Pahlawan("Maou Anos", 150);
        Musuh musuh = new Musuh("Kanon", 200);

        System.out.println("---Atribut Awal---");
        System.out.println(pahlawan.getNama() + " memiliki " + pahlawan.getKesehatan() + " HP.");
        System.out.println(musuh.getNama() + " memiliki " + musuh.getKesehatan() + " HP.");
        System.out.println();

        System.out.println("---Battle Start---");
        System.out.println();

        while (pahlawan.getKesehatan() > 0 && musuh.getKesehatan() > 0) {
            pahlawan.serang(musuh);
            System.out.println();

            if (musuh.getKesehatan() <= 0) {
                System.out.println(musuh.getNama() + " kalah! Maou Anos menang!");
                break;
            }

            musuh.serang(pahlawan);
            System.out.println();

            if (pahlawan.getKesehatan() <= 0) {
                System.out.println(pahlawan.getNama() + " kalah!");
                break;
            }
        }
    }
}
