package org.d3ifcool.testing.PengecekanSampel;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataHarianMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataSampelMenu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditCheckSampel extends Fragment {



    TextView txtidCheckSampel;
    TextView txtidKandang;
    RelativeLayout buttonUbahSampel;

    EditText editTextTotalAyamSaatIni;
    EditText editTextKondisiAyamSampelEdit;
    EditText editTextJumlahAyamSampelEdit1;
    EditText editTextTotalBobotAyamSampelEdit1;

    MaterialButton materialButtonPerubahan;



    EditText editTextJumlahAyamSampel;
    TextView txtTotalBobotAyam;

    Spinner spinnerUmurAyamSampel;
    Spinner spinnerJenisAyamSampel;
    TextView buttonAdd;
    
    CheckBox checkBoxubah;

    EditText textIn;
    TextView txtBobotRataRata;
    TextView txtBobotRataRataEdit1;
    RelativeLayout buttonShowAll;
    LinearLayout linearLayout;
    int count = 1;

    RelativeLayout btncek;

    private static final String ID_CHECK_SAMPEL = "id";
    private static final String ID_KANDANG = "idkandang";
    private static final String UMUR_AYAM = "umur_ayam";
    private static final String JENIS_AYAM = "jenis_ayam";
    private static final String TOTAL_AYAM = "totalayam";
    private static final String KONDISI_AYAM = "kondisiayam";
    private static final String BOBOT_AYAM = "bobotayam";
    private static final String JUMLAH_AYAM_SAMPEL = "jumlahayam";
    private static final String BOBOT_RATARATA = "bobotratarata";


    private String getIdCheckSampel;
    private String getIdKandang;
    private String getUmurAyam;
    private String getJenisAyam;
    private String getTotalAyam;
    private String getKondisiAyam;
    private String getJumlahAyamSampel;
    private String getBobotAyam;
    private String getBobotRatarata;

    LinearLayout linearLayoutUbahBobotDanJumlah;

    private DataService service;

    public EditCheckSampel() {
        // Required empty public constructor
    }


    public static EditCheckSampel newInstance(
            String idsampel,
            String idkandang,
            String umurayam,
            String jenisayam,
            String totalayam,
            String kondisiayam,
            String jumlahayam,
            String bobotayam,
            String bobotratarata) {
        EditCheckSampel fragment = new EditCheckSampel();
        Bundle args = new Bundle();
        args.putString(ID_CHECK_SAMPEL, idsampel);
        args.putString(ID_KANDANG, idkandang);
        args.putString(UMUR_AYAM, umurayam);
        args.putString(JENIS_AYAM, jenisayam);
        args.putString(TOTAL_AYAM, totalayam);
        args.putString(KONDISI_AYAM, kondisiayam);
        args.putString(JUMLAH_AYAM_SAMPEL, jumlahayam);
        args.putString(BOBOT_AYAM, bobotayam);
        args.putString(BOBOT_RATARATA, bobotratarata);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getIdCheckSampel = getArguments().getString(ID_CHECK_SAMPEL);
            getIdKandang = getArguments().getString(ID_KANDANG);
            getJenisAyam = getArguments().getString(JENIS_AYAM);
            getUmurAyam = getArguments().getString(UMUR_AYAM);
            getJenisAyam = getArguments().getString(JENIS_AYAM);
            getTotalAyam = getArguments().getString(TOTAL_AYAM);
            getKondisiAyam = getArguments().getString(KONDISI_AYAM);
            getJumlahAyamSampel = getArguments().getString(JUMLAH_AYAM_SAMPEL);
            getBobotAyam = getArguments().getString(BOBOT_AYAM);
            getBobotRatarata = getArguments().getString(BOBOT_RATARATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view  = inflater.inflate(R.layout.fragment_ediit_check_sampel, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolBarEditCekSampel);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        txtidCheckSampel = view.findViewById(R.id.txt_id_checksampel);
        txtidKandang = view.findViewById(R.id.txt_id_kandang_checksampel);

        txtidCheckSampel.setText(getIdCheckSampel);
        txtidKandang.setText(getIdKandang);

        textIn = view.findViewById(R.id.textin_edit);

        spinnerJenisAyamSampel = view.findViewById(R.id.spinner_jenis_ayam_sampel_edit);
        spinnerUmurAyamSampel = view.findViewById(R.id.spinner_umur_ayam_sampel_edit);

        editTextTotalAyamSaatIni = view.findViewById(R.id.edt_total_ayam_saat_ini_edit);
        editTextTotalAyamSaatIni.setText(getTotalAyam);

        editTextKondisiAyamSampelEdit = view.findViewById(R.id.edt_kondisi_ayam_sampel_edit);
        editTextKondisiAyamSampelEdit.setText(getKondisiAyam);


        editTextJumlahAyamSampelEdit1 = view.findViewById(R.id.edt_jumlah_ayam_sampel_edit1);
        editTextJumlahAyamSampelEdit1.setText(getJumlahAyamSampel);

        editTextTotalBobotAyamSampelEdit1 = view.findViewById(R.id.edt_total_bobot_edit1);
        editTextTotalBobotAyamSampelEdit1.setText(getBobotAyam);

        checkBoxubah = view.findViewById(R.id.checkBox_Ubah_BobotJumlah_Sampel);
        linearLayoutUbahBobotDanJumlah = view.findViewById(R.id.linear_ubah_total_bobot_dan_jumlah_sampel);


        editTextJumlahAyamSampel = view.findViewById(R.id.edt_jumlah_ayam_sampel_edit);
        txtTotalBobotAyam = view.findViewById(R.id.txt_bobot_cek_edit);


        txtBobotRataRataEdit1 = view.findViewById(R.id.txt_bobot_rata_rata_cek_edit1);
        txtBobotRataRataEdit1.setText(getBobotRatarata);

        checkBoxubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxubah.isChecked()){
                    linearLayoutUbahBobotDanJumlah.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutUbahBobotDanJumlah.setVisibility(View.GONE);
                    editTextJumlahAyamSampelEdit1.setText(getJumlahAyamSampel);
                    editTextTotalBobotAyamSampelEdit1.setText(getBobotAyam);
                    txtBobotRataRataEdit1.setText(getBobotRatarata);
                }
            }
        });

        materialButtonPerubahan = view.findViewById(R.id.button_simpan_perubahan_sampel);
        materialButtonPerubahan.setOnClickListener(new View.OnClickListener() {
            private boolean state = false;
            @Override
            public void onClick(View view) {
                if (state){
                    state = false;
                    materialButtonPerubahan.setText("Simpan Perubahan");
                    editTextJumlahAyamSampelEdit1.setText(getJumlahAyamSampel);
                    editTextTotalBobotAyamSampelEdit1.setText(getBobotAyam);
                    txtBobotRataRataEdit1.setText(getBobotRatarata);
                    materialButtonPerubahan.setBackgroundColor(Color.parseColor("#5666d5"));
                }else {
                    state = true;
                    String perubahanJumlah = editTextJumlahAyamSampel.getText().toString();
                    String perubahanTotalBobot = txtTotalBobotAyam.getText().toString();
                    String perubahanTotalBobotRataRata = txtBobotRataRata.getText().toString();
                    editTextJumlahAyamSampelEdit1.setText(perubahanJumlah);
                    editTextTotalBobotAyamSampelEdit1.setText(perubahanTotalBobot);
                    txtBobotRataRataEdit1.setText(perubahanTotalBobotRataRata);
                    materialButtonPerubahan.setText("Batalkan Perubahan");
                    materialButtonPerubahan.setBackgroundColor(Color.parseColor("#ee2737"));

                }
            }
        });




        SpinnerJenisAyamSampel();
        SpinnerUmurAyamSampel();



        buttonAdd = view.findViewById(R.id.add_edit);
        linearLayout = view.findViewById(R.id.layout_list_edit);

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (textIn.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Harap Masukkan Bobot Sampel", Toast.LENGTH_SHORT).show();
                }else {
                    final View addView = layoutInflater.inflate(R.layout.row_tambah_bobot_sampel, null);
                    final TextView textOut = (TextView)addView.findViewById(R.id.textout);
                    final TextView texttitle = (TextView)addView.findViewById(R.id.txt_title_bobot);

                    texttitle.setText("Bobot Sampel "+count++ + ":");
                    textOut.setText(textIn.getText().toString());
                    textIn.setText("");
                    TextView buttonRemove = (TextView) addView.findViewById(R.id.remove);
                    buttonRemove.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            texttitle.setText("Bobot Sampel "+count--);
                            ((LinearLayout)addView.getParent()).removeView(addView);
                        }});

                    linearLayout.addView(addView, 0);
                }

            }
        });

        txtBobotRataRata = view.findViewById(R.id.txt_bobot_rata_rata_cek_edit);
        btncek = view.findViewById(R.id.btn_hitung_bobot_cek_edit);
        btncek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (editTextJumlahAyamSampel.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Harap Masukkan Jumlah Sampel Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    float hasil;
                    float totalbobotayam;
                    float hitungBobotRataRata;
                    float jumlahsample = Float.parseFloat(editTextJumlahAyamSampel.getText().toString());
                    totalbobotayam = Float.parseFloat(txtTotalBobotAyam.getText().toString());

                    hasil = totalbobotayam*100/1000;

                    hitungBobotRataRata = hasil/jumlahsample;
                    txtBobotRataRata.setText(hitungBobotRataRata+"");
                }


