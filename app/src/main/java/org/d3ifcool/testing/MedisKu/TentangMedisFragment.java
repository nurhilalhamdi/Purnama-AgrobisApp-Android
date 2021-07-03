package org.d3ifcool.testing.MedisKu;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.PengecekanFragment;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;


public class TentangMedisFragment extends Fragment {

    private static final String NAMA_MEDIS = "namamedis";
    private static final String IMAGE_MEDIS = "imagemedis";
    private static final String DESKRIPSI_MEDIS = "deskripsimedis";
    private static final String GEJALA_MEDIS = "gejalamedis";
    TextView txtDeskripsiMedis;
    TextView txtGejalaMedis;
    ImageView imgMedis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_tentang_medis, container, false);





        String namamedis = getArguments().getString(NAMA_MEDIS);
        String imagemedis = getArguments().getString(IMAGE_MEDIS);
        String deskripsimedis = getArguments().getString(DESKRIPSI_MEDIS);
        String gejalamedis = getArguments().getString(GEJALA_MEDIS);
        txtDeskripsiMedis = view.findViewById(R.id.txt_deskripsi_medis);
        txtDeskripsiMedis.setText(deskripsimedis);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtDeskripsiMedis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        txtGejalaMedis = view.findViewById(R.id.txt_gejala_medis);
        txtGejalaMedis.setText(gejalamedis);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtGejalaMedis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        imgMedis = view.findViewById(R.id.image_medis);

        Glide.with(getContext()).load(imagemedis)
                .apply(RequestOptions.centerCropTransform())
                .into(imgMedis);

        Toolbar toolbar = view.findViewById(R.id.toolBarTentangMedis);
//        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setTitle("Tentang " + namamedis);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public static TentangMedisFragment newInstance(
            String namamedis,
            String imagemedis,
            String deskripsiMedis,
            String gejalamedis) {

        Bundle args = new Bundle();
        TentangMedisFragment fragment = new TentangMedisFragment();
        args.putString(NAMA_MEDIS,namamedis);
        args.putString(IMAGE_MEDIS,imagemedis);
        args.putString(DESKRIPSI_MEDIS,deskripsiMedis);
        args.putString(GEJALA_MEDIS,gejalamedis);
        fragment.setArguments(args);
        return fragment;
    }
}