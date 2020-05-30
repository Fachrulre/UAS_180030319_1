package com.bi183.effendi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "db_obat";
    private final static String TABLE_OBAT = "t_obat";
    private final static String KEY_ID_OBAT = "ID_obat";
    private final static String KEY_NAMA_OBAT = "Nama_Obat";
    private final static String KEY_DESKRIPSI_OBAT = "Deskripsi_Obat";
    private final static String KEY_ATURAN_PAKAI = "Aturan_Pakai";
    private final static String KEY_JENIS_OBAT = "Jenis_Obat";
    private final static String KEY_GAMBAR = "Gambar";
    private Context context;

    public DatabaseHandler(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = "CREATE TABLE " + TABLE_OBAT
                + "(" + KEY_ID_OBAT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAMA_OBAT + " TEXT, " + KEY_DESKRIPSI_OBAT + " TEXT, "
                + KEY_ATURAN_PAKAI + " TEXT, " + KEY_JENIS_OBAT + " TEXT, "
                + KEY_GAMBAR + " TEXT);";

        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiObatAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_OBAT;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahObat(Obat dataObat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_DESKRIPSI_OBAT, dataObat.getDeskripsiObat());
        cv.put(KEY_ATURAN_PAKAI, dataObat.getAturanPakai());
        cv.put(KEY_JENIS_OBAT, dataObat.getJenisObat());
        cv.put(KEY_GAMBAR, dataObat.getGambar());

        db.insert(TABLE_OBAT, null, cv);
        db.close();
    }

    public void tambahObat(Obat dataObat, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_DESKRIPSI_OBAT, dataObat.getDeskripsiObat());
        cv.put(KEY_ATURAN_PAKAI, dataObat.getAturanPakai());
        cv.put(KEY_JENIS_OBAT, dataObat.getJenisObat());
        cv.put(KEY_GAMBAR, dataObat.getGambar());

        db.insert(TABLE_OBAT, null, cv);
    }

    public void editObat(Obat dataObat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_DESKRIPSI_OBAT, dataObat.getDeskripsiObat());
        cv.put(KEY_ATURAN_PAKAI, dataObat.getAturanPakai());
        cv.put(KEY_JENIS_OBAT, dataObat.getJenisObat());
        cv.put(KEY_GAMBAR, dataObat.getGambar());

        db.update(TABLE_OBAT, cv, KEY_ID_OBAT + "=?", new String[]{String.valueOf(dataObat.getIdObat())});
        db.close();
    }

    public void hapusObat(int idObat) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_OBAT, KEY_ID_OBAT + "=?", new String[]{String.valueOf(idObat)});
        db.close();
    }

    public ArrayList<Obat> getAllObat() {
        ArrayList<Obat> dataObat = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_OBAT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Obat tempObat = new Obat(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5)
                );

                dataObat.add(tempObat);
            } while (csr.moveToNext());
        }
        return dataObat;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image,context);
        return location;
    }

    private void inisialisasiObatAwal(SQLiteDatabase db) {
        int idObat = 0;

        // Menambah databerita ke-1
        Obat obat1 = new Obat(
                idObat,
                "Neozep Forte",
                "Neozep Forte adalah sediaan obat yang mengandung Paracetamol, Phenylpropanolamine HCl, salicylamide, dan chlorpheniramine maleate. Neozep Forte digunakan sebagai Analgesik atau obat yang meringankan rasa nyeri, antipiretik atau dapat menurunkan demam, meringkan hidung tersumbat dan antihistamin.",
                "3-4 tablet sekali sehari.",
                "Obat Bebas",
                storeImageFile(R.drawable.obat_b)
        );

        tambahObat(obat1,db);
        idObat++;

        //Data berita ke-2
        Obat obat2 = new Obat(
                idObat,
                "Panadol Cold dan Flu",
                "Panadol Cold dan Flu adalah obat yang mengandung zat aktif kombinasi Paracetamol, pseudoephedrine HCl, dan dextromethorphan HBr. Paracetamol adalah obat yang mempunyai khasiat sebagai analgetic (pereda nyeri) dan antipiretik (penurun panas). Sebenarnya, paracetamol juga memiliki efek anti inflamasi, namun tidak signifikan. Oleh karena itu tidak dimasukkan sebagai obat NSAID.",
                "Dewasa : 1 kaplet 3 x sehari.",
                "Obat Bebas Terbatas",
                storeImageFile(R.drawable.obat_b)
        );

        tambahObat(obat2, db);
        idObat++;

        Obat obat3 = new Obat(
                idObat,
                "Asam Mefenamat",
                "Asam mefenamat, atau mefenamic acid, adalah obat untuk mengobati rasa sakit ringan hingga sedang. Sering digunakan sebagai obat sakit gigi, sakit kepala, dan meringankan rasa nyeri pada saat menstruasi.",
                "4 kali sehari dengan segelas air mineral (8 ons atau 240 mililiter) atau sesuai arahan dokter",
                "Obat Keras",
                storeImageFile(R.drawable.obat_k)
        );

        tambahObat(obat3, db);
        idObat++;
    }

}