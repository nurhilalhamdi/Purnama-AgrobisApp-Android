package org.d3ifcool.testing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.button.MaterialButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Adapter.AdapterCheckStok;
import org.d3ifcool.testing.Chickin.ChickinFragment;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Datas.DataPanen;
import org.d3ifcool.testing.Inventory.InventoryFragment;
import org.d3ifcool.testing.KesiapanKandang.CekKesiapanKandangTerbukaFragment;
import org.d3ifcool.testing.KesiapanKandang.CekKesiapanKandangTertutupFragment;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PanenPenimbangan.PanenPenimbanganFragment;
import org.d3ifcool.testing.PengecekanHarian.CekHarianFragment;
import org.d3ifcool.testing.PengecekanHarian.Common;
import org.d3ifcool.testing.PerformaKandang.PerformaKandangFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PengecekanFragment extends Fragment {
    private static final String SYARAT_1 = "syarat1" ;
    LinearLayout menuPersiapanKandang;
    LinearLayout menuAyamMasuk;
    LinearLayout menuCekInventory;
    LinearLayout menuCekHarian;
    LinearLayout menuCekSampel;
    LinearLayout menuPanenPenimbangan;
    LinearLayout menuPerformaKandang;
    ImageView avatarImage;
    CardView cardViewPanen;

    MaterialButton materialButtonAkhiriPeriode;
    TextView titlePeriode;

    TextView totalpakan;
    TextView totalobat;
    TextView totalvitamin;
    TextView totalvaksin;

    Chronometer chronometer;
    SessionManager sessionManager;
    SimpleDateFormat format;
    String currentTime;
    SwitchCompat switchCompatMulaiProduksi;

    private ShimmerFrameLayout shimmerAlamatKandang;
    private ShimmerFrameLayout shimmerJenisKandang;
    private ShimmerFrameLayout shimmerKodeKandang;
    private ShimmerFrameLayout shimmerKodeBlok;
    private ShimmerFrameLayout shimmerStatusKandang;
    private ShimmerFrameLayout shimmerProgressKandang;
    private ShimmerFrameLayout getShimmerProgressKandangPercentage;
    private ShimmerFrameLayout getShimmerProgressBarKandang;
    private ShimmerFrameLayout shimmerIdkandang;
    private ShimmerFrameLayout shimmerPopulasi;
    private ShimmerFrameLayout shimmerPeriode;
    private ShimmerFrameLayout shimmerTglChickin;
    private ShimmerFrameLayout shimmerProgressPanen;
    private LinearLayout notShimmer;

    Button cek;
    TextView ambiltanggal;
    TextView cekhitung;
    TextView percentage;


    TextView txtPopulasi;
    TextView txtPopulasi_sekarang;
    TextView txtPeriode;
    TextView txttitlePeriode;
    TextView txtTglchickin;
    TextView txtBobot;
    TextView idchickin;
    TextView type;
    TextView kondisi;
    TextView harga;
    TextView jenis;
    TextView txttotal_panen;
    ProgressBar progressBar;
    ProgressBar progressBarContoh;
    ColorGenerator colorGenerator;


    String formattedDate;

    private DataService service;
    List<Data> data;
    List<Data> dataKandangs = new ArrayList<>();
    List<DataChickin> datas;
    List<DataPanen> dataPanen;
    List<DataInventory.DataPakan> datapakan;
    List<DataInventory.DataVitamin> datavitamin;
    List<DataInventory.DataObat> dataObat;
    List<DataInventory.DataVaksin> dataVaksin;
    List<DataInventory.DataPeralatan> dataPeralatan;
    List<DataInventory.DataItemLain> dataItemLain;

    String idkandang;
    String kodekandang;
    String kodeblok;
    String jeniskandang;
    String alamatkandang;
    String statuskandang;
    String nilaiprogress;
    String syarat1;
    String syarat2;
    String syarat3;
    String syarat4;
    String syarat5;
    String syarat6;
    String syarat7;
    String syarat8;
    String syarat9;
    String syarat10;
    String populasi_sekarang;

    String jumlahpanen;



    TextView txtgetiduserkandang;
    TextView txtgetnamauserkandang;
    TextView txtidKandangIntent;
    TextView txtgetAlamatKandangIntent;
    TextView txtgetJenisKandangIntent;
    TextView txtgetStatusKandangIntent;
    //    TextView txtkodeKandangKodeBlokIntent;
    TextView kodekandangs;
    TextView kodebloks;
    TextView txtProgressHari;

//
//    TextView txtJumlahStokPakan;
//    TextView txtJumlahStokVitamin;
//    TextView txtJumlahStokObat;
//    TextView txtJumlahStokVaksin;
//    TextView txtJumlahStokPeralatan;
//    TextView txtJumlahStokItemLain;



    private Handler handler;
    private Runnable runnable;
    int hariCounter = 0;
    int conter = 0;
    private BroadcastReceiver minuteUpdateReceiver;

    private static final String TAG = "BroadcastTest";
    private Intent intent;

    private static final String ID_KANDANG = "idkandang";
    private static final String KODE_KANDANG = "kodekandang";
    private static final String KODE_BLOK = "kodeblok";
    private static final String JENIS_KANDANG = "jeniskandang";
    private static final String ALAMAT_KANDANG = "alamatkandang";
    private static final String STATUS_KANDANG = "statuskandang";
    private static final String NILAI_PROGRESS = "nilai_progress";
    float hitungs;



    PieChart pieChart;


    private RadioButton radioButton1;
    private RadioButton radioButton2;
    Button btncount;
    OnPengecekanListener pengecekanListener;
    public interface OnPengecekanListener{
        void cekharianPerformed(String id, String nama);
        void ceksampelPerformed(String id, String idkandang, String nama,String kodekandang, String kodeblok, String total_ayam_sekarang,String idchickin);

    }

    public PengecekanFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            idkandang = getArguments().getString(ID_KANDANG);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_pengecekan, container, false);
        menuCekHarian = view.findViewById(R.id.menu_cekharian);
        menuCekSampel = view.findViewById(R.id.menu_ceksampel);
        menuPersiapanKandang = view.findViewById(R.id.menu_persiapan_kandang);
        menuAyamMasuk = view.findViewById(R.id.menu_ayam_masuk);
        menuCekInventory = view.findViewById(R.id.menu_cekinventory);
        menuPanenPenimbangan = view.findViewById(R.id.menu_panen_penimbangan);
        menuPerformaKandang = view.findViewById(R.id.menu_performa_kandang);



        Toolbar toolbar = view.findViewById(R.id.toolBarPengecekan);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        progressBar = view.findViewById(R.id.pbars);

        cekhitung  = view.findViewById(R.id.text_progress);
        percentage  = view.findViewById(R.id.text_progress_percentage);
        avatarImage = view.findViewById(R.id.avatarimageIdKDG);

        txtgetiduserkandang = view.findViewById(R.id.txt_get_id_user_kandang);
        txtgetnamauserkandang = view.findViewById(R.id.txt_get_nama_user_kandang);
        txtidKandangIntent = view.findViewById(R.id.txt_get_id_kandang);
        txtgetAlamatKandangIntent = view.findViewById(R.id.txt_alamat_kandang);
        txtgetJenisKandangIntent = view.findViewById(R.id.txt_jenis_kandang);
        txtgetStatusKandangIntent = view.findViewById(R.id.txt_status_kandang);

        totalpakan = view.findViewById(R.id.totalpakan);
        totalobat = view.findViewById(R.id.totalobat);
        totalvitamin = view.findViewById(R.id.totalvitamin);
        totalvaksin = view.findViewById(R.id.totalvaksin);


        kodekandangs = view.findViewById(R.id.kdkandang);
        kodebloks = view.findViewById(R.id.blkandang);


        txtgetnamauserkandang.setText(MainActivity.prefConfig.readName());
        txtgetiduserkandang.setText(MainActivity.prefConfig.readId());

        txtidKandangIntent.setText(""+idkandang);




        txtPopulasi = view.findViewById(R.id.populasi);
        txtPopulasi_sekarang = view.findViewById(R.id.populasi_sekarang);
        txtPeriode = view.findViewById(R.id.periode);
        txttitlePeriode = view.findViewById(R.id.text_title_periode);
        txtTglchickin = view.findViewById(R.id.txt_tanggal_chickin);
        txtBobot = view.findViewById(R.id.txt_bobot);
        idchickin = view.findViewById(R.id.id_chickin);
        type = view.findViewById(R.id.type);
        kondisi = view.findViewById(R.id.kondisi);
        harga = view.findViewById(R.id.harga);
        jenis = view.findViewById(R.id.jenis);
        txttotal_panen = view.findViewById(R.id.txt_total_panen);

