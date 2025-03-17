import java.util.Scanner;

public class LoginSederhana{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean login = false;

        while(!login) {
            System.out.println("Pilih login: ");
            System.out.println("1. Admin  \n2. Mahasiswa");
            System.out.print("Pilih tipe login: ");
            int pilihan = input.nextInt();
            input.nextLine();

            switch(pilihan) {
                case 1:
                    System.out.print("Masukkan Username: ");
                    String admin = input.nextLine();
                    System.out.print("Masukkan Password: ");
                    String password = input.nextLine();

                    if(admin.equals("admin") && password.equals("admin123")) {
                        System.out.println("Anda berhasil login sebagai Admin.");
                        login = true;
                    } else {
                        System.out.println("Login gagal! Username atau Password salah.");
                    }
                    break;

                case 2:
                    System.out.print("Masukkan Nama: ");
                    String namaSiswa = input.nextLine();
                    System.out.print("Masukkan NIM: ");
                    String NIM = input.nextLine();

                    if(namaSiswa.equalsIgnoreCase("Muchamad Rizky Fanani") && NIM.equals("202410370110503")) {
                        System.out.println("Anda berhasil login sebagai Mahasiswa.");
                        login = true;
                    } else {
                        System.out.println("Login gagal! Nama atau NIM salah.");
                    }
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1 atau 2.");
            }
        }

        input.close();
    }
}