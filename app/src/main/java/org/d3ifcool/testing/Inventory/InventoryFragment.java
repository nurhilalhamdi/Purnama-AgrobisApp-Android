package org.d3ifcool.testing.Inventory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Adapter.AdapterRequest;
import org.d3ifcool.testing.Datas.DataRequest;
import org.d3ifcool.testing.Datas.DataStok;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InventoryFragment extends Fragment {


    private static final String ID_KANDANG = "idkandang";
    Spinner spinnerNamaProduk;
    private DataService service;

    Chip chipPakan, chipObat, chipVitamin, chipVaksin, chipPeralatan, chipDll;
    ArrayList<DataStok> dataStok;

    List<DataRequest> dataRequests;


    RelativeLayout btnsimpan;


    String getIdKandang;
    Button button_batalkirim;
    Button button_barangditerima;
    Button button_cancel;

    TextView txtSatuanInv;
//    TextView txtsetTanggalRequest;
    TextView txtIdbarang;
    EditText edtJumlahRequest;
    EditText edtCatatan;
    DatePickerDialog datePickerDialog;

    TextView tgl;

    TextView noRequest;

    TextView txtTitle2,txtnoreq,txttglreq,txtnamabarangreq,txtjumlahreq,txtstatusreq;

    private RecyclerView rvData;
    private AdapterRequest adapter;
    ArrayList<DataRequest> dataArrayList;

    TextInputLayout textInputLayout;

    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            getIdKandang = getArguments().getString(ID_KANDANG);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





        View view;
        view = inflater.inflate(R.layout.fragment_inventory, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarInventory);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);



        chipPakan = view.findViewById(R.id.kgPakan);
        chipObat = view.findViewById(R.id.kgObat);
        chipVaksin = view.findViewById(R.id.kgVaksin);
        chipVitamin = view.findViewById(R.id.kgVitamin);
        chipPeralatan = view.findViewById(R.id.kgAlat);
        chipDll = view.findViewById(R.id.kgLainnya);

        txtSatuanInv = view.findViewById(R.id.satuanInvetory);
//        txtsetTanggalRequest = view.findViewById(R.id.txt_tangggal_request);
        edtCatatan = view.findViewById(R.id.edt_catatan);
        edtJumlahRequest = view.findViewById(R.id.edt_jumlah_request);
        txtIdbarang = view.findViewById(R.id.txt_idbarang);
        tgl = view.findViewById(R.id.tgl);

        noRequest = view.findViewById(R.id.request_belum_ada);





        spinnerNamaProduk = view.findViewById(R.id.spinnerNamaProduk);
        spinnerNamaProduk.setPrompt("Pilih Produk");
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
        spinnerNamaProduk.setAdapter(arrayAdapterType);

        dataStok = new ArrayList<DataStok>();

        btnsimpan = view.findViewById(R.id.btn_simpan_request);
//        txtsetTanggalRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int date = calendar.get(Calendar.DATE);
//                int hours = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//                datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDate) {
//                        txtsetTanggalRequest.setText(mYear + "-" + (mMonth+1) + "-" + mDate);
//
//                    }
//                },year,month,date);
//
//                datePickerDialog.show();
//
//            }
//        });

        initSpinnerProduk();

//        initRequestProduk();



        rvData = view.findViewById(R.id.rvReq);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);
        loadData();

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinnerNamaProduk.getSelectedItem().equals("Pilih Kategori")){
                    ((TextView)spinnerNamaProduk.getChildAt(0)).setError("Harap Pilih Kategori produk");
                }else if (TextUtils.isEmpty(edtJumlahRequest.getText().toString())){
                    edtJumlahRequest.setError("Harap Masukkan Jumlah Barang Yang Di Request");
                }else {
//                    Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    initNotifikasiProduk();
                }

//                initRequestProduk();


            }
        });
        return  view;
    }


    public void refresh(int milisecond){
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        };
        handler.postDelayed(runnable,milisecond);
    }


    public void loadData(){
        Call<BaseResponse.BaseResponseApi<List<DataRequest>>> call = service.apiReadRequest(getIdKandang);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataRequest>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataRequest>>> call, Response<BaseResponse.BaseResponseApi<List<DataRequest>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());
                    adapter = new AdapterRequest(getContext(), dataArrayList, new AdapterRequest.CustomItemClickListener() {
                        @Override
                        public void onItemClick(DataRequest user, int position) {
//                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
                         openDialog(user.getKategori(),user.getNomor_request(),user.getTanggal_request(),user.getNama(),user.getJumlah_request(),user.getStatus(), user.getSatuan(),user.getKeterangan(),user.getId());

                        }
                    });