//        cardViewPanen = view.findViewById(R.id.card_panen);
        materialButtonAkhiriPeriode = view.findViewById(R.id.button_akhiriperiode);






//        txtProgressHari = view.findViewById(R.id.progres_hari);



//        switchCompatMulaiProduksi = view.findViewById(R.id.switch_compat);

//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                if ((SystemClock.elapsedRealtime() - ))
//            }
//        });

//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("save"+idkandang, Context.MODE_PRIVATE);
//        switchCompatMulaiProduksi.setChecked(sharedPreferences.getBoolean("value",false));
//        switchCompatMulaiProduksi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (switchCompatMulaiProduksi.isChecked()){
//                    SharedPreferences.Editor editor = getContext().getSharedPreferences("save"+idkandang,Context.MODE_PRIVATE).edit();
//                    editor.putBoolean("value" , true);
//                    editor.apply();
//                    switchCompatMulaiProduksi.setChecked(true);
//
////                    mulai();
//
////                    switchCompatMulaiProduksi.setText("Berhenti Produksi");
//                }else {
//                    SharedPreferences.Editor editor = getContext().getSharedPreferences("save"+idkandang,Context.MODE_PRIVATE).edit();
//                    editor.putBoolean("value",false);
//                    editor.apply();
//                    switchCompatMulaiProduksi.setChecked(false);
//                    chronometer.stop();
////                    switchCompatMulaiProduksi.setText("Mulai Produksi");
//                }
//            }
//        });



