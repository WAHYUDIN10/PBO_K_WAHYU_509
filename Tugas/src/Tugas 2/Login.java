import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan username: ");
        String username = input.nextLine();
        System.out.print("Masukkan password: ");
        String password = input.nextLine();

        if (username.equals("admin509") && password.equals("pass509")) {
            new Admin().tampilkanMenu();
        } else if (username.equals("mahasiswa509") && password.equals("pass509")) {
            new Mahasiswa().tampilkanMenu();
        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }

        input.close();
    }
}
