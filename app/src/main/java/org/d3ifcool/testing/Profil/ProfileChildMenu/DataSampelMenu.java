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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import org.d3ifcool.testing.Adapter.AdapterCheckSampel;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataCheckHarian;
import org.d3ifcool.testing.Datas.DataCheckSampel;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanHarian.Common;
import org.d3ifcool.testing.PengecekanHarian.EditCheckHarian;
import org.d3ifcool.testing.PengecekanSampel.EditCheckSampel;

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

public class DataSampelMenu extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private DataService service;

    Spinner spinnerPilihKandang, spinnerPilihPeriode;
    ArrayList<DataCheckSampel> dataArrayList;

    RelativeLayout btnCetakPDFSampel;


    TextView txtidkandang;
    TextView txtperiode;
    TextView txtkodekandang;
    TextView txtkodeblok;



    private RecyclerView rvData;
    private AdapterCheckSampel adapter;
    List<DataCheckSampel> dataCheckHarians = new ArrayList<>();

    public DataSampelMenu() {
        // Required empty public constructor
    }


//    public static DataSampelMenu newInstance(String param1, String param2) {
//        DataSampelMenu fragment = new DataSampelMenu();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_data_sampel_menu, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarDataSampel);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        spinnerPilihKandang = view.findViewById(R.id.spinner_kandang_sampel);
        spinnerPilihPeriode = view.findViewById(R.id.spinner_periode_sampel);

        SpinnerPilihKandang();


        btnCetakPDFSampel = view.findViewById(R.id.btn_cetak_pdf);



        txtperiode = view.findViewById(R.id.periode_getsampel);


        rvData = view.findViewById(R.id.rvCheckSampel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);

        btnCetakPDFSampel = view.findViewById(R.id.btn_cetak_pdfSampel);

        return view;
    }

    private String TanggalDanWaktu(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




    public void SpinnerPilihKandang() {
        Call<BaseResponse.BaseResponseApi<List<Data>>> kandangCalls = service.apiRead(MainActivity.prefConfig.readId());
        kandangCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<Data>>> response) {
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

//                            txtidkandang.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
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
                            LoadDataSampel(
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
                    btnCetakPDFSampel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Maaf, Data Sampel Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataChickin>>> call, Throwable t){

            }
        });

    }

    public void LoadDataSampel(String idkandang,
                               String periode ,
                               String kode_kandang,
                               String kode_blok,
                               String jumlah_doc,
                               String jenis_doc,
                               String kondisi,
                               String tanggal_chickin){




        Call<BaseResponse.BaseResponseApi<List<DataCheckSampel>>> call = service.apiGetCheckSampel(idkandang,periode);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataCheckSampel>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataCheckSampel>>> call, Response<BaseResponse.BaseResponseApi<List<DataCheckSampel>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());
                    btnCetakPDFSampel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions((Activity) getContext(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                            }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                if (new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf").exists()){
                                    new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf").delete();
                                }

                                try {

                                    Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                    PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf"));
                                    document.open();
                                    document.addAuthor("PT.Multi Data Sinergi");
                                    document.addCreator("Ayamqu.id");
                                    document.addCreationDate();

                                    Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                    Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                    Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                    Chunk title = new Chunk("Recording Sampel Ayam Pedaging" , fontbigBold);
                                    Paragraph paragraphTitle = new Paragraph(title);
                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                    paragraphTitle.setSpacingAfter(20);
                                    document.add(paragraphTitle);


                                    //Tabel Konten detail

                                    float columnwidth[] = {80,80,80,80};

                                    PdfPTable table3 = new PdfPTable(columnwidth);
                                    PdfPCell cell1;


                                    table3.setWidthPercentage(100);
                                    cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                    cell1.setRowspan(1);
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    cell1.setColspan(8);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kode_kandang));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase(": "+kode_blok));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+tanggal_chickin));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Periode"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+periode));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jumlah_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jenis DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jenis_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kondisi));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    table3.addCell(cell1);


                                    document.add(table3);




                                    Chunk title2 = new Chunk("\n" , fontbigBold);
                                    Paragraph paragraphTitle2 = new Paragraph(title2);
                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                    document.add(paragraphTitle2);


                                    //Tabel Konten
                                    PdfPTable table4 = new PdfPTable(8);
                                    table4.setWidthPercentage(100);
                                    table4.setWidths(new float[]{(float) 0.5,1,1,1,1,1,1,1});
                                    PdfPCell cell;


                                    cell = new PdfPCell(new Phrase("Usia",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Tanggal",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Total Ayam Sekarang",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Kondisi Ayam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);



                                    cell = new PdfPCell(new Phrase("Jenis Sampel Ayam",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Jumlah Sampel",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Bobot Sampel (gr)",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Bobot Rata-Rata Sampel (Kg)",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    table4.addCell(cell);


                                    for (int i = 0; i <dataArrayList.size() ; i++) {

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getUmur_ayam(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getTanggal_waktu_sampel(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getTotal_ayam_saat_ini(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getKondisi_ayam(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJenis_ayam_sampel(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_ayam_sampel(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getBobot_ayam_sampel(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getBobot_rata_rata(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        table4.addCell(cell);

                                        Log.d("coba", "createPDF: " + dataArrayList.get(i).getId_kandang());
                                    }





                                    document.add(table4);
                                    document.close();

                                    Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf"));
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                    adapter = new AdapterCheckSampel(getContext(), dataArrayList, new AdapterCheckSampel.CustomItemClickListener() {
                        @Override
                        public void onItemClick(DataCheckSampel user, int position) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    EditCheckSampel.newInstance(
                                            user.getId(),
                                            user.getId_kandang(),
                                            user.getUmur_ayam(),
                                            user.getJenis_ayam_sampel(),
                                            user.getTotal_ayam_saat_ini(),
                                            user.getKondisi_ayam(),
                                            user.getJumlah_ayam_sampel(),
                                            user.getBobot_ayam_sampel(),
                                            user.getBobot_rata_rata())).addToBackStack(null).commit();
                            Log.d("tessampel", "onItemClick: " + user.getId() + user.getUmur_ayam() + user.getKondisi_ayam());
                            Toast.makeText(getContext(), "" + user.getId(), Toast.LENGTH_SHORT).show();

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
                    btnCetakPDFSampel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Maaf, Data Sampel Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataCheckSampel>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
}













//                    btnCetakPDFSampel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                                ActivityCompat.requestPermissions((Activity) getContext(),
//                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
//                            }else {
////                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
//                                if (new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf").exists()){
//                                    new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf").delete();
//                                }
//
//                                try {
//
//                                    Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
//                                    PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf"));
//                                    document.open();
//                                    document.addAuthor("PT.Multi Data Sinergi");
//                                    document.addCreator("Ayamqu.id");
//                                    document.addCreationDate();
//
//                                    Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
//                                    Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
//                                    Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);
//
//                                    Chunk title = new Chunk("Recording Samepel Ayam Pedaging" , fontbigBold);
//                                    Paragraph paragraphTitle = new Paragraph(title);
//                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//                                    paragraphTitle.setSpacingAfter(20);
//                                    document.add(paragraphTitle);
//
//
//                                    //Tabel Konten detail
//
//                                    float columnwidth[] = {80,80,80,80};
//
//                                    PdfPTable table5 = new PdfPTable(columnwidth);
//                                    PdfPCell cell1;
//                                    table5.setWidthPercentage(100);
//                                    cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
//                                    cell1.setRowspan(1);
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    cell1.setColspan(8);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("Kode Kandang"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+kode_kandang));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//
//                                    cell1 = new PdfPCell(new Phrase("Kode Blok"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//
//                                    cell1 = new PdfPCell(new Phrase(": "+kode_blok));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("PPL Kandang"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//
//                                    cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+tanggal_chickin));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("Periode"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+periode));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+jumlah_doc));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("Jenis DOC"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+jenis_doc));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//                                    cell1 = new PdfPCell(new Phrase(": "+kondisi));
//                                    cell1.setBorder(Rectangle.NO_BORDER);
//                                    table5.addCell(cell1);
//
//
//                                    document.add(table5);
//
//
//
//
//                                    Chunk title2 = new Chunk("\n" , fontbigBold);
//                                    Paragraph paragraphTitle2 = new Paragraph(title2);
//                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//                                    document.add(paragraphTitle2);
//
//
//                                    //Tabel Konten
//                                    PdfPTable table6 = new PdfPTable(8);
//                                    table6.setWidthPercentage(100);
//                                    table6.setWidths(new float[]{(float) 0.5,1,1,1,1,1,1,1});
//                                    PdfPCell cell;
//
//                                    cell = new PdfPCell(new Phrase("Usia",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Tanggal",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Total Ayam Sekarang",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Kondisi Ayam",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//
//
//                                    cell = new PdfPCell(new Phrase("Jenis Sampel Ayam",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Jumlah Sampel",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Bobot Sampel",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
//                                    cell = new PdfPCell(new Phrase("Bobot Rata-Rata Sampel",bold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setRowspan(2);
//                                    table6.addCell(cell);
//
////
//                                    cell = new PdfPCell(new Phrase("Data Sampel Tidak Ditemukan",fontbigBold));
//                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                                    cell.setPadding(20);
//                                    cell.setColspan(17);
//                                    table6.addCell(cell);
//
//
//                                    document.add(table6);
//                                    document.close();
//
//                                    Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Sampel"+TanggalDanWaktu()+idkandang+".pdf"));
//                                } catch (DocumentException e) {
//                                    e.printStackTrace();
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//                    });