//        if(switchCompatMulaiProduksi.isChecked()){
//            mulai();
//            switchCompatMulaiProduksi.setText("Berhenti Produksi");
//        }
//        else {
//            chronometer.stop();
//            switchCompatMulaiProduksi.setText("Mulai Produksi");
//        }

        //
//        SharedPreferences settings = getContext().getSharedPreferences("MyPrefsFile", 0);
//        boolean silent = settings.getBoolean("switchkey", false);
//        switchCompatMulaiProduksi.setChecked(silent);
//        switchCompatMulaiProduksi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
//
//                if(isChecked){
//                    switchCompatMulaiProduksi.setText("Switch is currently ON");
//                }else{
//                    switchCompatMulaiProduksi.setText("Switch is currently OFF");
//                }
//                SharedPreferences settings = getContext().getSharedPreferences("MyPrefsFile", 0);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("switchkey", isChecked);
//                editor.apply();
//            }
//        });
//
//        //check the current state before we display the screen
//        if(switchCompatMulaiProduksi.isChecked()){
//            switchCompatMulaiProduksi.setText("Switch is currently ON");
//        }
//        else {
//            switchCompatMulaiProduksi.setText("Switch is currently OFF");
//        }


//
//        txtProgressHari = view.findViewById(R.id.progres_hari);
//        countDownStart();


