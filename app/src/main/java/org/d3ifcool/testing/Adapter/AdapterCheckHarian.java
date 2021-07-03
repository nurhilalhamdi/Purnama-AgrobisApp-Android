package org.d3ifcool.testing.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.DataCheckHarian;
import org.d3ifcool.testing.Datas.DataRequest;

import java.util.ArrayList;

public class AdapterCheckHarian extends RecyclerView.Adapter<AdapterCheckHarian.ViewHolder>implements Filterable {

    private ArrayList<DataCheckHarian> requestList;
    private ArrayList<DataCheckHarian> filteredRequestList;
    private Context context;
    ColorGenerator colorGenerator;


    public interface CustomItemClickListener {
        void onItemClick(DataCheckHarian user, int position);
    }

    AdapterCheckHarian.CustomItemClickListener customItemClickListener;


    public AdapterCheckHarian(Context context, ArrayList<DataCheckHarian> requestArrayList, AdapterCheckHarian.CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.requestList = requestArrayList;
        this.filteredRequestList = requestArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public AdapterCheckHarian.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_harian, viewGroup, false);
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
    public void onBindViewHolder(AdapterCheckHarian.ViewHolder viewHolder, int position) {





        viewHolder.mTextViewUsia.setText(filteredRequestList.get(position).getUsia_ayam());

        viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getTanggal_pengecekan());
        viewHolder.mTextViewMati.setText(filteredRequestList.get(position).getAyam_mati());
        viewHolder.mTextViewSakit.setText(filteredRequestList.get(position).getAyam_sakit());
        viewHolder.mTextViewPakan.setText(filteredRequestList.get(position).getJumlah_pakan());

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

                    ArrayList<DataCheckHarian> tempFilteredList = new ArrayList<>();

                    for (DataCheckHarian request : requestList) {

                        // search for user title
                        if (request.getTanggal_pengecekan().toLowerCase().contains(searchString)) {

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
                filteredRequestList = (ArrayList<DataCheckHarian>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView mTextViewUsia;
        public TextView mTextViewTanggal;
        public TextView mTextViewMati;
        public TextView mTextViewSakit;
        public TextView mTextViewPakan;

        public ViewHolder(View view) {
            super(view);
            mTextViewUsia= (TextView) view.findViewById(R.id.tv_usia_tabel);
            mTextViewTanggal= (TextView) view.findViewById(R.id.tv_tanggal_tabel);
            mTextViewMati = (TextView) view.findViewById(R.id.tv_mati_tabel);
            mTextViewSakit = (TextView) view.findViewById(R.id.tv_sakit_tabel);
            mTextViewPakan = (TextView) view.findViewById(R.id.tv_pakan_tabel);
        }
    }
}
