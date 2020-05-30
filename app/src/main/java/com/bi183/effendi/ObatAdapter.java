package com.bi183.effendi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.ObatViewHolder> {

    private Context context;
    private ArrayList<Obat> dataObat;

    public ObatAdapter(Context context, ArrayList<Obat> dataObat) {
        this.context = context;
        this.dataObat = dataObat;
    }

    @NonNull
    @Override
    public ObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_obat, parent, false);
        return new ObatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObatViewHolder holder, int position) {
        Obat tempObat = dataObat.get(position);
        holder.idObat = tempObat.getIdObat();
        holder.tvNama.setText(tempObat.getNamaObat());
        holder.tvDeskripsi.setText(tempObat.getDeskripsiObat());
        holder.aturan = tempObat.getAturanPakai();
        holder.jenis = tempObat.getJenisObat();
        holder.gambar = tempObat.getGambar();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgObat.setImageBitmap(bitmap);
            holder.imgObat.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er) {
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataObat.size();
    }

    public class ObatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgObat;
        private TextView tvNama, tvDeskripsi;
        private int idObat;
        private String aturan, jenis, gambar;

        public ObatViewHolder(@NonNull View itemView) {
            super(itemView);

            imgObat = itemView.findViewById(R.id.iv_obat);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bukaObat = new Intent(context, TampilActivity.class);
            bukaObat.putExtra("ID", idObat);
            bukaObat.putExtra("NAMA_OBAT", tvNama.getText());
            bukaObat.putExtra("DESKRIPSI_OBAT", tvDeskripsi.getText());
            bukaObat.putExtra("ATURAN_PAKAI", aturan);
            bukaObat.putExtra("JENIS_OBAT", jenis);
            bukaObat.putExtra("GAMBAR", gambar);
            context.startActivity(bukaObat);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idObat);
            bukaInput.putExtra("NAMA_OBAT", tvNama.getText());
            bukaInput.putExtra("DESKRIPSI_OBAT", tvDeskripsi.getText());
            bukaInput.putExtra("ATURAN_PAKAI", aturan);
            bukaInput.putExtra("JENIS_OBAT", jenis);
            bukaInput.putExtra("GAMBAR", gambar);
            context.startActivity(bukaInput);
            return true;
        }
    }
}
