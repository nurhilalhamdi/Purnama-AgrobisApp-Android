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

import com.google.android.material.chip.Chip;
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
import org.d3ifcool.testing.Adapter.AdapterCheckStok;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataCheckHarian;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Datas.DataStok;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
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


public class DataStokMenu extends Fragment {


    Spinner spinnerPilihKandang;
    Chip chipPakan, chipObat, chipVitamin, chipVaksin, chipPeralatan, chipDll;
    private DataService service;

    RelativeLayout btnCetakPDFStok;

    ArrayList<DataInventory.DataPakan> datapakan;
    ArrayList<DataInventory.DataObat> dataobat;
    ArrayList<DataInventory.DataVitamin> datavitamin;
    ArrayList<DataInventory.DataVaksin> dataVaksin;
    ArrayList<DataInventory.DataPeralatan> dataAlat;
    ArrayList<DataInventory.DataItemLain> dataItemLain;
    TextView idkandangstok;

    private RecyclerView rvDataPakan;
    private RecyclerView rvDataObat;
    private RecyclerView rvDataVitamin;
    private RecyclerView rvDataVaksin;
    private RecyclerView rvDataAlat;
    private RecyclerView rvDataItemLain;
    private AdapterCheckStok.AdapterPakan adapterPakan;
    private AdapterCheckStok.AdapterObat adapterObat;
    private AdapterCheckStok.AdapterVitamin adapterVitamin;
    private AdapterCheckStok.AdapterVaksin adapterVaksin;
    private AdapterCheckStok.AdapterAlat adapterAlat;
    private AdapterCheckStok.AdapterItemLain adapterItemLain;

    TextView txtTotalStok;
    TextView txtTotalJenis;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DataStokMenu() {
        // Required empty public constructor
    }


    public static DataStokMenu newInstance(String param1, String param2) {
        DataStokMenu fragment = new DataStokMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_data_stok_menu, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarDataStok);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        idkandangstok = view.findViewById(R.id.idkandang_stok);



        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
        spinnerPilihKandang = view.findViewById(R.id.spinner_kandang_stok);




        rvDataPakan = view.findViewById(R.id.rvCheckStokPakan);
        RecyclerView.LayoutManager layoutManagerPakan = new LinearLayoutManager(getContext());
        rvDataPakan.setLayoutManager(layoutManagerPakan);
        rvDataPakan.setItemAnimator(new DefaultItemAnimator());
        rvDataPakan.setAdapter(adapterPakan);




        rvDataObat = view.findViewById(R.id.rvCheckStokObat);
        RecyclerView.LayoutManager layoutManagerObat = new LinearLayoutManager(getContext());
        rvDataObat.setLayoutManager(layoutManagerObat);
        rvDataObat.setItemAnimator(new DefaultItemAnimator());
        rvDataObat.setAdapter(adapterObat);

        rvDataVitamin = view.findViewById(R.id.rvCheckStokVitamin);
        RecyclerView.LayoutManager layoutManagerVitamin = new LinearLayoutManager(getContext());
        rvDataVitamin.setLayoutManager(layoutManagerVitamin);
        rvDataVitamin.setItemAnimator(new DefaultItemAnimator());
        rvDataVitamin.setAdapter(adapterVitamin);

        rvDataVaksin = view.findViewById(R.id.rvCheckStokVaksin);
        RecyclerView.LayoutManager layoutManagerVaksin = new LinearLayoutManager(getContext());
        rvDataVaksin.setLayoutManager(layoutManagerVaksin);
        rvDataVaksin.setItemAnimator(new DefaultItemAnimator());
        rvDataVaksin.setAdapter(adapterVaksin);

        rvDataAlat = view.findViewById(R.id.rvCheckStokAlat);
        RecyclerView.LayoutManager layoutManagerAlat = new LinearLayoutManager(getContext());
        rvDataAlat.setLayoutManager(layoutManagerAlat);
        rvDataAlat.setItemAnimator(new DefaultItemAnimator());
        rvDataAlat.setAdapter(adapterAlat);

        rvDataItemLain = view.findViewById(R.id.rvCheckStokItemLain);
        RecyclerView.LayoutManager layoutManagerItemLain = new LinearLayoutManager(getContext());
        rvDataItemLain.setLayoutManager(layoutManagerItemLain);
        rvDataItemLain.setItemAnimator(new DefaultItemAnimator());
        rvDataItemLain.setAdapter(adapterItemLain);

