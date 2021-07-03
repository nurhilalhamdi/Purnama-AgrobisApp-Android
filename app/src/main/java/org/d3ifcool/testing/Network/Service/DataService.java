package org.d3ifcool.testing.Network.Service;

import org.d3ifcool.testing.Datas.Data;
import org.d3ifcool.testing.Datas.DataCheckHarian;
import org.d3ifcool.testing.Datas.DataCheckSampel;
import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataInventory;
import org.d3ifcool.testing.Datas.DataPanen;
import org.d3ifcool.testing.Datas.DataPerforma;
import org.d3ifcool.testing.Datas.DataRequest;
import org.d3ifcool.testing.Datas.DataStok;
import org.d3ifcool.testing.Datas.DataUser;
import org.d3ifcool.testing.Datas.MedisData;
import org.d3ifcool.testing.Network.EndPoint;
import org.d3ifcool.testing.Network.Response.BaseResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {
    @Multipart
    @POST(EndPoint.API_CREATE)
    Call<BaseResponse.BaseResponseApi> upload(
            @Part("nama") RequestBody nama,
            @Part MultipartBody.Part image);

    @Multipart
    @POST(EndPoint.API_CEKHARIAN)
    Call<BaseResponse.BaseResponseApi> inputDataHarian(@Part MultipartBody.Part file,
                                       @Part("tanggal_waktu_cek") RequestBody tanggal_waktu_cek,
                                       @Part("nama_petugas")RequestBody nama_petugas,
                                       @Part("alamat_kandang")RequestBody alamat_kandang,
                                       @Part("kode_kandang")RequestBody kode_kandang,
                                       @Part("kode_blok")RequestBody kode_blok,
                                       @Part("jumlah_ayam")RequestBody jumlah_ayam,
                                       @Part("umur_ayam")RequestBody umur_ayam,
                                       @Part("tanggal_ayam_masuk")RequestBody tanggal_ayam_masuk,
                                       @Part("kondisi_ayam")RequestBody kondisi_ayam,
                                       @Part("jumlah_ayam_sakit") RequestBody jumlah_ayam_sakit,
                                       @Part("gejala_sakit")RequestBody gejala_sakit,
                                       @Part("jam_pakan")RequestBody jam_pakan,
                                       @Part("jam_ganti_minum")RequestBody jam_ganti_minum,
                                       @Part("jam_ganti_vitamin")RequestBody jam_ganti_vitamin,
                                       @Part("jumlah_pakan")RequestBody jumlah_pakan,
                                       @Part("latitude")RequestBody latitude,
                                       @Part("longitude")RequestBody longitude,
                                       @Part("id_user")RequestBody id_user);



    @Multipart
    @POST(EndPoint.API_PENGECEKAN_HARIAN)
    Call<BaseResponse.BaseResponseApi> inputPengecekanHarian(@Part MultipartBody.Part file,
                                                       @Part("usia_ayam")RequestBody usia_ayam,
                                                       @Part("ayam_mati")RequestBody ayam_mati,
                                                       @Part("ayam_sakit")RequestBody ayam_sakit,
                                                       @Part("gejala_sakit")RequestBody gejala_sakit,
                                                       @Part("berat_ayam")RequestBody berat_ayam,
                                                       @Part("id_pakan")RequestBody id_pakan,
                                                       @Part("jumlah_pakan")RequestBody jumlah_pakan,
                                                       @Part("jumlah_pakan_ekor")RequestBody jumlah_pakan_ekor,
                                                       @Part("jam_pemberian_pakan")RequestBody jam_pemberian_pakan,
                                                       @Part("jam_pemberian_minum")RequestBody jam_pemberian_minum,
                                                       @Part("id_obat")RequestBody id_obat,
                                                       @Part("jumlah_obat")RequestBody jumlah_obat,
                                                       @Part("id_vitamin")RequestBody id_vitamin,
                                                       @Part("jumlah_vitamin")RequestBody jumlah_vitamin,
                                                       @Part("jam_pemberian_vitamin")RequestBody jam_pemberian_vitamin,
                                                       @Part("id_vaksin")RequestBody id_vaksin,
                                                       @Part("jumlah_vaksin")RequestBody jumlah_vaksin,
                                                       @Part("latitude")RequestBody latitude,
                                                       @Part("longitude")RequestBody longitude,
                                                       @Part("id_kandang")RequestBody id_kandang,
                                                       @Part("id_user")RequestBody id_user,
                                                       @Part("id_chickin")RequestBody id_chickin,
                                                       @Part("tanggal_pengecekan")RequestBody tanggal_pengecekan,
                                                        @Part("pakan_nama")RequestBody pakan_nama,
                                                             @Part("vitamin_nama")RequestBody vitamin_nama,
                                                             @Part("obat_nama")RequestBody obat_nama,
                                                             @Part("vaksin_nama")RequestBody vaksin_nama);

    @GET(EndPoint.API_GET_HARIAN)
    Call<BaseResponse.BaseResponseApi<List<DataCheckHarian>>> apiGetCheckHarian(@Query("id_kandang") String id_kandang,
                                                                                @Query("periode") String periode);

    @GET(EndPoint.API_GET_SAMPEL)
    Call<BaseResponse.BaseResponseApi<List<DataCheckSampel>>> apiGetCheckSampel(@Query("id_kandang") String id_kandang,
                                                                                @Query("periode") String periode);


    @FormUrlEncoded
    @POST(EndPoint.API_PENGECEKAN_HARIAN_EDIT)
    Call<BaseResponse.BaseResponseApi> updateCheckHarian(@Field("id") String id,
                                                         @Field("usia_ayam") String usia_ayam,
                                                         @Field("ayam_mati") String ayam_mati,
                                                         @Field("ayam_sakit") String ayam_sakit,
                                                         @Field("gejala_sakit") String gejala_sakit,
                                                         @Field("berat_ayam") String berat_ayam,
                                                         @Field("id_pakan") String id_pakan,
                                                         @Field("jumlah_pakan") String jumlah_pakan,
                                                         @Field("jumlah_pakan_ekor") String jumlah_pakan_ekor,
                                                         @Field("jam_pemberian_pakan") String jam_pemberian_pakan,
                                                         @Field("jam_pemberian_minum") String jam_pemberian_minum,
                                                         @Field("id_obat") String id_obat,
                                                         @Field("jumlah_obat") String jumlah_obat,
                                                         @Field("id_vitamin") String id_vitamin,
                                                         @Field("jumlah_vitamin") String jumlah_vitamin,
                                                         @Field("jam_pemberian_vitamin") String jam_pemberian_vitamin,
                                                         @Field("id_vaksin") String id_vaksin,
                                                         @Field("jumlah_vaksin") String jumlah_vaksin,
                                                         @Field("pakan_nama") String pakan_nama,
                                                         @Field("vitamin_nama") String vitamin_nama,
                                                         @Field("obat_nama") String obat_nama,
                                                         @Field("vaksin_nama") String vaksin_nama,
                                                         @Field("id_kandang") String id_kandang);

    @FormUrlEncoded
    @POST(EndPoint.API_PENGECEKAN_SAMPEL_EDIT)
    Call<BaseResponse.BaseResponseApi> updateCheckSampel(@Field("id") String id,
                                                         @Field("umur_ayam") String umur_ayam,
                                                         @Field("kondisi_ayam") String kondisi_ayam,
                                                         @Field("bobot_rata_rata") String bobot_rata_rata,
                                                         @Field("jenis_ayam_sampel") String jenis_ayam_sampel,
                                                         @Field("bobot_ayam_sampel") String bobot_ayam_sampel,
                                                         @Field("jumlah_ayam_sampel") String jumlah_ayam_sampel,
                                                         @Field("id_kandang") String id_kandang);

    //Obat
    @GET(EndPoint.API_OBAT_HARIAN_EDIT)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> apigetObatEdit(@Query("id_obat") String id_obat);

    //vitamin
    @GET(EndPoint.API_VITAMIN_HARIAN_EDIT)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> apigetVitaminEdit(@Query("id_vitamin") String id_vitamin);

    //vaksin
    @GET(EndPoint.API_VAKSIN_HARIAN_EDIT)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> apigetVaksinEdit(@Query("id_vaksin") String id_vaksin);



    @FormUrlEncoded
    @POST(EndPoint.API_PANEN)
    Call<BaseResponse.BaseResponseApi> tambahPanen(@Field("umur_panen") String umur_panen,
                                                     @Field("periode") String periode,
                                                     @Field("tanggal_panen") String tanggal_panen,
                                                     @Field("berat_panen_ekor") String berat_panen_ekor,
                                                     @Field("jumlah_panen") String jumlah_panen,
                                                     @Field("total_berat_keseluruhan") String total_berat_keseluruhan,
                                                     @Field("gagal_panen") String gagal_panen,
                                                     @Field("id_user") String id_user,
                                                     @Field("id_kandang") String id_kandang,
                                                     @Field("id_chickin") String id_chickin);

    @GET(EndPoint.API_PANEN)
    Call<BaseResponse.BaseResponseApi<List<DataPanen>>> apiGetPanen(@Query("id_kandang") String id_kandang,
                                                                    @Query("periode") String periode,
                                                                    @Query("id_chickin") String id_chickin);


    @GET(EndPoint.API_PERFORMA)
    Call<BaseResponse.BaseResponseApi<List<DataPerforma>>> apiGetPerforma(@Query("id_kandang") String id_kandang,
                                                                          @Query("periode") String periode);




























    @Multipart
    @POST(EndPoint.API_CEKSAMPEL)
    Call<BaseResponse.BaseResponseApi> inputDataSampel(
            @Part("tanggal_waktu_sampel") RequestBody tanggal_waktu_sampel,
            @Part("nama")RequestBody nama,
            @Part("kode_kandang")RequestBody kode_kandang,
            @Part("kode_blok")RequestBody kode_blok,
            @Part("jumlah_ayam")RequestBody jumlah_ayam,
            @Part("umur_ayam")RequestBody umur_ayam,
            @Part("kondisi_ayam")RequestBody kondisi_ayam,
            @Part("bobot_rata_rata")RequestBody bobot_rata_rata,
            @Part("jenis_ayam_sampel")RequestBody jenis_ayam_sampel,
            @Part("bobot_ayam_sampel")RequestBody bobot_ayam_sampel,
            @Part("jumlah_ayam_sampel")RequestBody jumlah_ayam_sampel,
            @Part("latitude")RequestBody latitude,
            @Part("longitude")RequestBody longitude,
            @Part MultipartBody.Part file,
            @Part("id_user")RequestBody id_user,
            @Part("id_kandang")RequestBody id_kandang,
            @Part("id_chickin")RequestBody id_chickin);


//
//    @GET(EndPoint.API_KANDANG)
//    Call<BaseResponse.BaseResponseApi<List<Kandang>>> getKandang();

    @GET(EndPoint.API_KANDANG)
    Call<BaseResponse.BaseResponseApi<List<Data>>> apiRead(@Query("id_user") String id_user);

    @GET(EndPoint.API_KANDANG_WHERE_IDKANDANG)
    Call<BaseResponse.BaseResponseApi<List<Data>>> apiReadWhereId(@Query("id_kandang") String id_kandang);



    //Pakan
    @GET(EndPoint.API_PAKAN)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPakan>>> apigetPakan(@Query("id_kandang") String id_kandang);

    //Vitamin
    @GET(EndPoint.API_VITAMIN)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVitamin>>> apigetVitamin(@Query("id_kandang") String id_kandang);

    //Obat
    @GET(EndPoint.API_OBAT)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataObat>>> apigetObat(@Query("id_kandang") String id_kandang);

    //Vaksin
    @GET(EndPoint.API_VAKSIN)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataVaksin>>> apigetVaksin(@Query("id_kandang") String id_kandang);

    //Peralatan
    @GET(EndPoint.API_PERALATAN)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataPeralatan>>> apigetPeralatan(@Query("id_kandang") String id_kandang);

    //Item Lain
    @GET(EndPoint.API_ITEMLAIN)
    Call<BaseResponse.BaseResponseApi<List<DataInventory.DataItemLain>>> apigetItemLain(@Query("id_kandang") String id_kandang);

    //stok
    @GET(EndPoint.API_STOK)
    Call<BaseResponse.BaseResponseApi<List<DataStok>>> apigetStok(@Query("id_kategori") String id_kategori);


//    //kode Request
//    @FormUrlEncoded
//    @POST(EndPoint.API_REQUEST)
//    Call<BaseResponse.BaseResponseApi> requestInventory(@Field("id_barang") String id_barang,
//                                                        @Field("jumlah_request") String jumlah_request,
//                                                        @Field("id_kandang") String id_kandang,
//                                                        @Field("catatan") String catatan,
//                                                        @Field("tanggal_request") String tanggal_request);

    //kode Request
    @FormUrlEncoded
    @POST(EndPoint.API_REQUEST)
    Call<BaseResponse.BaseResponseApi> notifikasi(@Field("id_barang") String id_barang,
                                                        @Field("jumlah_request") String jumlah_request,
                                                        @Field("id_kandang") String id_kandang,
                                                        @Field("catatan") String catatan,
                                                        @Field("tanggal_request") String tanggal_request);

    @GET(EndPoint.API_REQUEST)
    Call<BaseResponse.BaseResponseApi<List<DataRequest>>> apiReadRequest(@Query("id_kandang") String id_kandang);


    //hapus request inventory
    @FormUrlEncoded
    @POST(EndPoint.API_REQUEST_DELETE)
    Call<BaseResponse.BaseResponseApi> deleteRequestInventory(@Field("id") String id);


    @FormUrlEncoded
    @POST(EndPoint.API_KANDANG)
    Call<BaseResponse.BaseResponseApi> tambahKandang(@Field("alamat_kandang") String alamat,
                                           @Field("kode_kandang") String kode_kandang,
                                           @Field("kode_blok") String kode_blok,
                                           @Field("jenis_kandang") String jenis_kandang,
                                           @Field("id_user") String id_user);


    @FormUrlEncoded
    @POST(EndPoint.API_CHICKIN)
    Call<BaseResponse.BaseResponseApi> tambahChickin(@Field("type_produk") String type_produk,
                                                     @Field("populasi_masuk") String populasi_masuk,
                                                     @Field("kondisi_chick_in") String kondisi_chick_in,
                                                     @Field("harga_satuan") String harga_satuan,
                                                     @Field("jenis_produk") String jenis_produk,
                                                     @Field("tanggal_chickin") String tanggal_chickin,
                                                     @Field("id_kandang") String id_kandang,
                                                     @Field("periode") String periode,
                                                     @Field("total_ayam_saat_ini") String total_ayam_saat_ini,
                                                     @Field("berat_doc") String berat_doc);

    @FormUrlEncoded
    @POST(EndPoint.API_CHICKIN_EDIT)
    Call<BaseResponse.BaseResponseApi> updateChickin(@Field("id_chickin") String id_chickin,
                                                     @Field("type_produk") String type_produk,
                                                     @Field("populasi_masuk") String populasi_masuk,
                                                     @Field("kondisi_chick_in") String kondisi_chick_in,
                                                     @Field("harga_satuan") String harga_satuan,
                                                     @Field("jenis_produk") String jenis_produk,
                                                     @Field("tanggal_chickin") String tanggal_chickin,
                                                     @Field("id_kandang") String id_kandang,
                                                     @Field("periode") String periode,
                                                     @Field("total_ayam_saat_ini") String total_ayam_saat_ini,
                                                     @Field("berat_doc") String berat_doc);

    @GET(EndPoint.API_CHICKIN)
    Call<BaseResponse.BaseResponseApi<List<DataChickin>>> apiReadChickinWhereId(@Query("id_kandang") String id_kandang);

    @FormUrlEncoded
    @PUT(EndPoint.API_KANDANG)
    Call<BaseResponse.BaseResponseApi> tambahPersiapanKandang(@Field("id_kandang") String id_kandang,
                                                              @Field("nilai_progress") float nilai_progress,
                                                              @Field("status_kandang") String status_kandang,
                                                              @Field("syarat_kesiapan_1") String syarat_kesiapan_1,
                                                              @Field("syarat_kesiapan_2") String syarat_kesiapan_2,
                                                              @Field("syarat_kesiapan_3") String syarat_kesiapan_3,
                                                              @Field("syarat_kesiapan_4") String syarat_kesiapan_4,
                                                              @Field("syarat_kesiapan_5") String syarat_kesiapan_5,
                                                              @Field("syarat_kesiapan_6") String syarat_kesiapan_6,
                                                              @Field("syarat_kesiapan_7") String syarat_kesiapan_7,
                                                              @Field("syarat_kesiapan_8") String syarat_kesiapan_8,
                                                              @Field("syarat_kesiapan_9") String syarat_kesiapan_9,
                                                              @Field("syarat_kesiapan_10") String syarat_kesiapan_10);

    @FormUrlEncoded
    @POST(EndPoint.API_LOGIN)
    Call<BaseResponse.user> postLogin(@Field("username") String username,
                                 @Field("email") String email,
                                 @Field("password") String password);


    @GET(EndPoint.API_LOGIN)
    Call<BaseResponse.GetUserLogin> getUserLogin(@Query("id_user") String id_user);

    @FormUrlEncoded
    @POST(EndPoint.API_REGISTER)
    Call<BaseResponse.BaseResponseApi> perfomRegister(@Field("nama") String nama,
                                      @Field("username") String username,
                                      @Field("alamat") String alamat,
                                      @Field("email") String email,
                                      @Field("no_telephone") String no_telephone,
                                        @Field("password") String password,
                                                      @Field("status") String status,
                                                      @Field("konfirmasi_password") String konfirmasi_password);

    @FormUrlEncoded
    @POST(EndPoint.API_EDIT_PROFILE)
    Call<BaseResponse.BaseResponseApi> editProfile(@Field("id_user") String id_user,
                                                   @Field("nama") String nama,
                                                   @Field("username") String username,
                                                   @Field("alamat") String alamat,
                                                   @Field("email") String email,
                                                   @Field("no_telephone") String no_telephone,
                                                   @Field("password") String password);

    @GET(EndPoint.API_REGISTER)
    Call<BaseResponse.BaseResponseApi<List<DataUser>>> getuserWhereID(@Query("id_user") String id_user);

    @GET(EndPoint.API_MEDIS)
    Call<BaseResponse.BaseResponseApi<List<MedisData>>> getMedis();
}
