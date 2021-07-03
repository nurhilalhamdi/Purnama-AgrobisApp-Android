package org.d3ifcool.testing.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataRequest;

import java.util.ArrayList;
import java.util.Random;

public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.ViewHolder>implements Filterable {

    private ArrayList<DataRequest> requestList;
    private ArrayList<DataRequest> filteredRequestList;
    private Context context;
    ColorGenerator colorGenerator;


    public interface CustomItemClickListener {
        void onItemClick(DataRequest user, int position);
    }

    AdapterRequest.CustomItemClickListener customItemClickListener;


    public AdapterRequest(Context context, ArrayList<DataRequest> requestArrayList, AdapterRequest.CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.requestList = requestArrayList;
        this.filteredRequestList = requestArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public AdapterRequest.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_request, viewGroup, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredRequestList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AdapterRequest.ViewHolder viewHolder, int position) {





        viewHolder.mTextViewJenis.setText(filteredRequestList.get(position).getKategori());

        viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());
        viewHolder.mTextViewJumlah.setText(filteredRequestList.get(position).getJumlah_request() + " " +filteredRequestList.get(position).getSatuan());
        viewHolder.mTextViewStatus.setText(filteredRequestList.get(position).getStatus());

        switch (filteredRequestList.get(position).getStatus()) {
            case "Request Sedang Diproses":
            case "Request Diterima":
                viewHolder.mTextViewStatus.setBackgroundResource(R.drawable.bg_accept);
                break;
            case "Request Ditangguhkan":
                viewHolder.mTextViewStatus.setBackgroundResource(R.drawable.bg_warning);
                break;
            case "Request Ditolak":
                viewHolder.mTextViewStatus.setBackgroundResource(R.drawable.bg_decline);
                break;
        }
//        if (filteredUserList.get(position).getStatus_kandang().equals("Tidak Aktif")) {
//            viewHolder.mTextViewStatusKandang.setBackgroundResource(R.drawable.bg_decline);
//        } else {
//            viewHolder.mTextViewStatusKandang.setBackgroundResource(R.drawable.bg_accept);
//        }
    }

    @Override
    public int getItemCount() {
        return filteredRequestList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredRequestList = requestList;

                } else {

                    ArrayList<DataRequest> tempFilteredList = new ArrayList<>();

                    for (DataRequest request : requestList) {

                        // search for user title
                        if (request.getKategori().toLowerCase().contains(searchString)
                                || request.getNama().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(request);
                        }
                    }

                    filteredRequestList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRequestList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredRequestList = (ArrayList<DataRequest>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView mTextViewJenis;
        public TextView mTextViewJumlah;
        public TextView mTextViewNama;
        public TextView mTextViewStatus;
        public View views;

//        public TextView mTextViewJenisKandang;
//        public TextView mTextViewAlamatKandang;
//        public TextView mTextViewStatusKandang;

        public ViewHolder(View view) {
            super(view);
            mTextViewJenis = (TextView) view.findViewById(R.id.txt_jenis_produk_request);
            mTextViewJumlah = (TextView) view.findViewById(R.id.txt_jumlah_request);
            mTextViewNama = (TextView) view.findViewById(R.id.txt_nama_produk_request);
            mTextViewStatus = (TextView) view.findViewById(R.id.txt_status_request);
        }
    }
}
