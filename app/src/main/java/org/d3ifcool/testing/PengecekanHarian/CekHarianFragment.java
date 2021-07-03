package org.d3ifcool.testing.PengecekanHarian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Data;
import org.d3ifcool.testing.DatabaseSQLite.SQLiteHelper;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Inventory.InventoryFragment;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class CekHarianFragment extends Fragment {


    TextView infostokpakan;
    TextView infostokvitamin;
    TextView infostokvaksin;
    TextView infostokobat;

    LinearLayout linearlayoutObat;
    LinearLayout linearlayoutVitamin;
    LinearLayout linearlayoutVaksin;

    LinearLayout linearlayoutstokpakan;
    LinearLayout linearlayoutstokobat;
    LinearLayout linearlayoutstokvitamin;
    LinearLayout linearlayoutstokvaksin;

    Chip chipPakan;
    Chip chipObat;
    Chip chipVitamin;
    Chip chipVaksin;

    //TextView
    TextView txtNamaPetugas;
    TextView txtIdUser;
    TextView txtGetDate;
    TextView txtJamPakan;
    TextView txtJamGantiMinum;
    TextView txtJamGantiVitamin;
    TextView txtlatitude;
    TextView txtlongitude;


    TextView txtidkandang;
    TextView txtkodekandang;
    TextView txtkodeblok;
    TextView txtalamatkandang;
    TextView txtjumlahayam;

    TextView txtspinnerusia;
    TextView txtspinnerpakan;
    TextView txtspinnerobat;
    TextView txtspinnervaksin;
    TextView txtspinnervitamin;

    TextView textViewusia;
    TextView textViewfoto;

    //EditText
    EditText edtAlamatKandang;
    EditText edtKodeKandang;
    EditText edtKodeBlok;
    EditText edtJumlahAyam;
    EditText edtTanggalAyamMasuk;
    EditText edtJumlahAyamSakit;
    EditText edtGejalaSakit;
    EditText edtJumlahPakan;

    TextInputLayout editTextJumlahAyamMati;
    TextInputLayout editTextJumlahAyamSakit;
    TextInputLayout editTextGejalaSakit;
    TextInputLayout editTextBerat;
    TextInputLayout editTextJumlahPakan;
    TextInputLayout editTextJumlahPakanEkor;
    TextInputLayout editTextJamPakan;
    TextInputLayout editTextjamMinum;
    TextInputLayout editTextjumlahObat;
    TextInputLayout editTextjumlahVitamin;
    TextInputLayout editTextjamVitamin;
    TextInputLayout editTextjumlahVaksin;


    //Spinner
    Spinner spinnerUmurAyam;
    Spinner spinnerKondisiAyam;
    Spinner spinnerPakans;
    Spinner spinnerUsias;
    Spinner spinnerObats;
    Spinner spinnerVitamins;
    Spinner spinnerVaksins;
    //Button
    Button btnSimpan;
    Button btnjamPakan;
    Button btnjamGantiMinum;
    Button btnjamGantiVitamin;
    MaterialButton btnambilBukti;
    MaterialButton btnlihatSelengkapnya;

    //Circle Image
    ImageView image_bukti_cekharian;
    ImageView imageView_Simpan;

    ProgressBar pgbarLoading;



    //Database
    SQLiteHelper db;

    //Array
    List<Data> datas;
    List umurAyam;
    ArrayAdapter<Integer> spinnerArrayAdapterUmurAyam;
    ArrayAdapter<String> spinnerArrayAdapterKondisAyam;
    String[] kondisiAyam;
    String[] jumlahpakan;





    TextView txtspinnerpakanNama;
    TextView txtspinnerobatNama;
    TextView txtspinnervitaminNama;
    TextView txtspinnervaksinNama;
//
//    AutoCompleteTextView spinnerNamaPakan;
//    AutoCompleteTextView spinnerNamaObat;
//    AutoCompleteTextView spinnerNamaVaksin;
//    AutoCompleteTextView spinnerNamaVitamin;
//    AutoCompleteTextView spinnerUsia;

    //Int

    int hour;
    int minutes;
    int counter = 0;
    int LOCATION_REQUEST_CODE = 1001;
    static final int IMAGE_CAPTURE_CODE = 1001;
    String amPm;

    private DataService service;

    private static final String ID_KANDANG = "idkan dang";
    private static final String KODE_KANDANG = "kodekandang";
    private static final String KODE_BLOK = "kodeblok";
    private static final String ALAMAT_KANDANG = "alamatkandang";
    private static final String ID_CHICKIN = "idchickin";
    private static final String ID_USER = "iduser";
    private static final String NAMA_USER = "namauser";


    //Location
    static final String TAG = "MainActivity";
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null){
                return;
            }

            for (Location location:locationResult.getLocations()){
                Log.d(TAG, "onLocationResult: " + location.toString());

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> address = null;
                try {
                    address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                txtlatitude.setText(latitude);
                txtlongitude.setText(longitude);
            }
        }
    };


    // Uri
    Uri imageUri;

    private File file;
    private Bitmap imageBitmap;

    String idkandang;
    String alamatkandang;
    String kodekandang;
    String kodeblok;
    String idchickin;
    String namauser;
    String iduser;


    String usia;

    public CekHarianFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            idkandang = getArguments().getString(ID_KANDANG);
            alamatkandang = getArguments().getString(ALAMAT_KANDANG);
            kodekandang = getArguments().getString(KODE_KANDANG);
            kodeblok = getArguments().getString(KODE_BLOK);
            idchickin = getArguments().getString(ID_CHICKIN);
            namauser = getArguments().getString(NAMA_USER);
            iduser = getArguments().getString(ID_USER);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_cek_harian, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarCekHarian);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        linearlayoutstokpakan = view.findViewById(R.id.linearstokpakan);
        linearlayoutstokobat = view.findViewById(R.id.linearstokobat);
        linearlayoutstokvitamin = view.findViewById(R.id.linearstokvitamin);
        linearlayoutstokvaksin = view.findViewById(R.id.linearstokvaksin);

        infostokpakan = view.findViewById(R.id.info_stokpakan);
        infostokobat = view.findViewById(R.id.info_stokobat);
        infostokvitamin = view.findViewById(R.id.info_stokvitamin);
        infostokvaksin = view.findViewById(R.id.info_stokvaksin);

        linearlayoutObat = view.findViewById(R.id.linearObat);
        linearlayoutVitamin = view.findViewById(R.id.linearVitamin);
        linearlayoutVaksin = view.findViewById(R.id.linearVaksin);
        chipPakan = view.findViewById(R.id.tambahPakan);
        chipObat = view.findViewById(R.id.tambahObat);
        chipVaksin = view.findViewById(R.id.tambahVaksin);
        chipVitamin = view.findViewById(R.id.tambahVitamin);

        pgbarLoading = view.findViewById(R.id.pgbar_cekharian);

