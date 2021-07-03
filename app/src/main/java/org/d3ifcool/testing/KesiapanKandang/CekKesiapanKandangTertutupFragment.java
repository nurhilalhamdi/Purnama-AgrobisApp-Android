package org.d3ifcool.testing.KesiapanKandang;

import android.graphics.Color;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekKesiapanKandangTertutupFragment extends Fragment {


    private static final String SYARAT_1 = "syarat_1";
    private static final String SYARAT_2 = "syarat_2";
    private static final String SYARAT_3 = "syarat_3";
    private static final String SYARAT_4 = "syarat_4";
    private static final String SYARAT_5 = "syarat_5";
    private static final String SYARAT_6 = "syarat_6";
    private static final String SYARAT_7 = "syarat_7";
    private static final String SYARAT_8 = "syarat_8";
    private static final String SYARAT_9 = "syarat_9";
    private static final String SYARAT_10 = "syarat_10";
    private static final String ID_KANDANG = "id_kandang";
    private static final String STATUS_KANDANG = "status_kandang";


    SwitchCompat switchCompat;
    RelativeLayout btnSimpan;
    private DataService service;

    RadioButton radioButtonYA1;
    RadioButton radioButtonYA2;
    RadioButton radioButtonYA3;
    RadioButton radioButtonYA4;
    RadioButton radioButtonYA5;
    RadioButton radioButtonYA6;
    RadioButton radioButtonYA7;
    RadioButton radioButtonYA8;
    RadioButton radioButtonYA9;
    RadioButton radioButtonYA10;

    RadioButton radioButtonTIDAK1;
    RadioButton radioButtonTIDAK2;
    RadioButton radioButtonTIDAK3;
    RadioButton radioButtonTIDAK4;
    RadioButton radioButtonTIDAK5;
    RadioButton radioButtonTIDAK6;
    RadioButton radioButtonTIDAK7;
    RadioButton radioButtonTIDAK8;
    RadioButton radioButtonTIDAK9;
    RadioButton radioButtonTIDAK10;


    RadioGroup radioGroup;
    RadioGroup radioGroup2;
    RadioGroup radioGroup4;
    RadioGroup radioGroup6;
    RadioGroup radioGroup7;
    RadioGroup radioGroup8;
    RadioGroup radioGroup9;
    RadioGroup radioGroup10;
    RadioGroup radioGroup11;
    RadioGroup radioGroup12;
    int nilai1;
    int nilai2;
    int nilai3;
    int nilai4;
    int nilai5;
    int nilai6;
    int nilai7;
    int nilai8;
    int nilai9;
    int nilai10;
    int nilai11;
    int nilai12;
    float hasil;
    float hitung;
    String idkandang;
    String rg1;
    String rg2;
    String rg3;
    String rg4;
    String rg5;
    String rg6;
    String rg7;
    String rg8;
    String rg9;
    String rg10;
    String syarat1;
    String syarat2;
    String syarat3;
    String syarat4;
    String syarat5;
    String syarat6;
    String syarat7;
    String syarat8;
    String syarat9;
    String syarat10;
    String statusKandang;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_cek_kesiapan_kandang_tertutup, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarKesiapanKandangTertutup);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);


        idkandang = getArguments().getString(ID_KANDANG);
        statusKandang = getArguments().getString(STATUS_KANDANG);
        syarat1 = getArguments().getString(SYARAT_1);
        syarat2 = getArguments().getString(SYARAT_2);
        syarat3 = getArguments().getString(SYARAT_3);
        syarat4 = getArguments().getString(SYARAT_4);
        syarat5 = getArguments().getString(SYARAT_5);
        syarat6 = getArguments().getString(SYARAT_6);
        syarat7 = getArguments().getString(SYARAT_7);
        syarat8 = getArguments().getString(SYARAT_8);
        syarat9 = getArguments().getString(SYARAT_9);
        syarat10 = getArguments().getString(SYARAT_10);



        radioGroup = view.findViewById(R.id.radio_grup_1_tertutup);
        radioGroup2 = view.findViewById(R.id.radio_grup_2_tertutup);
        radioGroup4 = view.findViewById(R.id.radio_grup_4_tertutup);
        radioGroup6 = view.findViewById(R.id.radio_grup_6_tertutup);
        radioGroup7 = view.findViewById(R.id.radio_grup_7_tertutup);
        radioGroup8 = view.findViewById(R.id.radio_grup_8_tertutup);
        radioGroup9 = view.findViewById(R.id.radio_grup_9_tertutup);
        radioGroup10 = view.findViewById(R.id.radio_grup_10_tertutup);
        radioGroup11 = view.findViewById(R.id.radio_grup_11_tertutup);
        radioGroup12 = view.findViewById(R.id.radio_grup_12_tertutup);

        radioButtonYA1 = view.findViewById(R.id.rbYa1_tertutup);
        radioButtonYA2 = view.findViewById(R.id.rbYa2_tertutup);
        radioButtonYA3 = view.findViewById(R.id.rbYa4_tertutup);
        radioButtonYA4 = view.findViewById(R.id.rbYa6_tertutup);
        radioButtonYA5 = view.findViewById(R.id.rbYa7_tertutup);
        radioButtonYA6 = view.findViewById(R.id.rbYa8_tertutup);
        radioButtonYA7 = view.findViewById(R.id.rbYa9_tertutup);
        radioButtonYA8 = view.findViewById(R.id.rbYa10_tertutup);
        radioButtonYA9 = view.findViewById(R.id.rbYa11_tertutup);
        radioButtonYA10 = view.findViewById(R.id.rbYa12_tertutup);

        radioButtonTIDAK1 = view.findViewById(R.id.rbTidak1_tertutup);
        radioButtonTIDAK2 = view.findViewById(R.id.rbTidak2_tertutup);
        radioButtonTIDAK3 = view.findViewById(R.id.rbTidak4_tertutup);
        radioButtonTIDAK4 = view.findViewById(R.id.rbTidak6_tertutup);
        radioButtonTIDAK5 = view.findViewById(R.id.rbTidak7_tertutup);
        radioButtonTIDAK6 = view.findViewById(R.id.rbTidak8_tertutup);
        radioButtonTIDAK7 = view.findViewById(R.id.rbTidak9_tertutup);
        radioButtonTIDAK8 = view.findViewById(R.id.rbTidak10_tertutup);
        radioButtonTIDAK9 = view.findViewById(R.id.rbTidak11_tertutup);
        radioButtonTIDAK10 = view.findViewById(R.id.rbTidak12_tertutup);

        switchCompat = view.findViewById(R.id.switchKandangTertutup);



        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked){
                    statusKandang = "Aktif";
                }else {
                    statusKandang = "Tidak Aktif";
                }
            }
        });

        if(syarat1 != null || syarat2 != null || syarat3 != null || syarat4 != null || syarat5 != null
                || syarat6 != null || syarat7 != null || syarat8 != null || syarat9 != null || syarat10 != null){
            checkedRadiButton();
        }else {
            unCheckedRadioButtonWhenNull();
        }

        btnSimpan = view.findViewById(R.id.btn_simpan_persiapan_kandang_tertutup);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioButtonId1 = radioGroup.getCheckedRadioButtonId();
                int checkId2 = radioGroup2.getCheckedRadioButtonId();
                int checkId4 = radioGroup4.getCheckedRadioButtonId();
                int checkId6 = radioGroup6.getCheckedRadioButtonId();
                int checkId7 = radioGroup7.getCheckedRadioButtonId();
                int checkId8 = radioGroup8.getCheckedRadioButtonId();
                int checkId9 = radioGroup9.getCheckedRadioButtonId();
                int checkId10 = radioGroup10.getCheckedRadioButtonId();
                int checkId11 = radioGroup11.getCheckedRadioButtonId();
                int checkId12 = radioGroup12.getCheckedRadioButtonId();

                findRadioButton(checkedRadioButtonId1, checkId2, checkId4, checkId6,
                        checkId7, checkId8, checkId9, checkId10, checkId11, checkId12);

                if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup2.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup4.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup6.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup7.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup8.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup9.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup10.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup11.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else if (radioGroup12.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getContext(), "Harap Pilih Salah Satu", Toast.LENGTH_SHORT).show();
                }else{

//                    Toast.makeText(getContext(), "berhasil", Toast.LENGTH_SHORT).show();
//
                Log.d("ASD", "onClick: " + rg1);
                Call<BaseResponse.BaseResponseApi> call = service.tambahPersiapanKandang(
                        idkandang, hitung, statusKandang, rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10
                );

                call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
                    @Override
                    public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                        if (response.code() == 200) {

//                            Toast.makeText(getContext(), "Tambah Kesiapan Kandang Berhasil", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar.make(view,"Persiapan Kandang ditambahkan", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.parseColor("#00aa13"));
                            snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);
                            snackbar.show();
                            getFragmentManager().popBackStack();
                        } else {
//                            Toast.makeText(getContext(), "Tambah Kesiapan Kandang Gagal", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar.make(view,"Persiapan Kandang ditambahkan", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.parseColor("#ee2737"));
                            snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);
                            snackbar.show();
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
        });

        Log.d("kd", "onCreateView: " + idkandang);
        return view;
    }


    public void checkedRadiButton(){
        if (syarat1.equals("Ya")){
            radioButtonYA1.setChecked(true);
        }else if (syarat1.equals("Tidak")){
            radioButtonTIDAK1.setChecked(true);
        }

        if (syarat2.equals("Ya")){
            radioButtonYA2.setChecked(true);
        }else if (syarat2.equals("Tidak")){
            radioButtonTIDAK2.setChecked(true);
        }

        if (syarat3.equals("Ya")){
            radioButtonYA3.setChecked(true);
        }else if (syarat3.equals("Tidak")){
            radioButtonTIDAK3.setChecked(true);
        }

        if (syarat4.equals("Ya")){
            radioButtonYA4.setChecked(true);
        }else if (syarat4.equals("Tidak")){
            radioButtonTIDAK4.setChecked(true);
        }

        if (syarat5.equals("Ya")){
            radioButtonYA5.setChecked(true);
        }else if (syarat5.equals("Tidak")){
            radioButtonTIDAK5.setChecked(true);
        }

        if (syarat6.equals("Ya")){
            radioButtonYA6.setChecked(true);
        }else if (syarat6.equals("Tidak")){
            radioButtonTIDAK6.setChecked(true);
        }
        if (syarat7.equals("Ya")){
            radioButtonYA7.setChecked(true);
        }else if (syarat7.equals("Tidak")){
            radioButtonTIDAK7.setChecked(true);
        }
        if (syarat8.equals("Ya")){
            radioButtonYA8.setChecked(true);
        }else if (syarat8.equals("Tidak")){
            radioButtonTIDAK8.setChecked(true);
        }
        if (syarat9.equals("Ya")){
            radioButtonYA9.setChecked(true);
        }else if (syarat9.equals("Tidak")){
            radioButtonTIDAK9.setChecked(true);
        }
        if (syarat10.equals("Ya")){
            radioButtonYA10.setChecked(true);
        }else if (syarat10.equals("Tidak")){
            radioButtonTIDAK10.setChecked(true);
        }

        if (statusKandang.equals("Aktif")){
            switchCompat.setChecked(true);
        }else {
            switchCompat.setChecked(false);
        }
    }

    public void unCheckedRadioButtonWhenNull(){
        radioButtonYA1.setChecked(false);
        radioButtonTIDAK1.setChecked(false);

        radioButtonYA2.setChecked(false);
        radioButtonTIDAK2.setChecked(false);

        radioButtonYA3.setChecked(false);
        radioButtonTIDAK3.setChecked(false);

        radioButtonYA4.setChecked(false);
        radioButtonTIDAK4.setChecked(false);

        radioButtonYA5.setChecked(false);
        radioButtonTIDAK5.setChecked(false);

        radioButtonYA6.setChecked(false);
        radioButtonTIDAK6.setChecked(false);

        radioButtonYA7.setChecked(false);
        radioButtonTIDAK7.setChecked(false);

        radioButtonYA8.setChecked(false);
        radioButtonTIDAK8.setChecked(false);

        radioButtonYA9.setChecked(false);
        radioButtonTIDAK9.setChecked(false);

        radioButtonYA10.setChecked(false);
        radioButtonTIDAK10.setChecked(false);
    }

    public void findRadioButton(int checkId, int checkId2,int checkId3,int checkId4,int checkId5,
                                int checkId6,int checkId7,int checkId8,int checkId9,int checkId10) {

        switch (checkId){
            case R.id.rbYa1_tertutup:
                nilai1 = 5;
                rg1 = "Ya";
                break;
            case R.id.rbTidak1_tertutup:
                nilai1 = 1;
                rg1 = "Tidak";
                break;
        }
        switch (checkId2){
            case R.id.rbYa2_tertutup:
                nilai2 = 5;
                rg2 = "Ya";
                break;
            case R.id.rbTidak2_tertutup:
                nilai2 = 1;
                rg2 = "Tidak";
                break;
        }

        switch (checkId3){
            case R.id.rbYa4_tertutup:
                nilai4 = 5;
                rg3 = "Ya";
                break;
            case R.id.rbTidak4_tertutup:
                nilai4 = 1;
                rg3 = "Tidak";
                break;
        }
        switch (checkId4){
            case R.id.rbYa6_tertutup:
                nilai6 = 5;
                rg4 = "Ya";
                break;
            case R.id.rbTidak6_tertutup:
                nilai6 = 1;
                rg4 = "Tidak";
                break;
        }
        switch (checkId5){
            case R.id.rbYa7_tertutup:
                nilai7 = 5;
                rg5 = "Ya";
                break;
            case R.id.rbTidak7_tertutup:
                nilai7 = 1;
                rg5 = "Tidak";
                break;
        }
        switch (checkId6){
            case R.id.rbYa8_tertutup:
                nilai8 = 5;
                rg6 = "Ya";
                break;
            case R.id.rbTidak8_tertutup:
                nilai8 = 1;
                rg6 = "Tidak";
                break;
        }

        switch (checkId7){
            case R.id.rbYa9_tertutup:
                nilai9 = 5;
                rg7 = "Ya";
                break;
            case R.id.rbTidak9_tertutup:
                nilai9 = 1;
                rg7 = "Tidak";
                break;
        }
        switch (checkId8){
            case R.id.rbYa10_tertutup:
                nilai10 = 5;
                rg8 = "Ya";
                break;
            case R.id.rbTidak10_tertutup:
                nilai10 = 1;
                rg8 = "Tidak";
                break;
        }

        switch (checkId9){
            case R.id.rbYa11_tertutup:
                nilai11 = 5;
                rg9 = "Ya";
                break;
            case R.id.rbTidak11_tertutup:
                nilai11 = 1;
                rg9 = "Tidak";
                break;
        }
        switch (checkId10){
            case R.id.rbYa12_tertutup:
                nilai12 = 5;
                rg10 = "Ya";
                break;
            case R.id.rbTidak12_tertutup:
                nilai12 = 1;
                rg10 = "Tidak";
                break;
        }

        hasil = nilai1 + nilai2  + nilai4 + + nilai6 + nilai7 + nilai8 + nilai9 + nilai10 + nilai11 + nilai12;
        hitung = hasil/50*100;
    }


    public static CekKesiapanKandangTertutupFragment newInstance(
            String id_kandang,
            String status_kandang,
            String syarat1,
            String syarat2,
            String syarat3,
            String syarat4,
            String syarat5,
            String syarat6,
            String syarat7,
            String syarat8,
            String syarat9,
            String syarat10) {

        Bundle args = new Bundle();
        CekKesiapanKandangTertutupFragment fragment = new CekKesiapanKandangTertutupFragment();
        args.putString(ID_KANDANG,id_kandang);
        args.putString(STATUS_KANDANG,status_kandang);
        args.putString(SYARAT_1,syarat1);
        args.putString(SYARAT_2,syarat2);
        args.putString(SYARAT_3,syarat3);
        args.putString(SYARAT_4,syarat4);
        args.putString(SYARAT_5,syarat5);
        args.putString(SYARAT_6,syarat6);
        args.putString(SYARAT_7,syarat7);
        args.putString(SYARAT_8,syarat8);
        args.putString(SYARAT_9,syarat9);
        args.putString(SYARAT_10,syarat10);
        fragment.setArguments(args);

        return fragment;
    }
}