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

import org.d3ifcool.testing.Adapter.AdapterCheckSampel;
import org.d3ifcool.testing.Adapter.AdapterPanen;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataCheckSampel;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataPanen;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanHarian.Common;
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

public class DataPanenMenu extends Fragment {



    private static final String ID_KANDANG = "idkandang";
    private static final String ID_CHIKCIN = "idchickin";


    String getIdKandang;
    String getIdChikcin;
    Spinner spinnerPilihKandang, spinnerPilihPeriode;
    ArrayList<DataPanen> dataArrayList;

    RelativeLayout btnCetakPDFPanen;
    private DataService service;

    private RecyclerView rvData;
    private AdapterPanen adapter;

    public DataPanenMenu() {
        // Required empty public constructor
    }


    public static DataPanenMenu newInstance(
            String idkandang,
            String idchickin) {
        DataPanenMenu fragment = new DataPanenMenu();
        Bundle args = new Bundle();
        args.putString(ID_KANDANG, idkandang);
        args.putString(ID_CHIKCIN, idchickin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getIdKandang = getArguments().getString(ID_KANDANG);
            getIdChikcin = getArguments().getString(ID_CHIKCIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
         view = inflater.inflate(R.layout.fragment_data_panen_menu, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolBarDataPanen);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

         spinnerPilihKandang = view.findViewById(R.id.spinner_kandang_panen);
         spinnerPilihPeriode = view.findViewById(R.id.spinner_periode_panen);
         btnCetakPDFPanen = view.findViewById(R.id.btn_cetak_pdfPanen);

        SpinnerPilihKandang();

         rvData = view.findViewById(R.id.rvCheckPanen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);



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

                            LoadDataSampel(
                                    idkandang,
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPeriode(),
                                    kodekandang,
                                    kodeblok,
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getPopulasi_masuk(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getType_produk(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getKondisi_chick_in(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getTanggal_chickin(),
                                    datas.get(spinnerPilihPeriode.getSelectedItemPosition()).getId_chickin()
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
                    btnCetakPDFPanen.setOnClickListener(new View.OnClickListener() {

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
                               String tanggal_chickin,
                               String idchickin){




        Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call = service.apiGetPanen(idkandang,periode,idchickin);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataPanen>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call, Response<BaseResponse.BaseResponseApi<List<DataPanen>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());
                    btnCetakPDFPanen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions((Activity) getContext(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                            }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                if (new File(Common.getAppath(getContext())+"Panen"+TanggalDanWaktu()+idkandang+"-"+periode+".pdf").exists()){
                                    new File(Common.getAppath(getContext())+"Panen"+TanggalDanWaktu()+idkandang+"-"+periode+".pdf").delete();
                                }

                                try {

                                    Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                    PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Panen"+TanggalDanWaktu()+idkandang+"-"+periode+".pdf"));
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

                                    PdfPTable tableInfo = new PdfPTable(columnwidth);
                                    PdfPCell cell1;


                                    tableInfo.setWidthPercentage(100);
                                    cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                    cell1.setRowspan(1);
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    cell1.setColspan(8);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kode_kandang));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase(": "+kode_blok));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);


                                    cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+tanggal_chickin));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Periode"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+periode));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jumlah_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Jenis DOC"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+jenis_doc));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);

                                    cell1 = new PdfPCell(new Phrase(": "+kondisi));
                                    cell1.setBorder(Rectangle.NO_BORDER);
                                    tableInfo.addCell(cell1);


                                    document.add(tableInfo);




                                    Chunk title2 = new Chunk("\n" , fontbigBold);
                                    Paragraph paragraphTitle2 = new Paragraph(title2);
                                    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                    document.add(paragraphTitle2);


                                    //Tabel Konten
                                    PdfPTable tablePanen = new PdfPTable(7);
                                    tablePanen.setWidthPercentage(100);
                                    tablePanen.setWidths(new float[]{(float) 0.5,1,1,1,1,1,1});
                                    PdfPCell cell;


                                    cell = new PdfPCell(new Phrase("Periode",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Usia Panen",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Tanggal Panen",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Total Ayam Panen",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Berat Per Ekor (Kg)",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);


                                    cell = new PdfPCell(new Phrase("Total Berat Panen (Kg)",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);

                                    cell = new PdfPCell(new Phrase("Total Gagal Panen",bold));
                                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell.setRowspan(2);
                                    tablePanen.addCell(cell);



                                    for (int i = 0; i <dataArrayList.size() ; i++) {

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getPeriode(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getUmur_panen(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getTanggal_panen(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getJumlah_panen(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getBerat_panen_ekor(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getTotal_berat_keseluruhan(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        cell = new PdfPCell(new Phrase(dataArrayList.get(i).getGagal_panen(),normal));
                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        tablePanen.addCell(cell);

                                        Log.d("coba", "createPDF: " + dataArrayList.get(i).getId_kandang());
                                    }





                                    document.add(tablePanen);
                                    document.close();

                                    Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Panen"+TanggalDanWaktu()+idkandang+"-"+periode+".pdf"));
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                    adapter = new AdapterPanen(getContext(), dataArrayList, new AdapterPanen.CustomItemClickListener() {
                        @Override
                        public void onItemClick(DataPanen user, int position) {
//                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                    EditCheckSampel.newInstance(
//                                            user.getId(),
//                                            user.getId_kandang(),
//                                            user.getUmur_ayam(),
//                                            user.getJenis_ayam_sampel(),
//                                            user.getTotal_ayam_saat_ini(),
//                                            user.getKondisi_ayam(),
//                                            user.getJumlah_ayam_sampel(),
//                                            user.getBobot_ayam_sampel(),
//                                            user.getBobot_rata_rata())).addToBackStack(null).commit();
//                            Log.d("tessampel", "onItemClick: " + user.getId() + user.getUmur_ayam() + user.getKondisi_ayam());
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
                    btnCetakPDFPanen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Maaf, Data Sampel Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataPanen>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
}