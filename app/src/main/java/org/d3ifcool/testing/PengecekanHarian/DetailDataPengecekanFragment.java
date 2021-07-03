package org.d3ifcool.testing.PengecekanHarian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

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

import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDataPengecekanFragment extends Fragment {


    //Variabel untuk print pdf;
//    Display display;
//    String ImageUri;
//    String path;
//    String bitmap;
//
//    int totalHeight;
//    int totalWidth;
//
//    public static final int READ_PHONE = 110;
//    String file_name = "Data_Pengecekan_Harian";
//    File myPath;

    int REQUEST_CODE_ASK_PERMISSIONS = 111;
    File pdfFile;
    MaterialButton btnPrint;
    ArrayList<DataInventory.DataPakan> dataPakans = new ArrayList<>();
    List<DataInventory.DataPakan> datapakan;
    private static final String TAG = "PdfCreatorActivity";
    private DataService service;


    public DetailDataPengecekanFragment() {

    }


//    public static DetailDataPengecekanFragment newInstance(String param1, String param2) {
//        DetailDataPengecekanFragment fragment = new DetailDataPengecekanFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view;
        view =  inflater.inflate(R.layout.fragment_detail_data_pengecekan, container, false);



        Toolbar toolbar = view.findViewById(R.id.toolBarDetailCekharian);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

//        Display ss = getContext().getDisplay();


        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
//
//
//        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//

        loadData();
        btnPrint = view.findViewById(R.id.button_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    createPdfWrapper();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
                printData();
            }
        });


        return view;
    }

    public void printData(){
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }else {
            createPDF(Common.getAppath(getContext())+"Cek Harian.pdf");
        }

    }

    private void createPDF(String s){

        if (new File(s).exists()){
            new File(s).delete();
        }

        try {

            Document document = new Document(PageSize.A4.rotate(),10f,10f,10f,0f);
            PdfWriter.getInstance(document,new FileOutputStream(s));
            document.open();
//            document.setPageSize(PageSize.A4);
            document.addAuthor("PT.Multi Data Sinergi");
            document.addCreator("Ayamqu.id");
            document.addCreationDate();

            Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
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

            cell1 = new PdfPCell(new Phrase(": 000123231"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("Kode Blok"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);


            cell1 = new PdfPCell(new Phrase(": 0044221"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("PPL Kandang"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": Nurhilal Hamdi"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("Tanggal CHICK IN"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": 2020-02-02"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Periode"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": 1"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Jumlah DOC"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": 200 Ekor"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Jenis DOC"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": DOC Sinta"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Kondisi Awal Diterima"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(": Sehat"));
            cell1.setBorder(Rectangle.NO_BORDER);
            table1.addCell(cell1);


            document.add(table1);




            Chunk title2 = new Chunk("\n" , fontbigBold);
            Paragraph paragraphTitle2 = new Paragraph(title2);
            paragraphTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitle2);
//            document.left(150f);
//            document.top(150f);


            //Tabel Konten
            PdfPTable table = new PdfPTable(17);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1});
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
            cell.setColspan(3);
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

            cell = new PdfPCell(new Phrase("Hidup",bold));
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



//            cell = new PdfPCell(new Phrase("Usia",bold));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setRowspan(2);
//            table.addCell(cell);


            for (int i = 0; i <dataPakans.size() ; i++) {

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getCreated_at(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_pakan(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(dataPakans.get(i).getStok_pakan()),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dataPakans.get(i).getId_kandang(),normal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);




                Log.d("coba", "createPDF: " + dataPakans.get(i).getId_kandang());
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
            Common.openFiledPDF(getContext(),new File(Common.getAppath(getContext())+"Cek Harian.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadData(){
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan("43");
        pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
                if (response.code() == 200) {
                    datapakan = response.body().getData();
                    dataPakans.addAll(datapakan);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){

            }
        });
    }


//    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
//        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
//                    showMessageOKCancel("You need to allow access to Storage",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                                REQUEST_CODE_ASK_PERMISSIONS);
//                                    }
//                                }
//                            });
//                    return;
//                }
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//            }
//            return;
//        } else {
//            createPdf();
//        }
//    }
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(getContext())
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//
//    private void createPdf() throws FileNotFoundException, DocumentException {
//        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
//        if (!docsFolder.exists()) {
//            docsFolder.mkdir();
//            Log.i(TAG, "Created a new directory for PDF");
//        }
//        String pdfname = "Pengecekan.pdf";
//        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
//        OutputStream output = new FileOutputStream(pdfFile);
//        Document document = new Document(PageSize.A4);
//
//        Font normal = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
//        Font bold = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK);
//        Font fontbigBold = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK);
//        Chunk title = new Chunk("Recording Ayam Pedaging" , fontbigBold);
//        Paragraph paragraphTitle = new Paragraph(title);
//        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
//        paragraphTitle.setSpacingAfter(20);
//
//
////        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3});
////        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
////        table.getDefaultCell().setFixedHeight(50);
////        table.setTotalWidth(PageSize.A4.getWidth());
////        table.setWidthPercentage(100);
////        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
////        table.addCell("ID Pakan");
////        table.addCell("ID Barang");
////        table.addCell("Stok Pakan");
////        table.addCell("ID Kandang");
////        table.setHeaderRows(1);
//
//
//        PdfPTable table = new PdfPTable(5);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{1,5,3,3,3});
//
//        PdfPCell cell;
//        cell = new PdfPCell(new Phrase("NO",bold));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setRowspan(2);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("ID PAKAN",bold));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setRowspan(2);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("ID BARANG",bold));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setColspan(2);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("STOK PAKAN",bold));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("ID Kandang",bold));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(cell);
//
//
////        PdfPCell[] cells = table.getRow(0).getCells();
////        for (int j = 0; j < cells.length; j++) {
////            cells[j].setBackgroundColor(BaseColor.GRAY);
////        }
////        for (int i = 0; i < MyList1.size(); i++) {
////            name = MyList1.get(i);
////            type = MyList1.get(i);
////            date = MyList1.get(i);
////            url = MyList1.get(i);
////            price = MyList1.get(i);
////            String namen = name.getItem_name();
////            String pricen = price.getItem_price();
////            String daten = date.getCreatedAt();
////            String typen = type.getItem_type_code();
////            String urln = url.getItem_URL();
////            table.addCell(String.valueOf(namen));
////            table.addCell(String.valueOf(pricen));
////            table.addCell(String.valueOf(typen));
////            table.addCell(String.valueOf(urln));
////            table.addCell(String.valueOf(daten.substring(0, 10)));
////        }
//
//        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan("43");
//        pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
//            @SuppressLint({"SetTextI18n", "DefaultLocale"})
//            @Override
//            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
//                if (response.code() == 200) {
//                    List<DataInventory.DataPakan>  datapakan = response.body().getData();
//                    for (int i = 0; i <datapakan.size() ; i++) {
//                        PdfPCell cell;
//                        cell = new PdfPCell(new Phrase(String.valueOf(i+1),normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase(datapakan.get(i).getId_pakan(),normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase(datapakan.get(i).getId_barang(),normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase(String.valueOf(datapakan.get(i).getStok_pakan()),normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase("",normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase(datapakan.get(i).getId_pakan(),normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
//
//                        cell = new PdfPCell(new Phrase("",normal));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        table.addCell(cell);
////                        String idpakan = datapakan.get(i).getId_pakan();
////                        String idbarang = datapakan.get(i).getId_barang();
////                        int stokpakan = datapakan.get(i).getStok_pakan();
////                        String idkandang = datapakan.get(i).getId_kandang();
////                        table.addCell(String.valueOf(idpakan));
////                        table.addCell(String.valueOf(idbarang));
////                        table.addCell(String.valueOf(stokpakan));
////                        table.addCell(String.valueOf(idkandang));
//
//
//
//                    }
//
//                    PdfPCell cell;
//                    cell = new PdfPCell(new Phrase("Total",bold));
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setColspan(2);
//                    table.addCell(cell);
//
//
//
//                    //        System.out.println("Done");
//                    try {
//                        PdfWriter.getInstance(document, output);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    document.open();
//                    try {
//                        document.add(paragraphTitle);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        document.add(table);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
////                    for (int i = 0; i < dataPakans.size(); i++) {
////                        document.add(new Paragraph(String.valueOf(MyList1.get(i))));
////                    }
//                    document.close();
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){
//            }
//        });
//
////        Log.e("safiya", MyList1.toString());
//        previewPdf();
//    }
//
//
//    private void previewPdf() {
//        PackageManager packageManager = getContext().getPackageManager();
//        Intent testIntent = new Intent(Intent.ACTION_VIEW);
//        testIntent.setType("application/pdf");
//        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
//        if (list.size() > 0) {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            Uri uri = FileProvider.getUriForFile(getContext(),getContext().getApplicationContext().getPackageName() + ".provider",pdfFile);
//            intent.setDataAndType(uri, "application/pdf");
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            getContext().startActivity(intent);
//        } else {
//            Toast.makeText(getContext(), "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
//        }
//    }
}