//        txtJumlahStokPakan = view.findViewById(R.id.txt_jumlah_stok_pakan);
//        txtJumlahStokVitamin = view.findViewById(R.id.txt_jumlah_stok_vitamin);
//        txtJumlahStokObat = view.findViewById(R.id.txt_jumlah_stok_obat);
//        txtJumlahStokVaksin = view.findViewById(R.id.txt_jumlah_stok_vaksin);
//        txtJumlahStokPeralatan = view.findViewById(R.id.txt_jumlah_stok_peralatan);
//        txtJumlahStokItemLain = view.findViewById(R.id.txt_jumlah_stok_itemlain);



        Log.d("periode", "onCreateView: " + txtPeriode.getText().toString());

        menuCekHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                }else if (txtPopulasi.getText().equals("0")){
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Chick-In", Toast.LENGTH_SHORT).show();
                } else {
//                pengecekanListener.cekharianPerformed(txtgetiduserkandang.getText().toString(),txtgetnamauserkandang.getText().toString());
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                    assert appCompatActivity != null;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, CekHarianFragment.newInstance(idkandang, alamatkandang, kodekandang, kodeblok, idchickin.getText().toString(), txtgetiduserkandang.getText().toString(), txtgetnamauserkandang.getText().toString())).addToBackStack(null).commit();
                }
            }
        });

        menuCekSampel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                }else if (txtPopulasi.getText().equals("0")){
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Chick-In", Toast.LENGTH_SHORT).show();
                } else {
                    pengecekanListener.ceksampelPerformed(txtgetiduserkandang.getText().toString(), idkandang, txtgetnamauserkandang.getText().toString(), kodekandangs.getText().toString(), kodebloks.getText().toString(), txtPopulasi_sekarang.getText().toString(), idchickin.getText().toString());
                }
            }
        });




            menuAyamMasuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                        Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                    }else if (totalpakan.getText().equals("0") && totalobat.getText().equals("0") && totalvitamin.getText().equals("0") && totalvaksin.getText().equals("0")){
                        Toast.makeText(getContext(), "Mohon Sediakan Stok Pakan, Obat, Vitamin, Dan Vaksin Sebelum Melakukan Chickin", Toast.LENGTH_SHORT).show();
                    }else if (totalpakan.getText().equals("0")){
                        Toast.makeText(getContext(), "Stok Pakan Belum Ada, Belum Dapat Melakukan Chickin", Toast.LENGTH_SHORT).show();
                    }else if (totalobat.getText().equals("0")){
                        Toast.makeText(getContext(), "Stok Obat Belum Ada, Belum Dapat Melakukan Chickin", Toast.LENGTH_SHORT).show();
                    }else if (totalvitamin.getText().equals("0")){
                        Toast.makeText(getContext(), "Stok Vitamin Belum Ada, Belum Dapat Melakukan Chickin", Toast.LENGTH_SHORT).show();
                    }else if (totalvaksin.getText().equals("0")){
                        Toast.makeText(getContext(), "Stok Vaksin Belum Ada, Belum Dapat Melakukan Chickin", Toast.LENGTH_SHORT).show();
                    }else {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                        assert appCompatActivity != null;
                        appCompatActivity.getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_container, ChickinFragment.newInstance(idchickin.getText().toString(), type.getText().toString(), txtPopulasi.getText().toString(), kondisi.getText().toString(), harga.getText().toString(), jenis.getText().toString(), txtTglchickin.getText().toString(), idkandang, txtPeriode.getText().toString(),txtBobot.getText().toString())).addToBackStack(null).commit();
                    }
                }
            });






        menuCekInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                } else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                    assert appCompatActivity != null;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, InventoryFragment.newInstance(idkandang)).addToBackStack(null).commit();
                }
            }
        });

        menuPanenPenimbangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                }else if (txtPopulasi.getText().equals("0")){
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Chick-In", Toast.LENGTH_SHORT).show();
                }else if (!txttotal_panen.getText().equals("0")){
                    Toast.makeText(getContext(), "Anda Telah Melakukan Panen", Toast.LENGTH_SHORT).show();
                }
                else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                    assert appCompatActivity != null;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,  PanenPenimbanganFragment.newInstance(txtgetiduserkandang.getText().toString(),idkandang,idchickin.getText().toString(),kodekandang)).addToBackStack(null).commit();
                }
            }
        });

        menuPerformaKandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")) {
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Persiapan Kandang Dan Aktifkan Kandang", Toast.LENGTH_SHORT).show();
                }else if (txtPopulasi.getText().equals("0")){
                    Toast.makeText(getContext(), "Mohon Isi Terlebih Dahulu Form Chick-In", Toast.LENGTH_SHORT).show();
                }else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                    assert appCompatActivity != null;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,  PerformaKandangFragment.newInstance(kodekandangs.getText().toString(),txtgetAlamatKandangIntent.getText().toString(),idkandang)).addToBackStack(null).commit();
                }
            }
        });



        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);



        loadData();
        Pakanset();
        Obatset();
        Vitaminset();
        Vaksinset();

        

        shimmerJenisKandang = view.findViewById(R.id.shimmerJenisKandang);
        shimmerAlamatKandang = view.findViewById(R.id.shimmerAlamatKandang);
        shimmerKodeBlok = view.findViewById(R.id.shimmerKodeBlok);
        shimmerKodeKandang = view.findViewById(R.id.shimmerKodeKandang);
        shimmerStatusKandang = view.findViewById(R.id.shimmerStatusKandang);
        shimmerProgressKandang = view.findViewById(R.id.shimmerProgressKandang);
        getShimmerProgressBarKandang = view.findViewById(R.id.shimmerProgressBarKandang);
        getShimmerProgressKandangPercentage = view.findViewById(R.id.shimmerProgressKandangPercentage);
        shimmerIdkandang = view.findViewById(R.id.shimmerIDKandang);
        shimmerPeriode = view.findViewById(R.id.shimmerPeriode);
        shimmerPopulasi = view.findViewById(R.id.shimmerPopulasi);
        shimmerTglChickin = view.findViewById(R.id.shimmerTglChickIn);
        shimmerProgressPanen = view.findViewById(R.id.shimmerProgressPanen);

        Log.d("cek", "onResponse: " + kodeblok);


        String id = txtidKandangIntent.getText().toString();
        colorGenerator = ColorGenerator.MATERIAL;
        String letter = String.valueOf(String.valueOf(id.substring(id.length()-2)));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter,colorGenerator.getRandomColor());
        avatarImage.setImageDrawable(drawable);




