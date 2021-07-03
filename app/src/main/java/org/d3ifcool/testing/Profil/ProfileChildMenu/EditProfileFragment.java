package org.d3ifcool.testing.Profil.ProfileChildMenu;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private DataService service;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAMNAMA = "nama";
    private static final String ARG_PARAMUSERNAME = "username";
    private static final String ARG_PARAMALAMAT = "alamat";
    private static final String ARG_PARAMEMAIL = "email";
    private static final String ARG_PARAMNOTLP = "notlp";
    private static final String ARG_PARAM2PASS = "pass";
    private static final String ARG_PARAMIDUSER = "iduser";
    private static final String ARG_PARAMTEMPPASS = "temppass";

    ColorGenerator colorGenerator;
    ImageView avatarImage;
    // TODO: Rename and change types of parameters
    private String mNama;
    private String mUsername;
    private String mAlamat;
    private String mEmail;
    private String mNotlp;
    private String mPassword;
    private String mIduser;
    private String tempPass;

    CheckBox checkBox;

    TextInputLayout textInputLayoutNama;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutAlamat;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutNotlp;
    TextInputLayout textInputLayoutPassword;
    TextInputLayout textInputLayoutPasswordTemp;

    TextView textViewNamaUbah;
    TextView textViewUsernameUbah;
    TextView textViewAlamatUbah;
    TextView textViewEmailUbah;
    TextView textViewNotlpUbah;
    MaterialButton materialButtonUbah;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(
            String paramNama,
            String paramUsername,
            String paramAlamat,
            String paramEmail,
            String paramNoTlp,
            String paramPassword,
            String paramIdUser,
            String paramPasswordTemp) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAMNAMA, paramNama);
        args.putString(ARG_PARAMUSERNAME, paramUsername);
        args.putString(ARG_PARAMALAMAT, paramAlamat);
        args.putString(ARG_PARAMEMAIL, paramEmail);
        args.putString(ARG_PARAMNOTLP, paramNoTlp);
        args.putString(ARG_PARAM2PASS, paramPassword);
        args.putString(ARG_PARAMIDUSER, paramIdUser);
        args.putString(ARG_PARAMTEMPPASS, paramPasswordTemp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNama = getArguments().getString(ARG_PARAMNAMA);
            mUsername = getArguments().getString(ARG_PARAMUSERNAME);
            mAlamat = getArguments().getString(ARG_PARAMALAMAT);
            mEmail = getArguments().getString(ARG_PARAMEMAIL);
            mNotlp = getArguments().getString(ARG_PARAMNOTLP);
            mPassword = getArguments().getString(ARG_PARAM2PASS);
            mIduser = getArguments().getString(ARG_PARAMIDUSER);
            tempPass = getArguments().getString(ARG_PARAMTEMPPASS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);


        Toolbar toolbar = view.findViewById(R.id.toolBarUbahProfile);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        avatarImage = view.findViewById(R.id.avatarimage);
        textInputLayoutNama = view.findViewById(R.id.text_input_nama_ubah);
        textInputLayoutUsername = view.findViewById(R.id.text_input_username_ubah);
        textInputLayoutAlamat = view.findViewById(R.id.text_input_alamat_ubah);
        textInputLayoutEmail = view.findViewById(R.id.text_input_email_ubah);
        textInputLayoutNotlp = view.findViewById(R.id.text_input_telepon_ubah);
        textInputLayoutPassword = view.findViewById(R.id.text_input_password_ubah);
        textInputLayoutPasswordTemp = view.findViewById(R.id.text_input_password_temp_ubah);


        textViewNamaUbah = view.findViewById(R.id.txt_nama_petugas_ubah);
        textViewUsernameUbah = view.findViewById(R.id.txt_usernam_petugas_ubah);
        textViewAlamatUbah = view.findViewById(R.id.txt_alamat_petugas_ubah);
        textViewEmailUbah = view.findViewById(R.id.txt_email_petugas_ubah);
        textViewNotlpUbah = view.findViewById(R.id.txt_notlp_petugas_ubah);

        materialButtonUbah = view.findViewById(R.id.btn_ubah_profil);

                checkBox = view.findViewById(R.id.checkboxUbahPass);



        textViewNamaUbah.setText(mNama);
        textViewUsernameUbah.setText("@"+mUsername);
        textViewAlamatUbah.setText(mAlamat);
        textViewEmailUbah.setText(mEmail);
        textViewNotlpUbah.setText(mNotlp);



        textInputLayoutNama.getEditText().setText(mNama);
        textInputLayoutUsername.getEditText().setText(mUsername);
        textInputLayoutAlamat.getEditText().setText(mAlamat);
        textInputLayoutEmail.getEditText().setText(mEmail);
        textInputLayoutNotlp.getEditText().setText(mNotlp);
        textInputLayoutPassword.getEditText().setText(mPassword);
        textInputLayoutPasswordTemp.getEditText().setText(tempPass);


        colorGenerator = ColorGenerator.MATERIAL;
        String letter = String.valueOf(String.valueOf(textViewNamaUbah.getText().toString()).charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter,colorGenerator.getRandomColor());
        avatarImage.setImageDrawable(drawable);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    textInputLayoutPassword.setVisibility(View.VISIBLE);
                    textInputLayoutPasswordTemp.setVisibility(View.VISIBLE);
                }else {
                    textInputLayoutPassword.setVisibility(View.GONE);
                    textInputLayoutPasswordTemp.setVisibility(View.GONE);
                }
            }
        });

        materialButtonUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfiel();
            }
        });

        return view;
    }

    public void updateProfiel(){
        Call<BaseResponse.BaseResponseApi> call = service.editProfile(
                mIduser,
                textInputLayoutNama.getEditText().getText().toString(),
                textInputLayoutUsername.getEditText().getText().toString(),
                textInputLayoutAlamat.getEditText().getText().toString(),
                textInputLayoutEmail.getEditText().getText().toString(),
                textInputLayoutNotlp.getEditText().getText().toString(),
                textInputLayoutPassword.getEditText().getText().toString()
        );

        call.enqueue(new Callback<BaseResponse.BaseResponseApi>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi> call, Response<BaseResponse.BaseResponseApi> response) {
                if (response.code() == 200) {

                    Toast.makeText(getContext(), "Profile Berhasil Diubah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Profile Gagal Diubah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi> call, Throwable t) {
                Log.d("Kandang", "onFailure: " + t.getMessage());

            }
        });
    }
}