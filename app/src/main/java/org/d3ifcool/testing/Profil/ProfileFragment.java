package org.d3ifcool.testing.Profil;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataUser;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanHarian.CekHarianFragment;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataHarianMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataPanenMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataSampelMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.DataStokMenu;
import org.d3ifcool.testing.Profil.ProfileChildMenu.EditProfileFragment;
import org.d3ifcool.testing.Profil.ProfileChildMenu.PanduanFragment;
import org.d3ifcool.testing.Profil.ProfileChildMenu.RumusFragment;
import org.d3ifcool.testing.Profil.ProfileChildMenu.TentangKamiFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {


    List<DataUser> datas;
    LinearLayout menu_cekharian;
    LinearLayout menu_ceksampel;
    LinearLayout menu_datastok;
    LinearLayout menu_rumus;
    LinearLayout menu_panduan;
    LinearLayout menu_panen;
    LinearLayout menu_tentang;

    ImageView avatarImage;

    TextView textViewNamaPetugas, textViewIdPetugas, textViewNotlpPetugas, textViewEmailPetugas, textViewStatusPetugas;
    RelativeLayout btnEditProfile;

    ColorGenerator colorGenerator;


    private DataService service;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
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
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
        Toolbar toolbar = view.findViewById(R.id.toolBarProfile);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        avatarImage = view.findViewById(R.id.avatarimage);

        textViewIdPetugas = view.findViewById(R.id.txt_id_user_home);
        textViewNamaPetugas = view.findViewById(R.id.txt_nama_petugas_home);
        textViewNotlpPetugas = view.findViewById(R.id.txt_notlp_petugas_profile);
        textViewEmailPetugas = view.findViewById(R.id.txt_email_petugas_profile);
        textViewStatusPetugas = view.findViewById(R.id.txt_status_petugas_profile);

        menu_cekharian = view.findViewById(R.id.data_harian_menu);
        menu_ceksampel = view.findViewById(R.id.data_sampel_menu);
        menu_datastok = view.findViewById(R.id.data_stok_menu);
        menu_rumus = view.findViewById(R.id.data_rumus_menu);
        menu_panduan = view.findViewById(R.id.data_panduan_menu);
        menu_panen = view.findViewById(R.id.data_panen_menu);
        menu_tentang = view.findViewById(R.id.data_tentang_menu);


//        btnEditProfile = view.findViewById(R.id.btn_edit_profile);

        menu_cekharian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new DataHarianMenu()).addToBackStack(null).commit();
            }
        });
        menu_ceksampel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new DataSampelMenu()).addToBackStack(null).commit();
            }
        });
        menu_datastok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new DataStokMenu()).addToBackStack(null).commit();
            }
        });
        menu_rumus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new RumusFragment()).addToBackStack(null).commit();
            }
        });
        menu_panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new PanduanFragment()).addToBackStack(null).commit();
            }
        });
        menu_panen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new DataPanenMenu()).addToBackStack(null).commit();
            }
        });
        menu_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new TentangKamiFragment()).addToBackStack(null).commit();
            }
        });



        textViewNamaPetugas.setText(MainActivity.prefConfig.readName());
        textViewIdPetugas.setText(MainActivity.prefConfig.readId());





        LoadProfile();

        colorGenerator = ColorGenerator.MATERIAL;
        String letter = String.valueOf(String.valueOf(textViewNamaPetugas.getText().toString()).charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter,colorGenerator.getRandomColor());
        avatarImage.setImageDrawable(drawable);

        return view;
    }

    public void LoadProfile(){
        Call<BaseResponse.BaseResponseApi<List<DataUser>>> calls = service.getuserWhereID(MainActivity.prefConfig.readId());
        calls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataUser>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataUser>>> call, Response<BaseResponse.BaseResponseApi<List<DataUser>>> response) {
                if (response.code() == 200) {
                    datas = response.body().getData();
                    for (DataUser data2 : datas){
                        textViewEmailPetugas.setText(data2.getEmail());
                        textViewNotlpPetugas.setText(data2.getNo_telephone());
                        textViewStatusPetugas.setText(data2.getStatus());


//                        btnEditProfile.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//                                assert appCompatActivity != null;
//                                appCompatActivity.getSupportFragmentManager().beginTransaction().
//                                        replace(R.id.fragment_container,  EditProfileFragment.newInstance(textViewNamaPetugas.getText().toString(),data2.getUsername(),data2.getAlamat(),data2.getEmail(),data2.getNo_telephone(),data2.getPassword(),data2.getId_user(),data2.getKonfirmasi_password())).addToBackStack(null).commit();
//                            }
//                        });

                    }
                } else if (response.code() == 500) {


                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataUser>>> call, Throwable t){

            }
        });
    }
}