//        pieChart = view.findViewById(R.id.piechart);
//        setupPieChart();
//        loadChart();





//        loadhari();


//        radioButton1 = view.findViewById(R.id.radic_cek1);
//        radioButton2 = view.findViewById(R.id.radic_cek2);

//        radioButton1.setChecked(update("JP_ONE",idkandang));
//        radioButton2.setChecked(update("JP_TWO",idkandang));

//        btncount = view.findViewById(R.id.btncounter);
//        btncount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hariCounter++;
//                txtProgressHari.setText("Hari Ke-"+hariCounter);
//            }
//        });

//        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                saveShared("JP_ONE",b,idkandang);
//            }
//        });
//
//        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                saveShared("JP_TWO",b,idkandang);
//            }
//        });


        return view;
    }







//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void mulai(){
//        sessionManager = new SessionManager(getContext());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            format = new SimpleDateFormat("hh:mm:ss aa");
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            currentTime = format.format(new Date());
//        }
//        boolean flag = sessionManager.getFlag(idkandang);
//        if (!flag){
//            sessionManager.setCurrentTime(currentTime,idkandang);
//            sessionManager.setFlag(true,idkandang);
//            chronometer.start();
//        }else {
//            String sessionManagerCurrentTime = sessionManager.getCurrentTime(idkandang);
//            try {
//                Date date1 = format.parse(sessionManagerCurrentTime);
//                Date date2 = format.parse(currentTime);
//                long mils = date2.getTime() - date1.getTime();
//                chronometer.setBase(SystemClock.elapsedRealtime() - mils);
//                chronometer.start();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void savehari(){
//        SharedPreferences sphari = getContext().getSharedPreferences("idkandang",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sphari.edit();
//        editor.putInt("countervalue", hariCounter);
//        editor.apply();
//    }
//
//    public void loadhari(){
//        SharedPreferences sphari = getContext().getSharedPreferences("idkandang",Context.MODE_PRIVATE);
//        hariCounter = sphari.getInt("countervalue",Context.MODE_PRIVATE);
//        txtProgressHari.setText("Hari Ke-"+hariCounter);
//    }

    @Override
    public void onPause() {
        super.onPause();
//        savehari();
    }

    private void saveShared(String key, boolean value, String idkdg){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(idkdg,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean update(String key, String idkdg){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(idkdg,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        countDayRunnable.run();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        cekhitung.setText(nilaiprogress);
//            float hitung = sharedPreferences.getFloat("hitung", 0);
//        countDayRunnable.run();
//        }
//    }




//
//    private Runnable countDayRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hariCounter++;
//            txtProgressHari.setText(""+hariCounter);
//            handler.postDelayed(this::run,5000);
//        }
//    };


    private void loadData(){
        Call<BaseResponse.BaseResponseApi<List<Data>>> call = service.apiReadWhereId(idkandang);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<Data>>> response) {
                if (response.code() == 200) {
                    data = response.body().getData();
                    dataKandangs.addAll(data);
                    for (Data data1 : data){
                        Log.d("cek", "onResponse: " + data1.getKodekandang());
                        kodekandang = data1.getKodekandang();
                        kodeblok = data1.getKodeblok();
                        alamatkandang = data1.getAlamat_kandang();
                        jeniskandang = data1.getJenis_kandang();
                        statuskandang = data1.getStatus_kandang();
                        syarat1 = data1.getSyarat_kesiapan_1();
                        syarat2 = data1.getSyarat_kesiapan_2();
                        syarat3 = data1.getSyarat_kesiapan_3();
                        syarat4 = data1.getSyarat_kesiapan_4();
                        syarat5 = data1.getSyarat_kesiapan_5();
                        syarat6 = data1.getSyarat_kesiapan_6();
                        syarat7 = data1.getSyarat_kesiapan_7();
                        syarat8 = data1.getSyarat_kesiapan_8();
                        syarat9 = data1.getSyarat_kesiapan_9();
                        syarat10 = data1.getSyarat_kesiapan_10();
                        hitungs = Float.parseFloat(data1.getNilai_progress());
//                        populasi_sekarang = data1.getTotal_ayam_saat_ini();


                        kodekandangs.setText(kodekandang);
                        kodebloks.setText(kodeblok);
                        txtgetAlamatKandangIntent.setText(alamatkandang);
                        txtgetJenisKandangIntent.setText(jeniskandang);
                        txtgetStatusKandangIntent.setText(statuskandang);
//                        txtPopulasi_sekarang.setText(populasi_sekarang);
                        Log.d("populasisekarang", "onResponse: " + txtPopulasi_sekarang.getText().toString());

                        progressBar.setProgress((int) hitungs);
                        progressBar.setMax(100);
                        if (hitungs >= 90 && hitungs <= 100){
                            cekhitung.setText("Sangat Bagus");
                            percentage.setText(String.format("%.0f", hitungs) +"%");

                        }else if (hitungs >= 80 && hitungs <= 89){
                            cekhitung.setText("Cukup Bagus");
                            percentage.setText(String.format("%.0f", hitungs) +"%");

                        } else if (hitungs >= 60 && hitungs <= 79){
                            cekhitung.setText("Bagus");
                            percentage.setText(String.format("%.0f", hitungs) +"%");
//                            progressBar.getProgressDrawable().setColorFilter(
//                                    Color.parseColor("#99cc33"), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        else if (hitungs >= 40 && hitungs <= 59){
                            cekhitung.setText("Kurang Bagus");
                            percentage.setText(String.format("%.0f", hitungs) +"%");
//                            progressBar.getProgressDrawable().setColorFilter(
//                                    Color.parseColor("#ffcc00"), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        else {
                            cekhitung.setText("Tidak Bagus");
                            percentage.setText(String.format("%.0f", hitungs) +"%");
//                            progressBar.getProgressDrawable().setColorFilter(
//                                    Color.parseColor("#ee2737"), android.graphics.PorterDuff.Mode.SRC_IN);
                        }





                        menuPersiapanKandang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (txtgetJenisKandangIntent.getText().equals("Kandang Terbuka (Open House)")){
                                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                                    assert appCompatActivity != null;
                                    appCompatActivity.getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container,CekKesiapanKandangTerbukaFragment.
                                                    newInstance(idkandang,statuskandang,syarat1,syarat2,syarat3,syarat4,syarat5,syarat6,syarat7,syarat8,syarat9,syarat10)).addToBackStack(null).commit();
                                }else {
                                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                                    assert appCompatActivity != null;
                                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  CekKesiapanKandangTertutupFragment.newInstance(idkandang,statuskandang,syarat1,syarat2,syarat3,syarat4,syarat5,syarat6,syarat7,syarat8,syarat9,syarat10)).addToBackStack(null).commit();
                                }

                            }
                        });




                        if (txtgetStatusKandangIntent.getText().equals("Tidak Aktif")){
                            txtgetStatusKandangIntent.setBackgroundResource(R.drawable.bg_decline);
                        }else {
                            txtgetStatusKandangIntent.setBackgroundResource(R.drawable.bg_accept);
                        }

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                shimmerJenisKandang.startShimmer();
                                shimmerAlamatKandang.startShimmer();
                                shimmerKodeBlok.startShimmer();
                                shimmerKodeKandang.startShimmer();
                                shimmerStatusKandang.startShimmer();
                                getShimmerProgressBarKandang.startShimmer();
                                getShimmerProgressKandangPercentage.startShimmer();
                                shimmerProgressKandang.startShimmer();
                                shimmerIdkandang.startShimmer();
                                shimmerPeriode.startShimmer();
                                shimmerProgressPanen.startShimmer();


                                shimmerJenisKandang.setVisibility(View.GONE);
                                shimmerAlamatKandang.setVisibility(View.GONE);
                                shimmerKodeBlok.setVisibility(View.GONE);
                                shimmerKodeKandang.setVisibility(View.GONE);
                                shimmerStatusKandang.setVisibility(View.GONE);
                                getShimmerProgressBarKandang.setVisibility(View.GONE);
                                getShimmerProgressKandangPercentage.setVisibility(View.GONE);
                                shimmerProgressKandang.setVisibility(View.GONE);
                                shimmerIdkandang.setVisibility(View.GONE);
                                shimmerPeriode.setVisibility(View.GONE);
                                shimmerProgressPanen.setVisibility(View.GONE);

                                txtgetJenisKandangIntent.setVisibility(View.VISIBLE);
                                txtgetAlamatKandangIntent.setVisibility(View.VISIBLE);
                                kodebloks.setVisibility(View.VISIBLE);
                                kodekandangs.setVisibility(View.VISIBLE);
                                txtgetStatusKandangIntent.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.VISIBLE);
                                percentage.setVisibility(View.VISIBLE);
                                cekhitung.setVisibility(View.VISIBLE);
                                avatarImage.setVisibility(View.VISIBLE);

                                txtPeriode.setVisibility(View.VISIBLE);
                                txttitlePeriode.setVisibility(View.VISIBLE);
                                txttotal_panen.setVisibility(View.VISIBLE);

                            }
                        },1000);


                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t){

            }
        });

        ////Chickin get data API Call
        Call<BaseResponse.BaseResponseApi<List<DataChickin>>> calls = service.apiReadChickinWhereId(idkandang);
        calls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataChickin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataChickin>>> call, Response<BaseResponse.BaseResponseApi<List<DataChickin>>> response) {
                if (response.code() == 200) {
                    datas = response.body().getData();
                    for (DataChickin data2 : datas){
                        Log.d("cek2", "onResponse: " + data2.getPopulasi_masuk());
                        txtPopulasi.setText(data2.getPopulasi_masuk());
                        txtTglchickin.setText(data2.getTanggal_chickin());
                        txtBobot.setText(data2.getBerat_doc());
                        idchickin.setText(data2.getId_chickin());
                        type.setText(data2.getType_produk());
                        harga.setText(data2.getHarga_satuan());
                        jenis.setText(data2.getJenis_produk());
                        kondisi.setText(data2.getKondisi_chick_in());
                        txtPeriode.setText(data2.getPeriode());
                        txtPopulasi_sekarang.setText(data2.getTotal_ayam_saat_ini());
                        loadDataPanen(idkandang,data2.getPeriode(),data2.getId_chickin());

//                        loadDataPanen(idkandang,data2.getPeriode());

                        materialButtonAkhiriPeriode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog();
                        }
                    });

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                shimmerPopulasi.startShimmer();
                                shimmerTglChickin.startShimmer();
                                shimmerPopulasi.setVisibility(View.GONE);
                                shimmerTglChickin.setVisibility(View.GONE);
                                txtTglchickin.setVisibility(View.VISIBLE);
                                txtPopulasi.setVisibility(View.VISIBLE);


                            }
                        },1000);


                    }
                } else if (response.code() == 500) {
                    txtTglchickin.setText("0000-00-00");
                    txtPopulasi.setText("0");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerPopulasi.startShimmer();
                            shimmerTglChickin.startShimmer();
                            shimmerPopulasi.setVisibility(View.GONE);
                            shimmerTglChickin.setVisibility(View.GONE);
                            txtTglchickin.setVisibility(View.VISIBLE);
                            txtPopulasi.setVisibility(View.VISIBLE);


                        }
                    },1000);

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataChickin>>> call, Throwable t){

            }
        });

    }
    
    private void loadDataPanen(String idkandang, String periode,String idchickin){
        Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call = service.apiGetPanen(idkandang,periode,idchickin);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataPanen>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call, Response<BaseResponse.BaseResponseApi<List<DataPanen>>> response) {
                if (response.code() == 200) {
                    dataPanen = response.body().getData();

                    for (DataPanen data1 : dataPanen){

                        jumlahpanen = data1.getJumlah_panen();
                        txttotal_panen.setText(jumlahpanen);
//                        cardViewPanen.setVisibility(View.VISIBLE);

                    }

                    materialButtonAkhiriPeriode.setVisibility(View.VISIBLE);


                } else if (response.code() == 404) {
                    txttotal_panen.setText("0");
                    materialButtonAkhiriPeriode.setVisibility(View.GONE);
//                    cardViewPanen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call, Throwable t){

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set title dialog
        alertDialogBuilder.setTitle("Akhiri Periode " + txtPeriode.getText().toString() + " ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda Yakin Untuk Mengakhiri Periode " + txtPeriode.getText().toString())
                .setIcon(R.drawable.ic_information)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        txtPeriode.setVisibility(View.GONE);
//                        cardViewPanen.setVisibility(View.GONE);
//                        titlePeriode.setText("Periode Berakhir");
//                        txtPopulasi.setText("0");
//                        txttotal_panen.setText("0");
//                        txtTglchickin.setText("0000-00-00");
                        tambahChickin();

                        Toast.makeText(getContext(), "Periode Berakhir", Toast.LENGTH_SHORT).show();
                        loadData();

                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }


    public void tambahChickin() {
//        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        Call<BaseResponse.BaseResponseApi> call = service.tambahChickin(
                "",
                "",
                "",
                "",
                "",
                "000-00-00",
                idkandang,
                "",
                "",
                ""
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Periode Berakhir", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Periode Gagal Berakhir", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });

    }

    public void Pakanset(){
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan(idkandang);
        pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
                if (response.code() == 200) {
                    datapakan = new ArrayList<>(response.body().getData());
                    int total_pakan = 0;
                    for (int i = 0; i < datapakan.size(); i++) {
                        total_pakan += datapakan.get(i).getStok_pakan();
                        totalpakan.setText(total_pakan+"");

                    }

                }else if (response.code() == 404){
                    totalpakan.setText("0");

                }
            }
            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){

            }
        });

    }
    public void Obatset(){
        /////////////// Obat Response
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> obatCalls = service.apigetObat(idkandang);
        obatCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> response) {
                if (response.code() == 200) {
                    dataObat =  new ArrayList<>(response.body().getData());
                    int total_obat = 0;
                    for (int i = 0; i < dataObat.size(); i++) {

                        total_obat += dataObat.get(i).getStok_obat();
                        totalobat.setText(total_obat+"");

                    }

                }else if (response.code() == 404){
                   totalobat.setText("0");

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Throwable t){

            }
        });



    }
    public void Vitaminset(){
        /// Vitamin Response
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> vitaminCalls = service.apigetVitamin(idkandang);
        vitaminCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> response) {
                if (response.code() == 200) {
                    datavitamin = new ArrayList<>(response.body().getData());
                    int total_vitamin = 0;
                    for (int i = 0; i < datavitamin.size(); i++) {

                        total_vitamin += datavitamin.get(i).getStok_vitamin();
                        totalvitamin.setText(total_vitamin+"");

                    }

                }else if (response.code() == 404){
              totalvitamin.setText("0");

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Throwable t){

            }
        });

    }
    public void Vaksinset(){
        /// Vaksin Response
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> vaksinCalls = service.apigetVaksin(idkandang);
        vaksinCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> response) {
                if (response.code() == 200) {
                    dataVaksin = new ArrayList<>(response.body().getData());
                    int total_vaksin= 0;
                    for (int i = 0; i < dataVaksin.size(); i++) {
                        total_vaksin += dataVaksin.get(i).getStok_vaksin();
                        totalvaksin.setText(total_vaksin+"");

                    }

                }else if (response.code() == 404){
                   totalvaksin.setText("0");

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Throwable t){

            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        pengecekanListener = (OnPengecekanListener) activity;
    }

    public static PengecekanFragment newInstance(
            String idkandang) {

        Bundle args = new Bundle();
        PengecekanFragment fragment = new PengecekanFragment();
        args.putString(ID_KANDANG,idkandang);
        fragment.setArguments(args);
        return fragment;
    }
}