package org.d3ifcool.testing;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Adapter.AdapterKandang;
import org.d3ifcool.testing.Adapter.RecyclerViewInterface;
import org.d3ifcool.testing.Adapter.UploadAdapter;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.Kandang;
import org.d3ifcool.testing.KandangKu.KandangKuFragment;
import org.d3ifcool.testing.LoginAndRegister.DataUserLogin;
import org.d3ifcool.testing.LoginAndRegister.GetDataUserLogin;
import org.d3ifcool.testing.MedisKu.MedisKuFragment;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PerformaKandang.PerformaKandangFragment;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataHarianMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataPanenMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataSampelMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataStokMenu;
import org.d3ifcool.testing.Profil.ProfileFragment;
import org.d3ifcool.testing.RestAPI.APIRequestData;
import org.d3ifcool.testing.RestAPI.DataAPI;
import org.d3ifcool.testing.RestAPI.Server;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeFragment extends Fragment implements RecyclerViewInterface {


    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    TextView lat, lon;
    TextView message;
    String API = "7f04277a58b3c9044ed01144175cada4";
    TextView tempTxt, windTxt,  humidityTxt;
//Button

    TextView btnlogout;
    FloatingActionButton fabTambahKandang;
    ImageView avatarImage;
    CardView menuKandangKu;
    CardView menuMedisKu;

    CardView submenuCekHarian;
    CardView submenuCekSampel;
    CardView submenuDataStok;
    CardView submenuDataPanen;

    LinearLayout linearLayoutMenuProfile;

//TextView
    TextView txtNamaPetugas;
    TextView txtIdUser;




//Interface
    OnHomeListener homeListener;
    List<DataUserLogin> SampelList;

    private DataService service;


    List<Data> KandangList;

    @Override
    public void OnItemClick(int position) {
//        HomeFragment.newInstance(KandangList.get(position).getId());
//        Fragment fragment =  PengecekanFragment.newInstance(KandangList.get(position).getId());
//        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
//        fragmentTransaction.replace(R.id.fragment_container,fragment);
//        fragmentTransaction.commit();
//        homeListener.pengecekanPerformed(KandangList.get(position).getId_kandang(),KandangList.get(position).getKodekandang(),KandangList.get(position).getKodeblok());
//        Toast.makeText(this.getContext(), KandangList.get(position).getId_kandang(), Toast.LENGTH_SHORT).show();
    }


    public interface OnHomeListener{
        void logoutPerformed();
        void tambahKandangPerformed(String id);
//        void pengecekanPerformed(String id,String kodekandang, String kodeblok);
    }

    APIRequestData apiRequestData;
    //Recylerview
//    RecyclerView recyclerView;
//    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private RecyclerView rvData;
    private UploadAdapter adapter;

    ColorGenerator colorGenerator;



    public static HomeFragment hf;




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        linearLayoutMenuProfile = view.findViewById(R.id.linearLayoutMenuProfile);

        btnlogout = view.findViewById(R.id.btn_logout);
        txtNamaPetugas = view.findViewById(R.id.txt_nama_petugas);

//        fabTambahKandang = view.findViewById(R.id.fab_tambah_kandang);
//        avatarImage = view.findViewById(R.id.avatarimage);

        menuKandangKu = view.findViewById(R.id.menuKandangKu);
        menuKandangKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KandangKuFragment()).addToBackStack(null).commit();

            }
        });

        menuMedisKu = view.findViewById(R.id.menuMedisKu);
        menuMedisKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MedisKuFragment()).addToBackStack(null).commit();

            }
        });

        linearLayoutMenuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,  new ProfileFragment()).addToBackStack(null).commit();
            }
        });

        submenuCekHarian = view.findViewById(R.id.submenuCekharian);
        submenuCekHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,  new DataHarianMenu()).addToBackStack(null).commit();
            }
        });

        submenuCekSampel = view.findViewById(R.id.submenuCeksampel);
        submenuCekSampel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,  new DataSampelMenu()).addToBackStack(null).commit();
            }
        });
        submenuDataStok = view.findViewById(R.id.submenuDataStok);
        submenuDataStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,  new DataStokMenu()).addToBackStack(null).commit();
            }
        });
        submenuDataPanen = view.findViewById(R.id.submenuDataPanen);
        submenuDataPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,  new DataPanenMenu()).addToBackStack(null).commit();
            }
        });



        tempTxt = view.findViewById(R.id.txt_weather);
        humidityTxt = view.findViewById(R.id.txt_humidity);
        windTxt = view.findViewById(R.id.txt_wind);
        txtNamaPetugas.setText(MainActivity.prefConfig.readName());
        lat = view.findViewById(R.id.lat);
        lon = view.findViewById(R.id.longi);
        message = view.findViewById(R.id.txt_message);

//
//        colorGenerator = ColorGenerator.MATERIAL;
//        String letter = String.valueOf(String.valueOf(txtNamaPetugas.getText().toString()).charAt(0));
//        TextDrawable drawable = TextDrawable.builder().buildRound(letter,colorGenerator.getRandomColor());
//        avatarImage.setImageDrawable(drawable);

        btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                            getContext());

                    // set title dialog
                    alertDialogBuilder.setTitle("Konfirmasi Logout");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Anda Yakin Ingin Logout Dari Akun Anda")
                            .setIcon(R.drawable.ic_information)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogs,int ids) {

                                    homeListener.logoutPerformed();
                                    dialogs.cancel();



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
                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }

        });

//        fabTambahKandang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             homeListener.tambahKandangPerformed(txtIdUser.getText().toString().trim());
//            }
//        });

        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
//
//        rvData = view.findViewById(R.id.rvSampel);
//        adapter = new UploadAdapter(getContext());
//        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
//        rvData.setAdapter(adapter);
        loadData();
