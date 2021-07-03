package org.d3ifcool.testing.PengecekanSampel;

import android.Manifest;
import android.animation.LayoutTransition;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class CekSampleFrgament extends Fragment {


    //cek
    RelativeLayout btncek;
    TextView txtTotalBobotAyam;

    AutoCompleteTextView autoCompleteTextViewUmurAyamSampel;
    AutoCompleteTextView autoCompleteTextViewJenisAyamSampel;
    ImageView imageViewHasilFotoSampel;
    EditText editTextJumlahAyamSampel;
    EditText editTextKondisiAyamSampel;
    EditText editTextTotalAyamSampel;
    TextView txtkodekandang;
    TextView txtkodeblok;
    RelativeLayout btnSimpanSampling;

    EditText textIn;
    TextView buttonAdd;
    TextView txtBobotRataRata;
    TextView txtfotocek_sampel;
    RelativeLayout buttonShowAll;
    LinearLayout linearLayout;
    LinearLayout linearLayoutTambahFoto;

    private static final String ID_USER = "iduser";
    private static final String NAMA_USER = "namauser";
    private static final String KODE_KANDANG = "kodekandang";
    private static final String KODE_BLOK = "kodeblok";
    private static final String ID_KANDANG = "idkandang";
    private static final String JUMLAH_AYAM_SEKARANG = "jumlahayam";
    private static final String ID_CHICKIN = "idchickin";
    int count = 1;
//Textview

    private TextView txtiduser;
    private TextView txtnamauser;
    private TextView txtgetdate;
    private TextView txtidkandang;
    private TextView txtgetlatitude;
    private TextView txtgetlongitude;
    private TextView txtidchickin;




//Button



    //Circle Image



    private int LOCATION_REQUEST_CODE = 1001;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private DataService service;

    //Location
    private static final String TAG = "MainActivity";
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback = new LocationCallback() {
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

                txtgetlatitude.setText(latitude);
                txtgetlongitude.setText(longitude);
            }
        }
    };

    // Uri
    private Uri imageUri;

    // File
    private File file;

    //ImageBitmap
    private Bitmap imageBitmap;

    //ProgressBar
    private ProgressBar pgbarSampel;



    public CekSampleFrgament() {
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
        view = inflater.inflate(R.layout.fragment_cek_sample_frgament, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolBarCekSampel);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        autoCompleteTextViewJenisAyamSampel = view.findViewById(R.id.autoCompleteJenisAyamSampel);
        autoCompleteTextViewUmurAyamSampel = view.findViewById(R.id.autoCompleteUmurAyamSampel);
        linearLayoutTambahFoto = view.findViewById(R.id.tambah_foto_sampel);
        editTextJumlahAyamSampel = view.findViewById(R.id.edt_jumlah_ayam_sampel);
        editTextKondisiAyamSampel = view.findViewById(R.id.edt_kondisi_ayam_sampel);
        editTextTotalAyamSampel = view.findViewById(R.id.edt_total_ayam_keselurahan);
        txtkodekandang = view.findViewById(R.id.txt_kode_kandang);
        txtkodeblok = view.findViewById(R.id.txt_kode_blok);
        btnSimpanSampling = view.findViewById(R.id.btn_simpan_sampling);


        //cek
        txtTotalBobotAyam = view.findViewById(R.id.txt_bobot_cek);
        txtfotocek_sampel = view.findViewById(R.id.txt_foto_cek_sampel);


        linearLayout = view.findViewById(R.id.layout_list);
        textIn = view.findViewById(R.id.textin);
        buttonAdd = view.findViewById(R.id.add);

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

        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        buttonShowAll = (RelativeLayout) view.findViewById(R.id.showall);
        buttonShowAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                sumMyIntValues();
            }});



        txtBobotRataRata = view.findViewById(R.id.txt_bobot_rata_rata_cek);
        btncek = view.findViewById(R.id.btn_hitung_bobot_cek);
        btncek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            incassoMargherita();


                if (editTextJumlahAyamSampel.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Harap Masukkan Jumlah Sampel Dulu", Toast.LENGTH_SHORT).show();
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


        //Textview
        txtiduser = view.findViewById(R.id.txt_id_user_sampling);
        txtnamauser = view.findViewById(R.id.txt_nama_user_sampling);
        txtgetdate = view.findViewById(R.id.txt_get_date_sampling);
        txtgetlatitude = view.findViewById(R.id.txt_latitude_sampling);
        txtgetlongitude = view.findViewById(R.id.txt_longitude_sampling);
        txtidkandang = view.findViewById(R.id.txt_get_id_kandang_sampling);
        txtidchickin = view.findViewById(R.id.txt_id_chickin);

        //EditText
//        editTextHasilRata = view.findViewById(R.id.edt_hasil_rata_rata_bobot_sampling);


        imageViewHasilFotoSampel = view.findViewById(R.id.hasil_foto_Sampel);

        //Progress Bar
        pgbarSampel = view.findViewById(R.id.pgbar_ceksampel);


        String iduser = getArguments().getString(ID_USER);
        String namauser = getArguments().getString(NAMA_USER);
        String kodekandang = getArguments().getString(KODE_KANDANG);
        String kodeblok = getArguments().getString(KODE_BLOK);
        String idkandang = getArguments().getString(ID_KANDANG);
        String jumlahayam = getArguments().getString(JUMLAH_AYAM_SEKARANG);
        String idchickin = getArguments().getString(ID_CHICKIN);


        txtiduser.setText(iduser);
        txtnamauser.setText(namauser);
        txtkodekandang.setText(kodekandang);
        txtkodeblok.setText(kodeblok);
        txtidkandang.setText(idkandang);
        editTextTotalAyamSampel.setText(jumlahayam);
        txtidchickin.setText(idchickin);

        Log.d("idchickin", "onCreateView: " + txtidchickin.getText().toString());

        //Method
        ambilBukti();
        SimpanSampel();

        SpinnerJenisAyamSampel();
        SpinnerUmurAyamSampel();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
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


    private void SimpanSampel(){
      btnSimpanSampling.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if (autoCompleteTextViewUmurAyamSampel.getText().toString().equals("Pilih Umur Ayam")){
                  autoCompleteTextViewUmurAyamSampel.setError("Harap Pilih Umur Ayam");
              }

              else if (autoCompleteTextViewJenisAyamSampel.getText().toString().equals("Pilih Jenis Ayam")){
                  autoCompleteTextViewJenisAyamSampel.setError("Harap Pilih Jenis Ayam");
              }

              else if (TextUtils.isEmpty(editTextKondisiAyamSampel.getText().toString())){
                  editTextKondisiAyamSampel.setError("Harap Isi Kondisi Sampel");
              }

              else if (TextUtils.isEmpty(editTextJumlahAyamSampel.getText().toString())){
                  editTextJumlahAyamSampel.setError("Harap Isi Jumlah Sampel");
              }
              else if (txtTotalBobotAyam.getText().equals("")){
                  Toast.makeText(getContext(), "Harap Hitung Total Bobot", Toast.LENGTH_SHORT).show();
              } else if (txtBobotRataRata.getText().equals("0.00 kg")){
                  Toast.makeText(getContext(), "Harap Hitung Total Bobot Rata-Rata", Toast.LENGTH_SHORT).show();
              } else if (txtfotocek_sampel.getText().equals("")){
                  Toast.makeText(getContext(), "Harap Masukkan Bukti Foto Sampel", Toast.LENGTH_SHORT).show();
              }
              else {
                  saveSampel();
              }

          }
      });
    }


    private void saveSampel(){
        txtgetdate.setText(TanggalDanWaktu());
        file = new File(getRealPathFromURI(imageUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),requestBody);
//              APIRequestData apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        Call<BaseResponse.BaseResponseApi> call = service.inputDataSampel(
                RequestBody.create(MediaType.parse("text/plain"),txtgetdate.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtnamauser.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtkodekandang.getText().toString()),//sudah
                RequestBody.create(MediaType.parse("text/plain"),txtkodeblok.getText().toString()),//sudah
                RequestBody.create(MediaType.parse("text/plain"),editTextTotalAyamSampel.getText().toString()),//sudah
                RequestBody.create(MediaType.parse("text/plain"), autoCompleteTextViewUmurAyamSampel.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), editTextKondisiAyamSampel.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), txtBobotRataRata.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), autoCompleteTextViewJenisAyamSampel.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), txtTotalBobotAyam.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), editTextJumlahAyamSampel.getText().toString()),// sudah
                RequestBody.create(MediaType.parse("text/plain"), txtgetlatitude.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), txtgetlongitude.getText().toString()),
                part,
                RequestBody.create(MediaType.parse("text/plain"),txtiduser.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtidkandang.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),txtidchickin.getText().toString()));

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Tambah Data Sampel Berhasil", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Tambah Data Sampel Gagal", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
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
    public void onResume() {
        super.onResume();

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
//            image_bukti_ceksampel.setImageBitmap(imageBitmap);
            imageViewHasilFotoSampel.setImageBitmap(imageBitmap);
            imageViewHasilFotoSampel.setVisibility(View.VISIBLE);
            txtfotocek_sampel.setText("fotosampel");
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
        linearLayoutTambahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }


    public void SpinnerJenisAyamSampel(){
        String[] jenisAyam = getResources().getStringArray(R.array.jenis_ayam);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(),R.layout.dropdown_item,jenisAyam);
        autoCompleteTextViewJenisAyamSampel.setAdapter(arrayAdapter);
    }

    public void SpinnerUmurAyamSampel(){
        String[] umurAyam = getResources().getStringArray(R.array.umur_ayam);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(),R.layout.dropdown_item,umurAyam);
        autoCompleteTextViewUmurAyamSampel.setAdapter(arrayAdapter);
    }

    public static CekSampleFrgament newInstance(String id,String idkandang,String namauser,String kodekandang, String kodeblok, String jumlahayaam, String idchickin) {

        Bundle args = new Bundle();
        CekSampleFrgament fragment = new CekSampleFrgament();
        args.putString(ID_USER, id);
        args.putString(ID_KANDANG, idkandang);
        args.putString(NAMA_USER,namauser);
        args.putString(KODE_KANDANG,kodekandang);
        args.putString(KODE_BLOK,kodeblok);
        args.putString(JUMLAH_AYAM_SEKARANG,jumlahayaam);
        args.putString(ID_CHICKIN,idchickin);
        fragment.setArguments(args);
        return fragment;
    }
}