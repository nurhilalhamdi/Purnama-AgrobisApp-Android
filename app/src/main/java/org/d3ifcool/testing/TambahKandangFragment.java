package org.d3ifcool.testing;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.RestAPI.APIRequestData;
import org.d3ifcool.testing.RestAPI.DataAPI;
import org.d3ifcool.testing.RestAPI.Server;

import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionKabAdapter;
import meridianid.farizdotid.actdaerahindonesia.adapter.SuggestionProvAdapter;
import meridianid.farizdotid.actdaerahindonesia.util.JsonParse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahKandangFragment extends Fragment {




    private TextView textViewAlamat;
    private TextView txtiduser;

    private RelativeLayout btnTambahKandang;


    EditText editTextKabKota;
    EditText editTextKecamatan;
    EditText editTextJalan;
    EditText editTextKodeKandang;
    EditText editTextKodeBlok;

    AutoCompleteTextView autoCompleteTextViewJenisKandang;




    APIRequestData apiRequestData;
    private DataService service;

    public TambahKandangFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_tambah_kandang, container, false);

        txtiduser = view.findViewById(R.id.txt_id_user_tambah_kandang);
        textViewAlamat = view.findViewById(R.id.text_tambah_kandang_alamat);
        editTextKabKota = view.findViewById(R.id.actKabKota);
        editTextKecamatan = view.findViewById(R.id.actKecamatan);
        editTextJalan = view.findViewById(R.id.actJalan);
        editTextKodeKandang = view.findViewById(R.id.text_input_kode_kandang);
        editTextKodeBlok = view.findViewById(R.id.text_input_kode_blok_kandang);

        btnTambahKandang = view.findViewById(R.id.btn_tambah_kandang);

        Toolbar toolbar = view.findViewById(R.id.toolBarTambahKandang);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String kabkota = editTextKabKota.getText().toString();
                String kec = editTextKecamatan.getText().toString();
                String jal = editTextJalan.getText().toString();

                textViewAlamat.setText(jal + ", " + kabkota.toUpperCase() + " - " + kec.toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editTextKecamatan.addTextChangedListener(textWatcher);
        editTextKabKota.addTextChangedListener(textWatcher);
        editTextJalan.addTextChangedListener(textWatcher);

        txtiduser.setText(MainActivity.prefConfig.readId());

        btnTambahKandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editTextKabKota.getText().toString())){
                    editTextKabKota.setError("Harap Isi Kabupaten/Kota");
                }else if (TextUtils.isEmpty(editTextKecamatan.getText().toString())){
                    editTextKecamatan.setError("Harap Isi Kecamatan");
                }else if (TextUtils.isEmpty(editTextJalan.getText().toString())){
                    editTextJalan.setError("Harap Isi Jalan/Alamat");
                }else if (TextUtils.isEmpty(editTextKodeKandang.getText().toString())){
                    editTextKodeKandang.setError("Harap Isi Jalan/Alamat");
                }else if (TextUtils.isEmpty(editTextKodeBlok.getText().toString())){
                    editTextKodeBlok.setError("Harap Isi Kode Blok");
                }else if (TextUtils.isEmpty(editTextKodeKandang.getText().toString())){
                    editTextKodeKandang.setError("Harap Isi Kode Kandang");
                }else if (autoCompleteTextViewJenisKandang.getText().toString().equals("Jenis Kandang")){
                    autoCompleteTextViewJenisKandang.setError("Harap Pilih Jenis Kandang");
                }
                else {
                tambahKandang();
//                    Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] jenisKandang = getResources().getStringArray(R.array.jenis_kandang);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(),R.layout.dropdown_item,jenisKandang);
        autoCompleteTextViewJenisKandang = view.findViewById(R.id.autoCompleteJenisKandang);
        autoCompleteTextViewJenisKandang.setAdapter(arrayAdapter);

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        return view;
    }

    public void tambahKandang() {
//        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        Call<BaseResponse.BaseResponseApi> call = service.tambahKandang(
                textViewAlamat.getText().toString(),
                editTextKodeKandang.getText().toString(),
                editTextKodeBlok.getText().toString(),
                autoCompleteTextViewJenisKandang.getText().toString(),
                txtiduser.getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Tambah Kandang Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Tambah Kandang Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });

    }


}