package com.example.rajaongkir;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rajaongkir.api.RegisterAPI;
import com.example.rajaongkir.api.ServerAPI;
import com.example.rajaongkir.model.Kota;
import com.example.rajaongkir.model.Provinsi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Provinsi provinsi;
    Kota kota;
    Button btncekongkir;
    TextView tvongkir, tvlamakirim;
    Spinner spinprovinsi, spinKota;
    ArrayList<String> province_name = new ArrayList<>();
    ArrayList<String> city_name = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<Integer> province_id = new ArrayList<>();
    ArrayList<Integer> city_id = new ArrayList<>();
    int id_kota_tujuan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvongkir = (TextView) findViewById(R.id.tvOngkir);
        tvlamakirim = (TextView) findViewById(R.id.tvLamaKirim);
        spinprovinsi = (Spinner) findViewById(R.id.spinProvinsi);
        spinKota = (Spinner) findViewById(R.id.spinKota);
        btncekongkir = (Button) findViewById(R.id.btnCekOngkir);
        btncekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekOngkir("399", ""+id_kota_tujuan, 1, "jne");
            }
        });

        load_provinsi();

        spinprovinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int kode = 0;
                Log.i("info spin touch", ""+province_name.get(position)+" " +
                        "Kode ; "+province_id.get(position));
                load_kota(province_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                id_kota_tujuan = city_id.get(position);
                Log.i("info city touch", ""+id_kota_tujuan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void load_provinsi(){
        ServerAPI urlAPI = new ServerAPI();
        String URL = urlAPI.Base_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<ResponseBody> call = api.get_provinsi();
        Log.i("info load", "Persiapan Masuk Enqueue");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("info Respon 001 : ", response.toString());
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.i("info Respon 002",json.get("rajaongkir").toString());
                    provinsi = new Provinsi();
                    JSONArray jsonarr = new JSONArray();
                    jsonarr = json.getJSONObject("rajaongkir").getJSONArray("results");
                    for(int i = 0;i<jsonarr.length();i++){
                        Log.i("info Respon 003",jsonarr.toString());
                        provinsi.province_id=Integer.parseInt(jsonarr.getJSONObject(i)
                                .get("province_id").toString());
                        provinsi.province_name=jsonarr.getJSONObject(i)
                                .get("province").toString();
                        province_name.add(provinsi.get_province_name());
                        province_id.add(provinsi.get_province_id());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, province_name);
                    spinprovinsi.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("Info Respon Error",e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("info gagal","Gagal "+t.getMessage());
                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load_kota(int province_id){
        ServerAPI urlAPI = new ServerAPI();
        String URL = urlAPI.Base_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<ResponseBody> call = api.get_kota(province_id);
        Log.i("info load 201","persiapan masuk enqueue, provinsi id : "+province_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.i("info respon 202",json.get("rajaongkir").toString());
                    kota = new Kota();
                    city_name.clear();
                    city_id.clear();
                    type.clear();

                    JSONArray jsonarr = new JSONArray();
                    jsonarr = json.getJSONObject("rajaongkir").getJSONArray("results");

                    for(int i=0;i<jsonarr.length();i++){
                        kota.city_id=Integer.parseInt(jsonarr.getJSONObject(i).get("city_id").toString());
                        kota.city_name=jsonarr.getJSONObject(i).get("city_name").toString();
                        kota.type=jsonarr.getJSONObject(i).get("type").toString();
                        Log.i("info add array ", kota.get_city_name());
                        city_name.add(kota.get_type()+ " " + kota.get_city_name());
                        city_id.add(kota.get_city_id());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, city_name);
                    spinKota.setAdapter(adapter);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.e("error 201",e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error 202",t.toString());
            }
        });
    }

    public void cekOngkir(String asal, String tujuan, int berat, String kurir){
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setCancelable(false);

        pd.setMessage("sedang mengambil data...");
        pd.setProgress(0);
        pd.setMax(100);
        pd.show();

        ServerAPI urlAPI = new ServerAPI();
        String URL = urlAPI.Base_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);

        api.cekongkir(asal, tujuan, berat, kurir).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.i("info respon",json.toString());
                    JSONArray cost = new JSONArray();
                    cost=json.getJSONObject("rajaongkir").
                            getJSONArray("results")
                            .getJSONObject(0)
                            .getJSONArray("costs")
                            .getJSONObject(1)
                            .getJSONArray("cost");
                    tvongkir.setText("Rp"+cost.getJSONObject(0).get("value"));
                    tvlamakirim.setText(cost.getJSONObject(0).get("etd").toString()+" Hari");
                    pd.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"cek ongkir gagal "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}