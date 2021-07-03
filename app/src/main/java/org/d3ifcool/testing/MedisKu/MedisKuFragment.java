package org.d3ifcool.testing.MedisKu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Adapter.MedisAdapter;
import org.d3ifcool.testing.Adapter.UploadAdapter;
import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.MedisData;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;
import org.d3ifcool.testing.PengecekanFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedisKuFragment extends Fragment {

    private DataService service;
    private RecyclerView rvMedis;
    private MedisAdapter adapter;
    ArrayList<MedisData> dataArrayList;
    SearchView editTextSearchMedisku;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_medis_ku, container, false);
        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);

        Toolbar toolbar = view.findViewById(R.id.toolBarIdMedisku);
//        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        rvMedis = view.findViewById(R.id.rv_medis);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false);
        rvMedis.setLayoutManager(gridLayoutManager);
        rvMedis.setItemAnimator(new DefaultItemAnimator());
        rvMedis.setAdapter(adapter);
//        loadData();
        editTextSearchMedisku = view.findViewById(R.id.edtSearchMedisku);
        editTextSearchMedisku.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMedis);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();

            }
        },1000);


        return view;

    }

    private void loadData(){
        Call<BaseResponse.BaseResponseApi<List<MedisData>>> call = service.getMedis();
        call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<MedisData>>>() {
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.MedisData>>> call, Response<BaseResponse.BaseResponseApi<List<MedisData>>> response) {
                if (response.code() == 200) {
                    dataArrayList = new ArrayList<>(response.body().getData());
                    adapter = new MedisAdapter(getContext(), dataArrayList, new MedisAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(MedisData user, int position) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, TentangMedisFragment.newInstance(user.getNama_medis(),user.getImage_medis(),user.getDeskripsi_medis(),user.getGejala_medis())).addToBackStack(null).commit();
                            Toast.makeText(getContext(), "" + user.getNama_medis(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    rvMedis.setAdapter(adapter);
//                    adapter.addAll(response.body().getData());
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<MedisData>>> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
}