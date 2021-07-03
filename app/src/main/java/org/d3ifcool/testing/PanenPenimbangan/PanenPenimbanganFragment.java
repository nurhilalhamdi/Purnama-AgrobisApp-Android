package org.d3ifcool.testing.PanenPenimbangan;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.KesiapanKandang.CekKesiapanKandangTerbukaFragment;
import org.d3ifcool.testing.KesiapanKandang.CekKesiapanKandangTertutupFragment;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PanenPenimbanganFragment extends Fragment {



    MaterialButton materialButtonPanen;
    AutoCompleteTextView autoCompleteTextViewUmurPanen;
    DatePickerDialog datePickerDialog;

    TextView textViewTanggalAyamPanen;
    TextView textViewKKodeKandangAyamPanen;
    TextView textViewidKandangAyamPanen;
    TextView textViewidUserAyamPanen;
    TextView textViewidChickinAyamPanen;
    TextView textViewTotalAyamSekarangPanen;
    TextView textViewDOCPanen;
    TextView textViewPeriodePanen;
    TextView textViewSpinnerUmurPanen;


    EditText editTextBeratEkorPanen;
    EditText editTextQtyEkorPanen;
    EditText editTextQtyEkorGagalPanen;
    EditText editTextTonasePanen;

    private DataService service;
    List<Data> data;
    List<DataChickin> datas;

    private static final String ID_USER = "iduser";
    private static final String ID_KANDANG = "idkandang";
    private static final String ID_CHIKCIN = "idchickin";
    private static final String KODE_KANDANG = "kodekandang";
    String getIdUser;
    String getIdKandang;
    String getIdChikcin;
    String getKodeKandang;

    int umur = 0;

    
    public PanenPenimbanganFragment() {
        // Required empty public constructor
    }


    public static PanenPenimbanganFragment newInstance(
            String iduser,
            String idkandang,
            String idchickin,
            String kodekandang) {
        PanenPenimbanganFragment fragment = new PanenPenimbanganFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, iduser);
        args.putString(ID_KANDANG, idkandang);
        args.putString(ID_CHIKCIN, idchickin);
        args.putString(KODE_KANDANG, kodekandang);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getIdUser = getArguments().getString(ID_USER);
            getIdKandang = getArguments().getString(ID_KANDANG);
            getIdChikcin = getArguments().getString(ID_CHIKCIN);
            getKodeKandang = getArguments().getString(KODE_KANDANG);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_panen_penimbangan, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarPanenPenimbangan);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        materialButtonPanen = view.findViewById(R.id.button_simpan_panen);
        autoCompleteTextViewUmurPanen = view.findViewById(R.id.autoCompleteUmurAyamPanen);
        textViewTanggalAyamPanen = view.findViewById(R.id.txt_tanggal_ayam_panen);
        textViewidKandangAyamPanen = view.findViewById(R.id.txt_id_kandangPanen);
        textViewKKodeKandangAyamPanen = view.findViewById(R.id.txt_kode_kandang_panen);
        textViewidUserAyamPanen = view.findViewById(R.id.txt_id_userPanen);
        textViewidChickinAyamPanen = view.findViewById(R.id.txt_id_chickinPanen);
        textViewidKandangAyamPanen = view.findViewById(R.id.txt_id_kandangPanen);
        textViewidKandangAyamPanen = view.findViewById(R.id.txt_id_kandangPanen);
        textViewTotalAyamSekarangPanen = view.findViewById(R.id.txt_total_ayam_saat_sekarang_panen);
        textViewDOCPanen = view.findViewById(R.id.txt_doc_panen);
        textViewPeriodePanen = view.findViewById(R.id.txt_id_periodePanen);
        textViewSpinnerUmurPanen = view.findViewById(R.id.txtspinnerumurayam);

        editTextBeratEkorPanen = view.findViewById(R.id.edt_berat_perekor_panen);
        editTextQtyEkorPanen = view.findViewById(R.id.edt_qty_perekor_panen);
        editTextQtyEkorGagalPanen = view.findViewById(R.id.edt_qty_perekor_gagal_panen);
        editTextTonasePanen = view.findViewById(R.id.edt_tonase_ayam_panen);

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);


        textViewidUserAyamPanen.setText(getIdUser);
        textViewidKandangAyamPanen.setText(getIdKandang);
        textViewidChickinAyamPanen.setText(getIdChikcin);
        textViewKKodeKandangAyamPanen.setText(getKodeKandang);


        SpinnerUmurAyamPanen();
        selectPickerDate();
        loadDataChickin();
        loadDataKandang();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!editTextBeratEkorPanen.getText().toString().equals("") && !editTextQtyEkorPanen.getText().toString().equals("")){
                    double temp1 = Double.parseDouble(editTextBeratEkorPanen.getText().toString());
                    double temp2 = Double.parseDouble(editTextQtyEkorPanen.getText().toString());
                    int temp3 = Integer.parseInt(textViewTotalAyamSekarangPanen.getText().toString());
                    double hasil = temp1*temp2;
                    double tempTonase = hasil/1000;
//                    int temp3 = Integer.parseInt(editTextQtyEkorGagalPanen.getText().toString());
                    editTextTonasePanen.setText(String.valueOf(tempTonase));
                    editTextQtyEkorGagalPanen.setText(String.valueOf(temp3 - temp2 ));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editTextBeratEkorPanen.addTextChangedListener(textWatcher);
        editTextQtyEkorPanen.addTextChangedListener(textWatcher);

        autoCompleteTextViewUmurPanen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "Selected Item: " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                textViewSpinnerUmurPanen.setText(adapterView.getItemAtPosition(i).toString());
            }
        });






        materialButtonPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    umur = Integer.parseInt(textViewSpinnerUmurPanen.getText().toString());

                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                }

                if (autoCompleteTextViewUmurPanen.getText().toString().equals("Pilih Usia Panen")) {
                    Toast.makeText(getContext(), "Harap Masukkan Usia Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else if (textViewTanggalAyamPanen.getText().equals("Tanggal Panen")) {
                    Toast.makeText(getContext(), "Harap Masukkan Tanggal Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else if (editTextBeratEkorPanen.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Harap Masukkan Berat Ayam Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else if (editTextQtyEkorPanen.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Harap Masukkan Jumlah Ayam Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else if (editTextTonasePanen.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Harap Masukkan Berat Ayam Dan Jumlah Ayam Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else if (umur < 30) {
                    Toast.makeText(getContext(), "Maaf Umur Ayam Belum Memasuki Masa Panen", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    tambahPanen();
                }

//                if (autoCompleteTextViewUmurPanen.getText().toString().equals("Pilih Usia Panen")){
//                    Toast.makeText(getContext(), "Harap Masukkan Usia Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                }else if (textViewTanggalAyamPanen.getText().equals("Tanggal Panen")){
//                    Toast.makeText(getContext(), "Harap Masukkan Tanggal Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                }else if (editTextBeratEkorPanen.getText().toString().trim().isEmpty()){
//                    Toast.makeText(getContext(), "Harap Masukkan Berat Ayam Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                }else if (editTextQtyEkorPanen.getText().toString().trim().isEmpty()){
//                    Toast.makeText(getContext(), "Harap Masukkan Jumlah Ayam Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                }else if (editTextTonasePanen.getText().toString().trim().isEmpty()){
//                    Toast.makeText(getContext(), "Harap Masukkan Berat Ayam Dan Jumlah Ayam Panen Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                }else
//                    if (30 > umur){
//                    Toast.makeText(getContext(), "Maaf Umur Ayam Belum Memasuki Masa Panen", Toast.LENGTH_SHORT).show();
//                } else
//                 {
//                     Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
//                    tambahPanen();
//                }

            }
        });



        return view;
    }

    public void SpinnerUmurAyamPanen(){
        String[] umurAyam = getResources().getStringArray(R.array.umur_ayam);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(),R.layout.dropdown_item,umurAyam);
        autoCompleteTextViewUmurPanen.setAdapter(arrayAdapter);
