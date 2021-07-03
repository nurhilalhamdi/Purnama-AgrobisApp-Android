package org.d3ifcool.testing.LoginAndRegister;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.RestAPI.DataAPI;

import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisFragment extends Fragment {
    TextInputLayout nama,username,alamat,email,notlp,password,konfirmasipass;
    Button btnregis;

    String status = "Aktif";

    OnAfterRegist onAfterRegist;

    public interface OnAfterRegist{
        public void afterRegistPerformed();
    }

    public RegisFragment() {
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
        view =  inflater.inflate(R.layout.fragment_regis, container, false);
        nama = view.findViewById(R.id.text_input_nama_register);
        username = view.findViewById(R.id.text_input_username_register);
        alamat = view.findViewById(R.id.text_input_alamat_register);
        email = view.findViewById(R.id.text_input_email_register);
        notlp = view.findViewById(R.id.text_input_telepon_register);
        password = view.findViewById(R.id.text_input_password_register);
        konfirmasipass = view.findViewById(R.id.text_input_konfirmasi_password_register);
        btnregis = view.findViewById(R.id.btn_daftar);



        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateNama() || !validateUsername() || !validateAlamat() || !validateEmail() || !validateNoTelepone() || !validatePassword() || !validateKonfirmPassword()) {
                    return;
                } else {
                    performRegis();
                }
            }
        });
        return view;
    }

//    password.getEditText().getText().toString(),
    public void performRegis(){
        Call<BaseResponse.BaseResponseApi> call = MainActivity.service.perfomRegister(
                nama.getEditText().getText().toString(),
                username.getEditText().getText().toString(),
                alamat.getEditText().getText().toString(),
                email.getEditText().getText().toString(),
                notlp.getEditText().getText().toString(),
                password.getEditText().getText().toString(),
                 status,
                konfirmasipass.getEditText().getText().toString());


        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
//                if (!validateNama() || !validateUsername() || !validateAlamat() || !validateEmail() || !validateNoTelepone() || !validatePassword() || !validateKonfirmPassword()) {
//                    return;
//                } else {
                    if (response.code() == 200){
                        Toast.makeText(getContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        onAfterRegist.afterRegistPerformed();
                    }else if (response.code() == 404){
                        Toast.makeText(getContext(), "Username Telah Digunakan", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Gagals", "onFailure: " + t.getMessage());
                MainActivity.prefConfig.displayToast("Registrasi Gagal");
            }
        });

//        nama.setText(null);
//        alamat.setText(null);
//        email.setText(null);
//        username.setText(null);
//        password.setText(null);
//        notlp.setText(null);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onAfterRegist = (OnAfterRegist) activity;
    }


    private boolean validateEmail(){
        String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            email.setError("Email tidak boleh kosong");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email.setError("Email tidak valid");
            return false;
        }else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername(){
        Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z\\d_-]{2,15}$");
        String usernameInput = username.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()){
            username.setError("Username tidak boleh kosong");
            return false;
        }else if (!USERNAME_PATTERN.matcher(usernameInput).matches()){
            username.setError("Username tidak valid");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        Pattern PASSWORD_PATTERN = Pattern.compile("^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$");
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Password tidak boleh kosong");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password terlalu lemah, harus mengandung karakter a-zA-Z dan @#$%^&+=");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean validateKonfirmPassword() {
        String konfirm = konfirmasipass.getEditText().getText().toString().trim();
        String pasword = password.getEditText().getText().toString().trim();
        if (konfirm.isEmpty()) {
            konfirmasipass.setError("Konfirmasi Password tidak boleh kosong");
            return false;
        }else if (!konfirm.equals(pasword)){
            konfirmasipass.setError("Password tidak sama");
            return false;
        }  else {
            password.setError(null);
            return true;
        }
    }

        private boolean validateNama(){
            String namaInput = nama.getEditText().getText().toString().trim();
            if (namaInput.isEmpty()){
                nama.setError("Nama tidak boleh kosong");
                return false;
            }else {
                nama.setError(null);
                return true;
            }
    }

    private boolean validateAlamat(){
        String alamatInput = alamat.getEditText().getText().toString().trim();
        if (alamatInput.isEmpty()){
            alamat.setError("Alamat tidak boleh kosong");
            return false;
        }else {
            alamat.setError(null);
            return true;
        }
    }

    private boolean validateNoTelepone(){
        String noteleponInput = notlp.getEditText().getText().toString().trim();
        if (noteleponInput.isEmpty()){
            notlp.setError("Nomor telepon tidak boleh kosong");
            return false;
        }else {
            notlp.setError(null);
            return true;
        }
    }




}