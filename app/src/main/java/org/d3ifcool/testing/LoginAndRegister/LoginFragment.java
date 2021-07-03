package org.d3ifcool.testing.LoginAndRegister;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.RestAPI.DataAPI;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    TextView registrasi;
    TextInputLayout username,password;
    MaterialButton buttonlogin;
    private ProgressBar pgbarLogin;
    OnLoginFormActivityListener onLoginFormActivityListener;

    public interface OnLoginFormActivityListener{
        public void performRegister();
        public void performLogin(String id, String nama);
    }

    public LoginFragment() {
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
        view =  inflater.inflate(R.layout.fragment_login, container, false);




        username = view.findViewById(R.id.text_input_username_login);
        password = view.findViewById(R.id.text_input_password_login);
        buttonlogin = view.findViewById(R.id.btn_login);
        pgbarLogin = view.findViewById(R.id.pgbar_login);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        registrasi = view.findViewById(R.id.register_text);
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onLoginFormActivityListener.performRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onLoginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

    public void performLogin(){
        pgbarLogin.setVisibility(View.VISIBLE);
        Call<BaseResponse.user> call = MainActivity.service.postLogin(
               username.getEditText().getText().toString(),
                username.getEditText().getText().toString(),
                password.getEditText().getText().toString()
        );
        call.enqueue(new Callback<BaseResponse.user>() {
            @Override
            public void onResponse(Call<BaseResponse.user> call, Response<BaseResponse.user> response) {
                if (!validateUsename() || !validatePass()) {
                    return;
                } else {
                    if (response.code() == 200) {
                        pgbarLogin.setVisibility(View.INVISIBLE);
                        MainActivity.prefConfig.writeLoginStatus(true);
                        onLoginFormActivityListener.performLogin(response.body().getId(), response.body().getNama());
                        MainActivity.prefConfig.displayToast("Login Berhasil");
                    }else if (response.code() == 404) {
                        pgbarLogin.setVisibility(View.VISIBLE);
                        MainActivity.prefConfig.displayToast("Maaf Akun Anda Sementara Tidak Di Aktifkan");
                        pgbarLogin.setVisibility(View.INVISIBLE);
                    }else {
                        MainActivity.prefConfig.displayToast("Login Gagal");
                    }
                }
            }
            @Override
            public void onFailure(Call<BaseResponse.user> call, Throwable t) {
                Log.d("Gagals", "onFailure: " + t.getMessage());
            }
        });
    }


    private boolean validateUsename(){
        String usernameLogin = username.getEditText().getText().toString().trim();
        if (usernameLogin.isEmpty()){
            username.setError("Username Tidak Boleh Kosong");
            pgbarLogin.setVisibility(View.INVISIBLE);
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePass(){
        String passLogin = password.getEditText().getText().toString().trim();
        if (passLogin.isEmpty()){
            password.setError("Password Tidak Boleh Kosong");
            pgbarLogin.setVisibility(View.INVISIBLE);
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }
}