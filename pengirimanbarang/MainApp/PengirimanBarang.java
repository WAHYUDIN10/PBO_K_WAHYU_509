package pengirimanbarang.MainApp;

import pengirimanbarang.model.DataBarang;
import pengirimanbarang.model.ServicePrice;
import pengirimanbarang.model.TrackingEvent;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PengirimanBarang extends Application {

    // --- UI Components for Form Pengiriman ---
    private ComboBox<String> mainDeliveryTypeComboBox;
    private VBox generalGoodsFormVBox;
    private VBox towingMotorFormVBox;

    private TextField itemNameField;
    private TextField weightField;
    private ComboBox<String> motorCCComboBox;

    private ComboBox<String> originCityComboBox;
    private ComboBox<String> destinationCityComboBox;
    private ComboBox<String> expeditionComboBox;
    private ToggleGroup shippingTypeGroup;
    private RadioButton regularRadio;
    private RadioButton expressRadio;
    private Label formDistanceLabel;
    private Label formCostLabel;

    // --- UI Components for Detail Pengiriman ---
    private Label detailItemNameLabel;
    private Label detailAsalLabel;
    private Label detailTujuanLabel;
    private Label detailBeratLabel;
    private Label detailJarakLabel;
    private Label detailJenisPengirimanLabel;
    private Label detailEkspedisiLabel;
    private Label detailHargaPerKgLabel;
    private Label detailHargaPerKmLabel;
    private Label detailTotalBiayaLabel;
    private Label detailEstimasiWaktuLabel;
    private Label detailNomorResiLabel;

    private Stage primaryStage;
    private Scene serviceSelectionScene;
    private Scene formScene;
    private Scene detailScene;
    private Scene trackingScene;

    // Data for calculations
    private final Map<String, Integer> distances = new HashMap<>();

    // Detailed pricing for general goods: Map<Ekspedisi, Map<JenisPengiriman, ServicePrice>>
    private final Map<String, Map<String, ServicePrice>> detailedPricing = new HashMap<>();

    // Detailed pricing for motorcycle towing: Map<Ekspedisi, Map<CC_Category, ServicePrice>>
    private final Map<String, Map<String, ServicePrice>> towingPricing = new HashMap<>();

    private final Map<String, List<TrackingEvent>> trackingData = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("DEBUG: Aplikasi dimulai, metode start() dipanggil.");
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Aplikasi Penentu Biaya Pengiriman Barang");

        // Inisialisasi Jarak Antar Kota
        // Format: "KotaAsal-KotaTujuan", jarak_dalam_km
        // Pastikan nama kota sama persis dengan yang ada di ComboBox

        // Jarak antar kota di Pulau Jawa
        distances.put("Jakarta-Surabaya", 800); distances.put("Surabaya-Jakarta", 800);
        distances.put("Jakarta-Bandung", 150); distances.put("Bandung-Jakarta", 150);
        distances.put("Surabaya-Bandung", 700); distances.put("Bandung-Surabaya", 700);
        distances.put("Jakarta-Semarang", 450); distances.put("Semarang-Jakarta", 450);
        distances.put("Surabaya-Semarang", 350); distances.put("Semarang-Surabaya", 350);
        distances.put("Jakarta-Yogyakarta", 550); distances.put("Yogyakarta-Jakarta", 550);
        distances.put("Surabaya-Yogyakarta", 320); distances.put("Yogyakarta-Surabaya", 320);
        distances.put("Surabaya-Malang", 90); distances.put("Malang-Surabaya", 90);
        distances.put("Yogyakarta-Solo", 60); distances.put("Solo-Yogyakarta", 60);
        distances.put("Semarang-Yogyakarta", 120); distances.put("Yogyakarta-Semarang", 120);
        distances.put("Bandung-Semarang", 400); distances.put("Semarang-Bandung", 400);
        distances.put("Jakarta-Solo", 500); distances.put("Solo-Jakarta", 500);
        distances.put("Bandung-Yogyakarta", 480); distances.put("Yogyakarta-Bandung", 480);


        // Jarak antar kota di Sumatera
        distances.put("Medan-Palembang", 1300); distances.put("Palembang-Medan", 1300);
        distances.put("Medan-Pekanbaru", 700); distances.put("Pekanbaru-Medan", 700);
        distances.put("Medan-Padang", 800); distances.put("Padang-Medan", 800);
        distances.put("Palembang-Pekanbaru", 650); distances.put("Pekanbaru-Palembang", 650);
        distances.put("Palembang-Padang", 700); distances.put("Padang-Palembang", 700);
        distances.put("Palembang-Bandar Lampung", 200); distances.put("Bandar Lampung-Palembang", 200);
        distances.put("Pekanbaru-Padang", 350); distances.put("Padang-Pekanbaru", 350);
        distances.put("Medan-Bandar Lampung", 1600); distances.put("Bandar Lampung-Medan", 1600);


        // Jarak antar kota di Kalimantan
        distances.put("Banjarmasin-Balikpapan", 350); distances.put("Balikpapan-Banjarmasin", 350);
        distances.put("Banjarmasin-Samarinda", 400); distances.put("Samarinda-Banjarmasin", 400);
        distances.put("Banjarmasin-Pontianak", 750); distances.put("Pontianak-Banjarmasin", 750);
        distances.put("Balikpapan-Samarinda", 100); distances.put("Samarinda-Balikpapan", 100);
        distances.put("Pontianak-Samarinda", 600); distances.put("Samarinda-Pontianak", 600);
        distances.put("Balikpapan-Pontianak", 500); distances.put("Pontianak-Balikpapan", 500);


        // Jarak antar kota di Sulawesi
        distances.put("Makassar-Kendari", 300); distances.put("Kendari-Makassar", 300);
        distances.put("Makassar-Palu", 600); distances.put("Palu-Makassar", 600);
        distances.put("Makassar-Manado", 1500); distances.put("Manado-Makassar", 1500);
        distances.put("Manado-Kendari", 1200); distances.put("Kendari-Manado", 1200);
        distances.put("Manado-Palu", 800); distances.put("Palu-Manado", 800);
        distances.put("Palu-Kendari", 450); distances.put("Kendari-Palu", 450);


        // Jarak antar kota di Bali & Nusa Tenggara
        distances.put("Denpasar-Mataram", 100); distances.put("Mataram-Denpasar", 100);
        distances.put("Denpasar-Kupang", 700); distances.put("Kupang-Denpasar", 700);
        distances.put("Mataram-Kupang", 600); distances.put("Kupang-Mataram", 600);


        // Jarak antar kota di Papua
        distances.put("Jayapura-Sorong", 800); distances.put("Sorong-Jayapura", 800);
        distances.put("Jayapura-Manokwari", 700); distances.put("Manokwari-Jayapura", 700);
        distances.put("Sorong-Manokwari", 300); distances.put("Manokwari-Sorong", 300);


        // --- Jarak Antar Pulau (Hanya Kota Besar) ---

        // Jawa <-> Sumatera
        distances.put("Jakarta-Medan", 1900); distances.put("Medan-Jakarta", 1900);
        distances.put("Jakarta-Palembang", 600); distances.put("Palembang-Jakarta", 600);
        distances.put("Jakarta-Pekanbaru", 1000); distances.put("Pekanbaru-Jakarta", 1000);
        distances.put("Jakarta-Bandar Lampung", 300); distances.put("Bandar Lampung-Jakarta", 300);
        distances.put("Jakarta-Padang", 950); distances.put("Padang-Jakarta", 950);
        distances.put("Surabaya-Medan", 2200); distances.put("Medan-Surabaya", 2200);
        distances.put("Surabaya-Palembang", 900); distances.put("Palembang-Surabaya", 900);
        distances.put("Surabaya-Pekanbaru", 1300); distances.put("Pekanbaru-Surabaya", 1300);

        // Jawa <-> Kalimantan
        distances.put("Jakarta-Banjarmasin", 1200); distances.put("Banjarmasin-Jakarta", 1200);
        distances.put("Jakarta-Balikpapan", 1100); distances.put("Balikpapan-Jakarta", 1100);
        distances.put("Jakarta-Samarinda", 1150); distances.put("Samarinda-Jakarta", 1150);
        distances.put("Jakarta-Pontianak", 900); distances.put("Pontianak-Jakarta", 900);
        distances.put("Surabaya-Banjarmasin", 850); distances.put("Banjarmasin-Surabaya", 850);
        distances.put("Surabaya-Balikpapan", 900); distances.put("Balikpapan-Surabaya", 900);
        distances.put("Semarang-Pontianak", 950); distances.put("Pontianak-Semarang", 950);

        // Jawa <-> Sulawesi
        distances.put("Jakarta-Makassar", 1800); distances.put("Makassar-Jakarta", 1800);
        distances.put("Jakarta-Manado", 2500); distances.put("Manado-Jakarta", 2500);
        distances.put("Jakarta-Palu", 2000); distances.put("Palu-Jakarta", 2000);
        distances.put("Surabaya-Makassar", 950); distances.put("Makassar-Surabaya", 950);
        distances.put("Surabaya-Manado", 1700); distances.put("Manado-Surabaya", 1700);

        // Jawa <-> Bali & Nusa Tenggara
        distances.put("Jakarta-Denpasar", 1200); distances.put("Denpasar-Jakarta", 1200);
        distances.put("Jakarta-Mataram", 1300); distances.put("Mataram-Jakarta", 1300);
        distances.put("Jakarta-Kupang", 1600); distances.put("Kupang-Jakarta", 1600);
        distances.put("Surabaya-Denpasar", 400); distances.put("Denpasar-Surabaya", 400);
        distances.put("Surabaya-Mataram", 500); distances.put("Mataram-Surabaya", 500);
        distances.put("Surabaya-Kupang", 1000); distances.put("Kupang-Surabaya", 1000);

        // Jawa <-> Papua
        distances.put("Jakarta-Jayapura", 3500); distances.put("Jayapura-Jakarta", 3500);
        distances.put("Jakarta-Sorong", 3200); distances.put("Sorong-Jakarta", 3200);
        distances.put("Jakarta-Manokwari", 3000); distances.put("Manokwari-Jakarta", 3000);
        distances.put("Surabaya-Jayapura", 2800); distances.put("Jayapura-Surabaya", 2800);
        distances.put("Surabaya-Sorong", 2500); distances.put("Sorong-Surabaya", 2500);
        distances.put("Surabaya-Manokwari", 2300); distances.put("Manokwari-Surabaya", 2300);

        // Sumatera <-> Kalimantan
        distances.put("Medan-Pontianak", 1500); distances.put("Pontianak-Medan", 1500);
        distances.put("Palembang-Banjarmasin", 1000); distances.put("Banjarmasin-Palembang", 1000);
        distances.put("Pekanbaru-Samarinda", 1300); distances.put("Samarinda-Pekanbaru", 1300);

        // Sumatera <-> Sulawesi
        distances.put("Medan-Makassar", 2500); distances.put("Makassar-Medan", 2500);
        distances.put("Palembang-Makassar", 1800); distances.put("Makassar-Palembang", 1800);

        // Kalimantan <-> Sulawesi
        distances.put("Balikpapan-Makassar", 450); distances.put("Makassar-Balikpapan", 450);
        distances.put("Samarinda-Makassar", 500); distances.put("Makassar-Samarinda", 500);
        distances.put("Pontianak-Makassar", 800); distances.put("Makassar-Pontianak", 800);
        distances.put("Banjarmasin-Makassar", 600); distances.put("Makassar-Banjarmasin", 600);
        distances.put("Balikpapan-Manado", 1200); distances.put("Manado-Balikpapan", 1200);
        distances.put("Samarinda-Manado", 1100); distances.put("Manado-Samarinda", 1100);
        distances.put("Pontianak-Palu", 900); distances.put("Palu-Pontianak", 900);

        // Sulawesi <-> Papua
        distances.put("Makassar-Jayapura", 1700); distances.put("Jayapura-Makassar", 1700);
        distances.put("Makassar-Sorong", 1400); distances.put("Sorong-Makassar", 1400);
        distances.put("Manado-Jayapura", 1000); distances.put("Jayapura-Manado", 1000);
        distances.put("Manado-Sorong", 700); distances.put("Sorong-Manado", 700);
        distances.put("Palu-Jayapura", 1500); distances.put("Jayapura-Palu", 1500);
        distances.put("Palu-Sorong", 1200); distances.put("Sorong-Palu", 1200);
        distances.put("Kendari-Jayapura", 1600); distances.put("Jayapura-Kendari", 1600);

        // Bali & Nusa Tenggara <-> Sulawesi
        distances.put("Denpasar-Makassar", 800); distances.put("Makassar-Denpasar", 800);
        distances.put("Mataram-Makassar", 700); distances.put("Makassar-Mataram", 700);
        distances.put("Kupang-Makassar", 1000); distances.put("Makassar-Kupang", 1000);
        distances.put("Denpasar-Manado", 1800); distances.put("Manado-Denpasar", 1800);
        distances.put("Mataram-Palu", 1300); distances.put("Palu-Mataram", 1300);

        // Bali & Nusa Tenggara <-> Papua
        distances.put("Denpasar-Jayapura", 2000); distances.put("Jayapura-Denpasar", 2000);
        distances.put("Denpasar-Sorong", 1700); distances.put("Sorong-Denpasar", 1700);
        distances.put("Mataram-Jayapura", 1900); distances.put("Jayapura-Mataram", 1900);
        distances.put("Kupang-Sorong", 1500); distances.put("Sorong-Kupang", 1500);
        distances.put("Kupang-Jayapura", 2300); distances.put("Jayapura-Kupang", 2300); // Rute baru yang mungkin berguna


        // Kalimantan <-> Papua
        distances.put("Banjarmasin-Jayapura", 2000); distances.put("Jayapura-Banjarmasin", 2000);
        distances.put("Balikpapan-Sorong", 1400); distances.put("Sorong-Balikpapan", 1400);
        distances.put("Pontianak-Manokwari", 1800); distances.put("Manokwari-Pontianak", 1800);


        // Inisialisasi detailed pricing data for General Goods
        Map<String, ServicePrice> jneServices = new HashMap<>();
        jneServices.put("Reguler", new ServicePrice(8000, 3000, "3 - 5 Hari"));
        jneServices.put("Express", new ServicePrice(8000, 4225, "1 - 2 Hari"));
        detailedPricing.put("JNE", jneServices);

        Map<String, ServicePrice> tikiServices = new HashMap<>();
        tikiServices.put("Reguler", new ServicePrice(8500, 3200, "3 - 4 Hari"));
        tikiServices.put("Express", new ServicePrice(8500, 4500, "1 - 2 Hari"));
        detailedPricing.put("TIKI", tikiServices);

        Map<String, ServicePrice> posServices = new HashMap<>();
        posServices.put("Reguler", new ServicePrice(7500, 2800, "4 - 6 Hari"));
        posServices.put("Express", new ServicePrice(7500, 4000, "2 - 3 Hari"));
        detailedPricing.put("POS Indonesia", posServices);

        Map<String, ServicePrice> sicepatServices = new HashMap<>();
        sicepatServices.put("Reguler", new ServicePrice(8200, 3100, "2 - 4 Hari"));
        sicepatServices.put("Express", new ServicePrice(8200, 4300, "1 - 1 Hari"));
        detailedPricing.put("SiCepat", sicepatServices);

        // Inisialisasi detailed pricing data for Motorcycle Towing
        Map<String, ServicePrice> towingProServices = new HashMap<>();
        towingProServices.put("<250cc", new ServicePrice(300000, 5000, "1 - 2 Hari"));
        towingProServices.put("250-600cc", new ServicePrice(500000, 6500, "1 - 2 Hari"));
        towingProServices.put(">600cc", new ServicePrice(750000, 8000, "1 - 2 Hari"));
        towingPricing.put("Towing Pro", towingProServices);

        Map<String, ServicePrice> angkutMotorCepatServices = new HashMap<>();
        angkutMotorCepatServices.put("<250cc", new ServicePrice(280000, 4800, "2 - 3 Hari"));
        angkutMotorCepatServices.put("250-600cc", new ServicePrice(480000, 6200, "2 - 3 Hari"));
        angkutMotorCepatServices.put(">600cc", new ServicePrice(700000, 7500, "2 - 3 Hari"));
        towingPricing.put("Angkut Motor Cepat", angkutMotorCepatServices);

        // Dummy data untuk pelacakan dengan riwayat
        trackingData.put("TRK123456789", Arrays.asList(
                new TrackingEvent(LocalDateTime.now().minusDays(3), "Jakarta", "Paket siap dijemput."),
                new TrackingEvent(LocalDateTime.now().minusDays(3).plusHours(2), "Jakarta", "Paket telah dijemput dari pengirim."),
                new TrackingEvent(LocalDateTime.now().minusDays(2), "Pusat Sortir Jakarta", "Paket tiba di pusat sortir."),
                new TrackingEvent(LocalDateTime.now().minusDays(1), "Surabaya", "Paket tiba di kota tujuan, siap dikirim ke alamat penerima."),
                new TrackingEvent(LocalDateTime.now().minusHours(5), "Surabaya", "Paket dalam proses pengiriman oleh kurir.")
        ));
        trackingData.put("TRK987654321", Arrays.asList(
                new TrackingEvent(LocalDateTime.now().minusDays(5), "Bandung", "Paket siap dijemput."),
                new TrackingEvent(LocalDateTime.now().minusDays(5).plusHours(3), "Bandung", "Paket dijemput dari pengirim."),
                new TrackingEvent(LocalDateTime.now().minusDays(4), "Pusat Sortir Bandung", "Paket transit di hub regional."),
                new TrackingEvent(LocalDateTime.now().minusDays(2), "Medan", "Paket tiba di kota tujuan."),
                new TrackingEvent(LocalDateTime.now().minusDays(1), "Medan", "Paket telah berhasil diterima oleh Yuni (Penerima).")
        ));
        trackingData.put("TRK000111222", Arrays.asList(
                new TrackingEvent(LocalDateTime.now().minusDays(1), "Denpasar", "Paket siap dijemput."),
                new TrackingEvent(LocalDateTime.now().minusDays(1).plusHours(1), "Denpasar", "Paket dijemput dari pengirim."),
                new TrackingEvent(LocalDateTime.now().minusHours(8), "Denpasar", "Paket dalam perjalanan menuju Yogyakarta.")
        ));

        System.out.println("DEBUG: Membangun Scene...");
        createServiceSelectionScene();
        createFormScene();
        createDetailScene();
        createTrackingScene();
        System.out.println("DEBUG: Scene selesai dibangun.");

        primaryStage.setScene(serviceSelectionScene);
        // >>> PERUBAHAN DI SINI <<<
        primaryStage.setResizable(true); // Memungkinkan jendela untuk diubah ukurannya
        primaryStage.setWidth(500);     // Set lebar awal yang lebih besar
        primaryStage.setHeight(750);    // Set tinggi awal yang lebih besar
        // Jika ingin fullscreen (pengguna tidak bisa keluar dengan mudah):
        // primaryStage.setFullScreen(true);
        // primaryStage.setFullScreenExitHint("Tekan ESC untuk keluar dari mode fullscreen");
        // primaryStage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.valueOf("ESC"));

        System.out.println("DEBUG: Memanggil primaryStage.show()...");
        primaryStage.show();
        System.out.println("DEBUG: primaryStage.show() selesai dipanggil. Seharusnya jendela terlihat sekarang.");
    }

    private void createServiceSelectionScene() {
        VBox mainLayout = new VBox(25);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(50));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to top left, #B3E5FC, #BBDEFB);");

        Label title = new Label("Pilih Jenis Layanan");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#2196F3"));
        VBox.setMargin(title, new Insets(0, 0, 20, 0));

        Button generalPackageButton = new Button("Kirim Paket Umum");
        generalPackageButton.setPrefSize(300, 80);
        generalPackageButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        generalPackageButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        generalPackageButton.setOnAction(e -> {
            mainDeliveryTypeComboBox.setValue("Barang Umum");
            primaryStage.setScene(formScene);
            resetForm();
        });

        Button towingMotorButton = new Button("Layanan Towing Motor");
        towingMotorButton.setPrefSize(300, 80);
        towingMotorButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        towingMotorButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: #333333; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        towingMotorButton.setOnAction(e -> {
            mainDeliveryTypeComboBox.setValue("Towing Motor");
            primaryStage.setScene(formScene);
            resetForm();
        });

        Button trackPackageButton = new Button("Lacak Paket");
        trackPackageButton.setPrefSize(300, 80);
        trackPackageButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        trackPackageButton.setStyle("-fx-background-color: #00BCD4; -fx-text-fill: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        trackPackageButton.setOnAction(e -> {
            primaryStage.setScene(trackingScene);
        });

        mainLayout.getChildren().addAll(title, generalPackageButton, towingMotorButton, trackPackageButton);
        serviceSelectionScene = new Scene(mainLayout, 450, 650);
    }


    private void createFormScene() {
        VBox rootLayout = new VBox();
        rootLayout.setStyle("-fx-background-color: #E0F7FA;");
        rootLayout.setAlignment(Pos.TOP_CENTER);

        // Header
        StackPane headerPane = new StackPane();
        headerPane.setPrefHeight(80);
        headerPane.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 0 0 20 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        Label headerTitle = new Label("Form Pengiriman");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);
        headerPane.getChildren().add(headerTitle);
        StackPane.setAlignment(headerTitle, Pos.CENTER);

        // Content Area (Card)
        VBox contentBox = new VBox(15);
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        VBox.setMargin(contentBox, new Insets(20, 25, 20, 25)); // Margin card dari tepi scene

        // GridPane for form fields inside contentBox
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10)); // Padding internal for grid

        // Column constraints for labels and fields
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setHalignment(HPos.LEFT);
        col1.setPrefWidth(120); // Width for labels

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2);

        int row = 0;

        Label mainDeliveryTypeLabel = new Label("Jenis Pengiriman:");
        mainDeliveryTypeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        mainDeliveryTypeComboBox = new ComboBox<>();
        mainDeliveryTypeComboBox.getItems().addAll("Barang Umum", "Towing Motor");
        mainDeliveryTypeComboBox.setValue("Barang Umum");
        mainDeliveryTypeComboBox.setPrefWidth(200); // Sesuaikan lebar
        mainDeliveryTypeComboBox.setMaxWidth(Double.MAX_VALUE); // Memenuhi lebar
        mainDeliveryTypeComboBox.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        formGrid.add(mainDeliveryTypeLabel, 0, row);
        formGrid.add(mainDeliveryTypeComboBox, 1, row++);

        // General Goods Form VBox
        generalGoodsFormVBox = new VBox(10);
        generalGoodsFormVBox.setPadding(new Insets(0));
        GridPane generalGoodsInnerGrid = new GridPane();
        generalGoodsInnerGrid.setHgap(10);
        generalGoodsInnerGrid.setVgap(10);
        generalGoodsInnerGrid.getColumnConstraints().addAll(col1, col2); // Apply constraints

        Label itemNameLabel = new Label("Nama Barang:");
        itemNameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        itemNameField = new TextField("Buku");
        itemNameField.setMaxWidth(Double.MAX_VALUE);
        itemNameField.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        generalGoodsInnerGrid.add(itemNameLabel, 0, 0);
        generalGoodsInnerGrid.add(itemNameField, 1, 0);

        Label weightLabel = new Label("Berat (kg):");
        weightLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        weightField = new TextField("2.5");
        weightField.setMaxWidth(Double.MAX_VALUE);
        weightField.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        generalGoodsInnerGrid.add(weightLabel, 0, 1);
        generalGoodsInnerGrid.add(weightField, 1, 1);
        generalGoodsFormVBox.getChildren().add(generalGoodsInnerGrid);
        formGrid.add(generalGoodsFormVBox, 0, row++, 2, 1); // Add as one component spanning 2 columns

        // Towing Motor Form VBox
        towingMotorFormVBox = new VBox(10);
        towingMotorFormVBox.setPadding(new Insets(0));
        towingMotorFormVBox.setVisible(false);
        towingMotorFormVBox.setManaged(false);
        GridPane towingMotorInnerGrid = new GridPane();
        towingMotorInnerGrid.setHgap(10);
        towingMotorInnerGrid.setVgap(10);
        towingMotorInnerGrid.getColumnConstraints().addAll(col1, col2); // Apply constraints

        Label motorCCLabel = new Label("Kategori CC Motor:");
        motorCCLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        motorCCComboBox = new ComboBox<>();
        motorCCComboBox.getItems().addAll("<250cc", "250-600cc", ">600cc");
        motorCCComboBox.setValue("<250cc");
        motorCCComboBox.setMaxWidth(Double.MAX_VALUE);
        motorCCComboBox.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        towingMotorInnerGrid.add(motorCCLabel, 0, 0);
        towingMotorInnerGrid.add(motorCCComboBox, 1, 0);
        towingMotorFormVBox.getChildren().add(towingMotorInnerGrid);
        formGrid.add(towingMotorFormVBox, 0, row++, 2, 1); // Add as one component spanning 2 columns

        // Spacer for visual separation (like the line in the image)
        Separator separator1 = new Separator();
        separator1.setPadding(new Insets(5, 0, 5, 0));
        formGrid.add(separator1, 0, row++, 2, 1);


        List<String> cities = new ArrayList<>();
        // Jawa
        cities.add("Jakarta"); cities.add("Surabaya"); cities.add("Bandung"); cities.add("Semarang");
        cities.add("Yogyakarta"); cities.add("Malang"); cities.add("Solo");
        // Sumatera
        cities.add("Medan"); cities.add("Palembang"); cities.add("Pekanbaru"); cities.add("Bandar Lampung");
        cities.add("Padang");
        // Kalimantan
        cities.add("Banjarmasin"); cities.add("Balikpapan"); cities.add("Samarinda"); cities.add("Pontianak");
        // Sulawesi
        cities.add("Makassar"); cities.add("Manado"); cities.add("Kendari"); cities.add("Palu");
        // Bali & Nusa Tenggara
        cities.add("Denpasar"); cities.add("Mataram"); cities.add("Kupang");
        // Papua
        cities.add("Jayapura"); cities.add("Sorong"); cities.add("Manokwari");


        Label originCityLabel = new Label("Kota Asal:");
        originCityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        originCityComboBox = new ComboBox<>();
        originCityComboBox.getItems().addAll(cities);
        originCityComboBox.setValue("Jakarta");
        originCityComboBox.setMaxWidth(Double.MAX_VALUE);
        originCityComboBox.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        formGrid.add(originCityLabel, 0, row);
        formGrid.add(originCityComboBox, 1, row++);

        Label destinationCityLabel = new Label("Kota Tujuan:");
        destinationCityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        destinationCityComboBox = new ComboBox<>();
        destinationCityComboBox.getItems().addAll(cities);
        destinationCityComboBox.setValue("Surabaya");
        destinationCityComboBox.setMaxWidth(Double.MAX_VALUE);
        destinationCityComboBox.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        formGrid.add(destinationCityLabel, 0, row);
        formGrid.add(destinationCityComboBox, 1, row++);

        Label shippingTypeLabel = new Label("Jenis Layanan:");
        shippingTypeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        shippingTypeGroup = new ToggleGroup();
        regularRadio = new RadioButton("Reguler");
        regularRadio.setToggleGroup(shippingTypeGroup);
        regularRadio.setStyle("-fx-text-fill: #555555;");
        expressRadio = new RadioButton("Express");
        expressRadio.setToggleGroup(shippingTypeGroup);
        expressRadio.setSelected(true); // Default select Express as in image
        expressRadio.setStyle("-fx-text-fill: #555555;");

        HBox radioBox = new HBox(15, regularRadio, expressRadio);
        radioBox.setAlignment(Pos.CENTER_LEFT); // Align radios to left
        formGrid.add(shippingTypeLabel, 0, row);
        formGrid.add(radioBox, 1, row++);

        Label expeditionLabel = new Label("Ekspedisi:"); // Label diganti jadi lebih singkat
        expeditionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        expeditionComboBox = new ComboBox<>();
        expeditionComboBox.setMaxWidth(Double.MAX_VALUE);
        expeditionComboBox.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-padding: 8;");
        formGrid.add(expeditionLabel, 0, row);
        formGrid.add(expeditionComboBox, 1, row++);

        contentBox.getChildren().add(formGrid);

        // Result Labels (Jarak & Biaya) - Ditempatkan setelah formGrid, di luar GridPane
        formDistanceLabel = new Label("Pilihan Ekspedisi: 800 km"); // Set default text as in image
        formDistanceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        formDistanceLabel.setStyle("-fx-text-fill: #666666; -fx-alignment: center_right;"); // Align text to right
        formDistanceLabel.setMaxWidth(Double.MAX_VALUE);
        contentBox.getChildren().add(formDistanceLabel);

        formCostLabel = new Label("Biaya pengiriman untuk Buku (2.5 kg) adalah: Rp3.400.000");
        formCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        formCostLabel.setStyle("-fx-text-fill: #007ACC; -fx-alignment: center_right;"); // Align text to right
        formCostLabel.setMaxWidth(Double.MAX_VALUE);
        contentBox.getChildren().add(formCostLabel);

        // Buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Padding atas untuk tombol

        Button calculateButton = new Button("Hitung Biaya");
        calculateButton.setOnAction(e -> showDetailPengiriman());
        calculateButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 20; -fx-font-weight: bold; -fx-font-size: 14px;");
        calculateButton.setMaxWidth(Double.MAX_VALUE);
        calculateButton.setPrefHeight(45); // Set tinggi tombol

        Button kirimBarangLainFormButton = new Button("Kirim Barang Lain");
        kirimBarangLainFormButton.setOnAction(e -> resetForm());
        kirimBarangLainFormButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 20; -fx-font-weight: bold; -fx-font-size: 14px;");
        kirimBarangLainFormButton.setMaxWidth(Double.MAX_VALUE);
        kirimBarangLainFormButton.setPrefHeight(45); // Set tinggi tombol

        Button backFromFormButton = new Button("Kembali");
        backFromFormButton.setOnAction(e -> primaryStage.setScene(serviceSelectionScene));
        backFromFormButton.setStyle("-fx-background-color: #AAAAAA; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 20; -fx-font-weight: bold; -fx-font-size: 14px;");
        backFromFormButton.setMaxWidth(Double.MAX_VALUE);
        backFromFormButton.setPrefHeight(45); // Set tinggi tombol

        buttonBox.getChildren().addAll(calculateButton, kirimBarangLainFormButton, backFromFormButton);
        contentBox.getChildren().add(buttonBox);


        rootLayout.getChildren().addAll(headerPane, contentBox);

        itemNameField.textProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        weightField.textProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        motorCCComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        originCityComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        destinationCityComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        shippingTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());
        expeditionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFormCostLabel());

        mainDeliveryTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Barang Umum".equals(newVal)) {
                generalGoodsFormVBox.setVisible(true);
                generalGoodsFormVBox.setManaged(true);
                towingMotorFormVBox.setVisible(false);
                towingMotorFormVBox.setManaged(false);
                expeditionComboBox.getItems().setAll(detailedPricing.keySet());
                expeditionComboBox.setValue("JNE");
            } else if ("Towing Motor".equals(newVal)) {
                generalGoodsFormVBox.setVisible(false);
                generalGoodsFormVBox.setManaged(false);
                towingMotorFormVBox.setVisible(true);
                towingMotorFormVBox.setManaged(true);
                expeditionComboBox.getItems().setAll(towingPricing.keySet());
                expeditionComboBox.setValue("Towing Pro");
            }
            updateFormCostLabel();
        });

        expeditionComboBox.getItems().setAll(detailedPricing.keySet());
        expeditionComboBox.setValue("JNE");

        updateFormCostLabel();

        // >>> PERUBAHAN DI SINI <<<
        formScene = new Scene(rootLayout, 450, 680); // Ukuran awal scene ini
    }

    private void updateFormCostLabel() {
        try {
            String mainType = mainDeliveryTypeComboBox.getValue();
            String originCity = originCityComboBox.getValue();
            String destinationCity = destinationCityComboBox.getValue();
            RadioButton selectedRadio = (RadioButton) shippingTypeGroup.getSelectedToggle();
            String shippingType = selectedRadio != null ? selectedRadio.getText() : null;
            String selectedEkspedisi = expeditionComboBox.getValue();

            if (originCity == null || destinationCity == null || shippingType == null || selectedEkspedisi == null) {
                formCostLabel.setText("");
                formDistanceLabel.setText("Jarak Dihitung Otomatis:");
                return;
            }

            Integer distance = distances.get(originCity + "-" + destinationCity);
            if (distance == null) {
                formDistanceLabel.setText("Jarak Dihitung Otomatis: N/A (Jarak tidak tersedia)");
                formCostLabel.setText("");
                return;
            }

            double totalCost = 0;
            String itemNameForLabel = "";
            String itemCategoryForLabel = "";

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            currencyFormat.setMaximumFractionDigits(0);

            if ("Barang Umum".equals(mainType)) {
                String itemName = itemNameField.getText();
                double weight;
                try {
                    weight = Double.parseDouble(weightField.getText());
                } catch (NumberFormatException e) {
                    formCostLabel.setText("Masukkan angka valid untuk berat.");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }

                if (itemName.isEmpty() || weight <= 0) {
                    formCostLabel.setText("");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }

                Map<String, ServicePrice> serviceMap = detailedPricing.get(selectedEkspedisi);
                if (serviceMap == null) {
                    formCostLabel.setText("Ekspedisi umum tidak ditemukan.");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }
                ServicePrice serviceDetails = serviceMap.get(shippingType);
                if (serviceDetails == null) {
                    formCostLabel.setText("Jenis pengiriman umum tidak tersedia untuk ekspedisi ini.");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }

                double hargaPerKg = serviceDetails.getHargaPerKg();
                double hargaPerKm = serviceDetails.getHargaPerKm();
                totalCost = (weight * hargaPerKg) + (distance * hargaPerKm);
                itemNameForLabel = itemName;
                itemCategoryForLabel = String.format("%.1f kg", weight);

                formCostLabel.setText(String.format("Biaya pengiriman untuk %s (%s) adalah: %s", itemNameForLabel, itemCategoryForLabel, currencyFormat.format(totalCost)));

            } else if ("Towing Motor".equals(mainType)) {
                String motorCC = motorCCComboBox.getValue();
                if (motorCC == null || motorCC.isEmpty()) {
                    formCostLabel.setText("");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }

                Map<String, ServicePrice> serviceMap = towingPricing.get(selectedEkspedisi);
                if (serviceMap == null) {
                    formCostLabel.setText("Ekspedisi towing tidak ditemukan.");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }
                ServicePrice serviceDetails = serviceMap.get(motorCC);
                if (serviceDetails == null) {
                    formCostLabel.setText("Layanan towing untuk CC ini tidak tersedia.");
                    formDistanceLabel.setText(String.format("Jarak Dihitung Otomatis: %d km", distance));
                    return;
                }

                double baseFee = serviceDetails.getHargaPerKg();
                double hargaPerKm = serviceDetails.getHargaPerKm();
                totalCost = baseFee + (distance * hargaPerKm);
                itemNameForLabel = "Motor";
                itemCategoryForLabel = motorCC;

                formCostLabel.setText(String.format("Biaya towing motor %s (%s) adalah: %s", itemNameForLabel, itemCategoryForLabel, currencyFormat.format(totalCost)));

            } else {
                formCostLabel.setText("Pilih jenis pengiriman utama.");
                formDistanceLabel.setText("Jarak Dihitung Otomatis:");
                return;
            }

            formDistanceLabel.setText(String.format("Pilihan Ekspedisi: %d km", distance)); // Updated to match image

        } catch (NumberFormatException e) {
            formCostLabel.setText("Masukkan angka yang valid untuk berat.");
            formDistanceLabel.setText("Jarak Dihitung Otomatis:");
        } catch (NullPointerException e) {
            formCostLabel.setText("Lengkapi semua pilihan.");
            formDistanceLabel.setText("Jarak Dihitung Otomatis:");
        } catch (Exception e) {
            formCostLabel.setText("Terjadi kesalahan.");
            formDistanceLabel.setText("Jarak Dihitung Otomatis:");
            e.printStackTrace();
        }
    }


    private void createDetailScene() {
        VBox rootLayout = new VBox();
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #FFFFFF);");
        rootLayout.setPadding(new Insets(0));

        StackPane headerPane = new StackPane();
        headerPane.setPrefHeight(100);
        headerPane.setStyle("-fx-background-color: #FFA500; -fx-padding: 20; -fx-background-radius: 0 0 20 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        Label headerTitle = new Label("Biaya Pengiriman");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerTitle.setTextFill(Color.WHITE);
        headerPane.getChildren().add(headerTitle);
        StackPane.setAlignment(headerTitle, Pos.CENTER);

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(30, 25, 25, 25));

        VBox detailCard = new VBox(8);
        detailCard.setPadding(new Insets(20));
        detailCard.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        detailCard.setPrefWidth(350);

        Label detailCardTitle = new Label("Detail Pengiriman");
        detailCardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        detailCardTitle.setTextFill(Color.web("#333333"));
        detailCard.getChildren().add(detailCardTitle);
        detailCard.getChildren().add(new Separator());

        GridPane detailGrid = new GridPane();
        detailGrid.setHgap(10);
        detailGrid.setVgap(8);

        // Column constraints for labels and fields in detailGrid
        ColumnConstraints detailCol1 = new ColumnConstraints();
        detailCol1.setHgrow(Priority.NEVER);
        detailCol1.setHalignment(HPos.LEFT);
        detailCol1.setPrefWidth(120); // Width for labels

        ColumnConstraints detailCol2 = new ColumnConstraints();
        detailCol2.setHgrow(Priority.ALWAYS);
        detailGrid.getColumnConstraints().addAll(detailCol1, detailCol2);

        detailItemNameLabel = new Label();
        detailAsalLabel = new Label();
        detailTujuanLabel = new Label();
        detailBeratLabel = new Label();
        detailJarakLabel = new Label();
        detailJenisPengirimanLabel = new Label();
        detailEkspedisiLabel = new Label();
        detailNomorResiLabel = new Label();
        detailTotalBiayaLabel = new Label();
        detailHargaPerKgLabel = new Label();
        detailHargaPerKmLabel = new Label();
        detailEstimasiWaktuLabel = new Label();

        addDetailRow(detailGrid, "Nomor Resi", detailNomorResiLabel, 0);
        addDetailRow(detailGrid, "Item/Motor", detailItemNameLabel, 1);
        addDetailRow(detailGrid, "Berat/CC", detailBeratLabel, 2);
        addDetailRow(detailGrid, "Asal", detailAsalLabel, 3);
        addDetailRow(detailGrid, "Tujuan", detailTujuanLabel, 4);
        addDetailRow(detailGrid, "Jarak", detailJarakLabel, 5);
        addDetailRow(detailGrid, "Jenis Layanan", detailJenisPengirimanLabel, 6);
        addDetailRow(detailGrid, "Ekspedisi", detailEkspedisiLabel, 7);

        detailCard.getChildren().add(detailGrid);
        contentBox.getChildren().add(detailCard);

        VBox summaryCard = new VBox(10);
        summaryCard.setPadding(new Insets(20));
        summaryCard.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        summaryCard.setPrefWidth(350);

        Label summaryCardTitle = new Label("Ringkasan Biaya");
        summaryCardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        summaryCardTitle.setTextFill(Color.web("#333333"));
        summaryCard.getChildren().add(summaryCardTitle);
        summaryCard.getChildren().add(new Separator());

        GridPane priceGrid = new GridPane();
        priceGrid.setHgap(10);
        priceGrid.setVgap(8);
        priceGrid.getColumnConstraints().addAll(detailCol1, detailCol2); // Apply constraints

        addDetailRow(priceGrid, "Biaya Awal/Kg", detailHargaPerKgLabel, 0);
        addDetailRow(priceGrid, "Biaya per Km", detailHargaPerKmLabel, 1);
        addDetailRow(priceGrid, "Estimasi Waktu", detailEstimasiWaktuLabel, 2);
        detailEstimasiWaktuLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        detailEstimasiWaktuLabel.setTextFill(Color.web("#28A745"));

        summaryCard.getChildren().add(priceGrid);

        VBox totalCostBox = new VBox(5);
        totalCostBox.setAlignment(Pos.CENTER_RIGHT);
        Label totalCostLabelText = new Label("TOTAL BIAYA:");
        totalCostLabelText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalCostLabelText.setTextFill(Color.web("#555555"));

        detailTotalBiayaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        detailTotalBiayaLabel.setTextFill(Color.web("#007ACC"));
        totalCostBox.getChildren().addAll(totalCostLabelText, detailTotalBiayaLabel);
        summaryCard.getChildren().add(totalCostBox);
        VBox.setMargin(totalCostBox, new Insets(10, 0, 0, 0));

        contentBox.getChildren().add(summaryCard);


        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 20, 0));

        Button backButtonDetail = new Button("Kembali");
        backButtonDetail.setOnAction(e -> {
            resetForm();
            primaryStage.setScene(serviceSelectionScene);
        });
        backButtonDetail.setPrefWidth(150);
        backButtonDetail.setPrefHeight(40);
        backButtonDetail.setStyle("-fx-background-color: #6C757D; -fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 14px;");

        Button sendAnotherButtonDetail = new Button("Kirim Barang Lain");
        sendAnotherButtonDetail.setOnAction(e -> {
            resetForm();
            primaryStage.setScene(formScene);
        });
        sendAnotherButtonDetail.setPrefWidth(150);
        sendAnotherButtonDetail.setPrefHeight(40);
        sendAnotherButtonDetail.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 14px;");

        buttonBox.getChildren().addAll(backButtonDetail, sendAnotherButtonDetail);
        contentBox.getChildren().add(buttonBox);

        rootLayout.getChildren().addAll(headerPane, contentBox);

        // >>> PERUBAHAN DI SINI <<<
        detailScene = new Scene(rootLayout, 450, 680); // Ukuran awal scene ini
    }

    private void addDetailRow(GridPane grid, String labelText, Label valueLabel, int row) {
        Label label = new Label(labelText + ":");
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #555555;");
        valueLabel.setStyle("-fx-text-fill: #333333;");
        grid.add(label, 0, row);
        grid.add(valueLabel, 1, row);
    }


    private void showDetailPengiriman() {
        try {
            String mainType = mainDeliveryTypeComboBox.getValue();
            String originCity = originCityComboBox.getValue();
            String destinationCity = destinationCityComboBox.getValue();
            RadioButton selectedRadio = (RadioButton) shippingTypeGroup.getSelectedToggle();
            String shippingType = selectedRadio != null ? selectedRadio.getText() : null;
            String selectedEkspedisi = expeditionComboBox.getValue();

            if (originCity == null || destinationCity == null || shippingType == null || selectedEkspedisi == null) {
                showAlert("Validasi", "Pastikan semua bidang terisi dengan benar (kota, jenis pengiriman, ekspedisi).", Alert.AlertType.WARNING);
                return;
            }

            Integer distance = distances.get(originCity + "-" + destinationCity);
            if (distance == null) {
                showAlert("Error Data Jarak", String.format("Data jarak tidak tersedia untuk %s ke %s. Tambahkan data jarak di kode aplikasi.", originCity, destinationCity), Alert.AlertType.ERROR);
                return;
            }

            double totalCost = 0;
            ServicePrice serviceDetails = null;
            DataBarang dataBarang;
            String generatedTrackingNumber = "TRK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            currencyFormat.setMaximumFractionDigits(0);

            if ("Barang Umum".equals(mainType)) {
                String itemName = itemNameField.getText();
                double weight;
                try {
                    weight = Double.parseDouble(weightField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Error Input", "Masukkan angka yang valid untuk berat (misal: 2.5).", Alert.AlertType.ERROR);
                    return;
                }


                if (itemName.isEmpty() || weight <= 0) {
                    showAlert("Validasi", "Nama Barang dan Berat harus diisi dengan benar.", Alert.AlertType.WARNING);
                    return;
                }

                Map<String, ServicePrice> serviceMap = detailedPricing.get(selectedEkspedisi);
                if (serviceMap == null) {
                    showAlert("Error Data Harga", "Data harga untuk ekspedisi umum '" + selectedEkspedisi + "' tidak ditemukan.", Alert.AlertType.ERROR);
                    return;
                }
                serviceDetails = serviceMap.get(shippingType);
                if (serviceDetails == null) {
                    showAlert("Error Data Harga", "Jenis pengiriman umum '" + shippingType + "' tidak tersedia untuk ekspedisi '" + selectedEkspedisi + "'.", Alert.AlertType.ERROR);
                    return;
                }

                totalCost = (weight * serviceDetails.getHargaPerKg()) + (distance * serviceDetails.getHargaPerKm());
                dataBarang = new DataBarang(itemName, weight, originCity, destinationCity, shippingType, selectedEkspedisi, mainType);

                detailItemNameLabel.setText(dataBarang.getNamaBarang());
                detailBeratLabel.setText(String.format("%.1f kg", dataBarang.getBerat()));
                detailHargaPerKgLabel.setText(currencyFormat.format(serviceDetails.getHargaPerKg()));

                List<TrackingEvent> initialEvents = new ArrayList<>();
                initialEvents.add(new TrackingEvent(LocalDateTime.now(), originCity, "Pesanan Dibuat, menunggu penjemputan."));
                trackingData.put(generatedTrackingNumber, initialEvents);


            } else if ("Towing Motor".equals(mainType)) {
                String motorCC = motorCCComboBox.getValue();
                if (motorCC == null || motorCC.isEmpty()) {
                    showAlert("Validasi", "Pilih kategori CC Motor.", Alert.AlertType.WARNING);
                    return;
                }

                Map<String, ServicePrice> serviceMap = towingPricing.get(selectedEkspedisi);
                if (serviceMap == null) {
                    showAlert("Error Data Harga", "Data harga untuk ekspedisi towing '" + selectedEkspedisi + "' tidak ditemukan.", Alert.AlertType.ERROR);
                    return;
                }
                serviceDetails = serviceMap.get(motorCC);
                if (serviceDetails == null) {
                    showAlert("Error Data Harga", "Layanan towing untuk kategori CC '" + motorCC + "' tidak tersedia untuk ekspedisi '" + selectedEkspedisi + "'.", Alert.AlertType.ERROR);
                    return;
                }

                totalCost = serviceDetails.getHargaPerKg() + (distance * serviceDetails.getHargaPerKm());
                dataBarang = new DataBarang(motorCC, originCity, destinationCity, shippingType, selectedEkspedisi, mainType);

                detailItemNameLabel.setText("Motor");
                detailBeratLabel.setText(dataBarang.getMotorCCCategory() != null ? dataBarang.getMotorCCCategory() : motorCC);
                detailHargaPerKgLabel.setText(currencyFormat.format(serviceDetails.getHargaPerKg()) + " (Base Fee)");

                List<TrackingEvent> initialEvents = new ArrayList<>();
                initialEvents.add(new TrackingEvent(LocalDateTime.now(), originCity, "Pesanan Towing Dibuat, menunggu penjemputan motor."));
                trackingData.put(generatedTrackingNumber, initialEvents);

            } else {
                showAlert("Terjadi Kesalahan", "Jenis pengiriman utama tidak valid.", Alert.AlertType.ERROR);
                return;
            }

            detailNomorResiLabel.setText(generatedTrackingNumber);
            detailAsalLabel.setText(dataBarang.getAsal());
            detailTujuanLabel.setText(dataBarang.getTujuan());
            detailJarakLabel.setText(String.format("%d km", distance));
            detailJenisPengirimanLabel.setText(dataBarang.getJenisPengiriman());
            detailEkspedisiLabel.setText(dataBarang.getNamaEkspedisi());
            detailHargaPerKmLabel.setText(currencyFormat.format(serviceDetails.getHargaPerKm()));
            detailTotalBiayaLabel.setText(currencyFormat.format(totalCost));
            detailEstimasiWaktuLabel.setText(serviceDetails.getEstimasiWaktu());

            primaryStage.setScene(detailScene);

        } catch (NumberFormatException e) {
            showAlert("Error Input", "Masukkan angka yang valid untuk berat (misal: 2.5) atau pilih kategori CC motor.", Alert.AlertType.ERROR);
        } catch (NullPointerException e) {
            showAlert("Terjadi Kesalahan", "Mohon pastikan semua pilihan (jenis pengiriman utama, kota, jenis layanan, ekspedisi, dan detail item/motor) telah dipilih/diisi dengan benar. Detil: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Terjadi Kesalahan", "Terjadi kesalahan yang tidak terduga: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void resetForm() {
        itemNameField.setText("Buku");
        weightField.setText("2.5");
        motorCCComboBox.setValue("<250cc");
        originCityComboBox.setValue("Jakarta");
        destinationCityComboBox.setValue("Surabaya");
        expressRadio.setSelected(true);
        mainDeliveryTypeComboBox.setValue("Barang Umum");

        updateFormCostLabel();
    }

    private void createTrackingScene() {
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(30, 40, 30, 40));
        mainLayout.setStyle("-fx-background-color: #E0F7FA;");

        Label headerTitle = new Label("Lacak Paket Anda");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerTitle.setTextFill(Color.web("#006064"));
        VBox.setMargin(headerTitle, new Insets(0, 0, 20, 0));

        VBox trackingForm = new VBox(15);
        trackingForm.setAlignment(Pos.CENTER);
        trackingForm.setPadding(new Insets(20));
        trackingForm.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        Label trackingNumberLabel = new Label("Masukkan Nomor Resi:");
        trackingNumberLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        TextField trackingNumberField = new TextField();
        trackingNumberField.setPromptText("Contoh: TRK123456789");
        trackingNumberField.setPrefHeight(40);
        trackingNumberField.setStyle("-fx-background-radius: 5; -fx-border-color: #B2EBF2; -fx-border-radius: 5; -fx-padding: 8;");


        Button trackButton = new Button("Lacak");
        trackButton.setPrefWidth(Double.MAX_VALUE);
        trackButton.setPrefHeight(45);
        trackButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        trackButton.setStyle("-fx-background-color: #00BCD4; -fx-text-fill: white; -fx-background-radius: 10;");

        TextArea trackingResultArea = new TextArea();
        trackingResultArea.setPrefHeight(200);
        trackingResultArea.setEditable(false);
        trackingResultArea.setWrapText(true);
        trackingResultArea.setStyle("-fx-control-inner-background: #F8F8F8; -fx-background-radius: 5; -fx-border-color: #B2EBF2; -fx-border-radius: 5; -fx-padding: 10;");
        trackingResultArea.setText("Masukkan nomor resi untuk melihat status paket Anda.");

        trackButton.setOnAction(e -> {
            String trackingNum = trackingNumberField.getText().trim().toUpperCase();
            if (trackingNum.isEmpty()) {
                trackingResultArea.setText("Nomor resi tidak boleh kosong.");
            } else {
                List<TrackingEvent> events = trackingData.get(trackingNum);
                if (events != null && !events.isEmpty()) {
                    StringBuilder result = new StringBuilder();
                    result.append("Nomor Resi: ").append(trackingNum).append("\n\n");
                    for (TrackingEvent event : events) {
                        result.append(event.toString()).append("\n");
                    }
                    trackingResultArea.setText(result.toString());
                } else {
                    trackingResultArea.setText("Nomor Resi: " + trackingNum + "\nStatus: Tidak Ditemukan. Mohon periksa kembali nomor resi Anda.");
                }
            }
        });

        Button backButton = new Button("Kembali ke Pilihan Layanan");
        backButton.setPrefWidth(250);
        backButton.setPrefHeight(40);
        backButton.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        backButton.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; -fx-background-radius: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(serviceSelectionScene));

        trackingForm.getChildren().addAll(trackingNumberLabel, trackingNumberField, trackButton, trackingResultArea);

        mainLayout.getChildren().addAll(headerTitle, trackingForm, backButton);

        // >>> PERUBAHAN DI SINI <<<
        trackingScene = new Scene(mainLayout, 450, 680); // Ukuran awal scene ini
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}