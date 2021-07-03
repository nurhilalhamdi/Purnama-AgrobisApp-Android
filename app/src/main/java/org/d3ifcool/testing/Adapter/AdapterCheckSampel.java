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
import org.d3ifcool.testing.Datas.DataCheckSampel;

import java.util.ArrayList;

public class AdapterCheckSampel extends RecyclerView.Adapter<AdapterCheckSampel.ViewHolder>implements Filterable {

    private ArrayList<DataCheckSampel> requestList;
    private ArrayList<DataCheckSampel> filteredRequestList;
    private Context context;
    ColorGenerator colorGenerator;


    public interface CustomItemClickListener {
        void onItemClick(DataCheckSampel user, int position);
    }

    AdapterCheckSampel.CustomItemClickListener customItemClickListener;


    public AdapterCheckSampel(Context context, ArrayList<DataCheckSampel> requestArrayList, AdapterCheckSampel.CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.requestList = requestArrayList;
        this.filteredRequestList = requestArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public AdapterCheckSampel.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_sampel, viewGroup, false);
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
    public void onBindViewHolder(AdapterCheckSampel.ViewHolder viewHolder, int position) {





        viewHolder.mTextViewUsia.setText(filteredRequestList.get(position).getUmur_ayam());

        viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getTanggal_waktu_sampel());
        viewHolder.mTextViewBobot.setText(filteredRequestList.get(position).getBobot_ayam_sampel());
        viewHolder.mTextViewJumlah.setText(filteredRequestList.get(position).getTotal_ayam_saat_ini());
        viewHolder.getmTextViewBobotRataRata.setText(filteredRequestList.get(position).getBobot_rata_rata());

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

                    ArrayList<DataCheckSampel> tempFilteredList = new ArrayList<>();

                    for (DataCheckSampel request : requestList) {

                        // search for user title
                        if (request.getPeriode().toLowerCase().contains(searchString)) {

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
                filteredRequestList = (ArrayList<DataCheckSampel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView mTextViewUsia;
        public TextView mTextViewTanggal;
        public TextView mTextViewBobot;
        public TextView mTextViewJumlah;
        public TextView getmTextViewBobotRataRata;

        public ViewHolder(View view) {
            super(view);
            mTextViewUsia= (TextView) view.findViewById(R.id.tv_usia_tabel_sampel);
            mTextViewTanggal= (TextView) view.findViewById(R.id.tv_tanggal_tabel_sampel);
            mTextViewBobot = (TextView) view.findViewById(R.id.tv_bobot_tabel_sampel);
            mTextViewJumlah = (TextView) view.findViewById(R.id.tv_jumlah_tabel_sampel);
            getmTextViewBobotRataRata = (TextView) view.findViewById(R.id.tv_bobot_rata_tabel_sampel);
        }
    }
}
