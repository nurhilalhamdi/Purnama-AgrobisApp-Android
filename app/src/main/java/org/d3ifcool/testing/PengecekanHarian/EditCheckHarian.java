package org.d3ifcool.testing.PengecekanHarian;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataHarianMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCheckHarian extends Fragment {



    MaterialButton buttonUbahHarian;

    TextInputLayout editTextJumlahAyamMati_edit;
    TextInputLayout editTextJumlahAyamSakit_edit;
    TextInputLayout editTextGejalaSakit_edit;
    TextInputLayout editTextBerat_edit;
    TextInputLayout editTextJumlahPakan_edit;
    TextInputLayout editTextJumlahPakanEkor_edit;
    TextInputLayout editTextJamPakan_edit;
    TextInputLayout editTextjamMinum_edit;
    TextInputLayout editTextjumlahObat_edit;
    TextInputLayout editTextjumlahVitamin_edit;
    TextInputLayout editTextjamVitamin_edit;
    TextInputLayout editTextjumlahVaksin_edit;

    Spinner spinnerPakans;
    Spinner spinnerUsias;
    Spinner spinnerObats;
    Spinner spinnerVitamins;
    Spinner spinnerVaksins;

    TextView txtspinnerusia;
    TextView txtspinnerpakan;
    TextView txtspinnerobat;
    TextView txtspinnervaksin;
    TextView txtspinnervitamin;


    TextView txtspinnerpakanNama;
    TextView txtspinnerobatNama;
    TextView txtspinnervitaminNama;
    TextView txtspinnervaksinNama;
    TextView txtidkandang;






    List<DataInventory.DataObat> dataObat;
    List<DataInventory.DataVitamin> dataVitamin;
    List<DataInventory.DataVaksin> dataVaksin;
    List<DataInventory.DataObat> dataObatgets = new ArrayList<>();

    int hour;
    int minutes;

    private static final String ID_CHECK_HARIAN = "id";
    private static final String ID_KANDANG = "idkandang";
    private static final String NAMA_PAKAN = "namapakan";


    private static final String USIA = "usia";
    private static final String AYAM_MATI = "ayammati";
    private static final String AYAM_SAKIT = "ayamsakit";
    private static final String GEJALA_SAKIT = "gejalasakit";
    private static final String BERAT_AYAM = "beratayam";

    private static final String JUMLAH_PAKAN = "jumlahpakan";
    private static final String JUMLAH_PAKAN_EKOR = "jumlahpakanekor";
    private static final String JAM_PAKAN = "jampakan";
    private static final String JAM_MINUM = "jamminum";
    private static final String NAMA_OBAT = "idobat";
    private static final String JUMLAH_OBAT = "jumlahobat";
    private static final String NAMA_VITAMIN = "idvitamin" ;
    private static final String JUMLAH_VITAMIN = "jumlahvitamin";
    private static final String JAM_VITAMIN = "jamvitamin";
    private static final String NAMA_VAKSIN = "idvaksin";
    private static final String JUMLAH_VAKSIN = "jumlahvaksin";

    TextView txtIdUbahHarian;

    String id;
    String id_kandang;
    String nama_pakan;
    String jumlah_pakan;
    String jumlah_pakan_ekor;
    String jam_pakan;
    String jam_minum;
    String namaobat;
    String namavitamin;
    String namavaksin;

    String usia;
    String ayam_mati;
    String ayam_sakit;
    String gejala_sakit;
    String berat_ayam;
    String jumlah_obat;
    String jumlah_vitamin;
    String jam_vitamin;
    String jumlah_vaksin;
    private DataService service;






    
    public static EditCheckHarian newInstance(
            String id,
            String usia,
            String ayam_mati,
            String ayam_sakit,
            String gejala_sakit,
            String berat_ayam,
            String id_kandang,
            String nama_pakan,
            String jumlah_pakan,
            String jumlah_pakan_ekor,
            String jam_pemberian_pakan,
            String jam_pemberian_minum,
            String nama_obat,
            String jumlah_obat,
            String nama_vitamin,
            String jumlah_vitamin,
            String jam_vitamin,
            String nama_vaksin,
            String jumlah_vaksin) {
        EditCheckHarian fragment = new EditCheckHarian();
        Bundle args = new Bundle();
        args.putString(ID_CHECK_HARIAN, id);
        args.putString(USIA, usia);
        args.putString(AYAM_MATI, ayam_mati);
        args.putString(AYAM_SAKIT, ayam_sakit);
        args.putString(GEJALA_SAKIT, gejala_sakit);
        args.putString(BERAT_AYAM, berat_ayam);
        args.putString(ID_KANDANG, id_kandang);
        args.putString(NAMA_PAKAN, nama_pakan);
        args.putString(JUMLAH_PAKAN, jumlah_pakan);
        args.putString(JUMLAH_PAKAN_EKOR, jumlah_pakan_ekor);
        args.putString(JAM_PAKAN, jam_pemberian_pakan);
        args.putString(JAM_MINUM, jam_pemberian_minum);
        args.putString(NAMA_OBAT, nama_obat);
        args.putString(JUMLAH_OBAT, jumlah_obat);
        args.putString(NAMA_VITAMIN, nama_vitamin);
        args.putString(JUMLAH_VITAMIN, jumlah_vitamin);
        args.putString(JAM_VITAMIN, jam_vitamin);
        args.putString(NAMA_VAKSIN, nama_vaksin);
        args.putString(JUMLAH_VAKSIN, jumlah_vaksin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID_CHECK_HARIAN);
            usia = getArguments().getString(USIA);
            ayam_mati = getArguments().getString(AYAM_MATI);
            ayam_sakit = getArguments().getString(AYAM_SAKIT);
            gejala_sakit = getArguments().getString(GEJALA_SAKIT);
            berat_ayam = getArguments().getString(BERAT_AYAM);
            id_kandang = getArguments().getString(ID_KANDANG);
            nama_pakan = getArguments().getString(NAMA_PAKAN);
            jumlah_pakan = getArguments().getString(JUMLAH_PAKAN);
            jumlah_pakan_ekor= getArguments().getString(JUMLAH_PAKAN_EKOR);
            jam_pakan = getArguments().getString(JAM_PAKAN);
            jam_minum = getArguments().getString(JAM_MINUM);
            namaobat = getArguments().getString(NAMA_OBAT);
            jumlah_obat = getArguments().getString(JUMLAH_OBAT);
            namavitamin = getArguments().getString(NAMA_VITAMIN);
            jumlah_vitamin = getArguments().getString(JUMLAH_VITAMIN);
            jam_vitamin = getArguments().getString(JAM_VITAMIN);
            namavaksin = getArguments().getString(NAMA_VAKSIN);
            jumlah_vaksin = getArguments().getString(JUMLAH_VAKSIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_edit_check_harian, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarUbahCekHarian);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        txtIdUbahHarian = view.findViewById(R.id.txt_id_edit_harian);
        txtIdUbahHarian.setText(id);

        editTextJumlahAyamMati_edit = view.findViewById(R.id.edt_jumlah_ayam_mati_edit_harian);
        editTextJumlahAyamSakit_edit = view.findViewById(R.id.edt_jumlah_ayam_sakits_edit_harian);
        editTextGejalaSakit_edit = view.findViewById(R.id.edt_gejala_sakits_edit_harians);
        editTextBerat_edit = view.findViewById(R.id.edt_berat_bobot_ayam_edit_harians);
        editTextJumlahPakan_edit = view.findViewById(R.id.edt_jumlah_pakan_ayam_edit_harians);
        editTextJumlahPakanEkor_edit = view.findViewById(R.id.edt_jumlah_pakan_ayam_gram_ekor_edit_harians);
        editTextJamPakan_edit = view.findViewById(R.id.edt_jam_pergantian_pakan_edit_harians);
        editTextjamMinum_edit = view.findViewById(R.id.edt_jam_pergantian_minum_edit_harians);
        editTextjumlahObat_edit = view.findViewById(R.id.edt_jumlah_obat_ayam_edit_harians);
        editTextjumlahVitamin_edit = view.findViewById(R.id.edt_jumlah_vitamin_ayam_edit_harians);
        editTextjamVitamin_edit = view.findViewById(R.id.edt_jam_pemberian_vitamin_edit_harians);
        editTextjumlahVaksin_edit = view.findViewById(R.id.edt_jumlah_vaksin_ayam_edit_harians);

        spinnerPakans = view.findViewById(R.id.spinner_pakans_edit_harians);
        spinnerUsias = view.findViewById(R.id.spinner_usias_edit_harian);
        spinnerObats = view.findViewById(R.id.spinner_obats_edit_harians);
        spinnerVitamins = view.findViewById(R.id.spinner_vitamins_edit_harians);
        spinnerVaksins = view.findViewById(R.id.spinner_vaksins_edit_harians);


        txtspinnerusia = view.findViewById(R.id.txt_spinner_usia_edit);
        txtspinnerpakan = view.findViewById(R.id.txt_spinner_pakan_edit);
        txtspinnerobat = view.findViewById(R.id.txt_spinner_obat_edit);
        txtspinnervitamin = view.findViewById(R.id.txt_spinner_vitamin_edit);
        txtspinnervaksin = view.findViewById(R.id.txt_spinner_vaksin_edit);


        txtspinnerpakanNama   = view.findViewById(R.id.txt_spinner_pakan_nama_edit);
        txtspinnerobatNama    = view.findViewById(R.id.txt_spinner_obat_nama_edit);
        txtspinnervitaminNama = view.findViewById(R.id.txt_spinner_vitamin_nama_edit);
        txtspinnervaksinNama  = view.findViewById(R.id.txt_spinner_vaksin_nama_edit);

        txtidkandang = view.findViewById(R.id.txt_id_kandang_edit);


        buttonUbahHarian = view.findViewById(R.id.button_ubah_check_harian);

        buttonUbahHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateHarian();
            }
        });

        SpinnerUsia(usia);
        SpinnerPakan(id_kandang);
        SpinnerObat(id_kandang);
        SpinnerVitamin(id_kandang);
        SpinnerVaksin(id_kandang);
        TimePickerJamPakan();
        TimePickerJamMinum();
        TimePickerJamVitamin();


        editTextJumlahAyamMati_edit.getEditText().setText(ayam_mati);
        editTextJumlahAyamSakit_edit.getEditText().setText(ayam_sakit);
        editTextGejalaSakit_edit.getEditText().setText(gejala_sakit);
        editTextBerat_edit.getEditText().setText(berat_ayam);
        editTextJumlahPakan_edit.getEditText().setText(jumlah_pakan);
        editTextJumlahPakanEkor_edit.getEditText().setText(jumlah_pakan_ekor);
        Log.d("jampakan", "onCreateView: " + jam_pakan + " " + jam_minum );
        editTextJamPakan_edit.getEditText().setText(jam_pakan);
        editTextjamMinum_edit.getEditText().setText(jam_minum);
        editTextjumlahObat_edit.getEditText().setText(jumlah_obat);
        editTextjumlahVitamin_edit.getEditText().setText(jumlah_vitamin);
        editTextjamVitamin_edit.getEditText().setText(jam_vitamin);
        editTextjumlahVaksin_edit.getEditText().setText(jumlah_vaksin);
        txtidkandang.setText(id_kandang);










