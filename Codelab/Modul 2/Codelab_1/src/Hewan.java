import java.util.Scanner;

public class Hewan {

    //atribut (data)
    String nama;
    String jenis;
    String suara;


    //method untuk meminta user untuk menginputkan data hewan menggunakan scanner
    void inputDataHewan(Scanner input){
        System.out.print("Masukkan Nama : ");
        this.nama = input.nextLine();
        System.out.print("Masukkan Jenis : ");
        this.jenis = input.nextLine();
        System.out.print("Masukkan Suara : ");
        this.suara = input.nextLine();
    }
    void tampilkanInfo(){
        System.out.println("Nama = " + nama );
        System.out.println("Jenis = " + jenis );
        System.out.println("Suara = " + suara + "\n");
    }
}