//        refresh();
        tesLogout();
        hf = this;

        Locale locale = Locale.getDefault(); //OR Locale.getDefault()

        Geocoder geocoder = new Geocoder(getContext(), locale);


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            message.setText("Selamat Pagi,");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            message.setText("Selamat Siang,");
        }else if(timeOfDay >= 16 && timeOfDay < 18){
            message.setText("Selamat Sore,");
        }else if(timeOfDay >= 18 && timeOfDay < 24){
            message.setText("Selamat Malam,");
        }

        locationTrack = new LocationTrack(getContext());
        if (locationTrack.canGetLocation()) {
            TextView lokasi = (TextView)view.findViewById(R.id.txt_lokasi);
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                lokasi.setText(addresses.get(0).getSubAdminArea());
            } catch (IOException e) {
                e.printStackTrace();
            }

            lat.setText(latitude+"");
            lon.setText(longitude+"");

        } else {
            locationTrack.showSettingsAlert();
        }


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                    String result = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + lat.getText().toString() + "&lon="+lon.getText().toString()+"&units=metric&appid=" + API);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            JSONObject main = jsonObj.getJSONObject("main");
                            JSONObject sys = jsonObj.getJSONObject("sys");
                            JSONObject wind = jsonObj.getJSONObject("wind");
                            JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                            Long updatedAt = jsonObj.getLong("dt");
                            String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                            String temp = main.getString("temp") + "°C";
                            String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                            String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                            String pressure = main.getString("pressure");
                            String humidity = main.getString("humidity");

                            Long sunrise = sys.getLong("sunrise");
                            Long sunset = sys.getLong("sunset");
                            String windSpeed = wind.getString("speed");
                            String weatherDescription = weather.getString("description");

                            String address = jsonObj.getString("name") + ", " + sys.getString("country");


                            /* Populating extracted data into our views */

                            tempTxt.setText(temp);

                            windTxt.setText(windSpeed + " Km/h");

                            humidityTxt.setText(humidity + " %");




                        } catch (JSONException e) {
                        }
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        homeListener = (OnHomeListener) activity;

    }

    private void loadData(){
        Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call = service.apiRead(MainActivity.prefConfig.readId());
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> response) {
                if(response.code() == 200) {
//                    adapter.addAll(response.body().getData());

                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }

//    public void  refresh(){
//        Call<DataAPI.kandangApi> call = apiRequestData.getKandang(txtIdUser.getText().toString());
//        call.enqueue(new Callback<DataAPI.kandangApi>() {
//            @Override
//            public void onResponse(Call<DataAPI.kandangApi> call, Response<DataAPI.kandangApi> response) {
//                if (response.body().getStatus().equals("Ok")) {
//                     KandangList = response.body().getListDataKandang();
//                    Log.d("Retrofit Get", "Jumlah data Kontak: " + String.valueOf(KandangList.size()));
//                    setAdapter();
//                }else if (response.body().getStatus().equals("fail")){
//                    Toast.makeText(getContext(), "Data Kandang Telah Di Hapus Oleh Server", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DataAPI.kandangApi> call, Throwable t) {
//                Log.e("Retrofit Get", t.toString());
//            }
//        });
//    }

//    public void setAdapter(){
//        adapter = new AdapterKandang(KandangList,this);
//        recyclerView.setAdapter(adapter);
//    }
    
    public void  tesLogout(){
        Call<BaseResponse.GetUserLogin> sampelCall = service.getUserLogin(MainActivity.prefConfig.readId());
        sampelCall.enqueue(new Callback<BaseResponse.GetUserLogin>() {
            @Override
            public void onResponse(Call<BaseResponse.GetUserLogin> call, Response<BaseResponse.GetUserLogin> response) {

                if (response.code() == 200) {
                     SampelList = response.body().getListdatauserlogin();
                    Log.d("Get", "Jumlah data Kontak: " + String.valueOf(SampelList.get(0).getId()));

                }else if (response.code() == 204){
                    Toast.makeText(getContext(), "Maaf Akun Anda Telah Dihapus", Toast.LENGTH_LONG).show();
                    homeListener.logoutPerformed();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.GetUserLogin> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });


    }







//    class weatherTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            /* Showing the ProgressBar, Making the main design GONE */
//            findViewById(R.id.loader).setVisibility(View.VISIBLE);
//            findViewById(R.id.mainContainer).setVisibility(View.GONE);
//            findViewById(R.id.errorText).setVisibility(View.GONE);
//        }
//
//        protected String doInBackground(String... args) {
//            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon="+lon+"&units=metric&appid=" + API);
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//
//            try {
//                JSONObject jsonObj = new JSONObject(result);
//                JSONObject main = jsonObj.getJSONObject("main");
//                JSONObject sys = jsonObj.getJSONObject("sys");
//                JSONObject wind = jsonObj.getJSONObject("wind");
//                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
//
//                Long updatedAt = jsonObj.getLong("dt");
//                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
//                String temp = main.getString("temp") + "°C";
//                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
//                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
//                String pressure = main.getString("pressure");
//                String humidity = main.getString("humidity");
//
//                Long sunrise = sys.getLong("sunrise");
//                Long sunset = sys.getLong("sunset");
//                String windSpeed = wind.getString("speed");
//                String weatherDescription = weather.getString("description");
//
//                String address = jsonObj.getString("name") + ", " + sys.getString("country");
//
//
//                /* Populating extracted data into our views */
//
//                tempTxt.setText(temp);
//
//
//                windTxt.setText(windSpeed);
//
//                humidityTxt.setText(humidity);
//
//                /* Views populated, Hiding the loader, Showing the main design */
//
//
//            } catch (JSONException e) {
//
//            }
//
//        }
//    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }



    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }



}