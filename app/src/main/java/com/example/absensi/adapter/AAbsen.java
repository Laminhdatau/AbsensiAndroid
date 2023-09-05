package com.example.absensi.adapter;


import static com.example.absensi.api.ApiServer.base_url;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.absensi.R;
import com.example.absensi.model.MAbsen;
import com.google.android.gms.fido.fido2.api.common.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AAbsen extends RecyclerView.Adapter<AAbsen.ViewHolder> {
    TextView tvJenisAbsen;
    TextView tvNik;
    TextView tvNama;
    TextView tvLokasi;
    TextView tvAbsenTime;
    private List<MAbsen> absenList;

    public AAbsen(List<MAbsen> absenList) {
        this.absenList = absenList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvHistory;
        TextView tvStatusAbsen;
        TextView tvNik;
        TextView tvNama;
        TextView tvLokasi;
        TextView tvAbsenTime;
        ImageView ivProfile;
        public ViewHolder(View itemView) {
            super(itemView);
            cvHistory = itemView.findViewById(R.id.cvHistory);
            tvStatusAbsen = itemView.findViewById(R.id.tvStatusAbsen);
            ivProfile = itemView.findViewById(R.id.imageProfile);
            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvAbsenTime = itemView.findViewById(R.id.tvAbsenTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout CardView yang sesuai di sini
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_absen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Mengisi elemen-elemen dalam CardView dengan data dari absenList
        MAbsen absen = absenList.get(position);
        if (absen != null) {
            // Set data ke elemen-elemen dalam CardView
            TextView tvStatusAbsen = holder.cvHistory.findViewById(R.id.tvStatusAbsen);
            tvStatusAbsen.setText(absen.getJenis_absen());

            TextView tvNik = holder.cvHistory.findViewById(R.id.tvNik);
            tvNik.setText(absen.getNik());

            TextView tvNama = holder.cvHistory.findViewById(R.id.tvNama);
            tvNama.setText(absen.getNama_lengkap());

            TextView tvLokasi = holder.cvHistory.findViewById(R.id.tvLokasi);
            tvLokasi.setText(absen.getNama_lokasi());

            TextView tvAbsenTime = holder.cvHistory.findViewById(R.id.tvAbsenTime);
           tvAbsenTime.setText(absen.getFormattedTime_absen());

            ImageView ivProfile = holder.cvHistory.findViewById(R.id.imageProfile);
            String imageUrl = absen.getUser_image();

            Glide.with(ivProfile.getContext())
                    .load(base_url+"get_image/"+absen.getUser_image())
                    .into(ivProfile);


        }
    }

    @Override
    public int getItemCount() {
        return absenList.size();
    }
}

