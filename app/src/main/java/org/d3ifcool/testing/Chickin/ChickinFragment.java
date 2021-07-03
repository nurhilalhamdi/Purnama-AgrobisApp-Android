package org.d3ifcool.testing.Chickin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.KesiapanKandang.CekKesiapanKandangTerbukaFragment;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChickinFragment extends Fragment {


    private static final String ID_KANDANG = "idkandang" ;
    private static final String POPULASI = "populasi";
    private static final String ID_CHICKIN = "idchickin";
    private static final String TYPE = "type";
    private static final String KONDISI = "kondisi";
    private static final String HARGA = "harga";
    private static final String JENIS = "jenis";
    private static final String TANGGAL = "tanggal";
    private static final String PERIODE = "periode";
    private static final String BOBOT = "Bobot";
    RelativeLayout btnTambahChickin;
    TextView txtsetTanggalChickin;
    DatePickerDialog datePickerDialog;
    AutoCompleteTextView autoCompleteTextViewTypeProduk;
    AutoCompleteTextView autoCompleteTextViewJenisProduk;
    EditText editTextJumlahChickin;
    EditText editTextKondisiChickin;
    EditText editTextHargaSatuanChickin;
    EditText editTextPeriodeChickin;
    EditText editTextBeratDOC;
    String getIdChickin;
    String getType;
    String getPopulasi;
    String getKondisi;
    String getHarga;
    String getJenis;
    String getTanggal;
    String getIdKandang;
    String getPeriode;
    String getBobot;
    Spinner spinnerType;
    Spinner spinnerJenis;

    private DataService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_chickin, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarChickin);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        editTextJumlahChickin = view.findViewById(R.id.edt_jumlah_chickin);
        editTextHargaSatuanChickin = view.findViewById(R.id.edt_harga_satuan_chickin);
        editTextKondisiChickin = view.findViewById(R.id.edt_kondisi_ayam_chickin);
        editTextPeriodeChickin = view.findViewById(R.id.edt_periode_ayam_chickin);
        editTextBeratDOC = view.findViewById(R.id.edt_berat_doc);
        txtsetTanggalChickin = view.findViewById(R.id.txt_tanggal_ayam_chickin);
        txtsetTanggalChickin = view.findViewById(R.id.txt_tanggal_ayam_chickin);
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
        getIdKandang = getArguments().getString(ID_KANDANG);
        getPopulasi = getArguments().getString(POPULASI);
        getHarga = getArguments().getString(HARGA);
        getIdChickin = getArguments().getString(ID_CHICKIN);
        getJenis = getArguments().getString(JENIS);
        getType = getArguments().getString(TYPE);
        getKondisi = getArguments().getString(KONDISI);
        getTanggal = getArguments().getString(TANGGAL);
        getPeriode = getArguments().getString(PERIODE);
        getBobot = getArguments().getString(BOBOT);