//TextView
        txtNamaPetugas = view.findViewById(R.id.txt_nama_petugas_cekharian);
        txtIdUser = view.findViewById(R.id.txt_id_user_cekharian);
        txtGetDate = view.findViewById(R.id.txt_get_date);
        txtlatitude = view.findViewById(R.id.txt_latitude);
        txtlongitude = view.findViewById(R.id.txt_longitude);

        txtidkandang = view.findViewById(R.id.txt_id_kandang_cekharian);
        txtkodekandang = view.findViewById(R.id.txt_kode_kandang_cekharian);
        txtkodeblok = view.findViewById(R.id.txt_kode_blok_cekharian);
        txtalamatkandang = view.findViewById(R.id.txt_alamat_kandang_cekharian);
        txtjumlahayam = view.findViewById(R.id.txt_jumlah_ayam_cekharian);

        textViewfoto = view.findViewById(R.id.txt_foto_cek);

//EditText


        editTextJumlahAyamMati = view.findViewById(R.id.edt_jumlah_ayam_mati);
        editTextJumlahAyamSakit = view.findViewById(R.id.edt_jumlah_ayam_sakits);
        editTextGejalaSakit = view.findViewById(R.id.edt_gejala_sakits);
        editTextBerat = view.findViewById(R.id.edt_berat_bobot_ayam);
        editTextJumlahPakan = view.findViewById(R.id.edt_jumlah_pakan_ayam);
        editTextJumlahPakanEkor = view.findViewById(R.id.edt_jumlah_pakan_ayam_gram_ekor);
        editTextJamPakan = view.findViewById(R.id.edt_jam_pergantian_pakan);
        editTextjamMinum = view.findViewById(R.id.edt_jam_pergantian_minum);
        editTextjumlahObat = view.findViewById(R.id.edt_jumlah_obat_ayam);
        editTextjumlahVitamin = view.findViewById(R.id.edt_jumlah_vitamin_ayam);
        editTextjamVitamin = view.findViewById(R.id.edt_jam_pemberian_vitamin);
        editTextjumlahVaksin = view.findViewById(R.id.edt_jumlah_vaksin_ayam);

