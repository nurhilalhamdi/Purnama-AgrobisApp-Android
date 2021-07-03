package org.d3ifcool.testing.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.Kandang;
import org.d3ifcool.testing.HomeFragment;
import org.d3ifcool.testing.MainActivity;
import org.d3ifcool.testing.PengecekanFragment;
import org.d3ifcool.testing.RestAPI.DataAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class AdapterKandang extends RecyclerView.Adapter<AdapterKandang.MyViewHolder> {

    List<Kandang> mKandangList;
    Context context;

    RecyclerViewInterface recyclerViewInterface;
    public AdapterKandang(List<Kandang> KandangList, RecyclerViewInterface recyclerViewInterface){
        this.mKandangList = KandangList;
        this.recyclerViewInterface = recyclerViewInterface;
    }





    @NonNull
    @Override
    public AdapterKandang.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sampel, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mView);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterKandang.MyViewHolder holder, int position) {
        holder.mTextViewId.setText("ID . " + mKandangList.get(position).getId());
        holder.mTextViewKodeKandang.setText(mKandangList.get(position).getKodekandang() + " . " + mKandangList.get(position).getKodeblok());
        holder.mTextViewJenisKandang.setText(mKandangList.get(position).getJenis_kandang());
        holder.mTextViewAlamatKandang.setText(mKandangList.get(position).getAlamatkandang());


    }

    @Override
    public int getItemCount() {
        return mKandangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewId;
        public TextView mTextViewKodeKandang;
        public TextView mTextViewIdUser;
        public TextView mTextViewNamaUser;

        public TextView mTextViewJenisKandang;
        public TextView mTextViewAlamatKandang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            mTextViewId = itemView.findViewById(R.id.tvIdKandang);
//            mTextViewKodeKandang = itemView.findViewById(R.id.tvKodeKandang);
//            mTextViewJenisKandang = itemView.findViewById(R.id.tvJenisKandang);
//            mTextViewAlamatKandang = itemView.findViewById(R.id.tvAlamatKandang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    onAdapterListener.pengecekanPerformed();
                    recyclerViewInterface.OnItemClick(getAdapterPosition());

                }
            });
        }
    }
}