//                    adapter = new AdapterRequest(getContext(), dataArrayList, new AdapterRequest.CustomItemClickListener() {
//                        @Override
//                        public void onItemClick(Data user, int position) {
//                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PengecekanFragment.newInstance(user.getId_kandang())).addToBackStack(null).commit();
//                            Toast.makeText(getContext(), "" + user.getId_kandang(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    rvData.setAdapter(adapter);
//                    adapter.addAll(response.body().getData());
                    int count = adapter.getItemCount();
                    rvData.setVisibility(View.VISIBLE);
                    noRequest.setVisibility(View.GONE);

                } else if (response.code() == 500) {
                    noRequest.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.GONE);
                }
                refresh(1000);
             }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataRequest>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }



    private void openDialog(String kategori, String noreq, String tglreq, String namabarang, String jumlahreq, String statusreq, String satuan, String keterangan, String id){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.request_dialog);

        txtTitle2 = dialog.findViewById(R.id.title2);
        txtnoreq = dialog.findViewById(R.id.txtnorequest);
        txttglreq = dialog.findViewById(R.id.txttglrequest);
        txtnamabarangreq = dialog.findViewById(R.id.txtnamabarangrequest);
        txtjumlahreq = dialog.findViewById(R.id.txtjumlahbarangrequest);
        txtstatusreq = dialog.findViewById(R.id.txtstatusrequest);
        textInputLayout = dialog.findViewById(R.id.name_text_input);

        button_barangditerima = dialog.findViewById(R.id.button_diterima);
        button_batalkirim = dialog.findViewById(R.id.button_batal);
        button_cancel = dialog.findViewById(R.id.button_cancel);







        txtTitle2.setText("Kategori " + kategori);
        txtnoreq.setText(noreq);
        txttglreq.setText(tglreq);
        txtnamabarangreq.setText(namabarang);
        txtjumlahreq.setText(jumlahreq + " " + satuan);
        txtstatusreq.setText(statusreq);
        textInputLayout.getEditText().setText(keterangan);

        textInputLayout.setEnabled(false);

        switch (statusreq) {
            case "Request Sedang Diproses":
                button_batalkirim.setVisibility(View.VISIBLE);
                button_barangditerima.setVisibility(View.GONE);
                txtstatusreq.setBackgroundResource(R.drawable.bg_accept);
                break;
            case "Request Diterima":
                txtstatusreq.setBackgroundResource(R.drawable.bg_accept);
                button_barangditerima.setVisibility(View.VISIBLE);
                button_batalkirim.setVisibility(View.GONE);
                break;
            case "Request Ditangguhkan":

                txtstatusreq.setBackgroundResource(R.drawable.bg_warning);
                break;
            case "Request Ditolak":
                txtstatusreq.setBackgroundResource(R.drawable.bg_decline);
                break;
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        button_batalkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set title dialog
                alertDialogBuilder.setTitle("Konfirmasi Batalkan Request");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Anda Yakin No Request " + txtnoreq.getText().toString() + "  Akan Dibatalkan ?")
                        .setIcon(R.drawable.ic_information)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogs,int ids) {
                                // jika tombol diklik, maka akan menutup activity ini
//                        txtPeriode.setVisibility(View.GONE);
//                        cardViewPanen.setVisibility(View.GONE);
//                        titlePeriode.setText("Periode Berakhir");
//                        txtPopulasi.setText("0");
//                        txttotal_panen.setText("0");
//                        txtTglchickin.setText("0000-00-00");
                                initDeleteRequestProduk(id);
                                dialogs.cancel();
                                dialog.cancel();


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
        });

        button_barangditerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());

                    // set title dialog
                    alertDialogBuilder.setTitle("Konfirmasi Produk Telah Diterima");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Pastikan Produk " + txtnamabarangreq.getText().toString() + " Telah Diterima !")
                            .setIcon(R.drawable.ic_information)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogs,int ids) {
                                    // jika tombol diklik, maka akan menutup activity ini
//                        txtPeriode.setVisibility(View.GONE);
//                        cardViewPanen.setVisibility(View.GONE);
//                        titlePeriode.setText("Periode Berakhir");
//                        txtPopulasi.setText("0");
//                        txttotal_panen.setText("0");
//                        txtTglchickin.setText("0000-00-00");
                                    initDeleteRequestProdukDiterima(id);
                                    dialogs.cancel();
                                    dialog.cancel();

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
        });
    }

