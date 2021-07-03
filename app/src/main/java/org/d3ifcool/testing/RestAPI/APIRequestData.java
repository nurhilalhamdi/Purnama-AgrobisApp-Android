package org.d3ifcool.testing.RestAPI;

import org.d3ifcool.testing.Datas.GetSampel;
import org.d3ifcool.testing.LoginAndRegister.GetDataUserLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIRequestData {


    @Multipart
    @POST("ApiHarian/CekHarian")
    Call<DataAPI.user> inputDataHarian(@Part MultipartBody.Part file,
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
    @POST("ApiSampel/CekSampel")
    Call<DataAPI.user> inputDataSampel(
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
                                 @Part MultipartBody.Part image,
                                 @Part("id_user")RequestBody id_user,
                                 @Part("id_kandang")RequestBody id_kandang);

    @GET("ApiSampel/CekSampel")
    Call<GetSampel> getSampel(@Query("id_user") String id_user);

    @GET("ApiLogin/UserLogin")
    Call<GetDataUserLogin> getUserLogin(@Query("id_user") String id_user);

    @FormUrlEncoded
    @POST("ApiRegister/UserRegister")
    Call<DataAPI.user> perfomRegister(@Field("nama") String nama,
                                      @Field("username") String username,
                                      @Field("alamat") String alamat,
                                      @Field("email") String email,
                                      @Field("no_telephone") String no_telephone,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("ApiLogin/UserLogin")
    Call<DataAPI.user> postLogin(@Field("username") String username,
                                    @Field("email") String email,
                                    @Field("password") String password);

    @GET("ApiKandang/Kandang")
    Call<DataAPI.kandangApi> getKandang(@Query("id_user") String id_user);

//    @FormUrlEncoded
//    @POST("ApiKandang/Kandang")
//    Call<DataAPI.kandangApi> tambahKandang(@Field("alamat_kandang") String alamat,
//                                      @Field("kode_kandang") String kode_kandang,
//                                      @Field("kode_blok") String kode_blok,
//                                      @Field("jenis_kandang") String jenis_kandang,
//                                      @Field("id_user") String id_user);
}