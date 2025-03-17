public class RekeningBank {
    String nomorR,namaP;
    double saldo;


    void tampilkanInfo(){
        System.out.println("===============================");
        System.out.println("nomor rekening : " + nomorR);
        System.out.println("nama pemilik : " + namaP);
        System.out.println("saldo : " +"Rp."+saldo);
        System.out.println("===============================\n");
    }


    void setorUang(double jumlah){
        if (jumlah >0 ){
            saldo += jumlah;
            System.out.println(namaP + " " + "menyetor Rp." + jumlah + " " + "saldo sekarang Rp." + saldo);
        }
    }

    void tarikTunai(double jumlah){
        if(saldo >= jumlah){
            saldo -= jumlah;
            System.out.println(namaP + " " + "menarik Rp." + jumlah + " " + " (berhasil !) saldo saat ini Rp." + saldo );
        }else{
            System.out.println(namaP + " " + "menarik Rp." + jumlah + " " + "(gagal ! saldo anda tidak cukup)" + " " + "saldo saat ini Rp." + saldo );

        }
    }
}