//Button

        btnambilBukti = view.findViewById(R.id.button_ambil_bukti);


//Spinner

//Database
        db = new SQLiteHelper(getContext());

//Array
        datas = new ArrayList<>();
        umurAyam = new ArrayList<Integer>();
        kondisiAyam = new String[]{"Pilih Kondisi Ayam", "Sehat", "Sakit"};
        jumlahpakan = new String[]{"Piih Jumlah Pakan" , "1 Zak" , "½ Zak"};

//Circle image
        image_bukti_cekharian = view.findViewById(R.id.hasil_foto_harian);



        ///////////////////////////////////////////////////////////////////////////////////////////////

        txtNamaPetugas.setText(namauser);
        txtIdUser.setText(iduser);

        txtidkandang.setText(idkandang);
        txtkodekandang.setText(kodekandang);
        txtkodeblok.setText(kodeblok);
        txtalamatkandang.setText(alamatkandang);
        txtjumlahayam.setText(idchickin);


//Method
        ambilBukti();





        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);



//        textViewusia = view.findViewById(R.id.txt_usia_coba);







        txtspinnerusia = view.findViewById(R.id.txt_spinner_usia);
        txtspinnerpakan = view.findViewById(R.id.txt_spinner_pakan);
        txtspinnerobat = view.findViewById(R.id.txt_spinner_obat);
        txtspinnervitamin = view.findViewById(R.id.txt_spinner_vitamin);
        txtspinnervaksin = view.findViewById(R.id.txt_spinner_vaksin);



        txtspinnerpakanNama   = view.findViewById(R.id.txt_spinner_pakan_nama);
        txtspinnerobatNama    = view.findViewById(R.id.txt_spinner_obat_nama);
        txtspinnervitaminNama = view.findViewById(R.id.txt_spinner_vitamin_nama);
        txtspinnervaksinNama  = view.findViewById(R.id.txt_spinner_vaksin_nama);









        imageView_Simpan = view.findViewById(R.id.imgSimpan);
        imageView_Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HandelEmpty();


                // pakan





                try {
                    int stokpakan = Integer.parseInt(infostokpakan.getText().toString());
                    int jumlahpakanmasuk = Integer.parseInt(editTextJumlahPakan.getEditText().getText().toString());

                    if (stokpakan < jumlahpakanmasuk) {
                        Toast.makeText(getContext(), "Stok Pakan Tidak Cukup !", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException exception){
                    exception.printStackTrace();
                }

                try {

                    int stokobat = Integer.parseInt(infostokobat.getText().toString());
                    int jumlahobatmasuk = Integer.parseInt(editTextjumlahObat.getEditText().getText().toString());


                    if (stokobat < jumlahobatmasuk) {
                        Toast.makeText(getContext(), "Stok Obat Tidak Cukup !", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException exception){
                    exception.printStackTrace();
                }


                try {
                    int stokvitamin = Integer.parseInt(infostokvitamin.getText().toString());
                    int jumlahvitaminmasuk = Integer.parseInt(editTextjumlahVitamin.getEditText().getText().toString());


                    if (stokvitamin < jumlahvitaminmasuk){
                        Toast.makeText(getContext(), "Stok Vitamin Tidak Cukup !", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException exception){
                    exception.printStackTrace();
                }

                try {

                    int stokvaksin = Integer.parseInt(infostokvaksin.getText().toString());
                    int jumlahvaksinmasuk = Integer.parseInt(editTextjumlahVaksin.getEditText().getText().toString());



                    if (stokvaksin < jumlahvaksinmasuk){
                        Toast.makeText(getContext(), "Stok Vaksin Tidak Cukup !", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException exception){
                    exception.printStackTrace();
                }



//
//




//                    Toast.makeText(getContext(), "Barang Cukup", Toast.LENGTH_SHORT).show();
//                }

                if (spinnerUsias.getSelectedItem().equals("-- Pilih Umur Ayam --")) {
                    ((TextView)spinnerUsias.getChildAt(0)).setError("Harap Pilih Type Produk");
                    Toast.makeText(getContext(), "Pilih Umur Ayam", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(editTextJumlahAyamMati.getEditText().getText().toString())){
                    editTextJumlahAyamMati.setError("Harap Masukkan Jumlah Chickin");
                }
                else if (TextUtils.isEmpty(editTextJumlahAyamSakit.getEditText().getText().toString())){
                    editTextJumlahAyamSakit.setError("Harap Masukkan Jumlah Ayam Sakit");
                }

                else if (TextUtils.isEmpty(editTextGejalaSakit.getEditText().getText().toString())){
                    editTextGejalaSakit.setError("Harap Masukkan Gejala Ayam Sakit");
                }

                else if (TextUtils.isEmpty(editTextBerat.getEditText().getText().toString())){
                    editTextBerat.setError("Harap Masukkan Berat Ayam ");
                }
                else if (spinnerPakans.getSelectedItem().equals("Pilih Merk Pakan")) {
                    ((TextView)spinnerPakans.getChildAt(0)).setError("Harap Pilih Merk Pakan");
                    Toast.makeText(getContext(), "Pilih Merk Pakan", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(editTextJumlahPakan.getEditText().getText().toString())){
                    editTextJumlahPakan.setError("Harap Masukkan Jumlah Pakan Ayam ");

                }
                else if (TextUtils.isEmpty(editTextJumlahPakanEkor.getEditText().getText().toString())){
                    editTextJumlahPakanEkor.setError("Harap Masukkan Jumlah Pakan Ayam/Ekor ");
                }

                else if (TextUtils.isEmpty(editTextJamPakan.getEditText().getText().toString())){
                    editTextJamPakan.setError("Harap Masukkan Jam Pemberian Pakan");
                }

                else if (TextUtils.isEmpty(editTextjamMinum.getEditText().getText().toString())){
                    editTextjamMinum.setError("Harap Masukkan Jam Pemberian Minum");
                }


                else if (spinnerObats.getSelectedItem().equals("Pilih Merk Obat")) {
                    ((TextView)spinnerObats.getChildAt(0)).setError("Harap Pilih Merk Obat");
                    Toast.makeText(getContext(), "Pilih Merk Obat", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(editTextjumlahObat.getEditText().getText().toString())){
                    editTextjumlahObat.setError("Harap Masukkan Jumlah Obat ");


                }

                else if (spinnerVitamins.getSelectedItem().equals("Pilih Merk Vitamin")) {
                    ((TextView)spinnerVitamins.getChildAt(0)).setError("Harap Pilih Merk Vitamin");
                    Toast.makeText(getContext(), "Pilih Merk Vitamin", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(editTextjumlahVitamin.getEditText().getText().toString())){
                    editTextjumlahVitamin.setError("Harap Masukkan Jumlah Vitamin");

                }

                else if (TextUtils.isEmpty(editTextjamVitamin.getEditText().getText().toString())){
                    editTextjamVitamin.setError("Harap Masukkan Jam Vitamin");

                }

                else if (spinnerVaksins.getSelectedItem().equals("Pilih Merk Vaksin")) {
                    ((TextView)spinnerVaksins.getChildAt(0)).setError("Harap Pilih Merk Vaksin");
                    Toast.makeText(getContext(), "Pilih Merk Vaksin", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(editTextjumlahVaksin.getEditText().getText().toString())){
                    editTextjumlahVaksin.setError("Harap Masukkan Jumlah Vaksin");

                }


                else if (textViewfoto.getText().equals("")){
                    Toast.makeText(getContext(), "Harap Masukkan Foto Bukti Pengecekan", Toast.LENGTH_SHORT).show();
                }

                else if (infostokpakan.getText().equals("0")){
                    Toast.makeText(getContext(), "Merk Pakan Ini Telah Habis, Harap Lakukan Request Stok Pakan", Toast.LENGTH_SHORT).show();
                }

                else if (infostokobat.getText().equals("0")){
                    Toast.makeText(getContext(), "Merk Obat Ini Telah Habis, Harap Lakukan Request Stok Obat", Toast.LENGTH_SHORT).show();
                }

                else if (infostokvitamin.getText().equals("0")){
                    Toast.makeText(getContext(), "Merk Vitamin Ini Telah Habis, Harap Lakukan Request Stok Vitamin", Toast.LENGTH_SHORT).show();
                }

                else if (infostokvaksin.getText().equals("0")){
                    Toast.makeText(getContext(), "Merk Vaksin Ini Telah Habis, Harap Lakukan Request Stok Vaksin", Toast.LENGTH_SHORT).show();
                }

                else {

                    SimpanPengecekanHarian();
                }




//                Toast.makeText(getContext(), ""+infostokpakan.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });





        SpinnerPakan(view);
        SpinnerUsia(view);
        SpinnerObat(view);
        SpinnerVaksin(view);
        SpinnerVitamin(view);

        TimePickerJamMinum();
        TimePickerJamPakan();
        TimePickerJamVitamin();


//
//        btnlihatSelengkapnya = view.findViewById(R.id.button_lihat_selengkapnya);
//        btnlihatSelengkapnya.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//                assert appCompatActivity != null;
//                appCompatActivity.getSupportFragmentManager().beginTransaction().
//                        replace(R.id.fragment_container,  new DetailDataPengecekanFragment()).addToBackStack(null).commit();
//            }
//        });


        pgbarLoading.setVisibility(View.GONE);

        return view;
    }






    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

    }





    public void SimpanPengecekanHarian(){
        pgbarLoading.setVisibility(View.VISIBLE);
        txtGetDate.setText(TanggalDanWaktu());
        file = new File(getRealPathFromURI(imageUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),requestBody);
        Call<BaseResponse.BaseResponseApi> call = service.inputPengecekanHarian(
                part,
                RequestBody.create(MediaType.parse("text/plain"),spinnerUsias.getSelectedItem().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextJumlahAyamMati.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextJumlahAyamSakit.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextGejalaSakit.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextBerat.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnerpakan.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextJumlahPakan.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextJumlahPakanEkor.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextJamPakan.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextjamMinum.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnerobat.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextjumlahObat.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnervitamin.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextjumlahVitamin.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextjamVitamin.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnervaksin.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),editTextjumlahVaksin.getEditText().getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtlatitude.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtlongitude.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),idkandang),
                RequestBody.create(MediaType.parse("text/plain"),iduser),
                RequestBody.create(MediaType.parse("text/plain"),idchickin),
                RequestBody.create(MediaType.parse("text/plain"),txtGetDate.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnerpakanNama.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnervitaminNama.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnerobatNama.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtspinnervaksinNama.getText().toString())
                );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    pgbarLoading.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tambah Data Harian Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    pgbarLoading.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tambah Data Harian Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {

                Log.d("Gagal", "onFailure: " + t.getMessage());
            }
        });
    }




    private String TanggalDanWaktu(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
            askOpenCameraPermission();
            askWriteStoragePermission();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }


    @Override
    public void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            // successfully captured the image
            // display it in image view
            if (data != null) {
                imageBitmap  = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(getActivity().getApplicationContext(),imageBitmap);
                file = new File(getRealPathFromURI(imageUri));
            }

            ImageDecoder.Source source = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(getActivity().getContentResolver(),imageUri);
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    imageBitmap = ImageDecoder.decodeBitmap(source);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            image_bukti_cekharian.setImageBitmap(imageBitmap);
            textViewfoto.setText("fotoset");
        }
    }

    public void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1001);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
//                },LOCATION_REQUEST_CODE
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
        }else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        }
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }



    public void capturePicture(){
        // capture picture
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (
                    getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, LOCATION_REQUEST_CODE);
            }
            else {
                openCamera();
            }
        }else {
            openCamera();
        }
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From The Camera");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,IMAGE_CAPTURE_CODE);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void askLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "askLocationPermission: ");
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            }
        }
    }


    public void askOpenCameraPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)){
                Log.d(TAG, "askLocationPermission: ");
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.CAMERA},1001);
            }else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.CAMERA},1001);
            }
        }
    }

    public void askWriteStoragePermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Log.d(TAG, "askLocationPermission: ");
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
            }else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checkSettingsAndStartLocationUpdates();
                openCamera();


            }else {
                Toast.makeText(getContext(), "Location Permission not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ambilBukti(){
        btnambilBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();

            }
        });
    }


    public void SpinnerPakan(View view){
        spinnerPakans = view.findViewById(R.id.spinner_pakans);
        spinnerPakans.setPrompt("Pilih Pakan Yang Digunakan");
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_pakan));
        spinnerPakans.setAdapter(arrayAdapterType);
        chipPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipPakan.isChecked()){
                    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> pakanCalss = service.apigetPakan(idkandang);
                    pakanCalss.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>>() {
                        @SuppressLint({"SetTextI18n", "DefaultLocale"})
                        @Override
                        public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Response<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> response) {
                            if (response.code() == 200) {
                                linearlayoutstokpakan.setVisibility(View.VISIBLE);
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
                                        Toast.makeText(getContext(), "" + datapakan.get(spinnerPakans.getSelectedItemPosition()).getId_barang(), Toast.LENGTH_SHORT).show();
                                        txtspinnerpakan.setText(datapakan.get(spinnerPakans.getSelectedItemPosition()).getId_pakan());
                                        txtspinnerpakanNama.setText(datapakan.get(spinnerPakans.getSelectedItemPosition()).getNama());
                                        infostokpakan.setText(datapakan.get(spinnerPakans.getSelectedItemPosition()).getStok_pakan()+"");

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                infostokpakan.setText("0");
                                String[] message = new String[]{"Stok Pakan Di Kandang Belum Ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerPakans.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> call, Throwable t){
                            String[] message = new String[]{"Barang Pakan habis/belum ada"};
                            ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                            spinnerPakans.setAdapter(arrayAdapterType);
                        }
                    });
                }else {
                    linearlayoutstokpakan.setVisibility(View.GONE);
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_pakan));
                    spinnerPakans.setAdapter(arrayAdapterType);
                }
            }
        });


    }
    public void SpinnerUsia(View view){
        spinnerUsias = view.findViewById(R.id.spinner_usias);
        spinnerUsias.setPrompt("Pilih Usia Ayam");
        ArrayAdapter<String> arrayAdapterTypeUsia = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.umur_ayam));
        spinnerUsias.setAdapter(arrayAdapterTypeUsia);
    }
    public void SpinnerObat(View view){

        spinnerObats = view.findViewById(R.id.spinner_obats);
        spinnerObats.setPrompt("Pilih Obat Yang Digunakan");

        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_obat));
        spinnerObats.setAdapter(arrayAdapterType);

        chipObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipObat.isChecked()){
                    linearlayoutstokobat.setVisibility(View.VISIBLE);
                    linearlayoutObat.setVisibility(View.VISIBLE);
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
                                        infostokobat.setText(dataobat.get(spinnerObats.getSelectedItemPosition()).getStok_obat()+"");
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                String[] message = new String[]{"Stok Obat Di Kandang Belum Ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerObats.setAdapter(arrayAdapterType);
                                infostokobat.setText("0");
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> call, Throwable t){
                            String[] message = new String[]{"Barang Obat habis/belum ada"};
                            ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                            spinnerObats.setAdapter(arrayAdapterType);
                        }
                    });
                }else {
                    linearlayoutstokobat.setVisibility(View.GONE);
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_obat));
                    spinnerObats.setAdapter(arrayAdapterType);
                }
            }
        });

    }
    public void SpinnerVitamin(View view){
        spinnerVitamins = view.findViewById(R.id.spinner_vitamins);
        spinnerVitamins.setPrompt("Pilih Vitamin Yang Digunakan");
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_vitamin));
        spinnerVitamins.setAdapter(arrayAdapterType);
        chipVitamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVitamin.isChecked()){
                    linearlayoutstokvitamin.setVisibility(View.VISIBLE);
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

                                        Toast.makeText(getContext(), "" + datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getId_barang(), Toast.LENGTH_SHORT).show();
                                        txtspinnervitamin.setText(datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getId_vitamin());
                                        txtspinnervitaminNama.setText(datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getNama());
                                        infostokvitamin.setText(datavitamin.get(spinnerVitamins.getSelectedItemPosition()).getStok_vitamin()+"");
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                String[] message = new String[]{"Stok Vitamin Di Kandang Belum Ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerVitamins.setAdapter(arrayAdapterType);
                                infostokvitamin.setText("0");
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> call, Throwable t){
                            String[] message = new String[]{"Barang Vitamin habis/belum ada"};
                            ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                            spinnerVitamins.setAdapter(arrayAdapterType);
                        }
                    });
                }else {
                    linearlayoutstokvitamin.setVisibility(View.GONE);
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_vitamin));
                    spinnerVitamins.setAdapter(arrayAdapterType);
                }
            }
        });

    }
    public void SpinnerVaksin(View view){

        spinnerVaksins = view.findViewById(R.id.spinner_vaksins);
        spinnerVaksins.setPrompt("Pilih Vaksin Yang Digunakan");
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_vaksin));
        spinnerVaksins.setAdapter(arrayAdapterType);
        chipVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipVaksin.isChecked()){
                    linearlayoutstokvaksin.setVisibility(View.VISIBLE);
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
//                            Toast.makeText(getContext(), "" + datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getId_barang(), Toast.LENGTH_SHORT).show();
                                        txtspinnervaksin.setText(datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getId_vaksin());
                                        txtspinnervaksinNama.setText(datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getNama());
                                        infostokvaksin.setText(datavaksin.get(spinnerVaksins.getSelectedItemPosition()).getStok_vaksin()+"");
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else {
                                infostokvaksin.setText("0");
                                String[] message = new String[]{"Stok Vaksin Di Kandang Belum Ada"};
                                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                                spinnerVaksins.setAdapter(arrayAdapterType);
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> call, Throwable t){
                            String[] message = new String[]{"Barang Vaksin habis/belum ada"};
                            ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,message);
                            spinnerVaksins.setAdapter(arrayAdapterType);
                        }
                    });
                }else {
                    linearlayoutstokvaksin.setVisibility(View.GONE);
                    ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(requireContext(),R.layout.dropdown_item,getResources().getStringArray(R.array.pilih_vaksin));
                    spinnerVaksins.setAdapter(arrayAdapterType);
                }
            }
        });


    }


    public void TimePickerJamPakan(){
        editTextJamPakan.getEditText().setOnClickListener(new View.OnClickListener() {
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

                            editTextJamPakan.getEditText().setText(simpleDateFormat1.format(date));
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
        editTextjamMinum.getEditText().setOnClickListener(new View.OnClickListener() {
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

                            editTextjamMinum.getEditText().setText(simpleDateFormat1.format(date));
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
        editTextjamVitamin.getEditText().setOnClickListener(new View.OnClickListener() {
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

                            editTextjamVitamin.getEditText().setText(simpleDateFormat1.format(date));
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

//    public void HandelEmpty(){
//        if (editTextJumlahAyamMati.getEditText().getText().toString().trim().isEmpty()){
//            editTextJumlahAyamMati.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextJumlahAyamSakit.getEditText().getText().toString().trim().isEmpty()){
//            editTextJumlahAyamSakit.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextGejalaSakit.getEditText().getText().toString().trim().isEmpty()){
//            editTextGejalaSakit.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextBerat.getEditText().getText().toString().trim().isEmpty()){
//            editTextBerat.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextJumlahPakan.getEditText().getText().toString().trim().isEmpty()){
//            editTextJumlahPakan.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextJumlahPakanEkor.getEditText().getText().toString().trim().isEmpty()){
//            editTextJumlahPakanEkor.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextJamPakan.getEditText().getText().toString().trim().isEmpty()){
//            editTextJamPakan.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextjamMinum.getEditText().getText().toString().trim().isEmpty()){
//            editTextjamMinum.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextjumlahObat.getEditText().getText().toString().trim().isEmpty()){
//            editTextjumlahObat.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextjumlahVitamin.getEditText().getText().toString().trim().isEmpty()){
//            editTextjumlahVitamin.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextjamVitamin.getEditText().getText().toString().trim().isEmpty()){
//            editTextjamVitamin.setError("Harap Isi Bidang Ini");
//        }
//        else if (editTextjumlahVaksin.getEditText().getText().toString().trim().isEmpty()){
//            editTextjumlahVaksin.setError("Harap Isi Bidang Ini");
//        }else {
//            SimpanPengecekanHarian();
//        }
//
//    }



    public static CekHarianFragment newInstance(
            String idkandang,
            String alamatkandang,
            String kodekandang,
            String kodeblok,
            String idchickin,
            String iduser,
            String namauser) {

        Bundle args = new Bundle();
        CekHarianFragment fragment = new CekHarianFragment();
        args.putString(ID_KANDANG,idkandang);
        args.putString(ALAMAT_KANDANG,alamatkandang);
        args.putString(KODE_KANDANG,kodekandang);
        args.putString(KODE_BLOK,kodeblok);
        args.putString(ID_CHICKIN,idchickin);
        args.putString(ID_USER,iduser);
        args.putString(NAMA_USER,namauser);
        fragment.setArguments(args);
        return fragment;
    }
}