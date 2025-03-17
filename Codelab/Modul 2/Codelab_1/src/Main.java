import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Hewan hewan1 = new Hewan(); //membuat object hewan1 dan hewan2
        Hewan hewan2 = new Hewan();

        hewan1.inputDataHewan(input); //memanggil method di class Hewan untuk meminta input dari user dari objek hewan1
        System.out.println();
        hewan2.inputDataHewan(input); //memanggil method di class Hewan untuk meminta input dari user dari objek hewan2

        System.out.println("\n======== Informasi Hewan =======");
        hewan1.tampilkanInfo(); // memanggil method di class hewan untuk menampilkan data dari hewan1
        hewan2.tampilkanInfo();// memanggil method di class hewan untuk menampilkan data dari hewan2
        input.close();
    }
}