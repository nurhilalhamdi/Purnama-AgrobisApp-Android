package org.d3ifcool.testing.PerformaKandang;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.orgd3if4019.testing.R;

import org.d3ifcool.testing.Datas.DataChickin;
import org.d3ifcool.testing.Datas.DataPerforma;
import org.d3ifcool.testing.Network.Response.BaseResponse;
import org.d3ifcool.testing.Network.Service.DataService;
import org.d3ifcool.testing.Network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformaKandangFragment extends Fragment {


    private static final String KODE_KANDANG = "kodekandang";
    private static final String ALAMAT_KANDANG = "alamatkandang";
    private static final String ID_KANDANG = "idkandang";

    PieChart pieChart;
    Spinner spinnerPeriodePerforma;

    private DataService service;

    private String getAlamatKandang;
    private String getKodeKandang;
    private String getIdKandang;


    TextView textViewKodeKandang;
    TextView textViewAlamatKandang;
    TextView textViewPeriodeKandang;
    TextView textViewTanggalPerforma;
    TextView textViewPopulasiPeriodeAwal;
    TextView sisaAyam;

    TextView txtDeplesi;
    TextView txtFCR;
    TextView txtKematian;
    TextView txtABW;
    TextView txtFeedIntake;
    TextView txtPBB;
    TextView txtIP;

    public PerformaKandangFragment() {

    }

    public static PerformaKandangFragment newInstance(String kodekandang,
                                                      String alamatkandang,
                                                      String idkandang) {
        PerformaKandangFragment fragment = new PerformaKandangFragment();
        Bundle args = new Bundle();
        args.putString(KODE_KANDANG, kodekandang);
        args.putString(ALAMAT_KANDANG, alamatkandang);
        args.putString(ID_KANDANG, idkandang);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getKodeKandang = getArguments().getString(KODE_KANDANG);
            getAlamatKandang = getArguments().getString(ALAMAT_KANDANG);
            getIdKandang = getArguments().getString(ID_KANDANG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view;
        view = inflater.inflate(R.layout.fragment_performa_kandang, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBarPerforma);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        service = ServiceGenerator.createBaseService(this.getContext(), DataService.class);
        pieChart = view.findViewById(R.id.chart_pie);


        textViewKodeKandang = view.findViewById(R.id.txt_kode_kandang_performa);
        textViewAlamatKandang = view.findViewById(R.id.txt_alamat_kandang_performa);
        textViewPeriodeKandang = view.findViewById(R.id.txt_periode_performa);
        textViewTanggalPerforma = view.findViewById(R.id.txt_tanggal_kandang_mulai_performa);
        textViewPopulasiPeriodeAwal = view.findViewById(R.id.txt_populasi_periode_awal);
        txtKematian = view.findViewById(R.id.txt_kematian_performa);
        txtABW = view.findViewById(R.id.txt_abw);
        sisaAyam = view.findViewById(R.id.sisaAyam);
        txtFeedIntake = view.findViewById(R.id.txt_feed_intake);
        spinnerPeriodePerforma = view.findViewById(R.id.spinner_periode_performa);

        txtDeplesi = view.findViewById(R.id.txt_deplesi);
        txtFCR = view.findViewById(R.id.txt_fcr_performa);
        txtPBB = view.findViewById(R.id.txt_pbb);
        txtIP = view.findViewById(R.id.txt_ip_performa);

        textViewKodeKandang.setText(getKodeKandang);
        textViewAlamatKandang.setText(getAlamatKandang);

        setupPieChart();
//        loadPieChartData();
        SpinnerPilihPeriode(getIdKandang);

        return view;
    }




    public void SpinnerPilihPeriode(String idkandang) {
        Call<BaseResponse.BaseResponseApi<List<DataChickin>>> calls = service.apiReadChickinWhereId(idkandang);
        calls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataChickin>>>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.DataChickin>>> call, Response<BaseResponse.BaseResponseApi<List<DataChickin>>> response) {
                if (response.code() == 200) {
                    List<DataChickin> datas = response.body().getData();
                    List<String> listSpinnerPerforma = new ArrayList<String>();


                    for (int i = 0; i < datas.size(); i++) {

                        listSpinnerPerforma.add("Periode " + datas.get(i).getPeriode());
                    }
                    ArrayAdapter<String> spinnerArrayAdapterPilihPeriode = new ArrayAdapter<String>(
                            getContext(), R.layout.dropdown_item, listSpinnerPerforma);
                    spinnerArrayAdapterPilihPeriode.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerPeriodePerforma.setAdapter(spinnerArrayAdapterPilihPeriode);
                    spinnerPeriodePerforma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "" + datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getPeriode(), Toast.LENGTH_SHORT).show();
                            textViewPeriodeKandang.setText(datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getPeriode());
                            textViewTanggalPerforma.setText(datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getTanggal_chickin());
                            textViewPopulasiPeriodeAwal.setText("Populasi Awal " +datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getPopulasi_masuk());
                            sisaAyam.setText(datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getTotal_ayam_saat_ini());

//

                            Call<BaseResponse.BaseResponseApi<List<DataPerforma>>> call = service.apiGetPerforma(idkandang,datas.get(spinnerPeriodePerforma.getSelectedItemPosition()).getPeriode());
                            call.enqueue(new Callback<BaseResponse.BaseResponseApi<List<DataPerforma>>>() {
                                @Override
                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<DataPerforma>>> call, Response<BaseResponse.BaseResponseApi<List<DataPerforma>>> response) {
                                    if (response.code() == 200) {
                                        ArrayList<DataPerforma> dataArrayList = new ArrayList<>(response.body().getData());

                                        for (DataPerforma dataPerforma : dataArrayList){


                                            if (dataPerforma.getTotal_ayam_saat_ini() == null
                                                    && dataPerforma.getJumlah_panen() == null
                                                    && dataPerforma.getAyam_mati() == null
                                                    && dataPerforma.getAyam_sakit() == null
                                                    && dataPerforma.getGagal_panen() == null
                                                    && dataPerforma.getPopulasi_masuk() == null
                                                    && dataPerforma.getJumlah_panen() == null
                                                    && dataPerforma.getTotal_konsumsi_pakan() == null
                                                    && dataPerforma.getTotal_berat_keseluruhan() == null
                                                    && dataPerforma.getBerat_panen_ekor() == null
                                                    && dataPerforma.getBerat_doc() == null
                                                    && dataPerforma.getUmur_panen() == null){

                                                pieChart.clear();
                                                txtFCR.setText(String.format("%.3f", 0f) + " %");
                                                txtDeplesi.setText("0 %");
                                                txtKematian.setText(String.format("%.4f", 0f) + " %");
                                                txtIP.setText(String.format("%.4f", 0f) + " %");
                                                txtABW.setText(0f+"");
                                                txtFeedIntake.setText(0f+"");
                                                txtPBB.setText(0+"");
                                            }else {
                                                loadPieChartData(
                                                        Float.parseFloat(dataPerforma.getTotal_ayam_saat_ini()),
                                                        Float.parseFloat(dataPerforma.getJumlah_panen()),
                                                        Float.parseFloat(dataPerforma.getAyam_mati()),
                                                        Float.parseFloat(dataPerforma.getAyam_sakit()),
                                                        Float.parseFloat(dataPerforma.getGagal_panen()));

                                                ///Deplesi-----------
                                                double populasiawal = Double.parseDouble(dataPerforma.getPopulasi_masuk());
                                                double populasipanen = Double.parseDouble(dataPerforma.getJumlah_panen());
                                                double temp1 = populasiawal-populasipanen;
                                                double temp2 = temp1/populasiawal;
                                                double temp3 = temp2*100;

                                                //FCR-----------
                                                int totalkonsumsipakan = Integer.parseInt(dataPerforma.getTotal_konsumsi_pakan());
                                                double totalbobotpanen = Double.parseDouble(dataPerforma.getTotal_berat_keseluruhan());
                                                double temptotalFCR = totalkonsumsipakan/totalbobotpanen;


                                                //Kematian
                                                double jumlahAyamMati = Double.parseDouble(dataPerforma.getAyam_mati());
                                                double tempTotalKematian = jumlahAyamMati/populasiawal;
                                                double hasilkematian = tempTotalKematian*100;

                                                //ABW
                                                double hasilABW = totalbobotpanen/populasipanen;

                                                //FEED INTAKE
                                                double hasilFeedintake = totalkonsumsipakan/populasipanen;

                                                //PBB
                                                int beratpanenekor = Integer.parseInt(dataPerforma.getBerat_panen_ekor());
                                                int beratdoc = Integer.parseInt(dataPerforma.getBerat_doc());
                                                int tempPBB = beratpanenekor-beratdoc;
                                                int umurPanen = Integer.parseInt(dataPerforma.getUmur_panen());

                                                //IP
                                                int tempA = 100-10;
                                                double tempB = tempA*hasilABW;

                                                double tempC = temptotalFCR*umurPanen;
                                                double tempD = tempB/tempC;
                                                double hasilIP = tempD*100;



                                                txtFCR.setText(String.format("%.2f", temptotalFCR) + " %");
                                                txtDeplesi.setText(temp3+"");
                                                txtKematian.setText(String.format("%.2f", hasilkematian) + " %");
                                                txtABW.setText(hasilABW+"");
                                                txtFeedIntake.setText(String.format("%.2f", hasilFeedintake));
                                                txtPBB.setText(tempPBB+"");
                                                txtIP.setText(hasilIP + " %");

                                            }

                                        }

                                    } else if (response.code() == 500) {

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataPerforma>>> call, Throwable t) {
                                    Log.e(".error", t.toString());
                                }
                            });



//                            Call<BaseResponse.BaseResponseApi<List<Data>>> kandangCalls = service.apiRead(getIdKandang);
//                            kandangCalls.enqueue(new Callback<BaseResponse.BaseResponseApi<List<Data>>>() {
//                                @SuppressLint({"SetTextI18n", "DefaultLocale"})
//                                @Override
//                                public void onResponse(Call<BaseResponse.BaseResponseApi<List<org.d3ifcool.testing.Datas.Data>>> call, Response<BaseResponse.BaseResponseApi<List<Data>>> response) {
//                                    if (response.code() == 200) {
//                                        List<org.d3ifcool.testing.Datas.Data> dataKandang = response.body().getData();
//                                        for (Data data2 : dataKandang){
//
//                                        }
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<BaseResponse.BaseResponseApi<List<Data>>> call, Throwable t) {
//
//                                }
//                            });//

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }else if (response.code() == 500) {

//                    rvData.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
//                    rvData.setAdapter(adapter);
//                    btnCetakPDFSampel.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(getContext(), "Maaf, Data Sampel Masih Kosong Untuk Melakukan Tindakan Ini", Toast.LENGTH_SHORT).show();
//                        }
//                    });


                }
            }

            @Override
            public void onFailure(Call<BaseResponse.BaseResponseApi<List<DataChickin>>> call, Throwable t){

            }
        });

    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterText("Populasi");
        pieChart.setCenterTextSize(18);
        pieChart.setExtraOffsets(0f,0f,30f,0f);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void loadPieChartData(float sisaAyam,
                                  float jumlahPanen,
                                  float jumlahMati,
                                  float jumlahsakit,
                                  float gagalpanen){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(jumlahPanen,"Panen"));
        entries.add(new PieEntry(sisaAyam,"Sisa Ayam"));
        entries.add(new PieEntry(jumlahMati,"Ayam Mati"));
        entries.add(new PieEntry(jumlahsakit,"Ayam Sakit"));
        entries.add(new PieEntry(gagalpanen,"Gagal Panen"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries,"Populasi Ayam");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));

        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

    }


}