//                Toast.makeText(getContext(), hitungBobotRataRata+"", Toast.LENGTH_SHORT).show();

            }
        });

        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        buttonShowAll = (RelativeLayout) view.findViewById(R.id.showall_edit);
        buttonShowAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                sumMyIntValues();
            }});

        buttonUbahSampel = view.findViewById(R.id.btn_simpan_edit_sampel);

        buttonUbahSampel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSampel();
            }
        });
        return view;
    }

    private float sumMyIntValues() {
        float sum = 0;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            TextView myView = (TextView) v.findViewById(R.id.textout);
            sum = sum + Float.parseFloat(myView.getText().toString());
        }

        Toast.makeText(getContext(),  sum+"", Toast.LENGTH_SHORT).show();
        txtTotalBobotAyam.setText(sum+"");

        return sum;
    }

    public void SpinnerJenisAyamSampel(){
        String[] jenisAyam = getResources().getStringArray(R.array.jenis_ayam);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,jenisAyam);
        spinnerJenisAyamSampel.setAdapter(arrayAdapter);
        spinnerJenisAyamSampel.setSelection(arrayAdapter.getPosition(getJenisAyam));
    }

    public void SpinnerUmurAyamSampel(){
        String[] umurAyam = getResources().getStringArray(R.array.umur_ayam);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,umurAyam);
        spinnerUmurAyamSampel.setAdapter(arrayAdapter);
        spinnerUmurAyamSampel.setSelection(arrayAdapter.getPosition(getUmurAyam));
    }

    public void UpdateSampel() {
        Call<BaseResponse.BaseResponseApi> call = service.updateCheckSampel(
                getIdCheckSampel,
                spinnerUmurAyamSampel.getSelectedItem().toString(),
                editTextKondisiAyamSampelEdit.getText().toString(),
                txtBobotRataRataEdit1.getText().toString(),
                spinnerJenisAyamSampel.getSelectedItem().toString(),
                editTextTotalBobotAyamSampelEdit1.getText().toString(),
                editTextJumlahAyamSampelEdit1.getText().toString(),
                getIdKandang
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {

                    Toast.makeText(getContext(), "Ubah Data Sampel Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Ubah Data Harian Gagal", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });
    }
}