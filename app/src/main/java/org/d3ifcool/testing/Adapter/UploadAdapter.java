package org.d3ifcool.testing.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.PengecekanFragment;

import java.util.ArrayList;
import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder>implements Filterable {

    private ArrayList<Data> userList;
    private ArrayList<Data> filteredUserList;
    private Context context;
    ColorGenerator colorGenerator;


    public interface CustomItemClickListener {
         void onItemClick(Data user, int position);
    }

    CustomItemClickListener customItemClickListener;


    public UploadAdapter(Context context,ArrayList<Data> userArrayList,CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public UploadAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sampel, viewGroup, false);
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
    public void onBindViewHolder(UploadAdapter.ViewHolder viewHolder, int position) {

            String id = filteredUserList.get(position).getId_kandang();
            colorGenerator = ColorGenerator.MATERIAL;
            String letter = String.valueOf(String.valueOf(id.substring(id.length()-2)));
            TextDrawable drawable = TextDrawable.builder().buildRoundRect(letter,colorGenerator.getRandomColor(),10);


            viewHolder.mTextViewAlamatKandang.setText(filteredUserList.get(position).getAlamat_kandang());
            viewHolder.mTextViewId.setImageDrawable(drawable);
            viewHolder.mTextViewKodeKandang.setText(filteredUserList.get(position).getKodekandang() + " \u2022 " + filteredUserList.get(position).getKodeblok());
            viewHolder.mTextViewStatusKandang.setText(filteredUserList.get(position).getStatus_kandang());
            if (filteredUserList.get(position).getStatus_kandang().equals("Tidak Aktif")) {
                viewHolder.mTextViewStatusKandang.setBackgroundResource(R.drawable.bg_decline);
            } else {
                viewHolder.mTextViewStatusKandang.setBackgroundResource(R.drawable.bg_accept);
            }
    }

    @Override
    public int getItemCount() {
        if (filteredUserList == null){
            return 0;
        }else {
            return filteredUserList.size();
        }

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

                    ArrayList<Data> tempFilteredList = new ArrayList<>();

                    for (Data user : userList) {

                        // search for user title
                        if (user.getAlamat_kandang().toLowerCase().contains(searchString)
                                || user.getKodekandang().toLowerCase().contains(searchString)
                                || user.getKodeblok().toLowerCase().contains(searchString)) {

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
                filteredUserList = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mTextViewId;
        public TextView mTextViewKodeKandang;
        public TextView mTextViewIdUser;
        public TextView mTextViewNamaUser;

        public TextView mTextViewJenisKandang;
        public TextView mTextViewAlamatKandang;
        public TextView mTextViewStatusKandang;

        public ViewHolder(View view) {
            super(view);
            mTextViewAlamatKandang = (TextView) view.findViewById(R.id.tvAlamatKandang);
            mTextViewKodeKandang = (TextView) view.findViewById(R.id.tvKodeKandang);
            mTextViewId = (ImageView) view.findViewById(R.id.tvIdKandang);
            mTextViewStatusKandang = (TextView) view.findViewById(R.id.tvStatusKandang);
        }
    }
}
