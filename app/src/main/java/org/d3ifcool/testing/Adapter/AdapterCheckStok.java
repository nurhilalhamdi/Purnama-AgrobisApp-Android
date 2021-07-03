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
import org.d3ifcool.testing.Datas.DataInventory;

import java.util.ArrayList;

public class AdapterCheckStok {


    public static class AdapterPakan extends RecyclerView.Adapter<AdapterCheckStok.AdapterPakan.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataPakan> requestList;
        private ArrayList<DataInventory.DataPakan> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataPakan user, int position);
        }

        AdapterCheckStok.AdapterPakan.CustomItemClickListener customItemClickListener;


        public AdapterPakan(Context context, ArrayList<DataInventory.DataPakan> requestArrayList, AdapterCheckStok.AdapterPakan.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterPakan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterPakan.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_pakan()+"");


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

                        ArrayList<DataInventory.DataPakan> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataPakan request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataPakan>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }


    public static class AdapterObat extends RecyclerView.Adapter<AdapterCheckStok.AdapterObat.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataObat> requestList;
        private ArrayList<DataInventory.DataObat> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataObat user, int position);
        }

        AdapterCheckStok.AdapterObat.CustomItemClickListener customItemClickListener;


        public AdapterObat(Context context, ArrayList<DataInventory.DataObat> requestArrayList, AdapterCheckStok.AdapterObat.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterObat.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterObat.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_obat()+"");


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

                        ArrayList<DataInventory.DataObat> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataObat request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataObat>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }


    public static class AdapterVitamin extends RecyclerView.Adapter<AdapterCheckStok.AdapterVitamin.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataVitamin> requestList;
        private ArrayList<DataInventory.DataVitamin> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataVitamin user, int position);
        }

        AdapterCheckStok.AdapterVitamin.CustomItemClickListener customItemClickListener;


        public AdapterVitamin(Context context, ArrayList<DataInventory.DataVitamin> requestArrayList, AdapterCheckStok.AdapterVitamin.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterVitamin.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterVitamin.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_vitamin()+"");


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

                        ArrayList<DataInventory.DataVitamin> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataVitamin request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataVitamin>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }

    public static class AdapterVaksin extends RecyclerView.Adapter<AdapterCheckStok.AdapterVaksin.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataVaksin> requestList;
        private ArrayList<DataInventory.DataVaksin> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataVaksin user, int position);
        }

        AdapterCheckStok.AdapterVaksin.CustomItemClickListener customItemClickListener;


        public AdapterVaksin(Context context, ArrayList<DataInventory.DataVaksin> requestArrayList, AdapterCheckStok.AdapterVaksin.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterVaksin.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterVaksin.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_vaksin()+"");


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

                        ArrayList<DataInventory.DataVaksin> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataVaksin request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataVaksin>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }


    public static class AdapterAlat extends RecyclerView.Adapter<AdapterCheckStok.AdapterAlat.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataPeralatan> requestList;
        private ArrayList<DataInventory.DataPeralatan> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataPeralatan user, int position);
        }

        AdapterCheckStok.AdapterAlat.CustomItemClickListener customItemClickListener;


        public AdapterAlat(Context context, ArrayList<DataInventory.DataPeralatan> requestArrayList, AdapterCheckStok.AdapterAlat.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterAlat.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterAlat.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_peralatan()+"");


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

                        ArrayList<DataInventory.DataPeralatan> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataPeralatan request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataPeralatan>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }

    public static class AdapterItemLain extends RecyclerView.Adapter<AdapterCheckStok.AdapterItemLain.ViewHolder>implements Filterable {

        private ArrayList<DataInventory.DataItemLain> requestList;
        private ArrayList<DataInventory.DataItemLain> filteredRequestList;
        private Context context;
        ColorGenerator colorGenerator;


        public interface CustomItemClickListener {
            void onItemClick(DataInventory.DataItemLain user, int position);
        }

        AdapterCheckStok.AdapterItemLain.CustomItemClickListener customItemClickListener;


        public AdapterItemLain(Context context, ArrayList<DataInventory.DataItemLain> requestArrayList, AdapterCheckStok.AdapterItemLain.CustomItemClickListener customItemClickListener) {
            this.context = context;
            this.requestList = requestArrayList;
            this.filteredRequestList = requestArrayList;
            this.customItemClickListener = customItemClickListener;
        }

        @Override
        public AdapterCheckStok.AdapterItemLain.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_check_stok, viewGroup, false);
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
        public void onBindViewHolder(AdapterCheckStok.AdapterItemLain.ViewHolder viewHolder, int position) {





            viewHolder.mTextViewNama.setText(filteredRequestList.get(position).getNama());

            viewHolder.mTextViewTanggal.setText(filteredRequestList.get(position).getCreated_at());
            viewHolder.mTextViewStok.setText(filteredRequestList.get(position).getStok_itemlain()+"");


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

                        ArrayList<DataInventory.DataItemLain> tempFilteredList = new ArrayList<>();

                        for (DataInventory.DataItemLain request : requestList) {

                            // search for user title
                            if (request.getNama().toLowerCase().contains(searchString)) {

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
                    filteredRequestList = (ArrayList<DataInventory.DataItemLain>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public TextView mTextViewNama;
            public TextView mTextViewTanggal;
            public TextView mTextViewStok;

            public ViewHolder(View view) {
                super(view);
                mTextViewNama= (TextView) view.findViewById(R.id.txt_nama_barang);
                mTextViewTanggal= (TextView) view.findViewById(R.id.txt_tanggal_masuk_barang);
                mTextViewStok = (TextView) view.findViewById(R.id.txt_jumlah_stok_barang);
            }
        }
    }

}