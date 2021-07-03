package org.d3ifcool.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.KandangKu.KandangKuFragment;

import org.d3ifcool.testing.LoginAndRegister.LoginFragment;
import org.d3ifcool.testing.LoginAndRegister.PrefConfig;
import org.d3ifcool.testing.LoginAndRegister.RegisFragment;
import org.d3ifcool.testing.MedisKu.MedisKuFragment;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanHarian.CekHarianFragment;
import org.d3ifcool.testing.PengecekanSampel.CekSampleFrgament;
import org.d3ifcool.testing.Profil.ProfileFragment;
import org.d3ifcool.testing.RestAPI.APIRequestData;
import org.d3ifcool.testing.RestAPI.Server;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnLoginFormActivityListener,
        HomeFragment.OnHomeListener,
        RegisFragment.OnAfterRegist,
        KandangKuFragment.OnKandangKuListener,
        PengecekanFragment.OnPengecekanListener{


    public static APIRequestData apiRequestData;

    public static DataService service;

    //prefloginregister variabel
    public static PrefConfig prefConfig;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiRequestData = Server.konekRetrofit().create(APIRequestData.class);
        service = ServiceGenerator.createBaseService(this, DataService.class);


//...

        //preflogin
        prefConfig = new PrefConfig(this);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (prefConfig.readLoginStatus()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment()).commit();
            }
        }


//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemReselectedListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_kandangku:
                    selectedFragment = new KandangKuFragment();
                    break;
                case R.id.nav_medisku:
                    selectedFragment = new MedisKuFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).addToBackStack(null).commit();

            return true;
        }
    };

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performLogin(String id, String nama) {
        prefConfig.writeName(nama);
        prefConfig.writeId(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }


    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        prefConfig.writeId("Id");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }

    @Override
    public void cekharianPerformed(String id, String nama) {
        prefConfig.writeName(nama);
        prefConfig.writeId(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CekHarianFragment()).addToBackStack(null).commit();
    }

    @Override
    public void ceksampelPerformed(String id, String idkandang,String nama, String kodekandang, String kodeblok , String total_ayam_sekarang , String idchickin) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CekSampleFrgament.newInstance(id,idkandang,nama,kodekandang,kodeblok,total_ayam_sekarang, idchickin)).addToBackStack(null).commit();
    }



    @Override
    public void tambahKandangPerformed(String id) {
        prefConfig.writeId(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TambahKandangFragment()).addToBackStack(null).commit();
    }

    @Override
    public void afterRegistPerformed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }

//
//    @Override
//    public void pengecekanPerformed(String id, String kodekandang, String kodeblok) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PengecekanFragment.newInstance(id,kodekandang,kodeblok)).addToBackStack(null).commit();
//    }

    @Override
    public void tambahkandangPerformed(String id) {
        prefConfig.writeId(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TambahKandangFragment()).addToBackStack(null).commit();
    }
}