//        Log.d("namaobat2", "onCreateView: " + namaobatget.getText().toString());




        return view;

    }

    public void SpinnerUsia(String usia) {
        spinnerUsias.setPrompt("Pilih Usia Ayam");
        ArrayAdapter<String> arrayAdapterTypeUsia = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.umur_ayam));
        spinnerUsias.setAdapter(arrayAdapterTypeUsia);
        spinnerUsias.setSelection(arrayAdapterTypeUsia.getPosition(usia));
    }

    public void UpdateHarian() {
        Call<BaseResponse.BaseResponseApi> call = service.updateCheckHarian(
                id,
                spinnerUsias.getSelectedItem().toString(),
                editTextJumlahAyamMati_edit.getEditText().getText().toString(),
                editTextJumlahAyamSakit_edit.getEditText().getText().toString(),
                editTextGejalaSakit_edit.getEditText().getText().toString(),
                editTextBerat_edit.getEditText().getText().toString(),
                txtspinnerpakan.getText().toString(),
                editTextJumlahPakan_edit.getEditText().getText().toString(),
                editTextJumlahPakanEkor_edit.getEditText().getText().toString(),
                editTextJamPakan_edit.getEditText().getText().toString(),
                editTextjamMinum_edit.getEditText().getText().toString(),
                txtspinnerobat.getText().toString(),
                editTextjumlahObat_edit.getEditText().getText().toString(),
                txtspinnervitamin.getText().toString(),
                editTextjumlahVitamin_edit.getEditText().getText().toString(),
                editTextjamVitamin_edit.getEditText().getText().toString(),
                txtspinnervaksin.getText().toString(),
                editTextjumlahVaksin_edit.getEditText().getText().toString(),
                txtspinnerpakanNama.getText().toString(),
                txtspinnervitaminNama.getText().toString(),
                txtspinnerobatNama.getText().toString(),
                txtspinnervaksinNama.getText().toString(),
                txtidkandang.getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {

                    Toast.makeText(getContext(), "Ubah Data Harian Berhasil", Toast.LENGTH_SHORT).show();
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


    public void SpinnerPakan(String idkandang){
        spinnerPakans.setPrompt("Pilih Pakan Yang Digunakan");
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan(idkandang);
        pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
                if (response.code() == 200) {
                    List<DataInventory.DataPakan>  datapakan = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = 0; i <datapakan.size() ; i++) {

                        listSpinner.add(datapakan.get(i).getNama());
                    }

                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                    spinnerPakans.setAdapter(arrayAdapterType);

                    spinnerPakans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            Toast.makeText(getContext(), "" + datapakan.get(spinnerPakans.getSelectedItemPosition()).getId_barang(), Toast.LENGTH_SHORT).show();
                            txtspinnerpakan.setText(datapakan.get(spinnerPakans.getSelectedItemPosition()).getId_pakan());
                            txtspinnerpakanNama.setText(datapakan.get(spinnerPakans.getSelectedItemPosition()).getNama());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinnerPakans.setSelection(arrayAdapterType.getPosition(nama_pakan));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){
                String[] message = new String[]{"Barang Pakan habis/belum ada"};
                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                spinnerPakans.setAdapter(arrayAdapterType);
            }
        });
    }


    public void SpinnerObat(String idkandang){
        spinnerObats.setPrompt("Pilih Pakan Yang Digunakan");
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> obatCalls = service.apigetObat(idkandang);
        obatCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> response) {
                if (response.code() == 200) {
                    List<DataInventory.DataObat>  dataobat = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = 0; i <dataobat.size() ; i++) {

                        listSpinner.add(dataobat.get(i).getNama());
                    }

                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                    spinnerObats.setAdapter(arrayAdapterType);

                    spinnerObats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            txtspinnerNamaPakan.setText(dataobat.get(spinnerObats.getSelectedItemPosition()).getId_barang());
//                            Toast.makeText(getContext(), "" + dataobat.get(spinnerVitamins.getSelectedItemPosition()).getId_barang(), Toast.LENGTH_SHORT).show();
                            txtspinnerobat.setText(dataobat.get(spinnerObats.getSelectedItemPosition()).getId_obat());
                            txtspinnerobatNama.setText(dataobat.get(spinnerObats.getSelectedItemPosition()).getNama());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinnerObats.setSelection(arrayAdapterType.getPosition(namaobat));

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Throwable t){
                String[] message = new String[]{"Barang Obat habis/belum ada"};
                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                spinnerObats.setAdapter(arrayAdapterType);
            }
        });
    }
    public void SpinnerVitamin(String idkandang){
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> vitaminCalls = service.apigetVitamin(idkandang);
        vitaminCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> response) {
                if (response.code() == 200) {
                    List<DataInventory.DataVitamin>  datavitamin = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = 0; i <datavitamin.size() ; i++) {

                        listSpinner.add(datavitamin.get(i).getNama());
                    }

                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                    spinnerVitamins.setAdapter(arrayAdapterType);

                    spinnerVitamins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            txtspinnervitamin.setText(datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getId_vitamin());
                            txtspinnervitaminNama.setText(datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getNama());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinnerVitamins.setSelection(arrayAdapterType.getPosition(namavitamin));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Throwable t){
                String[] message = new String[]{"Barang Vitamin habis/belum ada"};
                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                spinnerVitamins.setAdapter(arrayAdapterType);
            }
        });
    }


    public void SpinnerVaksin(String idkandang){
        Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> vaksinCalls = service.apigetVaksin(idkandang);
        vaksinCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> response) {
                if (response.code() == 200) {
                    List<DataInventory.DataVaksin>  datavaksin = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = 0; i <datavaksin.size() ; i++) {

                        listSpinner.add(datavaksin.get(i).getNama());
                    }

                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,listSpinner);
                    spinnerVaksins.setAdapter(arrayAdapterType);

                    spinnerVaksins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            txtspinnervaksin.setText(datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getId_vaksin());
                            txtspinnervaksinNama.setText(datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getNama());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinnerVaksins.setSelection(arrayAdapterType.getPosition(namavaksin));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Throwable t){
                String[] message = new String[]{"Barang Vaksin habis/belum ada"};
                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                spinnerVaksins.setAdapter(arrayAdapterType);
            }
        });
    }


    public void TimePickerJamPakan(){
        editTextJamPakan_edit.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.ThemeOverlay_MaterialComponents_TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;

                        String time = hour + ":" + minutes;
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm"
                        );
                        try {
                            Date date = simpleDateFormat.parse(time);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
                                    "HH:mm:ss"
                            );

                            editTextJamPakan_edit.getEditText().setText(simpleDateFormat1.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, true
                );
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minutes);
                timePickerDialog.show();
            }
        });
    }


    public void TimePickerJamMinum(){
        editTextjamMinum_edit.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.ThemeOverlay_MaterialComponents_TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;

                        String time = hour + ":" + minutes;
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm"
                        );
                        try {
                            Date date = simpleDateFormat.parse(time);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
                                    "HH:mm:ss"
                            );

                            editTextjamMinum_edit.getEditText().setText(simpleDateFormat1.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, true
                );
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minutes);
                timePickerDialog.show();
            }
        });
    }

    public void TimePickerJamVitamin(){
        editTextjamVitamin_edit.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.ThemeOverlay_MaterialComponents_TimePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;

                        String time = hour + ":" + minutes;
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm"
                        );
                        try {
                            Date date = simpleDateFormat.parse(time);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
                                    "HH:mm:ss"
                            );

                            editTextjamVitamin_edit.getEditText().setText(simpleDateFormat1.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, true
                );
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minutes);
                timePickerDialog.show();
            }
        });
    }


}






