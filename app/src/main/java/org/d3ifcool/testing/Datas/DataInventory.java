package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataInventory {

    public class DataPakan {
        @SerializedName("id_pakan")
        String id_pakan;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_pakan")
        int stok_pakan;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;


        public DataPakan(String id_pakan, String id_barang, int stok_pakan, String id_kandang, String created_at, String kode, String nama, String stok, String deskripsi, String id_kategori, String id_satuan, String satuan, String harga, String modal) {
            this.id_pakan = id_pakan;
            this.id_barang = id_barang;
            this.stok_pakan = stok_pakan;
            this.id_kandang = id_kandang;
            this.created_at = created_at;
            this.kode = kode;
            this.nama = nama;
            this.stok = stok;
            this.deskripsi = deskripsi;
            this.id_kategori = id_kategori;
            this.id_satuan = id_satuan;
            this.satuan = satuan;
            this.harga = harga;
            this.modal = modal;
        }

        public DataPakan() {
        }

        public String getId_pakan() {
            return id_pakan;
        }

        public void setId_pakan(String id_pakan) {
            this.id_pakan = id_pakan;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_pakan() {
            return stok_pakan;
        }

        public void setStok_pakan(int stok_pakan) {
            this.stok_pakan = stok_pakan;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

    }

    public class DataVitamin {
        @SerializedName("id_vitamin")
        String id_vitamin;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_vitamin")
        int stok_vitamin;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;

        public String getId_vitamin() {
            return id_vitamin;
        }

        public void setId_vitamin(String id_vitamin) {
            this.id_vitamin = id_vitamin;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_vitamin() {
            return stok_vitamin;
        }

        public void setStok_vitamin(int stok_vitamin) {
            this.stok_vitamin = stok_vitamin;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }
    }

    public class DataObat {
        @SerializedName("id_obat")
        String id_obat;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_obat")
        int stok_obat;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;


        public String getId_obat() {
            return id_obat;
        }

        public void setId_obat(String id_obat) {
            this.id_obat = id_obat;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_obat() {
            return stok_obat;
        }

        public void setStok_obat(int stok_obat) {
            this.stok_obat = stok_obat;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }
    }

    public class DataVaksin {
        @SerializedName("id_vaksin")
        String id_vaksin;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_vaksin")
        int stok_vaksin;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;

        public String getId_vaksin() {
            return id_vaksin;
        }

        public void setId_vaksin(String id_vaksin) {
            this.id_vaksin = id_vaksin;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_vaksin() {
            return stok_vaksin;
        }

        public void setStok_vaksin(int stok_vaksin) {
            this.stok_vaksin = stok_vaksin;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }
    }

    public class DataPeralatan {
        @SerializedName("id_peralatan")
        String id_obat;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_peralatan")
        int stok_peralatan;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;

        public String getId_obat() {
            return id_obat;
        }

        public void setId_obat(String id_obat) {
            this.id_obat = id_obat;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_peralatan() {
            return stok_peralatan;
        }

        public void setStok_peralatan(int stok_peralatan) {
            this.stok_peralatan = stok_peralatan;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }
    }


    public class DataItemLain {
        @SerializedName("id_itemlain")
        String id_obat;
        @SerializedName("id_barang")
        String id_barang;
        @SerializedName("stok_itemlain")
        int stok_itemlain;
        @SerializedName("id_kandang")
        String id_kandang;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("kode")
        String kode;
        @SerializedName("nama")
        String nama;
        @SerializedName("stok")
        String stok;
        @SerializedName("deskripsi")
        String deskripsi;
        @SerializedName("id_kategori")
        String id_kategori;
        @SerializedName("id_satuan")
        String id_satuan;
        @SerializedName("satuan")
        String satuan;
        @SerializedName("harga")
        String harga;
        @SerializedName("modal")
        String modal;

        public String getId_obat() {
            return id_obat;
        }

        public void setId_obat(String id_obat) {
            this.id_obat = id_obat;
        }

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public int getStok_itemlain() {
            return stok_itemlain;
        }

        public void setStok_itemlain(int stok_itemlain) {
            this.stok_itemlain = stok_itemlain;
        }

        public String getId_kandang() {
            return id_kandang;
        }

        public void setId_kandang(String id_kandang) {
            this.id_kandang = id_kandang;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getId_satuan() {
            return id_satuan;
        }

        public void setId_satuan(String id_satuan) {
            this.id_satuan = id_satuan;
        }

        public String getSatuan() {
            return satuan;
        }

        public void setSatuan(String satuan) {
            this.satuan = satuan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }
    }
}
