package org.d3ifcool.testing.DatabaseSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "Agrobizid";
    public static final String TABLE_NAME = "tbCekharian";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TANGGAL_WAKTU_CEK = "tanggal_waktu_cek";
    public static final String COLUMN_NAMA_PETUGAS = "nama_petugas";
    public static final String COLUMN_ALAMAT_KANDANG = "alamat_kandang";
    public static final String COLUMN_KODE_KANDANG = "kode_kandang";
    public static final String COLUMN_KODE_BLOK= "kode_blok";
    public static final String COLUMN_JUMLAH_AYAM = "jumlah_ayam";
    public static final String COLUMN_UMUR_AYAM = "umur_ayam";
    public static final String COLUMN_TANGGAL_AYAM_MASUK = "tanggal_ayam_masuk";
    public static final String COLUMN_KONDISI_AYAM = "kondisi_ayam";
    public static final String COLUMN_JUMLAH_AYAM_SAKIT = "jumlah_ayam_sakit";
    public static final String COLUMN_GEJALA_SAKIT = "gejala_sakit";
    public static final String COLUMN_JAM_PAKAN = "jam_pakan";
    public static final String COLUMN_JAM_GANTI_MINUM = "jam_ganti_minum";
    public static final String COLUMN_JAM_GANTI_VITAMIN = "jam_ganti_vitamin";
    public static final String COLUMN_JUMLAH_PAKAN = "jumlah_pakan";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ID_USER = "id_user";


    private static final int DB_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_TANGGAL_WAKTU_CEK + " TEXT, " +
                COLUMN_NAMA_PETUGAS + " TEXT, " +
                COLUMN_ALAMAT_KANDANG + " TEXT , " +
                COLUMN_KODE_KANDANG + " TEXT , " +
                COLUMN_KODE_BLOK + " TEXT , " +
                COLUMN_JUMLAH_AYAM + " TEXT , " +
                COLUMN_UMUR_AYAM + " TEXT , " +
                COLUMN_TANGGAL_AYAM_MASUK + " DATE , " +
                COLUMN_KONDISI_AYAM + " TEXT , " +
                COLUMN_JUMLAH_AYAM_SAKIT + " TEXT , " +
                COLUMN_GEJALA_SAKIT + " TEXT , " +
                COLUMN_JAM_PAKAN + " TIME , " +
                COLUMN_JAM_GANTI_MINUM + " TEXT , " +
                COLUMN_JAM_GANTI_VITAMIN + " TEXT , " +
                COLUMN_JUMLAH_PAKAN + " TEXT , " +
                COLUMN_LATITUDE + " TEXT , " +
                COLUMN_LONGITUDE + " TEXT , " +
                COLUMN_ID_USER + " TEXT " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean TambahData(String tanggal_waktu_cek,
                              String nama_petugas,
                              String alamat_kandang,
                              String kode_kandang,
                              String kode_blok,
                              String jumlah_ayam,
                              String umur_ayam,
                              String tanggal_ayam_masuk,
                              String kondisi_ayam,
                              String jumlah_ayam_sakit,
                              String gejala_sakit,
                              String jam_pakan,
                              String jam_ganti_minum,
                              String jam_ganti_vitamin,
                              String jumlah_pakan,
                              String latitude,
                              String longitude,
                              String id_user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TANGGAL_WAKTU_CEK, tanggal_waktu_cek);
        contentValues.put(COLUMN_NAMA_PETUGAS, nama_petugas);
        contentValues.put(COLUMN_ALAMAT_KANDANG, alamat_kandang);
        contentValues.put(COLUMN_KODE_KANDANG, kode_kandang);
        contentValues.put(COLUMN_KODE_BLOK, kode_blok);
        contentValues.put(COLUMN_JUMLAH_AYAM, jumlah_ayam);
        contentValues.put(COLUMN_UMUR_AYAM, umur_ayam);
        contentValues.put(COLUMN_TANGGAL_AYAM_MASUK, tanggal_ayam_masuk);
        contentValues.put(COLUMN_KONDISI_AYAM, kondisi_ayam);
        contentValues.put(COLUMN_JUMLAH_AYAM_SAKIT, jumlah_ayam_sakit);
        contentValues.put(COLUMN_GEJALA_SAKIT, gejala_sakit);
        contentValues.put(COLUMN_JAM_PAKAN, jam_pakan);
        contentValues.put(COLUMN_JAM_GANTI_MINUM, jam_ganti_minum);
        contentValues.put(COLUMN_JAM_GANTI_VITAMIN, jam_ganti_vitamin);
        contentValues.put(COLUMN_JUMLAH_PAKAN, jumlah_pakan);
        contentValues.put(COLUMN_LATITUDE, latitude);
        contentValues.put(COLUMN_LONGITUDE, longitude);
        contentValues.put(COLUMN_ID_USER, id_user);

        long results = db.insert(TABLE_NAME, null, contentValues);

        return results != 0;

    }
}
