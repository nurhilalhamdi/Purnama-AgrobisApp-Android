package org.d3ifcool.testing.KandangKu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Adapter.UploadAdapter;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KandangKuFragment extends Fragment {

//    RecyclerView recyclerView;
//    RecyclerView.Adapter adapter;
    private DataService service;


    public interface OnKandangKuListener{
        void tambahkandangPerformed(String id);
    }
    OnKandangKuListener onkandangKuListener;

    private RecyclerView rvData;
    private UploadAdapter adapter;
    ArrayList<Data> dataArrayList;

    TextView txtIdUser;
    TextView txtKandangAktif;
    CardView tambahKandangPribadi;
    SearchView editTextSearch;

//    List<Data> list;

    public KandangKuFragment() {
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




        view =  inflater.inflate(R.layout.fragment_kandang_ku, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolBarId);
//        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        txtIdUser = view.findViewById(R.id.txt_id_user);
        txtIdUser.setText(MainActivity.prefConfig.readId());
        txtKandangAktif = view.findViewById(R.id.txt_kandang_aktif);
        tambahKandangPribadi = view.findViewById(R.id.menuKandangPribadi);


//        list = new ArrayList<>();
        editTextSearch = view.findViewById(R.id.edtSearch);


//        editTextSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//            }
//        });

//        rvData = view.findViewById(R.id.rvSampel);

//        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
//        rvData.setAdapter(adapter);





        rvData = view.findViewById(R.id.rvSampel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);
        loadData();


            tambahKandangPribadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onkandangKuListener.tambahkandangPerformed(txtIdUser.getText().toString().trim());
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onkandangKuListener = (OnKandangKuListener) activity;

    }



    public void loadData(){
        Call<BaseResponse.BaseResponseApi<List<Data>>> call = service.apiRead(txtIdUser.getText().toString());
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<Data>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());
                    adapter = new UploadAdapter(getContext(), dataArrayList, new UploadAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(Data user, int position) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PengecekanFragment.newInstance(user.getId_kandang())).addToBackStack(null).commit();
                            Toast.makeText(getContext(), "" + user.getId_kandang(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    editTextSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            adapter.getFilter().filter(s);
                            return false;
                        }
                    });
                    rvData.setAdapter(adapter);
//                    adapter.addAll(response.body().getData());
                    int count = adapter.getItemCount();

                    txtKandangAktif.setText(" " + count + " Kandang aktif");
                } else if (response.code() == 500) {
                    txtKandangAktif.setText(" Tidak ada kandang");
                    txtKandangAktif.setBackgroundResource(R.drawable.bg_decline);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.menu_item,menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = null;
//        if (searchItem != null){
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return true;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    //
//    public void filter(String text){
//        ArrayList<Data> filterList = new ArrayList<>();
//        for (Data item : list){
//            if (item.getAlamat_kandang().toLowerCase().contains(text.toLowerCase())){
//                filterList.add(item);
//            }
//        }
//        adapter.filteredList(filterList);
//    }

}