//
//        String[] typeProduk = getResources().getStringArray(R.array.type_produk);
//        ArrayAdapter arrayAdapterTypeProduk = new ArrayAdapter(requireContext(),R.layout.dropdown_item,typeProduk);
//        autoCompleteTextViewTypeProduk.setAdapter(arrayAdapterTypeProduk);
//
//        String[] jenisProduk = getResources().getStringArray(R.array.jenis_produk);
//        ArrayAdapter arrayAdapterJenisProduk = new ArrayAdapter(requireContext(),R.layout.dropdown_item,jenisProduk);
//        autoCompleteTextViewJenisProduk.setAdapter(arrayAdapterJenisProduk);

        spinnerType = view.findViewById(R.id.spinnerType);
        spinnerType.setPrompt("Pilih Type DOC");
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.type_produk));
        spinnerType.setAdapter(arrayAdapterType);


        spinnerJenis = view.findViewById(R.id.spinnerJenis);
        spinnerType.setPrompt("Pilih Jenis DOC");
        ArrayAdapter<String> arrayAdapterJenis = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.jenis_produk));
        spinnerJenis.setAdapter(arrayAdapterJenis);
        txtsetTanggalChickin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);
                datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDate) {
                            txtsetTanggalChickin.setText(mYear + "-" + (mMonth+1) + "-" + mDate);

                    }
                },year,month,date);

                datePickerDialog.show();

            }
        });


        if (getPopulasi.equals("0")){
            editTextJumlahChickin.setText("");
            editTextHargaSatuanChickin.setText("");
            editTextKondisiChickin.setText("");
            txtsetTanggalChickin.setText("");
        }else {

            SpinnerAdapter adapterType = spinnerType.getAdapter();
            for (int i = 0; i < adapterType.getCount(); i++) {
                if (spinnerType.getItemAtPosition(i).equals(getType)) {
                    spinnerType.setSelection(i);
                }
            }


            SpinnerAdapter adapterJenis = spinnerJenis.getAdapter();
            for (int i = 0; i < adapterJenis.getCount(); i++) {
                if (spinnerJenis.getItemAtPosition(i).equals(getJenis)) {
                    spinnerJenis.setSelection(i);
                }
            }
            editTextJumlahChickin.setText(getPopulasi);
            editTextHargaSatuanChickin.setText(getHarga);
            editTextKondisiChickin.setText(getKondisi);
            editTextPeriodeChickin.setText(getPeriode);
            editTextBeratDOC.setText(getBobot);
            txtsetTanggalChickin.setText(getTanggal);
        }


        btnTambahChickin = view.findViewById(R.id.btn_simpan_chickin);
        btnTambahChickin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPopulasi.equals("0")){
                    if (TextUtils.isEmpty(editTextPeriodeChickin.getText().toString())){
                        editTextPeriodeChickin.setError("Harap Isi Periode Jumlah Chickin");
                    }

                    else if (spinnerType.getSelectedItem().equals("-- Pilih Type Produk --")) {
                        ((TextView)spinnerType.getChildAt(0)).setError("Harap Pilih Type Produk");
                    }

                    else if (spinnerJenis.getSelectedItem().equals("-- Pilih Jenis Produk --")) {
                        ((TextView)spinnerJenis.getChildAt(0)).setError("Harap Pilih Jenis Produk");
                    }

                    else if (TextUtils.isEmpty(editTextJumlahChickin.getText().toString())){
                        editTextJumlahChickin.setError("Harap Masukkan Jumlah Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextBeratDOC.getText().toString())){
                        editTextBeratDOC.setError("Harap Masukkan Berat Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextKondisiChickin.getText().toString())){
                        editTextKondisiChickin.setError("Harap Masukkan Kondisi Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextHargaSatuanChickin.getText().toString())){
                        editTextHargaSatuanChickin.setError("Harap Masukkan Harga Chickin");
                    }

                    else if (TextUtils.isEmpty(txtsetTanggalChickin.getText().toString())){
                        txtsetTanggalChickin.setError("Harap Masukkan Tanggal Chickin");
                    }

                    else {

                        tambahChickin();
                    }

                }else {
                    if (TextUtils.isEmpty(editTextPeriodeChickin.getText().toString())){
                        editTextPeriodeChickin.setError("Harap Isi Periode Jumlah Chickin");
                    }

                    else if (spinnerType.getSelectedItem().equals("-- Pilih Type Produk --")) {
                        ((TextView)spinnerType.getChildAt(0)).setError("Harap Pilih Type Produk");
                    }

                    else if (spinnerJenis.getSelectedItem().equals("-- Pilih Jenis Produk --")) {
                        ((TextView)spinnerJenis.getChildAt(0)).setError("Harap Pilih Jenis Produk");
                    }

                    else if (TextUtils.isEmpty(editTextJumlahChickin.getText().toString())){
                        editTextJumlahChickin.setError("Harap Masukkan Jumlah Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextBeratDOC.getText().toString())){
                        editTextBeratDOC.setError("Harap Masukkan Berat Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextKondisiChickin.getText().toString())){
                        editTextKondisiChickin.setError("Harap Masukkan Kondisi Chickin");
                    }

                    else if (TextUtils.isEmpty(editTextHargaSatuanChickin.getText().toString())){
                        editTextHargaSatuanChickin.setError("Harap Masukkan Harga Chickin");
                    }

                    else if (TextUtils.isEmpty(txtsetTanggalChickin.getText().toString())){
                        txtsetTanggalChickin.setError("Harap Masukkan Tanggal Chickin");
                    }

                    else {

                        updateChickIn();
                    }

                }
            }
        });
        return view;
    }

    public void updateChickIn(){
        Call<BaseResponse.BaseResponseApi> call = service.updateChickin(
                getIdChickin,
                spinnerType.getSelectedItem().toString(),
                editTextJumlahChickin.getText().toString(),
                editTextKondisiChickin.getText().toString(),
                editTextHargaSatuanChickin.getText().toString(),
                spinnerJenis.getSelectedItem().toString(),
                txtsetTanggalChickin.getText().toString(),
                getIdKandang,
                editTextPeriodeChickin.getText().toString(),
                editTextJumlahChickin.getText().toString(),
                editTextBeratDOC.getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {

                    Toast.makeText(getContext(), "Tambah Kesiapan Kandang Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Tambah Kesiapan Kandang Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });
    }




    public void tambahChickin() {
//        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        Call<BaseResponse.BaseResponseApi> call = service.tambahChickin(
                spinnerType.getSelectedItem().toString(),
                editTextJumlahChickin.getText().toString(),
                editTextKondisiChickin.getText().toString(),
                editTextHargaSatuanChickin.getText().toString(),
                spinnerJenis.getSelectedItem().toString(),
                txtsetTanggalChickin.getText().toString(),
                getIdKandang,
                editTextPeriodeChickin.getText().toString(),
                editTextJumlahChickin.getText().toString(),
                editTextBeratDOC.getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Tambah Chickin Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Tambah Chickin Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });

    }

    public static ChickinFragment newInstance(
            String id_chickin,
            String type_produk,
            String populasi,
            String kondisi,
            String harga,
            String jenis,
            String tanggal,
            String id_kandang,
            String periode,
            String bobot) {

        Bundle args = new Bundle();
        ChickinFragment fragment = new ChickinFragment();
        args.putString(ID_CHICKIN,id_chickin);
        args.putString(TYPE,type_produk);
        args.putString(POPULASI,populasi);
        args.putString(KONDISI,kondisi);
        args.putString(HARGA,harga);
        args.putString(JENIS,jenis);
        args.putString(TANGGAL,tanggal);
        args.putString(ID_KANDANG,id_kandang);
        args.putString(PERIODE,periode);
        args.putString(BOBOT,bobot);
        fragment.setArguments(args);

        return fragment;
    }

}