//
//    private void initRequestProduk(){
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
//        tgl.setText(currentDate);
//        Call<BaseResponse.BaseResponseApi> getkodeRequest = service.requestInventory(
//                txtIdbarang.getText().toString(),
//                edtJumlahRequest.getText().toString(),
//                getIdKandang,
//                edtCatatan.getText().toString(),
//                tgl.getText().toString()
//        );
//        getkodeRequest.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
//            @SuppressLint({"SetTextI18n", "DefaultLocale"})
//            @Override
//            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
//                if (response.code() == 200) {
//                    Toast.makeText(getContext(), "Request Berhasil", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(), "Request Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t){
//
//            }
//        });
//    }
    private void initDeleteRequestProduk(String id){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        tgl.setText(currentDate);
        Call<BaseResponse.BaseResponseApi> getkodeRequest = service.deleteRequestInventory(id);
        getkodeRequest.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {


                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Request Berhasil Dibatalkan", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Request Gagal dibatalkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t){

            }
        });
    }
    private void initDeleteRequestProdukDiterima(String id){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        tgl.setText(currentDate);
        Call<BaseResponse.BaseResponseApi> getkodeRequest = service.deleteRequestInventory(id);
        getkodeRequest.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {


                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Request Diterima", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Request Diterima", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t){

            }
        });
    }

    private void initNotifikasiProduk(){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        tgl.setText(currentDate);
        Call<BaseResponse.BaseResponseApi> getkodeRequest = service.notifikasi(
                txtIdbarang.getText().toString(),
                edtJumlahRequest.getText().toString(),
                getIdKandang,
                edtCatatan.getText().toString(),
                tgl.getText().toString()
        );
        getkodeRequest.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Request Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }else {
                    Toast.makeText(getContext(), "Request Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t){

            }
        });
    }

    private void initSpinnerProduk(){
        chipPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipPakan.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("1");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();

                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());


                                }



                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);


                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }else {
                                String[] message = new String[]{"Pakan habis/belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });

        chipObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipObat.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("2");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();
                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());
                                }

                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);

                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });






                            }else {
                                String[] message = new String[]{"Barang Obat habis/belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);

                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });

        chipVitamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVitamin.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("3");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();
                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());
                                }

                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);

                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }else {
                                String[] message = new String[]{"Barang Vitamin habis/belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });


        chipVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVaksin.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("4");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();
                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());
                                }



                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);

                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                String[] message = new String[]{"Barang Vaksin habis/ belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });

        chipPeralatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipPeralatan.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("5");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();
                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());
                                }



                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }else {
                                String[] message = new String[]{"Barang Alat habis/belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });

        chipDll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipDll.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataStok>>> pakanCalss = service.apigetStok("6");
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataStok>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Response<BaseResponse.BaseResponseApi<List<DataStok>>> response) {
                            if (response.code() == 200) {
                                List<DataStok>  datastok = response.body().getData();
                                List<String> listSpinner = new ArrayList<String>();
                                for (int i = 0; i <datastok.size() ; i++) {
                                    listSpinner.add(datastok.get(i).getNama());
                                }



                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                                spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                        Toast.makeText(getContext(), ""+datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getKode(), Toast.LENGTH_SHORT).show();
                                        txtSatuanInv.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getSatuan());
                                        txtIdbarang.setText(datastok.get(spinnerNamaProduk.getSelectedItemPosition()).getId_barang());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }else {
                                String[] message = new String[]{"Barang Lainnya habis/belum ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerNamaProduk.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataStok>>> call, Throwable t){

                        }
                    });
                }else {
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.kategori));
                    spinnerNamaProduk.setAdapter(arrayAdapterType);
                }
            }
        });
    }

    public static InventoryFragment newInstance(

            String id_kandang) {

        Bundle args = new Bundle();
        InventoryFragment fragment = new InventoryFragment();

        args.putString(ID_KANDANG,id_kandang);
        fragment.setArguments(args);

        return fragment;
    }

}