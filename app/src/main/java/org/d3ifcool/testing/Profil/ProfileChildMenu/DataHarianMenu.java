package org.d3ifcool.testing.Profil.ProfileChildMenu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import org.d3ifcool.testing.Adapter.AdapterCheckHarian;
import org.d3ifcool.testing.Adapter.UploadAdapter;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataCheckHarian;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanFragment;
import org.d3ifcool.testing.PengecekanHarian.Common;
import org.d3ifcool.testing.PengecekanHarian.EditCheckHarian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataHarianMenu extends Fragment {

    Spinner spinnerPilihKandang, spinnerPilihPeriode;
    LinearLayout linearLayoutSpinnerPeriode;

    private DataService service;

    TextView txtidkandang;
    TextView txtperiode;
    TextView txtkodekandang;
    TextView txtkodeblok;



    private RecyclerView rvData;
    private AdapterCheckHarian adapter;
    ArrayList<DataCheckHarian> dataArrayList;
    ArrayList<DataInventory.DataObat> dataObat;
    ArrayList<DataInventory.DataObat> dataObatS = new ArrayList<>();
    List<DataCheckHarian> dataCheckHarians = new ArrayList<>();

    RelativeLayout btnCetakPDF;


    public DataHarianMenu() {
        // Required empty public constructor
    }
//    public static DataHarianMenu newInstance(String param1, String param2) {
//        DataHarianMenu fragment = new DataHarianMenu();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_data_harian_menu, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolBarDataHarian);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        spinnerPilihKandang = view.findViewById(R.id.spinner_kandang);
        spinnerPilihPeriode = view.findViewById(R.id.spinner_periode);

        linearLayoutSpinnerPeriode = view.findViewById(R.id.linearSpinnerPeriode);

        txtidkandang = view.findViewById(R.id.idkandang_getharian);
        txtperiode = view.findViewById(R.id.periode_getharian);

        SpinnerPilihKandang();

        rvData = view.findViewById(R.id.rvCheckHarian);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);

        btnCetakPDF = view.findViewById(R.id.btn_cetak_pdf);



        txtkodekandang = view.findViewById(R.id.kode_kandang);
        txtkodeblok = view.findViewById(R.id.kode_blok);

        txtkodekandang.setText(MainActivity.prefConfig.readName());
        txtkodeblok.setText(MainActivity.prefConfig.readId());




        return view;
    }

    private String TanggalDanWaktu(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


//    public void SpinnerPilihKandang(){
//        spinnerArrayAdapterPilihKandang = new ArrayAdapter<String>(
//                getContext(), R.layout.dropdown_item,pilihKandangStringArray);
//        spinnerArrayAdapterPilihKandang.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        spinnerPilihKandang.setAdapter(spinnerArrayAdapterPilihKandang);
//        spinnerPilihKandang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if (i == 0){
//                    linearLayoutSpinnerPeriode.setVisibility(View.GONE);
//                }else{
//                    linearLayoutSpinnerPeriode.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }


    public void SpinnerPilihKandang() {
        Call<BaseResponse.BaseResponseApi<List<Data>>> kandangCalls = service.apiRead(MainActivity.prefConfig.readId());
        kandangCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> response) {
                if (response.code() == 200) {
                    List<org.d3ifcool.testing.Datas.Data> dataKandang = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < dataKandang.size(); i++) {

                        listSpinner.add("Kode Kandang " + dataKandang.get(i).getKodekandang());
                    }
                    ArrayAdapter<String> spinnerArrayAdapterPilihKandang = new ArrayAdapter<String>(
                            getContext(), R.layout.dropdown_item, listSpinner);
                    spinnerArrayAdapterPilihKandang.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerPilihKandang.setAdapter(spinnerArrayAdapterPilihKandang);
                    spinnerPilihKandang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "" + dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang(), Toast.LENGTH_SHORT).show();

                            txtidkandang.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            SpinnerPilihPeriode(
                                    dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang(),
                                    dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang(),
                                    dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok());
                            //                            if (i == 0) {
//                                linearLayoutSpinnerPeriode.setVisibility(View.GONE);
//                            } else {
//                                linearLayoutSpinnerPeriode.setVisibility(View.VISIBLE);
//                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t) {
                String[] message = new String[]{"Kandang Belum Ditambahkan"};
                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, message);
                spinnerPilihKandang.setAdapter(arrayAdapterType);
            }
        });
    }

    public void SpinnerPilihPeriode(String idkandang,
                                    String kodekandang,
                                    String kodeblok) {
        Call<BaseResponse.BaseResponseApi<List<DataChickin>>> calls = service.apiReadChickinWhereId(idkandang);
        calls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataChickin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataChickin>>> call, Response<BaseResponse.BaseResponseApi<List<DataChickin>>> response) {
                if (response.code() == 200) {
                    List<DataChickin> datas = response.body().getData();
                    List<String> listSpinnerChickin = new ArrayList<String>();


                    for (int i = 0; i < datas.size(); i++) {

                        listSpinnerChickin.add("Periode " + datas.get(i).getPeriode());
                    }
                    ArrayAdapter<String> spinnerArrayAdapterPilihPeriode = new ArrayAdapter<String>(
                            getContext(), R.layout.dropdown_item, listSpinnerChickin);
                    spinnerArrayAdapterPilihPeriode.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerPilihPeriode.setAdapter(spinnerArrayAdapterPilihPeriode);
                    spinnerPilihPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "" + datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPeriode(), Toast.LENGTH_SHORT).show();
                            txtperiode.setText(datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPeriode());
                            LoadDataharian(
                                    idkandang,
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPeriode(),
                                    kodekandang,
                                    kodeblok,
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPopulasi_masuk(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getType_produk(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getKondisi_chick_in(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getTanggal_chickin()
                            );

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    }else if (response.code() == 500) {

                    rvData.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    rvData.setAdapter(adapter);
                    btnCetakPDF.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Maaf, Data Harian Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataChickin>>> call, Throwable t){

            }
        });

    }

    public void LoadDataharian(String idkandang,
                               String periode ,
                               String kode_kandang,
                               String kode_blok,
                               String jumlah_doc,
                               String jenis_doc,
                               String kondisi,
                               String tanggal_chickin){




        Call<BaseResponse.BaseResponseApi<List<DataCheckHarian>>> call = service.apiGetCheckHarian(idkandang,periode);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataCheckHarian>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataCheckHarian>>> call, Response<BaseResponse.BaseResponseApi<List<DataCheckHarian>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());


                    btnCetakPDF.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                            }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                if (new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf").exists()){
                                    new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf").delete();
                                }

                                try {

                                    Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                    PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf"));
                                    document.open();
                                    document.addAuthor("PT.Multi Data Sinergi");
                                    document.addCreator("Ayamqu.id");
                                    document.addCreationDate();

                                    Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                    Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                    Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                    Chunk title = new Chunk("Recording Ayam Pedaging" , fontbigBold);
                                    Paragraph paragraphTitle = new Paragraph(title);
                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                    paragraphTitle.setSpacingAfter(20);
                                    document.add(paragraphTitle);


                                    //Tabel Konten detail

                                    float columnwidth[] = {80,80,80,80};

                                    PdfPTable table1 = new PdfPTable(columnwidth);
                                    PdfPCell cell1;


                                    table1.setWidthPercentage(100);
                                    cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                    cell1.setRowspan(1);
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    cell1.setColspan(8);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kode_kandang));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase(": "+kode_blok));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+tanggal_chickin));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Periode"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+periode));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jumlah_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jenis DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jenis_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kondisi));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table1.addCell(cell1);


                                    document.add(table1);




                                    Chunk title2 = new Chunk("\n" , fontbigBold);
                                    Paragraph paragraphTitle2 = new Paragraph(title2);
                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                    document.add(paragraphTitle2);


                                    //Tabel Konten
                                    PdfPTable table = new PdfPTable(16);
                                    table.setWidthPercentage(100);
                                    table.setWidths(new float[]{2,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1});
                                    PdfPCell cell;

                                    cell = new PdfPCell(new Phrase("Tanggal",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Usia",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table.addCell(cell);


                                    cell = new PdfPCell(new Phrase("Populasi Ayam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(2);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Perkembangan Ayam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(1);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Konsumsi Pakan",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(4);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Vitamin",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(3);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Obat",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(2);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Vaksin",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(2);
                                    table.addCell(cell);



                                    //Row 3 Baris
                                    cell = new PdfPCell(new Phrase("Mati",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Sakit",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);



                                    //----------------Masa Pertumbuhan

                                    cell = new PdfPCell(new Phrase("Berat(gr)",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    //------------Konsumsi Pakan
                                    cell = new PdfPCell(new Phrase("Jenis",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Kg/Zak",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("gr/Ekor",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);


                                    //----------------Vitamin
                                    cell = new PdfPCell(new Phrase("Jenis",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jumlah",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    //-----------Obat
                                    cell = new PdfPCell(new Phrase("Jenis",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jumlah",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    //-----------Vaksin
                                    cell = new PdfPCell(new Phrase("Jenis",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jumlah",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);





                                    for (int i = 0; i <dataArrayList.size() ; i++) {

//                                        int ayamMati = Integer.parseInt(dataArrayList.get(i).getAyam_mati());
//                                        int ayamHidup = Integer.parseInt(dataArrayList.get(i).getTotal_ayam_saat_ini());
//                                        int hidup = ayamHidup+ayamMati-ayamMati;

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getTanggal_pengecekan(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getUsia_ayam(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getAyam_mati(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);


                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getAyam_sakit(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);


                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getBerat_ayam(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getPakan_nama(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_pakan(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_pakan_ekor(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJam_pemberian_pakan(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getVitamin_nama(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_vitamin(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJam_pemberian_vitamin(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);







                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getObat_nama(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_obat(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getVaksin_nama(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_vaksin(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table.addCell(cell);




                                        Log.d("coba", "createPDF: " + dataArrayList.get(i).getId_kandang());
                                    }

                                    cell = new PdfPCell(new Phrase("Total",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setColspan(2);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Total 1",normal));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Total 2",normal));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    table.addCell(cell);




                                    document.add(table);
                                    document.close();

                                    Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf"));
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

                    adapter = new AdapterCheckHarian(getContext(), dataArrayList, new AdapterCheckHarian.CustomItemClickListener() {
                        @Override
                        public void onItemClick(DataCheckHarian user, int position) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    EditCheckHarian.newInstance(user.getId(),
                                                                user.getUsia_ayam(),
                                                                user.getAyam_mati(),
                                                                user.getAyam_sakit(),
                                                                user.getGejala_sakit(),
                                                                user.getBerat_ayam(),
                                                                user.getId_kandang(),
                                                                user.getPakan_nama(),
                                                                user.getJumlah_pakan(),
                                                                user.getJumlah_pakan_ekor(),
                                                                user.getJam_pemberian_pakan(),
                                                                user.getJam_pemberian_minum(),
                                                                user.getObat_nama(),
                                                                user.getJumlah_obat(),
                                                                user.getVitamin_nama(),
                                                                user.getJumlah_vitamin(),
                                                                user.getJam_pemberian_vitamin(),
                                                                user.getVaksin_nama(),
                                                                user.getJumlah_vaksin())).addToBackStack(null).commit();
//                            Log.d("teskuda", "onItemClick: " + user.getId_vaksin());
//                            Toast.makeText(getContext(), "" + user.getId(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    rvData.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    rvData.setVisibility(View.VISIBLE);
//                    adapter.addAll(response.body().getData());

                } else if (response.code() == 500) {

                    rvData.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    rvData.setAdapter(adapter);


                    btnCetakPDF.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Maaf, Data Harian Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataCheckHarian>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
}

//    public void onClick(View view) {
//        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions((Activity) getContext(),
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
//        }else {
////                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
//            if (new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf").exists()){
//                new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf").delete();
//            }
//
//            try {
//
//                Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
//                PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf"));
//                document.open();
//                document.addAuthor("PT.Multi Data Sinergi");
//                document.addCreator("Ayamqu.id");
//                document.addCreationDate();
//
//                Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
//                Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
//                Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);
//
//                Chunk title = new Chunk("Recording Ayam Pedaging" , fontbigBold);
//                Paragraph paragraphTitle = new Paragraph(title);
//                paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//                paragraphTitle.setSpacingAfter(20);
//                document.add(paragraphTitle);
//
//
//                //Tabel Konten detail
//
//                float columnwidth[] = {80,80,80,80};
//
//                PdfPTable table1 = new PdfPTable(columnwidth);
//                PdfPCell cell1;
//                table1.setWidthPercentage(100);
//                cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
//                cell1.setRowspan(1);
//                cell1.setBorder(Rectangle.NO_BORDER);
//                cell1.setColspan(8);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("Kode Kandang"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+kode_kandang));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//
//                cell1 = new PdfPCell(new Phrase("Kode Blok"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//
//                cell1 = new PdfPCell(new Phrase(": "+kode_blok));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("PPL Kandang"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//
//                cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+tanggal_chickin));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("Periode"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+periode));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+jumlah_doc));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("Jenis DOC"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+jenis_doc));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//                cell1 = new PdfPCell(new Phrase(": "+kondisi));
//                cell1.setBorder(Rectangle.NO_BORDER);
//                table1.addCell(cell1);
//
//
//                document.add(table1);
//
//
//
//
//                Chunk title2 = new Chunk("\n" , fontbigBold);
//                Paragraph paragraphTitle2 = new Paragraph(title2);
//                paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//                document.add(paragraphTitle2);
//
//
//                //Tabel Konten
//                PdfPTable table = new PdfPTable(17);
//                table.setWidthPercentage(100);
//                table.setWidths(new float[]{2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1});
//                PdfPCell cell;
//
//                cell = new PdfPCell(new Phrase("Tanggal",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setRowspan(2);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Usia",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setRowspan(2);
//                table.addCell(cell);
//
//
//                cell = new PdfPCell(new Phrase("Populasi Ayam",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(3);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Perkembangan Ayam",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(1);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Konsumsi Pakan",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(4);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Vitamin",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(3);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Obat",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Vaksin",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//
//
//                //Row 3 Baris
//                cell = new PdfPCell(new Phrase("Mati",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Sakit",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Hidup",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//
//                //----------------Masa Pertumbuhan
//
//                cell = new PdfPCell(new Phrase("Berat(gr)",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                //------------Konsumsi Pakan
//                cell = new PdfPCell(new Phrase("Jenis",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Kg/Zak",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("gr/Ekor",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Jam",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//
//                //----------------Vitamin
//                cell = new PdfPCell(new Phrase("Jenis",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Jumlah",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Jam",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                //-----------Obat
//                cell = new PdfPCell(new Phrase("Jenis",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Jumlah",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                //-----------Vaksin
//                cell = new PdfPCell(new Phrase("Jenis",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Jumlah",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Data Pengcekan Harian Tidak Ditemukan",fontbigBold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setPadding(20);
//                cell.setColspan(17);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Total",bold));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Total 1",normal));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Total 2",normal));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//
//
//
//                document.add(table);
//                document.close();
//
//                Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Harian"+TanggalDanWaktu()+idkandang+".pdf"));
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }