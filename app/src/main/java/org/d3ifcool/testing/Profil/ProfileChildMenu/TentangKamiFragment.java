package org.d3ifcool.testing.Profil.ProfileChildMenu;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.orgd3if4019.testing.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TentangKamiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TentangKamiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    MaterialButton materialButton;
    TextView textView;

    public TentangKamiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TentangKamiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TentangKamiFragment newInstance(String param1, String param2) {
        TentangKamiFragment fragment = new TentangKamiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_tentang_kami, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarTentangKami);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        materialButton = view.findViewById(R.id.button_privacy);
        textView = view.findViewById(R.id.txt_privacy);

        materialButton.setOnClickListener(new View.OnClickListener() {
            private boolean state = false;
            @Override
            public void onClick(View view) {
                if (state){
                    state = false;
                    materialButton.setText("Privacy Policy");
                    textView.setVisibility(View.GONE);
                }else {
                    state = true;
                    materialButton.setText("Privacy Policy");
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}