//        textViewSpinnerUmurPanen.setText(autoCompleteTextViewUmurPanen);


    }

    public void selectPickerDate(){
        textViewTanggalAyamPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);
                datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDate) {
                        textViewTanggalAyamPanen.setText(mYear + "-" + (mMonth+1) + "-" + mDate);

                    }
                },year,month,date);

                datePickerDialog.show();

            }
        });
    }

    public void tambahPanen() {
//        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        Call<BaseResponse.BaseResponseApi> call = service.tambahPanen(
                autoCompleteTextViewUmurPanen.getText().toString(),
                textViewPeriodePanen.getText().toString(),
                textViewTanggalAyamPanen.getText().toString(),
                editTextBeratEkorPanen.getText().toString(),
                editTextQtyEkorPanen.getText().toString(),
                editTextTonasePanen.getText().toString(),
                editTextQtyEkorGagalPanen.getText().toString(),
                textViewidUserAyamPanen.getText().toString(),
                textViewidKandangAyamPanen.getText().toString(),
                textViewidChickinAyamPanen.getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Tambah Panen Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Tambah Panen Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });

    }

    public void loadDataChickin(){
        ////Chickin get data API Call
        Call<BaseResponse.BaseResponseApi<List<DataChickin>>> calls = service.apiReadChickinWhereId(getIdKandang);
        calls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataChickin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataChickin>>> call, Response<BaseResponse.BaseResponseApi<List<DataChickin>>> response) {
                if (response.code() == 200) {
                    datas = response.body().getData();
                    for (DataChickin data2 : datas){
                        Log.d("cek2", "onResponse: " + data2.getPopulasi_masuk());
                        textViewDOCPanen.setText(data2.getType_produk());
                        textViewPeriodePanen.setText(data2.getPeriode());
                        textViewTotalAyamSekarangPanen.setText(data2.getTotal_ayam_saat_ini());

                    }
                } else if (response.code() == 500) {
                    textViewDOCPanen.setText("0");
                    textViewDOCPanen.setText("DOC Belum Masuk Atau Kosong");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataChickin>>> call, Throwable t){

            }
        });

    }

    private void loadDataKandang(){
        Call<BaseResponse.BaseResponseApi<List<Data>>> call = service.apiReadWhereId(getIdKandang);
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<Data>>> response) {
                if (response.code() == 200) {
                    data = response.body().getData();
                    for (Data data1 : data){
                        Log.d("cek", "onResponse: " + data1.getKodekandang());





                    }
                } else if (response.code() == 500) {
                    textViewDOCPanen.setText("0");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t){

            }
        });
    }
}