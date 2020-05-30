package com.bi183.effendi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgObat;
    private TextView tvNama, tvDeskripsi, tvAturan, tvJenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgObat = findViewById(R.id.iv_obat);
        tvNama = findViewById(R.id.tv_nama);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvAturan = findViewById(R.id.tv_aturan);
        tvJenis = findViewById(R.id.tv_jenis);

        Intent terimaData = getIntent();
        tvNama.setText(terimaData.getStringExtra("NAMA_OBAT"));
        tvDeskripsi.setText(terimaData.getStringExtra("DESKRIPSI_OBAT"));
        tvAturan.setText(terimaData.getStringExtra("ATURAN_PAKAI"));
        tvJenis.setText(terimaData.getStringExtra("JENIS_OBAT"));
        String imglocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imglocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgObat.setImageBitmap(bitmap);
            imgObat.setContentDescription(imglocation);
        } catch (FileNotFoundException er) {
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }
}
