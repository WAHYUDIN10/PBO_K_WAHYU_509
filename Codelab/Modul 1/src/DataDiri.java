import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
public class DataDiri {
    public static void main(String[] args) {
        Scanner objInput = new Scanner(System.in);
        System.out.print("masukkan nama : ");
        String nama = objInput.nextLine();
        System.out.print("masukkan jenis kelamin P/L :");
        char gender = objInput.next().charAt(0);
        objInput.nextLine();
        System.out.print("masukkan tanggal lahir (TTTT-BB-HH) : ");
        String inputTanggal = objInput.nextLine();

        LocalDate tanggalLahirUser = LocalDate.parse(inputTanggal);
        LocalDate tanggalHariIni = LocalDate.now();

        Period usia = Period.between(tanggalLahirUser, tanggalHariIni);

        String jenisKelamin;

        if (gender == 'l' || gender == 'L') {
            jenisKelamin = "Laki-laki";
        } else if (gender == 'p' || gender == 'P') {
            jenisKelamin = "Perempuan";
        }else{
            jenisKelamin = "Tidak diketahui";
        }

        System.out.println("\n Data Diri:");
        System.out.println("Nama:" + nama);
        System.out.println("Jenis Kelamin: " + jenisKelamin);
        System.out.println("Umur: " + usia.getYears() + " " + "Tahun" + " " + usia.getMonths() + " " + "Bulan" + " " + usia.getDays() + " " + "Hari");

    }
}