package org.d3ifcool.testing.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.MedisData;

import java.util.ArrayList;

public class MedisAdapter extends RecyclerView.Adapter<MedisAdapter.ViewHolder>implements Filterable {

    private ArrayList<MedisData> userList;
    private ArrayList<MedisData> filteredUserList;
    private Context context;



    public interface CustomItemClickListener {
        void onItemClick(MedisData user, int position);
    }

    MedisAdapter.CustomItemClickListener customItemClickListener;


    public MedisAdapter(Context context, ArrayList<MedisData> userArrayList, MedisAdapter.CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public MedisAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_medis, viewGroup, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MedisAdapter.ViewHolder viewHolder, int position) {



            viewHolder.mTextViewNamaMedis.setBackground(null);
            viewHolder.mTextViewNamaMedis.setText(filteredUserList.get(position).getNama_medis());

            viewHolder.mImageViewMedisIcon.setBackground(null);
            Glide.with(context).load(filteredUserList.get(position).getFile_icon_medis())
                    .apply(RequestOptions.centerCropTransform())
                    .into(viewHolder.mImageViewMedisIcon);

    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<MedisData> tempFilteredList = new ArrayList<>();

                    for (MedisData user : userList) {

                        // search for user title
                        if (user.getNama_medis().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<MedisData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextViewNamaMedis;
        public ImageView mImageViewMedisIcon;

        public ViewHolder(View view) {
            super(view);
                mTextViewNamaMedis = (TextView) view.findViewById(R.id.nama_medis);
            mImageViewMedisIcon = (ImageView) view.findViewById(R.id.icon_medis);


        }
    }
}
