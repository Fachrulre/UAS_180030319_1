package com.bi183.effendi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editNama, editDeskripsi, editAturan, editJenis;
    private ImageView ivObat;
    private DatabaseHandler dbHandler;
    private boolean updateData = false;
    private int idObat = 0;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editNama = findViewById(R.id.edit_nama);
        editDeskripsi = findViewById(R.id.edit_deskripsi_obat);
        editAturan = findViewById(R.id.edit_aturan);
        editJenis = findViewById(R.id.edit_jenis_obat);
        ivObat = findViewById(R.id.iv_obat);
        btnSimpan = findViewById(R.id.btn_simpan);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")) {
            updateData = false;
        } else {
            updateData = true;
            idObat = data.getInt("ID");
            editNama.setText(data.getString("NAMA_OBAT"));
            editDeskripsi.setText(data.getString("DESKRIPSI_OBAT"));
            editAturan.setText(data.getString("ATURAN_PAKAI"));
            editJenis.setText(data.getString("JENIS_OBAT"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));

        }

        ivObat.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,3)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er) {
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kesalahan dalam pemilihan gambar", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "obat-"+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivObat.setImageBitmap(bitmap);
            ivObat.setContentDescription(imageLocation);
        } catch (FileNotFoundException er) {
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if (updateData==true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_menu_hapus) {
            alertHapusDataObat();
        }

        return super.onOptionsItemSelected(item);
    }

    private void simpanData() {
        String nama, deskripsi, aturan, jenis, gambar;
        nama = editNama.getText().toString();
        deskripsi = editDeskripsi.getText().toString();
        aturan = editAturan.getText().toString();
        jenis = editJenis.getText().toString();
        gambar = ivObat.getContentDescription().toString();

        Obat tempObat = new Obat(
                idObat, nama, deskripsi, aturan, jenis, gambar
        );

        if (updateData == true) {
            dbHandler.editObat(tempObat);
            Toast.makeText(this, "Data Obat Diperbarui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahObat(tempObat);
            Toast.makeText(this, "Data Obat ditambahkan", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void alertHapusDataObat(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin Hapus Data Obat ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Pilih Ya untuk menghapus obat")
                .setIcon(R.drawable.ic_warning_red_24dp)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        hapusData();
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void hapusData() {
        dbHandler.hapusObat(idObat);
        Toast.makeText(this, "Data Obat dihapus", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan) {
            simpanData();
        } else if (idView == R.id.iv_obat) {
            pickImage();
        }
    }

}