        chipPakan = view.findViewById(R.id.kgPakan_stok);
        chipObat = view.findViewById(R.id.kgObat_stok);
        chipVitamin = view.findViewById(R.id.kgVitamin_Stok);
        chipVaksin = view.findViewById(R.id.kgVaksin_Stok);
        chipPeralatan = view.findViewById(R.id.kgAlat_Stok);
        chipDll = view.findViewById(R.id.kgLainnya_Stok);
        btnCetakPDFStok = view.findViewById(R.id.btn_cetak_pdfStok);

        txtTotalStok = view.findViewById(R.id.txt_total_stok);
        txtTotalJenis = view.findViewById(R.id.txt_total_jenis);


        initStok();
        return view;
    }


    private String TanggalDanWaktu(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void SpinnerPilihKandangForPakan() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());


                            ////////////////////////////// Pakan Response
                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
                                    if (response.code() == 200) {
                                        datapakan = new ArrayList<>(response.body().getData());
                                        int total_pakan = 0;
                                        for (int i = 0; i < datapakan.size(); i++) {
                                            txtTotalJenis.setText(""+datapakan.size());
                                            total_pakan += datapakan.get(i).getStok_pakan();
                                            txtTotalStok.setText(total_pakan+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Pakan Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Pakan Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Pakan Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Pakan" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table7 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table7.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table7.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table7.addCell(cell1);

                                                        document.add(table7);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table8 = new PdfPTable(5);
                                                        table8.setWidthPercentage(100);
                                                        table8.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table8.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table8.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table8.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table8.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table8.addCell(cell);





                                                        for (int i = 0; i <datapakan.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(datapakan.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table8.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datapakan.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table8.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datapakan.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table8.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(datapakan.get(i).getStok_pakan()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table8.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datapakan.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table8.addCell(cell);



                                                            Log.d("coba", "createPDF: " + datapakan.get(i).getId_kandang());
                                                        }






                                                        document.add(table8);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Pakan Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterPakan = new AdapterCheckStok.AdapterPakan(getContext(), datapakan, new AdapterCheckStok.AdapterPakan.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataPakan user, int position) {

                                            }
                                        });
                                        rvDataPakan.setAdapter(adapterPakan);
                                        adapterPakan.notifyDataSetChanged();
                                        rvDataPakan.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataPakan.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataPakan.setAdapter(adapterPakan);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Pakan Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){

                                }
                            });




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
    public void SpinnerPilihKandangForObat() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());



                            /////////////// Obat Response
                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> obatCalls = service.apigetObat(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            obatCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> response) {
                                    if (response.code() == 200) {
                                        dataobat =  new ArrayList<>(response.body().getData());
                                        int total_obat = 0;
                                        for (int i = 0; i < dataobat.size(); i++) {
                                            txtTotalJenis.setText(""+dataobat.size());
                                            total_obat += dataobat.get(i).getStok_obat();
                                            txtTotalStok.setText(total_obat+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Obat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Obat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Obat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Obat" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table9 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table9.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table9.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table9.addCell(cell1);

                                                        document.add(table9);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table10 = new PdfPTable(5);
                                                        table10.setWidthPercentage(100);
                                                        table10.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table10.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table10.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table10.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table10.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table10.addCell(cell);





                                                        for (int i = 0; i <dataobat.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(dataobat.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table10.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataobat.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table10.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataobat.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table10.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(dataobat.get(i).getStok_obat()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table10.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataobat.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table10.addCell(cell);



                                                            Log.d("coba", "createPDF: " + datapakan.get(i).getId_kandang());
                                                        }






                                                        document.add(table10);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Obat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterObat = new AdapterCheckStok.AdapterObat(getContext(), dataobat, new AdapterCheckStok.AdapterObat.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataObat user, int position) {

                                            }
                                        });
                                        rvDataObat.setAdapter(adapterObat);
                                        adapterObat.notifyDataSetChanged();
                                        rvDataObat.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataObat.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataObat.setAdapter(adapterObat);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Obat Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Throwable t){

                                }
                            });



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

    public void SpinnerPilihKandangForVitamin() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());


                            /// Vitamin Response
                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> vitaminCalls = service.apigetVitamin(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            vitaminCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> response) {
                                    if (response.code() == 200) {
                                        datavitamin = new ArrayList<>(response.body().getData());
                                        int total_vitamin = 0;
                                        for (int i = 0; i < datavitamin.size(); i++) {
                                            txtTotalJenis.setText(""+datavitamin.size());
                                            total_vitamin += datavitamin.get(i).getStok_vitamin();
                                            txtTotalStok.setText(total_vitamin+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Vitamin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Vitamin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Vitamin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Vitamin" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table11 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table11.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table11.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table11.addCell(cell1);

                                                        document.add(table11);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table12 = new PdfPTable(5);
                                                        table12.setWidthPercentage(100);
                                                        table12.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table12.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table12.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table12.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table12.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table12.addCell(cell);





                                                        for (int i = 0; i <datavitamin.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(datavitamin.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table12.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datavitamin.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table12.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datavitamin.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table12.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(datavitamin.get(i).getStok_vitamin()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table12.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(datavitamin.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table12.addCell(cell);



                                                            Log.d("coba", "createPDF: " + datavitamin.get(i).getId_kandang());
                                                        }






                                                        document.add(table12);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Vitamin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterVitamin = new AdapterCheckStok.AdapterVitamin(getContext(), datavitamin, new AdapterCheckStok.AdapterVitamin.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataVitamin user, int position) {

                                            }
                                        });
                                        rvDataVitamin.setAdapter(adapterVitamin);
                                        adapterVitamin.notifyDataSetChanged();
                                        rvDataVitamin.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataVitamin.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataVitamin.setAdapter(adapterVitamin);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Vitamin Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Throwable t){

                                }
                            });

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

    public void SpinnerPilihKandangForVaksin() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());


                                                        /// Vaksin Response
                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> vaksinCalls = service.apigetVaksin(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            vaksinCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> response) {
                                    if (response.code() == 200) {
                                        dataVaksin = new ArrayList<>(response.body().getData());
                                        int total_vaksin= 0;
                                        for (int i = 0; i < dataVaksin.size(); i++) {
                                            txtTotalJenis.setText(""+dataVaksin.size());
                                            total_vaksin += dataVaksin.get(i).getStok_vaksin();
                                            txtTotalStok.setText(total_vaksin+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Vaksin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Vaksin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Vaksin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Vaksin" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table13 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table13.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table13.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table13.addCell(cell1);

                                                        document.add(table13);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table14 = new PdfPTable(5);
                                                        table14.setWidthPercentage(100);
                                                        table14.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table14.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table14.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table14.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table14.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table14.addCell(cell);





                                                        for (int i = 0; i <dataVaksin.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(dataVaksin.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table14.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataVaksin.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table14.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataVaksin.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table14.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(dataVaksin.get(i).getStok_vaksin()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table14.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataVaksin.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table14.addCell(cell);



                                                            Log.d("coba", "createPDF: " + dataVaksin.get(i).getId_kandang());
                                                        }






                                                        document.add(table14);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Vaksin Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterVaksin = new AdapterCheckStok.AdapterVaksin(getContext(), dataVaksin, new AdapterCheckStok.AdapterVaksin.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataVaksin user, int position) {

                                            }
                                        });
                                        rvDataVaksin.setAdapter(adapterVaksin);
                                        adapterVaksin.notifyDataSetChanged();
                                        rvDataVaksin.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataVaksin.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataVaksin.setAdapter(adapterVaksin);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Vaksin Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Throwable t){

                                }
                            });
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
    public void SpinnerPilihKandangForAlat() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());


                            /// Peralatan Response
                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>> peralatanCalls = service.apigetPeralatan(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            peralatanCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>> response) {
                                    if (response.code() == 200) {
                                        dataAlat = new ArrayList<>(response.body().getData());
                                        int total_alat= 0;
                                        for (int i = 0; i < dataAlat.size(); i++) {
                                            txtTotalJenis.setText(""+dataAlat.size());
                                            total_alat += dataAlat.get(i).getStok_peralatan();
                                            txtTotalStok.setText(total_alat+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Alat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Alat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Alat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Peralatan" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table15 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table15.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table15.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table15.addCell(cell1);

                                                        document.add(table15);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table16 = new PdfPTable(5);
                                                        table16.setWidthPercentage(100);
                                                        table16.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table16.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table16.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table16.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table16.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table16.addCell(cell);





                                                        for (int i = 0; i <dataAlat.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(dataAlat.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table16.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataAlat.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table16.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataAlat.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table16.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(dataAlat.get(i).getStok_peralatan()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table16.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataAlat.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table16.addCell(cell);



                                                            Log.d("coba", "createPDF: " + dataAlat.get(i).getId_kandang());
                                                        }






                                                        document.add(table16);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Alat Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterAlat = new AdapterCheckStok.AdapterAlat(getContext(), dataAlat, new AdapterCheckStok.AdapterAlat.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataPeralatan user, int position) {

                                            }
                                        });
                                        rvDataAlat.setAdapter(adapterAlat);
                                        adapterAlat.notifyDataSetChanged();
                                        rvDataAlat.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataAlat.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataAlat.setAdapter(adapterAlat);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Alat Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }
                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>> call, Throwable t){

                                }
                            });
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

    public void SpinnerPilihKandangForItemLain() {
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

                            idkandangstok.setText(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());


                            Call<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>> itemLainCalls = service.apigetItemLain(dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang());
                            itemLainCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>> response) {
                                    if (response.code() == 200) {
                                        dataItemLain = new ArrayList<>(response.body().getData());
                                        int total_itemlain= 0;
                                        for (int i = 0; i < dataItemLain.size(); i++) {
                                            txtTotalJenis.setText(""+dataItemLain.size());
                                            total_itemlain += dataItemLain.get(i).getStok_itemlain();
                                            txtTotalStok.setText(total_itemlain+"");

                                        }
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                                    ActivityCompat.requestPermissions((Activity) getContext(),
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
                                                }else {
//                                createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
                                                    if (new File(Common.getAppath(getContext())+"Cek Item Lainnya Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").exists()){
                                                        new File(Common.getAppath(getContext())+"Cek Item Lainnya Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf").delete();
                                                    }

                                                    try {

                                                        Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
                                                        PdfWriter.getInstance(document,new FileOutputStream(Common.getAppath(getContext())+"Cek Item Lainnya Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                        document.open();
                                                        document.addAuthor("PT.Multi Data Sinergi");
                                                        document.addCreator("Ayamqu.id");
                                                        document.addCreationDate();

                                                        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL, BaseColor.BLACK);
                                                        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);
                                                        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,BaseColor.BLACK);

                                                        Chunk title = new Chunk("Data Stok Item Lainnya" , fontbigBold);
                                                        Paragraph paragraphTitle = new Paragraph(title);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        paragraphTitle.setSpacingAfter(20);
                                                        document.add(paragraphTitle);


                                                        //Tabel Konten detail

                                                        float columnwidth[] = {80,80,80,80};

                                                        PdfPTable table17 = new PdfPTable(columnwidth);
                                                        PdfPCell cell1;


                                                        table17.setWidthPercentage(100);
                                                        cell1 = new PdfPCell(new Phrase("Informasi Kandang",bold));
                                                        cell1.setRowspan(1);
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        cell1.setColspan(8);
                                                        table17.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Tanggal / Waktu"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+TanggalDanWaktu()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(" "));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("Kode Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodekandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Kode Blok"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getKodeblok()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase("PPL Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+MainActivity.prefConfig.readName()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);


                                                        cell1 = new PdfPCell(new Phrase("Alamat Kandang"));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        cell1 = new PdfPCell(new Phrase(": "+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getAlamat_kandang()));
                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                        table17.addCell(cell1);

                                                        document.add(table17);


                                                        Chunk title2 = new Chunk("\n" , fontbigBold);
                                                        Paragraph paragraphTitle2 = new Paragraph(title2);
                                                        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
                                                        document.add(paragraphTitle2);


                                                        //Tabel Konten
                                                        PdfPTable table18 = new PdfPTable(5);
                                                        table18.setWidthPercentage(100);
                                                        table18.setWidths(new float[]{2,1,1,1,1});
                                                        PdfPCell cell;

                                                        cell = new PdfPCell(new Phrase("Tanggal Masuk",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table18.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Kode Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table18.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Nama Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table18.addCell(cell);


                                                        cell = new PdfPCell(new Phrase("Jumlah Barang",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table18.addCell(cell);

                                                        cell = new PdfPCell(new Phrase("Satuan",bold));
                                                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cell.setRowspan(2);
                                                        table18.addCell(cell);





                                                        for (int i = 0; i <dataItemLain.size() ; i++) {

                                                            cell = new PdfPCell(new Phrase(dataItemLain.get(i).getCreated_at(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table18.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataItemLain.get(i).getKode(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table18.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataItemLain.get(i).getNama(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table18.addCell(cell);


                                                            cell = new PdfPCell(new Phrase(String.valueOf(dataItemLain.get(i).getStok_itemlain()),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table18.addCell(cell);

                                                            cell = new PdfPCell(new Phrase(dataItemLain.get(i).getSatuan(),normal));
                                                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                            table18.addCell(cell);



                                                            Log.d("coba", "createPDF: " + dataItemLain.get(i).getId_kandang());
                                                        }


                                                        document.add(table18);
                                                        document.close();

                                                        Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Item Lainnya Stok"+TanggalDanWaktu()+dataKandang.get(spinnerPilihKandang.getSelectedItemPosition()).getId_kandang()+".pdf"));
                                                    } catch (DocumentException e) {
                                                        e.printStackTrace();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        });
                                        adapterItemLain = new AdapterCheckStok.AdapterItemLain(getContext(), dataItemLain, new AdapterCheckStok.AdapterItemLain.CustomItemClickListener() {
                                            @Override
                                            public void onItemClick(DataInventory.DataItemLain user, int position) {

                                            }
                                        });
                                        rvDataItemLain.setAdapter(adapterItemLain);
                                        adapterItemLain.notifyDataSetChanged();
                                        rvDataItemLain.setVisibility(View.VISIBLE);
                                    }else if (response.code() == 404){
                                        rvDataItemLain.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                                        rvDataItemLain.setAdapter(adapterItemLain);
                                        txtTotalJenis.setText("0");
                                        txtTotalStok.setText("0");
                                        btnCetakPDFStok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "Maaf, Stok Item Lainnya Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>> call, Throwable t){

                                }
                            });
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

    public void initStok(){
        chipPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipPakan.isChecked()){

                        SpinnerPilihKandangForPakan();

                    rvDataObat.setVisibility(View.GONE);
                    rvDataVitamin.setVisibility(View.GONE);
                    rvDataVaksin.setVisibility(View.GONE);
                    rvDataAlat.setVisibility(View.GONE);
                    rvDataItemLain.setVisibility(View.GONE);
                    rvDataPakan.setVisibility(View.VISIBLE);
                }else {

                }

            }
        });
        chipObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipObat.isChecked()){

                    SpinnerPilihKandangForObat();
                    rvDataObat.setVisibility(View.VISIBLE);
                    rvDataPakan.setVisibility(View.GONE);
                    rvDataVitamin.setVisibility(View.GONE);
                    rvDataVaksin.setVisibility(View.GONE);
                    rvDataAlat.setVisibility(View.GONE);
                    rvDataItemLain.setVisibility(View.GONE);

                }else {

                }

            }
        });
        chipVitamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVitamin.isChecked()){

                    SpinnerPilihKandangForVitamin();
                    rvDataVitamin.setVisibility(View.VISIBLE);
                    rvDataPakan.setVisibility(View.GONE);
                    rvDataObat.setVisibility(View.GONE);
                    rvDataVaksin.setVisibility(View.GONE);
                    rvDataAlat.setVisibility(View.GONE);
                    rvDataItemLain.setVisibility(View.GONE);

                }else {

                }

            }
        });

        chipVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVaksin.isChecked()){

                    SpinnerPilihKandangForVaksin();
                    rvDataVaksin.setVisibility(View.VISIBLE);
                    rvDataPakan.setVisibility(View.GONE);
                    rvDataObat.setVisibility(View.GONE);
                    rvDataVitamin.setVisibility(View.GONE);
                    rvDataAlat.setVisibility(View.GONE);
                    rvDataItemLain.setVisibility(View.GONE);


                }else {

                }

            }
        });

        chipPeralatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipPeralatan.isChecked()){

                    SpinnerPilihKandangForAlat();
                    rvDataAlat.setVisibility(View.VISIBLE);
                    rvDataVaksin.setVisibility(View.GONE);
                    rvDataPakan.setVisibility(View.GONE);
                    rvDataObat.setVisibility(View.GONE);
                    rvDataVitamin.setVisibility(View.GONE);
                    rvDataItemLain.setVisibility(View.GONE);


                }else {

                }

            }
        });

        chipDll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipDll.isChecked()){

                    SpinnerPilihKandangForItemLain();
                    rvDataItemLain.setVisibility(View.VISIBLE);
                    rvDataAlat.setVisibility(View.GONE);
                    rvDataVaksin.setVisibility(View.GONE);
                    rvDataPakan.setVisibility(View.GONE);
                    rvDataObat.setVisibility(View.GONE);
                    rvDataVitamin.setVisibility(View.GONE);


                }else {

                }

            }
        });

    }





}